/*    */ package com.eoos.log;
/*    */ 
/*    */ import org.apache.log4j.Appender;
/*    */ import org.apache.log4j.Category;
/*    */ import org.apache.log4j.ConsoleAppender;
/*    */ import org.apache.log4j.Layout;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.helpers.LogLog;
/*    */ import org.apache.log4j.spi.HierarchyEventListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StartupConsoleAppender
/*    */   extends ConsoleAppender
/*    */ {
/* 20 */   private Category ownCategory = null;
/*    */   
/*    */   public StartupConsoleAppender(Layout layout) {
/* 23 */     super(layout);
/*    */     
/* 25 */     Logger.getRootLogger().getLoggerRepository().addHierarchyEventListener(new HierarchyEventListener()
/*    */         {
/*    */           public void removeAppenderEvent(Category cat, Appender appender) {
/* 28 */             if (StartupConsoleAppender.this == appender) {
/* 29 */               LogLog.debug("removed startup console appender");
/*    */             }
/*    */           }
/*    */           
/*    */           public void addAppenderEvent(Category cat, Appender appender) {
/* 34 */             if (StartupConsoleAppender.this == appender) {
/* 35 */               StartupConsoleAppender.this.setOwnCategory(cat);
/* 36 */             } else if (appender instanceof ConsoleAppender) {
/* 37 */               StartupConsoleAppender.this.getOwnCategory().removeAppender((Appender)StartupConsoleAppender.this);
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private synchronized void setOwnCategory(Category cat) {
/* 47 */     this.ownCategory = cat;
/*    */   }
/*    */   
/*    */   private synchronized Category getOwnCategory() {
/* 51 */     return this.ownCategory;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\log\StartupConsoleAppender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */