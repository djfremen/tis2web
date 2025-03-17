/*     */ package com.eoos.gm.tis2web.frame.ws.lt.server;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehDesc;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvalidVehException
/*     */   extends Exception
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  16 */   private List<Pair> validList = new ArrayList<Pair>();
/*     */   
/*  18 */   private List<Pair> invalidList = new ArrayList<Pair>();
/*  19 */   private int invalidCode = 0;
/*  20 */   private String invalidVin = null;
/*     */ 
/*     */   
/*     */   public List<Pair> getValidAttributes() {
/*  24 */     return this.validList;
/*     */   }
/*     */   
/*     */   public int getInvalidCode() {
/*  28 */     return this.invalidCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Pair> getInvalidAttributes() {
/*  33 */     return this.invalidList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToValidList(Pair attr) {
/*  38 */     this.validList.add(attr);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToInvalidList(Pair attr) {
/*  43 */     this.invalidList.add(attr);
/*     */   }
/*     */   
/*     */   public void addInvalidCode(int code) {
/*  47 */     this.invalidCode |= code;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  52 */     String result = new String("No details available.");
/*  53 */     if (this.invalidVin == null) {
/*  54 */       result = new String("validList[");
/*  55 */       Iterator<Pair> it = this.validList.iterator();
/*  56 */       while (it.hasNext()) {
/*  57 */         Pair attr = it.next();
/*  58 */         result = result.concat(attr.getFirst() + "=" + attr.getSecond() + ",");
/*     */       } 
/*  60 */       if (result.endsWith(",")) {
/*  61 */         result = result.substring(0, result.length() - 1);
/*     */       }
/*  63 */       result = result.concat("] invalidList[");
/*  64 */       it = this.invalidList.iterator();
/*  65 */       while (it.hasNext()) {
/*  66 */         Pair attr = it.next();
/*  67 */         result = result.concat(attr.getFirst() + "=" + attr.getSecond() + ",");
/*     */       } 
/*  69 */       if (result.endsWith(",")) {
/*  70 */         result = result.substring(0, result.length() - 1);
/*     */       }
/*  72 */       result = result.concat("]");
/*     */     } else {
/*  74 */       result = result.concat("invalidVIN[" + this.invalidVin + "]");
/*     */     } 
/*     */     
/*  77 */     return result;
/*     */   }
/*     */   
/*     */   public void adjustValidList(VehDesc vehDesc) {
/*  81 */     if (this.invalidCode != 0) {
/*  82 */       if ((this.invalidCode & 0x1) == 0 && vehDesc.getVin() != null && vehDesc.getVin().length() != 0 && !isContained(this.validList, "vin")) {
/*  83 */         addToValidList((Pair)new PairImpl("vin", vehDesc.getVin()));
/*     */       }
/*  85 */       if ((this.invalidCode & 0x2) == 0 && vehDesc.getMake() != null && vehDesc.getMake().length() != 0 && !isContained(this.validList, "make")) {
/*  86 */         addToValidList((Pair)new PairImpl("make", vehDesc.getMake()));
/*     */       }
/*  88 */       if ((this.invalidCode & 0x4) == 0 && vehDesc.getModel() != null && vehDesc.getModel().length() != 0 && !isContained(this.validList, "model")) {
/*  89 */         addToValidList((Pair)new PairImpl("model", vehDesc.getModel()));
/*     */       }
/*  91 */       if ((this.invalidCode & 0x8) == 0 && vehDesc.getYear() != null && vehDesc.getYear().length() != 0 && !isContained(this.validList, "year")) {
/*  92 */         addToValidList((Pair)new PairImpl("year", vehDesc.getYear()));
/*     */       }
/*  94 */       if ((this.invalidCode & 0x10) == 0 && vehDesc.getEngine() != null && vehDesc.getEngine().length() != 0 && !isContained(this.validList, "engine")) {
/*  95 */         addToValidList((Pair)new PairImpl("engine", vehDesc.getEngine()));
/*     */       }
/*  97 */       if ((this.invalidCode & 0x20) == 0 && vehDesc.getTransmission() != null && vehDesc.getTransmission().length() != 0 && !isContained(this.validList, "transmission")) {
/*  98 */         addToValidList((Pair)new PairImpl("transmission", vehDesc.getTransmission()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isContained(List<Pair> list, String attr) {
/* 105 */     boolean result = false;
/* 106 */     Iterator<Pair> it = list.iterator();
/* 107 */     while (it.hasNext()) {
/* 108 */       String key = (String)((Pair)it.next()).getFirst();
/* 109 */       if (key.compareTo(attr) == 0) {
/* 110 */         result = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 114 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\InvalidVehException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */