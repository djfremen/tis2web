/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.WIS;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.AssemblyGroup;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssemblyGroupSelectionElement
/*    */   extends SelectBoxSelectionElement
/*    */   implements DataRetrievalAbstraction.DataCallback
/*    */ {
/* 28 */   public static final Object ANY = new Object();
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   public AssemblyGroupSelectionElement(ClientContext context) {
/* 33 */     super(context.createID(), true, null, 1, null);
/* 34 */     this.context = context;
/*    */     
/* 36 */     setDataCallback(this);
/* 37 */     setValue(ANY);
/*    */   }
/*    */   
/*    */   public List getData() {
/* 41 */     List<Object> retValue = null;
/*    */     
/* 43 */     if (WIS.hasSaabData(this.context)) {
/* 44 */       retValue = new LinkedList(AssemblyGroup.getSaabDomain(this.context));
/*    */     } else {
/* 46 */       retValue = new LinkedList(AssemblyGroup.getDomain(this.context));
/*    */     } 
/* 48 */     retValue.add(0, ANY);
/* 49 */     return retValue;
/*    */   }
/*    */   
/*    */   protected boolean isSaabData(ClientContext context) {
/* 53 */     CTOCNode root = TocTree.getInstance(context).getRootNode();
/* 54 */     if (root != null) {
/* 55 */       return (root.hasProperty((SITOCProperty)CTOCProperty.DTC_LIST) || root.hasProperty((SITOCProperty)CTOCProperty.ECM_LIST));
/*    */     }
/* 57 */     String make = VCFacade.getInstance(context).getCurrentSalesmake();
/* 58 */     String sfms = ApplicationContext.getInstance().getProperty("component.si.tsb.SmallFilterMakes");
/* 59 */     return (sfms != null && sfms.indexOf(make) > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 64 */     if (option == ANY) {
/* 65 */       return this.context.getLabel("any");
/*    */     }
/* 67 */     return ((AssemblyGroup)option).getIdentifier(this.context.getLocale());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 72 */     if (value == null) {
/* 73 */       value = ANY;
/*    */     }
/* 75 */     super.setValue(value);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\input\AssemblyGroupSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */