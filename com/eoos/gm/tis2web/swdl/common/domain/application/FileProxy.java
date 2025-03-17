/*    */ package com.eoos.gm.tis2web.swdl.common.domain.application;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.common.Identifiable;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileProxy
/*    */   implements Identifiable, FileInformation, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   private String identifier = null;
/*    */   
/* 19 */   private String name = null;
/*    */   
/* 21 */   private String revision = null;
/*    */   
/* 23 */   private Integer size = null;
/*    */   
/* 25 */   private Integer status = null;
/*    */   
/* 27 */   private Integer type = null;
/*    */   
/* 29 */   private Version version = null;
/*    */   
/* 31 */   private String fingerprint = null;
/*    */ 
/*    */   
/*    */   public FileProxy(String id, String name, String revision, Integer size, Integer status, Integer type, String fingerprint, Version version) {
/* 35 */     this.identifier = id;
/* 36 */     this.name = name;
/* 37 */     this.revision = revision;
/* 38 */     this.size = size;
/* 39 */     this.status = status;
/* 40 */     this.type = type;
/* 41 */     this.version = version;
/* 42 */     this.fingerprint = fingerprint;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 46 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getRevision() {
/* 50 */     return this.revision;
/*    */   }
/*    */   
/*    */   public Integer getSize() {
/* 54 */     return this.size;
/*    */   }
/*    */   
/*    */   public void setSize(Integer size) {
/* 58 */     this.size = size;
/*    */   }
/*    */   
/*    */   public Integer getStatus() {
/* 62 */     return this.status;
/*    */   }
/*    */   
/*    */   public Integer getType() {
/* 66 */     return this.type;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 70 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public Version getVersion() {
/* 74 */     return this.version;
/*    */   }
/*    */   
/*    */   public String getFingerprint() {
/* 78 */     return this.fingerprint;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\application\FileProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */