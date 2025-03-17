/*    */ package com.eoos.gm.tis2web.swdl.common.domain.application;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.common.Identifiable;
/*    */ import java.io.Serializable;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Application
/*    */   implements Identifiable, Serializable, Cloneable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 19 */   private String identifier = null;
/*    */   
/* 21 */   private String description = null;
/*    */   
/* 23 */   private Set versions = null;
/*    */   
/* 25 */   private Set resourceIDs = null;
/*    */ 
/*    */   
/*    */   public Application(String identifier, String description, Set resourceIDs) {
/* 29 */     this.identifier = identifier;
/* 30 */     this.description = description;
/* 31 */     this.resourceIDs = resourceIDs;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 35 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 39 */     return this.description;
/*    */   }
/*    */   
/*    */   public Set getVersions() {
/* 43 */     return this.versions;
/*    */   }
/*    */   
/*    */   public void setVersions(Set versions) {
/* 47 */     this.versions = versions;
/*    */   }
/*    */   
/*    */   public Object clone() {
/* 51 */     return new Application(this.identifier, this.description, this.resourceIDs);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 55 */     return this.description;
/*    */   }
/*    */   
/*    */   public Set getResourceIDs() {
/* 59 */     return this.resourceIDs;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\application\Application.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */