/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ import com.eoos.jdbc.ConNvent;
/*    */ import com.eoos.jdbc.ConnectionProvider;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.util.ConfigurationUtil;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class ConNvent extends ConNvent {
/*    */   public ConNvent(ConnectionProvider backend, long maxIdleDuration) {
/* 11 */     super(backend, maxIdleDuration);
/*    */   }
/*    */   
/*    */   public static ConnectionProvider create(ConnectionProvider backend, long maxIdleDuration) {
/* 15 */     return (ConnectionProvider)new ConNvent(backend, maxIdleDuration);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isEnabled() {
/* 20 */     return ConfigurationUtil.isTrue("frame.use.convent", (Configuration)ConfigurationServiceProvider.getService());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\ConNvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */