/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TroubleCode;
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
/*    */ 
/*    */ public class TroubleCodeSelectionElement
/*    */   extends SelectBoxSelectionElement
/*    */   implements DataRetrievalAbstraction.DataCallback
/*    */ {
/* 22 */   public static final Object ANY = new Object();
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   public TroubleCodeSelectionElement(ClientContext context) {
/* 27 */     super(context.createID(), true, null, 1, null);
/* 28 */     this.context = context;
/* 29 */     setDataCallback(this);
/* 30 */     setValue(ANY);
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 34 */     if (value == null) {
/* 35 */       value = ANY;
/*    */     }
/* 37 */     super.setValue(value);
/*    */   }
/*    */   
/*    */   public List getData() {
/* 41 */     List<Object> retValue = new LinkedList(TroubleCode.getDomain(this.context));
/* 42 */     retValue.add(0, ANY);
/* 43 */     return retValue;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 47 */     if (option == ANY) {
/* 48 */       return this.context.getLabel("any");
/*    */     }
/* 50 */     return ((TroubleCode)option).getIdentifier();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\input\TroubleCodeSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */