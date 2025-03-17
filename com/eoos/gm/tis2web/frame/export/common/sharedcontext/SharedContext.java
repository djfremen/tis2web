/*     */ package com.eoos.gm.tis2web.frame.export.common.sharedcontext;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ public class SharedContext
/*     */ {
/*  26 */   public static final Integer ENC_WINDOWS = Integer.valueOf(1);
/*     */   
/*  28 */   public static final Integer ENC_UTF8 = Integer.valueOf(2);
/*     */   
/*     */   private static final String DEFAULT_SM_ID = "Frame.DefaultSalesmake.ID";
/*     */   
/*     */   private static final String COUNTRY_ID = "Frame.Country.ID";
/*     */   
/*     */   private static final String USR_GRP_2_MANUF_ID = "Frame.UsrGroup2Manuf.ID";
/*     */   
/*     */   private static final String TEXT_ENCODING = "text.download.encoding";
/*     */   
/*     */   private static final String PERSISTENT_OBJECTS = "persistent.objects";
/*     */   
/*     */   private static final String DISPLAY_HEIGHT = "displayheigth";
/*     */   
/*  42 */   private static final Object PLUGIN_CHECK = "plugin.check";
/*     */   
/*  44 */   private static final Object USE_LTHOURS = "use.lthours";
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
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientContext context;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean specialAccess;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public SharedContext(ClientContext context) {
/* 195 */     this.specialAccess = false; this.context = context; this.context.addLogoutListener(new ClientContextBase.LogoutListener() {
/*     */           public void onLogout() { SharedContext.this.context = null; }
/*     */         });
/* 198 */   } public static SharedContext getInstance(ClientContext context) { synchronized (context.getLockObject()) { SharedContext instance = (SharedContext)context.getObject(SharedContext.class); if (instance == null) { instance = new SharedContext(context); context.storeObject(SharedContext.class, instance); }  return instance; }  } public void setUsrGroup2ManufMap(Map usrGroup2Manuf) { this.context.storeObject("Frame.UsrGroup2Manuf.ID", usrGroup2Manuf); } public Map getUsrGroup2ManufMap() { return (Map)this.context.getObject("Frame.UsrGroup2Manuf.ID"); } public void setDefaultSalesmake(String dsm) { getDefaultSalesmake(); this.context.storeObject("Frame.DefaultSalesmake.ID", dsm); } public String getDefaultSalesmake() { return (String)this.context.getObject("Frame.DefaultSalesmake.ID"); } public void setObject(Object key, Object o) { this.context.storeObject(key, o); } public Object getObject(Object key) { return this.context.getObject(key); } public String getCountry() { return (String)this.context.getObject("Frame.Country.ID"); } public void setCountry(String country) { this.context.storeObject("Frame.Country.ID", country); } public void setDisplayHeight(Integer height) { getDisplayHeight(); this.context.storeObject("displayheigth", height); } public Integer getDisplayHeight() { Integer retValue = (Integer)this.context.getObject("displayheigth"); if (retValue == null) retValue = Integer.valueOf(500);  return retValue; } public void setTextEncoding(Integer encoding) { this.context.storeObject("text.download.encoding", encoding); } public void setSpecialAccess(boolean b) { this.specialAccess = b; }
/*     */   public Integer getTextEncoding() { Integer retValue = (Integer)this.context.getObject("text.download.encoding"); return retValue; }
/*     */   public Boolean checkPlugins() { return (Boolean)getPersistentObject(PLUGIN_CHECK); }
/*     */   public void setCheckPlugins(Boolean check) { setPersistentObject(PLUGIN_CHECK, check); }
/* 202 */   public Boolean useLTHours() { return (Boolean)getPersistentObject(USE_LTHOURS); } public void setUseLTHours(Boolean useLTHours) { setPersistentObject(USE_LTHOURS, useLTHours); } public Object getPersistentObject(Object key) { Serializable retValue = null; synchronized (this.context.getLockObject()) { Map persistentObjects = (Map)this.context.getObject("persistent.objects"); if (persistentObjects != null) retValue = (Serializable)persistentObjects.get(key);  }  return retValue; } public void setPersistentObject(Object key, Object object) { synchronized (this.context.getLockObject()) { Map<Object, Object> persistentObjects = (Map)this.context.getObject("persistent.objects"); if (persistentObjects == null) { persistentObjects = new HashMap<Object, Object>(); this.context.storeObject("persistent.objects", persistentObjects); }  persistentObjects.put(key, object); }  } public LocaleInfo getLocaleInfo() { return LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()); } public String getCurrentSalesmake() { try { return VehicleConfigurationUtil.getMake(VCServiceProvider.getInstance().getService(this.context).getCfg()).toString(); } catch (NullPointerException e) { return null; }  } public Set getUserGroups() { return new HashSet(getUsrGroup2ManufMap().keySet()); } public String getDealercode() { String ret = null; DealerCode testDC = (DealerCode)getObject(DealerCode.class); if (testDC != null) ret = testDC.getDealerCode();  return ret; } public void setLoginInfo(LoginInfo loginInfo) { this.context.storeObject(LoginInfo.class, loginInfo); } public LoginInfo getLoginInfo() { return (LoginInfo)this.context.getObject(LoginInfo.class); } public boolean isSpecialAccess() { return this.specialAccess; }
/*     */ 
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\sharedcontext\SharedContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */