/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import com.eoos.ref.v3.IReference;
/*    */ import com.eoos.ref.v3.TimedMutationReference;
/*    */ 
/*    */ public class HotspotCache
/*    */   extends AbstractCache
/*    */ {
/*    */   private long maxInactivity;
/*    */   
/*    */   public HotspotCache(long maxInactivity) {
/* 12 */     this(maxInactivity, null);
/*    */   }
/*    */   
/*    */   public HotspotCache(long maxInactivity, Integer expectedSize) {
/* 16 */     super(expectedSize);
/* 17 */     this.maxInactivity = maxInactivity;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IReference createReference(Object information) {
/* 22 */     return (IReference)new TimedMutationReference(information, TimedMutationReference.Type.SOFT, TimedMutationReference.Type.WEAK, this.maxInactivity, true, null);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\HotspotCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */