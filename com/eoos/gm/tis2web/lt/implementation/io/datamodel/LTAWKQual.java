/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTAWKQual
/*     */   implements Cloneable
/*     */ {
/*  11 */   private String awBeschreibung = null;
/*     */ 
/*     */   
/*  14 */   private byte ordnung = 0;
/*     */ 
/*     */   
/*  17 */   private char operator = Character.MIN_VALUE;
/*     */ 
/*     */   
/*  20 */   private char gueltigkeit = Character.MIN_VALUE;
/*     */ 
/*     */   
/*  23 */   private String awElement = null;
/*     */ 
/*     */   
/*  26 */   private List elementeList = null;
/*     */   
/*     */   public Object clone() {
/*  29 */     LTAWKQual oq = new LTAWKQual();
/*  30 */     oq.awBeschreibung = this.awBeschreibung;
/*  31 */     oq.ordnung = this.ordnung;
/*  32 */     oq.operator = this.operator;
/*  33 */     oq.gueltigkeit = this.gueltigkeit;
/*  34 */     oq.awElement = this.awElement;
/*  35 */     oq.elementeList = this.elementeList;
/*  36 */     return oq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAwBeschreibung(Integer lc) {
/*  46 */     if (this.awBeschreibung == null) {
/*  47 */       this.awBeschreibung = new String();
/*     */     }
/*  49 */     if (this.awBeschreibung.length() > 0) {
/*  50 */       return this.awBeschreibung;
/*     */     }
/*  52 */     Iterator<LTAWKElement> it = null;
/*  53 */     if (this.elementeList != null) {
/*  54 */       it = this.elementeList.iterator();
/*     */     }
/*     */     
/*  57 */     LTAWKElement curelem = null;
/*  58 */     while (it != null && it.hasNext()) {
/*  59 */       curelem = it.next();
/*  60 */       this.awBeschreibung += ' ';
/*  61 */       String res = curelem.getAwBeschreibung(lc);
/*  62 */       if (this.awBeschreibung.indexOf(res) == -1)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  68 */         this.awBeschreibung += res; } 
/*     */     } 
/*  70 */     return this.awBeschreibung;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAwBeschreibung(String awBeschreibung) {
/*  80 */     this.awBeschreibung = awBeschreibung;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getOrdnung() {
/*  89 */     return this.ordnung;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOrdnung(byte ordnung) {
/*  99 */     this.ordnung = ordnung;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getOperator() {
/* 108 */     return this.operator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOperator(char operator) {
/* 118 */     this.operator = operator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char getGueltigkeit() {
/* 127 */     return this.gueltigkeit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGueltigkeit(char gueltigkeit) {
/* 137 */     this.gueltigkeit = gueltigkeit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAwElement() {
/* 146 */     return this.awElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAwElement(String awElement) {
/* 156 */     this.awElement = awElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getElementeList() {
/* 165 */     return this.elementeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementeList(List elementeList) {
/* 175 */     this.elementeList = elementeList;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTAWKQual.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */