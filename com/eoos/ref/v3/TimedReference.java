/*    */ package com.eoos.ref.v3;
/*    */ 
/*    */ import com.eoos.condition.Condition;
/*    */ import com.eoos.util.v2.AssertUtil;
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
/*    */ public class TimedReference
/*    */   implements IReference, IReference.Snoopable
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(TimedReference.class);
/*    */   
/*    */   private long maxInactivity;
/*    */   
/*    */   private LastAccessRecordingRef backend;
/*    */   
/*    */   public TimedReference(Object referent, final long maxInactivity) {
/* 25 */     AssertUtil.ensure((Condition)new AssertUtil.ExtCondition()
/*    */         {
/*    */           public String getExplanation(Object testedObject) {
/* 28 */             return "maxInactivity for TimedReference must be greater 0";
/*    */           }
/*    */           
/*    */           public boolean check(Object obj) {
/* 32 */             return (maxInactivity > 0L);
/*    */           }
/*    */         });
/*    */     
/* 36 */     this.maxInactivity = maxInactivity;
/* 37 */     this.backend = new LastAccessRecordingRef(referent);
/*    */   }
/*    */   
/*    */   public Object get() {
/* 41 */     if (this.backend == null)
/* 42 */       return null; 
/* 43 */     if (System.currentTimeMillis() - this.backend.getLastAccess() > this.maxInactivity) {
/* 44 */       log.debug("clearing reference");
/* 45 */       Object referent = this.backend.snoop();
/* 46 */       cleanup(referent);
/* 47 */       this.backend = null;
/* 48 */       return null;
/*    */     } 
/* 50 */     return this.backend.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object snoop() {
/* 55 */     if (this.backend == null)
/* 56 */       return null; 
/* 57 */     if (System.currentTimeMillis() - this.backend.getLastAccess() > this.maxInactivity) {
/* 58 */       log.debug("clearing reference");
/* 59 */       this.backend = null;
/* 60 */       return null;
/*    */     } 
/* 62 */     return this.backend.snoop();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return super.toString() + "[" + ((this.backend != null) ? (String.valueOf(this.backend.snoop()) + ", maxInactivity: " + this.maxInactivity) : "NULL") + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   protected void cleanup(Object referent) {}
/*    */ 
/*    */   
/*    */   public void clear() {
/* 75 */     this.backend.clear();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\ref\v3\TimedReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */