/*     */ package com.eoos.scsm.v2.cache;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReadThroughCacheAdapter
/*     */ {
/*     */   private Cache cache;
/*     */   private ReadOperation readOperation;
/*     */   
/*     */   public ReadThroughCacheAdapter(Cache cache, ReadOperation readOperation) {
/*  59 */     this.cache = cache;
/*  60 */     this.readOperation = readOperation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object identifier) {
/*  73 */     Object retValue = this.cache.lookup(identifier);
/*  74 */     if (retValue == null) {
/*  75 */       retValue = this.readOperation.get(identifier);
/*  76 */       this.cache.store(identifier, retValue);
/*     */     } 
/*  78 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bulkGet(final BulkReadAdapter adapter) {
/*  83 */     if (this.readOperation instanceof BulkReadOperation) {
/*  84 */       final List<Object> unresolvedIdentifiers = new LinkedList();
/*  85 */       for (Iterator iter = adapter.getIdentifiers().iterator(); iter.hasNext(); ) {
/*  86 */         Object identifier = iter.next();
/*  87 */         Object obj = this.cache.lookup(identifier);
/*  88 */         if (obj != null) {
/*  89 */           adapter.setObject(identifier, obj); continue;
/*     */         } 
/*  91 */         unresolvedIdentifiers.add(identifier);
/*     */       } 
/*     */ 
/*     */       
/*  95 */       ((BulkReadOperation)this.readOperation).bulkRead(new BulkReadAdapter()
/*     */           {
/*     */             public void setObject(Object identifier, Object loadedObject) {
/*  98 */               ReadThroughCacheAdapter.this.cache.store(identifier, loadedObject);
/*  99 */               adapter.setObject(identifier, loadedObject);
/*     */             }
/*     */             
/*     */             public Collection getIdentifiers() {
/* 103 */               return unresolvedIdentifiers;
/*     */             }
/*     */           });
/*     */     }
/*     */     else {
/*     */       
/* 109 */       for (Iterator iter = adapter.getIdentifiers().iterator(); iter.hasNext(); ) {
/* 110 */         Object identifier = iter.next();
/* 111 */         adapter.setObject(identifier, get(identifier));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface BulkReadOperation extends ReadOperation {
/*     */     void bulkRead(ReadThroughCacheAdapter.BulkReadAdapter param1BulkReadAdapter);
/*     */   }
/*     */   
/*     */   public static interface ReadOperation {
/*     */     Object get(Object param1Object);
/*     */   }
/*     */   
/*     */   public static interface BulkReadAdapter {
/*     */     Collection getIdentifiers();
/*     */     
/*     */     void setObject(Object param1Object1, Object param1Object2);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\ReadThroughCacheAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */