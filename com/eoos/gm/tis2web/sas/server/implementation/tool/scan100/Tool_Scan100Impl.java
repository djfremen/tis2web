/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.scan100;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.AccessType;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKAResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.tool.ToolBridge;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.tool.ToolBridgeImpl;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SCASKAResult;
/*    */ import java.io.Serializable;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tool_Scan100Impl
/*    */   implements Tool, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 25 */   private static final Logger log = Logger.getLogger(Tool_Scan100Impl.class);
/*    */   
/* 27 */   private static Tool_Scan100Impl instance = null;
/*    */   
/* 29 */   private final Object SYNC_BRIDGE = new Serializable()
/*    */     {
/*    */       private static final long serialVersionUID = 1L;
/*    */     };
/*    */   
/* 34 */   private ToolBridge bridge = null;
/*    */   
/*    */   private Tool.CommunicationSettings communicationSettings;
/*    */ 
/*    */   
/*    */   public static synchronized Tool_Scan100Impl getInstance() {
/* 40 */     if (instance == null) {
/* 41 */       instance = new Tool_Scan100Impl();
/*    */     }
/* 43 */     return instance;
/*    */   }
/*    */   
/*    */   private ToolBridge createBridge(Tool.CommunicationSettings settings) throws Tool.Exception {
/* 47 */     synchronized (this.SYNC_BRIDGE) {
/* 48 */       if (this.bridge == null) {
/* 49 */         this.bridge = ToolBridgeImpl.getInstance("SCAN100");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 57 */         if (this.bridge.initialize((settings != null) ? settings.toString() : "AUTO,115200") == null) {
/* 58 */           this.bridge = null;
/* 59 */           throw new Tool.Exception();
/*    */         } 
/*    */       } 
/* 62 */       return this.bridge;
/*    */     } 
/*    */   }
/*    */   private Tool_Scan100Impl() {
/* 66 */     this.communicationSettings = null;
/*    */   }
/*    */   public SecurityAccessRequest readRequest(Tool.CommunicationSettings communicationSettings) throws Tool.Exception {
/* 69 */     this.communicationSettings = communicationSettings;
/* 70 */     SecurityAccessRequest retValue = null;
/*    */     
/* 72 */     SCASKARequestImpl sCASKARequestImpl = new SCASKARequestImpl();
/* 73 */     log.debug("read request " + String.valueOf(sCASKARequestImpl));
/* 74 */     return (SecurityAccessRequest)sCASKARequestImpl;
/*    */   }
/*    */   
/*    */   public AccessType[] writeResponse(SecurityAccessResponse response) throws Tool.Exception {
/* 78 */     log.debug("writing reponse " + String.valueOf(response));
/* 79 */     List<AccessType> retValue = new LinkedList();
/* 80 */     log.debug("writing ska/sca");
/* 81 */     SCASKAResult result = createBridge(this.communicationSettings).scaEnableECU(((SCASKAResponse)response).getHardwareKey());
/* 82 */     if (result.getStatusSCA()) {
/* 83 */       log.debug("writing sca successful");
/* 84 */       retValue.add(AccessType.SCA);
/*    */     } 
/* 86 */     if (result.getStatusSKA()) {
/* 87 */       log.debug("writing ska successful");
/* 88 */       retValue.add(AccessType.SKA);
/*    */     } 
/* 90 */     if (retValue.size() == 0) {
/* 91 */       throw new Tool.Exception();
/*    */     }
/* 93 */     return retValue.<AccessType>toArray(new AccessType[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 98 */     return "Scan 100";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\scan100\Tool_Scan100Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */