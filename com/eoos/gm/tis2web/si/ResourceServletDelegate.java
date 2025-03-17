/*     */ package com.eoos.gm.tis2web.si;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.cache.StreamCache;
/*     */ import com.eoos.scsm.v2.cache.StreamCacheHitRatioAdapter;
/*     */ import com.eoos.scsm.v2.cache.StreamCache_FileRI;
/*     */ import com.eoos.scsm.v2.io.MultiplexingOutputStream;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceServletDelegate
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(ResourceServletDelegate.class);
/*     */   
/*  33 */   private static ResourceServletDelegate instance = null;
/*     */   
/*     */   private StreamCache streamcache;
/*  36 */   private LockObjectProvider lop = new LockObjectProvider();
/*     */   
/*     */   private ResourceServletDelegate() {
/*  39 */     initCache();
/*  40 */     ConfigurationServiceProvider.getService().addObserver(new ConfigurationService.Observer()
/*     */         {
/*     */           public void onModification() {
/*  43 */             boolean enable = ConfigurationUtil.getBoolean("component.si.resource.cache.enabled", (Configuration)ConfigurationServiceProvider.getService()).booleanValue();
/*  44 */             if (ResourceServletDelegate.this.isCacheEnabled() != enable) {
/*  45 */               ResourceServletDelegate.this.initCache();
/*     */             }
/*     */           }
/*     */         });
/*     */     
/*  50 */     log.debug("...done");
/*     */   }
/*     */   
/*     */   public static synchronized ResourceServletDelegate getInstance() {
/*  54 */     if (instance == null) {
/*  55 */       instance = new ResourceServletDelegate();
/*     */     }
/*  57 */     return instance;
/*     */   }
/*     */   
/*     */   private boolean isCacheEnabled() {
/*  61 */     return (this.streamcache != StreamCache.DUMMY);
/*     */   }
/*     */   
/*     */   private boolean initCache() {
/*  65 */     if (ConfigurationUtil.getBoolean("component.si.resource.cache.enabled", (Configuration)ConfigurationServiceProvider.getService()).booleanValue()) {
/*     */       try {
/*  67 */         File file; String dir = ConfigurationServiceProvider.getService().getProperty("component.si.resource.cache.dir");
/*     */         
/*  69 */         if (!Util.isNullOrEmpty(dir)) {
/*  70 */           file = new File(dir);
/*  71 */           if (!file.exists() && !file.mkdirs()) {
/*  72 */             throw new IOException("unable to create dir " + String.valueOf(dir));
/*     */           }
/*     */         } else {
/*  75 */           file = Util.createTmpDir("si");
/*     */         } 
/*  77 */         log.debug("...creating stream cache for tmp directory " + file);
/*  78 */         this.streamcache = (StreamCache)new StreamCacheHitRatioAdapter((StreamCache)new StreamCache_FileRI(file, new StreamCache_FileRI.Callback()
/*     */               {
/*     */                 public String toFilename(Object key) {
/*  81 */                   return String.valueOf(key);
/*     */                 }
/*     */               }));
/*  84 */         log.info("si resource cache is enabled");
/*  85 */         return true;
/*     */       }
/*  87 */       catch (Exception e) {
/*  88 */         log.error("unable to create stream cache, using dummy - exception: " + e, e);
/*  89 */         this.streamcache = StreamCache.DUMMY;
/*  90 */         return false;
/*     */       } 
/*     */     }
/*  93 */     log.info("si resource cache disabled ");
/*  94 */     this.streamcache = StreamCache.DUMMY;
/*  95 */     return false;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 100 */     IMAGE, GRAPHIC;
/*     */   }
/*     */   
/*     */   private long getLength(Object key) throws IOException {
/* 104 */     return ((StreamCache.StreamSize)((StreamCacheHitRatioAdapter)this.streamcache).getBackendCache()).getLength(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processData(Type type, int id, HttpServletResponse response) throws IOException {
/* 109 */     ServletOutputStream servletOutputStream = response.getOutputStream();
/*     */     try {
/* 111 */       getResource(type, id, (OutputStream)servletOutputStream, response);
/*     */     } finally {
/*     */       
/* 114 */       servletOutputStream.flush();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void getResource(Type type, int id, OutputStream os, HttpServletResponse response) throws IOException {
/* 120 */     String key = type + String.valueOf(id);
/* 121 */     synchronized (this.lop.getLockObject(Integer.valueOf(id))) {
/*     */       
/* 123 */       InputStream is = this.streamcache.lookup(key);
/*     */       
/* 125 */       if (is != null) {
/* 126 */         if (response != null) {
/* 127 */           response.setContentLength((int)getLength(key));
/*     */         }
/*     */         try {
/* 130 */           StreamUtil.transfer(is, os);
/*     */         } finally {
/* 132 */           is.close();
/*     */         } 
/*     */       } else {
/* 135 */         SIOBlob blob = null;
/* 136 */         switch (type) {
/*     */           case IMAGE:
/* 138 */             blob = SIDataAdapterFacade.getInstance().getSI().getImage(id);
/*     */             break;
/*     */           
/*     */           case GRAPHIC:
/* 142 */             blob = SIDataAdapterFacade.getInstance().getSI().getGraphic(id);
/*     */             break;
/*     */           
/*     */           default:
/* 146 */             throw new IllegalArgumentException();
/*     */         } 
/*     */ 
/*     */         
/* 150 */         if (blob == null) {
/* 151 */           if (response != null)
/* 152 */             response.setStatus(204); 
/*     */         } else {
/* 154 */           if (response != null) {
/* 155 */             response.setContentLength((blob.getData()).length);
/*     */           }
/* 157 */           is = new ByteArrayInputStream(blob.getData());
/*     */           try {
/* 159 */             OutputStream cos = this.streamcache.getStorageStream(key);
/*     */             try {
/* 161 */               StreamUtil.transfer(is, (OutputStream)new MultiplexingOutputStream(new OutputStream[] { os, cos }));
/*     */             } finally {
/* 163 */               cos.close();
/*     */             } 
/*     */           } finally {
/* 166 */             is.close();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 176 */     log.debug("hit ratio of stream cache: " + ((StreamCacheHitRatioAdapter)this.streamcache).getHitRatio() + " hit/request");
/* 177 */     this.streamcache.clear();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\ResourceServletDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */