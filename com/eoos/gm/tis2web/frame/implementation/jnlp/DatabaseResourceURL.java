/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
/*    */ 
/*    */ public class DatabaseResourceURL extends URLConnection {
/* 12 */   protected static URLStreamHandler handler = new DatabaseURLStreamHandler();
/*    */   public static final String DBPROTOCOL = "db";
/*    */   protected String name;
/*    */   protected String version;
/*    */   protected boolean pack200;
/*    */   
/*    */   public static URL makeDatabaseResourceURL(JnlpResource resource, boolean pack200) {
/*    */     try {
/* 20 */       String extension = pack200 ? ".pack.gz" : "";
/* 21 */       return new URL("db", "localhost", pack200 ? 1 : 0, "/" + resource.getVersionId() + "/" + resource.getName() + extension, handler);
/* 22 */     } catch (MalformedURLException e) {
/*    */       
/* 24 */       return null;
/*    */     } 
/*    */   }
/*    */   public static URL makeDatabaseResourceURL(JnlpDatabaseResource resource, boolean pack200) {
/*    */     try {
/* 29 */       String extension = (pack200 && resource.supportsPack200()) ? ".pack.gz" : "";
/* 30 */       return new URL("db", "localhost", pack200 ? 1 : 0, "/" + resource.getVersion() + "/" + resource.getName() + extension, handler);
/* 31 */     } catch (MalformedURLException e) {
/*    */       
/* 33 */       return null;
/*    */     } 
/*    */   }
/*    */   public static URL makeDatabaseResourceURL(String path, boolean pack200) {
/*    */     try {
/* 38 */       path = path.substring(path.lastIndexOf(':') + 2);
/* 39 */       return new URL("db", "localhost", pack200 ? 1 : 0, path, handler);
/* 40 */     } catch (MalformedURLException e) {
/*    */       
/* 42 */       return null;
/*    */     } 
/*    */   }
/*    */   public DatabaseResourceURL(JnlpResource resource) throws MalformedURLException {
/* 46 */     super(resource.getResource());
/*    */   }
/*    */   
/*    */   public DatabaseResourceURL(URL url) throws MalformedURLException {
/* 50 */     super(url);
/* 51 */     parseURL(url);
/*    */   }
/*    */   
/*    */   protected void parseURL(URL url) {
/* 55 */     String path = url.getPath();
/* 56 */     int idx = path.lastIndexOf('/');
/* 57 */     if (idx == -1) {
/* 58 */       throw new IllegalArgumentException();
/*    */     }
/* 60 */     this.name = path.substring(idx + 1);
/* 61 */     this.version = path.substring(1, idx);
/* 62 */     this.pack200 = (url.getPort() > 0 || path.endsWith(".pack.gz"));
/*    */   }
/*    */   
/*    */   protected JnlpDatabaseResource getResource() {
/* 66 */     return ResourceCatalog.getInstance().lookupResource(this.name, this.version, this.pack200);
/*    */   }
/*    */ 
/*    */   
/*    */   public void connect() throws IOException {}
/*    */   
/*    */   public int getContentLength() {
/* 73 */     return (getResource().getResource()).length;
/*    */   }
/*    */   
/*    */   public InputStream getInputStream() throws IOException {
/* 77 */     return new ByteArrayInputStream(getResource().getResource());
/*    */   }
/*    */   
/*    */   public long getLastModified() {
/* 81 */     return getResource().getTimeStamp();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\DatabaseResourceURL.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */