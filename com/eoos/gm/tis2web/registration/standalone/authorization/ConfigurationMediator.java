/*    */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*    */ 
/*    */ import com.eoos.gm.tis2web.registration.service.cai.InstallationType;
/*    */ import com.eoos.gm.tis2web.registration.service.cai.Subscription;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class ConfigurationMediator
/*    */ {
/*    */   private static final String PREFIX = "frame.registration.standalone.";
/*    */   private Configuration configuration;
/*    */   
/*    */   public ConfigurationMediator(Configuration configuration) {
/* 16 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public String getRegistryDatabase() {
/* 20 */     return this.configuration.getProperty("frame.registration.standalone.registration.database");
/*    */   }
/*    */   
/*    */   public String getAuthorizationDefinition() {
/* 24 */     return this.configuration.getProperty("frame.registration.standalone.registration.authorizations");
/*    */   }
/*    */   
/*    */   public String getDealershipFile() {
/* 28 */     return this.configuration.getProperty("frame.registration.standalone.dealership");
/*    */   }
/*    */   
/*    */   public String getLicenseFile() {
/* 32 */     return this.configuration.getProperty("frame.registration.standalone.licence");
/*    */   }
/*    */   
/*    */   public InstallationType getInstallationType() {
/* 36 */     String installation = this.configuration.getProperty("frame.registration.standalone.default.installation");
/* 37 */     return InstallationType.get(installation.toUpperCase(Locale.ENGLISH));
/*    */   }
/*    */   
/*    */   public Subscription getDefaultAuthorization() {
/* 41 */     String authorization = this.configuration.getProperty("frame.registration.standalone.default.authorization");
/* 42 */     return (Subscription)AuthorizationImpl.lookup(authorization);
/*    */   }
/*    */   
/*    */   public int getDefaultMaxSessionCount() {
/*    */     try {
/* 47 */       return Integer.parseInt(this.configuration.getProperty("frame.registration.standalone.default.sessions"));
/* 48 */     } catch (Exception e) {
/* 49 */       return 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean skipHardwareKeyCheck() {
/* 54 */     String os = System.getProperty("os.name");
/* 55 */     if (os.indexOf("Windows 2003") >= 0) {
/* 56 */       return true;
/*    */     }
/*    */     try {
/* 59 */       String setting = this.configuration.getProperty("frame.registration.standalone.hwk.check");
/* 60 */       return (setting != null && setting.equalsIgnoreCase("false"));
/* 61 */     } catch (Exception e) {
/* 62 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean checkMode() {
/*    */     try {
/* 68 */       for (Iterator<String> iter = this.configuration.getKeys().iterator(); iter.hasNext(); ) {
/* 69 */         String key = iter.next();
/* 70 */         if (key.equals("frame.registration.standalone.softwarekey") && 
/* 71 */           this.configuration.getProperty("frame.registration.standalone.softwarekey").equalsIgnoreCase("false")) {
/* 72 */           return true;
/*    */         }
/*    */       }
/*    */     
/* 76 */     } catch (Exception e) {}
/*    */     
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\ConfigurationMediator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */