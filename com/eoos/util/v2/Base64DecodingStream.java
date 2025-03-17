/*    */ package com.eoos.util.v2;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.Reader;
/*    */ 
/*    */ public class Base64DecodingStream
/*    */   extends InputStream
/*    */ {
/*    */   private Reader reader;
/* 11 */   private char[] readBuffer = new char[4];
/*    */   
/* 13 */   private byte[] outBuffer = new byte[3];
/* 14 */   private int position = 3;
/*    */   
/*    */   public Base64DecodingStream(Reader reader) {
/* 17 */     this.reader = reader;
/*    */   }
/*    */ 
/*    */   
/*    */   private void fillOutBuffer() throws IOException {
/* 22 */     int read = this.reader.read(this.readBuffer);
/* 23 */     if (read == -1)
/* 24 */     { this.outBuffer = null; }
/* 25 */     else { if (read != 4) {
/* 26 */         throw new IllegalStateException("missing input");
/*    */       }
/* 28 */       this.outBuffer = Base64EncodingUtil.decode(String.valueOf(this.readBuffer));
/* 29 */       this.position = 0; }
/*    */   
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 34 */     int retValue = 0;
/* 35 */     if (this.position == 3) {
/* 36 */       fillOutBuffer();
/*    */     }
/* 38 */     if (this.outBuffer == null) {
/* 39 */       retValue = -1;
/*    */     } else {
/* 41 */       retValue = this.outBuffer[this.position++] & 0xFF;
/*    */     } 
/* 43 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 48 */     this.reader.close();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\Base64DecodingStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */