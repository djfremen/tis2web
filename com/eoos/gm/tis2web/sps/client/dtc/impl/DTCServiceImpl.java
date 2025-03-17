/*     */ package com.eoos.gm.tis2web.sps.client.dtc.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.dtc.IDTCService;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.passthru.common.PtTool;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ISPSTool;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DTCServiceImpl
/*     */   implements IDTCService
/*     */ {
/*  24 */   protected static Logger log = Logger.getLogger(DTCServiceImpl.class);
/*     */   
/*     */   public static final int PROTOCOL_CLASS2 = 1;
/*     */   
/*     */   public static final int PROTOCOL_GMLAN = 3;
/*     */   
/*     */   protected boolean clearDTC = false;
/*     */   
/*  32 */   protected Integer wLinkInfo = null;
/*     */   
/*  34 */   private static DTCServiceImpl instance = null;
/*     */   
/*  36 */   protected AttributeValueMap data = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAlreadyClearDTCsExecuted = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public static DTCServiceImpl getInstance() {
/*  45 */     if (instance == null) {
/*  46 */       instance = new DTCServiceImpl();
/*     */     }
/*  48 */     return instance;
/*     */   }
/*     */   
/*     */   public boolean isAutomaticallyClearDTCsMode() {
/*  52 */     return this.clearDTC;
/*     */   }
/*     */   
/*     */   public boolean isAlreadyClearDTCsExecuted() {
/*  56 */     return this.isAlreadyClearDTCsExecuted;
/*     */   }
/*     */   
/*     */   protected void setClearDTCs(boolean isClearDTC) {
/*  60 */     this.clearDTC = isClearDTC;
/*     */   }
/*     */   
/*     */   protected boolean isPassThru(AttributeValueMap data) throws Exception {
/*  64 */     String tool = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/*  65 */     if (tool == null || (!tool.equals("T2_REMOTE") && !tool.equals("TEST_DRIVER")))
/*  66 */       return true; 
/*  67 */     if (tool.equals("TEST_DRIVER")) {
/*  68 */       String dtype = (String)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_TYPE);
/*  69 */       if (dtype != null && (dtype.equalsIgnoreCase("pass-thru") || dtype.equalsIgnoreCase("J2534"))) {
/*  70 */         return true;
/*     */       }
/*     */     } 
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   protected Tool getTool() {
/*  77 */     Value value = this.data.getValue(CommonAttribute.TOOL);
/*  78 */     if (value != null && value instanceof ValueAdapter) {
/*  79 */       return (Tool)((ValueAdapter)value).getAdaptee();
/*     */     }
/*  81 */     return null;
/*     */   }
/*     */   
/*     */   public Integer getVehicleLink() {
/*  85 */     return this.wLinkInfo;
/*     */   }
/*     */   
/*     */   public void evaluateVIT1ClearDTC(VIT1 vit1, AttributeValueMap data) throws Exception {
/*  89 */     if (!isPassThru(data))
/*     */       return; 
/*  91 */     this.isAlreadyClearDTCsExecuted = false;
/*  92 */     this.data = data;
/*  93 */     if (data.getValue(CommonAttribute.CLEAR_DTCS) == CommonValue.OK) {
/*  94 */       log.debug("server-side: clear DTC enabled");
/*  95 */       setClearDTCs(true);
/*  96 */     } else if (data.getValue(CommonAttribute.CLEAR_DTCS) == CommonValue.DISABLE) {
/*     */       return;
/*     */     } 
/*  99 */     this.wLinkInfo = (Integer)AVUtil.accessValue(data, CommonAttribute.VEHICLE_LINK);
/* 100 */     log.debug("server-side: vehicle link=" + this.wLinkInfo);
/*     */     
/* 102 */     List deviceList = (List)AVUtil.accessValue(data, CommonAttribute.VIT1_DEVICE_LIST);
/* 103 */     if (deviceList != null) {
/* 104 */       Iterator<Integer> it = deviceList.iterator();
/* 105 */       while (it.hasNext()) {
/* 106 */         Integer device = it.next();
/* 107 */         VIT vit = vit1.getControlModuleBlock(device);
/* 108 */         if (vit == null)
/*     */           continue; 
/* 110 */         int protocol = Integer.valueOf(vit.getAttrValue("protocol")).intValue();
/* 111 */         if ((protocol == 1 || protocol == 3) && 
/* 112 */           !isAutomaticallyClearDTCsMode()) {
/* 113 */           log.debug("client-side: clear DTC enabled");
/* 114 */           setClearDTCs(true);
/*     */         } 
/*     */ 
/*     */         
/* 118 */         if (protocol == 3) {
/* 119 */           if (this.wLinkInfo == null) {
/* 120 */             this.wLinkInfo = Integer.valueOf(vit.getAttrValue("pinnum"));
/* 121 */             log.debug("client-side: vehicle link=" + this.wLinkInfo);
/*     */           } 
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void executeClearDTCs() {
/* 131 */     Tool tool = getTool();
/* 132 */     if (tool == null)
/* 133 */       log.debug("no selected tool"); 
/* 134 */     if (tool instanceof PtTool) {
/* 135 */       if (this.isAlreadyClearDTCsExecuted) {
/*     */         return;
/*     */       }
/*     */       
/* 139 */       ISPSTool spsTool = ((PtTool)tool).getSPSTool();
/* 140 */       if (spsTool != null) {
/* 141 */         boolean result = spsTool.clearVehicleDTCs(getVehicleLink());
/* 142 */         log.debug("sps client-side:execute clear DTCs for vehicle link=" + getVehicleLink());
/* 143 */         log.debug("sps client-side:result clear DTCs=" + result);
/* 144 */         this.isAlreadyClearDTCsExecuted = true;
/*     */       }
/*     */     
/* 147 */     } else if (((String)tool.getType().getAdaptee()).equals("TEST_DRIVER")) {
/* 148 */       if (this.isAlreadyClearDTCsExecuted) {
/*     */         return;
/*     */       }
/*     */       
/* 152 */       this.isAlreadyClearDTCsExecuted = true;
/* 153 */       log.debug("no clearDTCs functionallity for Test_Driver");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\dtc\impl\DTCServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */