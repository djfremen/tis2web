/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import jnlp.sample.util.VersionID;
/*    */ 
/*    */ 
/*    */ public class JnlpDatabaseResource
/*    */ {
/*    */   public static final String NATIVELIB_RESOURCE = "nativelib";
/*    */   protected boolean isNativeLib;
/*    */   protected String name;
/*    */   protected String version;
/*    */   protected VersionID versionID;
/*    */   protected long timestamp;
/*    */   protected byte[] resource;
/*    */   protected byte[] resource200;
/*    */   protected Map history;
/*    */   
/*    */   public boolean isNativeLibResource() {
/* 21 */     return this.isNativeLib;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 25 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 29 */     return this.version;
/*    */   }
/*    */   
/*    */   public VersionID getVersionID() {
/* 33 */     return this.versionID;
/*    */   }
/*    */   
/*    */   public long getTimeStamp() {
/* 37 */     return this.timestamp;
/*    */   }
/*    */   
/*    */   public byte[] getResource() {
/* 41 */     return this.resource;
/*    */   }
/*    */   
/*    */   public byte[] getResourcePack200() {
/* 45 */     return supportsPack200() ? this.resource200 : this.resource;
/*    */   }
/*    */   
/*    */   public boolean supportsPack200() {
/* 49 */     return (this.resource200 != null && this.resource200.length > 0);
/*    */   }
/*    */   
/*    */   protected JnlpDatabaseResource() {}
/*    */   
/*    */   public JnlpDatabaseResource(String type, String name, String version, VersionID versionID, long timestamp, byte[] resource, byte[] resource200) {
/* 55 */     this.isNativeLib = type.equalsIgnoreCase("nativelib");
/* 56 */     this.name = name;
/* 57 */     this.version = version;
/* 58 */     this.versionID = versionID;
/* 59 */     this.timestamp = timestamp;
/* 60 */     this.resource = resource;
/* 61 */     this.resource200 = resource200;
/*    */   }
/*    */   
/*    */   public void addHistory(JnlpDatabaseResource resource) {
/* 65 */     if (resource.history != null) {
/* 66 */       if (this.history == null) {
/* 67 */         this.history = resource.history;
/*    */       } else {
/* 69 */         this.history.putAll(resource.history);
/*    */       } 
/* 71 */     } else if (this.history == null) {
/* 72 */       this.history = new HashMap<Object, Object>();
/*    */     } 
/* 74 */     this.history.put(resource.getVersion(), resource);
/*    */   }
/*    */   
/*    */   public JnlpDatabaseResource getResourceVersion(String version) {
/* 78 */     if (this.version.equals(version))
/* 79 */       return this; 
/* 80 */     if (this.history != null) {
/* 81 */       return (JnlpDatabaseResource)this.history.get(version);
/*    */     }
/* 83 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\JnlpDatabaseResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */