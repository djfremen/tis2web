/*    */ package com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import java.util.Comparator;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodeComparator
/*    */   implements Comparator
/*    */ {
/* 23 */   private static final Logger log = Logger.getLogger(NodeComparator.class);
/*    */   
/* 25 */   private static Map instances = new HashMap<Object, Object>();
/*    */   
/* 27 */   private LocaleInfo locale = null;
/*    */ 
/*    */   
/*    */   private NodeComparator(Locale locale) {
/* 31 */     this.locale = LocaleInfoProvider.getInstance().getLocale(locale);
/*    */   }
/*    */   
/*    */   public static synchronized NodeComparator getInstance(Locale locale) {
/* 35 */     NodeComparator instance = (NodeComparator)instances.get(locale);
/* 36 */     if (instance == null) {
/* 37 */       instance = new NodeComparator(locale);
/* 38 */       instances.put(locale, instance);
/*    */     } 
/* 40 */     return instance;
/*    */   }
/*    */   
/*    */   private boolean isSIO(SITOCElement element) {
/* 44 */     return element instanceof com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */   }
/*    */   
/*    */   private boolean isAssemblyGroup(SITOCElement element) {
/* 48 */     return (element instanceof CTOCNode && ((CTOCNode)element).hasProperty((SITOCProperty)CTOCProperty.AssemblyGroup));
/*    */   }
/*    */   
/*    */   private boolean isSIT(SITOCElement element) {
/* 52 */     return (element instanceof CTOCNode && !((CTOCNode)element).hasProperty((SITOCProperty)CTOCProperty.AssemblyGroup));
/*    */   }
/*    */   
/*    */   private int getTypeIndicator(SITOCElement e) {
/* 56 */     int retValue = 0;
/* 57 */     if (isSIT(e)) {
/* 58 */       retValue = 1;
/* 59 */     } else if (isAssemblyGroup(e)) {
/* 60 */       retValue = 2;
/* 61 */     } else if (isSIO(e)) {
/* 62 */       retValue = 3;
/*    */     } 
/* 64 */     return retValue;
/*    */   }
/*    */   
/*    */   private int compareByLabel(SITOCElement e1, SITOCElement e2) {
/* 68 */     String l1 = e1.getLabel(this.locale);
/* 69 */     String l2 = e2.getLabel(this.locale);
/* 70 */     return l1.compareTo(l2);
/*    */   }
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 74 */     int retValue = 0;
/*    */     try {
/* 76 */       SITOCElement e1 = (SITOCElement)o1;
/* 77 */       SITOCElement e2 = (SITOCElement)o2;
/*    */       
/* 79 */       int ti1 = getTypeIndicator(e1);
/* 80 */       int ti2 = getTypeIndicator(e2);
/* 81 */       retValue = ti1 - ti2;
/*    */       
/* 83 */       if (retValue == 0) {
/* 84 */         retValue = compareByLabel(e1, e2);
/*    */       }
/* 86 */     } catch (Exception e) {
/* 87 */       log.error("unable to compare objects - error:" + e, e);
/* 88 */       retValue = 0;
/*    */     } 
/* 90 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\ctoctree\datamodel\NodeComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */