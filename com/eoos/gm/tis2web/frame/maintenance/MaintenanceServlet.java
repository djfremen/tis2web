/*     */ package com.eoos.gm.tis2web.frame.maintenance;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.Statement;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MaintenanceServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  34 */   private static final Logger log = Logger.getLogger(MaintenanceServlet.class);
/*     */   
/*     */   private static final String RESOURCE = "/maintenance/queries.properties";
/*     */   
/*  38 */   private Properties queries = null;
/*     */   
/*  40 */   private List dbPrefixList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getResourceAsStream(String name) {
/*  50 */     if (name.indexOf("/") != 0) {
/*  51 */       name = "/" + name;
/*     */     }
/*  53 */     return getServletContext().getResourceAsStream(name);
/*     */   }
/*     */   
/*     */   private class ConnectionData
/*     */   {
/*  58 */     public String url = null;
/*     */     
/*  60 */     public String drv = null;
/*     */     
/*  62 */     public String usr = null;
/*     */     
/*  64 */     public String pwd = null;
/*     */     
/*  66 */     public String prefix = null;
/*     */     
/*     */     public ConnectionData(String prefix) {
/*  69 */       this.prefix = prefix;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private ConnectionData getConnectionData(String dbPrefix) {
/*  75 */     if (!dbPrefix.endsWith(".")) {
/*  76 */       dbPrefix = dbPrefix + ".";
/*     */     }
/*  78 */     ConnectionData retValue = new ConnectionData(dbPrefix);
/*  79 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), dbPrefix);
/*  80 */     retValue.url = subConfigurationWrapper.getProperty("url");
/*  81 */     retValue.usr = null; int i;
/*  82 */     for (i = 0; i < 2 && retValue.usr == null; i++) {
/*  83 */       switch (i) {
/*     */         case 0:
/*  85 */           retValue.usr = subConfigurationWrapper.getProperty("usr");
/*     */           break;
/*     */         case 1:
/*  88 */           retValue.usr = subConfigurationWrapper.getProperty("user");
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/*  94 */     retValue.pwd = null;
/*  95 */     for (i = 0; i < 3 && retValue.pwd == null; i++) {
/*  96 */       switch (i) {
/*     */         case 0:
/*  98 */           retValue.pwd = subConfigurationWrapper.getProperty("pwd");
/*     */           break;
/*     */         case 1:
/* 101 */           retValue.pwd = subConfigurationWrapper.getProperty("passwd");
/*     */           break;
/*     */         case 2:
/* 104 */           retValue.pwd = subConfigurationWrapper.getProperty("password");
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 110 */     retValue.drv = null;
/* 111 */     for (i = 0; i < 2 && retValue.drv == null; i++) {
/* 112 */       switch (i) {
/*     */         case 0:
/* 114 */           retValue.drv = subConfigurationWrapper.getProperty("driver");
/*     */           break;
/*     */         case 1:
/* 117 */           retValue.drv = subConfigurationWrapper.getProperty("drv");
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 122 */     if (retValue.drv == null) {
/* 123 */       retValue.drv = "oracle.jdbc.OracleDriver";
/*     */     }
/*     */     
/* 126 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDatabaseConfigurationKey(String key) {
/* 131 */     boolean ret = (key.indexOf(".db.") != -1 || key.indexOf("adapter") != -1);
/* 132 */     ret = (ret && key.endsWith(".url"));
/* 133 */     ret = (ret && !key.startsWith("dynamic.key"));
/* 134 */     return ret;
/*     */   }
/*     */   
/*     */   private synchronized List getDBPrefixList() {
/* 138 */     if (this.dbPrefixList == null) {
/* 139 */       this.dbPrefixList = new LinkedList();
/* 140 */       ApplicationContext applicationContext = ApplicationContext.getInstance();
/* 141 */       for (Iterator<String> iter = applicationContext.getKeys().iterator(); iter.hasNext(); ) {
/* 142 */         String key = iter.next();
/* 143 */         if (isDatabaseConfigurationKey(key)) {
/* 144 */           this.dbPrefixList.add(key.substring(0, key.lastIndexOf(".url")));
/*     */         }
/*     */       } 
/*     */     } 
/* 148 */     return this.dbPrefixList;
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized String getQuery(String dbprefix) {
/* 153 */     if (this.queries == null) {
/* 154 */       this.queries = new Properties();
/*     */       try {
/* 156 */         this.queries.load(getResourceAsStream("/maintenance/queries.properties"));
/* 157 */       } catch (IOException e) {
/* 158 */         log.error("unable to load dbqueries - error:" + e);
/*     */       } 
/*     */     } 
/* 161 */     return this.queries.getProperty(dbprefix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class QueryResult
/*     */   {
/*     */     public String message;
/*     */     
/*     */     public MaintenanceServlet.ConnectionData connectionData;
/*     */ 
/*     */     
/*     */     public static QueryResult createErrorResult(String errorMessage, MaintenanceServlet.ConnectionData data) {
/* 174 */       QueryResult retValue = new QueryResult();
/* 175 */       retValue.message = (errorMessage != null) ? errorMessage : "";
/* 176 */       retValue.connectionData = data;
/* 177 */       return retValue;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 183 */     log.debug("checking database liveliness ...");
/* 184 */     List<QueryResult> queryResultList = new LinkedList();
/*     */     
/* 186 */     for (Iterator<String> iter = getDBPrefixList().iterator(); iter.hasNext(); ) {
/* 187 */       String prefix = iter.next();
/* 188 */       log.debug("...found DB configuration property : " + prefix);
/* 189 */       String query = getQuery(prefix);
/* 190 */       if (query != null) {
/* 191 */         log.debug("...found corresponding query: " + query);
/* 192 */         log.debug("...retrieving connection data");
/* 193 */         ConnectionData cd = getConnectionData(prefix);
/*     */         
/* 195 */         log.debug("...trying to connect");
/* 196 */         Connection connection = null;
/*     */         try {
/* 198 */           if (cd.url != null && cd.url.length() > 0 && query != null && query.length() > 0) {
/* 199 */             Class.forName(cd.drv);
/* 200 */             connection = DriverManager.getConnection(cd.url, cd.usr, cd.pwd);
/*     */             try {
/* 202 */               log.debug("...executing query");
/* 203 */               Statement statement = connection.createStatement();
/*     */               try {
/* 205 */                 statement.executeQuery(query).close();
/* 206 */                 log.debug("...SUCCESS");
/*     */               } finally {
/* 208 */                 statement.close();
/*     */               } 
/*     */             } finally {
/* 211 */               connection.close();
/*     */             }  continue;
/*     */           } 
/* 214 */           log.warn("...aborted because url or query is null (check configuration)");
/*     */         }
/* 216 */         catch (Exception e) {
/* 217 */           log.warn("...unable to query database " + cd.prefix + " (url:" + cd.url + ", drv:" + cd.drv + ", usr:" + cd.usr + ", pwd:" + cd.pwd + ") -error:" + e);
/* 218 */           queryResultList.add(QueryResult.createErrorResult(e.getMessage(), cd));
/*     */         } 
/*     */         continue;
/*     */       } 
/* 222 */       log.debug("...no corresponding query found, skipping");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 227 */     log.debug("...creating status page");
/* 228 */     OutputStreamWriter osw = new OutputStreamWriter((OutputStream)response.getOutputStream());
/* 229 */     if (queryResultList.size() == 0) {
/* 230 */       osw.write("<html><head></head><body>STATUS=OK</body></html>");
/*     */     } else {
/* 232 */       StringBuffer tmp = new StringBuffer("<html><head></head><body>STATUS=DB_ERROR<br>{ERRORS}</body></html>");
/* 233 */       for (Iterator<QueryResult> iterator = queryResultList.iterator(); iterator.hasNext(); ) {
/* 234 */         QueryResult qr = iterator.next();
/* 235 */         StringBuffer error = new StringBuffer("DB_NAME={DB_NAME}<br>ERR_DESCR={MESSAGE}<br>");
/* 236 */         StringUtilities.replace(error, "{DB_NAME}", qr.connectionData.prefix);
/* 237 */         StringUtilities.replace(error, "{MESSAGE}", Util.escapeReservedHTMLChars(qr.message));
/* 238 */         StringUtilities.replace(tmp, "{ERRORS}", error.toString() + "{ERRORS}");
/*     */       } 
/* 240 */       StringUtilities.replace(tmp, "{ERRORS}", "");
/* 241 */       osw.write(tmp.toString());
/*     */     } 
/* 243 */     osw.close();
/* 244 */     log.debug("...done");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 257 */     processRequest(request, response);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 269 */     processRequest(request, response);
/*     */   }
/*     */   
/*     */   protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 273 */     if (req.getProtocol().endsWith("1.1")) {
/* 274 */       resp.sendError(405, "");
/*     */     } else {
/* 276 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 281 */     if (req.getProtocol().endsWith("1.1")) {
/* 282 */       resp.sendError(405, "");
/*     */     } else {
/* 284 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 289 */     if (req.getProtocol().endsWith("1.1")) {
/* 290 */       resp.sendError(405, "");
/*     */     } else {
/* 292 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 297 */     if (req.getProtocol().endsWith("1.1")) {
/* 298 */       resp.sendError(405, "");
/*     */     } else {
/* 300 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/* 305 */     if (req.getProtocol().endsWith("1.1")) {
/* 306 */       resp.sendError(405, "");
/*     */     } else {
/* 308 */       resp.sendError(400, "");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\maintenance\MaintenanceServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */