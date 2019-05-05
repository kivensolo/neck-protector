package com.zeke.cd.ssl;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * 插件的证书信任管理器。
 * 默认为所有的链接都为安全，即信任所有证书。
 */
public class PluginX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
