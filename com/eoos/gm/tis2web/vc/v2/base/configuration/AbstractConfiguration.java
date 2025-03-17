/*    */ package com.eoos.gm.tis2web.vc.v2.base.configuration;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class AbstractConfiguration
/*    */   implements IConfiguration
/*    */ {
/*  9 */   private Integer hashCode = null;
/* 10 */   private String toString = null;
/*    */ 
/*    */   
/*    */   protected abstract ConfigurationUtil getConfigurationUtil();
/*    */ 
/*    */   
/*    */   public abstract Set getKeys();
/*    */ 
/*    */   
/*    */   public abstract Value getValue(Object paramObject);
/*    */   
/*    */   public boolean equals(Object obj) {
/* 22 */     if (this == obj)
/* 23 */       return true; 
/* 24 */     if (obj instanceof IConfiguration) {
/* 25 */       return getConfigurationUtil().equals(this, (IConfiguration)obj);
/*    */     }
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 32 */     if (this.hashCode == null) {
/* 33 */       this.hashCode = Integer.valueOf(getConfigurationUtil().hashCode(this));
/*    */     }
/* 35 */     return this.hashCode.intValue();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     if (this.toString == null) {
/* 40 */       this.toString = ConfigurationUtil.toString(this);
/*    */     }
/* 42 */     return this.toString;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\configuration\AbstractConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */