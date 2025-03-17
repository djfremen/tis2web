/*     */ package com.eoos.gm.tis2web.frame.dls.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.ILeaseInternal;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.scsm.v2.collection.util.ArrayIterator;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.CipherInputStream;
/*     */ import javax.crypto.CipherOutputStream;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ @SuppressWarnings({"DMI_COLLECTION_OF_URLS"})
/*     */ public class LeaseRegistry_Dir
/*     */   implements LeaseRegistry
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(LeaseRegistry_Dir.class);
/*     */   
/*     */   private File dir;
/*     */   
/*     */   private static final String _key = "ACED00057372001E636F6D2E73756E2E63727970746F2E70726F76696465722E4445534B65796B349C35DA1568980200015B00036B65797400025B427870757200025B42ACF317F8060854E0020000787000000008DCB6E6A7462370BF";
/*     */   
/*     */   private SecretKey key;
/*     */   
/*     */   public LeaseRegistry_Dir(File dir) {
/*  39 */     if (dir == null) {
/*  40 */       dir = new File(System.getProperty("user.home"), ".t2wdls");
/*     */     }
/*  42 */     dir = new File(dir, ".lreg");
/*  43 */     this.dir = dir;
/*  44 */     if (!this.dir.exists() && 
/*  45 */       !this.dir.mkdirs()) {
/*  46 */       throw new IllegalStateException("unable to create dir: " + String.valueOf(dir));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  51 */       ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Util.parseBytes("ACED00057372001E636F6D2E73756E2E63727970746F2E70726F76696465722E4445534B65796B349C35DA1568980200015B00036B65797400025B427870757200025B42ACF317F8060854E0020000787000000008DCB6E6A7462370BF")));
/*     */       
/*     */       try {
/*  54 */         this.key = (SecretKey)ois.readObject();
/*     */       } finally {
/*  56 */         ois.close();
/*     */       } 
/*  58 */     } catch (Exception e) {
/*  59 */       Util.throwRuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized Map getLeaseMap() {
/*  64 */     Map<Object, Object> ret = new HashMap<Object, Object>();
/*  65 */     File[] leases = this.dir.listFiles();
/*     */     try {
/*  67 */       for (ArrayIterator<File> arrayIterator = new ArrayIterator((Object[])leases); arrayIterator.hasNext(); ) {
/*  68 */         File file = arrayIterator.next();
/*  69 */         Cipher cipher = Cipher.getInstance("DES");
/*  70 */         cipher.init(2, this.key);
/*     */         
/*     */         try {
/*  73 */           ObjectInputStream ois = new ObjectInputStream(new CipherInputStream(new FileInputStream(file), cipher));
/*     */           try {
/*  75 */             URL serverURL = (URL)ois.readObject();
/*  76 */             Lease lease = (Lease)ois.readObject();
/*  77 */             Lease former = (Lease)ret.put(serverURL, lease);
/*  78 */             if (former != null && ((ILeaseInternal)former).getCreationDate() > ((ILeaseInternal)lease).getCreationDate()) {
/*  79 */               ret.put(serverURL, former);
/*     */             }
/*     */           } finally {
/*  82 */             StreamUtil.close(ois, log);
/*     */           }
/*     */         
/*  85 */         } catch (Exception e) {
/*  86 */           log.warn("unable to read file : " + String.valueOf(file) + ", skipping - exception: " + e, e);
/*     */         } 
/*     */       } 
/*  89 */     } catch (Exception e) {
/*  90 */       throw new RuntimeException(e);
/*     */     } 
/*  92 */     return ret;
/*     */   }
/*     */   
/*  95 */   private static final Pattern P1 = Pattern.compile("\\.");
/*     */   
/*     */   private String toFilename(URL serverURL) {
/*  98 */     if (serverURL == null) {
/*  99 */       return "n";
/*     */     }
/* 101 */     StringBuffer tmp = new StringBuffer(serverURL.getHost());
/* 102 */     Util.delete(tmp, P1);
/* 103 */     return tmp.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void registerLease(Lease lease, URL serverURL) {
/*     */     try {
/* 109 */       Cipher cipher = Cipher.getInstance("DES");
/* 110 */       cipher.init(1, this.key);
/*     */       
/* 112 */       ObjectOutputStream oos = new ObjectOutputStream(new CipherOutputStream(new FileOutputStream(new File(this.dir, toFilename(serverURL))), cipher));
/*     */       try {
/* 114 */         oos.writeObject(serverURL);
/* 115 */         oos.writeObject(lease);
/*     */       } finally {
/*     */         
/* 118 */         StreamUtil.close(oos, log);
/*     */       } 
/* 120 */     } catch (Exception e) {
/* 121 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\LeaseRegistry_Dir.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */