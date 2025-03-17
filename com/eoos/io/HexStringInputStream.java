/*    */ package com.eoos.io;
/*    */ 
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HexStringInputStream
/*    */   extends InputStream
/*    */ {
/*    */   protected InputStream is;
/* 18 */   protected byte[] buffer = new byte[2];
/*    */ 
/*    */   
/*    */   public HexStringInputStream(InputStream is) {
/* 22 */     this.is = is;
/*    */   }
/*    */   
/*    */   public int read() throws IOException {
/* 26 */     if (this.is.read(this.buffer) != -1) {
/* 27 */       String tmp = new String(this.buffer, "UTF-8");
/* 28 */       return StringUtilities.hexToBytes(tmp)[0];
/*    */     } 
/* 30 */     return -1;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\io\HexStringInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */