/*     */ package com.eoos.gm.tis2web.sas.common.model;
/*     */ 
/*     */ import com.eoos.collection.MinimalMap;
/*     */ import com.eoos.collection.implementation.MinimalMapAdapter;
/*     */ import com.eoos.condition.Condition;
/*     */ import com.eoos.util.AssertUtil;
/*     */ import com.eoos.util.MultitonSupport;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public class VIN
/*     */   implements Serializable {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*  18 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*     */         public Object createObject(Object identifier) {
/*  20 */           return new VIN((String)identifier);
/*     */         }
/*     */       }) {
/*     */       protected MinimalMap createInstanceStorageMap() {
/*  24 */         return (MinimalMap)new MinimalMapAdapter(new WeakHashMap<Object, Object>());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private String key;
/*     */   
/*     */   private VIN(String key) {
/*  32 */     this.key = key;
/*     */   }
/*     */   
/*     */   public static synchronized VIN getInstance(String key) {
/*  36 */     key = StringUtilities.removeWhitespaces(key);
/*  37 */     return (VIN)multitonSupport.getInstance(key.toUpperCase(Locale.ENGLISH));
/*     */   }
/*     */   
/*     */   private Object readResolve() throws ObjectStreamException {
/*  41 */     return getInstance(this.key);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  45 */     return this.key;
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
/*     */   public String getPart(final int startPosition, final int endPosition) {
/*     */     try {
/*  60 */       AssertUtil.ensure(this.key, new Condition() {
/*     */             public boolean check(Object obj) {
/*  62 */               String key = (String)obj;
/*  63 */               boolean retValue = true;
/*  64 */               retValue = (retValue && key != null);
/*  65 */               retValue = (retValue && startPosition > 0 && startPosition <= key.length());
/*  66 */               retValue = (retValue && endPosition >= startPosition && endPosition <= key.length());
/*  67 */               return retValue;
/*     */             }
/*     */           });
/*     */       
/*  71 */       return this.key.substring(startPosition - 1, endPosition);
/*     */     }
/*  73 */     catch (com.eoos.util.AssertUtil.AssertException e) {
/*  74 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCarlinePart() {
/*  80 */     return getPart(4, 4);
/*     */   }
/*     */   
/*     */   public String getModelYearPart() {
/*  84 */     return getPart(10, 10);
/*     */   }
/*     */   
/*     */   public String getPlantPart() {
/*  88 */     return getPart(11, 11);
/*     */   }
/*     */   
/*     */   public String getWMIPart() {
/*  92 */     return getPart(1, 3);
/*     */   }
/*     */   
/*     */   public String getChassisPart() {
/*  96 */     return getPart(12, 17);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 100 */     VIN vin = getInstance("ys3df58h6y5381348");
/* 101 */     System.out.println(vin.getCarlinePart());
/* 102 */     System.out.println(vin.getModelYearPart());
/* 103 */     System.out.println(vin.getWMIPart());
/* 104 */     System.out.println(vin.getChassisPart());
/* 105 */     System.out.println(vin.getPlantPart());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\VIN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */