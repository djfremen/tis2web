/*     */ package com.eoos.gm.tis2web.sas.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*     */ import com.eoos.gm.tis2web.sas.client.system.starter.StarterProvider;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.main.MainFrame;
/*     */ import com.eoos.gm.tis2web.sas.common.system.LabelResourceProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Log4jUtil;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Startup
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(Startup.class);
/*     */   
/*     */   public static void main(String[] args) throws Throwable {
/*     */     try {
/*  34 */       final LabelResource labelResource = LabelResourceProvider.getInstance().getLabelResource();
/*  35 */       Locale locale = Util.parseLocale(System.getProperty("locale"));
/*     */       
/*  37 */       LogManager.getLoggerRepository().resetConfiguration();
/*  38 */       Logger rootLogger = Logger.getRootLogger();
/*  39 */       Log4jUtil.attachConsoleAppender(rootLogger, null);
/*  40 */       File homeDir = new File(System.getProperty("user.home"));
/*  41 */       homeDir = new File(homeDir, "sas");
/*  42 */       homeDir.mkdirs();
/*  43 */       File logFile = new File(homeDir, "sas.log");
/*  44 */       Log4jUtil.attachRollingFileAppender(Logger.getLogger("com.eoos.gm.tis2web.sas"), logFile);
/*     */       
/*  46 */       if (StarterProvider.getInstance().getStarter().isStartAllowed()) {
/*     */         
/*  48 */         if (!StarterProvider.getInstance().getStarter().setEnvironment()) {
/*  49 */           log.fatal("unable to init environment !!!");
/*     */         } else {
/*  51 */           SASClientContextProvider.getInstance().createContext(homeDir);
/*  52 */           final List ports = StarterProvider.getInstance().getStarter().getAvailableCommPorts();
/*  53 */           final List<String> rates = Arrays.asList(new String[] { "115200", "57600", "38400", "19200", "9600" });
/*     */           
/*  55 */           if (SoftwareKeyCheckClient.checkSoftwareKey("com.eoos.gm.tis2web.sas.client.message") == 0) {
/*  56 */             final Object sync = new Object();
/*  57 */             synchronized (sync)
/*     */             {
/*  59 */               SwingUtilities.invokeLater(new Runnable()
/*     */                   {
/*     */                     public void run() {
/*     */                       try {
/*  63 */                         MainFrame mainFrame = MainFrame.createInstance(new MainFrame.Callback()
/*     */                             {
/*  65 */                               private Locale locale = SASClientContextProvider.getInstance().getContext().getLocale();
/*     */                               
/*     */                               public String getMessage(String key) {
/*  68 */                                 return labelResource.getMessage(this.locale, key);
/*     */                               }
/*     */                               
/*     */                               public String getLabel(String key) {
/*  72 */                                 return labelResource.getLabel(this.locale, key);
/*     */                               }
/*     */                               
/*     */                               public List getBaudrates() {
/*  76 */                                 return rates;
/*     */                               }
/*     */                               
/*     */                               public List getPorts() {
/*  80 */                                 return ports;
/*     */                               }
/*     */                             });
/*     */                         
/*  84 */                         mainFrame.addWindowListener(new WindowAdapter()
/*     */                             {
/*     */                               public void windowClosing(WindowEvent e) {
/*  87 */                                 synchronized (sync) {
/*  88 */                                   sync.notify();
/*     */                                 } 
/*     */                               }
/*     */                             });
/*  92 */                         mainFrame.start();
/*  93 */                       } catch (Throwable t) {
/*  94 */                         Startup.log.error("exception - " + t, t);
/*  95 */                         synchronized (sync) {
/*  96 */                           sync.notify();
/*     */                         } 
/*     */                       } 
/*     */                     }
/*     */                   });
/* 101 */               sync.wait();
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 108 */         JOptionPane.showMessageDialog(null, labelResource.getMessage(locale, "error.already.running"), "Exception", 0);
/*     */       } 
/* 110 */     } catch (Throwable t) {
/* 111 */       log.error("unable to execute - exception: " + t, t);
/* 112 */       throw t;
/*     */     } finally {
/* 114 */       System.exit(0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */