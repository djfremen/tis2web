/*     */ package com.eoos.gm.tis2web.frame.dialog.plugincheck;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.permission.ModuleAccessPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.HtmlInputElementBase;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PluginCheckDialog
/*     */   extends Page
/*     */ {
/*  34 */   private static Logger log = Logger.getLogger(PluginCheckDialog.class);
/*     */   
/*  36 */   private static List pluginList = null;
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  41 */       StringBuffer temp = ApplicationContext.getInstance().loadFile(PluginCheckDialog.class, "plugincheck.html", null);
/*     */ 
/*     */       
/*  44 */       pluginList = Plugin.getPlugins();
/*     */       
/*  46 */       Set<String> includeFiles = new HashSet();
/*  47 */       for (Iterator<Plugin> iter = pluginList.iterator(); iter.hasNext(); ) {
/*  48 */         Plugin plugin = iter.next();
/*  49 */         includeFiles.add(plugin.getJavascriptFile());
/*  50 */         StringUtilities.replace(temp, "{FUNCTIONNAMES}", "\"" + plugin.getCheckFunctionName() + "()\",{FUNCTIONNAMES}");
/*     */       } 
/*  52 */       StringUtilities.replace(temp, ",{FUNCTIONNAMES}", "");
/*  53 */       StringUtilities.replace(temp, "{FUNCTIONNAMES}", "");
/*     */       
/*  55 */       StringBuffer includeScript = new StringBuffer();
/*  56 */       for (Iterator<String> iterator = includeFiles.iterator(); iterator.hasNext(); ) {
/*  57 */         String fileName = iterator.next();
/*  58 */         byte[] data = ApplicationContext.getInstance().loadResource("frame/" + fileName);
/*  59 */         includeScript.append(new String(data));
/*     */       } 
/*  61 */       StringUtilities.replace(temp, "{INCLUDED_SCRIPT}", includeScript.toString());
/*  62 */       template = temp.toString();
/*     */     }
/*  64 */     catch (Exception e) {
/*  65 */       log.error("unable to load template - error:" + e, e);
/*  66 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */   
/*  70 */   private int status = 0;
/*     */   
/*     */   private ClickButtonElement beSkip;
/*     */ 
/*     */   
/*     */   public PluginCheckDialog(final ClientContext context) {
/*  76 */     super(context);
/*     */     
/*  78 */     HtmlInputElementBase htmlInputElementBase = new HtmlInputElementBase("pluginstatus")
/*     */       {
/*     */         public boolean clicked() {
/*  81 */           return false;
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  85 */           return null;
/*     */         }
/*     */         
/*     */         public void setValue(Map submitParams) {
/*  89 */           if (submitParams.containsKey(this.parameterName)) {
/*  90 */             PluginCheckDialog.this.status = Integer.valueOf((String)submitParams.get(this.parameterName)).intValue();
/*     */           }
/*     */         }
/*     */         
/*     */         public String getHtmlCode(Map params) {
/*  95 */           return null;
/*     */         }
/*     */       };
/*  98 */     addElement((HtmlElement)htmlInputElementBase);
/*     */     
/* 100 */     this.beSkip = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 103 */           return context.getLabel("skip");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 107 */           return PluginCheckDialog.this.onFinished();
/*     */         }
/*     */       };
/* 110 */     addElement((HtmlElement)this.beSkip);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFormContent(Map params) {
/* 117 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 119 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.plugin.check.notification"));
/* 120 */     StringUtilities.replace(code, "{BUTTON_SKIP}", this.beSkip.getHtmlCode(params));
/* 121 */     return code.toString();
/*     */   }
/*     */   
/*     */   private List getAvailableModuleList() {
/* 125 */     List<String> retValue = null;
/*     */     try {
/* 127 */       retValue = new LinkedList();
/* 128 */       for (Iterator<VisualModule> iter = ConfiguredServiceProvider.getInstance().getServices(VisualModule.class).iterator(); iter.hasNext(); ) {
/* 129 */         VisualModule module = iter.next();
/* 130 */         if (ModuleAccessPermission.getInstance((ClientContext)this.context).check((Module)module)) {
/* 131 */           retValue.add(module.getType());
/*     */         }
/*     */       } 
/* 134 */     } catch (Exception e) {
/* 135 */       log.error("unable to determine module list -error:" + e, e);
/* 136 */       retValue = new ArrayList<String>();
/*     */     } 
/* 138 */     return retValue;
/*     */   }
/*     */   
/*     */   private Collection getInstalledPlugins() {
/* 142 */     Collection retValue = new HashSet();
/* 143 */     if (this.status != -1) {
/* 144 */       for (int i = 0; i < pluginList.size(); i++) {
/* 145 */         int mask = 1 << i;
/* 146 */         if ((this.status & mask) == mask) {
/* 147 */           retValue.add(pluginList.get(i));
/*     */         }
/*     */       } 
/*     */     }
/* 151 */     return retValue;
/*     */   }
/*     */   
/*     */   private Collection determinePluginsFor(String moduleType) {
/* 155 */     Collection<Plugin> retValue = new HashSet();
/* 156 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component." + moduleType + ".plugin.");
/* 157 */     for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 158 */       String key = iter.next();
/* 159 */       String pluginID = subConfigurationWrapper.getProperty(key);
/* 160 */       for (Iterator<Plugin> iterPlugins = pluginList.iterator(); iterPlugins.hasNext(); ) {
/* 161 */         Plugin plugin = iterPlugins.next();
/* 162 */         if (plugin.getID().equalsIgnoreCase(pluginID)) {
/* 163 */           retValue.add(plugin);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     return retValue;
/*     */   }
/*     */   
/*     */   protected Object onUnhandledSubmit(Map params) {
/* 172 */     HashSet neededPlugins = new HashSet();
/* 173 */     for (Iterator<String> iter = getAvailableModuleList().iterator(); iter.hasNext();) {
/* 174 */       neededPlugins.addAll(determinePluginsFor(iter.next()));
/*     */     }
/* 176 */     log.debug("needed plugins (based on installed and accessable modules and mapping in configuration): " + neededPlugins);
/*     */     
/* 178 */     Collection<?> installedPlugins = getInstalledPlugins();
/* 179 */     log.debug("installed plugins: " + installedPlugins);
/*     */     
/* 181 */     neededPlugins.removeAll(installedPlugins);
/*     */     
/* 183 */     if (neededPlugins.size() == 0) {
/* 184 */       return onFinished();
/*     */     }
/* 186 */     return new PluginInstallDialog((ClientContext)this.context, neededPlugins, (this.status == -1))
/*     */       {
/*     */         protected ResultObject onFinished() {
/* 189 */           return PluginCheckDialog.this.onFinished();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected abstract ResultObject onFinished();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dialog\plugincheck\PluginCheckDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */