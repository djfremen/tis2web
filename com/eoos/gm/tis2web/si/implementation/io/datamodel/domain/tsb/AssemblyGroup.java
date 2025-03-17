/*     */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.comparator.AGComparator_AssemblyGroupCode;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class AssemblyGroup
/*     */ {
/*  36 */   protected static Logger log = Logger.getLogger(AssemblyGroup.class);
/*     */   
/*  38 */   protected static List domain = null;
/*     */   
/*  40 */   protected static List domainSaab = null;
/*     */   
/*  42 */   protected static Map nodeToInstance = new HashMap<Object, Object>();
/*     */   
/*  44 */   protected static Map gt2scds = null;
/*     */   
/*  46 */   protected static Map gt2scds_Mapping = null;
/*     */   
/*  48 */   protected static List disabledSCDS = null;
/*     */   
/*     */   protected CTOCNode node;
/*     */   
/*     */   protected AssemblyGroup(CTOCNode node) {
/*  53 */     this.node = node;
/*     */   }
/*     */   
/*     */   public static synchronized AssemblyGroup getInstance(CTOCNode node) {
/*  57 */     AssemblyGroup instance = (AssemblyGroup)nodeToInstance.get(node);
/*  58 */     if (instance == null) {
/*  59 */       instance = new AssemblyGroup(node);
/*  60 */       nodeToInstance.put(node, instance);
/*     */     } 
/*  62 */     return instance;
/*     */   }
/*     */   
/*     */   public static Map getGT2SCDS() {
/*  66 */     return gt2scds;
/*     */   }
/*     */   
/*     */   public static List getSCDSs(Object gt) {
/*  70 */     return (gt2scds_Mapping == null) ? null : (List)gt2scds_Mapping.get(gt);
/*     */   }
/*     */   
/*     */   public static boolean isSCDSGroupeDisabled(String scds) {
/*  74 */     return (disabledSCDS != null && disabledSCDS.contains(scds));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean existSCDSGroupeDisabled() {
/*  79 */     return (disabledSCDS != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  84 */     return this.node.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  89 */     if (this == obj)
/*  90 */       return true; 
/*  91 */     if (obj instanceof AssemblyGroup) {
/*  92 */       AssemblyGroup other = (AssemblyGroup)obj;
/*  93 */       boolean ret = Util.equals(this.node, other.node);
/*  94 */       return ret;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIdentifier(LocaleInfo li) {
/* 101 */     return this.node.getLabel(li);
/*     */   }
/*     */   
/*     */   public String getIdentifier(Locale locale) {
/* 105 */     return getIdentifier(LocaleInfoProvider.getInstance().getLocale(locale));
/*     */   }
/*     */   
/*     */   public CTOCNode getNode() {
/* 109 */     return this.node;
/*     */   }
/*     */   
/*     */   public static synchronized void reset() {
/* 113 */     domain = null;
/* 114 */     domainSaab = null;
/* 115 */     gt2scds = null;
/* 116 */     nodeToInstance = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   private static Map loadAllAG(ClientContext context) {
/* 120 */     Map<Object, Object> all = new HashMap<Object, Object>();
/* 121 */     SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(context).getSICTOCService();
/* 122 */     CTOCNode node = siCTOCService.getCTOC().getCTOC(CTOCDomain.SCDS);
/* 123 */     Iterator<CTOCNode> iter = node.getChildren().iterator();
/* 124 */     while (iter.hasNext()) {
/* 125 */       CTOCNode node_ag = iter.next();
/* 126 */       AssemblyGroup assemblyGroup = getInstance(node_ag);
/* 127 */       all.put(node_ag.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup), assemblyGroup);
/*     */     } 
/* 129 */     return all;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized List getDomain(ClientContext context) {
/* 134 */     if (domain == null) {
/*     */       
/* 136 */       List<?> _domain = new LinkedList();
/* 137 */       SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(context).getSICTOCService();
/* 138 */       Map scds2gt = deduceSCDS2GT(siCTOCService);
/* 139 */       Map allDomain = loadAllAG(context);
/* 140 */       _domain = reduceAGList(allDomain, scds2gt);
/* 141 */       Collections.sort(_domain, AGComparator_AssemblyGroupCode.COMPARATOR_ASC);
/* 142 */       domain = Collections.unmodifiableList(_domain);
/* 143 */       gt2scds = getGT2SCDS(allDomain, scds2gt);
/* 144 */       gt2scds_Mapping = getGT2SCDSMapping(scds2gt);
/* 145 */       scds2gt = null;
/* 146 */       allDomain = null;
/*     */     } 
/*     */     
/* 149 */     return domain;
/*     */   }
/*     */   
/*     */   public static synchronized List getSaabDomain(ClientContext context) {
/* 153 */     if (domainSaab == null) {
/* 154 */       List<AssemblyGroup> _domain = new LinkedList();
/* 155 */       SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(context).getSICTOCService();
/* 156 */       CTOCNode node = siCTOCService.getCTOC().getCTOC(CTOCDomain.WIS_SCT);
/* 157 */       Iterator<CTOCNode> iter = node.getChildren().iterator();
/* 158 */       while (iter.hasNext()) {
/* 159 */         _domain.add(getInstance(iter.next()));
/*     */       }
/* 161 */       domainSaab = Collections.unmodifiableList(_domain);
/*     */     } 
/* 163 */     return domainSaab;
/*     */   }
/*     */   
/*     */   private static List reduceAGList(Map allDomain, Map scds2gt) {
/* 167 */     List<AssemblyGroup> reduceList = new LinkedList();
/* 168 */     boolean existSCDS2GT = !(scds2gt == null);
/* 169 */     Iterator<String> iter = allDomain.keySet().iterator();
/* 170 */     while (iter.hasNext()) {
/* 171 */       String ag_code = iter.next();
/* 172 */       AssemblyGroup ag = (AssemblyGroup)allDomain.get(ag_code);
/* 173 */       if (existSCDS2GT) {
/* 174 */         if (!scds2gt.containsKey(ag_code))
/* 175 */           reduceList.add(ag); 
/*     */         continue;
/*     */       } 
/* 178 */       reduceList.add(ag);
/*     */     } 
/*     */     
/* 181 */     return reduceList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Map deduceSCDS2GT(SICTOCService siCTOCService) {
/* 188 */     Map<Object, Object> scds2gt = null;
/*     */     try {
/* 190 */       scds2gt = new HashMap<Object, Object>();
/* 191 */       String DELIMITER_SCDS2GT = "->";
/* 192 */       String DELIMITER_AG = ",";
/* 193 */       String DISABLED_AG = "-";
/*     */       
/* 195 */       CTOCNode node = siCTOCService.getCTOC().getCTOC(CTOCDomain.SCDS2GT);
/* 196 */       if (node == null)
/* 197 */         return null; 
/* 198 */       if (node.getChildren() == null)
/* 199 */         return null; 
/* 200 */       Iterator<CTOCNode> iter = node.getChildren().iterator();
/* 201 */       while (iter.hasNext()) {
/* 202 */         CTOCNode node_scds2gt_child = iter.next();
/* 203 */         String scds2gt_mapping = (String)node_scds2gt_child.getProperty((SITOCProperty)CTOCProperty.SCDS2GT_GROUP);
/* 204 */         int index = scds2gt_mapping.indexOf(DELIMITER_SCDS2GT);
/* 205 */         if (index != -1) {
/* 206 */           String scds = scds2gt_mapping.substring(0, index);
/* 207 */           index += DELIMITER_SCDS2GT.length();
/* 208 */           String gts = scds2gt_mapping.substring(index, scds2gt_mapping.length());
/* 209 */           List<String> gtList = new LinkedList();
/* 210 */           if (gts.indexOf(DELIMITER_AG) != -1) {
/* 211 */             StringTokenizer st = new StringTokenizer(gts, DELIMITER_AG);
/* 212 */             while (st.hasMoreTokens()) {
/* 213 */               String gt = st.nextToken();
/* 214 */               gtList.add(gt);
/*     */             } 
/*     */           } else {
/* 217 */             if (gts.indexOf(DISABLED_AG) != -1) {
/* 218 */               if (disabledSCDS == null)
/* 219 */                 disabledSCDS = new LinkedList(); 
/* 220 */               disabledSCDS.add(scds);
/*     */             } 
/* 222 */             gtList.add(gts);
/*     */           } 
/*     */           
/* 225 */           scds2gt.put(scds, gtList);
/*     */         } 
/*     */       } 
/* 228 */     } catch (Exception e) {
/* 229 */       log.debug("unable to deduce SCDS2GT mapping: " + e);
/* 230 */       return null;
/*     */     } 
/* 232 */     return scds2gt;
/*     */   }
/*     */   
/*     */   protected static Map getGT2SCDS(Map allDomain, Map scds2gt_mapping) {
/* 236 */     if (scds2gt_mapping == null)
/* 237 */       return null; 
/* 238 */     Map<Object, Object> gt2scds = null;
/*     */     try {
/* 240 */       gt2scds = new HashMap<Object, Object>();
/* 241 */       Iterator<String> iter = scds2gt_mapping.keySet().iterator();
/* 242 */       while (iter.hasNext()) {
/* 243 */         String scds = iter.next();
/* 244 */         List gtList = (List)scds2gt_mapping.get(scds);
/* 245 */         Iterator<String> itGTList = gtList.iterator();
/* 246 */         while (itGTList.hasNext()) {
/* 247 */           List scdsList = new LinkedList();
/* 248 */           String gt_code = itGTList.next();
/* 249 */           AssemblyGroup gt_ag = (AssemblyGroup)allDomain.get(gt_code);
/* 250 */           if (gt2scds.containsKey(gt_ag)) {
/* 251 */             scdsList.addAll((List)gt2scds.get(gt_ag));
/*     */           }
/* 253 */           if (!scdsList.contains(scds))
/* 254 */             scdsList.add(allDomain.get(scds)); 
/* 255 */           gt2scds.put(allDomain.get(gt_code), scdsList);
/* 256 */           scdsList = null;
/*     */         } 
/*     */       } 
/* 259 */     } catch (Exception e) {
/* 260 */       log.debug("unable to get gt2scds groups map: " + e);
/* 261 */       return null;
/*     */     } 
/* 263 */     return gt2scds;
/*     */   }
/*     */   
/*     */   protected static Map getGT2SCDSMapping(Map scds2gt_mapping) {
/* 267 */     if (scds2gt_mapping == null)
/* 268 */       return null; 
/* 269 */     Map<Object, Object> gt2scds = null;
/*     */     try {
/* 271 */       gt2scds = new HashMap<Object, Object>();
/* 272 */       Iterator<String> iter = scds2gt_mapping.keySet().iterator();
/* 273 */       while (iter.hasNext()) {
/* 274 */         String scds = iter.next();
/* 275 */         List gtList = (List)scds2gt_mapping.get(scds);
/* 276 */         Iterator<String> itGTList = gtList.iterator();
/* 277 */         while (itGTList.hasNext()) {
/* 278 */           List<String> scdsList = new LinkedList();
/* 279 */           String gt_code = itGTList.next();
/* 280 */           if (gt2scds.containsKey(gt_code)) {
/* 281 */             scdsList.addAll((List)gt2scds.get(gt_code));
/*     */           }
/* 283 */           if (!scdsList.contains(scds))
/* 284 */             scdsList.add(scds); 
/* 285 */           gt2scds.put(gt_code, scdsList);
/* 286 */           scdsList = null;
/*     */         } 
/*     */       } 
/* 289 */     } catch (Exception e) {
/* 290 */       log.debug("unable to get gt2scds groups map: " + e);
/* 291 */       return null;
/*     */     } 
/* 293 */     return gt2scds;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\AssemblyGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */