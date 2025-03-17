/*     */ package com.eoos.gm.tis2web.swdl.client.util;
/*     */ 
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class MessageFormater
/*     */ {
/*     */   public static String FormatMsg(String message, String[] params) {
/*  25 */     if (message == null) {
/*  26 */       return "";
/*     */     }
/*  28 */     String msg = new String(message);
/*     */     try {
/*  30 */       if (params != null) {
/*  31 */         StringBuffer msgBuf = new StringBuffer(msg);
/*  32 */         for (int i = 0; i < params.length; i++) {
/*  33 */           int indx = msg.indexOf("{%s}");
/*  34 */           if (indx == -1)
/*     */             break; 
/*  36 */           msgBuf = msgBuf.replace(indx, indx + 4, params[i]);
/*  37 */           msg = msgBuf.toString();
/*     */         } 
/*     */       } 
/*  40 */     } catch (Exception e) {}
/*     */     
/*  42 */     return msg;
/*     */   }
/*     */   
/*     */   public static String FormatMsgBR(String message, int charNr) {
/*  46 */     StringBuffer buf = new StringBuffer("");
/*  47 */     if (message != null) {
/*  48 */       buf.append("<html>");
/*  49 */       buf.append(StringUtilities.replace(formatString(message, charNr), '\n', "<br>"));
/*  50 */       buf.append("</html>");
/*     */     } 
/*  52 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private static String formatString(String str, int maxChar) {
/*  56 */     StringBuffer result = new StringBuffer(str);
/*  57 */     if (maxChar > 0) {
/*  58 */       List list = str2List(result.toString(), maxChar);
/*  59 */       result.replace(0, result.length(), list2Str(list, maxChar));
/*     */     } 
/*  61 */     return result.toString();
/*     */   }
/*     */   
/*     */   private static List str2List(String str, int maxChar) {
/*  65 */     List<String> list = new ArrayList();
/*  66 */     StringBuffer buffer = new StringBuffer("");
/*  67 */     for (int index = 0; index < str.length(); index++) {
/*  68 */       if (index + 1 < str.length() && str.charAt(index + 1) == '\n') {
/*  69 */         buffer.append(str.charAt(index));
/*  70 */         list.add(new String(buffer.toString()));
/*  71 */         buffer.delete(0, buffer.length());
/*     */       } else {
/*  73 */         buffer.append(str.charAt(index));
/*  74 */         if (str.charAt(index) == ' ' || str.charAt(index) == '\n') {
/*  75 */           list.add(new String(buffer.toString()));
/*  76 */           buffer.delete(0, buffer.length());
/*     */         } 
/*     */       } 
/*     */     } 
/*  80 */     if (buffer.length() > 0) {
/*  81 */       list.add(new String(buffer.toString()));
/*     */     }
/*  83 */     return list;
/*     */   }
/*     */   
/*     */   private static String list2Str(List list, int maxChar) {
/*  87 */     StringBuffer result = new StringBuffer();
/*  88 */     StringBuffer line = new StringBuffer();
/*  89 */     Iterator<String> it = list.iterator();
/*  90 */     while (it.hasNext()) {
/*  91 */       String word = it.next();
/*  92 */       if (line.length() + word.length() < maxChar) {
/*  93 */         if (word.indexOf('\n') == -1) {
/*  94 */           line.append(word); continue;
/*     */         } 
/*  96 */         line.append(word);
/*  97 */         result.append(line.toString());
/*  98 */         line.delete(0, line.length());
/*     */         continue;
/*     */       } 
/* 101 */       result.append(line.append('\n').toString());
/* 102 */       line.replace(0, line.length(), word);
/*     */     } 
/*     */     
/* 105 */     if (line.length() > 0) {
/* 106 */       result.append(line.toString());
/*     */     }
/* 108 */     return result.toString();
/*     */   }
/*     */   
/*     */   public static String FormatCR2BR(String message) {
/* 112 */     StringBuffer buf = new StringBuffer("");
/* 113 */     if (message != null) {
/* 114 */       buf.append("<html>");
/* 115 */       buf.append(StringUtilities.replace(message, '\n', "<br>"));
/* 116 */       buf.append("</html>");
/*     */     } 
/* 118 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\util\MessageFormater.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */