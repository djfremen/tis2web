/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html.input;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.html.base.ApplicationContext;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.LocaleSelectionElement;
/*     */ import com.eoos.resource.loading.DirectoryResourceLoading;
/*     */ import com.eoos.resource.loading.ResourceLoading;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CountrySelectionElement extends LocaleSelectionElement {
/*  22 */   private static final Logger log = Logger.getLogger(CountrySelectionElement.class);
/*     */   
/*  24 */   private static final Object SYNC_MAPPING = new Object();
/*     */   
/*  26 */   private static Map codeToDisplay = null;
/*     */   
/*     */   public CountrySelectionElement(String parameterName, boolean singleSelectionMode, DataRetrievalAbstraction.DataCallback optionsCallback, int size, String targetBookmark, Locale displayLocale) {
/*  29 */     super(parameterName, singleSelectionMode, optionsCallback, size, targetBookmark, displayLocale);
/*     */   }
/*     */   
/*     */   private static Map getCodeMapping() {
/*  33 */     synchronized (SYNC_MAPPING) {
/*  34 */       if (codeToDisplay == null) {
/*  35 */         codeToDisplay = new HashMap<Object, Object>();
/*     */         try {
/*  37 */           String file = ApplicationContext.getInstance().loadTextResource("/common/iso3166.txt", "utf-8");
/*  38 */           LineNumberReader lnr = new LineNumberReader(new StringReader(file));
/*  39 */           String line = null;
/*  40 */           while ((line = lnr.readLine()) != null) {
/*  41 */             if (line.trim().length() == 0 || line.startsWith("#")) {
/*     */               continue;
/*     */             }
/*     */             try {
/*  45 */               String code = line.substring(0, 2);
/*  46 */               String description = line.substring(11).trim();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  52 */               if (!codeToDisplay.containsKey(code)) {
/*  53 */                 codeToDisplay.put(code, description);
/*     */               }
/*  55 */             } catch (Exception e) {
/*  56 */               log.warn("unable to process line, skipping : " + String.valueOf(line));
/*     */             }
/*     */           
/*     */           } 
/*  60 */         } catch (Exception e) {
/*  61 */           throw new RuntimeException(e);
/*     */         } 
/*     */       } 
/*  64 */       return codeToDisplay;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getDisplayValue(Object option) {
/*  69 */     String ret = super.getDisplayValue(option);
/*  70 */     if (ret.length() == 2) {
/*  71 */       ret = (String)getCodeMapping().get(ret.toUpperCase(Locale.ENGLISH));
/*     */     }
/*  73 */     return ret;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  77 */     final DirectoryResourceLoading fallback = new DirectoryResourceLoading(new File("e:/projects/workspace/tis2web.1/sources/war/resources"));
/*  78 */     final DirectoryResourceLoading front = new DirectoryResourceLoading(new File("e:/projects/workspace/tis2web.1/build/env/smaier/overwrite/resources"));
/*  79 */     final ResourceLoading loader = new ResourceLoading()
/*     */       {
/*     */         public Collection searchResources(Object searchPattern) {
/*  82 */           return null;
/*     */         }
/*     */         
/*     */         public Object loadResource(Object identifier) throws IOException {
/*  86 */           Object ret = null;
/*     */           try {
/*  88 */             ret = front.loadResource(identifier);
/*  89 */           } catch (Exception e) {
/*  90 */             ret = null;
/*     */           } 
/*  92 */           if (ret == null) {
/*  93 */             ret = fallback.loadResource(identifier);
/*     */           }
/*  95 */           return ret;
/*     */         }
/*     */         
/*     */         public InputStream getResource(Object identifier) throws IOException {
/*  99 */           InputStream ret = null;
/*     */           try {
/* 101 */             ret = front.getResource(identifier);
/* 102 */           } catch (Exception e) {
/* 103 */             ret = null;
/*     */           } 
/* 105 */           if (ret == null) {
/* 106 */             ret = fallback.getResource(identifier);
/*     */           }
/* 108 */           return ret;
/*     */         }
/*     */         
/*     */         public boolean existsResource(Object identifier) {
/* 112 */           boolean ret = front.existsResource(identifier);
/* 113 */           if (!ret) {
/* 114 */             ret = fallback.existsResource(identifier);
/*     */           }
/* 116 */           return ret;
/*     */         }
/*     */       };
/*     */     
/* 120 */     FrameServiceProvider.create(loader);
/* 121 */     ApplicationContext.getInstance().setResourceLoader(new ApplicationContext.ResourceLoading()
/*     */         {
/*     */           public byte[] loadResource(String name) {
/*     */             try {
/* 125 */               return (byte[])loader.loadResource(name);
/* 126 */             } catch (IOException e) {
/* 127 */               throw new RuntimeException(e);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean existsResource(String name) {
/* 133 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 137 */     ApplicationContext.getInstance().onStartup();
/*     */     
/* 139 */     Map map = getCodeMapping();
/* 140 */     System.out.println(map.get("AG"));
/* 141 */     System.out.println(map.get("DE"));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\input\CountrySelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */