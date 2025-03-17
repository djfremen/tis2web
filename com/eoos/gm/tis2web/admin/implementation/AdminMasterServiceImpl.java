/*     */ package com.eoos.gm.tis2web.admin.implementation;
/*     */ 
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.admin.implementation.ui.html.main.AdminMasterMainPage;
/*     */ import com.eoos.gm.tis2web.admin.info.ASServiceImpl_ServerInfo;
/*     */ import com.eoos.gm.tis2web.admin.service.AdminMasterService;
/*     */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.instantiation.Singleton;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.util.ClassUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdminMasterServiceImpl
/*     */   implements AdminMasterService, Configurable, Singleton
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(AdminMasterServiceImpl.class);
/*     */   
/*  34 */   private List subservices = new LinkedList();
/*     */   
/*  36 */   private static AdminMasterServiceImpl instance = null;
/*     */   
/*     */   public AdminMasterServiceImpl(Configuration _configuration) {
/*  39 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(_configuration, "subservice.");
/*  40 */     List<Comparable> tmp = new ArrayList(subConfigurationWrapper.getKeys());
/*  41 */     Collections.sort(tmp);
/*  42 */     for (Iterator<Comparable> iter = tmp.iterator(); iter.hasNext(); ) {
/*  43 */       String key = (String)iter.next();
/*  44 */       if (key.endsWith(".class")) {
/*  45 */         String subServiceKey = key.substring(0, key.length() - 6);
/*  46 */         log.info("....initializing subservice :" + subServiceKey);
/*  47 */         SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper, subServiceKey + ".");
/*     */         try {
/*  49 */           Class<?> clazz = Class.forName(subConfigurationWrapper.getProperty(key));
/*  50 */           Collection interfaces = ClassUtil.getAllInterfaces(clazz);
/*  51 */           AdminSubService subService = null;
/*  52 */           if (interfaces.contains(Configurable.class)) {
/*  53 */             if (interfaces.contains(Singleton.class)) {
/*  54 */               subService = (AdminSubService)clazz.getMethod("createInstance", new Class[] { Configuration.class }).invoke(null, new Object[] { subConfigurationWrapper1 });
/*     */             } else {
/*  56 */               subService = clazz.getConstructor(new Class[] { Configuration.class }).newInstance(new Object[] { subConfigurationWrapper1 });
/*     */             }
/*     */           
/*  59 */           } else if (interfaces.contains(Singleton.class)) {
/*  60 */             subService = (AdminSubService)clazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
/*     */           } else {
/*  62 */             subService = (AdminSubService)clazz.newInstance();
/*     */           } 
/*     */           
/*  65 */           this.subservices.add(subService);
/*  66 */         } catch (Exception e) {
/*  67 */           log.warn("...unable to initialize subservice: " + subServiceKey + " - exception:" + e, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/*  73 */       this.subservices.add(new ASServiceImpl_ServerInfo());
/*     */     }
/*  75 */     catch (Exception e) {
/*  76 */       log.error("unable to add server info service, ignoring - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized AdminMasterServiceImpl createInstance(Configuration configuration) {
/*  81 */     instance = new AdminMasterServiceImpl(configuration);
/*  82 */     return instance;
/*     */   }
/*     */   
/*     */   public static synchronized AdminMasterServiceImpl getInstance() {
/*  86 */     return instance;
/*     */   }
/*     */   
/*     */   public List getSubServices() {
/*  90 */     return this.subservices;
/*     */   }
/*     */   
/*     */   public Object getUI(String sessionID, Map parameters) {
/*  94 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  95 */     SharedContextProxy.getInstance(context).update();
/*  96 */     return AdminMasterMainPage.getInstance(context).getHtmlCode(parameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isActive(String sessionID) {
/* 101 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*     */   }
/*     */   
/*     */   public Boolean invalidateSession(String sessionID) {
/* 105 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*     */   }
/*     */   
/*     */   public String getType() {
/* 109 */     return "admin";
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/* 117 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 121 */     return "adminmasterservice";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAvailable(ClientContext context) {
/* 129 */     boolean retValue = false;
/* 130 */     for (Iterator<AdminSubService> iter = this.subservices.iterator(); iter.hasNext() && !retValue;) {
/* 131 */       retValue = (retValue || ((AdminSubService)iter.next()).isAvailable(context));
/*     */     }
/* 133 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\implementation\AdminMasterServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */