/*    */ package com.eoos.gm.tis2web.registration.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*    */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.SalesOrganization;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigurationMediator
/*    */ {
/*    */   private static final String PREFIX = "component.registration.server.";
/*    */   private Configuration configuration;
/*    */   
/*    */   public ConfigurationMediator(Configuration configuration) {
/* 21 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getEmail(SalesOrganization organization) {
/* 26 */     return this.configuration.getProperty("component.registration.server.email." + organization.toString().toLowerCase(Locale.ENGLISH));
/*    */   }
/*    */   
/*    */   public static SalesOrganization getSalesOrganization(ClientContext context) {
/*    */     try {
/* 31 */       SharedContext sc = context.getSharedContext();
/* 32 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 33 */       Set resources = aclMI.getAuthorizedResources("SalesOrganization", sc.getUsrGroup2ManufMap(), sc.getCountry());
/* 34 */       if (resources == null) {
/* 35 */         return null;
/*    */       }
/* 37 */       for (Iterator iter = resources.iterator(); iter.hasNext(); ) {
/* 38 */         Object resource = iter.next();
/* 39 */         SalesOrganization organization = SalesOrganization.get(resource.toString().toUpperCase(Locale.ENGLISH));
/* 40 */         if (organization != null) {
/* 41 */           return organization;
/*    */         }
/*    */       } 
/* 44 */     } catch (Exception e) {}
/*    */     
/* 46 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\server\ConfigurationMediator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */