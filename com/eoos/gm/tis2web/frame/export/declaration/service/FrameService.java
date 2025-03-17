/*    */ package com.eoos.gm.tis2web.frame.export.declaration.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Service;
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface FrameService
/*    */   extends Service, Module
/*    */ {
/* 15 */   public static final Integer ENC_WINDOWS = Integer.valueOf(1);
/*    */   
/* 17 */   public static final Integer ENC_UTF8 = Integer.valueOf(2);
/*    */   public static final String FRAME_DEFAULT_SM_ID = "Frame.DefaultSalesmake.ID";
/*    */   public static final String FRAME_COUNTRY_ID = "Frame.Country.ID";
/*    */   public static final String FRAME_REALM_ID = "Frame.Realm.ID";
/*    */   public static final String FRAME_LOCALE_ID = "Frame.Locale.ID";
/*    */   public static final String FRAME_USR_GRP_2_MANUF_ID = "Frame.UsrGroup2Manuf.ID";
/*    */   public static final String TEXT_ENCODING = "text.download.encoding";
/*    */   public static final String PERSISTENT_OBJECTS = "persistent.objects";
/*    */   
/*    */   Boolean setUsrGroup2ManufMap(Map paramMap, String paramString);
/*    */   
/*    */   Map getUsrGroup2ManufMap(String paramString);
/*    */   
/*    */   Boolean setObject(Serializable paramSerializable, String paramString);
/*    */   
/*    */   Object getObject(Object paramObject, String paramString);
/*    */   
/*    */   Boolean setLocale(Locale paramLocale, String paramString);
/*    */   
/*    */   Locale getLocale(String paramString);
/*    */   
/*    */   String getCountry(String paramString);
/*    */   
/*    */   Boolean setCountry(String paramString1, String paramString2);
/*    */   
/*    */   Boolean setDisplayHeight(Integer paramInteger, String paramString);
/*    */   
/*    */   Integer getDisplayHeight(String paramString);
/*    */   
/*    */   Boolean setTextEncoding(Integer paramInteger, String paramString);
/*    */   
/*    */   Integer getTextEncoding(String paramString);
/*    */   
/*    */   Boolean setPersistentObject(String paramString, Serializable paramSerializable1, Serializable paramSerializable2);
/*    */   
/*    */   Serializable getPersistentObject(String paramString, Serializable paramSerializable);
/*    */   
/*    */   void storePersistentObject(String paramString, Serializable paramSerializable) throws Exception;
/*    */   
/*    */   Object getPersistentObject(String paramString) throws Exception;
/*    */   
/*    */   boolean invalidateSession(String paramString);
/*    */   
/*    */   boolean isActive(String paramString);
/*    */   
/*    */   boolean registerUID(String paramString, long paramLong) throws IOException;
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\declaration\service\FrameService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */