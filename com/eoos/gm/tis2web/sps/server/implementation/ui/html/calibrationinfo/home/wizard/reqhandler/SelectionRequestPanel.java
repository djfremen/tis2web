/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler;
/*    */ 
/*    */ import com.eoos.datatype.Denotation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.RequestHandlerPanel;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionRequestPanel
/*    */   extends HtmlElementContainerBase
/*    */   implements RequestHandlerPanel
/*    */ {
/*    */   private ClientContext context;
/*    */   private SelectionRequest request;
/*    */   private SelectBoxSelectionElement selectionElement;
/*    */   
/* 33 */   private static final Object NULL_VALUE = new Denotation() {
/*    */       public String getDenotation(Locale locale) {
/* 35 */         return ApplicationContext.getInstance().getMessage(locale, "sps.please.select");
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public SelectionRequestPanel(final ClientContext context, SelectionRequest request) {
/* 41 */     this.context = context;
/* 42 */     this.request = request;
/*    */     
/* 44 */     DataRetrievalAbstraction.DataCallback dc = new DataRetrievalAbstraction.DataCallback() {
/*    */         public List getData() {
/* 46 */           List<Object> retValue = new LinkedList(SelectionRequestPanel.this.request.getOptions());
/* 47 */           retValue.add(0, SelectionRequestPanel.NULL_VALUE);
/* 48 */           return retValue;
/*    */         }
/*    */       };
/* 51 */     this.selectionElement = new SelectBoxSelectionElement(context.createID(), true, dc, Math.min(dc.getData().size(), 10), "_top") {
/*    */         protected String getDisplayValue(Object option) {
/* 53 */           if (option instanceof Denotation) {
/* 54 */             return ((Denotation)option).getDenotation(context.getLocale());
/*    */           }
/* 56 */           return String.valueOf(option);
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 61 */     addElement((HtmlElement)this.selectionElement);
/* 62 */     if (request instanceof DefaultValueRetrieval) {
/* 63 */       this.selectionElement.setValue(((DefaultValueRetrieval)request).getDefaultValue());
/*    */     } else {
/* 65 */       this.selectionElement.setValue(dc.getData().get(0));
/*    */     } 
/*    */   }
/*    */   
/*    */   private String getDisplay(Attribute attribute) {
/* 70 */     if (attribute instanceof Denotation) {
/* 71 */       return ((Denotation)attribute).getDenotation(this.context.getLocale());
/*    */     }
/* 73 */     return String.valueOf(attribute);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 78 */     StringBuffer tmp = new StringBuffer("<table><tr><td valign=\"top\"><b>{LABEL}:</b></td><td>{SELECTION}</td></tr></table>");
/* 79 */     StringUtilities.replace(tmp, "{LABEL}", getDisplay(this.request.getAttribute()));
/* 80 */     StringUtilities.replace(tmp, "{SELECTION}", this.selectionElement.getHtmlCode(params));
/* 81 */     return tmp.toString();
/*    */   }
/*    */   
/*    */   public boolean onNext(CustomAVMap avMap) {
/* 85 */     Object value = this.selectionElement.getValue();
/* 86 */     if (value != null && value != NULL_VALUE) {
/* 87 */       avMap.explicitSet(this.request.getAttribute(), (Value)value);
/* 88 */       return true;
/*    */     } 
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\SelectionRequestPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */