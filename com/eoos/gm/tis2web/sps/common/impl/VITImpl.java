/*     */ package com.eoos.gm.tis2web.sps.common.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.tokenizer.StringTokenizer;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VITImpl
/*     */   implements VIT, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  23 */   protected List vitAttrs = new ArrayList();
/*     */ 
/*     */   
/*     */   public void addAttribute(Pair attr) {
/*  27 */     this.vitAttrs.add(attr);
/*     */   }
/*     */   
/*     */   public void setAttribute(Pair attr) {
/*     */     int i;
/*  32 */     for (i = 0; i < this.vitAttrs.size(); i++) {
/*  33 */       Pair attribute = this.vitAttrs.get(i);
/*  34 */       if (attribute.getFirst().equals(attr.getFirst())) {
/*     */         break;
/*     */       }
/*     */     } 
/*  38 */     if (i < this.vitAttrs.size()) {
/*  39 */       this.vitAttrs.set(i, attr);
/*     */     } else {
/*  41 */       this.vitAttrs.add(attr);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Pair getAttribute(String attrName) {
/*  46 */     for (int i = 0; i < this.vitAttrs.size(); i++) {
/*  47 */       Pair attr = this.vitAttrs.get(i);
/*  48 */       String name = (String)attr.getFirst();
/*  49 */       if (attrName.equals(name)) {
/*  50 */         return attr;
/*     */       }
/*     */     } 
/*  53 */     return null;
/*     */   }
/*     */   
/*     */   public List getAttributes(String attrName) {
/*  57 */     List<Pair> attrs = new ArrayList();
/*  58 */     for (int i = 0; i < this.vitAttrs.size(); i++) {
/*  59 */       Pair attr = this.vitAttrs.get(i);
/*  60 */       String name = (String)attr.getFirst();
/*  61 */       if (attrName.equals(name)) {
/*  62 */         attrs.add(attr);
/*     */       }
/*     */     } 
/*  65 */     return attrs;
/*     */   }
/*     */   
/*     */   public String getAttrValue(String attrName) {
/*  69 */     for (int i = 0; i < this.vitAttrs.size(); i++) {
/*  70 */       Pair attr = this.vitAttrs.get(i);
/*  71 */       String name = (String)attr.getFirst();
/*  72 */       if (attrName.equals(name)) {
/*  73 */         return (String)attr.getSecond();
/*     */       }
/*     */     } 
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   public void setAttributes(List attrs) {
/*  80 */     this.vitAttrs = attrs;
/*     */   }
/*     */   
/*     */   public void addAttributes(List attrs) {
/*  84 */     this.vitAttrs.addAll(attrs);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  88 */     return this.vitAttrs.isEmpty();
/*     */   }
/*     */   
/*     */   public List getAttributes() {
/*  92 */     return this.vitAttrs;
/*     */   }
/*     */   
/*     */   public List getAttrValues(String attrName) {
/*  96 */     List<Object> attrVals = new ArrayList();
/*  97 */     for (int i = 0; i < this.vitAttrs.size(); i++) {
/*  98 */       Pair attr = this.vitAttrs.get(i);
/*  99 */       String name = (String)attr.getFirst();
/* 100 */       if (attrName.equals(name)) {
/* 101 */         attrVals.add(attr.getSecond());
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return attrVals;
/*     */   }
/*     */   
/*     */   public String getAttrValue(String attrName, int index) {
/* 109 */     List<String> attrVals = getAttrValues(attrName);
/* 110 */     if (attrName.equals("snoet") && attrVals.size() > 0) {
/* 111 */       attrVals = getValues4Value(attrVals.get(0), " ");
/*     */     }
/* 113 */     int indx = (index >= 0) ? index : (attrVals.size() + index);
/* 114 */     String attrVal = (indx < attrVals.size() && indx >= 0) ? attrVals.get(indx) : null;
/*     */     
/* 116 */     return attrVal;
/*     */   }
/*     */   
/*     */   public List getValues4Value(String attrVal, String delimiter) {
/* 120 */     List<String> vals = new ArrayList();
/* 121 */     StringTokenizer stringTokenizer = new StringTokenizer(attrVal, delimiter);
/* 122 */     Iterator<String> it = stringTokenizer.iterator();
/*     */     
/* 124 */     while (it.hasNext()) {
/* 125 */       vals.add(trim(it.next()));
/*     */     }
/*     */     
/* 128 */     return vals;
/*     */   }
/*     */   
/*     */   private String trim(String org) {
/* 132 */     String orgStr = org;
/* 133 */     while (orgStr.startsWith("\t") || orgStr.startsWith(" ")) {
/* 134 */       orgStr = orgStr.substring(1);
/*     */     }
/* 136 */     while (orgStr.endsWith("\t") || orgStr.startsWith(" ")) {
/* 137 */       orgStr = orgStr.substring(0, orgStr.length() - 1);
/*     */     }
/* 139 */     return orgStr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\VITImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */