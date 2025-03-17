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
/*    */ @WebFault(name = "invToken", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types")
/*    */ public class SecurityFault
/*    */   extends Exception
/*    */ {
/*    */   private InvToken faultInfo;
/*    */   
/*    */   public SecurityFault(String message, InvToken faultInfo) {
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
/*    */   public SecurityFault(String message, InvToken faultInfo, Throwable cause) {
/* 37 */     super(message, cause);
/* 38 */     this.faultInfo = faultInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvToken getFaultInfo() {
/* 47 */     return this.faultInfo;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\SecurityFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */