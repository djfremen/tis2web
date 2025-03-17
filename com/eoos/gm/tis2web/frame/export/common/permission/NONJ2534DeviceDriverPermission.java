/*    */ package com.eoos.gm.tis2web.frame.export.common.permission;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NONJ2534DeviceDriverPermission
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(NONJ2534DeviceDriverPermission.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/* 26 */   private Set permittedDriverIDs = new HashSet();
/*    */   
/*    */   private NONJ2534DeviceDriverPermission(ClientContext context) {
/* 29 */     this.context = context;
/*    */     try {
/* 31 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 32 */       Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 33 */       for (Iterator<String> iter = aclService.getAuthorizedResources("NONJ2534DeviceDriver", ACLService.ALL_RESOURCE_VALUES, usrGroup2Manuf, context.getSharedContext().getCountry()).iterator(); iter.hasNext();) {
/* 34 */         this.permittedDriverIDs.add(normalize(iter.next()));
/*    */       }
/* 36 */       log.debug("permitted drivers: " + String.valueOf(this.permittedDriverIDs));
/*    */     }
/* 38 */     catch (Exception e) {
/* 39 */       log.warn("unable to retrieve collection of permitted NON-J2534 device drivers - exception:" + e, e);
/* 40 */       log.warn("disabling access to all drivers");
/* 41 */       this.permittedDriverIDs = Collections.EMPTY_SET;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static NONJ2534DeviceDriverPermission getInstance(ClientContext context) {
/* 46 */     synchronized (context.getLockObject()) {
/* 47 */       NONJ2534DeviceDriverPermission instance = (NONJ2534DeviceDriverPermission)context.getObject(NONJ2534DeviceDriverPermission.class);
/* 48 */       if (instance == null) {
/* 49 */         instance = new NONJ2534DeviceDriverPermission(context);
/* 50 */         context.storeObject(NONJ2534DeviceDriverPermission.class, instance);
/*    */       } 
/* 52 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   private String normalize(String string) {
/* 57 */     String ret = null;
/* 58 */     if (string != null) {
/* 59 */       ret = string.toLowerCase(Locale.ENGLISH);
/* 60 */       ret = StringUtilities.removeWhitespace(ret);
/*    */     } 
/* 62 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean check(String deviceDriverID) {
/*    */     try {
/* 67 */       deviceDriverID = normalize(deviceDriverID);
/* 68 */       if (ApplicationContext.getInstance().developMode()) {
/* 69 */         log.debug("DEVELOP MODE !!!!!allowing access to device driver :" + String.valueOf(deviceDriverID));
/* 70 */         return true;
/*    */       } 
/* 72 */       return this.permittedDriverIDs.contains(deviceDriverID);
/*    */     }
/* 74 */     catch (Exception e) {
/* 75 */       log.warn("unable to determine access permission for context " + String.valueOf(this.context) + " and device driver: " + String.valueOf(deviceDriverID) + ", returning false");
/* 76 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean hasPotentialPermissions() {
/* 81 */     if (ApplicationContext.getInstance().developMode()) {
/* 82 */       log.debug("DEVELOP MODE !!!!! indicating potential permission to install device driver(s)");
/* 83 */       return true;
/*    */     } 
/* 85 */     return (this.permittedDriverIDs.size() > 0);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\permission\NONJ2534DeviceDriverPermission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */