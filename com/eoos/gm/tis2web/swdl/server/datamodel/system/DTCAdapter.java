/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.dtc.service.cai.DTC;
/*    */ import com.eoos.gm.tis2web.swdl.common.domain.dtc.TroubleCode;
/*    */ import com.eoos.util.ZipUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DTCAdapter
/*    */   implements DTC
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private TroubleCode tc;
/*    */   private String portalID;
/*    */   
/*    */   public DTCAdapter(TroubleCode tc, String portalID) {
/* 18 */     this.tc = tc;
/* 19 */     this.portalID = portalID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getContent() {
/*    */     try {
/* 29 */       return ZipUtil.gunzip(this.tc.getContent());
/* 30 */     } catch (Exception e) {
/* 31 */       return this.tc.getContent();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getBACCode() {
/* 36 */     return this.tc.getBACCode();
/*    */   }
/*    */   
/*    */   public Long getDate() {
/* 40 */     return this.tc.getDate();
/*    */   }
/*    */   
/*    */   public String getCountryCode() {
/* 44 */     return this.tc.getCountryCode();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     return "DTCAdapter[bac: " + this.tc.getBACCode() + "]";
/*    */   }
/*    */   
/*    */   public String getApplicationID() {
/* 52 */     return "SWDL";
/*    */   }
/*    */   
/*    */   public String getPortalID() {
/* 56 */     return this.portalID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\DTCAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */