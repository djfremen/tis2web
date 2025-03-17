/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.db.ILTCache;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTAWKSchluessel
/*     */ {
/*  13 */   private String nr = null;
/*     */ 
/*     */   
/*  16 */   private String awtext = null;
/*     */ 
/*     */   
/*  19 */   private String sx = null;
/*     */ 
/*     */   
/*  22 */   private String aw = null;
/*     */ 
/*     */   
/*     */   private boolean change_flag = false;
/*     */ 
/*     */   
/*  28 */   private List qualList = null;
/*     */   
/*  30 */   private static ILTCache cache = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void connect2Cache(ILTCache c) {
/*  40 */     cache = c;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNr() {
/*  57 */     return this.nr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNr(String nr) {
/*  67 */     this.nr = nr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAwtext() {
/*  76 */     return this.awtext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAwtext(String awtext) {
/*  86 */     this.awtext = awtext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSx() {
/*  95 */     return this.sx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSx(String sx) {
/* 105 */     this.sx = sx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAw() {
/* 114 */     return this.aw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAw(String aw) {
/* 124 */     this.aw = aw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChange_flag() {
/* 133 */     return this.change_flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChange_flag(boolean change_flag) {
/* 143 */     this.change_flag = change_flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getQualList() {
/* 152 */     return this.qualList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQualList(List qualList) {
/* 162 */     this.qualList = qualList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAWText(Integer lc) {
/* 168 */     if (this.awtext == null) {
/* 169 */       this.awtext = new String();
/*     */     }
/* 171 */     if (this.awtext.length() > 0) {
/* 172 */       return this.awtext;
/*     */     }
/*     */     
/* 175 */     Iterator<LTAWKQual> it = this.qualList.iterator();
/*     */     
/* 177 */     LTAWKQual curQual = null;
/* 178 */     while (it.hasNext()) {
/* 179 */       curQual = it.next();
/*     */ 
/*     */       
/* 182 */       if (curQual.getGueltigkeit() != '=') {
/*     */         
/* 184 */         this.awtext += cache.findOperatorCacheElement(lc, curQual.getGueltigkeit());
/* 185 */         this.awtext += ' ';
/*     */       } 
/*     */ 
/*     */       
/* 189 */       this.awtext += curQual.getAwBeschreibung(lc);
/* 190 */       this.awtext += curQual.getOperator();
/* 191 */       this.awtext += ' ';
/*     */     } 
/* 193 */     return this.awtext;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTAWKSchluessel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */