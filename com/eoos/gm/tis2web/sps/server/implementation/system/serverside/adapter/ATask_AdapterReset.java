/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.AdministrativeTask;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.util.Task;
/*    */ import java.net.URL;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ATask_AdapterReset
/*    */   implements AdministrativeTask {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private static final class CT_ResetAdapters implements ClusterTask {
/*    */     private static final long serialVersionUID = 1L;
/*    */     private String schemaAdapterIdentifier;
/*    */     
/*    */     public CT_ResetAdapters(String schemaAdapterIdentifier) {
/* 25 */       this.schemaAdapterIdentifier = schemaAdapterIdentifier;
/*    */     }
/*    */     
/*    */     public Object execute() {
/* 29 */       Object retValue = null;
/*    */       try {
/* 31 */         ServiceResolution.getInstance().getSchemaAdapter(this.schemaAdapterIdentifier).reset();
/* 32 */       } catch (Exception e) {
/* 33 */         retValue = e;
/*    */       } 
/* 35 */       return retValue;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 41 */   private static final Logger log = Logger.getLogger(ATask_AdapterReset.class);
/*    */   
/*    */   private String schemaAdapterIdentifier;
/*    */   
/*    */   public ATask_AdapterReset(String schemaAdapterIdentifier) {
/* 46 */     this.schemaAdapterIdentifier = schemaAdapterIdentifier;
/*    */   }
/*    */   
/*    */   public void execute(ClientContext principal) throws Exception {
/* 50 */     CT_ResetAdapters cT_ResetAdapters = new CT_ResetAdapters(this.schemaAdapterIdentifier);
/*    */     
/* 52 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)cT_ResetAdapters, principal);
/* 53 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 54 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 55 */       URL url = iter.next();
/* 56 */       if (result.getResult(url) instanceof Exception) {
/* 57 */         log.warn("failed to reset sps schema adapter (" + String.valueOf(this.schemaAdapterIdentifier) + ") - for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 61 */     if (result.getLocalResult() instanceof Exception) {
/* 62 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 68 */     String message = ApplicationContext.getInstance().getMessage(locale, "sps.reset.adapter");
/* 69 */     message = message.replaceAll("'", "''");
/* 70 */     return MessageFormat.format(message, new Object[] { String.valueOf(this.schemaAdapterIdentifier) });
/*    */   }
/*    */   
/*    */   public String getErrorMessage(Exception e, Locale locale) {
/* 74 */     String message = ApplicationContext.getInstance().getMessage(locale, "sps.reset.adapter.failed");
/* 75 */     message = message.replaceAll("'", "''");
/* 76 */     return MessageFormat.format(message, new Object[] { String.valueOf(this.schemaAdapterIdentifier) });
/*    */   }
/*    */   
/*    */   public String getSuccessMessage(Locale locale) {
/* 80 */     String message = ApplicationContext.getInstance().getMessage(locale, "sps.reset.adapter.successful");
/* 81 */     message = message.replaceAll("'", "''");
/* 82 */     return MessageFormat.format(message, new Object[] { String.valueOf(this.schemaAdapterIdentifier) });
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 86 */     return ComponentAccessPermission.getInstance(context).check("admin.sps.adapter.reset");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\ATask_AdapterReset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */