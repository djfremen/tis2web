/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class KeyRemovalFacade
/*    */   extends ConfigurationWrapperBase {
/*    */   private static final String SPECIAL_VALUE_REMOVAL = "#REMOVE";
/*    */   private static final String SPECIAL_VALUE_REMOVAL_LC = "#remove";
/*    */   
/*    */   public KeyRemovalFacade(Configuration backend) {
/* 14 */     super(backend);
/*    */   }
/*    */   
/*    */   private boolean isRemoved(String key) {
/* 18 */     String value = getWrappedConfiguration().getProperty(key);
/*    */     
/* 20 */     return (value != null && (value.startsWith("#REMOVE") || value.startsWith("#remove")));
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 24 */     Set ret = new LinkedHashSet(super.getKeys());
/* 25 */     for (Iterator<String> iter = ret.iterator(); iter.hasNext();) {
/* 26 */       if (isRemoved(iter.next())) {
/* 27 */         iter.remove();
/*    */       }
/*    */     } 
/* 30 */     return ret;
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 34 */     if (isRemoved(key)) {
/* 35 */       return null;
/*    */     }
/* 37 */     return super.getProperty(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\KeyRemovalFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */