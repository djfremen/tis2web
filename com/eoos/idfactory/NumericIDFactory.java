/*    */ package com.eoos.idfactory;
/*    */ 
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ public final class NumericIDFactory
/*    */   implements IDFactory, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private BigInteger counter;
/*    */   
/*    */   private static final class SerializationProxy
/*    */     implements Externalizable {
/*    */     private static final long serialVersionUID = 1L;
/*    */     private BigInteger counter;
/*    */     
/*    */     private SerializationProxy(BigInteger counter) {
/* 23 */       this.counter = counter;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public SerializationProxy() {}
/*    */ 
/*    */     
/*    */     private Object readResolve() throws ObjectStreamException {
/* 32 */       return new NumericIDFactory(this.counter);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 36 */       int length = in.readInt();
/* 37 */       byte[] bytes = new byte[length];
/* 38 */       int count = in.read(bytes);
/* 39 */       if (count != length) {
/* 40 */         throw new IllegalStateException();
/*    */       }
/* 42 */       this.counter = new BigInteger(bytes);
/*    */     }
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException {
/* 46 */       byte[] bytes = this.counter.toByteArray();
/* 47 */       out.writeInt(bytes.length);
/* 48 */       out.write(this.counter.toByteArray());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NumericIDFactory() {
/* 56 */     this.counter = BigInteger.ZERO;
/*    */   }
/*    */   
/*    */   public NumericIDFactory(BigInteger counter) {
/* 60 */     this.counter = counter;
/*    */   }
/*    */   
/*    */   public Object createID() {
/* 64 */     Object retValue = this.counter;
/* 65 */     this.counter = this.counter.add(BigInteger.ONE);
/* 66 */     return retValue;
/*    */   }
/*    */   
/*    */   private Object writeReplace() throws ObjectStreamException {
/* 70 */     return new SerializationProxy(this.counter);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\idfactory\NumericIDFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */