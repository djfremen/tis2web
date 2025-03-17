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
/*    */ @WebFault(name = "InvalidUsername", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt")
/*    */ public class SecurityFault
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private InvalidUsername faultInfo;
/*    */   
/*    */   public SecurityFault(String message, InvalidUsername faultInfo) {
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
/*    */   public SecurityFault(String message, InvalidUsername faultInfo, Throwable cause) {
/* 42 */     super(message, cause);
/* 43 */     this.faultInfo = faultInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidUsername getFaultInfo() {
/* 52 */     return this.faultInfo;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\SecurityFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */