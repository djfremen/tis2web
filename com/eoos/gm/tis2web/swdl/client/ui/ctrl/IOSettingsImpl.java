/*    */ package com.eoos.gm.tis2web.swdl.client.ui.ctrl;
/*    */ 
/*    */ import java.io.InputStream;
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
/*    */ public class IOSettingsImpl
/*    */   implements IOSettings
/*    */ {
/* 17 */   private Logger log = Logger.getLogger(IOSettingsImpl.class);
/* 18 */   private IOSettingsCtrl ctrlSettings = new IOSettingsCtrl();
/* 19 */   private ClassLoader cl = IOSettingsImpl.class.getClassLoader();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(String file, Hashtable settings) {
/*    */     try {
/* 27 */       InputStream is = this.cl.getResourceAsStream(file);
/* 28 */       settings.putAll(this.ctrlSettings.loadSettings(is));
/* 29 */     } catch (Exception e) {
/* 30 */       this.log.error("Exception when load settings from file: " + file + ". " + e, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void save(String file, Hashtable settings) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\ctrl\IOSettingsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */