/*     */ package com.eoos.gm.tis2web.frame.msg.admin.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.Group;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.ui.el.LocaleSelectionElement;
/*     */ import com.eoos.gm.tis2web.frame.msg.util.MessageUtil;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MessageDetailDialog
/*     */   extends DialogBase {
/*  21 */   private static final Logger log = Logger.getLogger(MessageDetailDialog.class);
/*     */   
/*     */   private static String template;
/*     */   
/*     */   private IMessage msg;
/*     */   
/*     */   private ClientContext context;
/*     */   private SelectBoxSelectionElement selectionLocale;
/*     */   private ClickButtonElement buttonClose;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "detailpanel.html", null).toString();
/*  34 */       template = StringUtilities.replace(template, "class=\"msgdetail\"", "");
/*  35 */     } catch (Exception e) {
/*  36 */       log.error("error loading template - error:" + e, e);
/*  37 */       throw new RuntimeException();
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
/*     */   public MessageDetailDialog(final ClientContext context, final Callback callback) {
/*  50 */     super(context);
/*  51 */     this.context = context;
/*     */     
/*  53 */     this.msg = callback.getMessage();
/*  54 */     this.selectionLocale = (SelectBoxSelectionElement)LocaleSelectionElement.create(context, this.msg, new LocaleSelectionElement.Callback()
/*     */         {
/*     */           public Object onChange() {
/*  57 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean mark(Locale locale) {
/*  62 */             return false;
/*     */           }
/*     */           
/*     */           public boolean autoSubmit() {
/*  66 */             return true;
/*     */           }
/*     */         },  this.msg.getSupportedLocales());
/*     */     
/*  70 */     addElement((HtmlElement)this.selectionLocale);
/*  71 */     this.selectionLocale.setValue(this.msg.getSupportedLocales().iterator().next());
/*     */     
/*  73 */     this.buttonClose = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  76 */           return context.getLabel("close");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  80 */           return callback.onClose();
/*     */         }
/*     */       };
/*     */     
/*  84 */     addElement((HtmlElement)this.buttonClose);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getModulesLabel(IMessage msg) {
/*  89 */     return MessageUtil.getModulesLabel(msg.getTargetModules(), this.context.getLocale());
/*     */   } public static interface Callback {
/*     */     IMessage getMessage(); Object onClose(); }
/*     */   private String getGroupsLabel(IMessage msg) {
/*  93 */     if (msg == null || msg.getUserGroups() == null) {
/*  94 */       return "-";
/*     */     }
/*  96 */     StringBuffer ret = new StringBuffer();
/*  97 */     for (Iterator<Group> iter = msg.getUserGroups().iterator(); iter.hasNext(); ) {
/*  98 */       Group group = iter.next();
/*  99 */       ret.append(group.toString().toUpperCase(Locale.ENGLISH));
/* 100 */       ret.append(", ");
/*     */     } 
/* 102 */     if (ret.length() > 0) {
/* 103 */       ret.delete(ret.length() - 2, ret.length());
/*     */     }
/* 105 */     return ret.toString();
/*     */   }
/*     */   
/*     */   private String getUsersLabel(IMessage msg) {
/* 109 */     if (msg == null || msg.getUserIDs() == null) {
/* 110 */       return "-";
/*     */     }
/* 112 */     StringBuffer ret = new StringBuffer();
/* 113 */     for (Iterator<String> iter = msg.getUserIDs().iterator(); iter.hasNext(); ) {
/* 114 */       String userid = iter.next();
/* 115 */       ret.append(userid);
/* 116 */       ret.append(", ");
/*     */     } 
/* 118 */     if (ret.length() > 0) {
/* 119 */       ret.delete(ret.length() - 2, ret.length());
/*     */     }
/* 121 */     return ret.toString();
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/* 125 */     StringBuffer tmp = new StringBuffer(template);
/*     */     
/* 127 */     StringUtilities.replace(tmp, "{LABEL_ID}", this.context.getLabel("identifier"));
/* 128 */     StringUtilities.replace(tmp, "{INPUT_ID}", this.msg.getExternalID());
/*     */     
/* 130 */     StringUtilities.replace(tmp, "{LABEL_TYPE}", this.context.getLabel("type"));
/* 131 */     StringUtilities.replace(tmp, "{SELECTION_TYPE}", MessageUtil.isInfo(this.msg) ? this.context.getLabel("info") : this.context.getLabel("warning"));
/*     */     
/* 133 */     StringUtilities.replace(tmp, "{LABEL_APPLICATIONS}", this.context.getLabel("application.s"));
/* 134 */     StringUtilities.replace(tmp, "{SELECTION_APPLICATIONS}", getModulesLabel(this.msg));
/*     */     
/* 136 */     StringUtilities.replace(tmp, "{LABEL_GROUPS}", this.context.getLabel("group.s"));
/* 137 */     StringUtilities.replace(tmp, "{SELECTION_GROUPS}", getGroupsLabel(this.msg));
/*     */     
/* 139 */     StringUtilities.replace(tmp, "{LABEL_USERIDS}", this.context.getLabel("users.s"));
/* 140 */     StringUtilities.replace(tmp, "{INPUT_USERIDS}", getUsersLabel(this.msg));
/*     */     
/* 142 */     StringUtilities.replace(tmp, "{LABEL_LOCALE}", this.context.getLabel("language"));
/* 143 */     StringUtilities.replace(tmp, "{SELECTION_LOCALE}", this.selectionLocale.getHtmlCode(params));
/*     */     
/* 145 */     StringUtilities.replace(tmp, "{SELECTION_DEFAULT_LOCALE}", this.msg.getDefaultLocale().getDisplayLanguage(this.context.getLocale()));
/* 146 */     StringUtilities.replace(tmp, "{LABEL_DEFAULT_LOCALE}", this.context.getLabel("default.locale"));
/*     */     
/* 148 */     Locale locale = (Locale)this.selectionLocale.getValue();
/* 149 */     StringUtilities.replace(tmp, "{LABEL_TITLE}", this.context.getLabel("title"));
/* 150 */     StringUtilities.replace(tmp, "{INPUT_TITLE}", this.msg.getContent(locale).getTitle());
/*     */     
/* 152 */     StringUtilities.replace(tmp, "{LABEL_TEXT}", this.context.getLabel("text"));
/* 153 */     StringUtilities.replace(tmp, "{INPUT_TEXT}", toDisplay(this.msg.getContent(locale).getText()));
/*     */     
/* 155 */     StringUtilities.replace(tmp, "{BUTTON_CLOSE}", this.buttonClose.getHtmlCode(params));
/* 156 */     StringUtilities.replace(tmp, "{BUTTON_ACTION}", "");
/*     */     
/* 158 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   private String toDisplay(String text) {
/* 162 */     String ret = text;
/* 163 */     if (ret != null) {
/* 164 */       ret = StringUtilities.replace(ret, "\r\n", "<br/>");
/* 165 */       ret = StringUtilities.replace(ret, "\n", "<br/>");
/*     */     } 
/* 167 */     return ret;
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 171 */     return this.context.getLabel("message.details");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\MessageDetailDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */