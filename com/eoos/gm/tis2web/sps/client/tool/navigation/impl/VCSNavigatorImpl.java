/*     */ package com.eoos.gm.tis2web.sps.client.tool.navigation.impl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolAttribute;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolValue;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.navigation.VCSNavigator;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.RequestBuilderImpl;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class VCSNavigatorImpl
/*     */   implements VCSNavigator
/*     */ {
/*  27 */   private static Logger log = Logger.getLogger(VCSNavigatorImpl.class);
/*     */   
/*     */   private List navigationAttributes;
/*     */   
/*     */   private Map lastNavigationContext;
/*     */   
/*  33 */   private Navigator navigator = null;
/*     */   
/*     */   public VCSNavigatorImpl() {
/*  36 */     this.navigationAttributes = new ArrayList();
/*  37 */     this.lastNavigationContext = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   private Pair[] getAttributes() {
/*  41 */     Pair[] attrs = this.navigator.getAttributes();
/*  42 */     if (attrs != null && attrs.length != 0) {
/*  43 */       for (int i = 0; i < attrs.length; i++) {
/*  44 */         Pair attr = attrs[i];
/*  45 */         int index = attributeIndex((String)attr.getFirst());
/*  46 */         if (index < 0) {
/*  47 */           this.navigationAttributes.add(attr);
/*  48 */           log.debug("Navigation attribute: " + attrs[i].getFirst() + ", " + attrs[i].getSecond() + ", " + i);
/*  49 */         } else if (index >= 0 && index == i) {
/*  50 */           log.debug("Known navigation attribute: " + attr.getFirst());
/*     */         } else {
/*  52 */           log.warn("Invalid navigation attribute index: " + index + " (" + attr.getFirst() + ", expected: " + i + ")");
/*     */         } 
/*     */       } 
/*     */     } else {
/*  56 */       log.error("Could not retrieve navigation attributes");
/*     */     } 
/*  58 */     return attrs;
/*     */   }
/*     */   
/*     */   private Pair[] getValues(String attribute) {
/*  62 */     return this.navigator.getValues(attribute);
/*     */   }
/*     */   
/*     */   private boolean setFilter(String attribute, String value) {
/*  66 */     return this.navigator.setFilter(attribute, value);
/*     */   }
/*     */   
/*     */   private boolean resetFilter() {
/*  70 */     return this.navigator.resetFilter();
/*     */   }
/*     */   
/*     */   private int attributeIndex(String attrKey) {
/*  74 */     int result = -1;
/*  75 */     for (int i = 0; i < this.navigationAttributes.size(); i++) {
/*  76 */       Pair pair = this.navigationAttributes.get(i);
/*  77 */       if (((String)pair.getFirst()).compareTo(attrKey) == 0) {
/*  78 */         result = i;
/*     */         break;
/*     */       } 
/*     */     } 
/*  82 */     return result;
/*     */   }
/*     */   
/*     */   private SelectionRequest createNextRequest(Object requestData) {
/*  86 */     SelectionRequest request = null;
/*  87 */     ToolAttribute toolAttribute = null;
/*  88 */     List<NavigationValueImpl> toolValues = new ArrayList();
/*     */     
/*  90 */     int attrIndex = this.lastNavigationContext.size();
/*  91 */     if (attrIndex < this.navigationAttributes.size()) {
/*  92 */       Pair attribute = this.navigationAttributes.get(attrIndex);
/*  93 */       NavigationAttributeImpl navigationAttributeImpl = new NavigationAttributeImpl((String)attribute.getFirst(), (String)attribute.getSecond());
/*  94 */       Pair[] values = getValues((String)attribute.getFirst());
/*  95 */       for (int i = 0; i < values.length; i++) {
/*  96 */         toolValues.add(new NavigationValueImpl((String)values[i].getFirst(), (String)values[i].getSecond()));
/*     */       }
/*  98 */       if (toolValues.size() == 1) {
/*  99 */         request = (new RequestBuilderImpl()).makeSelectionRequest((Attribute)navigationAttributeImpl, toolValues, (Value)toolValues.get(0), null);
/*     */       } else {
/* 101 */         request = (new RequestBuilderImpl()).makeSelectionRequest((Attribute)navigationAttributeImpl, toolValues, null);
/*     */       } 
/*     */     } 
/* 104 */     return request;
/*     */   }
/*     */   
/*     */   private void setNavigationContext(Object requestData) throws Exception {
/* 108 */     if (requestData == null || !(requestData instanceof AttributeValueMap) || ((AttributeValueMap)requestData).getAttributes().isEmpty()) {
/* 109 */       resetFilter();
/*     */     } else {
/* 111 */       AttributeValueMap navContext = (AttributeValueMap)requestData;
/*     */       try {
/* 113 */         if (this.lastNavigationContext.isEmpty() || isNewNavigationContext(navContext)) {
/* 114 */           resetFilter();
/*     */           
/* 116 */           Iterator<Attribute> it = navContext.getAttributes().iterator();
/* 117 */           int size = navContext.getAttributes().size();
/* 118 */           Pair[] filterList = new Pair[size];
/* 119 */           for (int i = 0; i < size; i++) {
/* 120 */             filterList[i] = null;
/*     */           }
/* 122 */           while (it.hasNext()) {
/* 123 */             Attribute attr = it.next();
/* 124 */             if (attr instanceof ToolAttribute) {
/* 125 */               ToolAttribute attribute = (ToolAttribute)attr;
/* 126 */               int index = attributeIndex(attribute.getKey());
/* 127 */               ToolValue value = (ToolValue)navContext.getValue((Attribute)attribute);
/* 128 */               filterList[index] = (Pair)new PairImpl(attribute.getKey(), value.getKey());
/*     */             } 
/*     */           } 
/* 131 */           int cnt = 0;
/* 132 */           for (; cnt < filterList.length && 
/* 133 */             filterList[cnt] != null; cnt++) {
/*     */             
/* 135 */             if (isModifiedAttribute(filterList[cnt])) {
/* 136 */               cnt++;
/* 137 */               purgeNavigationContext(filterList, cnt, navContext);
/*     */               break;
/*     */             } 
/*     */           } 
/* 141 */           log.debug("Number of navigation attributes in AttributeValueMap: " + cnt);
/* 142 */           for (int attrIndex = 0; attrIndex < cnt; attrIndex++) {
/* 143 */             setFilter((String)filterList[attrIndex].getFirst(), (String)filterList[attrIndex].getSecond());
/* 144 */             log.debug("NavigationContext iteration " + attrIndex + ": " + filterList[attrIndex].getFirst() + "=" + filterList[attrIndex].getSecond());
/*     */           } 
/*     */         } 
/* 147 */       } catch (Exception e) {
/* 148 */         log.error("Invalid AttributeValueMap: " + e);
/* 149 */         throw new Exception();
/*     */       } 
/* 151 */       saveNavigationContext(navContext);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void purgeNavigationContext(Pair[] filterList, int cnt, AttributeValueMap navContext) {
/* 156 */     for (int attrIndex = cnt; attrIndex < filterList.length && 
/* 157 */       filterList[attrIndex] != null; attrIndex++) {
/* 158 */       String attribute = (String)filterList[attrIndex].getFirst();
/* 159 */       Iterator<Attribute> it = navContext.getAttributes().iterator();
/* 160 */       while (it.hasNext()) {
/* 161 */         Attribute attr = it.next();
/* 162 */         if (attr instanceof ToolAttribute && (
/* 163 */           (ToolAttribute)attr).getKey().equals(attribute)) {
/* 164 */           navContext.remove(attr);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isModifiedAttribute(Pair context) {
/* 176 */     if (this.lastNavigationContext == null || this.lastNavigationContext.isEmpty()) {
/* 177 */       return false;
/*     */     }
/* 179 */     String attribute = (String)context.getFirst();
/* 180 */     String value = (String)context.getSecond();
/* 181 */     Iterator<Attribute> it = this.lastNavigationContext.keySet().iterator();
/* 182 */     while (it.hasNext()) {
/* 183 */       Attribute attr = it.next();
/* 184 */       if (attr instanceof ToolAttribute && (
/* 185 */         (ToolAttribute)attr).getKey().equals(attribute)) {
/* 186 */         ToolValue val = (ToolValue)this.lastNavigationContext.get(attr);
/* 187 */         return (val.getKey() == null || !val.getKey().equals(value));
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     return false;
/*     */   }
/*     */   
/*     */   private void saveNavigationContext(AttributeValueMap navContext) {
/* 195 */     this.lastNavigationContext.clear();
/* 196 */     if (navContext != null) {
/* 197 */       Iterator<Attribute> it = navContext.getAttributes().iterator();
/* 198 */       while (it.hasNext()) {
/* 199 */         Attribute attr = it.next();
/* 200 */         if (attr instanceof ToolAttribute) {
/* 201 */           this.lastNavigationContext.put(attr, navContext.getValue(attr));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isNewNavigationContext(AttributeValueMap navContext) {
/* 208 */     boolean result = true;
/*     */     try {
/* 210 */       if (navContext != null) {
/* 211 */         Iterator<Attribute> it = navContext.getAttributes().iterator();
/* 212 */         int diffs = 0;
/* 213 */         int count = 0;
/* 214 */         while (it.hasNext()) {
/* 215 */           Attribute attr = it.next();
/* 216 */           if (attr instanceof ToolAttribute) {
/* 217 */             count++;
/* 218 */             if (!this.lastNavigationContext.get(attr).equals(navContext.getValue(attr))) {
/* 219 */               diffs++;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 224 */         if (count == this.lastNavigationContext.size() && diffs == 0) {
/* 225 */           result = false;
/*     */         }
/*     */       } 
/* 228 */     } catch (Exception e) {
/* 229 */       log.debug("Navigation context has changed.");
/*     */     } 
/* 231 */     return result;
/*     */   }
/*     */   
/*     */   public boolean createNavigator() {
/* 235 */     boolean ret = false;
/* 236 */     this.navigator = new Navigator();
/* 237 */     List brands = ClientAppContextProvider.getClientAppContext().getSupportedBrandKeys();
/* 238 */     if (brands != null) {
/* 239 */       log.debug("Navigation brands: " + brands.toString());
/*     */     }
/* 241 */     Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/* 242 */     if (locale != null) {
/* 243 */       log.debug("Navigation locale: " + locale.toString());
/*     */     }
/* 245 */     if (this.navigator.initialize(brands, locale) == true) {
/* 246 */       getAttributes();
/* 247 */       ret = true;
/*     */     } else {
/* 249 */       log.error("Could not initialize navigator");
/*     */     } 
/* 251 */     return ret;
/*     */   }
/*     */   
/*     */   public void discardNavigator() {
/* 255 */     this.navigator = null;
/*     */   }
/*     */   
/*     */   public Integer getMethodGroupID(Object requestData) throws Exception {
/* 259 */     Integer result = null;
/* 260 */     setNavigationContext(requestData);
/* 261 */     result = this.navigator.getMethodGroupID();
/* 262 */     if (result == null) {
/*     */       
/* 264 */       log.debug("No MethodGroupID found yet. Throwing next SelectionRequest Exception.");
/* 265 */       SelectionRequest request = createNextRequest(requestData);
/* 266 */       RequestException e = new RequestException((Request)request);
/* 267 */       throw e;
/*     */     } 
/* 269 */     return result;
/*     */   }
/*     */   
/*     */   public RIMParams getRIMParams() {
/* 273 */     RIMParams rimParams = this.navigator.getRIMParams();
/* 274 */     if (rimParams != null) {
/* 275 */       log.debug("RIM parameters: " + rimParams.getDeviceID() + ", " + rimParams.getProtocol() + ", " + rimParams.getCommParam());
/*     */     } else {
/* 277 */       log.debug("Could not retrieve RIM parameters.");
/*     */     } 
/* 279 */     return rimParams;
/*     */   }
/*     */   
/*     */   public String getMethodGroupProviderID() {
/* 283 */     String result = this.navigator.getMethodGroupProviderID();
/* 284 */     if (result != null) {
/* 285 */       log.debug("Method group provider ID: " + result);
/*     */     } else {
/* 287 */       log.debug("Could not retrieve method group provider ID");
/*     */     } 
/* 289 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\navigation\impl\VCSNavigatorImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */