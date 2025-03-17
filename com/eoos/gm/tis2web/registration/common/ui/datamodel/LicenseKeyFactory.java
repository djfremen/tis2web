/*    */ package com.eoos.gm.tis2web.registration.common.ui.datamodel;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class LicenseKeyFactory
/*    */ {
/*    */   public static LicenseKey createLicenseKey(String key) throws IllegalArgumentException {
/* 10 */     final String _key = normalize(key);
/* 11 */     if (_key == null || _key.length() != 64) {
/* 12 */       throw new IllegalArgumentException("invalid license key");
/*    */     }
/* 14 */     final String external2 = toExternalForm(_key);
/* 15 */     return new LicenseKey() {
/*    */         public String toString() {
/* 17 */           return _key;
/*    */         }
/*    */         
/*    */         public String toExternalForm(boolean includeSeparators) {
/* 21 */           if (includeSeparators) {
/* 22 */             return external2;
/*    */           }
/* 24 */           return _key;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static String normalize(String key) {
/* 32 */     if (key != null) {
/* 33 */       key = key.toUpperCase(Locale.ENGLISH).trim();
/* 34 */       key = StringUtilities.replace(key, "-", "");
/*    */     } 
/* 36 */     return key;
/*    */   }
/*    */   
/*    */   private static String toExternalForm(String key) {
/* 40 */     StringBuffer tmp = new StringBuffer(key);
/* 41 */     for (int i = 0; i < 15; i++) {
/* 42 */       tmp.insert((i + 1) * 4 + i, "-");
/*    */     }
/* 44 */     return tmp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\LicenseKeyFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */