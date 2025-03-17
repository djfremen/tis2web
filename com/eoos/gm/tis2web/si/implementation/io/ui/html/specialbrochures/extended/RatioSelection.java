/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.extended;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/* 22 */   protected static final Pair[] ratios = new Pair[] { (Pair)new PairImpl("10", "90"), (Pair)new PairImpl("20", "80"), (Pair)new PairImpl("30", "70"), (Pair)new PairImpl("40", "60"), (Pair)new PairImpl("50", "50"), (Pair)new PairImpl("60", "40"), (Pair)new PairImpl("70", "30") };
/*    */ 
/*    */   
/*    */   public RatioSelection(ClientContext context) {
/* 26 */     super(context.createID(), true, Arrays.asList(ratios), 1);
/* 27 */     setValue(ratios[2]);
/*    */   }
/*    */   
/*    */   protected boolean autoSubmitOnChange() {
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   protected Object onChange(Map submitParams) {
/* 35 */     HtmlElementContainer container = getContainer();
/* 36 */     while (container.getContainer() != null) {
/* 37 */       container = container.getContainer();
/*    */     }
/* 39 */     return container;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 43 */     Pair ratio = (Pair)option;
/* 44 */     return ratio.getFirst().toString() + ":" + ratio.getSecond().toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 49 */     Object ret = super.getValue();
/* 50 */     if (ret == null) {
/* 51 */       ret = ratios[2];
/*    */     }
/* 53 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\extended\RatioSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */