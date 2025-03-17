/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.service.SIService;
/*    */ 
/*    */ public class SPSDocumentService
/*    */ {
/*    */   public static String getURL(ClientContext context, String uri) {
/* 10 */     if (uri != null) {
/* 11 */       int index = uri.lastIndexOf("/");
/* 12 */       uri = (index != -1) ? uri.substring(index + 1) : uri;
/*    */     } 
/* 14 */     SIService siService = (SIService)ConfiguredServiceProvider.getInstance().getService(SIService.class);
/* 15 */     if (siService != null) {
/* 16 */       return siService.getSearchURL(context, uri).toString();
/*    */     }
/* 18 */     return null;
/*    */   }
/*    */   
/*    */   public static final class SPSDocumentReference
/*    */   {
/*    */     private int programmingInstructionID;
/*    */     private String href;
/*    */     
/*    */     public int getProgrammingInstructionID() {
/* 27 */       return this.programmingInstructionID;
/*    */     }
/*    */     
/*    */     public String getHREF() {
/* 31 */       return this.href;
/*    */     }
/*    */     
/*    */     public SPSDocumentReference(int programmingInstructionID, String href) {
/* 35 */       this.programmingInstructionID = programmingInstructionID;
/* 36 */       this.href = href;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSDocumentService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */