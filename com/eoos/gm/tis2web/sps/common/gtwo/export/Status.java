/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export;
/*    */ 
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Status
/*    */   implements Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 11 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 13 */           return new Status((String)identifier);
/*    */         }
/*    */       });
/*    */   
/* 17 */   public static final Status OK = getInstance("ok");
/*    */   
/* 19 */   public static final Status EXCEPTION = getInstance("exception");
/*    */   
/*    */   private String code;
/*    */   
/*    */   private Status(String code) {
/* 24 */     this.code = code;
/*    */   }
/*    */   
/*    */   public static synchronized Status getInstance(String code) {
/* 28 */     return (Status)multitonSupport.getInstance(code);
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 32 */     return getInstance(this.code);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\Status.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */