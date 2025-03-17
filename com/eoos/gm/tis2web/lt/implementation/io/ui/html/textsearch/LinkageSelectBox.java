/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinkageSelectBox
/*    */   extends SelectBoxSelectionElement
/*    */ {
/* 18 */   public static final Integer AND = Integer.valueOf(1);
/*    */   
/* 20 */   public static final Integer OR = Integer.valueOf(2);
/*    */   
/* 22 */   public static final Integer EXPERT = Integer.valueOf(3);
/*    */   
/* 24 */   protected static List data = new ArrayList(3);
/*    */   static {
/* 26 */     data.add(AND);
/* 27 */     data.add(OR);
/* 28 */     data.add(EXPERT);
/*    */   }
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   protected class DataCallback implements DataRetrievalAbstraction.DataCallback { public List getData() {
/* 34 */       return LinkageSelectBox.data;
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LinkageSelectBox(ClientContext context) {
/* 41 */     super(context.createID(), true, null, 1, null);
/* 42 */     this.optionsCallback = new DataCallback();
/* 43 */     this.context = context;
/* 44 */     setValue(AND);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/*    */     String retValue;
/* 50 */     if (option.equals(AND)) {
/* 51 */       retValue = this.context.getLabel("and");
/* 52 */     } else if (option.equals(OR)) {
/* 53 */       retValue = this.context.getLabel("or");
/* 54 */     } else if (option.equals(EXPERT)) {
/* 55 */       retValue = this.context.getLabel("expert");
/*    */     } else {
/* 57 */       retValue = "-";
/*    */     } 
/* 59 */     return retValue.toUpperCase(Locale.ENGLISH);
/*    */   }
/*    */   
/*    */   protected String getSubmitValue(Object option) {
/* 63 */     return String.valueOf(option);
/*    */   }
/*    */   
/*    */   protected Object getOption(String submitValue) {
/* 67 */     return Integer.valueOf(Integer.parseInt(submitValue));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\textsearch\LinkageSelectBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */