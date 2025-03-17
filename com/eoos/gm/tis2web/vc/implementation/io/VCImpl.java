/*      */ package com.eoos.gm.tis2web.vc.implementation.io;
/*      */ 
/*      */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*      */ import com.eoos.gm.tis2web.frame.export.common.locale.LGSIT;
/*      */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*      */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.ConNvent;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*      */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.DBMS;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRBaseValue;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRConfigurationImpl;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRConstraintImpl;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRDomainImpl;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRMake;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRModel;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRModelYear;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCRValueImpl;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VCSchema;
/*      */ import com.eoos.gm.tis2web.vc.implementation.io.db.VehicleOptionGroup;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCMake;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCModel;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCModelYear;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCRConfiguration;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCRConstraint;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VINDecoder;
/*      */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationManagement;
/*      */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*      */ import com.eoos.gm.tis2web.vc.v2.base.value.UnresolvableException;
/*      */ import com.eoos.gm.tis2web.vc.v2.base.value.ValueManagement;
/*      */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*      */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*      */ import com.eoos.jdbc.ConnectionProvider;
/*      */ import com.eoos.jdbc.JDBCUtil;
/*      */ import com.eoos.propcfg.Configuration;
/*      */ import com.eoos.scsm.v2.cache.Cache;
/*      */ import com.eoos.scsm.v2.util.Util;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.log4j.Logger;
/*      */ 
/*      */ 
/*      */ public class VCImpl
/*      */   implements VC
/*      */ {
/*   70 */   protected static Logger log = Logger.getLogger(VCImpl.class); protected Map domains; protected Map domainsByName;
/*      */   protected Map configurations;
/*      */   protected Map configurationsByKey;
/*      */   protected VINDecoder decoder;
/*      */   protected DBVersionInformation version;
/*      */   private ILVCAdapter.Retrieval lvcr;
/*      */   private ConnectionProvider connectionProvider;
/*      */   private final Object SYNC_DTN;
/*      */   private Map domainIDToName;
/*      */   private Cache valueResolve;
/*      */   private Cache cfgToVCR;
/*      */   
/*      */   public Collection getSalesMakes() {
/*   83 */     return ((VCRDomain)getDomain("Make")).getValues();
/*      */   }
/*      */   
/*      */   public VCDomain getDomain(int domain_id) {
/*   87 */     return getDomain(Integer.valueOf(domain_id));
/*      */   }
/*      */   
/*      */   public VCDomain getDomain(Integer domain_id) {
/*   91 */     return (VCDomain)this.domains.get(domain_id);
/*      */   }
/*      */   
/*      */   public VCDomain getDomain(String domain) {
/*   95 */     return (VCDomain)this.domainsByName.get(domain);
/*      */   }
/*      */   
/*      */   public String getLabel(VCValue value) {
/*   99 */     return getValueLabel(((VCRValue)value).getLabel());
/*      */   }
/*      */   
/*      */   public String getLabel(Integer locale_id, VCValue value) {
/*  103 */     return getValueLabel(locale_id, (VCRValue)value);
/*      */   }
/*      */   
/*      */   public VCValue getValue(Integer locale_id, VCDomain domain, String label) {
/*  107 */     return getLabelValue(locale_id, (VCRDomain)domain, label);
/*      */   }
/*      */   
/*      */   public Collection getConfigurations() {
/*  111 */     return this.configurations.values();
/*      */   }
/*      */   
/*      */   public VCConfiguration getConfiguration(VCR vcr) {
/*  115 */     return findConfiguration(vcr);
/*      */   }
/*      */   
/*      */   public VCConfiguration getConfiguration(Integer config_id) {
/*  119 */     return (VCConfiguration)this.configurations.get(config_id);
/*      */   }
/*      */   
/*      */   public VCConfiguration getConfiguration(VCMake make, VCModel model, VCModelYear modelYear) {
/*  123 */     return (VCConfiguration)this.configurationsByKey.get(VCRConfigurationImpl.makeKey((VCRMake)make, (VCRModel)model, (VCRModelYear)modelYear));
/*      */   }
/*      */   
/*      */   public String getDisplay(VCConfiguration config) {
/*  127 */     return assembleDisplay(config);
/*      */   }
/*      */   
/*      */   public String getDisplayModel(VCR vcr) {
/*  131 */     return assembleDisplayModel(vcr);
/*      */   }
/*      */   
/*      */   public Collection getConfigurations(Collection configurations, VCR vcr) {
/*  135 */     return filterConfigurations(configurations, vcr);
/*      */   }
/*      */   
/*      */   public Collection getConfigurations(VCR vcr) {
/*  139 */     return filterConfigurations(vcr);
/*      */   }
/*      */   
/*      */   public Collection getSalesMakes(Collection configurations) {
/*  143 */     return filterSalesMakes(configurations);
/*      */   }
/*      */   
/*      */   public Collection getModels(Collection configurations) {
/*  147 */     return filterModels(configurations);
/*      */   }
/*      */   
/*      */   public Collection getModelYears(Collection configurations) {
/*  151 */     return filterModelYears(configurations);
/*      */   }
/*      */   
/*      */   public Collection getEngines(VCR vcr, Collection configurations) {
/*  155 */     return filterEngines(vcr, configurations);
/*      */   }
/*      */   
/*      */   public Collection getTransmissions(VCR vcr, Collection configurations) {
/*  159 */     return filterTransmissions(vcr, configurations);
/*      */   }
/*      */   
/*      */   public VINDecoder getVINDecoder() {
/*  163 */     return this.decoder;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List getVehicleOptions(Integer esc) {
/*  169 */     return provideVehicleOptions(esc);
/*      */   }
/*      */   
/*      */   public List getAttributes(String domain, VCR vcr) {
/*  173 */     return listAttributes(getDomain(domain), vcr);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public DBVersionInformation getVersionInfo() {
/*  179 */     return this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VCImpl(Configuration cfg, final IDatabaseLink dblink, ILVCAdapter.Retrieval lvcr) throws Exception {
/*  794 */     this.SYNC_DTN = new Object();
/*  795 */     this.domainIDToName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  907 */     this.valueResolve = Tis2webUtil.createStdCache();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     this.cfgToVCR = Tis2webUtil.createStdCache(); this.lvcr = lvcr; DBMS dbms = new DBMS(dblink); VCSchema schema = new VCSchema(dbms); this.version = schema.loadVersionInformation(); this.domains = schema.loadDomains(); this.domainsByName = new HashMap<Object, Object>(); Iterator<VCRDomain> it = this.domains.values().iterator(); while (it.hasNext()) { VCRDomain domain = it.next(); this.domainsByName.put(domain.getDomainName(), domain); }  this.configurationsByKey = new HashMap<Object, Object>(); if (getDomain("Vehicle") != null) { this.configurations = schema.loadConfigurations(this); Iterator<VCRConfiguration> cit = this.configurations.values().iterator(); while (cit.hasNext()) { VCRConfiguration configuration = cit.next(); this.configurationsByKey.put(configuration.getKey(), configuration); }  } else { this.configurations = new HashMap<Object, Object>(); }  schema.loadMappings(this); this.decoder = schema.createVINDecoder(this, "com.eoos.gm.tis2web.vc.implementation.io.vin.VINDecoderTIS"); this.decoder.setConfiguration(cfg); schema = null; dbms = null; this.connectionProvider = ConNvent.create(new ConnectionProvider() {
/*      */           public void releaseConnection(Connection connection) { dblink.releaseConnection(connection); } public Connection getConnection() { try { return dblink.requestConnection(); } catch (Exception e) { throw new RuntimeException(e); }  }
/*      */         },  60000L);
/* 1001 */   } public VCValue map(VCValue value, String domain) { VCRDomain valueDomain = (VCRDomain)getDomain(value.getDomainID()); VCRDomainImpl targetDomain = (VCRDomainImpl)getDomain(domain); return valueDomain.map(value, (VCDomain)targetDomain); } protected VCConfiguration findConfiguration(VCR vcr) { log.debug("configuration lookup for: " + String.valueOf(vcr)); Iterator<VCConfiguration> it = this.configurations.values().iterator(); while (it.hasNext()) { VCConfiguration configuration = it.next(); VCR vcc = this.lvcr.getLVCAdapter().makeVCR(configuration); if (vcr.match(vcc)) { log.debug("...successfully matched configuration: " + configuration); return configuration; }  }  log.debug("...failed to retrieve configuration for vcr: " + String.valueOf(vcr)); return null; } protected String assembleDisplay(VCConfiguration configuration) { StringBuffer label = new StringBuffer(); List<VCRValueImpl> elements = configuration.getElements(); for (int k = 0; k < elements.size(); k++) { VCRValueImpl value = elements.get(k); if (label.length() > 0) label.append(' ');  label.append(getLabel((VCValue)value)); }  return label.toString(); } private ILVCAdapter getLVCAdapter() { return this.lvcr.getLVCAdapter(); } protected String assembleDisplayModel(VCR vcr) { String result = null; VCRDomain domain = (VCRDomain)getDomain("Model"); Iterator<VCModel> it = domain.getValues().iterator(); while (it.hasNext()) { VCModel model = it.next(); VCR test = getLVCAdapter().makeVCR((VCValue)model); if (vcr.match(test)) { if (result == null) { result = model.toString(); continue; }  result = model.toString() + " + " + result; }  }  return result; } public List listAttributes(VCDomain domain, VCR vcr) { List<String> attributes = new ArrayList(); Iterator<VCValue> it = ((VCRDomain)domain).getValues().iterator(); while (it.hasNext()) { VCValue value = it.next(); if (vcr.match(value)) attributes.add(value.toString());  }  return attributes; } protected String getValueLabel(Integer locale_id, VCRValue value) { VCRLabel label = value.getLabel(); String l = label.getLabel(locale_id); if (l == null) { LocaleInfo li = LocaleInfoProvider.getInstance().getLocale(locale_id); List<Integer> flc = li.getLocaleFLC(LGSIT.VC); if (flc == null) return null;  for (int i = 0; i < flc.size(); i++) { l = label.getLabel(flc.get(i)); if (l != null) break;  }  }  return l; } protected String getValueLabel(VCRLabel label) { if (label == null || !(label instanceof com.eoos.gm.tis2web.vc.implementation.io.db.VCLabel)) return null;  return label.getLabel(null); } public VCValue getLabelValue(Integer locale_id, VCRDomain domain, String label) { VCRValue value = domain.lookup(locale_id, label); if (value == null) { LocaleInfo li = LocaleInfoProvider.getInstance().getLocale(locale_id); List<Integer> flc = li.getLocaleFLC(LGSIT.VC); if (flc == null) return null;  for (int i = 0; i < flc.size(); i++) { value = domain.lookup(flc.get(i), label); if (value != null) break;  }  }  return (VCValue)value; } protected Collection filterConfigurations(Collection vcAuthorizedConfigurations, VCR vcr) { if (getLVCAdapter().isNullVCR(vcr)) return null;  HashSet<VCConfiguration> configurations = null; List<VCRAttribute> fixedAttributes = vcr.getAttributes(); if (fixedAttributes.size() == 0) return null;  configurations = new HashSet(); for (int i = 0; i < fixedAttributes.size(); i++) { VCRAttribute attribute = fixedAttributes.get(i); VCRDomain domain = (VCRDomain)getDomain(attribute.getDomainID()); VCRBaseValue value = (VCRBaseValue)domain.getValue(attribute.getValueID()); if (!domain.getDomainName().equalsIgnoreCase("Make") && !domain.getDomainName().equalsIgnoreCase("Model") && !domain.getDomainName().equalsIgnoreCase("ModelYear")) { configurations.retainAll(value.getConfigurations()); } else { Iterator<VCConfiguration> iterator = null; if (i == 0) { iterator = vcAuthorizedConfigurations.iterator(); } else { iterator = configurations.iterator(); }  while (iterator.hasNext()) { VCConfiguration conf = iterator.next(); Iterator<VCValue> iter = conf.getElements().iterator(); boolean find = false; while (iter.hasNext()) { VCValue elem = iter.next(); if (elem.getDomainID().equals(value.getDomainID()) && elem.toString().equals(value.toString())) find = true;  }  if (find && i == 0) { configurations.add(conf); continue; }  if (!find && i != 0 && configurations.contains(conf)) { configurations.remove(conf); iterator = configurations.iterator(); }  }  }  }  return configurations; } protected Collection filterConfigurations(VCR vcr) { if (getLVCAdapter().isNullVCR(vcr)) return null;  HashSet configurations = null; List<VCRAttribute> fixedAttributes = vcr.getAttributes(); if (fixedAttributes.size() == 0) return null;  for (int i = 0; i < fixedAttributes.size(); i++) { VCRAttribute attribute = fixedAttributes.get(i); VCRDomain domain = (VCRDomain)getDomain(attribute.getDomainID()); VCRBaseValue value = (VCRBaseValue)domain.getValue(attribute.getValueID()); if (configurations == null) { configurations = new HashSet(value.getConfigurations()); } else { configurations.retainAll(value.getConfigurations()); }  }  return configurations; } protected Collection filterSalesMakes(Collection configurations) { VCRDomain domain = (VCRDomain)getDomain("Make"); return filterDomain(configurations, domain); } protected Collection filterModels(Collection configurations) { VCRDomain domain = (VCRDomain)getDomain("Model"); return filterDomain(configurations, domain); } protected Collection filterModelYears(Collection configurations) { VCRDomain domain = (VCRDomain)getDomain("ModelYear"); return filterDomain(configurations, domain); } protected Collection filterEngines(VCR vcr, Collection configurations) { VCRDomain domain = (VCRDomain)getDomain("Engine"); VCRBaseValue transmission = extract(vcr, getDomain("Transmission")); if (transmission == null) return filterDomain(configurations, domain);  return filterDomain(vcr, configurations, domain, transmission); } protected Collection filterTransmissions(VCR vcr, Collection configurations) { VCRDomain domain = (VCRDomain)getDomain("Transmission"); VCRBaseValue engine = extract(vcr, getDomain("Engine")); if (engine == null) return filterDomain(configurations, domain);  return filterDomain(vcr, configurations, domain, engine); } protected Collection filterDomain(Collection configurations, VCRDomain domain) { Collection members = domain.getValues(); if (configurations == null) return members;  HashSet<VCRBaseValue> filter = new HashSet(); Iterator<VCRBaseValue> it = members.iterator(); while (it.hasNext()) { VCRBaseValue member = it.next(); if (!filter.contains(member) && match(configurations, member)) filter.add(member);  }  return filter; } protected Collection filterDomain(VCR vcr, Collection configurations, VCRDomain domain, VCRBaseValue constraint) { Collection members = domain.getValues(); if (configurations == null) return members;  HashSet<VCRBaseValue> filter = new HashSet(); Iterator<VCRBaseValue> it = members.iterator(); while (it.hasNext()) { VCRBaseValue member = it.next(); if (!filter.contains(member) && match(vcr, configurations, member, constraint)) filter.add(member);  }  return filter; } protected boolean match(Collection configurations, VCRBaseValue candidate) { Iterator<VCRConfiguration> it = configurations.iterator(); while (it.hasNext()) { VCRConfiguration configuration = it.next(); if (match(configuration, candidate)) return true;  }  return false; } protected boolean match(VCR vcr, Collection configurations, VCRBaseValue candidate, VCRBaseValue constraint) { Iterator<VCRConfiguration> it = configurations.iterator(); while (it.hasNext()) { VCRConfiguration configuration = it.next(); if (matchConfiguration(configuration, vcr)) if (match(configuration, candidate)) if (matchConstraint(configuration, constraint, candidate)) return true;    }  return false; } protected boolean match(VCRConfiguration configuration, VCRBaseValue candidate) { return (configuration.match((VCValue)candidate) || configuration.matchAssociation((VCValue)candidate)); } protected VCRBaseValue extract(VCR vcr, VCDomain domain) { if (getLVCAdapter().isNullVCR(vcr)) return null;  List<VCRAttribute> fixedAttributes = vcr.getAttributes(); if (fixedAttributes.size() == 0) return null;  int domainID = domain.getDomainID().intValue(); for (int i = 0; i < fixedAttributes.size(); i++) { VCRAttribute attribute = fixedAttributes.get(i); if (attribute.getDomainID() == domainID) return (VCRBaseValue)((VCRDomain)domain).getValue(attribute.getValueID());  }  return null; } public VCR toVCR(IConfiguration cfg) { VCR ret = null;
/* 1002 */     if (cfg != null) {
/* 1003 */       ret = (VCR)this.cfgToVCR.lookup(cfg);
/* 1004 */       if (ret == null) {
/* 1005 */         ret = toVCR((cfg != null) ? Collections.<IConfiguration>singletonList(cfg) : null);
/* 1006 */         this.cfgToVCR.store(cfg, ret);
/*      */       } 
/*      */     } 
/* 1009 */     return ret; } protected Collection filterSalesMakes(VCR vcr) { if (getLVCAdapter().isNullVCR(vcr)) { VCRDomain domain = (VCRDomain)getDomain("Make"); return domain.getValues(); }  HashSet makes = null; List<VCRAttribute> fixedAttributes = vcr.getAttributes(); if (fixedAttributes.size() == 0) return null;  for (int i = 0; i < fixedAttributes.size(); i++) { VCRAttribute attribute = fixedAttributes.get(i); VCRDomain domain = (VCRDomain)getDomain(attribute.getDomainID()); domain.getValue(attribute.getValueID()); if (makes == null); }  return makes; } protected Collection filterEngines(VCR vcr) { if (getLVCAdapter().isNullVCR(vcr)) { VCRDomain domain = (VCRDomain)getDomain("Engine"); return domain.getValues(); }  VCRDomain transmissions = (VCRDomain)getDomain("Transmission"); VCRBaseValue transmission = null; HashSet engines = null; List<VCRAttribute> fixedAttributes = vcr.getAttributes(); if (fixedAttributes.size() == 0) return null;  for (int i = 0; i < fixedAttributes.size(); i++) { VCRAttribute attribute = fixedAttributes.get(i); VCRDomain domain = (VCRDomain)getDomain(attribute.getDomainID()); VCRBaseValue value = (VCRBaseValue)domain.getValue(attribute.getValueID()); if (transmissions == domain) transmission = value;  if (engines == null); }  if (engines != null && transmission != null) return filterConstraint(vcr, engines, transmission);  return engines; } protected Collection filterConstraint(VCR vcr, Collection candidates, VCRBaseValue constraint) { Collection<VCRBaseValue> filter = new ArrayList(); Iterator<VCRBaseValue> it = candidates.iterator(); while (it.hasNext()) { VCRBaseValue candidate = it.next(); if (matchConstraint(vcr, constraint, candidate)) filter.add(candidate);  }  return filter; } protected boolean matchConstraint(VCR vcr, VCRBaseValue constraint, VCRBaseValue candidate) { List<VCRConfiguration> configurations = constraint.getConfigurations(); if (configurations == null) return false;  for (int i = 0; i < configurations.size(); i++) { VCRConfiguration configuration = configurations.get(i); if (matchConfiguration(configuration, vcr)) if (matchConfiguration(configuration, candidate)) if (matchConstraint(configuration, constraint, candidate)) return true;    }  return false; } protected boolean matchConfiguration(VCRConfiguration configuration, VCR vcr) { List<VCValue> elements = configuration.getElements(); if (elements == null) return false;  for (int i = 0; i < elements.size(); i++) { VCValue element = elements.get(i); if (!vcr.match(element)) return false;  }  return true; } protected boolean matchConfiguration(VCRConfiguration configuration, VCRBaseValue candidate) { List configurations = candidate.getConfigurations(); return (configurations == null) ? false : configurations.contains(configuration); } protected boolean matchConstraint(VCRConfiguration configuration, VCRBaseValue valueA, VCRBaseValue valueB) { VCRConstraintImpl vCRConstraintImpl = new VCRConstraintImpl(); vCRConstraintImpl.addElement((VCValue)valueA); vCRConstraintImpl.addElement((VCValue)valueB); return configuration.matchConstraint((VCRConstraint)vCRConstraintImpl); } protected List provideVehicleOptions(Integer esc) { Set<? extends VehicleOptionGroup> options = collectVehicleOptions(esc); if (options == null) return null;  List<VehicleOptionGroup> result = new ArrayList(); VCRDomain groups = (VCRDomain)getDomain("DDB Special Constraint Group"); VCRDomainImpl domain = (VCRDomainImpl)getDomain("DDB Special Constraint"); if (groups == null || options == null) return null;  Iterator<VCRValueImpl> it = groups.getValues().iterator(); while (it.hasNext()) { VCRValueImpl group = it.next(); List candidates = filterVehicleOptions(groups, domain, group, options); if (candidates != null) result.add(new VehicleOptionGroup(group, candidates));  }  result.addAll(options); return (result.size() == 0) ? null : result; } protected List filterVehicleOptions(VCRDomain groups, VCRDomainImpl domain, VCRValueImpl group, Set options) { List candidates = groups.getAssociations((VCValue)group, (VCDomain)domain); if (candidates == null) return null;  List<VCRValue> result = new ArrayList(); Iterator<VCRValue> it = candidates.iterator(); while (it.hasNext()) { VCRValue option = it.next(); if (options.contains(option)) { result.add(option); options.remove(option); }  }  return (result.size() == 0) ? null : result; } protected Set collectVehicleOptions(Integer esc) { Set result = new HashSet(); VCRDomainImpl domain = (VCRDomainImpl)getDomain("DDB Electronic System"); VCRValue system = domain.getValue(esc); if (system == null) return null;  List options = domain.getAssociations((VCValue)system, getDomain("DDB Special Constraint")); if (options != null) for (int i = 0; i < options.size(); i++) result.add(options.get(i));   options = domain.getAssociations((VCValue)system, (VCDomain)domain); if (options != null) for (int i = 0; i < options.size(); i++) result.add(options.get(i));   return (result.size() == 0) ? null : result; } protected static void dump(VC vcs, Collection values) { if (values == null) { log.info("no values"); return; }  Iterator<VCValue> it = values.iterator(); while (it.hasNext()) { VCValue value = it.next(); log.info(value.getValueID() + ": " + vcs.getLabel(value)); }  } private Connection getReadConnection() { Connection ret = this.connectionProvider.getConnection(); try { if (!ret.isReadOnly()) ret.setReadOnly(true);  if (!ret.getAutoCommit()) ret.setAutoCommit(false);  return ret; } catch (SQLException e) { throw new RuntimeException(e); }  } private void releaseConnection(Connection connection) { this.connectionProvider.releaseConnection(connection); } private Map getResolveMap_DomainName(Connection connection) { synchronized (this.SYNC_DTN) { if (this.domainIDToName == null) { Connection connection2 = (connection != null) ? connection : getReadConnection(); try { this.domainIDToName = (Map)JDBCUtil.executeQuery(connection2, new JDBCUtil.QueryCallback() { public void setParameters(PreparedStatement stmt) throws SQLException {} public String getQuery() { return "select a.domain_id, a.domain_name from vcrdomain a"; } public Object evaluateResult(ResultSet rs) throws SQLException { Map<Object, Object> ret = new HashMap<Object, Object>(); while (rs.next()) ret.put(Integer.valueOf(rs.getInt(1)), rs.getString(2));  return ret; } }
/*      */             ); } catch (SQLException e) { throw new RuntimeException(e); } finally { if (connection == null) releaseConnection(connection2);  }  }  return this.domainIDToName; }  } private String getDomainName(int domainID, Connection connection) { return (String)getResolveMap_DomainName(connection).get(Integer.valueOf(domainID)); } private Object toVCKey(int domainID, Connection connection) { String denotiation = Util.normalize(getDomainName(domainID, connection)); if ("make".equals(denotiation)) return VehicleConfigurationUtil.KEY_MAKE;  if ("model".equals(denotiation)) return VehicleConfigurationUtil.KEY_MODEL;  if ("modelyear".equals(denotiation)) return VehicleConfigurationUtil.KEY_MODELYEAR;  if ("engine".equals(denotiation)) return VehicleConfigurationUtil.KEY_ENGINE;  if ("transmission".equals(denotiation)) return VehicleConfigurationUtil.KEY_TRANSMISSION;  return null; } private Integer toDomainID(Object vcKey, Connection connection) { String key = Util.normalize(vcKey.toString()); for (Iterator<Map.Entry> iter = getResolveMap_DomainName(connection).entrySet().iterator(); iter.hasNext(); ) { Map.Entry entry = iter.next(); String domainName = Util.normalize(String.valueOf(entry.getValue())); if (Util.equals(key, domainName)) return (Integer)entry.getKey();  }  return null; } private ConfigurationManagement getCfgMngmnt() { return (ConfigurationManagement)VehicleConfigurationUtil.cfgManagement; } private ValueManagement getValueMngmnt() { return (ValueManagement)VehicleConfigurationUtil.valueManagement; } private String getValueDenotation(final int domainID, final int valueID, final int localeID, Connection connection) { try { String ret = (String)JDBCUtil.executeQuery(connection, new JDBCUtil.QueryCallback() { public void setParameters(PreparedStatement stmt) throws SQLException { stmt.setInt(1, domainID); stmt.setInt(2, valueID); stmt.setInt(3, localeID); } public String getQuery() { return "select a.label from vcrlabel a, vcrvalue b where a.label_id=b.value_label  and b.domain_id=? and b.value_id=? and a.language_id=?"; } public Object evaluateResult(ResultSet rs) throws SQLException { if (rs.next()) return rs.getString(1);  return null; } }
/*      */         ); if (ret == null && localeID == 0) ret = getValueDenotation(domainID, valueID, LocaleInfoProvider.getInstance().getLocale(Locale.ENGLISH).getLocaleID().intValue(), connection);  return ret; } catch (SQLException e) { throw new RuntimeException(e); }  } private Object toValue(int domainID, int valueID, Connection connection) { Object ret = this.valueResolve.lookup(Integer.valueOf(valueID)); if (ret == null) { String denotation = getValueDenotation(domainID, valueID, 0, connection); if (denotation == null) return null;  Object key = toVCKey(domainID, connection); ret = VehicleConfigurationUtil.toModelObject(key, denotation); this.valueResolve.store(Integer.valueOf(valueID), ret); }  return ret; } private Integer getValueID(final int domainID, String denotation, final int localeID, Connection connection) { try { final String search = denotation.replaceAll("\\s+", "%").toLowerCase(Locale.ENGLISH); Integer ret = (Integer)JDBCUtil.executeQuery(connection, new JDBCUtil.QueryCallback() {
/*      */             public void setParameters(PreparedStatement stmt) throws SQLException { stmt.setInt(1, domainID); stmt.setString(2, search); stmt.setInt(3, localeID); } public String getQuery() { return "select b.value_id from vcrlabel a, vcrvalue b where a.label_id=b.value_label  and b.domain_id=?  and LOWER(a.label) like ? and a.language_id=?"; } public Object evaluateResult(ResultSet rs) throws SQLException { if (rs.next()) return Integer.valueOf(rs.getInt(1));  return null; }
/* 1013 */           }); if (ret == null && localeID == 0) ret = getValueID(domainID, denotation, LocaleInfoProvider.getInstance().getLocale(Locale.ENGLISH).getLocaleID().intValue(), connection);  return ret; } catch (SQLException e) { throw new RuntimeException(e); }  } public Collection toConfiguration(VCR vcr) { Collection<IConfiguration> ret = new LinkedList(); if (vcr != null) { Connection connection = getReadConnection(); try { for (Iterator<VCRExpression> iterExpressions = vcr.getExpressions().iterator(); iterExpressions.hasNext(); ) { IConfiguration.Mutable cfg = (IConfiguration.Mutable)getCfgMngmnt().toMutableConfiguration(getCfgMngmnt().getEmptyConfiguration()); VCRExpression expression = iterExpressions.next(); for (Iterator<VCRTerm> iterTerms = expression.getTerms().iterator(); iterTerms.hasNext(); ) { VCRTerm term = iterTerms.next(); Object key = toVCKey(term.getDomainID(), connection); if (key == null) { log.warn("unable to resolve vcr (unknown domain), returning null"); return null; }  Collection<Object> values = new LinkedList(); for (Iterator<VCRAttribute> iterAttributes = term.getAttributes().iterator(); iterAttributes.hasNext(); ) { VCRAttribute attribute = iterAttributes.next(); Object value = toValue(attribute.getDomainID(), attribute.getValueID(), connection); if (value == null) { log.warn("unable to resolve value , returning null"); return null; }  values.add(value); }  cfg.setAttribute(key, getValueMngmnt().union(values)); }  ret.add(getCfgMngmnt().toImmutableConfiguration((IConfiguration)cfg)); }  } finally { releaseConnection(connection); }  }  return ret; } public VCR toVCR(List cfgs) { if (Util.isNullOrEmpty(cfgs)) {
/* 1014 */       return null;
/*      */     }
/* 1016 */     Connection connection = getReadConnection();
/*      */     try {
/* 1018 */       ILVCAdapter adapter = this.lvcr.getLVCAdapter();
/* 1019 */       VCR ret = adapter.makeVCR();
/*      */       
/* 1021 */       for (Iterator<IConfiguration> iterCfgs = cfgs.iterator(); iterCfgs.hasNext(); ) {
/* 1022 */         VCRExpression vcrExpression = adapter.makeExpression();
/* 1023 */         IConfiguration cfg = iterCfgs.next();
/* 1024 */         for (Iterator iterKeys = cfg.getKeys().iterator(); iterKeys.hasNext(); ) {
/* 1025 */           Collection values; Object key = iterKeys.next();
/* 1026 */           VCRTerm vcrTerm = adapter.makeTerm();
/* 1027 */           Integer domainID = toDomainID(key, connection);
/* 1028 */           if (domainID == null) {
/* 1029 */             log.warn("unable to determine domain id for key: " + String.valueOf(key) + " - returning null");
/* 1030 */             return null;
/*      */           } 
/*      */           
/*      */           try {
/* 1034 */             values = getValueMngmnt().resolve(cfg.getValue(key), null);
/* 1035 */           } catch (UnresolvableException e) {
/* 1036 */             throw new RuntimeException(e);
/*      */           } 
/* 1038 */           for (Iterator iterValues = values.iterator(); iterValues.hasNext(); ) {
/* 1039 */             Object value = iterValues.next();
/* 1040 */             Integer valueID = getValueID(domainID.intValue(), VehicleConfigurationUtil.toString_Normalized(value), 0, connection);
/* 1041 */             if (valueID == null) {
/* 1042 */               log.warn("unable to determine value id for value: " + String.valueOf(value) + "- returning null");
/* 1043 */               return null;
/*      */             } 
/* 1045 */             VCRAttribute vcrAttribute = adapter.makeAttribute(domainID.intValue(), valueID.intValue());
/* 1046 */             vcrTerm.add(vcrAttribute);
/*      */           } 
/* 1048 */           vcrExpression.add(vcrTerm);
/*      */         } 
/* 1050 */         ret.add(vcrExpression);
/*      */       } 
/* 1052 */       return ret;
/*      */     } finally {
/* 1054 */       releaseConnection(connection);
/*      */     }  }
/*      */ 
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\VCImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */