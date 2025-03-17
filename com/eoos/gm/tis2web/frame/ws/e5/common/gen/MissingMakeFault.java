/*    */ package com.eoos.gm.tis2web.frame.ws.e5.common.gen;
/*    */ 
/*    */ import javax.xml.ws.WebFault;
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
/*    */ @WebFault(name = "makeOpts", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types")
/*    */ public class MissingMakeFault
/*    */   extends Exception
/*    */ {
/*    */   private MakeOpts faultInfo;
/*    */   
/*    */   public MissingMakeFault(String message, MakeOpts faultInfo) {
/* 26 */     super(message);
/* 27 */     this.faultInfo = faultInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MissingMakeFault(String message, MakeOpts faultInfo, Throwable cause) {
/* 37 */     super(message, cause);
/* 38 */     this.faultInfo = faultInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MakeOpts getFaultInfo() {
/* 47 */     return this.faultInfo;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\MissingMakeFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */