/*    */ package com.eoos.scsm.v2.multiton.v4;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.AssertUtil;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StaticMultitonSupport
/*    */   implements IMultitonSupport
/*    */ {
/* 16 */   static final Logger log = Logger.getLogger(StaticMultitonSupport.class);
/*    */   
/* 18 */   private Map instances = new ConcurrentHashMap<Object, Object>();
/*    */   
/*    */   private IMultitonSupport.CreationCallback creationCallback;
/*    */   
/*    */   private IMultitonSupport.CreationCallbackExt extCreationCallback;
/*    */   
/*    */   public StaticMultitonSupport(IMultitonSupport.CreationCallback creationCallback) {
/* 25 */     AssertUtil.ensureNotNull(creationCallback);
/* 26 */     this.creationCallback = creationCallback;
/* 27 */     if (creationCallback instanceof IMultitonSupport.CreationCallbackExt) {
/* 28 */       this.extCreationCallback = (IMultitonSupport.CreationCallbackExt)creationCallback;
/* 29 */       if (Util.beParanoid()) {
/* 30 */         this.extCreationCallback = MultitonUtil.createCheckWrapper(this.extCreationCallback);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Object getInstance(Object key) {
/* 37 */     return getInstance(key, true);
/*    */   }
/*    */   
/*    */   public synchronized Object getInstance(Object key, boolean create) {
/* 41 */     Object instance = null;
/* 42 */     if (log.isDebugEnabled()) {
/* 43 */       log.debug("retrieving instance for: " + Util.toString(key));
/*    */     }
/* 45 */     instance = this.instances.get(key);
/* 46 */     if (instance == null && create) {
/* 47 */       if (log.isDebugEnabled()) {
/* 48 */         log.debug("...no instance found, creating instance for key: " + Util.toString(key));
/*    */       }
/* 50 */       if (this.extCreationCallback != null) {
/* 51 */         key = this.extCreationCallback.createStorageReplacement(key);
/*    */       }
/*    */       
/* 54 */       instance = this.creationCallback.createInstance(key);
/* 55 */       if (log.isDebugEnabled()) {
/* 56 */         log.debug("...created: " + Util.toString(instance));
/*    */       }
/* 58 */       this.instances.put(key, instance);
/*    */     } else {
/* 60 */       log.debug("...instance found");
/*    */     } 
/* 62 */     if (log.isDebugEnabled()) {
/* 63 */       log.debug("...returning: " + Util.toString(instance));
/*    */     }
/* 65 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized void removeInstance(Object object, IMultitonSupport.ArgumentType type) {
/* 69 */     if (object != null) {
/* 70 */       AssertUtil.ensure(type, AssertUtil.NOT_NULL);
/* 71 */       if (type == KEY) {
/* 72 */         this.instances.remove(object);
/*    */       } else {
/* 74 */         synchronized (this.instances) {
/* 75 */           for (Iterator<Map.Entry> iter = this.instances.entrySet().iterator(); iter.hasNext(); ) {
/* 76 */             Map.Entry entry = iter.next();
/* 77 */             if (object.equals(entry.getValue())) {
/* 78 */               iter.remove();
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void visitInstances(IMultitonSupport.Visitor visitor) {
/* 87 */     synchronized (this.instances) {
/* 88 */       for (Iterator iter = this.instances.values().iterator(); iter.hasNext();)
/* 89 */         visitor.onVisit(iter.next()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\multiton\v4\StaticMultitonSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */