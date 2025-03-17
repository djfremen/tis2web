/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter;
/*     */ 
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKElement;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKGruppen;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKQual;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.interpreter.TriStateLogic;
/*     */ import java.util.Iterator;
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
/*     */ public class LTNOPSolver
/*     */   extends LTSolver
/*     */ {
/*     */   private LTAWKQual qual;
/*     */   
/*     */   public LTNOPSolver(LTAWKQual qual, AWKInterpreter i) {
/*  25 */     super(i);
/*  26 */     this.qual = qual;
/*     */   }
/*     */   
/*     */   public TriStateLogic apply() {
/*  30 */     char validation = this.qual.getGueltigkeit();
/*     */     
/*  32 */     switch (validation) {
/*     */       case '=':
/*  34 */         return doesItemMatch();
/*     */       case '-':
/*  36 */         if (this.inter.getVC().getEngine().length() == 0)
/*     */         {
/*     */ 
/*     */           
/*  40 */           return TriStateLogic.UNKNOWN;
/*     */         }
/*  42 */         return doesEngineMatch().not();
/*     */       case '<':
/*  44 */         if (this.inter.getVC().getYear().length() == 0) {
/*  45 */           return TriStateLogic.UNKNOWN;
/*     */         }
/*  47 */         return doesYearMatch(false);
/*     */       case '>':
/*  49 */         if (this.inter.getVC().getYear().length() == 0) {
/*  50 */           return TriStateLogic.UNKNOWN;
/*     */         }
/*  52 */         return doesYearMatch(true);
/*     */     } 
/*     */     
/*  55 */     return TriStateLogic.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   private TriStateLogic doesEngineMatch() {
/*  60 */     if (this.inter.getVC().getEngine().length() > 0) {
/*  61 */       return TriStateLogic.UNKNOWN;
/*     */     }
/*     */ 
/*     */     
/*  65 */     for (Iterator<LTAWKElement> eit = this.qual.getElementeList().iterator(); eit.hasNext(); ) {
/*  66 */       LTAWKElement e = eit.next();
/*     */       
/*  68 */       if (e.getAwElementTyp() != 'M')
/*     */       {
/*  70 */         return TriStateLogic.FALSE;
/*     */       }
/*     */       
/*  73 */       if (e.isAwGruppenIndikator() && e.getGruppenList() != null) {
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
/*  87 */         Iterator<LTAWKGruppen> it = e.getGruppenList().iterator(); if (it.hasNext()) {
/*  88 */           LTAWKGruppen g = it.next();
/*     */ 
/*     */           
/*  91 */           if (g.getAwElementVerweis().equals(this.inter.getVC().getEngine())) {
/*  92 */             return TriStateLogic.TRUE;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 100 */           return TriStateLogic.FALSE;
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 105 */       if (!e.getAwElementVerweis().equals(this.inter.getVC().getEngine()))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 110 */         return TriStateLogic.FALSE;
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return TriStateLogic.TRUE;
/*     */   }
/*     */ 
/*     */   
/*     */   private TriStateLogic validate(char elemtyp, String what) {
/* 119 */     switch (elemtyp) {
/*     */       case 'M':
/* 121 */         if (this.inter.getVC().getEngine().length() == 0)
/*     */         {
/* 123 */           return TriStateLogic.UNKNOWN;
/*     */         }
/*     */         
/* 126 */         if (what.equals(this.inter.getVC().getEngine())) {
/* 127 */           return TriStateLogic.TRUE;
/*     */         }
/*     */         break;
/*     */       case 'J':
/* 131 */         if (this.inter.getVC().getYear().length() == 0) {
/* 132 */           return TriStateLogic.UNKNOWN;
/*     */         }
/*     */         
/* 135 */         if (what.equals(this.inter.getVC().getYear())) {
/* 136 */           return TriStateLogic.TRUE;
/*     */         }
/*     */         break;
/*     */       case 'G':
/* 140 */         if (this.inter.getVC().getTransmission().length() == 0)
/*     */         {
/* 142 */           return TriStateLogic.UNKNOWN;
/*     */         }
/*     */         
/* 145 */         if (what.equals(this.inter.getVC().getTransmission())) {
/* 146 */           return TriStateLogic.TRUE;
/*     */         }
/*     */         break;
/*     */       case 'K':
/* 150 */         return TriStateLogic.UNKNOWN;
/*     */       
/*     */       case 'S':
/* 153 */         return TriStateLogic.UNKNOWN;
/*     */     } 
/*     */ 
/*     */     
/* 157 */     return TriStateLogic.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   private TriStateLogic doesItemMatch() {
/* 162 */     if (this.qual == null) {
/* 163 */       return new TriStateLogic(0);
/*     */     }
/*     */ 
/*     */     
/* 167 */     for (Iterator<LTAWKElement> it = this.qual.getElementeList().iterator(); it.hasNext(); ) {
/* 168 */       LTAWKElement e = it.next();
/* 169 */       char elemtyp = e.getAwElementTyp();
/*     */       
/* 171 */       if (e.isAwGruppenIndikator() && e.getGruppenList() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 179 */         boolean bNoElements4Group = true;
/*     */ 
/*     */         
/* 182 */         for (Iterator<LTAWKGruppen> itg = e.getGruppenList().iterator(); itg.hasNext(); ) {
/* 183 */           LTAWKGruppen g = itg.next();
/*     */           
/* 185 */           bNoElements4Group = false;
/*     */           
/* 187 */           TriStateLogic triStateLogic = validate(elemtyp, g.getAwElementVerweis());
/*     */           
/* 189 */           if (!triStateLogic.equals(0))
/*     */           {
/* 191 */             return triStateLogic;
/*     */           }
/*     */           
/* 194 */           if (bNoElements4Group) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 201 */             switch (elemtyp) {
/*     */               case 'M':
/* 203 */                 if (this.inter.getVC().getEngine().length() == 0)
/*     */                 {
/*     */ 
/*     */ 
/*     */                   
/* 208 */                   return TriStateLogic.UNKNOWN;
/*     */                 }
/*     */                 break;
/*     */               case 'J':
/* 212 */                 if (this.inter.getVC().getYear().length() == 0) {
/* 213 */                   return TriStateLogic.UNKNOWN;
/*     */                 }
/*     */                 break;
/*     */               case 'G':
/* 217 */                 if (this.inter.getVC().getTransmission().length() == 0)
/*     */                 {
/* 219 */                   return TriStateLogic.UNKNOWN;
/*     */                 }
/*     */                 break;
/*     */               case 'K':
/* 223 */                 return TriStateLogic.UNKNOWN;
/*     */               case 'S':
/* 225 */                 return TriStateLogic.UNKNOWN;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 230 */             return TriStateLogic.FALSE;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 237 */       TriStateLogic t = validate(elemtyp, e.getAwElementVerweis());
/*     */       
/* 239 */       if (!t.equals(TriStateLogic.FALSE))
/*     */       {
/* 241 */         return t;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 246 */     return TriStateLogic.FALSE;
/*     */   }
/*     */ 
/*     */   
/*     */   private TriStateLogic doesYearMatch(boolean greater) {
/* 251 */     if (this.inter.getVC().getYear().length() == 0) {
/* 252 */       return TriStateLogic.UNKNOWN;
/*     */     }
/* 254 */     int intYear = Integer.valueOf(this.inter.getVC().getYear()).intValue();
/*     */ 
/*     */     
/* 257 */     for (Iterator<LTAWKElement> it = this.qual.getElementeList().iterator(); it.hasNext(); ) {
/* 258 */       LTAWKElement e = it.next();
/*     */       
/* 260 */       boolean bMYGroupEmpty = true;
/* 261 */       if (e.getAwElementTyp() != 'J') {
/* 262 */         return TriStateLogic.FALSE;
/*     */       }
/* 264 */       if (e.isAwGruppenIndikator() && e.getGruppenList() != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 272 */         for (Iterator<LTAWKGruppen> itg = e.getGruppenList().iterator(); itg.hasNext(); ) {
/* 273 */           LTAWKGruppen g = itg.next();
/*     */           
/* 275 */           bMYGroupEmpty = false;
/* 276 */           if (greater && Integer.valueOf(g.getAwElementVerweis()).intValue() > intYear) {
/* 277 */             return TriStateLogic.FALSE;
/*     */           }
/* 279 */           if (!greater && Integer.valueOf(g.getAwElementVerweis()).intValue() < intYear) {
/* 280 */             return TriStateLogic.FALSE;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 285 */         if (bMYGroupEmpty) {
/* 286 */           return TriStateLogic.FALSE;
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 291 */       int ival = Integer.valueOf(e.getAwElementVerweis()).intValue();
/* 292 */       if (greater && ival > intYear) {
/* 293 */         return TriStateLogic.FALSE;
/*     */       }
/* 295 */       if (!greater && ival < intYear) {
/* 296 */         return TriStateLogic.FALSE;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     return TriStateLogic.TRUE;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\awkinterpreter\LTNOPSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */