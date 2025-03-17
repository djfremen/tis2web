/*     */ package com.eoos.gm.tis2web.sas.server.implementation.tool.tech2;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sas.client.system.starter.StarterProvider;
/*     */ import com.eoos.gm.tis2web.sas.common.model.AccessType;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKAResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.ToolBridge;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.ToolBridgeImpl;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SCASKAResult;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SSAData;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SSARequestResponseAdapter;
/*     */ import java.io.Serializable;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tool_Tech2Impl
/*     */   implements Tool, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  31 */   private static final Logger log = Logger.getLogger(Tool_Tech2Impl.class);
/*     */   
/*  33 */   private static Tool_Tech2Impl instance = null;
/*     */   
/*  35 */   private final Object SYNC_BRIDGE = new Serializable()
/*     */     {
/*     */       private static final long serialVersionUID = 1L;
/*     */     };
/*     */   
/*  40 */   private ToolBridge bridge = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Tool_Tech2Impl getInstance() {
/*  47 */     if (instance == null) {
/*  48 */       instance = new Tool_Tech2Impl();
/*     */     }
/*  50 */     return instance;
/*     */   }
/*     */   
/*     */   private ToolBridge getBridge() throws Tool.Exception {
/*  54 */     synchronized (this.SYNC_BRIDGE) {
/*  55 */       if (this.bridge == null) {
/*  56 */         throw new IllegalStateException();
/*     */       }
/*     */       
/*  59 */       return this.bridge;
/*     */     } 
/*     */   }
/*     */   
/*     */   private ToolBridge createBridge(Tool.CommunicationSettings settings) throws Tool.Exception {
/*  64 */     synchronized (this.SYNC_BRIDGE) {
/*  65 */       this.bridge = ToolBridgeImpl.getInstance("TECH2");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       String tech2WinComPort = StarterProvider.getInstance().getStarter().getTech2WinComPort();
/*  71 */       log.debug("Set Tech2Win COM port driver property: " + tech2WinComPort);
/*  72 */       if (tech2WinComPort != null && tech2WinComPort.length() > 0) {
/*  73 */         Pair[] properties = new Pair[1];
/*  74 */         String property = "tech2win_com_port," + tech2WinComPort;
/*  75 */         properties[0] = (Pair)new PairImpl(property, Tool.DRIVER_PROPERTY_CATEGORY_TECH2WIN_COM_PORT);
/*  76 */         this.bridge.setDriverProperties(properties);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  85 */       if (this.bridge.initialize((settings != null) ? settings.toString() : "AUTO,115200") == null) {
/*  86 */         this.bridge = null;
/*  87 */         throw new Tool.Exception();
/*     */       } 
/*  89 */       return this.bridge;
/*     */     } 
/*     */   }
/*     */   public SecurityAccessRequest readRequest(Tool.CommunicationSettings communicationSettings) throws Tool.Exception {
/*     */     SSARequestResponseAdapter sSARequestResponseAdapter;
/*  94 */     SecurityAccessRequest retValue = null;
/*  95 */     SSAData ssaData = createBridge(communicationSettings).getSSAData();
/*  96 */     if (ssaData == null) {
/*  97 */       SCASKARequestImpl sCASKARequestImpl = new SCASKARequestImpl();
/*     */     } else {
/*  99 */       sSARequestResponseAdapter = new SSARequestResponseAdapter(ssaData);
/*     */     } 
/* 101 */     log.debug("read request " + String.valueOf(sSARequestResponseAdapter));
/* 102 */     return (SecurityAccessRequest)sSARequestResponseAdapter;
/*     */   }
/*     */   
/*     */   public AccessType[] writeResponse(SecurityAccessResponse response) throws Tool.Exception {
/* 106 */     log.debug("writing reponse " + String.valueOf(response));
/* 107 */     List<AccessType> retValue = new LinkedList();
/* 108 */     if (response instanceof com.eoos.gm.tis2web.sas.common.model.reqres.SSAResponse) {
/* 109 */       if (!(response instanceof SSARequestResponseAdapter)) {
/* 110 */         throw new IllegalStateException("unable to process a facaded response");
/*     */       }
/* 112 */       log.debug("writing ssa response");
/* 113 */       SSAData data = ((SSARequestResponseAdapter)response).getSSAData();
/* 114 */       if (getBridge().setSSAData(data, null)) {
/* 115 */         log.debug("writing successful");
/* 116 */         retValue.add(AccessType.SSA);
/*     */       } else {
/* 118 */         log.debug("writing failed");
/*     */       } 
/*     */     } else {
/*     */       
/* 122 */       log.debug("writing ska/sca");
/* 123 */       SCASKAResult result = getBridge().scaEnableECU(((SCASKAResponse)response).getHardwareKey());
/* 124 */       if (result.getStatusSCA()) {
/* 125 */         log.debug("writing sca successful");
/* 126 */         retValue.add(AccessType.SCA);
/*     */       } 
/* 128 */       if (result.getStatusSKA()) {
/* 129 */         log.debug("writing ska successful");
/* 130 */         retValue.add(AccessType.SKA);
/*     */       } 
/*     */     } 
/* 133 */     if (retValue.size() == 0) {
/* 134 */       throw new Tool.Exception();
/*     */     }
/* 136 */     return retValue.<AccessType>toArray(new AccessType[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDenotation(Locale locale) {
/* 141 */     return "Tech 2";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\tech2\Tool_Tech2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */