/*     */ package com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.gtwo.RootDispatcher;
/*     */ import com.eoos.gm.tis2web.si.implementation.log.SIEventLogFacade;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.Data;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.DataProxy;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.gtwo.ResponseData;
/*     */ import com.eoos.html.gtwo.servlet.RequestHandler;
/*     */ import com.eoos.html.gtwo.servlet.dispatching.Dispatcher;
/*     */ import com.eoos.html.gtwo.util.BinaryResponseDataWriter;
/*     */ import com.eoos.html.gtwo.util.ResponseDataImpl;
/*     */ import java.io.IOException;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class StatContRequestHandler
/*     */   implements RequestHandler
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(StatContRequestHandler.class);
/*     */   
/*     */   private ClientContext context;
/*  28 */   private Dispatcher parent = (Dispatcher)RootDispatcher.getInstance();
/*     */   
/*  30 */   private static BinaryResponseDataWriter binaryWriter = new BinaryResponseDataWriter();
/*     */   
/*     */   private StatContRequestHandler(ClientContext context) {
/*  33 */     this.context = context;
/*  34 */     this.parent = (Dispatcher)RootDispatcher.getInstance();
/*  35 */     this.parent.registerHandler(this);
/*  36 */     context.addLogoutListener(new ClientContextBase.LogoutListener()
/*     */         {
/*     */           public void onLogout() {
/*  39 */             StatContRequestHandler.this.parent.unregisterHandler(StatContRequestHandler.this);
/*  40 */             StatContRequestHandler.log.debug("unregistered " + StatContRequestHandler.this + " from RootDispatcher");
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPath() {
/*  48 */     StringBuffer path = new StringBuffer(this.parent.getDispatchPath(this));
/*  49 */     path.append(this.context.getSessionID());
/*  50 */     return path.toString();
/*     */   }
/*     */   
/*     */   public static StatContRequestHandler getInstance(ClientContext context) {
/*  54 */     synchronized (context.getLockObject()) {
/*  55 */       StatContRequestHandler instance = (StatContRequestHandler)context.getObject(StatContRequestHandler.class);
/*  56 */       if (instance == null) {
/*  57 */         instance = new StatContRequestHandler(context);
/*  58 */         context.storeObject(StatContRequestHandler.class, instance);
/*     */       } 
/*  60 */       return instance;
/*     */     } 
/*     */   }
/*     */   public void handle(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/*     */       ResponseDataImpl responseDataImpl;
/*  66 */       String path = request.getPathInfo();
/*  67 */       path = path.substring(this.parent.getDispatchPath(this).length());
/*     */ 
/*     */       
/*  70 */       int index = path.indexOf("/");
/*  71 */       if (index == -1) {
/*  72 */         throw new IllegalStateException();
/*     */       }
/*     */ 
/*     */       
/*  76 */       String sessionID = path.substring(0, index);
/*  77 */       if (!this.context.getSessionID().equalsIgnoreCase(sessionID)) {
/*  78 */         throw new IllegalStateException("");
/*     */       }
/*     */       
/*  81 */       path = path.substring(index + 1);
/*     */       
/*  83 */       DataProxy dataProxy = new DataProxy(path);
/*  84 */       log.debug("serving request for :" + dataProxy);
/*     */       
/*  86 */       if (dataProxy.isTraceableDocument()) {
/*  87 */         synchronized (this.context.getLockObject()) {
/*  88 */           StatContContext sccontext = StatContContext.getInstance(this.context);
/*     */ 
/*     */           
/*  91 */           boolean addToHistory = true;
/*     */ 
/*     */           
/*     */           try {
/*  95 */             if (sccontext.isDefaultDocument((Data)dataProxy)) {
/*  96 */               addToHistory = false;
/*  97 */               Data backDocument = sccontext.popBackDocument();
/*  98 */               if (backDocument != null) {
/*     */                 
/* 100 */                 log.debug("...sending redirect request for :" + backDocument);
/* 101 */                 response.sendRedirect(request.getContextPath() + request.getServletPath() + getPath() + "/" + backDocument.getFilename() + "?back");
/*     */                 return;
/*     */               } 
/*     */             } 
/* 105 */           } catch (NoSuchElementException e) {}
/*     */ 
/*     */           
/* 108 */           if (addToHistory) {
/* 109 */             sccontext.addToHistory((Data)dataProxy);
/* 110 */             log.debug("... added " + dataProxy + " to history");
/*     */           } 
/*     */ 
/*     */           
/* 114 */           SIEventLogFacade.getInstance().createEntryStatic(dataProxy.getFilename(), this.context);
/* 115 */           sccontext.setCurrent((Data)dataProxy);
/*     */         } 
/*     */       }
/*     */       
/* 119 */       ResponseData _data = null;
/* 120 */       String type = "application/octet-stream";
/* 121 */       if (dataProxy.getFilename().endsWith(".html") || dataProxy.getFilename().endsWith(".htm")) {
/* 122 */         type = "text/html";
/* 123 */         responseDataImpl = new ResponseDataImpl(type, dataProxy.toByteArray());
/* 124 */       } else if (dataProxy.getFilename().endsWith(".css")) {
/* 125 */         type = "text/css";
/* 126 */         responseDataImpl = new ResponseDataImpl(1, type, dataProxy.toByteArray());
/*     */       }
/* 128 */       else if (dataProxy.getFilename().endsWith(".gif")) {
/* 129 */         type = "image/gif";
/* 130 */         responseDataImpl = new ResponseDataImpl(1, type, dataProxy.toByteArray());
/*     */       } else {
/* 132 */         responseDataImpl = new ResponseDataImpl(type, dataProxy.toByteArray());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 137 */       binaryWriter.write(request, response, responseDataImpl);
/*     */     }
/* 139 */     catch (Exception e) {
/* 140 */       log.error("unable to handle request - exception :" + e, e);
/*     */       try {
/* 142 */         response.sendError(500);
/* 143 */       } catch (IOException e1) {
/* 144 */         log.error("unable to send error response - exception: " + e1, e1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     return getClass().getName() + "[context: " + String.valueOf(this.context) + "]";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcont\datamodel\system\StatContRequestHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */