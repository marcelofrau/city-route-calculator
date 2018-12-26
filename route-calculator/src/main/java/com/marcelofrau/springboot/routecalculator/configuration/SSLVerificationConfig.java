package com.marcelofrau.springboot.routecalculator.configuration;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * This class configures the RestTemplate to use a certificate connection with cities-registry micro-service.
 *
 * It has stored the certificate and make sure the cities-registry implementation is using
 * the same SSL certificate to validate. In this way no service without the valid certificate would be
 * accepted.
 *
 * So to communication happen between route-calculator and cities-registry micro-services are SSL validated
 * through the certificate.
 *
 * This class can be improved in the future to allow only matching hostname to be allowed to connect.
 */
@Configuration
public class SSLVerificationConfig {

    private static final Logger logger = LoggerFactory.getLogger(SSLVerificationConfig.class);

    /**
     * This RestTemplate is using the certificate that is going to connect to cities-registry micro-service.
     *
     * @return A restTemplate that can communicate to cities-registry micro-service
     * @throws KeyStoreException If any problem reading certificate occur
     * @throws NoSuchAlgorithmException If any problem reading certificate occur
     * @throws KeyManagementException If any problem reading certificate occur
     */
    @Bean
    public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        final HostnameVerifier allPassVerifier = (String hostname, SSLSession sslSession) -> {
            return true; // ignoring hostname (TODO: this could be improved in a production environment)
        };

        final SSLContext sslContext;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(new File("keystore-city-registry.p12"),
                    "marcelofrau".toCharArray()).build();
        } catch (CertificateException e) {
            throw new RuntimeException("Error reading certificate data. Make sure the certificate is valid.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading cities certificate file.", e);
        }

        final SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, allPassVerifier);
        final CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        final BasicAuthenticationInterceptor basicAuth = new BasicAuthenticationInterceptor("route-calculator", "route-calculator");

        final RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.getInterceptors().add(basicAuth);

        return restTemplate;
    }

}
