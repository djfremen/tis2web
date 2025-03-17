/*    */ package com.eoos.scsm.v2.io;
/*    */ 
/*    */ import com.eoos.log.Log4jUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.prefs.BackingStoreException;
/*    */ import java.util.prefs.Preferences;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PreferencesOutputStream
/*    */   extends OutputStream
/*    */ {
/* 15 */   private static final Logger log = Logger.getLogger(PreferencesOutputStream.class);
/*    */   
/* 17 */   private static final int MAX_LENGTH = (int)Math.floor(6144.0D);
/*    */   
/*    */   private Preferences node;
/*    */   
/* 21 */   private int segmentNumber = 0;
/*    */   
/* 23 */   private ByteBuffer buffer = ByteBuffer.wrap(new byte[MAX_LENGTH]);
/*    */   
/*    */   public PreferencesOutputStream(Preferences node) {
/* 26 */     this.node = node;
/*    */   }
/*    */ 
/*    */   
/*    */   private void writeNextSegment() {
/* 31 */     byte[] data = null;
/* 32 */     if (this.buffer.position() != 0) {
/* 33 */       if (this.buffer.remaining() == 0) {
/* 34 */         data = this.buffer.array();
/* 35 */         log.debug("writing full segment");
/*    */       } else {
/* 37 */         data = new byte[this.buffer.position()];
/* 38 */         System.arraycopy(this.buffer.array(), 0, data, 0, this.buffer.position());
/* 39 */         log.debug("writing truncated segment (size:" + data.length + ")");
/*    */       } 
/* 41 */       this.node.putByteArray("seg" + this.segmentNumber++, data);
/* 42 */       this.buffer.clear();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void write(int b) throws IOException {
/* 47 */     if (this.buffer.remaining() > 0) {
/* 48 */       this.buffer.put((byte)b);
/*    */     } else {
/* 50 */       writeNextSegment();
/* 51 */       write(b);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 56 */     if (this.buffer.remaining() >= len) {
/* 57 */       this.buffer.put(b, off, len);
/*    */     } else {
/* 59 */       int actual = this.buffer.remaining();
/* 60 */       this.buffer.put(b, off, actual);
/* 61 */       writeNextSegment();
/* 62 */       write(b, off + actual, len - actual);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void flushNode() {
/*    */     try {
/* 68 */       this.node.flush();
/* 69 */     } catch (BackingStoreException e) {
/* 70 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 76 */     writeNextSegment();
/* 77 */     this.buffer = null;
/* 78 */     flushNode();
/*    */   }
/*    */   
/*    */   public void flush() throws IOException {
/* 82 */     writeNextSegment();
/* 83 */     flushNode();
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Throwable {
/* 87 */     Log4jUtil.attachConsoleAppender();
/* 88 */     PreferencesOutputStream pos = new PreferencesOutputStream(Preferences.systemRoot().node("test"));
/* 89 */     OutputStreamWriter writer = new OutputStreamWriter(pos);
/* 90 */     for (int i = 0; i < 10000; i++) {
/* 91 */       writer.write("*" + i + "*");
/*    */     }
/* 93 */     writer.close();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\PreferencesOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */