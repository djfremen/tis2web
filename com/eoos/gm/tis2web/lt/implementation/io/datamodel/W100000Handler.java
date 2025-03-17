/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W100000Handler
/*     */ {
/*  19 */   static String W100000 = new String("W100000");
/*     */ 
/*     */ 
/*     */   
/*     */   Integer aw;
/*     */ 
/*     */ 
/*     */   
/*     */   Integer lc;
/*     */ 
/*     */   
/*     */   boolean IsGermany = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isW100000Work(String onr) {
/*  35 */     return onr.equals(W100000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W100000Handler(Integer w100000AW, boolean bIsGermany) {
/*  45 */     this.IsGermany = bIsGermany;
/*  46 */     this.aw = w100000AW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getW100000AW() {
/*  59 */     return this.aw.intValue();
/*     */   }
/*     */   
/*     */   public void setGermany(boolean b) {
/*  63 */     this.IsGermany = b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGermanMinus() {
/* 103 */     return this.IsGermany;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\W100000Handler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */