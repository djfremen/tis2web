/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.Writer;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VIT2Writer
/*    */   extends BufferedWriter
/*    */ {
/*    */   public VIT2Writer(Writer w) {
/* 18 */     super(w);
/*    */   }
/*    */   
/*    */   public void write(List<String> attrLines) throws Exception {
/* 22 */     for (int i = 0; i < attrLines.size(); i++) {
/* 23 */       String line = attrLines.get(i);
/* 24 */       write(line);
/* 25 */       newLine();
/*    */     } 
/* 27 */     write("Rem END");
/* 28 */     newLine();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\io\VIT2Writer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */