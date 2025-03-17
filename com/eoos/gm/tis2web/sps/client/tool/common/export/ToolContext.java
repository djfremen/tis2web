/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.export;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ 
/*    */ 
/*    */ public interface ToolContext
/*    */ {
/*    */   public static final String EVENT_TYPE = "EventType";
/*  9 */   public static final Integer REQUEST_EVENT = Integer.valueOf(3);
/* 10 */   public static final Integer GEN_REQUEST_EVENT = Integer.valueOf(5);
/*    */   
/*    */   public static final String PROG_MODE_REPROGRAM = "REPROG";
/*    */   
/*    */   public static final String PROG_MODE_REPLACE_AND_REPROGRAM = "REPLACE";
/*    */   
/*    */   public static final String REPLACE_ECU = "ReplaceECU";
/*    */   
/* 18 */   public static final Integer NO_REPLACE = Integer.valueOf(0);
/* 19 */   public static final Integer REPLACE = Integer.valueOf(1);
/*    */   
/*    */   public static final String SALESORG_BASE = "SalesOrg,";
/*    */   
/*    */   public static final String METHOD_GROUP = "MethodGroup";
/*    */   
/*    */   public static final String ERROR_FLAG = "Error_Not";
/*    */   public static final String DRIVER_PROPERTY_TECH2WIN_COM_PORT = "tech2win_com_port";
/* 27 */   public static final Integer DRIVER_PROPERTY_CATEGORY_TECH2WIN_COM_PORT = Integer.valueOf(1003);
/*    */   
/*    */   Pair getProgrammingMode();
/*    */   
/*    */   void setProgrammingMode(String paramString);
/*    */   
/*    */   Pair getErrorFlag();
/*    */   
/*    */   Pair getSalesOrg();
/*    */   
/*    */   void setSalesOrg(String paramString);
/*    */   
/*    */   Pair getEventType();
/*    */   
/*    */   void setEventTypeValue(Integer paramInteger);
/*    */   
/*    */   Pair getMethodGroup();
/*    */   
/*    */   void setMethodGroupValue(Integer paramInteger);
/*    */   
/*    */   Pair getReplaceInfo();
/*    */   
/*    */   void setReplaceInfo(Integer paramInteger);
/*    */   
/*    */   void setTech2WinComPort(String paramString);
/*    */   
/*    */   Pair getTech2WinComPortProperty();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\ToolContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */