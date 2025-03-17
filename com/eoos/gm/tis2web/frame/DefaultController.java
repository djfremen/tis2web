/*     */ package com.eoos.gm.tis2web.frame;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dialog.ContextKillDialog;
/*     */ import com.eoos.gm.tis2web.frame.dialog.ModifiedNewsDialog;
/*     */ import com.eoos.gm.tis2web.frame.dialog.plugincheck.PluginCheckDialog;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.VisualModuleProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.HttpContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.permission.ModuleAccessPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.frame.login.dialog.LoginDialogAccess;
/*     */ import com.eoos.gm.tis2web.frame.login.dialog.LoginDialogProvider;
/*     */ import com.eoos.gm.tis2web.frame.login.log.LoginInfoAdapter;
/*     */ import com.eoos.gm.tis2web.frame.login.log.LoginLog;
/*     */ import com.eoos.gm.tis2web.frame.login.log.LoginLogFacade;
/*     */ import com.eoos.gm.tis2web.frame.login.monitor.LoginMonitor;
/*     */ import com.eoos.gm.tis2web.frame.logmail.LogMailer;
/*     */ import com.eoos.gm.tis2web.frame.msg.MsgFacade;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*     */ import com.eoos.gm.tis2web.frame.msg.util.MsgUIDialog;
/*     */ import com.eoos.gm.tis2web.frame.scout.IScout;
/*     */ import com.eoos.gm.tis2web.frame.scout.IScoutKilledSessionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.scout.InteractionException;
/*     */ import com.eoos.gm.tis2web.frame.scout.PortalScoutMapper;
/*     */ import com.eoos.gm.tis2web.frame.scout.ScoutChainImpl;
/*     */ import com.eoos.gm.tis2web.frame.scout.ScoutException;
/*     */ import com.eoos.gm.tis2web.news.service.NewsService;
/*     */ import com.eoos.gm.tis2web.news.service.NewsServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINImpl;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.ref.v3.TimedReference;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.security.execution.delimiter.ExecutionDelimiter;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.Timespan;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TimerTask;
/*     */ import javax.servlet.http.HttpServletRequest;
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
/*     */ public class DefaultController
/*     */ {
/*     */   private static class NoApplicationException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */   }
/*     */   
/*     */   private class UserSynch
/*     */     implements ContextCreator
/*     */   {
/*  95 */     private LockObjectProvider lockProvider = new LockObjectProvider();
/*     */     
/*     */     public Object createContext(LoginInfo loginInfo) {
/*  98 */       synchronized (this.lockProvider.getLockObject(loginInfo.getUser())) {
/*  99 */         if (ClientContextProvider.getInstance().isActive(loginInfo.getUser())) {
/* 100 */           return DefaultController.active;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 107 */         ClientContext ret = ClientContextProvider.getInstance().getContext(loginInfo.getUser(), true, loginInfo.isPublicAccess(), loginInfo.isSpecialAccess());
/* 108 */         DefaultController.this.sheduleAutomaticLogout(loginInfo, ret);
/* 109 */         return ret;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object replaceContext(LoginInfo loginInfo, Dispatchable disp, ClientContext context) {
/* 114 */       synchronized (this.lockProvider.getLockObject(loginInfo.getUser())) {
/* 115 */         ClientContextProvider prov = ClientContextProvider.getInstance();
/* 116 */         String user = loginInfo.getUser();
/* 117 */         ClientContext ct = prov.getContext(user);
/* 118 */         if (ct != null) {
/* 119 */           synchronized (ct.getLockObject()) {
/* 120 */             prov.invalidateSession(user);
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 128 */         context.clearAllDispatchables();
/* 129 */         ClientContext ret = ClientContextProvider.getInstance().getContext(user, true, loginInfo.isPublicAccess(), loginInfo.isSpecialAccess());
/* 130 */         ret.registerDispatchable(disp);
/* 131 */         DefaultController.this.sheduleAutomaticLogout(loginInfo, ret);
/* 132 */         return ret;
/*     */       } 
/*     */     }
/*     */     
/*     */     private UserSynch() {} }
/* 137 */   public static Integer busy = Integer.valueOf(1);
/*     */   
/* 139 */   public static Integer active = Integer.valueOf(2);
/*     */   
/* 141 */   private static final Logger log = Logger.getLogger(DefaultController.class);
/*     */   
/* 143 */   private static final Logger logPerf = Logger.getLogger("performance." + DefaultController.class.getName());
/*     */   
/* 145 */   protected static final Logger logSessions = Logger.getLogger("sessions");
/*     */   
/* 147 */   private IScout scoutChain = null;
/*     */   private PeriodicTask ptSessionMonitor;
/*     */   
/*     */   private static class AutomaticLogoutTask extends TimerTask {
/*     */     private String sessionID;
/*     */     
/*     */     public AutomaticLogoutTask(String sessionID) {
/* 154 */       this.sessionID = sessionID;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 159 */         DefaultController.log.info("checking automatic logout for session:" + this.sessionID);
/* 160 */         FrameService service = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 161 */         boolean active = service.isActive(this.sessionID);
/* 162 */         if (!active) {
/* 163 */           DefaultController.log.info("session:" + this.sessionID + " ready for logout");
/* 164 */           service.invalidateSession(this.sessionID);
/* 165 */           DefaultController.log.info("invalidated session:" + this.sessionID);
/* 166 */           DefaultController.getInstance().removeAutomaticLogoutTask(this.sessionID);
/*     */         } else {
/*     */ 
/*     */           
/*     */           try {
/*     */ 
/*     */             
/* 173 */             long lastAccess = ClientContextProvider.getInstance().getLastAccess(this.sessionID);
/*     */             
/* 175 */             long sessionTimeout = ApplicationContext.getInstance().getSessionTimeout(ClientContextProvider.getInstance().isPublicAccess(this.sessionID));
/* 176 */             long checkInterval = sessionTimeout - System.currentTimeMillis() - lastAccess + 30000L;
/* 177 */             DefaultController.log.info("session:" + this.sessionID + " is still active, rescheduling check");
/* 178 */             DefaultController.log.info("....next check in " + new Timespan(checkInterval));
/* 179 */             Util.getTimer().schedule(new AutomaticLogoutTask(this.sessionID), checkInterval);
/*     */           }
/* 181 */           catch (InvalidSessionException e) {
/* 182 */             DefaultController.log.warn("session: " + this.sessionID + " seems to be inactive, stopping rescheduling");
/*     */           } 
/*     */         } 
/* 185 */       } catch (Exception ex) {
/* 186 */         DefaultController.log.error(ex, ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 191 */       return getClass().getName() + "[" + String.valueOf(this.sessionID) + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SessionMonitor
/*     */     implements Runnable {
/* 197 */     private long oldSessionCount = 0L;
/*     */ 
/*     */     
/*     */     public synchronized void run() {
/*     */       long newCount, closedCount;
/* 202 */       synchronized (ClientContextProvider.getInstance()) {
/* 203 */         newCount = (ClientContextProvider.getInstance()).newContextCount;
/* 204 */         (ClientContextProvider.getInstance()).newContextCount = 0L;
/* 205 */         closedCount = (ClientContextProvider.getInstance()).logoutContextCount;
/* 206 */         (ClientContextProvider.getInstance()).logoutContextCount = 0L;
/*     */       } 
/* 208 */       DefaultController.logSessions.info("********");
/* 209 */       DefaultController.logSessions.info("active sessions at beginning of interval: " + this.oldSessionCount);
/* 210 */       DefaultController.logSessions.info("new sessions during interval: " + newCount);
/* 211 */       DefaultController.logSessions.info("closed sessions during interval: " + closedCount);
/* 212 */       DefaultController.logSessions.info("--> total active sessions during interval: " + (this.oldSessionCount + newCount));
/* 213 */       DefaultController.logSessions.info("********");
/* 214 */       this.oldSessionCount = this.oldSessionCount + newCount - closedCount;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 220 */   private Map automaticLogoutTaskMap = new HashMap<Object, Object>();
/*     */   
/* 222 */   private UserSynch userSynch = new UserSynch();
/*     */   
/* 224 */   private static DefaultController instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean loginsDisabled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object SYNC_DLN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TimedReference rsDisabled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized DefaultController getInstance() {
/* 262 */     if (instance == null) {
/* 263 */       instance = new DefaultController();
/*     */     }
/* 265 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableLogins() {
/* 272 */     this.loginsDisabled = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableLogins() {
/* 277 */     this.loginsDisabled = false;
/*     */   }
/*     */   
/*     */   public boolean loginsDisabled() {
/* 281 */     return this.loginsDisabled;
/*     */   }
/*     */   
/*     */   public ResultObject init(Map params) {
/* 285 */     return init(params, this.userSynch);
/*     */   }
/*     */   
/* 288 */   public DefaultController() { this.SYNC_DLN = new Object();
/*     */     
/* 290 */     this.rsDisabled = null; this.scoutChain = (IScout)new ScoutChainImpl(); this.scoutChain = (IScout)new PortalScoutMapper(this.scoutChain); this.scoutChain = (IScout)new IScoutKilledSessionWrapper(this.scoutChain); try { long interval = (Integer.parseInt(ApplicationContext.getInstance().getProperty("frame.session.monitor.interval")) * 60 * 1000); this.ptSessionMonitor = new PeriodicTask(new SessionMonitor(), interval); this.ptSessionMonitor.start(); } catch (Exception e) { log.error("unable to start session monitor - error:" + e, e); }  try { log.debug("trying to start LogMailer"); LogMailer.getInstance().start(); log.debug("started LogMailer"); } catch (Exception e) { log.error("unable to start LogMailer - error:" + e, e); }  this.loginsDisabled = false; try { if (ConfigurationUtil.isTrue("frame.login.disabled", (Configuration)ApplicationContext.getInstance())) { this.loginsDisabled = true; log.info("*********************************************************"); log.info("*** USER LOGINS ARE DISABLED, do not forget to UNLOCK ***"); log.info("*********************************************************"); }
/*     */        }
/*     */     catch (Exception e) { log.error("unable to determine login lock status, allowing logins - exception: " + e, e); }
/* 293 */      } private ResultObject getDisabledLoginResult() throws Exception { synchronized (this.SYNC_DLN) {
/* 294 */       ResultObject rs = (this.rsDisabled != null) ? (ResultObject)this.rsDisabled.get() : null;
/* 295 */       if (rs == null) {
/* 296 */         ResultObject.FileProperties props = new ResultObject.FileProperties();
/*     */         
/* 298 */         String file = ConfigurationServiceProvider.getService().getProperty("frame.login.disabled.login.notification.file");
/* 299 */         FileInputStream fis = new FileInputStream(file);
/*     */         
/*     */         try {
/* 302 */           props.data = StreamUtil.readFully(fis);
/*     */         } finally {
/* 304 */           fis.close();
/*     */         } 
/* 306 */         props.filename = (new File(file)).getName();
/* 307 */         if (props.filename.endsWith("html")) {
/* 308 */           props.mime = "text/html;charset=utf-8";
/*     */         } else {
/* 310 */           props.mime = "text/plain;charset=utf-8";
/*     */         } 
/* 312 */         props.inline = true;
/*     */         
/* 314 */         rs = new ResultObject(13, false, false, props);
/* 315 */         this.rsDisabled = new TimedReference(rs, 10000L);
/*     */       } 
/* 317 */       return rs;
/*     */     }  }
/*     */ 
/*     */   
/*     */   private boolean checkLimitedLogin(Map params) {
/* 322 */     if (ConfigurationUtil.getBoolean("frame.login.token.required", (Configuration)ApplicationContext.getInstance()).booleanValue()) {
/* 323 */       String token = (String)params.get("login.token");
/* 324 */       if (!Util.isNullOrEmpty(token)) {
/* 325 */         return ExecutionDelimiter.check("login.token", token);
/*     */       }
/* 327 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 331 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultObject init(Map params, ContextCreator contextCreator) {
/* 336 */     StopWatch sw = StopWatch.getInstance().start();
/* 337 */     ResultObject result = null;
/*     */     try {
/* 339 */       HttpServletRequest request = (HttpServletRequest)params.get("request");
/*     */       
/* 341 */       LoginInfo loginInfo = null;
/*     */       try {
/* 343 */         loginInfo = this.scoutChain.getLoginInfo(params);
/* 344 */       } catch (ScoutException e) {
/* 345 */         return displayMessage(params, e.getMessage());
/* 346 */       } catch (InteractionException e) {
/* 347 */         return e.getResponse();
/*     */       } 
/*     */       
/* 350 */       if (this.loginsDisabled && !loginInfo.getT2WGroup().equalsIgnoreCase("admin")) {
/* 351 */         return getDisabledLoginResult();
/*     */       }
/*     */       
/* 354 */       if (loginInfo == null || !loginInfo.isAuthorized() || (!loginInfo.isSpecialAccess() && !checkLimitedLogin(params))) {
/*     */         try {
/* 356 */           LoginLogFacade.getInstance().add((LoginLog.Entry)new LoginInfoAdapter(loginInfo, request, false));
/* 357 */         } catch (Exception e) {
/* 358 */           log.warn("...failed to log login request - exception:" + e, e);
/*     */         } 
/* 360 */         if (loginInfo != null) {
/* 361 */           LoginMonitor.getInstance().add(loginInfo, false);
/*     */         }
/* 363 */         StringBuffer tmp = new StringBuffer("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><span style=\"text-align: center; width=100%\"><b>{MESSAGE}</b></span></body></html>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 369 */         StringUtilities.replace(tmp, "{MESSAGE}", ApplicationContext.getInstance().getMessage((loginInfo != null) ? loginInfo.getLocale() : request.getLocale(), "frame.invalid.session", "Login denied - invalid login information or invalid session."));
/* 370 */         result = new ResultObject(0, 404, tmp.toString());
/*     */       } else {
/* 372 */         Object createRes = contextCreator.createContext(loginInfo);
/*     */         
/* 374 */         if (createRes.equals(active)) {
/* 375 */           synchronized (this) {
/* 376 */             result = showKillDialog(loginInfo, request, params);
/*     */           } 
/*     */         } else {
/*     */           
/*     */           try {
/* 381 */             LoginLogFacade.getInstance().add((LoginLog.Entry)new LoginInfoAdapter(loginInfo, request, true));
/* 382 */           } catch (Exception e) {
/* 383 */             log.warn("...unable to log login request - exception: " + e, e);
/*     */           } 
/*     */           
/* 386 */           ClientContext context = (ClientContext)createRes;
/*     */           
/* 388 */           HttpContext.getInstance(context).setRequestScheme(request.getScheme());
/*     */           
/* 390 */           String path = (String)params.get("preset_context_path");
/* 391 */           if (path == null) {
/* 392 */             path = request.getContextPath();
/*     */           }
/* 394 */           context.setRequestURL(path);
/*     */           
/* 396 */           SharedContext sc = context.getSharedContext();
/* 397 */           sc.setLoginInfo(loginInfo);
/*     */ 
/*     */ 
/*     */           
/* 401 */           context.setLocale(loginInfo.getLocale());
/* 402 */           sc.setCountry(loginInfo.getCountry());
/* 403 */           sc.setUsrGroup2ManufMap(loginInfo.getGroup2ManufMap());
/* 404 */           if (loginInfo.getDealerCode() != null) {
/* 405 */             sc.setObject(DealerCode.class, loginInfo.getDealerCode());
/*     */           }
/*     */           
/* 408 */           log.debug("setting salesmake ...");
/* 409 */           Collection availableSalesmakes = VCFacade.getInstance(context).getSalesmakeDomain();
/* 410 */           if (availableSalesmakes.size() > 0) {
/* 411 */             String sm = context.getSharedContext().getDefaultSalesmake();
/* 412 */             Make defaultMake = VCFacade.toMake(sm);
/* 413 */             if (defaultMake == null || !availableSalesmakes.contains(defaultMake)) {
/* 414 */               log.debug("...stored default salesmake: " + String.valueOf(sm) + " not supported, using application default");
/* 415 */               sm = ApplicationContext.getInstance().getProperty("frame.application.default.salesmake");
/* 416 */               defaultMake = VCFacade.toMake(sm);
/* 417 */               if (defaultMake == null || !availableSalesmakes.contains(defaultMake)) {
/* 418 */                 log.debug("...application default salesmake: " + String.valueOf(sm) + " is not supported, using first supported");
/* 419 */                 defaultMake = (Make)CollectionUtil.getFirst(availableSalesmakes);
/*     */               } 
/*     */             } 
/* 422 */             log.debug("...salesmake is <" + defaultMake + ">");
/* 423 */             VCFacade.getInstance(context).setSalesmake(defaultMake);
/*     */           } else {
/* 425 */             log.debug("...empty salesmake domain, skipping");
/*     */           } 
/*     */           
/* 428 */           if (!Util.isNullOrEmpty(loginInfo.getRequestedVC())) {
/* 429 */             VIN vin = null;
/* 430 */             IConfiguration cfg = null;
/*     */             
/* 432 */             String[] parts = loginInfo.getRequestedVC().split("@");
/* 433 */             if (parts.length > 0) {
/* 434 */               VINImpl vINImpl; if (!Util.isNullOrEmpty(parts[0])) {
/* 435 */                 vINImpl = new VINImpl(parts[0]);
/*     */               }
/* 437 */               if (parts.length > 1 && !Util.isNullOrEmpty(parts[1]))
/*     */               {
/* 439 */                 cfg = VehicleConfigurationUtil.parse(parts[1]);
/*     */               }
/* 441 */               VCFacade.getInstance(context).storeCfg(cfg, (VIN)vINImpl);
/*     */             } 
/*     */           } 
/*     */           
/* 445 */           log.debug("determining the default application (visual service)");
/*     */           
/* 447 */           VisualModule defaultModule = null;
/*     */ 
/*     */           
/* 450 */           List visualModules = VisualModuleProvider.getInstance(context).getVisualModules();
/* 451 */           if (visualModules.size() == 0) {
/* 452 */             log.error("unable to determine any valid visual service (application) for user " + context.getSessionID());
/* 453 */             log.error("....please check ACL and configuration");
/* 454 */             throw new NoApplicationException();
/*     */           } 
/*     */           
/* 457 */           if (loginInfo.getRequestedModule() != null) {
/* 458 */             defaultModule = VisualModuleProvider.getInstance(context).getVisualModule(loginInfo.getRequestedModule());
/*     */             try {
/* 460 */               if (defaultModule.getUI(context.getSessionID(), null) == null) {
/* 461 */                 log.warn("unable to retrieve UI of reqeusted module " + loginInfo.getRequestedModule() + ", ignoring request");
/* 462 */                 defaultModule = null;
/*     */               } 
/* 464 */             } catch (Exception e) {
/* 465 */               log.warn("unable to retrieve UI of requested module " + loginInfo.getRequestedModule() + " ignoring request - exception: ", e);
/* 466 */               defaultModule = null;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 471 */             String defaultType = ApplicationContext.getInstance().getProperty("frame.application.default.service.type");
/* 472 */             log.debug("...default application (service type) should be (according to configuration): " + String.valueOf(defaultType));
/* 473 */             defaultModule = VisualModuleProvider.getInstance(context).getVisualModule(defaultType);
/*     */           } 
/* 475 */           if (defaultModule == null) {
/* 476 */             log.debug("....using first service, that the user is allowed to access");
/* 477 */             defaultModule = (VisualModule)CollectionUtil.getFirst(visualModules);
/* 478 */             log.debug("...using service: " + String.valueOf(defaultModule));
/*     */           } 
/*     */           
/* 481 */           log.debug("retrieving ui code from default application");
/* 482 */           Object tmp = defaultModule.getUI(context.getSessionID(), params);
/* 483 */           if (tmp instanceof ResultObject) {
/* 484 */             result = (ResultObject)tmp;
/*     */           } else {
/* 486 */             result = new ResultObject(0, tmp);
/*     */           } 
/*     */           try {
/* 489 */             log.debug("updating login monitor with " + String.valueOf(loginInfo));
/* 490 */             LoginMonitor.getInstance().add(loginInfo, true);
/* 491 */           } catch (Exception e) {
/* 492 */             log.warn("unable to update login monitor - error:" + e + ", skipping");
/*     */           } 
/*     */           
/* 495 */           log.debug("checking messages for default application ....");
/*     */           
/* 497 */           List<IMessage> messages = MsgFacade.getInstance(context).getMessages(defaultModule.getType());
/* 498 */           for (int i = messages.size(); i > 0; i--) {
/* 499 */             IMessage msg = messages.get(i - 1);
/* 500 */             MsgUIDialog dialog = MsgUIDialog.create(context, msg, result);
/* 501 */             result = new ResultObject(0, dialog.getHtmlCode(params));
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 506 */           if (ModuleAccessPermission.getInstance(context).check("news")) {
/* 507 */             log.debug("checking for unread news");
/*     */             try {
/* 509 */               NewsService nmi = NewsServiceProvider.getInstance().getService();
/* 510 */               if (nmi.containsNewItems(context.getSessionID()).booleanValue()) {
/* 511 */                 log.debug("unread news found , hooking ui code with news dialog");
/* 512 */                 final ResultObject defaultUI = result;
/* 513 */                 ModifiedNewsDialog mnDialog = new ModifiedNewsDialog(context)
/*     */                   {
/*     */                     protected ResultObject onClose(Map submitParams) {
/* 516 */                       return defaultUI;
/*     */                     }
/*     */                   };
/* 519 */                 result = new ResultObject(0, mnDialog.getHtmlCode(params));
/*     */               } 
/* 521 */             } catch (Exception e) {
/* 522 */               log.warn("unable to check for modified news -error:" + e, e);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 527 */           Collection loginDialogProviders = ConfiguredServiceProvider.getInstance().getServices(LoginDialogProvider.class);
/* 528 */           for (Iterator<LoginDialogProvider> iter = loginDialogProviders.iterator(); iter.hasNext();) {
/*     */             try {
/* 530 */               LoginDialogProvider provider = iter.next();
/* 531 */               LoginDialogAccess dialogAccess = provider.getLoginDialogAccess();
/* 532 */               if (dialogAccess.isAvailable(context)) {
/* 533 */                 log.debug("mounting login dialog from provider: " + String.valueOf(provider));
/* 534 */                 result = new ResultObject(0, dialogAccess.getDialogCode(params, context, result));
/*     */               } 
/* 536 */             } catch (Exception e) {
/* 537 */               log.error("unable to access login dialog, skipping - exception: " + e, e);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 542 */           if (!context.isPublicAccess()) {
/*     */             
/* 544 */             Boolean check = null;
/*     */ 
/*     */             
/*     */             try {
/* 548 */               check = context.getSharedContext().checkPlugins();
/* 549 */             } catch (Exception e) {
/* 550 */               log.warn("unable to detect setting for plugin check");
/*     */             } 
/*     */             
/* 553 */             if (check != null && !check.booleanValue()) {
/* 554 */               log.debug("plugin checking is disabled by user");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 563 */                 Number ts = (Number)context.getSharedContext().getPersistentObject("plugin.check.ts");
/* 564 */                 TypeDecorator td = new TypeDecorator((Configuration)ApplicationContext.getInstance());
/* 565 */                 Number tsCurrent = td.getNumber("frame.plugin.check.timestamp");
/* 566 */                 if (tsCurrent != null && (ts == null || ts.longValue() < tsCurrent.longValue())) {
/* 567 */                   check = Boolean.TRUE;
/* 568 */                   log.debug("...overwriting");
/* 569 */                   context.getSharedContext().setPersistentObject("plugin.check.ts", tsCurrent);
/*     */                 } 
/* 571 */               } catch (Exception e) {
/* 572 */                 log.error("unable to determine plugin.check.timestamp difference -error:" + e, e);
/*     */               } 
/*     */             } 
/*     */             
/* 576 */             if (check == null || check.booleanValue()) {
/* 577 */               final ResultObject defaultUI = result;
/* 578 */               PluginCheckDialog pcd = new PluginCheckDialog(context)
/*     */                 {
/*     */                   protected ResultObject onFinished() {
/* 581 */                     return defaultUI;
/*     */                   }
/*     */                 };
/*     */               
/* 585 */               result = new ResultObject(0, pcd.getHtmlCode(params));
/*     */             } 
/*     */           } 
/*     */           
/* 589 */           long currentET = sw.stop();
/* 590 */           logPerf.info("login sequence done after :" + currentET + " ms");
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 597 */       return result;
/* 598 */     } catch (NoApplicationException e) {
/* 599 */       return onNoApplication(params);
/* 600 */     } catch (Throwable t) {
/* 601 */       log.error("unable to login - exception:" + t, t);
/* 602 */       throw new RuntimeException(t);
/*     */     } finally {
/* 604 */       StopWatch.freeInstance(sw);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeAutomaticLogoutTask(String user) {
/* 612 */     synchronized (this.automaticLogoutTaskMap) {
/* 613 */       this.automaticLogoutTaskMap.remove(user);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sheduleAutomaticLogout(LoginInfo loginInfo, ClientContext context) {
/* 621 */     AutomaticLogoutTask automaticLogoutTask = new AutomaticLogoutTask(context.getSessionID());
/* 622 */     AutomaticLogoutTask oldLogoutTask = null;
/* 623 */     synchronized (this.automaticLogoutTaskMap) {
/* 624 */       oldLogoutTask = this.automaticLogoutTaskMap.put(loginInfo.getUser(), automaticLogoutTask);
/*     */     } 
/* 626 */     if (oldLogoutTask != null) {
/* 627 */       log.debug("cancelling old logout task: " + oldLogoutTask);
/* 628 */       oldLogoutTask.cancel();
/*     */     } 
/*     */     
/* 631 */     long checkInterval = ApplicationContext.getInstance().getSessionTimeout(loginInfo.isPublicAccess());
/* 632 */     log.debug("scheduling automatic logout task " + automaticLogoutTask);
/* 633 */     Util.getTimer().schedule(automaticLogoutTask, checkInterval + 30000L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ResultObject showKillDialog(final LoginInfo loginInfo, HttpServletRequest request, final Map params) {
/* 639 */     final ClientContext tmpContext = ClientContextProvider.getInstance().getTmpContext("tks-" + loginInfo.getUser() + ApplicationContext.getInstance().createID());
/*     */     try {
/* 641 */       tmpContext.setLocale(loginInfo.getLocale());
/* 642 */     } catch (Exception e) {
/* 643 */       log.error("unable to set locale for tmp context");
/*     */     } 
/*     */     
/* 646 */     String path = request.getContextPath();
/* 647 */     tmpContext.setRequestURL(path);
/* 648 */     final String contextPath = request.getContextPath();
/*     */     
/* 650 */     ContextKillDialog dialog = new ContextKillDialog(tmpContext)
/*     */       {
/*     */         protected ResultObject onOK(Map submitParams) {
/* 653 */           Map<Object, Object> newParams = new HashMap<Object, Object>(params);
/* 654 */           newParams.put("loginInfo", loginInfo);
/* 655 */           newParams.put("preset_context_path", contextPath);
/* 656 */           newParams.put("request", submitParams.get("request"));
/* 657 */           final ContextKillDialog dlg = this;
/* 658 */           DefaultController.ContextCreator cC = new DefaultController.ContextCreator() {
/*     */               public Object createContext(LoginInfo loginInfo) {
/* 660 */                 return DefaultController.this.userSynch.replaceContext(loginInfo, (Dispatchable)dlg, tmpContext);
/*     */               }
/*     */             };
/*     */           
/* 664 */           return DefaultController.this.init(newParams, cC);
/*     */         }
/*     */       };
/* 667 */     return new ResultObject(0, dialog.getHtmlCode(params));
/*     */   }
/*     */   
/*     */   public ResultObject onInvalidDispatchTarget(Map parameters) throws Exception {
/* 671 */     HttpServletRequest request = (HttpServletRequest)parameters.get("request");
/* 672 */     StringBuffer tmp = new StringBuffer("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><span style=\"text-align: center; width=100%\"><b>{MESSAGE}</b></span></body></html>");
/* 673 */     StringUtilities.replace(tmp, "{MESSAGE}", ApplicationContext.getInstance().getMessage(request.getLocale(), "frame.invalid.session", "Invalid session - probably expired."));
/* 674 */     return new ResultObject(0, 404, tmp.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private ResultObject onNoApplication(Map parameters) {
/* 679 */     HttpServletRequest request = (HttpServletRequest)parameters.get("request");
/* 680 */     StringBuffer tmp = new StringBuffer("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><span style=\"text-align: center; width=100%\"><b>{MESSAGE}</b></span></body></html>");
/* 681 */     StringUtilities.replace(tmp, "{MESSAGE}", ApplicationContext.getInstance().getMessage(request.getLocale(), "frame.no.application.available", "no application available !!"));
/* 682 */     return new ResultObject(0, 501, tmp.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultObject displayServerBusyMessage() throws Exception {
/* 687 */     StringBuffer tmp = new StringBuffer("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><span style=\"text-align: center; width=100%\"><b>{MESSAGE}</b></span></body></html>");
/*     */ 
/*     */ 
/*     */     
/* 691 */     StringUtilities.replace(tmp, "{MESSAGE}", "TIS2WEB Servers are busy. Please, try again in a few minutes.");
/* 692 */     return new ResultObject(0, 503, tmp.toString());
/*     */   }
/*     */   
/*     */   private ResultObject displayMessage(Map parameters, String message) {
/* 696 */     HttpServletRequest request = (HttpServletRequest)parameters.get("request");
/* 697 */     StringBuffer tmp = new StringBuffer("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><span style=\"text-align: center; width=100%\"><b>{MESSAGE}</b></span></body></html>");
/* 698 */     StringUtilities.replace(tmp, "{MESSAGE}", ApplicationContext.getInstance().getMessage(request.getLocale(), message, "TIS2WEB not available (" + message + ")"));
/* 699 */     return new ResultObject(0, 500, tmp.toString());
/*     */   }
/*     */   
/*     */   private static interface ContextCreator {
/*     */     Object createContext(LoginInfo param1LoginInfo);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\DefaultController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */