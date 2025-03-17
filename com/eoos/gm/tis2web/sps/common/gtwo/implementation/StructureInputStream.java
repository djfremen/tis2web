/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class StructureInputStream {
/*    */   protected byte[] buffer;
/*    */   protected int pos;
/*    */   
/*    */   public StructureInputStream(byte[] data) throws IOException {
/* 11 */     this.buffer = data;
/*    */   }
/*    */   
/*    */   public StructureInputStream(InputStream in) throws IOException {
/* 15 */     this(in, in.available());
/*    */   }
/*    */   
/*    */   public StructureInputStream(InputStream in, long size) throws IOException {
/* 19 */     this.buffer = new byte[(int)size];
/* 20 */     if (in.read(this.buffer) != size) {
/* 21 */       throw new IOException();
/*    */     }
/*    */   }
/*    */   
/*    */   public final byte readByte() throws IOException {
/* 26 */     return this.buffer[this.pos++];
/*    */   }
/*    */   
/*    */   public final char readChar() throws IOException {
/* 30 */     return (char)readByte();
/*    */   }
/*    */   
/*    */   public final String readString(int length) throws IOException {
/* 34 */     StringBuffer string = new StringBuffer(length);
/* 35 */     for (int i = 0; i < length; i++) {
/* 36 */       char c = readChar();
/* 37 */       if (c != '\000') {
/* 38 */         string.append(c);
/*    */       }
/*    */     } 
/* 41 */     return string.toString().trim();
/*    */   }
/*    */   
/*    */   public final short readShortLE() throws IOException {
/* 45 */     byte b0 = readByte();
/* 46 */     byte b1 = readByte();
/* 47 */     return (short)((b1 & 0xFF) << 8 | b0 & 0xFF);
/*    */   }
/*    */   
/*    */   public final int readIntLE() throws IOException {
/* 51 */     byte b0 = readByte();
/* 52 */     byte b1 = readByte();
/* 53 */     byte b2 = readByte();
/* 54 */     byte b3 = readByte();
/* 55 */     return b3 << 24 | (b2 & 0xFF) << 16 | (b1 & 0xFF) << 8 | b0 & 0xFF;
/*    */   }
/*    */   
/*    */   public final void close() {
/* 59 */     this.buffer = null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\StructureInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */