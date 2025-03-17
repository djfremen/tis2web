/*    */ package com.eoos.ref.v3;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LastAccessRecordingRef
/*    */   implements IReference, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Object referent;
/*    */   private long lastAccess;
/*    */   
/*    */   public LastAccessRecordingRef(Object referent) {
/* 17 */     this.referent = referent;
/* 18 */     this.lastAccess = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get() {
/* 23 */     this.lastAccess = System.currentTimeMillis();
/* 24 */     return snoop();
/*    */   }
/*    */   
/*    */   public Object snoop() {
/* 28 */     return this.referent;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLastAccess() {
/* 33 */     return this.lastAccess;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 37 */     this.referent = null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\ref\v3\LastAccessRecordingRef.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */