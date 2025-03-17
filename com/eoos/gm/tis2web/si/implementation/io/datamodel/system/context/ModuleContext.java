/*     */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.system.context;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.sitfilter.SIT;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ModuleContext
/*     */ {
/*     */   public static final String PAGEID_HOME = "SI_MAIN";
/*     */   public static final String PAGEID_NUMBER_SEARCH = "SI_NUMBER_SEARCH";
/*     */   public static final String PAGEID_TEXT_SEARCH = "SI_TEXT_SEARCH";
/*     */   public static final String PAGEID_BOOKMARK = "SI_BOOKMARK";
/*     */   public static final String PAGEID_STDINFO = "SI_DISPLAY";
/*     */   public static final String PAGEID_TSB_SEARCH = "SI_BULLETINS";
/*     */   public static final String PAGEID_SPECIAL_BROCHURES = "SI_SPECIAL_BROCHURES";
/*     */   public static final String PAGEID_FAULT_DIAG = "SI_FAULT_DIAG";
/*  36 */   protected static Map instances = new HashMap<Object, Object>();
/*     */   
/*     */   protected String sessionID;
/*     */   
/*  40 */   protected Boolean bulletinAccess = null;
/*     */   
/*  42 */   protected String pageIdentifier = null;
/*     */   
/*  44 */   protected String sit = null;
/*     */ 
/*     */   
/*     */   public ModuleContext(String sessionID) {
/*  48 */     this.sessionID = sessionID;
/*     */   }
/*     */   
/*     */   public static synchronized ModuleContext getInstance(String sessionID) {
/*  52 */     ModuleContext instance = (ModuleContext)instances.get(sessionID);
/*  53 */     if (instance == null) {
/*  54 */       instance = new ModuleContext(sessionID);
/*  55 */       instances.put(sessionID, instance);
/*     */     } 
/*  57 */     return instance;
/*     */   }
/*     */   
/*     */   public void update() {
/*  61 */     SharedContextProxy.getInstance(getClientContext()).update();
/*     */   }
/*     */   
/*     */   public ClientContext getClientContext() {
/*  65 */     return ClientContextProvider.getInstance().getContext(this.sessionID);
/*     */   }
/*     */   
/*     */   public SharedContextProxy getSharedContextProxy() {
/*  69 */     return SharedContextProxy.getInstance(getClientContext());
/*     */   }
/*     */   
/*     */   public synchronized boolean allowBulletinAccess() {
/*  73 */     if (this.bulletinAccess == null) {
/*     */       try {
/*  75 */         Set sits = SIT.getInstance(getClientContext()).getPositiv_ACL_SITs();
/*  76 */         return sits.contains(extractSITNumber("SIT-12"));
/*  77 */       } catch (Exception e) {
/*  78 */         this.bulletinAccess = new Boolean(false);
/*     */       } 
/*     */     }
/*  81 */     return this.bulletinAccess.booleanValue();
/*     */   }
/*     */   
/*     */   private String extractSITNumber(String sit) {
/*  85 */     int index = sit.indexOf("-");
/*  86 */     return sit.substring((index != -1) ? (index + 1) : 0);
/*     */   }
/*     */   
/*     */   public void setPageIdentifier(String identifier) {
/*  90 */     this.pageIdentifier = identifier;
/*     */   }
/*     */   
/*     */   public String getPageIdentifier() {
/*  94 */     return this.pageIdentifier;
/*     */   }
/*     */   
/*     */   public void setSIT(String sit) {
/*  98 */     this.sit = sit;
/*     */   }
/*     */   
/*     */   public String getSIT() {
/* 102 */     return this.sit;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\system\context\ModuleContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */