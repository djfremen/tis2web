/*    */ package com.eoos.gm.tis2web.frame.login.log.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.login.log.LoginLog;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.text.DateFormat;
/*    */ import java.util.Collection;
/*    */ import java.util.Date;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ public class LoginLogUtil
/*    */ {
/*    */   private static final String LOG_TEMPLATE = "{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}\t{DIVISION}\t{ORG_COUNTRY}\t{MAPPED_COUNTRY}\t{T2W_GROUP}";
/* 19 */   private static final DateFormat LOG_DATEFORMAT = DateFormat.getDateInstance(2);
/*    */   
/* 21 */   private static final DateFormat LOG_TIMEFORMAT = DateFormat.getTimeInstance(2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static byte[] createExport(Collection entries) {
/*    */     OutputStreamWriter osw;
/* 28 */     if (entries == null || entries.size() == 0) {
/* 29 */       return null;
/*    */     }
/* 31 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(entries.size() * 500);
/*    */     
/*    */     try {
/* 34 */       osw = new OutputStreamWriter(baos, "UTF-8");
/* 35 */     } catch (UnsupportedEncodingException e) {
/* 36 */       throw new RuntimeException(e);
/*    */     } 
/*    */     
/* 39 */     PrintWriter pw = new PrintWriter(osw);
/*    */ 
/*    */     
/* 42 */     StringBuffer header = new StringBuffer("{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}\t{DIVISION}\t{ORG_COUNTRY}\t{MAPPED_COUNTRY}\t{T2W_GROUP}");
/* 43 */     StringUtilities.replace(header, "{USER}", "NAME");
/* 44 */     StringUtilities.replace(header, "{STATUS}", "STATUS");
/* 45 */     StringUtilities.replace(header, "{DATE}", "DATE");
/* 46 */     StringUtilities.replace(header, "{TIME}", "TIME");
/* 47 */     StringUtilities.replace(header, "{IP}", "SOURCE ADDR");
/* 48 */     String freeParam = ApplicationContext.getInstance().getProperty("frame.scout.nao.login.logparam");
/* 49 */     if (freeParam == null) {
/* 50 */       freeParam = "";
/*    */     }
/* 52 */     StringUtilities.replace(header, "{FREEPARAM}", freeParam);
/* 53 */     StringUtilities.replace(header, "{ORIGIN}", "ORIGIN");
/* 54 */     StringUtilities.replace(header, "{GROUPNAME}", "GROUP");
/* 55 */     StringUtilities.replace(header, "{GROUPCODE}", "CODE");
/* 56 */     StringUtilities.replace(header, "{DIVISION}", "DIVISION");
/* 57 */     StringUtilities.replace(header, "{ORG_COUNTRY}", "COUNTRY (SPOREF)");
/* 58 */     StringUtilities.replace(header, "{MAPPED_COUNTRY}", "COUNTRY");
/* 59 */     StringUtilities.replace(header, "{T2W_GROUP}", "INTERNAL GROUP");
/*    */     
/* 61 */     pw.println(header);
/* 62 */     synchronized (LOG_DATEFORMAT) {
/* 63 */       for (Iterator<LoginLog.Entry2> iter = entries.iterator(); iter.hasNext(); ) {
/* 64 */         LoginLog.Entry2 entry = iter.next();
/* 65 */         StringBuffer logentry = new StringBuffer("{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}\t{DIVISION}\t{ORG_COUNTRY}\t{MAPPED_COUNTRY}\t{T2W_GROUP}");
/* 66 */         StringUtilities.replace(logentry, "{USER}", adjust(entry.getUsername()));
/* 67 */         StringUtilities.replace(logentry, "{STATUS}", entry.successfulLogin() ? "allow" : "deny");
/* 68 */         StringUtilities.replace(logentry, "{DATE}", LOG_DATEFORMAT.format(new Date(entry.getTimestamp())));
/* 69 */         StringUtilities.replace(logentry, "{TIME}", LOG_TIMEFORMAT.format(new Date(entry.getTimestamp())));
/* 70 */         StringUtilities.replace(logentry, "{IP}", adjust(entry.getSourceAddress()));
/* 71 */         StringUtilities.replace(logentry, "{FREEPARAM}", adjust(entry.getFreeParameter()));
/* 72 */         StringUtilities.replace(logentry, "{ORIGIN}", adjust(entry.getOrigin()));
/* 73 */         StringUtilities.replace(logentry, "{GROUPNAME}", adjust(entry.getUserGroup()));
/* 74 */         StringUtilities.replace(logentry, "{GROUPCODE}", adjust(entry.getDealerCode()));
/* 75 */         StringUtilities.replace(logentry, "{DIVISION}", adjust(entry.getDivisionCode()));
/* 76 */         StringUtilities.replace(logentry, "{ORG_COUNTRY}", adjust(entry.getOriginalCountryCode()));
/* 77 */         StringUtilities.replace(logentry, "{MAPPED_COUNTRY}", adjust(entry.getMappedCountryCode()));
/* 78 */         StringUtilities.replace(logentry, "{T2W_GROUP}", adjust(entry.getT2WGroup()));
/* 79 */         pw.println(logentry);
/*    */       } 
/*    */     } 
/* 82 */     pw.flush();
/* 83 */     pw.close();
/*    */     
/* 85 */     return baos.toByteArray();
/*    */   }
/*    */ 
/*    */   
/*    */   private static String adjust(String string) {
/* 90 */     if (string == null) {
/* 91 */       return "-";
/*    */     }
/* 93 */     return String.valueOf(string);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\lo\\util\LoginLogUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */