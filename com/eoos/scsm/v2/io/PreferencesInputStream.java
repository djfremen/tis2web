/*    */ package com.eoos.scsm.v2.io;
/*    */ 
/*    */ import java.io.EOFException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.LineNumberReader;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.prefs.Preferences;
/*    */ 
/*    */ public class PreferencesInputStream
/*    */   extends InputStream
/*    */ {
/*    */   private Preferences node;
/* 15 */   private ByteBuffer buffer = null;
/*    */   
/* 17 */   private int segmentNumber = 0;
/*    */   
/*    */   public PreferencesInputStream(Preferences node) {
/* 20 */     this.node = node;
/*    */   }
/*    */ 
/*    */   
/*    */   private void readNextSegment() throws EOFException {
/* 25 */     byte[] ret = this.node.getByteArray("seg" + this.segmentNumber++, null);
/* 26 */     if (ret != null) {
/* 27 */       this.buffer = ByteBuffer.wrap(ret).asReadOnlyBuffer();
/*    */     } else {
/* 29 */       throw new EOFException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 34 */     if (this.buffer.remaining() > 0) {
/* 35 */       return this.buffer.get();
/*    */     }
/* 37 */     readNextSegment();
/* 38 */     return read();
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 43 */     if (this.buffer == null || this.buffer.remaining() == 0) {
/*    */       try {
/* 45 */         readNextSegment();
/* 46 */       } catch (EOFException e) {
/* 47 */         return -1;
/*    */       } 
/*    */     }
/* 50 */     if (len < this.buffer.remaining()) {
/* 51 */       this.buffer.get(b, off, len);
/* 52 */       return len;
/*    */     } 
/* 54 */     int actual = this.buffer.remaining();
/* 55 */     this.buffer.get(b, off, actual);
/* 56 */     return actual;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 61 */     this.buffer = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void main(String[] args) throws Throwable {
/* 66 */     PreferencesInputStream pis = new PreferencesInputStream(Preferences.systemRoot().node("test"));
/* 67 */     LineNumberReader lnr = new LineNumberReader(new InputStreamReader(pis));
/* 68 */     String line = null;
/* 69 */     while ((line = lnr.readLine()) != null) {
/* 70 */       System.out.println(line);
/*    */     }
/* 72 */     lnr.close();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\PreferencesInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */