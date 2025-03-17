/*    */ package com.eoos.gm.tis2web.rpo.ri;
/*    */ 
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class RPOCodeOnly
/*    */   extends AbstractRPO
/*    */ {
/*    */   private String code;
/*    */   
/*    */   public RPOCodeOnly(String code) {
/* 12 */     this.code = code;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 16 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale locale) {
/* 20 */     return null;
/*    */   }
/*    */   
/*    */   public RPOFamily getFamily() {
/* 24 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\ri\RPOCodeOnly.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */