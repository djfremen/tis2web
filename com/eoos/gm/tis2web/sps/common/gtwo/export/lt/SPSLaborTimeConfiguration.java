/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export.lt;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SPSLaborTimeConfiguration implements Serializable {
/*    */   private static final long serialVersionUID = 8077431034912516572L;
/*    */   private String formula;
/*    */   private int maxDownloadTime;
/*    */   private int maxProgrammingTime;
/*    */   private int maxType4Time;
/*    */   private boolean display;
/*    */   
/*    */   public SPSLaborTimeConfiguration(String formula, int maxDownloadTime, int maxProgrammingTime, int maxType4Time, boolean display) {
/* 14 */     this.formula = formula.trim();
/* 15 */     this.maxDownloadTime = maxDownloadTime;
/* 16 */     this.maxProgrammingTime = maxProgrammingTime;
/* 17 */     this.maxType4Time = maxType4Time;
/*    */   }
/*    */   
/*    */   public String getFormula() {
/* 21 */     return this.formula;
/*    */   }
/*    */   
/*    */   public int getMaxDownloadTime() {
/* 25 */     return this.maxDownloadTime;
/*    */   }
/*    */   
/*    */   public int getMaxProgrammingTime() {
/* 29 */     return this.maxProgrammingTime;
/*    */   }
/*    */   
/*    */   public int getMaxType4Time() {
/* 33 */     return this.maxType4Time;
/*    */   }
/*    */   
/*    */   public boolean isDisplay() {
/* 37 */     return this.display;
/*    */   }
/*    */   
/*    */   protected boolean isInvalid(String value) {
/* 41 */     return (value == null || value.trim().length() == 0);
/*    */   }
/*    */   
/*    */   public SPSLaborTimeConfiguration(String formula, String maxDownloadTime, String maxProgrammingTime, String maxType4Time, String display) {
/* 45 */     if (isInvalid(formula) || isInvalid(maxDownloadTime) || isInvalid(maxProgrammingTime) || isInvalid(maxType4Time)) {
/*    */       return;
/*    */     }
/* 48 */     this.formula = formula.trim();
/* 49 */     this.maxDownloadTime = Integer.parseInt(maxDownloadTime.trim());
/* 50 */     this.maxProgrammingTime = Integer.parseInt(maxProgrammingTime.trim());
/* 51 */     this.maxType4Time = Integer.parseInt(maxType4Time.trim());
/* 52 */     if (display != null && display.trim().toLowerCase().equals("true"))
/* 53 */       this.display = true; 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\lt\SPSLaborTimeConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */