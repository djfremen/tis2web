/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.ImageElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
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
/*     */ public abstract class NotificationMessageBox
/*     */   extends MessageBoxBase
/*     */ {
/*     */   protected ImageElement icon;
/*     */   protected ClickButtonElement buttonOK;
/*     */   
/*     */   public NotificationMessageBox(ClientContext context, String caption, String message) {
/*  25 */     super(context, (caption != null) ? caption : context.getLabel("notification"), message);
/*  26 */     this.buttonOK = createOKButton();
/*  27 */     addElement((HtmlElement)this.buttonOK);
/*     */     
/*  29 */     this.icon = createIcon();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Type
/*     */   {
/*     */     private Type() {}
/*     */   }
/*     */ 
/*     */   
/*  39 */   public static final Type TYPE_INFO = new Type();
/*     */   
/*  41 */   public static final Type TYPE_WARNING = new Type();
/*     */   
/*     */   public static NotificationMessageBox create(Type type, ClientContext context, String caption, String message, Object returnValue) {
/*  44 */     if (type == TYPE_INFO) {
/*  45 */       return createInfoMessage(context, caption, message, returnValue);
/*     */     }
/*  47 */     return createWarningMessage(context, caption, message, returnValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public static NotificationMessageBox createInfoMessage(ClientContext context, String caption, String message, final Object returnValue) {
/*  52 */     return new NotificationMessageBox(context, caption, message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/*  55 */           return returnValue;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static NotificationMessageBox createWarningMessage(ClientContext context, String caption, String message, final Object returnValue) {
/*  62 */     return new NotificationMessageBox(context, caption, message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/*  65 */           return returnValue;
/*     */         }
/*     */         
/*     */         protected ImageElement createIcon() {
/*  69 */           return new ImageElement() {
/*     */               protected String getImageURL() {
/*  71 */                 return "pic/common/warning.gif";
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClickButtonElement createOKButton() {
/*  80 */     return new ClickButtonElement(this.context.createID(), null) {
/*     */         protected String getLabel() {
/*  82 */           return NotificationMessageBox.this.context.getLabel("ok");
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  86 */           return NotificationMessageBox.this.onOK(params);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected ImageElement createIcon() {
/*  92 */     return new ImageElement() {
/*     */         protected String getImageURL() {
/*  94 */           return "pic/common/information.gif";
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected String getIconCode(Map params) {
/* 100 */     return this.icon.getHtmlCode(params);
/*     */   }
/*     */   
/*     */   protected String getButtonsCode(Map params) {
/* 104 */     return this.buttonOK.getHtmlCode(params);
/*     */   }
/*     */   
/*     */   protected abstract Object onOK(Map paramMap);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\NotificationMessageBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */