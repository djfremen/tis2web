/*     */ package com.eoos.gm.tis2web.frame.dls.client.api;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.DLSServiceImpl;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.impl.PropertiesConfigurationImpl;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DLSServiceFactory
/*     */ {
/*  20 */   private static final Logger log = Logger.getLogger(DLSServiceFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DLSService createService(AuthenticationQuery proxyAuthenticationCallback, Properties properties) {
/*  34 */     log.debug("creating DLS service..."); try {
/*     */       DLSService dLSService;
/*  36 */       DLSServiceImpl dLSServiceImpl = new DLSServiceImpl(proxyAuthenticationCallback, (Configuration)new PropertiesConfigurationImpl(properties), new DLSServiceImpl.Callback()
/*     */           {
/*     */             public File getHomeDir() {
/*  39 */               File ret = DLSServiceFactory.getGDSDir();
/*  40 */               if (ret != null) {
/*  41 */                 ret = new File(ret, ".dls");
/*     */               }
/*  43 */               return ret;
/*     */             }
/*     */           });
/*  46 */       if ("true".equalsIgnoreCase(properties.getProperty("debug"))) {
/*  47 */         dLSService = (DLSService)ReflectionUtil.createCallLogger(dLSServiceImpl, log, false);
/*     */       }
/*  49 */       return dLSService;
/*  50 */     } catch (MalformedURLException e) {
/*  51 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static File getGDSDir() {
/*  57 */     File ret = null;
/*     */     try {
/*  59 */       Class<?> lookupClass = Class.forName("com.eoos.gm.tis2web.frame.dls.client.api.GDSHomeDirLookup");
/*  60 */       ret = (File)lookupClass.getMethod("getHomeDir", (Class[])null).invoke(null, (Object[])null);
/*  61 */     } catch (Exception e) {
/*  62 */       log.warn("unable to lookup GDS home dir, ignoring - exception: " + e, e);
/*     */     } 
/*  64 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DLSService createService(AuthenticationQuery proxyAuthenticationCallback) {
/*  75 */     return createService(proxyAuthenticationCallback, System.getProperties());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DLSService createServiceSwk(final File homeDirectory) {
/*  90 */     log.debug("creating DLS service for SoftwareKeyCheck ...");
/*     */     try {
/*     */       DLSService dLSService;
/*  93 */       Configuration cfgProp = ConfigurationUtil.getSystemPropertiesAdapter();
/*  94 */       DLSServiceImpl dLSServiceImpl = new DLSServiceImpl(null, cfgProp, new DLSServiceImpl.Callback()
/*     */           {
/*     */             public File getHomeDir() {
/*  97 */               File ret = homeDirectory;
/*  98 */               if (ret != null) {
/*  99 */                 ret = new File(ret, ".dls");
/*     */               } else {
/* 101 */                 ret = new File(ConfigurationUtil.getSystemPropertiesAdapter().getProperty("user.home"), ".dls");
/*     */               } 
/* 103 */               return ret;
/*     */             }
/*     */           });
/*     */       
/* 107 */       if ("true".equalsIgnoreCase(cfgProp.getProperty("debug"))) {
/* 108 */         dLSService = (DLSService)ReflectionUtil.createCallLogger(dLSServiceImpl, log, false);
/*     */       }
/* 110 */       return dLSService;
/* 111 */     } catch (MalformedURLException e) {
/* 112 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\api\DLSServiceFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */