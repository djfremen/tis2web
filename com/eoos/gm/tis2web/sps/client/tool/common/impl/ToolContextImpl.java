/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolContext;
/*    */ 
/*    */ public class ToolContextImpl
/*    */   implements ToolContext {
/*    */   private String programmingMode;
/* 10 */   private Integer programmingModeValue = Integer.valueOf(4);
/* 11 */   private Integer errorFlagValue = Integer.valueOf(0);
/*    */   private Integer eventTypeValue;
/*    */   private String salesOrg;
/* 14 */   private Integer salesOrgValue = Integer.valueOf(0);
/*    */   private Integer methodGroupValue;
/* 16 */   private Integer replaceInfoValue = Integer.valueOf(0);
/*    */   private String comPort;
/*    */   
/*    */   public Pair getProgrammingMode() {
/* 20 */     return (Pair)new PairImpl(this.programmingMode, this.programmingModeValue);
/*    */   }
/*    */   
/*    */   public void setProgrammingMode(String mode) {
/* 24 */     this.programmingMode = mode;
/*    */     
/* 26 */     if (mode.compareToIgnoreCase("REPLACE") == 0) {
/* 27 */       this.replaceInfoValue = REPLACE;
/*    */     }
/*    */   }
/*    */   
/*    */   public Pair getErrorFlag() {
/* 32 */     return (Pair)new PairImpl("Error_Not", this.errorFlagValue);
/*    */   }
/*    */   
/*    */   public Pair getSalesOrg() {
/* 36 */     return (Pair)new PairImpl("SalesOrg," + this.salesOrg, this.salesOrgValue);
/*    */   }
/*    */   
/*    */   public void setSalesOrg(String salesOrg) {
/* 40 */     this.salesOrg = salesOrg;
/*    */   }
/*    */   
/*    */   public Pair getEventType() {
/* 44 */     return (Pair)new PairImpl("EventType", this.eventTypeValue);
/*    */   }
/*    */   
/*    */   public void setEventTypeValue(Integer value) {
/* 48 */     this.eventTypeValue = value;
/*    */   }
/*    */   
/*    */   public Pair getMethodGroup() {
/* 52 */     return (Pair)new PairImpl("MethodGroup", this.methodGroupValue);
/*    */   }
/*    */   
/*    */   public void setMethodGroupValue(Integer methodGroup) {
/* 56 */     this.methodGroupValue = methodGroup;
/*    */   }
/*    */   
/*    */   public Pair getReplaceInfo() {
/* 60 */     return (Pair)new PairImpl("ReplaceECU", this.replaceInfoValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setReplaceInfo(Integer value) {
/* 65 */     this.replaceInfoValue = value;
/*    */   }
/*    */   
/*    */   public void setTech2WinComPort(String comPort) {
/* 69 */     this.comPort = comPort;
/*    */   }
/*    */   
/*    */   public Pair getTech2WinComPortProperty() {
/* 73 */     if (this.comPort != null && this.comPort.length() > 0) {
/* 74 */       return (Pair)new PairImpl("tech2win_com_port," + this.comPort, DRIVER_PROPERTY_CATEGORY_TECH2WIN_COM_PORT);
/*    */     }
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\ToolContextImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */