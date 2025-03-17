/*     */ package com.eoos.gm.tis2web.sps.client.ui.datamodel;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.ReleaseDateFormat;
/*     */ import com.eoos.gm.tis2web.sps.client.util.Transform;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*     */ import com.eoos.html.gtwo.util.HtmlConversion;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HistoryData
/*     */ {
/*  24 */   protected List data = null;
/*     */   
/*  26 */   private static final Logger log = Logger.getLogger(HistoryData.class);
/*     */   
/*     */   protected int countAttribute;
/*     */   
/*     */   public HistoryData(List data) {
/*  31 */     this.data = data;
/*     */   }
/*     */   
/*     */   public Vector getHistoryData() {
/*  35 */     Vector<Vector<String>> result = new Vector();
/*     */     
/*     */     try {
/*  38 */       List list = this.data;
/*  39 */       if (list != null) {
/*  40 */         Iterator<History> iterator = list.iterator();
/*     */         
/*  42 */         while (iterator.hasNext()) {
/*  43 */           Vector<String> row = new Vector();
/*  44 */           History history = iterator.next();
/*     */           
/*  46 */           String relDate = ReleaseDateFormat.getReleaseDateFormat(history.getReleaseDate());
/*  47 */           if (relDate != null) {
/*  48 */             row.add(relDate);
/*     */           } else {
/*  50 */             row.add(" ");
/*  51 */           }  Pair pair = history.getAttributes().get(0);
/*  52 */           if (pair != null) {
/*  53 */             String first = pair.getFirst().toString();
/*  54 */             String second = pair.getSecond().toString();
/*  55 */             String htmlRow = "<html> <table><tr><td>" + first + "</td>" + "<td align=\"right\" >" + second + "</td>" + "</tr>";
/*     */             
/*  57 */             for (int i = 1; i < history.getAttributes().size(); i++) {
/*  58 */               new Vector();
/*  59 */               pair = history.getAttributes().get(i);
/*  60 */               first = pair.getFirst().toString();
/*  61 */               second = pair.getSecond().toString();
/*  62 */               htmlRow = htmlRow + "<tr><td>" + first + "</td>" + "<td align=\"right\" >" + second + "</td>" + "</tr>";
/*     */             } 
/*     */             
/*  65 */             htmlRow = htmlRow + "</html> </table>";
/*  66 */             row.add(htmlRow);
/*  67 */             if (history.getDescription() == null) {
/*  68 */               row.add("");
/*     */             } else {
/*  70 */               String historyString = fixHTML(history.getDescription().toString());
/*  71 */               historyString = HtmlConversion.getInstance().convert(historyString);
/*  72 */               historyString = StringUtilities.replace(historyString, "\\n", "\n");
/*  73 */               historyString = Transform.convertStringToHtml(historyString);
/*  74 */               row.add(historyString);
/*     */             } 
/*  76 */             result.add(row);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*  81 */     } catch (Exception e) {
/*  82 */       log.error("unable to construct history data, -exception : " + e.getMessage());
/*     */     } 
/*  84 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private String fixHTML(String html) {
/*  89 */     html = StringUtilities.replace(html, "&lt;a href=&quot;", "<a href=\"");
/*  90 */     html = StringUtilities.replace(html, "&quot;&gt;", "\">");
/*  91 */     html = StringUtilities.replace(html, "&lt;/a&gt;", "");
/*  92 */     int idx = html.indexOf("<a");
/*  93 */     if (idx >= 0) {
/*  94 */       StringBuffer buffer = new StringBuffer();
/*  95 */       buffer.append(html.substring(0, idx));
/*  96 */       for (int i = 0; i < html.length(); i++) {
/*  97 */         if (html.charAt(i) == '>') {
/*  98 */           buffer.append(html.substring(i + 1));
/*  99 */           return fixHTML(buffer.toString());
/*     */         } 
/*     */       } 
/*     */     } 
/* 103 */     return html;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\datamodel\HistoryData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */