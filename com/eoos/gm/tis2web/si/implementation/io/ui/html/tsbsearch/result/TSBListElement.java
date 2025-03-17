/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.result;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.BulletinSearchPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.input.TSBComparatorSelectionElement;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TSBListElement
/*     */   extends ListElement
/*     */   implements DataRetrievalAbstraction.DataCallback
/*     */ {
/*  28 */   protected HtmlElement headerPublicationDate = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  30 */         return TSBListElement.this.context.getLabel("publication.date");
/*     */       }
/*     */     };
/*     */   
/*  34 */   protected HtmlElement headerRemedyNumber = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  36 */         return TSBListElement.this.context.getLabel("remedy.number");
/*     */       }
/*     */     };
/*     */   
/*  40 */   protected HtmlElement headerSalesmake = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  42 */         return TSBListElement.this.context.getLabel("salesmake");
/*     */       }
/*     */     };
/*     */   
/*  46 */   protected HtmlElement headerModel = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  48 */         return TSBListElement.this.context.getLabel("model");
/*     */       }
/*     */     };
/*     */   
/*  52 */   protected HtmlElement headerModels = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  54 */         return TSBListElement.this.context.getLabel("models");
/*     */       }
/*     */     };
/*     */   
/*  58 */   protected HtmlElement headerEngine = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  60 */         return TSBListElement.this.context.getLabel("engine");
/*     */       }
/*     */     };
/*     */   
/*  64 */   protected HtmlElement headerAssemblyGroup = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  66 */         return TSBListElement.this.context.getLabel("assembly.group");
/*     */       }
/*     */     };
/*     */   
/*  70 */   protected HtmlElement headerSymptom = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  72 */         return TSBListElement.this.context.getLabel("symptom");
/*     */       }
/*     */     };
/*     */   
/*  76 */   protected HtmlElement headerTroubleCode = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  78 */         return TSBListElement.this.context.getLabel("trouble.code");
/*     */       }
/*     */     };
/*     */   
/*  82 */   protected HtmlElement headerSubject = (HtmlElement)new HtmlLabel() {
/*     */       protected String getLabel() {
/*  84 */         return TSBListElement.this.context.getLabel("subject");
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   protected ClientContext context;
/*     */   protected List data;
/*     */   protected Integer mode;
/*     */   
/*     */   public TSBListElement(ClientContext context, List tsbList, Integer mode) {
/*  94 */     setDataCallback(this);
/*  95 */     this.context = context;
/*  96 */     this.data = tsbList;
/*  97 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/* 101 */     if (this.mode.equals(TSBComparatorSelectionElement.MODE_MODIFICATION_DATE)) {
/* 102 */       return 3;
/*     */     }
/* 104 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 109 */     if (this.mode.equals(TSBComparatorSelectionElement.MODE_MODIFICATION_DATE)) {
/* 110 */       switch (columnIndex) {
/*     */         case 0:
/* 112 */           return this.headerPublicationDate;
/*     */         
/*     */         case 1:
/* 115 */           return this.headerSubject;
/*     */         
/*     */         case 2:
/* 118 */           return this.headerModels;
/*     */       } 
/*     */       
/* 121 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 124 */     HtmlElement firstColumn = null;
/*     */ 
/*     */     
/* 127 */     if (this.mode.equals(TSBComparatorSelectionElement.MODE_MODEL)) {
/* 128 */       firstColumn = this.headerModel;
/* 129 */     } else if (this.mode.equals(TSBComparatorSelectionElement.MODE_ENGINE)) {
/* 130 */       firstColumn = this.headerEngine;
/* 131 */     } else if (this.mode.equals(TSBComparatorSelectionElement.MODE_REMEDY_NUMBER)) {
/* 132 */       firstColumn = this.headerRemedyNumber;
/*     */     } else {
/* 134 */       firstColumn = this.headerTroubleCode;
/*     */     } 
/*     */     
/* 137 */     switch (columnIndex) {
/*     */       case 0:
/* 139 */         return firstColumn;
/*     */       
/*     */       case 1:
/* 142 */         return this.headerPublicationDate;
/*     */       
/*     */       case 2:
/* 145 */         return this.headerSubject;
/*     */       
/*     */       case 3:
/* 148 */         return this.headerModels;
/*     */     } 
/*     */     
/* 151 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*     */     HtmlLabel htmlLabel;
/* 157 */     TSBWrapper tsb = (TSBWrapper)data;
/*     */     
/* 159 */     if (this.mode.equals(TSBComparatorSelectionElement.MODE_MODIFICATION_DATE)) {
/* 160 */       switch (columnIndex) {
/*     */         case 0:
/* 162 */           return (HtmlElement)new HtmlLabel(this.context.formatDate(tsb.getPublicationDate()));
/*     */         
/*     */         case 1:
/* 165 */           return (HtmlElement)getLinkElement(tsb);
/*     */         
/*     */         case 2:
/* 168 */           return getAllModels(tsb);
/*     */       } 
/*     */       
/* 171 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 174 */     HtmlElement firstColumn = null;
/*     */ 
/*     */     
/* 177 */     if (this.mode.equals(TSBComparatorSelectionElement.MODE_MODEL)) {
/* 178 */       firstColumn = getModel(tsb);
/* 179 */     } else if (this.mode.equals(TSBComparatorSelectionElement.MODE_ENGINE)) {
/* 180 */       htmlLabel = new HtmlLabel(tsb.getEngine());
/* 181 */     } else if (this.mode.equals(TSBComparatorSelectionElement.MODE_REMEDY_NUMBER)) {
/* 182 */       htmlLabel = new HtmlLabel(tsb.getRemedyNumber());
/*     */     } else {
/* 184 */       htmlLabel = new HtmlLabel(tsb.getTroubleCode());
/*     */     } 
/*     */     
/* 187 */     switch (columnIndex) {
/*     */       case 0:
/* 189 */         return (HtmlElement)htmlLabel;
/*     */       
/*     */       case 1:
/* 192 */         return (HtmlElement)new HtmlLabel(this.context.formatDate(tsb.getPublicationDate()));
/*     */       
/*     */       case 2:
/* 195 */         return (HtmlElement)getLinkElement(tsb);
/*     */       
/*     */       case 3:
/* 198 */         return getAllModels(tsb);
/*     */     } 
/*     */     
/* 201 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getData() {
/* 207 */     return this.data;
/*     */   }
/*     */   
/*     */   public LinkElement getLinkElement(final TSBWrapper tsb) {
/* 211 */     return new LinkElement("tsb" + tsb.getID(), null) {
/*     */         protected String getLabel() {
/* 213 */           return tsb.getSubject(LocaleInfoProvider.getInstance().getLocale(TSBListElement.this.context.getLocale()));
/*     */         }
/*     */         
/*     */         protected String getTargetFrame() {
/* 217 */           return "_top";
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/* 221 */           BulletinSearchPanel.getInstance(TSBListElement.this.context).switchView(2, new Object[] { this.val$tsb.getTSB(), new Boolean(false) });
/*     */           
/* 223 */           return MainPage.getInstance(TSBListElement.this.context);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public HtmlElement getAllModels(TSBWrapper tsb) {
/*     */     HtmlLabel htmlLabel;
/* 230 */     if (!tsb.hasModelRestriction()) {
/* 231 */       htmlLabel = new HtmlLabel(this.context.getLabel("all.models"));
/*     */     } else {
/*     */       
/* 234 */       htmlLabel = new HtmlLabel(tsb.getAllModelString());
/*     */     } 
/* 236 */     return (HtmlElement)htmlLabel;
/*     */   }
/*     */   
/*     */   public HtmlElement getModel(TSBWrapper tsb) {
/* 240 */     return (HtmlElement)new HtmlLabel(tsb.getModel());
/*     */   }
/*     */   
/*     */   public HtmlElement getEngine(TSBWrapper tsb) {
/* 244 */     HtmlLabel retValue = null;
/* 245 */     if (!tsb.hasEngineRestriction()) {
/* 246 */       retValue = new HtmlLabel(this.context.getLabel("all.engines"));
/*     */     } else {
/* 248 */       retValue = new HtmlLabel(tsb.getEngine());
/*     */     } 
/* 250 */     return (HtmlElement)retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\result\TSBListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */