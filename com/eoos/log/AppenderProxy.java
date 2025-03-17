/*     */ package com.eoos.log;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.TimerTask;
/*     */ import org.apache.log4j.Appender;
/*     */ import org.apache.log4j.AppenderSkeleton;
/*     */ import org.apache.log4j.Category;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.spi.HierarchyEventListener;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ 
/*     */ public class AppenderProxy
/*     */   extends AppenderSkeleton
/*     */ {
/*  21 */   private List eventList = new LinkedList();
/*     */   
/*  23 */   private List proxiedAppenderNames = new LinkedList();
/*     */   
/*  25 */   private Category ownCategory = null;
/*     */   
/*  27 */   private HierarchyEventListener hierarchyEventListener = new HierarchyEventListener()
/*     */     {
/*     */       public void removeAppenderEvent(Category cat, Appender appender) {}
/*     */ 
/*     */       
/*     */       public void addAppenderEvent(Category cat, Appender appender) {
/*  33 */         if (AppenderProxy.this.proxiedAppenderNames.contains(appender.getName())) {
/*  34 */           LogLog.debug("found proxied appender, transferring logging events");
/*  35 */           synchronized (AppenderProxy.this) {
/*  36 */             for (Iterator<LoggingEvent> iter = AppenderProxy.this.getEventList().iterator(); iter.hasNext(); ) {
/*  37 */               LoggingEvent event = iter.next();
/*  38 */               if (event.getLoggerName() == null || Logger.getRootLogger().equals(cat) || event.getLoggerName().startsWith(cat.getName())) {
/*  39 */                 appender.doAppend(event);
/*     */               }
/*     */             } 
/*  42 */             LogLog.debug("removing appender from proxied appender list");
/*  43 */             AppenderProxy.this.proxiedAppenderNames.remove(appender.getName());
/*     */             
/*  45 */             if (AppenderProxy.this.proxiedAppenderNames.size() == 0) {
/*  46 */               LogLog.debug("all proxied appender served, requesting dispose...");
/*  47 */               AppenderProxy.this.dispose();
/*     */             } 
/*     */           } 
/*  50 */         } else if (appender == AppenderProxy.this) {
/*  51 */           LogLog.debug("setting own category");
/*  52 */           AppenderProxy.this.setOwnCategory(cat);
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  58 */   private final HierarchyEventListener listenerHook = new HierarchyEventListener()
/*     */     {
/*     */       public void removeAppenderEvent(Category cat, Appender appender) {
/*  61 */         AppenderProxy.this.hierarchyEventListener.removeAppenderEvent(cat, appender);
/*     */       }
/*     */       
/*     */       public void addAppenderEvent(Category cat, Appender appender) {
/*  65 */         AppenderProxy.this.hierarchyEventListener.addAppenderEvent(cat, appender);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  70 */   private static final HierarchyEventListener DUMMY = new HierarchyEventListener()
/*     */     {
/*     */       public void removeAppenderEvent(Category cat, Appender appender) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void addAppenderEvent(Category cat, Appender appender) {}
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private TimerTask ttRelease = null;
/*     */   
/*     */   public AppenderProxy(List<?> proxiedAppenderNames, long maxTimespan) {
/*  87 */     setName("proxy appender for " + String.valueOf(proxiedAppenderNames));
/*  88 */     this.proxiedAppenderNames = new LinkedList(proxiedAppenderNames);
/*  89 */     Logger.getRootLogger().getLoggerRepository().addHierarchyEventListener(this.listenerHook);
/*  90 */     this.ttRelease = new TimerTask()
/*     */       {
/*     */         public void run()
/*     */         {
/*  94 */           synchronized (AppenderProxy.this) {
/*  95 */             if (AppenderProxy.this.ttRelease != null) {
/*  96 */               LogLog.debug("maximum time interval for proxy reached, requesting dispose");
/*  97 */               AppenderProxy.this.dispose();
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/* 102 */     if (maxTimespan != -1L) {
/* 103 */       Util.getTimer().schedule(this.ttRelease, maxTimespan);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AppenderProxy(String proxiedAppenderName, long maxTimespan) {
/* 109 */     this(Arrays.asList(new String[] { proxiedAppenderName }, ), maxTimespan);
/*     */   }
/*     */   
/*     */   private synchronized void setOwnCategory(Category cat) {
/* 113 */     this.ownCategory = cat;
/*     */   }
/*     */   
/*     */   private synchronized Category getOwnCategory() {
/* 117 */     return this.ownCategory;
/*     */   }
/*     */   
/*     */   private synchronized List getEventList() {
/* 121 */     return this.eventList;
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void append(LoggingEvent event) {
/* 126 */     getEventList().add(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() {}
/*     */ 
/*     */   
/*     */   public boolean requiresLayout() {
/* 134 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized void dispose() {
/* 138 */     if (this.ttRelease != null) {
/* 139 */       LogLog.debug("...disposing " + getName());
/* 140 */       this.ttRelease.cancel();
/* 141 */       this.ttRelease = null;
/* 142 */       this.eventList.clear();
/*     */       
/* 144 */       this.hierarchyEventListener = DUMMY;
/* 145 */       getOwnCategory().removeAppender((Appender)this);
/*     */     } else {
/* 147 */       LogLog.debug("...already disposed");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\log\AppenderProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */