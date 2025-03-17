/*     */ package com.eoos.gm.tis2web.rpo;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPOContainer;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*     */ import com.eoos.gm.tis2web.rpo.api.RPORetrieval;
/*     */ import com.eoos.gm.tis2web.rpo.ri.RPOContainerRI;
/*     */ import com.eoos.gm.tis2web.rpo.ri.RPOFamiliyRI;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.jdbc.CachedRetrievalSupportV3;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.impl.PropertiesConfigurationImpl;
/*     */ import com.eoos.scsm.v2.util.I15dTextSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabaseAdapter
/*     */   implements RPORetrieval
/*     */ {
/*     */   private static final class RPOFamiliyProxy
/*     */     implements RPOFamily
/*     */   {
/*     */     private final String identifier;
/*     */     private DatabaseAdapter adapter;
/*     */     
/*     */     private RPOFamiliyProxy(DatabaseAdapter adapter, String identifier) {
/*  47 */       this.identifier = identifier;
/*  48 */       this.adapter = adapter;
/*     */     }
/*     */     
/*     */     private RPOFamily resolve() {
/*  52 */       return this.adapter.resolveRPOFamilies(Collections.singleton(this.identifier)).get(this.identifier);
/*     */     }
/*     */     
/*     */     public String getDescription(Locale locale) {
/*  56 */       return resolve().getDescription(locale);
/*     */     }
/*     */     
/*     */     public String getID() {
/*  60 */       return this.identifier;
/*     */     }
/*     */   }
/*     */   
/*  64 */   private static final Logger log = Logger.getLogger(DatabaseAdapter.class);
/*     */   
/*     */   private Configuration config;
/*     */   
/*     */   private IDatabaseLink dbLink;
/*     */   
/*     */   private CachedRetrievalSupportV3 retrievalSupport_Families;
/*     */ 
/*     */   
/*     */   public DatabaseAdapter(Configuration config) {
/*  74 */     this.config = config;
/*  75 */     this.dbLink = DatabaseLink.openDatabase((Configuration)new SubConfigurationWrapper(config, "db."));
/*  76 */     this.retrievalSupport_Families = new CachedRetrievalSupportV3("RPOFamily", ConNvent.create(new ConnectionProvider()
/*     */           {
/*     */             public void releaseConnection(Connection connection) {
/*  79 */               DatabaseAdapter.this.dbLink.releaseConnection(connection);
/*     */             }
/*     */             
/*     */             public Connection getConnection() {
/*     */               try {
/*  84 */                 return DatabaseAdapter.this.dbLink.requestConnection();
/*  85 */               } catch (Exception e) {
/*  86 */                 throw new RuntimeException(e);
/*     */               } 
/*     */             }
/*     */           },  60000L), Tis2webUtil.createStdCache());
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, RPOFamily> resolveRPOFamilies(Collection<String> ids) {
/*     */     try {
/*  95 */       return this.retrievalSupport_Families.getObjects(ids, new CachedRetrievalSupportV3.Callback()
/*     */           {
/*     */             public void setParameters(Object identifier, PreparedStatement stmt) throws SQLException {
/*  98 */               stmt.setString(1, String.valueOf(identifier));
/*     */             }
/*     */ 
/*     */             
/*     */             public void initStatement(PreparedStatement stmt) throws SQLException {}
/*     */             
/*     */             public String getQuery() {
/* 105 */               return "select locale, description from rpo_family_description where familyID=?";
/*     */             }
/*     */             
/*     */             public Object createObject(Object identifier, ResultSet rs) throws Exception {
/* 109 */               Map<Object, Object> localeToDescription = new HashMap<Object, Object>();
/*     */               while (true) {
/* 111 */                 Locale locale = Util.parseLocale(rs.getString(1));
/* 112 */                 String description = rs.getString(2);
/* 113 */                 localeToDescription.put(locale, description);
/* 114 */                 if (!rs.next())
/* 115 */                   return new RPOFamiliyRI(String.valueOf(identifier), new I15dTextSupport(Locale.ENGLISH, localeToDescription)); 
/*     */               } 
/*     */             }
/*     */             public Object createKey(Object identifier) {
/* 119 */               return identifier;
/*     */             }
/*     */           });
/* 122 */     } catch (Exception e) {
/* 123 */       throw new RuntimeException(e);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map readRPOs(Connection connection, Collection rpoIDs) throws SQLException {
/*     */     // Byte code:
/*     */     //   0: getstatic com/eoos/gm/tis2web/rpo/DatabaseAdapter.log : Lorg/apache/log4j/Logger;
/*     */     //   3: invokevirtual isDebugEnabled : ()Z
/*     */     //   6: ifeq -> 34
/*     */     //   9: getstatic com/eoos/gm/tis2web/rpo/DatabaseAdapter.log : Lorg/apache/log4j/Logger;
/*     */     //   12: new java/lang/StringBuilder
/*     */     //   15: dup
/*     */     //   16: invokespecial <init> : ()V
/*     */     //   19: ldc 'reading RPOs for ids: '
/*     */     //   21: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   24: aload_2
/*     */     //   25: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   28: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   31: invokevirtual debug : (Ljava/lang/Object;)V
/*     */     //   34: aload_1
/*     */     //   35: ldc 'select familyID, locale, description from rpo_description where rpocode=?'
/*     */     //   37: invokeinterface prepareStatement : (Ljava/lang/String;)Ljava/sql/PreparedStatement;
/*     */     //   42: astore_3
/*     */     //   43: new java/util/LinkedHashMap
/*     */     //   46: dup
/*     */     //   47: invokespecial <init> : ()V
/*     */     //   50: astore #4
/*     */     //   52: new java/util/HashMap
/*     */     //   55: dup
/*     */     //   56: invokespecial <init> : ()V
/*     */     //   59: astore #5
/*     */     //   61: aload_2
/*     */     //   62: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   67: astore #6
/*     */     //   69: aload #6
/*     */     //   71: invokeinterface hasNext : ()Z
/*     */     //   76: ifeq -> 327
/*     */     //   79: aload #6
/*     */     //   81: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   86: checkcast java/lang/String
/*     */     //   89: astore #7
/*     */     //   91: aload_3
/*     */     //   92: iconst_1
/*     */     //   93: aload #7
/*     */     //   95: invokeinterface setString : (ILjava/lang/String;)V
/*     */     //   100: aload_3
/*     */     //   101: invokeinterface executeQuery : ()Ljava/sql/ResultSet;
/*     */     //   106: astore #8
/*     */     //   108: iconst_0
/*     */     //   109: istore #9
/*     */     //   111: aconst_null
/*     */     //   112: astore #10
/*     */     //   114: new java/util/HashMap
/*     */     //   117: dup
/*     */     //   118: invokespecial <init> : ()V
/*     */     //   121: astore #11
/*     */     //   123: aload #8
/*     */     //   125: invokeinterface next : ()Z
/*     */     //   130: ifeq -> 213
/*     */     //   133: iconst_1
/*     */     //   134: istore #9
/*     */     //   136: aload #10
/*     */     //   138: ifnonnull -> 175
/*     */     //   141: aload #8
/*     */     //   143: iconst_1
/*     */     //   144: invokeinterface getString : (I)Ljava/lang/String;
/*     */     //   149: astore #12
/*     */     //   151: aload #5
/*     */     //   153: aload #12
/*     */     //   155: aconst_null
/*     */     //   156: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   161: pop
/*     */     //   162: new com/eoos/gm/tis2web/rpo/DatabaseAdapter$RPOFamiliyProxy
/*     */     //   165: dup
/*     */     //   166: aload_0
/*     */     //   167: aload #12
/*     */     //   169: aconst_null
/*     */     //   170: invokespecial <init> : (Lcom/eoos/gm/tis2web/rpo/DatabaseAdapter;Ljava/lang/String;Lcom/eoos/gm/tis2web/rpo/DatabaseAdapter$1;)V
/*     */     //   173: astore #10
/*     */     //   175: aload #8
/*     */     //   177: iconst_2
/*     */     //   178: invokeinterface getString : (I)Ljava/lang/String;
/*     */     //   183: invokestatic parseLocale : (Ljava/lang/String;)Ljava/util/Locale;
/*     */     //   186: astore #12
/*     */     //   188: aload #8
/*     */     //   190: iconst_3
/*     */     //   191: invokeinterface getString : (I)Ljava/lang/String;
/*     */     //   196: astore #13
/*     */     //   198: aload #11
/*     */     //   200: aload #12
/*     */     //   202: aload #13
/*     */     //   204: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   209: pop
/*     */     //   210: goto -> 123
/*     */     //   213: iload #9
/*     */     //   215: ifeq -> 254
/*     */     //   218: aload #4
/*     */     //   220: aload #7
/*     */     //   222: new com/eoos/gm/tis2web/rpo/ri/RPORI
/*     */     //   225: dup
/*     */     //   226: aload #7
/*     */     //   228: new com/eoos/scsm/v2/util/I15dTextSupport
/*     */     //   231: dup
/*     */     //   232: getstatic java/util/Locale.ENGLISH : Ljava/util/Locale;
/*     */     //   235: aload #11
/*     */     //   237: invokespecial <init> : (Ljava/util/Locale;Ljava/util/Map;)V
/*     */     //   240: aload #10
/*     */     //   242: invokespecial <init> : (Ljava/lang/String;Lcom/eoos/scsm/v2/util/I15dTextSupport;Lcom/eoos/gm/tis2web/rpo/api/RPOFamily;)V
/*     */     //   245: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   250: pop
/*     */     //   251: goto -> 299
/*     */     //   254: getstatic com/eoos/gm/tis2web/rpo/DatabaseAdapter.log : Lorg/apache/log4j/Logger;
/*     */     //   257: new java/lang/StringBuilder
/*     */     //   260: dup
/*     */     //   261: invokespecial <init> : ()V
/*     */     //   264: ldc 'no data found for RPO: '
/*     */     //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   269: aload #7
/*     */     //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   274: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   277: invokevirtual warn : (Ljava/lang/Object;)V
/*     */     //   280: aload #4
/*     */     //   282: aload #7
/*     */     //   284: new com/eoos/gm/tis2web/rpo/ri/RPOCodeOnly
/*     */     //   287: dup
/*     */     //   288: aload #7
/*     */     //   290: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   293: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   298: pop
/*     */     //   299: jsr -> 313
/*     */     //   302: goto -> 324
/*     */     //   305: astore #14
/*     */     //   307: jsr -> 313
/*     */     //   310: aload #14
/*     */     //   312: athrow
/*     */     //   313: astore #15
/*     */     //   315: aload #8
/*     */     //   317: invokeinterface close : ()V
/*     */     //   322: ret #15
/*     */     //   324: goto -> 69
/*     */     //   327: aload #5
/*     */     //   329: aload_0
/*     */     //   330: aload #5
/*     */     //   332: invokeinterface keySet : ()Ljava/util/Set;
/*     */     //   337: invokevirtual resolveRPOFamilies : (Ljava/util/Collection;)Ljava/util/Map;
/*     */     //   340: invokeinterface putAll : (Ljava/util/Map;)V
/*     */     //   345: aload #4
/*     */     //   347: astore #6
/*     */     //   349: jsr -> 363
/*     */     //   352: aload #6
/*     */     //   354: areturn
/*     */     //   355: astore #16
/*     */     //   357: jsr -> 363
/*     */     //   360: aload #16
/*     */     //   362: athrow
/*     */     //   363: astore #17
/*     */     //   365: aload_3
/*     */     //   366: invokeinterface close : ()V
/*     */     //   371: ret #17
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #129	-> 0
/*     */     //   #130	-> 9
/*     */     //   #132	-> 34
/*     */     //   #134	-> 43
/*     */     //   #135	-> 52
/*     */     //   #137	-> 61
/*     */     //   #138	-> 79
/*     */     //   #139	-> 91
/*     */     //   #140	-> 100
/*     */     //   #142	-> 108
/*     */     //   #143	-> 111
/*     */     //   #144	-> 114
/*     */     //   #145	-> 123
/*     */     //   #146	-> 133
/*     */     //   #147	-> 136
/*     */     //   #148	-> 141
/*     */     //   #149	-> 151
/*     */     //   #150	-> 162
/*     */     //   #152	-> 175
/*     */     //   #153	-> 188
/*     */     //   #154	-> 198
/*     */     //   #155	-> 210
/*     */     //   #156	-> 213
/*     */     //   #157	-> 218
/*     */     //   #159	-> 254
/*     */     //   #160	-> 280
/*     */     //   #162	-> 299
/*     */     //   #164	-> 302
/*     */     //   #163	-> 305
/*     */     //   #165	-> 324
/*     */     //   #167	-> 327
/*     */     //   #168	-> 345
/*     */     //   #171	-> 355
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   151	24	12	strFamily	Ljava/lang/String;
/*     */     //   188	22	12	locale	Ljava/util/Locale;
/*     */     //   198	12	13	description	Ljava/lang/String;
/*     */     //   111	188	9	found	Z
/*     */     //   114	185	10	rpoFamily	Lcom/eoos/gm/tis2web/rpo/api/RPOFamily;
/*     */     //   123	176	11	localeToDescription	Ljava/util/Map;
/*     */     //   91	233	7	rpoCode	Ljava/lang/String;
/*     */     //   108	216	8	rs	Ljava/sql/ResultSet;
/*     */     //   69	258	6	iter	Ljava/util/Iterator;
/*     */     //   52	303	4	result	Ljava/util/Map;
/*     */     //   61	294	5	resolvedFamily	Ljava/util/Map;
/*     */     //   0	373	0	this	Lcom/eoos/gm/tis2web/rpo/DatabaseAdapter;
/*     */     //   0	373	1	connection	Ljava/sql/Connection;
/*     */     //   0	373	2	rpoIDs	Ljava/util/Collection;
/*     */     //   43	330	3	stmt	Ljava/sql/PreparedStatement;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   43	352	355	finally
/*     */     //   108	302	305	finally
/*     */     //   305	310	305	finally
/*     */     //   355	360	355	finally
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RPOContainer getRPOs(String vin) throws VIN.InvalidVINException, VIN.UnsupportedVINException {
/* 176 */     log.info("retrieving RPOs for VIN: " + vin + " ...");
/*     */     try {
/* 178 */       Connection connection = this.dbLink.requestConnection();
/*     */       try {
/* 180 */         String modelDesignator = null;
/* 181 */         String vehiclenumber = null;
/* 182 */         Collection rpoCodes = new LinkedHashSet();
/* 183 */         boolean vehicleNumberAttributeExists = true;
/* 184 */         PreparedStatement stmt = connection.prepareStatement("select * from rpo_vin where vin=?");
/*     */         try {
/* 186 */           stmt.setString(1, Util.toUpperCase(vin));
/* 187 */           ResultSet rs = stmt.executeQuery();
/*     */           try {
/* 189 */             if (rs.next()) {
/* 190 */               String codeList = rs.getString("rpocode_string");
/* 191 */               modelDesignator = rs.getString("modeldesignator");
/*     */               try {
/* 193 */                 vehiclenumber = rs.getString("vehiclenumber");
/* 194 */               } catch (Exception e) {
/* 195 */                 vehicleNumberAttributeExists = false;
/*     */               } 
/* 197 */               rpoCodes.addAll(Util.parseList(codeList));
/*     */             } else {
/* 199 */               throw new VIN.UnsupportedVINException(vin);
/*     */             } 
/*     */           } finally {
/* 202 */             rs.close();
/*     */           } 
/*     */         } finally {
/*     */           
/* 206 */           stmt.close();
/*     */         } 
/*     */         
/* 209 */         if (!vehicleNumberAttributeExists) {
/* 210 */           log.info("Table RPO_VIN w/o Vehicle Number Attribute");
/*     */         }
/* 212 */         Map rpos = readRPOs(connection, rpoCodes);
/* 213 */         return (RPOContainer)new RPOContainerRI(modelDesignator, vehiclenumber, rpos.values());
/*     */       } finally {
/*     */         
/* 216 */         this.dbLink.releaseConnection(connection);
/*     */       } 
/* 218 */     } catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.UnsupportedVINException e) {
/* 219 */       throw e;
/* 220 */     } catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.InvalidVINException e) {
/* 221 */       throw e;
/* 222 */     } catch (Exception e) {
/* 223 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Throwable {
/* 229 */     Log4jUtil.attachConsoleAppender();
/* 230 */     Properties properties = new Properties();
/* 231 */     properties.setProperty("db.url", "jdbc:transbase://pollux:5024/rpo");
/* 232 */     properties.setProperty("db.drv", "transbase.jdbc.Driver");
/* 233 */     properties.setProperty("db.usr", "tbadmin");
/* 234 */     properties.setProperty("db.pwd", "dgs");
/* 235 */     PropertiesConfigurationImpl propertiesConfigurationImpl = new PropertiesConfigurationImpl(properties);
/*     */     
/* 237 */     DatabaseAdapter adapter = new DatabaseAdapter((Configuration)propertiesConfigurationImpl);
/* 238 */     adapter.getRPOs("W0LGS67E291000276");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\DatabaseAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */