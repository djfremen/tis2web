/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVIN;
/*     */ 
/*     */ public class SPSVIN implements SPSVIN {
/*   6 */   private static int[] modelYearsALPHA = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, -1, 8, 9, 10, 11, 12, -1, 13, -1, 14, 15, 16, -1, 17, 18, 19, 20, -1 };
/*     */   
/*   8 */   private static int[] modelYearsNUMERIC = new int[] { -1, 21, 22, 23, 24, 25, 26, 27, 28, 29 };
/*     */   
/*     */   protected static final int MAKE = 3;
/*     */   
/*     */   protected static final int GWVR = 4;
/*     */   
/*     */   protected static final int LINE = 5;
/*     */   
/*     */   protected static final int BODY = 6;
/*     */   
/*     */   protected static final int SERIES = 6;
/*     */   
/*     */   protected static final int RESTRAINTS = 7;
/*     */   
/*     */   protected static final int MODEL_YEAR_INTERPRETATION = 7;
/*     */   
/*     */   protected static final int ENGINE = 8;
/*     */   
/*     */   protected static final int CHECK_DIGIT = 9;
/*     */   
/*     */   protected static final int MODEL_YEAR = 10;
/*     */   
/*     */   protected String vin;
/*     */   
/*     */   protected boolean isTruck;
/*     */   
/*     */   protected char line;
/*     */   
/*     */   protected char series;
/*     */   
/*     */   protected String sLineSeries;
/*     */   
/*     */   public SPSVIN(String vin) {
/*  41 */     this.vin = vin;
/*  42 */     init();
/*     */   }
/*     */   
/*     */   public String toString() {
/*  46 */     return this.vin;
/*     */   }
/*     */   
/*     */   public boolean validate() {
/*  50 */     return (new VINVerifier()).isCorrect(this.vin);
/*     */   }
/*     */   
/*     */   public char getPosition(int pos) {
/*  54 */     return this.vin.charAt(pos - 1);
/*     */   }
/*     */   
/*     */   public String getWMI() {
/*  58 */     return this.vin.substring(0, 3);
/*     */   }
/*     */   
/*     */   public char getMake() {
/*  62 */     return getPosition(3);
/*     */   }
/*     */   
/*     */   public String getLineSeries() {
/*  66 */     return this.sLineSeries;
/*     */   }
/*     */   
/*     */   public char getLine() {
/*  70 */     return this.line;
/*     */   }
/*     */   
/*     */   public char getSeries() {
/*  74 */     return this.series;
/*     */   }
/*     */   
/*     */   public char getEngine() {
/*  78 */     return getPosition(8);
/*     */   }
/*     */   
/*     */   public char getCheckDigit() {
/*  82 */     return getPosition(9);
/*     */   }
/*     */   
/*     */   public int getModelYearVC() {
/*  86 */     int my = 0;
/*  87 */     char myc = getPosition(10);
/*  88 */     if (Character.isLetter(myc)) {
/*  89 */       my = modelYearsALPHA[myc - 65];
/*     */     } else {
/*  91 */       my = modelYearsNUMERIC[myc - 48];
/*     */     } 
/*  93 */     if (Character.isLetter(getPosition(7))) {
/*  94 */       return 2010 + my;
/*     */     }
/*  96 */     return 1980 + my;
/*     */   }
/*     */ 
/*     */   
/*     */   public char getModelYear() {
/* 101 */     if (Character.isLetter(getPosition(7))) {
/* 102 */       return getPosition(10);
/*     */     }
/* 104 */     return getPosition(10);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSequence() {
/* 109 */     return this.vin.substring(11);
/*     */   }
/*     */   
/*     */   public int getSequenceNo() {
/* 113 */     String sequence = getSequence();
/* 114 */     StringBuffer number = new StringBuffer();
/* 115 */     for (int i = 0; i < sequence.length(); i++) {
/* 116 */       char c = sequence.charAt(i);
/* 117 */       number.append(Character.isDigit(c) ? c : 48);
/*     */     } 
/* 119 */     return Integer.parseInt(number.toString());
/*     */   }
/*     */   
/*     */   public char getPosition4or6() {
/* 123 */     if (this.isTruck) {
/* 124 */       return getPosition(4);
/*     */     }
/* 126 */     return getPosition(6);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTruck() {
/* 131 */     return this.isTruck;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 135 */     return this.vin.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 139 */     return (object != null && object instanceof SPSVIN && ((SPSVIN)object).toString().equals(this.vin));
/*     */   }
/*     */   
/*     */   protected void init() {
/* 143 */     this.line = getPosition(5);
/* 144 */     this.series = getPosition(6);
/* 145 */     this.sLineSeries = "" + this.line + this.series;
/* 146 */     if (Character.isLetter(getMake())) {
/* 147 */       this.isTruck = true;
/*     */     } else {
/* 149 */       char my = getModelYear();
/* 150 */       char make = getMake();
/* 151 */       if (my >= 'B' && my <= 'E') {
/* 152 */         this.series = ' ';
/*     */       } else {
/* 154 */         this.line = getPosition(4);
/* 155 */         this.series = getPosition(5);
/*     */       } 
/* 157 */       if (my >= 'B' && my <= 'G' && (
/* 158 */         make == '0' || make == '5' || make == '8')) {
/* 159 */         this.isTruck = true;
/* 160 */         this.line = getPosition(5);
/* 161 */         this.series = getPosition(6);
/*     */       } 
/*     */       
/* 164 */       this.sLineSeries = "" + this.line + this.series;
/*     */       
/* 166 */       if ((my == 'C' || my == 'D') && make == '1' && 
/* 167 */         this.line == 'W') {
/* 168 */         char engine = getEngine();
/* 169 */         if ("AHJKNV9".indexOf(engine) >= 0) {
/* 170 */           this.series = '1';
/* 171 */           this.sLineSeries = "W1";
/*     */         } else {
/* 173 */           this.sLineSeries = "W ";
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 178 */       if ((my == 'C' || my == 'D' || my == 'E') && make == '3' && 
/* 179 */         this.line == 'M') {
/* 180 */         char engine = getEngine();
/* 181 */         if ("35ERTX".indexOf(engine) >= 0) {
/* 182 */           this.sLineSeries = "M1";
/* 183 */           this.series = '1';
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 188 */       if (my == 'E' && make == '2') {
/* 189 */         char engine = getEngine();
/* 190 */         if (this.line == 'F' && engine == 'R') {
/* 191 */           String ident = "" + getPosition(6) + getPosition(7);
/* 192 */           if (ident.equals("19") || ident.equals("27") || ident.equals("35")) {
/* 193 */             this.sLineSeries = "F1";
/* 194 */             this.series = '1';
/*     */           } 
/* 196 */         } else if (this.line == 'F' && "TXZ".indexOf(engine) >= 0) {
/* 197 */           this.sLineSeries = "F1";
/* 198 */           this.series = '1';
/* 199 */         } else if (this.line == 'L' && engine == 'C') {
/* 200 */           this.sLineSeries = "L1";
/* 201 */           this.series = '1';
/* 202 */         } else if (this.line == 'S' && "AHN".indexOf(engine) >= 0) {
/* 203 */           this.sLineSeries = "S1";
/* 204 */           this.series = '1';
/* 205 */         } else if (this.line == 'T' && "AHN".indexOf(engine) >= 0) {
/* 206 */           this.sLineSeries = "T1";
/* 207 */           this.series = '1';
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static String makeTruckKey(char my, char tmake) {
/* 214 */     return my + ":" + tmake;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSVIN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */