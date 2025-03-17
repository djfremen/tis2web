/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class StructureInputStream
/*    */ {
/*    */   protected byte[] buffer;
/*    */   protected int pos;
/*    */   
/*    */   public StructureInputStream(byte[] data) throws IOException {
/* 12 */     this.buffer = data;
/*    */   }
/*    */   
/*    */   public StructureInputStream(InputStream in) throws IOException {
/* 16 */     this(in, in.available());
/*    */   }
/*    */   
/*    */   public StructureInputStream(InputStream in, long size) throws IOException {
/* 20 */     this.buffer = new byte[(int)size];
/* 21 */     if (in.read(this.buffer) != size) {
/* 22 */       throw new IOException();
/*    */     }
/*    */   }
/*    */   
/*    */   public final byte readByte() throws IOException {
/* 27 */     return this.buffer[this.pos++];
/*    */   }
/*    */   
/*    */   public final char readChar() throws IOException {
/* 31 */     return (char)readByte();
/*    */   }
/*    */   
/*    */   public final String readString(int length) throws IOException {
/* 35 */     StringBuffer string = new StringBuffer(length);
/* 36 */     for (int i = 0; i < length; i++) {
/* 37 */       char c = readChar();
/* 38 */       if (c != '\000') {
/* 39 */         string.append(c);
/*    */       }
/*    */     } 
/* 42 */     return string.toString().trim();
/*    */   }
/*    */   
/*    */   public final short readShortLE() throws IOException {
/* 46 */     byte b0 = readByte();
/* 47 */     byte b1 = readByte();
/* 48 */     return (short)((b1 & 0xFF) << 8 | b0 & 0xFF);
/*    */   }
/*    */   
/*    */   public final int readIntLE() throws IOException {
/* 52 */     byte b0 = readByte();
/* 53 */     byte b1 = readByte();
/* 54 */     byte b2 = readByte();
/* 55 */     byte b3 = readByte();
/* 56 */     return b3 << 24 | (b2 & 0xFF) << 16 | (b1 & 0xFF) << 8 | b0 & 0xFF;
/*    */   }
/*    */   
/*    */   public final void close() {
/* 60 */     this.buffer = null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\StructureInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */