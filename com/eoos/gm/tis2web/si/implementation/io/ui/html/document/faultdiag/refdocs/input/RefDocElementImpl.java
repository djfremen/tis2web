/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefDocElementImpl
/*    */   implements RefDocElement
/*    */ {
/*    */   private SIO sio;
/*    */   private String vehicleSystem;
/*    */   private String informationType;
/*    */   private String document;
/*    */   
/*    */   public RefDocElementImpl(SIO sio, String vehicleSystem, String informationType, String document) {
/* 22 */     this.vehicleSystem = (vehicleSystem != null) ? vehicleSystem : "";
/* 23 */     this.informationType = (informationType != null) ? informationType : "";
/* 24 */     this.document = (document != null) ? document : "";
/* 25 */     this.sio = sio;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SIO getSIO() {
/* 32 */     return this.sio;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getVehicleSystem() {
/* 39 */     return this.vehicleSystem;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getInformationType() {
/* 46 */     return this.informationType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDocument() {
/* 53 */     return this.document;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\refdocs\input\RefDocElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */