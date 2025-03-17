/*    */ package com.eoos.scsm.v2.io;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class InputStreamDebugWrapper
/*    */   extends FilterInputStream
/*    */ {
/*    */   private Logger log;
/*    */   
/*    */   public InputStreamDebugWrapper(InputStream in, Logger log) {
/* 14 */     super(in);
/* 15 */     this.log = (log != null) ? log : Logger.getLogger(InputStreamDebugWrapper.class);
/*    */   }
/*    */   
/*    */   public InputStreamDebugWrapper(InputStream in) {
/* 19 */     this(in, null);
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 23 */     int ret = super.read();
/* 24 */     this.log.debug("read 1 byte");
/* 25 */     return ret;
/*    */   }
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 29 */     int ret = super.read(b, off, len);
/* 30 */     this.log.debug("read " + ret + " bytes");
/* 31 */     return ret;
/*    */   }
/*    */   
/*    */   public int available() throws IOException {
/* 35 */     int ret = super.available();
/* 36 */     this.log.debug(ret + " bytes available");
/* 37 */     return ret;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 41 */     super.close();
/* 42 */     this.log.debug("stream closed");
/*    */   }
/*    */   
/*    */   public synchronized void mark(int readlimit) {
/* 46 */     super.mark(readlimit);
/* 47 */     this.log.debug("marked with readlimit: " + readlimit);
/*    */   }
/*    */   
/*    */   public synchronized void reset() throws IOException {
/* 51 */     super.reset();
/* 52 */     this.log.debug("stream resetted");
/*    */   }
/*    */   
/*    */   public long skip(long n) throws IOException {
/* 56 */     long ret = super.skip(n);
/* 57 */     this.log.debug("skipped " + ret + " bytes");
/* 58 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\InputStreamDebugWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */