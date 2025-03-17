/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnsupportedVehicleException
/*    */   extends SPSException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private ServiceID serviceID;
/*    */   
/*    */   public UnsupportedVehicleException() {
/* 14 */     super("sps.exception.unsupported.vehicle");
/*    */   }
/*    */   
/*    */   public UnsupportedVehicleException(ServiceID serviceID) {
/* 18 */     super("sps.exception.unsupported.vehicle");
/* 19 */     this.serviceID = serviceID;
/*    */   }
/*    */   
/*    */   public ServiceID getServiceID() {
/* 23 */     return this.serviceID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\UnsupportedVehicleException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */