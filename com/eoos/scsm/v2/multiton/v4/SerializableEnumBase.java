/*    */ package com.eoos.scsm.v2.multiton.v4;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SerializableEnumBase
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private transient int index;
/*    */   
/*    */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 19 */     out.defaultWriteObject();
/*    */     
/* 21 */     int index = Util.indexOf(getInstanceArray(), this);
/* 22 */     if (index == -1) {
/* 23 */       throw new IllegalStateException("the instance is not in the instance array");
/*    */     }
/* 25 */     out.writeInt(index);
/*    */   }
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 30 */     in.defaultReadObject();
/* 31 */     this.index = in.readInt();
/*    */   }
/*    */   
/*    */   protected Object readResolve() throws ObjectStreamException {
/* 35 */     return getInstanceArray()[this.index];
/*    */   }
/*    */   
/*    */   protected abstract Object[] getInstanceArray();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\multiton\v4\SerializableEnumBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */