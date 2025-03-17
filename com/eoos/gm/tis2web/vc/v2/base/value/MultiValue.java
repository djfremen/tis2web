/*     */ package com.eoos.gm.tis2web.vc.v2.base.value;
/*     */ 
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.multiton.v4.IMultitonSupport;
/*     */ import com.eoos.scsm.v2.multiton.v4.WeakMultitonSupport;
/*     */ import com.eoos.scsm.v2.objectpool.ListPool;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class MultiValue
/*     */   implements Value
/*     */ {
/*     */   private static final class Type {
/*     */     private Type() {}
/*     */   }
/*     */   
/*  20 */   public static final Type INCLUSION = new Type();
/*     */   
/*  22 */   public static final Type EXCLUSION = new Type();
/*     */   
/*     */   private Type type;
/*     */   
/*     */   private Set values;
/*     */   
/*  28 */   private Integer hashCode = null;
/*     */   
/*  30 */   private String toString = null;
/*     */   
/*  32 */   private static final WeakMultitonSupport multitonSupport = new WeakMultitonSupport((IMultitonSupport.CreationCallback)new IMultitonSupport.CreationCallbackExt()
/*     */       {
/*     */         public Object createInstance(Object key) {
/*  35 */           List<MultiValue.Type> args = (List)key;
/*     */           
/*  37 */           return new MultiValue(args.get(0), (Set)args.get(1)) {
/*     */             
/*     */             };
/*     */         }
/*     */         
/*     */         public Object createStorageReplacement(Object key) {
/*  43 */           return new ArrayList((List)key);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private MultiValue(Type type, Set<?> values) {
/*  49 */     this.type = type;
/*  50 */     this.values = new HashSet(values);
/*     */   }
/*     */   
/*     */   public static MultiValue getInstance(Type type, Set values) {
/*  54 */     List<Type> key = ListPool.getThreadInstance().get();
/*     */     try {
/*  56 */       key.add(type);
/*  57 */       key.add(values);
/*  58 */       return (MultiValue)multitonSupport.getInstance(key, true);
/*     */     } finally {
/*  60 */       ListPool.getThreadInstance().free(key);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set getValues() {
/*  65 */     return this.values;
/*     */   }
/*     */   
/*     */   public boolean isType(Type type) {
/*  69 */     return (this.type == type);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  73 */     if (this.toString == null) {
/*  74 */       StringBuffer tmp = new StringBuffer(CollectionUtil.toString(this.values));
/*  75 */       if (this.type == EXCLUSION) {
/*  76 */         tmp.insert(0, "!");
/*     */       }
/*  78 */       this.toString = tmp.toString();
/*     */     } 
/*  80 */     return this.toString;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  84 */     if (this == obj)
/*  85 */       return true; 
/*  86 */     if (obj != null && hashCode() != obj.hashCode())
/*  87 */       return false; 
/*  88 */     if (obj instanceof MultiValue) {
/*  89 */       MultiValue mv = (MultiValue)obj;
/*  90 */       boolean ret = (this.type == mv.type);
/*  91 */       ret = (ret && this.values.equals(mv.values));
/*  92 */       return ret;
/*     */     } 
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  99 */     if (this.hashCode == null) {
/* 100 */       int ret = MultiValue.class.hashCode();
/* 101 */       ret = HashCalc.addHashCode(ret, this.type);
/* 102 */       ret = HashCalc.addHashCode(ret, this.values);
/* 103 */       this.hashCode = Integer.valueOf(ret);
/*     */     } 
/* 105 */     return this.hashCode.intValue();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\value\MultiValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */