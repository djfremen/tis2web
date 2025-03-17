/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import com.eoos.ref.v3.IReference;
/*    */ import com.eoos.ref.v3.IReferenceAdapter;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class AllOrNothingCacheBase
/*    */   implements Cache, Cache.Size, Cache.KeyQuery
/*    */ {
/* 14 */   private static final Logger log = Logger.getLogger(AllOrNothingCacheBase.class);
/*    */   
/* 16 */   protected final Object SYNC_MAP = new Object();
/*    */   
/* 18 */   private IReference refMap = null;
/*    */   
/*    */   private Integer expectedSize;
/*    */   
/*    */   protected AllOrNothingCacheBase() {
/* 23 */     this(null);
/*    */   }
/*    */   
/*    */   public AllOrNothingCacheBase(Integer expectedSize) {
/* 27 */     this.expectedSize = expectedSize;
/*    */   }
/*    */   
/*    */   protected Integer getExpectedSize() {
/* 31 */     return this.expectedSize;
/*    */   }
/*    */   
/*    */   protected Map getMap() {
/* 35 */     synchronized (this.SYNC_MAP) {
/* 36 */       Map ret = null;
/* 37 */       if (this.refMap == null || (ret = (Map)this.refMap.get()) == null) {
/* 38 */         log.debug("creating new backing map ..");
/* 39 */         this.refMap = createCacheReference(createBackingMap(getExpectedSize()));
/*    */       } 
/* 41 */       return ret;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 46 */     getMap().clear();
/*    */   }
/*    */   
/*    */   public Object lookup(Object key) {
/* 50 */     return getMap().get(key);
/*    */   }
/*    */   
/*    */   public void store(Object key, Object information) {
/* 54 */     if (information != null) {
/* 55 */       getMap().put(key, information);
/*    */     } else {
/* 57 */       getMap().remove(key);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 63 */     return getMap().size();
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 67 */     return getMap().keySet();
/*    */   }
/*    */   
/*    */   protected IReference createCacheReference(Map backingMap) {
/* 71 */     return (IReference)new IReferenceAdapter(new SoftReference<Map>(backingMap));
/*    */   }
/*    */   
/*    */   protected Map createBackingMap(Integer expectedSize) {
/* 75 */     if (expectedSize == null) {
/* 76 */       return new ConcurrentHashMap<Object, Object>();
/*    */     }
/* 78 */     return new ConcurrentHashMap<Object, Object>(expectedSize.intValue());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\AllOrNothingCacheBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */