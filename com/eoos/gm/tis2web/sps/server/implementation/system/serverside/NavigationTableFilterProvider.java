/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.NavigationTableFilter;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class NavigationTableFilterProvider
/*    */ {
/*    */   public static NavigationTableFilter getFilter(ClientContext context) {
/* 14 */     synchronized (context.getLockObject()) {
/* 15 */       NavigationTableFilter instance = (NavigationTableFilter)context.getObject(NavigationTableFilter.class);
/* 16 */       if (instance == null) {
/* 17 */         ACLService aclService = ACLServiceProvider.getInstance().getService();
/* 18 */         Map usrGroup2Manuf = context.getSharedContext().getUsrGroup2ManufMap();
/* 19 */         Collection entries = aclService.getAuthorizedResources("Navigation_VC", ACLService.ALL_RESOURCE_VALUES, usrGroup2Manuf, context.getSharedContext().getCountry());
/* 20 */         instance = new NavigationTableFilter(entries);
/* 21 */         context.storeObject(NavigationTableFilter.class, instance);
/*    */       } 
/*    */       
/* 24 */       return instance;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\NavigationTableFilterProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */