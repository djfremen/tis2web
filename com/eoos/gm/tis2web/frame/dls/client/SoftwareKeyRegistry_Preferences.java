/*    */ package com.eoos.gm.tis2web.frame.dls.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*    */ import com.eoos.scsm.v2.io.PreferencesInputStream;
/*    */ import com.eoos.scsm.v2.io.PreferencesOutputStream;
/*    */ import com.eoos.scsm.v2.io.StreamUtil;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.prefs.BackingStoreException;
/*    */ import java.util.prefs.Preferences;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SoftwareKeyRegistry_Preferences implements SoftwareKeyRegistry {
/* 17 */   private static final Logger log = Logger.getLogger(SoftwareKeyRegistry_Preferences.class);
/*    */   
/* 19 */   private static Preferences REGISTRY = Preferences.systemRoot().node("com/eoos/security/swkreg");
/*    */   
/* 21 */   private final Object SYNC = new Object();
/*    */   
/* 23 */   private SoftwareKey swk = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private SoftwareKey readSWK() {
/* 30 */     log.debug("reading swk ...");
/*    */     try {
/* 32 */       if (Util.isNullOrEmpty(REGISTRY.keys())) {
/* 33 */         log.debug("...no registered swk");
/* 34 */         return null;
/*    */       } 
/* 36 */     } catch (BackingStoreException e) {
/* 37 */       throw new RuntimeException(e);
/*    */     } 
/*    */     
/*    */     try {
/* 41 */       ObjectInputStream ois = new ObjectInputStream((InputStream)new PreferencesInputStream(REGISTRY));
/*    */       try {
/* 43 */         return (SoftwareKey)ois.readObject();
/*    */       } finally {
/* 45 */         StreamUtil.close(ois, log);
/*    */       } 
/* 47 */     } catch (Exception e) {
/* 48 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public SoftwareKey getSoftwareKey() {
/* 53 */     synchronized (this.SYNC) {
/* 54 */       if (this.swk == null) {
/* 55 */         this.swk = readSWK();
/*    */       }
/* 57 */       return this.swk;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void registerSoftwareKey(SoftwareKey swk) {
/* 62 */     synchronized (this.SYNC) {
/*    */       try {
/* 64 */         ObjectOutputStream oos = new ObjectOutputStream((OutputStream)new PreferencesOutputStream(REGISTRY));
/*    */         try {
/* 66 */           oos.writeObject(swk);
/*    */         } finally {
/*    */           
/* 69 */           StreamUtil.close(oos, log);
/*    */         } 
/* 71 */         this.swk = null;
/* 72 */       } catch (Exception e) {
/* 73 */         throw new RuntimeException(e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void deleteSoftwareKey() {
/* 79 */     synchronized (this.SYNC) {
/* 80 */       this.swk = null;
/*    */       try {
/* 82 */         REGISTRY.clear();
/* 83 */         REGISTRY.flush();
/* 84 */       } catch (BackingStoreException e) {
/* 85 */         throw new RuntimeException(e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\SoftwareKeyRegistry_Preferences.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */