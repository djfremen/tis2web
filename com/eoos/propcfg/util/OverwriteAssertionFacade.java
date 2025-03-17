/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class OverwriteAssertionFacade
/*    */   extends ConfigurationWrapperBase
/*    */ {
/*    */   private static final String SPECIAL_VALUE_OVERWRITE = "#OVERWRITE";
/*    */   private static final String SPECIAL_VALUE_OVERWRITE_LC = "#overwrite";
/* 11 */   private static final Logger log = Logger.getLogger(OverwriteAssertionFacade.class);
/*    */   
/*    */   public OverwriteAssertionFacade(Configuration backend) {
/* 14 */     super(backend);
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 18 */     String ret = super.getProperty(key);
/* 19 */     if (ret != null && (ret.startsWith("#OVERWRITE") || ret.startsWith("#overwrite"))) {
/* 20 */       log.error("assertion failed - value of key : " + key + " should have been overwritten, continuing with value 'null'");
/* 21 */       return null;
/*    */     } 
/* 23 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\OverwriteAssertionFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */