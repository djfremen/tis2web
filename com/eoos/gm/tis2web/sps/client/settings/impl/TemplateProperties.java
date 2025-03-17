/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Properties;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TemplateProperties
/*    */   extends Properties
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public void load(String file) throws IOException {
/*    */     try {
/* 29 */       InputStream properties = null;
/* 30 */       properties = getClass().getClassLoader().getResourceAsStream(file);
/* 31 */       load(properties);
/*    */       try {
/* 33 */         properties.close();
/* 34 */       } catch (Exception x) {}
/*    */     }
/* 36 */     catch (IOException e) {
/* 37 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\TemplateProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */