/*    */ package com.eoos.gm.tis2web.tech2view.client;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*    */ import com.eoos.gm.tis2web.tech2view.client.app.impl.Tech2ViewBridgeImpl;
/*    */ import com.eoos.gm.tis2web.tech2view.client.common.ClientAppContext;
/*    */ import com.eoos.gm.tis2web.tech2view.client.common.ClientAppContextProvider;
/*    */ import com.eoos.util.Log4jUtil;
/*    */ import java.io.File;
/*    */ import org.apache.log4j.LogManager;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.helpers.LogLog;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Startup
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(Startup.class);
/*    */ 
/*    */   
/*    */   public static void main(String[] args) throws Throwable {
/*    */     try {
/* 24 */       LogManager.getLoggerRepository().resetConfiguration();
/* 25 */       Logger rootLogger = Logger.getRootLogger();
/* 26 */       Log4jUtil.attachConsoleAppender(rootLogger, null);
/* 27 */       File logFile = new File(System.getProperty("user.home"));
/* 28 */       logFile = new File(logFile, "tech2view");
/* 29 */       logFile.mkdirs();
/* 30 */       logFile = new File(logFile, "tech2view-client.log");
/* 31 */       Log4jUtil.attachRollingFileAppender(Logger.getLogger("com.eoos.gm.tis2web.tech2view"), logFile);
/* 32 */     } catch (Exception e) {
/* 33 */       LogLog.error("WARNING: Cannot initialize logger configuration");
/*    */     } 
/*    */     
/*    */     try {
/* 37 */       ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*    */       
/* 39 */       appContext.init();
/* 40 */       String nativeLan = appContext.getNativeLan();
/*    */       
/* 42 */       if (SoftwareKeyCheckClient.checkSoftwareKey("com.eoos.gm.tis2web.tech2view.client.common.message") == 0) {
/*    */         
/* 44 */         String tech2WinComPort = appContext.getTech2WinComPort();
/* 45 */         if (tech2WinComPort != null && tech2WinComPort.length() > 0) {
/* 46 */           Tech2ViewBridgeImpl.getInstance().setProperties("tech2view", new Pair[] { (Pair)new PairImpl("tech2win_com_port", tech2WinComPort) });
/*    */         }
/*    */         
/* 49 */         Tech2ViewBridgeImpl.getInstance().setLanguage("tech2view", nativeLan);
/* 50 */         Tech2ViewBridgeImpl.getInstance().startUI("tech2view", "tech2");
/*    */       } 
/* 52 */     } catch (Throwable t) {
/* 53 */       log.error("exception: " + t, t);
/* 54 */       throw t;
/*    */     } finally {
/* 56 */       System.exit(0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\client\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */