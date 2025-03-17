/*     */ package com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system;
/*     */ 
/*     */ import com.eoos.context.Context;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.Data;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Stack;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class StatContContext
/*     */   extends Context
/*     */ {
/*  16 */   private static final Logger log = Logger.getLogger(StatContContext.class);
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  20 */   private Stack history = new Stack();
/*     */   
/*  22 */   private Data back = null;
/*     */   
/*  24 */   private Data current = null;
/*     */   
/*     */   private StatContContext(ClientContext context) {
/*  27 */     this.context = context;
/*  28 */     this.context.addLogoutListener(new ClientContextBase.LogoutListener()
/*     */         {
/*     */           public void onLogout() {
/*  31 */             StatContContext.this.context = null;
/*  32 */             StatContContext.this.clear();
/*  33 */             StatContContext.this.history.clear();
/*  34 */             StatContContext.this.history = null;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  39 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/*  42 */             StatContContext.this.reset();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static StatContContext getInstance(ClientContext context) {
/*  49 */     synchronized (context.getLockObject()) {
/*  50 */       StatContContext instance = (StatContContext)context.getObject(StatContContext.class);
/*  51 */       if (instance == null) {
/*  52 */         instance = new StatContContext(context);
/*  53 */         context.storeObject(StatContContext.class, instance);
/*     */       } 
/*  55 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized Data popBackDocument() {
/*  60 */     Data ret = this.back;
/*  61 */     this.back = null;
/*  62 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void back() {
/*  67 */     if (this.history.size() > 1) {
/*  68 */       this.back = this.history.pop();
/*  69 */       if (this.back.equals(this.current)) {
/*  70 */         this.back = this.history.pop();
/*     */       }
/*     */     } else {
/*  73 */       log.debug("unable to go back, history contains only current document");
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void reset() {
/*  78 */     this.history.clear();
/*  79 */     this.current = null;
/*  80 */     this.back = null;
/*     */   }
/*     */   
/*     */   public synchronized void addToHistory(Data data) {
/*  84 */     if (this.history.isEmpty()) {
/*  85 */       log.debug("setting default document: " + String.valueOf(data));
/*  86 */       this.history.push(data);
/*  87 */     } else if (!this.history.peek().equals(data)) {
/*  88 */       this.history.push(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized boolean isDefaultDocument(Data data) throws NoSuchElementException {
/*  93 */     if (this.history.isEmpty()) {
/*  94 */       throw new NoSuchElementException();
/*     */     }
/*  96 */     return this.history.get(0).equals(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setCurrent(Data data) {
/* 101 */     this.current = data;
/*     */   }
/*     */   
/*     */   public synchronized Data getCurrent() {
/* 105 */     return this.current;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcont\datamodel\system\StatContContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */