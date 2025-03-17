/*     */ package com.eoos.scsm.v2.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashCalc
/*     */ {
/*     */   private static final class ArrayMode
/*     */   {
/*     */     private ArrayMode() {}
/*     */   }
/*     */   
/*  16 */   public static final ArrayMode CONTENT_BASED = new ArrayMode();
/*     */   
/*  18 */   public static final ArrayMode ID_BASED = new ArrayMode();
/*     */   
/*     */   public static int addHashCode(int hashCode, Object object) {
/*  21 */     return addHashCode(hashCode, object, CONTENT_BASED);
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, Object object, ArrayMode arrayMode) {
/*  25 */     if (object != null && object.getClass().isArray() && arrayMode.equals(CONTENT_BASED)) {
/*  26 */       Class<?> componentClass = object.getClass().getComponentType();
/*  27 */       if (Object.class.equals(componentClass))
/*  28 */         return addHashCode(hashCode, (Object[])object); 
/*  29 */       if (byte.class.equals(componentClass))
/*  30 */         return addHashCode(hashCode, (byte[])object); 
/*  31 */       if (boolean.class.equals(componentClass))
/*  32 */         return addHashCode(hashCode, (boolean[])object); 
/*  33 */       if (short.class.equals(componentClass))
/*  34 */         return addHashCode(hashCode, (short[])object); 
/*  35 */       if (char.class.equals(componentClass))
/*  36 */         return addHashCode(hashCode, (char[])object); 
/*  37 */       if (int.class.equals(componentClass))
/*  38 */         return addHashCode(hashCode, (int[])object); 
/*  39 */       if (long.class.equals(componentClass))
/*  40 */         return addHashCode(hashCode, (long[])object); 
/*  41 */       if (float.class.equals(componentClass))
/*  42 */         return addHashCode(hashCode, (float[])object); 
/*  43 */       if (double.class.equals(componentClass)) {
/*  44 */         return addHashCode(hashCode, (double[])object);
/*     */       }
/*  46 */       throw new Error("unhandled primitive type");
/*     */     } 
/*     */     
/*  49 */     int retValue = 37 * hashCode;
/*  50 */     retValue += (object != null) ? object.hashCode() : 0;
/*  51 */     return retValue;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, Object[] object) {
/*  55 */     int b = 378551;
/*  56 */     int a = 63689;
/*     */     
/*  58 */     for (int i = 0; i < object.length; i++) {
/*  59 */       hashCode = hashCode * a + addHashCode(0, object[i], CONTENT_BASED);
/*  60 */       a *= b;
/*     */     } 
/*     */     
/*  63 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, boolean[] object) {
/*  67 */     int b = 378551;
/*  68 */     int a = 63689;
/*     */     
/*  70 */     for (int i = 0; i < object.length; i++) {
/*  71 */       hashCode = hashCode * a + addHashCode(0, object[i]);
/*  72 */       a *= b;
/*     */     } 
/*     */     
/*  75 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, char[] object) {
/*  79 */     int b = 378551;
/*  80 */     int a = 63689;
/*     */     
/*  82 */     for (int i = 0; i < object.length; i++) {
/*  83 */       hashCode = hashCode * a + addHashCode(0, object[i]);
/*  84 */       a *= b;
/*     */     } 
/*     */     
/*  87 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, byte[] object) {
/*  91 */     int b = 378551;
/*  92 */     int a = 63689;
/*     */     
/*  94 */     for (int i = 0; i < object.length; i++) {
/*  95 */       hashCode = hashCode * a + addHashCode(0, object[i]);
/*  96 */       a *= b;
/*     */     } 
/*     */     
/*  99 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, short[] object) {
/* 103 */     int b = 378551;
/* 104 */     int a = 63689;
/*     */     
/* 106 */     for (int i = 0; i < object.length; i++) {
/* 107 */       hashCode = hashCode * a + addHashCode(0, object[i]);
/* 108 */       a *= b;
/*     */     } 
/*     */     
/* 111 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, int[] object) {
/* 115 */     int b = 378551;
/* 116 */     int a = 63689;
/*     */     
/* 118 */     for (int i = 0; i < object.length; i++) {
/* 119 */       hashCode = hashCode * a + addHashCode(0, object[i]);
/* 120 */       a *= b;
/*     */     } 
/*     */     
/* 123 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, long[] object) {
/* 127 */     int b = 378551;
/* 128 */     int a = 63689;
/*     */     
/* 130 */     for (int i = 0; i < object.length; i++) {
/* 131 */       hashCode = hashCode * a + addHashCode(0, object[i]);
/* 132 */       a *= b;
/*     */     } 
/*     */     
/* 135 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, float[] object) {
/* 139 */     int b = 378551;
/* 140 */     int a = 63689;
/*     */     
/* 142 */     for (int i = 0; i < object.length; i++) {
/* 143 */       hashCode = hashCode * a + addHashCode(0, object[i]);
/* 144 */       a *= b;
/*     */     } 
/*     */     
/* 147 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, double[] object) {
/* 151 */     int b = 378551;
/* 152 */     int a = 63689;
/*     */     
/* 154 */     for (int i = 0; i < object.length; i++) {
/* 155 */       hashCode = hashCode * a + addHashCode(0, object[i]);
/* 156 */       a *= b;
/*     */     } 
/*     */     
/* 159 */     return hashCode;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, long object) {
/* 163 */     int retValue = 37 * hashCode;
/* 164 */     retValue += (int)(object ^ object >>> 32L);
/* 165 */     return retValue;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, int object) {
/* 169 */     int retValue = 37 * hashCode;
/* 170 */     retValue += object;
/* 171 */     return retValue;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, boolean object) {
/* 175 */     int retValue = 37 * hashCode;
/* 176 */     retValue += object ? 1 : 0;
/* 177 */     return retValue;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, float object) {
/* 181 */     int retValue = 37 * hashCode;
/* 182 */     retValue += Float.floatToIntBits(object);
/* 183 */     return retValue;
/*     */   }
/*     */   
/*     */   public static int addHashCode(int hashCode, double object) {
/* 187 */     long _object = Double.doubleToLongBits(object);
/* 188 */     return addHashCode(hashCode, _object);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 192 */     int c2 = 37;
/* 193 */     int x1 = 5;
/* 194 */     int x2 = 6;
/*     */     
/* 196 */     for (int i = 0; i < 10; i++) {
/* 197 */       int x3 = i;
/* 198 */       int x4 = c2 * x1 + x2 - c2 * x3;
/*     */       
/* 200 */       System.out.println(addHashCode(0, new int[] { x1, x2 }));
/* 201 */       System.out.println(addHashCode(0, new int[] { x3, x4 }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\HashCalc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */