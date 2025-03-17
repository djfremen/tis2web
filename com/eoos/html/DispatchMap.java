/*    */ package com.eoos.html;
/*    */ 
/*    */ import com.eoos.ref.v3.IReference;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DispatchMap
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(DispatchMap.class);
/*    */   
/* 24 */   private static DispatchMap instance = new DispatchMap();
/*    */   
/* 26 */   private Map map = new ConcurrentHashMap<Object, Object>(1000);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DispatchMap getInstance() {
/* 33 */     return instance;
/*    */   }
/*    */   
/*    */   public void register(Dispatchable dispatchable) {
/* 37 */     if (dispatchable != null) {
/* 38 */       this.map.put(dispatchable.getIdentifier(), dispatchable);
/* 39 */       if (log.isTraceEnabled()) {
/* 40 */         log.trace("registered dispatchable " + dispatchable.getIdentifier());
/* 41 */         log.trace("...stack:" + Util.compactStackTrace(new Throwable(), 0, 10));
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void register(IReference refDispatchable) {
/* 47 */     if (refDispatchable != null) {
/* 48 */       Dispatchable d = (Dispatchable)refDispatchable.get();
/* 49 */       this.map.put(d.getIdentifier(), refDispatchable);
/*    */       
/* 51 */       if (log.isTraceEnabled()) {
/* 52 */         log.trace("registered dispatchable " + d.getIdentifier());
/* 53 */         log.trace("...stack:" + Util.compactStackTrace(new Throwable(), 0, 10));
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public Dispatchable get(Object identifier) {
/* 59 */     if (identifier != null) {
/* 60 */       Object obj = this.map.get(identifier);
/*    */       
/* 62 */       if (obj instanceof IReference) {
/* 63 */         obj = ((IReference)obj).get();
/*    */       }
/* 65 */       if (obj == null) {
/* 66 */         log.warn("unable to lookup dispatchable " + String.valueOf(identifier));
/*    */       }
/*    */       
/* 69 */       return (Dispatchable)obj;
/*    */     } 
/* 71 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void unregister(Dispatchable dispatchable) {
/* 76 */     if (dispatchable != null && dispatchable.getIdentifier() != null && 
/* 77 */       this.map.remove(dispatchable.getIdentifier()) != null)
/*    */     {
/* 79 */       if (log.isTraceEnabled()) {
/* 80 */         log.trace("unregistered dispatchable " + dispatchable.getIdentifier());
/* 81 */         log.trace("...stack:" + Util.compactStackTrace(new Throwable(), 0, 10));
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void unregister(IReference refDispatchable) {
/* 88 */     Iterator<Map.Entry> iter = this.map.entrySet().iterator();
/* 89 */     while (iter.hasNext()) {
/* 90 */       Map.Entry entry = iter.next();
/* 91 */       if (entry.getValue().equals(refDispatchable)) {
/* 92 */         if (log.isTraceEnabled()) {
/* 93 */           log.trace("unregistered dispatchable " + entry.getKey());
/* 94 */           log.trace("...stack:" + Util.compactStackTrace(new Throwable(), 0, 10));
/*    */         } 
/* 96 */         iter.remove();
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\DispatchMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */