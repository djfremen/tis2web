/*     */ package com.eoos.datatype;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.tokenizer.v2.CSTokenizer;
/*     */ import com.eoos.util.HashCalc;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class VersionNumber
/*     */   implements IVersionNumber
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  15 */   private static final String[] DEFAULT_DEL = new String[] { ".", "-" };
/*     */ 
/*     */   
/*     */   private String orgVersionString;
/*     */ 
/*     */ 
/*     */   
/*     */   public static class StructureException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */   }
/*     */   
/*  28 */   private List parts = Collections.EMPTY_LIST;
/*     */   
/*  30 */   private String[] delimiters = DEFAULT_DEL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object createPart(String token) {
/*  37 */     boolean digitOnly = true;
/*  38 */     for (int i = 0; i < token.length() && digitOnly; i++) {
/*  39 */       digitOnly = (digitOnly && Character.isDigit(token.charAt(i)));
/*     */     }
/*  41 */     if (digitOnly) {
/*  42 */       return Integer.valueOf(token);
/*     */     }
/*  44 */     return token;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static VersionNumber parse(String versionString, String[] delimiters) {
/*  50 */     if (versionString == null || delimiters == null) {
/*  51 */       throw new IllegalArgumentException();
/*     */     }
/*  53 */     VersionNumber versionNumber = new VersionNumber();
/*  54 */     versionNumber.orgVersionString = versionString;
/*  55 */     versionNumber.delimiters = delimiters;
/*  56 */     versionNumber.parts = new LinkedList();
/*  57 */     for (CSTokenizer tokenizer = new CSTokenizer(versionString, delimiters); tokenizer.hasNext(); ) {
/*  58 */       String token = tokenizer.next().toString().trim();
/*  59 */       if (token.length() > 0) {
/*  60 */         versionNumber.parts.add(createPart(token));
/*     */       }
/*     */     } 
/*     */     
/*  64 */     versionNumber.parts = normalizePartList(versionNumber.parts);
/*  65 */     return versionNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public static VersionNumber parse(String versionString) {
/*  70 */     return parse(versionString, DEFAULT_DEL);
/*     */   }
/*     */   
/*     */   public boolean isEqualTo(String _version) {
/*  74 */     boolean retValue = false;
/*     */     try {
/*  76 */       VersionNumber versionNumber = parse(_version, this.delimiters);
/*  77 */       retValue = equals(versionNumber);
/*  78 */     } catch (Exception e) {}
/*     */     
/*  80 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean isHigherThan(VersionNumber versionNumber) throws StructureException {
/*  84 */     int comp = 0;
/*  85 */     int size = Math.min(this.parts.size(), versionNumber.parts.size());
/*  86 */     for (int i = 0; i < size; i++) {
/*  87 */       Object part1 = this.parts.get(i);
/*  88 */       Object part2 = versionNumber.parts.get(i);
/*  89 */       if (!part1.getClass().equals(part2.getClass()))
/*  90 */         throw new StructureException(); 
/*  91 */       if (comp == 0) {
/*  92 */         comp = ((Comparable<Object>)part1).compareTo(part2);
/*     */       }
/*     */     } 
/*  95 */     if (comp > 0)
/*  96 */       return true; 
/*  97 */     if (comp == 0 && this.parts.size() > versionNumber.parts.size()) {
/*  98 */       return true;
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHigherThan(String _version) throws StructureException {
/* 105 */     VersionNumber versionNumber = parse(_version, this.delimiters);
/* 106 */     return isHigherThan(versionNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     return this.orgVersionString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toNormalizedString() {
/* 121 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 123 */       for (Iterator iter = this.parts.iterator(); iter.hasNext(); ) {
/* 124 */         tmp.append(String.valueOf(iter.next()));
/* 125 */         if (iter.hasNext()) {
/* 126 */           tmp.append(".");
/*     */         }
/*     */       } 
/* 129 */       if (this.parts.size() > 1 && this.parts.get(this.parts.size() - 1) instanceof CharSequence) {
/* 130 */         int index = tmp.lastIndexOf(".");
/* 131 */         tmp.replace(index, index + 1, "-");
/*     */       } 
/* 133 */       return tmp.toString();
/*     */     } finally {
/*     */       
/* 136 */       StringBufferPool.getThreadInstance().free(tmp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 141 */     int retValue = VersionNumber.class.hashCode();
/* 142 */     retValue = HashCalc.addHashCode(retValue, this.parts);
/* 143 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 147 */     boolean retValue = false;
/* 148 */     if (this == obj) {
/* 149 */       retValue = true;
/* 150 */     } else if (obj instanceof VersionNumber) {
/* 151 */       VersionNumber versionNumber = (VersionNumber)obj;
/* 152 */       retValue = this.parts.equals(versionNumber.parts);
/*     */     } 
/* 154 */     return retValue;
/*     */   }
/*     */   
/*     */   public int compareTo(Object obj) {
/* 158 */     VersionNumber versionNumber = (VersionNumber)obj;
/*     */     
/* 160 */     int ret = 0;
/* 161 */     int size = Math.min(this.parts.size(), versionNumber.parts.size());
/* 162 */     for (int i = 0; i < size && ret == 0; i++) {
/* 163 */       Object part1 = this.parts.get(i);
/* 164 */       Object part2 = versionNumber.parts.get(i);
/* 165 */       if (!part1.getClass().equals(part2.getClass())) {
/* 166 */         part1 = String.valueOf(part1);
/* 167 */         part2 = String.valueOf(part2);
/*     */       } 
/* 169 */       ret = ((Comparable<Object>)part1).compareTo(part2);
/*     */     } 
/*     */ 
/*     */     
/* 173 */     if (ret == 0 && 
/* 174 */       !equals(versionNumber)) {
/* 175 */       ret = this.parts.size() - versionNumber.parts.size();
/*     */     }
/*     */     
/* 178 */     return ret;
/*     */   }
/*     */   
/*     */   private static List normalizePartList(List<?> parts) {
/* 182 */     List<?> ret = parts;
/* 183 */     if (ret != null) {
/* 184 */       int remove = 0;
/* 185 */       for (int i = parts.size() - 1; i >= 0; ) {
/* 186 */         Object part = parts.get(i);
/* 187 */         if (String.valueOf(part).equals("0")) {
/* 188 */           remove++;
/*     */           
/*     */           i--;
/*     */         } 
/*     */       } 
/* 193 */       if (remove > 0) {
/* 194 */         ret = new LinkedList(parts.subList(0, parts.size() - remove));
/*     */       }
/*     */     } 
/* 197 */     return ret;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws StructureException {
/* 201 */     VersionNumber vn1 = parse("1.5.0.0.0");
/* 202 */     VersionNumber vn2 = parse("1.5");
/* 203 */     System.out.println(vn1.equals(vn2));
/* 204 */     System.out.println(vn1.isHigherThan(vn2));
/* 205 */     System.out.println(vn1.isEqualTo("1.5.0  "));
/* 206 */     System.out.println(vn1.isHigherThan("1.5.0 "));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\VersionNumber.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */