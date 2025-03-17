/*     */ package com.eoos.datatype;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.tokenizer.v2.CSTokenizer;
/*     */ import com.eoos.util.HashCalc;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Version
/*     */   implements IVersion, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  16 */   private static final String[] DEFAULT_DEL = new String[] { ".", "-" };
/*     */   
/*     */   private String orgVersionString;
/*     */   
/*  20 */   private List parts = Collections.EMPTY_LIST;
/*     */   
/*  22 */   private String[] delimiters = DEFAULT_DEL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object createPart(String token) {
/*  29 */     boolean digitOnly = true;
/*  30 */     for (int i = 0; i < token.length() && digitOnly; i++) {
/*  31 */       digitOnly = (digitOnly && Character.isDigit(token.charAt(i)));
/*     */     }
/*  33 */     if (digitOnly) {
/*  34 */       return Integer.valueOf(token);
/*     */     }
/*  36 */     return token;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Version parse(String versionString, String[] delimiters) {
/*  42 */     if (versionString == null || delimiters == null) {
/*  43 */       throw new IllegalArgumentException();
/*     */     }
/*  45 */     Version version = new Version();
/*  46 */     version.orgVersionString = versionString;
/*  47 */     version.delimiters = delimiters;
/*  48 */     version.parts = new LinkedList();
/*  49 */     for (CSTokenizer tokenizer = new CSTokenizer(versionString, delimiters); tokenizer.hasNext(); ) {
/*  50 */       String token = String.valueOf(tokenizer.next());
/*  51 */       version.parts.add(createPart(token));
/*     */     } 
/*     */     
/*  54 */     return version;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Version parse(String versionString) {
/*  59 */     return parse(versionString, DEFAULT_DEL);
/*     */   }
/*     */   
/*     */   public boolean isEqualTo(String _version) {
/*  63 */     boolean retValue = false;
/*     */     try {
/*  65 */       Version version = parse(_version, this.delimiters);
/*  66 */       retValue = equals(version);
/*  67 */     } catch (Exception e) {}
/*     */     
/*  69 */     return retValue;
/*     */   }
/*     */   
/*     */   private boolean isHigherThan(Version version) throws IVersion.StructureException {
/*  73 */     int comp = 0;
/*  74 */     int size = Math.min(this.parts.size(), version.parts.size());
/*  75 */     for (int i = 0; i < size; i++) {
/*  76 */       Object part1 = this.parts.get(i);
/*  77 */       Object part2 = version.parts.get(i);
/*  78 */       if (!part1.getClass().equals(part2.getClass()))
/*  79 */         throw new IVersion.StructureException(); 
/*  80 */       if (comp == 0) {
/*  81 */         comp = ((Comparable<Object>)part1).compareTo(part2);
/*     */       }
/*     */     } 
/*  84 */     if (comp > 0)
/*  85 */       return true; 
/*  86 */     if (comp == 0 && this.parts.size() > version.parts.size()) {
/*  87 */       return true;
/*     */     }
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHigherThan(String _version) throws IVersion.StructureException {
/*  94 */     Version version = parse(_version, this.delimiters);
/*  95 */     return isHigherThan(version);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return this.orgVersionString;
/*     */   }
/*     */   
/*     */   public String toNormalizedString() {
/* 104 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 106 */       for (Iterator iter = this.parts.iterator(); iter.hasNext(); ) {
/* 107 */         tmp.append(String.valueOf(iter.next()));
/* 108 */         if (iter.hasNext()) {
/* 109 */           tmp.append(".");
/*     */         }
/*     */       } 
/* 112 */       if (this.parts.size() > 1 && this.parts.get(this.parts.size() - 1) instanceof CharSequence) {
/* 113 */         int index = tmp.lastIndexOf(".");
/* 114 */         tmp.replace(index, index + 1, "-");
/*     */       } 
/* 116 */       return tmp.toString();
/*     */     } finally {
/*     */       
/* 119 */       StringBufferPool.getThreadInstance().free(tmp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 124 */     int retValue = IVersion.class.hashCode();
/* 125 */     retValue = HashCalc.addHashCode(retValue, this.parts);
/* 126 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 130 */     boolean retValue = false;
/* 131 */     if (this == obj) {
/* 132 */       retValue = true;
/* 133 */     } else if (obj instanceof Version) {
/* 134 */       Version version = (Version)obj;
/* 135 */       retValue = this.parts.equals(version.parts);
/*     */     } 
/* 137 */     return retValue;
/*     */   }
/*     */   
/*     */   public int compareTo(Object obj) {
/* 141 */     Version version = (Version)obj;
/*     */     try {
/* 143 */       if (isHigherThan(version))
/* 144 */         return 1; 
/* 145 */       if (equals(version)) {
/* 146 */         return 0;
/*     */       }
/* 148 */       return -1;
/*     */     }
/* 150 */     catch (StructureException e) {
/* 151 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\Version.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */