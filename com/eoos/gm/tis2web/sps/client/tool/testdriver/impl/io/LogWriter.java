/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.Writer;
/*    */ 
/*    */ 
/*    */ public class LogWriter
/*    */   extends BufferedWriter
/*    */ {
/*    */   public LogWriter(Writer w) {
/* 11 */     super(w);
/*    */   }
/*    */   
/*    */   public void writeAttr(String attr, String val) throws Exception {
/* 15 */     write(attr + ": \"" + val + "\"");
/* 16 */     newLine();
/*    */   }
/*    */   
/*    */   public void writeOptAttr(String attr, String val) throws Exception {
/* 20 */     write("Option: \"" + attr + "; " + val + "\"");
/* 21 */     newLine();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\io\LogWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */