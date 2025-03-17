/*   */ package com.eoos.gm.tis2web.registration.common.ui.datamodel;
/*   */ 
/*   */ public class SoftwareKeyFactory
/*   */ {
/*   */   public static SoftwareKey createSoftwareKey(final String key) {
/* 6 */     return new SoftwareKey() {
/*   */         public String toString() {
/* 8 */           return key;
/*   */         }
/*   */       };
/*   */   }
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\SoftwareKeyFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */