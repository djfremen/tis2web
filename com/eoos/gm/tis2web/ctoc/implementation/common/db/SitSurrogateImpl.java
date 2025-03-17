/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceUtil;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WIS;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.sitfilter.SIT;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOElement;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class SitSurrogateImpl
/*     */ {
/*  32 */   private int cutoff = 0;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private List sios;
/*     */   
/*     */   private LocaleInfo locale;
/*     */   
/*     */   private String country;
/*     */   
/*     */   private VCR vcr;
/*     */   
/*     */   private Cache cacheSearch;
/*     */   
/*  46 */   private List validSitsNode = null;
/*     */   
/*  48 */   private Map allowedSitsACL = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComparatorLabel comparatorLabel;
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComparatorOrder comparatorOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List searchSIOs(CTOCNode sitNodeSelected) {
/*  63 */     List<?> ret = null;
/*  64 */     if ((ret = (List)this.cacheSearch.lookup(sitNodeSelected)) == null) {
/*  65 */       String sitSelectedId = sitNodeSelected.getID().toString();
/*  66 */       Collections.sort(this.sios, this.comparatorOrder);
/*  67 */       Map<Object, Object> siosFiltered = new HashMap<Object, Object>();
/*  68 */       if (this.sios != null) {
/*  69 */         int count = 0;
/*  70 */         for (Iterator<SIOElement> it = this.sios.iterator(); it.hasNext(); ) {
/*  71 */           SIOElement sio = it.next();
/*  72 */           if (sio != null) {
/*  73 */             List sits = sio.getSits();
/*  74 */             if (sits == null || !sits.contains(sitSelectedId)) {
/*     */               continue;
/*     */             }
/*  77 */             if (!checkSioVCR(sio, this.locale, this.vcr)) {
/*     */               continue;
/*     */             }
/*  80 */             if (!checkSioCountry(sio, this.country)) {
/*     */               continue;
/*     */             }
/*     */             
/*  84 */             siosFiltered.put(sio.getID(), sio);
/*  85 */             count++;
/*  86 */             if (this.cutoff == count) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/*  92 */         if (siosFiltered.size() != 0) {
/*  93 */           for (Iterator<SIOElement> iterator = siosFiltered.values().iterator(); iterator.hasNext(); ) {
/*  94 */             SIOElement sio = iterator.next();
/*  95 */             if (!checkSioLabel(sio, this.locale)) {
/*  96 */               iterator.remove();
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 101 */         ret = new ArrayList(siosFiltered.values());
/* 102 */         Collections.sort(ret, this.comparatorOrder);
/* 103 */         if (this.cutoff == count) {
/* 104 */           ret.add(this.context.getMessage("si.search.result.too-many-hits"));
/*     */         }
/* 106 */         this.cacheSearch.store(sitNodeSelected, ret);
/*     */       } 
/*     */     } 
/* 109 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List searchSITs() {
/* 115 */     if (this.validSitsNode != null) {
/* 116 */       return this.validSitsNode;
/*     */     }
/* 118 */     List<String> validSits = new LinkedList();
/*     */     
/* 120 */     if (this.sios != null) {
/* 121 */       for (Iterator<SIOElement> it = this.sios.iterator(); it.hasNext(); ) {
/* 122 */         SIOElement sio = it.next();
/*     */         
/* 124 */         if (sio != null) {
/* 125 */           List sits = sio.getSits();
/*     */           
/* 127 */           if (sits == null)
/*     */             continue; 
/* 129 */           boolean sioValidVCR = false;
/* 130 */           boolean sioValidLabel = false;
/* 131 */           for (Iterator<String> itSits = sits.iterator(); itSits.hasNext(); ) {
/* 132 */             String sit = itSits.next();
/* 133 */             if (validSits.contains(sit)) {
/*     */               continue;
/*     */             }
/* 136 */             if (!this.allowedSitsACL.containsKey(sit)) {
/*     */               continue;
/*     */             }
/* 139 */             if (!sioValidVCR && !checkSioVCR(sio, this.locale, this.vcr)) {
/*     */               break;
/*     */             }
/* 142 */             if (!sioValidLabel && !checkSio(sio, this.locale, this.country)) {
/*     */               break;
/*     */             }
/* 145 */             sioValidVCR = true;
/* 146 */             sioValidLabel = true;
/* 147 */             validSits.add(sit);
/* 148 */             if (validSits.size() == this.allowedSitsACL.size()) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 156 */     this.validSitsNode = new LinkedList();
/* 157 */     if (validSits != null) {
/* 158 */       for (Iterator<String> it = validSits.iterator(); it.hasNext(); ) {
/* 159 */         String sitId = it.next();
/* 160 */         if (this.allowedSitsACL.get(sitId) != null) {
/* 161 */           this.validSitsNode.add(this.allowedSitsACL.get(sitId));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 166 */     Collections.sort(this.validSitsNode, this.comparatorLabel);
/* 167 */     return this.validSitsNode;
/*     */   }
/*     */   
/*     */   protected boolean checkSioVCR(SIOElement sio, LocaleInfo locale, VCR vcr) {
/* 171 */     if (vcr != null && vcr != VCR.NULL && sio.getVCR() != null && !sio.getVCR().match(locale, vcr)) {
/* 172 */       return false;
/*     */     }
/*     */     
/* 175 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean checkSio(SIOElement sio, LocaleInfo locale, String country) {
/* 179 */     if (checkSioCountry(sio, country) && checkSioLabel(sio, locale)) {
/* 180 */       return true;
/*     */     }
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean checkSioLabel(SIOElement sio, LocaleInfo locale) {
/* 186 */     if (sio.getLabel(locale) == null) {
/* 187 */       return false;
/*     */     }
/* 189 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean checkSioCountry(SIOElement sio, String country) {
/* 193 */     if (country != null) {
/* 194 */       String nmc = sio.getNonMarketsConstraints();
/* 195 */       if (!Util.isNullOrEmpty(nmc) && nmc.indexOf(country) >= 0) {
/* 196 */         return false;
/*     */       }
/*     */     } 
/* 199 */     return true;
/*     */   }
/*     */   
/*     */   protected Map getSitsNodeAllowedACL() {
/* 203 */     Map<Object, Object> sits = new HashMap<Object, Object>();
/*     */     try {
/* 205 */       List sitsNode = SIT.getInstance(this.context).getSITS();
/* 206 */       filterSITs(sitsNode);
/* 207 */       for (Iterator<CTOCNode> it = sitsNode.iterator(); it.hasNext(); ) {
/* 208 */         CTOCNode sit = it.next();
/* 209 */         sits.put(sit.getID().toString(), sit);
/*     */       } 
/* 211 */     } catch (Exception fme) {}
/*     */     
/* 213 */     return sits;
/*     */   }
/*     */   
/*     */   protected Set getSaabSITACLs() {
/*     */     try {
/* 218 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 219 */       SharedContextProxy scp = SharedContextProxy.getInstance(this.context);
/* 220 */       HashSet<String> manufacturers = new HashSet();
/* 221 */       manufacturers.add(WIS.SAAB_MAKE);
/* 222 */       Map<Object, Object> ug2m = new HashMap<Object, Object>();
/* 223 */       ug2m.put(WIS.SAAB_GROUP, manufacturers);
/* 224 */       return aclMI.getAuthorizedResources("SIT", ug2m, scp.getCountry());
/* 225 */     } catch (Exception e) {
/* 226 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSaabSIT(Set sits, CTOCNode sit) {
/* 232 */     String target = CTOCServiceUtil.extractSITKey(sit).toString();
/* 233 */     for (Iterator<String> iter = sits.iterator(); iter.hasNext(); ) {
/* 234 */       String wis = iter.next();
/* 235 */       if (target.equals(wis)) {
/* 236 */         return true;
/*     */       }
/*     */     } 
/* 239 */     return false;
/*     */   }
/*     */   
/*     */   protected void filterSITs(List sits) {
/* 243 */     if (WIS.hasSaabData(this.context)) {
/* 244 */       Set wis = getSaabSITACLs();
/* 245 */       for (Iterator<CTOCNode> iter = sits.iterator(); iter.hasNext(); ) {
/* 246 */         CTOCNode sit = iter.next();
/* 247 */         if (!isSaabSIT(wis, sit))
/* 248 */           iter.remove(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class ComparatorLabel
/*     */     implements Comparator {
/*     */     public int compare(Object obj, Object obj2) {
/*     */       try {
/* 257 */         String label = ((CTOCNode)obj).getLabel(SitSurrogateImpl.this.locale);
/* 258 */         String label2 = ((CTOCNode)obj2).getLabel(SitSurrogateImpl.this.locale);
/* 259 */         return label.compareTo(label2);
/*     */       }
/* 261 */       catch (Exception e) {
/* 262 */         return 0;
/*     */       } 
/*     */     } }
/*     */   
/*     */   public SitSurrogateImpl(int cutoff, ClientContext context, Collection<?> sios, VCR vcr) {
/* 267 */     this.comparatorLabel = new ComparatorLabel();
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
/* 285 */     this.comparatorOrder = new ComparatorOrder();
/*     */     this.cutoff = cutoff;
/*     */     this.context = context;
/*     */     this.cacheSearch = Tis2webUtil.createStdCache();
/*     */     this.sios = new ArrayList(sios);
/*     */     this.locale = LocaleInfoProvider.getInstance().getLocale(context.getLocale());
/*     */     this.country = SharedContextProxy.getInstance(context).getCountry();
/*     */     this.allowedSitsACL = getSitsNodeAllowedACL();
/*     */     this.vcr = vcr;
/*     */   }
/*     */   
/*     */   public class ComparatorOrder implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/*     */       int retValue = 0;
/*     */       try {
/*     */         SIOElement e1 = (SIOElement)o1;
/*     */         SIOElement e2 = (SIOElement)o2;
/*     */         retValue = e1.getOrder() - e2.getOrder();
/*     */       } catch (Exception e) {
/*     */         retValue = 0;
/*     */       } 
/*     */       return retValue;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\SitSurrogateImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */