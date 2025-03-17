/*    */ package com.eoos.gm.tis2web.vc.v2.vin;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import java.io.InvalidObjectException;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class VINImpl
/*    */   implements VIN {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String vin;
/*    */   
/*    */   private static final class SerialProxy
/*    */     implements Serializable {
/*    */     private static final long serialVersionUID = 1L;
/*    */     private String vin;
/*    */     
/*    */     private SerialProxy(String vin) {
/* 20 */       this.vin = vin;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public SerialProxy() {}
/*    */ 
/*    */     
/*    */     private Object readResolve() throws ObjectStreamException {
/*    */       try {
/* 30 */         return new VINImpl(this.vin);
/* 31 */       } catch (InvalidVINException e) {
/* 32 */         throw new InvalidObjectException("encapulated VIN is invalid");
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public VINImpl(String vin) throws VIN.InvalidVINException {
/* 41 */     if (vin == null || vin.trim().length() != 17) {
/* 42 */       throw new VIN.InvalidVINException();
/*    */     }
/* 44 */     this.vin = vin.toUpperCase(Locale.ENGLISH);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 48 */     int ret = VIN.class.hashCode();
/* 49 */     ret = HashCalc.addHashCode(ret, this.vin);
/* 50 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 54 */     if (this == obj)
/* 55 */       return true; 
/* 56 */     if (obj instanceof VIN) {
/* 57 */       String vin = String.valueOf(obj);
/* 58 */       return this.vin.equalsIgnoreCase(vin);
/*    */     } 
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return this.vin;
/*    */   }
/*    */   
/*    */   public char getModelyearCode() {
/* 69 */     return this.vin.charAt(9);
/*    */   }
/*    */   
/*    */   public String getWMI() {
/* 73 */     return this.vin.substring(0, 3);
/*    */   }
/*    */   
/*    */   private Object writeReplace() throws ObjectStreamException {
/* 77 */     return new SerialProxy(this.vin);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\vin\VINImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */