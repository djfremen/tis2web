/*    */ package com.eoos.scsm.v2.io;
/*    */ 
/*    */ import java.io.FilterOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class OutputStreamDebugWrapper
/*    */   extends FilterOutputStream
/*    */ {
/*    */   private Logger log;
/*    */   
/*    */   public OutputStreamDebugWrapper(OutputStream out) {
/* 14 */     this(out, null);
/*    */   }
/*    */   
/*    */   public OutputStreamDebugWrapper(OutputStream out, Logger log) {
/* 18 */     super(out);
/* 19 */     this.log = (log != null) ? log : Logger.getLogger(OutputStreamDebugWrapper.class);
/*    */   }
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 23 */     this.out.write(b, off, len);
/* 24 */     this.log.debug("wrote " + len + " bytes");
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 29 */     super.write(b);
/* 30 */     this.log.debug("wrote 1 byte");
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 35 */     super.close();
/* 36 */     this.log.debug("stream closed");
/*    */   }
/*    */   
/*    */   public void flush() throws IOException {
/* 40 */     super.flush();
/* 41 */     this.log.debug("stream flushed");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\OutputStreamDebugWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */