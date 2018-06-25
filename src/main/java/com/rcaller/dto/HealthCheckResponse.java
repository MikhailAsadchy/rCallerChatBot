package com.rcaller.dto;

/**
 * Created by Mikhail_Asadchy (EPAM)
 */
public class HealthCheckResponse {

   private String status;
   private Mail mail;
   private DiskSpace diskSpace;
   private Db db;

   public String getStatus() {
      return status;
   }

   public void setStatus(final String status) {
      this.status = status;
   }

   public Mail getMail() {
      return mail;
   }

   public void setMail(final Mail mail) {
      this.mail = mail;
   }

   public DiskSpace getDiskSpace() {
      return diskSpace;
   }

   public void setDiskSpace(final DiskSpace diskSpace) {
      this.diskSpace = diskSpace;
   }

   public Db getDb() {
      return db;
   }

   public void setDb(final Db db) {
      this.db = db;
   }
   public class Mail {
      private String status;
      private String location;

      public String getStatus() {
         return status;
      }

      public void setStatus(final String status) {
         this.status = status;
      }

      public String getLocation() {
         return location;
      }

      public void setLocation(final String location) {
         this.location = location;
      }

   }


   public class DiskSpace {
      private String status;
      private String total;
      private String free;
      private String threshold;

      public String getStatus() {
         return status;
      }

      public void setStatus(final String status) {
         this.status = status;
      }

      public String getTotal() {
         return total;
      }

      public void setTotal(final String total) {
         this.total = total;
      }

      public String getFree() {
         return free;
      }

      public void setFree(final String free) {
         this.free = free;
      }

      public String getThreshold() {
         return threshold;
      }

      public void setThreshold(final String threshold) {
         this.threshold = threshold;
      }
   }
   public class Db {
      private String status;
      private String database;
      private String hello;

      public String getStatus() {
         return status;
      }

      public void setStatus(final String status) {
         this.status = status;
      }

      public String getDatabase() {
         return database;
      }

      public void setDatabase(final String database) {
         this.database = database;
      }

      public String getHello() {
         return hello;
      }

      public void setHello(final String hello) {
         this.hello = hello;
      }
   }

}
