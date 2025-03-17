/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ public class FaultDiagSelectionElement
/*    */   extends SelectBoxSelectionElement
/*    */ {
/*    */   private static class Options
/*    */     implements DataRetrievalAbstraction.DataCallback
/*    */   {
/*    */     private DataRetrievalAbstraction.DataCallback dcb;
/*    */     private String first;
/*    */     
/*    */     public Options(DataRetrievalAbstraction.DataCallback dcb, String first) {
/* 30 */       this.dcb = dcb;
/* 31 */       this.first = first;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public List getData() {
/* 39 */       ArrayList<String> list = new ArrayList(this.dcb.getData().size() + 1);
/* 40 */       list.add(this.first);
/*    */       
/* 42 */       list.addAll(this.dcb.getData());
/*    */       
/* 44 */       return list;
/*    */     }
/*    */   }
/*    */   
/*    */   public FaultDiagSelectionElement(String parameterName, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size, String targetBookmark) {
/* 49 */     super(parameterName, singleSelectionMode, optionsCallback, size, targetBookmark);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FaultDiagSelectionElement(ClientContext context, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size) {
/* 59 */     super(context.createID(), singleSelectionMode, new Options(optionsCallback, context.getLabel("no.selection")), size);
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 63 */     Object ret = super.getValue();
/* 64 */     Iterator it = getDataCallback().getData().iterator();
/* 65 */     if (it.hasNext() && it.next() == ret) {
/* 66 */       ret = null;
/*    */     }
/*    */     
/* 69 */     return ret;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 73 */     setValue(this.optionsCallback.getData().get(0));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\FaultDiagSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */