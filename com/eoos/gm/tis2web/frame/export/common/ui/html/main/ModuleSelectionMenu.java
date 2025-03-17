/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.VisualModuleProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.permission.ModuleAccessPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.menu.MenuRenderer;
/*     */ import com.eoos.gm.tis2web.frame.msg.MsgFacade;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*     */ import com.eoos.gm.tis2web.frame.msg.util.MsgUIDialog;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.GlobalVCDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.GlobalVINResolver;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.input.IDLinkElement;
/*     */ import com.eoos.html.renderer.menu.MenuRenderer;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleSelectionMenu
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   private class MenuRendererCallback
/*     */     implements MenuRenderer.Callback, MenuRenderer.Remainder
/*     */   {
/*     */     private Map params;
/*     */     
/*     */     private MenuRendererCallback() {}
/*     */     
/*     */     public String getCode(Object item) {
/*  52 */       return ((ModuleSelectionMenu.MenuItem)item).getHtmlCode(this.params);
/*     */     }
/*     */     
/*     */     public List getItems() {
/*  56 */       return ModuleSelectionMenu.this.items;
/*     */     }
/*     */     
/*     */     public void init(Map params) {
/*  60 */       this.params = params;
/*     */     }
/*     */     
/*     */     public boolean isActive(Object item) {
/*  64 */       return ((ModuleSelectionMenu.MenuItem)item).module.getType().equals(ModuleSelectionMenu.this.main.getModuleType());
/*     */     }
/*     */     
/*     */     public HtmlElement getRemainder() {
/*  68 */       return ModuleSelectionMenu.this.getRemainder();
/*     */     }
/*     */   }
/*     */   
/*     */   private class MenuItem
/*     */     extends IDLinkElement {
/*     */     public VisualModule module;
/*     */     
/*     */     public MenuItem(VisualModule module) {
/*  77 */       super(ModuleSelectionMenu.this.context.createID(), null, "frame." + module.getType());
/*  78 */       this.module = module;
/*     */     }
/*     */     
/*     */     protected String getLabel() {
/*  82 */       return ModuleSelectionMenu.this.context.getLabel("module.type." + this.module.getType() + ".abbreviation");
/*     */     }
/*     */     
/*     */     public Object onClick(Map submitParams) {
/*  86 */       return ModuleSelectionMenu.this.switchModule(this.module);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  91 */   private static Logger log = Logger.getLogger(ModuleSelectionMenu.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  96 */       template = ApplicationContext.getInstance().loadFile(ModuleSelectionMenu.class, "moduleselection.html", null).toString();
/*  97 */     } catch (Exception e) {
/*  98 */       log.error("unable to load template - error:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/* 104 */   protected List items = new LinkedList();
/*     */   
/*     */   private MenuRendererCallback menuCallback;
/*     */   
/*     */   private MainPage main;
/*     */ 
/*     */   
/*     */   public ModuleSelectionMenu(ClientContext context, MainPage main) {
/* 112 */     this.context = context;
/* 113 */     this.main = main;
/* 114 */     initItems();
/*     */     
/* 116 */     this.menuCallback = new MenuRendererCallback();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initItems() {
/*     */     try {
/* 122 */       for (Iterator<VisualModule> iter = VisualModuleProvider.getInstance(this.context).getVisualModules().iterator(); iter.hasNext(); ) {
/* 123 */         MenuItem item = new MenuItem(iter.next());
/* 124 */         this.items.add(item);
/* 125 */         addElement((HtmlElement)item);
/*     */       } 
/* 127 */     } catch (Exception e) {
/* 128 */       log.error("unable to retrieve list of modules: " + e);
/* 129 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 134 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 136 */     StringUtilities.replace(code, "{URL_SO_LOGO}", "pic/common/so-logo.gif");
/* 137 */     StringUtilities.replace(code, "{TOOLTIP_SO_LOGO}", this.context.getMessage("frame.tooltip.so.logo"));
/* 138 */     StringUtilities.replace(code, "{MENU}", MenuRenderer.getInstance(this.context).getHtmlCode(this.menuCallback, params));
/*     */     
/* 140 */     String vehicle = null;
/* 141 */     if (ModuleAccessPermission.getInstance(this.context).check("vc")) {
/* 142 */       vehicle = this.main.getVehicleDescription();
/*     */     }
/* 144 */     StringUtilities.replace(code, "{VEHICLE}", (vehicle != null) ? vehicle : "&nbsp;");
/* 145 */     return code.toString();
/*     */   }
/*     */   
/*     */   public Object switchModule(final VisualModule module) {
/* 149 */     Object retValue = null;
/*     */     try {
/*     */       try {
/* 152 */         if (this.main.switchModuleHook(module)) {
/*     */           
/* 154 */           Object nextUI = module.getUI(this.context.getSessionID(), null);
/*     */           
/* 156 */           List<IMessage> messages = MsgFacade.getInstance(this.context).getMessages(module.getType());
/* 157 */           for (int i = messages.size(); i > 0; i--) {
/* 158 */             IMessage msg = messages.get(i - 1);
/* 159 */             MsgUIDialog dialog = MsgUIDialog.create(this.context, msg, nextUI);
/* 160 */             nextUI = dialog.getHtmlCode(null);
/*     */           } 
/* 162 */           retValue = nextUI;
/*     */         } else {
/*     */           
/* 165 */           retValue = this.main;
/*     */         } 
/* 167 */       } catch (RuntimeException e) {
/* 168 */         if (e.getCause() instanceof com.eoos.gm.tis2web.vc.v2.UnsupportedVehicleCfgException) {
/* 169 */           return VCFacade.getInstance(this.context).getDialog(new VCService.DialogCallback()
/*     */               {
/*     */                 public Object onClose(boolean cancelled) {
/* 172 */                   if (!cancelled) {
/* 173 */                     return ModuleSelectionMenu.this.switchModule(module);
/*     */                   }
/* 175 */                   return ModuleSelectionMenu.this.main;
/*     */                 }
/*     */                 
/*     */                 public boolean isReadonly(Object key) {
/* 179 */                   return false;
/*     */                 }
/*     */                 
/*     */                 public boolean isMandatory(Object key, IConfiguration currentCfg) {
/* 183 */                   return (VehicleConfigurationUtil.KEY_MAKE == key);
/*     */                 }
/*     */                 
/*     */                 public VINResolver getVINResolver() {
/* 187 */                   return (VINResolver)GlobalVINResolver.getInstance(ModuleSelectionMenu.this.context);
/*     */                 }
/*     */                 
/*     */                 public VehicleCfgStorage getStorage() {
/* 191 */                   return (VehicleCfgStorage)VCServiceProvider.getInstance().getService(ModuleSelectionMenu.this.context);
/*     */                 }
/*     */                 
/*     */                 public CfgDataProvider getDataProvider() {
/* 195 */                   return GlobalVCDataProvider.getInstance(ModuleSelectionMenu.this.context);
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/* 200 */         throw e;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 205 */     catch (Exception e) {
/* 206 */       log.error("unable to switch to module: " + String.valueOf(module) + ", returning current page - exception: " + e, e);
/* 207 */       retValue = this.main;
/*     */     } 
/* 209 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getRemainder() {
/* 214 */     return (HtmlElement)new HtmlLabel("&nbsp;");
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 218 */     System.out.println("mode.develop".hashCode());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\ModuleSelectionMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */