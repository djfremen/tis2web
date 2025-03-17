/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DisplayableToolAttribute
/*    */   implements DisplayableAttribute
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(DisplayableToolAttribute.class);
/*    */   private String attributeKey;
/*    */   
/*    */   public DisplayableToolAttribute(String key) {
/* 16 */     this.attributeKey = key;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/*    */     try {
/* 21 */       return LabelResourceProvider.getInstance().getLabelResource().getLabel(locale, this.attributeKey);
/* 22 */     } catch (Exception e) {
/* 23 */       log.warn("unable to retrieve denotation - exception:" + e + " returning attribute key");
/* 24 */       return this.attributeKey;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\DisplayableToolAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */