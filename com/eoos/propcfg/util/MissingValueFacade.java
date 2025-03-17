/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class MissingValueFacade
/*    */   extends ConfigurationWrapperBase
/*    */ {
/*    */   private Logger log;
/*    */   
/*    */   public MissingValueFacade(Configuration backend, Logger log) {
/* 12 */     super(backend);
/* 13 */     this.log = (log != null) ? log : Logger.getLogger(MissingValueFacade.class);
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 17 */     String ret = super.getProperty(key);
/* 18 */     if (ret == null) {
/* 19 */       this.log.warn("returning <null> value for key: " + key);
/*    */     }
/* 21 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\MissingValueFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */