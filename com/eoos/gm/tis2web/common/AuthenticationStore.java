/*     */ package com.eoos.gm.tis2web.common;
/*     */ 
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.Base64DecodingStream;
/*     */ import com.eoos.util.v2.Base64EncodingStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.CipherInputStream;
/*     */ import javax.crypto.CipherOutputStream;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AuthenticationStore
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(AuthenticationStore.class);
/*     */   
/*  31 */   public static AuthenticationStore instance = null;
/*     */   private static SecretKey key;
/*     */   
/*     */   static {
/*  35 */     String tmp = "ACED0005737200146A6176612E73656375726974792E4B6579526570BDF94FB3889AA5430200044C0009616C676F726974686D7400124C6A6176612F6C616E672F537472696E673B5B0007656E636F6465647400025B424C0006666F726D617471007E00014C00047479706574001B4C6A6176612F73656375726974792F4B657952657024547970653B7870740003444553757200025B42ACF317F8060854E00200007870000000082C5849A1F8EA4C687400035241577E7200196A6176612E73656375726974792E4B6579526570245479706500000000000000001200007872000E6A6176612E6C616E672E456E756D00000000000000001200007870740006534543524554";
/*     */     try {
/*  37 */       ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Util.parseBytes(tmp)));
/*     */       try {
/*  39 */         key = (SecretKey)ois.readObject();
/*     */       } finally {
/*  41 */         ois.close();
/*     */       } 
/*  43 */     } catch (Exception e) {
/*  44 */       Util.throwRuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*  48 */   private String propertyName = "e.astor";
/*     */   
/*     */   private Map map;
/*     */   
/*     */   public AuthenticationStore() {
/*  53 */     String tmp = System.getProperty(this.propertyName);
/*  54 */     if (!Util.isNullOrEmpty(tmp)) {
/*  55 */       log.debug("found stored authentication map");
/*     */       try {
/*  57 */         Cipher cipher = Cipher.getInstance("DES");
/*  58 */         cipher.init(2, key);
/*  59 */         ObjectInputStream ois = new ObjectInputStream(new CipherInputStream(new GZIPInputStream((InputStream)new Base64DecodingStream(new StringReader(tmp))), cipher));
/*     */         try {
/*  61 */           this.map = (Map)ois.readObject();
/*     */         } finally {
/*  63 */           StreamUtil.close(ois, log);
/*     */         } 
/*  65 */         if (log.isDebugEnabled()) {
/*  66 */           log.debug("...the map contains authentication for: " + this.map.keySet());
/*     */         }
/*  68 */       } catch (Exception e) {
/*  69 */         log.error("unable to read stored authentication mappings, ignoring - exception: " + e, e);
/*  70 */         this.map = new HashMap<Object, Object>();
/*     */       } 
/*     */     } else {
/*  73 */       log.debug("unable to find stored authentication map, creating new");
/*  74 */       this.map = new HashMap<Object, Object>();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static AuthenticationStore getInstance() {
/*  80 */     if (instance == null) {
/*  81 */       instance = new AuthenticationStore();
/*     */     }
/*  83 */     return instance;
/*     */   }
/*     */   
/*     */   public synchronized void put(InetSocketAddress address, String realm, String authScheme, Authentication authentication) {
/*  87 */     this.map.put(toKey(address, realm, authScheme), authentication);
/*  88 */     log.debug("added authentication for : " + address + ", realm: " + String.valueOf(realm) + ", scheme: " + authScheme);
/*     */     
/*  90 */     store();
/*  91 */     log.debug("...and stored state");
/*     */   }
/*     */   
/*     */   private void store() {
/*     */     try {
/*  96 */       Cipher cipher = Cipher.getInstance("DES");
/*  97 */       cipher.init(1, key);
/*     */       
/*  99 */       StringWriter sw = new StringWriter();
/* 100 */       ObjectOutputStream oos = new ObjectOutputStream(new CipherOutputStream(new GZIPOutputStream((OutputStream)new Base64EncodingStream(sw)), cipher));
/*     */       try {
/* 102 */         oos.writeObject(this.map);
/*     */       } finally {
/*     */         
/* 105 */         StreamUtil.close(oos, log);
/*     */       } 
/* 107 */       System.setProperty(this.propertyName, sw.toString());
/*     */     }
/* 109 */     catch (Exception e) {
/* 110 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List toKey(InetSocketAddress address, String realm, String scheme) {
/* 116 */     List<InetSocketAddress> key = new LinkedList();
/* 117 */     key.add(address);
/* 118 */     key.add(realm);
/* 119 */     key.add(scheme);
/* 120 */     return key;
/*     */   }
/*     */   
/*     */   public Authentication get(InetSocketAddress address, String realm, String scheme) {
/* 124 */     return (Authentication)this.map.get(toKey(address, realm, scheme));
/*     */   }
/*     */   
/*     */   public synchronized void remove(InetSocketAddress address, String realm, String scheme) {
/* 128 */     this.map.remove(toKey(address, realm, scheme));
/* 129 */     store();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\AuthenticationStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */