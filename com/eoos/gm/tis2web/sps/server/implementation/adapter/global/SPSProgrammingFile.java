/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingFile;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class SPSProgrammingFile extends SPSProgrammingFile {
/*    */   public static SPSProgrammingFile load(long vci, SPSSchemaAdapterGlobal adapter) throws Exception {
/*  9 */     IDatabaseLink dblink = adapter.getDatabaseLink();
/* 10 */     return SPSProgrammingFile.load(dblink, vci);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSProgrammingFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */