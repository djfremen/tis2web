/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ 
/*    */ public class TSBComparatorSelectionElement
/*    */   extends SelectBoxSelectionElement
/*    */   implements DataRetrievalAbstraction.DataCallback
/*    */ {
/*    */   protected ClientContext context;
/* 23 */   public static final Integer MODE_MODIFICATION_DATE = Integer.valueOf(1);
/*    */   
/* 25 */   public static final Integer MODE_MODEL = Integer.valueOf(2);
/*    */   
/* 27 */   public static final Integer MODE_REMEDY_NUMBER = Integer.valueOf(3);
/*    */   
/* 29 */   public static final Integer MODE_TROUBLE_CODE = Integer.valueOf(4);
/*    */   
/* 31 */   public static final Integer MODE_ENGINE = Integer.valueOf(5);
/*    */   
/* 33 */   protected static List options = new LinkedList();
/*    */   static {
/* 35 */     options.add(MODE_MODIFICATION_DATE);
/*    */     
/* 37 */     options.add(MODE_REMEDY_NUMBER);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TSBComparatorSelectionElement(ClientContext context) {
/* 43 */     super(context.createID(), true, null, 1, null);
/* 44 */     this.context = context;
/*    */     
/* 46 */     setDataCallback(this);
/* 47 */     setValue(options.get(0));
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 51 */     if (value == null) {
/* 52 */       value = options.get(0);
/*    */     }
/* 54 */     super.setValue(value);
/*    */   }
/*    */   
/*    */   public List getData() {
/* 58 */     return options;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 62 */     if (option == MODE_MODIFICATION_DATE)
/* 63 */       return this.context.getLabel("publication.date"); 
/* 64 */     if (option == MODE_MODEL)
/* 65 */       return this.context.getLabel("model"); 
/* 66 */     if (option == MODE_ENGINE)
/* 67 */       return this.context.getLabel("engine"); 
/* 68 */     if (option == MODE_REMEDY_NUMBER)
/* 69 */       return this.context.getLabel("remedy.number"); 
/* 70 */     if (option == MODE_TROUBLE_CODE) {
/* 71 */       return this.context.getLabel("trouble.code");
/*    */     }
/* 73 */     return "-";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\input\TSBComparatorSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */