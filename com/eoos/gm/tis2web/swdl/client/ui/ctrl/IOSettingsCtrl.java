/*    */ package com.eoos.gm.tis2web.swdl.client.ui.ctrl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
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
/*    */ public class IOSettingsCtrl
/*    */   extends Properties
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public void saveSettings(OutputStream stream, Hashtable settings) throws Exception {
/* 27 */     Enumeration<String> keys = settings.keys();
/* 28 */     while (keys.hasMoreElements()) {
/* 29 */       String key = keys.nextElement();
/* 30 */       setProperty(key, (String)settings.get(key));
/*    */     } 
/*    */     try {
/* 33 */       store(stream, (String)null);
/* 34 */     } catch (IOException e) {
/* 35 */       throw e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Hashtable loadSettings(InputStream stream) throws Exception {
/* 40 */     Hashtable<Object, Object> settings = new Hashtable<Object, Object>();
/*    */     try {
/* 42 */       load(stream);
/* 43 */       Enumeration<?> keys = propertyNames();
/* 44 */       while (keys.hasMoreElements()) {
/* 45 */         String key = (String)keys.nextElement();
/* 46 */         settings.put(key, getProperty(key));
/*    */       } 
/* 48 */     } catch (IOException e) {
/* 49 */       throw e;
/*    */     } 
/* 51 */     return settings;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\ctrl\IOSettingsCtrl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */