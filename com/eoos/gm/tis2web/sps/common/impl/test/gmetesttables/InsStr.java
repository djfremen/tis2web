/*    */ package com.eoos.gm.tis2web.sps.common.impl.test.gmetesttables;
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
/*    */ public class InsStr
/*    */ {
/* 14 */   private StringBuffer cont = new StringBuffer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void insSel(String what, String table, String where, String val) {
/* 21 */     if (this.cont.length() != 0)
/* 22 */       this.cont.append(", "); 
/* 23 */     this.cont.append("select min (" + what + ") from " + table + " where " + where + " = '" + val + "'");
/*    */   }
/*    */ 
/*    */   
/*    */   public void insSel(String what) {
/* 28 */     if (this.cont.length() != 0)
/* 29 */       this.cont.append(", "); 
/* 30 */     this.cont.append(what);
/*    */   }
/*    */ 
/*    */   
/*    */   public String get() {
/* 35 */     return this.cont.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\test\gmetesttables\InsStr.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */