/*    */ package com.eoos.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InputStreamCountWrapper
/*    */   extends InputStream
/*    */ {
/*    */   private InputStream is;
/* 14 */   private Counter counter = new Counter();
/*    */ 
/*    */   
/*    */   public InputStreamCountWrapper(InputStream is) {
/* 18 */     this.is = is;
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 22 */     int retValue = this.is.read();
/* 23 */     if (retValue != -1) {
/* 24 */       this.counter.inc();
/*    */     }
/* 26 */     return retValue;
/*    */   }
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 30 */     int ret = this.is.read(b, off, len);
/* 31 */     if (ret != -1) {
/* 32 */       this.counter.inc(ret);
/*    */     }
/* 34 */     return ret;
/*    */   }
/*    */   
/*    */   public BigInteger getCount() {
/* 38 */     return this.counter.getValue();
/*    */   }
/*    */   
/*    */   public int available() throws IOException {
/* 42 */     return this.is.available();
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 46 */     this.is.close();
/*    */   }
/*    */   
/*    */   public synchronized void mark(int readlimit) {
/* 50 */     this.is.mark(readlimit);
/*    */   }
/*    */   
/*    */   public boolean markSupported() {
/* 54 */     return this.is.markSupported();
/*    */   }
/*    */   
/*    */   public synchronized void reset() throws IOException {
/* 58 */     this.is.reset();
/*    */   }
/*    */   
/*    */   public long skip(long n) throws IOException {
/* 62 */     return super.skip(n);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\io\InputStreamCountWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */