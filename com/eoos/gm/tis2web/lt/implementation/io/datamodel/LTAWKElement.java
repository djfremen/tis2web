/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.db.ILTCache;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LTAWKElement
/*    */ {
/* 11 */   private String awBeschreibung = null;
/*    */   
/*    */   private boolean awGruppenIndikator = false;
/*    */   
/* 15 */   private String awElementVerweis = null;
/*    */   
/* 17 */   private char awElementTyp = Character.MIN_VALUE;
/*    */   
/* 19 */   private List gruppenList = null;
/*    */   
/* 21 */   private static ILTCache cache = null;
/*    */   
/*    */   public static void connect2Cache(ILTCache c) {
/* 24 */     cache = c;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAwBeschreibung(Integer lc) {
/* 32 */     if (this.awBeschreibung == null) {
/* 33 */       this.awBeschreibung = new String();
/*    */     }
/*    */     
/* 36 */     if (this.awBeschreibung.length() > 0) {
/* 37 */       return this.awBeschreibung;
/*    */     }
/*    */ 
/*    */     
/* 41 */     String text = "";
/*    */     
/* 43 */     if (isAwGruppenIndikator() && this.gruppenList != null) {
/* 44 */       if (this.gruppenList != null) {
/* 45 */         this.gruppenList.iterator();
/* 46 */         setAwBeschreibung(cache.findCacheTextElement(lc, getAwElementVerweis()));
/*    */       } 
/*    */     } else {
/* 49 */       if (getAwElementTyp() == 'S' || getAwElementTyp() == 's') {
/* 50 */         text = cache.findCacheTextElement(lc, getAwElementVerweis());
/* 51 */       } else if (getAwElementTyp() == 'K' || getAwElementTyp() == 'k') {
/* 52 */         text = cache.findCacheTextElement(lc, getAwElementVerweis());
/*    */       } 
/*    */       
/* 55 */       if (text.length() > 0) {
/* 56 */         setAwBeschreibung(text);
/*    */       } else {
/*    */         
/* 59 */         setAwBeschreibung(getAwElementVerweis());
/*    */       } 
/* 61 */     }  return this.awBeschreibung;
/*    */   }
/*    */   
/*    */   public void setAwBeschreibung(String awBeschreibung) {
/* 65 */     this.awBeschreibung = awBeschreibung;
/*    */   }
/*    */   
/*    */   public boolean isAwGruppenIndikator() {
/* 69 */     return this.awGruppenIndikator;
/*    */   }
/*    */   
/*    */   public void setAwGruppenIndikator(boolean awGruppenIndikator) {
/* 73 */     this.awGruppenIndikator = awGruppenIndikator;
/*    */   }
/*    */   
/*    */   public String getAwElementVerweis() {
/* 77 */     return this.awElementVerweis;
/*    */   }
/*    */   
/*    */   public void setAwElementVerweis(String awElementVerweis) {
/* 81 */     this.awElementVerweis = awElementVerweis;
/*    */   }
/*    */   
/*    */   public char getAwElementTyp() {
/* 85 */     return this.awElementTyp;
/*    */   }
/*    */   
/*    */   public void setAwElementTyp(char awElementTyp) {
/* 89 */     this.awElementTyp = awElementTyp;
/*    */   }
/*    */   
/*    */   public List getGruppenList() {
/* 93 */     return this.gruppenList;
/*    */   }
/*    */   
/*    */   public void setGruppenList(List gruppenList) {
/* 97 */     this.gruppenList = gruppenList;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTAWKElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */