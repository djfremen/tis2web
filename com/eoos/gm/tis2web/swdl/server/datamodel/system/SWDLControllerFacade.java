/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.ServerError;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.Command;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SWDLControllerFacade
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(SWDLControllerFacade.class);
/*     */   
/*  28 */   private static SWDLControllerFacade instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized SWDLControllerFacade getInstance() {
/*  35 */     if (instance == null) {
/*  36 */       instance = new SWDLControllerFacade();
/*     */     }
/*  38 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultObject execute(Map params) {
/*  43 */     Object resultObj = null;
/*  44 */     Command command = null;
/*  45 */     ByteArrayInputStream bais = new ByteArrayInputStream(StringUtilities.hexToBytes((String)params.get("command")));
/*     */     try {
/*  47 */       ObjectInputStream ois = new ObjectInputStream(bais);
/*  48 */       command = (Command)ois.readObject();
/*  49 */       ois.close();
/*  50 */     } catch (Exception e) {
/*  51 */       log.error("Exception when unpack the command;  " + e, e);
/*  52 */       resultObj = new ServerError(2);
/*  53 */       return packResponse(resultObj);
/*     */     } 
/*  55 */     String sessionID = (String)command.getParameter("sessionid");
/*  56 */     log.debug("received command for session:" + sessionID);
/*     */     
/*  58 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/*  59 */     if (context == null) {
/*  60 */       log.debug("no valid session for " + sessionID);
/*  61 */       Boolean debug = (Boolean)command.getParameter("debugsession");
/*  62 */       if (debug != null && debug.booleanValue()) {
/*  63 */         context = ClientContextProvider.getInstance().getContext(sessionID, true);
/*  64 */         log.debug("... created debug session for " + sessionID);
/*     */       } else {
/*  66 */         log.debug("... returning null");
/*  67 */         resultObj = new ServerError(1);
/*  68 */         return packResponse(resultObj);
/*     */       } 
/*     */     } 
/*  71 */     log.debug("trying to aquire lock for session:" + sessionID);
/*  72 */     synchronized (context.getLockObject()) {
/*  73 */       log.debug("aquired lock for session:" + sessionID + " executing command");
/*  74 */       context.keepAlive();
/*  75 */       SharedContextProxy.getInstance(context).update();
/*     */       try {
/*  77 */         resultObj = SWDLController.getInstance(context).execute(command);
/*  78 */       } catch (ReInitializationException e) {
/*  79 */         log.warn("unable to execute command, because application registry has been reinitialized");
/*  80 */         log.warn("...indicating error to client");
/*  81 */         resultObj = new ServerError(6);
/*     */       } 
/*     */       
/*  84 */       log.debug("result of command execution:" + String.valueOf(resultObj));
/*     */     } 
/*  86 */     if (resultObj == null) {
/*  87 */       resultObj = new ServerError(5);
/*     */     }
/*  89 */     log.debug("returning result session:" + sessionID);
/*  90 */     return packResponse(resultObj);
/*     */   }
/*     */   
/*     */   private ResultObject packResponse(Object obj) {
/*  94 */     byte[] responseBuff = null;
/*     */     try {
/*  96 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  97 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/*  98 */       oos.writeObject(obj);
/*  99 */       responseBuff = baos.toByteArray();
/* 100 */       oos.close();
/* 101 */     } catch (Exception x) {
/* 102 */       log.error("Exception when pack the response;  " + x, x);
/* 103 */       return null;
/*     */     } 
/* 105 */     return new ResultObject(10, new PairImpl("application/octet-stream", responseBuff));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\SWDLControllerFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */