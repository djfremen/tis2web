/*     */ package com.eoos.html.gtwo.servlet.dispatching;
/*     */ 
/*     */ import com.eoos.html.gtwo.servlet.RequestHandler;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.util.IDFactory;
/*     */ import com.eoos.util.IDFactoryImpl;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DispatcherImpl
/*     */   implements Dispatcher
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(DispatcherImpl.class);
/*     */   
/*     */   private String prefix;
/*     */   
/*  27 */   protected Map handlerToID = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  29 */   private static final IDFactory IDFACTORY = (IDFactory)new IDFactoryImpl(3);
/*     */   
/*  31 */   private Dispatcher parentDispatcher = null;
/*     */ 
/*     */   
/*     */   public DispatcherImpl() {
/*  35 */     this.prefix = IDFACTORY.getNextID();
/*     */   }
/*     */   
/*     */   public void setParent(Dispatcher dispatcher) {
/*  39 */     this.parentDispatcher = dispatcher;
/*  40 */     dispatcher.registerHandler(this);
/*     */   }
/*     */   
/*     */   public String getDispatchPath(RequestHandler handler) {
/*  44 */     StringBuffer retValue = StringBufferPool.getThreadInstance().get("/");
/*     */     try {
/*  46 */       if (this.parentDispatcher != null) {
/*  47 */         retValue.append(this.parentDispatcher.getDispatchPath(this));
/*     */       }
/*  49 */       if (handler != null) {
/*  50 */         String identifier = (String)this.handlerToID.get(handler);
/*  51 */         if (identifier == null) {
/*  52 */           return null;
/*     */         }
/*  54 */         retValue.append(this.prefix + identifier + "");
/*     */       } 
/*     */ 
/*     */       
/*  58 */       retValue.append("/");
/*  59 */       StringUtilities.replace(retValue, "//", "/");
/*     */       
/*  61 */       return retValue.toString();
/*     */     } finally {
/*     */       
/*  64 */       StringBufferPool.getThreadInstance().free(retValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   private RequestHandler retrieve(String identifier) {
/*  69 */     RequestHandler retValue = null;
/*  70 */     for (Iterator<Map.Entry> iter = this.handlerToID.entrySet().iterator(); iter.hasNext() && retValue == null; ) {
/*  71 */       Map.Entry entry = iter.next();
/*  72 */       if (identifier.equalsIgnoreCase((String)entry.getValue())) {
/*  73 */         retValue = (RequestHandler)entry.getKey();
/*     */       }
/*     */     } 
/*  76 */     return retValue;
/*     */   }
/*     */   
/*     */   public void registerHandler(RequestHandler handler) {
/*  80 */     if (!this.handlerToID.containsKey(handler)) {
/*  81 */       String identifier = IDFACTORY.getNextID();
/*  82 */       this.handlerToID.put(handler, identifier);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unregisterHandler(RequestHandler handler) {
/*  87 */     this.handlerToID.remove(handler);
/*     */   }
/*     */   
/*     */   public void handle(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*  91 */     String path = request.getPathInfo();
/*  92 */     if (path.indexOf("/") == 0) {
/*  93 */       path = path.substring(1);
/*     */     }
/*  95 */     if (this.parentDispatcher != null) {
/*  96 */       String pathPrefix = this.parentDispatcher.getDispatchPath(this);
/*  97 */       path = path.substring(pathPrefix.length());
/*     */     } 
/*     */     
/* 100 */     if (path.indexOf(this.prefix) == 0) {
/* 101 */       path = path.substring(this.prefix.length());
/*     */       
/* 103 */       String identifier = null;
/* 104 */       int index = path.indexOf("/");
/* 105 */       if (index != -1) {
/* 106 */         identifier = path.substring(0, index);
/*     */       } else {
/* 108 */         identifier = path;
/*     */       } 
/* 110 */       RequestHandler handler = retrieve(identifier);
/* 111 */       if (handler != null) {
/* 112 */         handler.handle(session, request, response);
/*     */       } else {
/* 114 */         log.warn("unable to dispatch request - no dispatchable found for identifier:" + identifier);
/* 115 */         handleUnknown(session, request, response);
/*     */       } 
/*     */     } else {
/* 118 */       log.info("handling non dispatch url (path info:" + String.valueOf(path) + ")");
/* 119 */       handleUnknown(session, request, response);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleUnknown(HttpSession session, HttpServletRequest request, HttpServletResponse response) {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtwo\servlet\dispatching\DispatcherImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */