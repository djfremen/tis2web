/*    */ package com.eoos.jdbc;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class StatementManagerSupport {
/*  6 */   private static final Logger log = Logger.getLogger(StatementManagerSupport.class);
/*    */   
/*  8 */   private ThreadLocal refStatementManager = new ThreadLocal();
/*    */   
/* 10 */   private IStatementManager sharedInstance = null;
/*    */   private ConnectionProvider connectionProvider;
/*    */   
/*    */   public StatementManagerSupport(ConnectionProvider connectionProvider) {
/* 14 */     this.connectionProvider = connectionProvider;
/* 15 */     this.sharedInstance = new StatementManagerV2(connectionProvider);
/*    */   }
/*    */   
/*    */   public ConnectionProvider getConnectionProvider() {
/* 19 */     return this.connectionProvider;
/*    */   }
/*    */   
/*    */   public IStatementManager getStatementManager() {
/* 23 */     if (getMode() == Mode.SHARED_INSTANCE) {
/* 24 */       return this.sharedInstance;
/*    */     }
/* 26 */     return _getStatementManager_TLI();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private IStatementManager _getStatementManager_TLI() {
/* 32 */     IStatementManager ret = this.refStatementManager.get();
/* 33 */     if (ret == null) {
/* 34 */       log.debug("creating new statement manager for thread " + Thread.currentThread().getName());
/* 35 */       ret = new StatementManagerV2(this.connectionProvider);
/* 36 */       this.refStatementManager.set(ret);
/*    */     } 
/* 38 */     return ret;
/*    */   }
/*    */   
/*    */   private enum Mode {
/* 42 */     THREAD_LOCAL_INSTANCE, SHARED_INSTANCE;
/*    */   }
/*    */   
/*    */   protected Mode getMode() {
/* 46 */     return Mode.SHARED_INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\StatementManagerSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */