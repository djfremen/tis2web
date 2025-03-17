/*     */ package com.eoos.util;
/*     */ 
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pager
/*     */ {
/*     */   public static class Index
/*     */   {
/*     */     public int pageIndex;
/*     */     public int offset;
/*     */     
/*     */     public String toString() {
/*  17 */       return "pageIndex:" + this.pageIndex + " offset:" + this.offset;
/*     */     }
/*     */   }
/*     */   
/*  21 */   private static final List EMPTYLIST = new ArrayList();
/*     */   
/*     */   private List list;
/*  24 */   private int pageSize = 10;
/*  25 */   private int currentPageIndex = 0;
/*     */ 
/*     */   
/*     */   public Pager(int pageSize, List list) {
/*  29 */     setPageSize(pageSize);
/*  30 */     this.currentPageIndex = 0;
/*  31 */     this.list = (list == null) ? EMPTYLIST : list;
/*     */   }
/*     */   
/*     */   public Pager(int pageSize) {
/*  35 */     this(pageSize, null);
/*     */   }
/*     */   
/*     */   public synchronized void setList(List list) {
/*  39 */     this.list = list;
/*     */   }
/*     */   
/*     */   public synchronized void setPageSize(int pageSize) {
/*  43 */     this.pageSize = pageSize;
/*     */   }
/*     */   
/*     */   protected List getList() {
/*  47 */     return this.list;
/*     */   }
/*     */   
/*     */   public int getPageSize() {
/*  51 */     return this.pageSize;
/*     */   }
/*     */   
/*     */   public synchronized int getPageCount() {
/*  55 */     if (this.pageSize == -1) {
/*  56 */       return 1;
/*     */     }
/*  58 */     return getList().size() / this.pageSize + ((getList().size() % this.pageSize != 0) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized List getPage(int index) {
/*  63 */     if (index < 0 || index > getPageCount() - 1) {
/*  64 */       return EMPTYLIST;
/*     */     }
/*  66 */     if (this.pageSize == -1) {
/*  67 */       return getList();
/*     */     }
/*  69 */     SectionIndex sectionIndex = new SectionIndex();
/*  70 */     sectionIndex.start = index * this.pageSize;
/*  71 */     sectionIndex.end = sectionIndex.start + this.pageSize;
/*  72 */     return getList().subList(sectionIndex.start, Math.min(sectionIndex.end, getList().size()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized List getCurrentPage() {
/*  78 */     gotoPage(this.currentPageIndex);
/*  79 */     return getPage(this.currentPageIndex);
/*     */   }
/*     */   
/*     */   public synchronized int getCurrentPageIndex() {
/*  83 */     return this.currentPageIndex;
/*     */   }
/*     */   
/*     */   public synchronized void gotoPage(int pageIndex) {
/*  87 */     if (pageIndex < 0) {
/*  88 */       this.currentPageIndex = 0;
/*     */     } else {
/*  90 */       this.currentPageIndex = (pageIndex > getPageCount() - 1) ? (getPageCount() - 1) : pageIndex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized int toAbsoluteIndex(int index) {
/*  95 */     if (this.pageSize == -1) {
/*  96 */       return index;
/*     */     }
/*  98 */     return this.currentPageIndex * this.pageSize + index;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Index toRelativeIndex(int index) {
/* 103 */     Index relativeIndex = new Index();
/* 104 */     if (this.pageSize == -1) {
/* 105 */       relativeIndex.pageIndex = 0;
/* 106 */       relativeIndex.offset = index;
/*     */     } else {
/* 108 */       relativeIndex.pageIndex = index / this.pageSize;
/* 109 */       relativeIndex.offset = index % this.pageSize;
/*     */     } 
/* 111 */     return relativeIndex;
/*     */   }
/*     */   
/*     */   public synchronized Object get(int relativeIndex) {
/* 115 */     return getList().get(toAbsoluteIndex(relativeIndex));
/*     */   }
/*     */   
/*     */   public synchronized List getCompleteList() {
/* 119 */     return getList();
/*     */   }
/*     */   
/*     */   public synchronized Index getIndexOf(Object object) {
/* 123 */     int realIndex = getList().indexOf(object);
/* 124 */     if (realIndex == -1) {
/* 125 */       return null;
/*     */     }
/* 127 */     return toRelativeIndex(realIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean empty() {
/* 132 */     return (getList().size() == 0);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\Pager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */