/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.sids.service.cai.InvalidVinException;
/*    */ import com.eoos.gm.tis2web.sids.service.cai.NoServiceIDException;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.sql.ResultSet;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VinServiceIDs
/*    */ {
/* 23 */   private static Logger log = Logger.getLogger(VinServiceIDs.class);
/* 24 */   private VinServiceIDsAttributes serviceIDAttributes = null;
/* 25 */   private String serviceID = null;
/*    */   
/*    */   private VinServiceIDs(final String vin, final Locale locale) throws InvalidVinException, NoServiceIDException {
/* 28 */     log.info("VinServiceIDs.VinServiceIDs: vin = " + vin);
/*    */     
/* 30 */     if (vin.length() < 17) {
/* 31 */       throw new InvalidVinException();
/*    */     }
/* 33 */     final ParamWhereClause params = new ParamWhereClause();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 43 */       final DatabaseLink db = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "component.sps.sids.service.db");
/* 44 */       params.addLikeOrNullCondition((IDatabaseLink)databaseLink, "WMI", vin.substring(0, 3));
/* 45 */       params.addLikeOrNullCondition((IDatabaseLink)databaseLink, "VDS", vin.substring(3, 9));
/* 46 */       params.addLikeOrNullCondition((IDatabaseLink)databaseLink, "ModelYear", vin.substring(9, 10));
/* 47 */       params.addLikeOrNullCondition((IDatabaseLink)databaseLink, "VIS", vin.substring(10, 17));
/* 48 */       ServiceIDDBReader dbr = new ServiceIDDBReader((IDatabaseLink)databaseLink, "select min(ServiceCode) as ServiceCode,count (distinct ServiceCode) as serviceCodeCount from SC_BaseVehicle a", params, "") {
/*    */           public void readRow(ResultSet rs) throws Exception {
/* 50 */             int count = rs.getInt("serviceCodeCount".toUpperCase(Locale.ENGLISH));
/* 51 */             if (count > 1) {
/* 52 */               VinServiceIDs.this.serviceIDAttributes = new VinServiceIDsAttributes(vin, params, db, locale);
/*    */             }
/* 54 */             VinServiceIDs.this.serviceID = rs.getString("ServiceCode".toUpperCase(Locale.ENGLISH)).trim();
/*    */           }
/*    */         };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 62 */       dbr.read();
/* 63 */     } catch (Exception e) {
/* 64 */       log.fatal(e, e);
/* 65 */       throw new RuntimeException();
/*    */     } 
/* 67 */     if (this.serviceID == null) {
/* 68 */       throw new NoServiceIDException();
/*    */     }
/*    */   }
/*    */   
/*    */   public static VinServiceIDs getInstance(String vin, Locale locale) throws InvalidVinException, NoServiceIDException {
/* 73 */     return new VinServiceIDs(vin, locale);
/*    */   }
/*    */   
/*    */   public String getServiceID(AttributeValueMap attrToVal) throws RequestException, NoServiceIDException {
/*    */     String ret;
/* 78 */     if (this.serviceIDAttributes == null) {
/* 79 */       ret = this.serviceID;
/*    */     } else {
/* 81 */       ret = this.serviceIDAttributes.getServiceID(attrToVal);
/* 82 */       if (ret == null && this.serviceID != null) {
/* 83 */         log.error("For vin " + this.serviceIDAttributes.getVin() + " exists no unique Servicecode, because the attributes ar not matching  : " + this.serviceID + " was used.");
/* 84 */         ret = this.serviceID;
/*    */       } 
/*    */     } 
/*    */     
/* 88 */     if (ret == null) {
/* 89 */       throw new NoServiceIDException();
/*    */     }
/* 91 */     if (!ret.startsWith("SID_")) {
/* 92 */       ret = "SID_" + ret;
/*    */     }
/* 94 */     log.info("VinServiceIDs.getInstance ServiceID = " + ret);
/* 95 */     return ret;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\VinServiceIDs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */