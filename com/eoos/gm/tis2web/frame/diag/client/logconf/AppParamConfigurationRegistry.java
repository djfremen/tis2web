/*    */ package com.eoos.gm.tis2web.frame.diag.client.logconf;
/*    */ 
/*    */ public class AppParamConfigurationRegistry
/*    */   extends AppParamConfigurations {
/*    */   private String[] _keyPath;
/*    */   private String _keyType;
/*    */   private String _keyName;
/*    */   private String _keyRemoveText;
/*    */   private int _keyRemoveLevel;
/*    */   
/*    */   public String[] get_keyPath() {
/* 12 */     return this._keyPath;
/*    */   }
/*    */   
/*    */   public void set_keyPath(String[] path) {
/* 16 */     this._keyPath = path;
/*    */   }
/*    */   
/*    */   public String get_keyPathAsString() {
/* 20 */     if (this._keyPath == null || this._keyPath.length == 0)
/* 21 */       return null; 
/* 22 */     String sRet = "";
/* 23 */     for (int i = 0; i < this._keyPath.length; i++) {
/* 24 */       sRet = sRet + this._keyPath[i] + "\\";
/*    */     }
/* 26 */     sRet = sRet.substring(0, sRet.lastIndexOf("\\"));
/* 27 */     return sRet;
/*    */   }
/*    */   
/*    */   public void set_keyPath(String path) {
/* 31 */     this._keyPath = path.split("\\\\");
/*    */   }
/*    */   
/*    */   public String get_keyType() {
/* 35 */     return this._keyType;
/*    */   }
/*    */   
/*    */   public void set_keyType(String type) {
/* 39 */     this._keyType = type;
/*    */   }
/*    */   
/*    */   public String get_keyName() {
/* 43 */     return this._keyName;
/*    */   }
/*    */   
/*    */   public void set_keyName(String name) {
/* 47 */     this._keyName = name;
/*    */   }
/*    */   
/*    */   public String get_keyRemoveText() {
/* 51 */     return this._keyRemoveText;
/*    */   }
/*    */   
/*    */   public void set_keyRemoveText(String removeText) {
/* 55 */     this._keyRemoveText = removeText;
/*    */   }
/*    */   
/*    */   public int get_keyRemoveLevel() {
/* 59 */     return this._keyRemoveLevel;
/*    */   }
/*    */   
/*    */   public void set_keyRemoveLevel(int removeLevel) {
/* 63 */     this._keyRemoveLevel = removeLevel;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\client\logconf\AppParamConfigurationRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */