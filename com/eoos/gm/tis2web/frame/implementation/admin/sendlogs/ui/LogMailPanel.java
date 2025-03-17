/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.sendlogs.ui;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.logmail.LogMailer;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogMailPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 20 */   private static Logger log = Logger.getLogger(LogMailPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 25 */       template = ApplicationContext.getInstance().loadFile(LogMailPanel.class, "logmailpanel.html", null).toString();
/* 26 */     } catch (Exception e) {
/* 27 */       log.error("unable to load template - error:" + e, e);
/* 28 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected TextInputElement ieMailAddress;
/*    */   
/*    */   protected ClickButtonElement ieOK;
/*    */   
/*    */   private ClientContext context;
/* 38 */   private String statusMessage = null;
/*    */ 
/*    */   
/*    */   private LogMailPanel(final ClientContext context) {
/* 42 */     this.context = context;
/*    */     
/* 44 */     this.ieMailAddress = new TextInputElement(context.createID());
/* 45 */     addElement((HtmlElement)this.ieMailAddress);
/*    */     
/* 47 */     this.ieOK = new ClickButtonElement(context.createID(), null) {
/*    */         protected String getLabel() {
/* 49 */           return context.getLabel("send");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/* 53 */           String email = (String)LogMailPanel.this.ieMailAddress.getValue();
/* 54 */           if (email != null && email.length() > 0) {
/* 55 */             LogMailPanel.log.debug("trying to send logfile email to " + email);
/*    */             try {
/* 57 */               LogMailer.getInstance().sendLogs(email);
/* 58 */               LogMailPanel.log.debug("...successfully send");
/* 59 */               LogMailPanel.this.statusMessage = context.getMessage("successfully.send.logs");
/*    */             }
/* 61 */             catch (Throwable e) {
/* 62 */               LogMailPanel.log.error("...failed to send - exception:" + e, e);
/* 63 */               LogMailPanel.this.statusMessage = context.getMessage("error.see.log");
/*    */             } 
/*    */           } 
/* 66 */           return null;
/*    */         }
/*    */       };
/* 69 */     addElement((HtmlElement)this.ieOK);
/*    */   }
/*    */ 
/*    */   
/*    */   public static LogMailPanel getInstance(ClientContext context) {
/* 74 */     synchronized (context.getLockObject()) {
/* 75 */       LogMailPanel instance = (LogMailPanel)context.getObject(LogMailPanel.class);
/* 76 */       if (instance == null) {
/* 77 */         instance = new LogMailPanel(context);
/* 78 */         context.storeObject(LogMailPanel.class, instance);
/*    */       } 
/* 80 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 85 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 87 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.log.mailer.dialog.title"));
/* 88 */     StringUtilities.replace(code, "{LABEL_EMAIL}", this.context.getLabel("email.address"));
/* 89 */     StringUtilities.replace(code, "{INPUT_EMAIL}", this.ieMailAddress.getHtmlCode(params));
/* 90 */     StringUtilities.replace(code, "{BUTTON_OK}", this.ieOK.getHtmlCode(params));
/* 91 */     if (this.statusMessage != null) {
/* 92 */       StringUtilities.replace(code, "{STATUS}", this.statusMessage);
/* 93 */       this.statusMessage = null;
/*    */     } else {
/* 95 */       StringUtilities.replace(code, "{STATUS}", "");
/*    */     } 
/*    */     
/* 98 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\sendlog\\ui\LogMailPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */