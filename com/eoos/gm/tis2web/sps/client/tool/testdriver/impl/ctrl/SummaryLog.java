/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.io.IOUtil;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.sql.Timestamp;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Vector;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SummaryLog
/*    */   extends BufferedWriter
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(SummaryLog.class);
/*    */ 
/*    */   
/*    */   public SummaryLog() throws IOException {
/* 26 */     super(new FileWriter((new File(IOUtil.getDirectory(), "summary_" + getTime() + ".log")).getAbsolutePath(), false));
/*    */   }
/*    */   
/*    */   protected static String getTime() {
/* 30 */     SimpleDateFormat formater = new SimpleDateFormat("ssmmHH_ddMMyy");
/* 31 */     return formater.format(new Timestamp(System.currentTimeMillis()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeRecord(Vector<E> record) {
/*    */     try {
/* 37 */       if (record.size() > 0) {
/* 38 */         write(record.get(0).toString());
/*    */       }
/*    */       
/* 41 */       for (int j = 1; j < record.size(); j++) {
/* 42 */         write(":\t");
/* 43 */         Object entry = record.get(j);
/* 44 */         write(entry.toString());
/*    */       } 
/* 46 */       newLine();
/* 47 */       log.debug("SummaryLog wrote Record");
/* 48 */     } catch (IOException e) {
/* 49 */       log.error(e);
/* 50 */       log.debug("SummaryLog writeRecord error");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\SummaryLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */