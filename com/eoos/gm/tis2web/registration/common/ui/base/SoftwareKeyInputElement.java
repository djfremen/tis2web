/*     */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.SoftwareKeyFactory;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class SoftwareKeyInputElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   private static final String TEMPLATE = "<div id=\"softwarekeypanel\">  <table ><tr><th>{LABEL}:</th><td nowrap=\"nowrap\">{INPUT}</td></tr></table></div>";
/*     */   private LinkElement linkKeyInput;
/*  20 */   private TextInputElement[] inputKeyParts = new TextInputElement[16];
/*     */   
/*     */   public SoftwareKeyInputElement(final ClientContext context) {
/*  23 */     for (int i = 0; i < 16; i++) {
/*  24 */       this.inputKeyParts[i] = new TextInputElement(context.createID(), 4, 4);
/*  25 */       addElement((HtmlElement)this.inputKeyParts[i]);
/*     */     } 
/*  27 */     resetValue();
/*     */     
/*  29 */     this.linkKeyInput = new LinkElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  32 */           return context.getLabel("software.key");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  36 */           final HtmlElementContainer returnUI = getTopLevelContainer();
/*  37 */           SoftwareKey key = (SoftwareKey)SoftwareKeyInputElement.this.getValue();
/*  38 */           return new KeyInputDialog(context, (key != null) ? key.toString() : null)
/*     */             {
/*     */               protected Object createKey(String keyValue) throws Exception {
/*  41 */                 return SoftwareKeyFactory.createSoftwareKey(keyValue);
/*     */               }
/*     */               
/*     */               protected Object onClose(Object key) {
/*  45 */                 if (key != null) {
/*  46 */                   SoftwareKeyInputElement.this.setValue(key);
/*     */                 }
/*  48 */                 return returnUI;
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  55 */     addElement((HtmlElement)this.linkKeyInput);
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetValue() {
/*  60 */     for (int i = 0; i < 16; i++) {
/*  61 */       this.inputKeyParts[i].setValue("");
/*     */     }
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  66 */     StringBuffer ret = new StringBuffer("<div id=\"softwarekeypanel\">  <table ><tr><th>{LABEL}:</th><td nowrap=\"nowrap\">{INPUT}</td></tr></table></div>");
/*  67 */     StringUtilities.replace(ret, "{LABEL}", this.linkKeyInput.getHtmlCode(params));
/*  68 */     for (int i = 0; i < 16; i++) {
/*  69 */       String code = this.inputKeyParts[i].getHtmlCode(params);
/*  70 */       if (i < 15) {
/*  71 */         code = code.concat(" - {INPUT}");
/*     */       }
/*  73 */       StringUtilities.replace(ret, "{INPUT}", code);
/*     */     } 
/*  75 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public Object getValue() {
/*  79 */     final StringBuffer swk = new StringBuffer();
/*  80 */     for (int i = 0; i < 16; i++) {
/*  81 */       String tmp = (String)this.inputKeyParts[i].getValue();
/*  82 */       int fill = 4 - tmp.length();
/*  83 */       if (fill == 0) {
/*  84 */         swk.append(tmp);
/*  85 */         for (; fill > 0; fill--) {
/*  86 */           swk.append(" ");
/*     */         }
/*     */       } 
/*     */     } 
/*  90 */     return new SoftwareKey() {
/*     */         public String toString() {
/*  92 */           return swk.toString();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/*  98 */     String softwareKey = null;
/*  99 */     if (value != null && (softwareKey = ((SoftwareKey)value).toString()) != null) {
/* 100 */       if ("n/a".equalsIgnoreCase(softwareKey)) {
/* 101 */         resetValue();
/*     */         return;
/*     */       } 
/* 104 */       for (int i = 0; i < 16; i++) {
/* 105 */         int lowerIndex = i * 4;
/* 106 */         int upperIndex = lowerIndex + 4;
/* 107 */         this.inputKeyParts[i].setValue(softwareKey.subSequence(lowerIndex, upperIndex));
/*     */       } 
/*     */     } else {
/* 110 */       resetValue();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\SoftwareKeyInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */