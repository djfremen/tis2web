/*     */ package com.eoos.scsm.v2.reflect;
/*     */ 
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionUtil
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(ReflectionUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Decorator
/*     */   {
/*     */     public static Object createMultiplexingFacade(Object[] backends, ReflectionUtil.ResultFlattening resultFlattening) {
/*  43 */       return ReflectionUtil.createMultiplexingFacade(backends, ReflectionUtil.ContinuationRule.ALWAYS, resultFlattening);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class[] getTypes(Object[] objects) {
/*  53 */     Class[] classes = new Class[objects.length];
/*  54 */     for (int i = 0; i < objects.length; i++) {
/*  55 */       classes[i] = objects[i].getClass();
/*     */     }
/*  57 */     return classes;
/*     */   }
/*     */   
/*     */   public static Collection getAllInterfaces(Class<?> clazz) {
/*  61 */     Collection<Class<?>> retValue = new LinkedHashSet();
/*  62 */     if (clazz.isInterface()) {
/*  63 */       retValue.add(clazz);
/*     */     }
/*  65 */     for (Iterator<Class<?>> iter = Arrays.<Class<?>>asList(clazz.getInterfaces()).iterator(); iter.hasNext(); ) {
/*  66 */       Class<?> c = iter.next();
/*  67 */       retValue.add(c);
/*  68 */       retValue.addAll(getAllInterfaces(c));
/*     */     } 
/*     */     
/*  71 */     if (clazz.getSuperclass() != null) {
/*  72 */       retValue.addAll(getAllInterfaces(clazz.getSuperclass()));
/*     */     }
/*  74 */     return retValue;
/*     */   }
/*     */   
/*     */   public static Method getSuitableMethod(Class clazz, String methodName, Object[] params) throws NoSuchMethodException {
/*  78 */     Class[] types = null;
/*  79 */     if (params != null) {
/*  80 */       types = new Class[params.length];
/*  81 */       for (int i = 0; i < params.length; i++) {
/*  82 */         types[i] = (params[i] != null) ? params[i].getClass() : Object.class;
/*     */       }
/*     */     } 
/*  85 */     return clazz.getMethod(methodName, types);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method getSuitableMethod(Class clazz, String methodName) throws NoSuchMethodException {
/*  90 */     Method[] methods = clazz.getMethods();
/*  91 */     for (int i = 0; i < methods.length; i++) {
/*  92 */       if (methods[i].getName().equals(methodName)) {
/*  93 */         return methods[i];
/*     */       }
/*     */     } 
/*  96 */     throw new NoSuchMethodException();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object synchronizedAccess(final Object object) {
/* 101 */     InvocationHandler handler = new InvocationHandler()
/*     */       {
/*     */         public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 104 */           return method.invoke(object, args);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 109 */     return Proxy.newProxyInstance(object.getClass().getClassLoader(), (Class[])getAllInterfaces(object.getClass()).toArray((Object[])new Class[0]), handler);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object combine(Object[] objects) {
/* 114 */     if (objects == null || objects.length == 0)
/* 115 */       throw new IllegalArgumentException(); 
/* 116 */     if (objects.length == 1) {
/* 117 */       return objects[0];
/*     */     }
/*     */     
/* 120 */     Collection allInterfaces = new HashSet();
/* 121 */     final Map<Object, Object> objectToInterfaces = new LinkedHashMap<Object, Object>();
/* 122 */     for (int i = 0; i < objects.length; i++) {
/* 123 */       Collection tmp = getAllInterfaces(objects[i].getClass());
/* 124 */       objectToInterfaces.put(objects[i], tmp);
/* 125 */       allInterfaces.addAll(tmp);
/*     */     } 
/* 127 */     Class[] clazzes = (Class[])allInterfaces.toArray((Object[])new Class[allInterfaces.size()]);
/*     */     
/* 129 */     InvocationHandler handler = new InvocationHandler()
/*     */       {
/*     */         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 132 */           Object invokeOn = null;
/* 133 */           for (Iterator<Map.Entry> iter = objectToInterfaces.entrySet().iterator(); iter.hasNext() && invokeOn == null; ) {
/* 134 */             Map.Entry entry = iter.next();
/* 135 */             Collection interfaces = (Collection)entry.getValue();
/* 136 */             if (interfaces != null && interfaces.contains(method.getDeclaringClass())) {
/* 137 */               invokeOn = entry.getKey();
/*     */             }
/*     */           } 
/*     */           
/* 141 */           return method.invoke(invokeOn, args);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 146 */     return Proxy.newProxyInstance(ReflectionUtil.class.getClassLoader(), clazzes, handler);
/*     */   }
/*     */   
/*     */   public static interface CreationCallback
/*     */   {
/* 151 */     public static final CreationCallback STANDARD_CONSTRUCTOR = new CreationCallback()
/*     */       {
/*     */         public boolean useMethod(Method method) {
/* 154 */           return false;
/*     */         }
/*     */         
/*     */         public boolean useConstructor(Constructor constructor) {
/* 158 */           return ((constructor.getParameterTypes()).length == 0);
/*     */         }
/*     */         
/*     */         public Object[] getParameters() {
/* 162 */           return null;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     boolean useConstructor(Constructor param1Constructor);
/*     */ 
/*     */     
/*     */     boolean useMethod(Method param1Method);
/*     */     
/*     */     Object[] getParameters();
/*     */   }
/*     */   
/*     */   public static final class CreationCallbackFirstMatch
/*     */     implements CreationCallback
/*     */   {
/*     */     private Object[] params;
/*     */     private List classes;
/*     */     
/*     */     public CreationCallbackFirstMatch(Object[] params) {
/* 182 */       this.params = (params != null) ? params : new Object[0];
/* 183 */       this.classes = Arrays.asList((Class<?>[][])ReflectionUtil.getTypes(this.params));
/*     */     }
/*     */     
/*     */     public Object[] getParameters() {
/* 187 */       return this.params;
/*     */     }
/*     */     
/*     */     public boolean useConstructor(Constructor constructor) {
/* 191 */       return Util.equals(constructor.getParameterTypes(), this.classes);
/*     */     }
/*     */     
/*     */     public boolean useMethod(Method method) {
/* 195 */       return Util.collectionEquals(Arrays.asList(method.getParameterTypes()), this.classes);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object createInstance(String className, CreationCallback callback) throws Exception {
/* 202 */     Class<?> clazz = Class.forName(className);
/* 203 */     Constructor[] constructors = (Constructor[])clazz.getConstructors();
/* 204 */     for (int i = 0; i < constructors.length; i++) {
/* 205 */       if (callback.useConstructor(constructors[i])) {
/* 206 */         return constructors[i].newInstance(callback.getParameters());
/*     */       }
/*     */     } 
/* 209 */     Method[] methods = clazz.getMethods();
/* 210 */     for (int j = 0; j < methods.length; j++) {
/* 211 */       if (Modifier.isStatic(methods[j].getModifiers()) && callback.useMethod(methods[j])) {
/* 212 */         return methods[j].invoke(null, callback.getParameters());
/*     */       }
/*     */     } 
/* 215 */     throw new Exception("no suitable constructor/method found");
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object createBean(Class[] interfazes) {
/* 220 */     final Map<Object, Object> backingMap = new LinkedHashMap<Object, Object>();
/* 221 */     InvocationHandler ihandler = new InvocationHandler()
/*     */       {
/*     */         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 224 */           Object ret = null;
/* 225 */           if (method.getName().startsWith("get")) {
/* 226 */             String key = method.getName().substring(3);
/* 227 */             ret = backingMap.get(key);
/* 228 */           } else if (method.getName().startsWith("set") && args != null) {
/* 229 */             String key = method.getName().substring(3);
/* 230 */             backingMap.put(key, (args.length == 1) ? args[0] : args);
/* 231 */           } else if (method.getName().startsWith("is")) {
/* 232 */             String key = method.getName().substring(2);
/* 233 */             if (!(ret = backingMap.get(key) instanceof Boolean)) {
/* 234 */               throw new UnsupportedOperationException();
/*     */             }
/* 236 */           } else if (method.getName().equals("toMap")) {
/* 237 */             ret = backingMap;
/*     */           } else {
/* 239 */             throw new UnsupportedOperationException();
/*     */           } 
/* 241 */           return ret;
/*     */         }
/*     */       };
/*     */     
/* 245 */     return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfazes, ihandler);
/*     */   }
/*     */   
/*     */   public static Object createExplodingCallLogger(Object object, Logger log) {
/* 249 */     if (object != null) {
/* 250 */       Class[] interfazes = (Class[])getAllInterfaces(object.getClass()).toArray((Object[])new Class[0]);
/* 251 */       if (interfazes != null && interfazes.length > 0) {
/* 252 */         return Proxy.newProxyInstance(object.getClass().getClassLoader(), interfazes, new IHExplodingCallLogger(object, log));
/*     */       }
/* 254 */       throw new IllegalArgumentException("objects class does not implement any interface");
/*     */     } 
/*     */     
/* 257 */     throw new NullPointerException();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object createCallLogger(final Object object, final Logger log, final boolean recursive) {
/* 262 */     if (object != null) {
/* 263 */       Class[] interfazes = (Class[])getAllInterfaces(object.getClass()).toArray((Object[])new Class[0]);
/* 264 */       if (interfazes != null && interfazes.length > 0) {
/* 265 */         InvocationHandler handler = new InvocationHandler()
/*     */           {
/*     */             private String toString(Object obj) {
/* 268 */               return String.valueOf(obj);
/*     */             }
/*     */             
/*     */             public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 272 */               log.info("invoking method:" + toString(method) + " on: " + toString(object));
/* 273 */               if (args != null) {
/* 274 */                 for (int i = 0; i < args.length; i++) {
/* 275 */                   log.info("...param[" + i + "]: " + toString(args[i]));
/*     */                 }
/*     */               }
/*     */               try {
/* 279 */                 Object result = method.invoke(object, args);
/* 280 */                 log.info("...result: " + toString(result));
/* 281 */                 if (recursive && method.getReturnType().isInterface()) {
/* 282 */                   result = ReflectionUtil.createCallLogger(result, log, recursive);
/*     */                 }
/* 284 */                 return result;
/* 285 */               } catch (Throwable t) {
/* 286 */                 log.error("...thrown: " + t.getClass().getName() + ": " + t.getMessage(), t);
/* 287 */                 throw t;
/*     */               } 
/*     */             }
/*     */           };
/* 291 */         return Proxy.newProxyInstance(object.getClass().getClassLoader(), interfazes, handler);
/*     */       } 
/* 293 */       throw new IllegalArgumentException("objects class does not implement any interface");
/*     */     } 
/*     */     
/* 296 */     throw new NullPointerException();
/*     */   }
/*     */   
/*     */   public static interface ResultFlattening
/*     */   {
/* 301 */     public static final ResultFlattening RETURN_ARRAY = new ResultFlattening()
/*     */       {
/*     */         public Object flatten(Object[] results, Method m) {
/* 304 */           return results;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 309 */     public static final ResultFlattening RETURN_FIRST_NOT_NULL = new ResultFlattening()
/*     */       {
/*     */         public Object flatten(Object[] results, Method m) {
/* 312 */           Object ret = null;
/* 313 */           for (int i = 0; i < results.length && ret == null; i++) {
/* 314 */             ret = results[i];
/*     */           }
/* 316 */           return ret;
/*     */         }
/*     */       };
/*     */     
/*     */     Object flatten(Object[] param1ArrayOfObject, Method param1Method);
/*     */   }
/*     */   
/*     */   public static interface ContinuationRule
/*     */   {
/* 325 */     public static final ContinuationRule ALWAYS = new ContinuationRule()
/*     */       {
/*     */         public boolean callNext(Object previousResult) {
/* 328 */           return true;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 333 */     public static final ContinuationRule WHILE_NULL = new ContinuationRule()
/*     */       {
/*     */         public boolean callNext(Object previousResult) {
/* 336 */           return (previousResult == null);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 341 */     public static final ContinuationRule WHILE_FALSE = new ContinuationRule()
/*     */       {
/*     */         public boolean callNext(Object previousResult) {
/* 344 */           return Boolean.FALSE.equals(previousResult);
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     boolean callNext(Object param1Object);
/*     */   }
/*     */   
/*     */   public static Object createMultiplexingFacade(final Object[] backends, ContinuationRule continuationRule, final ResultFlattening resultFlattening) {
/* 353 */     if (backends == null || backends.length == 0)
/* 354 */       throw new IllegalArgumentException(); 
/* 355 */     if (backends.length == 1) {
/* 356 */       return backends[0];
/*     */     }
/*     */     
/* 359 */     Set commonInterfaces = new HashSet(getAllInterfaces(backends[0].getClass()));
/* 360 */     for (int i = 1; i < backends.length; i++) {
/* 361 */       CollectionUtil.intersect(commonInterfaces, getAllInterfaces(backends[i].getClass()));
/*     */     }
/*     */     
/* 364 */     InvocationHandler handler = new InvocationHandler()
/*     */       {
/*     */         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*     */           try {
/* 368 */             Object[] results = new Object[backends.length];
/* 369 */             for (int i = 0; i < backends.length; i++) {
/* 370 */               results[i] = method.invoke(backends[i], args);
/*     */             }
/* 372 */             if (!method.getReturnType().equals(Void.class)) {
/* 373 */               return resultFlattening.flatten(results, method);
/*     */             }
/* 375 */             return null;
/*     */           }
/* 377 */           catch (InvocationTargetException e) {
/* 378 */             throw e.getTargetException();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 383 */     return Proxy.newProxyInstance(ReflectionUtil.class.getClassLoader(), (Class[])commonInterfaces.toArray((Object[])new Class[commonInterfaces.size()]), handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Equality createEqualityDelegate(final Object object) {
/* 394 */     Field[] fields1 = object.getClass().getDeclaredFields();
/* 395 */     List<Field> fields2 = new LinkedList();
/* 396 */     for (int i = 0; i < fields1.length; i++) {
/* 397 */       if (!fields1[i].getType().equals(Equality.class) && (
/* 398 */         fields1[i].getModifiers() & 0x80) == 0) {
/* 399 */         if (!fields1[i].isAccessible()) {
/* 400 */           final Field f = fields1[i];
/* 401 */           AccessController.doPrivileged(new PrivilegedAction()
/*     */               {
/*     */                 public Object run() {
/* 404 */                   f.setAccessible(true);
/* 405 */                   return null;
/*     */                 }
/*     */               });
/*     */         } 
/*     */         
/* 410 */         fields2.add(fields1[i]);
/*     */       } 
/*     */     } 
/*     */     
/* 414 */     final Field[] fields = fields2.<Field>toArray(new Field[fields2.size()]);
/* 415 */     return new Equality()
/*     */       {
/*     */         public boolean equals(Object other) {
/*     */           boolean ret;
/* 419 */           if (object == other) {
/* 420 */             ret = true;
/* 421 */           } else if (object.getClass().equals(other.getClass())) {
/* 422 */             ret = true;
/* 423 */             for (int i = 0; i < fields.length && ret; i++) {
/*     */               try {
/* 425 */                 Object value1 = fields[i].get(object);
/* 426 */                 Object value2 = fields[i].get(other);
/* 427 */                 ret = Util.equals(value1, value2);
/* 428 */               } catch (IllegalAccessException e) {
/* 429 */                 throw new RuntimeException(e);
/*     */               } 
/*     */             } 
/*     */           } else {
/* 433 */             ret = false;
/*     */           } 
/* 435 */           return ret;
/*     */         }
/*     */         
/*     */         public int hashCode() {
/* 439 */           int hash = object.getClass().hashCode();
/* 440 */           for (int i = 0; i < fields.length; i++) {
/*     */             try {
/* 442 */               hash = HashCalc.addHashCode(hash, fields[i].get(object));
/* 443 */             } catch (IllegalAccessException e) {
/* 444 */               throw new RuntimeException(e);
/*     */             } 
/*     */           } 
/*     */           
/* 448 */           return hash;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static interface CachingProxyCallback {
/* 454 */     public static final CachingProxyCallback STD = new ReflectionUtil.CachingProxyCallbackAdapter()
/*     */       {
/*     */         public Object createKey(Method m, Object[] args) {
/* 457 */           List<Method> ret = new LinkedList(Arrays.asList(args));
/* 458 */           ret.add(m);
/* 459 */           return ret;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     boolean enableCaching(Method param1Method);
/*     */ 
/*     */     
/*     */     Object createKey(Method param1Method, Object[] param1ArrayOfObject);
/*     */ 
/*     */     
/*     */     Object toCachedValue(Object param1Object);
/*     */     
/*     */     Object fromCachedValue(Object param1Object);
/*     */   }
/*     */   
/*     */   public static abstract class CachingProxyCallbackAdapter
/*     */     implements CachingProxyCallback
/*     */   {
/*     */     public boolean enableCaching(Method m) {
/* 479 */       return true;
/*     */     }
/*     */     
/*     */     public abstract Object createKey(Method param1Method, Object[] param1ArrayOfObject);
/*     */     
/*     */     public Object toCachedValue(Object result) {
/* 485 */       return result;
/*     */     }
/*     */     
/*     */     public Object fromCachedValue(Object cachedValue) {
/* 489 */       return cachedValue;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object createCachingProxy(final Object object, final Cache cache, final CachingProxyCallback callback) {
/* 495 */     if (log.isDebugEnabled()) {
/* 496 */       log.debug("creating caching proxy for " + String.valueOf(object) + " ...");
/*     */     }
/* 498 */     Class[] interfaces = (Class[])getAllInterfaces(object.getClass()).toArray((Object[])new Class[0]);
/*     */     
/* 500 */     if (log.isDebugEnabled()) {
/* 501 */       log.debug("... proxied interfaces: " + Arrays.<Class<?>[]>asList((Class<?>[][])interfaces));
/*     */     }
/*     */     
/* 504 */     InvocationHandler handler = new InvocationHandler()
/*     */       {
/*     */         private Object _invoke(Method method, Object[] args) throws Throwable {
/*     */           try {
/* 508 */             return method.invoke(object, args);
/* 509 */           } catch (InvocationTargetException e) {
/* 510 */             throw e.getCause();
/*     */           } 
/*     */         }
/*     */         
/*     */         public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 515 */           Object ret = null;
/* 516 */           if (callback.enableCaching(method)) {
/* 517 */             Object key = callback.createKey(method, args);
/* 518 */             ret = cache.lookup(key);
/* 519 */             if (ret == null) {
/* 520 */               ret = _invoke(method, args);
/* 521 */               cache.store(key, callback.toCachedValue(ret));
/*     */             } else {
/* 523 */               ret = callback.fromCachedValue(ret);
/*     */             } 
/*     */           } else {
/* 526 */             ret = _invoke(method, args);
/*     */           } 
/* 528 */           return ret;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 533 */     return Proxy.newProxyInstance(ReflectionUtil.class.getClassLoader(), interfaces, handler);
/*     */   }
/*     */   
/*     */   public static Class getBaseType(Class clazz) {
/* 537 */     if (clazz == null)
/* 538 */       return null; 
/* 539 */     if (clazz.isArray()) {
/* 540 */       return getBaseType(clazz.getComponentType());
/*     */     }
/* 542 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void getAllReferencedClasses(Class c, Set<Class<?>> result) {
/* 548 */     Class<?> tmp = getBaseType(c.getSuperclass());
/* 549 */     if (tmp != null && !result.contains(tmp)) {
/* 550 */       result.add(tmp);
/* 551 */       getAllReferencedClasses(tmp, result);
/*     */     } 
/* 553 */     Class[] tmp2 = c.getClasses();
/* 554 */     for (int i = 0; i < tmp2.length; i++) {
/* 555 */       tmp = getBaseType(tmp2[i]);
/* 556 */       if (tmp != null && !result.contains(tmp)) {
/* 557 */         result.add(tmp);
/* 558 */         getAllReferencedClasses(tmp, result);
/*     */       } 
/*     */     } 
/*     */     
/* 562 */     Method[] methods = c.getMethods();
/* 563 */     for (int j = 0; j < methods.length; j++) {
/* 564 */       tmp = getBaseType(methods[j].getReturnType());
/* 565 */       if (tmp != null && !result.contains(tmp)) {
/* 566 */         result.add(tmp);
/* 567 */         getAllReferencedClasses(tmp, result);
/*     */       } 
/* 569 */       tmp2 = methods[j].getParameterTypes(); int k;
/* 570 */       for (k = 0; k < tmp2.length; k++) {
/* 571 */         tmp = getBaseType(tmp2[k]);
/* 572 */         if (tmp != null && !result.contains(tmp)) {
/* 573 */           result.add(tmp);
/* 574 */           getAllReferencedClasses(tmp, result);
/*     */         } 
/*     */       } 
/* 577 */       tmp2 = methods[j].getExceptionTypes();
/* 578 */       for (k = 0; k < tmp2.length; k++) {
/* 579 */         tmp = getBaseType(tmp2[k]);
/* 580 */         if (tmp != null && !result.contains(tmp)) {
/* 581 */           result.add(tmp);
/* 582 */           getAllReferencedClasses(tmp, result);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set getAllReferencedClasses(Class c, final String prefix) {
/* 590 */     Set ret = new HashSet();
/* 591 */     getAllReferencedClasses(c, ret);
/* 592 */     CollectionUtil.filter(ret, new Filter()
/*     */         {
/*     */           public boolean include(Object obj) {
/* 595 */             Class c = ReflectionUtil.getBaseType((Class)obj);
/* 596 */             return (c != null && c.getName().startsWith(prefix));
/*     */           }
/*     */         });
/* 599 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object createDummyImplementation(Class interfaze, final boolean recursive) {
/* 604 */     InvocationHandler handler = new InvocationHandler() {
/*     */         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 606 */           if (recursive && method.getReturnType().isInterface()) {
/* 607 */             return ReflectionUtil.createDummyImplementation(method.getReturnType(), recursive);
/*     */           }
/* 609 */           return null;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 614 */     return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { interfaze }, handler);
/*     */   }
/*     */   
/*     */   public static Object createProxy(Object object, InvocationHandler handler) {
/* 618 */     Class[] interfaces = (Class[])getAllInterfaces(object.getClass()).toArray((Object[])new Class[0]);
/* 619 */     return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, handler);
/*     */   }
/*     */   
/*     */   public static interface Equality {
/*     */     boolean equals(Object param1Object);
/*     */     
/*     */     int hashCode();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\reflect\ReflectionUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */