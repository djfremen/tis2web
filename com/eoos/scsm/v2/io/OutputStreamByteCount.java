/*    */ package com.eoos.scsm.v2.io;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.ICounter;
/*    */ import java.io.FilterOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class OutputStreamByteCount
/*    */   extends FilterOutputStream
/*    */ {
/*    */   private ICounter counter;
/*    */   
/*    */   public OutputStreamByteCount(OutputStream out, ICounter counter) {
/* 14 */     super(out);
/* 15 */     this.counter = counter;
/*    */   }
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 19 */     this.out.write(b, off, len);
/* 20 */     this.counter.inc(len);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 25 */     this.out.write(b);
/* 26 */     this.counter.inc();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\OutputStreamByteCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */