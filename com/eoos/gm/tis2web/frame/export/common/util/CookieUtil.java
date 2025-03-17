/*     */ package com.eoos.gm.tis2web.frame.export.common.util;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Locale;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CookieUtil
/*     */ {
/*  13 */   private static final Logger log = Logger.getLogger(CookieUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String trim(String identifier) {
/*  20 */     if (identifier == null) {
/*  21 */       return identifier;
/*     */     }
/*  23 */     while (identifier.length() > 0 && Character.isWhitespace(identifier.charAt(0))) {
/*  24 */       identifier = identifier.substring(1);
/*     */     }
/*     */     
/*  27 */     while (identifier.length() > 0 && Character.isWhitespace(identifier.charAt(identifier.length() - 1))) {
/*  28 */       identifier = identifier.substring(0, identifier.length() - 1);
/*     */     }
/*     */     
/*  31 */     return identifier;
/*     */   }
/*     */   
/*  34 */   private static final Comparator COOKIE_COMPARATOR = new Comparator()
/*     */     {
/*     */       private int getOrd(String string) {
/*  37 */         if (string.startsWith("$version"))
/*  38 */           return 0; 
/*  39 */         if (string.startsWith("jsessionid")) {
/*  40 */           return 1;
/*     */         }
/*  42 */         int hash = string.hashCode();
/*     */         
/*  44 */         return 5 + ((hash > 0) ? hash : (hash * -1));
/*     */       }
/*     */ 
/*     */       
/*     */       public int compare(Object o1, Object o2) {
/*  49 */         int retValue = 0;
/*     */         try {
/*  51 */           String cookie1 = ((String)o1).toLowerCase(Locale.ENGLISH);
/*  52 */           String cookie2 = ((String)o2).toLowerCase(Locale.ENGLISH);
/*  53 */           retValue = getOrd(cookie1) - getOrd(cookie2);
/*  54 */         } catch (Exception e) {
/*  55 */           CookieUtil.log.warn("unable to compare cookies, ignoring - exception: " + e, e);
/*     */         } 
/*     */ 
/*     */         
/*  59 */         return retValue;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public static String adjustCookieOrdering(String cookie) {
/*     */     try {
/*  66 */       if (cookie == null || (cookie.indexOf(",") == -1 && cookie.indexOf(";") == -1)) {
/*  67 */         return cookie;
/*     */       }
/*     */       
/*  70 */       LinkedList<String> cookies = new LinkedList();
/*  71 */       StringTokenizer tokenizer = new StringTokenizer(cookie, ";,");
/*  72 */       String currentCookie = null;
/*  73 */       while (tokenizer.hasMoreTokens()) {
/*  74 */         String _cookie = trim(tokenizer.nextToken());
/*  75 */         if (currentCookie == null || !_cookie.startsWith("$")) {
/*  76 */           if (currentCookie != null) {
/*  77 */             cookies.add(currentCookie);
/*     */           }
/*  79 */           currentCookie = _cookie; continue;
/*     */         } 
/*  81 */         currentCookie = currentCookie + "; " + _cookie;
/*     */       } 
/*     */       
/*  84 */       cookies.add(currentCookie);
/*     */       
/*  86 */       Collections.sort(cookies, COOKIE_COMPARATOR);
/*     */       
/*  88 */       StringBuffer retValue = new StringBuffer();
/*  89 */       for (Iterator<String> iter = cookies.iterator(); iter.hasNext(); ) {
/*  90 */         String _cookie = iter.next();
/*  91 */         retValue.append(_cookie);
/*  92 */         if (iter.hasNext()) {
/*  93 */           retValue.append("; ");
/*     */         }
/*     */       } 
/*  96 */       return retValue.toString();
/*     */     }
/*  98 */     catch (Exception e) {
/*  99 */       log.warn("unable to reorder cookies - exception:" + e, e);
/* 100 */       return cookie;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 105 */     String cookie = "Location=en_uS; JSESSIONID=32o48u20934udklsfj--j";
/* 106 */     System.out.println(adjustCookieOrdering(cookie));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\CookieUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */