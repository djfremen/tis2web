/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ICOPClientParams
/*    */ {
/* 14 */   private static ICOPClientParams instance = null;
/* 15 */   private static List<String> serverList = new ArrayList<String>();
/* 16 */   private static final Logger log = Logger.getLogger(ICOPClientParams.class);
/*    */   
/*    */   private ICOPClientParams() {
/* 19 */     init();
/*    */   }
/*    */   
/*    */   protected static synchronized ICOPClientParams getInstance() {
/* 23 */     if (instance == null) {
/* 24 */       instance = new ICOPClientParams();
/*    */     }
/* 26 */     return instance;
/*    */   }
/*    */   
/*    */   protected List<String> getServerList() {
/* 30 */     return serverList;
/*    */   }
/*    */ 
/*    */   
/*    */   private void init() {
/* 35 */     SubConfigurationWrapper nameConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.lt.icop.client.name");
/* 36 */     SubConfigurationWrapper urlConfig = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.lt.icop.client.server");
/* 37 */     if (nameConfig != null) {
/* 38 */       Iterator<String> it = nameConfig.getKeys().iterator();
/* 39 */       while (it.hasNext()) {
/* 40 */         String ndx = it.next();
/* 41 */         String name = nameConfig.getProperty(ndx);
/* 42 */         String urlStr = urlConfig.getProperty(ndx);
/* 43 */         if (name.length() > 0 && urlStr != null) {
/* 44 */           serverList.add(name + "=" + urlStr);
/*    */         }
/*    */       } 
/*    */     } 
/* 48 */     log.info("ICOP server map initialized [" + serverList.size() + " entries]");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\ICOPClientParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */