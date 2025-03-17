/*     */ package com.eoos.gm.tis2web.sids.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.InvalidVinException;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.NoServiceIDException;
/*     */ import com.eoos.gm.tis2web.sids.service.cai.ServiceID;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.RequestGroupImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.SelectionRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.RequestException;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.text.Collator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIDS
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(SIDS.class);
/*     */   
/*     */   public static ServiceID getServiceID(Locale locale, String vin, AttributeValueMap avMap) throws RequestException, InvalidVinException, NoServiceIDException {
/*  34 */     log.info("Service Code Request for VIN " + vin);
/*  35 */     if (vin.length() < 17) {
/*  36 */       throw new InvalidVinException();
/*     */     }
/*  38 */     List domain = CacheSIDS.getInstance().getBaseVehicles();
/*  39 */     if (domain == null) {
/*  40 */       throw new NoServiceIDException();
/*     */     }
/*  42 */     BaseVehicleSet base = new BaseVehicleSet(domain, vin);
/*  43 */     if (!base.isSupportedVIN()) {
/*  44 */       log.info("VIN not supported");
/*  45 */       throw new NoServiceIDException();
/*     */     } 
/*  47 */     ServiceID serviceID = base.getServiceID();
/*  48 */     if (serviceID == null) {
/*  49 */       Map selections = extractUserSelections(avMap);
/*  50 */       ClientContext context = getClientContext(avMap);
/*  51 */       serviceID = resolveServiceID(base, locale, vin, selections, context);
/*     */     } 
/*  53 */     log.info("Assign Service Code " + serviceID.toString().toUpperCase(Locale.ENGLISH) + " to VIN " + vin);
/*  54 */     return serviceID;
/*     */   }
/*     */   
/*     */   protected static ServiceID resolveServiceID(BaseVehicleSet candidates, Locale locale, String vin, Map selections, ClientContext context) throws NoServiceIDException, RequestException {
/*  58 */     if (selections != null) {
/*     */       try {
/*  60 */         candidates = candidates.reduce(vin, selections, context);
/*  61 */         if (!candidates.isSupportedVIN()) {
/*  62 */           log.info("no base vehicles qualified (possibly due to VC/ACL restriction)");
/*  63 */           throw new NoServiceIDException();
/*     */         } 
/*  65 */         ServiceID sid = candidates.getServiceID();
/*  66 */         if (sid != null) {
/*  67 */           return sid;
/*     */         }
/*     */       }
/*  70 */       catch (Exception e) {
/*  71 */         log.error(e);
/*  72 */         throw new NoServiceIDException();
/*     */       } 
/*     */     }
/*  75 */     List<VehicleAttribute> qualifierAttributes = candidates.getQualifierAttributes();
/*  76 */     if (qualifierAttributes == null) {
/*  77 */       log.info("no qualifier attribute(s) available");
/*  78 */       throw new NoServiceIDException();
/*     */     } 
/*  80 */     for (int i = 0; i < qualifierAttributes.size(); i++) {
/*  81 */       VehicleAttribute attribute = qualifierAttributes.get(i);
/*  82 */       Integer selection = (selections == null) ? null : (Integer)selections.get(attribute.getID());
/*  83 */       if (selection == null) {
/*  84 */         createUserSelectionRequest(locale, candidates, attribute);
/*     */       }
/*     */     } 
/*  87 */     if (candidates.isSupportedVIN()) {
/*  88 */       ServiceID sid = candidates.getServiceID();
/*  89 */       if (sid != null) {
/*  90 */         return sid;
/*     */       }
/*     */     } 
/*  93 */     log.info("no available qualifier attribute");
/*  94 */     throw new NoServiceIDException();
/*     */   }
/*     */   
/*     */   protected static void createUserSelectionRequest(Locale locale, BaseVehicleSet candidates, VehicleAttribute attribute) throws RequestException, NoServiceIDException {
/*  98 */     List values = candidates.getQualifierValues(attribute);
/*  99 */     if (values == null || values.size() == 1) {
/* 100 */       log.error("no qualifier values (" + attribute + ") available");
/* 101 */       throw new NoServiceIDException();
/*     */     } 
/* 103 */     String attributeLabel = attribute.getDescription(locale);
/* 104 */     checkLabel(locale, attribute, attributeLabel);
/* 105 */     String attributeDescription = attribute.getDescription(Locale.ENGLISH);
/* 106 */     checkLabel(locale, attribute, attributeDescription);
/* 107 */     DisplayableServiceIDAttrImpl attributeGUI = new DisplayableServiceIDAttrImpl(attribute.getID().intValue(), attributeLabel, attributeDescription);
/* 108 */     List<DisplayableServiceIDItem> options = new ArrayList(values.size());
/* 109 */     Iterator<VehicleValue> it = values.iterator();
/* 110 */     while (it.hasNext()) {
/* 111 */       VehicleValue value = it.next();
/* 112 */       String label = value.getDescription(locale);
/* 113 */       checkLabel(locale, value, label);
/* 114 */       DisplayableServiceIDItem option = new DisplayableServiceIDItem(value.getID().intValue(), label);
/* 115 */       options.add(option);
/*     */     } 
/* 117 */     Collections.sort(options, new DisplayableServiceIDItem.Compare(Collator.getInstance(locale)));
/* 118 */     SelectionRequestImpl selectionRequestImpl = new SelectionRequestImpl(RequestGroupImpl.getInstance("com.eoos.gm.tis2web.sids.service.ServiceIDService"), (Attribute)attributeGUI, options);
/* 119 */     throw new RequestException(selectionRequestImpl);
/*     */   }
/*     */   
/*     */   protected static String checkLabel(Locale locale, Object item, String label) {
/* 123 */     if (label == null) {
/* 124 */       log.warn("no label for locale '" + locale + "' (" + item + ')');
/* 125 */       label = "";
/*     */     } 
/* 127 */     return label;
/*     */   }
/*     */   
/*     */   protected static ClientContext getClientContext(AttributeValueMap avMap) {
/* 131 */     String sessionID = (String)AVUtil.accessValue(avMap, CommonAttribute.SESSION_ID);
/* 132 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID, false);
/* 133 */     return context;
/*     */   }
/*     */   
/*     */   protected static Map extractUserSelections(AttributeValueMap avMap) {
/* 137 */     Map<Object, Object> selections = new HashMap<Object, Object>();
/* 138 */     Iterator<Attribute> it = avMap.getAttributes().iterator();
/* 139 */     while (it.hasNext()) {
/* 140 */       Attribute next = it.next();
/* 141 */       if (next instanceof DisplayableServiceIDItem) {
/* 142 */         DisplayableServiceIDItem attribute = (DisplayableServiceIDItem)next;
/* 143 */         Value object = avMap.getValue((Attribute)attribute);
/* 144 */         if (object instanceof DisplayableServiceIDItem) {
/* 145 */           DisplayableServiceIDItem value = (DisplayableServiceIDItem)object;
/* 146 */           selections.put(Integer.valueOf(attribute.getId()), Integer.valueOf(value.getId()));
/* 147 */           log.info("user selection: " + attribute.getDenotation(Locale.ENGLISH) + "=" + value.getDenotation(Locale.ENGLISH));
/*     */         } 
/*     */       } 
/*     */     } 
/* 151 */     return (selections.size() == 0) ? null : selections;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\SIDS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */