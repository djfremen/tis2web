/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class FaultCodeSelectionPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  28 */   private static Logger log = Logger.getLogger(FaultCodeSelectionPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(FaultCodeSelectionPanel.class, "faultcodeselectionpanel.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("unable to load template - error:" + e, e);
/*  36 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/*     */ 
/*     */   
/*     */   protected HtmlElementStack stack;
/*     */ 
/*     */   
/*     */   protected FaultDiagSelectionElement faultSelection;
/*     */   
/*     */   protected ClickButtonElement okButton;
/*     */ 
/*     */   
/*     */   public void init(final ClientContext context, DataRetrievalAbstraction.DataCallback faultCodes, HtmlElementStack stack) {
/*  54 */     this.context = context;
/*  55 */     this.stack = stack;
/*  56 */     this.faultSelection = new FaultDiagSelectionElement(context, true, faultCodes, 1) {
/*     */         protected Object onChange(Map submitParams) {
/*  58 */           FaultCodeSelectionPanel.this.okButton.setDisabled(new Boolean((getValue() == null)));
/*  59 */           HtmlElementContainer container = FaultCodeSelectionPanel.this.getContainer();
/*  60 */           while (container.getContainer() != null) {
/*  61 */             container = container.getContainer();
/*     */           }
/*  63 */           return container;
/*     */         }
/*     */         
/*     */         protected boolean autoSubmitOnChange() {
/*  67 */           return true;
/*     */         }
/*     */       };
/*     */     
/*  71 */     addElement((HtmlElement)this.faultSelection);
/*  72 */     this.okButton = new ClickButtonElement(context.createID(), null) {
/*     */         public Object onClick(Map params) {
/*  74 */           HtmlElement np = FaultCodeSelectionPanel.this.getNextPanel();
/*  75 */           if (np != null) {
/*  76 */             FaultCodeSelectionPanel.this.stack.push(np);
/*     */           }
/*  78 */           HtmlElementContainer container = FaultCodeSelectionPanel.this.getContainer();
/*  79 */           while (container.getContainer() != null) {
/*  80 */             container = container.getContainer();
/*     */           }
/*  82 */           return container;
/*     */         }
/*     */         
/*     */         public String getLabel() {
/*  86 */           return context.getLabel("button.ok");
/*     */         }
/*     */       };
/*  89 */     this.okButton.setDisabled(new Boolean(true));
/*  90 */     addElement((HtmlElement)this.okButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  99 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 101 */     StringUtilities.replace(code, "{FAULT_CODE_LABEL}", this.context.getLabel("si.faultdiag.FaultCode"));
/* 102 */     StringUtilities.replace(code, "{ECM_SELECTION}", EcmSelectionCode(params));
/*     */     
/* 104 */     StringUtilities.replace(code, "{FAULT_CODE_SELECTION}", this.faultSelection.getHtmlCode(params));
/* 105 */     StringUtilities.replace(code, "{OK_BUTTON}", this.okButton.getHtmlCode(params));
/*     */     
/* 107 */     return code.toString();
/*     */   }
/*     */   
/*     */   public void disableFaultSelection(boolean disable) {
/* 111 */     this.faultSelection.reset();
/* 112 */     this.faultSelection.setDisabled(new Boolean(disable));
/* 113 */     this.okButton.setDisabled(new Boolean(true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 122 */     this.faultSelection.reset();
/* 123 */     this.okButton.setDisabled(new Boolean(true));
/*     */   }
/*     */   
/*     */   public abstract String EcmSelectionCode(Map paramMap);
/*     */   
/*     */   public abstract HtmlElement getNextPanel();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\FaultCodeSelectionPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */