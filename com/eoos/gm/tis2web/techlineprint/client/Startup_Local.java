/*    */ package com.eoos.gm.tis2web.techlineprint.client;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.techlineprint.client.app.impl.TechlinePrintBridgeImpl;
/*    */ import com.eoos.gm.tis2web.techlineprint.client.common.ClientAppContext;
/*    */ import com.eoos.gm.tis2web.techlineprint.client.common.ClientAppContextProvider;
/*    */ import com.eoos.util.Log4jUtil;
/*    */ import java.io.File;
/*    */ import org.apache.log4j.LogManager;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Startup_Local
/*    */ {
/*    */   public static void main(String[] args) throws Exception {
/* 20 */     System.setProperty("language.id", "en_US");
/* 21 */     System.setProperty("session.id", "techlineprint-local");
/* 22 */     System.setProperty("language.native.id", "ENU");
/* 23 */     System.setProperty("devices", "tech2, tech32");
/*    */     
/*    */     try {
/* 26 */       LogManager.getLoggerRepository().resetConfiguration();
/* 27 */       Logger rootLogger = Logger.getRootLogger();
/* 28 */       Log4jUtil.attachConsoleAppender(rootLogger, null);
/* 29 */       File logFile = new File(System.getProperty("user.home"));
/* 30 */       logFile = new File(logFile, "techlineprint");
/* 31 */       logFile.mkdirs();
/* 32 */       logFile = new File(logFile, "techlineprint-client.log");
/* 33 */       Log4jUtil.attachRollingFileAppender(Logger.getLogger("com.eoos.gm.tis2web.techlineprint"), logFile);
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
/* 49 */       TechlinePrintBridgeImpl.getInstance().setProperties("techlineprint", new Pair[] { (Pair)new PairImpl("tech2win_com_port", tech2WinComPort) });
/*    */     }
/*    */     
/* 52 */     TechlinePrintBridgeImpl.getInstance().setLanguage("techlineprint", nativeLan);
/* 53 */     TechlinePrintBridgeImpl.getInstance().startUI("techlineprint", (String[])appContext.getSupportedDevices().toArray((Object[])new String[appContext.getSupportedDevices().size()]));
/* 54 */     System.exit(0);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\Startup_Local.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */