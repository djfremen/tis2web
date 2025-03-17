/*     */ package com.eoos.scsm.v2.database;
/*     */ 
/*     */ import com.eoos.jdbc.JDBCUtil;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.propcfg.impl.PropertiesConfigurationImpl;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.Blob;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlobExtraction
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(BlobExtraction.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void exportBlobs(Connection connection, String query, File outputDir, Callback callback) throws Exception {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: invokeinterface prepareStatement : (Ljava/lang/String;)Ljava/sql/PreparedStatement;
/*     */     //   7: astore #4
/*     */     //   9: aload_3
/*     */     //   10: aload #4
/*     */     //   12: invokeinterface setParameters : (Ljava/sql/PreparedStatement;)V
/*     */     //   17: aload #4
/*     */     //   19: invokeinterface executeQuery : ()Ljava/sql/ResultSet;
/*     */     //   24: astore #5
/*     */     //   26: aload #5
/*     */     //   28: invokeinterface next : ()Z
/*     */     //   33: ifeq -> 122
/*     */     //   36: aload_3
/*     */     //   37: aload_2
/*     */     //   38: aload #5
/*     */     //   40: invokeinterface getOutputStream : (Ljava/io/File;Ljava/sql/ResultSet;)Ljava/io/OutputStream;
/*     */     //   45: astore #6
/*     */     //   47: aload_3
/*     */     //   48: aload #5
/*     */     //   50: invokeinterface getBlob : (Ljava/sql/ResultSet;)Ljava/sql/Blob;
/*     */     //   55: astore #7
/*     */     //   57: aload #7
/*     */     //   59: invokeinterface getBinaryStream : ()Ljava/io/InputStream;
/*     */     //   64: astore #8
/*     */     //   66: aload #8
/*     */     //   68: aload #6
/*     */     //   70: invokestatic transfer : (Ljava/io/InputStream;Ljava/io/OutputStream;)V
/*     */     //   73: jsr -> 87
/*     */     //   76: goto -> 96
/*     */     //   79: astore #9
/*     */     //   81: jsr -> 87
/*     */     //   84: aload #9
/*     */     //   86: athrow
/*     */     //   87: astore #10
/*     */     //   89: aload #8
/*     */     //   91: invokevirtual close : ()V
/*     */     //   94: ret #10
/*     */     //   96: jsr -> 110
/*     */     //   99: goto -> 119
/*     */     //   102: astore #11
/*     */     //   104: jsr -> 110
/*     */     //   107: aload #11
/*     */     //   109: athrow
/*     */     //   110: astore #12
/*     */     //   112: aload #6
/*     */     //   114: invokevirtual close : ()V
/*     */     //   117: ret #12
/*     */     //   119: goto -> 26
/*     */     //   122: jsr -> 136
/*     */     //   125: goto -> 148
/*     */     //   128: astore #13
/*     */     //   130: jsr -> 136
/*     */     //   133: aload #13
/*     */     //   135: athrow
/*     */     //   136: astore #14
/*     */     //   138: aload #5
/*     */     //   140: getstatic com/eoos/scsm/v2/database/BlobExtraction.log : Lorg/apache/log4j/Logger;
/*     */     //   143: invokestatic close : (Ljava/sql/ResultSet;Lorg/apache/log4j/Logger;)V
/*     */     //   146: ret #14
/*     */     //   148: jsr -> 162
/*     */     //   151: goto -> 174
/*     */     //   154: astore #15
/*     */     //   156: jsr -> 162
/*     */     //   159: aload #15
/*     */     //   161: athrow
/*     */     //   162: astore #16
/*     */     //   164: aload #4
/*     */     //   166: getstatic com/eoos/scsm/v2/database/BlobExtraction.log : Lorg/apache/log4j/Logger;
/*     */     //   169: invokestatic close : (Ljava/sql/Statement;Lorg/apache/log4j/Logger;)V
/*     */     //   172: ret #16
/*     */     //   174: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #34	-> 0
/*     */     //   #36	-> 9
/*     */     //   #37	-> 17
/*     */     //   #39	-> 26
/*     */     //   #40	-> 36
/*     */     //   #42	-> 47
/*     */     //   #43	-> 57
/*     */     //   #45	-> 66
/*     */     //   #46	-> 73
/*     */     //   #48	-> 76
/*     */     //   #47	-> 79
/*     */     //   #49	-> 96
/*     */     //   #51	-> 99
/*     */     //   #50	-> 102
/*     */     //   #52	-> 119
/*     */     //   #54	-> 122
/*     */     //   #56	-> 125
/*     */     //   #55	-> 128
/*     */     //   #58	-> 148
/*     */     //   #60	-> 151
/*     */     //   #59	-> 154
/*     */     //   #62	-> 174
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   57	39	7	blob	Ljava/sql/Blob;
/*     */     //   66	30	8	is	Ljava/io/InputStream;
/*     */     //   47	72	6	os	Ljava/io/OutputStream;
/*     */     //   26	122	5	rs	Ljava/sql/ResultSet;
/*     */     //   0	175	0	connection	Ljava/sql/Connection;
/*     */     //   0	175	1	query	Ljava/lang/String;
/*     */     //   0	175	2	outputDir	Ljava/io/File;
/*     */     //   0	175	3	callback	Lcom/eoos/scsm/v2/database/BlobExtraction$Callback;
/*     */     //   9	166	4	stmt	Ljava/sql/PreparedStatement;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   9	151	154	finally
/*     */     //   26	125	128	finally
/*     */     //   47	99	102	finally
/*     */     //   66	76	79	finally
/*     */     //   79	84	79	finally
/*     */     //   102	107	102	finally
/*     */     //   128	133	128	finally
/*     */     //   154	159	154	finally
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  65 */     Log4jUtil.attachConsoleAppender();
/*     */     try {
/*  67 */       InputStream cfgInputStream = null;
/*  68 */       if (args.length > 0) {
/*  69 */         cfgInputStream = new FileInputStream(args[0]);
/*     */       } else {
/*     */         
/*  72 */         String path = BlobExtraction.class.getPackage().getName();
/*  73 */         path = path.replace('.', '/');
/*  74 */         cfgInputStream = BlobExtraction.class.getClassLoader().getResourceAsStream(path + "/myConfiguration.properties");
/*     */       } 
/*     */       
/*  77 */       Properties properties = new Properties();
/*     */       try {
/*  79 */         properties.load(cfgInputStream);
/*     */       } finally {
/*  81 */         cfgInputStream.close();
/*     */       } 
/*     */       
/*  84 */       PropertiesConfigurationImpl propertiesConfigurationImpl = new PropertiesConfigurationImpl(properties);
/*     */       
/*  86 */       String driver = propertiesConfigurationImpl.getProperty("db.drv");
/*  87 */       Class.forName(driver);
/*     */       
/*  89 */       String url = propertiesConfigurationImpl.getProperty("db.url");
/*  90 */       String user = propertiesConfigurationImpl.getProperty("db.usr");
/*  91 */       String pwd = propertiesConfigurationImpl.getProperty("db.pwd");
/*     */       
/*  93 */       File dir = new File(propertiesConfigurationImpl.getProperty("dest.dir"));
/*  94 */       dir.mkdirs();
/*     */       
/*  96 */       String className = propertiesConfigurationImpl.getProperty("query.callback");
/*     */       
/*  98 */       Callback callback = (Callback)Class.forName(className).newInstance();
/*     */       
/* 100 */       Connection connection = DriverManager.getConnection(url, user, pwd);
/*     */       try {
/* 102 */         exportBlobs(connection, propertiesConfigurationImpl.getProperty("query"), dir, callback);
/*     */       } finally {
/*     */         
/* 105 */         JDBCUtil.close(connection, log);
/*     */       }
/*     */     
/* 108 */     } catch (Exception e) {
/* 109 */       System.err.println("unable to extract blob(s) - exception: " + e);
/* 110 */       e.printStackTrace(System.err);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void setParameters(PreparedStatement param1PreparedStatement) throws Exception;
/*     */     
/*     */     OutputStream getOutputStream(File param1File, ResultSet param1ResultSet) throws Exception;
/*     */     
/*     */     Blob getBlob(ResultSet param1ResultSet) throws Exception;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\database\BlobExtraction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */