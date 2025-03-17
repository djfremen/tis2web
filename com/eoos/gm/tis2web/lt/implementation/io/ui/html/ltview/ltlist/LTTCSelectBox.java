/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTTroublecode;
/*    */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*    */ import com.eoos.html.renderer.HtmlDivRenderer;
/*    */ import java.util.LinkedList;
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
/*    */ public class LTTCSelectBox
/*    */   extends SelectBoxSelectionElement
/*    */ {
/*    */   private ClientContext context;
/*    */   
/*    */   public LTTCSelectBox(ClientContext context, LinkedList<LTTroublecode> lData) {
/* 24 */     super("lttcsel", true, null, 1);
/* 25 */     LTTroublecode dummy = new LTTroublecode();
/* 26 */     dummy.setTroubleCodeText(context.getLabel("lt.notc"));
/* 27 */     lData = (LinkedList)lData.clone();
/* 28 */     lData.add(0, dummy);
/* 29 */     setOptions(lData);
/* 30 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected String getDisplayValue(Object option) {
/* 34 */     LTTroublecode oc = (LTTroublecode)option;
/* 35 */     if (option == getOptions().get(0)) {
/* 36 */       return oc.getTroubleCodeText();
/*    */     }
/* 38 */     return oc.getTroubleCode() + " - " + oc.getTroubleCodeText();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map map) {
/* 43 */     final String code = super.getHtmlCode(map);
/*    */     
/* 45 */     return HtmlDivRenderer.getInstance().getHtmlCode((HtmlDivRenderer.Callback)new HtmlDivRenderer.CallbackAdapter()
/*    */         {
/*    */ 
/*    */ 
/*    */           
/*    */           public String getContent()
/*    */           {
/* 52 */             return LTTCSelectBox.this.context.getLabel("lt.seltc") + "<br>" + code;
/*    */           }
/*    */           
/*    */           protected String getClaZZ() {
/* 56 */             return "lttcsel";
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\ltlist\LTTCSelectBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */