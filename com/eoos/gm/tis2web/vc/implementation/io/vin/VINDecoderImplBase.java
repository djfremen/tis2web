/*     */ package com.eoos.gm.tis2web.vc.implementation.io.vin;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.vin.exceptions.InvalidModelYearException;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.vin.exceptions.InvalidWMIException;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.InvalidVINException;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCMake;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCModel;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCModelYear;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VINDecoder;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VINStructure;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class VINDecoderImplBase
/*     */   implements VINDecoder
/*     */ {
/*  32 */   protected static Logger log = Logger.getLogger(VINDecoderImplBase.class);
/*     */   
/*     */   public static final int VIN_REMEDIATION = 6;
/*     */   
/*     */   public static final String NO_MODELYEAR_PROPERTY = "vehicle.NoModelYearVIN";
/*     */   
/*     */   protected VC vcs;
/*  39 */   protected HashMap structuresByKey = new HashMap<Object, Object>();
/*     */   
/*  41 */   protected HashMap structuresByID = new HashMap<Object, Object>();
/*     */   
/*  43 */   protected List propertyNoModelYearVIN = new ArrayList();
/*     */   
/*     */   public VINDecoderImplBase(VC vcs) {
/*  46 */     this.vcs = vcs;
/*     */   }
/*     */   
/*     */   public List decode(VCMake make, String VIN) throws Exception {
/*  50 */     String vin = VIN.toUpperCase(Locale.ENGLISH);
/*     */     
/*  52 */     if (vin.length() < 3) {
/*  53 */       throw new InvalidWMIException("VIN: '" + vin + "' WMI is INVALID");
/*     */     }
/*     */     
/*  56 */     VCValue wmi = decodeWMI(vin);
/*  57 */     if (wmi == null) {
/*  58 */       throw new InvalidWMIException("VIN: '" + vin + "' WMI is UNKNOWN");
/*     */     }
/*     */     
/*  61 */     if (vin.length() != 17) {
/*  62 */       throw new InvalidVINException("VIN: '" + vin + "' is INVALID");
/*     */     }
/*     */     
/*  65 */     boolean wildcardMY = isWildcardModelYear((VCValue)make, wmi);
/*  66 */     boolean propertyNoMY = matchPropertyNoModelYear(vin);
/*  67 */     VCValue my = (wildcardMY || propertyNoMY) ? null : decodeModelYear(wmi, vin);
/*  68 */     if (my == null && !wildcardMY && !propertyNoMY) {
/*  69 */       throw new InvalidModelYearException("VIN: '" + vin + "' Model Year is INVALID");
/*     */     }
/*     */     
/*  72 */     VINStructure structure = lookupStructureVIN((VCValue)make, my, wmi, VIN);
/*  73 */     if (structure == null) {
/*  74 */       throw new InvalidVINException("VIN: '" + vin + "' is INVALID");
/*     */     }
/*     */     
/*  77 */     VCRConfiguration vehicle = null;
/*  78 */     VCValue model = null;
/*  79 */     if (wildcardMY || propertyNoMY) {
/*  80 */       model = decodeModel(vin, structure);
/*  81 */       if (model == null) {
/*  82 */         throw new InvalidVINException("VIN: '" + vin + "' is INVALID (Model unknown)");
/*     */       }
/*     */     } else {
/*  85 */       vehicle = decodeVehicle(vin, structure);
/*  86 */       if (vehicle == null) {
/*  87 */         throw new InvalidVINException("VIN: '" + vin + "' is INVALID");
/*     */       }
/*     */     } 
/*     */     
/*  91 */     VCValue engine = decodeEngine(vin, structure);
/*  92 */     if (engine != null && !wildcardMY && !propertyNoMY && !vehicle.matchAssociation(engine)) {
/*  93 */       throw new InvalidVINException("VIN: '" + vin + "' is INVALID");
/*     */     }
/*     */     
/*  96 */     VCValue transmission = decodeTransmission(vin, structure);
/*  97 */     if (transmission != null && !wildcardMY && !propertyNoMY && !vehicle.matchAssociation(transmission)) {
/*  98 */       throw new InvalidVINException("VIN: '" + vin + "' is INVALID");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 103 */     ArrayList<VCRConfiguration> result = new ArrayList();
/* 104 */     if (vehicle != null) {
/* 105 */       result.add(vehicle);
/*     */     }
/* 107 */     if (model != null) {
/* 108 */       result.add(make);
/* 109 */       result.add(model);
/*     */     } 
/* 111 */     result.add(engine);
/* 112 */     result.add(transmission);
/* 113 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List decode(String vin) throws Exception {
/* 119 */     ArrayList<List> results = new ArrayList();
/*     */     
/* 121 */     boolean invalidWMI = false;
/* 122 */     boolean invalidVIN = false;
/* 123 */     boolean invalidMY = false;
/*     */     
/* 125 */     List result = null;
/* 126 */     Collection makes = this.vcs.getSalesMakes();
/* 127 */     Iterator<VCMake> it = makes.iterator();
/* 128 */     while (it.hasNext()) {
/* 129 */       VCMake make = it.next();
/*     */       try {
/* 131 */         result = decode(make, vin);
/* 132 */       } catch (Exception e) {
/* 133 */         if (e instanceof InvalidVINException) {
/* 134 */           invalidVIN = true; continue;
/* 135 */         }  if (e instanceof InvalidModelYearException) {
/* 136 */           invalidMY = true; continue;
/* 137 */         }  if (e instanceof InvalidWMIException) {
/* 138 */           invalidWMI = true;
/*     */         }
/*     */         continue;
/*     */       } 
/* 142 */       if (result != null) {
/* 143 */         boolean found = false;
/* 144 */         for (int i = 0; i < results.size(); i++) {
/* 145 */           if (match(results.get(i), result)) {
/* 146 */             found = true;
/*     */           }
/*     */         } 
/* 149 */         if (!found) {
/* 150 */           results.add(result);
/*     */         }
/*     */       } 
/*     */     } 
/* 154 */     if (results.isEmpty()) {
/* 155 */       if (invalidWMI == true) {
/* 156 */         throw new InvalidWMIException("VIN: " + vin + " Invalid WMI");
/*     */       }
/* 158 */       if (invalidVIN == true) {
/* 159 */         throw new InvalidVINException("VIN: " + vin);
/*     */       }
/* 161 */       if (invalidMY == true) {
/* 162 */         throw new InvalidModelYearException("VIN: " + vin + " Invalid Model Year");
/*     */       }
/* 164 */       return null;
/*     */     } 
/* 166 */     return results;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean match(List<E> vcc, List candidate) {
/*     */     try {
/* 172 */       if (vcc.size() != candidate.size()) {
/* 173 */         return false;
/*     */       }
/* 175 */       for (int i = 0; i < vcc.size(); i++) {
/* 176 */         if (vcc.get(i) == null)
/* 177 */           return (candidate.get(i) == null); 
/* 178 */         if (candidate.get(i) == null)
/* 179 */           return false; 
/* 180 */         if (!vcc.get(i).equals(candidate.get(i))) {
/* 181 */           return false;
/*     */         }
/*     */       } 
/* 184 */       return true;
/*     */     }
/* 186 */     catch (Exception x) {
/* 187 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getValidSalesmakesForVIN(String vin) {
/* 192 */     List<String> validSalesmakes = null;
/* 193 */     if (vin.length() >= 3) {
/* 194 */       String wmi = vin.substring(0, 3);
/* 195 */       validSalesmakes = new ArrayList();
/* 196 */       Set entries = this.structuresByID.entrySet();
/* 197 */       Iterator<Map.Entry> it = entries.iterator();
/* 198 */       while (it.hasNext()) {
/* 199 */         Map.Entry entry = it.next();
/* 200 */         VINStructure vinStructure = (VINStructure)entry.getValue();
/* 201 */         if (vinStructure.getWMI().toString().equalsIgnoreCase(wmi) && 
/* 202 */           !validSalesmakes.contains(vinStructure.getMake().toString())) {
/* 203 */           validSalesmakes.add(vinStructure.getMake().toString());
/*     */         }
/*     */       } 
/*     */       
/* 207 */       Collections.sort(validSalesmakes);
/*     */     } 
/* 209 */     return validSalesmakes;
/*     */   }
/*     */   
/*     */   protected VCValue decodeModelYear(VCValue wmi, String vin) {
/* 213 */     String key = vin.substring(9, 10);
/* 214 */     if ("9BG".equals(wmi.toString())) {
/* 215 */       if ((key.charAt(0) == 'W' || key.charAt(0) == 'V') && key.charAt(0) != '0') {
/* 216 */         key = vin.substring(8, 9);
/* 217 */       } else if (vin.charAt(8) != '0') {
/* 218 */         return null;
/*     */       } 
/*     */     }
/* 221 */     VCRDomain domain = (VCRDomain)this.vcs.getDomain("VIN ModelYear");
/* 222 */     boolean vinRemediation = !Character.isDigit(vin.charAt(6));
/* 223 */     if (vinRemediation) {
/* 224 */       VCRValue vCRValue = domain.lookup("#" + key);
/* 225 */       if (vCRValue == null) {
/* 226 */         vCRValue = domain.lookup(key);
/*     */       }
/* 228 */       return (VCValue)vCRValue;
/*     */     } 
/* 230 */     return (VCValue)domain.lookup(key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected VCValue decodeWMI(String vin) {
/* 235 */     String key = vin.substring(0, 3);
/* 236 */     VCRDomain domain = (VCRDomain)this.vcs.getDomain("VIN WMI");
/* 237 */     return (VCValue)domain.lookup(key);
/*     */   }
/*     */   
/*     */   protected VCValue decodeModel(String vin, VINStructure structure) {
/* 241 */     VCModel model = (VCModel)structure.match(vin, this.vcs.getDomain("Model"));
/* 242 */     return (VCValue)model;
/*     */   }
/*     */   
/*     */   protected VCRConfiguration decodeVehicle(String vin, VINStructure structure) {
/* 246 */     VCRConfiguration vehicle = null;
/* 247 */     VCModel model = (VCModel)structure.match(vin, this.vcs.getDomain("Model"));
/* 248 */     if (model != null) {
/* 249 */       VCRDomain domain = (VCRDomain)this.vcs.getDomain("ModelYear");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 255 */       VCModelYear my = (VCModelYear)domain.getValue(structure.getModelYear().getValueID());
/* 256 */       vehicle = (VCRConfiguration)this.vcs.getConfiguration((VCMake)structure.getMake(), model, my);
/*     */     } 
/* 258 */     return vehicle;
/*     */   }
/*     */   
/*     */   protected VCValue decodeEngine(String vin, VINStructure structure) {
/* 262 */     VCValue engine = structure.match(vin, this.vcs.getDomain("Engine"));
/* 263 */     if (engine == null && 
/* 264 */       structure.getElement(this.vcs.getDomain("Engine")) != null) {
/* 265 */       throw new InvalidVINException("VIN: '" + vin + "' is INVALID");
/*     */     }
/*     */     
/* 268 */     return engine;
/*     */   }
/*     */   
/*     */   protected VCValue decodeTransmission(String vin, VINStructure structure) {
/* 272 */     VCValue transmission = structure.match(vin, this.vcs.getDomain("Transmission"));
/* 273 */     if (transmission == null && 
/* 274 */       structure.getElement(this.vcs.getDomain("Transmission")) != null) {
/* 275 */       throw new InvalidVINException("VIN: '" + vin + "' is INVALID");
/*     */     }
/*     */     
/* 278 */     return transmission;
/*     */   }
/*     */   
/*     */   public VINStructure lookupStructure(Integer structureID) {
/* 282 */     return (VINStructure)this.structuresByID.get(structureID);
/*     */   }
/*     */   
/*     */   public VINStructure lookupStructure(VCValue make, VCValue modelYear, VCValue wmi, String filterVIN) {
/* 286 */     return (VINStructure)this.structuresByKey.get(VINStructureImpl.makeFilterKey(make, modelYear, wmi, filterVIN));
/*     */   }
/*     */   
/*     */   protected boolean isWildcardModelYear(VCValue make, VCValue wmi) {
/* 290 */     return (this.structuresByKey.get(VINStructureImpl.makeFilterKey(make, null, wmi, null)) != null);
/*     */   }
/*     */   
/*     */   protected boolean matchPropertyNoModelYear(String vin) {
/* 294 */     for (int p = 0; p < this.propertyNoModelYearVIN.size(); p++) {
/* 295 */       String pattern = this.propertyNoModelYearVIN.get(p);
/* 296 */       for (int i = 0; pattern != null && i < pattern.length(); i++) {
/* 297 */         char c = pattern.charAt(i);
/* 298 */         if (c != '?' && c != vin.charAt(i)) {
/* 299 */           pattern = null;
/*     */         }
/*     */       } 
/* 302 */       if (pattern != null) {
/* 303 */         return true;
/*     */       }
/*     */     } 
/* 306 */     return false;
/*     */   }
/*     */   
/*     */   public VINStructure lookupStructureVIN(VCValue make, VCValue modelYear, VCValue wmi, String filterVIN) {
/* 310 */     VINStructure result = null;
/* 311 */     Iterator<VINStructure> it = this.structuresByKey.values().iterator();
/* 312 */     while (it.hasNext()) {
/* 313 */       VINStructure structure = it.next();
/* 314 */       if (make.equals(structure.getMake()) && structure.match(modelYear, wmi, filterVIN)) {
/* 315 */         if (result == null) {
/* 316 */           result = structure; continue;
/* 317 */         }  if ("#".equals(result.getFilterVIN())) {
/* 318 */           result = structure; continue;
/* 319 */         }  if ("#".equals(structure.getFilterVIN())) {
/*     */           continue;
/*     */         }
/* 322 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 326 */     return result;
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
/*     */   
/*     */   public VINStructure getStructure(Integer structureID, VCValue make, VCValue modelYear, VCValue wmi, String filterVIN) {
/* 347 */     VINStructure structure = lookupStructure(make, modelYear, wmi, filterVIN);
/* 348 */     if (structure == null) {
/* 349 */       structure = new VINStructureImpl(structureID, make, modelYear, wmi, filterVIN);
/* 350 */       add(structure);
/*     */     } 
/* 352 */     return structure;
/*     */   }
/*     */   
/*     */   public Collection getStructures() {
/* 356 */     return this.structuresByKey.values();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(VINStructure structure) {
/* 362 */     this.structuresByKey.put(structure.getFilterKey(), structure);
/* 363 */     this.structuresByID.put(structure.getStructureID(), structure);
/*     */   }
/*     */   
/*     */   public void setConfiguration(Configuration cfg) {
/* 367 */     for (Iterator iterKeys = cfg.getKeys().iterator(); iterKeys.hasNext(); ) {
/* 368 */       Object key = iterKeys.next();
/* 369 */       if (key.toString().indexOf("vehicle.NoModelYearVIN") >= 0) {
/* 370 */         String pattern = cfg.getProperty(key.toString());
/* 371 */         this.propertyNoModelYearVIN.add(pattern);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\vin\VINDecoderImplBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */