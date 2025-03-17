/*    */ package com.eoos.gm.tis2web.sids.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ServiceIDDBReader
/*    */   extends DBReader
/*    */ {
/*    */   private ParamWhereClause whereClause;
/*    */   private String sql;
/*    */   
/*    */   public ServiceIDDBReader(IDatabaseLink db, String before, ParamWhereClause whereClause, String after) {
/* 24 */     super(db);
/* 25 */     this.whereClause = whereClause;
/* 26 */     this.sql = before + " " + whereClause.getSql() + " " + after;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setParams(PreparedStatement stmt) throws Exception {
/* 32 */     List params = this.whereClause.getParams();
/* 33 */     Iterator<String> it = params.iterator();
/* 34 */     int ind = 0;
/* 35 */     while (it.hasNext()) {
/* 36 */       ind++;
/* 37 */       String value = it.next();
/* 38 */       stmt.setString(ind, value);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void read() throws Exception {
/* 44 */     read(this.sql);
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\ServiceIDDBReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */