/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.VIT1HistoryRequest;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.Adapter;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.Attachment;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.EntryImpl;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLogFacade;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.TypeDecorator;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SPSEvents
/*    */ {
/* 30 */   private static final Logger log = Logger.getLogger(SPSEvents.class);
/*    */   
/* 32 */   protected static final DateFormat DFORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
/*    */   
/*    */   public static void logUnfinishedSession(Adapter adapter, String sessionID, SPSSession session) {
/* 35 */     List<SPSEventLog.Attribute> attributes = Collections.singletonList(new SPSEventLog.Attribute("sessionID", sessionID));
/* 36 */     EntryImpl logEntry = EntryImpl.create(adapter, "sequence.start", attributes);
/* 37 */     SPSEventLogFacade.getInstance().add((SPSEventLog.Entry)logEntry, null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean logExtVIT1() {
/* 42 */     return TypeDecorator.getBoolean((Configuration)ApplicationContext.getInstance(), "component.sps.event.log.ext.vit1.logging", true);
/*    */   }
/*    */   
/*    */   private static String asString(Pair[] vitEntries) {
/* 46 */     if (vitEntries == null || vitEntries.length == 0) {
/* 47 */       return null;
/*    */     }
/* 49 */     StringWriter writer = new StringWriter();
/* 50 */     PrintWriter pw = new PrintWriter(writer);
/* 51 */     for (int i = 0; i < vitEntries.length; i++) {
/* 52 */       Pair pair = vitEntries[i];
/* 53 */       pw.print(pair.getFirst());
/* 54 */       pw.print("=");
/* 55 */       pw.println(pair.getSecond());
/*    */     } 
/* 57 */     return writer.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   protected static Attachment[] getVIT1Attachments(AttributeValueMap data) throws RequestException {
/* 62 */     Attachment[] attachments = null;
/* 63 */     if (logExtVIT1()) {
/* 64 */       log.debug("...creating attachments");
/* 65 */       Map vit1History = null;
/* 66 */       if ((vit1History = (Map)AVUtil.accessValue(data, CommonAttribute.VIT1_HISTORY)) == null) {
/* 67 */         log.debug("...requesting vit1 history");
/* 68 */         throw new RequestException(new VIT1HistoryRequest());
/*    */       } 
/* 70 */       log.debug("...retrieved vit1 history, creating attachemnt(s)");
/* 71 */       List<Attachment> listAttachments = new LinkedList();
/* 72 */       for (Iterator<Map.Entry> iter = vit1History.entrySet().iterator(); iter.hasNext(); ) {
/* 73 */         Map.Entry entry = iter.next();
/* 74 */         String ecuAddress = (String)entry.getKey();
/* 75 */         List<Pair[]> vit1List = (List)entry.getValue();
/* 76 */         for (int i = 0; i < vit1List.size(); i++) {
/* 77 */           Pair[] entries = vit1List.get(i);
/* 78 */           String strVIT = asString(entries);
/* 79 */           if (strVIT != null) {
/* 80 */             Attachment.Key key = Attachment.Key.forString("VIT1(" + ecuAddress + ")-" + i);
/* 81 */             listAttachments.add(new Attachment(key, strVIT));
/*    */           } else {
/*    */             
/* 84 */             log.warn("...skipping empty VIT");
/*    */           } 
/*    */         } 
/*    */       } 
/* 88 */       attachments = listAttachments.<Attachment>toArray(new Attachment[listAttachments.size()]);
/*    */     } else {
/*    */       
/* 91 */       log.debug("...extended vit1 logging disabled");
/*    */     } 
/* 93 */     return attachments;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSEvents.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */