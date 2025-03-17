/*    */ package com.eoos.scsm.v2.collection.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayIterator
/*    */   implements Iterator
/*    */ {
/*    */   private Object[] array;
/* 15 */   private int index = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayIterator(Object[] array) {
/* 24 */     if (array == null) {
/* 25 */       throw new IllegalArgumentException();
/*    */     }
/* 27 */     this.array = array;
/*    */   }
/*    */   
/*    */   public void remove() {
/* 31 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 35 */     return (this.index < this.array.length - 1);
/*    */   }
/*    */   
/*    */   public Object next() {
/*    */     try {
/* 40 */       return this.array[++this.index];
/* 41 */     } catch (IndexOutOfBoundsException e) {
/* 42 */       throw new NoSuchElementException();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\collectio\\util\ArrayIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */