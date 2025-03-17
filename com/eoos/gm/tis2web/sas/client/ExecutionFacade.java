/*     */ package com.eoos.gm.tis2web.sas.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.client.util.DeviceSettings;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.sas.client.req.IncompleteResponseConfirmationRequest;
/*     */ import com.eoos.gm.tis2web.sas.client.req.RequestException;
/*     */ import com.eoos.gm.tis2web.sas.client.req.ToolSelectionRequestImpl;
/*     */ import com.eoos.gm.tis2web.sas.client.req.VINValidationRequestImpl;
/*     */ import com.eoos.gm.tis2web.sas.client.system.SASClientContext;
/*     */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*     */ import com.eoos.gm.tis2web.sas.common.model.AccessType;
/*     */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.BadRequestException;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.MissingPermissionException;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.SalesOrganisationReadException;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.SystemException;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.WriteResponseException;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSARequest;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSAResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityRequestHandler;
/*     */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*     */ import com.eoos.gm.tis2web.sas.common.system.ISASServer;
/*     */ import com.eoos.gm.tis2web.sas.common.system.SASServerProvider;
/*     */ import com.eoos.thread.AbortionException;
/*     */ import com.eoos.thread.AsynchronousExecution;
/*     */ import com.eoos.thread.AsynchronousExecutionCallback2;
/*     */ import com.eoos.thread.CustomThread;
/*     */ import com.eoos.thread.ProgressInfo;
/*     */ import com.eoos.thread.ProgressObserver;
/*     */ import com.eoos.thread.impl.ProgressAdapter;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExecutionFacade
/*     */ {
/*  44 */   private static final Logger log = Logger.getLogger(ExecutionFacade.class);
/*     */   
/*     */   private static class ReadRequestException extends ISASServer.Exception {
/*     */     private static final long serialVersionUID = 1L;
/*  48 */     private Throwable cause = null;
/*     */ 
/*     */     
/*     */     public ReadRequestException(Throwable cause) {
/*  52 */       this.cause = cause;
/*     */     }
/*     */     
/*     */     public Throwable getCause() {
/*  56 */       return this.cause;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  61 */   private static ExecutionFacade instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ExecutionFacade getInstance() {
/*  67 */     if (instance == null) {
/*  68 */       instance = new ExecutionFacade();
/*     */     }
/*  70 */     return instance;
/*     */   }
/*     */   
/*     */   private SASClientContext getClientContext() {
/*  74 */     return SASClientContextProvider.getInstance().getContext();
/*     */   }
/*     */   
/*     */   private ISASServer getServer() throws InvalidSessionException {
/*  78 */     return SASServerProvider.getInstance().getServer(getClientContext().getSessionID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Tool getTool(ExecutionAdapter adapter) throws InvalidSessionException, ISASServer.Exception, RequestException {
/*  98 */     Tool tool = (Tool)adapter.getValue(ExecutionAdapter.Key.TOOL);
/*  99 */     if (tool == null) {
/* 100 */       log.debug("no tool selected, returning tool selection request");
/* 101 */       List tools = getServer().getTools();
/* 102 */       throw new RequestException(new ToolSelectionRequestImpl(tools));
/*     */     } 
/* 104 */     return tool;
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureVINValidation(ExecutionAdapter adapter, SSARequest request) throws BadRequestException, RequestException {
/* 109 */     Boolean validated = (Boolean)adapter.getValue(ExecutionAdapter.Key.VINVALIDATION);
/* 110 */     if (validated == null || !validated.booleanValue()) {
/* 111 */       VIN vin = null;
/*     */       try {
/* 113 */         vin = request.getVINs().get(0);
/* 114 */       } catch (Exception e) {
/* 115 */         log.debug("unable to retrieve vin for validation - exception :" + e, e);
/* 116 */         throw new BadRequestException();
/*     */       } 
/* 118 */       if (vin != null) {
/* 119 */         log.debug("vin not validated yet, returning vin validation request");
/* 120 */         throw new RequestException(new VINValidationRequestImpl(vin));
/*     */       } 
/* 122 */       log.debug("vin is null, skipping validation");
/* 123 */       adapter.setValue(ExecutionAdapter.Key.VINVALIDATION, Boolean.TRUE);
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Tool.CommunicationSettings convert(DeviceSettings settings) {
/* 131 */     Tool.CommunicationSettings ret = null;
/* 132 */     if (settings != null) {
/* 133 */       ret = new DeviceSettingsAdapter(settings);
/*     */     }
/* 135 */     return ret;
/*     */   }
/*     */   
/*     */   private SecurityAccessRequest getRequest(ExecutionAdapter adapter, Tool tool) throws Tool.Exception {
/* 139 */     SecurityAccessRequest request = (SecurityAccessRequest)adapter.getValue(ExecutionAdapter.Key.REQUEST);
/* 140 */     if (request == null) {
/* 141 */       ProgressAdapter pa = new ProgressAdapter();
/* 142 */       pa.tick("reading.request");
/* 143 */       log.debug("reading request from tool");
/*     */       
/* 145 */       Tool.CommunicationSettings settings = convert(SASClientContextProvider.getInstance().getContext().getDeviceSettings());
/* 146 */       request = tool.readRequest(settings);
/* 147 */       pa.tick("");
/* 148 */       adapter.setValue(ExecutionAdapter.Key.REQUEST, request);
/*     */     } 
/* 150 */     return request;
/*     */   }
/*     */   
/*     */   private SecurityAccessResponse getResponse(ExecutionAdapter adapter, SecurityAccessRequest request) throws SecurityRequestHandler.Exception, InvalidSessionException {
/* 154 */     SecurityAccessResponse response = (SecurityAccessResponse)adapter.getValue(ExecutionAdapter.Key.RESPONSE);
/* 155 */     if (response == null) {
/* 156 */       ProgressAdapter pa = new ProgressAdapter();
/* 157 */       pa.tick("handling.request");
/* 158 */       log.debug("handling the request");
/* 159 */       response = getServer().handle(request, SASClientContextProvider.getInstance().getContext().getSessionID());
/* 160 */       pa.tick("");
/* 161 */       adapter.setValue(ExecutionAdapter.Key.RESPONSE, response);
/*     */     } 
/* 163 */     return response;
/*     */   }
/*     */   
/*     */   private void ensureIncompleteSSAResponseFeedback(ExecutionAdapter adapter, SSAResponse response) throws RequestException {
/* 167 */     if (response.isIncomplete()) {
/* 168 */       Boolean confirmed = (Boolean)adapter.getValue(ExecutionAdapter.Key.INCOMPLETE_SSAR_WARNING);
/* 169 */       if (confirmed == null) {
/* 170 */         throw new RequestException(new IncompleteResponseConfirmationRequest());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessType[] execute(ExecutionAdapter adapter) throws Throwable {
/* 189 */     AsynchronousExecution asyncExecution = null;
/*     */ 
/*     */     
/*     */     try {
/* 193 */       final ProgressAdapter progressAdapter = new ProgressAdapter();
/* 194 */       progressAdapter.tick("", 20);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       AccessType[] retValue = null;
/* 202 */       asyncExecution = AsynchronousExecution.start(new AsynchronousExecution.Operation() { public Object execute(Object[] input) throws SystemException, SalesOrganisationReadException, MissingPermissionException, ExecutionFacade.ReadRequestException, BadRequestException, RequestException, SecurityRequestHandler.Exception, InvalidSessionException, WriteResponseException {
/*     */               Tool tool;
/* 204 */               AccessType[] retValue = null;
/* 205 */               ExecutionAdapter adapter = (ExecutionAdapter)input[0];
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 210 */                 tool = ExecutionFacade.this.getTool(adapter);
/* 211 */               } catch (com.eoos.gm.tis2web.sas.common.system.ISASServer.Exception e) {
/* 212 */                 throw new SystemException(e);
/*     */               } 
/* 214 */               ExecutionFacade.this.assertNotAborted();
/*     */ 
/*     */               
/* 217 */               SecurityAccessRequest request = null;
/*     */               try {
/* 219 */                 request = ExecutionFacade.this.getRequest(adapter, tool);
/* 220 */               } catch (Exception e) {
/* 221 */                 throw new ExecutionFacade.ReadRequestException(e);
/*     */               } 
/* 223 */               ExecutionFacade.this.assertNotAborted();
/*     */               
/* 225 */               if (request != null) {
/*     */                 
/* 227 */                 if (request instanceof SSARequest)
/*     */                 {
/*     */                   
/* 230 */                   ExecutionFacade.this.ensureVINValidation(adapter, (SSARequest)request);
/*     */                 }
/*     */ 
/*     */                 
/* 234 */                 SecurityAccessResponse response = ExecutionFacade.this.getResponse(adapter, request);
/* 235 */                 if (response instanceof SSAResponse) {
/* 236 */                   ExecutionFacade.this.ensureIncompleteSSAResponseFeedback(adapter, (SSAResponse)response);
/*     */                 }
/* 238 */                 ExecutionFacade.this.assertNotAborted();
/*     */ 
/*     */                 
/*     */                 try {
/* 242 */                   ProgressAdapter pa = new ProgressAdapter();
/* 243 */                   pa.tick("writing.response");
/* 244 */                   retValue = tool.writeResponse(response);
/* 245 */                   pa.tick("");
/* 246 */                 } catch (com.eoos.gm.tis2web.sas.common.model.tool.Tool.Exception e) {
/* 247 */                   throw new WriteResponseException(e);
/*     */                 } 
/*     */               } 
/*     */               
/* 251 */               return retValue;
/*     */             } }
/*     */           new Object[] { adapter });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       asyncExecution.setProgressObserver(new ProgressObserver() {
/*     */             public boolean onProcessing(ProgressInfo progressInfo) {
/* 261 */               progressAdapter.tick(progressInfo.getMessage());
/* 262 */               return true;
/*     */             }
/*     */           });
/*     */       
/* 266 */       while ((retValue = (AccessType[])asyncExecution.getResult(1000L)) == null && !asyncExecution.isFinished()) {
/* 267 */         progressAdapter.tick();
/*     */       }
/*     */       
/* 270 */       progressAdapter.finish();
/* 271 */       return retValue;
/* 272 */     } catch (AbortionException e) {
/* 273 */       asyncExecution.abort();
/* 274 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(final ExecutionAdapter adapter, final AsynchronousExecutionCallback2 callback) {
/* 280 */     CustomThread t = new CustomThread() {
/*     */         public void run() {
/*     */           try {
/* 283 */             callback.onFinished(ExecutionFacade.this.execute(adapter));
/* 284 */           } catch (Throwable t) {
/* 285 */             callback.onException(t);
/*     */           } 
/*     */         }
/*     */       };
/* 289 */     t.setObserver((ProgressObserver)callback);
/* 290 */     t.start();
/*     */   }
/*     */ 
/*     */   
/*     */   private void assertNotAborted() {
/* 295 */     if (Thread.currentThread() instanceof CustomThread)
/* 296 */       ((CustomThread)Thread.currentThread()).assertNotAborted(); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\ExecutionFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */