/*    */ package com.eoos.gm.tis2web.vc.v2.provider;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class CfgProviderSet
/*    */   implements CfgProvider
/*    */ {
/*    */   private Set<CfgProvider> providers;
/*    */   
/*    */   public CfgProviderSet(Set<CfgProvider> providers) {
/* 17 */     this.providers = (providers != null) ? providers : Collections.EMPTY_SET;
/*    */   }
/*    */   
/*    */   public Set getConfigurations() {
/* 21 */     Set ret = new HashSet();
/* 22 */     for (CfgProvider provider : this.providers) {
/* 23 */       ret.addAll(provider.getConfigurations());
/*    */     }
/* 25 */     return ret;
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 29 */     CfgProvider first = (CfgProvider)CollectionUtil.getFirst(this.providers);
/* 30 */     return (first != null) ? first.getKeys() : Collections.EMPTY_SET;
/*    */   }
/*    */   
/*    */   public long getLastModified() {
/* 34 */     return 0L;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 38 */     int ret = getClass().hashCode();
/* 39 */     ret = HashCalc.addHashCode(ret, this.providers);
/* 40 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 44 */     if (this == obj)
/* 45 */       return true; 
/* 46 */     if (obj instanceof CfgProviderSet) {
/* 47 */       return Util.collectionEquals(this.providers, ((CfgProviderSet)obj).providers);
/*    */     }
/* 49 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\provider\CfgProviderSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */