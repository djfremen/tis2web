/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sids.service.ServiceIDService;
/*    */ import com.eoos.gm.tis2web.sids.service.cai.InvalidVinException;
/*    */ import com.eoos.gm.tis2web.sids.service.cai.NoServiceIDException;
/*    */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ServiceIDServiceImpl
/*    */   implements ServiceIDService
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(ServiceIDServiceImpl.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ServiceID getServiceID(Locale locale, String vin, AttributeValueMap avMap) throws RequestException, InvalidVinException, NoServiceIDException {
/* 24 */     ServiceID newServiceID = SIDS.getServiceID(locale, vin, avMap);
/* 25 */     return newServiceID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getIdentifier() {
/* 32 */     return null;
/*    */   }
/*    */   
/*    */   public ServiceID getServiceID(String identifier) {
/* 36 */     return ServiceIDImpl.getInstance(identifier);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 40 */     log.info("resetting");
/* 41 */     CacheSIDS.reset();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\ServiceIDServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */