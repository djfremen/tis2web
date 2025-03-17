/*    */ package com.eoos.log;
/*    */ 
/*    */ import org.apache.log4j.AppenderSkeleton;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Priority;
/*    */ import org.apache.log4j.spi.LoggingEvent;
/*    */ 
/*    */ public class DummyAppender
/*    */   extends AppenderSkeleton {
/*    */   public DummyAppender() {
/* 11 */     setThreshold((Priority)Level.toLevel(2147483647));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void append(LoggingEvent event) {}
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */   
/*    */   public boolean requiresLayout() {
/* 21 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\log\DummyAppender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */