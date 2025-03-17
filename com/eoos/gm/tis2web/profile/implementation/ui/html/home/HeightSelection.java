/*    */ package com.eoos.gm.tis2web.profile.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*    */ import java.util.Arrays;
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
/*    */ public class HeightSelection
/*    */   extends SelectBoxSelectionElement
/*    */ {
/*    */   protected static Integer[] heights;
/*    */   
/*    */   static {
/* 24 */     List<Integer> tmp = new LinkedList();
/* 25 */     for (int i = 400; i <= 1400; i += 50) {
/* 26 */       tmp.add(Integer.valueOf(i));
/*    */     }
/* 28 */     heights = tmp.<Integer>toArray(new Integer[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public HeightSelection(ClientContext context) {
/* 34 */     super(context.createID(), true, Arrays.asList(heights), 1);
/*    */     try {
/* 36 */       setValue(context.getSharedContext().getDisplayHeight());
/* 37 */     } catch (Exception e) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean autoSubmitOnChange() {
/* 43 */     return true;
/*    */   }
/*    */   
/*    */   protected Object onChange(Map submitParams) {
/* 47 */     HtmlElementContainer container = getContainer();
/* 48 */     while (container.getContainer() != null) {
/* 49 */       container = container.getContainer();
/*    */     }
/* 51 */     return container;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 55 */     return String.valueOf(option) + " Pixel";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\implementatio\\ui\html\home\HeightSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */