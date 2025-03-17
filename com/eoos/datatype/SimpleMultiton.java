/*    */ package com.eoos.datatype;
/*    */ 
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SimpleMultiton
/*    */   implements Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 11 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 13 */           return new SimpleMultiton((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String key;
/*    */   
/*    */   private SimpleMultiton(String key) {
/* 20 */     this.key = key;
/*    */   }
/*    */   
/*    */   public static synchronized SimpleMultiton getInstance(String key) {
/* 24 */     return (SimpleMultiton)multitonSupport.getInstance(key);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 28 */     return getInstance(this.key);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     return "SimpleMultiton@" + String.valueOf(this.key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\SimpleMultiton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */