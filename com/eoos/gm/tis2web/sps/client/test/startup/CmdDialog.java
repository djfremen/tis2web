/*    */ package com.eoos.gm.tis2web.sps.client.test.startup;
/*    */ 
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.LineNumberReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CmdDialog
/*    */ {
/*    */   public String perform(String caption, String endCondition) throws Exception {
/* 15 */     String result = null;
/* 16 */     System.out.print(caption);
/* 17 */     LineNumberReader rdr = new LineNumberReader(new InputStreamReader(System.in));
/* 18 */     result = rdr.readLine();
/* 19 */     if (endCondition.compareTo(result) != 0) {
/* 20 */       return result;
/*    */     }
/* 22 */     throw new EndException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\startup\CmdDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */