/*   */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*   */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*   */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingFile;
/*   */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*   */ 
/*   */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*   */ public class SPSProgrammingFile extends SPSProgrammingFile {
/*   */   public static SPSProgrammingFile load(IDatabaseLink dblink, long vci) throws Exception {
/* 9 */     return SPSProgrammingFile.load(dblink, true, vci);
/*   */   }
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSProgrammingFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */