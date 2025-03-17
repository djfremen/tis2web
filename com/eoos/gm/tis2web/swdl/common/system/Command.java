/*    */ package com.eoos.gm.tis2web.swdl.common.system;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Hashtable;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Command
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int CID_GET_DEVICES = 1;
/*    */   public static final int CID_GET_APPLICATIONS = 2;
/*    */   public static final int CID_GET_FILESET = 3;
/*    */   public static final int CID_GET_FILE = 4;
/*    */   public static final int CID_GET_NEWEST_VERSION = 5;
/*    */   public static final int CID_SAVE_TROUBLECODE = 6;
/*    */   public static final int CID_GET_UI_LANGUAGE = 7;
/*    */   public static final int CID_GET_DEALERCODE = 8;
/*    */   public static final int CID_GET_VERSION = 9;
/*    */   public static final int CID_GET_OBJ_LANGUAGE = 10;
/*    */   public static final int CID_NOTIFY_TECH_DOWNLOAD = 11;
/*    */   public static final int CID_GET_NEW_VERSION = 12;
/*    */   public static final int CID_LOGOUT = 13;
/*    */   public static final String KEY_PARAM_DEVICE = "device";
/*    */   public static final String KEY_PARAM_APPLICATIONID = "applicationid";
/*    */   public static final String KEY_PARAM_APPLICATIONDESC = "applicationdesc";
/*    */   public static final String KEY_PARAM_FILEID = "fileid";
/*    */   public static final String KEY_PARAM_TROUBLECODE = "troublecode";
/*    */   public static final String KEY_PARAM_LANGUAGEID = "languageid";
/*    */   public static final String KEY_PARAM_VERSIONNO = "versionno";
/*    */   public static final String KEY_PARAM_SESSIONID = "sessionid";
/*    */   public static final String KEY_PARAM_DEBUG = "debugsession";
/*    */   public static final String KEY_PARAM_DEVICENAME = "devicename";
/*    */   private int commandID;
/* 63 */   private Map parameters = new Hashtable<Object, Object>();
/*    */ 
/*    */   
/*    */   public Command(int commandID) {
/* 67 */     this.commandID = commandID;
/*    */   }
/*    */   
/*    */   public int getCommandID() {
/* 71 */     return this.commandID;
/*    */   }
/*    */   
/*    */   public Object getParameter(String key) {
/* 75 */     return this.parameters.get(key);
/*    */   }
/*    */   
/*    */   public void addParameter(String key, Object param) {
/* 79 */     this.parameters.put(key, param);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\system\Command.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */