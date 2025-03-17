/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.system.context.ModuleContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.vcr.VCRUtil;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.bookmarks.BookmarkPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.FaultDiagPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.home.HomePanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.numbersearch.NumberSearchPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.SpecialBrochuresPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.stdinfo.StandardInfoPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.TextSearchPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.BulletinSearchPanel;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.html.element.input.UnhandledSubmit;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MainHook
/*     */   extends HtmlElementHook
/*     */   implements UnhandledSubmit
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(MainHook.class);
/*     */   
/*  38 */   private static final Logger siLog = Logger.getLogger("sistatistics.log");
/*     */   
/*     */   public static final int HOME = 0;
/*     */   
/*     */   public static final int STANDARD_INFO = 1;
/*     */   
/*     */   public static final int TEXT_SEARCH = 2;
/*     */   
/*     */   public static final int NUMBER_SEARCH = 3;
/*     */   
/*     */   public static final int BOOKMARK_LIST = 4;
/*     */   
/*     */   public static final int BULLETIN_SEARCH = 5;
/*     */   
/*     */   public static final int SPECIAL_BROCHURES = 6;
/*     */   
/*     */   public static final int FAULT_DIAG = 7;
/*     */   
/*  56 */   private int ui = 0;
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */   
/*     */   private MainHook(final ClientContext context) {
/*  62 */     this.context = context;
/*  63 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/*  66 */             if ((MainHook.this.ui == 1 || MainHook.this.ui == 7) && !VCRUtil.checkMandatory(VCFacade.getInstance(context).getCfg())) {
/*  67 */               MainHook.this.switchUI(0);
/*     */             } else {
/*  69 */               MainHook.this.writeLogSIStatistics();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  74 */     ModuleContext.getInstance(context.getSessionID()).setPageIdentifier("SI_MAIN");
/*     */   }
/*     */ 
/*     */   
/*     */   public static MainHook getInstance(ClientContext context) {
/*  79 */     synchronized (context.getLockObject()) {
/*  80 */       MainHook instance = (MainHook)context.getObject(MainHook.class);
/*  81 */       if (instance == null) {
/*  82 */         instance = new MainHook(context);
/*  83 */         context.storeObject(MainHook.class, instance);
/*     */       } 
/*  85 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected HtmlElement getActiveElement() {
/*  90 */     switch (this.ui) {
/*     */       case 0:
/*  92 */         return (HtmlElement)HomePanel.getInstance(this.context);
/*     */       case 1:
/*  94 */         return (HtmlElement)StandardInfoPanel.getInstance(this.context);
/*     */       case 2:
/*  96 */         return (HtmlElement)TextSearchPanel.getInstance(this.context);
/*     */       case 3:
/*  98 */         return (HtmlElement)NumberSearchPanel.getInstance(this.context);
/*     */       case 4:
/* 100 */         return (HtmlElement)BookmarkPanel.getInstance(this.context);
/*     */       case 5:
/* 102 */         return (HtmlElement)BulletinSearchPanel.getInstance(this.context);
/*     */       case 6:
/* 104 */         return (HtmlElement)SpecialBrochuresPanel.getInstance(this.context);
/*     */       case 7:
/* 106 */         return (HtmlElement)FaultDiagPanel.getInstance(this.context);
/*     */     } 
/* 108 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentUI() {
/* 113 */     return this.ui;
/*     */   }
/*     */   
/*     */   public void switchUI(int ui) {
/* 117 */     switchUI(ui, true);
/*     */   }
/*     */   
/*     */   public void switchUI(int ui, boolean check) {
/* 121 */     int oldUI = this.ui;
/*     */     try {
/* 123 */       this.ui = ui;
/* 124 */       if (check) {
/* 125 */         getActiveElement();
/*     */       }
/* 127 */     } catch (Throwable e) {
/* 128 */       log.error("unable to switch ui - error:" + e, e);
/* 129 */       this.ui = oldUI;
/*     */     } 
/*     */ 
/*     */     
/* 133 */     String pageid = null;
/* 134 */     switch (this.ui) {
/*     */       case 0:
/* 136 */         pageid = "SI_MAIN";
/*     */         break;
/*     */       case 1:
/* 139 */         pageid = "SI_DISPLAY";
/*     */         break;
/*     */       case 2:
/* 142 */         pageid = "SI_TEXT_SEARCH";
/*     */         break;
/*     */       case 3:
/* 145 */         pageid = "SI_NUMBER_SEARCH";
/*     */         break;
/*     */       case 4:
/* 148 */         pageid = "SI_BOOKMARK";
/*     */         break;
/*     */       case 5:
/* 151 */         pageid = "SI_BULLETINS";
/*     */         break;
/*     */       case 6:
/* 154 */         pageid = "SI_SPECIAL_BROCHURES";
/*     */         break;
/*     */       case 7:
/* 157 */         pageid = "SI_FAULT_DIAG";
/*     */         break;
/*     */     } 
/*     */     
/* 161 */     ModuleContext.getInstance(this.context.getSessionID()).setPageIdentifier(pageid);
/* 162 */     writeLogSIStatistics();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object handleSubmit(Map params) {
/* 173 */     HtmlElement element = getActiveElement();
/* 174 */     if (element instanceof UnhandledSubmit) {
/* 175 */       return ((UnhandledSubmit)element).handleSubmit(params);
/*     */     }
/* 177 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeLogSIStatistics() {
/*     */     try {
/* 183 */       if (ConfigurationUtil.isTrue("component.si.statistics.activated", (Configuration)ConfigurationServiceProvider.getService())) {
/* 184 */         String vehicle = VCFacade.getInstance(this.context).getCurrentVCDisplay(false);
/* 185 */         siLog.info(this.context.getSessionID() + "/ " + this.context.getLocale() + " " + ModuleContext.getInstance(this.context.getSessionID()).getPageIdentifier() + " " + vehicle + "' ");
/*     */       } 
/* 187 */     } catch (Exception ex) {
/* 188 */       log.error("unable to write service information statistics - exception: " + ex, ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\main\MainHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */