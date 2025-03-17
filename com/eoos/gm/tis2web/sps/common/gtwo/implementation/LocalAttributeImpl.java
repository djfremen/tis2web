/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.LocalAttribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class LocalAttributeImpl
/*    */   implements Attribute, Serializable, LocalAttribute {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 13 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 15 */           return new LocalAttributeImpl((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String key;
/*    */   
/*    */   private LocalAttributeImpl(String key) {
/* 22 */     this.key = key;
/*    */   }
/*    */   
/*    */   public static synchronized LocalAttributeImpl getInstance(String key) {
/* 26 */     return (LocalAttributeImpl)multitonSupport.getInstance(key);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 30 */     return getInstance(this.key);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 34 */     return super.toString() + "[key=" + this.key + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\LocalAttributeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */