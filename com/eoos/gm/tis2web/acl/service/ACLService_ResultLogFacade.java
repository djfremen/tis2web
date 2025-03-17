/*    */ package com.eoos.gm.tis2web.acl.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.util.ConfigurationUtil;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ACLService_ResultLogFacade
/*    */   implements ACLService {
/* 13 */   private static final Logger log = Logger.getLogger(ACLService_ResultLogFacade.class);
/*    */   
/*    */   private ACLService delegate;
/*    */   
/*    */   public ACLService_ResultLogFacade(ACLService delegate) {
/* 18 */     this.delegate = delegate;
/*    */   }
/*    */   
/*    */   private boolean isEnabled() {
/* 22 */     return ConfigurationUtil.isTrue("frame.acl.trace.enabled", (Configuration)ApplicationContext.getInstance());
/*    */   }
/*    */ 
/*    */   
/*    */   public Set getAllResources(String resGroup) {
/* 27 */     Set ret = this.delegate.getAllResources(resGroup);
/* 28 */     if (isEnabled()) {
/* 29 */       log.info("getAllResources(" + String.valueOf(resGroup) + ") - returning:" + String.valueOf(ret));
/*    */     }
/* 31 */     return ret;
/*    */   }
/*    */   
/*    */   public Set getAuthorizedResources(String resGroup, Map usrGroup2Manuf, String country) {
/* 35 */     Set ret = this.delegate.getAuthorizedResources(resGroup, usrGroup2Manuf, country);
/* 36 */     if (isEnabled()) {
/* 37 */       log.info("getAuthorizedResources(" + String.valueOf(resGroup) + ", " + String.valueOf(usrGroup2Manuf) + ", " + String.valueOf(country) + ") - returning:" + String.valueOf(ret));
/*    */     }
/* 39 */     return ret;
/*    */   }
/*    */   
/*    */   public Set getAuthorizedResources(String resGroup, Set resValues, Map usrGroup2Manuf, String country) {
/* 43 */     Set ret = this.delegate.getAuthorizedResources(resGroup, resValues, usrGroup2Manuf, country);
/* 44 */     if (isEnabled()) {
/* 45 */       log.info("getAuthorizedResources(" + String.valueOf(resGroup) + ", " + ((ACLService.ALL_RESOURCE_VALUES == resValues) ? "ALL" : String.valueOf(resValues)) + ", " + String.valueOf(usrGroup2Manuf) + ", " + String.valueOf(country) + ") - returning:" + String.valueOf(ret));
/*    */     }
/* 47 */     return ret;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 51 */     return this.delegate.getIdentifier();
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 55 */     return this.delegate.getModuleInformation();
/*    */   }
/*    */   
/*    */   public String getType() {
/* 59 */     return this.delegate.getType();
/*    */   }
/*    */   
/*    */   public Set getUserGroups() {
/* 63 */     return this.delegate.getUserGroups();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\acl\service\ACLService_ResultLogFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */