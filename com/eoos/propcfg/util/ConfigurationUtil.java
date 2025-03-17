/*     */ package com.eoos.propcfg.util;
/*     */ 
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.HashCalc;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationUtil
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(ConfigurationUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Mode
/*     */   {
/*     */     private Mode() {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  38 */   public static final Mode MODE_FULL_KEYS = new Mode();
/*     */   
/*  40 */   public static final Mode MODE_PREFIXES = new Mode();
/*     */   
/*     */   public static boolean isSubKey(String subkey, String parent) {
/*  43 */     return subkey.startsWith(parent);
/*     */   }
/*     */   
/*     */   public static List getPrefixes(String key) {
/*  47 */     List<String> retValue = new LinkedList();
/*  48 */     int i = key.length();
/*  49 */     while ((i = key.lastIndexOf(".", i - 1)) != -1) {
/*  50 */       retValue.add(key.substring(0, i));
/*     */     }
/*  52 */     return retValue;
/*     */   }
/*     */   
/*     */   public static int configurationHash(Configuration configuration, Collection keys, Mode mode) {
/*  56 */     if (mode == MODE_FULL_KEYS) {
/*  57 */       return configurationHash_FullKeys(configuration, keys);
/*     */     }
/*  59 */     return configurationHash_Prefixes(configuration, keys);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int configurationHash_FullKeys(Configuration configuration, Collection<?> _keys) {
/*  65 */     int ret = "CONFIGURATIONHash".hashCode();
/*  66 */     List<Comparable> keys = new ArrayList(_keys);
/*  67 */     Collections.sort(keys);
/*  68 */     for (Iterator<Comparable> iter = keys.iterator(); iter.hasNext(); ) {
/*  69 */       String key = (String)iter.next();
/*  70 */       ret = HashCalc.addHashCode(ret, key);
/*  71 */       String value = configuration.getProperty(key);
/*  72 */       ret = HashCalc.addHashCode(ret, value);
/*     */     } 
/*  74 */     return ret;
/*     */   }
/*     */   
/*     */   private static int configurationHash_Prefixes(Configuration configuration, Collection keyPrefixes) {
/*  78 */     int ret = "CONFIGURATIONHash".hashCode();
/*  79 */     List<Comparable> keys = new ArrayList(configuration.getKeys());
/*  80 */     Collections.sort(keys);
/*  81 */     for (Iterator<Comparable> iter = keys.iterator(); iter.hasNext(); ) {
/*  82 */       String key = (String)iter.next();
/*  83 */       if (keyMatchesPrefix(key, keyPrefixes)) {
/*  84 */         ret = HashCalc.addHashCode(ret, key);
/*  85 */         String value = configuration.getProperty(key);
/*  86 */         ret = HashCalc.addHashCode(ret, value);
/*     */       } 
/*     */     } 
/*  89 */     return ret;
/*     */   }
/*     */   
/*     */   public static int configurationHash(Configuration configuration) {
/*  93 */     return configurationHash(configuration, configuration.getKeys(), MODE_FULL_KEYS);
/*     */   }
/*     */   
/*     */   public static boolean keyMatchesPrefix(String key, Collection keyPrefixes) {
/*  97 */     boolean ret = false;
/*  98 */     for (Iterator<String> iter = keyPrefixes.iterator(); iter.hasNext() && !ret;) {
/*  99 */       ret = key.startsWith(iter.next());
/*     */     }
/* 101 */     return ret;
/*     */   }
/*     */   
/*     */   public static Set getMatchingKeys(Configuration configuration, Collection keyPrefixes) {
/* 105 */     Set<String> ret = new LinkedHashSet();
/* 106 */     for (Iterator<String> iter = configuration.getKeys().iterator(); iter.hasNext(); ) {
/* 107 */       String key = iter.next();
/* 108 */       if (keyMatchesPrefix(key, keyPrefixes)) {
/* 109 */         ret.add(key);
/*     */       }
/*     */     } 
/* 112 */     return ret;
/*     */   }
/*     */   
/*     */   private static boolean contains(String container, String string) {
/* 116 */     return (container.indexOf(string) != -1);
/*     */   }
/*     */   
/*     */   public static boolean isPossiblyPasswordKey(String key) {
/* 120 */     boolean ret = false;
/* 121 */     ret = (ret || contains(key, "pwd"));
/* 122 */     ret = (ret || contains(key, "passwd"));
/* 123 */     ret = (ret || contains(key, "password"));
/* 124 */     ret = (ret || contains(key, "pass"));
/* 125 */     ret = (ret || contains(key, "passwort"));
/* 126 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Collection getKeys(Configuration configuration, String prefix, String suffix) {
/* 131 */     Collection ret = new LinkedList(configuration.getKeys());
/* 132 */     for (Iterator<String> iter = ret.iterator(); iter.hasNext(); ) {
/* 133 */       String key = iter.next();
/* 134 */       if ((prefix != null && !key.startsWith(prefix)) || (suffix != null && !key.endsWith(suffix))) {
/* 135 */         iter.remove();
/*     */       }
/*     */     } 
/* 138 */     return ret;
/*     */   }
/*     */   
/*     */   public static Boolean getBoolean(String property, Configuration conf) {
/* 142 */     String tmp = conf.getProperty(property);
/* 143 */     if (Util.isNullOrEmpty(tmp)) {
/* 144 */       return Boolean.FALSE;
/*     */     }
/* 146 */     return Boolean.valueOf(tmp.trim());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isTrue(String property, Configuration conf) {
/* 151 */     return getBoolean(property, conf).booleanValue();
/*     */   }
/*     */   
/*     */   public static Number getNumber(String key, Configuration conf) {
/* 155 */     NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
/* 156 */     String tmp = conf.getProperty(key);
/* 157 */     if (Util.isNullOrEmpty(tmp)) {
/* 158 */       return null;
/*     */     }
/*     */     try {
/* 161 */       return nf.parse(tmp);
/* 162 */     } catch (ParseException e) {
/* 163 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasValue(String key, Configuration conf, String value) {
/* 169 */     String value2 = conf.getProperty(key);
/* 170 */     return Util.equals(Util.trim(value), Util.trim(value2));
/*     */   }
/*     */   
/*     */   public static Configuration getSystemPropertiesAdapter() {
/* 174 */     return SystemPropertiesAdapter.getInstance();
/*     */   }
/*     */   
/*     */   public static Properties toProperties(Configuration cfg) {
/* 178 */     Properties ret = new Properties();
/* 179 */     for (Iterator<String> iter = cfg.getKeys().iterator(); iter.hasNext(); ) {
/* 180 */       String key = iter.next();
/* 181 */       String value = cfg.getProperty(key);
/* 182 */       if (value != null) {
/* 183 */         ret.put(key, cfg.getProperty(key)); continue;
/*     */       } 
/* 185 */       log.warn("skipping key: " + key + " (is null)");
/*     */     } 
/*     */     
/* 188 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> getValueList(Configuration cfg, String key) {
/* 198 */     List<String> ret = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     if (key.endsWith(".")) {
/* 205 */       key = key.substring(0, key.length() - 1);
/*     */     }
/* 207 */     SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(cfg, key);
/*     */ 
/*     */     
/* 210 */     if (subConfigurationWrapper.getKeys().size() == 1) {
/* 211 */       ret = Util.parseList(subConfigurationWrapper.getProperty(subConfigurationWrapper.getKeys().iterator().next()));
/*     */     } else {
/* 213 */       ret = new LinkedList();
/* 214 */       for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext();) {
/* 215 */         ret.add(subConfigurationWrapper.getProperty(iter.next()));
/*     */       }
/*     */     } 
/*     */     
/* 219 */     return ret;
/*     */   }
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
/*     */   public static <T> List<T> getList(Configuration cfg, String key, Util.ObjectCreation<T> creationCallback) {
/* 239 */     List<T> ret = _getList(cfg, key, creationCallback);
/* 240 */     if (Util.isNullOrEmpty(ret)) {
/* 241 */       if (key.endsWith("s")) {
/* 242 */         ret = _getList(cfg, key.substring(0, key.length() - 1), creationCallback);
/*     */       } else {
/* 244 */         ret = _getList(cfg, key + "s", creationCallback);
/*     */       } 
/*     */     }
/* 247 */     return ret;
/*     */   }
/*     */   
/*     */   private static <T> List<T> _getList(Configuration cfg, String key, Util.ObjectCreation<T> creationCallback) {
/* 251 */     String value = cfg.getProperty(key);
/* 252 */     if (Util.isNullOrEmpty(value)) {
/* 253 */       return Collections.EMPTY_LIST;
/*     */     }
/* 255 */     return Util.parseList(value, "\\s*,\\s*", creationCallback);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Util.Duration getDuration(Configuration configuration, String key) {
/*     */     try {
/* 261 */       return Util.parseDuration(configuration.getProperty(key));
/* 262 */     } catch (Exception e) {
/* 263 */       log.warn("unable to retrieve duration for key: " + key + ", returning null");
/* 264 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\ConfigurationUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */