package org.simple.net.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * org.simple.net.https
 *
 * @author Simple
 * @date 2020/9/28
 * @desc
 * 信任所有host
 */
public class TrustAllHost implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
