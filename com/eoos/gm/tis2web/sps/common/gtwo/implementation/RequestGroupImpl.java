/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RequestGroupImpl
/*    */   implements RequestGroup, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 15 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 17 */           return new RequestGroupImpl(identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private Object key;
/*    */   
/*    */   private RequestGroupImpl(Object key) {
/* 24 */     this.key = key;
/*    */   }
/*    */   
/*    */   public static synchronized RequestGroup getInstance(Serializable key) {
/* 28 */     return (RequestGroup)multitonSupport.getInstance(key);
/*    */   }
/*    */   
/*    */   public static synchronized RequestGroup createInstance() {
/* 32 */     Long identifier = null;
/*    */     do {
/* 34 */       identifier = Long.valueOf(System.currentTimeMillis());
/* 35 */     } while (multitonSupport.existsInstance(identifier));
/*    */     
/* 37 */     return getInstance(identifier);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 41 */     return getInstance((Serializable)this.key);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     return super.toString() + "[key=" + this.key + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\RequestGroupImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */