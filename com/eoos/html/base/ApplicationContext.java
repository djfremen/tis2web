/*     */ package com.eoos.html.base;
/*     */ 
/*     */ import com.eoos.context.Context;
/*     */ import com.eoos.context.IContext;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.util.FileUtilities;
/*     */ import com.eoos.util.IDFactory;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ApplicationContext
/*     */   implements IContext
/*     */ {
/*  34 */   private static final Logger log = Logger.getLogger(ApplicationContext.class);
/*     */   
/*     */   private static final String MESSAGE_RESOURCE_NAME = "message";
/*     */   
/*     */   private static final String LABEL_RESOURCE_NAME = "label";
/*     */   
/*  40 */   private ResourceLoading resourceLoader = null;
/*     */   
/*  42 */   protected final Object LOCK_PROPERTIES = new Object();
/*     */   
/*  44 */   private final Object lockObject = new Object();
/*     */   
/*  46 */   private Context contextDelegate = new Context();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract IDFactory getIDFactory();
/*     */ 
/*     */   
/*     */   public String getLabel(Locale locale, String key) {
/*  54 */     return getLabel(locale, key, key);
/*     */   }
/*     */   
/*     */   public String getLabel(Locale locale, String key, String defaultValue) {
/*  58 */     String result = null;
/*     */     
/*     */     try {
/*  61 */       ResourceBundle resource = ResourceBundle.getBundle("label", locale);
/*  62 */       result = resource.getString(key);
/*     */       
/*  64 */       if (result == null) {
/*  65 */         result = defaultValue;
/*     */       }
/*  67 */     } catch (Exception e) {
/*  68 */       result = defaultValue;
/*     */     } 
/*  70 */     return result;
/*     */   }
/*     */   
/*     */   public String getMessage(Locale locale, String key) {
/*  74 */     return getMessage(locale, key, key);
/*     */   }
/*     */   
/*     */   public String getMessage(Locale locale, String key, String defaultValue) {
/*  78 */     String result = null;
/*     */     
/*     */     try {
/*  81 */       ResourceBundle resource = ResourceBundle.getBundle("message", locale);
/*  82 */       result = resource.getString(key);
/*     */       
/*  84 */       if (result == null) {
/*  85 */         result = defaultValue;
/*     */       }
/*  87 */     } catch (Exception e) {
/*  88 */       result = defaultValue;
/*     */     } 
/*  90 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized byte[] loadResource(String name) {
/*  95 */     log.debug("loading resource: " + name);
/*  96 */     byte[] result = this.resourceLoader.loadResource(name);
/*  97 */     log.debug("finished loading");
/*  98 */     return result;
/*     */   }
/*     */   
/*     */   public boolean existsResource(String name) {
/* 102 */     return this.resourceLoader.existsResource(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized byte[] loadResource(Locale locale, String name) {
/* 107 */     byte[] retValue = null;
/* 108 */     String language = locale.getLanguage();
/* 109 */     String country = locale.getCountry();
/* 110 */     String variant = locale.getVariant();
/*     */     
/* 112 */     if (variant != null) {
/* 113 */       retValue = loadResource(language + "/" + country + "/" + variant + "/" + name);
/*     */     }
/* 115 */     if (retValue == null && country != null) {
/* 116 */       retValue = loadResource(language + "/" + country + "/" + name);
/*     */     }
/* 118 */     if (retValue == null) {
/* 119 */       retValue = loadResource(language + "/" + name);
/*     */     }
/* 121 */     return retValue;
/*     */   }
/*     */   
/*     */   public synchronized void setResourceLoader(ResourceLoading resourceLoader) {
/* 125 */     this.resourceLoader = resourceLoader;
/*     */   }
/*     */   
/*     */   public synchronized String createID() {
/* 129 */     return getIDFactory().getNextID();
/*     */   }
/*     */   
/*     */   public StringBuffer loadFile(Class clazz, String name, String encoding) throws Exception {
/* 133 */     StringBuffer retValue = null;
/*     */     
/* 135 */     String path = clazz.getPackage().getName();
/* 136 */     path = StringUtilities.replace(path, ".", "/");
/* 137 */     retValue = FileUtilities.readTextResource(clazz.getClassLoader(), path + "/" + name, encoding);
/* 138 */     return retValue;
/*     */   }
/*     */   
/*     */   public String loadTextResource(String name, String encoding) throws Exception {
/* 142 */     return new String(this.resourceLoader.loadResource(name), (encoding != null) ? encoding : "utf-8");
/*     */   }
/*     */   
/*     */   public void storeObject(Object identifier, Object data) {
/* 146 */     this.contextDelegate.storeObject(identifier, data);
/*     */   }
/*     */   
/*     */   public Object getObject(Object identifier) {
/* 150 */     return this.contextDelegate.getObject(identifier);
/*     */   }
/*     */   
/*     */   public Collection getObjects(Filter filter) {
/* 154 */     return this.contextDelegate.getObjects(filter);
/*     */   }
/*     */   
/*     */   public void onShutdown() {
/* 158 */     this.contextDelegate.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeObject(Object identifier) {
/* 163 */     this.contextDelegate.removeObject(identifier);
/*     */   }
/*     */   
/*     */   public Object getLockObject() {
/* 167 */     return this.lockObject;
/*     */   }
/*     */   
/*     */   public static interface ResourceLoading {
/*     */     byte[] loadResource(String param1String);
/*     */     
/*     */     boolean existsResource(String param1String);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\base\ApplicationContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */