/*    */ package com.eoos.gm.tis2web.frame.export.common.datatype;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public class CachingStrategy
/*    */ {
/*    */   public static final String CTOC_NO_CACHE = "CTOCImpl roots";
/*    */   public static final String CTOC_COMPLETE_CACHE = "CTOCImpl complete";
/*    */   public static final String CTOC_STRUCTURE_CACHE = "CTOCImpl toc+content";
/*    */   public static final String CTOC_ONLY_STRUCTURE_CACHE = "CTOCImpl toc";
/*    */   public static final String CTOC_PROPERTY_CACHE = "CTOCImpl properties";
/*    */   public static final String CTOC_LABEL_CACHE = "CTOCImpl labels";
/*    */   public static final String SI_NO_CACHE = "SI on-demand";
/*    */   public static final String SI_SUBJECT_CACHE = "SI pre-load subjects";
/*    */   public static final String SI_ONLY_PROPERTY_CACHE = "SI pre-load properties w/o links";
/*    */   public static final String SI_PROPERTY_CACHE = "SI pre-load properties";
/*    */   public static final String SI_COMPLETE_CACHE = "SI complete (w/o blobs)";
/*    */   public static final String SCDS_ONLY = "SCDS";
/* 31 */   Set strategies = new HashSet();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(String strategy) {
/* 37 */     this.strategies.add(strategy);
/*    */   }
/*    */   
/*    */   public boolean contains(String strategy) {
/* 41 */     return this.strategies.contains(strategy);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\datatype\CachingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */