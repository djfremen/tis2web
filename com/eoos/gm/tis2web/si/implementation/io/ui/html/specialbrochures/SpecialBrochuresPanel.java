/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.ModuleMenu;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr.NodeChangeListener;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.basic.BasicModePanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.extended.ExtendedModePanel;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ public class SpecialBrochuresPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  29 */   private static Logger log = Logger.getLogger(SpecialBrochuresPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  34 */       template = ApplicationContext.getInstance().loadFile(SpecialBrochuresPanel.class, "specialbrochurespanel.html", null).toString();
/*  35 */     } catch (Exception e) {
/*  36 */       log.error("unable to load template - error:" + e, e);
/*  37 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MODE_BASIC = 0;
/*     */   public static final int MODE_EXTENDED = 1;
/*     */   
/*     */   protected class MyHook
/*     */     extends HtmlElementHook
/*     */   {
/*  48 */     protected ExtendedModePanel extendedModePanel = new ExtendedModePanel(SpecialBrochuresPanel.this.context);
/*  49 */     protected BasicModePanel basicModePanel = new BasicModePanel(SpecialBrochuresPanel.this.context);
/*     */ 
/*     */     
/*     */     protected HtmlElement getActiveElement() {
/*  53 */       switch (SpecialBrochuresPanel.this.mode) {
/*     */         case 0:
/*  55 */           return (HtmlElement)this.basicModePanel;
/*     */       } 
/*  57 */       return (HtmlElement)this.extendedModePanel;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   protected int mode = 1;
/*     */   
/*     */   protected ClientContext context;
/*     */   
/*     */   protected ModuleMenu moduleMenu;
/*     */   
/*     */   protected MyHook hook;
/*     */ 
/*     */   
/*     */   private SpecialBrochuresPanel(final ClientContext context) {
/*  76 */     this.context = context;
/*     */     
/*  78 */     this.hook = new MyHook();
/*  79 */     addElement((HtmlElement)this.hook);
/*     */     
/*  81 */     final DocumentPage page = ((DocumentPageRetrieval)this.hook.getActiveElement()).getDocumentPage();
/*     */     
/*  83 */     ModuleMenu.Connector connector = new ModuleMenu.Connector() {
/*     */         public SIO getSIO() {
/*     */           try {
/*  86 */             return SpecialBrochuresContext.getInstance(context).getSelectedSIO();
/*  87 */           } catch (NullPointerException e) {
/*     */             
/*  89 */             return null;
/*     */           } 
/*     */         }
/*     */         public boolean enableBack() {
/*  93 */           SpecialBrochuresContext sbc = SpecialBrochuresContext.getInstance(context);
/*     */           try {
/*  95 */             return (sbc.getPredessor(sbc.getSelectedSIO()) != null);
/*  96 */           } catch (Exception e) {
/*  97 */             SpecialBrochuresPanel.log.warn("unable to decide correct status of back button; disabling - exception:" + e, e);
/*  98 */             return false;
/*     */           } 
/*     */         }
/*     */         
/*     */         public void back(Map params) {
/*     */           try {
/* 104 */             SpecialBrochuresContext sbc = SpecialBrochuresContext.getInstance(context);
/* 105 */             sbc.setSelectedSIO(sbc.getPredessor(sbc.getSelectedSIO()), true);
/* 106 */           } catch (Exception e) {
/* 107 */             SpecialBrochuresPanel.log.error("unable to navigate back - exception:" + e, e);
/*     */           } 
/*     */         }
/*     */         
/*     */         public String getPageCode() {
/* 112 */           return page.getHtmlCode(null);
/*     */         }
/*     */         
/*     */         public boolean enableNavToggle() {
/* 116 */           return true;
/*     */         }
/*     */         
/*     */         public void toggleNavigation() {
/* 120 */           SpecialBrochuresPanel.this.toggleMode();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void registerNodeChangeListener(NodeChangeListener listener) {}
/*     */       };
/* 128 */     this.moduleMenu = new ModuleMenu(context, connector);
/* 129 */     addElement((HtmlElement)this.moduleMenu);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientContext getContext() {
/* 134 */     return this.context;
/*     */   }
/*     */   
/*     */   public static SpecialBrochuresPanel getInstance(ClientContext context) {
/* 138 */     synchronized (context.getLockObject()) {
/* 139 */       SpecialBrochuresPanel instance = (SpecialBrochuresPanel)context.getObject(SpecialBrochuresPanel.class);
/* 140 */       if (instance == null) {
/* 141 */         instance = new SpecialBrochuresPanel(context);
/* 142 */         context.storeObject(SpecialBrochuresPanel.class, instance);
/*     */       } 
/* 144 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 149 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 151 */     StringUtilities.replace(code, "{MODULE_MENU}", this.moduleMenu.getHtmlCode(params));
/* 152 */     StringUtilities.replace(code, "{HOOK}", this.hook.getHtmlCode(params));
/*     */     
/* 154 */     return code.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleMode() {
/* 159 */     if (this.mode == 0) {
/* 160 */       this.mode = 1;
/*     */     } else {
/* 162 */       this.mode = 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\SpecialBrochuresPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */