/*    */ package com.eoos.gm.tis2web.frame.dls;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoftwareKeyRI
/*    */   implements SoftwareKey, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String hex;
/*    */   private byte[] external;
/*    */   
/*    */   public SoftwareKeyRI(String hex) {
/* 18 */     this.hex = hex;
/* 19 */     this.external = Util.parseBytes(hex);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 23 */     int ret = SoftwareKey.class.hashCode();
/* 24 */     ret = HashCalc.addHashCode(ret, this.hex);
/* 25 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 29 */     if (this == obj)
/* 30 */       return true; 
/* 31 */     if (obj instanceof SoftwareKeyRI) {
/* 32 */       SoftwareKeyRI swk = (SoftwareKeyRI)obj;
/* 33 */       return this.hex.equalsIgnoreCase(swk.hex);
/*    */     } 
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return this.hex;
/*    */   }
/*    */   
/*    */   public byte[] toExternal() {
/* 44 */     return this.external;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\SoftwareKeyRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */