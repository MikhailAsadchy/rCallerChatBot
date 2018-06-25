/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.rcaller.client;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;


public class SSLIssuesIgnoringHttpClientFactory {
   public static HttpClient createHttpClient(final boolean redirectsEnabled) {
      try {
         final TrustManager[] trustAllCerts =
               { new DummyTrustManager() };

         final SSLContext sslContext = SSLContext.getInstance("TLSv1");
         sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

         final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

         httpClientBuilder.setSSLHostnameVerifier(new DummyHostnameVerifier());
         httpClientBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext, new DummyHostnameVerifier()));
         httpClientBuilder.setDefaultRequestConfig(RequestConfig.custom().setCircularRedirectsAllowed(true).setRedirectsEnabled(
               redirectsEnabled).build());

         return httpClientBuilder.build();
      }
      catch (KeyManagementException | NoSuchAlgorithmException e) {
         throw new RuntimeException(e);
      }
   }
}
