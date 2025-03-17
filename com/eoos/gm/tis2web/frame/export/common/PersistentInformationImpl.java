/*    */ package com.eoos.gm.tis2web.frame.export.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.StorageService;
/*    */ import com.eoos.scsm.v2.objectpool.HashMapPool;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ public class PersistentInformationImpl
/*    */   implements ClientContextProvider.PersistentInformation
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(PersistentInformationImpl.class);
/*    */ 
/*    */   
/*    */   public static final String KEY_DEFAULT_SM = "default.sm";
/*    */ 
/*    */   
/*    */   public static final String KEY_DISPLAY_HEIGHT = "display.height";
/*    */ 
/*    */   
/*    */   public static final String KEY_TEXT_ENCODING = "text.encoding";
/*    */   
/*    */   public static final String KEY_PERSISTENT_OBJECTS = "persistent.objects";
/*    */ 
/*    */   
/*    */   public void load(ClientContext context) {
/*    */     try {
/* 38 */       synchronized (context.getLockObject()) {
/* 39 */         StorageService storage = (StorageService)FrameServiceProvider.getInstance().getService(StorageService.class);
/* 40 */         StorageService.ObjectStore objectStore = storage.getObjectStoreFacade();
/* 41 */         Map map = (Map)objectStore.load(context.getSessionID() + ".sav");
/* 42 */         if (map != null) {
/* 43 */           String sm = (String)map.get("default.sm");
/* 44 */           Integer dh = (Integer)map.get("display.height");
/* 45 */           Integer te = (Integer)map.get("text.encoding");
/* 46 */           Map persistentObjects = (Map)map.get("persistent.objects");
/*    */           
/* 48 */           context.getSharedContext().setDefaultSalesmake(sm);
/* 49 */           context.getSharedContext().setDisplayHeight(dh);
/* 50 */           context.getSharedContext().setTextEncoding(te);
/* 51 */           context.storeObject("persistent.objects", persistentObjects);
/*    */           
/* 53 */           log.info("loaded settings for user:" + context.getSessionID());
/*    */         } else {
/* 55 */           log.warn("no settings data for user: " + context.getSessionID());
/*    */         } 
/*    */       } 
/* 58 */     } catch (Exception e) {
/* 59 */       log.error("unable to load settings for user:" + context.getSessionID() + " - exception: " + e, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void save(ClientContext context) {
/*    */     try {
/* 65 */       synchronized (context.getLockObject()) {
/* 66 */         Map<String, String> map = HashMapPool.getThreadInstance().get();
/*    */         
/*    */         try {
/* 69 */           String sm = context.getSharedContext().getDefaultSalesmake();
/* 70 */           Integer dh = context.getSharedContext().getDisplayHeight();
/* 71 */           Integer te = context.getSharedContext().getTextEncoding();
/* 72 */           Map po = (Map)context.getObject("persistent.objects");
/*    */           
/* 74 */           map.put("default.sm", sm);
/* 75 */           map.put("display.height", dh);
/* 76 */           map.put("text.encoding", te);
/* 77 */           map.put("persistent.objects", po);
/*    */           
/* 79 */           StorageService storage = (StorageService)FrameServiceProvider.getInstance().getService(StorageService.class);
/* 80 */           StorageService.ObjectStore objectStore = storage.getObjectStoreFacade();
/* 81 */           objectStore.store(context.getSessionID() + ".sav", map);
/*    */         } finally {
/* 83 */           HashMapPool.getThreadInstance().free(map);
/*    */         } 
/*    */         
/* 86 */         log.info("stored settings for user:" + context.getSessionID());
/*    */       } 
/* 88 */     } catch (Exception e) {
/* 89 */       log.error("unable to store settings for user:" + context.getSessionID() + " - error:" + e, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\PersistentInformationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */