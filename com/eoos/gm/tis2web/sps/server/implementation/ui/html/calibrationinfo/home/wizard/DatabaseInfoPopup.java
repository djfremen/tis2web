/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*    */ import com.eoos.util.AssertUtil;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DatabaseInfoPopup
/*    */   extends Page
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(DatabaseInfoPopup.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 26 */       template = ApplicationContext.getInstance().loadFile(DatabaseInfoPopup.class, "databaseinfopopup.html", null).toString();
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to load template - error:" + e, e);
/* 29 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private DatabaseInfo dbInfo;
/*    */   
/*    */   public DatabaseInfoPopup(ClientContext context, DatabaseInfo dbInfo) {
/* 39 */     super(context);
/* 40 */     this.context = context;
/* 41 */     this.dbInfo = dbInfo;
/* 42 */     AssertUtil.ensure(this.dbInfo, AssertUtil.NOT_NULL);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 47 */     StringBuffer tmp = new StringBuffer(template);
/* 48 */     StringUtilities.replace(tmp, "{TITLE}", this.dbInfo.getTitle());
/* 49 */     StringUtilities.replace(tmp, "{LABEL_VIN}", this.context.getLabel("vin"));
/* 50 */     StringUtilities.replace(tmp, "{VIN}", this.dbInfo.getVIN());
/* 51 */     StringUtilities.replace(tmp, "{LABEL_CLOSE}", this.context.getLabel("close"));
/*    */     
/* 53 */     List tables = this.dbInfo.getTables();
/* 54 */     if (tables == null || tables.size() == 0) {
/* 55 */       StringUtilities.replace(tmp, "{TABLES}", this.context.getMessage("sps.calib.info.no.debug.info.available"));
/*    */     } else {
/* 57 */       StringBuffer tablesCode = new StringBuffer("{TABLE}");
/* 58 */       for (Iterator<DatabaseInfo.Table> iter = tables.iterator(); iter.hasNext(); ) {
/* 59 */         DatabaseInfo.Table table = iter.next();
/* 60 */         StringBuffer codeTable = new StringBuffer("<table><tr><td><b>{TITLE}:</b></td></tr><tr><td>{INNER_TABLE}</td></tr></table>");
/* 61 */         StringUtilities.replace(codeTable, "{TITLE}", table.getTitle());
/* 62 */         StringBuffer innerTable = new StringBuffer("<table>{ROWS}</table>");
/* 63 */         for (int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
/* 64 */           StringBuffer row = new StringBuffer("<tr>{COLS}</tr>");
/* 65 */           for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
/* 66 */             StringBuffer col = new StringBuffer();
/* 67 */             if (table.isHeader(rowIndex, colIndex)) {
/* 68 */               col.append("<th>{CONTENT}</th>");
/*    */             } else {
/* 70 */               col.append("<td>{CONTENT}</td>");
/*    */             } 
/* 72 */             StringUtilities.replace(col, "{CONTENT}", String.valueOf(table.getContent(rowIndex, colIndex)));
/* 73 */             StringUtilities.replace(row, "{COLS}", col + "{COLS}");
/*    */           } 
/* 75 */           StringUtilities.replace(row, "{COLS}", "");
/* 76 */           StringUtilities.replace(innerTable, "{ROWS}", row + "{ROWS}");
/*    */         } 
/* 78 */         StringUtilities.replace(innerTable, "{ROWS}", "");
/* 79 */         StringUtilities.replace(codeTable, "{INNER_TABLE}", innerTable.toString());
/* 80 */         StringUtilities.replace(tablesCode, "{TABLE}", codeTable + "<br>{TABLE}");
/*    */       } 
/* 82 */       StringUtilities.replace(tablesCode, "<br>{TABLE}", "");
/* 83 */       StringUtilities.replace(tablesCode, "{TABLE}", "");
/*    */       
/* 85 */       StringUtilities.replace(tmp, "{TABLES}", tablesCode.toString());
/*    */     } 
/*    */     
/* 88 */     return tmp.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getOnLoadHandlerCode(Map params) {
/* 93 */     return "javascript:window.focus()";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\DatabaseInfoPopup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */