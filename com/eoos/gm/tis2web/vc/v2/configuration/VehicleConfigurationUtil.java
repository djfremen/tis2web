/*     */ package com.eoos.gm.tis2web.vc.v2.configuration;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.CfgMngmnt_RI;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationManagement;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.UnresolvableException;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.ValueManagement;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.ValueMngmnt_RI;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.ValueUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Engine;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.EngineImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.MakeImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Model;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.ModelImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.ModelyearImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.NormalizationRegistry;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Transmission;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.TransmissionImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINImpl;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class VehicleConfigurationUtil
/*     */ {
/*  53 */   private static final Logger log = Logger.getLogger(VehicleConfigurationUtil.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigurationUtil cfgUtil;
/*     */ 
/*     */ 
/*     */   
/*     */   public static CfgMngmnt_RI cfgManagement;
/*     */ 
/*     */ 
/*     */   
/*     */   public static ValueUtil valueUtil;
/*     */ 
/*     */   
/*  68 */   public static ValueMngmnt_RI valueManagement = new ValueMngmnt_RI(); private static final Pattern[] p; public static final int CHECK_BASIC = 1; public static final int CHECK_ISO_3779 = 2; public static final Object KEY_MAKE; public static final Object KEY_MODEL; public static final Object KEY_MODELYEAR; public static final Object KEY_ENGINE; public static final Object KEY_TRANSMISSION; public static final Object[] KEYS; public static final Set KEY_SET; public static final List KEY_LIST;
/*  69 */   static { valueUtil = new ValueUtil((ValueManagement)valueManagement);
/*  70 */     cfgManagement = new CfgMngmnt_RI(valueUtil);
/*  71 */     cfgUtil = new ConfigurationUtil((ConfigurationManagement)cfgManagement, valueUtil);
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
/* 407 */     p = new Pattern[] { Pattern.compile("(\\p{Upper}\\p{Lower}+($|(\\p{Space}|\\p{Punct})+))+"), Pattern.compile("(\\p{Upper}+($|(\\p{Space}|\\p{Punct})+))+"), Pattern.compile("(\\p{Upper}(\\p{Upper}+|\\p{Lower}+)($|(\\p{Space}|\\p{Punct})+))+"), Pattern.compile("(\\p{Upper}|\\p{Digit}|\\p{Punct}|\\p{Space})*"), Pattern.compile(".*") };
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
/*     */ 
/*     */     
/* 572 */     KEY_MAKE = new Object() {
/*     */         public String toString() {
/* 574 */           return "MAKE";
/*     */         }
/*     */       };
/*     */     
/* 578 */     KEY_MODEL = new Object() {
/*     */         public String toString() {
/* 580 */           return "MODEL";
/*     */         }
/*     */       };
/*     */     
/* 584 */     KEY_MODELYEAR = new Object() {
/*     */         public String toString() {
/* 586 */           return "MODELYEAR";
/*     */         }
/*     */       };
/*     */     
/* 590 */     KEY_ENGINE = new Object() {
/*     */         public String toString() {
/* 592 */           return "ENGINE";
/*     */         }
/*     */       };
/*     */     
/* 596 */     KEY_TRANSMISSION = new Object() {
/*     */         public String toString() {
/* 598 */           return "TRANSMISSION";
/*     */         }
/*     */       };
/*     */     
/* 602 */     KEYS = new Object[] { KEY_MAKE, KEY_MODEL, KEY_MODELYEAR, KEY_ENGINE, KEY_TRANSMISSION };
/*     */     
/* 604 */     KEY_SET = Collections.unmodifiableSet(CollectionUtil.toSet(KEYS));
/*     */     
/* 606 */     KEY_LIST = Arrays.asList(KEYS); }
/*     */   public static IConfiguration createCfg(Object make, Object model, Object modelyear, Object engine, Object transmission) { return createCfg(make, model, modelyear, engine, transmission, false); }
/*     */   public static IConfiguration createCfg(final Object make, final Object model, final Object modelyear, final Object engine, final Object transmission, final boolean includeNullValues) { return cfgUtil.create(new ConfigurationUtil.CreationCallback() {
/*     */           public Value getValue(Object key) { if (VehicleConfigurationUtil.KEY_MAKE == key) return VehicleConfigurationUtil.valueManagement.toValue(make);  if (VehicleConfigurationUtil.KEY_MODEL == key) return VehicleConfigurationUtil.valueManagement.toValue(model);  if (VehicleConfigurationUtil.KEY_MODELYEAR == key) return VehicleConfigurationUtil.valueManagement.toValue(modelyear);  if (VehicleConfigurationUtil.KEY_ENGINE == key) return VehicleConfigurationUtil.valueManagement.toValue(engine);  if (VehicleConfigurationUtil.KEY_TRANSMISSION == key) return VehicleConfigurationUtil.valueManagement.toValue(transmission);  return null; }
/*     */           public Set getKeys() { return VehicleConfigurationUtil.KEY_SET; }
/*     */           public boolean includeNullValues() { return includeNullValues; }
/*     */         }); }
/* 613 */   public static IConfiguration createVC(Make make, Model model, Modelyear modelyear, Engine engine, Transmission transmission) { return createCfg(make, model, modelyear, engine, transmission); } public static IConfiguration createVC(String make, String model, String modelyear, String engine, String transmission) { Make _make = null; if (!Util.isNullOrEmpty(make)) _make = toMake(make);  Model _model = null; if (!Util.isNullOrEmpty(model)) _model = toModel(model);  Modelyear _modelyear = null; if (!Util.isNullOrEmpty(modelyear)) _modelyear = toModelyear(modelyear);  Engine _engine = null; if (!Util.isNullOrEmpty(engine)) _engine = toEngine(engine);  Transmission _transmission = null; if (!Util.isNullOrEmpty(transmission)) _transmission = toTransmission(transmission);  return createVC(_make, _model, _modelyear, _engine, _transmission); } public static boolean equals(Engine engine1, Engine engine2) { if (engine1 == null || engine2 == null) return (engine1 == null && engine2 == null);  return engine1.getIdentifier().equals(engine2.getIdentifier()); } public static boolean equals(Make make1, Make make2) { if (make1 == null || make2 == null) return (make1 == null && make2 == null);  return make1.getIdentifier().equals(make2.getIdentifier()); } public static boolean equals(Model model1, Model model2) { if (model1 == null || model2 == null) return (model1 == null && model2 == null);  return model1.getIdentifier().equals(model2.getIdentifier()); } public static boolean equals(Modelyear modelyear1, Modelyear modelyear2) { if (modelyear1 == null || modelyear2 == null) return (modelyear1 == null && modelyear2 == null);  return modelyear1.getIdentifier().equals(modelyear2.getIdentifier()); } public static boolean equals(Transmission transmission1, Transmission transmission2) { if (transmission1 == null || transmission2 == null) return (transmission1 == null && transmission2 == null);  return transmission1.getIdentifier().equals(transmission2.getIdentifier()); } public static Engine getEngine(IConfiguration currentCfg) { return (Engine)resolveSingularValue(currentCfg.getValue(KEY_ENGINE)); } public static Engine toEngine(String engine) { return (Engine)EngineImpl.getInstance(engine); } private static Object resolveSingularValue(Value value) { try { Object ret = null; if (value != null) { Set resolved = valueUtil.resolve(value, null); if (!Util.isNullOrEmpty(resolved)) ret = CollectionUtil.getFirst(resolved);  }  return ret; } catch (UnresolvableException e) { throw new RuntimeException(e); }  } public static Set resolveValues(Collection<Value> values) { Set ret = new HashSet(); for (Iterator<Value> iter = values.iterator(); iter.hasNext();) { try { ret.addAll(valueUtil.resolve(iter.next(), null)); } catch (UnresolvableException e) { throw new RuntimeException(e); }  }  return ret; } public static Make getMake(IConfiguration currentCfg) { return (Make)resolveSingularValue(currentCfg.getValue(KEY_MAKE)); } public static Make toMake(String make) { return (Make)MakeImpl.getInstance(make); } public static Model getModel(IConfiguration currentCfg) { return (Model)resolveSingularValue(currentCfg.getValue(KEY_MODEL)); } public static Model toModel(String model) { return (Model)ModelImpl.getInstance(model); } public static Modelyear getModelyear(IConfiguration currentCfg) { return (Modelyear)resolveSingularValue(currentCfg.getValue(KEY_MODELYEAR)); } public static Modelyear toModelyear(String modelyear) { return (Modelyear)ModelyearImpl.getInstance(modelyear); } public static Transmission getTransmission(IConfiguration currentCfg) { return (Transmission)resolveSingularValue(currentCfg.getValue(KEY_TRANSMISSION)); } public static Transmission toTransmission(String transmission) { return (Transmission)TransmissionImpl.getInstance(transmission); } public static VIN getVIN(String vin) throws VIN.InvalidVINException { return (VIN)new VINImpl(vin); } public static int hashCode(Engine engine) { int ret = Engine.class.hashCode(); ret = HashCalc.addHashCode(ret, engine.getIdentifier()); return ret; } public static String getConfigurationValue(IConfiguration cfg, Object key) { String ret = null;
/* 614 */     if (cfg != null) {
/* 615 */       Object value = cfg.getValue(key);
/* 616 */       if (value != null) {
/* 617 */         value = resolveSingularValue((Value)value);
/* 618 */         if (value != null) {
/* 619 */           ret = toString_Normalized(value);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 624 */     return ret; } public static int hashCode(Make make) { int ret = Make.class.hashCode(); ret = HashCalc.addHashCode(ret, make.getIdentifier()); return ret; } public static int hashCode(Model model) { int ret = Model.class.hashCode(); ret = HashCalc.addHashCode(ret, model.getIdentifier()); return ret; } public static int hashCode(Modelyear modelyear) { int ret = Modelyear.class.hashCode(); ret = HashCalc.addHashCode(ret, modelyear.getIdentifier()); return ret; } public static int hashCode(Transmission transmission) { int ret = Transmission.class.hashCode(); ret = HashCalc.addHashCode(ret, transmission.getIdentifier()); return ret; } public static int hashCode(IConfiguration cfg) { return cfgUtil.hashCode(cfg); } public static boolean isEmptyCfg(IConfiguration cfg) { return cfgUtil.equals(cfg, cfgManagement.getEmptyConfiguration()); } public static String normalize(String string) { StringBuffer buffer = new StringBuffer(); char[] charArray = string.toLowerCase(Locale.ENGLISH).toCharArray(); for (int i = 0; i < charArray.length; i++) { char c = charArray[i]; if (Character.isLetterOrDigit(c)) buffer.append(c);  }  return buffer.toString(); } public static IConfiguration set(IConfiguration cfg, Object key, Object modelObject) { return cfgManagement.setAttribute(cfg, key, valueManagement.toValue(modelObject)); } public static String toString(Engine engine) { return toString_Normalized(engine); } public static String toString(Make make) { return toString_Normalized(make); } public static String toString(Model model) { return toString_Normalized(model); } public static String toString(Modelyear modelyear) { return toString_Normalized(modelyear); } public static String toString(Transmission transmission) { return toString_Normalized(transmission); } public static String toString(IConfiguration vehicleCfg, boolean includeEmptyAttributes) { return toString(vehicleCfg, new DisplayCallback() { public String getDisplayValue(Object obj) { return String.valueOf(obj); }
/*     */         },  includeEmptyAttributes); } public static String toString(IConfiguration vehicleCfg, ClientContext context, boolean includeEmptyAttributes) { final VCService vcs = VCServiceProvider.getInstance().getService(context); return toString(vehicleCfg, new DisplayCallback() { public String getDisplayValue(Object obj) { return vcs.getDisplayValue(obj); }
/*     */         },  includeEmptyAttributes); } public static String toString(IConfiguration vehicleCfg, DisplayCallback callback, boolean includeEmptyAttributes) { StringBuffer ret = new StringBuffer(); Make make = getMake(vehicleCfg); if (make != null) { ret.append(callback.getDisplayValue(make) + ", "); } else if (includeEmptyAttributes) { ret.append(", "); }  Model model = getModel(vehicleCfg); if (model != null) { ret.append(callback.getDisplayValue(model) + ", "); } else if (includeEmptyAttributes) { ret.append(", "); }  Modelyear modelyear = getModelyear(vehicleCfg); if (modelyear != null) { ret.append(callback.getDisplayValue(modelyear) + ", "); } else if (includeEmptyAttributes) { ret.append(", "); }  Engine engine = getEngine(vehicleCfg); if (engine != null) { ret.append(callback.getDisplayValue(engine) + ", "); } else if (includeEmptyAttributes) { ret.append(", "); }  Transmission transmission = getTransmission(vehicleCfg); if (transmission != null) { ret.append(callback.getDisplayValue(transmission) + ", "); } else if (includeEmptyAttributes) { ret.append(", "); }  if (ret.length() > 2) ret.delete(ret.length() - 2, ret.length());  return ret.toString(); } public static String[] toStringArray(IConfiguration vehicleCfg) { String[] ret = new String[5]; Make make = getMake(vehicleCfg); Model model = getModel(vehicleCfg); Modelyear modelyear = getModelyear(vehicleCfg); Engine engine = getEngine(vehicleCfg); Transmission transmission = getTransmission(vehicleCfg); ret[0] = (make != null) ? make.toString() : null; ret[1] = (model != null) ? model.toString() : null; ret[2] = (modelyear != null) ? modelyear.toString() : null; ret[3] = (engine != null) ? engine.toString() : null; ret[4] = (transmission != null) ? transmission.toString() : null; return ret; } private static int calcComparatorOrd(String string) { int ret = 0; if (Util.isNullOrEmpty(string)) return Integer.MAX_VALUE;  for (int i = 0; i < p.length; i++) { if (p[i].matcher(string).matches()) { ret += i * 1000; break; }  }  return ret; } public static void main(String[] args) { Log4jUtil.attachConsoleAppender(); String[] test = { "Y20 DTH", "y20 dth" }; List<?> tmp = new LinkedList(Arrays.asList((Object[])test)); Collections.sort(tmp, new Comparator() { public int compare(Object o1, Object o2) { String s1 = (String)o1; String s2 = (String)o2; int result = s2.length() - s1.length(); if (result == 0) result = VehicleConfigurationUtil.calcComparatorOrd(s1) - VehicleConfigurationUtil.calcComparatorOrd(s2);  return (result != 0) ? result : s1.compareTo(s2); } }
/*     */       ); Logger.getRootLogger().debug("result:" + tmp); } private static String selectDisplay(Collection<?> options) { try { List<?> _options = new ArrayList(options); Collections.sort(_options, new Comparator() { public int compare(Object o1, Object o2) { String s1 = (String)o1; String s2 = (String)o2; int result = s2.length() - s1.length(); if (result == 0) result = VehicleConfigurationUtil.calcComparatorOrd(s1) - VehicleConfigurationUtil.calcComparatorOrd(s2);  return (result != 0) ? result : s1.compareTo(s2); } }
/* 628 */         ); return (String)_options.get(0); } catch (Exception e) { log.warn("unable to determine display string, returning first (if available) - exception:", e); if (options != null && options.size() > 0) return options.iterator().next();  return "n/a"; }  } public static String toString_Normalized(Object normalizedObject) { String ret = null; if (normalizedObject != null) { Collection tmp = NormalizationRegistry.getInstance().getOriginalForms(normalizedObject); if (tmp.size() == 1) return (String)CollectionUtil.getFirst(tmp);  if (tmp.size() > 0) ret = selectDisplay(tmp);  }  return ret; } public static Object toModelObject(Object key, String value) { if (value.equals("*")) return valueManagement.getANY();  if (key == KEY_MAKE) return MakeImpl.getInstance(value);  if (key == KEY_MODEL) return ModelImpl.getInstance(value);  if (key == KEY_MODELYEAR) return ModelyearImpl.getInstance(value);  if (key == KEY_ENGINE) return EngineImpl.getInstance(value);  if (key == KEY_TRANSMISSION) return TransmissionImpl.getInstance(value);  throw new IllegalStateException(); } public static String keyToString(Object key, Locale locale) { if (key == null) return "null";  ApplicationContext context = ApplicationContext.getInstance(); if (key == KEY_MAKE) return context.getLabel(locale, "make");  if (key == KEY_MODEL) return context.getLabel(locale, "model");  if (key == KEY_MODELYEAR) return context.getLabel(locale, "modelyear");  if (key == KEY_TRANSMISSION) return context.getLabel(locale, "transmission");  if (key == KEY_ENGINE) return context.getLabel(locale, "engine");  return key.toString(); } public static boolean checkVIN(String vin, int flagCheck) { boolean ret = true; ret = (ret && vin != null); if (ret) { vin = vin.toUpperCase(Locale.ENGLISH); if (flagCheck - 2 >= 0) { ret = (ret && vin.length() == 17); } else { ret = (ret && vin.length() > 0); ret = (ret && vin.length() <= 17); }  for (int i = 0; i < vin.length() && ret; i++) { char c = vin.charAt(i); ret = (ret && Character.isLetterOrDigit(c)); if (flagCheck - 2 >= 0) ret = (ret && c != 'O' && c != 'Q' && c != 'I');  }  }  return ret; } public static Set<Make> getMakes(CfgProvider cfgProvider) { Set<Make> ret = new HashSet<Make>();
/* 629 */     for (Iterator<IConfiguration> iter = cfgProvider.getConfigurations().iterator(); iter.hasNext(); ) {
/* 630 */       IConfiguration config = iter.next();
/* 631 */       ret.add(getMake(config));
/*     */     } 
/* 633 */     return ret; }
/*     */ 
/*     */   
/*     */   public static IConfiguration parse(String string) {
/* 637 */     if (!Util.isNullOrEmpty(string)) {
/* 638 */       String[] values = new String[5];
/* 639 */       Arrays.fill((Object[])values, (Object)null);
/*     */       
/* 641 */       String[] partsList = string.split("\\s*,\\s*");
/* 642 */       System.arraycopy(partsList, 0, values, 0, partsList.length);
/* 643 */       return createVC(values[0], values[1], values[2], values[3], values[4]);
/*     */     } 
/* 645 */     return cfgManagement.getEmptyConfiguration();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String encode(VIN vin, IConfiguration cfg) {
/* 650 */     StringBuilder ret = new StringBuilder();
/* 651 */     if (vin != null) {
/* 652 */       ret.append(vin.toString());
/*     */     }
/* 654 */     ret.append("@");
/* 655 */     ret.append(toString(cfg, true));
/* 656 */     return ret.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DecodingResult decode(String encoded) {
/* 666 */     String[] parts = (encoded != null) ? encoded.split("@") : new String[0];
/* 667 */     VIN vin = null;
/* 668 */     IConfiguration cfg = null;
/* 669 */     if (parts.length > 0) {
/* 670 */       if (!Util.isNullOrEmpty(parts[0])) {
/*     */         try {
/* 672 */           VINImpl vINImpl = new VINImpl(parts[0]);
/* 673 */         } catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.InvalidVINException e) {
/* 674 */           log.warn("unable to create VIN, continuing with <null> - exception: ", (Throwable)e);
/* 675 */           vin = null;
/*     */         } 
/*     */       }
/* 678 */       if (parts.length > 1 && !Util.isNullOrEmpty(parts[1])) {
/* 679 */         cfg = parse(parts[1]);
/*     */       }
/*     */     } 
/*     */     
/* 683 */     final VIN fVin = vin;
/* 684 */     final IConfiguration fCfg = cfg;
/* 685 */     return new DecodingResult()
/*     */       {
/*     */         public VIN getVIN() {
/* 688 */           return fVin;
/*     */         }
/*     */         
/*     */         public IConfiguration getConfiguration() {
/* 692 */           return fCfg;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Filter createConfigurationFilter(Set cfgStrings) {
/* 699 */     if (Util.isNullOrEmpty(cfgStrings)) {
/* 700 */       return Filter.INCLUDE_ALL;
/*     */     }
/* 702 */     final Set<IConfiguration> cfgs = new HashSet();
/* 703 */     for (Iterator<String> iter = cfgStrings.iterator(); iter.hasNext(); ) {
/* 704 */       String tmp = iter.next();
/*     */       
/* 706 */       Object[] values = new Object[5];
/* 707 */       Arrays.fill(values, valueManagement.getANY());
/*     */       
/* 709 */       String[] parts = tmp.split("\\s*,\\s*");
/* 710 */       for (int j = 0; j < parts.length; j++) {
/* 711 */         Object key = KEYS[j];
/* 712 */         values[j] = toModelObject(key, parts[j]);
/*     */       } 
/*     */       
/* 715 */       cfgs.add(createCfg(values[0], values[1], values[2], values[3], values[4]));
/*     */     } 
/*     */     
/* 718 */     return new Filter()
/*     */       {
/*     */         public boolean include(Object obj) {
/* 721 */           boolean ret = false;
/* 722 */           IConfiguration conf = (IConfiguration)obj;
/* 723 */           for (Iterator<IConfiguration> iter = cfgs.iterator(); iter.hasNext() && !ret; ) {
/* 724 */             IConfiguration cfg = iter.next();
/* 725 */             ret = VehicleConfigurationUtil.cfgUtil.isPartialConfiguration(cfg, conf, ConfigurationUtil.EXPAND_WITH_ANY);
/*     */           } 
/*     */           
/* 728 */           return ret;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ICfgSerializationWrapper
/*     */     implements Serializable, IConfiguration
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private String string;
/*     */     private transient IConfiguration cfg;
/*     */     
/*     */     private ICfgSerializationWrapper(IConfiguration cfg) {
/* 742 */       this.cfg = cfg;
/* 743 */       this.string = VehicleConfigurationUtil.toString(cfg, true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ICfgSerializationWrapper() {}
/*     */ 
/*     */     
/*     */     private Object readResolve() throws ObjectStreamException {
/* 752 */       this.cfg = VehicleConfigurationUtil.parse(this.string);
/* 753 */       return this.cfg;
/*     */     }
/*     */     
/*     */     public Set getKeys() {
/* 757 */       return this.cfg.getKeys();
/*     */     }
/*     */     
/*     */     public Value getValue(Object key) {
/* 761 */       return this.cfg.getValue(key);
/*     */     }
/*     */   }
/*     */   
/*     */   public static IConfiguration ensureSerializable(IConfiguration cfg) {
/* 766 */     if (!(cfg instanceof Serializable)) {
/* 767 */       return new ICfgSerializationWrapper(cfg);
/*     */     }
/* 769 */     return cfg;
/*     */   }
/*     */   
/*     */   public static interface DecodingResult {
/*     */     VIN getVIN();
/*     */     
/*     */     IConfiguration getConfiguration();
/*     */   }
/*     */   
/*     */   public static interface DisplayCallback {
/*     */     String getDisplayValue(Object param1Object);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\configuration\VehicleConfigurationUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */