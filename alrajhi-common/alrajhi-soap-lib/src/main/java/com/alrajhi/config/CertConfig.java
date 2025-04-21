package com.alrajhi.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/20/2025
 * @Time: 4:54 PM
 */
@Configuration
@Log4j2
public class CertConfig {

    @PostConstruct
    public void initCert() throws Exception {

        setupSSLFromCert();
    }

    private void setupSSLFromCert() throws Exception {
        try (InputStream certInputStream = CertConfig.class
                .getClassLoader()
                .getResourceAsStream("Cert/apps.ocp.nonprod.alrajhi.bank.crt")) {


            if (certInputStream == null) {
                throw new IllegalStateException("Certificate file not found in classpath");
            }

            // Parse the cert
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(certInputStream);

            // Create a KeyStore containing our trusted cert
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null); // create empty
            trustStore.setCertificateEntry("alrajhi-cert", cert);

            // Create a TrustManager that trusts the cert in our KeyStore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            // Set up the SSLContext to use our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

            // Set as default for all HTTPS connections
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            // Optional (not recommended for prod): disable hostname check
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            System.out.println("Custom certificate loaded from resources.");
        }
    }
}
