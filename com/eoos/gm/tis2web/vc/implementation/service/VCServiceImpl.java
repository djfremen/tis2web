/*     */ package com.eoos.gm.tis2web.vc.implementation.service;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.marker.Configurable;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.VCImpl;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.ui.html.home.VehicleOptionsDialog;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.InvalidVINException;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRConstraint;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Engine;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Model;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Transmission;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class VCServiceImpl
/*     */   implements Configurable
/*     */ {
/*  56 */   private static final Logger log = Logger.getLogger(VCServiceImpl.class);
/*     */   
/*  58 */   private static final VCDomain DOMAIN_MAKE = new VCDomain()
/*     */     {
/*     */       public String getDomainName() {
/*  61 */         return "Make";
/*     */       }
/*     */       
/*     */       public Integer getDomainID() {
/*  65 */         return Integer.valueOf(2);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  70 */   private static final VCDomain DOMAIN_MODEL = new VCDomain()
/*     */     {
/*     */       public String getDomainName() {
/*  73 */         return "Model";
/*     */       }
/*     */       
/*     */       public Integer getDomainID() {
/*  77 */         return Integer.valueOf(6);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  82 */   private static final VCDomain DOMAIN_MODELYEAR = new VCDomain()
/*     */     {
/*     */       public String getDomainName() {
/*  85 */         return "Modelyear";
/*     */       }
/*     */       
/*     */       public Integer getDomainID() {
/*  89 */         return Integer.valueOf(3);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  94 */   private static final VCDomain DOMAIN_ENGINE = new VCDomain()
/*     */     {
/*     */       public String getDomainName() {
/*  97 */         return "engine";
/*     */       }
/*     */       
/*     */       public Integer getDomainID() {
/* 101 */         return Integer.valueOf(7);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 106 */   private static final VCDomain DOMAIN_TRANSMISSION = new VCDomain()
/*     */     {
/*     */       public String getDomainName() {
/* 109 */         return "transmission";
/*     */       }
/*     */       
/*     */       public Integer getDomainID() {
/* 113 */         return Integer.valueOf(8);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 118 */   private final Object SYNC_CFGS = new Object();
/*     */   
/* 120 */   private Set cfgs = null;
/*     */   
/* 122 */   private final Object SYNC_VC = new Object();
/*     */   
/* 124 */   private VCImpl vc = null;
/*     */   
/*     */   private VINResolver cacheFacade;
/*     */   
/*     */   private Configuration cfg;
/*     */   
/*     */   private ILVCAdapter.Retrieval lvcr;
/*     */   
/*     */   public VCServiceImpl(Configuration cfg, ILVCAdapter.Retrieval lvcr) {
/* 133 */     this.cfg = cfg;
/* 134 */     this.lvcr = lvcr;
/* 135 */     this.cacheFacade = (VINResolver)ReflectionUtil.createCachingProxy(new VINResolver()
/*     */         {
/*     */           public Set resolveVIN(VIN vin) throws VIN.InvalidVINException {
/* 138 */             return VCServiceImpl.this._resolveVIN(vin);
/*     */           }
/*     */         },  Tis2webUtil.createStdCache(), ReflectionUtil.CachingProxyCallback.STD);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 145 */     return "vc";
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
/*     */   public List getSalesmakeDomain() {
/* 159 */     return getVCRValues("Make");
/*     */   }
/*     */   
/*     */   public List getModelDomain() {
/* 163 */     return getVCRValues("Model");
/*     */   }
/*     */   
/*     */   public List getEngineDomain() {
/* 167 */     return getVCRValues("Engine");
/*     */   }
/*     */   
/*     */   public List getTransmissionDomain() {
/* 171 */     return getVCRValues("Transmission");
/*     */   }
/*     */   
/*     */   protected synchronized List getVCRValues(String domainID) {
/* 175 */     Collection<?> data = null;
/*     */     
/* 177 */     VCR filter = VCR.NULL;
/* 178 */     VC vc = getVC();
/* 179 */     if (domainID.equalsIgnoreCase("Make")) {
/* 180 */       data = vc.getSalesMakes();
/* 181 */     } else if (domainID.equalsIgnoreCase("Model")) {
/* 182 */       data = vc.getModels(vc.getConfigurations(filter));
/* 183 */     } else if (domainID.equalsIgnoreCase("ModelYear")) {
/* 184 */       data = vc.getModelYears(vc.getConfigurations(filter));
/* 185 */     } else if (domainID.equalsIgnoreCase("Engine")) {
/* 186 */       data = vc.getEngines(filter, vc.getConfigurations(filter));
/* 187 */     } else if (domainID.equalsIgnoreCase("Transmission")) {
/* 188 */       data = vc.getTransmissions(filter, vc.getConfigurations(filter));
/*     */     } 
/* 190 */     return new ArrayList(data);
/*     */   }
/*     */   
/*     */   public ModuleInformation getModuleInformation() {
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupported(String salesmake) {
/* 203 */     return true;
/*     */   }
/*     */   
/*     */   public Object getIdentifier() {
/* 207 */     return "vcservice";
/*     */   }
/*     */   
/*     */   public VC getVC() {
/* 211 */     synchronized (this.SYNC_VC) {
/* 212 */       if (this.vc == null) {
/*     */         
/*     */         try {
/* 215 */           DatabaseLink databaseLink = DatabaseLink.openDatabase(this.cfg, "db");
/* 216 */           this.vc = new VCImpl(this.cfg, (IDatabaseLink)databaseLink, this.lvcr);
/* 217 */         } catch (Exception e) {
/* 218 */           log.error("unable to init vc - exception:" + e);
/* 219 */           throw new ExceptionWrapper(e);
/*     */         } 
/*     */       }
/* 222 */       return (VC)this.vc;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getVehicleOptionsDialog(String sessionID, List esc, ILVCAdapter.ReturnHandler returnHandler, ILVCAdapter adapter) {
/* 227 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 228 */     VehicleOptionsDialog dialog = new VehicleOptionsDialog(context, adapter);
/* 229 */     dialog.init(returnHandler, esc);
/* 230 */     return dialog.getHtmlCode(null);
/*     */   }
/*     */   
/*     */   public Set getConfigurations() {
/* 234 */     synchronized (this.SYNC_CFGS) {
/* 235 */       if (this.cfgs == null) {
/* 236 */         this.cfgs = new HashSet();
/*     */         try {
/* 238 */           Collection tmp = getVC().getConfigurations();
/* 239 */           for (Iterator<VCConfiguration> iter = tmp.iterator(); iter.hasNext(); ) {
/* 240 */             VCConfiguration cfg = iter.next();
/* 241 */             Make make = VehicleConfigurationUtil.toMake(String.valueOf(cfg.getElement(DOMAIN_MAKE)));
/* 242 */             Model model = VehicleConfigurationUtil.toModel(String.valueOf(cfg.getElement(DOMAIN_MODEL)));
/* 243 */             Modelyear modelyear = VehicleConfigurationUtil.toModelyear(String.valueOf(cfg.getElement(DOMAIN_MODELYEAR)));
/* 244 */             for (Iterator<VCRConstraint> iter2 = cfg.getConstraints().iterator(); iter2.hasNext(); ) {
/* 245 */               VCRConstraint constraint = iter2.next();
/* 246 */               Engine engine = VehicleConfigurationUtil.toEngine(String.valueOf(constraint.getElement(DOMAIN_ENGINE)));
/* 247 */               Transmission transmission = VehicleConfigurationUtil.toTransmission(String.valueOf(constraint.getElement(DOMAIN_TRANSMISSION)));
/* 248 */               this.cfgs.add(VehicleConfigurationUtil.createCfg(make, model, modelyear, engine, transmission));
/*     */             } 
/*     */           } 
/* 251 */         } catch (Exception e) {
/* 252 */           log.error("unable to init configurations, returning empty collection - exception:" + e, e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     return this.cfgs;
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 261 */     return VehicleConfigurationUtil.KEY_SET;
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 265 */     return 0L;
/*     */   }
/*     */   
/*     */   public Set resolveVIN(VIN vin) throws VIN.InvalidVINException {
/* 269 */     return this.cacheFacade.resolveVIN(vin);
/*     */   }
/*     */ 
/*     */   
/*     */   private Set _resolveVIN(VIN vin) throws VIN.InvalidVINException {
/* 274 */     Set<IConfiguration> ret = new LinkedHashSet();
/*     */     try {
/* 276 */       List tmp = getVC().getVINDecoder().decode(vin.toString());
/* 277 */       for (Iterator<List> iter1 = tmp.iterator(); iter1.hasNext(); ) {
/* 278 */         List tmp2 = iter1.next();
/* 279 */         String make = null;
/* 280 */         String model = null;
/* 281 */         String modelyear = null;
/* 282 */         String engine = null;
/* 283 */         String transmission = null;
/* 284 */         for (Iterator iter2 = tmp2.iterator(); iter2.hasNext(); ) {
/* 285 */           Object attribute = iter2.next();
/* 286 */           if (attribute instanceof VCConfiguration) {
/* 287 */             VCConfiguration vcc = (VCConfiguration)attribute;
/* 288 */             List elements = vcc.getElements();
/* 289 */             for (int j = 0; j < elements.size(); j++) {
/* 290 */               Object element = elements.get(j);
/* 291 */               if (element instanceof com.eoos.gm.tis2web.vc.service.cai.VCMake) {
/* 292 */                 make = element.toString();
/* 293 */               } else if (element instanceof com.eoos.gm.tis2web.vc.service.cai.VCModel) {
/* 294 */                 model = element.toString();
/* 295 */               } else if (element instanceof com.eoos.gm.tis2web.vc.service.cai.VCModelYear) {
/* 296 */                 modelyear = element.toString();
/*     */               } 
/*     */             }  continue;
/* 299 */           }  if (attribute instanceof com.eoos.gm.tis2web.vc.service.cai.VCMake) {
/* 300 */             make = attribute.toString(); continue;
/* 301 */           }  if (attribute instanceof com.eoos.gm.tis2web.vc.service.cai.VCModel) {
/* 302 */             model = attribute.toString(); continue;
/* 303 */           }  if (attribute instanceof com.eoos.gm.tis2web.vc.service.cai.VCModelYear) {
/* 304 */             modelyear = attribute.toString(); continue;
/* 305 */           }  if (attribute instanceof com.eoos.gm.tis2web.vc.service.cai.VCEngine) {
/* 306 */             engine = attribute.toString(); continue;
/* 307 */           }  if (attribute instanceof com.eoos.gm.tis2web.vc.service.cai.VCTransmission) {
/* 308 */             transmission = attribute.toString();
/*     */           }
/*     */         } 
/* 311 */         ret.add(VehicleConfigurationUtil.createVC(make, model, modelyear, engine, transmission));
/*     */       } 
/* 313 */       return ret;
/* 314 */     } catch (InvalidVINException e) {
/* 315 */       throw new VIN.InvalidVINException();
/* 316 */     } catch (Exception e) {
/* 317 */       log.warn("unable to resolve vin: " + String.valueOf(vin) + " - exception:" + e, e);
/* 318 */       return Collections.EMPTY_SET;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 324 */     getVC().getConfigurations();
/*     */   }
/*     */   
/*     */   public DBVersionInformation getDatabaseInfo() {
/* 328 */     return getVC().getVersionInfo();
/*     */   }
/*     */   
/*     */   public Collection toConfiguration(VCR vcr) {
/* 332 */     return getVC().toConfiguration(vcr);
/*     */   }
/*     */   
/*     */   public VCR toVCR(IConfiguration cfg) {
/* 336 */     return getVC().toVCR(cfg);
/*     */   }
/*     */   
/*     */   public VCR toVCR(List cfgs) {
/* 340 */     return getVC().toVCR(cfgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getConfigurations_Legacy() {
/* 345 */     return getVC().getConfigurations();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\service\VCServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */