/*    */ package com.eoos.gm.tis2web.sps.common.impl.test.gmetesttables;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*    */ import java.io.InputStream;
/*    */ import java.sql.Connection;
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GMETestTableGen
/*    */ {
/* 19 */   private static Logger log = Logger.getLogger(Table.class);
/*    */   
/*    */   public static String insSel(String what, String table, String where, String val) {
/* 22 */     return "select min (" + what + ") from " + table + " where " + where + " = " + val;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void insert(Connection paramConnection);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run(String database) {
/*    */     try {
/* 37 */       Properties props = new Properties();
/* 38 */       String file = "com/eoos/gm/tis2web/sps/client/test/GMETestTables/GMETestTableGen.properties";
/* 39 */       InputStream ip = getClass().getClassLoader().getResourceAsStream(file);
/* 40 */       props.load(ip);
/*    */       
/* 42 */       log.debug("Properties loaded");
/* 43 */       String driver = props.getProperty(database + ".drv");
/* 44 */       String url = props.getProperty(database + ".url");
/* 45 */       String user = props.getProperty(database + ".usr");
/* 46 */       String password = props.getProperty(database + ".pwd");
/* 47 */       DatabaseLink databaseLink = new DatabaseLink(driver, url, user, password, false, 4);
/* 48 */       Connection con = databaseLink.requestConnection();
/* 49 */       insert(con);
/* 50 */       databaseLink.releaseConnection(con);
/*    */     }
/* 52 */     catch (Exception e) {
/* 53 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/* 59 */     (new NavToReqInf()).run("gme.test.NavToReqInf");
/* 60 */     (new NonDisplayOptions()).run("gme.test.NonDisplayOptions");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\test\gmetesttables\GMETestTableGen.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */