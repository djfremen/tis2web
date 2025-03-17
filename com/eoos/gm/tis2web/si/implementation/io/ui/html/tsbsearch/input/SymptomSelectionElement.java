/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.Symptom;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
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
/*    */ public class SymptomSelectionElement
/*    */   extends SelectBoxSelectionElement
/*    */   implements DataRetrievalAbstraction.DataCallback
/*    */ {
/* 24 */   public static final Object ANY = new Object();
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   public SymptomSelectionElement(ClientContext context) {
/* 29 */     super(context.createID(), true, null, 1, null);
/* 30 */     this.context = context;
/* 31 */     setDataCallback(this);
/* 32 */     setValue(ANY);
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 36 */     if (value == null) {
/* 37 */       value = ANY;
/*    */     }
/* 39 */     super.setValue(value);
/*    */   }
/*    */   
/*    */   public List getData() {
/* 43 */     List<?> retValue = new LinkedList(Symptom.getDomain(this.context));
/* 44 */     Collections.sort(retValue, new Comparator() {
/*    */           public int compare(Object obj, Object obj1) {
/* 46 */             return ((Symptom)obj).getIdentifier(SymptomSelectionElement.this.context.getLocale()).compareTo(((Symptom)obj1).getIdentifier(SymptomSelectionElement.this.context.getLocale()));
/*    */           }
/*    */         });
/* 49 */     retValue.add(0, ANY);
/* 50 */     return retValue;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 54 */     if (option == ANY) {
/* 55 */       return this.context.getLabel("any");
/*    */     }
/* 57 */     return ((Symptom)option).getIdentifier(this.context.getLocale());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\input\SymptomSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */