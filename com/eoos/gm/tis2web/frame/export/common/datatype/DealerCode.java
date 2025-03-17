/*    */ package com.eoos.gm.tis2web.frame.export.common.datatype;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DealerCode
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 19 */   public static final DealerCode NULL = new DealerCode();
/*    */ 
/*    */   
/*    */   private HashMap dealerCodes;
/*    */ 
/*    */   
/*    */   public void setDealerCodes(HashMap codes) {
/* 26 */     this.dealerCodes = codes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDealerCode() {
/* 31 */     String result = null;
/* 32 */     if (this.dealerCodes != null) {
/* 33 */       Iterator<String> it = this.dealerCodes.keySet().iterator();
/* 34 */       while (it.hasNext()) {
/* 35 */         String grp = it.next();
/* 36 */         if (grp != null) {
/* 37 */           result = (String)this.dealerCodes.get(grp);
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 42 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\datatype\DealerCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */