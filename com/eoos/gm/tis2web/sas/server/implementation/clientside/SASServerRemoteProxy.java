/*     */ package com.eoos.gm.tis2web.sas.server.implementation.clientside;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.sas.client.hwk.HWKProviderFacade;
/*     */ import com.eoos.gm.tis2web.sas.client.system.ServerTaskExecution;
/*     */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sas.common.model.SalesOrganisation;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.InvalidHardwareKeyException;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.UnprivilegedUserException;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSARequest;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSARequest2;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityRequestHandler;
/*     */ import com.eoos.gm.tis2web.sas.common.system.ISASServer;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.clientside.tasks.HWKReplacementRequest;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.clientside.tasks.HandleSARequest;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.clientside.tasks.HardwareIDRequest;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.clientside.tasks.HardwareKeyValidationRequest;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.clientside.tasks.SOCheckPermissionRequest;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.clientside.tasks.ToolListRequest;
/*     */ import com.eoos.util.MultitonSupport;
/*     */ import com.eoos.util.Task;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SASServerRemoteProxy
/*     */   implements ISASServer
/*     */ {
/*  32 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*     */         public Object createObject(Object identifier) {
/*  34 */           return new SASServerRemoteProxy((String)identifier);
/*     */         }
/*     */       });
/*     */   
/*     */   private String sessionID;
/*     */   
/*     */   private SASServerRemoteProxy(String sessionID) {
/*  41 */     this.sessionID = sessionID;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized SASServerRemoteProxy getInstance(String sessionID) {
/*  46 */     return (SASServerRemoteProxy)multitonSupport.getInstance(sessionID);
/*     */   }
/*     */   
/*     */   public SecurityAccessResponse handle(SecurityAccessRequest request, String sessionID) throws SecurityRequestHandler.Exception {
/*  50 */     if (request instanceof SSARequest) {
/*     */       try {
/*  52 */         SSARequest2 sSARequest2; SSARequest ssaRequest = (SSARequest)request;
/*  53 */         if (!(request instanceof SSARequest2) || ((SSARequest2)request).getHardwareKey() == null) {
/*  54 */           HardwareKey hwk = HWKProviderFacade.getInstance().getHardwareKey();
/*  55 */           if (isValid(hwk)) {
/*  56 */             sSARequest2 = ssaRequest.setHardwareKey(hwk);
/*     */           } else {
/*  58 */             throw new InvalidHardwareKeyException();
/*     */           } 
/*     */         } 
/*  61 */         HandleSARequest task = new HandleSARequest(this.sessionID, (SecurityAccessRequest)sSARequest2);
/*  62 */         Object retValue = ServerTaskExecution.getInstance().execute((Task)task);
/*  63 */         return (SecurityAccessResponse)returnHook_SRH(retValue);
/*  64 */       } catch (Exception e) {
/*  65 */         throw new SecurityRequestHandler.Exception(e);
/*     */       } 
/*     */     }
/*  68 */     return LocalRequestHandler.getInstance().handle(request, sessionID);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object returnHook_SRH(Object retValue) throws SecurityRequestHandler.Exception {
/*  73 */     if (!(retValue instanceof Throwable))
/*  74 */       return retValue; 
/*  75 */     if (retValue instanceof SecurityRequestHandler.Exception)
/*  76 */       throw (SecurityRequestHandler.Exception)retValue; 
/*  77 */     if (retValue instanceof RuntimeException) {
/*  78 */       throw (RuntimeException)retValue;
/*     */     }
/*  80 */     throw new ExceptionWrapper((Throwable)retValue);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object returnHook(Object retValue) throws ISASServer.Exception {
/*  85 */     if (!(retValue instanceof Throwable))
/*  86 */       return retValue; 
/*  87 */     if (retValue instanceof ISASServer.Exception)
/*  88 */       throw (ISASServer.Exception)retValue; 
/*  89 */     if (retValue instanceof RuntimeException) {
/*  90 */       throw (RuntimeException)retValue;
/*     */     }
/*  92 */     throw new ExceptionWrapper((Throwable)retValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public List getTools() throws ISASServer.Exception {
/*  97 */     ToolListRequest task = new ToolListRequest(this.sessionID);
/*  98 */     return (List)returnHook(ServerTaskExecution.getInstance().execute((Task)task));
/*     */   }
/*     */   
/*     */   public boolean isValid(HardwareKey hardwareKey) throws ISASServer.Exception {
/* 102 */     HardwareKeyValidationRequest task = new HardwareKeyValidationRequest(this.sessionID, hardwareKey);
/* 103 */     return ((Boolean)returnHook(ServerTaskExecution.getInstance().execute((Task)task))).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean allowAccess(SalesOrganisation salesOrganisation) throws ISASServer.Exception {
/* 107 */     SOCheckPermissionRequest task = new SOCheckPermissionRequest(this.sessionID, salesOrganisation);
/* 108 */     return ((Boolean)returnHook(ServerTaskExecution.getInstance().execute((Task)task))).booleanValue();
/*     */   }
/*     */   
/*     */   public HardwareKey getHWKReplacement() throws UnprivilegedUserException {
/* 112 */     HWKReplacementRequest task = new HWKReplacementRequest(this.sessionID);
/* 113 */     Object ret = ServerTaskExecution.getInstance().execute((Task)task);
/* 114 */     if (ret instanceof UnprivilegedUserException)
/* 115 */       throw (UnprivilegedUserException)ret; 
/* 116 */     if (ret instanceof RuntimeException)
/* 117 */       throw (RuntimeException)ret; 
/* 118 */     if (ret instanceof ISASServer.Exception) {
/* 119 */       throw new RuntimeException((ISASServer.Exception)ret);
/*     */     }
/* 121 */     return (HardwareKey)ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHardwareID() throws ISASServer.Exception {
/* 126 */     HardwareIDRequest task = new HardwareIDRequest(this.sessionID);
/* 127 */     Object ret = ServerTaskExecution.getInstance().execute((Task)task);
/* 128 */     if (ret instanceof RuntimeException)
/* 129 */       throw (RuntimeException)ret; 
/* 130 */     if (ret instanceof ISASServer.Exception) {
/* 131 */       throw new RuntimeException((ISASServer.Exception)ret);
/*     */     }
/* 133 */     return (String)ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\clientside\SASServerRemoteProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */