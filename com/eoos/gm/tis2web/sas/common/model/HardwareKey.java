/*    */ package com.eoos.gm.tis2web.sas.common.model;
/*    */ 
/*    */ import com.eoos.util.HashCalc;
/*    */ import com.eoos.util.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ public class HardwareKey
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String encoded;
/*    */   private String decoded;
/*    */   
/*    */   public HardwareKey(String encoded, String decoded) {
/* 16 */     this.encoded = encoded;
/* 17 */     this.decoded = decoded;
/*    */   }
/*    */   
/*    */   public static synchronized HardwareKey getInstance(String encoded, String decoded) {
/* 21 */     return new HardwareKey(encoded, decoded);
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 25 */     boolean retValue = false;
/* 26 */     if (this == object) {
/* 27 */       retValue = true;
/* 28 */     } else if (object instanceof HardwareKey) {
/* 29 */       HardwareKey hwk = (HardwareKey)object;
/* 30 */       retValue = Util.equals(this.encoded, hwk.encoded);
/* 31 */       retValue = (retValue && Util.equals(this.decoded, hwk.decoded));
/*    */     } 
/* 33 */     return retValue;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 37 */     int retValue = HardwareKey.class.hashCode();
/* 38 */     retValue = HashCalc.addHashCode(retValue, this.encoded);
/* 39 */     retValue = HashCalc.addHashCode(retValue, this.decoded);
/* 40 */     return retValue;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     return "HWK(" + String.valueOf(this.encoded) + "/" + String.valueOf(this.decoded) + ")";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getEncoded() {
/* 49 */     return this.encoded;
/*    */   }
/*    */   
/*    */   public String getDecoded() {
/* 53 */     return this.decoded;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\HardwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */