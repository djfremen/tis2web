/*    */ package com.eoos.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ByteUtil
/*    */ {
/*    */   private static final int KBYTE = 1024;
/*    */   private static final int MBYTE = 1048576;
/*    */   private static final int GBYTE = 1073741824;
/*    */   
/*    */   public static long getAsGigaBytes(long bytes) {
/* 15 */     return bytes / 1073741824L;
/*    */   }
/*    */   
/*    */   public static long getAsMegaBytes(long bytes) {
/* 19 */     return bytes / 1048576L;
/*    */   }
/*    */   
/*    */   public static long getAsKiloBytes(long bytes) {
/* 23 */     return bytes / 1024L;
/*    */   }
/*    */   
/*    */   public static long getGigaBytes(long bytes) {
/* 27 */     return getAsGigaBytes(bytes);
/*    */   }
/*    */   
/*    */   public static long getMegaBytes(long bytes) {
/* 31 */     return getAsMegaBytes(bytes) - getAsGigaBytes(bytes) * 1024L;
/*    */   }
/*    */   
/*    */   public static long getKiloBytes(long bytes) {
/* 35 */     return getAsKiloBytes(bytes) - getAsMegaBytes(bytes) * 1024L;
/*    */   }
/*    */   
/*    */   public static long getBytes(long bytes) {
/* 39 */     return bytes - getAsKiloBytes(bytes) * 1024L;
/*    */   }
/*    */   
/*    */   public static String toString(long bytes) {
/* 43 */     return getGigaBytes(bytes) + " GByte, " + getMegaBytes(bytes) + " MByte, " + getKiloBytes(bytes) + " kByte, " + getBytes(bytes) + " Byte";
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 47 */     System.out.println(toString(1073741824L));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\ByteUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */