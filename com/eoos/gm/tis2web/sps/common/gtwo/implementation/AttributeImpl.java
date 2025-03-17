/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class AttributeImpl
/*    */   implements Attribute, Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 12 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 14 */           return new AttributeImpl((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String key;
/*    */   
/*    */   private AttributeImpl(String key) {
/* 21 */     this.key = key;
/*    */   }
/*    */   
/*    */   public static synchronized Attribute getInstance(String key) {
/* 25 */     return (Attribute)multitonSupport.getInstance(key);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 29 */     return getInstance(this.key);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 33 */     return this.key;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\AttributeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */