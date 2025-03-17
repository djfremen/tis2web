/*    */ package com.eoos.scsm.v2.io;
/*    */ 
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class MultiplexingOutputStream
/*    */   extends OutputStream {
/*    */   private Collection outputStreams;
/*    */   
/*    */   public MultiplexingOutputStream(Collection outputStreams) {
/* 15 */     this.outputStreams = outputStreams;
/*    */   }
/*    */   
/*    */   public MultiplexingOutputStream(OutputStream... outputStreams) {
/* 19 */     this(Arrays.asList(outputStreams));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 24 */     for (Iterator<OutputStream> iter = this.outputStreams.iterator(); iter.hasNext();) {
/* 25 */       ((OutputStream)iter.next()).write(b);
/*    */     }
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 30 */     for (Iterator<OutputStream> iter = this.outputStreams.iterator(); iter.hasNext();) {
/* 31 */       ((OutputStream)iter.next()).close();
/*    */     }
/*    */   }
/*    */   
/*    */   public void flush() throws IOException {
/* 36 */     for (Iterator<OutputStream> iter = this.outputStreams.iterator(); iter.hasNext();) {
/* 37 */       ((OutputStream)iter.next()).flush();
/*    */     }
/*    */   }
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 42 */     for (Iterator<OutputStream> iter = this.outputStreams.iterator(); iter.hasNext();) {
/* 43 */       ((OutputStream)iter.next()).write(b, off, len);
/*    */     }
/*    */   }
/*    */   
/*    */   public void write(byte[] b) throws IOException {
/* 48 */     for (Iterator<OutputStream> iter = this.outputStreams.iterator(); iter.hasNext();) {
/* 49 */       ((OutputStream)iter.next()).write(b);
/*    */     }
/*    */   }
/*    */   
/*    */   public BufferedOutputStream createBufferedFacade(int bufferSize) {
/* 54 */     return new BufferedOutputStream(this, bufferSize);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\MultiplexingOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */