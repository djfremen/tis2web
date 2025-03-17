/*     */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TSCSSupport
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(TSCSSupport.class);
/*     */   
/*  24 */   private static TSCSSupport instance = null;
/*     */   
/*     */   private IDatabaseLink dbLink;
/*     */   
/*     */   private TSCSSupport() {
/*  29 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.sps.adapter.tscs-support.db.");
/*  30 */     this.dbLink = DatabaseLink.openDatabase((Configuration)subConfigurationWrapper);
/*     */   }
/*     */   
/*     */   public static synchronized TSCSSupport getInstance() {
/*  34 */     if (instance == null) {
/*  35 */       instance = new TSCSSupport();
/*     */     }
/*  37 */     return instance;
/*     */   }
/*     */   
/*     */   public String getMessage(String errorCode, Integer deviceID, Locale locale, Collection userGroups) {
/*  41 */     log.debug("retrieving message for errorCode: " + errorCode + ", deviceID: " + String.valueOf(deviceID) + ", locale: " + String.valueOf(locale));
/*     */     try {
/*  43 */       Connection connection = this.dbLink.requestConnection();
/*     */       try {
/*  45 */         if (Util.isNullOrEmpty(getRecipients(userGroups))) {
/*  46 */           log.debug("...there are no recipients for user groups: " + String.valueOf(userGroups) + ", returning null");
/*  47 */           return null;
/*     */         } 
/*  49 */         return getMessage(connection, errorCode, deviceID, locale);
/*     */       } finally {
/*     */         
/*  52 */         this.dbLink.releaseConnection(connection);
/*     */       } 
/*  54 */     } catch (Exception e) {
/*  55 */       log.error("unable to retrieve message, throwing new RuntimeException - exception :" + e, e);
/*  56 */       throw new RuntimeException("unable to retrieve message");
/*     */     } 
/*     */   }
/*     */   private String getMessage(Connection connection, String errorCode, Integer deviceID, Locale locale) throws Exception {
/*     */     long messageID;
/*  61 */     String message = null;
/*     */ 
/*     */     
/*  64 */     PreparedStatement stmt = connection.prepareStatement("select a.message_id, a.device_id  from tscs_support a where LOWER(a.error_code)=? ");
/*     */     try {
/*  66 */       stmt.setString(1, Util.toLowerCase(errorCode));
/*  67 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/*  69 */         if (rs.next()) {
/*  70 */           log.debug("...checking device id list");
/*  71 */           String _deviceIDList = rs.getString(2);
/*  72 */           log.debug("...processing database entry for 'device id list': " + String.valueOf(_deviceIDList));
/*  73 */           Collection deviceIDs = null;
/*  74 */           if (!Util.isNullOrEmpty(_deviceIDList)) {
/*  75 */             deviceIDs = Util.parseIntegerList(_deviceIDList);
/*  76 */             log.debug("...resulting collection: " + String.valueOf(deviceIDs));
/*     */           } 
/*     */           
/*  79 */           if (deviceIDs != null && !deviceIDs.contains(deviceID)) {
/*  80 */             log.debug("...the collection does not include device id: " + deviceID + ", returning null");
/*  81 */             return null;
/*     */           } 
/*     */           
/*  84 */           messageID = rs.getLong(1);
/*  85 */           log.debug("...received message id: " + messageID);
/*  86 */           if (messageID == 0L && rs.wasNull()) {
/*  87 */             log.debug("...message id has null value, returning empty message");
/*  88 */             return "";
/*     */           } 
/*     */         } else {
/*     */           
/*  92 */           log.debug("...no entry found, returning null");
/*  93 */           return null;
/*     */         } 
/*     */       } finally {
/*  96 */         JDBCUtil.close(rs);
/*     */       } 
/*     */     } finally {
/*     */       
/* 100 */       JDBCUtil.close(stmt);
/*     */     } 
/*     */     
/*     */     do {
/* 104 */       log.debug("...requesting message content for messageID: " + messageID + " and locale: " + String.valueOf(locale));
/* 105 */       stmt = connection.prepareStatement("select a.message from tscs_message a where a.message_id=? and LOWER(a.language_code)=?");
/*     */       try {
/* 107 */         stmt.setLong(1, messageID);
/* 108 */         stmt.setString(2, Util.toLowerCase(String.valueOf(locale)));
/* 109 */         ResultSet rs = stmt.executeQuery();
/*     */         try {
/* 111 */           if (rs.next()) {
/* 112 */             log.debug("...found");
/* 113 */             message = rs.getString(1);
/*     */           } else {
/* 115 */             log.debug("...not found");
/*     */           } 
/*     */         } finally {
/*     */           
/* 119 */           JDBCUtil.close(rs);
/*     */         } 
/*     */       } finally {
/* 122 */         JDBCUtil.close(stmt);
/*     */       }
/*     */     
/* 125 */     } while (message == null && (locale = Util.shortenLocale(locale)) != null);
/*     */     
/* 127 */     return message;
/*     */   }
/*     */   
/*     */   public Collection getRecipients(Collection userGroups) {
/*     */     try {
/* 132 */       Connection connection = this.dbLink.requestConnection();
/*     */       try {
/* 134 */         return getRecipients(connection, userGroups);
/*     */       } finally {
/* 136 */         this.dbLink.releaseConnection(connection);
/*     */       } 
/* 138 */     } catch (Exception e) {
/* 139 */       log.error("unable to retrieve recipients, returning empty collection- exception :" + e, e);
/* 140 */       throw new RuntimeException("unable to retrieve recipients");
/*     */     } 
/*     */   }
/*     */   
/*     */   private Collection getRecipients(Connection connection, Collection userGroups) throws Exception {
/*     */     // Byte code:
/*     */     //   0: new java/util/LinkedHashSet
/*     */     //   3: dup
/*     */     //   4: invokespecial <init> : ()V
/*     */     //   7: astore_3
/*     */     //   8: aload_1
/*     */     //   9: ldc 'select b.email from tscs_recipient a, tscs_organization b where LOWER(a.user_group)=? and a.organization_id=b.organization_id '
/*     */     //   11: invokeinterface prepareStatement : (Ljava/lang/String;)Ljava/sql/PreparedStatement;
/*     */     //   16: astore #4
/*     */     //   18: aload_2
/*     */     //   19: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   24: astore #5
/*     */     //   26: aload #5
/*     */     //   28: invokeinterface hasNext : ()Z
/*     */     //   33: ifeq -> 121
/*     */     //   36: aload #5
/*     */     //   38: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   43: checkcast java/lang/String
/*     */     //   46: astore #6
/*     */     //   48: aload #4
/*     */     //   50: iconst_1
/*     */     //   51: aload #6
/*     */     //   53: invokestatic toLowerCase : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   56: invokeinterface setString : (ILjava/lang/String;)V
/*     */     //   61: aload #4
/*     */     //   63: invokeinterface executeQuery : ()Ljava/sql/ResultSet;
/*     */     //   68: astore #7
/*     */     //   70: aload #7
/*     */     //   72: invokeinterface next : ()Z
/*     */     //   77: ifeq -> 95
/*     */     //   80: aload_3
/*     */     //   81: aload #7
/*     */     //   83: iconst_1
/*     */     //   84: invokeinterface getString : (I)Ljava/lang/String;
/*     */     //   89: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   94: pop
/*     */     //   95: jsr -> 109
/*     */     //   98: goto -> 118
/*     */     //   101: astore #8
/*     */     //   103: jsr -> 109
/*     */     //   106: aload #8
/*     */     //   108: athrow
/*     */     //   109: astore #9
/*     */     //   111: aload #7
/*     */     //   113: invokestatic close : (Ljava/sql/ResultSet;)V
/*     */     //   116: ret #9
/*     */     //   118: goto -> 26
/*     */     //   121: jsr -> 135
/*     */     //   124: goto -> 144
/*     */     //   127: astore #10
/*     */     //   129: jsr -> 135
/*     */     //   132: aload #10
/*     */     //   134: athrow
/*     */     //   135: astore #11
/*     */     //   137: aload #4
/*     */     //   139: invokestatic close : (Ljava/sql/Statement;)V
/*     */     //   142: ret #11
/*     */     //   144: aload_3
/*     */     //   145: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #146	-> 0
/*     */     //   #148	-> 8
/*     */     //   #150	-> 18
/*     */     //   #151	-> 36
/*     */     //   #152	-> 48
/*     */     //   #153	-> 61
/*     */     //   #155	-> 70
/*     */     //   #156	-> 80
/*     */     //   #159	-> 95
/*     */     //   #161	-> 98
/*     */     //   #160	-> 101
/*     */     //   #162	-> 118
/*     */     //   #164	-> 121
/*     */     //   #166	-> 124
/*     */     //   #165	-> 127
/*     */     //   #167	-> 144
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   48	70	6	userGroup	Ljava/lang/String;
/*     */     //   70	48	7	rs	Ljava/sql/ResultSet;
/*     */     //   26	95	5	iter	Ljava/util/Iterator;
/*     */     //   0	146	0	this	Lcom/eoos/gm/tis2web/sps/server/implementation/system/serverside/TSCSSupport;
/*     */     //   0	146	1	connection	Ljava/sql/Connection;
/*     */     //   0	146	2	userGroups	Ljava/util/Collection;
/*     */     //   8	138	3	ret	Ljava/util/Collection;
/*     */     //   18	128	4	stmt	Ljava/sql/PreparedStatement;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   18	124	127	finally
/*     */     //   70	98	101	finally
/*     */     //   101	106	101	finally
/*     */     //   127	132	127	finally
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\TSCSSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */