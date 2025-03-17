/*     */ package com.eoos.gm.tis2web.frame.ws.lt.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.FatalError;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.FatalFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.GetVehRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.GetVehResponse;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.GuiRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.GuiResponse;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.IclRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.IclResponse;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.IdFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.InvalidReqId;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.InvalidVehicleDescription;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.LaborOpFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.LogoutRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.OpList;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.OpNumValRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.OpNumValResult;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.OpRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.QualifierFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.QualifierValidateRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.QualifierValuesRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.QualifierValuesResponse;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.ResetTalRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.SecurityFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.SetVehDescRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.SetVehDescResult;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.TalRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.TalResponse;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehDescriptionFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehValRequest;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehValResult;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.security.WsSecurityHandler2;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.util.LtwsUtils;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.wrapper.LaborOpWrapper;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.wrapper.QualifierWrapper;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.wrapper.VehDescWrapper;
/*     */ import com.eoos.gm.tis2web.lt.icop.server.ICOPServerSupport;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.icl.ICLContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINImpl;
/*     */ import java.net.URL;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import javax.annotation.Resource;
/*     */ import javax.jws.HandlerChain;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @HandlerChain(file = "chain.xml")
/*     */ @WebService(serviceName = "LTService", portName = "LTServicePort", endpointInterface = "com.eoos.gm.tis2web.frame.ws.lt.common.generated.LTServicePort", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", wsdlLocation = "WEB-INF/wsdl/LTService/LaborOp.wsdl")
/*     */ public class LTService
/*     */ {
/*  68 */   private static final Logger log = Logger.getLogger(LTService.class);
/*  69 */   private static final Logger wsPerformanceLog = Logger.getLogger("wsperformance");
/*     */   
/*     */   @Resource
/*     */   WebServiceContext wsContext;
/*     */   
/*     */   public QualifierValuesResponse getQualifierValues(QualifierValuesRequest params) throws SecurityFault, FatalFault {
/*  75 */     long ts = (new Date()).getTime();
/*  76 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/*  77 */     log.debug("Received qualifier values request: " + reqId);
/*  78 */     QualifierValuesResponse result = new QualifierValuesResponse();
/*  79 */     String msg = "getQualifierValues [" + reqId + "]";
/*     */     try {
/*  81 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/*  82 */       result = (new QualifierWrapper(null)).getQualifierValues();
/*  83 */       long pt = (new Date()).getTime() - ts;
/*  84 */       log.debug("Sending qualifier values: " + reqId);
/*  85 */       wsPerformanceLog.debug(msg + ": " + pt + " ms");
/*  86 */     } catch (Exception e) {
/*  87 */       log.debug(msg + " failed.");
/*  88 */       wsPerformanceLog.debug(msg + " failed: " + ((new Date()).getTime() - ts) + " ms");
/*  89 */       if (e instanceof SecurityFault) {
/*  90 */         throw (SecurityFault)e;
/*     */       }
/*  92 */       log.error("ICOP request failed.", e);
/*  93 */       FatalError fError = new FatalError();
/*  94 */       fError.setDetails("Unqualified fatal error.");
/*  95 */       throw new FatalFault("Severe server error", fError);
/*     */     } 
/*     */ 
/*     */     
/*  99 */     return result;
/*     */   }
/*     */   
/*     */   public boolean validateQualifier(QualifierValidateRequest params) throws QualifierFault, SecurityFault, FatalFault {
/* 103 */     long ts = (new Date()).getTime();
/* 104 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 105 */     log.debug("Received qualifier validation request: " + reqId);
/* 106 */     String msg = "validateQualifier [" + reqId + "]";
/*     */     try {
/* 108 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/* 109 */       (new QualifierWrapper(params.getQualifier())).validateAll();
/* 110 */       long pt = (new Date()).getTime() - ts;
/* 111 */       log.debug("Qualifier validated: " + reqId);
/* 112 */       wsPerformanceLog.debug(msg + ": " + pt + " ms");
/* 113 */     } catch (Exception e) {
/* 114 */       log.debug(msg + " failed.");
/* 115 */       wsPerformanceLog.debug(msg + " failed: " + ((new Date()).getTime() - ts) + " ms");
/* 116 */       if (e instanceof SecurityFault)
/* 117 */         throw (SecurityFault)e; 
/* 118 */       if (e instanceof QualifierFault) {
/* 119 */         throw (QualifierFault)e;
/*     */       }
/* 121 */       log.error("ICOP request failed.", e);
/* 122 */       FatalError fError = new FatalError();
/* 123 */       fError.setDetails("Unqualified fatal error.");
/* 124 */       throw new FatalFault("Severe server error", fError);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   public VehValResult validateVehDesc(VehValRequest params) throws QualifierFault, VehDescriptionFault, SecurityFault, FatalFault {
/* 132 */     long ts = (new Date()).getTime();
/* 133 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 134 */     VehValResult result = null;
/* 135 */     log.debug("Received vehicle validation request: " + reqId);
/*     */     try {
/* 137 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/* 138 */       QualifierWrapper qWrapper = new QualifierWrapper(params.getQualifier());
/* 139 */       qWrapper.validateLangAndCountry();
/* 140 */       log.debug("Qualifier validated.");
/* 141 */       VehDescWrapper vehDescWrapper = new VehDescWrapper(params.getVehDesc());
/* 142 */       log.debug("Requested vehicle: " + vehDescWrapper.toString());
/* 143 */       result = vehDescWrapper.validateVehDesc(qWrapper.getLocale(), false);
/* 144 */       log.debug("Resolved vehicle: " + vehDescWrapper.toString());
/* 145 */       result.setSuccess(Boolean.valueOf(true));
/* 146 */       long pt = (new Date()).getTime() - ts;
/* 147 */       log.debug("Sending resolved vehicle description: " + reqId);
/* 148 */       wsPerformanceLog.debug("validateVehDesc [" + reqId + "]: " + pt + " ms");
/* 149 */     } catch (Exception e) {
/* 150 */       throwExceptionC(e, ts, "vehicle validation [" + reqId + "] failed");
/*     */     } 
/* 152 */     return result;
/*     */   }
/*     */   
/*     */   public SetVehDescResult setVehDesc(SetVehDescRequest params) throws QualifierFault, VehDescriptionFault, IdFault, SecurityFault, FatalFault {
/* 156 */     long ts = (new Date()).getTime();
/* 157 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 158 */     SetVehDescResult result = new SetVehDescResult();
/* 159 */     log.debug("Received  set vehicle description request: " + reqId); try {
/*     */       VINImpl vINImpl;
/* 161 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/* 162 */       QualifierWrapper qWrapper = new QualifierWrapper(params.getQualifier());
/* 163 */       qWrapper.validateGuiLangAndCountry();
/* 164 */       log.debug("Qualifier validated.");
/* 165 */       VehDescWrapper vehDescWrapper = new VehDescWrapper(params.getVehDesc());
/* 166 */       log.debug("Requested vehicle: " + vehDescWrapper.toString());
/* 167 */       VIN vinObj = null;
/* 168 */       if (vehDescWrapper.isSufficient()) {
/* 169 */         vehDescWrapper.validateVehDesc(qWrapper.getGuiLocale(), false);
/* 170 */         result.setVehDesc(vehDescWrapper.getVehDesc());
/*     */         try {
/* 172 */           vINImpl = new VINImpl(vehDescWrapper.getVin());
/* 173 */         } catch (Exception e) {}
/*     */       } else {
/*     */         
/*     */         try {
/* 177 */           vehDescWrapper.resolveMake(qWrapper.getGuiLocale());
/*     */         }
/* 179 */         catch (InvalidVehException e) {
/* 180 */           InvalidVehicleDescription invVehDesc = new InvalidVehicleDescription();
/* 181 */           LtwsUtils.setInvalidVehicleDetails(e, invVehDesc);
/* 182 */           VehDescriptionFault vdFault = new VehDescriptionFault("Invalid vehicle description", invVehDesc);
/* 183 */           throw vdFault;
/*     */         } 
/*     */       } 
/* 186 */       log.debug("Resolved vehicle: " + vehDescWrapper.toString());
/* 187 */       ICOPServerSupport.RequestParameters requestParams = null;
/*     */       try {
/* 189 */         requestParams = ICOPServerSupport.setVehDesc(params.getReqId(), (VIN)vINImpl, vehDescWrapper.getConfiguration());
/* 190 */       } catch (InvalidSessionException ex) {
/* 191 */         InvalidReqId invId = new InvalidReqId();
/* 192 */         invId.setReqId(params.getReqId());
/* 193 */         throw new IdFault("Invalid session Id.", invId);
/*     */       } 
/* 195 */       Properties p = requestParams.getPostParameters();
/* 196 */       result.setUrl(requestParams.getURL().toString());
/* 197 */       result.getDetails().addAll(LtwsUtils.propertiesToList(p));
/* 198 */       long pt = (new Date()).getTime() - ts;
/* 199 */       log.debug("Vehicle description set: " + reqId);
/* 200 */       wsPerformanceLog.debug("setVehDesc [" + reqId + "]: " + pt + " ms");
/* 201 */     } catch (Exception e) {
/* 202 */       throwExceptionA(e, ts, "setVehDesc [" + reqId + "] failed");
/*     */     } 
/* 204 */     return result;
/*     */   }
/*     */   
/*     */   public OpNumValResult validateOpNumber(OpNumValRequest params) throws QualifierFault, VehDescriptionFault, LaborOpFault, SecurityFault, FatalFault {
/* 208 */     long ts = (new Date()).getTime();
/* 209 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 210 */     OpNumValResult result = new OpNumValResult();
/*     */     try {
/* 212 */       log.debug("Received operation number validation request: " + reqId);
/* 213 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/* 214 */       QualifierWrapper qWrapper = new QualifierWrapper(params.getQualifier());
/* 215 */       qWrapper.validateLangAndCountry();
/* 216 */       log.debug("Qualifier validated.");
/* 217 */       VehDescWrapper vehDescWrapper = new VehDescWrapper(params.getVehDesc());
/* 218 */       log.debug("Requested vehicle: " + vehDescWrapper.toString());
/* 219 */       vehDescWrapper.validateVehDesc(qWrapper.getLocale(), false);
/* 220 */       log.debug("Resolved vehicle: " + vehDescWrapper.toString());
/* 221 */       ClientContext clientContext = getClientContext(reqId, qWrapper.getLocale());
/* 222 */       LTClientContext ltClientContext = getContext(clientContext, qWrapper.getT2wCountry(), vehDescWrapper.getConfiguration(), vehDescWrapper.getVin(), true);
/* 223 */       AWAdapter awAdapter = new AWAdapter();
/* 224 */       awAdapter.validateOpNumber(params.getOpNumber(), ltClientContext);
/* 225 */       result.setVehDesc(vehDescWrapper.getVehDesc());
/* 226 */       result.setSuccess(Boolean.valueOf(true));
/* 227 */       long pt = (new Date()).getTime() - ts;
/* 228 */       log.debug("Operation number validated: " + reqId);
/* 229 */       wsPerformanceLog.debug("validateOpNumber [" + reqId + "]: " + pt + " ms");
/* 230 */     } catch (Exception e) {
/* 231 */       throwExceptionB(e, ts, "validateOpNumber [" + reqId + "] failed");
/*     */     } 
/* 233 */     return result;
/*     */   }
/*     */   
/*     */   public OpList getLaborOperation(OpRequest params) throws QualifierFault, VehDescriptionFault, LaborOpFault, SecurityFault, FatalFault {
/* 237 */     long ts = (new Date()).getTime();
/* 238 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 239 */     OpList result = new OpList();
/* 240 */     log.debug("Received labor operation request for opNumber: " + params.getOpNumber() + " / " + reqId);
/*     */     try {
/* 242 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/* 243 */       QualifierWrapper qWrapper = new QualifierWrapper(params.getQualifier());
/* 244 */       qWrapper.validateLangAndCountry();
/* 245 */       log.debug("Qualifier validated.");
/* 246 */       VehDescWrapper vehDescWrapper = new VehDescWrapper(params.getVehDesc());
/* 247 */       log.debug("Requested vehicle: " + vehDescWrapper.toString());
/* 248 */       Locale locale = qWrapper.getLocale();
/* 249 */       vehDescWrapper.validateVehDesc(locale, false);
/* 250 */       result.setVehDesc(vehDescWrapper.getVehDesc());
/* 251 */       log.debug("Resolved vehicle: " + vehDescWrapper.toString());
/* 252 */       ClientContext clientContext = getClientContext(reqId, locale);
/* 253 */       LTClientContext ltClientContext = getContext(clientContext, qWrapper.getT2wCountry(), vehDescWrapper.getConfiguration(), vehDescWrapper.getVin(), params.isEnforceHours());
/* 254 */       AWAdapter awAdapter = new AWAdapter();
/* 255 */       result.getOperations().addAll(awAdapter.getFormattedAWList(params.getOpNumber(), ltClientContext));
/* 256 */       long pt = (new Date()).getTime() - ts;
/* 257 */       log.debug("Sending labor operation list: " + reqId);
/* 258 */       wsPerformanceLog.debug("getLaborOperation [" + reqId + "]: " + pt + " ms");
/* 259 */     } catch (Exception e) {
/* 260 */       throwExceptionB(e, ts, "getLaborOperation [" + reqId + "] failed");
/*     */     } 
/* 262 */     return result;
/*     */   }
/*     */   
/*     */   public GuiResponse startGui(GuiRequest params) throws QualifierFault, VehDescriptionFault, SecurityFault, FatalFault {
/* 266 */     long ts = (new Date()).getTime();
/* 267 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 268 */     log.debug("Received GUI request: " + reqId);
/* 269 */     GuiResponse result = new GuiResponse(); try {
/*     */       VINImpl vINImpl;
/* 271 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/* 272 */       QualifierWrapper qWrapper = new QualifierWrapper(params.getQualifier());
/* 273 */       qWrapper.validateGuiLangAndCountry();
/* 274 */       log.debug("Qualifier validated.");
/* 275 */       VehDescWrapper vehDescWrapper = new VehDescWrapper(params.getVehDesc());
/* 276 */       log.debug("Requested vehicle: " + vehDescWrapper.toString());
/* 277 */       VIN vinObj = null;
/* 278 */       if (vehDescWrapper.isSufficient()) {
/* 279 */         vehDescWrapper.validateVehDesc(qWrapper.getGuiLocale(), false);
/* 280 */         result.setVehDesc(vehDescWrapper.getVehDesc());
/*     */         try {
/* 282 */           vINImpl = new VINImpl(vehDescWrapper.getVin());
/* 283 */         } catch (Exception e) {}
/*     */       } else {
/*     */         
/*     */         try {
/* 287 */           vehDescWrapper.resolveMake(qWrapper.getGuiLocale());
/* 288 */         } catch (InvalidVehException e) {
/* 289 */           InvalidVehicleDescription invVehDesc = new InvalidVehicleDescription();
/* 290 */           LtwsUtils.setInvalidVehicleDetails(e, invVehDesc);
/* 291 */           VehDescriptionFault vdFault = new VehDescriptionFault("Invalid vehicle description", invVehDesc);
/* 292 */           throw vdFault;
/*     */         } 
/*     */       } 
/* 295 */       log.debug("Resolved vehicle: " + vehDescWrapper.toString());
/* 296 */       ICOPServerSupport.RequestParameters loginParams = ICOPServerSupport.startGUI(reqId, (VIN)vINImpl, vehDescWrapper.getConfiguration(), qWrapper.getT2wCountry(), qWrapper.getGuiLocale().toString());
/* 297 */       URL url = loginParams.getURL();
/* 298 */       Properties p = loginParams.getPostParameters();
/* 299 */       result.setUrl(url.toString());
/* 300 */       result.getDetails().addAll(LtwsUtils.propertiesToList(p));
/* 301 */       result.setReqId(reqId);
/* 302 */       long pt = (new Date()).getTime() - ts;
/* 303 */       log.debug("Sending gui launch parameters: " + reqId);
/* 304 */       wsPerformanceLog.debug("startGui [" + reqId + "]: " + pt + " ms");
/* 305 */     } catch (Exception e) {
/* 306 */       throwExceptionC(e, ts, "startGui [" + reqId + "] failed");
/*     */     } 
/* 308 */     return result;
/*     */   }
/*     */   
/*     */   public GetVehResponse getVehDesc(GetVehRequest params) throws IdFault, SecurityFault, FatalFault {
/* 312 */     long ts = (new Date()).getTime();
/* 313 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 314 */     log.debug("Received GetVehicleDescription request: " + reqId);
/* 315 */     GetVehResponse result = new GetVehResponse();
/*     */     try {
/* 317 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/*     */       try {
/* 319 */         result.setVehDesc(LtwsUtils.getVehDesc(ICOPServerSupport.getVehicleConfiguration(params.getSessionId())));
/* 320 */       } catch (InvalidSessionException ex) {
/* 321 */         InvalidReqId invId = new InvalidReqId();
/* 322 */         invId.setReqId(params.getSessionId());
/* 323 */         throw new IdFault("Invalid session Id.", invId);
/*     */       } 
/* 325 */       long pt = (new Date()).getTime() - ts;
/* 326 */       log.debug("Sending VehicleDescription: " + reqId);
/* 327 */       wsPerformanceLog.debug("getVehDesc [" + reqId + "]: " + pt + " ms");
/* 328 */     } catch (Exception e) {
/* 329 */       throwExceptionD(e, ts, "getVehDesc [" + reqId + "] failed");
/*     */     } 
/* 331 */     return result;
/*     */   }
/*     */   
/*     */   public TalResponse getTal(TalRequest params) throws IdFault, SecurityFault, FatalFault {
/* 335 */     long ts = (new Date()).getTime();
/* 336 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 337 */     log.debug("Received TAL request: " + reqId);
/* 338 */     TalResponse result = new TalResponse();
/*     */     try {
/* 340 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/* 341 */       ICOPServerSupport.TAL tal = null;
/*     */       try {
/* 343 */         tal = ICOPServerSupport.getTAL(params.getReqId());
/* 344 */       } catch (InvalidSessionException ex) {
/* 345 */         InvalidReqId invId = new InvalidReqId();
/* 346 */         invId.setReqId(params.getReqId());
/* 347 */         throw new IdFault("Invalid TAL request Id.", invId);
/*     */       } 
/* 349 */       LaborOpWrapper lopWrapper = new LaborOpWrapper();
/* 350 */       result.getOperations().addAll(lopWrapper.getTalItems(tal));
/* 351 */       result.setSum(tal.getSum());
/* 352 */       result.setUnit(tal.getUnit());
/* 353 */       result.setHeader(tal.getHeader());
/* 354 */       result.setTrailer(tal.getTrailer());
/* 355 */       result.setVehDesc(LtwsUtils.getVehDesc(tal));
/* 356 */       long pt = (new Date()).getTime() - ts;
/* 357 */       log.debug("Sending TAL: " + reqId);
/* 358 */       wsPerformanceLog.debug("getTal [" + reqId + "]: " + pt + " ms");
/* 359 */     } catch (Exception e) {
/* 360 */       throwExceptionD(e, ts, "getTal [" + reqId + "] failed");
/*     */     } 
/* 362 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public IclResponse getIcl(IclRequest params) throws QualifierFault, VehDescriptionFault, LaborOpFault, SecurityFault, FatalFault {
/* 367 */     long ts = (new Date()).getTime();
/* 368 */     IclResponse result = new IclResponse();
/* 369 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 370 */     log.debug("Received ICL request for opNumber: " + params.getOpNumber() + " / " + reqId);
/*     */     try {
/* 372 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/* 373 */       QualifierWrapper qWrapper = new QualifierWrapper(params.getQualifier());
/* 374 */       qWrapper.validateLangAndCountry();
/* 375 */       log.debug("Qualifier validated.");
/* 376 */       VehDescWrapper vehDescWrapper = new VehDescWrapper(params.getVehDesc());
/* 377 */       log.debug("Requested vehicle: " + vehDescWrapper.toString());
/* 378 */       Locale locale = qWrapper.getLocale();
/* 379 */       vehDescWrapper.validateVehDesc(locale, true);
/* 380 */       result.setVehDesc(vehDescWrapper.getVehDesc());
/* 381 */       log.debug("Resolved vehicle: " + vehDescWrapper.toString());
/* 382 */       ClientContext clientContext = getClientContext(reqId, locale);
/* 383 */       AWAdapter awAdapter = new AWAdapter();
/* 384 */       LTClientContext ltClientContext = getContext(clientContext, qWrapper.getT2wCountry(), vehDescWrapper.getConfiguration(), vehDescWrapper.getVin(), true);
/* 385 */       awAdapter.validateOpNumber(params.getOpNumber(), ltClientContext);
/* 386 */       getContext(clientContext, qWrapper.getT2wCountry(), vehDescWrapper.getConfiguration(), vehDescWrapper.getVin(), false);
/* 387 */       ICLContext iclContext = new ICLContext(clientContext, params.getOpNumber());
/* 388 */       if (iclContext.singleChecklistAvailable()) {
/* 389 */         byte[] blob = iclContext.getChecklist();
/* 390 */         if (blob != null && blob.length > 0) {
/* 391 */           result.setBlob(blob);
/* 392 */           result.setHasChecklist(Boolean.valueOf(true));
/*     */         } 
/* 394 */       } else if (iclContext.multipleChecklistAvailable()) {
/* 395 */         VehDescriptionFault vDescFault = LtwsUtils.getVehicleDescriptionFault(vehDescWrapper, iclContext.getVehicleRestriction());
/* 396 */         throw vDescFault;
/*     */       } 
/* 398 */       long pt = (new Date()).getTime() - ts;
/* 399 */       log.debug("Sending ICL document: " + reqId);
/* 400 */       wsPerformanceLog.debug("getIcl [" + reqId + "]: " + pt + " ms");
/* 401 */     } catch (Exception e) {
/* 402 */       throwExceptionB(e, ts, "getIcl [" + reqId + "] failed");
/*     */     } 
/* 404 */     return result;
/*     */   }
/*     */   
/*     */   public boolean resetTal(ResetTalRequest params) throws IdFault, SecurityFault, FatalFault {
/* 408 */     long ts = (new Date()).getTime();
/* 409 */     boolean result = false;
/* 410 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 411 */     log.debug("Received resetTal request: " + reqId);
/*     */     try {
/* 413 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/*     */       try {
/* 415 */         result = ICOPServerSupport.resetTal(params.getReqId());
/* 416 */       } catch (Exception e) {
/* 417 */         InvalidReqId invReqId = new InvalidReqId();
/* 418 */         invReqId.setReqId(params.getReqId());
/* 419 */         throw new IdFault("Invalid session ID", invReqId);
/*     */       } 
/* 421 */       long pt = (new Date()).getTime() - ts;
/* 422 */       log.debug("TAL reset: " + reqId);
/* 423 */       wsPerformanceLog.debug("resetTal [" + reqId + "]: " + pt + " ms");
/* 424 */     } catch (Exception e) {
/* 425 */       throwExceptionD(e, ts, "resetTal [" + reqId + "] failed");
/*     */     } 
/* 427 */     return result;
/*     */   }
/*     */   
/*     */   public boolean logout(LogoutRequest params) throws IdFault, SecurityFault, FatalFault {
/* 431 */     long ts = (new Date()).getTime();
/* 432 */     boolean result = false;
/* 433 */     String reqId = getLogRequestId(params.getUid()) + "_" + ApplicationContext.getInstance().getIDFactory().getNextID();
/* 434 */     log.debug("Received logout request: " + reqId);
/*     */     try {
/* 436 */       (new WsSecurityHandler2()).authorize(this.wsContext.getMessageContext(), params.getUid());
/*     */       try {
/* 438 */         result = ICOPServerSupport.logout(params.getReqId());
/* 439 */       } catch (Exception e) {
/* 440 */         InvalidReqId invReqId = new InvalidReqId();
/* 441 */         invReqId.setReqId(params.getReqId());
/* 442 */         throw new IdFault("Invalid session ID", invReqId);
/*     */       } 
/* 444 */       long pt = (new Date()).getTime() - ts;
/* 445 */       log.debug("Logout performed: " + reqId);
/* 446 */       wsPerformanceLog.debug("logout [" + reqId + "]: " + pt + " ms");
/* 447 */     } catch (Exception e) {
/* 448 */       throwExceptionD(e, ts, "logout [" + reqId + "] failed");
/*     */     } 
/* 450 */     return result;
/*     */   }
/*     */   
/*     */   private ClientContext getClientContext(String id, Locale locale) {
/* 454 */     return ClientContextProvider.getInstance().getTmpContext(id, locale);
/*     */   }
/*     */   
/*     */   private LTClientContext getContext(ClientContext context, String country, IConfiguration vehConfig, String vin, boolean enforceLTHours) throws UnspecifiedException {
/* 458 */     LTClientContext result = null;
/* 459 */     context.getSharedContext().setCountry(country);
/* 460 */     context.getSharedContext().setUseLTHours(new Boolean(enforceLTHours));
/*     */     try {
/* 462 */       VCFacade.getInstance(context).storeCfg(vehConfig, (vin != null) ? (VIN)new VINImpl(vin) : null);
/* 463 */     } catch (Exception e) {
/* 464 */       log.debug("Unexpected exception (1): " + e, e);
/* 465 */       throw new UnspecifiedException();
/*     */     } 
/* 467 */     result = LTClientContext.getInstance(context);
/*     */     try {
/* 469 */       result.setVCR();
/* 470 */     } catch (Exception e) {
/* 471 */       log.debug("Unexpected exception (2): " + e, e);
/* 472 */       throw new UnspecifiedException();
/*     */     } 
/* 474 */     return result;
/*     */   }
/*     */   
/*     */   private void throwExceptionA(Exception e, long t0, String text) throws IdFault, QualifierFault, VehDescriptionFault, SecurityFault, FatalFault {
/* 478 */     log.debug(text + ".");
/* 479 */     wsPerformanceLog.debug(text + ": " + ((new Date()).getTime() - t0) + " ms");
/* 480 */     if (e instanceof IdFault)
/* 481 */       throw (IdFault)e; 
/* 482 */     if (e instanceof QualifierFault)
/* 483 */       throw (QualifierFault)e; 
/* 484 */     if (e instanceof VehDescriptionFault)
/* 485 */       throw (VehDescriptionFault)e; 
/* 486 */     if (e instanceof SecurityFault) {
/* 487 */       throw (SecurityFault)e;
/*     */     }
/* 489 */     log.error("ICOP request failed.", e);
/* 490 */     FatalError fError = new FatalError();
/* 491 */     fError.setDetails("Unqualified fatal error.");
/* 492 */     throw new FatalFault("Severe server error", fError);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwExceptionB(Exception e, long t0, String text) throws QualifierFault, VehDescriptionFault, LaborOpFault, SecurityFault, FatalFault {
/* 498 */     log.debug(text + ".");
/* 499 */     wsPerformanceLog.debug(text + ": " + ((new Date()).getTime() - t0) + " ms");
/* 500 */     if (e instanceof QualifierFault)
/* 501 */       throw (QualifierFault)e; 
/* 502 */     if (e instanceof VehDescriptionFault)
/* 503 */       throw (VehDescriptionFault)e; 
/* 504 */     if (e instanceof LaborOpFault)
/* 505 */       throw (LaborOpFault)e; 
/* 506 */     if (e instanceof SecurityFault) {
/* 507 */       throw (SecurityFault)e;
/*     */     }
/* 509 */     log.error("ICOP request failed.", e);
/* 510 */     FatalError fError = new FatalError();
/* 511 */     fError.setDetails("Unqualified fatal error.");
/* 512 */     throw new FatalFault("Severe server error", fError);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwExceptionC(Exception e, long t0, String text) throws QualifierFault, VehDescriptionFault, SecurityFault, FatalFault {
/* 518 */     log.debug(text + ".");
/* 519 */     wsPerformanceLog.debug(text + ": " + ((new Date()).getTime() - t0) + " ms");
/* 520 */     if (e instanceof QualifierFault)
/* 521 */       throw (QualifierFault)e; 
/* 522 */     if (e instanceof VehDescriptionFault)
/* 523 */       throw (VehDescriptionFault)e; 
/* 524 */     if (e instanceof SecurityFault) {
/* 525 */       throw (SecurityFault)e;
/*     */     }
/* 527 */     log.error("ICOP request failed.", e);
/* 528 */     FatalError fError = new FatalError();
/* 529 */     fError.setDetails("Unqualified fatal error.");
/* 530 */     throw new FatalFault("Severe server error", fError);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwExceptionD(Exception e, long t0, String text) throws IdFault, SecurityFault, FatalFault {
/* 536 */     log.debug(text + ".");
/* 537 */     wsPerformanceLog.debug(text + ": " + ((new Date()).getTime() - t0) + " ms");
/* 538 */     if (e instanceof IdFault)
/* 539 */       throw (IdFault)e; 
/* 540 */     if (e instanceof SecurityFault) {
/* 541 */       throw (SecurityFault)e;
/*     */     }
/* 543 */     log.error("ICOP request failed.", e);
/* 544 */     FatalError fError = new FatalError();
/* 545 */     fError.setDetails("Unqualified fatal error.");
/* 546 */     throw new FatalFault("Severe server error", fError);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getLogRequestId(String str) {
/* 552 */     String result = str;
/*     */     try {
/* 554 */       int ndx = str.lastIndexOf(":::");
/* 555 */       if (ndx >= 0) {
/* 556 */         result = str.substring(0, ndx);
/*     */       }
/* 558 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 561 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\LTService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */