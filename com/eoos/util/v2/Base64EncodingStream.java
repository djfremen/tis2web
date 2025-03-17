/*    */ package com.eoos.util.v2;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Writer;
/*    */ 
/*    */ public class Base64EncodingStream
/*    */   extends OutputStream {
/*  9 */   private byte[] buffer = new byte[3];
/*    */   
/* 11 */   private int position = 0;
/*    */   
/*    */   private Writer writer;
/*    */ 
/*    */   
/*    */   public Base64EncodingStream(Writer writer) {
/* 17 */     this.writer = writer;
/*    */   }
/*    */   
/*    */   public void write(int b) throws IOException {
/* 21 */     this.buffer[this.position++] = (byte)(b & 0xFF);
/* 22 */     if (this.position == 3) {
/* 23 */       flushBuffer();
/*    */     }
/*    */   }
/*    */   
/*    */   private void flushBuffer() throws IOException {
/* 28 */     this.writer.write(Base64EncodingUtil.encode(this.buffer));
/* 29 */     this.position = 0;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 33 */     if (this.position != 0) {
/* 34 */       flushBuffer();
/*    */     }
/* 36 */     this.writer.close();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\Base64EncodingStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */