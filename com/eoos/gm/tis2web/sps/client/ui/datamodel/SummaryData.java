/*     */ package com.eoos.gm.tis2web.sps.client.ui.datamodel;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*     */ import com.eoos.html.gtwo.util.HtmlConversion;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ public class SummaryData
/*     */ {
/*  21 */   protected Summary summary = null;
/*     */   
/*  23 */   protected static final Logger log = Logger.getLogger(SummaryData.class);
/*     */   
/*     */   public SummaryData(Summary summary) {
/*  26 */     this.summary = summary;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getDescription() {
/*  31 */     Vector<Vector<String>> result = new Vector();
/*  32 */     History select = this.summary.getSelectedSoftware();
/*     */     try {
/*  34 */       if (select != null) {
/*     */         
/*  36 */         String description = select.getDescription();
/*     */         
/*  38 */         if (description == null) {
/*  39 */           return null;
/*     */         }
/*  41 */         description = StringUtilities.replace(description, "\\\\n", "\\n");
/*  42 */         description = HtmlConversion.getInstance().convert(description);
/*  43 */         String searchFor = "\\n";
/*  44 */         int index = description.indexOf(searchFor);
/*  45 */         while (index != -1) {
/*  46 */           Vector<String> row = new Vector();
/*  47 */           String lineString = description.substring(0, index);
/*  48 */           if (index < description.length()) {
/*  49 */             description = description.substring(index + 2, description.length());
/*     */           }
/*  51 */           if (lineString.length() == 0)
/*     */             break; 
/*  53 */           row.add(lineString);
/*  54 */           result.add(row);
/*  55 */           index = description.indexOf(searchFor);
/*     */         } 
/*  57 */         if (description.length() != 0 && !description.equalsIgnoreCase(searchFor)) {
/*  58 */           Vector<String> row = new Vector();
/*  59 */           row.add(description);
/*  60 */           result.add(row);
/*     */         }
/*     */       
/*     */       } 
/*  64 */     } catch (Exception e) {
/*  65 */       log.error("getDescription() methode, -exception: " + e.getMessage());
/*     */     } 
/*     */     
/*  68 */     return result;
/*     */   }
/*     */   
/*     */   public Vector getSummaryData() {
/*  72 */     Vector<Vector<String>> result = new Vector();
/*     */     try {
/*  74 */       History select = this.summary.getSelectedSoftware();
/*  75 */       if (select != null) {
/*  76 */         for (int i = 0; i < select.getAttributes().size(); i++) {
/*  77 */           Vector<String> row = new Vector();
/*  78 */           Pair pair = select.getAttributes().get(i);
/*  79 */           row.add(pair.getFirst().toString());
/*  80 */           row.add("");
/*  81 */           row.add(pair.getSecond().toString());
/*  82 */           result.add(row);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  87 */       History current = this.summary.getCurrentSoftware();
/*  88 */       if (current != null) {
/*  89 */         for (int i = 0; i < current.getAttributes().size(); i++) {
/*  90 */           Pair pair = current.getAttributes().get(i);
/*  91 */           String key = pair.getFirst().toString();
/*  92 */           String value = pair.getSecond().toString();
/*  93 */           if (value != null && value.indexOf("#NULL#") >= 0) {
/*  94 */             value = "";
/*     */           }
/*  96 */           int index = isAttributeAdded(key, result);
/*  97 */           if (index == -1) {
/*  98 */             Vector<String> newData = new Vector();
/*  99 */             newData.add(key);
/* 100 */             newData.add(value);
/* 101 */             newData.add("&nbsp;");
/* 102 */             result.add(newData);
/*     */           } else {
/*     */             
/* 105 */             for (int j = 0; j < result.size(); j++) {
/* 106 */               if (index == j) {
/* 107 */                 Vector<String> vector = result.get(j);
/* 108 */                 vector.set(1, value);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 114 */     } catch (Exception e) {
/* 115 */       log.error("getSummaryData() method, -exception: " + e.getMessage());
/*     */     } 
/* 117 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int isAttributeAdded(String attribute, Vector result) {
/*     */     try {
/* 127 */       for (int i = 0; i < result.size(); i++) {
/* 128 */         Object row = result.get(i);
/* 129 */         if (row instanceof Vector && (
/* 130 */           (Vector)row).indexOf(attribute) != -1) {
/* 131 */           return i;
/*     */         }
/*     */       } 
/* 134 */     } catch (Exception e) {
/* 135 */       log.error("isAttributeAdded() method, -exception: " + e.getMessage());
/*     */     } 
/* 137 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\datamodel\SummaryData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */