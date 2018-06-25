package com.rcaller.service.healtcheck.impl;

import java.nio.charset.Charset;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.rcaller.dto.HealthCheckResponse;
import com.rcaller.service.healtcheck.RCallerHealtCheckService;


/**
 * Created by Mikhail_Asadchy (EPAM)
 */
@Component
public class RCallerHealtCheckServiceImpl implements RCallerHealtCheckService {

   @Resource
   private RestTemplate template;

   @Value("${healtcheck.url}")
   private String healtcheckUrl;
   @Value("${healtcheck.username}")
   private String username;
   @Value("${healtcheck.password}")
   private String password;

   @Value("${healtcheck.report.success.message}")
   private String successMessage;
   @Value("${healtcheck.report.rootFailed.message}")
   private String rootFailedMessage;
   @Value("${healtcheck.report.mailFailed.message}")
   private String mailFailedMessage;
   @Value("${healtcheck.report.diskFailed.message}")
   private String diskFailedMessage;
   @Value("${healtcheck.report.dbFailed.message}")
   private String dbFailedMessage;
   @Value("${healtcheck.failedToRespond.message}")
   private String failedToRespondMessage;

   @Value("${healtcheck.success.status}")
   private String successStatus;

   @Override
   public String getHealthCheckStatus() {
      final ResponseEntity<HealthCheckResponse> response = template.exchange(healtcheckUrl, HttpMethod.GET,
            new HttpEntity<>(createHeaders(username, password)), HealthCheckResponse.class);
      return processResponse(response);
   }

   private String processResponse(final ResponseEntity<HealthCheckResponse> response) {
      final HttpStatus statusCode = response.getStatusCode();
      if (statusCode != HttpStatus.OK) {
         return String.format(failedToRespondMessage, statusCode);
      }
      else {
         final HealthCheckResponse body = response.getBody();
         final boolean generalStatusFailed = checkStatusFailed(body.getStatus());
         final boolean mailStatusFailed = checkStatusFailed(body.getMail().getStatus());
         final boolean diskStatusFailed = checkStatusFailed(body.getDiskSpace().getStatus());
         final boolean databaseStatusFailed = checkStatusFailed(body.getDb().getStatus());

         if (!generalStatusFailed && !mailStatusFailed && !diskStatusFailed && !databaseStatusFailed) {
            return getSuccessMessage();
         }
         else {
            return buildErrorMessage(body, generalStatusFailed, mailStatusFailed, diskStatusFailed, databaseStatusFailed);
         }
      }

   }

   private String buildErrorMessage(final HealthCheckResponse body, final boolean generalStatusFailed,
         final boolean mailStatusFailed, final boolean diskStatusFailed, final boolean databaseStatusFailed) {
      StringBuilder sb = new StringBuilder();
      if (generalStatusFailed) {
         sb.append(String.format(rootFailedMessage, body.getStatus()));
      }
      if (mailStatusFailed) {
         sb.append(String.format(mailFailedMessage, body.getMail().getStatus()));
      }
      if (diskStatusFailed) {
         sb.append(String.format(diskFailedMessage, body.getDiskSpace().getStatus()));
      }
      if (databaseStatusFailed) {
         sb.append(String.format(dbFailedMessage, body.getDb().getStatus()));
      }
      return sb.toString();
   }

   private String getSuccessMessage() {
      return successMessage;
   }

   private boolean checkStatusFailed(String status) {
      return !status.equals(successStatus);
   }

   private HttpHeaders createHeaders(String username, String password) {
      return new HttpHeaders() {{
         String auth = username + ":" + password;
         byte[] encodedAuth = Base64.encodeBase64(
               auth.getBytes(Charset.forName("US-ASCII")));
         String authHeader = "Basic " + new String(encodedAuth);
         set("Authorization", authHeader);
      }};
   }

}
