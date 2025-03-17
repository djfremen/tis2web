/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.datatype.Identifiable;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.util.MultitonSupport;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class ConstantValueImpl
/*    */   implements Value, Identifiable, Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 14 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*    */         public Object createObject(Object identifier) {
/* 16 */           return new ConstantValueImpl((String)identifier);
/*    */         }
/*    */       });
/*    */   
/*    */   private String key;
/*    */   
/*    */   private ConstantValueImpl(String key) {
/* 23 */     this.key = key;
/*    */   }
/*    */   
/*    */   public static synchronized Value getInstance(String key) {
/* 27 */     return (Value)multitonSupport.getInstance(key);
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readReplace() throws ObjectStreamException {
/* 32 */     return getInstance(this.key);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 36 */     return this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object object) {
/* 41 */     if (object == null)
/* 42 */       return false; 
/* 43 */     if (object instanceof Identifiable && ((Identifiable)object).getIdentifier() == this.key) {
/* 44 */       return true;
/*    */     }
/* 46 */     return this.key.equals(object.toString());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     int ret = ConstantValueImpl.class.hashCode();
/* 53 */     ret = HashCalc.addHashCode(ret, this.key);
/* 54 */     return ret;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 58 */     return this.key;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ConstantValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */