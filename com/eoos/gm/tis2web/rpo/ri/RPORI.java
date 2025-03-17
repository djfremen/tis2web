/*    */ package com.eoos.gm.tis2web.rpo.ri;
/*    */ 
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*    */ import com.eoos.scsm.v2.util.I15dTextSupport;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RPORI
/*    */   extends AbstractRPO
/*    */ {
/*    */   private String code;
/*    */   private I15dTextSupport description;
/*    */   private RPOFamily family;
/*    */   
/*    */   public RPORI(String code, I15dTextSupport description, RPOFamily family) {
/* 17 */     this.code = code;
/* 18 */     this.description = description;
/* 19 */     this.family = family;
/*    */   }
/*    */   
/*    */   public String getCode() {
/* 23 */     return this.code;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale locale) {
/* 27 */     return this.description.getText(locale);
/*    */   }
/*    */   
/*    */   public RPOFamily getFamily() {
/* 31 */     return this.family;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\ri\RPORI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */