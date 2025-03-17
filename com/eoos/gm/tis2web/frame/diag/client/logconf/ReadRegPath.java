/*    */ package com.eoos.gm.tis2web.frame.diag.client.logconf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.StringWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReadRegPath
/*    */ {
/*    */   public static String getKeyForRegistryPath(String regPath, String sKey, String sKeyTyp) {
/* 18 */     if (sKey == null || sKey.equals("") || sKeyTyp == null || sKeyTyp.equals("")) {
/* 19 */       return null;
/*    */     }
/*    */     
/* 22 */     String _KEY = "reg query \"" + regPath + "\"" + " /v " + sKey;
/* 23 */     return startRegForKey(_KEY, sKeyTyp);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String startRegForKey(String key, String keyType) {
/*    */     try {
/* 29 */       Process process = Runtime.getRuntime().exec(key);
/* 30 */       StreamReader reader = new StreamReader(process.getInputStream());
/*    */       
/* 32 */       reader.start();
/* 33 */       process.waitFor();
/* 34 */       reader.join();
/*    */       
/* 36 */       String result = reader.getResult();
/*    */       
/* 38 */       int p = result.indexOf(keyType);
/*    */       
/* 40 */       if (p == -1) {
/* 41 */         return null;
/*    */       }
/*    */       
/* 44 */       return result.substring(p + keyType.length()).trim();
/* 45 */     } catch (Exception e) {
/* 46 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   private static interface IRegKey {
/*    */     public static final String REGQUERY_UTIL = "reg query "; }
/*    */   
/*    */   static class StreamReader extends Thread {
/*    */     private InputStream is;
/*    */     
/*    */     StreamReader(InputStream is) {
/* 57 */       this.is = is;
/* 58 */       this.sw = new StringWriter();
/*    */     }
/*    */     private StringWriter sw;
/*    */     public void run() {
/*    */       try {
/*    */         int c;
/* 64 */         while ((c = this.is.read()) != -1)
/* 65 */           this.sw.write(c); 
/* 66 */       } catch (IOException e) {}
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     String getResult() {
/* 72 */       return this.sw.toString();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\logconf\ReadRegPath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */