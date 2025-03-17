/*    */ package com.eoos.gm.tis2web.frame.dialog.plugincheck;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Plugin
/*    */ {
/*    */   private String id;
/*    */   private String displayName;
/*    */   private String instExeName;
/*    */   private String descriptionKey;
/*    */   private String javascriptFile;
/*    */   private String checkFunctionName;
/* 28 */   private static List pluginList = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized List getPlugins() {
/* 35 */     if (pluginList == null) {
/* 36 */       Collection<Plugin> plugins = new HashSet();
/* 37 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.plugin.id.");
/* 38 */       for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 39 */         String pluginID = subConfigurationWrapper.getProperty(iter.next());
/*    */         
/* 41 */         SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.plugin." + pluginID + ".");
/* 42 */         Plugin plugin = new Plugin();
/* 43 */         plugin.id = pluginID;
/* 44 */         plugin.displayName = subConfigurationWrapper1.getProperty("name.display");
/* 45 */         plugin.descriptionKey = subConfigurationWrapper1.getProperty("description.key");
/* 46 */         plugin.instExeName = subConfigurationWrapper1.getProperty("install.file.name");
/* 47 */         plugin.javascriptFile = subConfigurationWrapper1.getProperty("check.js.file");
/* 48 */         plugin.checkFunctionName = subConfigurationWrapper1.getProperty("check.js.function");
/* 49 */         plugins.add(plugin);
/*    */       } 
/* 51 */       pluginList = new ArrayList<Plugin>(plugins);
/*    */     } 
/* 53 */     return pluginList;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 57 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getDisplayName() {
/* 61 */     return this.displayName;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale locale) {
/* 65 */     return ApplicationContext.getInstance().getMessage(locale, this.descriptionKey);
/*    */   }
/*    */   
/*    */   public String getInstallationExecutableName() {
/* 69 */     return this.instExeName.toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */   
/*    */   public String getJavascriptFile() {
/* 73 */     return this.javascriptFile;
/*    */   }
/*    */   
/*    */   public String getCheckFunctionName() {
/* 77 */     return this.checkFunctionName;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 81 */     boolean retValue = false;
/* 82 */     if (this == obj) {
/* 83 */       retValue = true;
/* 84 */     } else if (obj instanceof Plugin) {
/* 85 */       Plugin plugin = (Plugin)obj;
/* 86 */       retValue = this.id.equalsIgnoreCase(plugin.id);
/*    */     } 
/* 88 */     return retValue;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 92 */     return this.id.hashCode() + 39;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 96 */     return "Plugin[id:" + this.id + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dialog\plugincheck\Plugin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */