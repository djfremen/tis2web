/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ 
/*    */ public class CommonValue
/*    */ {
/*  8 */   public static final Value JAVA_CLIENT = ConstantValueImpl.getInstance("sps.value.java-client");
/*  9 */   public static final Value HTML_CLIENT = ConstantValueImpl.getInstance("sps.value.html-client");
/*    */ 
/*    */   
/* 12 */   public static final Value TEST_DRIVER = ConstantValueImpl.getInstance("TEST_DRIVER");
/* 13 */   public static final Value J2534_TOOL = ConstantValueImpl.getInstance("PT_J2534");
/*    */ 
/*    */ 
/*    */   
/* 17 */   public static final Value TECH2_REMOTE = ConstantValueImpl.getInstance("T2_REMOTE");
/*    */ 
/*    */   
/* 20 */   public static final Value REPROGRAM = (Value)DisplayableValueImpl.getInstance("sps.value.reprogram-ecu");
/* 21 */   public static final Value REPLACE_AND_REPROGRAM = (Value)DisplayableValueImpl.getInstance("sps.value.replace-and-program-ecu");
/*    */ 
/*    */   
/* 24 */   public static final Value NORMAL = (Value)DisplayableValueImpl.getInstance("sps.value.normal");
/* 25 */   public static final Value VCI = (Value)DisplayableValueImpl.getInstance("sps.value.vci");
/*    */   
/* 27 */   public static final Value OK = ValueImpl.getInstance("ok");
/* 28 */   public static final Value FAIL = ValueImpl.getInstance("fail");
/*    */   
/* 30 */   public static final Value EXECUTION_MODE_INFO = ValueImpl.getInstance("sps.value.execution.info");
/* 31 */   public static final Value EXECUTION_MODE_REPROGRAM = ValueImpl.getInstance("sps.value.execution.reprogram");
/*    */   
/* 33 */   public static final Value DISABLE = ValueImpl.getInstance("sps.disable");
/* 34 */   public static final Value ENABLE = ValueImpl.getInstance("sps.enable");
/*    */   
/* 36 */   public static final Value SPS_GME = ValueImpl.getInstance("sps.gme");
/* 37 */   public static final Value SPS_NAO = ValueImpl.getInstance("sps.nao");
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\CommonValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */