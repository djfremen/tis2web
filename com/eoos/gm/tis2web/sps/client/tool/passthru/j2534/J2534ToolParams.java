/*    */ package com.eoos.gm.tis2web.sps.client.tool.passthru.j2534;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class J2534ToolParams
/*    */ {
/*    */   String name;
/*    */   String j2534Version;
/*    */   String protocols;
/*    */   String dllPath;
/*    */   String configApp;
/*    */   String driverVersion;
/*    */   
/*    */   public J2534ToolParams(String name, String version, String protocols, String dll, String configApplication, String driverVersion) {
/* 18 */     this.name = name;
/* 19 */     this.j2534Version = version;
/* 20 */     this.protocols = protocols;
/* 21 */     this.dllPath = dll;
/* 22 */     this.configApp = configApplication;
/* 23 */     this.driverVersion = driverVersion;
/*    */   }
/*    */   
/*    */   protected String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */   
/*    */   protected String getJ2534Version() {
/* 31 */     return this.j2534Version;
/*    */   }
/*    */   
/*    */   protected String getProtocols() {
/* 35 */     return this.protocols;
/*    */   }
/*    */   
/*    */   protected String getDllPath() {
/* 39 */     return this.dllPath;
/*    */   }
/*    */   
/*    */   protected String getConfigApplication() {
/* 43 */     return this.configApp;
/*    */   }
/*    */   
/*    */   protected String getDriverVersion() {
/* 47 */     return this.driverVersion;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\j2534\J2534ToolParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */