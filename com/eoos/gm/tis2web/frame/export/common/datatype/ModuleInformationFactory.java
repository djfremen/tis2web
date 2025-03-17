/*    */ package com.eoos.gm.tis2web.frame.export.common.datatype;
/*    */ 
/*    */ import com.eoos.datatype.VersionNumber;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class ModuleInformationFactory
/*    */ {
/*    */   public static ModuleInformation create(final String description, String strVersion, String build, final Object dbVersionInfo) {
/* 12 */     VersionNumber version = null;
/* 13 */     if (strVersion != null && strVersion.trim().length() > 0) {
/* 14 */       StringBuffer _version = new StringBuffer(strVersion.trim());
/* 15 */       if (build != null && build.trim().length() > 0) {
/* 16 */         _version.append("-" + build.trim());
/*    */       }
/* 18 */       version = VersionNumber.parse(_version.toString());
/*    */     } 
/* 20 */     final VersionNumber v = version;
/*    */     
/* 22 */     return new ModuleInformation()
/*    */       {
/*    */         public VersionNumber getVersion() {
/* 25 */           return v;
/*    */         }
/*    */         
/*    */         public String getDescription(Locale locale) {
/* 29 */           return description;
/*    */         }
/*    */         
/*    */         public Object getDatabaseVersionInformation() {
/* 33 */           return dbVersionInfo;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static ModuleInformation create(Module module, String strVersion, String build, Object dbVersionInfo) {
/* 40 */     return create(getDefaultDescription(module), strVersion, build, dbVersionInfo);
/*    */   }
/*    */   
/*    */   public static String getDefaultDescription(Module module) {
/* 44 */     return ApplicationContext.getInstance().getLabel(Locale.ENGLISH, "module.type." + module.getType());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\datatype\ModuleInformationFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */