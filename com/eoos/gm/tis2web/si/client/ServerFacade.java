/*    */ package com.eoos.gm.tis2web.si.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.common.TaskExecutionClientFactory;
/*    */ import com.eoos.gm.tis2web.frame.dls.SessionKey;
/*    */ import com.eoos.gm.tis2web.si.client.model.IServer;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ServerFacade
/*    */   implements IServer {
/* 10 */   private static final Logger log = Logger.getLogger(ServerFacade.class);
/*    */   private SessionKey sessionKey;
/*    */   
/*    */   public ServerFacade(SessionKey sessionKey) {
/* 14 */     this.sessionKey = sessionKey;
/*    */   }
/*    */   
/*    */   public byte[] getScreenData(String identifier) throws Exception {
/* 18 */     log.debug("retrieving screen data for " + identifier);
/* 19 */     METask task = new METask(this.sessionKey, "getScreenData", new Object[] { identifier });
/* 20 */     Object obj = TaskExecutionClientFactory.createTaskExecutionClient().execute(task);
/* 21 */     rethrowException(obj);
/* 22 */     return (byte[])obj;
/*    */   }
/*    */   
/*    */   private void rethrowException(Object obj) throws Exception {
/* 26 */     if (obj instanceof Exception)
/* 27 */       throw (Exception)obj; 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\ServerFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */