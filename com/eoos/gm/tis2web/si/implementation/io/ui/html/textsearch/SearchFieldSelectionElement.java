/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*    */ import java.util.Arrays;
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
/*    */ 
/*    */ public class SearchFieldSelectionElement
/*    */   extends SelectBoxSelectionElement
/*    */ {
/*    */   public static final String CONTENT = "content";
/*    */   public static final String SUBJECT = "subject";
/*    */   private ClientContext context;
/*    */   
/*    */   public SearchFieldSelectionElement(ClientContext context) {
/* 26 */     super(context.createID(), true, Arrays.asList(new String[] { "subject", "content" }, ), 1);
/* 27 */     this.context = context;
/* 28 */     setValue("subject");
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 32 */     if (option == "content") {
/* 33 */       return this.context.getLabel("content");
/*    */     }
/* 35 */     return this.context.getLabel("subject");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 40 */     if (value == null) {
/* 41 */       value = "subject";
/*    */     }
/* 43 */     super.setValue(value);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\SearchFieldSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */