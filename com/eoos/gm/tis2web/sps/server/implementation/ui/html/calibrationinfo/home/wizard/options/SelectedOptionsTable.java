/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.options;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.html.element.HtmlElementBase;
/*    */ import com.eoos.util.StringUtilities;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectedOptionsTable
/*    */   extends HtmlElementBase
/*    */ {
/*    */   private ClientContext context;
/*    */   private Callback callback;
/*    */   
/*    */   public static class StandardCallback
/*    */     implements Callback
/*    */   {
/*    */     private CustomAVMap avMap;
/*    */     
/*    */     public StandardCallback(CustomAVMap avMap) {
/* 35 */       this.avMap = avMap;
/*    */     }
/*    */     
/*    */     public int getOptionsCount() {
/* 39 */       return this.avMap.getExplicitEntries().size();
/*    */     }
/*    */     
/*    */     public Attribute getAttribute(int optionIndex) {
/* 43 */       return ((CustomAVMap.Entry)this.avMap.getExplicitEntries().get(optionIndex)).getAttribute();
/*    */     }
/*    */     
/*    */     public Value getValue(int optionIndex) {
/* 47 */       return ((CustomAVMap.Entry)this.avMap.getExplicitEntries().get(optionIndex)).getValue();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public SelectedOptionsTable(ClientContext context, CustomAVMap avMap) {
/* 53 */     this(context, new StandardCallback(avMap));
/*    */   }
/*    */   
/*    */   public SelectedOptionsTable(ClientContext context, Callback callback) {
/* 57 */     this.context = context;
/* 58 */     this.callback = callback;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 62 */     StringBuffer tmp = new StringBuffer("<table >{ROWS}</table>");
/* 63 */     for (int i = 0; i < this.callback.getOptionsCount(); i++) {
/* 64 */       Attribute attribute = this.callback.getAttribute(i);
/* 65 */       Value value = this.callback.getValue(i);
/*    */       
/* 67 */       StringUtilities.replace(tmp, "{ROWS}", "<tr><th>" + AVUtil.getDisplayString(attribute, this.context.getLocale()) + ":</th><td>" + AVUtil.getDisplayString(value, this.context.getLocale()) + "</td></tr>{ROWS}");
/*    */     } 
/*    */     
/* 70 */     StringUtilities.replace(tmp, "{ROWS}", "");
/* 71 */     return tmp.toString();
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     int getOptionsCount();
/*    */     
/*    */     Attribute getAttribute(int param1Int);
/*    */     
/*    */     Value getValue(int param1Int);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\options\SelectedOptionsTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */