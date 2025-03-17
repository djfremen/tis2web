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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NavToReqInf
/*    */   extends GMETestTableGen
/*    */ {
/*    */   public void insert(Connection con) {
/* 32 */     Table table = new Table(con, "SPS_BaseVehicle ");
/* 33 */     if (table.create("ReqInfoID number(18) not null,ServiceCode number(18) not null,ModelCode number(18),ModelYearCode number(18),SystemType number(18)")) {
/* 34 */       table.insertSelect("1, min (a.salesmakecode),min(b.modelcode),min (c.ModelYearCode),null  from sps_salesmake a,SPS_ModelDescription b,SPS_ModelYear  c where a.salesmake = 'Opel' and b.description='ASTRA-G' and c.ModelYear='2001'");
/* 35 */       table.insertSelect("2, min (a.salesmakecode),min(b.modelcode),min (c.ModelYearCode),min (d.SystemType)from sps_salesmake a,SPS_ModelDescription b,SPS_ModelYear c,SPS_SystemTypes d where a.salesmake = 'Opel' and b.description='ASTRA-G' and c.ModelYear='2004' and d.description ='Engine'");
/* 36 */       table.insertSelect("3, min (a.salesmakecode),min(b.modelcode),min (c.ModelYearCode),min (d.SystemType)from sps_salesmake a,SPS_ModelDescription b,SPS_ModelYear c,SPS_SystemTypes d where a.salesmake = 'Opel' and b.description='CORSA-C' and c.ModelYear='2004' and d.description ='Engine'");
/* 37 */       table.insertSelect("4, min (a.salesmakecode),min(b.modelcode),min (c.ModelYearCode),min (d.SystemType)from sps_salesmake a,SPS_ModelDescription b,SPS_ModelYear c,SPS_SystemTypes d where a.salesmake = 'Opel' and b.description='CORSA-C' and c.ModelYear='2004' and d.description ='Engine'");
/* 38 */       table.insertSelect("5, min (a.salesmakecode),min(b.modelcode),min (c.ModelYearCode),min (d.SystemType)from sps_salesmake a,SPS_ModelDescription b,SPS_ModelYear c,SPS_SystemTypes d where a.salesmake = 'Opel' and b.description='CORSA-C' and c.ModelYear='2004' and d.description ='Engine'");
/* 39 */       table.insertSelect("6, min (a.salesmakecode),min(b.modelcode),min (c.ModelYearCode),min (d.SystemType)from sps_salesmake a,SPS_ModelDescription b,SPS_ModelYear c,SPS_SystemTypes d where a.salesmake = 'Opel' and b.description='CORSA-C' and c.ModelYear='2004' and d.description ='Engine'");
/*    */     } 
/*    */     
/* 42 */     table = new Table(con, "SPS_BaseVehOption");
/* 43 */     if (table.create("ReqInfoID Number(18) not null,CategoryCode  varchar2(6),OptionGroup number(18) not null,OptionOrder number(18) not null")) {
/* 44 */       table.insertSelect("2, min(a.CategoryCode ),min(b.OptionGroup ),0  from SPS_Categories a,SPS_OptionGroup b,SPS_FreeOptions c where a.description = 'Engine' and b.OptionCode=c.OptionCode and c.description = 'Z 10 XEP'");
/* 45 */       table.insertSelect("3, min(a.CategoryCode ),min(b.OptionGroup ),0  from SPS_Categories a,SPS_OptionGroup b,SPS_FreeOptions c where a.description = 'Engine' and b.OptionCode=c.OptionCode and c.description = 'Z 12 XE'");
/* 46 */       table.insertSelect("4, min(a.CategoryCode ),min(b.OptionGroup ),0  from SPS_Categories a,SPS_OptionGroup b,SPS_FreeOptions c where a.description = 'Engine' and b.OptionCode=c.OptionCode and c.description = 'Z 13 DT'");
/* 47 */       table.insertSelect("5, min(a.CategoryCode ),min(b.OptionGroup ),0  from SPS_Categories a,SPS_OptionGroup b,SPS_FreeOptions c where a.description = 'Engine' and b.OptionCode=c.OptionCode and c.description = 'Z 14 XE'");
/*    */     } 
/*    */     
/* 50 */     table = new Table(con, "SPS_ReqInfo");
/* 51 */     if (table.create("ReqInfoID   number(18) not null,ReqInfoMeth_GroupID number(18) not null    ")) {
/* 52 */       table.insert("1,7");
/* 53 */       table.insert("2,7");
/* 54 */       table.insert("3,7");
/* 55 */       table.insert("4,8");
/* 56 */       table.insert("5,7");
/* 57 */       table.insert("6,73");
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {
/* 62 */     (new NavToReqInf()).run("gme.test.NavToReqInf");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\test\gmetesttables\NavToReqInf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */