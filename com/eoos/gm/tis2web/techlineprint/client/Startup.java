/*    */ package com.eoos.gm.tis2web.techlineprint.client;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*    */ import com.eoos.gm.tis2web.techlineprint.client.app.impl.TechlinePrintBridgeImpl;
/*    */ import com.eoos.gm.tis2web.techlineprint.client.common.ClientAppContext;
/*    */ import com.eoos.gm.tis2web.techlineprint.client.common.ClientAppContextProvider;
/*    */ import com.eoos.util.Log4jUtil;
/*    */ import java.io.File;
/*    */ import org.apache.log4j.LogManager;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.helpers.LogLog;
/*    */ 
/*    */ 
/*    */ public class Startup
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(Startup.class);
/*    */ 
/*    */   
/*    */   public static void main(String[] args) throws Throwable {
/*    */     try {
/* 23 */       LogManager.getLoggerRepository().resetConfiguration();
/* 24 */       Logger rootLogger = Logger.getRootLogger();
/* 25 */       Log4jUtil.attachConsoleAppender(rootLogger, null);
/* 26 */       File logFile = new File(System.getProperty("user.home"));
/* 27 */       logFile = new File(logFile, "techlineprint");
/* 28 */       logFile.mkdirs();
/* 29 */       logFile = new File(logFile, "techlineprint-client.log");
/* 30 */       Log4jUtil.attachRollingFileAppender(Logger.getLogger("com.eoos.gm.tis2web.techlineprint"), logFile);
/* 31 */     } catch (Exception e) {
/* 32 */       LogLog.error("WARNING: Cannot initialize logger configuration - exception: " + e, e);
/*    */     } 
/*    */     
/*    */     try {
/* 36 */       ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/*    */       
/* 38 */       appContext.init();
/* 39 */       String nativeLan = appContext.getNativeLan();
/*    */       
/* 41 */       if (SoftwareKeyCheckClient.checkSoftwareKey("com.eoos.gm.tis2web.techlineprint.client.common.message") == 0) {
/*    */         
/* 43 */         String tech2WinComPort = appContext.getTech2WinComPort();
/* 44 */         if (tech2WinComPort != null && tech2WinComPort.length() > 0) {
/* 45 */           TechlinePrintBridgeImpl.getInstance().setProperties("techlineprint", new Pair[] { (Pair)new PairImpl("tech2win_com_port", tech2WinComPort) });
/*    */         }
/*    */         
/* 48 */         TechlinePrintBridgeImpl.getInstance().setLanguage("techlineprint", nativeLan);
/* 49 */         TechlinePrintBridgeImpl.getInstance().startUI("techlineprint", (String[])appContext.getSupportedDevices().toArray((Object[])new String[appContext.getSupportedDevices().size()]));
/*    */       } 
/* 51 */     } catch (Throwable t) {
/* 52 */       log.error("unable to execute - exception: " + t, t);
/* 53 */       throw t;
/*    */     } finally {
/* 55 */       System.exit(0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */