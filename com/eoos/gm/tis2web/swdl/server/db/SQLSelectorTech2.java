/*    */ package com.eoos.gm.tis2web.swdl.server.db;
/*    */ 
/*    */ import java.util.Hashtable;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SQLSelectorTech2
/*    */   implements SQLSelector
/*    */ {
/* 16 */   private Map key2sql = null;
/*    */ 
/*    */   
/*    */   public SQLSelectorTech2() {
/* 20 */     this.key2sql = new Hashtable<Object, Object>();
/* 21 */     this.key2sql.put(Integer.valueOf(1), "SELECT application_id, application_desc FROM SD_Applications");
/* 22 */     this.key2sql.put(Integer.valueOf(2), "SELECT version_id, version_no, version_date, version_size, additional_information FROM SD_Versions WHERE application_id=?");
/* 23 */     this.key2sql.put(Integer.valueOf(3), "SELECT f.file_id, f.file_name, f.file_type, f.revision, f.status, f.file_content, f.md5, v.language_id FROM SD_Version_Files v, SD_Files f WHERE v.version_id=? and f.file_id = v.file_id");
/* 24 */     this.key2sql.put(Integer.valueOf(4), "SELECT file_id, file_name, file_type, revision, status, page_no, block_no, memory_address, number_of_blocks, file_content, md5 FROM SD_Files WHERE file_id=?");
/*    */   }
/*    */   
/*    */   public String getSQL(int key) {
/* 28 */     String sql = (String)this.key2sql.get(Integer.valueOf(key));
/* 29 */     return sql;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\db\SQLSelectorTech2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */