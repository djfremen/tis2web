/*     */ package com.eoos.gm.tis2web.frame.msg.admin;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.msg.util.MessageUtil;
/*     */ import java.util.Comparator;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public interface IMessage
/*     */ {
/*     */   public static final class Status
/*     */   {
/*  13 */     public static final Status ACTIVE = new Status();
/*     */     
/*  15 */     public static final Status INACTIVE = new Status();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int toExternal() {
/*  22 */       if (this == ACTIVE) {
/*  23 */         return 1;
/*     */       }
/*  25 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public static Status toStatus(int external) {
/*  30 */       if (external > 0) {
/*  31 */         return ACTIVE;
/*     */       }
/*  33 */       return INACTIVE;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Type
/*     */   {
/*  39 */     public static final Type INFO = new Type();
/*     */     
/*  41 */     public static final Type WARNING = new Type();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int toExternal() {
/*  48 */       if (this == INFO) {
/*  49 */         return 0;
/*     */       }
/*  51 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public static Type toType(int external) {
/*  56 */       if (external == 0) {
/*  57 */         return INFO;
/*     */       }
/*  59 */       return WARNING;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class TitleComparator
/*     */     implements Comparator {
/*     */     private Locale locale;
/*     */     
/*     */     public TitleComparator(Locale locale) {
/*  68 */       this.locale = locale;
/*     */     }
/*     */     
/*     */     public int compare(Object o1, Object o2) {
/*  72 */       String title1 = MessageUtil.getTitle((IMessage)o1, this.locale);
/*  73 */       String title2 = MessageUtil.getTitle((IMessage)o2, this.locale);
/*  74 */       return title1.compareTo(title2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  79 */   public static final Comparator COMPARATOR_ID = new Comparator()
/*     */     {
/*     */       public int compare(Object o1, Object o2) {
/*  82 */         return ((IMessage)o1).getID().compareTo(((IMessage)o2).getID());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  87 */   public static final Comparator COMPARATOR_EXTID = new Comparator()
/*     */     {
/*     */       public int compare(Object o1, Object o2) {
/*     */         try {
/*  91 */           return ((IMessage)o1).getExternalID().compareTo(((IMessage)o2).getExternalID());
/*  92 */         } catch (Exception e) {
/*  93 */           Logger.getLogger(getClass()).warn("unable to compare objects, returning 0 - exception : " + e, e);
/*  94 */           return 0;
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 100 */   public static final Comparator COMPARATOR_STATUS = new Comparator()
/*     */     {
/*     */       public int compare(Object o1, Object o2) {
/* 103 */         return ((IMessage)o1).getStatus().toExternal() - ((IMessage)o2).getStatus().toExternal();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 108 */   public static final Comparator COMPARATOR_TYPE = new Comparator()
/*     */     {
/*     */       public int compare(Object o1, Object o2) {
/* 111 */         return ((IMessage)o1).getType().toExternal() - ((IMessage)o2).getType().toExternal();
/*     */       }
/*     */     };
/*     */   
/*     */   String getID();
/*     */   
/*     */   String getExternalID();
/*     */   
/*     */   Set getTargetModules();
/*     */   
/*     */   Status getStatus();
/*     */   
/*     */   Type getType();
/*     */   
/*     */   Locale getDefaultLocale();
/*     */   
/*     */   Set getSupportedLocales();
/*     */   
/*     */   IContent getContent(Locale paramLocale);
/*     */   
/*     */   Set getUserGroups();
/*     */   
/*     */   Set getUserIDs();
/*     */   
/*     */   public static interface IContent {
/*     */     Locale getLocale();
/*     */     
/*     */     String getTitle();
/*     */     
/*     */     String getText();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admin\IMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */