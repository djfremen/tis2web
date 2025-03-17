/*     */ package com.eoos.gm.tis2web.sps.service.cai;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributeValueMapRI
/*     */   implements AttributeValueMapExt
/*     */ {
/*     */   private static final byte SET = 0;
/*     */   private static final byte REMOVE = 1;
/*     */   private static final byte SPECIAL = 2;
/*  21 */   private AttributeValueMapRI delegate = null;
/*     */   
/*  23 */   private byte mode = 2;
/*     */   
/*  25 */   private Attribute attribute = null;
/*     */   
/*  27 */   private Value value = null;
/*     */   
/*     */   public AttributeValueMapRI() {
/*  30 */     this(false);
/*     */   }
/*     */   
/*     */   private AttributeValueMapRI(boolean eoc) {
/*  34 */     if (!eoc) {
/*  35 */       this.delegate = new AttributeValueMapRI(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private AttributeValueMapRI(AttributeValueMapRI delegate, Attribute attribute, Value value, byte mode) {
/*  40 */     this.delegate = delegate;
/*  41 */     this.attribute = attribute;
/*  42 */     this.value = value;
/*  43 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public void exchange(Attribute attribute, Value value) {
/*  47 */     set(attribute, value);
/*     */   }
/*     */   
/*     */   private void _getAttributes(List<Attribute> list) {
/*  51 */     if (this.delegate != null) {
/*  52 */       this.delegate._getAttributes(list);
/*     */     }
/*  54 */     if (0 == this.mode && !list.contains(this.attribute)) {
/*  55 */       list.add(this.attribute);
/*  56 */     } else if (1 == this.mode) {
/*  57 */       list.remove(this.attribute);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection getAttributes() {
/*  62 */     List ret = new LinkedList();
/*  63 */     _getAttributes(ret);
/*  64 */     return ret;
/*     */   }
/*     */   
/*     */   public Value getValue(Attribute attribute) {
/*  68 */     Value ret = null;
/*  69 */     if (this.attribute != null && this.attribute.equals(attribute)) {
/*  70 */       if (this.mode == 0) {
/*  71 */         ret = this.value;
/*     */       }
/*  73 */     } else if (this.delegate != null) {
/*  74 */       ret = this.delegate.getValue(attribute);
/*     */     } 
/*  76 */     return ret;
/*     */   }
/*     */   
/*     */   public void remove(Attribute attribute) {
/*  80 */     if (this.mode != 2) {
/*  81 */       throw new UnsupportedOperationException();
/*     */     }
/*  83 */     this.delegate = new AttributeValueMapRI(this.delegate, attribute, null, (byte)1);
/*     */   }
/*     */   
/*     */   public int getChainDepth() {
/*  87 */     int ret = 0;
/*  88 */     if (this.delegate != null) {
/*  89 */       ret = this.delegate.getChainDepth();
/*     */     }
/*  91 */     if (this.mode != 2) {
/*  92 */       ret++;
/*     */     }
/*  94 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getSavePoint() {
/*  99 */     return this.delegate;
/*     */   }
/*     */   
/*     */   public void restoreSavePoint(Object savepoint) {
/* 103 */     this.delegate = (AttributeValueMapRI)savepoint;
/*     */   }
/*     */   
/*     */   public void set(Attribute attribute, Value value) {
/* 107 */     if (this.mode != 2) {
/* 108 */       throw new UnsupportedOperationException();
/*     */     }
/* 110 */     if (value == null) {
/* 111 */       remove(attribute);
/*     */     }
/* 113 */     else if (value != getValue(attribute)) {
/* 114 */       this.delegate = new AttributeValueMapRI(this.delegate, attribute, value, (byte)0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 120 */     if (this.mode == 0)
/* 121 */       return "SET: " + String.valueOf(this.attribute) + "=" + String.valueOf(this.value); 
/* 122 */     if (this.mode == 1) {
/* 123 */       return "REMOVE: " + String.valueOf(this.attribute);
/*     */     }
/* 125 */     return "SPECIAL: " + ((this.delegate == null) ? "EOC" : "FOC");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printSequence(OutputCallback callback) {
/* 134 */     if (this.delegate != null) {
/* 135 */       this.delegate.printSequence(callback);
/*     */     }
/* 137 */     callback.println(toString());
/*     */   }
/*     */   
/*     */   public static interface OutputCallback {
/*     */     void println(String param1String);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\service\cai\AttributeValueMapRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */