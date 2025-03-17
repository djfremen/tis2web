/*    */ package com.eoos.gm.tis2web.lt.implementation.io.icl.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
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
/*    */ 
/*    */ public class DBMS
/*    */ {
/*    */   public static final String ORACLE = "Oracle";
/*    */   public static final String TRANSBASE = "Transbase";
/*    */   static final String GET_LANGUAGES = "select la_id,la_scds from Languages";
/*    */   static final String GET_CONSTANTS = "select a.cs_name,b.la_fk,b.tx_text from Constants a,Strings b where b.tx_id = a.tx_fk";
/*    */   static final String GET_COUNTRYGROUPS = "select cg.CG_ID, cg.CG_NAME, c.CO_CODE from COUNTRIES c, COUNTRYGROUPS cg WHERE c.CG_FK = cg.CG_ID";
/*    */   static final String GET_WILDCARD_COUNTRYGROUP = "select CG_ID, CG_NAME from COUNTRYGROUPS";
/*    */   static final String GET_ENGINES = "select en_id,en_code from Engines";
/*    */   static final String GET_MODELS = "select mo_id,mo_name from Models";
/*    */   static final String GET_MODELYEARS = "select my_id,my_year from ModelYears";
/*    */   static final String GET_ATTRIBUTES = "select a.at_id,b.la_fk,b.tx_text from Attributes a,Strings b where b.tx_id = a.tx_fk";
/*    */   static final String GET_COMMENTS = "select a.cm_id,b.la_fk,b.tx_text from Comments a,Strings b where b.tx_id = a.tx_fk";
/*    */   static final String GET_DRIVERTYPES = "select a.dt_id,b.la_fk,b.tx_text from DriverTypes a,Strings b where b.tx_id = a.tx_fk";
/*    */   static final String GET_SERVICETYPES = "select a.st_id,b.la_fk,b.tx_text from ServiceTypes a,Strings b where b.tx_id = a.tx_fk";
/*    */   static final String GET_SERVICETYPES_XSL = "select a.st_id,b.la_fk,b.tx_text,a.st_style from ServiceTypes a,Strings b where b.tx_id = a.tx_fk";
/*    */   static final String GET_MAJOROPERATION_DESCRIPTIONS = "select a.ha_descr,b.la_fk,b.tx_text from MajorOps a,Strings b where b.tx_id = a.tx_fk";
/*    */   static final String GET_MAJOROPERATIONS = "select ha_id,ha_descr,tx_fk,dt_fk,st_fk from MajorOps";
/*    */   static final String GET_VEHICLE2CHECKLIST = "select a.mo_fk,a.my_fk,a.en_fk,a.ha_fk,a.cg_fk,a.cl_fk from CL2Vehicle a, Checklists b where a.cl_fk = b.cl_id and b.rls_flag = 1";
/*    */   static final String GET_CHECKLIST = "select a.ce_id, b.tx_id, b.tx_text, a.ai_flag, a.ec_flag, e.el_sp_link, a.cm_fk, a.el_order from Strings b, Elements e, CL2Element a  where  a.cl_fk=? and b.tx_id = a.tx_fk and b.la_fk=? and a.el_fk = e.el_id order by a.el_order";
/*    */   static final String GET_CHECKLIST_FOOTERS = "select a.fo_fk, a.fo_order, b.tx_id, b.tx_text from CL2Footer a,Footers c,Strings b where  a.cl_fk=? and c.fo_id= a.fo_fk and b.tx_id =c.tx_fk and b.la_fk=? order by a.fo_order";
/*    */   static final String GET_CHECKLIST_ATTRIBUTES = "select cle.ce_id, cev.at_order, cev.va_order, cev.at_fk, ass.tx_id, ass.tx_text, cev.va_fk, v.va_order as valorder, vs.tx_id, vs.tx_text from CL2Element cle, CE2Value cev, Attributes a, Strings  ass,\"Values\" v, Strings vs where cle.cl_fk=? and cev.ce_fk=cle.ce_id and a.at_id = cev.at_fk and ass.tx_id = a.tx_fk and ass.la_fk=? and v.va_id=cev.va_fk and vs.tx_id = v.tx_fk and (vs.la_fk=? OR vs.la_fk=0)order by cev.at_order,cev.va_order";
/*    */   static final String GET_CHECKLIST_FOOTER_POSITIONS = "select aa.ce_id, c.fn_id, c.fn_order, b.tx_id, b.tx_text from CL2Element aa, CE2Footnote a, Footnotes c, Strings b where aa.cl_fk=? and a.ce_fk=aa.ce_id and c.fn_id = a.fn_fk and b.tx_id =c.tx_fk and b.la_fk=? order by c.fn_order";
/*    */   protected Connection dbConnection;
/*    */   protected IDatabaseLink dblink;
/*    */   
/*    */   public Connection requestConnection() throws Exception {
/* 57 */     if (this.dblink != null) {
/* 58 */       return this.dblink.requestConnection();
/*    */     }
/* 60 */     return this.dbConnection;
/*    */   }
/*    */ 
/*    */   
/*    */   public DBMS(Connection db) {
/* 65 */     this.dbConnection = db;
/*    */   }
/*    */   
/*    */   public DBMS(IDatabaseLink dblink) {
/* 69 */     this.dblink = dblink;
/*    */   }
/*    */   
/*    */   public synchronized void releaseConnection(Connection connection) {
/* 73 */     if (this.dblink != null) {
/* 74 */       this.dblink.releaseConnection(connection);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean reconnect() {
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public String getSQL(String sql) {
/* 83 */     return sql;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\icl\db\DBMS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */