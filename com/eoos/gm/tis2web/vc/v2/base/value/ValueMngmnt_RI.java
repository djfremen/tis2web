/*     */ package com.eoos.gm.tis2web.vc.v2.base.value;
/*     */ 
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.objectpool.SetPool;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ValueMngmnt_RI
/*     */   implements ValueManagement
/*     */ {
/*  15 */   private static final Logger log = Logger.getLogger(ValueMngmnt_RI.class);
/*     */   
/*  17 */   private static final Value ANY = new Value()
/*     */     {
/*     */       public String toString() {
/*  20 */         return "ANY";
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Value toValue(Object object) {
/*  29 */     if (object == null || object instanceof Value) {
/*  30 */       return (Value)object;
/*     */     }
/*  32 */     return createInclusionValue(Collections.singleton(object));
/*     */   }
/*     */ 
/*     */   
/*     */   public Value createInclusionValue(Set values) {
/*  37 */     return MultiValue.getInstance(MultiValue.INCLUSION, values);
/*     */   }
/*     */   
/*     */   public Value createExclusionValue(Set values) {
/*  41 */     return MultiValue.getInstance(MultiValue.EXCLUSION, values);
/*     */   }
/*     */   
/*     */   public Value getANY() {
/*  45 */     return ANY;
/*     */   }
/*     */   
/*     */   public boolean isANY(Value value) {
/*  49 */     return (value == ANY);
/*     */   }
/*     */   
/*     */   private boolean isExclusionValue(Value value) {
/*  53 */     return (value instanceof MultiValue && ((MultiValue)value).isType(MultiValue.EXCLUSION));
/*     */   }
/*     */   
/*     */   private boolean isInclusionValue(Value value) {
/*  57 */     return (value instanceof MultiValue && ((MultiValue)value).isType(MultiValue.INCLUSION));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set extractValues(Value value) {
/*  68 */     if (value instanceof MultiValue) {
/*  69 */       return ((MultiValue)value).getValues();
/*     */     }
/*  71 */     throw new IllegalArgumentException("value: " + String.valueOf(value) + " is not a multi value");
/*     */   }
/*     */ 
/*     */   
/*     */   public Set createDisjunctiveValues(Set values, Set<Value> result) {
/*  76 */     if (log.isDebugEnabled()) {
/*  77 */       log.debug("creating disjunctive values for :" + values);
/*     */     }
/*  79 */     boolean containsANY = false;
/*     */     
/*  81 */     SetPool poolSet = SetPool.getThreadInstance();
/*  82 */     Set includedValues = poolSet.get();
/*     */     try {
/*  84 */       Set<?> excludedValues = poolSet.get();
/*     */       try {
/*  86 */         for (Iterator<Value> iterator = values.iterator(); iterator.hasNext(); ) {
/*  87 */           Value value = iterator.next();
/*  88 */           if (isInclusionValue(value)) {
/*  89 */             includedValues.addAll(extractValues(value)); continue;
/*  90 */           }  if (isExclusionValue(value)) {
/*  91 */             excludedValues.addAll(extractValues(value)); continue;
/*  92 */           }  if (!containsANY && isANY(value)) {
/*  93 */             containsANY = true;
/*     */           }
/*     */         } 
/*     */         
/*  97 */         if (containsANY || (excludedValues.size() > 0 && includedValues.size() > 0)) {
/*     */           
/*  99 */           excludedValues.addAll(includedValues);
/* 100 */           includedValues.addAll(excludedValues);
/*     */         } 
/*     */         
/* 103 */         for (Iterator iter = includedValues.iterator(); iter.hasNext();) {
/* 104 */           result.add(createInclusionValue(Collections.singleton(iter.next())));
/*     */         }
/* 106 */         if (excludedValues.size() > 0) {
/* 107 */           result.add(createExclusionValue(new HashSet(excludedValues)));
/*     */         }
/*     */       } finally {
/* 110 */         poolSet.free(excludedValues);
/*     */       } 
/*     */     } finally {
/* 113 */       poolSet.free(includedValues);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (result.size() == 0 && containsANY) {
/* 119 */       result.add(getANY());
/*     */     }
/* 121 */     if (log.isDebugEnabled()) {
/* 122 */       log.debug("...result: " + result);
/*     */     }
/*     */     
/* 125 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Value value1, Value value2) {
/* 130 */     if (value1 == null) {
/* 131 */       return (value2 == null);
/*     */     }
/* 133 */     return value1.equals(value2);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode(Value value) {
/* 138 */     if (value == null) {
/* 139 */       return 0;
/*     */     }
/* 141 */     return value.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public Value intersect(Value value1, Value value2) {
/* 146 */     if (log.isDebugEnabled()) {
/* 147 */       log.debug("intersecting " + String.valueOf(value1) + " with " + String.valueOf(value2));
/*     */     }
/* 149 */     Value ret = null;
/*     */     
/* 151 */     if (value1 == value2) {
/* 152 */       ret = value1;
/* 153 */     } else if (value1 == null || value2 == null) {
/* 154 */       ret = null;
/* 155 */     } else if (isANY(value1)) {
/* 156 */       ret = value2;
/* 157 */     } else if (isANY(value2)) {
/* 158 */       ret = value1;
/*     */     } else {
/* 160 */       boolean isExclusionWrapperV1 = isExclusionValue(value1);
/* 161 */       boolean isExclusionWrapperV2 = isExclusionValue(value2);
/*     */       
/* 163 */       SetPool poolSet = SetPool.getThreadInstance();
/* 164 */       Set<?> excludedValues = poolSet.get();
/*     */       try {
/* 166 */         Set includedValues = poolSet.get();
/*     */         try {
/* 168 */           if (isExclusionWrapperV1 && isExclusionWrapperV2) {
/* 169 */             excludedValues.addAll(extractValues(value1));
/* 170 */             excludedValues.addAll(extractValues(value2));
/* 171 */             ret = createExclusionValue(new HashSet(excludedValues));
/* 172 */           } else if (isExclusionWrapperV1 || isExclusionWrapperV2) {
/* 173 */             if (isExclusionWrapperV1) {
/* 174 */               excludedValues = extractValues(value1);
/* 175 */               includedValues = extractValues(value2);
/*     */             } else {
/* 177 */               excludedValues = extractValues(value2);
/* 178 */               includedValues = extractValues(value1);
/*     */             } 
/* 180 */             Set<?> remainingValues = poolSet.get();
/*     */             try {
/* 182 */               remainingValues.addAll(includedValues);
/* 183 */               CollectionUtil.substract(remainingValues, excludedValues);
/*     */               
/* 185 */               if (remainingValues.size() == 0) {
/* 186 */                 ret = null;
/*     */               } else {
/* 188 */                 ret = createInclusionValue(new HashSet(remainingValues));
/*     */               } 
/*     */             } finally {
/* 191 */               poolSet.free(remainingValues);
/*     */             } 
/*     */           } else {
/* 194 */             Set values1 = extractValues(value1);
/* 195 */             Set values2 = extractValues(value2);
/* 196 */             Set<?> intersection = poolSet.get();
/*     */             try {
/* 198 */               intersection.addAll(values1);
/* 199 */               CollectionUtil.intersect(intersection, values2);
/*     */               
/* 201 */               if (intersection.size() == 0) {
/* 202 */                 ret = null;
/*     */               } else {
/* 204 */                 ret = createInclusionValue(new HashSet(intersection));
/*     */               } 
/*     */             } finally {
/* 207 */               poolSet.free(intersection);
/*     */             } 
/*     */           } 
/*     */         } finally {
/* 211 */           poolSet.free(includedValues);
/*     */         } 
/*     */       } finally {
/* 214 */         poolSet.free(excludedValues);
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     if (log.isDebugEnabled()) {
/* 219 */       log.debug("...intersection result: " + String.valueOf(ret));
/*     */     }
/* 221 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public Value union(Collection values) {
/* 226 */     if (log.isDebugEnabled()) {
/* 227 */       log.debug("combining (union) values: " + values);
/*     */     }
/* 229 */     Value ret = null;
/*     */     
/* 231 */     SetPool poolSet = SetPool.getThreadInstance();
/*     */     
/* 233 */     Set<?> excludedValues = poolSet.get();
/*     */     try {
/* 235 */       Set<?> includedValues = poolSet.get();
/*     */       
/*     */       try {
/* 238 */         for (Iterator<Value> iter = values.iterator(); iter.hasNext() && ret == null; ) {
/* 239 */           Value currentValue = iter.next();
/* 240 */           if (isANY(currentValue)) {
/* 241 */             ret = getANY(); continue;
/* 242 */           }  if (isExclusionValue(currentValue)) {
/* 243 */             excludedValues.addAll(extractValues(currentValue)); continue;
/* 244 */           }  if (isInclusionValue(currentValue)) {
/* 245 */             includedValues.addAll(extractValues(currentValue)); continue;
/*     */           } 
/* 247 */           throw new UnsupportedValueException();
/*     */         } 
/*     */ 
/*     */         
/* 251 */         if (ret == null) {
/* 252 */           if (excludedValues.size() > 0) {
/* 253 */             CollectionUtil.substract(excludedValues, includedValues);
/* 254 */             if (excludedValues.size() == 0) {
/* 255 */               ret = getANY();
/*     */             } else {
/* 257 */               ret = createExclusionValue(new HashSet(excludedValues));
/*     */             } 
/* 259 */           } else if (includedValues.size() > 0) {
/* 260 */             ret = createInclusionValue(new HashSet(includedValues));
/*     */           } 
/*     */         }
/*     */       } finally {
/* 264 */         poolSet.free(includedValues);
/*     */       } 
/*     */     } finally {
/* 267 */       poolSet.free(excludedValues);
/*     */     } 
/* 269 */     if (log.isDebugEnabled()) {
/* 270 */       log.debug("...resulting value: " + ret);
/*     */     }
/* 272 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set resolve(Value value, ValueManagement.ResolveCallback callback) throws UnresolvableException {
/* 277 */     if (value == null)
/* 278 */       return Collections.EMPTY_SET; 
/* 279 */     if (isInclusionValue(value))
/* 280 */       return extractValues(value); 
/* 281 */     if (isExclusionValue(value) || isANY(value)) {
/*     */       Set<?> domain;
/* 283 */       if (callback != null && (domain = callback.getDomain()) != null) {
/* 284 */         domain = new HashSet(domain);
/* 285 */         if (isANY(value)) {
/* 286 */           return domain;
/*     */         }
/* 288 */         domain.removeAll(extractValues(value));
/* 289 */         return domain;
/*     */       } 
/*     */       
/* 292 */       throw new UnresolvableException();
/*     */     } 
/*     */     
/* 295 */     return Collections.singleton(value);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\value\ValueMngmnt_RI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */