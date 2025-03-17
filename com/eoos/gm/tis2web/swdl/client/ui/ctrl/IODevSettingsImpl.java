/*    */ package com.eoos.gm.tis2web.swdl.client.ui.ctrl;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.util.Hashtable;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IODevSettingsImpl
/*    */   implements IOSettings
/*    */ {
/* 20 */   private Logger log = Logger.getLogger(IODevSettingsImpl.class);
/* 21 */   private IOSettingsCtrl ctrlSettings = new IOSettingsCtrl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(String file, Hashtable settings) {
/*    */     try {
/* 39 */       String path = System.getProperties().get("user.home") + File.separator + "swdl" + File.separator + file;
/* 40 */       FileInputStream is = new FileInputStream(path);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 45 */       settings.putAll(this.ctrlSettings.loadSettings(is));
/* 46 */     } catch (Exception e) {
/* 47 */       this.log.error("Exception when load settings from file: " + file + ". " + e, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void save(String file, Hashtable settings) {
/*    */     try {
/* 55 */       String path = System.getProperties().get("user.home") + File.separator + "swdl";
/* 56 */       File dirSwdl = new File(path);
/* 57 */       if (!dirSwdl.exists())
/* 58 */         dirSwdl.mkdirs(); 
/* 59 */       path = path + File.separator + file;
/* 60 */       FileOutputStream os = new FileOutputStream(path);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 69 */       this.ctrlSettings.saveSettings(os, settings);
/* 70 */     } catch (Exception e) {
/* 71 */       this.log.error("Exception when save settings in file: " + file + ". " + e, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\ctrl\IODevSettingsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */