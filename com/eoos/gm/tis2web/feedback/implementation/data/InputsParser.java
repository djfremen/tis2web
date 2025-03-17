/*     */ package com.eoos.gm.tis2web.feedback.implementation.data;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputsParser
/*     */ {
/*     */   public static String getFilledForm(String html, Map params, boolean withEmpty) {
/*  12 */     String ret = "";
/*  13 */     ret = setInputs(html, params);
/*  14 */     ret = setSelects(ret, params, withEmpty);
/*  15 */     ret = setTextArea(ret, params);
/*  16 */     return ret;
/*     */   }
/*     */   
/*     */   private static String setInputs(String html, Map<?, ?> params) {
/*  20 */     Map<Object, Object> submit = new HashMap<Object, Object>();
/*  21 */     submit.putAll(params);
/*  22 */     String ret = html;
/*  23 */     String tmp = html;
/*     */     int beginIndex;
/*  25 */     for (beginIndex = tmp.indexOf("<input"); beginIndex != -1; beginIndex = tmp.indexOf("<input", endIndex)) {
/*  26 */       int endIndex = tmp.indexOf(">", beginIndex);
/*  27 */       String InputLine = tmp.substring(beginIndex, endIndex + ">".length());
/*  28 */       int typeInputIndex = InputLine.indexOf("type=\"input\"");
/*  29 */       if (typeInputIndex != -1) {
/*  30 */         int nameIndex = InputLine.indexOf("name=\"");
/*  31 */         if (nameIndex != -1) {
/*  32 */           String Name = InputLine.substring(nameIndex + "name=\"".length(), InputLine.indexOf("\"", nameIndex + "name=\"".length()));
/*  33 */           String value = (String)submit.get(Name);
/*  34 */           String replace = "<input type=\"text\" readonly=\"readonly\" value=\"" + value + "\" size=\"" + value.length() + "\">";
/*  35 */           ret = replace(ret, InputLine, replace);
/*     */         } 
/*     */       } 
/*  38 */       int typeCheckboxIndex = InputLine.indexOf("type=\"checkbox\"");
/*  39 */       if (typeCheckboxIndex != -1) {
/*  40 */         int nameIndex = InputLine.indexOf("name=\"");
/*  41 */         if (nameIndex != -1) {
/*  42 */           String Name = InputLine.substring(nameIndex + "name=\"".length(), InputLine.indexOf("\"", nameIndex + "name=\"".length()));
/*  43 */           String value = (String)submit.get(Name);
/*  44 */           String replace = "";
/*  45 */           if (value == null)
/*  46 */             value = ""; 
/*  47 */           if (value.compareToIgnoreCase("on") == 0) {
/*  48 */             replace = "<input type=\"checkbox\" checked=\"" + value + "\" readonly=\"readonly\" />";
/*     */           } else {
/*  50 */             replace = "<input type=\"checkbox\" readonly=\"readonly\" />";
/*  51 */           }  ret = replace(ret, InputLine, replace);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  56 */     return ret;
/*     */   }
/*     */   
/*     */   private static String setSelects(String html, Map<?, ?> params, boolean withEmpty) {
/*  60 */     Map<Object, Object> submit = new HashMap<Object, Object>();
/*  61 */     submit.putAll(params);
/*  62 */     String ret = html;
/*  63 */     String tmp = html;
/*     */     int beginIndex;
/*  65 */     for (beginIndex = tmp.indexOf("<select"); beginIndex != -1; beginIndex = tmp.indexOf("<select", endIndex)) {
/*  66 */       int endIndex = tmp.indexOf("</select>", beginIndex);
/*  67 */       String InputLine = tmp.substring(beginIndex, endIndex + "</select>".length());
/*  68 */       int nameIndex = InputLine.indexOf("name=\"");
/*  69 */       if (nameIndex != -1) {
/*  70 */         String Name = InputLine.substring(nameIndex + "name=\"".length(), InputLine.indexOf("\"", nameIndex + "name=\"".length()));
/*  71 */         String value = (String)submit.get(Name);
/*  72 */         value = value.trim();
/*  73 */         String Option = getSelectedOption(value, InputLine);
/*  74 */         String strIDandEN = getIdAttributeOfSelectedOption(Option) + "_;_" + getXmlAttributeOfSelectedOption(Option);
/*  75 */         if (value.compareTo("") == 0 && !withEmpty) {
/*  76 */           params.remove(Name);
/*     */         } else {
/*  78 */           params.put(Name, strIDandEN);
/*  79 */         }  String replace = replace(InputLine, Option, "<option selected>" + value + "</option>");
/*  80 */         replace = replace(replace, "<select", "<select disabled=\"disabled\"");
/*  81 */         ret = replace(ret, InputLine, replace);
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return ret;
/*     */   }
/*     */   
/*     */   private static String getSelectedOption(String value, String InputLine) {
/*  89 */     String ret = "<option>";
/*  90 */     String tmp = InputLine;
/*     */     int optionStart;
/*  92 */     for (optionStart = tmp.indexOf("<option"); optionStart != -1; optionStart = tmp.indexOf("<option", optionEnd)) {
/*  93 */       int optionEnd = tmp.indexOf("</option>", optionStart);
/*  94 */       String Option = tmp.substring(optionStart, optionEnd + "</option>".length());
/*  95 */       if (Option.indexOf(value) != -1 && getTagValue("option", Option).compareTo(value) == 0) {
/*  96 */         return Option;
/*     */       }
/*     */     } 
/*  99 */     return ret;
/*     */   }
/*     */   
/*     */   private static String getTagValue(String TagName, String FullTag) {
/* 103 */     String ret = "";
/* 104 */     String Tag = "</" + TagName + ">";
/* 105 */     String tmp = FullTag;
/* 106 */     int optionStart = tmp.indexOf(">");
/* 107 */     if (optionStart != -1) {
/* 108 */       int optionEnd = tmp.indexOf(Tag, optionStart);
/* 109 */       ret = tmp.substring(optionStart + ">".length(), optionEnd);
/*     */     } 
/* 111 */     return ret;
/*     */   }
/*     */   
/*     */   private static String getIdAttributeOfSelectedOption(String Option) {
/* 115 */     String ret = "";
/* 116 */     String tmp = Option;
/* 117 */     int idIdx = tmp.indexOf("ID=\"");
/* 118 */     if (idIdx != -1) {
/* 119 */       int startIdx = idIdx + "ID=\"".length();
/* 120 */       ret = tmp.substring(startIdx, tmp.indexOf("\"", startIdx + 1));
/*     */     } 
/* 122 */     return ret;
/*     */   }
/*     */   
/*     */   private static String getXmlAttributeOfSelectedOption(String Option) {
/* 126 */     String ret = "";
/* 127 */     String tmp = Option;
/* 128 */     int xmlIdx = tmp.indexOf("xml=\"");
/* 129 */     if (xmlIdx != -1) {
/* 130 */       int startIdx = xmlIdx + "xml=\"".length();
/* 131 */       ret = tmp.substring(startIdx, tmp.indexOf("\"", startIdx + 1));
/*     */     } 
/* 133 */     return ret;
/*     */   }
/*     */   
/*     */   private static String setTextArea(String html, Map<?, ?> params) {
/* 137 */     Map<Object, Object> submit = new HashMap<Object, Object>();
/* 138 */     submit.putAll(params);
/* 139 */     String ret = html;
/* 140 */     String tmp = html;
/*     */     int beginIndex;
/* 142 */     for (beginIndex = tmp.indexOf("<textarea"); beginIndex != -1; beginIndex = tmp.indexOf("<textarea", endIndex)) {
/* 143 */       int endIndex = tmp.indexOf("</textarea>", beginIndex);
/* 144 */       String InputLine = tmp.substring(beginIndex, endIndex);
/* 145 */       int nameIndex = InputLine.indexOf("name=\"");
/* 146 */       if (nameIndex != -1) {
/* 147 */         String Name = InputLine.substring(nameIndex + "name=\"".length(), InputLine.indexOf("\"", nameIndex + "name=\"".length()));
/* 148 */         Object tmpObj = submit.get(Name);
/* 149 */         String replace = InputLine + (String)tmpObj;
/* 150 */         replace = replace(replace, "<textarea ", "<textarea readonly=\"readonly\"");
/* 151 */         ret = replace(ret, InputLine, replace);
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     return ret;
/*     */   }
/*     */   
/*     */   private static String replace(String content, String expr, String repl) {
/* 159 */     String ret = content;
/* 160 */     if (content.indexOf(expr) != -1) {
/* 161 */       String before = content.substring(0, content.indexOf(expr));
/* 162 */       String after = content.substring(content.indexOf(expr) + expr.length());
/* 163 */       ret = before + repl + after;
/*     */     } 
/* 165 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementation\data\InputsParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */