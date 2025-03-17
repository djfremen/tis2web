/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.IOException;
/*    */ import java.net.Proxy;
/*    */ import java.net.ProxySelector;
/*    */ import java.net.SocketAddress;
/*    */ import java.net.URI;
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CustomProxySelector
/*    */   extends ProxySelector
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(CustomProxySelector.class);
/*    */   
/*    */   public static final String KEY = "FILE";
/*    */   
/*    */   private ProxySelector delegate;
/*    */   
/*    */   private static final int MODE_OVERWRITE = 0;
/*    */   private static final int MODE_DELEGATE = 1;
/* 26 */   private int mode = 1;
/*    */   
/* 28 */   private Map proxyMap = null;
/*    */   
/*    */   public CustomProxySelector(ProxySelector delegate) {
/* 31 */     this.delegate = delegate;
/* 32 */     this.mode = Boolean.getBoolean("eoos.ignore.persistent.proxy.settings") ? 1 : 0;
/*    */   }
/*    */   
/*    */   private synchronized Map getProxyMap() {
/* 36 */     if (this.proxyMap == null) {
/* 37 */       this.proxyMap = ProxyUtil.readProxyMapFile();
/*    */     }
/* 39 */     return this.proxyMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
/* 44 */     log.warn("connecting failed for " + String.valueOf(uri) + " via " + sa + " - exception: " + ioe, ioe);
/* 45 */     this.delegate.connectFailed(uri, sa, ioe);
/*    */   }
/*    */   
/*    */   private boolean containsProxy(List list) {
/* 49 */     boolean containsProxy = false;
/* 50 */     if (!Util.isNullOrEmpty(list)) {
/* 51 */       for (Iterator<Proxy> iter = list.iterator(); iter.hasNext() && !containsProxy; ) {
/* 52 */         Proxy proxy = iter.next();
/* 53 */         containsProxy = !Proxy.NO_PROXY.equals(proxy);
/*    */       } 
/*    */     }
/* 56 */     return containsProxy;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Proxy> select(URI uri) {
/* 61 */     log.debug("retrieving proxy list for " + String.valueOf(uri) + "...");
/* 62 */     if (Boolean.getBoolean("debug") && log.isDebugEnabled()) {
/* 63 */       List<StackTraceElement> tmp = Arrays.asList((new Throwable()).getStackTrace());
/* 64 */       tmp = tmp.subList(0, Math.min(5, tmp.size()));
/* 65 */       log.debug("************************************call trace:");
/* 66 */       for (Iterator<StackTraceElement> iter = tmp.iterator(); iter.hasNext(); ) {
/* 67 */         StackTraceElement ste = iter.next();
/* 68 */         log.debug("  " + ste);
/*    */       } 
/* 70 */       log.debug("***********************************************");
/*    */     } 
/*    */     
/* 73 */     List<Proxy> ret = this.delegate.select(uri);
/* 74 */     if (!containsProxy(ret) && this.mode == 0) {
/* 75 */       List<Proxy> orgResponse = ret;
/* 76 */       log.debug("...delegation selector returned empty list, executing local lookup");
/*    */       do {
/* 78 */         log.debug("......lookup URI: " + String.valueOf(uri));
/* 79 */         ret = (List<Proxy>)getProxyMap().get(uri);
/* 80 */         uri = Util.shortenPath(uri);
/*    */       }
/* 82 */       while (!containsProxy(ret) && uri != null);
/*    */       
/* 84 */       if (ret == null) {
/* 85 */         ret = orgResponse;
/*    */       }
/*    */     } 
/*    */     
/* 89 */     log.debug("...resulting list: " + ret);
/*    */     
/* 91 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\CustomProxySelector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */