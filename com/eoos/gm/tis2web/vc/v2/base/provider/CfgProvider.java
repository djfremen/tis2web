/*    */ package com.eoos.gm.tis2web.vc.v2.base.provider;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ 
/*    */ public interface CfgProvider {
/*  7 */   public static final CfgProvider DUMMY = new CfgProvider()
/*    */     {
/*    */       public long getLastModified() {
/* 10 */         return 0L;
/*    */       }
/*    */       
/*    */       public Set getKeys() {
/* 14 */         return Collections.EMPTY_SET;
/*    */       }
/*    */       
/*    */       public Set getConfigurations() {
/* 18 */         return Collections.EMPTY_SET;
/*    */       }
/*    */     };
/*    */   
/*    */   Set getConfigurations();
/*    */   
/*    */   long getLastModified();
/*    */   
/*    */   Set getKeys();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\CfgProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */