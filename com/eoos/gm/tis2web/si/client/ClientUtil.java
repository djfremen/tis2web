/*    */ package com.eoos.gm.tis2web.si.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.client.model.Baudrate;
/*    */ import com.eoos.gm.tis2web.si.client.model.Port;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientUtil
/*    */ {
/*    */   public static Port toPort(final String denotation) {
/* 14 */     return Util.isNullOrEmpty(denotation) ? null : new Port()
/*    */       {
/*    */         public int hashCode()
/*    */         {
/* 18 */           int ret = getClass().hashCode();
/* 19 */           ret = HashCalc.addHashCode(ret, denotation);
/* 20 */           return ret;
/*    */         }
/*    */         
/*    */         public String toString() {
/* 24 */           return denotation;
/*    */         }
/*    */ 
/*    */         
/*    */         public boolean equals(Object obj) {
/* 29 */           if (this == obj)
/* 30 */             return true; 
/* 31 */           if (obj instanceof Port) {
/* 32 */             Port other = (Port)obj;
/* 33 */             boolean ret = denotation.equals(other.toString());
/* 34 */             return ret;
/*    */           } 
/* 36 */           return false;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static Baudrate toBaudrate(final String rate) {
/* 43 */     return Util.isNullOrEmpty(rate) ? null : new Baudrate()
/*    */       {
/*    */         public int hashCode()
/*    */         {
/* 47 */           int ret = getClass().hashCode();
/* 48 */           ret = HashCalc.addHashCode(ret, rate);
/* 49 */           return ret;
/*    */         }
/*    */         
/*    */         public String toString() {
/* 53 */           return rate;
/*    */         }
/*    */ 
/*    */         
/*    */         public boolean equals(Object obj) {
/* 58 */           if (this == obj)
/* 59 */             return true; 
/* 60 */           if (obj instanceof Baudrate) {
/* 61 */             Baudrate other = (Baudrate)obj;
/* 62 */             boolean ret = rate.equals(other.toString());
/* 63 */             return ret;
/*    */           } 
/* 65 */           return false;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\ClientUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */