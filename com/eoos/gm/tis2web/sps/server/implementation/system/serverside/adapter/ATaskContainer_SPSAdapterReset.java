/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.AdministrativeTask;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ATaskContainer_SPSAdapterReset
/*    */   implements AdministrativeTask.Container
/*    */ {
/*    */   public List getTasks(ClientContext context) {
/* 13 */     List<ATask_AdapterReset> retValue = new LinkedList();
/* 14 */     for (Iterator<SchemaAdapter> iter = ServiceResolution.getInstance().getSchemaAdapters().iterator(); iter.hasNext(); ) {
/* 15 */       SchemaAdapter adapter = iter.next();
/* 16 */       retValue.add(new ATask_AdapterReset(ServiceResolution.getInstance().getIdentifier(adapter)));
/*    */     } 
/* 18 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\ATaskContainer_SPSAdapterReset.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */