/*    */ package com.eoos.gm.tis2web.swdl.client.util;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceDataProvider
/*    */ {
/* 11 */   private static ResourceDataProvider instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ResourceDataProvider getInstance() {
/* 17 */     if (instance == null) {
/* 18 */       instance = new ResourceDataProvider();
/*    */     }
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   public byte[] getData(String name) throws Exception {
/* 24 */     InputStream is = getClass().getClassLoader().getResourceAsStream("com/eoos/gm/tis2web/swdl/client/resources/" + name);
/* 25 */     return StreamUtil.readFully(is);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\ResourceDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */