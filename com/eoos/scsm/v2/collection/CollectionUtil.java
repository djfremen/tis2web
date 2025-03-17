/*     */ package com.eoos.scsm.v2.collection;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.scsm.v2.collection.util.ArrayIterator;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.objectpool.SetPool;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollectionUtil
/*     */ {
/*  32 */   public static final Properties EMPTY_PROPERTIES = new Properties()
/*     */     {
/*     */       private static final long serialVersionUID = 1L;
/*     */       
/*     */       public synchronized Object setProperty(String key, String value) {
/*  37 */         throw new UnsupportedOperationException("immutable instance");
/*     */       }
/*     */       
/*     */       public synchronized void clear() {
/*  41 */         throw new UnsupportedOperationException("immutable instance");
/*     */       }
/*     */       
/*     */       public Set entrySet() {
/*  45 */         return Collections.EMPTY_SET;
/*     */       }
/*     */       
/*     */       public Set keySet() {
/*  49 */         return Collections.EMPTY_SET;
/*     */       }
/*     */       
/*     */       public synchronized Object put(Object key, Object value) {
/*  53 */         throw new UnsupportedOperationException("immutable instance");
/*     */       }
/*     */       
/*     */       public synchronized void putAll(Map t) {
/*  57 */         throw new UnsupportedOperationException("immutable instance");
/*     */       }
/*     */       
/*     */       public synchronized Object remove(Object key) {
/*  61 */         throw new UnsupportedOperationException("immutable instance");
/*     */       }
/*     */       
/*     */       public Collection values() {
/*  65 */         return Collections.EMPTY_SET;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFirstMatch(Collection collection, Filter filter) {
/*  74 */     for (Iterator iter = collection.iterator(); iter.hasNext(); ) {
/*  75 */       Object obj = iter.next();
/*  76 */       if (filter.include(obj)) {
/*  77 */         return obj;
/*     */       }
/*     */     } 
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public static void filter(Collection collection, Filter filter) {
/*  84 */     if (collection != null) {
/*  85 */       if (Filter.EXCLUDE_ALL == filter) {
/*  86 */         collection.clear();
/*  87 */       } else if (filter != null && Filter.INCLUDE_ALL != filter) {
/*  88 */         for (Iterator iter = collection.iterator(); iter.hasNext();) {
/*  89 */           if (!filter.include(iter.next())) {
/*  90 */             iter.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void filter(Collection collection, final Filter filter) {
/*  99 */     filter(collection, new Filter()
/*     */         {
/*     */           public boolean include(Object obj) {
/* 102 */             return filter.include(obj);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static Collection filterAndReturn(Collection collection, Filter filter) {
/* 108 */     filter(collection, filter);
/* 109 */     return collection;
/*     */   }
/*     */   
/*     */   public static Collection filterAndReturn(Collection collection, Filter filter) {
/* 113 */     filter(collection, filter);
/* 114 */     return collection;
/*     */   }
/*     */   
/*     */   public static void union(Collection a, Collection b) {
/* 118 */     a.addAll(b);
/*     */   }
/*     */   
/*     */   public static void intersect(Collection a, Collection<?> b) {
/* 122 */     a.retainAll(b);
/*     */   }
/*     */   
/*     */   public static Collection intersectAndReturn(Collection ret, Collection b) {
/* 126 */     intersect(ret, b);
/* 127 */     return ret;
/*     */   }
/*     */   
/*     */   public static boolean haveIntersection(Collection a, Collection b) {
/* 131 */     SetPool poolSet = SetPool.getThreadInstance();
/* 132 */     Set tmp = poolSet.get();
/*     */     try {
/* 134 */       tmp.addAll(a);
/* 135 */       intersect(tmp, b);
/* 136 */       return (tmp.size() > 0);
/*     */     } finally {
/* 138 */       poolSet.free(tmp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void substract(Collection from, Collection<?> b) {
/* 143 */     from.removeAll(b);
/*     */   }
/*     */   
/*     */   public static void unify(Collection c) {
/* 147 */     final Set processed = SetPool.getThreadInstance().get();
/*     */     try {
/* 149 */       Filter filter = new Filter()
/*     */         {
/*     */           public boolean include(Object obj) {
/* 152 */             if (processed.contains(obj)) {
/* 153 */               return false;
/*     */             }
/* 155 */             processed.add(obj);
/* 156 */             return true;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 161 */       filter(c, filter);
/*     */     } finally {
/* 163 */       SetPool.getThreadInstance().free(processed);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface ForeachCallback {
/*     */     public static interface ResultHandling {
/* 169 */       public static final ResultHandling STD = new ResultHandling()
/*     */         {
/*     */           public Object addLastResult(Object result, Object lastResult) {
/* 172 */             if (result == null) {
/* 173 */               result = new LinkedList();
/*     */             }
/* 175 */             ((List<Object>)result).add(lastResult);
/* 176 */             return result;
/*     */           }
/*     */         };
/*     */       
/* 180 */       public static final ResultHandling FLATTENSET = new ResultHandling()
/*     */         {
/*     */           public Object addLastResult(Object result, Object lastResult) {
/* 183 */             if (result == null) {
/* 184 */               result = new LinkedHashSet();
/*     */             }
/* 186 */             ((Set)result).addAll((Collection)lastResult);
/* 187 */             return result;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/*     */       Object addLastResult(Object param2Object1, Object param2Object2);
/*     */     }
/* 194 */     public static final Exception ABORT_ITERATION = new Exception();
/*     */ 
/*     */     
/*     */     Object executeOperation(Object param1Object) throws Exception;
/*     */   }
/*     */   
/*     */   public static Object foreach(Collection c, ForeachCallback callback) {
/* 201 */     return foreach(c, callback, ForeachCallback.ResultHandling.STD); } public static interface ResultHandling {
/*     */     public static final ResultHandling STD = new ResultHandling() { public Object addLastResult(Object result, Object lastResult) { if (result == null) result = new LinkedList();  ((List<Object>)result).add(lastResult); return result; } }
/*     */     ; public static final ResultHandling FLATTENSET = new ResultHandling() { public Object addLastResult(Object result, Object lastResult) { if (result == null)
/*     */             result = new LinkedHashSet();  ((Set)result).addAll((Collection)lastResult); return result; } }
/* 205 */     ; Object addLastResult(Object param1Object1, Object param1Object2); } public static Object foreach(Collection c, ForeachCallback callback, ForeachCallback.ResultHandling resultHandling) { Object ret = null;
/*     */     try {
/* 207 */       resultHandling = (resultHandling != null) ? resultHandling : ForeachCallback.ResultHandling.STD;
/* 208 */       for (Iterator iter = c.iterator(); iter.hasNext(); ) {
/* 209 */         Object result = callback.executeOperation(iter.next());
/* 210 */         ret = resultHandling.addLastResult(ret, result);
/*     */       } 
/* 212 */     } catch (Exception e) {
/* 213 */       if (e != ForeachCallback.ABORT_ITERATION) {
/* 214 */         throw Util.toRuntimeException(e);
/*     */       }
/*     */     } 
/* 217 */     return ret; }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object foreachUntilNotNull(Collection c, ForeachCallback callback) {
/*     */     try {
/* 223 */       Object ret = null;
/* 224 */       for (Iterator iter = c.iterator(); iter.hasNext() && ret == null;) {
/* 225 */         ret = callback.executeOperation(iter.next());
/*     */       }
/* 227 */       return ret;
/* 228 */     } catch (Exception e) {
/* 229 */       if (e != ForeachCallback.ABORT_ITERATION) {
/* 230 */         throw Util.toRuntimeException(e);
/*     */       }
/* 232 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkUntilTrue(Collection c, CheckCallback callback) {
/* 242 */     boolean ret = false;
/* 243 */     for (Iterator iter = c.iterator(); iter.hasNext() && !ret;) {
/* 244 */       ret = callback.check(iter.next());
/*     */     }
/* 246 */     return ret;
/*     */   }
/*     */   
/*     */   public static boolean checkUntilFalse(Collection c, CheckCallback callback) {
/* 250 */     boolean ret = true;
/* 251 */     for (Iterator iter = c.iterator(); iter.hasNext() && !ret;) {
/* 252 */       ret = callback.check(iter.next());
/*     */     }
/* 254 */     return ret;
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
/*     */   
/*     */   public static Set toSet(Collection<?> c) {
/* 268 */     if (c == null)
/* 269 */       return Collections.EMPTY_SET; 
/* 270 */     if (c instanceof Set) {
/* 271 */       return (Set)c;
/*     */     }
/* 273 */     return new LinkedHashSet(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toString(Iterator iter) {
/* 278 */     StringBuffer retValue = StringBufferPool.getThreadInstance().get("[");
/*     */     try {
/* 280 */       while (iter.hasNext()) {
/* 281 */         retValue.append(String.valueOf(iter.next()));
/* 282 */         if (iter.hasNext()) {
/* 283 */           retValue.append(", ");
/*     */         }
/*     */       } 
/* 286 */       retValue.append("]");
/* 287 */       return retValue.toString();
/*     */     } finally {
/*     */       
/* 290 */       StringBufferPool.getThreadInstance().free(retValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toString(Object[] array) {
/* 295 */     if (array == null) {
/* 296 */       return "null";
/*     */     }
/* 298 */     return toString((Iterator)new ArrayIterator(array));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toString(Collection c) {
/* 303 */     if (c == null) {
/* 304 */       return "null";
/*     */     }
/* 306 */     return toString(c.iterator());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static List toSortedList(Collection<?> c, Comparator<?> cmp) {
/* 322 */     List<?> retValue = null;
/* 323 */     if (c instanceof List) {
/* 324 */       retValue = (List)c;
/*     */     } else {
/* 326 */       retValue = new LinkedList(c);
/*     */     } 
/* 328 */     Collections.sort(retValue, cmp);
/* 329 */     return retValue;
/*     */   }
/*     */   
/*     */   public static List toList(Collection<?> c) {
/* 333 */     List retValue = null;
/* 334 */     if (c instanceof List) {
/* 335 */       retValue = (List)c;
/*     */     } else {
/* 337 */       retValue = new LinkedList(c);
/*     */     } 
/* 339 */     return retValue;
/*     */   }
/*     */   
/*     */   public static List toList(Object[] a) {
/* 343 */     return Arrays.asList(a);
/*     */   }
/*     */   
/*     */   public static LinkedList newList(Object[] a) {
/* 347 */     return new LinkedList(toList(a));
/*     */   }
/*     */   
/*     */   public static LinkedHashSet toSet(Object[] a) {
/* 351 */     return new LinkedHashSet(toList(a));
/*     */   }
/*     */   
/*     */   public static Map reverseMapOf(Map map) {
/* 355 */     Set entries = map.entrySet();
/* 356 */     Map<Object, Object> ret = new LinkedHashMap<Object, Object>(entries.size());
/* 357 */     for (Iterator<Map.Entry> iter = entries.iterator(); iter.hasNext(); ) {
/* 358 */       Map.Entry entry = iter.next();
/* 359 */       ret.put(entry.getValue(), entry.getKey());
/*     */     } 
/* 361 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E> E getFirst(Collection<E> collection) {
/* 366 */     E ret = null;
/* 367 */     if (collection != null && collection.size() > 0) {
/* 368 */       ret = collection.iterator().next();
/*     */     }
/* 370 */     return ret;
/*     */   }
/*     */   
/*     */   public static Collection flattenAndReturn(Collection originCollection, Collection targetCollection) {
/* 374 */     flatten(originCollection, targetCollection);
/* 375 */     return targetCollection;
/*     */   }
/*     */   
/*     */   public static void flatten(Collection origin, Collection target) {
/* 379 */     for (Iterator<Collection> iter = origin.iterator(); iter.hasNext();) {
/* 380 */       target.addAll(iter.next());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unify(Collection c, final Transforming transforming) {
/* 386 */     Filter filter = new Filter() {
/* 387 */         private Set processed = new HashSet();
/*     */         
/*     */         public boolean include(Object obj) {
/* 390 */           if (transforming != null) {
/* 391 */             obj = transforming.transform(obj);
/*     */           }
/* 393 */           if (this.processed.contains(obj)) {
/* 394 */             return false;
/*     */           }
/* 396 */           this.processed.add(obj);
/* 397 */           return true;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 402 */     filter(c, filter);
/*     */   }
/*     */   
/* 405 */   public static final Iterator NULL_ITERATOR = new Iterator()
/*     */     {
/*     */       public void remove() {
/* 408 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Object next() {
/* 412 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/*     */       public boolean hasNext() {
/* 416 */         return false;
/*     */       }
/*     */     };
/*     */   
/*     */   public static Iterator iterator(Collection c) {
/* 421 */     if (c != null) {
/* 422 */       return c.iterator();
/*     */     }
/* 424 */     return NULL_ITERATOR;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set mutableSingletonSet(Object obj) {
/* 429 */     Set<Object> ret = new HashSet(1);
/* 430 */     ret.add(obj);
/* 431 */     return ret;
/*     */   }
/*     */   
/*     */   public static interface CheckCallback {
/*     */     boolean check(Object param1Object);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\collection\CollectionUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */