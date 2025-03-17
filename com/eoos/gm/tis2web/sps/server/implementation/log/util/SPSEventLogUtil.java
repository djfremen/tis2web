/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.text.DateFormat;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSEventLogUtil
/*    */ {
/*    */   public static byte[] createExport(Collection entries) {
/*    */     OutputStreamWriter osw;
/* 22 */     if (entries == null || entries.size() == 0) {
/* 23 */       return null;
/*    */     }
/*    */     
/* 26 */     DateFormat.getDateTimeInstance(3, 3, Locale.US);
/* 27 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(entries.size() * 500);
/*    */     
/*    */     try {
/* 30 */       osw = new OutputStreamWriter(baos, "UTF-8");
/* 31 */     } catch (UnsupportedEncodingException e) {
/* 32 */       throw new RuntimeException(e);
/*    */     } 
/*    */     
/* 35 */     PrintWriter pw = new PrintWriter(osw);
/* 36 */     for (Iterator<SPSEventLog.Entry> iter = entries.iterator(); iter.hasNext(); ) {
/* 37 */       SPSEventLog.Entry entry = iter.next();
/* 38 */       print(pw, entry);
/* 39 */       pw.println();
/*    */     } 
/* 41 */     pw.flush();
/* 42 */     pw.close();
/* 43 */     return baos.toByteArray();
/*    */   }
/*    */   
/*    */   private static void print(PrintWriter pw, SPSEventLog.Entry entry) {
/* 47 */     StringBuffer tmp = new StringBuffer("<{NAME} {ATTRIBUTES} />");
/* 48 */     StringUtilities.replace(tmp, "{NAME}", entry.getEventName());
/* 49 */     for (Iterator<SPSEventLog.Attribute> iter = entry.getEventAttributes().iterator(); iter.hasNext(); ) {
/* 50 */       SPSEventLog.Attribute attribute = iter.next();
/* 51 */       StringBuffer attr = new StringBuffer();
/* 52 */       attr.append(attribute.getName());
/* 53 */       attr.append("=");
/* 54 */       attr.append("\"" + attribute.getValue() + "\"");
/* 55 */       StringUtilities.replace(tmp, "{ATTRIBUTES}", attr.toString() + " {ATTRIBUTES}");
/*    */     } 
/* 57 */     StringUtilities.replace(tmp, " {ATTRIBUTES}", "");
/*    */     
/* 59 */     pw.println(tmp.toString());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\lo\\util\SPSEventLogUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */