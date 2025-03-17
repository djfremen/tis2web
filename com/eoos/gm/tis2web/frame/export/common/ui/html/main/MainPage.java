/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.permission.ModuleAccessPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.DTCTestClientLinkElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.DiagToolLinkElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.FeedbackLinkElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.HelpLinkElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.LogoutLinkElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.NewsLinkElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.ProfileLinkElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.html.HtmlCodeRenderer;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MainPage
/*     */   extends Page
/*     */ {
/*     */   protected HtmlElementHook mainHook;
/*     */   private CommonMenuContainer menuContainer;
/*  48 */   protected OnLoadHandler onLoadHandler = null;
/*     */   
/*     */   private static final String template = "<table width=\"100%\"><tr><td>{CONTENT}</td></tr><tr><td>&nbsp;</td></tr><tr><td align=\"left\"><table width=\"90%\"><tr><td>{NAVIGATION_HINT}</td></tr></table></td></tr></table>";
/*     */ 
/*     */   
/*     */   public MainPage(ClientContext context) {
/*  54 */     super(context);
/*     */     
/*  56 */     this.mainHook = createMainHook();
/*  57 */     addElement((HtmlElement)this.mainHook);
/*     */     
/*  59 */     this.menuContainer = new CommonMenuContainer(context, (HtmlCodeRenderer)this.mainHook, createTopLinkElements(), this);
/*  60 */     addElement((HtmlElement)this.menuContainer);
/*     */   }
/*     */ 
/*     */   
/*     */   public HtmlElementHook getHook() {
/*  65 */     return this.mainHook;
/*     */   }
/*     */   
/*     */   protected abstract HtmlElementHook createMainHook();
/*     */   
/*     */   protected VCLinkElement createVCLink() {
/*  71 */     return new VCLinkElement((ClientContext)this.context, new VCLinkElement.Callback() {
/*     */           public Object getReturnUI() {
/*  73 */             return MainPage.this;
/*     */           }
/*     */           
/*     */           public boolean isMandatory(Object key, IConfiguration currentCfg) {
/*  77 */             return false;
/*     */           }
/*     */           
/*     */           public boolean isReadonly(Object key) {
/*  81 */             return false;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected List createTopLinkElements() {
/*  87 */     List<DTCTestClientLinkElement> retValue = new LinkedList();
/*     */     
/*  89 */     if (((ClientContext)this.context).isSpecialAccess()) {
/*  90 */       retValue.add(new DTCTestClientLinkElement((ClientContext)this.context, (HtmlElement)this));
/*     */     }
/*     */     
/*  93 */     if (ModuleAccessPermission.getInstance((ClientContext)this.context).check("diagtool")) {
/*  94 */       retValue.add(new DiagToolLinkElement((ClientContext)this.context, (HtmlElement)this));
/*     */     }
/*  96 */     if (ModuleAccessPermission.getInstance((ClientContext)this.context).check("vc")) {
/*  97 */       retValue.add(createVCLink());
/*     */     }
/*  99 */     if (ModuleAccessPermission.getInstance((ClientContext)this.context).check("profile")) {
/* 100 */       retValue.add(new ProfileLinkElement((ClientContext)this.context, (HtmlElement)this));
/*     */     }
/*     */     
/* 103 */     if (ModuleAccessPermission.getInstance((ClientContext)this.context).check("news")) {
/* 104 */       retValue.add(new NewsLinkElement((ClientContext)this.context) {
/*     */             public Object onClick(Map params) {
/* 106 */               return MainPage.this.onClick_News(params);
/*     */             }
/*     */           });
/*     */     }
/* 110 */     if (ModuleAccessPermission.getInstance((ClientContext)this.context).check("help")) {
/* 111 */       retValue.add(new HelpLinkElement((ClientContext)this.context) {
/*     */             public Object onClick(Map params) {
/* 113 */               return MainPage.this.onClick_Help(params);
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 118 */     if (ModuleAccessPermission.getInstance((ClientContext)this.context).check("feedback")) {
/* 119 */       retValue.add(new FeedbackLinkElement((ClientContext)this.context) {
/*     */             public Object onClick(Map params) {
/* 121 */               Object check = MainPage.this.onClick_Feedback(params);
/* 122 */               if (check == null)
/* 123 */                 check = super.onClick(params); 
/* 124 */               return check;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     retValue.add(new LogoutLinkElement((ClientContext)this.context));
/* 135 */     return retValue;
/*     */   } public static interface OnLoadHandler {
/*     */     String getCode(Map param1Map);
/*     */     boolean isTransient(); }
/*     */   public abstract String getModuleType();
/*     */   protected String getFormContent(Map params) {
/* 141 */     StringBuffer retValue = new StringBuffer();
/* 142 */     retValue.append("<table width=\"100%\"><tr><td>{CONTENT}</td></tr><tr><td>&nbsp;</td></tr><tr><td align=\"left\"><table width=\"90%\"><tr><td>{NAVIGATION_HINT}</td></tr></table></td></tr></table>");
/*     */     
/* 144 */     StringUtilities.replace(retValue, "{CONTENT}", this.menuContainer.getHtmlCode(params));
/*     */     
/* 146 */     if (ConfigurationUtil.hasValue("frame.display.navigation.hint", (Configuration)ApplicationContext.getInstance(), "yes")) {
/* 147 */       StringUtilities.replace(retValue, "{NAVIGATION_HINT}", this.context.getMessage("navigation.hint"));
/*     */     } else {
/* 149 */       StringUtilities.replace(retValue, "{NAVIGATION_HINT}", "");
/*     */     } 
/* 151 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   public Object switchModule(VisualModule module) {
/* 155 */     return this.menuContainer.getModuleSelectionMenu().switchModule(module);
/*     */   }
/*     */   
/*     */   protected boolean switchModuleHook(VisualModule module) {
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   protected String getVehicleDescription() {
/* 163 */     return VCFacade.getInstance(getContext()).getCurrentVCDisplay(true);
/*     */   }
/*     */   
/*     */   protected Object onClick_Help(Map params) {
/* 167 */     return new ResultObject(0, "sorry ... no help available");
/*     */   }
/*     */   
/*     */   protected Object onClick_Feedback(Map params) {
/* 171 */     return new ResultObject(0, "error: feedback module is currently unavailable");
/*     */   }
/*     */   
/*     */   protected Object onClick_News(Map params) {
/* 175 */     return new ResultObject(0, "sorry ... news not avialable");
/*     */   }
/*     */   
/*     */   public void setOnLoadHandler(OnLoadHandler handler) {
/* 179 */     this.onLoadHandler = handler;
/*     */   }
/*     */   
/*     */   protected String getOnLoadHandlerCode(Map params) {
/* 183 */     String retValue = null;
/* 184 */     if (this.onLoadHandler != null) {
/* 185 */       retValue = this.onLoadHandler.getCode(params);
/* 186 */       if (this.onLoadHandler.isTransient()) {
/* 187 */         this.onLoadHandler = null;
/*     */       }
/*     */     } 
/* 190 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */