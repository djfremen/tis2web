/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.result;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.AssemblyGroup;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.Symptom;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TroubleCode;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_AssemblyGroup;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_Symptom;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.filter.TSBFilter_TroubleCode;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.input.TSBComparatorSelectionElement;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.gtwo.PagedElement;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TSBResultPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(TSBResultPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  45 */       template = ApplicationContext.getInstance().loadFile(TSBResultPanel.class, "resultpanel.html", null).toString();
/*  46 */     } catch (Exception e) {
/*  47 */       log.error("unable to load template - error:" + e, e);
/*  48 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/*     */   
/*     */   protected PagedElement ieList;
/*     */   
/*     */   protected Integer mode;
/*  58 */   protected long hitcount = 0L;
/*     */   
/*     */   protected TSBFilter_AssemblyGroup filterAssemblyGroup;
/*     */   
/*     */   protected TSBFilter_Symptom filterSymptom;
/*     */   
/*     */   protected TSBFilter_TroubleCode filterTroubleCode;
/*     */   
/*     */   public TSBResultPanel(ClientContext context, List tsbList, TSBFilter_AssemblyGroup filterAssemblyGroup, TSBFilter_Symptom filterSymptom, TSBFilter_TroubleCode filterTroubleCode, Integer selectedMode) {
/*  67 */     this.context = context;
/*  68 */     this.mode = selectedMode;
/*  69 */     this.filterAssemblyGroup = filterAssemblyGroup;
/*  70 */     this.filterSymptom = filterSymptom;
/*  71 */     this.filterTroubleCode = filterTroubleCode;
/*     */     
/*  73 */     final LocaleInfo li = SharedContextProxy.getInstance(context).getLocaleInfo();
/*  74 */     final String country = SharedContextProxy.getInstance(context).getCountry();
/*     */ 
/*     */     
/*  77 */     Filter filter = new Filter() {
/*     */         public boolean include(Object object) {
/*  79 */           boolean retValue = false;
/*     */           try {
/*  81 */             SIOTSB tsb = (SIOTSB)object;
/*  82 */             retValue = tsb.isQualified(li, country, VCR.NULL);
/*  83 */           } catch (Exception e) {}
/*     */           
/*  85 */           return retValue;
/*     */         }
/*     */       };
/*  88 */     CollectionUtil.filter(tsbList, filter);
/*     */     
/*  90 */     List<TSBWrapper> resultList = new LinkedList();
/*     */     
/*  92 */     if (selectedMode.equals(TSBComparatorSelectionElement.MODE_MODEL)) {
/*  93 */       Iterator<SIOTSB> iter = tsbList.iterator();
/*  94 */       while (iter.hasNext()) {
/*  95 */         SIOTSB tsb = iter.next();
/*  96 */         Iterator<String> iter2 = tsb.getModels().iterator();
/*  97 */         while (iter2.hasNext()) {
/*  98 */           String model = iter2.next();
/*  99 */           resultList.add(new TSBWrapper(model, null, tsb));
/*     */         } 
/*     */       } 
/*     */       
/* 103 */       Collections.sort(resultList, new Comparator<TSBWrapper>() {
/*     */             public int compare(Object obj, Object obj1) {
/* 105 */               String model = ((TSBWrapper)obj).getModel();
/* 106 */               String model2 = ((TSBWrapper)obj1).getModel();
/* 107 */               return model.compareTo(model2);
/*     */             }
/*     */           });
/*     */     }
/* 111 */     else if (selectedMode.equals(TSBComparatorSelectionElement.MODE_ENGINE)) {
/* 112 */       Iterator<SIOTSB> iter = tsbList.iterator();
/* 113 */       while (iter.hasNext()) {
/* 114 */         SIOTSB tsb = iter.next();
/* 115 */         Iterator<String> iter2 = tsb.getEngines().iterator();
/* 116 */         while (iter2.hasNext()) {
/* 117 */           String engine = iter2.next();
/* 118 */           resultList.add(new TSBWrapper(null, engine, tsb));
/*     */         } 
/*     */       } 
/* 121 */       Collections.sort(resultList, new Comparator<TSBWrapper>() {
/*     */             public int compare(Object obj, Object obj1) {
/* 123 */               String engine = ((TSBWrapper)obj).getEngine();
/* 124 */               String engine2 = ((TSBWrapper)obj1).getEngine();
/* 125 */               return engine.compareTo(engine2);
/*     */             }
/*     */           });
/*     */     } else {
/*     */       
/* 130 */       Iterator<SIOTSB> iter = tsbList.iterator();
/* 131 */       while (iter.hasNext()) {
/* 132 */         SIOTSB tsb = iter.next();
/* 133 */         Object date = tsb.getPublicationDate();
/* 134 */         if (date != null)
/*     */         {
/* 136 */           resultList.add(new TSBWrapper(null, null, tsb));
/*     */         }
/*     */       } 
/*     */       
/* 140 */       Comparator<? super TSBWrapper> c = null;
/* 141 */       if (selectedMode.equals(TSBComparatorSelectionElement.MODE_MODIFICATION_DATE)) {
/* 142 */         c = new Comparator()
/*     */           {
/*     */             public int compare(Object o1, Object o2) {
/* 145 */               return -1 * ((TSBWrapper)o1).getPublicationDate().compareTo(((TSBWrapper)o2).getPublicationDate());
/*     */             }
/*     */           };
/* 148 */       } else if (selectedMode.equals(TSBComparatorSelectionElement.MODE_REMEDY_NUMBER)) {
/* 149 */         c = new Comparator() {
/*     */             public int compare(Object o1, Object o2) {
/* 151 */               return ((TSBWrapper)o1).getRemedyNumber().compareTo(((TSBWrapper)o2).getRemedyNumber());
/*     */             }
/*     */           };
/* 154 */       } else if (selectedMode.equals(TSBComparatorSelectionElement.MODE_TROUBLE_CODE)) {
/* 155 */         c = new Comparator() {
/*     */             public int compare(Object o1, Object o2) {
/* 157 */               return ((TSBWrapper)o1).getTroubleCode().compareTo(((TSBWrapper)o2).getTroubleCode());
/*     */             }
/*     */           };
/*     */       } 
/*     */       
/* 162 */       Collections.sort(resultList, c);
/*     */     } 
/*     */     
/* 165 */     this.hitcount = resultList.size();
/*     */     
/* 167 */     this.ieList = new PagedElement(context.createID(), (HtmlElement)new TSBListElement(context, resultList, selectedMode), 10, 20);
/* 168 */     addElement((HtmlElement)this.ieList);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 172 */     StringBuffer code = new StringBuffer(template);
/*     */ 
/*     */     
/* 175 */     StringBuffer tmp = new StringBuffer("{LABEL_FILTER}: {LABEL_ASSEMBLYGROUP}={FILTER_ASSEMBLYGROUP}, {LABEL_SYMPTOM}={FILTER_SYMPTOM}, {LABEL_TROUBLECODE}={FILTER_TROUBLECODE}");
/* 176 */     StringUtilities.replace(tmp, "{LABEL_FILTER}", this.context.getLabel("filter"));
/* 177 */     StringUtilities.replace(tmp, "{LABEL_ASSEMBLYGROUP}", this.context.getLabel("assembly.group"));
/* 178 */     StringUtilities.replace(tmp, "{LABEL_SYMPTOM}", this.context.getLabel("symptom"));
/* 179 */     StringUtilities.replace(tmp, "{LABEL_TROUBLECODE}", this.context.getLabel("trouble.code"));
/* 180 */     AssemblyGroup ag = (this.filterAssemblyGroup != null) ? this.filterAssemblyGroup.getAssemblyGroup() : null;
/* 181 */     StringUtilities.replace(tmp, "{FILTER_ASSEMBLYGROUP}", (ag != null) ? ag.getIdentifier(this.context.getLocale()) : this.context.getLabel("any"));
/* 182 */     Symptom s = (this.filterSymptom != null) ? this.filterSymptom.getSymptom() : null;
/* 183 */     StringUtilities.replace(tmp, "{FILTER_SYMPTOM}", (s != null) ? s.getIdentifier(this.context.getLocale()) : this.context.getLabel("any"));
/* 184 */     TroubleCode tc = (this.filterTroubleCode != null) ? this.filterTroubleCode.getTroubleCode() : null;
/* 185 */     StringUtilities.replace(tmp, "{FILTER_TROUBLECODE}", (tc != null) ? tc.getIdentifier() : this.context.getLabel("any"));
/*     */     
/* 187 */     StringUtilities.replace(code, "{FILTER}", tmp.toString());
/*     */     
/* 189 */     StringUtilities.replace(code, "{HITCOUNT}", this.context.getMessage("si.bulletin.search.hitcount") + ":" + this.hitcount);
/*     */     
/* 191 */     StringUtilities.replace(code, "{LIST_ELEMENT}", this.ieList.getHtmlCode(params));
/* 192 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\result\TSBResultPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */