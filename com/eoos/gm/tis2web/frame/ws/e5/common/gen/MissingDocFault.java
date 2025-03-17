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
/*    */ @WebFault(name = "missingDoc", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5/types")
/*    */ public class MissingDocFault
/*    */   extends Exception
/*    */ {
/*    */   private MissingDoc faultInfo;
/*    */   
/*    */   public MissingDocFault(String message, MissingDoc faultInfo) {
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
/*    */   public MissingDocFault(String message, MissingDoc faultInfo, Throwable cause) {
/* 37 */     super(message, cause);
/* 38 */     this.faultInfo = faultInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MissingDoc getFaultInfo() {
/* 47 */     return this.faultInfo;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\MissingDocFault.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */