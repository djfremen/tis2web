/*    */ package com.eoos.gm.tis2web.frame.msg.admin;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MessageRI
/*    */   implements IMessage
/*    */ {
/*    */   private final IMessage.Status status;
/*    */   private final String id;
/*    */   private final Locale defaultLocale;
/*    */   private final Set groups;
/*    */   private final String extID;
/*    */   private final Set targetModules;
/*    */   private final Map locale2Content;
/*    */   private final IMessage.Type type;
/*    */   private final long ts;
/*    */   private final Set userIDs;
/*    */   
/*    */   public MessageRI(IMessage.Status status, String id, Locale defaultLocale, Set groups, String extID, Set targetModules, Map locale2Content, IMessage.Type type, long ts, Set userIDs) {
/* 29 */     this.status = status;
/* 30 */     this.id = id;
/* 31 */     this.defaultLocale = defaultLocale;
/* 32 */     this.groups = groups;
/* 33 */     this.extID = extID;
/* 34 */     this.targetModules = targetModules;
/* 35 */     this.locale2Content = locale2Content;
/* 36 */     this.type = type;
/* 37 */     this.ts = ts;
/* 38 */     this.userIDs = userIDs;
/*    */   }
/*    */   
/*    */   public Set getUserGroups() {
/* 42 */     return this.groups;
/*    */   }
/*    */   
/*    */   public IMessage.Type getType() {
/* 46 */     return this.type;
/*    */   }
/*    */   
/*    */   public Set getSupportedLocales() {
/* 50 */     return this.locale2Content.keySet();
/*    */   }
/*    */   
/*    */   public IMessage.Status getStatus() {
/* 54 */     return this.status;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 58 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getExternalID() {
/* 62 */     return this.extID;
/*    */   }
/*    */   
/*    */   public Locale getDefaultLocale() {
/* 66 */     return this.defaultLocale;
/*    */   }
/*    */   
/*    */   public IMessage.IContent getContent(Locale locale) {
/* 70 */     IMessage.IContent ret = null;
/* 71 */     if (locale != null && locale.getCountry() != null) {
/* 72 */       locale = new Locale(locale.getLanguage());
/*    */     }
/* 74 */     ret = (IMessage.IContent)this.locale2Content.get(locale);
/* 75 */     if (ret == null && !locale.equals(this.defaultLocale)) {
/* 76 */       return getContent(this.defaultLocale);
/*    */     }
/* 78 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getTimestamp() {
/* 83 */     return this.ts;
/*    */   }
/*    */   
/*    */   public Set getTargetModules() {
/* 87 */     return this.targetModules;
/*    */   }
/*    */   
/*    */   public Set getUserIDs() {
/* 91 */     return this.userIDs;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admin\MessageRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */