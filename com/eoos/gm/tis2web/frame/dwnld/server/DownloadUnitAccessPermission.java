/*    */ package com.eoos.gm.tis2web.frame.dwnld.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadUnit;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.scsm.v2.multiton.v4.IMultitonSupport;
/*    */ import com.eoos.scsm.v2.multiton.v4.SoftMultitonSupport;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DownloadUnitAccessPermission
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(DownloadUnitAccessPermission.class);
/*    */   
/* 23 */   private static IMultitonSupport multitonSupport = (IMultitonSupport)new SoftMultitonSupport(new IMultitonSupport.CreationCallback()
/*    */       {
/*    */         public Object createInstance(Object identifier) {
/* 26 */           return new DownloadUnitAccessPermission((String)identifier);
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */   
/* 32 */   private Collection permittedResources = new HashSet();
/*    */   
/*    */   private String userGroup;
/*    */   
/*    */   private DownloadUnitAccessPermission(String userGroup) {
/* 37 */     this.userGroup = userGroup;
/*    */     try {
/* 39 */       if (log.isDebugEnabled()) {
/* 40 */         log.debug("determining permitted resources for userGroup: " + String.valueOf(userGroup));
/*    */       }
/* 42 */       ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 43 */       Map<Object, Object> usrGroup2Manuf = new HashMap<Object, Object>();
/* 44 */       usrGroup2Manuf.put(userGroup, Collections.singleton("ALL"));
/* 45 */       for (Iterator<String> iter = aclService.getAuthorizedResources("Download", ACLService.ALL_RESOURCE_VALUES, usrGroup2Manuf, null).iterator(); iter.hasNext();) {
/* 46 */         this.permittedResources.add(normalize(iter.next()));
/*    */       }
/* 48 */       if (log.isDebugEnabled()) {
/* 49 */         log.debug("...permitted resources (normalized): " + String.valueOf(this.permittedResources));
/*    */       }
/* 51 */     } catch (Exception e) {
/* 52 */       log.warn("unable to retrieve collection of permitted resources, disabling ALL - exception:" + e, e);
/* 53 */       this.permittedResources = Collections.EMPTY_SET;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static DownloadUnitAccessPermission getInstance(String userGroup) {
/* 58 */     return (DownloadUnitAccessPermission)multitonSupport.getInstance(userGroup, true);
/*    */   }
/*    */   
/*    */   private String normalize(String string) {
/* 62 */     return Util.normalize(string);
/*    */   }
/*    */   
/*    */   public boolean check(DownloadUnit downloadUnit) {
/* 66 */     if (log.isDebugEnabled()) {
/* 67 */       log.debug("checking access for user group: " + String.valueOf(this.userGroup) + " to download unit: " + String.valueOf(downloadUnit));
/*    */     }
/*    */     
/* 70 */     if (ApplicationContext.getInstance().developMode()) {
/* 71 */       log.debug("....allowing access (dev mode/sa)");
/* 72 */       return true;
/*    */     } 
/*    */     
/* 75 */     if (Util.isNullOrEmpty(downloadUnit.getACLTags())) {
/* 76 */       log.debug("...no acl resources assigned to unit, allowing access");
/* 77 */       return true;
/*    */     } 
/* 79 */     for (Iterator<String> iter = downloadUnit.getACLTags().iterator(); iter.hasNext(); ) {
/* 80 */       String tag = normalize(iter.next());
/* 81 */       if (this.permittedResources.contains(tag)) {
/* 82 */         log.debug("...found matching acl resource: " + tag + ", allowing access");
/* 83 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 87 */     log.debug("...no matching acl resource found, denying access");
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\server\DownloadUnitAccessPermission.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */