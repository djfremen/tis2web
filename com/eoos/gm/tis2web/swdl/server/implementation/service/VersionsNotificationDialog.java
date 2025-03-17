/*     */ package com.eoos.gm.tis2web.swdl.server.implementation.service;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.Version;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.CheckBoxElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.renderer.HtmlTableRenderer;
/*     */ import com.eoos.html.renderer.HtmlTagRenderer;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class VersionsNotificationDialog
/*     */   extends Page
/*     */ {
/*  29 */   private static final String IDS = VersionsNotificationDialog.class.getName();
/*     */   
/*  31 */   private static final Object KEY_NOTIFICATION_FLAG = "swdl.notify.keep.on";
/*     */   
/*  33 */   private static Logger log = Logger.getLogger(IDS);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  38 */       template = ApplicationContext.getInstance().loadFile(VersionsNotificationDialog.class, "versionsnotificationdialog.html", null).toString();
/*  39 */     } catch (Exception e) {
/*  40 */       log.error("unable to load template - error:" + e, e);
/*  41 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClickButtonElement ieOK;
/*     */   
/*     */   protected CheckBoxElement ieStopNotification;
/*     */   
/*     */   protected List versions;
/*     */   
/*     */   private DateFormat dateFormat;
/*     */   
/*     */   public VersionsNotificationDialog(final ClientContext context, Set<?> versions) {
/*  55 */     super(context);
/*  56 */     this.versions = new ArrayList(versions);
/*  57 */     this.dateFormat = DateFormat.getDateInstance(3, context.getLocale());
/*  58 */     this.ieOK = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  60 */           return context.getLabel("ok");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  65 */             SharedContext.getInstance(context).setPersistentObject(VersionsNotificationDialog.KEY_NOTIFICATION_FLAG, VersionsNotificationDialog.this.ieStopNotification.getValue());
/*  66 */           } catch (Exception e) {
/*  67 */             VersionsNotificationDialog.log.error("unable to store persistent value, ignoring - exception:" + e, e);
/*     */           } 
/*  69 */           return VersionsNotificationDialog.this.onClose(submitParams);
/*     */         }
/*     */       };
/*     */     
/*  73 */     addElement((HtmlElement)this.ieOK);
/*     */     
/*  75 */     this.ieStopNotification = new CheckBoxElement(context.createID());
/*  76 */     addElement((HtmlElement)this.ieStopNotification);
/*     */     
/*  78 */     Boolean value = null;
/*     */     try {
/*  80 */       value = (Boolean)SharedContext.getInstance(context).getPersistentObject(KEY_NOTIFICATION_FLAG);
/*  81 */     } catch (Exception e) {
/*  82 */       log.error("unable to access persistent value, ignoring - exception:" + e, e);
/*     */     } 
/*     */     
/*  85 */     this.ieStopNotification.setValue((value != null) ? value : Boolean.TRUE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean keepNotifying() {
/*  90 */     return ((Boolean)this.ieStopNotification.getValue()).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFormContent(Map params) {
/*  97 */     StringBuffer code = new StringBuffer(template);
/*  98 */     HtmlTableRenderer.CallbackAdapter callbackAdapter = new HtmlTableRenderer.CallbackAdapter()
/*     */       {
/*     */         public int getRowCount() {
/* 101 */           return VersionsNotificationDialog.this.versions.size() + 1;
/*     */         }
/*     */         
/*     */         public String getContent(int rowIndex, int columnIndex) {
/* 105 */           String ret = "&nbsp;";
/*     */           try {
/* 107 */             if (rowIndex == 0) {
/* 108 */               if (columnIndex == 1) {
/* 109 */                 ret = VersionsNotificationDialog.this.context.getLabel("version");
/* 110 */               } else if (columnIndex == 2) {
/* 111 */                 ret = VersionsNotificationDialog.this.context.getLabel("release.date");
/*     */               } 
/*     */             } else {
/* 114 */               Version version = VersionsNotificationDialog.this.versions.get(rowIndex - 1);
/* 115 */               if (columnIndex == 0) {
/* 116 */                 ret = version.getApplication().getDescription();
/* 117 */               } else if (columnIndex == 1) {
/* 118 */                 ret = version.getNumber();
/* 119 */               } else if (columnIndex == 2) {
/* 120 */                 ret = VersionsNotificationDialog.this.dateFormat.format(new Date(version.getDate().longValue()));
/*     */               } 
/*     */             } 
/* 123 */           } catch (Exception e) {
/* 124 */             VersionsNotificationDialog.log.error("unable to provide display information, returning \"-\" - exception:" + e, e);
/* 125 */             ret = "-";
/*     */           } 
/* 127 */           return ret;
/*     */         }
/*     */         
/*     */         public int getColumnCount() {
/* 131 */           return 3;
/*     */         }
/*     */         
/*     */         public boolean isHeader(int rowIndex, int columnIndex) {
/* 135 */           return (rowIndex == 0);
/*     */         }
/*     */         
/* 138 */         private final HtmlTagRenderer.AdditionalAttributes ATTR_ALIGN_RIGHT = new HtmlTagRenderer.AdditionalAttributes()
/*     */           {
/*     */             public void getAdditionalAttributes(Map<String, String> map) {
/* 141 */               map.put("align", "right");
/*     */             }
/*     */           };
/*     */ 
/*     */         
/*     */         public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesCell(int rowIndex, int columnIndex) {
/* 147 */           if (columnIndex == 2) {
/* 148 */             return this.ATTR_ALIGN_RIGHT;
/*     */           }
/* 150 */           return null;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 155 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("swdl.notification.new.tech2.versions"));
/* 156 */     StringUtilities.replace(code, "{VERSIONTABLE}", HtmlTableRenderer.getInstance().getHtmlCode((HtmlTableRenderer.Callback)callbackAdapter));
/* 157 */     StringUtilities.replace(code, "{CHECKBOX}", this.ieStopNotification.getHtmlCode(params));
/* 158 */     StringUtilities.replace(code, "{MSG_KEEP_NOTIFYING}", this.context.getMessage("swdl.notification.new.tech2.versions.keepon"));
/*     */     
/* 160 */     StringUtilities.replace(code, "{BUTTON_OK}", this.ieOK.getHtmlCode(params));
/* 161 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected abstract ResultObject onClose(Map paramMap);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\implementation\service\VersionsNotificationDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */