/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.AssemblyGroup;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_AssemblyGroup;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_Symptom;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_TroubleCode;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.NodeChangeListener;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.document.BulletinDisplayPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.input.BulletinSearchInputPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.result.TSBResultPanel;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BulletinSearchPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(BulletinSearchPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  45 */       template = ApplicationContext.getInstance().loadFile(BulletinSearchPanel.class, "bulletinsearchpanel.html", null).toString();
/*  46 */     } catch (Exception e) {
/*  47 */       log.error("unable to load template - error:" + e, e);
/*  48 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int VIEW_SEARCHINPUT = 0;
/*     */   public static final int VIEW_RESULTLIST = 1;
/*     */   public static final int VIEW_DOCUMENT = 2;
/*     */   protected MyHook hook;
/*     */   protected ClientContext context;
/*  58 */   protected int view = 0;
/*     */   protected ResultListLink ieResultListLink;
/*     */   private ModuleMenu moduleMenu;
/*     */   
/*     */   private class MyHook
/*     */     extends HtmlElementHook {
/*  64 */     protected TSBResultPanel resultPanel = null;
/*     */     protected BulletinSearchInputPanel inputPanel;
/*  66 */     protected BulletinDisplayPanel displayPanel = null;
/*     */     
/*     */     protected MyHook() {
/*  69 */       this.inputPanel = new BulletinSearchInputPanel(BulletinSearchPanel.this.context);
/*  70 */       this.displayPanel = new BulletinDisplayPanel(BulletinSearchPanel.this.context);
/*     */     }
/*     */     
/*     */     protected HtmlElement getActiveElement() {
/*  74 */       switch (BulletinSearchPanel.this.view) {
/*     */         case 0:
/*  76 */           return (HtmlElement)this.inputPanel;
/*     */         case 1:
/*  78 */           return (HtmlElement)this.resultPanel;
/*     */         case 2:
/*  80 */           return (HtmlElement)this.displayPanel;
/*     */       } 
/*  82 */       throw new IllegalStateException();
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
/*     */   private BulletinSearchPanel(final ClientContext context) {
/*  97 */     this.context = context;
/*  98 */     Util.executeAsynchronous(new Callable()
/*     */         {
/*     */           public Object call() throws Exception {
/* 101 */             SIDataAdapterFacade.getInstance(context).getSI().provideTSBs();
/* 102 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 106 */     this.hook = new MyHook();
/* 107 */     addElement((HtmlElement)this.hook);
/*     */     
/* 109 */     final DocumentPage page = this.hook.displayPanel.getDocumentPage();
/*     */     
/* 111 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIO getSIO() {
/* 113 */           if (BulletinSearchPanel.this.view == 2) {
/*     */             try {
/* 115 */               return page.getSIO();
/* 116 */             } catch (NullPointerException e) {}
/*     */           }
/*     */           
/* 119 */           return null;
/*     */         }
/*     */         
/*     */         public boolean enableBack() {
/* 123 */           return (BulletinSearchPanel.this.view == 2 || BulletinSearchPanel.this.view == 1);
/*     */         }
/*     */         
/*     */         public void back(Map params) {
/* 127 */           if (BulletinSearchPanel.this.view == 2 && BulletinSearchPanel.this.hook.resultPanel != null) {
/* 128 */             BulletinSearchPanel.this.view = 1;
/*     */           } else {
/* 130 */             BulletinSearchPanel.this.view = 0;
/*     */           } 
/*     */         }
/*     */         
/*     */         public String getPageCode() {
/* 135 */           return page.getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/* 139 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void toggleNavigation() {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void registerNodeChangeListener(NodeChangeListener listener) {}
/*     */       };
/* 151 */     this.moduleMenu = new ModuleMenu(context, connector) {
/*     */         protected void hookOnClick(HtmlElement element) {
/* 153 */           if (element == this.linkBulletins) {
/* 154 */             BulletinSearchPanel.this.view = 0;
/*     */           }
/*     */         }
/*     */       };
/* 158 */     addElement((HtmlElement)this.moduleMenu);
/*     */ 
/*     */     
/* 161 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 164 */             AssemblyGroup.reset();
/* 165 */             BulletinSearchPanel.this.switchView(0, (Object[])null);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BulletinSearchPanel getInstance(ClientContext context) {
/* 173 */     synchronized (context.getLockObject()) {
/* 174 */       BulletinSearchPanel instance = (BulletinSearchPanel)context.getObject(BulletinSearchPanel.class);
/* 175 */       if (instance == null) {
/* 176 */         instance = new BulletinSearchPanel(context);
/* 177 */         context.storeObject(BulletinSearchPanel.class, instance);
/*     */       } 
/* 179 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 184 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 186 */     StringUtilities.replace(code, "{MODULE_MENU}", this.moduleMenu.getHtmlCode(params));
/*     */     
/*     */     try {
/* 189 */       StringUtilities.replace(code, "{ACTIVE_ELEMENT}", this.hook.getHtmlCode(params));
/* 190 */     } catch (Exception e) {
/* 191 */       StringUtilities.replace(code, "{ACTIVE_ELEMENT}", "TSB Search not implemented.");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     return code.toString();
/*     */   }
/*     */   
/*     */   public void switchView(int view, Object[] arg) {
/* 204 */     switch (view) {
/*     */       case 0:
/* 206 */         this.view = view;
/*     */         return;
/*     */       
/*     */       case 1:
/*     */         try {
/* 211 */           this.hook.resultPanel = new TSBResultPanel(this.context, (List)arg[0], (TSBFilter_AssemblyGroup)arg[1], (TSBFilter_Symptom)arg[2], (TSBFilter_TroubleCode)arg[3], (Integer)arg[4]);
/* 212 */           this.view = view;
/* 213 */         } catch (Exception e) {
/* 214 */           log.error("unable to switch view - error:" + e, e);
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 2:
/*     */         try {
/* 220 */           Boolean resetResultPanel = (Boolean)arg[1];
/* 221 */           if (resetResultPanel.booleanValue()) {
/* 222 */             this.hook.resultPanel = null;
/*     */           }
/* 224 */           this.hook.displayPanel.setPage((SIOTSB)arg[0]);
/* 225 */           this.view = view;
/* 226 */         } catch (Exception e) {
/* 227 */           log.error("unable to switch view - error:" + e, e);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 233 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentPage getDocumentPage() {
/* 239 */     return this.hook.displayPanel.getDocumentPage();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\BulletinSearchPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */