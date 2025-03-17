/*     */ package com.eoos.gm.tis2web.si.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.client.SoftwareKeyCheckClient;
/*     */ import com.eoos.gm.tis2web.si.client.model.Port;
/*     */ import com.eoos.gm.tis2web.si.client.starter.Starter;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.SystemPropertiesAdapter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.Layout;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.RollingFileAppender;
/*     */ 
/*     */ public class Startup
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(Startup.class);
/*     */   
/*     */   public static void main(String[] args) throws Throwable {
/*  25 */     Log4jUtil.attachConsoleAppender(Logger.getRootLogger(), Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/*  26 */     log.debug("startup...");
/*  27 */     File homeDir = new File(System.getProperty("user.home"));
/*  28 */     homeDir = new File(homeDir, "tis2web/si");
/*  29 */     homeDir.mkdirs();
/*  30 */     log.debug("...home directory is " + homeDir);
/*     */     
/*  32 */     RollingFileAppender appender = new RollingFileAppender();
/*  33 */     appender.setLayout((Layout)Log4jUtil.PL_SIMPLE_WITH_SHORT_CATEGORY);
/*  34 */     appender.setFile((new File(homeDir, "si.client.log")).getAbsolutePath());
/*  35 */     appender.setAppend(false);
/*  36 */     appender.setImmediateFlush(true);
/*  37 */     appender.setMaximumFileSize(52428800L);
/*  38 */     appender.setMaxBackupIndex(10);
/*  39 */     appender.activateOptions();
/*     */     
/*  41 */     Logger.getRootLogger().addAppender((Appender)appender);
/*  42 */     log.debug("...log facility initialized");
/*     */     
/*     */     try {
/*  45 */       if (Starter.getInstance().isStartAllowed()) {
/*  46 */         SystemPropertiesAdapter systemPropertiesAdapter = SystemPropertiesAdapter.getInstance();
/*  47 */         Locale locale = Util.parseLocale(systemPropertiesAdapter.getProperty("language.id"));
/*  48 */         locale = (locale != null) ? locale : Locale.ENGLISH;
/*  49 */         final LabelResource labelResource = new LabelResource(locale);
/*     */         
/*  51 */         log.debug("...determining list of communication ports");
/*  52 */         List<String> _ports = Starter.getInstance().getAvailableCommPorts();
/*  53 */         _ports.add("AUTO");
/*  54 */         List<Port> ports = Util.transformList(_ports, new Transforming()
/*     */             {
/*     */               public Object transform(Object object) {
/*  57 */                 return ClientUtil.toPort((String)object);
/*     */               }
/*     */             });
/*     */         
/*  61 */         if (Util.isNullOrEmpty(ports)) {
/*  62 */           log.warn("did not find any communication port");
/*  63 */           String message = labelResource.getMessage("error.no.com.ports");
/*  64 */           showErrorDialog(message, labelResource);
/*     */         } else {
/*  66 */           log.debug("...available ports: " + ports);
/*     */           
/*     */           try {
/*  69 */             if (SoftwareKeyCheckClient.checkSoftwareKey(null) != -1) {
/*  70 */               log.debug("***application START ");
/*  71 */               ApplicationFacade facade = new ApplicationFacade(homeDir, (Configuration)systemPropertiesAdapter, labelResource, locale, ports);
/*  72 */               facade.executeTransfer();
/*  73 */               Util.executeOnAWTThread(new Runnable()
/*     */                   {
/*     */                     public void run() {
/*  76 */                       JOptionPane.showMessageDialog(null, labelResource.getMessage("finished"), null, 1);
/*     */                     }
/*     */                   }true);
/*  79 */               log.debug("***application EXITED normally");
/*     */             } else {
/*  81 */               log.warn("software key check failed");
/*  82 */               Util.executeOnAWTThread(new Runnable()
/*     */                   {
/*     */                     public void run() {
/*  85 */                       JOptionPane.showMessageDialog(null, labelResource.getMessage("software.key.check.failure"), labelResource.getLabel("error"), 0);
/*     */                     }
/*     */                   }true);
/*     */             }
/*     */           
/*     */           }
/*  91 */           catch (InterruptedException e) {
/*  92 */             log.info("!!! interrupted !!!", e);
/*  93 */           } catch (Throwable t) {
/*  94 */             String message = labelResource.getMessage("unable.to.execute");
/*  95 */             if (t instanceof FeedbackException) {
/*  96 */               message = ((FeedbackException)t).getMessage(labelResource);
/*  97 */               if (t.getCause() != null) {
/*  98 */                 t = t.getCause();
/*     */               }
/*     */             } 
/* 101 */             showErrorDialog(message, labelResource);
/* 102 */             throw t;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 106 */         log.warn("startup refused by starter");
/*     */       } 
/* 108 */     } catch (Throwable t) {
/* 109 */       log.error("unable to execute - exception: " + t, t);
/*     */     } finally {
/* 111 */       log.debug("exiting");
/* 112 */       System.exit(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void showErrorDialog(final String message, final LabelResource labelResource) {
/* 118 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 121 */             JOptionPane.showMessageDialog(null, message, labelResource.getLabel("error"), 0);
/*     */           }
/*     */         }true);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\Startup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */