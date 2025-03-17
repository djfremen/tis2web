/*     */ package com.eoos.gm.tis2web.frame.msg;
/*     */ 
/*     */ import com.eoos.collection.v2.CollectionUtil;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.StorageService;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.MessageManager;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MsgFacade
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(MsgFacade.class);
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
/*     */   private final String KEY_EXCLUSION_SET;
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
/*     */   private Set exclusionSet;
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
/*     */   private MsgFacade(ClientContext context) {
/*  88 */     this.exclusionSet = null; this.context = context; try { this.KEY_EXCLUSION_SET = Base64EncodingUtil.encode((context.getSessionID() + "msg.ext.set").getBytes("utf-8")); } catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }  cleanupExclusionSet();
/*     */   }
/*     */   private void cleanupExclusionSet() { Set set = getExclusionSet(); if (!Util.isNullOrEmpty(set)) { for (Iterator<String> iter = set.iterator(); iter.hasNext(); ) { String id = iter.next(); try { if (!MessageManager.getInstance().existsMessage(id))
/*  91 */             iter.remove();  } catch (Exception e) { log.warn("unable to determine status for id: " + String.valueOf(id) + ", skipping"); }  }  storeExclusionSet(set); }  } public void resetExclusion() { storeExclusionSet(new HashSet()); } private synchronized Set getExclusionSet() { if (this.exclusionSet == null) {
/*  92 */       StorageService store = (StorageService)FrameServiceProvider.getInstance().getService(StorageService.class);
/*     */       
/*  94 */       StorageService.ObjectStore objStore = store.getObjectStoreFacade();
/*     */       try {
/*  96 */         this.exclusionSet = (Set)objStore.load(this.KEY_EXCLUSION_SET);
/*  97 */       } catch (Exception e) {
/*  98 */         throw new RuntimeException(e);
/*     */       } 
/*     */       
/* 101 */       if (this.exclusionSet == null) {
/* 102 */         this.exclusionSet = new HashSet();
/*     */       }
/*     */     } 
/* 105 */     return this.exclusionSet; }
/*     */   public static MsgFacade getInstance(ClientContext context) { synchronized (context.getLockObject()) { MsgFacade instance = (MsgFacade)context.getObject(MsgFacade.class); if (instance == null) { instance = new MsgFacade(context); context.storeObject(MsgFacade.class, instance); }  return instance; }
/*     */      } public List getMessages(String moduleType) { final Set excluded = getExclusionSet(); Filter filter = new Filter() {
/*     */         public boolean include(Object obj) { return !excluded.contains(((IMessage)obj).getID()); }
/* 109 */       }; List ret = MessageManager.getInstance().getMessages(moduleType, this.context); CollectionUtil.filter(ret, filter); return ret; } private void storeExclusionSet(Set set) { this.exclusionSet = set;
/* 110 */     StorageService store = (StorageService)FrameServiceProvider.getInstance().getService(StorageService.class);
/* 111 */     StorageService.ObjectStore objStore = store.getObjectStoreFacade();
/*     */     try {
/* 113 */       objStore.store(this.KEY_EXCLUSION_SET, set);
/* 114 */     } catch (Exception e) {
/* 115 */       throw new RuntimeException(e);
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void excludeMessage(IMessage msg) {
/* 121 */     Set<String> tmp = getExclusionSet();
/* 122 */     tmp.add(msg.getID());
/* 123 */     storeExclusionSet(tmp);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\MsgFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */