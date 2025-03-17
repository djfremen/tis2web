/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch.resultlist;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch.TextSearchPanel;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIOLTListElement
/*     */   extends ListElement
/*     */   implements DataRetrievalAbstraction.DataCallback
/*     */ {
/*  28 */   protected HtmlElement headerMajorOperationNumber = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  30 */         return SIOLTListElement.this.context.getLabel("lt.majoroperation.number");
/*     */       }
/*     */     };
/*     */   
/*  34 */   protected HtmlElement headerDescription = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  36 */         return SIOLTListElement.this.context.getLabel("lt.majoroperation.description");
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/*     */   
/*     */   protected List data;
/*     */   
/*     */   public SIOLTListElement(ClientContext context, List elementList) {
/*  46 */     setDataCallback(this);
/*  47 */     this.context = context;
/*  48 */     this.data = elementList;
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/*  52 */     return 2;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/*  56 */     switch (columnIndex) {
/*     */       case 0:
/*  58 */         return this.headerMajorOperationNumber;
/*     */       case 1:
/*  60 */         return this.headerDescription;
/*     */     } 
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*  67 */     SIOLT element = (SIOLT)data;
/*     */     
/*  69 */     switch (columnIndex) {
/*     */       case 0:
/*  71 */         return (HtmlElement)new HtmlLabel(element.getMajorOperationNumber());
/*     */       case 1:
/*  73 */         return (HtmlElement)getLinkElement(element);
/*     */     } 
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getData() {
/*  80 */     return this.data;
/*     */   }
/*     */   
/*     */   public LinkElement getLinkElement(final SIOLT element) {
/*  84 */     return new LinkElement("elementlink" + element.getID(), null)
/*     */       {
/*     */         protected String getLabel()
/*     */         {
/*  88 */           return element.getSubject(LocaleInfoProvider.getInstance().getLocale(SIOLTListElement.this.context.getLocale()));
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  92 */           TextSearchPanel.getInstance(SIOLTListElement.this.context).showDocument(element);
/*     */           
/*  94 */           HtmlElementContainer parent = getContainer();
/*     */           
/*  96 */           while (parent.getContainer() != null) {
/*  97 */             parent = parent.getContainer();
/*     */           }
/*     */           
/* 100 */           return parent;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\textsearch\resultlist\SIOLTListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */