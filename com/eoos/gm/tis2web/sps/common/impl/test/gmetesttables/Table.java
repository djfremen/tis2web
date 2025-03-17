/*     */ package com.eoos.gm.tis2web.sps.common.impl.test.gmetesttables;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Table
/*     */ {
/*  16 */   private static Logger log = Logger.getLogger(Table.class);
/*     */   
/*     */   private Connection con;
/*     */   private String name;
/*     */   
/*     */   public Table(Connection con, String name) {
/*  22 */     this.con = con;
/*  23 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean create(String cols) {
/*  30 */     boolean ret = false;
/*  31 */     Statement stat = null;
/*  32 */     drop();
/*     */     try {
/*  34 */       stat = this.con.createStatement();
/*  35 */       String sql = "create table " + this.name + " (" + cols + ")";
/*  36 */       stat.execute(sql);
/*  37 */       ret = true;
/*  38 */       log.info(sql);
/*  39 */     } catch (Exception e) {
/*  40 */       log.error(e, e);
/*     */     } 
/*     */ 
/*     */     
/*  44 */     if (stat != null) {
/*     */       try {
/*  46 */         stat.close();
/*  47 */       } catch (SQLException e1) {
/*  48 */         log.error(e1, e1);
/*     */       } 
/*     */     }
/*  51 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean drop() {
/*  55 */     boolean ret = false;
/*  56 */     Statement stat = null;
/*     */     try {
/*  58 */       stat = this.con.createStatement();
/*  59 */       String sql = "drop table " + this.name;
/*  60 */       stat.execute(sql);
/*  61 */       ret = true;
/*  62 */       log.info(sql);
/*  63 */     } catch (Exception e) {
/*  64 */       log.error(e, e);
/*     */     } 
/*     */ 
/*     */     
/*  68 */     if (stat != null) {
/*     */       try {
/*  70 */         stat.close();
/*  71 */       } catch (SQLException e1) {
/*  72 */         log.error(e1, e1);
/*     */       } 
/*     */     }
/*  75 */     return ret;
/*     */   }
/*     */   
/*     */   public void insert(InsStr cols) {
/*  79 */     insert(cols.get());
/*     */   }
/*     */   
/*     */   public void insertSelect(InsStr cols) {
/*  83 */     insertSelect(cols.get());
/*     */   }
/*     */   
/*     */   public void insert(String cols) {
/*  87 */     Statement stat = null;
/*     */     try {
/*  89 */       stat = this.con.createStatement();
/*  90 */       String sql = "insert into " + this.name + " values (" + cols + ")";
/*  91 */       stat.execute(sql);
/*  92 */       log.info(sql);
/*  93 */     } catch (SQLException e) {
/*  94 */       log.error(e, e);
/*     */     } 
/*  96 */     if (stat != null) {
/*     */       try {
/*  98 */         stat.close();
/*  99 */       } catch (SQLException e1) {
/* 100 */         log.error(e1, e1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertSelect(String cols) {
/* 107 */     Statement stat = null;
/*     */     try {
/* 109 */       stat = this.con.createStatement();
/* 110 */       String sql = "insert into " + this.name + " select " + cols;
/* 111 */       stat.execute(sql);
/* 112 */       log.info(sql);
/* 113 */     } catch (SQLException e) {
/* 114 */       log.error(e, e);
/*     */     } 
/* 116 */     if (stat != null)
/*     */       try {
/* 118 */         stat.close();
/* 119 */       } catch (SQLException e1) {
/* 120 */         log.error(e1, e1);
/*     */       }  
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\test\gmetesttables\Table.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */