/*     */ package com.eoos.gm.tis2web.frame.ws.lt.server.wrapper;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.InvalidVehicleDescription;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehDesc;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehDescriptionFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehValResult;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.InvalidVehException;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.util.LtwsUtils;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProviderImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Engine;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Model;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Transmission;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINImpl;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VehDescWrapper
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(VehDescWrapper.class);
/*     */   
/*     */   private VehDesc vehDesc;
/*  38 */   private IConfiguration vConfig = null;
/*     */   
/*     */   public VehDescWrapper(VehDesc v) {
/*  41 */     this.vehDesc = v;
/*     */   }
/*     */ 
/*     */   
/*     */   public VehValResult validateVehDesc(Locale locale, boolean isIclRequest) throws VehDescriptionFault {
/*  46 */     VehValResult result = new VehValResult();
/*  47 */     InvalidVehException invalidVehException = new InvalidVehException();
/*     */     try {
/*  49 */       this.vConfig = resolveVehDesc(locale);
/*  50 */       result.setVehDesc(this.vehDesc);
/*  51 */     } catch (InvalidVehException invVehEx) {
/*  52 */       log.debug("Invalid vehicle description: " + invVehEx.toString());
/*  53 */       invalidVehException = invVehEx;
/*  54 */     } catch (Exception e) {
/*  55 */       log.error("Cannot resolve vehicle description: " + e.toString());
/*  56 */       invalidVehException.addInvalidCode(1024);
/*     */     } 
/*     */     
/*  59 */     if (invalidVehException.getInvalidCode() != 0) {
/*  60 */       InvalidVehicleDescription invVehDesc = new InvalidVehicleDescription();
/*  61 */       LtwsUtils.setInvalidVehicleDetails(invalidVehException, invVehDesc);
/*  62 */       VehDescriptionFault vdFault = new VehDescriptionFault("Invalid vehicle description", invVehDesc);
/*  63 */       throw vdFault;
/*     */     } 
/*  65 */     return result;
/*     */   }
/*     */   
/*     */   public IConfiguration getConfiguration() {
/*  69 */     return this.vConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resolveMake(Locale locale) throws InvalidVehException {
/*  74 */     IConfiguration config = VehicleConfigurationUtil.cfgUtil.getEmptyConfiguration();
/*  75 */     LTDataAdapterFacade ltAdapterFacade = LTDataAdapterFacade.getInstance();
/*  76 */     ILVCAdapter vcAdapter = ltAdapterFacade.getLVCAdapter();
/*  77 */     InvalidVehException e = new InvalidVehException();
/*  78 */     CfgDataProviderImpl cfgDataProviderImpl = new CfgDataProviderImpl((CfgProvider)vcAdapter, VehicleConfigurationUtil.cfgUtil);
/*  79 */     Set<Make> makes = cfgDataProviderImpl.getValues(VehicleConfigurationUtil.KEY_MAKE, config);
/*  80 */     boolean isLTSupported = !(ltAdapterFacade.getLT().getSmc(getMake()).intValue() < 0);
/*  81 */     Make make = VehicleConfigurationUtil.toMake(getMake());
/*  82 */     Value value = VehicleConfigurationUtil.valueManagement.toValue(make);
/*  83 */     if (!makes.contains(value) || !isLTSupported) {
/*  84 */       if (defaultMakeUsed()) {
/*  85 */         e.addToInvalidList((Pair)new PairImpl("defaultmake", getMake()));
/*  86 */         e.addInvalidCode(512);
/*     */       } else {
/*  88 */         e.addToInvalidList((Pair)new PairImpl("make", getMake()));
/*  89 */         e.addInvalidCode(2);
/*     */       } 
/*  91 */       throw e;
/*     */     } 
/*  93 */     this.vConfig = VehicleConfigurationUtil.set(config, VehicleConfigurationUtil.KEY_MAKE, make);
/*     */   }
/*     */   
/*     */   public VehDesc getVehDesc() {
/*  97 */     return this.vehDesc;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 101 */     StringBuffer strBuf = new StringBuffer();
/* 102 */     strBuf.append("vin=" + this.vehDesc.getVin() + ", ");
/* 103 */     strBuf.append("make=" + this.vehDesc.getMake() + ", ");
/* 104 */     strBuf.append("model=" + this.vehDesc.getModel() + ", ");
/* 105 */     strBuf.append("year=" + this.vehDesc.getYear() + ", ");
/* 106 */     strBuf.append("engine=" + this.vehDesc.getEngine() + ", ");
/* 107 */     strBuf.append("transmission=" + this.vehDesc.getTransmission() + ", ");
/* 108 */     strBuf.append("chassis=" + this.vehDesc.getChassis() + ", ");
/* 109 */     strBuf.append("defaultMake=" + this.vehDesc.getDefaultmake());
/* 110 */     return strBuf.toString();
/*     */   }
/*     */   
/*     */   public String toFormString() {
/* 114 */     StringBuffer strBuf = new StringBuffer(this.vehDesc.getMake());
/* 115 */     if (this.vehDesc.getModel() != null && this.vehDesc.getModel().length() > 0) {
/* 116 */       strBuf.append("," + this.vehDesc.getModel());
/*     */     }
/* 118 */     if (this.vehDesc.getYear() != null && this.vehDesc.getYear().length() > 0) {
/* 119 */       strBuf.append("," + this.vehDesc.getYear());
/*     */     }
/* 121 */     if (this.vehDesc.getEngine() != null && this.vehDesc.getEngine().length() > 0) {
/* 122 */       strBuf.append("," + this.vehDesc.getEngine());
/*     */     }
/* 124 */     if (this.vehDesc.getTransmission() != null && this.vehDesc.getTransmission().length() > 0) {
/* 125 */       strBuf.append("," + this.vehDesc.getTransmission());
/*     */     }
/* 127 */     return strBuf.toString();
/*     */   }
/*     */   
/*     */   public String getVin() {
/* 131 */     String result = null;
/* 132 */     if (this.vehDesc.getVin() != null && this.vehDesc.getVin().length() > 0) {
/* 133 */       result = this.vehDesc.getVin();
/*     */     }
/* 135 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isSufficient() {
/* 139 */     return ((this.vehDesc != null && this.vehDesc.getVin() != null && this.vehDesc.getVin().length() > 0) || (getMake() != null && getMake().length() > 0 && this.vehDesc.getModel() != null && this.vehDesc.getModel().length() > 0));
/*     */   }
/*     */   
/*     */   public void formatVehDescription(IConfiguration config) {
/* 143 */     String make = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getMake(config));
/* 144 */     this.vehDesc.setMake(make);
/* 145 */     String model = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getModel(config));
/* 146 */     this.vehDesc.setModel(model);
/* 147 */     String year = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getModelyear(config));
/* 148 */     this.vehDesc.setYear(year);
/* 149 */     String engine = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getEngine(config));
/* 150 */     this.vehDesc.setEngine(engine);
/* 151 */     String trans = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getTransmission(config));
/* 152 */     this.vehDesc.setTransmission(trans);
/*     */   }
/*     */   
/*     */   private String getMake() {
/* 156 */     String result = this.vehDesc.getMake();
/* 157 */     if (result == null || result.length() == 0) {
/* 158 */       result = this.vehDesc.getDefaultmake();
/*     */     }
/* 160 */     return result;
/*     */   }
/*     */   
/*     */   private boolean defaultMakeUsed() {
/* 164 */     boolean result = false;
/* 165 */     if (this.vehDesc.getMake() == null || this.vehDesc.getMake().length() == 0) {
/* 166 */       result = true;
/*     */     }
/* 168 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private IConfiguration resolveVehDesc(Locale locale) throws InvalidVehException {
/* 173 */     IConfiguration config = VehicleConfigurationUtil.cfgUtil.getEmptyConfiguration();
/* 174 */     LTDataAdapterFacade ltAdapterFacade = LTDataAdapterFacade.getInstance();
/* 175 */     ILVCAdapter vcAdapter = ltAdapterFacade.getLVCAdapter();
/* 176 */     String vinStr = this.vehDesc.getVin();
/* 177 */     InvalidVehException e = new InvalidVehException();
/* 178 */     boolean engResolved = false;
/* 179 */     boolean transResolved = false;
/* 180 */     CfgDataProviderImpl cfgDataProviderImpl = new CfgDataProviderImpl((CfgProvider)vcAdapter, VehicleConfigurationUtil.cfgUtil);
/* 181 */     Value value = null;
/* 182 */     if (vinStr != null && vinStr.length() > 0) {
/*     */       try {
/* 184 */         VINImpl vINImpl = new VINImpl(vinStr);
/* 185 */         Set<IConfiguration> vinConfig = vcAdapter.resolveVIN((VIN)vINImpl);
/* 186 */         if (vinConfig.isEmpty()) {
/* 187 */           throw new VIN.InvalidVINException();
/*     */         }
/* 189 */         Iterator<IConfiguration> it = vinConfig.iterator();
/* 190 */         while (it.hasNext()) {
/* 191 */           config = it.next();
/* 192 */           String aMake = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getMake(config));
/* 193 */           resolveMake(locale);
/* 194 */           if (vinConfig.size() == 1 || (aMake != null && aMake.compareToIgnoreCase(getMake()) == 0)) {
/*     */             break;
/*     */           }
/*     */         } 
/* 198 */         e.addToValidList((Pair)new PairImpl("vin", vinStr));
/* 199 */         String engine = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getEngine(config));
/* 200 */         if (engine != null) {
/* 201 */           engResolved = true;
/*     */         }
/* 203 */         String trans = VehicleConfigurationUtil.toString(VehicleConfigurationUtil.getEngine(config));
/* 204 */         if (trans != null) {
/* 205 */           transResolved = true;
/*     */         }
/* 207 */         this.vehDesc.setVin(vinStr.toUpperCase(Locale.ENGLISH));
/* 208 */       } catch (com.eoos.gm.tis2web.vc.v2.vin.VIN.InvalidVINException invVinEx) {
/* 209 */         e.addToInvalidList((Pair)new PairImpl("vin", vinStr));
/* 210 */         e.addInvalidCode(1);
/* 211 */         throw e;
/* 212 */       } catch (InvalidVehException invVehEx) {
/* 213 */         throw invVehEx;
/* 214 */       } catch (Exception ge) {
/* 215 */         e.addInvalidCode(1024);
/* 216 */         throw e;
/*     */       } 
/*     */     } else {
/* 219 */       Set<Make> makes = cfgDataProviderImpl.getValues(VehicleConfigurationUtil.KEY_MAKE, config);
/* 220 */       String makeStr = getMake();
/* 221 */       Make make = VehicleConfigurationUtil.toMake(makeStr);
/* 222 */       value = VehicleConfigurationUtil.valueManagement.toValue(make);
/* 223 */       if (!makes.contains(value) || ltAdapterFacade.getLT().getSmc(makeStr).intValue() < 0) {
/* 224 */         if (!defaultMakeUsed()) {
/* 225 */           e.addToInvalidList((Pair)new PairImpl("make", makeStr));
/* 226 */           e.addInvalidCode(2);
/*     */         } else {
/*     */           
/* 229 */           e.addToInvalidList((Pair)new PairImpl("defaultmake", makeStr));
/* 230 */           e.addInvalidCode(512);
/*     */         } 
/* 232 */         throw e;
/*     */       } 
/* 234 */       config = VehicleConfigurationUtil.set(config, VehicleConfigurationUtil.KEY_MAKE, make);
/* 235 */       if (!defaultMakeUsed()) {
/* 236 */         e.addToValidList((Pair)new PairImpl("make", makeStr));
/*     */       } else {
/*     */         
/* 239 */         e.addToValidList((Pair)new PairImpl("defaultmake", makeStr));
/*     */       } 
/*     */       
/* 242 */       Set<Model> models = cfgDataProviderImpl.getValues(VehicleConfigurationUtil.KEY_MODEL, config);
/* 243 */       Model model = VehicleConfigurationUtil.toModel(this.vehDesc.getModel());
/* 244 */       value = VehicleConfigurationUtil.valueManagement.toValue(model);
/* 245 */       if (!models.contains(value) || ltAdapterFacade.getLT().getMc(ltAdapterFacade.getLT().getSmc(makeStr), this.vehDesc.getModel()).intValue() < 0) {
/* 246 */         e.addToInvalidList((Pair)new PairImpl("model", this.vehDesc.getModel()));
/* 247 */         e.addInvalidCode(4);
/* 248 */         throw e;
/*     */       } 
/* 250 */       config = VehicleConfigurationUtil.set(config, VehicleConfigurationUtil.KEY_MODEL, model);
/* 251 */       e.addToValidList((Pair)new PairImpl("model", this.vehDesc.getModel()));
/*     */       
/* 253 */       if (this.vehDesc.getYear() != null && this.vehDesc.getYear().length() > 0) {
/* 254 */         Set<Modelyear> years = cfgDataProviderImpl.getValues(VehicleConfigurationUtil.KEY_MODELYEAR, config);
/* 255 */         Modelyear year = VehicleConfigurationUtil.toModelyear(this.vehDesc.getYear());
/* 256 */         value = VehicleConfigurationUtil.valueManagement.toValue(year);
/* 257 */         if (!years.contains(value)) {
/* 258 */           e.addToInvalidList((Pair)new PairImpl("year", this.vehDesc.getYear()));
/* 259 */           e.addInvalidCode(8);
/* 260 */           throw e;
/*     */         } 
/* 262 */         config = VehicleConfigurationUtil.set(config, VehicleConfigurationUtil.KEY_MODELYEAR, year);
/* 263 */         e.addToValidList((Pair)new PairImpl("year", this.vehDesc.getYear()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 268 */     if (!engResolved && this.vehDesc.getEngine() != null && this.vehDesc.getEngine().length() > 0) {
/* 269 */       Set<Engine> engines = cfgDataProviderImpl.getValues(VehicleConfigurationUtil.KEY_ENGINE, config);
/* 270 */       Engine engine = VehicleConfigurationUtil.toEngine(this.vehDesc.getEngine());
/* 271 */       value = VehicleConfigurationUtil.valueManagement.toValue(engine);
/* 272 */       if (!engines.contains(value)) {
/* 273 */         e.addToInvalidList((Pair)new PairImpl("engine", this.vehDesc.getEngine()));
/* 274 */         e.addInvalidCode(16);
/* 275 */         throw e;
/*     */       } 
/* 277 */       config = VehicleConfigurationUtil.set(config, VehicleConfigurationUtil.KEY_ENGINE, engine);
/* 278 */       e.addToValidList((Pair)new PairImpl("engine", this.vehDesc.getEngine()));
/*     */     } 
/*     */     
/* 281 */     if (!transResolved && this.vehDesc.getTransmission() != null && this.vehDesc.getTransmission().length() > 0) {
/* 282 */       Set<Transmission> transmissions = cfgDataProviderImpl.getValues(VehicleConfigurationUtil.KEY_TRANSMISSION, config);
/* 283 */       Transmission trans = VehicleConfigurationUtil.toTransmission(this.vehDesc.getTransmission());
/* 284 */       value = VehicleConfigurationUtil.valueManagement.toValue(trans);
/* 285 */       if (!transmissions.contains(value)) {
/* 286 */         e.addToInvalidList((Pair)new PairImpl("transmission", this.vehDesc.getTransmission()));
/* 287 */         e.addInvalidCode(32);
/* 288 */         throw e;
/*     */       } 
/* 290 */       config = VehicleConfigurationUtil.set(config, VehicleConfigurationUtil.KEY_TRANSMISSION, trans);
/* 291 */       e.addToValidList((Pair)new PairImpl("transmission", this.vehDesc.getTransmission()));
/*     */     } 
/* 293 */     formatVehDescription(config);
/* 294 */     return config;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\wrapper\VehDescWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */