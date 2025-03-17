/*    */ package com.eoos.gm.tis2web.frame.msg.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class IMessageWrapper
/*    */   implements IMessage
/*    */ {
/*    */   private IMessage delegate;
/*    */   
/*    */   public IMessageWrapper(IMessage msg) {
/* 13 */     this.delegate = msg;
/*    */   }
/*    */   
/*    */   public Locale getDefaultLocale() {
/* 17 */     return this.delegate.getDefaultLocale();
/*    */   }
/*    */   
/*    */   public String getExternalID() {
/* 21 */     return this.delegate.getExternalID();
/*    */   }
/*    */   
/*    */   public String getID() {
/* 25 */     return this.delegate.getID();
/*    */   }
/*    */   
/*    */   public IMessage.Status getStatus() {
/* 29 */     return this.delegate.getStatus();
/*    */   }
/*    */   
/*    */   public IMessage.Type getType() {
/* 33 */     return this.delegate.getType();
/*    */   }
/*    */   
/*    */   public Set getUserGroups() {
/* 37 */     return this.delegate.getUserGroups();
/*    */   }
/*    */   
/*    */   public IMessage.IContent getContent(Locale locale) {
/* 41 */     return this.delegate.getContent(locale);
/*    */   }
/*    */   
/*    */   public Set getSupportedLocales() {
/* 45 */     return this.delegate.getSupportedLocales();
/*    */   }
/*    */   
/*    */   public Set getTargetModules() {
/* 49 */     return this.delegate.getTargetModules();
/*    */   }
/*    */   
/*    */   public Set getUserIDs() {
/* 53 */     return this.delegate.getUserIDs();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ms\\util\IMessageWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */