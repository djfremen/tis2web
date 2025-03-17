/*     */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKeyFactory;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class LicenseKeyInputElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   private static final String TEMPLATE = "<div id=\"licensekeypanel\">  <table><tr><th>{LABEL}:</th><td nowrap=\"nowrap\">{INPUT}</td></tr></table></div>";
/*     */   private LinkElement linkKeyInput;
/*  20 */   private TextInputElement[] inputKeyParts = new TextInputElement[16];
/*     */   
/*     */   public LicenseKeyInputElement(final ClientContext context) {
/*  23 */     for (int i = 0; i < 16; i++) {
/*  24 */       this.inputKeyParts[i] = new TextInputElement(context.createID(), 4, 4);
/*  25 */       addElement((HtmlElement)this.inputKeyParts[i]);
/*     */     } 
/*  27 */     resetValue();
/*     */     
/*  29 */     this.linkKeyInput = new LinkElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  32 */           return context.getLabel("license.key");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  36 */           final HtmlElementContainer returnUI = getTopLevelContainer();
/*  37 */           LicenseKey key = (LicenseKey)LicenseKeyInputElement.this.getValue();
/*  38 */           return new KeyInputDialog(context, (key != null) ? key.toString() : null)
/*     */             {
/*     */               protected Object createKey(String keyValue) throws Exception {
/*  41 */                 return LicenseKeyFactory.createLicenseKey(keyValue);
/*     */               }
/*     */               
/*     */               protected Object onClose(Object key) {
/*  45 */                 if (key != null) {
/*  46 */                   LicenseKeyInputElement.this.setValue(key);
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
/*  60 */     new StringBuffer();
/*  61 */     for (int i = 0; i < 16; i++) {
/*  62 */       this.inputKeyParts[i].setValue("");
/*     */     }
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  67 */     StringBuffer ret = new StringBuffer("<div id=\"licensekeypanel\">  <table><tr><th>{LABEL}:</th><td nowrap=\"nowrap\">{INPUT}</td></tr></table></div>");
/*  68 */     StringUtilities.replace(ret, "{LABEL}", this.linkKeyInput.getHtmlCode(params));
/*  69 */     for (int i = 0; i < 16; i++) {
/*  70 */       String code = this.inputKeyParts[i].getHtmlCode(params);
/*  71 */       if (i < 15) {
/*  72 */         code = code.concat(" - {INPUT}");
/*     */       }
/*  74 */       StringUtilities.replace(ret, "{INPUT}", code);
/*     */     } 
/*  76 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public Object getValue() {
/*  80 */     final StringBuffer lk = new StringBuffer();
/*  81 */     for (int i = 0; i < 16; i++) {
/*  82 */       String tmp = (String)this.inputKeyParts[i].getValue();
/*  83 */       int fill = 4 - tmp.length();
/*  84 */       if (fill == 0) {
/*  85 */         lk.append(tmp);
/*  86 */         for (; fill > 0; fill--) {
/*  87 */           lk.append(" ");
/*     */         }
/*     */       } 
/*     */     } 
/*  91 */     StringUtilities.replace(lk, "O", "0");
/*  92 */     return new LicenseKey() {
/*     */         public String toString() {
/*  94 */           return lk.toString();
/*     */         }
/*     */         
/*     */         public String toExternalForm(boolean includeSeparators) {
/*  98 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 104 */     if (value != null) {
/* 105 */       String licenseKey = ((LicenseKey)value).toString();
/* 106 */       if (licenseKey != null && licenseKey.length() == 64) {
/* 107 */         for (int i = 0; i < 16; i++) {
/* 108 */           int lowerIndex = i * 4;
/* 109 */           int upperIndex = lowerIndex + 4;
/* 110 */           this.inputKeyParts[i].setValue(licenseKey.subSequence(lowerIndex, upperIndex));
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 115 */       resetValue();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\LicenseKeyInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */