/*    */ package com.eoos.scsm.v2.filter;
/*    */ 
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class LinkedFilter
/*    */   implements Filter
/*    */ {
/*    */   public static class Link
/*    */   {
/*    */     private Link() {}
/*    */   }
/*    */   
/* 16 */   public static final Link AND = new Link();
/*    */   
/* 18 */   public static final Link OR = new Link();
/*    */   
/* 20 */   public static final Link XOR = new Link();
/*    */   
/*    */   private Link link;
/*    */   
/*    */   private Collection filters;
/*    */   
/*    */   public LinkedFilter(Filter[] filters, Link link) {
/* 27 */     this(CollectionUtil.toList((Object[])filters), link);
/*    */   }
/*    */   
/*    */   public LinkedFilter(Collection collection, Link link) {
/* 31 */     this.filters = collection;
/* 32 */     if (Util.isNullOrEmpty(collection)) {
/* 33 */       throw new IllegalArgumentException();
/*    */     }
/* 35 */     this.link = link;
/*    */   }
/*    */   
/*    */   public boolean include(Object obj) {
/*    */     boolean ret;
/* 40 */     if (this.link == AND) {
/* 41 */       ret = true;
/* 42 */       for (Iterator<Filter> iter = this.filters.iterator(); iter.hasNext() && ret;) {
/* 43 */         ret = ((Filter)iter.next()).include(obj);
/*    */       }
/* 45 */     } else if (this.link == OR) {
/* 46 */       ret = false;
/* 47 */       for (Iterator<Filter> iter = this.filters.iterator(); iter.hasNext() && !ret;) {
/* 48 */         ret = ((Filter)iter.next()).include(obj);
/*    */       }
/* 50 */     } else if (this.link == XOR) {
/* 51 */       ret = false;
/* 52 */       Iterator<Filter> iter = this.filters.iterator();
/* 53 */       while (iter.hasNext() && !ret) {
/* 54 */         ret = ((Filter)iter.next()).include(obj);
/*    */       }
/* 56 */       while (iter.hasNext() && ret) {
/* 57 */         ret = !((Filter)iter.next()).include(obj);
/*    */       }
/*    */     } else {
/* 60 */       throw new IllegalStateException("unknown link");
/*    */     } 
/* 62 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\filter\LinkedFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */