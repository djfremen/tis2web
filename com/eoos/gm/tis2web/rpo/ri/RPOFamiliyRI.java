/*    */ package com.eoos.gm.tis2web.rpo.ri;
/*    */ 
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.I15dText;
/*    */ import com.eoos.scsm.v2.util.I15dTextSupport;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class RPOFamiliyRI
/*    */   implements RPOFamily
/*    */ {
/*    */   private String identifier;
/*    */   private I15dText description;
/*    */   
/*    */   public RPOFamiliyRI(String identifier, I15dTextSupport description) {
/* 17 */     this.identifier = identifier;
/* 18 */     this.description = (I15dText)description;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale locale) {
/* 22 */     return this.description.getText(locale);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 26 */     int ret = RPOFamily.class.hashCode();
/* 27 */     ret = HashCalc.addHashCode(ret, this.description);
/* 28 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 32 */     if (this == obj)
/* 33 */       return true; 
/* 34 */     if (obj instanceof RPOFamiliyRI) {
/* 35 */       RPOFamiliyRI other = (RPOFamiliyRI)obj;
/* 36 */       boolean ret = this.description.equals(other.description);
/* 37 */       return ret;
/*    */     } 
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getID() {
/* 44 */     return this.identifier;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\ri\RPOFamiliyRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */