/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.math.BigInteger;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AccessStatisticFacade
/*    */   extends ConfigurationWrapperBase
/*    */ {
/* 12 */   private Map accessCount = new HashMap<Object, Object>();
/*    */   
/*    */   public AccessStatisticFacade(Configuration backend) {
/* 15 */     super(backend);
/*    */   }
/*    */   
/*    */   public synchronized String getProperty(String key) {
/* 19 */     String ret = super.getProperty(key);
/* 20 */     BigInteger count = (BigInteger)this.accessCount.get(key);
/* 21 */     if (count == null) {
/* 22 */       count = BigInteger.ZERO;
/*    */     }
/* 24 */     this.accessCount.put(key, count.add(BigInteger.ONE));
/* 25 */     return ret;
/*    */   }
/*    */   
/*    */   public synchronized Set getRequestedKeys() {
/* 29 */     return this.accessCount.keySet();
/*    */   }
/*    */   
/*    */   public synchronized BigInteger getAccessCount(String key) {
/* 33 */     BigInteger ret = (BigInteger)this.accessCount.get(key);
/* 34 */     return (ret != null) ? ret : BigInteger.ZERO;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\AccessStatisticFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */