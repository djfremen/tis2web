/*     */ package com.eoos.gm.tis2web.vc.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VehicleContextData
/*     */ {
/*     */   public static final String VIN_ALLOWED_CHARS = "ABCDEFGHJKLMNPRSTUVWXYZ_0123456789";
/*     */   
/*     */   protected class VCValueData
/*     */   {
/*  38 */     private String value = null;
/*     */     
/*     */     private boolean fromVIN = false;
/*     */     
/*     */     public VCValueData(String value) {
/*  43 */       if (value == null) {
/*  44 */         this.value = new String("");
/*     */       } else {
/*  46 */         this.value = value;
/*     */       } 
/*  48 */       this.fromVIN = false;
/*     */     }
/*     */     
/*     */     public VCValueData(String value, boolean fromVIN) {
/*  52 */       this.value = value;
/*  53 */       this.fromVIN = fromVIN;
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/*  57 */       if (value == null) {
/*  58 */         this.value = new String("");
/*     */       } else {
/*  60 */         this.value = value;
/*     */       } 
/*     */     }
/*     */     
/*     */     public String getValue() {
/*  65 */       return this.value;
/*     */     }
/*     */     
/*     */     public void setFromVINFlag(boolean flag) {
/*  69 */       this.fromVIN = flag;
/*     */     }
/*     */     
/*     */     public boolean getFromVINFlag() {
/*  73 */       return this.fromVIN;
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj) {
/*  77 */       boolean retValue = false;
/*  78 */       VCValueData vcvd = (VCValueData)obj;
/*  79 */       if (vcvd != null) {
/*  80 */         String value = vcvd.getValue();
/*  81 */         if (value != null && 
/*  82 */           this.value.equalsIgnoreCase(value)) {
/*  83 */           retValue = true;
/*     */         }
/*     */       } 
/*     */       
/*  87 */       return retValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  92 */       int ret = VCValueData.class.hashCode();
/*  93 */       ret = HashCalc.addHashCode(ret, Util.toLowerCase(this.value));
/*  94 */       return ret;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  98 */       StringBuffer buffer = new StringBuffer();
/*  99 */       buffer.append("VC Value: ");
/* 100 */       buffer.append(this.value);
/* 101 */       buffer.append("   ");
/* 102 */       buffer.append("Set from VIN flag: ");
/* 103 */       buffer.append(this.fromVIN);
/* 104 */       return buffer.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 110 */   private static final Logger log = Logger.getLogger(VehicleContextData.class);
/*     */   
/* 112 */   private ClientContext context = null;
/*     */   
/*     */   public static final int E_VALID_VIN = 0;
/*     */   
/*     */   public static final int E_INVALID_VIN = 1;
/*     */   
/*     */   public static final int E_NOT_INTERPRETABLE_VIN = 2;
/*     */   
/*     */   public static final int E_MORE_MAKES_POSSIBLE = 3;
/*     */   
/* 122 */   private Map vcAttrValueMap = null;
/*     */   
/* 124 */   private String DEFAULT_SALESMAKE = null;
/*     */   
/* 126 */   private Collection vcAuthorizedConfigurations = null;
/*     */   
/* 128 */   private VCR authorizedVCR = null;
/*     */   
/*     */   public static final int VIN_WMI_POSITION = 1;
/*     */   
/*     */   public static final int VIN_MODELYEAR_POSITION = 10;
/*     */   
/* 134 */   protected Set observers = Collections.synchronizedSet(new HashSet());
/*     */   
/*     */   private VehicleContextData(ClientContext context) {
/* 137 */     this.context = context;
/* 138 */     init();
/*     */   }
/*     */   
/*     */   private synchronized Collection getVCAuthorizedConfigurations(ILVCAdapter adapter) {
/* 142 */     if (this.vcAuthorizedConfigurations == null) {
/* 143 */       Collection allCfgs = adapter.getConfigurations_Legacy();
/* 144 */       Set authorizResources = (Set)getACLResources();
/*     */       
/* 146 */       if (Util.isNullOrEmpty(authorizResources)) {
/* 147 */         this.vcAuthorizedConfigurations = allCfgs;
/*     */       } else {
/* 149 */         this.vcAuthorizedConfigurations = new LinkedList();
/* 150 */         for (Iterator resourcesIter = authorizResources.iterator(); resourcesIter.hasNext(); ) {
/* 151 */           Object resource = resourcesIter.next();
/* 152 */           String resourceConf = resource.toString();
/* 153 */           String[] tokens = tokenizeResourceConfiguration(resourceConf);
/*     */           try {
/* 155 */             VCRExpression expression = getResourceConfigurationVCR(adapter, tokens);
/* 156 */             if (this.authorizedVCR == null) {
/* 157 */               this.authorizedVCR = adapter.makeVCR();
/*     */             }
/* 159 */             this.authorizedVCR.add(expression);
/* 160 */           } catch (Exception x) {
/* 161 */             log.error("skip invalid configuration: " + resourceConf);
/*     */             continue;
/*     */           } 
/* 164 */           HashMap contextConfig = getContextConfiguration(tokens, adapter);
/*     */           
/* 166 */           for (Iterator<VCConfiguration> confIterator = allCfgs.iterator(); confIterator.hasNext(); ) {
/* 167 */             VCConfiguration config = confIterator.next();
/* 168 */             if (checkConfiguration(config, contextConfig, adapter)) {
/* 169 */               this.vcAuthorizedConfigurations.add(config);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 177 */     return this.vcAuthorizedConfigurations;
/*     */   }
/*     */   
/*     */   private void init() {
/* 181 */     this.vcAttrValueMap = new HashMap<Object, Object>();
/*     */     
/* 183 */     this.vcAttrValueMap.put("Make", new VCValueData(this.DEFAULT_SALESMAKE));
/*     */   }
/*     */   
/*     */   private boolean checkConfiguration(VCConfiguration configuration, HashMap pattern, ILVCAdapter adapter) {
/* 187 */     List elements = configuration.getElements();
/* 188 */     Iterator<VCValue> iter = elements.iterator();
/* 189 */     boolean match = false;
/* 190 */     while (iter.hasNext()) {
/* 191 */       VCValue elem = iter.next();
/*     */       
/* 193 */       if (elem.getDomainID().intValue() == adapter.getVC().getDomain("Make").getDomainID().intValue()) {
/* 194 */         if (!matchValue(elem, pattern)) {
/* 195 */           match = false;
/*     */           break;
/*     */         } 
/* 198 */         match = true;
/*     */       } 
/*     */       
/* 201 */       if (elem.getDomainID().intValue() == adapter.getVC().getDomain("Model").getDomainID().intValue()) {
/* 202 */         if (!matchValue(elem, pattern)) {
/* 203 */           match = false;
/*     */           break;
/*     */         } 
/* 206 */         match = true;
/*     */       } 
/*     */       
/* 209 */       if (elem.getDomainID().intValue() == adapter.getVC().getDomain("ModelYear").getDomainID().intValue()) {
/* 210 */         if (!matchValue(elem, pattern)) {
/* 211 */           match = false;
/*     */           break;
/*     */         } 
/* 214 */         match = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 219 */     return match;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean matchValue(VCValue element, HashMap pattern) {
/* 225 */     if (pattern.containsKey(element.getDomainID())) {
/* 226 */       if (pattern.get(element.getDomainID()).equals("*"))
/* 227 */         return true; 
/* 228 */       if (pattern.get(element.getDomainID()).toString().equalsIgnoreCase(element.toString())) {
/* 229 */         return true;
/*     */       }
/* 231 */       return false;
/*     */     } 
/*     */     
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   private Collection getACLResources() {
/*     */     try {
/* 239 */       SharedContext sc = this.context.getSharedContext();
/* 240 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 241 */       Set resources = aclMI.getAuthorizedResources("VC", sc.getUsrGroup2ManufMap(), sc.getCountry());
/*     */       
/* 243 */       return resources;
/* 244 */     } catch (Exception e) {
/* 245 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] tokenizeResourceConfiguration(String resource) {
/* 252 */     StringTokenizer st = new StringTokenizer(resource, "#");
/* 253 */     String[] tokens = new String[st.countTokens()];
/* 254 */     for (int count = 0; st.hasMoreTokens(); count++) {
/* 255 */       tokens[count] = st.nextToken();
/*     */     }
/* 257 */     return tokens;
/*     */   }
/*     */   
/*     */   protected HashMap getContextConfiguration(String[] tokens, ILVCAdapter adapter) {
/* 261 */     HashMap<Object, Object> configuration = new HashMap<Object, Object>();
/* 262 */     for (int count = 0; count < tokens.length; count++) {
/* 263 */       switch (count) {
/*     */         case 0:
/* 265 */           configuration.put(adapter.getVC().getDomain("Make").getDomainID(), tokens[count]);
/*     */           break;
/*     */         case 1:
/* 268 */           configuration.put(adapter.getVC().getDomain("Model").getDomainID(), tokens[count]);
/*     */           break;
/*     */         case 2:
/* 271 */           configuration.put(adapter.getVC().getDomain("ModelYear").getDomainID(), tokens[count]);
/*     */           break;
/*     */       } 
/*     */     } 
/* 275 */     return configuration;
/*     */   }
/*     */   
/*     */   protected VCValue lookupVehicleConfigurationValue(int which, String label, ILVCAdapter adapter) {
/* 279 */     VCDomain domain = null;
/* 280 */     switch (which) {
/*     */       case 0:
/* 282 */         domain = adapter.getVC().getDomain("Make");
/*     */         break;
/*     */       case 1:
/* 285 */         domain = adapter.getVC().getDomain("Model");
/*     */         break;
/*     */       case 2:
/* 288 */         domain = adapter.getVC().getDomain("ModelYear");
/*     */         break;
/*     */     } 
/* 291 */     return adapter.getVC().getValue(null, domain, label);
/*     */   }
/*     */   
/*     */   protected VCRExpression getResourceConfigurationVCR(ILVCAdapter adapter, String[] tokens) {
/* 295 */     VCRExpression expression = adapter.makeExpression();
/* 296 */     for (int i = 0; i < tokens.length; i++) {
/* 297 */       String token = tokens[i];
/* 298 */       if (!"*".equals(token)) {
/* 299 */         VCRTerm term = adapter.makeTerm(lookupVehicleConfigurationValue(i, token, adapter));
/* 300 */         expression.add(term);
/*     */       } 
/*     */     } 
/* 303 */     return expression;
/*     */   }
/*     */   
/*     */   public static VehicleContextData getInstance(ClientContext context) {
/* 307 */     synchronized (context.getLockObject()) {
/* 308 */       VehicleContextData instance = (VehicleContextData)context.getObject(VehicleContextData.class);
/* 309 */       if (instance == null) {
/* 310 */         instance = new VehicleContextData(context);
/* 311 */         context.storeObject(VehicleContextData.class, instance);
/*     */       } 
/* 313 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public VCR filterAuthorizedVCR(VCR vcr, ILVCAdapter.Retrieval lvcr) {
/* 318 */     if (this.authorizedVCR != null && vcr.getTerms().size() < 3) {
/* 319 */       if (vcr == VCR.NULL) {
/* 320 */         return vcr;
/*     */       }
/* 322 */       return vcr.diff(this.authorizedVCR);
/*     */     } 
/*     */     
/* 325 */     return vcr;
/*     */   }
/*     */ 
/*     */   
/*     */   public List filterAuthorizedConfigurations(List configurations, ILVCAdapter adapter) {
/* 330 */     List<List<VCConfiguration>> result = null;
/* 331 */     if (configurations != null) {
/* 332 */       Iterator<List> iterator = configurations.iterator();
/* 333 */       result = new ArrayList();
/* 334 */       while (iterator.hasNext()) {
/* 335 */         List<VCConfiguration> elems = iterator.next();
/* 336 */         if (elems != null && elems.size() != 0 && elems.get(0) instanceof VCConfiguration) {
/* 337 */           VCConfiguration vcfg = elems.get(0);
/* 338 */           if (getVCAuthorizedConfigurations(adapter).contains(vcfg)) {
/* 339 */             result.add(elems);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 344 */       if (result.size() == 0) {
/* 345 */         result = null;
/*     */       }
/*     */     } 
/* 348 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VehicleContextData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */