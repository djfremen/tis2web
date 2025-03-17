/*     */ package com.eoos.util.v2;
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
/*  29 */       super(s);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Equality implements Condition, FailExplanation {
/*     */     private Object object;
/*     */     
/*     */     public Equality(Object object) {
/*  37 */       this.object = object;
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/*  41 */       return (this.object == null) ? ((obj == null)) : this.object.equals(obj);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  45 */       return "condition: equal to " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/*  49 */       return String.valueOf(testedObject) + " is not equal to " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class UnEquality
/*     */     implements Condition, FailExplanation {
/*     */     private Object object;
/*     */     
/*     */     public UnEquality(Object object) {
/*  58 */       this.object = object;
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/*  62 */       return (this.object == null) ? ((obj != null)) : (!this.object.equals(obj));
/*     */     }
/*     */     
/*     */     public String toString() {
/*  66 */       return "condition: unequal to " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/*  70 */       return String.valueOf(testedObject) + " is equal to " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Lower implements Condition, FailExplanation {
/*     */     private Comparable object;
/*     */     
/*     */     public Lower(Comparable object) {
/*  78 */       this.object = object;
/*     */     }
/*     */     
/*     */     public Lower(long object) {
/*  82 */       this(Long.valueOf(object));
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/*  86 */       return (this.object.compareTo(obj) > 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  90 */       return "condition: lower than " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/*  94 */       return String.valueOf(testedObject) + " is not lower than " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Greater
/*     */     implements Condition, FailExplanation {
/*     */     private Comparable object;
/*     */     
/*     */     public Greater(Comparable object) {
/* 103 */       this.object = object;
/*     */     }
/*     */     
/*     */     public Greater(long object) {
/* 107 */       this(Long.valueOf(object));
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/* 111 */       return (this.object.compareTo(obj) < 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 115 */       return "condition: greater than " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/* 119 */       return String.valueOf(testedObject) + " is not greater than " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Equal
/*     */     implements Condition, FailExplanation {
/*     */     private Comparable object;
/*     */     
/*     */     public Equal(Comparable object) {
/* 128 */       this.object = object;
/*     */     }
/*     */     
/*     */     public Equal(long object) {
/* 132 */       this(Long.valueOf(object));
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/* 136 */       return (this.object.compareTo(obj) == 0);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 140 */       return "condition: equal(comparable) to " + String.valueOf(this.object);
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/* 144 */       return String.valueOf(testedObject) + " is not equal to " + String.valueOf(this.object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class OfType
/*     */     implements Condition, FailExplanation
/*     */   {
/*     */     private Class clazz;
/*     */     
/*     */     public OfType(Class clazz) {
/* 154 */       this.clazz = clazz;
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/* 158 */       return this.clazz.isAssignableFrom(obj.getClass());
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/* 162 */       return String.valueOf(testedObject) + " is not of type " + this.clazz.getName();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ElementOfType
/*     */     implements Condition, FailExplanation {
/*     */     private Class clazz;
/*     */     
/*     */     public ElementOfType(Class clazz) {
/* 171 */       this.clazz = clazz;
/*     */     }
/*     */     
/*     */     public boolean check(Object obj) {
/* 175 */       boolean retValue = true;
/*     */       try {
/* 177 */         for (Iterator iter = ((Collection)obj).iterator(); iter.hasNext() && retValue; ) {
/* 178 */           Object element = iter.next();
/* 179 */           retValue = (retValue && element.getClass().isAssignableFrom(this.clazz));
/*     */         } 
/* 181 */       } catch (Exception e) {
/* 182 */         retValue = false;
/*     */       } 
/*     */       
/* 185 */       return retValue;
/*     */     }
/*     */     
/*     */     public String getExplanation(Object testedObject) {
/* 189 */       return String.valueOf(testedObject) + " contains element(s) which is/are not of type " + this.clazz.getName();
/*     */     }
/*     */   }
/*     */   
/* 193 */   public static final Condition NOT_NULL = new UnEquality(null) {
/*     */       public String getExplanation(Object testedObject) {
/* 195 */         return "the tested object was null";
/*     */       }
/*     */     };
/*     */   
/* 199 */   public static final Condition NULL = new Equality(null) {
/*     */       public String getExplanation(Object testedObject) {
/* 201 */         return "the tested object was not null";
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensure(Object object, Condition condition) {
/* 210 */     if (!condition.check(object)) {
/* 211 */       StringBuffer message = new StringBuffer("assertion failed - ");
/* 212 */       if (condition instanceof FailExplanation) {
/* 213 */         message.append(((FailExplanation)condition).getExplanation(object));
/*     */       } else {
/* 215 */         message.append("object: " + String.valueOf(object) + ", condition:" + String.valueOf(condition));
/*     */       } 
/* 217 */       throw new AssertException(message.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void ensure(long number, Condition condition) {
/* 222 */     ensure(Long.valueOf(number), condition);
/*     */   }
/*     */   
/*     */   public static void ensure(int number, Condition condition) {
/* 226 */     ensure(Integer.valueOf(number), condition);
/*     */   }
/*     */   
/*     */   public static void ensure(Condition condition) {
/* 230 */     ensure((Object)null, condition);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 234 */     ensure((Object)null, NOT_NULL);
/* 235 */     System.out.println("ok");
/*     */   }
/*     */   
/*     */   public static interface ExtCondition extends Condition, FailExplanation {}
/*     */   
/*     */   public static interface FailExplanation {
/*     */     String getExplanation(Object param1Object);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\AssertUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */