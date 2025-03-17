/*    */ package com.eoos.gm.tis2web.frame.export;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ModuleAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.VisualModuleComparator;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class VisualModuleProvider {
/* 16 */   private static final Logger log = Logger.getLogger(VisualModuleProvider.class);
/*    */   
/*    */   private ClientContext context;
/*    */   
/* 20 */   private final Object SYNC = new Object();
/*    */   
/* 22 */   private List modules = null;
/*    */   
/*    */   private VisualModuleProvider(ClientContext context) {
/* 25 */     this.context = context;
/* 26 */     this.context.addLogoutListener(new ClientContextBase.LogoutListener() {
/*    */           public void onLogout() {
/* 28 */             VisualModuleProvider.this.context = null;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public static VisualModuleProvider getInstance(ClientContext context) {
/* 34 */     synchronized (context.getLockObject()) {
/* 35 */       VisualModuleProvider instance = (VisualModuleProvider)context.getObject(VisualModuleProvider.class);
/* 36 */       if (instance == null) {
/* 37 */         instance = new VisualModuleProvider(context);
/* 38 */         context.storeObject(VisualModuleProvider.class, instance);
/*    */       } 
/* 40 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public List getVisualModules() {
/* 45 */     synchronized (this.SYNC) {
/* 46 */       if (this.modules == null) {
/* 47 */         log.info("determining visual modules for " + this.context);
/* 48 */         this.modules = new LinkedList();
/* 49 */         for (Iterator<VisualModule> iter = ConfiguredServiceProvider.getInstance().getServices(VisualModule.class).iterator(); iter.hasNext(); ) {
/* 50 */           VisualModule module = iter.next();
/* 51 */           if (ModuleAccessPermission.getInstance(this.context).check((Module)module)) {
/* 52 */             log.info("...adding: " + module.getType());
/* 53 */             this.modules.add(module); continue;
/*    */           } 
/* 55 */           log.info("...ignoring (no permission): " + module.getType());
/*    */         } 
/*    */         
/* 58 */         Collections.sort(this.modules, (Comparator<?>)VisualModuleComparator.getInstance());
/* 59 */         log.info("determined visual modules: " + this.modules);
/*    */       } 
/* 61 */       return this.modules;
/*    */     } 
/*    */   }
/*    */   
/*    */   public VisualModule getVisualModule(String type) {
/* 66 */     VisualModule module = null;
/* 67 */     if (ModuleAccessPermission.getInstance(this.context).check(type)) {
/* 68 */       for (Iterator<VisualModule> iter = getVisualModules().iterator(); iter.hasNext() && module == null; ) {
/* 69 */         VisualModule current = iter.next();
/* 70 */         if (current.getType().equalsIgnoreCase(type)) {
/* 71 */           module = current;
/*    */         }
/*    */       } 
/*    */     }
/* 75 */     return module;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\VisualModuleProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */