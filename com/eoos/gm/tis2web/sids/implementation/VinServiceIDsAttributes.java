/*     */ package com.eoos.gm.tis2web.sids.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.RequestGroupImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.SelectionRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.UnresolvableException;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.GlobalVCDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.Collator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
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
/*     */ public class VinServiceIDsAttributes
/*     */ {
/*  49 */   private List serviceIDsAttributes = new ArrayList();
/*     */   
/*  51 */   private Map attrToDesc = new HashMap<Object, Object>();
/*     */   
/*  53 */   private Map valToDesc = new HashMap<Object, Object>();
/*     */   
/*  55 */   private Map attrEnToDesc = new HashMap<Object, Object>();
/*     */   
/*     */   private Locale locale;
/*     */   
/*  59 */   private static Logger log = Logger.getLogger(VinServiceIDsAttributes.class);
/*     */   
/*     */   private String vin;
/*     */ 
/*     */   
/*     */   public String getVin() {
/*  65 */     return this.vin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  70 */   private String sessionID = null;
/*     */   
/*     */   public VinServiceIDsAttributes(String vin, ParamWhereClause params, IDatabaseLink db, Locale loc) throws Exception {
/*  73 */     this.vin = vin;
/*  74 */     this.locale = loc;
/*  75 */     params.addLocaleCondition("SC_Attributes", "AttrID", "c", this.locale);
/*  76 */     params.addLocaleCondition("SC_Values", "ValueID", "d", this.locale);
/*  77 */     ServiceIDDBReader dbr = new ServiceIDDBReader(db, "select a.Add_VehID as aAdd_VehID,a.ServiceCode as aServiceCode,b.AttrID as bAttrID,b.ValueID as bValueID,c.Description as cDescription,ce.Description as ceDescription, d.Description  as dDescription from SC_BaseVehicle a, SC_AddVehDescr b, SC_Attributes c, SC_Attributes ce, SC_Values d", params, "and a.Add_VehID=b.Add_VehID and b.AttrID=c.AttrID and b.AttrID=ce.AttrID and ce.Locale='en' and b.ValueID=d.ValueID order by aAdd_VehID")
/*     */       {
/*     */         private int add_VehID;
/*  80 */         private ServiceIDAttributeMap attrMap = null;
/*     */         
/*     */         public void readRow(ResultSet rs) throws Exception {
/*  83 */           String serviceCode = rs.getString("aServiceCode".toUpperCase(Locale.ENGLISH)).trim();
/*  84 */           int curAdd_VehID = rs.getInt("aAdd_VehID".toUpperCase(Locale.ENGLISH));
/*  85 */           if (curAdd_VehID != this.add_VehID || this.attrMap == null) {
/*  86 */             this.add_VehID = curAdd_VehID;
/*  87 */             this.attrMap = new ServiceIDAttributeMap(serviceCode, this.add_VehID);
/*  88 */             VinServiceIDsAttributes.this.serviceIDsAttributes.add(this.attrMap);
/*     */           } 
/*  90 */           this.attrMap.put((K)Integer.valueOf(rs.getInt("bAttrID".toUpperCase(Locale.ENGLISH))), (V)Integer.valueOf(rs.getInt("bValueID".toUpperCase(Locale.ENGLISH))));
/*  91 */           VinServiceIDsAttributes.this.attrToDesc.put(Integer.valueOf(rs.getInt("bAttrID".toUpperCase(Locale.ENGLISH))), rs.getString("cDescription".toUpperCase(Locale.ENGLISH)).trim());
/*  92 */           VinServiceIDsAttributes.this.attrEnToDesc.put(Integer.valueOf(rs.getInt("bAttrID".toUpperCase(Locale.ENGLISH))), rs.getString("ceDescription".toUpperCase(Locale.ENGLISH)).trim());
/*     */           
/*  94 */           VinServiceIDsAttributes.this.valToDesc.put(Integer.valueOf(rs.getInt("bValueID".toUpperCase(Locale.ENGLISH))), rs.getString("dDescription".toUpperCase(Locale.ENGLISH)).trim());
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     dbr.read();
/* 115 */     fillAttributes();
/* 116 */     checkDeterminable();
/*     */   }
/*     */   
/*     */   public void fillAttributes() {
/* 120 */     Set<K> allAttrs = new HashSet();
/* 121 */     Iterator<ServiceIDAttributeMap> it = this.serviceIDsAttributes.iterator();
/* 122 */     while (it.hasNext()) {
/* 123 */       ServiceIDAttributeMap curAttrMap = it.next();
/* 124 */       allAttrs.addAll(curAttrMap.keySet());
/*     */     } 
/* 126 */     it = this.serviceIDsAttributes.iterator();
/* 127 */     Integer emptyVal = Integer.valueOf(-2147483648);
/* 128 */     while (it.hasNext()) {
/* 129 */       ServiceIDAttributeMap curAttrMap = it.next();
/* 130 */       Iterator<K> itAll = allAttrs.iterator();
/* 131 */       while (itAll.hasNext()) {
/* 132 */         Integer curAttr = (Integer)itAll.next();
/* 133 */         if (!curAttrMap.containsKey(curAttr)) {
/* 134 */           curAttrMap.put((K)curAttr, (V)emptyVal);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 140 */     String notEx = LabelResourceProvider.getInstance().getLabelResource().getLabel(this.locale, "sids.not.existent");
/* 141 */     if (notEx == null) {
/* 142 */       notEx = "not existent";
/*     */     }
/*     */     
/* 145 */     this.valToDesc.put(emptyVal, notEx);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkAttributes() {
/* 150 */     boolean bRet = true;
/* 151 */     Iterator<ServiceIDAttributeMap> it = this.serviceIDsAttributes.iterator();
/* 152 */     if (it.hasNext()) {
/* 153 */       ServiceIDAttributeMap attrMap = it.next();
/* 154 */       while (it.hasNext()) {
/* 155 */         ServiceIDAttributeMap curAttrMap = it.next();
/* 156 */         boolean equ = curAttrMap.keySet().equals(attrMap.keySet());
/* 157 */         if (!equ) {
/* 158 */           log.error("The attributesets for the following Add_VehIDs are different:" + attrMap.getAdd_VehID() + ", " + curAttrMap.getAdd_VehID() + ". Locale = " + this.locale.toString());
/*     */         }
/*     */         
/* 161 */         bRet = (equ && bRet);
/*     */       } 
/*     */     } 
/* 164 */     if (!bRet);
/*     */     
/* 166 */     return bRet;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkDeterminable() {
/* 171 */     boolean bRet = true;
/* 172 */     int to = this.serviceIDsAttributes.size() - 1;
/* 173 */     for (int ind = 0; ind < to; ind++) {
/* 174 */       ListIterator<ServiceIDAttributeMap> it = this.serviceIDsAttributes.listIterator(ind);
/* 175 */       ServiceIDAttributeMap attrMap = it.next();
/* 176 */       while (it.hasNext()) {
/* 177 */         ServiceIDAttributeMap curAttrMap = it.next();
/* 178 */         boolean equ = (curAttrMap.equals(attrMap) && !curAttrMap.getServiceID().equals(attrMap.getServiceID()));
/* 179 */         if (equ) {
/* 180 */           log.error("The attributevalues for the following Add_VehIDs are equal:" + attrMap.getAdd_VehID() + ", " + curAttrMap.getAdd_VehID() + ". The ServiceCode is not necessary determinable" + ". Locale = " + this.locale.toString());
/*     */         }
/*     */         
/* 183 */         bRet = (bRet && !equ);
/*     */       } 
/*     */     } 
/* 186 */     return bRet;
/*     */   }
/*     */   
/*     */   private String getSessionID(AttributeValueMap attrToVal) {
/* 190 */     String sessionID = (String)AVUtil.accessValue(attrToVal, CommonAttribute.SESSION_ID);
/* 191 */     return sessionID;
/*     */   }
/*     */   
/*     */   private ClientContext getContext(String sessionID) {
/* 195 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID, false);
/* 196 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getServiceID(AttributeValueMap attrToVal) throws RequestException {
/* 202 */     Map<Object, Object> attrMap = new HashMap<Object, Object>();
/* 203 */     Collection col = attrToVal.getAttributes();
/* 204 */     this.sessionID = getSessionID(attrToVal);
/*     */     
/* 206 */     Iterator<Attribute> it = col.iterator();
/* 207 */     while (it.hasNext()) {
/* 208 */       Attribute next = it.next();
/* 209 */       if (next instanceof DisplayableServiceIDItem) {
/* 210 */         DisplayableServiceIDItem attr = (DisplayableServiceIDItem)next;
/* 211 */         Value valObj = attrToVal.getValue((Attribute)attr);
/* 212 */         if (valObj instanceof DisplayableServiceIDItem) {
/* 213 */           DisplayableServiceIDItem val = (DisplayableServiceIDItem)valObj;
/* 214 */           attrMap.put(Integer.valueOf(attr.getId()), Integer.valueOf(val.getId()));
/*     */         } 
/*     */       } 
/*     */     } 
/* 218 */     return getServiceID(attrMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServiceID(Map attrMap) throws RequestException {
/* 223 */     TreeSet<String> serviceIDs = new TreeSet();
/* 224 */     Map<Object, Object> unknownAttrs = new TreeMap<Object, Object>();
/*     */ 
/*     */     
/* 227 */     Iterator<ServiceIDAttributeMap> it = this.serviceIDsAttributes.iterator();
/* 228 */     while (it.hasNext()) {
/*     */       
/* 230 */       ServiceIDAttributeMap curAttrs = it.next();
/* 231 */       boolean authorized = curAttrs.getServiceID().equals("SID_NAO") ? true : isAuthorizedConfiguration(curAttrs);
/*     */       
/* 233 */       if (authorized && curAttrs.entrySet().containsAll(attrMap.entrySet())) {
/* 234 */         curAttrs.addValuesMap(attrMap, unknownAttrs);
/* 235 */         serviceIDs.add(curAttrs.getServiceID());
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     if (serviceIDs.size() > 1) {
/* 240 */       it = unknownAttrs.entrySet().iterator();
/* 241 */       if (it.hasNext()) {
/* 242 */         Map.Entry entry = (Map.Entry)it.next();
/* 243 */         createRequest(entry);
/*     */       } else {
/* 245 */         log.error("For vin " + this.vin + " exists no unique Servicecode: " + (String)serviceIDs.first() + " was used");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 251 */     return (serviceIDs.size() > 0) ? serviceIDs.first() : null;
/*     */   }
/*     */   
/*     */   public boolean isAuthorizedConfiguration(ServiceIDAttributeMap curAttrs) {
/* 255 */     String make = null;
/* 256 */     String model = null;
/* 257 */     Integer emptyValue = Integer.valueOf(-2147483648);
/* 258 */     Iterator<Map.Entry<K, V>> itAttrs = curAttrs.entrySet().iterator();
/* 259 */     while (itAttrs.hasNext()) {
/* 260 */       Map.Entry entry = itAttrs.next();
/* 261 */       Integer attrId = (Integer)entry.getKey();
/* 262 */       String attrDesc = (String)this.attrEnToDesc.get(attrId);
/*     */       
/* 264 */       if (attrDesc.equalsIgnoreCase("badge") || attrDesc.equalsIgnoreCase("Make")) {
/* 265 */         make = (String)this.valToDesc.get(entry.getValue());
/*     */       }
/*     */       
/* 268 */       if (attrDesc.equalsIgnoreCase("Model") && !emptyValue.equals(entry.getValue())) {
/* 269 */         model = (String)this.valToDesc.get(entry.getValue());
/*     */       }
/*     */     } 
/* 272 */     log.info("check vehicle context for make ='" + make + "', model='" + model + "'");
/* 273 */     boolean ret = false;
/* 274 */     IConfiguration cfg = VehicleConfigurationUtil.createVC(null, model, null, null, null);
/* 275 */     Make _make = VehicleConfigurationUtil.toMake(make);
/* 276 */     Set values = GlobalVCDataProvider.getInstance(getContext(this.sessionID)).getValues(VehicleConfigurationUtil.KEY_MAKE, cfg);
/* 277 */     for (Iterator<Value> iter = values.iterator(); iter.hasNext() && !ret; ) {
/* 278 */       Value value = iter.next();
/*     */       try {
/* 280 */         Set<Make> makes = VehicleConfigurationUtil.valueUtil.resolve(value, null);
/*     */         
/* 282 */         for (Iterator<Make> iterMakes = makes.iterator(); iterMakes.hasNext() && !ret; ) {
/* 283 */           Make _make2 = iterMakes.next();
/* 284 */           ret = _make2.toString().startsWith(_make.toString());
/*     */         } 
/* 286 */       } catch (UnresolvableException e) {
/* 287 */         log.warn("unable to resolve value " + String.valueOf(value) + ", skipping");
/*     */       } 
/*     */     } 
/* 290 */     return ret;
/*     */   }
/*     */   
/*     */   private void createRequest(Map.Entry entry) throws RequestException {
/* 294 */     Integer attrId = (Integer)entry.getKey();
/* 295 */     String attrLbl = (String)this.attrToDesc.get(attrId);
/* 296 */     if (attrLbl == null) {
/* 297 */       attrLbl = "";
/*     */     }
/* 299 */     String attrDesc = (String)this.attrEnToDesc.get(attrId);
/* 300 */     if (attrDesc == null) {
/* 301 */       attrDesc = "";
/*     */     }
/* 303 */     DisplayableServiceIDAttrImpl attr = new DisplayableServiceIDAttrImpl(attrId.intValue(), attrLbl, attrDesc);
/* 304 */     Set vals = (Set)entry.getValue();
/* 305 */     List<DisplayableServiceIDItem> opts = new ArrayList(vals.size());
/* 306 */     Iterator<Integer> itVals = vals.iterator();
/* 307 */     while (itVals.hasNext()) {
/* 308 */       Integer id = itVals.next();
/* 309 */       String lbl = (String)this.valToDesc.get(id);
/* 310 */       if (lbl == null) {
/* 311 */         lbl = "";
/*     */       }
/*     */       
/* 314 */       DisplayableServiceIDItem option = new DisplayableServiceIDItem(id.intValue(), lbl);
/* 315 */       opts.add(option);
/*     */     } 
/* 317 */     Collections.sort(opts, new DisplayableServiceIDItem.Compare(Collator.getInstance(this.locale)));
/*     */ 
/*     */     
/* 320 */     SelectionRequestImpl selectionRequestImpl = new SelectionRequestImpl(RequestGroupImpl.getInstance("com.eoos.gm.tis2web.sids.service.ServiceIDService"), (Attribute)attr, opts);
/* 321 */     throw new RequestException(selectionRequestImpl);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\VinServiceIDsAttributes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */