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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebFault(name = "InvalidReqId", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt")
/*    */ public class IdFault
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private InvalidReqId faultInfo;
/*    */   
/*    */   public IdFault(String message, InvalidReqId faultInfo) {
/* 31 */     super(message);
/* 32 */     this.faultInfo = faultInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IdFault(String message, InvalidReqId faultInfo, Throwable cause) {
/* 42 */     super(message, cause);
/* 43 */     this.faultInfo = faultInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidReqId getFaultInfo() {
/* 52 */     return this.faultInfo;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\IdFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */