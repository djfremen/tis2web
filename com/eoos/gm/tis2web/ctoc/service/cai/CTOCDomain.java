/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CTOCDomain
/*    */ {
/*  8 */   CPR("SI-CTOCImpl", 2), SI("SI-CTOCImpl", 1), LT("LT-CTOCImpl", 3), SCDS("SCDS-CTOCImpl", 5), SIT("SIT/SITQ-CTOCImpl", 7), TSB("TSB-CTOCImpl", 17), SPECIAL_BROCHURE("SB-CTOCImpl", 9), HELP("HELP-CTOCImpl", 13), NEWS("NEWS-CTOCImpl", 15), SCDS2GT("SCDS2GT-GROUPS", 19), PRUNED_ASSEMBLY_GROUPS("PAG-CTOCImpl", 11), COMPLAINT("COMPLAINT-CTOCImpl", 21), NTF("NTF-CTOCImpl", 23), WIS_ECM("WIS-ECM", 24), WIS_DTC("WIS-DTC", 25), WIS_COMPONENTS("WIS-COMPONENTS", 26), WIS_SCT("WIS-SCT", 27);
/*    */   
/*    */   private int ord;
/*    */   private String label;
/*    */   
/*    */   CTOCDomain(String label, int ord) {
/* 14 */     this.label = label;
/* 15 */     this.ord = ord;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 19 */     return this.label;
/*    */   }
/*    */   
/*    */   public int ord() {
/* 23 */     return this.ord;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCDomain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */