/*     */ package com.eoos.gm.tis2web.frame.scout.standalone;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.DefaultController;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.StorageService;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LoginDialog
/*     */   extends DialogBase
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(LoginDialog.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  31 */       template = ApplicationContext.getInstance().loadFile(LoginDialog.class, "logindialog.html", null).toString();
/*  32 */     } catch (Exception e) {
/*  33 */       throw new RuntimeException("unable to load template", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClickButtonElement buttonOK;
/*     */   
/*     */   private SelectBoxSelectionElement selectionUser;
/*  41 */   private static final Object NO_SELECTION = new Object();
/*     */   
/*     */   private TextInputElement inputUser;
/*     */   
/*     */   public LoginDialog(final ClientContext context) {
/*  46 */     super(context);
/*  47 */     this.inputUser = new TextInputElement(context.createID(), 20, -1);
/*  48 */     addElement((HtmlElement)this.inputUser);
/*     */     
/*  50 */     this.buttonOK = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  53 */           return context.getLabel("ok");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  57 */           return LoginDialog.this.onOK(submitParams);
/*     */         }
/*     */         
/*     */         protected boolean isSubmitButton() {
/*  61 */           return true;
/*     */         }
/*     */       };
/*     */     
/*  65 */     addElement((HtmlElement)this.buttonOK);
/*     */     
/*  67 */     Set<?> users = getUserNames();
/*     */     
/*  69 */     final List<Comparable> userList = new ArrayList(users);
/*  70 */     Collections.sort(userList);
/*  71 */     userList.add(0, NO_SELECTION);
/*     */     
/*  73 */     DataRetrievalAbstraction.DataCallback datacallback = new DataRetrievalAbstraction.DataCallback()
/*     */       {
/*     */         public List getData() {
/*  76 */           return userList;
/*     */         }
/*     */       };
/*     */     
/*  80 */     this.selectionUser = new SelectBoxSelectionElement(context.createID(), true, datacallback, 1) {
/*     */         protected String getDisplayValue(Object option) {
/*  82 */           if (option == LoginDialog.NO_SELECTION) {
/*  83 */             return context.getLabel("no.selection");
/*     */           }
/*  85 */           return option.toString();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  90 */     addElement((HtmlElement)this.selectionUser);
/*     */   }
/*     */   
/*     */   private Set getUserNames() {
/*  94 */     StorageService storage = (StorageService)FrameServiceProvider.getInstance().getService(StorageService.class);
/*  95 */     StorageService.ObjectStore os = storage.getObjectStoreFacade();
/*  96 */     Set users = null;
/*     */     try {
/*  98 */       users = (Set)os.load("stdalone.login.dialog.user.list");
/*  99 */     } catch (Exception e) {
/* 100 */       log.error("unable to load user list, using empty list - exception: " + e, e);
/*     */     } 
/* 102 */     return (users != null) ? users : new HashSet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set addUserName(String username) throws Exception {
/* 108 */     StorageService storage = (StorageService)FrameServiceProvider.getInstance().getService(StorageService.class);
/* 109 */     StorageService.ObjectStore os = storage.getObjectStoreFacade();
/* 110 */     Set<String> users = getUserNames();
/* 111 */     users.add(username);
/*     */     
/* 113 */     os.store("stdalone.login.dialog.user.list", users);
/*     */     
/* 115 */     return users;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getContent(Map params) {
/* 120 */     StringBuffer tmp = new StringBuffer(template);
/* 121 */     StringUtilities.replace(tmp, "{SELECTION_USER}", this.selectionUser.getHtmlCode(params));
/* 122 */     StringUtilities.replace(tmp, "{INPUT_USER}", this.inputUser.getHtmlCode(params));
/* 123 */     StringUtilities.replace(tmp, "{MSG_1}", this.context.getMessage("select.username"));
/* 124 */     StringUtilities.replace(tmp, "{MSG_2}", this.context.getMessage("or.enter.new.username"));
/* 125 */     StringUtilities.replace(tmp, "{BUTTON_OK}", this.buttonOK.getHtmlCode(params));
/* 126 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 130 */     return getContext().getMessage("please.select.user.name");
/*     */   }
/*     */   
/*     */   protected Object onUnhandledSubmit(Map params) {
/* 134 */     return onOK(params);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object onOK(Map<String, String> params) {
/* 139 */     String user = null;
/* 140 */     Object selection = this.selectionUser.getValue();
/* 141 */     if (selection != NO_SELECTION) {
/* 142 */       user = (String)selection;
/*     */     } else {
/* 144 */       user = (String)this.inputUser.getValue();
/* 145 */       if (user != null && user.trim().length() > 0) {
/*     */         try {
/* 147 */           addUserName(user);
/* 148 */         } catch (Exception e) {
/* 149 */           log.error("uable to add user name - exception: " + e, e);
/*     */         } 
/*     */       }
/*     */     } 
/* 153 */     params.put("user", user);
/*     */     
/* 155 */     return DefaultController.getInstance().init(params);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\standalone\LoginDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */