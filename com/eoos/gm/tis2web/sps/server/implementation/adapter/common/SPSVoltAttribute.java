/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.VIT;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class SPSVoltAttribute
/*    */ {
/* 13 */   private List attributes = new ArrayList();
/*    */   
/*    */   public SPSVoltAttribute(Configuration configuration) {
/* 16 */     for (Iterator<String> iter = configuration.getKeys().iterator(); iter.hasNext(); ) {
/* 17 */       String key = iter.next();
/* 18 */       if (key.indexOf("volt.vit1.attribute") >= 0)
/*    */       {
/* 20 */         this.attributes.add(configuration.getProperty(key));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean extract(List<SPSEventLog.VoltAttribute> log, VIT vit) {
/* 26 */     boolean hasVoltAttributes = false;
/* 27 */     for (int i = 0; i < this.attributes.size(); i++) {
/* 28 */       String value = vit.getAttrValue(this.attributes.get(i));
/* 29 */       if (value != null && value.trim().length() > 0) {
/*    */         
/* 31 */         log.add(new SPSEventLog.VoltAttribute(this.attributes.get(i), value.trim()));
/* 32 */         hasVoltAttributes = true;
/*    */       } 
/*    */     } 
/* 35 */     return hasVoltAttributes;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSVoltAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */