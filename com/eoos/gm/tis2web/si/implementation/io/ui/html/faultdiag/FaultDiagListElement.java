/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentIFramePanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.FDSIOLUDocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.wd.CprlIFrame;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FaultDiagElement;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.SimpleFaultDiagElement;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.IFrameElement;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class FaultDiagListElement
/*     */   extends ListElement
/*     */ {
/*     */   protected List data;
/*     */   protected String faultCode;
/*     */   protected ClientContext context;
/*     */   protected HtmlLabel[] header;
/*     */   private HtmlElementStack stack;
/*     */   
/*     */   public List getData() {
/*  33 */     return this.data;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public FaultDiagListElement(DataRetrievalAbstraction.DataCallback data, String faultCode, ClientContext context, HtmlElementStack stack) {
/*  49 */     this(data, faultCode, context, stack, new HtmlLabel[] { new HtmlLabel(context.getLabel("si.faultdiag.FaultCode")), new HtmlLabel(context.getLabel("si.faultdiag.ServiceCategory")), new HtmlLabel(context.getLabel("si.faultdiag.Symptom")) });
/*     */   }
/*     */   
/*     */   protected FaultDiagListElement(DataRetrievalAbstraction.DataCallback data, String faultCode, ClientContext context, HtmlElementStack stack, HtmlLabel[] header) {
/*  53 */     super(data);
/*  54 */     this.faultCode = faultCode;
/*  55 */     this.stack = stack;
/*  56 */     this.context = context;
/*  57 */     this.header = header;
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
/*     */   protected int getColumnCount() {
/*  70 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/*  77 */     return (HtmlElement)this.header[columnIndex];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*     */     HtmlLabel htmlLabel;
/*  84 */     HtmlElement ret = null;
/*  85 */     if (data instanceof SimpleFaultDiagElement)
/*  86 */     { SimpleFaultDiagElement fde = (SimpleFaultDiagElement)data;
/*  87 */       switch (columnIndex)
/*     */       
/*     */       { case 0:
/*  90 */           ret = getLinkElement(data);
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
/* 102 */           return ret;case 1: htmlLabel = new HtmlLabel(fde.getServiceCategorie()); return (HtmlElement)htmlLabel; }  htmlLabel = new HtmlLabel(fde.getSymptom()); }  return (HtmlElement)htmlLabel;
/*     */   }
/*     */   
/*     */   public HtmlElement getLinkElement(Object data) {
/* 106 */     final SIO node = ((FaultDiagElement)data).getSIO();
/* 107 */     return (HtmlElement)new LinkElement(this.context.createID(), null) {
/*     */         protected String getLabel() {
/* 109 */           return FaultDiagListElement.this.faultCode;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 115 */           DocumentPage dp = new DocumentPage(FaultDiagListElement.this.context);
/* 116 */           dp.setSioluCreator((DocumentPage.SIOLUContainerCreator)new FDSIOLUDocumentContainer.FDSIOLUContainerCreator());
/* 117 */           FaultDiagPanel.getInstance(FaultDiagListElement.this.context).setDocumentPage(dp);
/* 118 */           CprlIFrame docCont = new CprlIFrame(FaultDiagListElement.this.context, node, dp);
/* 119 */           DocumentIFramePanel difp = new DocumentIFramePanel(FaultDiagListElement.this.context, (IFrameElement)docCont);
/* 120 */           FaultDiagListElement.this.stack.push((HtmlElement)difp);
/* 121 */           HtmlElementContainer container = FaultDiagListElement.this.getContainer();
/* 122 */           while (container.getContainer() != null) {
/* 123 */             container = container.getContainer();
/*     */           }
/* 125 */           return container;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\FaultDiagListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */