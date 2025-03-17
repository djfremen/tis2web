/*     */ package com.eoos.gm.tis2web.frame.scout.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.scout.SporefBaseScout;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.security.execution.delimiter.ExecutionDelimiter;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class NaoVspScout
/*     */   extends SporefBaseScout
/*     */ {
/*  42 */   static final Object SYNC_GROUPMAPDATA = new Object();
/*     */   
/*     */   protected static List groupMapData;
/*     */   
/*     */   private static boolean sporefValidation = true;
/*     */   
/*     */   private static Set portalIds;
/*     */   
/*     */   private static Set languages;
/*     */   
/*     */   private static final String CONFIG_TOGGLE_KEY = "frame.scout.nao.naovspscout.sporefticket.required";
/*     */   
/*     */   private static final String PORTAL_MAPPING_KEY = "frame.login.scout.portalmapping.NaoVspScout";
/*     */   
/*     */   private static final String SUPPORTED_LANGUAGES_KEY = "frame.scout.nao.naovspscout.languages";
/*     */   
/*     */   private static final String DEFAULT_LOCALE_COUNTRY_KEY = "frame.scout.nao.naovspscout.langcountry";
/*     */   
/*     */   private static final String DB_SETTINGS_KEY = "frame.scout.nao.naovspscout.db";
/*     */   
/*     */   private static final String DB_MAPTABLE_KEY = "frame.scout.nao.naovspscout.db.maptable";
/*     */   
/*  64 */   private static final List CONFIG_KEYS = Arrays.asList(new String[] { "frame.scout.nao.naovspscout.languages", "frame.scout.nao.naovspscout.sporefticket.required", "frame.scout.nao.naovspscout.sporefticket.secret", "frame.scout.nao.naovspscout.sporefticket.roundby" });
/*     */   
/*     */   private static final String VARIABLE_LOG_PARAMETER = "frame.scout.nao.naovspscout.login.logparam";
/*     */   
/*  68 */   private static final Logger log = Logger.getLogger(NaoVspScout.class);
/*     */   
/*  70 */   private final Object SYNC_STATE = new Object();
/*     */   
/*  72 */   private Integer configHash = null;
/*     */ 
/*     */   
/*     */   public NaoVspScout() {
/*  76 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*     */     
/*  78 */     configService.addObserver(new ConfigurationService.Observer() {
/*     */           public void onModification() {
/*  80 */             synchronized (NaoVspScout.this.SYNC_STATE) {
/*  81 */               if (NaoVspScout.this.configHash != null && NaoVspScout.this.calcConfigHash(NaoVspScout.CONFIG_KEYS) != NaoVspScout.this.configHash.intValue()) {
/*  82 */                 NaoVspScout.log.info("Configuration changed - resetting ...");
/*  83 */                 NaoVspScout.this.resetConfiguration();
/*  84 */                 NaoVspScout.log.info("Configuration reset completed.");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  90 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginInfo getLoginInfo(Map params) {
/*  99 */     LoginInfo loginInfo = null;
/* 100 */     long startTime = (new Date()).getTime();
/* 101 */     if (!isCalidReference(params)) {
/* 102 */       loginInfo = new LoginInfo();
/* 103 */       loginInfo.setUser(retrieveUser(params));
/* 104 */       loginInfo.setLoginGroup((String)params.get("group"));
/* 105 */       Locale locale = getLocaleByBrowserSettings(params, languages, "frame.scout.nao.naovspscout.langcountry");
/* 106 */       loginInfo.setLocale(locale);
/* 107 */       loginInfo.setSPOREFCountry((String)params.get("country"));
/* 108 */       loginInfo.setCountry(getCountry(params, locale));
/* 109 */       loginInfo.setDivisions((String)params.get("divisions"));
/* 110 */       loginInfo.setFromAddr(retrieveSourceAddr(params));
/* 111 */       loginInfo.setConfParam(getConfParam(params));
/* 112 */       loginInfo.setOrigin(getOrigin(params));
/* 113 */       loginInfo.setPortalId(getPortalId(params));
/*     */       
/* 115 */       NaoVspCandidate groupMapWinner = getGroupMapWinner(params);
/* 116 */       if (groupMapWinner != null) {
/* 117 */         loginInfo.setT2WGroup(groupMapWinner.getT2WGroup());
/*     */         
/* 119 */         loginInfo.setGroup2ManufMap(getGroup2ManufMap(groupMapWinner));
/* 120 */         loginInfo.setDealerCode(getDealerCode(groupMapWinner));
/* 121 */         if (isSporefValid(params, sporefValidation) && isValidLoginInfo(loginInfo)) {
/* 122 */           loginInfo.setAuthorized();
/*     */         }
/* 124 */         if (getSpecialAccess(params)) {
/* 125 */           loginInfo.setSpecialAccess(true);
/* 126 */           loginInfo.setAuthorized();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 130 */       log.debug("Legacy Cal-ID reference: Ignoring login request.");
/*     */     } 
/* 132 */     long time = (new Date()).getTime() - startTime;
/* 133 */     log.debug("NaoVspScout.getLoginInfo took: " + time + " ms");
/* 134 */     return loginInfo;
/*     */   }
/*     */   
/*     */   public String getScoutClassName() {
/* 138 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   private void init() {
/* 142 */     synchronized (NaoVspScout.class) {
/* 143 */       log.info("Reading allowed portal_ID's and supported languages from configuration ... ");
/* 144 */       if (portalIds == null) {
/* 145 */         portalIds = multipleEntries2Set("frame.login.scout.portalmapping.NaoVspScout");
/*     */       }
/* 147 */       if (languages == null) {
/* 148 */         languages = singleEntry2Set("frame.scout.nao.naovspscout.languages");
/*     */       }
/* 150 */       log.info("Reading portal_ID's and languages completed.");
/*     */       
/* 152 */       log.info("Reading group map data from database ... ");
/* 153 */       if (groupMapData == null) {
/* 154 */         groupMapData = getGroupMapData();
/* 155 */         log.info("Reading group map data completed.");
/*     */       } else {
/* 157 */         log.info("Nothing to do: GroupMapData already populated.");
/*     */       } 
/* 159 */       sporefValidation = getSporefSettings("frame.scout.nao.naovspscout.sporefticket.required");
/* 160 */       log.info(sporefValidation ? "SPOREF validation: ON" : "SPOREF validation: OFF!!!");
/*     */     } 
/* 162 */     this.configHash = Integer.valueOf(calcConfigHash(CONFIG_KEYS));
/*     */   }
/*     */   
/*     */   protected static void resetGroupMapData() {
/* 166 */     synchronized (SYNC_GROUPMAPDATA) {
/* 167 */       groupMapData = getGroupMapData();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetConfiguration() {
/* 172 */     portalIds = multipleEntries2Set("frame.login.scout.portalmapping.NaoVspScout");
/* 173 */     languages = singleEntry2Set("frame.scout.nao.naovspscout.languages");
/* 174 */     sporefValidation = getSporefSettings("frame.scout.nao.naovspscout.sporefticket.required");
/* 175 */     this.configHash = Integer.valueOf(calcConfigHash(CONFIG_KEYS));
/*     */   }
/*     */   private static List getGroupMapData() {
/*     */     DatabaseLink databaseLink;
/* 179 */     List<NaoVspCandidate> result = new LinkedList();
/* 180 */     IDatabaseLink db = null;
/* 181 */     Connection connection = null;
/* 182 */     Statement stmt = null;
/* 183 */     ResultSet rs = null;
/* 184 */     String tableName = ApplicationContext.getInstance().getProperty("frame.scout.nao.naovspscout.db.maptable");
/*     */     try {
/* 186 */       databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "frame.scout.nao.naovspscout.db");
/* 187 */       log.debug(databaseLink.getDatabaseLinkDescription());
/* 188 */       connection = databaseLink.requestConnection();
/* 189 */       stmt = connection.createStatement();
/* 190 */       String query = databaseLink.translate("select * from " + tableName);
/* 191 */       rs = stmt.executeQuery(query);
/* 192 */       while (rs.next()) {
/* 193 */         NaoVspCandidate candidate = new NaoVspCandidate(rs.getString("PREFIX").trim(), rs.getString("REQUIRED_GROUP").trim(), rs.getString("ID_FROM").trim(), rs.getString("ID_TO").trim(), rs.getString("POSTFIX").trim(), rs.getString("COUNTRY_CODE").trim(), rs.getString("DIVISION").trim(), rs.getString("TIS2WEB_GROUP").trim(), rs.getString("DEFAULT_BAC"));
/* 194 */         result.add(candidate);
/*     */       } 
/* 196 */     } catch (Exception e) {
/* 197 */       log.error("Cannot read " + tableName + " data from database: " + e);
/*     */     } finally {
/*     */       try {
/* 200 */         if (rs != null) {
/* 201 */           rs.close();
/*     */         }
/* 203 */         if (stmt != null) {
/* 204 */           stmt.close();
/*     */         }
/* 206 */       } catch (Exception e) {
/* 207 */         log.error("Cannot close: " + e);
/*     */       } 
/* 209 */       if (connection != null) {
/* 210 */         databaseLink.releaseConnection(connection);
/*     */       }
/*     */     } 
/* 213 */     return result;
/*     */   }
/*     */   
/*     */   public static String getCountry(Map params, Locale locale) {
/* 217 */     int FROM_REQUEST = 0;
/* 218 */     int FROM_LOCALE = 1;
/* 219 */     int DEFAULT = 2;
/* 220 */     int countrySelectedBy = -1;
/* 221 */     String result = null;
/* 222 */     String requestedCountry = null;
/*     */     
/* 224 */     if (params.get("country") != null) {
/* 225 */       requestedCountry = (String)params.get("country");
/* 226 */       if (requestedCountry.length() >= 2) {
/* 227 */         result = requestedCountry;
/* 228 */         countrySelectedBy = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     if (result == null && locale != null && locale.getCountry() != null) {
/* 233 */       result = locale.getCountry();
/* 234 */       countrySelectedBy = 1;
/*     */     } 
/*     */     
/* 237 */     if (result == null) {
/* 238 */       result = "US";
/* 239 */       countrySelectedBy = 2;
/*     */     } 
/* 241 */     if (log.isDebugEnabled()) {
/* 242 */       log.debug("Requested country: " + requestedCountry);
/* 243 */       String source = null;
/* 244 */       if (countrySelectedBy == 0) {
/* 245 */         source = "request";
/* 246 */       } else if (countrySelectedBy == 1) {
/* 247 */         source = "Locale";
/* 248 */       } else if (countrySelectedBy == 2) {
/* 249 */         source = "default";
/*     */       } else {
/* 251 */         source = "E_R_R_O_R!!!!!";
/*     */       } 
/* 253 */       log.debug("Final country: " + result + " (selected by " + source + ")");
/*     */     } 
/* 255 */     return result;
/*     */   }
/*     */   
/*     */   protected NaoVspCandidate getGroupMapWinner(Map params) {
/* 259 */     NaoVspCandidate result = null;
/* 260 */     NaoVspGroupHandler handler = new NaoVspGroupHandler();
/* 261 */     result = handler.getGroupMapWinner(params, groupMapData);
/* 262 */     return result;
/*     */   }
/*     */   
/*     */   private Map getGroup2ManufMap(NaoVspCandidate candidate) {
/* 266 */     Map<Object, Object> result = new HashMap<Object, Object>();
/* 267 */     String t2wGroup = candidate.getT2WGroup();
/* 268 */     if (t2wGroup != null) {
/* 269 */       HashSet<String> set = new HashSet();
/* 270 */       set.add("ALL");
/* 271 */       log.debug("TIS2Web internal group: " + t2wGroup);
/* 272 */       result.put(t2wGroup, set);
/*     */     } 
/* 274 */     return result;
/*     */   }
/*     */   
/*     */   private DealerCode getDealerCode(NaoVspCandidate candidate) {
/* 278 */     DealerCode result = new DealerCode();
/* 279 */     String bac = candidate.getBacCode();
/* 280 */     HashMap<Object, Object> dlrCodes = new HashMap<Object, Object>();
/* 281 */     if (bac != null)
/*     */     {
/* 283 */       dlrCodes.put(candidate.getT2WGroup(), bac);
/*     */     }
/* 285 */     result.setDealerCodes(dlrCodes);
/* 286 */     log.debug("BAC code: " + bac);
/* 287 */     return result;
/*     */   }
/*     */   
/*     */   private String getConfParam(Map params) {
/* 291 */     String result = null;
/* 292 */     String confParamName = ApplicationContext.getInstance().getProperty("frame.scout.nao.naovspscout.login.logparam");
/* 293 */     if (confParamName != null) {
/* 294 */       result = (String)params.get(confParamName);
/*     */     }
/* 296 */     return result;
/*     */   }
/*     */   
/*     */   protected boolean isValidLoginInfo(LoginInfo _loginInfo) {
/* 300 */     boolean result = false;
/* 301 */     if (_loginInfo.getUser() != null && _loginInfo.getGroup2ManufMap() != null && !_loginInfo.getGroup2ManufMap().isEmpty() && portalIds.contains(_loginInfo.getPortalId()))
/*     */     {
/* 303 */       result = true;
/*     */     }
/* 305 */     return result;
/*     */   }
/*     */   
/*     */   private boolean getSpecialAccess(Map params) {
/* 309 */     boolean retValue = false;
/*     */     try {
/* 311 */       String token = (String)params.get("ticket");
/* 312 */       if (token != null && token.trim().length() > 0) {
/* 313 */         retValue = ExecutionDelimiter.check(token);
/*     */       }
/* 315 */     } catch (Exception e) {
/* 316 */       log.debug("unable to evaluate special key");
/*     */     } 
/* 318 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\nao\NaoVspScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */