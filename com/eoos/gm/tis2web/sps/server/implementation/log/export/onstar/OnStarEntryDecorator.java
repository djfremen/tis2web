/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log.export.onstar;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnStarEntryDecorator
/*    */ {
/*    */   private SPSEventLog.Entry entry;
/* 12 */   private String vin = null;
/*    */   
/* 14 */   private String stid = null;
/*    */   
/* 16 */   private String esn = null;
/*    */   
/* 18 */   private String min = null;
/*    */   
/* 20 */   private String mdn = null;
/*    */   
/*    */   public OnStarEntryDecorator(SPSEventLog.Entry entry) {
/* 23 */     this.entry = entry;
/* 24 */     for (Iterator<SPSEventLog.Attribute> iter = entry.getEventAttributes().iterator(); iter.hasNext(); ) {
/* 25 */       SPSEventLog.Attribute attribute = iter.next();
/* 26 */       if (attribute.getName().equalsIgnoreCase("vin")) {
/* 27 */         this.vin = attribute.getValue(); continue;
/* 28 */       }  if (attribute.getName().equalsIgnoreCase("stid")) {
/* 29 */         this.stid = attribute.getValue(); continue;
/* 30 */       }  if (attribute.getName().equalsIgnoreCase("esn")) {
/* 31 */         this.esn = attribute.getValue(); continue;
/* 32 */       }  if (attribute.getName().equalsIgnoreCase("min")) {
/* 33 */         this.min = attribute.getValue(); continue;
/* 34 */       }  if (attribute.getName().equalsIgnoreCase("mdn")) {
/* 35 */         this.mdn = attribute.getValue();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public SPSEventLog.Entry getEntry() {
/* 42 */     return this.entry;
/*    */   }
/*    */   
/*    */   public String getVIN() {
/* 46 */     return this.vin;
/*    */   }
/*    */   
/*    */   public String getSTiD() {
/* 50 */     return this.stid;
/*    */   }
/*    */   
/*    */   public String getESN() {
/* 54 */     return this.esn;
/*    */   }
/*    */   
/*    */   public String getMIN() {
/* 58 */     return this.min;
/*    */   }
/*    */   
/*    */   public String getMDN() {
/* 62 */     return this.mdn;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\export\onstar\OnStarEntryDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */