/*    */ package com.eoos.gm.tis2web.frame.diag.client.logconf;
/*    */ 
/*    */ public class AppParamLogConfiguration
/*    */ {
/*  5 */   private final int _SEQUENTIAL = 0;
/*  6 */   private final int _RECURSIVE = 1;
/*    */   
/*    */   private String _dirMode;
/*    */   private String _dirBase;
/*    */   private String _dirPattern;
/*    */   private String _filePattern;
/*    */   
/*    */   public String get_dirMode() {
/* 14 */     return this._dirMode;
/*    */   }
/*    */   
/*    */   public int get_dirModeAsInt() {
/* 18 */     return this._dirMode.equalsIgnoreCase("sequential") ? 0 : 1;
/*    */   }
/*    */   
/*    */   public void set_dirMode(String mode) {
/* 22 */     this._dirMode = mode;
/*    */   }
/*    */   
/*    */   public String get_dirBase() {
/* 26 */     return this._dirBase;
/*    */   }
/*    */   
/*    */   public void set_dirBase(String base) {
/* 30 */     this._dirBase = (base != null) ? base.trim() : null;
/*    */   }
/*    */   
/*    */   public String get_dirPattern() {
/* 34 */     return this._dirPattern;
/*    */   }
/*    */   
/*    */   public void set_dirPattern(String pattern) {
/* 38 */     this._dirPattern = (pattern != null) ? pattern.trim() : null;
/*    */   }
/*    */   
/*    */   public String get_filePattern() {
/* 42 */     return this._filePattern;
/*    */   }
/*    */   
/*    */   public void set_filePattern(String pattern) {
/* 46 */     this._filePattern = (pattern != null) ? pattern.trim() : null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\logconf\AppParamLogConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */