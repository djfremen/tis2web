/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*    */ import java.util.Arrays;
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
/*    */ public class RatioSelection
/*    */   extends SelectBoxSelectionElement
/*    */ {
/* 21 */   protected static final Pair[] ratios = new Pair[] { (Pair)new PairImpl("30", "70"), (Pair)new PairImpl("40", "60"), (Pair)new PairImpl("50", "50"), (Pair)new PairImpl("60", "40"), (Pair)new PairImpl("70", "30") };
/*    */   
/*    */   private SplitContentPanel parent;
/*    */ 
/*    */   
/*    */   public RatioSelection(SplitContentPanel parent) {
/* 27 */     super(parent.getContext().createID(), true, Arrays.asList(ratios), 1);
/* 28 */     this.parent = parent;
/* 29 */     setValue(ratios[1]);
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 33 */     Object res = super.getValue();
/* 34 */     if (res == null) {
/* 35 */       res = ratios[1];
/*    */     }
/* 37 */     return res;
/*    */   }
/*    */   
/*    */   protected boolean autoSubmitOnChange() {
/* 41 */     return true;
/*    */   }
/*    */   protected Object onChange(Map submitParams) {
/*    */     HtmlElementContainer htmlElementContainer;
/* 45 */     SplitContentPanel splitContentPanel = this.parent;
/* 46 */     while (splitContentPanel.getContainer() != null) {
/* 47 */       htmlElementContainer = splitContentPanel.getContainer();
/*    */     }
/* 49 */     return htmlElementContainer;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 53 */     Pair ratio = (Pair)option;
/* 54 */     return ratio.getFirst().toString() + ":" + ratio.getSecond().toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\RatioSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */