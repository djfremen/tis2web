/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TypeDecorator
/*    */ {
/* 12 */   private ClientSettings clientSettings = null;
/*    */   
/*    */   public TypeDecorator(ClientSettings clientSettings) {
/* 15 */     this.clientSettings = clientSettings;
/*    */   }
/*    */   
/*    */   public Boolean getBoolean(String key) {
/* 19 */     Boolean retValue = null;
/* 20 */     String value = this.clientSettings.getProperty(key);
/* 21 */     if (value != null) {
/*    */       try {
/* 23 */         retValue = Boolean.valueOf(value);
/* 24 */       } catch (Exception e) {}
/*    */     }
/*    */     
/* 27 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\TypeDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */