/*    */ package com.eoos.gm.tis2web.sps.client.tool.spstool.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.dtc.cas.api.DTCUploadFactory;
/*    */ import com.eoos.gm.tis2web.dtc.cas.api.IDTCUpload;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ public class DTCUploadProvider
/*    */ {
/* 11 */   private static IDTCUpload dtcUpload = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized IDTCUpload getDTCUpload() {
/* 17 */     if (dtcUpload == null) {
/* 18 */       String _directory = ClientAppContextProvider.getClientAppContext().getHomeDir();
/* 19 */       File directory = new File(_directory, "dtc");
/* 20 */       directory.mkdirs();
/* 21 */       dtcUpload = DTCUploadFactory.createInstance("SPS", directory, (IDTCUpload.Callback)null);
/*    */     } 
/* 23 */     return dtcUpload;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\spstool\impl\DTCUploadProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */