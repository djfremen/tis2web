/*    */ package com.eoos.scsm.v2.io;
/*    */ 
/*    */ import java.io.FilterOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class ExchangeableOutputStreamFacade
/*    */   extends FilterOutputStream {
/*    */   public ExchangeableOutputStreamFacade(OutputStream os) {
/* 10 */     super(os);
/*    */   }
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 14 */     this.out.write(b, off, len);
/*    */   }
/*    */   
/*    */   public OutputStream exchange(OutputStream os) {
/* 18 */     OutputStream ret = this.out;
/* 19 */     this.out = os;
/* 20 */     return ret;
/*    */   }
/*    */   
/*    */   public OutputStream getFacadedOutputStream() {
/* 24 */     return this.out;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\io\ExchangeableOutputStreamFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */