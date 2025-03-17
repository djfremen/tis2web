/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.NCComparator;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.SITOCElementContainer;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.TocParser;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ECMCallback
/*    */   extends TocParser
/*    */   implements DataRetrievalAbstraction.DataCallback
/*    */ {
/* 23 */   protected LinkedList data = new LinkedList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ECMCallback(CTOCNode root, SITOCProperty first) {
/* 32 */     this.data = new LinkedList();
/* 33 */     parse((SITOCElement)root, first);
/* 34 */     Collections.sort(this.data, (Comparator<?>)new NCComparator());
/*    */   }
/*    */   
/*    */   protected void addElement(SITOCElement x) {
/* 38 */     List list2 = x.getChildren();
/* 39 */     if (list2 != null && list2.size() > 0) {
/* 40 */       this.data.add(new SITOCElementContainer(x, (SITOCProperty)CTOCProperty.ECM_LABEL));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List getData() {
/* 46 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\ecm\ECMCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */