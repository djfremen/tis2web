/*     */ package com.eoos.util;
/*     */ 
/*     */ import com.eoos.condition.Condition;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssertUtil
/*     */ {
/*     */   public static class AssertException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public AssertException() {}
/*     */     
/*     */     public AssertException(String s) {
/*  25 */       super(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Equality implements Condition, FailExplanation {
/*     */     private Object object;
/*     */     
/*     */     public Equality(Object object) {
/*  33 */       this.object = object;
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/*  37 */       return (this.object == null) ? ((obj == null)) : this.object.equals(obj);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  41 */       return "condition: equal to " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/*  45 */       return String.valueOf(testedObject) + " is not equal to " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class UnEquality
/*     */     implements Condition, FailExplanation {
/*     */     private Object object;
/*     */     
/*     */     public UnEquality(Object object) {
/*  54 */       this.object = object;
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/*  58 */       return (this.object == null) ? ((obj != null)) : (!this.object.equals(obj));
/*     */     }
/*     */     
/*     */     public String toString() {
/*  62 */       return "condition: unequal to " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/*  66 */       return String.valueOf(testedObject) + " is equal to " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Lower implements Condition, FailExplanation {
/*     */     private Comparable object;
/*     */     
/*     */     public Lower(Comparable object) {
/*  74 */       this.object = object;
/*     */     }
/*     */     
/*     */     public Lower(long object) {
/*  78 */       this(Long.valueOf(object));
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/*  82 */       return (this.object.compareTo(obj) > 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  86 */       return "condition: lower than " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/*  90 */       return String.valueOf(testedObject) + " is not lower than " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Greater
/*     */     implements Condition, FailExplanation {
/*     */     private Comparable object;
/*     */     
/*     */     public Greater(Comparable object) {
/*  99 */       this.object = object;
/*     */     }
/*     */     
/*     */     public Greater(long object) {
/* 103 */       this(Long.valueOf(object));
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/* 107 */       return (this.object.compareTo(obj) < 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 111 */       return "condition: greater than " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/* 115 */       return String.valueOf(testedObject) + " is not greater than " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Equal
/*     */     implements Condition, FailExplanation {
/*     */     private Comparable object;
/*     */     
/*     */     public Equal(Comparable object) {
/* 124 */       this.object = object;
/*     */     }
/*     */     
/*     */     public Equal(long object) {
/* 128 */       this(Long.valueOf(object));
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/* 132 */       return (this.object.compareTo(obj) == 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 136 */       return "condition: equal(comparable) to " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/* 140 */       return String.valueOf(testedObject) + " is not equal to " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class OfType
/*     */     implements Condition, FailExplanation
/*     */   {
/*     */     private Class clazz;
/*     */     
/*     */     public OfType(Class clazz) {
/* 150 */       this.clazz = clazz;
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/* 154 */       return this.clazz.isAssignableFrom(obj.getClass());
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/* 158 */       return String.valueOf(testedObject) + " is not of type " + this.clazz.getName();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ElementOfType
/*     */     implements Condition, FailExplanation {
/*     */     private Class clazz;
/*     */     
/*     */     public ElementOfType(Class clazz) {
/* 167 */       this.clazz = clazz;
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/* 171 */       boolean retValue = true;
/*     */       try {
/* 173 */         for (Iterator iter = ((Collection)obj).iterator(); iter.hasNext() && retValue; ) {
/* 174 */           Object element = iter.next();
/* 175 */           retValue = (retValue && element.getClass().isAssignableFrom(this.clazz));
/*     */         } 
/* 177 */       } catch (Exception e) {
/* 178 */         retValue = false;
/*     */       } 
/*     */       
/* 181 */       return retValue;
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/* 185 */       return String.valueOf(testedObject) + " contains element(s) which is/are not of type " + this.clazz.getName();
/*     */     }
/*     */   }
/*     */   
/* 189 */   public static final Condition NOT_NULL = new UnEquality(null) {
/*     */       public String getExplanation(Object testedObject) {
/* 191 */         return "the tested object was null";
/*     */       }
/*     */     };
/*     */   
/* 195 */   public static final Condition NULL = new Equality(null) {
/*     */       public String getExplanation(Object testedObject) {
/* 197 */         return "tested object is not null";
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensure(Object object, Condition condition) {
/* 207 */     if (!condition.check(object)) {
/* 208 */       StringBuffer message = new StringBuffer("assertion failed - ");
/* 209 */       if (condition instanceof FailExplanation) {
/* 210 */         message.append(((FailExplanation)condition).getExplanation(object));
/*     */       } else {
/* 212 */         message.append("object: " + String.valueOf(object) + ", condition:" + String.valueOf(condition));
/*     */       } 
/* 214 */       throw new AssertException(message.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void ensure(long number, Condition condition) {
/* 219 */     ensure(Long.valueOf(number), condition);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 223 */     ensure((Object)null, NOT_NULL);
/* 224 */     System.out.println("ok");
/*     */   }
/*     */   
/*     */   public static interface FailExplanation {
/*     */     String getExplanation(Object param1Object);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\AssertUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */