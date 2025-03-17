/*    */ package com.eoos.gm.tis2web.vc.v2.base.configuration;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class ConfigurationRI
/*    */   extends AbstractConfiguration
/*    */   implements IConfiguration.Mutable {
/* 10 */   Map map = null;
/*    */   
/*    */   ConfigurationRI(Map map) {
/* 13 */     this.map = map;
/*    */   }
/*    */   
/*    */   public final Set getKeys() {
/* 17 */     return this.map.keySet();
/*    */   }
/*    */ 
/*    */   
/*    */   public final Value getValue(Object key) {
/* 22 */     return (Value)this.map.get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public final void clear() {
/* 27 */     this.map.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void removeAttribute(Object key) {
/* 32 */     this.map.remove(key);
/*    */   }
/*    */   
/*    */   public final void setAttribute(Object key, Value value) {
/* 36 */     this.map.put(key, value);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\configuration\ConfigurationRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */