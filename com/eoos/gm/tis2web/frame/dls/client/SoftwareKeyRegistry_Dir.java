/*     */ package com.eoos.gm.tis2web.frame.dls.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.Windows6PlusSupport;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.CipherInputStream;
/*     */ import javax.crypto.CipherOutputStream;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SoftwareKeyRegistry_Dir
/*     */   implements SoftwareKeyRegistry
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(SoftwareKeyRegistry_Dir.class);
/*     */   
/*     */   private static final String _key = "ACED00057372001E636F6D2E73756E2E63727970746F2E70726F76696465722E4445534B65796B349C35DA1568980200015B00036B65797400025B427870757200025B42ACF317F8060854E0020000787000000008DCB6E6A7462370BF";
/*     */   
/*     */   private SecretKey key;
/*     */   
/*     */   private static final String FILENAME = ".swk";
/*  30 */   private final Object SYNC = new Object();
/*     */   
/*     */   private File dir;
/*     */   
/*  34 */   private SoftwareKey swk = null;
/*     */   
/*     */   public SoftwareKeyRegistry_Dir(File dir) {
/*  37 */     if (dir == null) {
/*  38 */       log.debug("SoftwareKeyRegistry directory not set!");
/*  39 */       throw new IllegalArgumentException("directory not set");
/*     */     } 
/*     */ 
/*     */     
/*  43 */     this.dir = dir;
/*  44 */     if (!this.dir.exists()) {
/*  45 */       if (!this.dir.mkdirs()) {
/*  46 */         throw new IllegalStateException("unable to create dir: " + String.valueOf(dir));
/*     */       }
/*  48 */       Windows6PlusSupport win6Support = new Windows6PlusSupport();
/*  49 */       if (win6Support.isWindows6Plus() && 
/*  50 */         !win6Support.setFullAccess(dir)) {
/*  51 */         throw new IllegalStateException("cannot adjust access rights: " + String.valueOf(dir));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  58 */       ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Util.parseBytes("ACED00057372001E636F6D2E73756E2E63727970746F2E70726F76696465722E4445534B65796B349C35DA1568980200015B00036B65797400025B427870757200025B42ACF317F8060854E0020000787000000008DCB6E6A7462370BF")));
/*     */       
/*     */       try {
/*  61 */         this.key = (SecretKey)ois.readObject();
/*     */       } finally {
/*  63 */         ois.close();
/*     */       } 
/*  65 */     } catch (Exception e) {
/*  66 */       Util.throwRuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static SoftwareKeyRegistry_Dir createGlobalInstance() {
/*  71 */     File file = new File(System.getenv("ALLUSERSPROFILE"));
/*  72 */     return new SoftwareKeyRegistry_Dir(new File(file, ".dls"));
/*     */   }
/*     */   
/*     */   private File getFile() {
/*  76 */     return new File(this.dir, ".swk");
/*     */   }
/*     */   
/*     */   private SoftwareKey readSWK() {
/*  80 */     log.debug("reading swk ...");
/*  81 */     File file = getFile();
/*  82 */     if (!file.exists()) {
/*  83 */       log.debug("...no registered swk");
/*  84 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/*  88 */       Cipher cipher = Cipher.getInstance("DES");
/*  89 */       cipher.init(2, this.key);
/*     */       
/*  91 */       ObjectInputStream ois = new ObjectInputStream(new CipherInputStream(new FileInputStream(file), cipher));
/*     */       try {
/*  93 */         return (SoftwareKey)ois.readObject();
/*     */       } finally {
/*  95 */         StreamUtil.close(ois, log);
/*     */       } 
/*  97 */     } catch (Exception e) {
/*  98 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SoftwareKey getSoftwareKey() {
/* 103 */     synchronized (this.SYNC) {
/* 104 */       if (this.swk == null) {
/* 105 */         this.swk = readSWK();
/*     */       }
/* 107 */       return this.swk;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerSoftwareKey(SoftwareKey swk) {
/* 112 */     synchronized (this.SYNC) {
/*     */       
/*     */       try {
/* 115 */         Cipher cipher = Cipher.getInstance("DES");
/* 116 */         cipher.init(1, this.key);
/*     */         
/* 118 */         ObjectOutputStream oos = new ObjectOutputStream(new CipherOutputStream(new FileOutputStream(getFile()), cipher));
/*     */         try {
/* 120 */           oos.writeObject(swk);
/*     */         } finally {
/*     */           
/* 123 */           StreamUtil.close(oos, log);
/*     */         } 
/* 125 */         this.swk = null;
/* 126 */       } catch (Exception e) {
/* 127 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deleteSoftwareKey() {
/* 133 */     synchronized (this.SYNC) {
/* 134 */       this.swk = null;
/* 135 */       File file = getFile();
/* 136 */       if (file.exists() && 
/* 137 */         !file.delete()) {
/* 138 */         log.warn("unable to delete swk file, writing null content");
/* 139 */         registerSoftwareKey(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\SoftwareKeyRegistry_Dir.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */