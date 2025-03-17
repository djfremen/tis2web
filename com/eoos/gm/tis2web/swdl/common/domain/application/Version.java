/*     */ package com.eoos.gm.tis2web.swdl.common.domain.application;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.common.Identifiable;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Version
/*     */   implements Identifiable, Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -1L;
/*     */   private String identifier;
/*     */   private String number;
/*     */   private Long date;
/*     */   private Long size;
/*     */   private String additionalInfo;
/*     */   private Application application;
/*     */   private Set neutralFiles;
/*     */   private Map lang2files;
/*     */   
/*     */   public Version(String id, String no, Long date, Long size, Application app, String additionalInfo) {
/*  41 */     this.identifier = id;
/*  42 */     this.number = no;
/*  43 */     this.date = date;
/*  44 */     this.size = size;
/*  45 */     this.application = app;
/*  46 */     this.additionalInfo = additionalInfo;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/*  50 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public String getNumber() {
/*  54 */     return this.number;
/*     */   }
/*     */   
/*     */   public Long getDate() {
/*  58 */     return this.date;
/*     */   }
/*     */   
/*     */   public Long getSize() {
/*  62 */     return this.size;
/*     */   }
/*     */   
/*     */   public String getAdditionalInfo() {
/*  66 */     return this.additionalInfo;
/*     */   }
/*     */   
/*     */   public Set getFiles(Language lang) {
/*  70 */     return (Set)this.lang2files.get(lang);
/*     */   }
/*     */   
/*     */   public Set getLanguages() {
/*  74 */     return new HashSet(this.lang2files.keySet());
/*     */   }
/*     */   
/*     */   public void setLan2Files(Map lang2files) {
/*  78 */     this.lang2files = lang2files;
/*     */   }
/*     */   
/*     */   public Set getLanNeutralFiles() {
/*  82 */     return this.neutralFiles;
/*     */   }
/*     */   
/*     */   public void setLanNeutralFiles(Set neutralFiles) {
/*  86 */     this.neutralFiles = neutralFiles;
/*     */   }
/*     */   
/*     */   public Application getApplication() {
/*  90 */     return this.application;
/*     */   }
/*     */   
/*     */   public static Set cloneSetFiles(Set files, Version vers) {
/*  94 */     Set<FileProxy> cloneFiles = new HashSet();
/*  95 */     Iterator<FileProxy> it = files.iterator();
/*  96 */     while (it.hasNext()) {
/*  97 */       FileProxy file = it.next();
/*  98 */       cloneFiles.add(new FileProxy((String)file.getIdentifier(), file.getName(), file.getRevision(), file.getSize(), file.getStatus(), file.getType(), file.getFingerprint(), vers));
/*     */     } 
/* 100 */     return cloneFiles;
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 104 */     Version cloneVers = new Version(this.identifier, this.number, this.date, this.size, (Application)this.application.clone(), this.additionalInfo);
/* 105 */     return cloneVers;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 109 */     return this.number;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\application\Version.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */