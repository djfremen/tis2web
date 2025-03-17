/*     */ package com.eoos.collection.v2;
/*     */ 
/*     */ import com.eoos.collection.v2.util.ArrayIterator;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollectionUtil
/*     */ {
/*     */   public static Object getFirstMatch(Collection collection, Filter filter) {
/*  27 */     for (Iterator iter = collection.iterator(); iter.hasNext(); ) {
/*  28 */       Object obj = iter.next();
/*  29 */       if (filter.include(obj)) {
/*  30 */         return obj;
/*     */       }
/*     */     } 
/*  33 */     return null;
/*     */   }
/*     */   
/*     */   public static void filter(Collection collection, Filter filter) {
/*  37 */     for (Iterator iter = collection.iterator(); iter.hasNext();) {
/*  38 */       if (!filter.include(iter.next())) {
/*  39 */         iter.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void union(Collection a, Collection b) {
/*  45 */     a.addAll(b);
/*     */   }
/*     */   
/*     */   public static void intersect(Collection a, Collection<?> b) {
/*  49 */     a.retainAll(b);
/*     */   }
/*     */   
/*     */   public static void substract(Collection from, Collection<?> b) {
/*  53 */     from.removeAll(b);
/*     */   }
/*     */   
/*     */   public static void unify(Collection c) {
/*  57 */     unify(c, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unify(Collection c, final Transforming transforming) {
/*  62 */     Filter filter = new Filter() {
/*  63 */         private Set processed = new HashSet();
/*     */         
/*     */         public boolean include(Object obj) {
/*  66 */           if (transforming != null) {
/*  67 */             obj = transforming.transform(obj);
/*     */           }
/*  69 */           if (this.processed.contains(obj)) {
/*  70 */             return false;
/*     */           }
/*  72 */           this.processed.add(obj);
/*  73 */           return true;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  78 */     filter(c, filter);
/*     */   }
/*     */   
/*     */   public static Set ensureSet(Collection<?> c) {
/*  82 */     if (c == null)
/*  83 */       return Collections.EMPTY_SET; 
/*  84 */     if (c instanceof Set) {
/*  85 */       return (Set)c;
/*     */     }
/*  87 */     return new HashSet(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface DelimiterCallback
/*     */   {
/*     */     String getStartDelimiter();
/*     */     
/*     */     String getEndDelimiter();
/*     */     
/*     */     String getSeparator();
/*     */   }
/*     */   
/*     */   public static String toString(Iterator iter, DelimiterCallback callback) {
/* 101 */     StringBuffer retValue = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 103 */       String delimStart = callback.getStartDelimiter();
/* 104 */       if (delimStart != null) {
/* 105 */         retValue.append(delimStart);
/*     */       }
/* 107 */       String separator = callback.getSeparator();
/* 108 */       while (iter.hasNext()) {
/* 109 */         retValue.append(String.valueOf(iter.next()));
/* 110 */         if (iter.hasNext() && separator != null) {
/* 111 */           retValue.append(separator);
/*     */         }
/*     */       } 
/* 114 */       String delimEnd = callback.getEndDelimiter();
/* 115 */       if (delimEnd != null) {
/* 116 */         retValue.append(delimEnd);
/*     */       }
/* 118 */       return retValue.toString();
/*     */     } finally {
/*     */       
/* 121 */       StringBufferPool.getThreadInstance().free(retValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toString(Iterator iter) {
/* 126 */     return toString(iter, new DelimiterCallback()
/*     */         {
/*     */           public String getStartDelimiter() {
/* 129 */             return "[";
/*     */           }
/*     */           
/*     */           public String getSeparator() {
/* 133 */             return ", ";
/*     */           }
/*     */           
/*     */           public String getEndDelimiter() {
/* 137 */             return "]";
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toString(Object[] array) {
/* 144 */     if (array == null) {
/* 145 */       return "null";
/*     */     }
/* 147 */     return toString((Iterator)new ArrayIterator(array));
/*     */   }
/*     */ 
/*     */   
/*     */   public static List toList(Collection<?> c) {
/* 152 */     List<Comparable> retValue = null;
/* 153 */     if (c != null) {
/* 154 */       if (c instanceof List) {
/* 155 */         retValue = (List)c;
/*     */       } else {
/* 157 */         retValue = new LinkedList(c);
/* 158 */         Collections.sort(retValue);
/*     */       } 
/*     */     }
/* 161 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean containsOneOf(Collection collection1, Collection collection2) {
/*     */     Collection smallerCollection, otherCollection;
/* 175 */     boolean retValue = false;
/*     */ 
/*     */     
/* 178 */     if (collection1.size() >= collection2.size()) {
/* 179 */       smallerCollection = collection2;
/* 180 */       otherCollection = collection1;
/*     */     } else {
/* 182 */       smallerCollection = collection1;
/* 183 */       otherCollection = collection2;
/*     */     } 
/* 185 */     for (Iterator iter = smallerCollection.iterator(); iter.hasNext() && !retValue;) {
/* 186 */       retValue = otherCollection.contains(iter.next());
/*     */     }
/* 188 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\collection\v2\CollectionUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */