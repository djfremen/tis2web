/*    */ package com.eoos.log;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class Adapter implements ILogger {
/*    */   private Logger log;
/*    */   
/*    */   public Adapter(Logger log) {
/*  9 */     this.log = log;
/*    */   }
/*    */   
/*    */   public void debug(Object message) {
/* 13 */     this.log.debug(message);
/*    */   }
/*    */   
/*    */   public void debug(Object message, Throwable t) {
/* 17 */     this.log.debug(message, t);
/*    */   }
/*    */   
/*    */   public void info(Object message) {
/* 21 */     this.log.info(message);
/*    */   }
/*    */   
/*    */   public void info(Object message, Throwable t) {
/* 25 */     this.log.info(message, t);
/*    */   }
/*    */   
/*    */   public void warn(Object message) {
/* 29 */     this.log.warn(message);
/*    */   }
/*    */   
/*    */   public void warn(Object message, Throwable t) {
/* 33 */     this.log.warn(message, t);
/*    */   }
/*    */   
/*    */   public void error(Object message) {
/* 37 */     this.log.error(message);
/*    */   }
/*    */   
/*    */   public void error(Object message, Throwable t) {
/* 41 */     this.log.debug(message, t);
/*    */   }
/*    */   
/*    */   public void fatal(Object message) {
/* 45 */     this.log.fatal(message);
/*    */   }
/*    */   
/*    */   public void fatal(Object message, Throwable t) {
/* 49 */     this.log.fatal(message, t);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\log\Adapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */