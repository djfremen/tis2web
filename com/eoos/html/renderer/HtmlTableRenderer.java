/*     */ package com.eoos.html.renderer;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HtmlTableRenderer
/*     */   extends HtmlTagRenderer
/*     */ {
/*     */   protected static final String TEMPLATE_TABLE = "<table {TABLE_ADDITIONAL}>{COLGROUP}{ROWS}</table>";
/*     */   protected static final String TEMPLATE_COLGROUP = "<colgroup>{COLS}</colgroup> ";
/*     */   protected static final String TEMPLATE_COL = "<col {WIDTH}>";
/*     */   protected static final String TEMPLATE_ROW = "<tr {ROW_ADDITIONAL}>{CELLS}</tr>";
/*     */   protected static final String TEMPLATE_CELL = "<td {CELL_ADDITIONAL}>{CELLCONTENT}</td>";
/*     */   protected static final String TEMPLATE_HEADER = "<th {CELL_ADDITIONAL}>{CELLCONTENT}</th>";
/*     */   
/*     */   public static abstract class CallbackAdapter
/*     */     implements Callback, AdditionalAttributes
/*     */   {
/*     */     public void getAdditionalAttributes(Map map) {}
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesCell(int rowIndex, int columnIndex) {
/*  40 */       return null;
/*     */     }
/*     */     
/*     */     public HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesRow(int rowIndex) {
/*  44 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isHeader(int rowIndex, int columnIndex) {
/*  48 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void init(Map params) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private static HtmlTableRenderer instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized HtmlTableRenderer getInstance() {
/*  70 */     if (instance == null) {
/*  71 */       instance = new HtmlTableRenderer();
/*     */     }
/*     */     
/*  74 */     return instance;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Callback callback) {
/*  78 */     return getHtmlCode(callback, (Map)null);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Callback callback, Map params) {
/*  82 */     callback.init(params);
/*  83 */     StringBuffer code = StringBufferPool.getThreadInstance().get("<table {TABLE_ADDITIONAL}>{COLGROUP}{ROWS}</table>");
/*     */     
/*     */     try {
/*  86 */       int nRowCount = callback.getRowCount();
/*  87 */       int nColumnCount = callback.getColumnCount();
/*     */       
/*  89 */       String colgroup = null;
/*     */       
/*  91 */       if (callback instanceof Colgroup) {
/*  92 */         StringBuffer tmpColgroup = StringBufferPool.getThreadInstance().get("<colgroup>{COLS}</colgroup> ");
/*     */         
/*     */         try {
/*  95 */           for (int i = 0; i < nColumnCount; i++) {
/*  96 */             StringBuffer col = StringBufferPool.getThreadInstance().get("<col {WIDTH}>");
/*     */             try {
/*  98 */               String width = ((Colgroup)callback).getColumnWidth(i);
/*  99 */               StringUtilities.replace(col, "{WIDTH}", (width != null) ? ("width=\"" + width + "\"") : "");
/*     */               
/* 101 */               StringUtilities.replace(tmpColgroup, "{COLS}", col.toString() + "{COLS}");
/*     */             } finally {
/* 103 */               StringBufferPool.getThreadInstance().free(col);
/*     */             } 
/*     */           } 
/*     */           
/* 107 */           StringUtilities.replace(tmpColgroup, "{COLS}", "");
/* 108 */           colgroup = tmpColgroup.toString();
/*     */         } finally {
/*     */           
/* 111 */           StringBufferPool.getThreadInstance().free(tmpColgroup);
/*     */         } 
/*     */       } 
/*     */       
/* 115 */       StringUtilities.replace(code, "{COLGROUP}", (colgroup != null) ? colgroup : "");
/*     */       
/* 117 */       String[][] content = new String[nRowCount][nColumnCount];
/*     */       int r;
/* 119 */       for (r = 0; r < nRowCount; r++) {
/* 120 */         for (int c = 0; c < nColumnCount; c++) {
/* 121 */           content[r][c] = callback.getContent(r, c);
/*     */         }
/*     */       } 
/*     */       
/* 125 */       for (r = 0; r < nRowCount; r++) {
/* 126 */         StringBuffer row = StringBufferPool.getThreadInstance().get("<tr {ROW_ADDITIONAL}>{CELLS}</tr>");
/*     */         
/*     */         try {
/* 129 */           for (int c = 0; c < nColumnCount; c++) {
/* 130 */             if (content[r][c] != null) {
/* 131 */               StringBuffer cell = StringBufferPool.getThreadInstance().get(callback.isHeader(r, c) ? "<th {CELL_ADDITIONAL}>{CELLCONTENT}</th>" : "<td {CELL_ADDITIONAL}>{CELLCONTENT}</td>");
/*     */               try {
/* 133 */                 StringUtilities.replace(cell, "{CELLCONTENT}", content[r][c]);
/*     */                 
/* 135 */                 String str1 = null;
/*     */                 
/* 137 */                 if (callback instanceof AdditionalAttributes) {
/* 138 */                   str1 = getAdditionalAttributesCode(((AdditionalAttributes)callback).getAdditionalAttributesCell(r, c));
/*     */                 }
/*     */                 
/* 141 */                 StringUtilities.replace(cell, "{CELL_ADDITIONAL}", (str1 != null) ? str1 : "");
/*     */                 
/* 143 */                 StringUtilities.replace(row, "{CELLS}", cell.toString() + "{CELLS}");
/*     */               } finally {
/* 145 */                 StringBufferPool.getThreadInstance().free(cell);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 150 */           StringUtilities.replace(row, "{CELLS}", "");
/*     */           
/* 152 */           String str = null;
/*     */           
/* 154 */           if (callback instanceof AdditionalAttributes) {
/* 155 */             str = getAdditionalAttributesCode(((AdditionalAttributes)callback).getAdditionalAttributesRow(r));
/*     */           }
/*     */           
/* 158 */           StringUtilities.replace(row, "{ROW_ADDITIONAL}", (str != null) ? str : "");
/*     */           
/* 160 */           StringUtilities.replace(code, "{ROWS}", row.toString() + "{ROWS}");
/*     */         } finally {
/*     */           
/* 163 */           StringBufferPool.getThreadInstance().free(row);
/*     */         } 
/*     */       } 
/*     */       
/* 167 */       StringUtilities.replace(code, "{ROWS}", "");
/*     */       
/* 169 */       String additional = null;
/*     */       
/* 171 */       if (callback instanceof AdditionalAttributes) {
/* 172 */         additional = getAdditionalAttributesCode((AdditionalAttributes)callback);
/*     */       }
/*     */       
/* 175 */       StringUtilities.replace(code, "{TABLE_ADDITIONAL}", (additional != null) ? additional : "");
/*     */       
/* 177 */       return code.toString();
/*     */     } finally {
/*     */       
/* 180 */       StringBufferPool.getThreadInstance().free(code);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 185 */     return getHtmlCode((Callback)callback, params);
/*     */   }
/*     */   
/*     */   public static interface Colgroup {
/*     */     String getColumnWidth(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface Callback extends HtmlTagRenderer.Callback {
/*     */     int getColumnCount();
/*     */     
/*     */     String getContent(int param1Int1, int param1Int2);
/*     */     
/*     */     boolean isHeader(int param1Int1, int param1Int2);
/*     */     
/*     */     int getRowCount();
/*     */   }
/*     */   
/*     */   public static interface AdditionalAttributes extends HtmlTagRenderer.AdditionalAttributes {
/*     */     HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesCell(int param1Int1, int param1Int2);
/*     */     
/*     */     HtmlTagRenderer.AdditionalAttributes getAdditionalAttributesRow(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlTableRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */