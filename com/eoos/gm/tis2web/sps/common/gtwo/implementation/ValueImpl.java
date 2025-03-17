/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValueImpl
/*    */   implements Value, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 15 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 17 */           return new ValueImpl((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String key;
/*    */   
/*    */   private ValueImpl(String key) {
/* 24 */     this.key = key;
/*    */   }
/*    */   
/*    */   public static synchronized Value getInstance(String key) {
/* 28 */     return (Value)multitonSupport.getInstance(key);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 32 */     return getInstance(this.key);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 36 */     return this.key;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */