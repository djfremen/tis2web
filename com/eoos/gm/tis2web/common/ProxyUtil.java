/*     */ package com.eoos.gm.tis2web.common;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.ProxySelector;
/*     */ import java.net.URI;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ProxyUtil
/*     */ {
/*  22 */   private static final Logger log = Logger.getLogger(ProxyUtil.class);
/*     */   private static final File PMFile;
/*     */   
/*     */   static {
/*  26 */     File dir = new File(System.getenv("ALLUSERSPROFILE"));
/*  27 */     dir = new File(dir, ".t2web");
/*  28 */     boolean created = dir.mkdirs();
/*  29 */     if (created) {
/*  30 */       Windows6PlusSupport win6Support = new Windows6PlusSupport();
/*  31 */       if (win6Support.isWindows6Plus() && !win6Support.setFullAccess(dir)) {
/*  32 */         log.error("cannot adjust access rights: " + String.valueOf(dir));
/*     */       }
/*     */     } 
/*     */     
/*  36 */     PMFile = new File(dir, ".proxies");
/*     */   }
/*     */   
/*     */   static final String PREF_LOOKUP_PATH = "com/eoos/http/proxy";
/*     */   
/*     */   public static Properties createProxyMap(Collection uris) {
/*  42 */     Properties ret = new Properties();
/*  43 */     for (Iterator<URI> iterURI = uris.iterator(); iterURI.hasNext(); ) {
/*  44 */       URI uri = iterURI.next();
/*  45 */       List<Proxy> proxies = ProxySelector.getDefault().select(uri);
/*  46 */       StringBuffer tmp = new StringBuffer();
/*  47 */       for (Iterator<Proxy> iterProxy = proxies.iterator(); iterProxy.hasNext(); ) {
/*  48 */         Proxy proxy = iterProxy.next();
/*  49 */         tmp.append(proxy.type().toString());
/*  50 */         if (proxy.type() != Proxy.Type.DIRECT) {
/*  51 */           tmp.append(":");
/*  52 */           InetSocketAddress address = (InetSocketAddress)proxy.address();
/*  53 */           tmp.append(address.getHostName());
/*  54 */           tmp.append(":");
/*  55 */           tmp.append(address.getPort());
/*     */         } 
/*  57 */         if (iterProxy.hasNext()) {
/*  58 */           tmp.append(", ");
/*     */         }
/*     */       } 
/*  61 */       ret.setProperty(uri.toString(), tmp.toString());
/*     */     } 
/*  63 */     return ret;
/*     */   }
/*     */   public static final String KEY = "FILE";
/*     */   public static void writeProxyMapFile(Properties mappings) {
/*     */     try {
/*  68 */       FileOutputStream fos = new FileOutputStream(PMFile);
/*     */       
/*     */       try {
/*  71 */         mappings.store(fos, (String)null);
/*     */       } finally {
/*  73 */         fos.close();
/*     */       } 
/*  75 */     } catch (Exception e) {
/*  76 */       throw Util.toRuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map readProxyMapFile() {
/*  82 */     log.debug("reading global proxy mapping file...");
/*  83 */     Map<Object, Object> proxyMap = new HashMap<Object, Object>();
/*  84 */     if (PMFile.exists()) {
/*     */       try {
/*  86 */         FileInputStream fis = new FileInputStream(PMFile);
/*     */         
/*     */         try {
/*  89 */           Properties properties = new Properties();
/*  90 */           properties.load(fis);
/*  91 */           for (Iterator<Map.Entry<Object, Object>> iter = properties.entrySet().iterator(); iter.hasNext();) {
/*     */             try {
/*  93 */               Map.Entry entry = iter.next();
/*  94 */               String _uri = (String)entry.getKey();
/*  95 */               String _proxy = (String)entry.getValue();
/*  96 */               URI uri = URI.create(_uri);
/*  97 */               List proxies = toProxyList(_proxy, uri);
/*  98 */               proxyMap.put(uri, proxies);
/*  99 */             } catch (Exception e) {
/* 100 */               log.warn("unable to process entry, ignoring - exception: " + e, e);
/*     */             } 
/*     */           } 
/*     */           
/* 104 */           log.debug("...successfully read  file");
/*     */         } finally {
/* 106 */           fis.close();
/*     */         } 
/* 108 */       } catch (Exception e) {
/* 109 */         throw Util.toRuntimeException(e);
/*     */       } 
/*     */     } else {
/* 112 */       log.debug("...file does not exist, returning empty mapping");
/*     */     } 
/* 114 */     return proxyMap;
/*     */   }
/*     */   
/*     */   private static List toProxyList(String string, URI uri) {
/* 118 */     List ret = null;
/* 119 */     if (!Util.isNullOrEmpty(string)) {
/*     */       try {
/* 121 */         ret = Util.parseList(string, "\\s*,\\s*", new Util.ObjectCreation() {
/*     */               public Object createObject(String string) {
/*     */                 byte b;
/* 124 */                 String[] parts = string.split("\\:");
/* 125 */                 Proxy.Type type = Proxy.Type.valueOf(parts[0]);
/* 126 */                 if (type == Proxy.Type.DIRECT) {
/* 127 */                   return Proxy.NO_PROXY;
/*     */                 }
/* 129 */                 String host = parts[1];
/*     */                 
/*     */                 try {
/* 132 */                   b = Integer.parseInt(parts[2]);
/* 133 */                 } catch (Exception e) {
/* 134 */                   ProxyUtil.log.warn("unable to parse port <" + parts[2] + ">, asssuming port 80");
/* 135 */                   b = 80;
/*     */                 } 
/* 137 */                 return new Proxy(type, new InetSocketAddress(host, b));
/*     */               }
/*     */             });
/*     */       }
/* 141 */       catch (Exception e) {
/* 142 */         log.warn("unable to parse string <" + string + ">, returning null - exception: " + e, e);
/*     */       } 
/*     */     }
/* 145 */     return ret;
/*     */   }
/*     */   
/*     */   public static void setDefaultProxySelector() {
/* 149 */     log.debug("exchanging proxy selector...");
/* 150 */     ProxySelector.setDefault(new CustomProxySelector(ProxySelector.getDefault()));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\ProxyUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */