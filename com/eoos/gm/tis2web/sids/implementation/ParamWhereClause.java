/*     */ package com.eoos.gm.tis2web.sids.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParamWhereClause
/*     */ {
/*  19 */   private List params = new LinkedList();
/*  20 */   private int aliasNum = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getParams() {
/*  26 */     return this.params;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer getSql() {
/*  33 */     return this.sql;
/*     */   }
/*     */   
/*  36 */   private StringBuffer sql = null;
/*     */ 
/*     */   
/*     */   public void addQMCondition(String name, String value) {
/*  40 */     start();
/*  41 */     addQMConditionNoStart(name, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addQMConditionNoStart(String name, String value) {
/*  47 */     for (int ind = 0; ind < value.length(); ind++) {
/*  48 */       if (ind > 0) {
/*  49 */         this.sql.append(" and");
/*     */       }
/*  51 */       String curChar = value.substring(ind, ind + 1);
/*  52 */       String indStr = Integer.toString(ind + 1);
/*  53 */       String add = " substr( " + name + "," + indStr + ",1)=?";
/*  54 */       this.sql.append("(").append(add).append(" or").append(add).append(")");
/*  55 */       this.params.add(curChar);
/*  56 */       this.params.add("?");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCondition(String name, String value) {
/*  62 */     this.sql.append(" ").append(name).append(" = ?");
/*  63 */     this.params.add(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStarCondition(String name, String value) {
/*  68 */     start();
/*  69 */     this.sql.append(" (");
/*  70 */     addStarLike(name);
/*  71 */     this.sql.append(" or");
/*  72 */     addCondition(name, value);
/*  73 */     this.sql.append(" )");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addQMStarCondition(String name, String value) {
/*  78 */     start();
/*  79 */     this.sql.append(" (");
/*  80 */     addStarLike(name);
/*  81 */     this.sql.append(" or");
/*  82 */     this.sql.append(" (");
/*  83 */     addQMConditionNoStart(name, value);
/*  84 */     this.sql.append(")");
/*  85 */     this.sql.append(")");
/*     */   }
/*     */ 
/*     */   
/*     */   private void addStarLike(String name) {
/*  90 */     this.sql.append(" ").append(name).append(" = '*'");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void start() {
/*  96 */     if (this.sql == null) {
/*  97 */       this.sql = new StringBuffer(" where");
/*     */     } else {
/*  99 */       this.sql.append(" and");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addLocaleCondition(String table, String column, String locAlias, Locale locale) {
/* 104 */     String locString = locale.getLanguage();
/* 105 */     if (locale.getCountry() != "")
/* 106 */       locString = locString + "_" + locale.getCountry(); 
/* 107 */     this.sql.append(" and");
/* 108 */     openBrace();
/* 109 */     addCondition(locAlias + ".Locale", locString);
/*     */     
/* 111 */     List<String> localeList = new ArrayList(3);
/* 112 */     localeList.add(locString);
/* 113 */     if (locale.getCountry() == "") {
/* 114 */       localeList.add("en");
/* 115 */       addLocaleCondition(table, column, locAlias, localeList);
/*     */     } else {
/*     */       
/* 118 */       localeList.add(locale.getLanguage());
/* 119 */       addLocaleCondition(table, column, locAlias, localeList);
/* 120 */       localeList.add("en");
/* 121 */       addLocaleCondition(table, column, locAlias, localeList);
/*     */     } 
/*     */ 
/*     */     
/* 125 */     closeBrace();
/*     */   }
/*     */ 
/*     */   
/*     */   public void openBrace() {
/* 130 */     this.sql.append(" (");
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeBrace() {
/* 135 */     this.sql.append(")");
/*     */   }
/*     */ 
/*     */   
/*     */   private void addLocaleCondition(String table, String column, String locAlias, List locales) {
/* 140 */     String addCond = new String((locales.size() > 2) ? "or {g}.Locale = ? " : "");
/* 141 */     StringBuffer cond = new StringBuffer(" or (not exists (select *  from {table} {g}  where b.{column}={g}.{column} and ({g}.Locale = ? {addCond})) and {locAlias}.Locale=?) ");
/* 142 */     StringUtilities.replace(cond, "{addCond}", addCond);
/* 143 */     StringUtilities.replace(cond, "{table}", table);
/* 144 */     StringUtilities.replace(cond, "{column}", column);
/* 145 */     StringUtilities.replace(cond, "{locAlias}", locAlias);
/* 146 */     StringUtilities.replace(cond, "{g}", nextAlias());
/* 147 */     this.sql.append(cond);
/* 148 */     this.params.addAll(locales);
/*     */   }
/*     */ 
/*     */   
/*     */   private String nextAlias() {
/* 153 */     return "lc" + Integer.toString(this.aliasNum++);
/*     */   }
/*     */   
/*     */   public void addLikeOrNullCondition(IDatabaseLink db, String name, String value) {
/* 157 */     start();
/* 158 */     this.sql.append(" (");
/* 159 */     if (db.getDBMS() == 2) {
/* 160 */       this.sql.append("( ? like REPLACE('?' BY '_' IN REPLACE('*' BY '%' IN TRIM(").append(name).append("))) )");
/*     */     } else {
/* 162 */       this.sql.append("( ? like Replace(Replace(trim(").append(name).append("),'*','%'),'?','_') )");
/*     */     } 
/* 164 */     this.params.add(value);
/* 165 */     this.sql.append(" or (").append(name).append(" is null )");
/* 166 */     this.sql.append(" )");
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\ParamWhereClause.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */