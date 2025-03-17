/*    */ package com.eoos.gm.tis2web.frame.msg.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.Module;
/*    */ import com.eoos.scsm.v2.util.StringUtilities;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageUtil
/*    */ {
/*    */   public static String getModulesLabel(Set modules, Locale locale) {
/* 18 */     StringBuffer ret = new StringBuffer();
/* 19 */     for (Iterator<Module> iter = modules.iterator(); iter.hasNext(); ) {
/* 20 */       Module module = iter.next();
/* 21 */       ret.append(module.getDenotation(locale));
/* 22 */       ret.append(", ");
/*    */     } 
/* 24 */     if (ret.length() > 0) {
/* 25 */       ret.delete(ret.length() - 2, ret.length());
/*    */     }
/*    */     
/* 28 */     return ret.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getTypeLabel(IMessage.Type type, Locale locale) {
/* 33 */     ApplicationContext context = ApplicationContext.getInstance();
/* 34 */     if (type == IMessage.Type.INFO)
/* 35 */       return context.getLabel(locale, "info"); 
/* 36 */     if (type == IMessage.Type.WARNING) {
/* 37 */       return context.getLabel(locale, "warning");
/*    */     }
/* 39 */     return "n/a";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getStatusLabel(IMessage.Status status, Locale locale) {
/* 45 */     ApplicationContext context = ApplicationContext.getInstance();
/* 46 */     if (status == IMessage.Status.ACTIVE)
/* 47 */       return context.getLabel(locale, "active"); 
/* 48 */     if (status == IMessage.Status.INACTIVE) {
/* 49 */       return context.getLabel(locale, "inactive");
/*    */     }
/* 51 */     return "n/a";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getTitle(IMessage msg, Locale locale) {
/* 57 */     IMessage.IContent content = (msg != null) ? msg.getContent(locale) : null;
/* 58 */     return (content != null) ? content.getTitle() : null;
/*    */   }
/*    */   
/*    */   public static String getText(IMessage msg, Locale locale) {
/* 62 */     IMessage.IContent content = (msg != null) ? msg.getContent(locale) : null;
/* 63 */     return (content != null) ? content.getText() : null;
/*    */   }
/*    */   
/*    */   public static boolean emptyContent(IMessage msg, Locale locale) {
/* 67 */     String text = getText(msg, locale);
/* 68 */     String title = getTitle(msg, locale);
/*    */     
/* 70 */     boolean ret = (text == null || text.trim().length() == 0);
/* 71 */     ret = (ret && (title == null || title.trim().length() == 0));
/* 72 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String convertLineBreaksToHTML(String text) {
/* 77 */     String ret = text;
/* 78 */     if (ret != null) {
/* 79 */       ret = StringUtilities.replace(ret, "\r\n", "<br/>");
/* 80 */       ret = StringUtilities.replace(ret, "\n", "<br/>");
/*    */     } 
/* 82 */     return ret;
/*    */   }
/*    */   
/*    */   public static boolean isInfo(IMessage msg) {
/* 86 */     return (msg.getType() == IMessage.Type.INFO);
/*    */   }
/*    */   
/*    */   public static boolean isWarning(IMessage msg) {
/* 90 */     return (msg.getType() == IMessage.Type.WARNING);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ms\\util\MessageUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */