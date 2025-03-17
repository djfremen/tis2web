/*     */ package com.eoos.gm.tis2web.frame.dls.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dls.ILeaseInternal;
/*     */ import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
/*     */ import com.eoos.scsm.v2.io.PreferencesInputStream;
/*     */ import com.eoos.scsm.v2.io.PreferencesOutputStream;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.prefs.BackingStoreException;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.CipherInputStream;
/*     */ import javax.crypto.CipherOutputStream;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ @SuppressWarnings({"DMI_COLLECTION_OF_URLS"})
/*     */ public class LeaseRegistry_Preferences implements LeaseRegistry {
/*  30 */   private static final Logger log = Logger.getLogger(LeaseRegistry_Preferences.class);
/*     */   
/*  32 */   private static Preferences REGISTRY = Preferences.systemRoot().node("com/eoos/security/lreg");
/*     */ 
/*     */   
/*  35 */   private final Object SYNC = new Object();
/*     */   
/*  37 */   private Map leaseMap = null;
/*     */   
/*     */   private static final String _key = "ACED00057372001E636F6D2E73756E2E63727970746F2E70726F76696465722E4445534B65796B349C35DA1568980200015B00036B65797400025B427870757200025B42ACF317F8060854E0020000787000000008DCB6E6A7462370BF";
/*     */   
/*     */   private SecretKey key;
/*     */   
/*     */   public LeaseRegistry_Preferences() {
/*     */     try {
/*  45 */       ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Util.parseBytes("ACED00057372001E636F6D2E73756E2E63727970746F2E70726F76696465722E4445534B65796B349C35DA1568980200015B00036B65797400025B427870757200025B42ACF317F8060854E0020000787000000008DCB6E6A7462370BF")));
/*     */       
/*     */       try {
/*  48 */         this.key = (SecretKey)ois.readObject();
/*     */       } finally {
/*  50 */         ois.close();
/*     */       } 
/*  52 */     } catch (Exception e) {
/*  53 */       Util.throwRuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Lease getNewer(Lease lease1, Lease lease2) {
/*  58 */     if (lease1 == null || lease2 == null) {
/*  59 */       return (lease1 != null) ? lease1 : lease2;
/*     */     }
/*  61 */     long date1 = ((ILeaseInternal)lease1).getCreationDate();
/*  62 */     long date2 = ((ILeaseInternal)lease2).getCreationDate();
/*     */     
/*  64 */     return (date1 >= date2) ? lease1 : lease2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Map readLeaseMap() {
/*  70 */     log.debug("reading lease map...");
/*  71 */     Map<Object, Object> ret = new HashMap<Object, Object>();
/*     */     try {
/*  73 */       String[] leaseIDs = REGISTRY.childrenNames();
/*  74 */       for (int i = 0; i < leaseIDs.length; i++) {
/*  75 */         Preferences node = REGISTRY.node(leaseIDs[i]);
/*  76 */         Cipher cipher = Cipher.getInstance("DES");
/*  77 */         cipher.init(2, this.key);
/*     */         
/*     */         try {
/*  80 */           ObjectInputStream ois = new ObjectInputStream(new CipherInputStream((InputStream)new PreferencesInputStream(node), cipher));
/*     */           
/*     */           try {
/*  83 */             URL serverURL = (URL)ois.readObject();
/*  84 */             Lease currentLease = (Lease)ret.get(serverURL);
/*  85 */             Lease lease = (Lease)ois.readObject();
/*  86 */             if (currentLease != null) {
/*  87 */               log.warn("more than one lease for url: " + serverURL + ", ignoring older");
/*  88 */               lease = getNewer(currentLease, lease);
/*     */             } 
/*  90 */             ret.put(serverURL, lease);
/*     */           } finally {
/*     */             
/*  93 */             StreamUtil.close(ois, log);
/*     */           } 
/*  95 */         } catch (Exception e) {
/*  96 */           log.error("unable to read lease from node: " + leaseIDs[i] + ", skipping - exception: " + e, e);
/*     */         } 
/*     */       } 
/*     */       
/* 100 */       log.debug("...resulting map: " + ret);
/* 101 */       return ret;
/* 102 */     } catch (Exception e) {
/* 103 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map getLeaseMap() {
/* 108 */     synchronized (this.SYNC) {
/* 109 */       if (this.leaseMap == null) {
/* 110 */         this.leaseMap = readLeaseMap();
/*     */       }
/* 112 */       return this.leaseMap;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String toPathname(URL serverURL) {
/* 117 */     if (serverURL == null) {
/* 118 */       return "n";
/*     */     }
/* 120 */     return String.valueOf(serverURL.hashCode());
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerLease(Lease lease, URL serverURL) {
/* 125 */     synchronized (this.SYNC) {
/* 126 */       Preferences baseNode = REGISTRY.node(toPathname(serverURL));
/*     */       
/*     */       try {
/* 129 */         Cipher cipher = Cipher.getInstance("DES");
/* 130 */         cipher.init(1, this.key);
/*     */         
/* 132 */         ObjectOutputStream oos = new ObjectOutputStream(new CipherOutputStream((OutputStream)new PreferencesOutputStream(baseNode), cipher));
/*     */         try {
/* 134 */           oos.writeObject(serverURL);
/* 135 */           oos.writeObject(lease);
/*     */         } finally {
/*     */           
/* 138 */           StreamUtil.close(oos, log);
/*     */         } 
/* 140 */         this.leaseMap = null;
/*     */       }
/* 142 */       catch (Exception e) {
/* 143 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void createKey() throws Throwable {
/* 150 */     KeyGenerator generator = KeyGenerator.getInstance("DES");
/* 151 */     SecretKey key = generator.generateKey();
/*     */     
/* 153 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 154 */     ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 155 */     oos.writeObject(key);
/* 156 */     oos.close();
/* 157 */     System.out.println(Util.toHexString(baos.toByteArray()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Throwable {
/* 164 */     createKey();
/*     */   }
/*     */   
/*     */   public void clear() {
/* 168 */     synchronized (this.SYNC) {
/*     */       try {
/* 170 */         String[] leaseIDs = REGISTRY.childrenNames();
/* 171 */         for (int i = 0; i < leaseIDs.length; i++) {
/* 172 */           Preferences node = REGISTRY.node(leaseIDs[i]);
/* 173 */           node.removeNode();
/*     */         } 
/* 175 */         REGISTRY.flush();
/*     */       }
/* 177 */       catch (BackingStoreException e) {
/* 178 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\LeaseRegistry_Preferences.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */