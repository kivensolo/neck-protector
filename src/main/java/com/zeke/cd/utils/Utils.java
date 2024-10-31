package com.zeke.cd.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.WeakHashMap;
import java.util.stream.Stream;

/**
 * md5工具类
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-05-04
 */
public class Utils {

    public static String calMD5(String imageKey) {
        String localCacheKey = calMD5(imageKey.getBytes());
        if (localCacheKey == null) {
            return imageKey;
        }
        return localCacheKey;
    }

    private static final WeakHashMap<Thread, MessageDigestCtx> _threadHashMap = new WeakHashMap<>();
    private static char[] hexCharMap = new char[]{
            '0', '1', '2', '3',
            '4', '5', '6', '7',
            '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F',
    };

    public static String calMD5(byte[] data) {
        MessageDigestCtx md5 = getMD5();
        if (md5 != null) {
            return String.valueOf(md5.digest(data));
        } else {
            return null;
        }
    }

    private static class MessageDigestCtx {
        MessageDigest digest;
        char[] digestStr = new char[32];

        public MessageDigestCtx(MessageDigest digest) {
            this.digest = digest;
        }

        public void reset() {
            digest.reset();
        }

        public char[] digest(byte[] data) {
            byte[] digestVal = digest.digest(data);
            for (int i = 0; i < 16; ++i) {
                int b = digestVal[i] & 0xFF;
                digestStr[i * 2] = hexCharMap[b / 16];
                digestStr[i * 2 + 1] = hexCharMap[b % 16];
            }
            return digestStr;
        }
    }

    private static MessageDigestCtx getMD5() {
        synchronized (_threadHashMap) {
            Thread thread = Thread.currentThread();
            MessageDigestCtx messageDigest = _threadHashMap.get(thread);
            if (messageDigest == null) {
                try {
                    MessageDigest md5 = MessageDigest.getInstance("md5");
                    MessageDigestCtx digestCtx = new MessageDigestCtx(md5);
                    _threadHashMap.put(thread, digestCtx);
                    return digestCtx;
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            messageDigest.reset();
            return messageDigest;
        }
    }

    @SuppressWarnings("Duplicates")
    public static String join(String sep, String[] container) {
        StringBuilder stringBuilder = new StringBuilder();
        Stream.of(container).forEach(o -> {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(sep);
            }
            stringBuilder.append(o);
        });
        return stringBuilder.toString();
    }

    @SuppressWarnings("Duplicates")
    public static String join(String sep, Iterable<? extends CharSequence> container) {
        StringBuilder stringBuilder = new StringBuilder();
        Stream.of(container).forEach(o -> {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(sep);
            }
            stringBuilder.append(o);
        });
        return stringBuilder.toString();
    }

    public static boolean isVersionLessOrEqu(String version1, String version2) {
        if(version1.equals(version2)) {
            return true;
        }
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int len = Math.min(v1.length, v2.length);
        for (int i = 0; i < len; i++) {
            int n1 = Integer.parseInt(v1[i]);
            int n2 = Integer.parseInt(v2[i]);
            if (n1 < n2) {
                return true;
            } else if (n1 > n2) {
                return false;
            }
        }
        return v1.length < v2.length;
    }
}
