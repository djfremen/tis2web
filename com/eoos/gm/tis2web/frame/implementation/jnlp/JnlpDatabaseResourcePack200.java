/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import jnlp.sample.util.VersionID;
/*    */ 
/*    */ 
/*    */ public class JnlpDatabaseResourcePack200
/*    */   extends JnlpDatabaseResource
/*    */ {
/*    */   private JnlpDatabaseResource resource;
/*    */   
/*    */   public JnlpDatabaseResourcePack200(JnlpDatabaseResource resource) {
/* 12 */     this.resource = resource;
/*    */   }
/*    */   
/*    */   public void addHistory(JnlpDatabaseResource resource) {
/* 16 */     throw new IllegalArgumentException();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 20 */     return this.resource.getName();
/*    */   }
/*    */   
/*    */   public byte[] getResource() {
/* 24 */     return this.resource.getResourcePack200();
/*    */   }
/*    */   
/*    */   public JnlpDatabaseResource getResourceVersion(String version) {
/* 28 */     return this.resource.getResourceVersion(version);
/*    */   }
/*    */   
/*    */   public long getTimeStamp() {
/* 32 */     return this.resource.getTimeStamp();
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 36 */     return this.resource.getVersion();
/*    */   }
/*    */   
/*    */   public VersionID getVersionID() {
/* 40 */     return this.resource.getVersionID();
/*    */   }
/*    */   
/*    */   public boolean isNativeLibResource() {
/* 44 */     return super.isNativeLibResource();
/*    */   }
/*    */   
/*    */   public boolean supportsPack200() {
/* 48 */     return this.resource.supportsPack200();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\JnlpDatabaseResourcePack200.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */