/*     */ package com.eoos.gm.tis2web.sps.client.util;
/*     */ 
/*     */ import com.eoos.html.gtwo.util.HtmlConversion;
/*     */ import com.eoos.tokenizer.StringTokenizer;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
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
/*     */ public class Transform
/*     */ {
/*     */   public static Vector createVector(Collection collection) {
/*  26 */     Vector result = new Vector();
/*     */     try {
/*  28 */       Iterator iterator = collection.iterator();
/*  29 */       while (iterator.hasNext()) {
/*  30 */         result.add(iterator.next());
/*     */       }
/*  32 */     } catch (Exception e) {
/*  33 */       System.out.println("Exception in createVector() methode: " + e.getMessage());
/*     */     } 
/*  35 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector copy(Vector<Vector<String>> data) {
/*  45 */     Vector<Vector<String>> copy = new Vector();
/*     */     
/*  47 */     for (int i = 0; i < data.size(); i++) {
/*  48 */       Vector<String> row = new Vector();
/*  49 */       for (int j = 0; j < ((Vector)data.get(i)).size(); j++) {
/*  50 */         row.add(new String(((Vector<String>)data.get(i)).get(j)));
/*     */       }
/*     */       
/*  53 */       copy.add(row);
/*     */     } 
/*  55 */     return copy;
/*     */   }
/*     */   
/*     */   public static String convertStringToHtml(String origin) {
/*  59 */     if (origin == null) {
/*  60 */       return null;
/*     */     }
/*  62 */     StringBuffer retValue = new StringBuffer("<html>{LINES}</html>");
/*  63 */     if (origin.indexOf("\n") != -1) {
/*  64 */       origin = HtmlConversion.getInstance().convert(origin);
/*  65 */       origin = StringUtilities.replace(origin, "\t\n", "\n");
/*  66 */       origin = StringUtilities.replace(origin, "\n\t", "\n");
/*  67 */       new StringBuffer(origin);
/*  68 */       StringTokenizer lineTokenizer = new StringTokenizer(origin, "\n");
/*  69 */       for (Iterator<String> iter = lineTokenizer.iterator(); iter.hasNext(); ) {
/*  70 */         String line = iter.next();
/*     */         
/*  72 */         StringBuffer lineCode = new StringBuffer("<table><tr>{COLS}</tr></table>");
/*  73 */         StringTokenizer columnTokenizer = new StringTokenizer(line, "\t");
/*  74 */         for (Iterator<String> colIter = columnTokenizer.iterator(); colIter.hasNext(); ) {
/*  75 */           String content = colIter.next();
/*  76 */           StringUtilities.replace(lineCode, "{COLS}", "<td>" + content + "</td>{COLS}");
/*     */         } 
/*  78 */         StringUtilities.replace(lineCode, "{COLS}", "");
/*  79 */         StringUtilities.replace(retValue, "{LINES}", lineCode + "{LINES}");
/*     */       } 
/*     */       
/*  82 */       StringUtilities.replace(retValue, "{LINES}", "");
/*     */     }
/*     */     else {
/*     */       
/*  86 */       StringUtilities.replace(retValue, "{LINES}", "<table><tr><td>" + origin + "</td></tr></table>");
/*     */     } 
/*  88 */     return retValue.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String convertHtmlToString(String origin) {
/*  93 */     if (origin == null)
/*  94 */       return null; 
/*  95 */     origin = StringUtilities.replace(origin, "<html>", "");
/*  96 */     origin = StringUtilities.replace(origin, "</html>", "");
/*  97 */     origin = StringUtilities.replace(origin, "<table>", "");
/*  98 */     origin = StringUtilities.replace(origin, "</table>", "");
/*  99 */     origin = StringUtilities.replace(origin, "<tr><td>", "");
/* 100 */     origin = StringUtilities.replace(origin, "</td></tr>", "");
/* 101 */     origin = StringUtilities.replace(origin, "<td>", "");
/* 102 */     origin = StringUtilities.replace(origin, "</td>", "");
/*     */     
/* 104 */     return origin;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\util\Transform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */