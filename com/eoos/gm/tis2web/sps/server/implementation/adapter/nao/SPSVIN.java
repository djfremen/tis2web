/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.DBMS;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVIN;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SPSVIN
/*     */   implements SPSVIN {
/*  13 */   private static int[] modelYearsALPHA = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, -1, 8, 9, 10, 11, 12, -1, 13, -1, 14, 15, 16, -1, 17, 18, 19, 20, -1 };
/*     */   
/*  15 */   private static int[] modelYearsNUMERIC = new int[] { -1, 21, 22, 23, 24, 25, 26, 27, 28, 29 };
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
/*     */   protected static final int RESTRAINTS = 7;
/*     */   protected static final int MODEL_YEAR_INTERPRETATION = 7;
/*     */   protected static final int ENGINE = 8;
/*     */   protected static final int CHECK_DIGIT = 9;
/*     */   protected static final int MODEL_YEAR = 10;
/*     */   protected String vin;
/*     */   protected boolean isTruck;
/*     */   protected char line;
/*     */   protected char series;
/*     */   protected String sLineSeries;
/*     */   
/*     */   public static final class StaticData
/*     */   {
/*     */     private Map years;
/*     */     private Map trucks;
/*     */     
/*     */     private StaticData(SPSSchemaAdapterNAO adapter) {
/*  43 */       IDatabaseLink dblink = adapter.getDatabaseLink();
/*  44 */       this.trucks = new HashMap<Object, Object>();
/*  45 */       Connection conn = null;
/*  46 */       DBMS.PreparedStatement stmt = null;
/*  47 */       ResultSet rs = null;
/*     */       try {
/*  49 */         conn = dblink.requestConnection();
/*  50 */         String sql = DBMS.getSQL(dblink, "SELECT Model_Year, Truck_Make, Truck_Description, Car_Make, Car_Description FROM PROM_Convert_Truck_Make");
/*  51 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  52 */         rs = stmt.executeQuery();
/*  53 */         while (rs.next()) {
/*  54 */           String my = rs.getString(1).trim();
/*  55 */           String tm = rs.getString(2).trim();
/*  56 */           String cm = rs.getString(4).trim();
/*     */ 
/*     */           
/*  59 */           this.trucks.put(SPSVIN.makeTruckKey(my.charAt(0), tm.charAt(0)), cm);
/*     */         } 
/*  61 */       } catch (Exception e) {
/*  62 */         this.trucks = null;
/*  63 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  66 */           if (rs != null) {
/*  67 */             rs.close();
/*     */           }
/*  69 */           if (stmt != null) {
/*  70 */             stmt.close();
/*     */           }
/*  72 */           if (this.trucks == null && conn != null) {
/*  73 */             dblink.releaseConnection(conn);
/*     */           }
/*  75 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/*  78 */       this.years = new HashMap<Object, Object>();
/*     */       try {
/*  80 */         String sql = DBMS.getSQL(dblink, "SELECT Model_Year, Year_VIN FROM PROM_U_Model_Year");
/*  81 */         stmt = DBMS.prepareSQLStatement(conn, sql);
/*  82 */         rs = stmt.executeQuery();
/*  83 */         while (rs.next()) {
/*  84 */           Integer my = Integer.valueOf(rs.getInt(1));
/*  85 */           String vin = rs.getString(2).trim();
/*  86 */           this.years.put(vin, my);
/*     */         } 
/*  88 */       } catch (Exception e) {
/*  89 */         throw new RuntimeException(e);
/*     */       } finally {
/*     */         try {
/*  92 */           if (rs != null) {
/*  93 */             rs.close();
/*     */           }
/*  95 */           if (stmt != null) {
/*  96 */             stmt.close();
/*     */           }
/*  98 */           if (conn != null) {
/*  99 */             dblink.releaseConnection(conn);
/*     */           }
/* 101 */         } catch (Exception x) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void init() {}
/*     */ 
/*     */     
/*     */     public Map getYears() {
/* 111 */       return this.years;
/*     */     }
/*     */     
/*     */     public Map getTrucks() {
/* 115 */       return this.trucks;
/*     */     }
/*     */     
/*     */     public static StaticData getInstance(SPSSchemaAdapterNAO adapter) {
/* 119 */       synchronized (adapter.getSyncObject()) {
/* 120 */         StaticData instance = (StaticData)adapter.getObject(StaticData.class);
/* 121 */         if (instance == null) {
/* 122 */           instance = new StaticData(adapter);
/* 123 */           adapter.storeObject(StaticData.class, instance);
/*     */         } 
/* 125 */         return instance;
/*     */       } 
/*     */     }
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
/*     */   public SPSVIN(String vin) {
/* 141 */     this.vin = vin;
/* 142 */     init();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 146 */     return this.vin;
/*     */   }
/*     */   
/*     */   public boolean validate() {
/* 150 */     return (new VINVerifier()).isCorrect(this.vin);
/*     */   }
/*     */   
/*     */   public char getPosition(int pos) {
/* 154 */     return this.vin.charAt(pos - 1);
/*     */   }
/*     */   
/*     */   public String getWMI() {
/* 158 */     return this.vin.substring(0, 3);
/*     */   }
/*     */   
/*     */   public char getMake() {
/* 162 */     return getPosition(3);
/*     */   }
/*     */   
/*     */   public String getLineSeries() {
/* 166 */     return this.sLineSeries;
/*     */   }
/*     */   
/*     */   public char getLine() {
/* 170 */     return this.line;
/*     */   }
/*     */   
/*     */   public char getSeries() {
/* 174 */     return this.series;
/*     */   }
/*     */   
/*     */   public char getEngine() {
/* 178 */     return getPosition(8);
/*     */   }
/*     */   
/*     */   public char getCheckDigit() {
/* 182 */     return getPosition(9);
/*     */   }
/*     */   
/*     */   public int getModelYearVC() {
/* 186 */     int my = 0;
/* 187 */     char myc = getPosition(10);
/* 188 */     if (Character.isLetter(myc)) {
/* 189 */       my = modelYearsALPHA[myc - 65];
/*     */     } else {
/* 191 */       my = modelYearsNUMERIC[myc - 48];
/*     */     } 
/* 193 */     if (Character.isLetter(getPosition(7))) {
/* 194 */       return 2010 + my;
/*     */     }
/* 196 */     return 1980 + my;
/*     */   }
/*     */ 
/*     */   
/*     */   public char getModelYear() {
/* 201 */     if (Character.isLetter(getPosition(7))) {
/* 202 */       return getPosition(10);
/*     */     }
/* 204 */     return getPosition(10);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSequence() {
/* 209 */     return this.vin.substring(11);
/*     */   }
/*     */   
/*     */   public int getSequenceNo() {
/* 213 */     String sequence = getSequence();
/* 214 */     StringBuffer number = new StringBuffer();
/* 215 */     for (int i = 0; i < sequence.length(); i++) {
/* 216 */       char c = sequence.charAt(i);
/* 217 */       number.append(Character.isDigit(c) ? c : 48);
/*     */     } 
/* 219 */     return Integer.parseInt(number.toString());
/*     */   }
/*     */   
/*     */   public char getPosition4or6() {
/* 223 */     if (this.isTruck) {
/* 224 */       return getPosition(4);
/*     */     }
/* 226 */     return getPosition(6);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTruck() {
/* 231 */     return this.isTruck;
/*     */   }
/*     */   
/*     */   public char getTruckMake(SPSSchemaAdapterNAO adapter) {
/* 235 */     if (this.isTruck) {
/* 236 */       String make = (String)StaticData.getInstance(adapter).getTrucks().get(makeTruckKey(getModelYear(), getMake()));
/*     */       
/* 238 */       if (make != null && make.trim().length() > 0) {
/* 239 */         return make.charAt(0);
/*     */       }
/*     */     } 
/* 242 */     return getMake();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 246 */     return this.vin.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 250 */     return (object != null && object instanceof SPSVIN && ((SPSVIN)object).toString().equals(this.vin));
/*     */   }
/*     */   
/*     */   protected void init() {
/* 254 */     boolean isVINRemediation = Character.isLetter(getPosition(7));
/* 255 */     this.line = getPosition(5);
/* 256 */     this.series = getPosition(6);
/* 257 */     this.sLineSeries = "" + this.line + this.series;
/* 258 */     if (Character.isLetter(getMake())) {
/* 259 */       this.isTruck = true;
/*     */     } else {
/* 261 */       char my = getModelYear();
/* 262 */       char make = getMake();
/* 263 */       if (!isVINRemediation && my >= 'B' && my <= 'E') {
/* 264 */         this.series = ' ';
/*     */       } else {
/* 266 */         this.line = getPosition(4);
/* 267 */         this.series = getPosition(5);
/*     */       } 
/* 269 */       if (!isVINRemediation && my >= 'B' && my <= 'G' && (
/* 270 */         make == '0' || make == '5' || make == '8')) {
/* 271 */         this.isTruck = true;
/* 272 */         this.line = getPosition(5);
/* 273 */         this.series = getPosition(6);
/*     */       } 
/*     */       
/* 276 */       this.sLineSeries = "" + this.line + this.series;
/*     */       
/* 278 */       if (!isVINRemediation && (my == 'C' || my == 'D') && make == '1' && 
/* 279 */         this.line == 'W') {
/* 280 */         char engine = getEngine();
/* 281 */         if ("AHJKNV9".indexOf(engine) >= 0) {
/* 282 */           this.series = '1';
/* 283 */           this.sLineSeries = "W1";
/*     */         } else {
/* 285 */           this.sLineSeries = "W ";
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 290 */       if (!isVINRemediation && (my == 'C' || my == 'D' || my == 'E') && make == '3' && 
/* 291 */         this.line == 'M') {
/* 292 */         char engine = getEngine();
/* 293 */         if ("35ERTX".indexOf(engine) >= 0) {
/* 294 */           this.sLineSeries = "M1";
/* 295 */           this.series = '1';
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 300 */       if (!isVINRemediation && my == 'E' && make == '2') {
/* 301 */         char engine = getEngine();
/* 302 */         if (this.line == 'F' && engine == 'R') {
/* 303 */           String ident = "" + getPosition(6) + getPosition(7);
/* 304 */           if (ident.equals("19") || ident.equals("27") || ident.equals("35")) {
/* 305 */             this.sLineSeries = "F1";
/* 306 */             this.series = '1';
/*     */           } 
/* 308 */         } else if (this.line == 'F' && "TXZ".indexOf(engine) >= 0) {
/* 309 */           this.sLineSeries = "F1";
/* 310 */           this.series = '1';
/* 311 */         } else if (this.line == 'L' && engine == 'C') {
/* 312 */           this.sLineSeries = "L1";
/* 313 */           this.series = '1';
/* 314 */         } else if (this.line == 'S' && "AHN".indexOf(engine) >= 0) {
/* 315 */           this.sLineSeries = "S1";
/* 316 */           this.series = '1';
/* 317 */         } else if (this.line == 'T' && "AHN".indexOf(engine) >= 0) {
/* 318 */           this.sLineSeries = "T1";
/* 319 */           this.series = '1';
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getVINLine(char make, SPSSchemaAdapterNAO adapter) throws Exception {
/* 326 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 327 */     Connection conn = null;
/* 328 */     DBMS.PreparedStatement stmt = null;
/* 329 */     ResultSet rs = null;
/*     */     try {
/* 331 */       conn = dblink.requestConnection();
/* 332 */       String sql = DBMS.getSQL(dblink, "SELECT SRS_Body FROM PROM_VIN_Line Where Model_year = ? AND SRS_CorT = ? AND SRS_Make = ? AND SRS_Series Like ?");
/* 333 */       stmt = DBMS.prepareSQLStatement(conn, sql);
/* 334 */       stmt.setString(1, DBMS.toString(getModelYear()));
/* 335 */       stmt.setString(2, this.isTruck ? "T" : "C");
/* 336 */       stmt.setString(3, DBMS.toString(make));
/* 337 */       stmt.setString(4, "%" + this.sLineSeries);
/* 338 */       rs = stmt.executeQuery();
/* 339 */       if (rs.next()) {
/* 340 */         return rs.getString(1).trim();
/*     */       }
/* 342 */     } catch (Exception e) {
/* 343 */       throw e;
/*     */     } finally {
/*     */       try {
/* 346 */         if (rs != null) {
/* 347 */           rs.close();
/*     */         }
/* 349 */         if (stmt != null) {
/* 350 */           stmt.close();
/*     */         }
/* 352 */         if (conn != null) {
/* 353 */           dblink.releaseConnection(conn);
/*     */         }
/* 355 */       } catch (Exception x) {}
/*     */     } 
/*     */     
/* 358 */     return null;
/*     */   }
/*     */   
/*     */   public int getYear(SPSSchemaAdapterNAO adapter) {
/* 362 */     Integer my = (Integer)StaticData.getInstance(adapter).getYears().get(DBMS.toString(getModelYear()));
/* 363 */     return (my != null) ? my.intValue() : -1;
/*     */   }
/*     */   
/*     */   protected static String makeTruckKey(char my, char tmake) {
/* 367 */     return my + ":" + tmake;
/*     */   }
/*     */   
/*     */   static void init(SPSSchemaAdapterNAO adapter) throws Exception {
/* 371 */     StaticData.getInstance(adapter).init();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSVIN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */