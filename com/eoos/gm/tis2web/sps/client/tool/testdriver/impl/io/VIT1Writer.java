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
/*    */ public class VIT1Writer
/*    */   extends BufferedWriter
/*    */ {
/*    */   public VIT1Writer(Writer w) {
/* 18 */     super(w);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(VIT1Reader reader, int statusLineNo, String status) throws Exception {
/* 23 */     int index = 0;
/*    */     String line;
/* 25 */     while ((line = reader.readLine()) != null) {
/* 26 */       index++;
/* 27 */       if (index == statusLineNo) {
/* 28 */         if (line.indexOf("Status") != -1) {
/* 29 */           line = "Rem Status: " + status;
/*    */         } else {
/* 31 */           write("Rem Status: " + status);
/* 32 */           newLine();
/*    */         } 
/*    */       }
/* 35 */       write(line);
/* 36 */       newLine();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(List<String> lst, int statusLineNo, String status) throws Exception {
/* 42 */     int index = 0;
/*    */     
/* 44 */     for (int i = 0; i < lst.size(); i++) {
/* 45 */       String line = lst.get(i);
/* 46 */       index++;
/* 47 */       if ((index == statusLineNo && statusLineNo == 1) || (statusLineNo > 1 && line.indexOf("Status") != -1)) {
/* 48 */         line = "Rem Status: " + status;
/*    */       }
/* 50 */       write(line);
/* 51 */       newLine();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\io\VIT1Writer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */