/*     */ package com.eoos.gm.tis2web.sps.client.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ 
/*     */ public class ExtensionFileFilter
/*     */   extends FileFilter
/*     */ {
/*  12 */   private Hashtable filters = null;
/*  13 */   private String description = null;
/*  14 */   private String fullDescription = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean useExtensionsInDescription = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionFileFilter() {
/*  24 */     this.filters = new Hashtable<Object, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionFileFilter(String extension) {
/*  34 */     this(extension, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionFileFilter(String extension, String description) {
/*  45 */     this();
/*  46 */     if (extension != null)
/*  47 */       addExtension(extension); 
/*  48 */     if (description != null) {
/*  49 */       setDescription(description);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionFileFilter(String[] filters) {
/*  60 */     this(filters, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionFileFilter(String[] filters, String description) {
/*  72 */     this();
/*  73 */     for (int i = 0; i < filters.length; i++)
/*     */     {
/*  75 */       addExtension(filters[i]);
/*     */     }
/*  77 */     if (description != null) {
/*  78 */       setDescription(description);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(File f) {
/*  89 */     if (f != null) {
/*  90 */       if (f.isDirectory()) {
/*  91 */         return true;
/*     */       }
/*  93 */       String extension = getExtension(f);
/*  94 */       if (extension != null && this.filters.get(getExtension(f)) != null) {
/*  95 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtension(File f) {
/* 109 */     if (f != null) {
/* 110 */       String filename = f.getName();
/* 111 */       int i = filename.lastIndexOf('.');
/* 112 */       if (i > 0 && i < filename.length() - 1) {
/* 113 */         return filename.substring(i + 1).toLowerCase(Locale.ENGLISH);
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExtension(String extension) {
/* 129 */     if (this.filters == null) {
/* 130 */       this.filters = new Hashtable<Object, Object>(5);
/*     */     }
/* 132 */     this.filters.put(extension.toLowerCase(Locale.ENGLISH), this);
/* 133 */     this.fullDescription = null;
/*     */   }
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
/*     */   public String getDescription() {
/* 146 */     if (this.fullDescription == null) {
/* 147 */       if (this.description == null || isExtensionListInDescription()) {
/* 148 */         this.fullDescription = (this.description == null) ? "(" : (this.description + " (");
/*     */         
/* 150 */         Enumeration<String> extensions = this.filters.keys();
/* 151 */         if (extensions != null) {
/* 152 */           this.fullDescription += "." + (String)extensions.nextElement();
/* 153 */           while (extensions.hasMoreElements()) {
/* 154 */             this.fullDescription += ", ." + (String)extensions.nextElement();
/*     */           }
/*     */         } 
/* 157 */         this.fullDescription += ")";
/*     */       } else {
/* 159 */         this.fullDescription = this.description;
/*     */       } 
/*     */     }
/* 162 */     return this.fullDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/* 174 */     this.description = description;
/* 175 */     this.fullDescription = null;
/*     */   }
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
/*     */   public void setExtensionListInDescription(boolean b) {
/* 188 */     this.useExtensionsInDescription = b;
/* 189 */     this.fullDescription = null;
/*     */   }
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
/*     */   public boolean isExtensionListInDescription() {
/* 202 */     return this.useExtensionsInDescription;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\util\ExtensionFileFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */