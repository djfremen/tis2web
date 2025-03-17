/*    */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class VCActionButtonsElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 20 */   private ClientContext context = null;
/*    */   
/* 22 */   HtmlElement buttonOK = null;
/*    */   
/* 24 */   HtmlElement buttonCancel = null;
/*    */   
/* 26 */   HtmlElement buttonReset = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public VCActionButtonsElement(ClientContext context) {
/* 31 */     this.context = context;
/* 32 */     createHtmlElements();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createHtmlElements() {
/* 37 */     this.buttonOK = (HtmlElement)new ClickButtonElement("buttonOK", "buttonOK") {
/*    */         protected String getLabel() {
/* 39 */           return VCActionButtonsElement.this.context.getLabel("button.ok");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 43 */           return VCActionButtonsElement.this.onOK(submitParams);
/*    */         }
/*    */         
/*    */         public boolean isDisabled() {
/* 47 */           return false;
/*    */         }
/*    */       };
/* 50 */     addElement(this.buttonOK);
/*    */     
/* 52 */     this.buttonCancel = (HtmlElement)new ClickButtonElement("buttonCancel", "buttonCancel") {
/*    */         protected String getLabel() {
/* 54 */           return VCActionButtonsElement.this.context.getLabel("button.cancel");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 58 */           return VCActionButtonsElement.this.onCancel(submitParams);
/*    */         }
/*    */       };
/* 61 */     addElement(this.buttonCancel);
/*    */     
/* 63 */     this.buttonReset = (HtmlElement)new ClickButtonElement("buttonReset", "buttonReset") {
/*    */         protected String getLabel() {
/* 65 */           return VCActionButtonsElement.this.context.getLabel("button.reset");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 69 */           return VCActionButtonsElement.this.onReset(submitParams);
/*    */         }
/*    */       };
/* 72 */     addElement(this.buttonReset);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 76 */     return this.buttonOK.getHtmlCode(params) + "&nbsp;" + this.buttonCancel.getHtmlCode(params) + "&nbsp;" + this.buttonReset.getHtmlCode(params);
/*    */   }
/*    */   
/*    */   protected HtmlElementContainer getRootContainer() {
/* 80 */     HtmlElementContainer container = getContainer();
/* 81 */     while (container.getContainer() != null) {
/* 82 */       container = container.getContainer();
/*    */     }
/* 84 */     return container;
/*    */   }
/*    */   
/*    */   protected abstract Object onOK(Map paramMap);
/*    */   
/*    */   protected abstract Object onCancel(Map paramMap);
/*    */   
/*    */   protected abstract Object onReset(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\ie\VCActionButtonsElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */