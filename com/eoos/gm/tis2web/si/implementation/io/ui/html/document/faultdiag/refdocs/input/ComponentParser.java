/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs.input;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.TocParser;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import java.text.Collator;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentParser
/*     */   extends TocParser
/*     */   implements DataRetrievalAbstraction.DataCallback
/*     */ {
/*  29 */   private static Logger log = Logger.getLogger(ComponentParser.class);
/*     */   
/*     */   private SITOCElement root;
/*     */   
/*     */   private Locale locale;
/*     */   
/*  35 */   private List data = new LinkedList();
/*     */ 
/*     */   
/*     */   public ComponentParser(SITOCElement root, SITOCElement component, Locale locale) {
/*  39 */     this.root = root;
/*  40 */     this.locale = locale;
/*  41 */     parseChildren(component);
/*  42 */     final Collator col = Collator.getInstance(locale);
/*  43 */     Comparator<?> comp = new Comparator() {
/*     */         public int compare(Object a, Object b) {
/*  45 */           RefDocElement aEl = (RefDocElement)a;
/*  46 */           RefDocElement bEl = (RefDocElement)b;
/*  47 */           int ret = col.compare(aEl.getVehicleSystem(), bEl.getVehicleSystem());
/*     */           
/*  49 */           if (ret == 0) {
/*  50 */             ret = col.compare(aEl.getInformationType(), bEl.getInformationType());
/*     */             
/*  52 */             if (ret == 0) {
/*  53 */               ret = col.compare(aEl.getDocument(), bEl.getDocument());
/*     */             }
/*     */           } 
/*     */           
/*  57 */           return ret;
/*     */         }
/*     */       };
/*  60 */     Collections.sort(this.data, comp);
/*  61 */     testComp2(component);
/*     */   }
/*     */   
/*     */   protected void addElement(final SITOCElement x) {
/*  65 */     if (this.root.getVCR().match(x.getVCR())) {
/*  66 */       final String document = x.getLabel(LocaleInfoProvider.getInstance().getLocale(this.locale));
/*  67 */       RecTocPropParser parser = new RecTocPropParser(x, (SITOCProperty)SIOProperty.WIS, (SITOCProperty)CTOCProperty.AssemblyGroup)
/*     */         {
/*     */           protected void addRecElement(SITOCElement y) {
/*  70 */             final String ag = y.getLabel(LocaleInfoProvider.getInstance().getLocale(ComponentParser.this.locale));
/*  71 */             if (ag != null) {
/*  72 */               TocParser parser2 = new TocParser() {
/*     */                   protected void addElement(SITOCElement snode) {
/*  74 */                     String sit = snode.getLabel(LocaleInfoProvider.getInstance().getLocale(ComponentParser.this.locale));
/*  75 */                     if (sit != null) {
/*  76 */                       ComponentParser.this.data.add(new RefDocElementImpl((SIO)x, ag, sit, document));
/*     */                     }
/*     */                   }
/*     */                 };
/*     */ 
/*     */               
/*  82 */               parser2.parseList(x.getProperty((SITOCProperty)SIOProperty.SIT));
/*     */             } 
/*     */           }
/*     */         };
/*  86 */       String[] prefs = { "sct=", "sc=", "scs=" };
/*  87 */       parser.parse(this.root, prefs);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getData() {
/*  94 */     return this.data;
/*     */   }
/*     */   
/*     */   protected void testComp(SITOCElement component) {
/*  98 */     component.getProperty((SITOCProperty)CTOCProperty.COMPONENT_ID);
/*  99 */     List<SITOCElement> list2 = component.getChildren();
/* 100 */     if (list2 != null) {
/* 101 */       for (int j = 0; j < list2.size(); j++) {
/* 102 */         SITOCElement x = list2.get(j);
/* 103 */         String scidx = (String)x.getProperty((SITOCProperty)SIOProperty.WIS);
/* 104 */         List<SITOCElement> ags = this.root.getChildren();
/* 105 */         for (int k = 0; k < ags.size(); k++) {
/* 106 */           SITOCElement node = ags.get(k);
/* 107 */           if (scidx.indexOf("sct=" + node.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) >= 0) {
/* 108 */             scidx = node.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/* 109 */             List<SITOCElement> sits = (List)x.getProperty((SITOCProperty)SIOProperty.SIT);
/* 110 */             for (int l = 0; l < sits.size(); l++) {
/* 111 */               SITOCElement snode = sits.get(l);
/* 112 */               snode.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/*     */             } 
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void testComp2(SITOCElement component) {
/* 123 */     component.getProperty((SITOCProperty)CTOCProperty.COMPONENT_ID);
/* 124 */     List<SITOCElement> list2 = component.getChildren();
/* 125 */     if (list2 != null) {
/* 126 */       for (int j = 0; j < list2.size(); j++) {
/* 127 */         SITOCElement x = list2.get(j);
/* 128 */         String scidx = (String)x.getProperty((SITOCProperty)SIOProperty.WIS);
/* 129 */         lookupSCX(scidx);
/* 130 */         List<SITOCElement> sits = (List)x.getProperty((SITOCProperty)SIOProperty.SIT);
/* 131 */         for (int l = 0; l < sits.size(); l++) {
/* 132 */           SITOCElement snode = sits.get(l);
/* 133 */           snode.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void testWIS() {
/* 140 */     CTOCNode components = (CTOCNode)this.root.getProperty((SITOCProperty)CTOCProperty.COMPONENT_LIST);
/* 141 */     if (components != null) {
/* 142 */       List<SITOCElement> list = components.getChildren();
/* 143 */       if (list != null) {
/* 144 */         for (int i = 0; i < list.size(); i++) {
/* 145 */           SITOCElement component = list.get(i);
/* 146 */           component.getProperty((SITOCProperty)CTOCProperty.COMPONENT_ID);
/* 147 */           List<SITOCElement> list2 = component.getChildren();
/* 148 */           if (list2 != null) {
/* 149 */             for (int j = 0; j < list2.size(); j++) {
/* 150 */               SITOCElement x = list2.get(j);
/* 151 */               String scidx = (String)x.getProperty((SITOCProperty)SIOProperty.WIS);
/* 152 */               List<SITOCElement> ags = this.root.getChildren();
/* 153 */               for (int k = 0; k < ags.size(); k++) {
/* 154 */                 SITOCElement node = ags.get(k);
/* 155 */                 if (scidx.indexOf("sct=" + node.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) >= 0) {
/* 156 */                   scidx = node.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/* 157 */                   List<SITOCElement> sits = (List)x.getProperty((SITOCProperty)SIOProperty.SIT);
/* 158 */                   for (int l = 0; l < sits.size(); l++) {
/* 159 */                     SITOCElement snode = sits.get(l);
/* 160 */                     snode.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/*     */                   } 
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String lookupSC(String scidx) {
/* 173 */     List<SITOCElement> ags = this.root.getChildren();
/* 174 */     for (int k = 0; k < ags.size(); k++) {
/* 175 */       SITOCElement node = ags.get(k);
/* 176 */       if (scidx.indexOf("sct=" + node.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) >= 0) {
/* 177 */         List<SITOCElement> scs = node.getChildren();
/* 178 */         for (int l = 0; l < scs.size(); l++) {
/* 179 */           SITOCElement snode = scs.get(l);
/* 180 */           if (scidx.indexOf("sc=" + snode.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) >= 0) {
/* 181 */             return snode.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 186 */     return null;
/*     */   }
/*     */   
/*     */   protected String lookupSC(SITOCElement node, String scidx) {
/* 190 */     List<SITOCElement> scs = node.getChildren();
/* 191 */     for (int l = 0; l < scs.size(); l++) {
/* 192 */       SITOCElement snode = scs.get(l);
/* 193 */       if (scidx.indexOf("sc=" + snode.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) >= 0) {
/* 194 */         return snode.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/*     */       }
/*     */     } 
/*     */     
/* 198 */     return "";
/*     */   }
/*     */   
/*     */   protected String lookupSCX(String scidx) {
/* 202 */     List<SITOCElement> ags = this.root.getChildren();
/* 203 */     for (int k = 0; k < ags.size(); k++) {
/* 204 */       SITOCElement node = ags.get(k);
/* 205 */       if (scidx.indexOf("sct=" + node.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup) + ";") >= 0) {
/* 206 */         List<SITOCElement> scs = node.getChildren();
/* 207 */         for (int l = 0; l < scs.size(); l++) {
/* 208 */           SITOCElement snode = scs.get(l);
/* 209 */           if (scidx.indexOf("sc=" + snode.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup) + ";scs=") >= 0) {
/* 210 */             List<SITOCElement> sscs = node.getChildren();
/* 211 */             for (int m = 0; m < sscs.size(); m++) {
/* 212 */               SITOCElement ssnode = sscs.get(m);
/* 213 */               if (scidx.indexOf("scs=" + node.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup) + ";") >= 0) {
/* 214 */                 return ssnode.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/*     */               }
/*     */             } 
/* 217 */           } else if (scidx.indexOf("sc=" + snode.getProperty((SITOCProperty)CTOCProperty.AssemblyGroup) + ";") >= 0) {
/* 218 */             return snode.getLabel(LocaleInfoProvider.getInstance().getLocale("en_GB"));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 223 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\refdocs\input\ComponentParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */