/*    */ package com.eoos.scsm.v2.objectpool;
/*    */ 
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListPool
/*    */ {
/* 14 */   private ObjectPool delegate = ObjectPool.createInstance(new ObjectPool.SPI()
/*    */       {
/*    */         public void reset(Object object) {
/* 17 */           ((List)object).clear();
/*    */         }
/*    */         
/*    */         public Object create() {
/* 21 */           return new LinkedList();
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */   
/* 27 */   private static ThreadLocal instance = new ThreadLocal();
/*    */   
/*    */   public static ListPool getThreadInstance() {
/* 30 */     SoftReference<ListPool> srPool = instance.get();
/* 31 */     ListPool ret = (srPool != null) ? srPool.get() : null;
/* 32 */     if (ret == null) {
/* 33 */       ret = new ListPool();
/* 34 */       instance.set(new SoftReference<ListPool>(ret));
/*    */     } 
/* 36 */     return ret;
/*    */   }
/*    */   
/*    */   public List get() {
/* 40 */     return (List)this.delegate.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public List get(Collection c) {
/* 45 */     List ret = get();
/* 46 */     ret.addAll(c);
/* 47 */     return ret;
/*    */   }
/*    */   
/*    */   public void free(List list) {
/* 51 */     this.delegate.free(list);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpool\ListPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */