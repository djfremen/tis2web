/*    */ package com.eoos.gm.tis2web.profile.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultSalesmakeSelection
/*    */   extends SelectBoxSelectionElement
/*    */ {
/*    */   private ClientContext context;
/* 26 */   private static final Object NOT_SET = new Object();
/*    */ 
/*    */   
/*    */   public DefaultSalesmakeSelection(ClientContext context) {
/* 30 */     super(context.createID(), true, null, 1);
/* 31 */     this.context = context;
/*    */     
/* 33 */     List<?> salesmakes = new LinkedList(VCFacade.getInstance(context).getSalesmakeDomain());
/* 34 */     Collections.sort(salesmakes, Util.COMPARATOR_TOSTRING);
/*    */     
/* 36 */     String currentValue = SharedContext.getInstance(context).getDefaultSalesmake();
/* 37 */     if (currentValue == null) {
/* 38 */       salesmakes.add(0, NOT_SET);
/* 39 */       setOptions(salesmakes);
/* 40 */       setValue(NOT_SET);
/*    */     } else {
/* 42 */       setOptions(salesmakes);
/* 43 */       setValue(VCFacade.toMake(currentValue));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean autoSubmitOnChange() {
/* 50 */     return true;
/*    */   }
/*    */   
/*    */   protected Object onChange(Map submitParams) {
/* 54 */     HtmlElementContainer container = getContainer();
/* 55 */     while (container.getContainer() != null) {
/* 56 */       container = container.getContainer();
/*    */     }
/* 58 */     return container;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 62 */     if (option == NOT_SET) {
/* 63 */       return this.context.getLabel("unspecified");
/*    */     }
/* 65 */     return VCFacade.getInstance(this.context).getDisplayValue(option);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 70 */     Object ret = super.getValue();
/* 71 */     if (ret == NOT_SET) {
/* 72 */       ret = null;
/*    */     }
/* 74 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\implementatio\\ui\html\home\DefaultSalesmakeSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */