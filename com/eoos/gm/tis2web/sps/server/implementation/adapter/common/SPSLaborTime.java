/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.sids.service.ServiceIDService;
/*    */ import com.eoos.gm.tis2web.sids.service.ServiceIDServiceProvider;
/*    */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.lt.SPSEvent;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.lt.SPSLaborTimeConfiguration;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSLaborTime
/*    */ {
/* 25 */   private static final Logger log = Logger.getLogger(SPSLaborTime.class);
/*    */   
/*    */   protected static Map mapping;
/*    */   
/*    */   protected static void init() {
/* 30 */     mapping = new HashMap<Object, Object>();
/* 31 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.sps.labor-time.");
/* 32 */     for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/* 33 */       String key = iter.next();
/* 34 */       if (key.endsWith(".id")) {
/* 35 */         init((Configuration)subConfigurationWrapper, subConfigurationWrapper.getProperty(key));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected static void init(Configuration configuration, String region) {
/* 41 */     SubConfigurationWrapper config = new SubConfigurationWrapper(configuration, "region." + region + ".");
/* 42 */     String formula = config.getProperty("formula");
/* 43 */     String maxDownloadTime = config.getProperty("max-download-time");
/* 44 */     String maxProgrammingTime = config.getProperty("max-programming-time");
/* 45 */     String maxType4Time = config.getProperty("max-type4-time");
/* 46 */     String display = config.getProperty("display");
/* 47 */     SPSLaborTimeConfiguration lt = new SPSLaborTimeConfiguration(formula, maxDownloadTime, maxProgrammingTime, maxType4Time, display);
/* 48 */     ServiceIDService serviceIDService = ServiceIDServiceProvider.getInstance().getService();
/* 49 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)config, "sid-mapping.");
/* 50 */     for (Iterator<String> iter2 = subConfigurationWrapper1.getKeys().iterator(); iter2.hasNext(); ) {
/* 51 */       ServiceID serviceID = serviceIDService.getServiceID(subConfigurationWrapper1.getProperty(iter2.next()));
/* 52 */       mapping.put(serviceID, lt);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static synchronized SPSLaborTimeConfiguration getLaborTimeConfiguration(ServiceID sid) {
/* 57 */     if (mapping == null) {
/* 58 */       init();
/*    */     }
/* 60 */     return (SPSLaborTimeConfiguration)mapping.get(sid);
/*    */   }
/*    */   
/*    */   public static void log(List<SPSEventLog.Attribute> attributes, Value data) {
/*    */     try {
/* 65 */       Pair pair = (Pair)((ValueAdapter)data).getAdaptee();
/* 66 */       if (pair.getFirst() instanceof SPSEvent) {
/* 67 */         SPSEvent event = (SPSEvent)pair.getFirst();
/* 68 */         log(attributes, false, 0, event);
/*    */       } else {
/* 70 */         List<SPSEvent> events = (List)pair.getFirst();
/* 71 */         for (int i = 0; i < events.size(); i++) {
/* 72 */           log(attributes, (events.size() > 1), i, events.get(i));
/*    */         }
/*    */       } 
/* 75 */       String ltfactor = (String)pair.getSecond();
/* 76 */       attributes.add(new SPSEventLog.Attribute("laboraddtime", ltfactor));
/* 77 */     } catch (Exception e) {
/* 78 */       log.error("failed to record labor time tracking data", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void log(List<SPSEventLog.Attribute> attributes, boolean sequence, int i, SPSEvent event) {
/* 83 */     String postfix = sequence ? ("." + i) : "";
/* 84 */     attributes.add(new SPSEventLog.Attribute("serverpctime" + postfix, Integer.toString(event.getActualDownloadTime())));
/* 85 */     if (event.getActualProgrammingTime() != 0) {
/* 86 */       attributes.add(new SPSEventLog.Attribute("progtime" + postfix, Integer.toString(event.getActualProgrammingTime())));
/*    */     }
/* 88 */     if (event.getActualType4Time() != 0)
/* 89 */       attributes.add(new SPSEventLog.Attribute("type4time" + postfix, Integer.toString(event.getActualType4Time()))); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSLaborTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */