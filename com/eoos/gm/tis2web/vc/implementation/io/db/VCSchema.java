/*     */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.vin.VCRvinImpl;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.IVCRMapping;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRConstraint;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRvin;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VINDecoder;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VINStructure;
/*     */ import com.eoos.jdbc.ConnectionProvider;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class VCSchema {
/*  31 */   protected static Logger log = Logger.getLogger(VCSchema.class);
/*     */   
/*     */   public static final int LANGUAGE_INDEPENDENT = 0;
/*     */   
/*     */   public static final int UNDEFINED = -1;
/*     */   
/*     */   protected DBMS dbms;
/*     */   
/*     */   protected HashMap labels;
/*     */   
/*     */   protected HashMap domains;
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/*     */   protected VCRLabel getLabel(int labelID) {
/*  46 */     return (labelID == -1) ? null : (VCRLabel)this.labels.get(Integer.valueOf(labelID));
/*     */   }
/*     */   
/*     */   protected VCRDomain getDomain(int domainID) {
/*  50 */     return (VCRDomain)this.domains.get(Integer.valueOf(domainID));
/*     */   }
/*     */   
/*     */   protected VCRValue getValue(int domainID, int valueID) {
/*  54 */     return getDomain(domainID).getValue(valueID);
/*     */   }
/*     */   
/*     */   public VCSchema(final DBMS dbms) throws Exception {
/*  58 */     this.dbms = dbms;
/*     */     
/*  60 */     this.connectionProvider = ConNvent.create(new ConnectionProvider()
/*     */         {
/*     */           public void releaseConnection(Connection connection) {
/*  63 */             dbms.releaseConnection(connection);
/*     */           }
/*     */           
/*     */           public Connection getConnection() {
/*  67 */             return dbms.getConnection();
/*     */           }
/*     */         },  60000L);
/*     */     
/*  71 */     this.labels = loadLabels(dbms);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection getConnection() {
/*  77 */     Connection ret = this.connectionProvider.getConnection();
/*     */     try {
/*  79 */       ret.setAutoCommit(false);
/*  80 */       ret.setReadOnly(true);
/*  81 */     } catch (SQLException e) {
/*  82 */       throw new RuntimeException(e);
/*     */     } 
/*  84 */     return ret;
/*     */   }
/*     */   
/*     */   private void releaseConnection(Connection connection) {
/*  88 */     this.connectionProvider.releaseConnection(connection);
/*     */   }
/*     */   
/*     */   public VINDecoder createVINDecoder(VC vcs, String vinDecoderClassName) throws Exception {
/*  92 */     VINDecoder decoder = instantiateVINDecoder(vcs, vinDecoderClassName);
/*  93 */     loadVINStructure(vcs, decoder);
/*  94 */     loadVINData(decoder);
/*  95 */     return decoder;
/*     */   }
/*     */   
/*     */   public DBVersionInformation loadVersionInformation() throws Exception {
/*  99 */     Connection connection = getConnection();
/*     */     try {
/* 101 */       Statement stmt = null;
/* 102 */       ResultSet rs = null;
/*     */       
/*     */       try {
/* 105 */         stmt = connection.createStatement();
/* 106 */         rs = stmt.executeQuery(this.dbms.getSQL("SELECT * FROM RELEASE"));
/* 107 */         if (rs.next()) {
/* 108 */           return new DBVersionInformation(rs.getString("RELEASE_ID"), rs.getDate("RELEASE_DATE"), rs.getString("DESCRIPTION"), rs.getString("VERSION"));
/*     */         }
/* 110 */         return null;
/*     */       }
/* 112 */       catch (Exception e) {
/* 113 */         log.error("loading version information failed.");
/* 114 */         throw e;
/*     */       } finally {
/*     */         try {
/* 117 */           if (rs != null) {
/* 118 */             rs.close();
/*     */           }
/* 120 */           if (stmt != null) {
/* 121 */             stmt.close();
/*     */           }
/* 123 */         } catch (Exception x) {}
/*     */       } 
/*     */     } finally {
/*     */       
/* 127 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map loadDomains() throws Exception {
/* 132 */     this.domains = new HashMap<Object, Object>();
/*     */ 
/*     */     
/* 135 */     Connection connection = getConnection();
/*     */     try {
/* 137 */       Statement stmt = null;
/* 138 */       ResultSet rs = null;
/*     */       try {
/* 140 */         stmt = connection.createStatement();
/* 141 */         rs = stmt.executeQuery(this.dbms.getSQL("SELECT DOMAIN_ID, DOMAIN_NAME, DOMAIN_LABEL, LANGUAGE_DEPENDENT FROM VCRDOMAIN"));
/* 142 */         while (rs.next()) {
/* 143 */           VCRDomainImpl instance; String domain = rs.getString("DOMAIN_NAME");
/* 144 */           if (domain != null) {
/* 145 */             domain = domain.trim();
/*     */           }
/* 147 */           if ("Engine".equals(domain)) {
/* 148 */             instance = new VCREngineDomain(rs.getInt("DOMAIN_ID"), domain, getLabel(rs.getInt("DOMAIN_LABEL")), rs.getBoolean("LANGUAGE_DEPENDENT"));
/*     */           } else {
/* 150 */             instance = new VCRDomainImpl(rs.getInt("DOMAIN_ID"), domain, getLabel(rs.getInt("DOMAIN_LABEL")), rs.getBoolean("LANGUAGE_DEPENDENT"));
/*     */           } 
/* 152 */           this.domains.put(instance.getDomainID(), instance);
/*     */         } 
/* 154 */       } catch (Exception e) {
/* 155 */         log.error("loading domains failed.");
/* 156 */         throw e;
/*     */       } finally {
/*     */         try {
/* 159 */           if (rs != null) {
/* 160 */             rs.close();
/*     */           }
/* 162 */           if (stmt != null) {
/* 163 */             stmt.close();
/*     */           }
/* 165 */         } catch (Exception x) {}
/*     */       } 
/*     */       
/* 168 */       Iterator<VCRDomainImpl> domainIterator = this.domains.values().iterator();
/* 169 */       while (domainIterator.hasNext()) {
/* 170 */         VCRDomainImpl instance = domainIterator.next();
/* 171 */         loadDomain(connection, instance);
/*     */       } 
/* 173 */       return this.domains;
/*     */     } finally {
/* 175 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadMappings(VC vcs) throws Exception {
/* 184 */     Connection connection = getConnection();
/*     */     try {
/* 186 */       Statement stmt = null;
/* 187 */       ResultSet rs = null;
/*     */       try {
/* 189 */         stmt = connection.createStatement();
/* 190 */         rs = stmt.executeQuery(this.dbms.getSQL("SELECT DOMAIN_ID, VALUE_ID, DOMAIN_RELID, VALUE_RELID FROM VCRMAPPING"));
/* 191 */         while (rs.next()) {
/* 192 */           int mtype = 0;
/* 193 */           int domainID = rs.getInt("DOMAIN_ID");
/* 194 */           if (domainID < 0) {
/* 195 */             domainID = -domainID;
/* 196 */             mtype = 1;
/*     */           } 
/* 198 */           VCRValue value = getValue(domainID, rs.getInt("VALUE_ID"));
/* 199 */           if (value == null) {
/*     */             continue;
/*     */           }
/* 202 */           VCRValue mappedValue = getValue(rs.getInt("DOMAIN_RELID"), rs.getInt("VALUE_RELID"));
/* 203 */           if (mappedValue == null) {
/*     */             continue;
/*     */           }
/* 206 */           IVCRMapping instance = new VCRMapping(mtype, (VCValue)value, (VCValue)mappedValue);
/* 207 */           VCRDomain valueDomain = (VCRDomain)vcs.getDomain(domainID);
/* 208 */           if (valueDomain == null) {
/* 209 */             log.warn("unknown domain-id '" + rs.getInt("DOMAIN_ID") + "'");
/*     */             continue;
/*     */           } 
/* 212 */           valueDomain.add(instance);
/*     */         } 
/* 214 */       } catch (Exception e) {
/* 215 */         log.error("loading mappings failed.");
/* 216 */         throw e;
/*     */       } finally {
/*     */         try {
/* 219 */           if (rs != null) {
/* 220 */             rs.close();
/*     */           }
/* 222 */           if (stmt != null) {
/* 223 */             stmt.close();
/*     */           }
/* 225 */         } catch (Exception x) {}
/*     */       } 
/*     */     } finally {
/*     */       
/* 229 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map loadConfigurations(VC vcs) throws Exception {
/* 234 */     HashMap<Object, Object> configurations = new HashMap<Object, Object>();
/*     */     
/* 236 */     Connection connection = getConnection();
/*     */     try {
/* 238 */       Statement stmt = null;
/*     */       
/* 240 */       ResultSet rs = null;
/*     */       try {
/* 242 */         VCRConfiguration configuration = null;
/* 243 */         int config_id = -1;
/* 244 */         stmt = connection.createStatement();
/* 245 */         rs = stmt.executeQuery(this.dbms.getSQL("SELECT CONFIG_ID, SEQ_NO, DOMAIN_ID, VALUE_ID FROM VCRCONFIG ORDER BY CONFIG_ID, SEQ_NO, DOMAIN_ID"));
/* 246 */         while (rs.next()) {
/* 247 */           config_id = rs.getInt("CONFIG_ID");
/* 248 */           if (configuration == null || configuration.getConfigID().intValue() != config_id) {
/* 249 */             configuration = new VCRConfigurationImpl(config_id);
/* 250 */             configurations.put(configuration.getConfigID(), configuration);
/*     */           } 
/* 252 */           configuration.addElement(getValue(rs.getInt("DOMAIN_ID"), rs.getInt("VALUE_ID")));
/*     */         } 
/* 254 */       } catch (Exception e) {
/* 255 */         log.error("loading configurations failed.");
/* 256 */         throw e;
/*     */       } finally {
/*     */         try {
/* 259 */           if (rs != null) {
/* 260 */             rs.close();
/*     */           }
/* 262 */           if (stmt != null) {
/* 263 */             stmt.close();
/*     */           }
/* 265 */         } catch (Exception x) {}
/*     */       } 
/*     */ 
/*     */       
/* 269 */       registerConfigurations(configurations);
/* 270 */       loadAssociations(connection, configurations);
/* 271 */       loadConstraints(connection, vcs, configurations);
/*     */     } finally {
/* 273 */       releaseConnection(connection);
/*     */     } 
/* 275 */     return configurations;
/*     */   }
/*     */   
/*     */   protected void registerConfigurations(HashMap configurations) {
/* 279 */     Iterator<VCRConfigurationImpl> cit = configurations.values().iterator();
/* 280 */     while (cit.hasNext()) {
/* 281 */       VCRConfigurationImpl configuration = cit.next();
/* 282 */       List<VCRBaseValue> elements = configuration.getElements();
/* 283 */       for (int i = 0; i < elements.size(); i++) {
/* 284 */         VCRBaseValue element = elements.get(i);
/* 285 */         element.addConfiguration((VCConfiguration)configuration);
/*     */       } 
/*     */     } 
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
/*     */   protected void registerConfigurations(VC vcs, HashMap configurations) {
/* 305 */     VCDomain makes = vcs.getDomain("Make");
/* 306 */     Iterator<VCRConfigurationImpl> cit = configurations.values().iterator();
/* 307 */     while (cit.hasNext()) {
/* 308 */       VCRConfigurationImpl configuration = cit.next();
/* 309 */       VCRMake make = (VCRMake)configuration.getElement(makes);
/* 310 */       make.addConfiguration((VCConfiguration)configuration);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadAssociations(Connection connection, HashMap configurations) throws Exception {
/* 316 */     Statement stmt = null;
/* 317 */     ResultSet rs = null;
/*     */     try {
/* 319 */       stmt = connection.createStatement();
/* 320 */       rs = stmt.executeQuery(this.dbms.getSQL("SELECT CONFIG_ID, DOMAIN_ID, VALUE_ID FROM VCRASSOC ORDER BY CONFIG_ID, DOMAIN_ID"));
/* 321 */       while (rs.next()) {
/* 322 */         VCRConfigurationImpl configuration = (VCRConfigurationImpl)configurations.get(Integer.valueOf(rs.getInt("CONFIG_ID")));
/* 323 */         VCRValue value = getValue(rs.getInt("DOMAIN_ID"), rs.getInt("VALUE_ID"));
/* 324 */         if (configuration == null || value == null) {
/*     */           continue;
/*     */         }
/* 327 */         configuration.addAssociation(value);
/* 328 */         if (value instanceof VCRBaseValue) {
/* 329 */           ((VCRBaseValue)value).addConfiguration((VCConfiguration)configuration);
/*     */         }
/*     */       } 
/* 332 */     } catch (Exception e) {
/* 333 */       log.error("loading associations failed.");
/* 334 */       throw e;
/*     */     } finally {
/*     */       try {
/* 337 */         if (rs != null) {
/* 338 */           rs.close();
/*     */         }
/* 340 */         if (stmt != null) {
/* 341 */           stmt.close();
/*     */         }
/* 343 */       } catch (Exception x) {}
/*     */     } 
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
/*     */   protected void loadConstraints(Connection connection, VC vcs, HashMap configurations) throws Exception {
/* 362 */     Statement stmt = null;
/* 363 */     ResultSet rs = null;
/* 364 */     VCDomain vehicles = vcs.getDomain("Vehicle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 371 */       stmt = connection.createStatement();
/* 372 */       rs = stmt.executeQuery(this.dbms.getSQL("SELECT CONSTRAINT_ID, SEQ_NO, DOMAIN_ID, VALUE_ID FROM VCRCONSTRAINT ORDER BY CONSTRAINT_ID, SEQ_NO, DOMAIN_ID"));
/* 373 */       int constraint_id = -1;
/* 374 */       VCRConstraint constraint = null;
/* 375 */       while (rs.next()) {
/* 376 */         constraint_id = rs.getInt("CONSTRAINT_ID");
/* 377 */         if (constraint == null || constraint.getConstraintID().intValue() != constraint_id) {
/* 378 */           constraint = new VCRConstraintImpl(constraint_id);
/*     */         }
/* 380 */         VCRValue value = getValue(rs.getInt("DOMAIN_ID"), rs.getInt("VALUE_ID"));
/* 381 */         if (constraint == null || value == null) {
/*     */           continue;
/*     */         }
/* 384 */         constraint.addElement((VCValue)value);
/* 385 */         if (value.getDomainID().equals(vehicles.getDomainID())) {
/* 386 */           VCRConfiguration configuration = (VCRConfiguration)configurations.get(value.getValueID());
/* 387 */           if (configuration == null) {
/*     */             continue;
/*     */           }
/* 390 */           configuration.addConstraint(constraint);
/*     */         } 
/*     */       } 
/* 393 */     } catch (Exception e) {
/* 394 */       log.error("loading constraints failed.");
/* 395 */       throw e;
/*     */     } finally {
/*     */       try {
/* 398 */         if (rs != null) {
/* 399 */           rs.close();
/*     */         }
/* 401 */         if (stmt != null) {
/* 402 */           stmt.close();
/*     */         }
/* 404 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected HashMap loadLabels(DBMS dbms) throws Exception {
/* 411 */     HashMap<Object, Object> labels = new HashMap<Object, Object>();
/*     */     
/* 413 */     Connection connection = getConnection();
/*     */     try {
/* 415 */       Statement stmt = null;
/* 416 */       ResultSet rs = null;
/*     */       try {
/* 418 */         stmt = connection.createStatement();
/* 419 */         rs = stmt.executeQuery(dbms.getSQL("SELECT LANGUAGE_ID, LABEL_ID, LABEL FROM VCRLABEL"));
/* 420 */         while (rs.next()) {
/* 421 */           int localeID = rs.getInt("LANGUAGE_ID");
/* 422 */           Integer labelID = Integer.valueOf(rs.getInt("LABEL_ID"));
/* 423 */           if (localeID == 0) {
/* 424 */             String str = rs.getString("LABEL");
/* 425 */             if (str != null) {
/* 426 */               str = str.trim();
/*     */             }
/* 428 */             labels.put(labelID, new VCLabel(labelID, str)); continue;
/*     */           } 
/* 430 */           VCLocaleLabel label = (VCLocaleLabel)labels.get(labelID);
/* 431 */           if (label == null) {
/* 432 */             label = new VCLocaleLabel(labelID);
/* 433 */             labels.put(labelID, label);
/*     */           } 
/* 435 */           String value = rs.getString("LABEL");
/* 436 */           if (value != null) {
/* 437 */             value = value.trim();
/*     */           }
/* 439 */           label.add(Integer.valueOf(localeID), value);
/*     */         }
/*     */       
/* 442 */       } catch (Exception e) {
/* 443 */         log.error("loading labels failed.");
/* 444 */         throw e;
/*     */       } finally {
/*     */         try {
/* 447 */           if (rs != null) {
/* 448 */             rs.close();
/*     */           }
/* 450 */           if (stmt != null) {
/* 451 */             stmt.close();
/*     */           }
/* 453 */         } catch (Exception x) {}
/*     */       } 
/*     */     } finally {
/*     */       
/* 457 */       releaseConnection(connection);
/*     */     } 
/* 459 */     return labels;
/*     */   }
/*     */   
/*     */   protected void loadDomain(Connection connection, VCRDomainImpl domain) throws Exception {
/* 463 */     PreparedStatement stmt = null;
/* 464 */     ResultSet rs = null;
/*     */     
/*     */     try {
/* 467 */       stmt = connection.prepareStatement(this.dbms.getSQL("SELECT VALUE_ID, VALUE_LABEL FROM VCRVALUE WHERE DOMAIN_ID = ?"));
/* 468 */       stmt.setInt(1, domain.getDomainID().intValue());
/* 469 */       rs = stmt.executeQuery();
/* 470 */       while (rs.next()) {
/* 471 */         VCRValue instance = makeValue(domain, rs.getInt("VALUE_ID"), getLabel(rs.getInt("VALUE_LABEL")));
/* 472 */         domain.add(instance);
/*     */       } 
/* 474 */     } catch (Exception e) {
/* 475 */       log.error("loading domain values failed.");
/* 476 */       throw e;
/*     */     } finally {
/*     */       try {
/* 479 */         if (rs != null) {
/* 480 */           rs.close();
/*     */         }
/* 482 */         if (stmt != null) {
/* 483 */           stmt.close();
/*     */         }
/* 485 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected VCRValue makeValue(VCRDomainImpl domain, int value_id, VCRLabel label) {
/* 491 */     if (domain.getDomainName().equals("Make"))
/* 492 */       return new VCRMake(domain, value_id, label); 
/* 493 */     if (domain.getDomainName().equals("ModelYear"))
/* 494 */       return new VCRModelYear(domain, value_id, label); 
/* 495 */     if (domain.getDomainName().equals("Model"))
/* 496 */       return new VCRModel(domain, value_id, label); 
/* 497 */     if (domain.getDomainName().equals("Engine"))
/* 498 */       return new VCREngine(domain, value_id, label); 
/* 499 */     if (domain.getDomainName().equals("Transmission")) {
/* 500 */       return new VCRTransmission(domain, value_id, label);
/*     */     }
/* 502 */     return new VCRValueImpl(domain, value_id, label);
/*     */   }
/*     */ 
/*     */   
/*     */   protected VINDecoder instantiateVINDecoder(VC vcs, String vinDecoderClassName) throws Exception {
/*     */     try {
/* 508 */       Class<?> vinDecoderClass = Class.forName(vinDecoderClassName);
/* 509 */       Constructor<?> constructor = vinDecoderClass.getConstructor(new Class[] { VC.class });
/* 510 */       return (VINDecoder)constructor.newInstance(new Object[] { vcs });
/* 511 */     } catch (Exception e) {
/* 512 */       log.error("vin decoder instantiation failed.");
/* 513 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadVINStructure(VC vcs, VINDecoder decoder) throws Exception {
/* 518 */     Connection connection = getConnection();
/*     */     try {
/* 520 */       Statement stmt = null;
/* 521 */       ResultSet rs = null;
/*     */       try {
/* 523 */         stmt = connection.createStatement();
/* 524 */         rs = stmt.executeQuery(this.dbms.getSQL("SELECT STRUCTURE_ID, MAKE_ID, MODEL_YEAR_ID, WMI_ID, VIN_PATTERN, DOMAIN_ID, POSITION_FROM, POSITION_TO FROM VINSTRUCTURE ORDER BY STRUCTURE_ID, MAKE_ID, MODEL_YEAR_ID, WMI_ID, DOMAIN_ID"));
/* 525 */         while (rs.next()) {
/* 526 */           Integer structureID = Integer.valueOf(rs.getInt("STRUCTURE_ID"));
/* 527 */           VCRValue make = ((VCRDomain)vcs.getDomain("Make")).getValue(rs.getInt("MAKE_ID"));
/* 528 */           VCRValue modelYear = ((VCRDomain)vcs.getDomain("VIN ModelYear")).getValue(rs.getInt("MODEL_YEAR_ID"));
/* 529 */           VCRValue wmi = ((VCRDomain)vcs.getDomain("VIN WMI")).getValue(rs.getInt("WMI_ID"));
/* 530 */           String filterVIN = rs.getString("VIN_PATTERN");
/* 531 */           VINStructure structure = decoder.getStructure(structureID, (VCValue)make, (VCValue)modelYear, (VCValue)wmi, filterVIN);
/* 532 */           structure.add(vcs.getDomain(rs.getInt("DOMAIN_ID")), rs.getInt("POSITION_FROM"), rs.getInt("POSITION_TO"));
/*     */         } 
/* 534 */       } catch (Exception e) {
/* 535 */         log.error("loading vin structures failed.", e);
/* 536 */         loadLegacyVINStructure(connection, vcs, decoder);
/*     */       } finally {
/*     */         try {
/* 539 */           if (rs != null) {
/* 540 */             rs.close();
/*     */           }
/* 542 */           if (stmt != null) {
/* 543 */             stmt.close();
/*     */           }
/* 545 */         } catch (Exception x) {}
/*     */       } 
/*     */     } finally {
/*     */       
/* 549 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadLegacyVINStructure(Connection connection, VC vcs, VINDecoder decoder) throws Exception {
/* 554 */     Statement stmt = null;
/* 555 */     ResultSet rs = null;
/*     */     try {
/* 557 */       stmt = connection.createStatement();
/* 558 */       rs = stmt.executeQuery(this.dbms.getSQL("SELECT STRUCTURE_ID, MAKE_ID, MODEL_YEAR_ID, WMI_ID, DOMAIN_ID, POSITION_FROM, POSITION_TO FROM VINSTRUCTURE ORDER BY STRUCTURE_ID, MAKE_ID, MODEL_YEAR_ID, WMI_ID, DOMAIN_ID"));
/* 559 */       while (rs.next()) {
/* 560 */         Integer structureID = Integer.valueOf(rs.getInt("STRUCTURE_ID"));
/* 561 */         VCRValue make = ((VCRDomain)vcs.getDomain("Make")).getValue(rs.getInt("MAKE_ID"));
/* 562 */         VCRValue modelYear = ((VCRDomain)vcs.getDomain("VIN ModelYear")).getValue(rs.getInt("MODEL_YEAR_ID"));
/* 563 */         VCRValue wmi = ((VCRDomain)vcs.getDomain("VIN WMI")).getValue(rs.getInt("WMI_ID"));
/* 564 */         String filterVIN = "#";
/* 565 */         VINStructure structure = decoder.getStructure(structureID, (VCValue)make, (VCValue)modelYear, (VCValue)wmi, filterVIN);
/* 566 */         structure.add(vcs.getDomain(rs.getInt("DOMAIN_ID")), rs.getInt("POSITION_FROM"), rs.getInt("POSITION_TO"));
/*     */       } 
/* 568 */     } catch (Exception e) {
/* 569 */       log.error("loading vin structures failed.");
/* 570 */       throw e;
/*     */     } finally {
/*     */       try {
/* 573 */         if (rs != null) {
/* 574 */           rs.close();
/*     */         }
/* 576 */         if (stmt != null) {
/* 577 */           stmt.close();
/*     */         }
/* 579 */       } catch (Exception x) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadVINData(VINDecoder decoder) throws Exception {
/* 585 */     Connection connection = getConnection();
/*     */     
/*     */     try {
/* 588 */       Statement stmt = null;
/* 589 */       ResultSet rs = null;
/*     */       try {
/* 591 */         stmt = connection.createStatement();
/* 592 */         rs = stmt.executeQuery(this.dbms.getSQL("SELECT STRUCTURE_ID, VIN_ELEMENT, DOMAIN_ID, VALUE_ID FROM VCRVIN"));
/* 593 */         while (rs.next()) {
/* 594 */           Integer structureID = Integer.valueOf(rs.getInt("STRUCTURE_ID"));
/* 595 */           VINStructure structure = decoder.lookupStructure(structureID);
/* 596 */           if (structure == null)
/* 597 */             log.error(structureID + "-" + rs.getString("VIN_ELEMENT") + "-" + rs.getInt("DOMAIN_ID") + ":" + rs.getInt("VALUE_ID")); 
/* 598 */           VCRValue value = getValue(rs.getInt("DOMAIN_ID"), rs.getInt("VALUE_ID"));
/* 599 */           String element = rs.getString("VIN_ELEMENT");
/* 600 */           if (element != null) {
/* 601 */             element = element.trim();
/*     */           }
/* 603 */           structure.add((VCRvin)new VCRvinImpl(structureID, element, (VCValue)value));
/*     */         } 
/* 605 */       } catch (Exception e) {
/* 606 */         log.error("loading vin data failed.");
/* 607 */         throw e;
/*     */       } finally {
/*     */         try {
/* 610 */           if (rs != null) {
/* 611 */             rs.close();
/*     */           }
/* 613 */           if (stmt != null) {
/* 614 */             stmt.close();
/*     */           }
/* 616 */         } catch (Exception x) {}
/*     */       } 
/*     */     } finally {
/*     */       
/* 620 */       releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCSchema.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */