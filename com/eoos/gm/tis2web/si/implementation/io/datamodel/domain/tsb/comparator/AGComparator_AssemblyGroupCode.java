/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.comparator;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.AssemblyGroup;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ public class AGComparator_AssemblyGroupCode
/*    */   implements Comparator
/*    */ {
/*    */   public static final int DIRECTION_ASCENDING = 1;
/*    */   public static final int DIRECTION_DESCENDING = -1;
/*    */   protected int direction;
/* 16 */   public static final Comparator COMPARATOR_ASC = new AGComparator_AssemblyGroupCode(1);
/*    */   
/* 18 */   public static final Comparator COMPARATOR_DESC = new AGComparator_AssemblyGroupCode(-1);
/*    */   
/*    */   protected AGComparator_AssemblyGroupCode(int direction) {
/* 21 */     this.direction = direction;
/*    */   }
/*    */   
/*    */   protected int compare(AssemblyGroup ag, AssemblyGroup ag2) {
/*    */     try {
/* 26 */       CTOCNode node1 = ag.getNode();
/* 27 */       CTOCNode node2 = ag2.getNode();
/* 28 */       return ((String)node1.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup)).compareTo((String)node2.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup));
/*    */     }
/* 30 */     catch (Exception e) {
/* 31 */       return 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int compare(Object o1, Object o2) {
/*    */     try {
/* 37 */       AssemblyGroup ag = (AssemblyGroup)o1;
/* 38 */       AssemblyGroup ag2 = (AssemblyGroup)o2;
/*    */       
/* 40 */       return compare(ag, ag2) * this.direction;
/* 41 */     } catch (Exception e) {
/* 42 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\comparator\AGComparator_AssemblyGroupCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */