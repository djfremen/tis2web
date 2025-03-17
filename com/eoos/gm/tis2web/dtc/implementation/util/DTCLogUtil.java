/*    */ package com.eoos.gm.tis2web.dtc.implementation.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.dtc.service.cai.DTC;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import java.io.OutputStream;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Date;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DTCLogUtil
/*    */ {
/*    */   public static void createExport(Collection loggedDTCS, OutputStream os) throws Exception {
/* 20 */     os.write(getExchangeHeader().getBytes("UTF-8"));
/* 21 */     for (Iterator<DTC> iter = loggedDTCS.iterator(); iter.hasNext(); ) {
/* 22 */       DTC dtc = iter.next();
/* 23 */       char deviceFlag = 'T';
/* 24 */       if ("GDS".equalsIgnoreCase(dtc.getApplicationID()) || "EGDS".equalsIgnoreCase(dtc.getApplicationID())) {
/* 25 */         deviceFlag = (char)dtc.getContent()[2];
/*    */       }
/* 27 */       os.write(getDTCHeader(dtc.getBACCode(), ((DTC.Logged)dtc).getTimestamp(), dtc.getCountryCode(), deviceFlag).getBytes("UTF-8"));
/* 28 */       os.write(dtc.getContent());
/*    */     } 
/* 30 */     os.close();
/*    */   }
/*    */   
/* 33 */   private static final DateFormat headerDF = new SimpleDateFormat("yyMMddHHmmss");
/*    */   
/*    */   private static String getExchangeHeader() throws Exception {
/* 36 */     StringBuffer exHeader = new StringBuffer("?HDR");
/* 37 */     exHeader.append(getSourcesite());
/* 38 */     exHeader.append("         ");
/* 39 */     synchronized (headerDF) {
/* 40 */       exHeader.append(headerDF.format(new Date()) + "     00005");
/*    */     }  int i;
/* 42 */     for (i = 0; i < 42; i++) {
/* 43 */       exHeader.append(" ");
/*    */     }
/* 45 */     exHeader.append("FTPDTCUPLOAD110001000");
/* 46 */     for (i = 0; i < 904; i++) {
/* 47 */       exHeader.append(" ");
/*    */     }
/* 49 */     return exHeader.toString();
/*    */   }
/*    */   
/*    */   private static String getDTCHeader(String bacCode, long timestamp, String countryCode, char deviceFlag) throws Exception {
/* 53 */     StringBuffer header = new StringBuffer("&THS");
/* 54 */     DateFormat df = null;
/*    */     
/* 56 */     int version = 2;
/*    */     try {
/* 58 */       version = Integer.parseInt(ApplicationContext.getInstance().getProperty("frame.dtc.log.export.format").trim());
/* 59 */     } catch (Exception e) {}
/*    */ 
/*    */     
/* 62 */     switch (version)
/*    */     { case 1:
/* 64 */         header.append(bacCode.substring(5) + "DCS");
/* 65 */         df = new SimpleDateFormat("MMddHHmm");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 73 */         header.append("   ");
/* 74 */         header.append(df.format(new Date(timestamp)) + "DTCUPLOAD1 A");
/* 75 */         header.append(deviceFlag);
/* 76 */         header.append(toFixedLength(countryCode, 2));
/* 77 */         return header.toString(); }  header.append(bacCode); header.append(getSourcesite()); df = new SimpleDateFormat("yyyyMMddHHmm"); header.append("   "); header.append(df.format(new Date(timestamp)) + "DTCUPLOAD1 A"); header.append(deviceFlag); header.append(toFixedLength(countryCode, 2)); return header.toString();
/*    */   }
/*    */   
/*    */   public static String toFixedLength(String string, int length) {
/* 81 */     StringBuffer ret = new StringBuffer();
/* 82 */     if (string != null) {
/* 83 */       ret.append(string);
/*    */     }
/* 85 */     for (int i = 0; i < length; i++) {
/* 86 */       ret.append(" ");
/*    */     }
/* 88 */     return ret.substring(0, length);
/*    */   }
/*    */   
/*    */   private static String getSourcesite() {
/* 92 */     return toFixedLength(ApplicationContext.getInstance().getProperty("frame.sourcesite"), 3);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 96 */     StringBuffer tmp = new StringBuffer();
/* 97 */     tmp.append('\005');
/* 98 */     tmp.append(84);
/* 99 */     System.out.println(Arrays.toString(tmp.toString().getBytes()));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementatio\\util\DTCLogUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */