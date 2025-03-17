/*    */ package com.eoos.scsm.v2.io;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.ICounter;
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class InputStreamByteCount
/*    */   extends FilterInputStream
/*    */ {
/*    */   private ICounter counter;
/*    */   
/*    */   public InputStreamByteCount(InputStream in, ICounter counter) {
/* 14 */     super(in);
/* 15 */     this.counter = counter;
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 19 */     int ret = super.read();
/* 20 */     if (ret != -1) {
/* 21 */       this.counter.inc();
/*    */     }
/* 23 */     return ret;
/*    */   }
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 27 */     int ret = super.read(b, off, len);
/* 28 */     if (ret != -1) {
/* 29 */       this.counter.inc(ret);
/*    */     }
/* 31 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\InputStreamByteCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */