/*    */ package com.eoos.gm.tis2web.swdl.client.msg;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SDEvent
/*    */   implements Notification
/*    */ {
/*    */   public static final long DISPLAY_TITLE = 1L;
/*    */   public static final long DISPLAY_MSG = 2L;
/*    */   public static final long BUTTON_PANEL__BTN_PRESSED = 3L;
/*    */   public static final long BUTTON_PANEL__ENABLE_BTN = 4L;
/*    */   public static final long BUTTON_PANEL__SHOW_ADD_BTNS = 5L;
/*    */   public static final long DRIVER_SELECTION = 6L;
/*    */   public static final long SHOW_WAIT_DLG = 7L;
/*    */   public static final long CHANGE_IMAGE = 8L;
/*    */   public static final long START_DOWNLOAD = 9L;
/*    */   public static final long BUTTON_PANEL_ENABLE = 10L;
/*    */   public static final long DOWNLOAD_COMPLETE = 11L;
/*    */   public static final long BUTTON_CHANGE_NAME = 12L;
/*    */   public static final long SRV_DOWN_PROGRESS = 13L;
/*    */   public static final long SHOW_SETUPINSTR_DLG = 14L;
/*    */   public static final long SWITCH_VIEW = 15L;
/*    */   public static final long INIT = 16L;
/*    */   public static final long TEST_DEVICE = 17L;
/*    */   public static final long SAVE_DEV_SETTINGS = 18L;
/*    */   public static final long SAVE_LOG_SETTINGS = 19L;
/*    */   public static final long UPDATE_SETTINGS = 20L;
/*    */   public static final long INIT_COM_PORTS = 22L;
/*    */   public static final long DOWNLOAD = 23L;
/*    */   public static final long START_SERVER_DOWN = 24L;
/*    */   public static final long START_SERVER_DOWNLOAD = 25L;
/*    */   public static final long START_FILE_SERVER_DOWNLOAD = 26L;
/*    */   public static final long PROGRESS_FILE_SERVER_DOWNLOAD = 27L;
/*    */   public static final long END_FILE_SERVER_DOWNLOAD = 28L;
/*    */   public static final long END_SERVER_DOWNLOAD = 29L;
/*    */   public static final long CHANGE_MSG_WAIT_DLG = 30L;
/*    */   public static final long DELETE_SWDL_CACHE = 31L;
/* 47 */   private long type = 0L;
/* 48 */   private List params = new ArrayList();
/*    */   
/*    */   public SDEvent(long type) {
/* 51 */     this.type = type;
/*    */   }
/*    */   
/*    */   public SDEvent(long type, Object param) {
/* 55 */     this.type = type;
/* 56 */     this.params.add(param);
/*    */   }
/*    */   
/*    */   public SDEvent(long type, Object param1, Object param2) {
/* 60 */     this.type = type;
/* 61 */     this.params.add(param1);
/* 62 */     this.params.add(param2);
/*    */   }
/*    */   
/*    */   public SDEvent(long type, Object param1, Object param2, Object param3) {
/* 66 */     this.type = type;
/* 67 */     this.params.add(param1);
/* 68 */     this.params.add(param2);
/* 69 */     this.params.add(param3);
/*    */   }
/*    */   
/*    */   public SDEvent(long type, Object param1, Object param2, Object param3, Object param4) {
/* 73 */     this.type = type;
/* 74 */     this.params.add(param1);
/* 75 */     this.params.add(param2);
/* 76 */     this.params.add(param3);
/* 77 */     this.params.add(param4);
/*    */   }
/*    */   
/*    */   public void addParam(Object param) {
/* 81 */     this.params.add(param);
/*    */   }
/*    */   
/*    */   public long getType() {
/* 85 */     return this.type;
/*    */   }
/*    */   
/*    */   public Object getParam(int i) {
/* 89 */     if (this.params.size() > i) {
/* 90 */       return this.params.get(i);
/*    */     }
/* 92 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\msg\SDEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */