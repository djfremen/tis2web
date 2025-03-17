/*     */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyInputDialog
/*     */   extends DialogBase
/*     */ {
/*     */   private ClientContext context;
/*     */   private TextInputElement ieTextInput;
/*     */   private ClickButtonElement buttonFinished;
/*     */   private ClickButtonElement buttonCancel;
/*  21 */   private String statusMessage = null;
/*     */   
/*     */   public KeyInputDialog(final ClientContext context, String defaultValue) {
/*  24 */     super(context);
/*  25 */     this.context = context;
/*     */     
/*  27 */     this.ieTextInput = new TextInputElement(context.createID(), 80, 80);
/*  28 */     addElement((HtmlElement)this.ieTextInput);
/*  29 */     if (defaultValue != null && defaultValue.length() >= 64) {
/*  30 */       StringBuffer tmp = new StringBuffer(defaultValue);
/*  31 */       StringUtilities.replace(tmp, "-", "");
/*  32 */       for (int i = 0; i < 15; i++) {
/*  33 */         tmp.insert((i + 1) * 4 + i, "-");
/*     */       }
/*     */       
/*  36 */       this.ieTextInput.setValue(tmp.toString());
/*     */     } 
/*     */     
/*  39 */     this.buttonFinished = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  42 */           return context.getLabel("ok");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  46 */           String tmp = (String)KeyInputDialog.this.ieTextInput.getValue();
/*     */           try {
/*  48 */             if (tmp != null) {
/*  49 */               tmp = StringUtilities.replace(tmp.trim(), "-", "");
/*  50 */               if (tmp.length() == 64) {
/*     */                 Object key;
/*     */                 try {
/*  53 */                   key = KeyInputDialog.this.createKey(tmp);
/*  54 */                 } catch (Exception e) {
/*  55 */                   throw KeyInputDialog.InternalException.KEY_CREATION;
/*     */                 } 
/*  57 */                 return KeyInputDialog.this.onClose(key);
/*     */               } 
/*  59 */               throw KeyInputDialog.InternalException.INVALID_INPUT_LENGTH;
/*     */             } 
/*     */             
/*  62 */             throw KeyInputDialog.InternalException.NO_INPUT;
/*     */           }
/*  64 */           catch (Exception e) {
/*  65 */             KeyInputDialog.this.setErrorStatus(e);
/*  66 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  72 */     addElement((HtmlElement)this.buttonFinished);
/*     */     
/*  74 */     this.buttonCancel = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  77 */           return context.getLabel("cancel");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  81 */           return KeyInputDialog.this.onClose((Object)null);
/*     */         }
/*     */       };
/*     */     
/*  85 */     addElement((HtmlElement)this.buttonCancel);
/*     */   }
/*     */   
/*     */   private void setErrorStatus(Exception e) {
/*  89 */     this.statusMessage = this.context.getMessage("unable.to.create.key");
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/*  93 */     StringBuffer tmp = new StringBuffer("<table class=\"keyinput\"><tr><td id=\"inputcontainer\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>1</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>2</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>3</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>4</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>5</strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>6</strong><br />1234-5678-9<strong>0</strong>12-3456-789<strong>0</strong>-1234-5678-9<strong>0</strong>12-3456-789<strong>0</strong>-1234-5678-9<strong>0</strong>12-3456-789<strong>0</strong>-1234<br />{INPUT_KEY}</td></tr>{STATUSROW}<tr><td align=\"center\">{BUTTON_OK}&nbsp;{BUTTON_CANCEL}</td></tr></table>");
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (this.statusMessage != null) {
/*  98 */       StringUtilities.replace(tmp, "{STATUSROW}", "<tr><td style=\"text-align:center;color:red\">" + this.statusMessage + "</td></tr>");
/*     */     } else {
/* 100 */       StringUtilities.replace(tmp, "{STATUSROW}", "");
/*     */     } 
/* 102 */     StringUtilities.replace(tmp, "{INPUT_KEY}", this.ieTextInput.getHtmlCode(params));
/* 103 */     StringUtilities.replace(tmp, "{BUTTON_OK}", this.buttonFinished.getHtmlCode(params));
/* 104 */     StringUtilities.replace(tmp, "{BUTTON_CANCEL}", this.buttonCancel.getHtmlCode(params));
/*     */     
/* 106 */     return tmp.toString();
/*     */   }
/*     */   protected abstract Object createKey(String paramString) throws Exception;
/*     */   protected String getTitle(Map params) {
/* 110 */     return this.context.getLabel("key.input");
/*     */   }
/*     */   
/*     */   protected abstract Object onClose(Object paramObject);
/*     */   
/*     */   private static final class InternalException extends Exception {
/* 116 */     private static final InternalException KEY_CREATION = new InternalException();
/*     */     private static final long serialVersionUID = 1L;
/* 118 */     private static final InternalException INVALID_INPUT_LENGTH = new InternalException();
/*     */     
/* 120 */     private static final InternalException NO_INPUT = new InternalException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\KeyInputDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */