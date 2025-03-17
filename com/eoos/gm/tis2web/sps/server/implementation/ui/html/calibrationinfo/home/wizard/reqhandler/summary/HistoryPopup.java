/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HistoryPopup
/*    */   extends Page
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(HistoryPopup.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 29 */       template = ApplicationContext.getInstance().loadFile(HistoryPopup.class, "history.html", null).toString();
/* 30 */     } catch (Exception e) {
/* 31 */       log.error("unable to load template - error:" + e, e);
/* 32 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private List history;
/*    */   
/*    */   public HistoryPopup(ClientContext context, List history) {
/* 42 */     super(context);
/* 43 */     this.context = context;
/* 44 */     this.history = history;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 49 */     StringBuffer tmp = new StringBuffer(template);
/* 50 */     StringUtilities.replace(tmp, "{LABEL_HISTORY}", this.context.getLabel("history"));
/* 51 */     StringUtilities.replace(tmp, "{LABEL_CLOSE}", this.context.getLabel("close"));
/*    */     
/* 53 */     if (this.history != null && this.history.size() > 0) {
/* 54 */       StringBuffer table = new StringBuffer("<table><tr><th>{LABEL_RELEASE_DATE}</th><th >{LABEL_SOFTWARE}</th><th>{LABEL_DESCRIPTION}</th></tr>{ROWS}</table>");
/* 55 */       StringUtilities.replace(table, "{LABEL_RELEASE_DATE}", this.context.getLabel("release.date"));
/* 56 */       StringUtilities.replace(table, "{LABEL_SOFTWARE}", this.context.getLabel("software"));
/* 57 */       StringUtilities.replace(table, "{LABEL_DESCRIPTION}", this.context.getLabel("description"));
/* 58 */       for (Iterator<History> iter = this.history.iterator(); iter.hasNext(); ) {
/* 59 */         History history = iter.next();
/* 60 */         StringBuffer row = new StringBuffer("<tr><td>{RELEASEDATE}</td><td>{ATTRIBUTE_TABLE}</td><td>{DESCRIPTION}</td></tr>");
/* 61 */         StringUtilities.replace(row, "{RELEASEDATE}", formatReleaseDate(history.getReleaseDate()));
/* 62 */         StringUtilities.replace(row, "{DESCRIPTION}", history.getDescription());
/*    */         
/* 64 */         StringBuffer attTable = new StringBuffer("<span class=\"att_table\"><table>{ROWS}</table></span>");
/* 65 */         for (Iterator<Pair> iterAttributes = history.getAttributes().iterator(); iterAttributes.hasNext(); ) {
/* 66 */           Pair pair = iterAttributes.next();
/* 67 */           StringUtilities.replace(attTable, "{ROWS}", "<tr><th>" + String.valueOf(pair.getFirst()) + "</th><td>" + String.valueOf(pair.getSecond() + "</td></tr>{ROWS}"));
/*    */         } 
/* 69 */         StringUtilities.replace(attTable, "{ROWS}", "");
/*    */         
/* 71 */         StringUtilities.replace(row, "{ATTRIBUTE_TABLE}", attTable.toString());
/* 72 */         StringUtilities.replace(table, "{ROWS}", row.toString() + "{ROWS}");
/*    */       } 
/* 74 */       StringUtilities.replace(table, "{ROWS}", "");
/* 75 */       StringUtilities.replace(tmp, "{HISTORY_TABLE}", table.toString());
/*    */     } else {
/*    */       
/* 78 */       StringUtilities.replace(tmp, "{HISTORY_TABLE}", "");
/*    */     } 
/*    */     
/* 81 */     return tmp.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   private String formatReleaseDate(String releaseDate) {
/*    */     try {
/* 87 */       DateFormat in = new SimpleDateFormat("yyyyMMdd");
/* 88 */       DateFormat out = DateFormat.getDateInstance(2, this.context.getLocale());
/* 89 */       Date d = in.parse(releaseDate);
/* 90 */       return out.format(d);
/* 91 */     } catch (Exception e) {
/* 92 */       log.warn("unable to format date: " + releaseDate);
/* 93 */       return releaseDate;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getOnLoadHandlerCode(Map params) {
/* 99 */     return "javascript:window.focus()";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\summary\HistoryPopup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */