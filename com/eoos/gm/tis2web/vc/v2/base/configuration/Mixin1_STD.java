/*    */ package com.eoos.gm.tis2web.vc.v2.base.configuration;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.value.ValueUtil;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class Mixin1_STD
/*    */   implements ConfigurationManagement.Mixin1
/*    */ {
/*    */   private ValueUtil valueUtil;
/*    */   
/*    */   public Mixin1_STD(ValueUtil valueUtil) {
/* 16 */     this.valueUtil = valueUtil;
/*    */   }
/*    */   
/*    */   public boolean equals(IConfiguration cfg1, IConfiguration cfg2) {
/* 20 */     boolean ret = true;
/* 21 */     if (cfg1 != cfg2) {
/* 22 */       if (ret = (cfg1 != null && cfg2 != null)) {
/* 23 */         Set keys1 = cfg1.getKeys();
/* 24 */         Set keys2 = cfg2.getKeys();
/* 25 */         ret = Util.equals(keys1, keys2);
/* 26 */         for (Iterator iter = cfg1.getKeys().iterator(); iter.hasNext() && ret; ) {
/* 27 */           Object key = iter.next();
/* 28 */           ret = this.valueUtil.equals(cfg1.getValue(key), cfg2.getValue(key));
/*    */         } 
/*    */       } 
/*    */     }
/* 32 */     return ret;
/*    */   }
/*    */   
/*    */   public int hashCode(IConfiguration cfg) {
/* 36 */     int ret = IConfiguration.class.hashCode();
/* 37 */     Set keys = cfg.getKeys();
/* 38 */     ret = HashCalc.addHashCode(ret, keys);
/* 39 */     for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
/* 40 */       Object key = iter.next();
/* 41 */       ret += this.valueUtil.hashCode(cfg.getValue(key));
/*    */     } 
/* 43 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\configuration\Mixin1_STD.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */