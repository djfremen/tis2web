/*    */ package com.eoos.gm.tis2web.frame.ws.lt.common.generated;
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
/*    */ @WebFault(name = "fatalError", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt")
/*    */ public class FatalFault
/*    */   extends Exception
/*    */ {
/*    */   private FatalError faultInfo;
/*    */   
/*    */   public FatalFault(String message, FatalError faultInfo) {
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
/*    */   public FatalFault(String message, FatalError faultInfo, Throwable cause) {
/* 37 */     super(message, cause);
/* 38 */     this.faultInfo = faultInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FatalError getFaultInfo() {
/* 47 */     return this.faultInfo;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\FatalFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */