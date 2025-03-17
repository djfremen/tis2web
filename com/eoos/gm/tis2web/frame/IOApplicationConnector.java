/*    */ package com.eoos.gm.tis2web.frame;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.Dispatcher;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ApplicationConnector;
/*    */ import com.eoos.resource.loading.ResourceLoading;
/*    */ import com.eoos.resource.loading.ServletResourceLoading;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.TimerTask;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IOApplicationConnector
/*    */   implements ApplicationConnector
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(IOApplicationConnector.class);
/*    */ 
/*    */ 
/*    */   
/*    */   private TimerTask memoryMonitor;
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getDefaultTarget() {
/* 30 */     return DefaultController.getInstance();
/*    */   }
/*    */   
/*    */   public void onShutdown() {
/* 34 */     if (this.memoryMonitor != null) {
/* 35 */       log.info("stopping memory monitor");
/* 36 */       this.memoryMonitor.cancel();
/*    */     } 
/*    */     
/* 39 */     ApplicationContext.getInstance().onShutdown();
/*    */   }
/*    */   
/*    */   public synchronized void onStartup(Object object) {
/* 43 */     log.info("starting memory monitor");
/*    */     
/* 45 */     this.memoryMonitor = Util.createTimerTask(Util.createMemoryMonitor(new Util.StringOutput()
/*    */           {
/*    */             public void write(String string) {
/* 48 */               IOApplicationConnector.log.info(string);
/*    */             }
/*    */           }));
/*    */     
/* 52 */     long rate = 1800000L;
/* 53 */     Util.getTimer().scheduleAtFixedRate(this.memoryMonitor, 300000L, rate);
/*    */     
/* 55 */     log.info("creating FrameServiceProvider");
/* 56 */     Dispatcher dispatcher = (Dispatcher)object;
/* 57 */     FrameServiceProvider.create((ResourceLoading)new ServletResourceLoading((HttpServlet)dispatcher));
/*    */     
/* 59 */     logServerInfo();
/* 60 */     ApplicationContext.getInstance().onStartup();
/*    */   }
/*    */ 
/*    */   
/*    */   private void logServerInfo() {
/* 65 */     log.info("Determined server IP address: " + ApplicationContext.getInstance().getIPAddr());
/* 66 */     log.info("Determined hostname: " + ApplicationContext.getInstance().getHostName());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\IOApplicationConnector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */