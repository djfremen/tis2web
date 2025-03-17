/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log.admin.ui.html.search.result;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.Adapter;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*    */ import com.eoos.util.DateConvert;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class EntryDisplayPopup
/*    */   extends Page {
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 21 */       template = ApplicationContext.getInstance().loadFile(EntryDisplayPopup.class, "entrydisplaypopup.html", null).toString();
/* 22 */     } catch (Exception e) {
/* 23 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/* 26 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*    */   
/* 28 */   private SPSEventLog.LoggedEntry entry = null;
/*    */   
/* 30 */   private String buttonCode = null;
/*    */   
/*    */   private EntryDisplayPopup(ClientContext context) {
/* 33 */     super(context);
/* 34 */     this.buttonCode = "<button name=\"cb\" type=\"button\" value=\"1\" onClick=\"javascript:window.close()\" >" + context.getLabel("close") + "</button>";
/*    */   }
/*    */   
/*    */   public static EntryDisplayPopup getInstance(ClientContext context) {
/* 38 */     synchronized (context.getLockObject()) {
/* 39 */       EntryDisplayPopup instance = (EntryDisplayPopup)context.getObject(EntryDisplayPopup.class);
/* 40 */       if (instance == null) {
/* 41 */         instance = new EntryDisplayPopup(context);
/* 42 */         context.storeObject(EntryDisplayPopup.class, instance);
/*    */       } 
/* 44 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setEntry(SPSEventLog.LoggedEntry entry) {
/* 49 */     this.entry = entry;
/*    */   }
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 53 */     if (this.entry == null)
/* 54 */       throw new IllegalStateException("no entry set"); 
/* 55 */     StringBuffer retValue = new StringBuffer(template);
/* 56 */     StringUtilities.replace(retValue, "{LABEL_DATE}", this.context.getLabel("date") + ":");
/* 57 */     StringUtilities.replace(retValue, "{DATE}", DateConvert.toDateString(this.entry.getTimestamp(), DATE_FORMAT));
/*    */     
/* 59 */     StringUtilities.replace(retValue, "{LABEL_EVENTNAME}", this.context.getLabel("eventname") + ":");
/* 60 */     StringUtilities.replace(retValue, "{EVENTNAME}", this.entry.getEventName());
/*    */     
/* 62 */     StringUtilities.replace(retValue, "{LABEL_ADAPTER}", this.context.getLabel("adapter") + ":");
/* 63 */     String adapter = (this.entry.getAdapter() == Adapter.GME) ? this.context.getLabel("sps.adapter.gme") : null;
/* 64 */     if (adapter == null) {
/* 65 */       adapter = (this.entry.getAdapter() == Adapter.NAO) ? this.context.getLabel("sps.adapter.nao") : this.context.getLabel("sps.adapter.global");
/*    */     }
/* 67 */     StringUtilities.replace(retValue, "{ADAPTER}", adapter);
/*    */     
/* 69 */     StringUtilities.replace(retValue, "{LABEL_NAME}", this.context.getLabel("attribute"));
/* 70 */     StringUtilities.replace(retValue, "{LABEL_VALUE}", this.context.getLabel("value"));
/*    */     
/* 72 */     List<SPSEventLog.Attribute> attributes = this.entry.getEventAttributes();
/* 73 */     for (int i = 0; i < attributes.size(); i++) {
/* 74 */       StringBuffer tmp = new StringBuffer("<tr class=\"{EVENODD}\"><td>{NAME}</td><td>{VALUE}</td></tr>{ATTRIBUTES}");
/* 75 */       SPSEventLog.Attribute attribute = attributes.get(i);
/* 76 */       StringUtilities.replace(tmp, "{EVENODD}", (i % 2 == 0) ? "even" : "odd");
/* 77 */       StringUtilities.replace(tmp, "{NAME}", attribute.getName());
/* 78 */       StringUtilities.replace(tmp, "{VALUE}", attribute.getValue());
/*    */       
/* 80 */       StringUtilities.replace(retValue, "{ATTRIBUTES}", tmp);
/*    */     } 
/* 82 */     StringUtilities.replace(retValue, "{ATTRIBUTES}", "");
/*    */     
/* 84 */     StringUtilities.replace(retValue, "{BUTTON_CLOSE}", this.buttonCode);
/*    */     
/* 86 */     return retValue.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\admi\\ui\html\search\result\EntryDisplayPopup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */