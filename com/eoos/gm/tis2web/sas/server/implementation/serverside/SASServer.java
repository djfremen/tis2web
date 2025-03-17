/*     */ package com.eoos.gm.tis2web.sas.server.implementation.serverside;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.InvalidSessionException;
/*     */ import com.eoos.gm.tis2web.frame.export.common.hwk.NoHWKPermission;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.hwk.HWKReplacementProvider;
/*     */ import com.eoos.gm.tis2web.registration.standalone.authorization.service.SoftwareKeyProvider;
/*     */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sas.common.model.SalesOrganisation;
/*     */ import com.eoos.gm.tis2web.sas.common.model.exception.UnprivilegedUserException;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityRequestHandler;
/*     */ import com.eoos.gm.tis2web.sas.common.system.ISASServer;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.scan100.Tool_Scan100Impl;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.Tool_Tech2Impl;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.test.TestToolExceptionOnRead;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.test.TestToolExceptionOnWrite;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.test.TestToolNoRequest;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.test.TestToolSCA;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.test.TestToolSKA;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.test.TestToolSSA;
/*     */ import com.eoos.gm.tis2web.sas.server.implementation.tool.test.TestToolSSASKASCA;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.util.MultitonSupport;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class SASServer
/*     */   implements ISASServer
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(SASServer.class);
/*     */   
/*  48 */   private static MultitonSupport multitonSupport = new MultitonSupport(new MultitonSupport.CreationCallback() {
/*     */         public Object createObject(final Object identifier) {
/*     */           try {
/*  51 */             ClientContext context = (ClientContext)identifier;
/*  52 */             context.addLogoutListener(new ClientContextBase.LogoutListener() {
/*     */                   public void onLogout() {
/*  54 */                     SASServer.log.debug("removing SASServer instance for context " + identifier);
/*  55 */                     SASServer.multitonSupport.removeInstance(identifier);
/*     */                   }
/*     */                 });
/*  58 */             return new SASServer(context);
/*  59 */           } catch (com.eoos.gm.tis2web.sas.common.system.ISASServer.Exception e) {
/*  60 */             SASServer.log.error("unable to create SASServer instance - exception: " + e, (Throwable)e);
/*  61 */             throw new ExceptionWrapper(e);
/*     */           } 
/*     */         }
/*     */         
/*     */         public String toString() {
/*  66 */           return "CreationCallback for SASServer instances";
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  72 */   private List tools = new LinkedList();
/*     */ 
/*     */   
/*     */   private SASServer(ClientContext context) throws ISASServer.Exception {
/*  76 */     this.context = context;
/*  77 */     this.context.addLogoutListener(new ClientContextBase.LogoutListener() {
/*     */           public void onLogout() {
/*  79 */             SASServer.this.context = null;
/*     */           }
/*     */         });
/*     */     
/*  83 */     initToolList();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initToolList() {
/*  88 */     this.tools.add(Tool_Tech2Impl.getInstance());
/*  89 */     this.tools.add(Tool_Scan100Impl.getInstance());
/*  90 */     if (ApplicationContext.getInstance().developMode()) {
/*  91 */       this.tools.add(new TestToolSCA());
/*  92 */       this.tools.add(new TestToolSKA());
/*  93 */       this.tools.add(new TestToolSSA());
/*  94 */       this.tools.add(new TestToolExceptionOnRead());
/*  95 */       this.tools.add(new TestToolExceptionOnWrite());
/*  96 */       this.tools.add(new TestToolNoRequest());
/*  97 */       this.tools.add(new TestToolSSASKASCA());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SASServer getInstance(String sessionID) throws InvalidSessionException {
/* 104 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 105 */     if (context == null) {
/* 106 */       log.warn("unable to provide instance for session " + sessionID + ", throwing InvalidSessionException");
/* 107 */       throw new InvalidSessionException(sessionID);
/*     */     } 
/* 109 */     synchronized (context.getLockObject()) {
/* 110 */       context.keepAlive();
/* 111 */       SharedContextProxy.getInstance(context).update();
/* 112 */       log.debug("returning SASServer instance for session " + sessionID);
/* 113 */       return (SASServer)multitonSupport.getInstance(context);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAccessResponse handle(SecurityAccessRequest request, String sessionID) throws SecurityRequestHandler.Exception {
/* 119 */     synchronized (this.context.getLockObject()) {
/* 120 */       log.debug("handling " + String.valueOf(request));
/* 121 */       return HandlerFacade.getInstance().handle(request, sessionID);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getTools() throws ISASServer.Exception {
/* 126 */     return this.tools;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid(HardwareKey hardwareKey) throws ISASServer.Exception {
/* 131 */     synchronized (this.context.getLockObject()) {
/*     */       
/* 133 */       log.debug("validating hardware key: " + String.valueOf(hardwareKey));
/* 134 */       boolean ret = false;
/* 135 */       if (ApplicationContext.getInstance().developMode()) {
/* 136 */         log.warn("...skipping hardware key validation (DEVELOP MODE)");
/* 137 */         ret = true;
/* 138 */       } else if (hwkNotRequired()) {
/* 139 */         log.debug("...hardware key is not required !");
/* 140 */         ret = true;
/* 141 */       } else if (hardwareKey != null) {
/* 142 */         ret = HardwareKeyValidation.getInstance().isValid(hardwareKey);
/*     */       } else {
/* 144 */         log.debug("...hardware key is null");
/* 145 */         ret = false;
/*     */       } 
/* 147 */       log.debug("....validation result: " + ret);
/* 148 */       return ret;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hwkNotRequired() {
/* 157 */     return NoHWKPermission.getInstance(this.context).check();
/*     */   }
/*     */   
/*     */   public boolean allowAccess(SalesOrganisation salesOrganisation) throws ISASServer.Exception {
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   private String trimUserName() {
/* 165 */     StringBuffer tmp = new StringBuffer(this.context.getSessionID());
/* 166 */     tmp.append("          ");
/* 167 */     return tmp.substring(0, 10);
/*     */   }
/*     */   
/*     */   public HardwareKey getHWKReplacement() throws UnprivilegedUserException {
/* 171 */     if (hwkNotRequired()) {
/* 172 */       String str; HardwareKey ret = null;
/* 173 */       log.debug("retrieving hardware key replacement for user: " + this.context);
/*     */       
/*     */       try {
/* 176 */         str = HWKReplacementProvider.getInstance().getReplacement(this.context.getSessionID());
/* 177 */       } catch (Exception e) {
/* 178 */         log.error("unable to retrieve/create HWK replacement, replacing with userid - exception:" + e, e);
/* 179 */         str = trimUserName();
/*     */       } 
/* 181 */       ret = new HardwareKey(str, str);
/* 182 */       log.debug("...returning hardware key replacement: " + ret);
/* 183 */       return ret;
/*     */     } 
/* 185 */     log.debug("...unprivileged user, throwing exception");
/* 186 */     throw new UnprivilegedUserException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHardwareID() throws ISASServer.Exception {
/* 191 */     if (ApplicationContext.getInstance().isStandalone()) {
/* 192 */       return SoftwareKeyProvider.getInstance().getService().getSubscriberID();
/*     */     }
/* 194 */     throw new IllegalArgumentException("central server installation");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\serverside\SASServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */