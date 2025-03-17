/*    */ package com.eoos.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OutputStreamCountWrapper
/*    */   extends OutputStream
/*    */ {
/*    */   private OutputStream os;
/* 13 */   private Counter counter = new Counter();
/*    */ 
/*    */   
/*    */   public OutputStreamCountWrapper(OutputStream os) {
/* 17 */     this.os = os;
/*    */   }
/*    */   
/*    */   public BigInteger getCount() {
/* 21 */     return this.counter.getValue();
/*    */   }
/*    */   
/*    */   public void write(int b) throws IOException {
/* 25 */     this.os.write(b);
/* 26 */     this.counter.inc();
/*    */   }
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 30 */     if (len == 0)
/*    */       return; 
/* 32 */     this.os.write(b, off, len);
/* 33 */     this.counter.inc(len);
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 37 */     this.os.close();
/*    */   }
/*    */   
/*    */   public void flush() throws IOException {
/* 41 */     this.os.flush();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\io\OutputStreamCountWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */