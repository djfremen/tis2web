/*    */ package com.eoos.gm.tis2web.tech2view.client;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.tech2view.client.app.impl.Tech2ViewBridgeImpl;
/*    */ import com.eoos.gm.tis2web.tech2view.client.common.ClientAppContext;
/*    */ import com.eoos.gm.tis2web.tech2view.client.common.ClientAppContextProvider;
/*    */ import com.eoos.util.Log4jUtil;
/*    */ import java.io.File;
/*    */ import org.apache.log4j.LogManager;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Startup_Local
/*    */ {
/*    */   public static void main(String[] args) throws Exception {
/* 21 */     System.setProperty("language.id", "en_US");
/* 22 */     System.setProperty("session.id", "tech2view-local");
/* 23 */     System.setProperty("language.native.id", "ENU");
/*    */     
/*    */     try {
/* 26 */       LogManager.getLoggerRepository().resetConfiguration();
/* 27 */       Logger rootLogger = Logger.getRootLogger();
/* 28 */       Log4jUtil.attachConsoleAppender(rootLogger, null);
/* 29 */       File logFile = new File(System.getProperty("user.home"));
/* 30 */       logFile = new File(logFile, "tech2view");
/* 31 */       logFile.mkdirs();
/* 32 */       logFile = new File(logFile, "tech2view-client.log");
/* 33 */       Log4jUtil.attachRollingFileAppender(Logger.getLogger("com.eoos.gm.tis2web.tech2view"), logFile);
/* 34 */     } catch (Exception e) {
/* 35 */       System.out.println("WARNING: Cannot initialize logger configuration");
/*    */     } 
/*    */     
/* 38 */     ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*    */     try {
/* 40 */       appContext.init();
/* 41 */     } catch (Exception e) {
/* 42 */       System.err.println(e);
/*    */       return;
/*    */     } 
/* 45 */     String nativeLan = appContext.getNativeLan();
/*    */     
/* 47 */     String tech2WinComPort = appContext.getTech2WinComPort();
/* 48 */     if (tech2WinComPort != null && tech2WinComPort.length() > 0) {
/* 49 */       Tech2ViewBridgeImpl.getInstance().setProperties("tech2view", new Pair[] { (Pair)new PairImpl("tech2win_com_port", tech2WinComPort) });
/*    */     }
/*    */     
/* 52 */     Tech2ViewBridgeImpl.getInstance().setLanguage("tech2view", nativeLan);
/* 53 */     Tech2ViewBridgeImpl.getInstance().startUI("tech2view", "tech2");
/* 54 */     System.exit(0);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\client\Startup_Local.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */