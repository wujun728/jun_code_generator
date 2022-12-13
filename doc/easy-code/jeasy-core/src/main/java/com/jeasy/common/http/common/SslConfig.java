package com.jeasy.common.http.common;

import com.jeasy.common.http.exception.HttpProcessException;
import com.jeasy.common.io.IoKit;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;


/**
 * 设置ssl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
public class SslConfig {

    private static final SslHandler SIMPLE_VERIFIER = new SslHandler();
    private static final String[] SUPPORTED_PROTOCOLS = new String[]{"TLSv1.2", "TLSv1.1", "SSLv3", "SSLv2Hello"};
    private static SSLSocketFactory sslFactory;
    private static SSLConnectionSocketFactory sslConnFactory;
    private static SSLIOSessionStrategy sslIOSessionStrategy;
    private static SslConfig sslutil = new SslConfig();
    private SSLContext sc;

    public static SslConfig getInstance() {
        return sslutil;
    }

    public static SslConfig custom() {
        return new SslConfig();
    }

    /**
     * 重写X509TrustManager类的三个方法,信任服务器证书
     */
    private static class SslHandler implements X509TrustManager, HostnameVerifier {

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
            //return null;
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        @Override
        public boolean verify(String paramString, SSLSession paramSSLSession) {
            return true;
        }
    }

    /**
     * 信任主机
     *
     * @return
     */
    public static HostnameVerifier getVerifier() {
        return SIMPLE_VERIFIER;
    }

    public synchronized SSLSocketFactory getSSLSF() throws HttpProcessException {
        if (sslFactory != null) {
            return sslFactory;
        }

        try {
            SSLContext sc = getSSLContext();
            sc.init(null, new TrustManager[]{SIMPLE_VERIFIER}, null);
            sslFactory = sc.getSocketFactory();
        } catch (KeyManagementException e) {
            throw new HttpProcessException(e);
        }
        return sslFactory;
    }

    public synchronized SSLConnectionSocketFactory getSSLCONNSF() throws HttpProcessException {
        if (sslConnFactory != null) {
            return sslConnFactory;
        }

        try {
            SSLContext sc = getSSLContext();
            sc.init(null, new TrustManager[]{SIMPLE_VERIFIER}, new java.security.SecureRandom());
            sslConnFactory = new SSLConnectionSocketFactory(sc, SUPPORTED_PROTOCOLS, null, SIMPLE_VERIFIER);
        } catch (KeyManagementException e) {
            throw new HttpProcessException(e);
        }
        return sslConnFactory;
    }

    public synchronized SSLIOSessionStrategy getSSLIOSS() throws HttpProcessException {
        if (sslIOSessionStrategy != null) {
            return sslIOSessionStrategy;
        }

        try {
            SSLContext sc = getSSLContext();
            sc.init(null, new TrustManager[]{SIMPLE_VERIFIER}, new java.security.SecureRandom());
            sslIOSessionStrategy = new SSLIOSessionStrategy(sc, SIMPLE_VERIFIER);
        } catch (KeyManagementException e) {
            throw new HttpProcessException(e);
        }
        return sslIOSessionStrategy;
    }

    public SslConfig customSSL(String keyStorePath, String keyStorepass) throws HttpProcessException {
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(keyStorePath));
            trustStore.load(instream, keyStorepass.toCharArray());
            // 相信自己的CA和所有自签名的证书
            sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
        } catch (KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            throw new HttpProcessException(e);
        } finally {
            IoKit.close(instream);
        }
        return this;
    }

    public SSLContext getSSLContext() throws HttpProcessException {
        try {
            if (sc == null) {
                sc = SSLContext.getInstance("SSLv3");
            }
            return sc;
        } catch (NoSuchAlgorithmException e) {
            throw new HttpProcessException(e);
        }
    }
}
