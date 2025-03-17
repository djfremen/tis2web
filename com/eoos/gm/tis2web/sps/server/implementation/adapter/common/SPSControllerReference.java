/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ControllerReference;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.util.v2.Util;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public abstract class SPSControllerReference
/*    */   implements ControllerReference
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   public static final Comparator COMPARATOR_DESCRIPTION = new Comparator() {
/* 17 */       private Logger log = Logger.getLogger(getClass());
/*    */       
/*    */       public int compare(Object o1, Object o2) {
/*    */         try {
/* 21 */           return Util.compare(((SPSControllerReference)o1).getDescription(), ((SPSControllerReference)o2).getDescription());
/* 22 */         } catch (Exception e) {
/* 23 */           this.log.warn("unable to compare controller descriptions, returning 0 (equal) - exception:" + e, e);
/* 24 */           return 0;
/*    */         } 
/*    */       }
/*    */     };
/*    */   
/*    */   protected String description;
/*    */   
/*    */   protected List methods;
/*    */ 
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 35 */     return this.description;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     return this.description;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 43 */     return this.description;
/*    */   }
/*    */   
/*    */   public List getProgrammingMethods() {
/* 47 */     return this.methods;
/*    */   }
/*    */   
/*    */   public boolean isType4Application() {
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   public List getHistory() {
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   public abstract List getOptions();
/*    */   
/*    */   List getPreSelectionOptions() throws Exception {
/* 61 */     return getPreOptions();
/*    */   }
/*    */   
/*    */   protected abstract List getPreOptions() throws Exception;
/*    */   
/*    */   SPSController update(List options, SPSSchemaAdapter adapter) throws Exception {
/* 67 */     return qualify(options, adapter);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract SPSController qualify(List paramList, SPSSchemaAdapter paramSPSSchemaAdapter) throws Exception;
/*    */ 
/*    */ 
/*    */   
/*    */   SPSController qualify(AttributeValueMap data, SPSProgrammingType method, SPSSchemaAdapter adapter) throws Exception {
/* 77 */     return evaluate(data, method, adapter);
/*    */   }
/*    */   
/*    */   protected abstract SPSController evaluate(AttributeValueMap paramAttributeValueMap, SPSProgrammingType paramSPSProgrammingType, SPSSchemaAdapter paramSPSSchemaAdapter) throws Exception;
/*    */   
/*    */   public abstract void reset();
/*    */   
/*    */   public int hashCode() {
/* 85 */     return this.description.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 89 */     return (object != null && object instanceof SPSControllerReference && ((SPSControllerReference)object).getDescription().equals(this.description));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSControllerReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */