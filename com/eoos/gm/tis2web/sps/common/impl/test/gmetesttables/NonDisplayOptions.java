/*    */ package com.eoos.gm.tis2web.sps.common.impl.test.gmetesttables;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NonDisplayOptions
/*    */   extends GMETestTableGen
/*    */ {
/*    */   public void insert(Connection con) {
/* 17 */     Table table = new Table(con, "SPS_VITCategories");
/* 18 */     if (table.create("CategoryCode char(3) not null, VITType char(2) not null,CatValue varchar2(255) not null,Position int")) {
/* 19 */       table.insert("'V01', 'A7', 'SNOET' ,-1");
/* 20 */       table.insert("'V02', 'A7' ,'DDI' ,NULL ");
/*    */     } 
/* 22 */     table = new Table(con, "SPS_VITOptions");
/* 23 */     if (table.create("OptionCode  int not null  , OptValue varchar2(255) not null")) {
/* 24 */       table.insert("1010, 'ABCD' ");
/* 25 */       table.insert("1020, 'DEFG' ");
/* 26 */       table.insert("4010, '123' ");
/* 27 */       table.insert("4020, '456' ");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/* 39 */     (new NonDisplayOptions()).run("gme.test.NonDisplayOptions");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\test\gmetesttables\NonDisplayOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */