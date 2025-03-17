/*    */ package com.eoos.scsm.v2.objectpool.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.ObjectPool;
/*    */ import java.util.Stack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BulkCreationSPIAdapter
/*    */   implements ObjectPool.SPI
/*    */ {
/*    */   private ObjectPool.SPI backend;
/*    */   private int bulkSize;
/* 16 */   private Stack stack = new Stack();
/*    */   
/*    */   public BulkCreationSPIAdapter(ObjectPool.SPI backend, int bulkSize) {
/* 19 */     this.backend = backend;
/* 20 */     this.bulkSize = bulkSize;
/*    */   }
/*    */   
/*    */   public synchronized Object create() {
/* 24 */     if (this.stack.isEmpty()) {
/* 25 */       for (int i = 0; i < this.bulkSize; i++) {
/* 26 */         this.stack.push(this.backend.create());
/*    */       }
/*    */     }
/* 29 */     return this.stack.pop();
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset(Object object) {
/* 34 */     this.backend.reset(object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\objectpoo\\util\BulkCreationSPIAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */