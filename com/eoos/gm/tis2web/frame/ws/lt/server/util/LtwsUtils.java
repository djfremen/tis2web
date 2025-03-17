/*     */ package com.eoos.gm.tis2web.frame.ws.lt.server.util;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.InvalidQualifier;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.InvalidVehicleDescription;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.StringValueList;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehDesc;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.VehDescriptionFault;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.InvalidQualifierException;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.InvalidVehException;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.server.wrapper.VehDescWrapper;
/*     */ import com.eoos.gm.tis2web.lt.icop.server.ICOPServerSupport;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LtwsUtils
/*     */ {
/*     */   public static void setInvalidVehicleDetails(InvalidVehException invVehEx, InvalidVehicleDescription invVehDesc) {
/*  28 */     invVehDesc.setInvalidAttrCode(invVehEx.getInvalidCode());
/*  29 */     StringValueList lst = new StringValueList();
/*  30 */     Iterator<Pair> it = invVehEx.getInvalidAttributes().iterator();
/*  31 */     while (it.hasNext()) {
/*  32 */       lst.getLe().add((String)((Pair)it.next()).getFirst());
/*     */     }
/*  34 */     invVehDesc.setInvalidAttrList(lst);
/*  35 */     lst = new StringValueList();
/*  36 */     it = invVehEx.getValidAttributes().iterator();
/*  37 */     while (it.hasNext()) {
/*  38 */       lst.getLe().add((String)((Pair)it.next()).getFirst());
/*     */     }
/*  40 */     invVehDesc.setValidAttrList(lst);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setInvalidQualifierDetails(InvalidQualifierException invQualEx, InvalidQualifier invQualifier) {
/*  45 */     invQualifier.setInvalidAttrCode(invQualEx.getInvalidCode());
/*  46 */     StringValueList lst = new StringValueList();
/*  47 */     Iterator<Pair> it = invQualEx.getInvalidAttributes().iterator();
/*  48 */     while (it.hasNext()) {
/*  49 */       lst.getLe().add((String)((Pair)it.next()).getFirst());
/*     */     }
/*  51 */     invQualifier.setInvalidAttrList(lst);
/*     */   }
/*     */   
/*     */   public static List<String> propertiesToList(Properties p) {
/*  55 */     List<String> result = new ArrayList<String>();
/*  56 */     if (p != null) {
/*  57 */       Iterator<Object> it = p.keySet().iterator();
/*  58 */       while (it.hasNext()) {
/*  59 */         String key = (String)it.next();
/*  60 */         String value = p.getProperty(key);
/*  61 */         result.add(key + "=" + value);
/*     */       } 
/*     */     } 
/*  64 */     return result;
/*     */   }
/*     */   
/*     */   public static VehDesc getVehDesc(ICOPServerSupport.TAL tal) {
/*  68 */     VehDesc result = new VehDesc();
/*  69 */     VehDescWrapper vehDescWrapper = new VehDescWrapper(result);
/*  70 */     if (tal.getVIN() != null) {
/*  71 */       result.setVin(tal.getVIN().toString());
/*     */     }
/*  73 */     vehDescWrapper.formatVehDescription(tal.getVehicleConfiguration());
/*  74 */     return result;
/*     */   }
/*     */   
/*     */   public static VehDesc getVehDesc(ICOPServerSupport.ConfigurationWrapper config) {
/*  78 */     VehDesc result = new VehDesc();
/*  79 */     VehDescWrapper vehDescWrapper = new VehDescWrapper(result);
/*  80 */     if (config.getVIN() != null) {
/*  81 */       result.setVin(config.getVIN().toString());
/*     */     }
/*  83 */     vehDescWrapper.formatVehDescription(config.getConfiguration());
/*  84 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static VehDescriptionFault getVehicleDescriptionFault(VehDescWrapper vehDescWrapper, List<Object> pairs) {
/*  89 */     InvalidVehicleDescription invDesc = new InvalidVehicleDescription();
/*  90 */     VehDescriptionFault result = new VehDescriptionFault("Missing vehicle attributes.", invDesc);
/*  91 */     Set<String> c1 = new HashSet<String>();
/*  92 */     Set<String> c2 = new HashSet<String>();
/*  93 */     Iterator<Pair<String, String>> it = pairs.iterator();
/*     */     try {
/*  95 */       while (it.hasNext()) {
/*  96 */         Pair<String, String> p = it.next();
/*  97 */         c1.add(p.getFirst());
/*  98 */         c2.add(p.getSecond());
/*     */       } 
/* 100 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 103 */     InvalidVehException invVehEx = new InvalidVehException();
/* 104 */     if (c1.contains("*") && c2.contains("*")) {
/* 105 */       invVehEx.addInvalidCode(8);
/* 106 */       invVehEx.addInvalidCode(16);
/* 107 */       invVehEx.addToInvalidList((Pair)new PairImpl("year", null));
/* 108 */       invVehEx.addToInvalidList((Pair)new PairImpl("engine", null));
/* 109 */     } else if (c1.contains("*")) {
/* 110 */       invVehEx.addInvalidCode(16);
/* 111 */       invVehEx.addToInvalidList((Pair)new PairImpl("engine", null));
/* 112 */     } else if (c2.contains("*")) {
/* 113 */       invVehEx.addInvalidCode(8);
/* 114 */       invVehEx.addToInvalidList((Pair)new PairImpl("year", null));
/* 115 */     } else if (!c1.contains("*") && !c2.contains("*")) {
/* 116 */       String e1 = vehDescWrapper.getVehDesc().getYear();
/* 117 */       if (e1 == null || e1.length() == 0 || !c1.contains(e1) || c1.size() > 1) {
/* 118 */         invVehEx.addInvalidCode(8);
/* 119 */         invVehEx.addToInvalidList((Pair)new PairImpl("year", null));
/*     */       } 
/* 121 */       String e2 = vehDescWrapper.getVehDesc().getEngine();
/* 122 */       if (e2 == null || e2.length() == 0 || !c2.contains(e2) || c2.size() > 1) {
/* 123 */         invVehEx.addInvalidCode(16);
/* 124 */         invVehEx.addToInvalidList((Pair)new PairImpl("engine", null));
/*     */       } 
/*     */     } 
/* 127 */     invVehEx.adjustValidList(vehDescWrapper.getVehDesc());
/* 128 */     setInvalidVehicleDetails(invVehEx, invDesc);
/* 129 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\serve\\util\LtwsUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */