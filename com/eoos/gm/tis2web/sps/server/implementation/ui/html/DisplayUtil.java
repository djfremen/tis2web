/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html;
/*    */ 
/*    */ import com.eoos.tokenizer.StringTokenizer;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DisplayUtil
/*    */ {
/*    */   public static String convertStringToHtml(String origin) {
/* 18 */     if (origin == null) {
/* 19 */       return null;
/*    */     }
/* 21 */     StringBuffer retValue = new StringBuffer("{LINES}");
/* 22 */     if (origin.indexOf("\n") != -1) {
/*    */       
/* 24 */       origin = StringUtilities.replace(origin, "\t\n", "\n");
/* 25 */       origin = StringUtilities.replace(origin, "\n\t", "\n");
/* 26 */       new StringBuffer(origin);
/* 27 */       StringTokenizer lineTokenizer = new StringTokenizer(origin, "\n");
/* 28 */       for (Iterator<String> iter = lineTokenizer.iterator(); iter.hasNext(); ) {
/* 29 */         String line = iter.next();
/*    */         
/* 31 */         StringBuffer lineCode = new StringBuffer("<table border=\"0\"><tr>{COLS}</tr></table>");
/* 32 */         StringTokenizer columnTokenizer = new StringTokenizer(line, "\t");
/* 33 */         for (Iterator<String> colIter = columnTokenizer.iterator(); colIter.hasNext(); ) {
/* 34 */           String content = colIter.next();
/* 35 */           StringUtilities.replace(lineCode, "{COLS}", "<td style=\"border:none\">" + content + "</td>{COLS}");
/*    */         } 
/* 37 */         StringUtilities.replace(lineCode, "{COLS}", "");
/* 38 */         StringUtilities.replace(retValue, "{LINES}", lineCode + "<br>{LINES}");
/*    */       } 
/*    */       
/* 41 */       StringUtilities.replace(retValue, "{LINES}", "");
/*    */     }
/*    */     else {
/*    */       
/* 45 */       StringUtilities.replace(retValue, "{LINES}", origin);
/*    */     } 
/* 47 */     return retValue.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\DisplayUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */