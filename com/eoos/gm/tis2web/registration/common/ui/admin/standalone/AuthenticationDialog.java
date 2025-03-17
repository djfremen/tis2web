/*     */ package com.eoos.gm.tis2web.registration.common.ui.admin.standalone;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.registration.common.task.RegistrationSTE;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.MaskedTextInputElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AuthenticationDialog extends DialogBase {
/*     */   private static String template;
/*     */   private ClientContext context;
/*     */   private TextInputElement ieUser;
/*     */   private TextInputElement iePwd;
/*     */   private ClickButtonElement buttonOK;
/*     */   private ClickButtonElement buttonCancel;
/*     */   
/*     */   static {
/*     */     try {
/*  24 */       template = ApplicationContext.getInstance().loadFile(AuthenticationDialog.class, "authdialog.html", null).toString();
/*  25 */     } catch (Exception e) {
/*  26 */       throw new RuntimeException(e);
/*     */     } 
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
/*     */   public AuthenticationDialog(final ClientContext context, final Callback callback) {
/*  42 */     super(context);
/*  43 */     this.context = context;
/*     */     
/*  45 */     this.ieUser = new TextInputElement(context.createID());
/*  46 */     addElement((HtmlElement)this.ieUser);
/*     */     
/*  48 */     this.iePwd = (TextInputElement)new MaskedTextInputElement(context.createID());
/*  49 */     addElement((HtmlElement)this.iePwd);
/*     */     
/*  51 */     this.buttonOK = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  54 */           return context.getLabel("ok");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  58 */           String user = (String)AuthenticationDialog.this.ieUser.getValue();
/*  59 */           String pwd = (String)AuthenticationDialog.this.iePwd.getValue();
/*     */           
/*     */           try {
/*  62 */             return callback.onOK(user, pwd);
/*  63 */           } catch (com.eoos.gm.tis2web.registration.common.task.RegistrationSTE.MissingAuthenticationException e) {
/*  64 */             return AuthenticationDialog.this.getErrorPopup(context.getMessage("invalid.authentication"));
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  70 */     addElement((HtmlElement)this.buttonOK);
/*     */     
/*  72 */     this.buttonCancel = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  75 */           return context.getLabel("cancel");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  79 */           return callback.onCancel();
/*     */         }
/*     */       };
/*     */     
/*  83 */     addElement((HtmlElement)this.buttonCancel);
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/*  87 */     StringBuffer ret = new StringBuffer(template);
/*     */     
/*  89 */     StringUtilities.replace(ret, "{LABEL_USER}", this.context.getLabel("user"));
/*  90 */     StringUtilities.replace(ret, "{INPUT_USER}", this.ieUser.getHtmlCode(params));
/*     */     
/*  92 */     StringUtilities.replace(ret, "{LABEL_PWD}", this.context.getLabel("password"));
/*  93 */     StringUtilities.replace(ret, "{INPUT_PWD}", this.iePwd.getHtmlCode(params));
/*     */     
/*  95 */     StringUtilities.replace(ret, "{BUTTON_OK}", this.buttonOK.getHtmlCode(params));
/*  96 */     StringUtilities.replace(ret, "{BUTTON_CANCEL}", this.buttonCancel.getHtmlCode(params));
/*     */     
/*  98 */     return ret.toString();
/*     */   } public static interface Callback {
/*     */     Object onOK(String param1String1, String param1String2) throws RegistrationSTE.MissingAuthenticationException; Object onCancel(); }
/*     */   protected String getTitle(Map params) {
/* 102 */     return this.context.getLabel("proxy.authentication");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\standalone\AuthenticationDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */