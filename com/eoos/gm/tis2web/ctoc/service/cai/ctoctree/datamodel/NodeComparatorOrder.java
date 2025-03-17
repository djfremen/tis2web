/*    */ package com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import java.util.Comparator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NodeComparatorOrder
/*    */   implements Comparator
/*    */ {
/* 14 */   private static final Logger log = Logger.getLogger(NodeComparatorOrder.class);
/*    */   
/* 16 */   private static NodeComparatorOrder instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized NodeComparatorOrder getInstance() {
/* 22 */     if (instance == null) {
/* 23 */       instance = new NodeComparatorOrder();
/*    */     }
/* 25 */     return instance;
/*    */   }
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/* 29 */     int retValue = 0;
/*    */     try {
/* 31 */       SITOCElement e1 = (SITOCElement)o1;
/* 32 */       SITOCElement e2 = (SITOCElement)o2;
/*    */       
/* 34 */       retValue = e1.getOrder() - e2.getOrder();
/* 35 */     } catch (Exception e) {
/* 36 */       log.error("unable to compare objects - error:" + e, e);
/* 37 */       retValue = 0;
/*    */     } 
/* 39 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\ctoctree\datamodel\NodeComparatorOrder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */