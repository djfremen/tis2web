/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import com.eoos.util.IDFactory;
/*    */ import com.eoos.util.IDFactoryImpl;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IDFactory
/*    */   implements IDFactory
/*    */ {
/*    */   private static final int SIZE = 400;
/* 21 */   private List ids = Collections.synchronizedList(new ArrayList(400));
/*    */   
/* 23 */   private IDFactoryImpl idf1 = new IDFactoryImpl(2, true);
/*    */   
/* 25 */   private IDFactoryImpl idf2 = new IDFactoryImpl(2, true);
/*    */   
/* 27 */   private IDFactoryImpl idf3 = new IDFactoryImpl(2, true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private synchronized void create() {
/* 36 */     List<String> id1 = new ArrayList(400);
/* 37 */     List<String> id2 = new ArrayList(400);
/* 38 */     List<String> id3 = new ArrayList(400); int i;
/* 39 */     for (i = 0; i < 400; i++) {
/* 40 */       id1.add(this.idf1.getNextID());
/* 41 */       id2.add(this.idf2.getNextID());
/* 42 */       id3.add(this.idf3.getNextID());
/*    */     } 
/* 44 */     Collections.shuffle(id1);
/* 45 */     Collections.shuffle(id2);
/* 46 */     Collections.shuffle(id3);
/*    */     
/* 48 */     for (i = 0; i < 400; i++) {
/* 49 */       String id = (String)id1.get(i) + (String)id2.get(i) + (String)id3.get(i);
/* 50 */       this.ids.add(id);
/*    */     } 
/* 52 */     Collections.shuffle(this.ids);
/*    */   }
/*    */   
/*    */   public synchronized String getNextID() {
/* 56 */     if (this.ids.size() == 0) {
/* 57 */       create();
/*    */     }
/* 59 */     return this.ids.remove(0);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 63 */     IDFactory idf = new IDFactory();
/* 64 */     for (int i = 0; i < 600; i++)
/* 65 */       System.out.println(idf.getNextID()); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\IDFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */