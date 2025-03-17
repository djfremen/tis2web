/*     */ package com.eoos.gm.tis2web.frame.scout.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.scout.ScoutException;
/*     */ import com.eoos.gm.tis2web.frame.scout.SporefBaseScout;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
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
/*     */ public class GmeCh21Scout
/*     */   extends SporefBaseScout
/*     */ {
/*  33 */   static final Object SYNC_GROUPMAPDATA = new Object();
/*     */   
/*     */   protected static List groupMapData;
/*     */   
/*     */   private static boolean sporefValidation = true;
/*     */   private static Set portalIds;
/*     */   private static Set languages;
/*     */   private static final String CONFIG_TOGGLE_KEY = "frame.scout.gme.gmech21scout.sporefticket.required";
/*     */   private static final String PORTAL_MAPPING_KEY = "frame.login.scout.portalmapping.GmeCh21Scout";
/*     */   private static final String SUPPORTED_LANGUAGES_KEY = "frame.scout.gme.gmech21scout.languages";
/*     */   private static final String DEFAULT_LOCALE_COUNTRY_KEY = "frame.scout.gme.gmech21scout.langcountry";
/*     */   private static final String DB_SETTINGS_KEY = "frame.scout.gme.gmech21scout.db";
/*     */   private static final String DB_MAPTABLE_KEY = "frame.scout.gme.gmech21scout.db.maptable";
/*  46 */   private static final List CONFIG_KEYS = Arrays.asList(new String[] { "frame.scout.gme.gmech21scout.languages", "frame.scout.gme.gmech21scout.sporefticket.required" });
/*     */   
/*  48 */   private static final Logger log = Logger.getLogger(GmeCh21Scout.class);
/*     */   
/*  50 */   private final Object SYNC_STATE = new Object();
/*  51 */   private Integer configHash = null;
/*     */ 
/*     */   
/*     */   public GmeCh21Scout() {
/*  55 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*     */     
/*  57 */     configService.addObserver(new ConfigurationService.Observer() {
/*     */           public void onModification() {
/*  59 */             synchronized (GmeCh21Scout.this.SYNC_STATE) {
/*  60 */               if (GmeCh21Scout.this.configHash != null && GmeCh21Scout.this.calcConfigHash(GmeCh21Scout.CONFIG_KEYS) != GmeCh21Scout.this.configHash.intValue()) {
/*  61 */                 GmeCh21Scout.log.info("Configuration changed - resetting ...");
/*  62 */                 GmeCh21Scout.this.resetConfiguration();
/*  63 */                 GmeCh21Scout.log.info("Configuration reset completed.");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  69 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  73 */     synchronized (GmeCh21Scout.class) {
/*  74 */       log.info("Reading allowed portal_ID's and languages from configuration ... ");
/*  75 */       if (portalIds == null) {
/*  76 */         portalIds = multipleEntries2Set("frame.login.scout.portalmapping.GmeCh21Scout");
/*     */       }
/*  78 */       if (languages == null) {
/*  79 */         languages = singleEntry2Set("frame.scout.gme.gmech21scout.languages");
/*     */       }
/*  81 */       log.info("Reading portal_ID's and languages completed.");
/*     */       
/*  83 */       log.info("Reading group map data from database ... ");
/*  84 */       if (groupMapData == null) {
/*  85 */         groupMapData = getGroupMapData();
/*  86 */         log.info("Reading group map data completed.");
/*     */       } else {
/*  88 */         log.info("Nothing to do: GroupMapData already populated.");
/*     */       } 
/*  90 */       sporefValidation = getSporefSettings("frame.scout.gme.gmech21scout.sporefticket.required");
/*  91 */       log.info(sporefValidation ? "SPOREF validation: ON" : "SPOREF validation: OFF!!!");
/*     */     } 
/*  93 */     this.configHash = Integer.valueOf(calcConfigHash(CONFIG_KEYS));
/*     */   }
/*     */   
/*     */   protected static void resetGroupMapData() {
/*  97 */     synchronized (SYNC_GROUPMAPDATA) {
/*  98 */       groupMapData = getGroupMapData();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetConfiguration() {
/* 103 */     portalIds = multipleEntries2Set("frame.login.scout.portalmapping.GmeCh21Scout");
/* 104 */     languages = singleEntry2Set("frame.scout.gme.gmech21scout.languages");
/* 105 */     sporefValidation = getSporefSettings("frame.scout.gme.gmech21scout.sporefticket.required");
/* 106 */     this.configHash = Integer.valueOf(calcConfigHash(CONFIG_KEYS));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginInfo getLoginInfo(Map params) {
/* 112 */     long startTime = (new Date()).getTime();
/* 113 */     LoginInfo loginInfo = null;
/* 114 */     if (!isCalidReference(params)) {
/* 115 */       loginInfo = new LoginInfo();
/* 116 */       loginInfo.setUser(retrieveUser(params));
/* 117 */       Locale locale = getLocaleByBrowserSettings(params, languages, "frame.scout.gme.gmech21scout.langcountry");
/* 118 */       loginInfo.setLocale(locale);
/* 119 */       loginInfo.setSPOREFCountry((String)params.get("country"));
/* 120 */       loginInfo.setCountry((String)params.get("country"));
/* 121 */       loginInfo.setDivisions((String)params.get("divisions"));
/* 122 */       loginInfo.setFromAddr(retrieveSourceAddr(params));
/* 123 */       loginInfo.setOrigin(getOrigin(params));
/* 124 */       loginInfo.setPortalId(getPortalId(params));
/*     */       
/* 126 */       GmeCh21Candidate groupMapWinner = getGroupMapWinner(params);
/* 127 */       if (groupMapWinner != null) {
/* 128 */         loginInfo.setT2WGroup(groupMapWinner.getT2WGroup());
/* 129 */         loginInfo.setGroup2ManufMap(getGroup2ManufMap(groupMapWinner));
/* 130 */         loginInfo.setDealerCode(getDealerCode(params, groupMapWinner));
/* 131 */         if (isSporefValid(params, sporefValidation) && isValidLoginInfo(loginInfo)) {
/* 132 */           loginInfo.setAuthorized();
/*     */         }
/* 134 */         if (getSpecialAccess(params)) {
/* 135 */           loginInfo.setSpecialAccess(true);
/* 136 */           loginInfo.setAuthorized();
/*     */         } 
/*     */       } 
/*     */     } else {
/* 140 */       log.debug("Legacy Cal-ID reference: Ignoring login request.");
/*     */     } 
/* 142 */     long time = (new Date()).getTime() - startTime;
/* 143 */     log.debug("GmeCh21Scout.getLoginInfo took: " + time + " ms");
/* 144 */     return loginInfo;
/*     */   }
/*     */   
/*     */   public String getScoutClassName() {
/* 148 */     return getClass().getName();
/*     */   }
/*     */   private static List getGroupMapData() {
/*     */     DatabaseLink databaseLink;
/* 152 */     List<GmeCh21Candidate> result = new LinkedList();
/* 153 */     IDatabaseLink db = null;
/* 154 */     Connection connection = null;
/* 155 */     Statement stmt = null;
/* 156 */     ResultSet rs = null;
/* 157 */     String tableName = ApplicationContext.getInstance().getProperty("frame.scout.gme.gmech21scout.db.maptable");
/*     */     try {
/* 159 */       databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "frame.scout.gme.gmech21scout.db");
/* 160 */       log.debug(databaseLink.getDatabaseLinkDescription());
/* 161 */       connection = databaseLink.requestConnection();
/* 162 */       stmt = connection.createStatement();
/* 163 */       String query = databaseLink.translate("select * from " + tableName);
/* 164 */       rs = stmt.executeQuery(query);
/* 165 */       while (rs.next()) {
/* 166 */         GmeCh21Candidate candidate = new GmeCh21Candidate(rs.getString("COUNTRY_CODE").trim(), rs.getString("DIVISION").trim(), rs.getString("TIS2WEB_GROUP").trim(), rs.getString("DEFAULT_BAC"));
/* 167 */         result.add(candidate);
/*     */       } 
/* 169 */     } catch (Exception e) {
/* 170 */       log.error("Cannot read " + tableName + " data from database: " + e);
/*     */     } finally {
/*     */       try {
/* 173 */         if (rs != null) {
/* 174 */           rs.close();
/*     */         }
/* 176 */         if (stmt != null) {
/* 177 */           stmt.close();
/*     */         }
/* 179 */       } catch (Exception e) {
/* 180 */         log.error("Cannot close: " + e);
/*     */       } 
/* 182 */       if (connection != null) {
/* 183 */         databaseLink.releaseConnection(connection);
/*     */       }
/*     */     } 
/* 186 */     return result;
/*     */   }
/*     */   
/*     */   private GmeCh21Candidate getGroupMapWinner(Map params) {
/* 190 */     GmeCh21Candidate result = null;
/* 191 */     GmeCh21GroupHandler handler = new GmeCh21GroupHandler();
/* 192 */     result = handler.getGroupMapWinner(params, groupMapData);
/* 193 */     return result;
/*     */   }
/*     */   
/*     */   private Map getGroup2ManufMap(GmeCh21Candidate candidate) {
/* 197 */     Map<Object, Object> result = new HashMap<Object, Object>();
/* 198 */     String t2wGroup = candidate.getT2WGroup();
/* 199 */     if (t2wGroup != null) {
/* 200 */       HashSet<String> set = new HashSet();
/* 201 */       set.add("ALL");
/* 202 */       log.debug("TIS2Web internal group: " + t2wGroup);
/* 203 */       result.put(t2wGroup, set);
/*     */     } 
/* 205 */     return result;
/*     */   }
/*     */   
/*     */   private char getPortalDesignator(Map params) {
/* 209 */     String portalID = getPortalId(params);
/* 210 */     String value = null;
/* 211 */     for (int i = 0; i < 3 && value == null; i++) {
/* 212 */       String key = "frame.login.scout.portal.designator.";
/* 213 */       switch (i) {
/*     */         case 0:
/* 215 */           key = key + portalID;
/*     */           break;
/*     */         case 1:
/* 218 */           key = key + Util.toUpperCase(portalID);
/*     */           break;
/*     */         case 2:
/* 221 */           key = key + Util.toLowerCase(portalID);
/*     */           break;
/*     */       } 
/* 224 */       value = ConfigurationServiceProvider.getService().getProperty(key);
/*     */     } 
/* 226 */     if (Util.isNullOrEmpty(value)) {
/* 227 */       throw new ScoutException("missing portal designator mapping for " + getPortalId(params));
/*     */     }
/* 229 */     return value.charAt(0);
/*     */   }
/*     */   
/*     */   private String getLocalDealerID(Map params) {
/* 233 */     String orgCode = null;
/* 234 */     Object tmp = params.get("DealerCode");
/* 235 */     if (tmp == null) {
/* 236 */       tmp = params.get("dealercode");
/*     */     }
/* 238 */     if (tmp.getClass().isArray()) {
/* 239 */       orgCode = ((String[])tmp)[0];
/*     */     } else {
/* 241 */       orgCode = (String)tmp;
/*     */     } 
/* 243 */     if (Util.isNullOrEmpty(orgCode)) {
/* 244 */       throw new ScoutException("missing login parameter 'DealerCode'");
/*     */     }
/* 246 */     return orgCode.substring(2);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDefaultBrand(Map params) {
/* 251 */     return (String)params.get("defaultbrand");
/*     */   }
/*     */   
/*     */   private DealerCode getDealerCode(Map params, GmeCh21Candidate candidate) {
/* 255 */     DealerCode result = new DealerCode();
/*     */     
/* 257 */     StringBuilder builder = new StringBuilder();
/* 258 */     builder.append(getPortalDesignator(params));
/* 259 */     builder.append(getDefaultBrand(params));
/* 260 */     builder.append(getLocalDealerID(params));
/* 261 */     while (builder.length() < 11) {
/* 262 */       builder.insert(1, '0');
/*     */     }
/*     */     
/* 265 */     HashMap<Object, Object> dlrCodes = new HashMap<Object, Object>();
/* 266 */     if (builder != null) {
/* 267 */       dlrCodes.put(candidate.getT2WGroup(), builder.toString());
/*     */     }
/* 269 */     result.setDealerCodes(dlrCodes);
/* 270 */     log.debug("BAC code: " + builder);
/* 271 */     return result;
/*     */   }
/*     */   
/*     */   private boolean isValidLoginInfo(LoginInfo _loginInfo) {
/* 275 */     boolean result = false;
/* 276 */     if (_loginInfo.getUser() != null && portalIds.contains(_loginInfo.getPortalId()))
/*     */     {
/* 278 */       result = true;
/*     */     }
/* 280 */     return result;
/*     */   }
/*     */   
/*     */   private boolean getSpecialAccess(Map params) {
/* 284 */     boolean retValue = false;
/*     */     try {
/* 286 */       String token = (String)params.get("ticket");
/* 287 */       if (token != null && token.trim().length() > 0) {
/* 288 */         retValue = ExecutionDelimiter.check(token);
/*     */       }
/* 290 */     } catch (Exception e) {
/* 291 */       log.debug("unable to evaluate special key");
/*     */     } 
/* 293 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeCh21Scout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */