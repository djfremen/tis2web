/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.Classifier;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ 
/*    */ public class ClassifierRI
/*    */   implements Classifier
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String string;
/*    */   
/*    */   public ClassifierRI(String string) {
/* 14 */     this.string = Util.toUpperCase(string);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 18 */     return this.string;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 22 */     if (this == obj)
/* 23 */       return true; 
/* 24 */     if (obj instanceof ClassifierRI) {
/* 25 */       return ((ClassifierRI)obj).string.equals(this.string);
/*    */     }
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 32 */     return HashCalc.addHashCode(ClassifierRI.class.hashCode(), this.string);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\ClassifierRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */