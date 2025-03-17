/*    */ package com.eoos.gm.tis2web.swdl.client.ui.ctrl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AppProperties
/*    */   extends Properties
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public void load(String file) throws IOException {
/*    */     try {
/* 35 */       InputStream properties = null;
/* 36 */       properties = getClass().getClassLoader().getResourceAsStream(file);
/* 37 */       load(properties);
/* 38 */     } catch (IOException e) {
/* 39 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\ctrl\AppProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */