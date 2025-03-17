/*     */ package com.eoos.gm.tis2web.frame.scout.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.LogoutTask;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.scout.SporefBaseScout;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimerTask;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GmeImt2Scout
/*     */   extends SporefBaseScout
/*     */ {
/*  31 */   protected static final Object SYNC_GROUPMAPDATA = new Object();
/*     */   
/*     */   protected static List groupMapData;
/*     */   
/*     */   private static boolean sporefValidation = true;
/*     */   
/*     */   private static Set portalIds;
/*     */   
/*     */   private static Set languages;
/*     */   
/*     */   private static final String CONFIG_TOGGLE_KEY = "frame.scout.gme.gmeimt2scout.sporefticket.required";
/*     */   
/*     */   private static final String PORTAL_MAPPING_KEY = "frame.login.scout.portalmapping.GmeImt2Scout";
/*     */   
/*     */   private static final String SUPPORTED_LANGUAGES_KEY = "frame.scout.gme.gmeimt2scout.languages";
/*     */   
/*     */   private static final String DEFAULT_LOCALE_COUNTRY_KEY = "frame.scout.gme.gmeimt2scout.langcountry";
/*     */   
/*     */   private static final String DB_SETTINGS_KEY = "frame.scout.gme.gmeimt2scout.db";
/*     */   
/*     */   private static final String DB_MAPTABLE_KEY = "frame.scout.gme.gmeimt2scout.db.maptable";
/*     */   
/*  53 */   private static final List CONFIG_KEYS = Arrays.asList(new String[] { "frame.scout.gme.gmeimt2scout.languages", "frame.scout.gme.gmeimt2scout.sporefticket.required" });
/*     */   
/*  55 */   private static final Logger log = Logger.getLogger(GmeImt2Scout.class);
/*     */   
/*  57 */   private final Object SYNC_STATE = new Object();
/*     */   
/*  59 */   private Integer configHash = null;
/*     */ 
/*     */   
/*     */   public GmeImt2Scout() {
/*  63 */     ConfigurationService configService = (ConfigurationService)FrameServiceProvider.getInstance().getService(ConfigurationService.class);
/*     */     
/*  65 */     configService.addObserver(new ConfigurationService.Observer() {
/*     */           public void onModification() {
/*  67 */             synchronized (GmeImt2Scout.this.SYNC_STATE) {
/*  68 */               if (GmeImt2Scout.this.configHash != null && GmeImt2Scout.this.calcConfigHash(GmeImt2Scout.CONFIG_KEYS) != GmeImt2Scout.this.configHash.intValue()) {
/*  69 */                 GmeImt2Scout.log.info("Configuration changed - resetting ...");
/*  70 */                 GmeImt2Scout.this.resetConfiguration();
/*  71 */                 GmeImt2Scout.log.info("Configuration reset completed.");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*  76 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  80 */     synchronized (GmeImt2Scout.class) {
/*  81 */       log.info("Reading allowed portal_ID's and supported languages from configuration ... ");
/*  82 */       if (portalIds == null) {
/*  83 */         portalIds = multipleEntries2Set("frame.login.scout.portalmapping.GmeImt2Scout");
/*     */       }
/*  85 */       if (languages == null) {
/*  86 */         languages = singleEntry2Set("frame.scout.gme.gmeimt2scout.languages");
/*     */       }
/*  88 */       log.info("Reading portal_ID's and languages completed.");
/*     */       
/*  90 */       log.info("Reading group map data from database ... ");
/*  91 */       if (groupMapData == null) {
/*  92 */         groupMapData = getGroupMapData();
/*  93 */         log.info("Reading group map data completed.");
/*     */       } else {
/*  95 */         log.info("Nothing to do: GroupMapData already populated.");
/*     */       } 
/*     */       
/*  98 */       sporefValidation = getSporefSettings("frame.scout.gme.gmeimt2scout.sporefticket.required");
/*  99 */       log.info(sporefValidation ? "SPOREF validation: ON" : "SPOREF validation: OFF!!!");
/*     */     } 
/* 101 */     this.configHash = Integer.valueOf(calcConfigHash(CONFIG_KEYS));
/*     */   }
/*     */   
/*     */   protected static void resetGroupMapData() {
/* 105 */     synchronized (SYNC_GROUPMAPDATA) {
/* 106 */       groupMapData = getGroupMapData();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetConfiguration() {
/* 111 */     portalIds = multipleEntries2Set("frame.login.scout.portalmapping.GmeImt2Scout");
/* 112 */     languages = singleEntry2Set("frame.scout.gme.gmeimt2scout.languages");
/* 113 */     sporefValidation = getSporefSettings("frame.scout.gme.gmeimt2scout.sporefticket.required");
/* 114 */     this.configHash = Integer.valueOf(calcConfigHash(CONFIG_KEYS));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginInfo getLoginInfo(Map params) {
/* 120 */     long startTime = (new Date()).getTime();
/* 121 */     LoginInfo loginInfo = null;
/* 122 */     if (!isCalidReference(params)) {
/* 123 */       loginInfo = new LoginInfo();
/* 124 */       loginInfo.setUser(retrieveUser(params));
/* 125 */       loginInfo.setLoginGroup((String)params.get("group"));
/* 126 */       loginInfo.setLocale(getLocaleByRequestParameter("language", params, languages, "frame.scout.gme.gmeimt2scout.langcountry"));
/* 127 */       loginInfo.setSPOREFCountry((String)params.get("country"));
/* 128 */       loginInfo.setCountry(getCountry(params));
/* 129 */       loginInfo.setDivisions((String)params.get("manufacturers"));
/* 130 */       loginInfo.setFromAddr(retrieveSourceAddr(params));
/* 131 */       loginInfo.setOrigin(getOrigin(params));
/* 132 */       loginInfo.setPortalId(getPortalId(params));
/*     */       
/* 134 */       GmeImt2Candidate groupMapWinner = getGroupMapWinner(params);
/* 135 */       if (groupMapWinner != null) {
/* 136 */         loginInfo.setT2WGroup(groupMapWinner.getT2WGroup());
/* 137 */         loginInfo.setGroup2ManufMap(getGroup2ManufMap(groupMapWinner));
/* 138 */         loginInfo.setDealerCode(getDealerCode(groupMapWinner));
/* 139 */         Long duration = null;
/* 140 */         if (isValidLoginInfo(loginInfo) && isSporefValid(params, sporefValidation) && (duration = getDuration(params)) != null) {
/* 141 */           loginInfo.setAuthorized();
/* 142 */           scheduleLogout(loginInfo.getUser(), new Date(System.currentTimeMillis() + duration.longValue()));
/* 143 */           loginInfo.setMaximumSessionDuration(duration.longValue());
/*     */         } 
/*     */       } 
/*     */     } else {
/* 147 */       log.debug("Legacy Cal-ID reference: Ignoring login request.");
/*     */     } 
/* 149 */     long time = (new Date()).getTime() - startTime;
/* 150 */     log.debug("GmeImt2Scout.getLoginInfo took: " + time + " ms");
/* 151 */     return loginInfo;
/*     */   }
/*     */   
/*     */   private void scheduleLogout(final String user, Date date) {
/* 155 */     final LogoutTask logoutTask = new LogoutTask(user);
/* 156 */     TimerTask tt = Util.createTimerTask(new Runnable()
/*     */         {
/*     */           public void run() {
/* 159 */             GmeImt2Scout.log.debug("running scheduled logout (subscription expired) for GMEIMT2 portal session: " + String.valueOf(user));
/* 160 */             logoutTask.execute();
/*     */           }
/*     */         });
/*     */     
/* 164 */     Util.getTimer().schedule(tt, date);
/*     */   }
/*     */   
/*     */   public String getScoutClassName() {
/* 168 */     return getClass().getName();
/*     */   }
/*     */   
/*     */   protected boolean isValidLoginInfo(LoginInfo _loginInfo) {
/* 172 */     boolean result = false;
/* 173 */     if (_loginInfo.getUser() != null && _loginInfo.getGroup2ManufMap() != null && !_loginInfo.getGroup2ManufMap().isEmpty()) {
/* 174 */       result = true;
/*     */     }
/* 176 */     return result;
/*     */   }
/*     */   
/*     */   private String getCountry(Map params) {
/* 180 */     String result = (String)params.get("country");
/* 181 */     if (result == null) {
/* 182 */       result = "DE";
/*     */     }
/* 184 */     return result;
/*     */   }
/*     */   
/*     */   private Long getDuration(Map params) {
/* 188 */     Long result = null;
/*     */     try {
/* 190 */       result = Long.valueOf((String)params.get("subscriptiontime"));
/* 191 */     } catch (Exception e) {
/* 192 */       log.error("Invalid subscriptiontime from portal: " + params.get("subscriptiontime"));
/*     */     } 
/* 194 */     return result;
/*     */   }
/*     */   private static List getGroupMapData() {
/*     */     DatabaseLink databaseLink;
/* 198 */     List<GmeImt2Candidate> result = new LinkedList();
/* 199 */     IDatabaseLink db = null;
/* 200 */     Connection connection = null;
/* 201 */     Statement stmt = null;
/* 202 */     ResultSet rs = null;
/* 203 */     String tableName = ApplicationContext.getInstance().getProperty("frame.scout.gme.gmeimt2scout.db.maptable");
/*     */     try {
/* 205 */       databaseLink = DatabaseLink.openDatabase((Configuration)ApplicationContext.getInstance(), "frame.scout.gme.gmeimt2scout.db");
/* 206 */       log.debug(databaseLink.getDatabaseLinkDescription());
/* 207 */       connection = databaseLink.requestConnection();
/* 208 */       stmt = connection.createStatement();
/* 209 */       String query = databaseLink.translate("select * from " + tableName);
/* 210 */       rs = stmt.executeQuery(query);
/* 211 */       while (rs.next()) {
/* 212 */         GmeImt2Candidate candidate = new GmeImt2Candidate(rs.getString("COUNTRY_CODE").trim(), rs.getString("MANUFACTURERS").trim(), rs.getString("PORTAL_GROUP").trim(), rs.getString("TIS2WEB_GROUP").trim(), rs.getString("DEFAULT_BAC"));
/* 213 */         result.add(candidate);
/*     */       } 
/* 215 */     } catch (Exception e) {
/* 216 */       log.error("Cannot read " + tableName + " data from database: " + e);
/*     */     } finally {
/*     */       try {
/* 219 */         if (rs != null) {
/* 220 */           rs.close();
/*     */         }
/* 222 */         if (stmt != null) {
/* 223 */           stmt.close();
/*     */         }
/* 225 */       } catch (Exception e) {
/* 226 */         log.error("Cannot close: " + e);
/*     */       } 
/* 228 */       if (connection != null) {
/* 229 */         databaseLink.releaseConnection(connection);
/*     */       }
/*     */     } 
/* 232 */     return result;
/*     */   }
/*     */   
/*     */   private GmeImt2Candidate getGroupMapWinner(Map params) {
/* 236 */     GmeImt2Candidate result = null;
/* 237 */     GmeImt2GroupHandler handler = new GmeImt2GroupHandler();
/* 238 */     result = handler.getGroupMapWinner(params, groupMapData);
/* 239 */     return result;
/*     */   }
/*     */   
/*     */   private Map getGroup2ManufMap(GmeImt2Candidate candidate) {
/* 243 */     Map<Object, Object> result = new HashMap<Object, Object>();
/* 244 */     String t2wGroup = candidate.getT2WGroup();
/* 245 */     if (t2wGroup != null) {
/* 246 */       HashSet<String> set = new HashSet();
/* 247 */       set.add("ALL");
/* 248 */       log.debug("TIS2Web internal group: " + t2wGroup);
/* 249 */       result.put(t2wGroup, set);
/*     */     } 
/* 251 */     return result;
/*     */   }
/*     */   
/*     */   private DealerCode getDealerCode(GmeImt2Candidate candidate) {
/* 255 */     DealerCode result = new DealerCode();
/* 256 */     String bac = candidate.getBacCode();
/* 257 */     HashMap<Object, Object> dlrCodes = new HashMap<Object, Object>();
/* 258 */     if (bac != null) {
/* 259 */       dlrCodes.put(candidate.getT2WGroup(), bac);
/*     */     }
/* 261 */     result.setDealerCodes(dlrCodes);
/* 262 */     log.debug("BAC code: " + bac);
/* 263 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeImt2Scout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */