/*    */ package com.eoos.html.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class HtmlDocumentFacade
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(HtmlDocumentFacade.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Callback callback;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   private static final Pattern patternBody = Pattern.compile("(?s)<body.*?>(.*?)</body.*?>", 2);
/* 31 */   private static final Pattern patternStylesheet = Pattern.compile("(?s)<link[^>]*?rel\\s*=\\s*[\"']stylesheet[^>]*?href\\s*=\\s*[\"'](.*?)[\"']", 2);
/* 32 */   private static final Pattern patternTitle = Pattern.compile("(?s)<title.*?>(.*?)</title.*?>", 2);
/* 33 */   private static final Pattern patternEncoding = Pattern.compile("(?s)<meta[^>]*charset\\s*=\\s*[\"'](.*?)[\"']", 2);
/* 34 */   private static final Pattern patternBodyStyle = Pattern.compile("(?s)<body[^>]*style\\s*=\\s*[\"'](.*?)[\"']", 2);
/*    */   
/*    */   public String getBody() {
/* 37 */     String body = null;
/* 38 */     String document = this.callback.getDocument();
/*    */     try {
/* 40 */       body = Util.getMatchingGroup(patternBody, 1, document);
/* 41 */     } catch (Exception e) {
/* 42 */       log.error("unable to retrieve body, returning null - exception:" + e, e);
/*    */     } 
/* 44 */     return body;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStyleSheet() {
/* 49 */     String stylesheet = null;
/*    */     
/* 51 */     String document = this.callback.getDocument();
/*    */     try {
/* 53 */       stylesheet = Util.getMatchingGroup(patternStylesheet, 1, document);
/* 54 */     } catch (Exception e) {
/* 55 */       log.error("unable to retrieve stylesheet, returning null - exception:" + e, e);
/*    */     } 
/* 57 */     return stylesheet;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 61 */     String title = null;
/*    */     
/* 63 */     String document = this.callback.getDocument();
/*    */     
/*    */     try {
/* 66 */       title = Util.getMatchingGroup(patternTitle, 1, document);
/* 67 */     } catch (Exception e) {
/* 68 */       log.error("unable to retrieve title, returning null - exception" + e, e);
/*    */     } 
/* 70 */     return title;
/*    */   }
/*    */   
/*    */   public String getEncoding() {
/* 74 */     String encoding = null;
/* 75 */     String document = this.callback.getDocument();
/*    */     try {
/* 77 */       encoding = Util.getMatchingGroup(patternEncoding, 1, document);
/* 78 */     } catch (Exception e) {
/* 79 */       log.error("unable to retrieve encoding, returning null - exception:" + e, e);
/*    */     } 
/* 81 */     return encoding;
/*    */   }
/*    */   
/*    */   public String getBodyStyleAttribute() {
/* 85 */     String encoding = null;
/*    */     
/* 87 */     String document = this.callback.getDocument();
/*    */     try {
/* 89 */       encoding = Util.getMatchingGroup(patternBodyStyle, 1, document);
/* 90 */     } catch (Exception e) {
/* 91 */       log.error("unable to retrieve style attribute, returning null - exception:" + e, e);
/*    */     } 
/* 93 */     return encoding;
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     String getDocument();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\htm\\util\HtmlDocumentFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */