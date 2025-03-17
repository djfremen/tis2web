/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DwnldFilter;
/*    */ 
/*    */ public class ClassificationFilter
/*    */   implements DwnldFilter
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*  9 */   public static final ClassificationFilter KDR_CORE = new ClassificationFilter("KCS");
/*    */   
/* 11 */   public static final ClassificationFilter KDR_DATA_DELIVERABLE = new ClassificationFilter("KDD");
/*    */   
/* 13 */   public static final ClassificationFilter KDR_DATA_DELIVERABLE_DESCRIPTOR = new ClassificationFilter("DDDescriptor");
/*    */   
/* 15 */   public static final ClassificationFilter KDR_SMART_START = new ClassificationFilter("KSS");
/*    */   
/* 17 */   public static final ClassificationFilter J2534_DEVICE_DRIVER = new ClassificationFilter("J2534Driver");
/*    */   
/* 19 */   public static final ClassificationFilter MDI = new ClassificationFilter("mdi");
/*    */   
/* 21 */   public static final ClassificationFilter JRE = new ClassificationFilter("jre");
/*    */   
/* 23 */   public static final ClassificationFilter SMARTSTART_INSTALLER = new ClassificationFilter("smartstartinstaller");
/*    */   
/* 25 */   public static final ClassificationFilter SMARTSTART_JRE = new ClassificationFilter("smartstartjre");
/*    */   
/* 27 */   public static final ClassificationFilter GDS = new ClassificationFilter("GDS");
/*    */   
/* 29 */   public static final ClassificationFilter SPEED_TEST = new ClassificationFilter("SPEEDTEST");
/*    */   
/*    */   private String classificationPattern;
/*    */   
/*    */   private ClassificationFilter(String pattern) {
/* 34 */     this.classificationPattern = pattern;
/*    */   }
/*    */   
/*    */   public static ClassificationFilter create(String pattern) {
/* 38 */     pattern = pattern.replace('*', '%');
/* 39 */     pattern = pattern.replace('?', '_');
/* 40 */     return new ClassificationFilter(pattern);
/*    */   }
/*    */   
/*    */   public String getSQLPattern() {
/* 44 */     return this.classificationPattern;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     return "ClassificationFilter[" + getSQLPattern() + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\ClassificationFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */