/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.filter.Filter;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import com.eoos.util.Util;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PairCollectionDecorator
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 23 */   private static final Logger log = Logger.getLogger(PairCollectionDecorator.class);
/*    */   
/*    */   private class KeyFilter
/*    */     implements Filter {
/* 27 */     private Object key = null;
/*    */     
/*    */     private int mode;
/*    */     
/*    */     public KeyFilter(Object key, int mode) {
/* 32 */       this.key = key;
/* 33 */       this.mode = mode;
/*    */     }
/*    */     
/*    */     public boolean include(Object obj) {
/* 37 */       boolean retValue = false;
/*    */       try {
/* 39 */         Pair pair = (Pair)obj;
/* 40 */         if (this.mode == 1) {
/* 41 */           Object first = pair.getFirst();
/* 42 */           retValue = (Util.equals(first, this.key) || Util.equals(first, "*"));
/*    */         } else {
/* 44 */           Object second = pair.getSecond();
/* 45 */           retValue = (Util.equals(second, this.key) || Util.equals(second, "*"));
/*    */         }
/*    */       
/* 48 */       } catch (Exception e) {
/* 49 */         PairCollectionDecorator.log.warn("unable to determine inclusion state, returning false - exception: " + e, e);
/*    */       } 
/* 51 */       return retValue;
/*    */     }
/*    */   }
/*    */   
/* 55 */   protected Set pairs = new HashSet();
/*    */   
/*    */   public static final int MODE_FIRST = 1;
/*    */   public static final int MODE_SECOND = 2;
/*    */   
/*    */   public PairCollectionDecorator(Collection pairs) {
/* 61 */     this.pairs.addAll(pairs);
/*    */   }
/*    */   
/*    */   public void put(Pair pair) {
/* 65 */     this.pairs.add(pair);
/*    */   }
/*    */   
/*    */   public Collection getAssigned(Object key, int mode) {
/* 69 */     Collection<Object> retValue = new HashSet();
/* 70 */     Filter filter = new KeyFilter(key, mode);
/* 71 */     for (Iterator<Pair> iter = CollectionUtil.filterAndReturn(new ArrayList(this.pairs), filter).iterator(); iter.hasNext(); ) {
/* 72 */       Pair pair = iter.next();
/* 73 */       if (mode == 1) {
/* 74 */         retValue.add(pair.getSecond()); continue;
/*    */       } 
/* 76 */       retValue.add(pair.getFirst());
/*    */     } 
/*    */     
/* 79 */     return retValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection getDomain(int mode) {
/* 87 */     Collection<Object> retValue = new HashSet();
/* 88 */     for (Iterator<Pair> iter = this.pairs.iterator(); iter.hasNext(); ) {
/* 89 */       Pair pair = iter.next();
/* 90 */       if (mode == 1) {
/* 91 */         retValue.add(pair.getFirst()); continue;
/*    */       } 
/* 93 */       retValue.add(pair.getSecond());
/*    */     } 
/*    */     
/* 96 */     return retValue;
/*    */   }
/*    */   
/*    */   public PairCollectionDecorator() {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\ltlist\page\PairCollectionDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */