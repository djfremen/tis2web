/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.ecm;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.FaultCodeSelectionPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.FaultDiagSelectionElement;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.FilterCallback;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.ecm.FilterCallback2;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ECMFaultCodeSelectionPanel
/*     */   extends FaultCodeSelectionPanel
/*     */   implements DataRetrievalAbstraction.DataCallback
/*     */ {
/*     */   private FilterCallback faultCodes;
/*     */   private FaultDiagSelectionElement ecmSelection;
/*  36 */   private Object selECM = null;
/*     */   
/*     */   private FilterCallback2 cprs;
/*     */   
/*     */   public List getData() {
/*  41 */     return (this.selECM == null) ? new LinkedList() : this.faultCodes.getData(this.selECM);
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
/*     */   public ECMFaultCodeSelectionPanel(ClientContext context, FilterCallback faultCodes, FilterCallback2 cprs, HtmlElementStack stack, DataRetrievalAbstraction.DataCallback ecms) {
/*  57 */     this.faultCodes = faultCodes;
/*  58 */     this.cprs = cprs;
/*  59 */     init(context, this, stack);
/*  60 */     this.ecmSelection = new FaultDiagSelectionElement(context, true, ecms, 1) {
/*     */         protected Object onChange(Map submitParams) {
/*  62 */           ECMFaultCodeSelectionPanel.this.selECM = getValue();
/*  63 */           ECMFaultCodeSelectionPanel.this.disableFaultSelection((ECMFaultCodeSelectionPanel.this.selECM == null));
/*  64 */           HtmlElementContainer container = ECMFaultCodeSelectionPanel.this.getContainer();
/*  65 */           while (container.getContainer() != null) {
/*  66 */             container = container.getContainer();
/*     */           }
/*  68 */           return container;
/*     */         }
/*     */         
/*     */         protected boolean autoSubmitOnChange() {
/*  72 */           return true;
/*     */         }
/*     */       };
/*     */     
/*  76 */     addElement((HtmlElement)this.ecmSelection);
/*  77 */     disableFaultSelection(true);
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
/*     */   public String EcmSelectionCode(Map params) {
/*  90 */     StringBuffer ret = new StringBuffer("<tr><th align=\"right\">{FAULT_CODE_LABEL}</th><td align=\"left\">{FAULT_CODE_SELECTION}</td></tr>");
/*  91 */     StringUtilities.replace(ret, "{FAULT_CODE_LABEL}", this.context.getLabel("si.faultdiag.ecm"));
/*     */     
/*  93 */     StringUtilities.replace(ret, "{FAULT_CODE_SELECTION}", this.ecmSelection.getHtmlCode(params));
/*  94 */     return ret.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HtmlElement getNextPanel() {
/*     */     ECMFaultDiagListElement eCMFaultDiagListElement;
/* 103 */     HtmlElement ret = null;
/* 104 */     if (this.okButton.isDisabled()) {
/* 105 */       this.faultSelection.reset();
/*     */     }
/*     */     else {
/*     */       
/* 109 */       final Object fc = this.faultSelection.getValue();
/* 110 */       final Object ecm = this.ecmSelection.getValue();
/* 111 */       if (fc != null && ecm != null) {
/* 112 */         DataRetrievalAbstraction.DataCallback dcb = new DataRetrievalAbstraction.DataCallback() {
/*     */             public List getData() {
/* 114 */               return ECMFaultCodeSelectionPanel.this.cprs.getData(ecm, fc);
/*     */             }
/*     */           };
/*     */ 
/*     */         
/* 119 */         eCMFaultDiagListElement = new ECMFaultDiagListElement(dcb, fc.toString(), this.context, this.stack, ecm.toString());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return (HtmlElement)eCMFaultDiagListElement;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 129 */     this.ecmSelection.reset();
/* 130 */     disableFaultSelection(true);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\ecm\ECMFaultCodeSelectionPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */