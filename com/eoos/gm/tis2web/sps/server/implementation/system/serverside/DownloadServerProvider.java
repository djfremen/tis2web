/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.DownloadServer;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.util.ConfigurationUtil;
/*    */ import java.net.URL;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class DownloadServerProvider
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(DownloadServerProvider.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DownloadServer getServer(String id) {
/*    */     try {
/* 20 */       URL url = new URL(ConfigurationServiceProvider.getService().getProperty("component.sps.download.site." + id + ".url"));
/* 21 */       boolean secured = ConfigurationUtil.getBoolean("component.sps.download.site." + id + ".secured", (Configuration)ConfigurationServiceProvider.getService()).booleanValue();
/*    */       
/* 23 */       return new DownloadServer(url, secured);
/* 24 */     } catch (Exception e) {
/* 25 */       log.error("unable to provide download server " + id + ", returning null - exception: " + e);
/* 26 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\DownloadServerProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */