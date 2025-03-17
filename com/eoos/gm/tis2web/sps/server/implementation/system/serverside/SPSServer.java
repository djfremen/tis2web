/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside;
/*     */ 
/*     */ import com.akamai.token.tokenFactory;
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.CookieWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.hwk.NoHWKPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.gm.tis2web.frame.hwk.HWKReplacementProvider;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*     */ import com.eoos.gm.tis2web.sps.common.RequestBuilder;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Brand;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ListValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.MailAttachment;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.NavigationTableData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.serveraccess.UnprivilegedUserException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.RequestBuilderImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.navtables.NavTableValidationMap;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLanguage;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.brand.BrandProvider;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.AdapterResetException;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter.SchemaAdapterFacade;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.LockMap;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.activation.DataSource;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ public class SPSServer
/*     */ {
/*  89 */   protected static RequestBuilder builder = (RequestBuilder)new RequestBuilderImpl();
/*     */   
/*  91 */   private static final LockMap lockMap = new LockMap();
/*     */   
/*  93 */   private static final Logger log = Logger.getLogger(SPSServer.class);
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */   
/*     */   private SPSServer(ClientContext context) throws Exception {
/*  99 */     this.context = context;
/* 100 */     SPSLanguage.init();
/* 101 */     context.addLogoutListener(new ClientContextBase.LogoutListener() {
/*     */           public void onLogout() {
/* 103 */             SPSServer.this.context = null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static SPSServer getInstance(String sessionID) throws InvalidSessionException {
/* 109 */     ClientContext context = null;
/* 110 */     synchronized (lockMap.getLockObject(sessionID)) {
/* 111 */       context = ClientContextProvider.getInstance().getContext(sessionID);
/*     */     } 
/* 113 */     if (context == null) {
/* 114 */       log.warn("unable to provide instance for session " + sessionID + ", throwing InvalidSessionException");
/* 115 */       throw new InvalidSessionException(sessionID);
/*     */     } 
/* 117 */     synchronized (context.getLockObject()) {
/* 118 */       context.keepAlive();
/* 119 */       SPSServer instance = (SPSServer)context.getObject(SPSServer.class);
/* 120 */       if (instance == null) {
/*     */         try {
/* 122 */           instance = new SPSServer(context);
/* 123 */           context.storeObject(SPSServer.class, instance);
/* 124 */           if (System.getProperty("sps-client-mode") != null) {
/* 125 */             loginSPSTestClient(sessionID);
/*     */           }
/* 127 */         } catch (Exception e) {
/* 128 */           log.error("unable to create SPSServer instance - exception: " + e, e);
/* 129 */           throw new ExceptionWrapper(e);
/*     */         } 
/*     */       }
/* 132 */       log.debug("returning SPSServer instance for session " + sessionID);
/* 133 */       return instance;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void loginSPSTestClient(String sessionID) {
/*     */     try {
/* 141 */       ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 142 */       SharedContext sc = context.getSharedContext();
/* 143 */       Map<Object, Object> group2ManufMap = new HashMap<Object, Object>();
/* 144 */       HashSet<String> set = new HashSet();
/* 145 */       set.add("ALL");
/* 146 */       group2ManufMap.put("admin", set);
/* 147 */       sc.setUsrGroup2ManufMap(group2ManufMap);
/* 148 */       sc.setCountry("US");
/* 149 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init(AttributeValueMap data) throws RequestException, Exception {
/* 154 */     if (data.getValue(CommonAttribute.DEVICE) == null)
/* 155 */     { getTool(data); }
/* 156 */     else { if (needsReplaceInstruction(data)) {
/* 157 */         String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 158 */         String content = toolType + "-" + "replace-instructions";
/* 159 */         DisplayRequest request = builder.makeDisplayRequest(CommonAttribute.REPLACE_INSTRUCTIONS, null, content);
/* 160 */         request.setAutoSubmit(false);
/* 161 */         throw new RequestException(request);
/* 162 */       }  if (needsPrepare4Communication(data)) {
/* 163 */         String toolType = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 164 */         String content = toolType + "-" + "prep-comm-instructions";
/* 165 */         DisplayRequest request = builder.makeDisplayRequest(CommonAttribute.PREPCOMMUNICATION, null, content);
/* 166 */         request.setAutoSubmit(false);
/* 167 */         throw new RequestException(request);
/* 168 */       }  if (data.getValue(CommonAttribute.TOOL_VIN) == null)
/* 169 */         throw new RequestException(builder.makeVINRequest()); 
/* 170 */       if (data.getValue(CommonAttribute.VIN) == null) {
/* 171 */         throw new RequestException(builder.makeVINDisplayRequest(CommonAttribute.VIN, data.getValue(CommonAttribute.TOOL_VIN)));
/*     */       } }
/*     */   
/*     */   }
/*     */   
/*     */   protected void getTool(AttributeValueMap data) throws Exception {
/* 177 */     ListValue tools = (ListValue)data.getValue(CommonAttribute.TOOLS);
/* 178 */     List<ValueAdapter> accepted = new ArrayList();
/*     */     try {
/* 180 */       ACLService service = ACLServiceProvider.getInstance().getService();
/* 181 */       Set toolset = service.getAuthorizedResources("Diagtool", this.context.getSharedContext().getUsrGroup2ManufMap(), this.context.getSharedContext().getCountry());
/* 182 */       Iterator<ValueAdapter> it = tools.getItems().iterator();
/* 183 */       while (it.hasNext()) {
/* 184 */         String tool = (String)((ValueAdapter)it.next()).getAdaptee();
/* 185 */         boolean authorized = false;
/* 186 */         Iterator<String> it2 = toolset.iterator();
/* 187 */         while (it2.hasNext()) {
/* 188 */           String type = it2.next();
/* 189 */           if (tool.toUpperCase(Locale.ENGLISH).endsWith(":" + type.toUpperCase(Locale.ENGLISH))) {
/* 190 */             authorized = true;
/*     */           }
/*     */         } 
/* 193 */         if (authorized || System.getProperty("sps-client-mode") != null || ApplicationContext.getInstance().developMode()) {
/* 194 */           tool = tool.substring(0, tool.indexOf(":"));
/*     */           
/* 196 */           accepted.add(new ValueAdapter(tool));
/*     */         } 
/*     */       } 
/* 199 */     } catch (Exception e) {
/* 200 */       log.error("unable to filter device list by ACL - error:" + e, e);
/* 201 */       throw new SPSException(CommonException.ServerSideFailure);
/*     */     } 
/* 203 */     throw new RequestException(builder.makeToolSelectionRequest(CommonAttribute.DEVICE, accepted, null));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean needsPrepare4Communication(AttributeValueMap data) {
/* 208 */     String tool = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 209 */     return (!tool.equals("TEST_DRIVER") && data.getValue(CommonAttribute.PREPCOMMUNICATION) == null);
/*     */   }
/*     */   
/*     */   protected boolean needsReplaceInstruction(AttributeValueMap data) {
/* 213 */     String tool = (String)AVUtil.accessValue(data, CommonAttribute.DEVICE);
/* 214 */     Value mode = data.getValue(CommonAttribute.MODE);
/* 215 */     return (!tool.equals("TEST_DRIVER") && tool.equals("T2_REMOTE") && mode.equals(CommonValue.REPLACE_AND_REPROGRAM) && data.getValue(CommonAttribute.REPLACE_INSTRUCTIONS) == null);
/*     */   }
/*     */   
/*     */   public Object execute(AttributeValueMap avMap) throws RequestException, Exception {
/* 219 */     synchronized (this.context.getLockObject()) {
/*     */       
/* 221 */       if ((String)AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID) == null) {
/* 222 */         avMap.set(CommonAttribute.SESSION_ID, (Value)new ValueAdapter(this.context.getSessionID()));
/*     */       }
/*     */       
/* 225 */       Locale locale = (Locale)AVUtil.accessValue(avMap, CommonAttribute.LOCALE);
/* 226 */       if (locale == null) {
/* 227 */         locale = this.context.getLocale();
/* 228 */         avMap.set(CommonAttribute.LOCALE, (Value)new ValueAdapter(locale));
/*     */       } 
/*     */       
/* 231 */       Value modeValue = avMap.getValue(CommonAttribute.EXECUTION_MODE);
/*     */       
/* 233 */       if (modeValue == null || modeValue != CommonValue.EXECUTION_MODE_INFO) {
/* 234 */         init(avMap);
/*     */       }
/*     */       
/* 237 */       getController(avMap);
/* 238 */       ProgrammingData data = getProgrammingData(avMap);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       if (modeValue != null && modeValue == CommonValue.EXECUTION_MODE_INFO) {
/* 244 */         return data;
/*     */       }
/*     */       
/* 247 */       return reprogram(data, avMap);
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
/*     */ 
/*     */   
/*     */   private Controller getController(AttributeValueMap avMap) throws RequestException, Exception {
/* 267 */     return SchemaAdapterFacade.getInstance().getController(avMap);
/*     */   }
/*     */   
/*     */   private ProgrammingData getProgrammingData(AttributeValueMap avMap) throws RequestException, Exception {
/* 271 */     return SchemaAdapterFacade.getInstance().getProgrammingData(avMap);
/*     */   }
/*     */   
/*     */   private Boolean reprogram(ProgrammingData data, AttributeValueMap avMap) throws RequestException, Exception {
/* 275 */     return SchemaAdapterFacade.getInstance().reprogram(data, avMap);
/*     */   }
/*     */   
/*     */   public byte[] getData(ProgrammingDataUnit dataUnit, AttributeValueMap avMap) throws Exception {
/*     */     try {
/* 280 */       return SchemaAdapterFacade.getInstance().getData(dataUnit, avMap);
/* 281 */     } catch (AdapterResetException e) {
/* 282 */       throw new SPSException(CommonException.DataReset);
/* 283 */     } catch (RuntimeException e) {
/* 284 */       log.warn("throwing " + e, e);
/* 285 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getCalibrationVerificationNumber(String partNumber) {
/*     */     try {
/* 292 */       return SchemaAdapterFacade.getInstance().getCalibrationVerificationNumber(this.context.getSessionID(), partNumber);
/* 293 */     } catch (RuntimeException e) {
/* 294 */       log.warn("re-throwing " + e, e);
/* 295 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getBulletin(String locale, String bulletin) {
/* 300 */     synchronized (this.context.getLockObject()) {
/*     */       
/* 302 */       return SchemaAdapterFacade.getInstance().getBulletin(locale, bulletin);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHTML(String locale, String id) {
/* 311 */     synchronized (this.context.getLockObject()) {
/*     */       
/* 313 */       return SPSInstructions.getInstance().getHTML(locale, id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getImage(String id) {
/* 322 */     synchronized (this.context.getLockObject()) {
/*     */       
/* 324 */       return SPSInstructions.getInstance().getImage(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection getNavigationTableData() throws Exception {
/* 333 */     synchronized (this.context.getLockObject()) {
/*     */       
/* 335 */       Collection ret = new LinkedHashSet();
/* 336 */       Locale locale = this.context.getLocale();
/* 337 */       for (Iterator<Brand> iter = BrandProvider.getInstance(this.context).getBrands().iterator(); iter.hasNext(); ) {
/* 338 */         Brand brand = iter.next();
/* 339 */         ret.addAll(NavigationTableDataStore.getInstance().getData(brand, locale));
/*     */       } 
/* 341 */       return ret;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NavTableValidationMap createNavigationTableValidationMap() throws Exception {
/* 350 */     NavTableValidationMap ret = new NavTableValidationMap();
/* 351 */     for (Iterator<NavigationTableData> iter = getNavigationTableData().iterator(); iter.hasNext(); ) {
/* 352 */       NavigationTableData ntd = iter.next();
/* 353 */       ret.put(ntd.getIdentifier(), ntd.getChecksum());
/*     */     } 
/* 355 */     return ret;
/*     */   }
/*     */   
/*     */   public DatabaseInfo getDatabaseInfo(AttributeValueMap map, Attribute attribute) throws Exception {
/* 359 */     synchronized (this.context.getLockObject()) {
/*     */       
/* 361 */       return SchemaAdapterFacade.getInstance().getDatabaseInfo(map, attribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String trimUserName() {
/* 370 */     StringBuffer tmp = new StringBuffer(this.context.getSessionID());
/* 371 */     tmp.append("          ");
/* 372 */     return tmp.substring(0, 10);
/*     */   }
/*     */   
/*     */   public String getHWKReplacement() throws UnprivilegedUserException {
/* 376 */     if (NoHWKPermission.getInstance(this.context).check()) {
/*     */       String str;
/*     */       try {
/* 379 */         str = HWKReplacementProvider.getInstance().getReplacement(this.context.getSessionID());
/* 380 */       } catch (Exception e) {
/* 381 */         log.error("unable to retrieve/create HWK replacement, replacing with userid - exception:" + e, e);
/* 382 */         str = trimUserName();
/*     */       } 
/* 384 */       return str;
/*     */     } 
/* 386 */     throw new UnprivilegedUserException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendEmail(final String orgSender, final Collection recipients, final String subject, final String text, Collection _attachments) throws Exception {
/* 391 */     final String sender = ApplicationContext.getInstance().getProperty("component.sps.mail.sender");
/* 392 */     if (!Util.isNullOrEmpty(sender)) {
/* 393 */       final List<DataSource> listAttachments = new LinkedList();
/* 394 */       for (Iterator<MailAttachment> iter = _attachments.iterator(); iter.hasNext(); ) {
/* 395 */         final MailAttachment ma = iter.next();
/* 396 */         listAttachments.add(new DataSource()
/*     */             {
/*     */               public OutputStream getOutputStream() throws IOException {
/* 399 */                 throw new IOException();
/*     */               }
/*     */               
/*     */               public String getName() {
/* 403 */                 return ma.name;
/*     */               }
/*     */               
/*     */               public InputStream getInputStream() throws IOException {
/* 407 */                 return new ByteArrayInputStream(ma.data);
/*     */               }
/*     */               
/*     */               public String getContentType() {
/* 411 */                 return ma.mime;
/*     */               }
/*     */             });
/*     */       } 
/*     */ 
/*     */       
/* 417 */       MailService service = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 418 */       service.send(new MailService.Callback()
/*     */           {
/*     */             public String getText() {
/* 421 */               return text;
/*     */             }
/*     */             
/*     */             public String getSubject() {
/* 425 */               return subject;
/*     */             }
/*     */             
/*     */             public String getSender() {
/* 429 */               return sender;
/*     */             }
/*     */             
/*     */             public Collection getReplyTo() {
/* 433 */               return Collections.singleton(orgSender);
/*     */             }
/*     */             
/*     */             public Collection getRecipients() {
/* 437 */               return recipients;
/*     */             }
/*     */             
/*     */             public DataSource[] getAttachments() {
/* 441 */               return (DataSource[])listAttachments.toArray((Object[])new DataSource[listAttachments.size()]);
/*     */             }
/*     */           });
/*     */     } else {
/*     */       
/* 446 */       throw new IllegalStateException("missing configuration entry for 'component.sps.mail.sender'");
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHardwareID() throws Exception {
/* 451 */     if (ApplicationContext.getInstance().isStandalone()) {
/* 452 */       return SoftwareKeyProvider.getInstance().getService().getSubscriberID();
/*     */     }
/* 454 */     throw new IllegalArgumentException("central server installation");
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
/*     */   public String getMessage(String errorCode, AttributeValueMap data) {
/*     */     try {
/* 469 */       Locale locale = (Locale)AVUtil.accessValue(data, CommonAttribute.LOCALE);
/* 470 */       Integer deviceID = (Integer)AVUtil.accessValue(data, CommonAttribute.DEVICE_ID);
/*     */       
/* 472 */       return TSCSSupport.getInstance().getMessage(errorCode, deviceID, locale, SharedContext.getInstance(this.context).getUserGroups());
/* 473 */     } catch (Exception e) {
/* 474 */       log.error("unable to retrieve message, returning null - exception:" + e, e);
/* 475 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAutomaticErrorNotification(String errorCode, AttributeValueMap data) {
/*     */     try {
/* 483 */       log.debug("sending automatic error notification email ...");
/*     */       
/* 485 */       StringBuffer text = new StringBuffer();
/* 486 */       text.append("user id: " + this.context.getSessionID());
/* 487 */       text.append("\n");
/*     */       
/* 489 */       String bac = SharedContext.getInstance(this.context).getDealercode();
/* 490 */       text.append("bac: " + String.valueOf(bac));
/* 491 */       text.append("\n");
/*     */       
/* 493 */       text.append("error code: " + String.valueOf(errorCode));
/* 494 */       text.append("\n");
/*     */       
/* 496 */       String vin = (String)AVUtil.accessValue(data, CommonAttribute.VIN);
/* 497 */       text.append("vin :" + String.valueOf(vin));
/* 498 */       text.append("\n");
/*     */       
/* 500 */       Integer deviceID = (Integer)AVUtil.accessValue(data, CommonAttribute.DEVICE_ID);
/* 501 */       text.append("device id: " + String.valueOf(deviceID));
/* 502 */       text.append("\n");
/*     */       
/* 504 */       SPSController controller = null;
/*     */       try {
/* 506 */         controller = (SPSController)SchemaAdapterFacade.getInstance().getController(data);
/* 507 */       } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */       
/* 511 */       String controllerID = null;
/* 512 */       if (controller != null) {
/* 513 */         controllerID = String.valueOf(controller.getID());
/*     */       }
/*     */       
/* 516 */       text.append("controller id: " + String.valueOf(controllerID));
/* 517 */       text.append("\n");
/*     */       
/* 519 */       VIT1Data vit1 = (VIT1Data)AVUtil.accessValue(data, CommonAttribute.VIT1);
/* 520 */       if (vit1 != null && vit1.getVIT() != null && vit1.getVIT().getAttributes() != null) {
/* 521 */         text.append("\nVIT1:\n");
/* 522 */         for (Iterator<Pair> iter = vit1.getVIT().getAttributes().iterator(); iter.hasNext(); ) {
/* 523 */           Pair pair = iter.next();
/* 524 */           text.append(String.valueOf(pair.getFirst()) + ": " + String.valueOf(pair.getSecond()));
/* 525 */           text.append("\n");
/*     */         } 
/*     */       } 
/*     */       
/* 529 */       Set userGroups = SharedContext.getInstance(this.context).getUserGroups();
/* 530 */       log.debug("...retrieving email recipient for current user group(s): " + String.valueOf(userGroups));
/* 531 */       final Collection recipients = TSCSSupport.getInstance().getRecipients(userGroups);
/*     */       
/* 533 */       final String sender = ApplicationContext.getInstance().getProperty("component.sps.mail.sender");
/*     */       
/* 535 */       final String message = text.toString();
/*     */       
/* 537 */       MailService service = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 538 */       service.send(new MailService.Callback()
/*     */           {
/*     */             public String getText() {
/* 541 */               return message;
/*     */             }
/*     */             
/*     */             public String getSubject() {
/* 545 */               return "TIS2Web: Automatic Error Notification";
/*     */             }
/*     */             
/*     */             public String getSender() {
/* 549 */               return sender;
/*     */             }
/*     */             
/*     */             public Collection getReplyTo() {
/* 553 */               return null;
/*     */             }
/*     */             
/*     */             public Collection getRecipients() {
/* 557 */               return recipients;
/*     */             }
/*     */             
/*     */             public DataSource[] getAttachments() {
/* 561 */               return null;
/*     */             }
/*     */           });
/*     */     
/*     */     }
/* 566 */     catch (Exception e) {
/* 567 */       log.error("unable to send automatic error notification, ignoring - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static CookieWrapper getAkamaiCookie() {
/* 572 */     long l1, time = System.currentTimeMillis();
/* 573 */     String _duration = ApplicationContext.getInstance().getProperty("component.sps.akamai.token.duration");
/*     */     
/*     */     try {
/* 576 */       l1 = Util.resolveDuration(Util.parseDuration(_duration));
/* 577 */     } catch (Exception e) {
/* 578 */       log.warn("unable to parse value for token duration, setting duration to default (1 hour)");
/* 579 */       l1 = 3600000L;
/*     */     } 
/* 581 */     String access = ApplicationContext.getInstance().getProperty("component.sps.akamai.token.token.access");
/* 582 */     if (Util.isNullOrEmpty(access)) {
/* 583 */       access = "/gds/*";
/*     */     }
/*     */     
/* 586 */     tokenFactory fact = new tokenFactory();
/* 587 */     CookieWrapper ret = new CookieWrapper();
/* 588 */     ret.cookie = "eoos_akamai=" + fact.generateToken("", time / 1000L, l1 / 1000L, access, "greentomatoe");
/* 589 */     ret.expirationDate = time + l1;
/* 590 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\SPSServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */