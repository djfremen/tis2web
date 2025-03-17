/*    */ package com.eoos.gm.tis2web.frame.dialog;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ContextKillDialog
/*    */   extends Page
/*    */ {
/* 19 */   private static final String IDS = ContextKillDialog.class.getName();
/*    */   
/* 21 */   private static Logger log = Logger.getLogger(IDS);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 26 */       template = ApplicationContext.getInstance().loadFile(ContextKillDialog.class, "killlogin.html", null).toString();
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to load template - error:" + e, e);
/* 29 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 36 */       notificationTemplate = ApplicationContext.getInstance().loadFile(ContextKillDialog.class, "notification.html", null).toString();
/* 37 */     } catch (Exception e) {
/* 38 */       log.error("unable to load template - error:" + e, e);
/* 39 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static String notificationTemplate;
/*    */   protected ClickButtonElement ieOK;
/*    */   protected ClickButtonElement ieCancel;
/*    */   
/*    */   public ContextKillDialog(final ClientContext context) {
/* 49 */     super(context);
/*    */     
/* 51 */     this.ieOK = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 53 */           return context.getLabel("button.yes");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 57 */           return ContextKillDialog.this.onOK(submitParams);
/*    */         }
/*    */       };
/*    */     
/* 61 */     addElement((HtmlElement)this.ieOK);
/*    */     
/* 63 */     this.ieCancel = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 65 */           return context.getLabel("button.cancel");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 69 */           return ContextKillDialog.this.onCancel();
/*    */         }
/*    */       };
/* 72 */     addElement((HtmlElement)this.ieCancel);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getNotificationPage() {
/* 77 */     return notificationTemplate;
/*    */   }
/*    */   
/*    */   protected ResultObject onCancel() {
/* 81 */     StringBuffer code = new StringBuffer(getNotificationPage());
/* 82 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.login.kill.cancelled"));
/* 83 */     return new ResultObject(0, code.toString());
/*    */   }
/*    */   
/*    */   protected ResultObject onOK(Map submitParams) {
/* 87 */     StringBuffer code = new StringBuffer(getNotificationPage());
/* 88 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.login.kill.successful"));
/* 89 */     return new ResultObject(0, code.toString());
/*    */   }
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 93 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 95 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.login.kill.message"));
/* 96 */     StringUtilities.replace(code, "{BUTTON_OK}", this.ieOK.getHtmlCode(params));
/* 97 */     StringUtilities.replace(code, "{BUTTON_CANCEL}", this.ieCancel.getHtmlCode(params));
/*    */     
/* 99 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dialog\ContextKillDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */