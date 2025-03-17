/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs.input.RefDocElement;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RefDocList
/*     */   extends ListElement
/*     */ {
/*     */   private ClientContext context;
/*     */   protected HtmlElementStack stack;
/*     */   protected HtmlLabel[] header;
/*     */   protected RefDocDialog page;
/*     */   
/*     */   public RefDocList(ClientContext context, DataRetrievalAbstraction.DataCallback docs, RefDocDialog page, HtmlElementStack stack) {
/*  39 */     super(docs);
/*  40 */     this.context = context;
/*  41 */     this.stack = stack;
/*  42 */     this.header = new HtmlLabel[] { new HtmlLabel(context.getLabel("si.faultdiag.VehicleSystem")), new HtmlLabel(context.getLabel("si.faultdiag.InformationType")), new HtmlLabel(context.getLabel("si.faultdiag.Document")) };
/*  43 */     this.page = page;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getColumnCount() {
/*  49 */     return 3;
/*     */   }
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*     */     LinkElement linkElement;
/*  53 */     HtmlElement ret = null;
/*  54 */     if (data instanceof RefDocElement) {
/*  55 */       final RefDocElement rdel = (RefDocElement)data;
/*  56 */       switch (columnIndex) {
/*     */         case 0:
/*  58 */           return (HtmlElement)new HtmlLabel(rdel.getVehicleSystem());
/*     */         
/*     */         case 1:
/*  61 */           return (HtmlElement)new HtmlLabel(rdel.getInformationType());
/*     */ 
/*     */         
/*     */         case 2:
/*  65 */           linkElement = new LinkElement(this.context.createID(), null)
/*     */             {
/*     */               protected String getLabel() {
/*  68 */                 return rdel.getDocument();
/*     */               }
/*     */               
/*     */               public Object onClick(Map submitParams) {
/*  72 */                 RefDocList.this.setDocument(rdel);
/*  73 */                 HtmlElementContainer container = RefDocList.this.getContainer();
/*  74 */                 while (container.getContainer() != null) {
/*  75 */                   container = container.getContainer();
/*     */                 }
/*  77 */                 return container;
/*     */               }
/*     */             };
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/*  84 */     return (HtmlElement)linkElement;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/*  88 */     return (HtmlElement)this.header[columnIndex];
/*     */   }
/*     */   
/*     */   public void setDocument(RefDocElement rdel) {
/*  92 */     this.stack.push((HtmlElement)new RefDocContainer(this.context, rdel.getSIO(), this.stack));
/*  93 */     this.page.setTitle(rdel.getDocument());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPage(RefDocDialog page) {
/* 106 */     this.page = page;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\refdocs\RefDocList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */