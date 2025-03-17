/*    */ package com.eoos.gm.tis2web.frame.implementation.service;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class CheckDataStoreBookmarks
/*    */ {
/* 17 */   private Connection conn = null;
/* 18 */   private PreparedStatement stmt = null;
/* 19 */   private ResultSet rs = null;
/*    */   
/*    */   public void execute() throws Exception {
/*    */     try {
/* 23 */       connect();
/* 24 */       load();
/*    */     } finally {
/* 26 */       if (this.rs != null) {
/* 27 */         this.rs.close();
/*    */       }
/* 29 */       if (this.stmt != null) {
/* 30 */         this.stmt.close();
/*    */       }
/* 32 */       if (this.conn != null) {
/* 33 */         this.conn.close();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void load() throws SQLException {
/* 39 */     int records = 0;
/* 40 */     this.stmt = this.conn.prepareStatement("SELECT DATA_ID, DATA_BLOB, TS, TS_DISPLAY FROM DATA_STORE");
/* 41 */     this.rs = this.stmt.executeQuery();
/* 42 */     while (this.rs.next()) {
/* 43 */       Map settings = null;
/* 44 */       String id = this.rs.getString("DATA_ID");
/* 45 */       if (id.toLowerCase().endsWith(".sav")) {
/*    */         try {
/* 47 */           byte[] data = StreamUtil.readFully(this.rs.getBinaryStream("DATA_BLOB"));
/* 48 */           if (data != null) {
/* 49 */             ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 50 */             ObjectInputStream ois = new ObjectInputStream(bais);
/* 51 */             settings = (Map)ois.readObject();
/* 52 */             if (settings == null)
/*    */               continue; 
/* 54 */             Map objects = (Map)settings.get("persistent.objects");
/* 55 */             if (objects == null)
/*    */               continue; 
/* 57 */             boolean hasBookmarks = false;
/* 58 */             LinkedList si = (LinkedList)objects.get("si.bookmarks");
/* 59 */             if (si != null) {
/* 60 */               hasBookmarks = true;
/*    */             }
/* 62 */             LinkedList lt = (LinkedList)objects.get("lt.bookmarks");
/* 63 */             if (lt != null) {
/* 64 */               hasBookmarks = true;
/*    */             }
/* 66 */             ois.close();
/* 67 */             if (hasBookmarks) {
/* 68 */               records++;
/* 69 */               System.out.println(id);
/*    */             } 
/*    */           } 
/* 72 */         } catch (Exception x) {}
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 77 */     System.out.println("records = " + records);
/*    */   }
/*    */   
/*    */   private void connect() throws Exception {
/*    */     try {
/* 82 */       Class.forName("oracle.jdbc.OracleDriver");
/* 83 */       String url = "jdbc:oracle:thin:@148.96.228.19:1526:tis2web1";
/* 84 */       this.conn = DriverManager.getConnection(url, "SC", "dgsglobal4pre");
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 89 */       this.conn.setAutoCommit(false);
/* 90 */     } catch (Exception e) {
/* 91 */       System.err.println(e);
/* 92 */       throw e;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 98 */     CheckDataStoreBookmarks task = new CheckDataStoreBookmarks();
/* 99 */     task.execute();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\CheckDataStoreBookmarks.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */