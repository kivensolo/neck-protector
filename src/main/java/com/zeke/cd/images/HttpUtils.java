package com.zeke.cd.images;

import com.zeke.cd.ssl.PluginHostnameVerifier;
import com.zeke.cd.ssl.PluginX509TrustManager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class HttpUtils {
    public static final String GET = "GET";
    public static final String POST = "POST";

    private static final ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    public static final int READ_TIMEOUT_IN_MILLIONS = 10000;
    public static final int CONNECT_TIMEOUT_IN_MILLIONS = 6000;

    public interface CallBack {
        void onFinish(String response);

        void onError(Exception e);
    }

    public static void sendHttpRequest(final String urlPath,
                                       final String method,
                                       final CallBack listener) {
        _sendHttpRequest(urlPath, method, listener);
    }

    private static void _sendHttpRequest(String urlPath, String method, CallBack listener) {
        executorService.submit((Callable<String>) () -> {
            HttpURLConnection conn = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            try {
                URL url = new URL(urlPath);
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod(method);
                    conn.setConnectTimeout(CONNECT_TIMEOUT_IN_MILLIONS);
                    conn.setReadTimeout(READ_TIMEOUT_IN_MILLIONS);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        int len = -1;
                        byte[] buf = new byte[1024];

                        while ((len = is.read(buf)) != -1) {
                            baos.write(buf, 0, len);
                        }
                        baos.flush();
                        if (listener != null) {
                            listener.onFinish(baos.toString());
                        }
                    } else {
                        if (listener != null) {
                            listener.onError(new Exception("responseCode is not 200"));
                        }
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static void sendHttpsRequest(final String requestUrl,
                                        String outputStr,
                                        final CallBack listener) {
        executorService.submit((Callable<String>) () -> {
            _httpsRequest(requestUrl, outputStr, listener);
            return null;
        });
    }

    /**
     * 处理https GET/POST请求
     * 请求地址、请求方法、参数
     */
    private static void _httpsRequest(String requestUrl,
                                      String outputStr,
                                      CallBack listener) {
        StringBuffer buffer;
        HttpsURLConnection conn = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = {new PluginX509TrustManager()};
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setSSLSocketFactory(ssf);
            conn.setHostnameVerifier(new PluginHostnameVerifier());
            conn.connect();
            writeContentToServer(outputStr, conn);

            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            if (listener != null) {
                listener.onFinish(buffer.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onError(e);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static void downLoadFile(String urlPath, File file) {
        executorService.submit((Callable<String>) () -> {
            HttpURLConnection conn = null;
            InputStream is = null;
            OutputStream fos = null;
            try {
                URL url = new URL(urlPath);
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(CONNECT_TIMEOUT_IN_MILLIONS);
                    conn.setReadTimeout(READ_TIMEOUT_IN_MILLIONS);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    is = conn.getInputStream();
                    if (!file.exists()) {
                    }
                    fos = new FileOutputStream(file);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private static void writeContentToServer(String outputStr, HttpsURLConnection conn) throws IOException {
        if (null != outputStr) {
            OutputStream os = conn.getOutputStream();
            os.write(outputStr.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }
}
