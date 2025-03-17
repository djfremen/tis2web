/*    */ package com.eoos.gm.tis2web.acl.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Service;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ACLService
/*    */   extends Service, Module
/*    */ {
/*    */   public static final String RESOURCE_GROUP_SIT = "SIT";
/*    */   public static final String RESOURCE_GROUP_DATA = "Data";
/*    */   public static final String RESOURCE_GROUP_APPLICATION = "Application";
/*    */   public static final String RESOURCE_GROUP_SALESMAKE = "Salesmake";
/*    */   public static final String RESOURCE_GROUP_COMPONENT = "Component";
/*    */   public static final String RESOURCE_GROUP_CD_MEDIA = "CD Media";
/*    */   public static final String RESOURCE_GROUP_BRAND = "Brand";
/*    */   public static final String RESOURCE_GROUP_ADAPTER = "Adapter";
/*    */   public static final String RESOURCE_GROUP_ADAPTERDATA = "AdapterData";
/*    */   public static final String RESOURCE_GROUP_J2534DEVICEDRIVER = "J2534DeviceDriver";
/*    */   public static final String RESOURCE_GROUP_NONJ2534DEVICEDRIVER = "NONJ2534DeviceDriver";
/*    */   public static final String RESOURCE_GROUP_NAVIGATION_VC = "Navigation_VC";
/* 39 */   public static final Set ALL_RESOURCE_VALUES = Collections.unmodifiableSet(new HashSet());
/*    */   public static final String COMPONENT_VALUE_DEALERVCI = "dealerVCI";
/*    */   public static final String COMPONENT_VALUE_DATABASE_INFO = "database.info";
/*    */   public static final String COMPONENT_VALUE_ADMIN_LOGINLOG = "admin.loginlog";
/*    */   public static final String COMPONENT_VALUE_ADMIN_SWDLLOG = "admin.swdllog";
/*    */   public static final String COMPONENT_VALUE_ADMIN_CONFIGURATION = "admin.configuration";
/*    */   public static final String COMPONENT_VALUE_ADMIN_SPSLOG = "admin.spslog";
/*    */   public static final String COMPONENT_VALUE_ADMIN_CACHES = "admin.caches";
/*    */   public static final String COMPONENT_VALUE_ADMIN_MESSAGES = "admin.messages";
/*    */   public static final String COMPONENT_VALUE_ADMIN_SENDLOGS = "log.mailer";
/*    */   public static final String COMPONENT_VALUE_ADMIN_EXECTASK = "exec.task";
/*    */   public static final String COMPONENT_VALUE_ADMIN_TASK_UPDATE_CONFIG = "update.configuration";
/*    */   public static final String COMPONENT_VALUE_ADMIN_LOGFILES = "admin.log.files";
/*    */   public static final String COMPONENT_VALUE_ADMIN_DTCLOG = "admin.dtc.log";
/*    */   public static final String COMPONENT_VALUE_ADMIN_LOG4J = "admin.log4j";
/*    */   public static final String COMPONENT_VALUE_ADMIN_SPSADAPTER_RESET = "admin.sps.adapter.reset";
/*    */   public static final String COMPONENT_VALUE_ADMIN_SWDLSERVICE_RESET = "admin.swdl.service.reset";
/*    */   public static final String COMPONENT_VALUE_ADMIN_HELPSERVICE_RESET = "admin.help.service.reset";
/*    */   public static final String COMPONENT_VALUE_ADMIN_NEWSSERVICE_RESET = "admin.news.service.reset";
/*    */   public static final String COMPONENT_VALUE_ADMIN_AUTHORIZATION = "admin.authorization";
/*    */   public static final String COMPONENT_VALUE_ADMIN_REGISTRATION = "admin.registration";
/*    */   public static final String COMPONENT_VALUE_ADMIN_SIDSSERVICE_RESET = "admin.sids.service.reset";
/*    */   public static final String COMPONENT_VALUE_SPSLOG_ONSTAR_ENTRIES = "spslog.entries.onstar";
/*    */   public static final String COMPONENT_VALUE_SPSLOG_ALL_ENTRIES = "spslog.entries.all";
/*    */   public static final String COMPONENT_VALUE_ADMIN_DLS = "admin.dls";
/*    */   public static final String COMPONENT_VALUE_ADMIN_AMS = "admin.ams";
/*    */   public static final String ADAPTERDATA_VALUE_NOHWK = "no-hwk";
/*    */   public static final String ADAPTERDATA_VALUE_PROCEED_SAME_VIN = "proceed-same-vin";
/*    */   public static final String ADAPTERDATA_VALUE_WARRANTY_CODE_LIST = "warranty-code-list";
/*    */   public static final String ADAPTERDATA_VALUE_SECURITY_CODE_ACCESS = "security-code-access";
/*    */   public static final String ADAPTERDATA_VALUE_SPS_SECURITY_ENABLED = "sps-security-enabled";
/*    */   public static final String ADAPTERDATA_VALUE_SPS_VCI_1001_ENABLED = "sps-vci-1001";
/*    */   public static final String RESOURCE_GROUP_VC = "VC";
/*    */   public static final String RESOURCE_GROUP_HARDWARE_KEY_CHECK = "HardwareKeyCheck";
/*    */   public static final String RESOURCE_GROUP_SALES_ORGANIZATION = "SalesOrganization";
/*    */   public static final String RESOURCE_GROUP_DOWNLOAD = "Download";
/*    */   public static final String COMPONENT_VALUE_ICOP = "icop.client";
/*    */   
/*    */   Set getAuthorizedResources(String paramString1, Set paramSet, Map paramMap, String paramString2);
/*    */   
/*    */   Set getAuthorizedResources(String paramString1, Map paramMap, String paramString2);
/*    */   
/*    */   Set getAllResources(String paramString);
/*    */   
/*    */   Set getUserGroups();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\acl\service\ACLService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */