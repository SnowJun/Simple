package org.simple.net.https;

import org.simple.net.util.SimpleLog;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * org.simple.net.https
 *
 * @author Simple
 * @date 2020/9/28
 * @desc
 */
public class Https {


    public class HttpsParas {
        private SSLSocketFactory sslSocketFactory;
        private X509TrustManager x509TrustManager;
        private HostnameVerifier trustAllHost = new TrustAllHost();

        public SSLSocketFactory getSslSocketFactory() {
            return sslSocketFactory;
        }

        public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
        }

        public X509TrustManager getX509TrustManager() {
            return x509TrustManager;
        }

        public void setX509TrustManager(X509TrustManager x509TrustManager) {
            this.x509TrustManager = x509TrustManager;
        }

        public HostnameVerifier getTrustAllHost() {
            return trustAllHost;
        }

        public void setTrustAllHost(HostnameVerifier trustAllHost) {
            this.trustAllHost = trustAllHost;
        }
    }



    public HttpsParas getHttpsParas(){
        return getHttpsParas(null,null,null,null);
    }

    /**
     * 生成https需要的参数
     *
     * @param trustManager
     * @param bksFile
     * @param password
     * @param certificates
     * @return
     */
    public HttpsParas getHttpsParas(X509TrustManager trustManager, InputStream bksFile, String password, InputStream... certificates) {
        HttpsParas paras = new HttpsParas();
        try {
            KeyManager[] keyManagers = getKeyManager(bksFile, password);
            TrustManager[] trustManagers = getTrustManager(certificates);
            X509TrustManager manager;
            if (trustManager != null) {
                //优先使用用户自定义的TrustManager
                manager = trustManager;
            } else if (trustManagers != null) {
                //然后使用默认的TrustManager
                manager = select(trustManagers);
            } else {
                //否则使用不安全的TrustManager
                manager = new TrustAllX509TrustManager();
            }
            // 创建TLS类型的SSLContext对象， that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            // 用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
            // 第一个参数是授权的密钥管理器，用来授权验证，比如授权自签名的证书验证。第二个是被授权的证书管理器，用来验证服务器端的证书
            sslContext.init(keyManagers, new TrustManager[]{manager}, null);
            // 通过sslContext获取SSLSocketFactory对象
            paras.setSslSocketFactory(sslContext.getSocketFactory());
            paras.setX509TrustManager(manager);
            return paras;
        } catch (NoSuchAlgorithmException e) {
            SimpleLog.e(e.getMessage());
            e.printStackTrace();
        } catch (KeyManagementException e) {
            SimpleLog.e(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private X509TrustManager select(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }


    private KeyManager[] getKeyManager(InputStream bksInputStream, String password) {
        try {
            if (null == bksInputStream || null == password) {
                return null;
            }
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksInputStream, password.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientKeyStore, password.toCharArray());
            return kmf.getKeyManagers();
        } catch (Exception e) {
            SimpleLog.e(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param certificateInputStreams
     * @return
     */
    private TrustManager[] getTrustManager(InputStream... certificateInputStreams) {
        if (null == certificateInputStreams || certificateInputStreams.length == 0) {
            return null;
        }
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(".x509");
            // 创建一个默认类型的KeyStore,存储需要新任的证书
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            for (int i = 0; i < certificateInputStreams.length; i++) {
                String certificateAlias = Integer.toString(i);
                //生产并存储
                Certificate cert = certificateFactory.generateCertificate(certificateInputStreams[i]);
                keyStore.setCertificateEntry(certificateAlias, cert);
                try {
                    if (null != certificateInputStreams[i]) {
                        certificateInputStreams[i].close();
                    }
                } catch (IOException e) {
                    SimpleLog.e(e.getMessage());
                    e.printStackTrace();
                }
            }
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            return trustManagerFactory.getTrustManagers();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
