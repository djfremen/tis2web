/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.propcfg.TypeDecorator;
/*    */ import java.util.Comparator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class VisualModuleComparator
/*    */   implements Comparator {
/* 13 */   private static final Logger log = Logger.getLogger(VisualModuleComparator.class);
/*    */   
/* 15 */   private static VisualModuleComparator instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized VisualModuleComparator getInstance() {
/* 22 */     if (instance == null) {
/* 23 */       instance = new VisualModuleComparator();
/*    */     }
/* 25 */     return instance;
/*    */   }
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 29 */     int retValue = 0;
/*    */     try {
/* 31 */       VisualModule m1 = (VisualModule)o1;
/* 32 */       VisualModule m2 = (VisualModule)o2;
/* 33 */       int order1 = 100;
/* 34 */       int order2 = 100;
/* 35 */       TypeDecorator t = new TypeDecorator((Configuration)new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.application.visual.module.order."));
/*    */       try {
/* 37 */         order1 = t.getNumber(m1.getType()).intValue();
/* 38 */       } catch (Exception e) {
/* 39 */         log.error("unable to determine correct order for visual module:" + m1.getType());
/*    */       } 
/*    */       try {
/* 42 */         order2 = t.getNumber(m2.getType()).intValue();
/* 43 */       } catch (Exception e) {
/* 44 */         log.error("unable to determine correct order for visual module:" + m2.getType());
/*    */       } 
/* 46 */       retValue = order1 - order2;
/* 47 */     } catch (Exception e) {
/* 48 */       log.warn("failed to compare - exception:" + e + " - ignoring");
/*    */     } 
/* 50 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\VisualModuleComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */