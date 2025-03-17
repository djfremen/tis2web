/*    */ package com.eoos.io;
/*    */ 
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HexStringOutputStream
/*    */   extends OutputStream
/*    */ {
/*    */   protected OutputStream out;
/*    */   
/*    */   public HexStringOutputStream(OutputStream out) {
/* 20 */     this.out = out;
/*    */   }
/*    */   
/*    */   public void write(int b) throws IOException {
/* 24 */     String tmp = StringUtilities.byteToHex((byte)b);
/* 25 */     this.out.write(tmp.getBytes("UTF-8"));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\io\HexStringOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */