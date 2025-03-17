/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import java.util.regex.Matcher;
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
/*    */ public class DynamicValueFacade
/*    */   extends ConfigurationWrapperBase
/*    */ {
/* 27 */   private static final Logger log = Logger.getLogger(DynamicValueFacade.class);
/*    */   
/* 29 */   private static String contentPatternStart = "$[";
/*    */   
/* 31 */   private static Pattern contentPattern = Pattern.compile("\\$\\[(([^\\[\\]]*?|\\[.*?\\])*)\\]");
/*    */   
/*    */   public DynamicValueFacade(Configuration backend) {
/* 34 */     super(backend);
/*    */   }
/*    */   
/*    */   public String getProperty(String key) {
/* 38 */     String ret = super.getProperty(key);
/* 39 */     if (!Util.isNullOrEmpty(ret) && ret.contains(contentPatternStart)) {
/* 40 */       StringBuffer result = StringBufferPool.getThreadInstance().get();
/*    */       try {
/* 42 */         Matcher m = contentPattern.matcher(ret);
/* 43 */         while (m.find()) {
/* 44 */           m.appendReplacement(result, Matcher.quoteReplacement(getReplacement(m.group(1))));
/*    */         }
/* 46 */         m.appendTail(result);
/* 47 */         ret = result.toString();
/*    */       } finally {
/*    */         
/* 50 */         StringBufferPool.getThreadInstance().free(result);
/*    */       } 
/*    */     } 
/* 53 */     return ret;
/*    */   }
/*    */   
/*    */   protected String getReplacement(String key) {
/* 57 */     String ret = key;
/* 58 */     if ("IP".equals(key)) {
/*    */       try {
/* 60 */         ret = InetAddress.getLocalHost().getHostAddress();
/* 61 */       } catch (UnknownHostException e) {
/* 62 */         log.error("unable to retrieve host address, skipping replacement - exception: ", e);
/*    */       } 
/* 64 */     } else if ("HOST".equals(key)) {
/*    */       try {
/* 66 */         ret = InetAddress.getLocalHost().getCanonicalHostName();
/* 67 */       } catch (UnknownHostException e) {
/* 68 */         log.error("unable to retrieve host address, skipping replacement - exception: ", e);
/*    */       } 
/* 70 */     } else if (key.startsWith("TIMESTAMP")) {
/* 71 */       if ("TIMESTAMP".equals(key)) {
/* 72 */         ret = String.valueOf(System.currentTimeMillis());
/*    */       } else {
/* 74 */         Matcher m = Pattern.compile("TIMESTAMP\\[(.*?)\\]").matcher(key);
/* 75 */         if (m.matches()) {
/* 76 */           String format = m.group(1);
/* 77 */           ret = (new SimpleDateFormat(format)).format(new Date());
/*    */         } 
/*    */       } 
/* 80 */     } else if (key.startsWith("PROPERTY")) {
/* 81 */       Matcher m = Pattern.compile("PROPERTY\\[(.*?)\\]").matcher(key);
/* 82 */       if (m.matches()) {
/* 83 */         ret = System.getProperty(m.group(1).toLowerCase(Locale.ENGLISH));
/*    */       }
/* 85 */     } else if (key.startsWith("ENVIRONMENT")) {
/* 86 */       Matcher m = Pattern.compile("ENVIRONMENT\\[(.*?)\\]").matcher(key);
/* 87 */       if (m.matches()) {
/* 88 */         ret = System.getenv(m.group(1));
/*    */       }
/*    */     } 
/*    */     
/* 92 */     return (ret != null) ? ret : key;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\DynamicValueFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */