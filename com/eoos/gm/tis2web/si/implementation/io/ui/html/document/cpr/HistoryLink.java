/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class HistoryLink
/*     */   extends LinkElement
/*     */ {
/*     */   private DocumentHistory documentHistory;
/*     */   private StepResults stepResults;
/*     */   private History history;
/*     */   private SIOCPR sioCpr;
/*  33 */   private int plane = 65;
/*     */ 
/*     */   
/*     */   private boolean isLinkPage = false;
/*     */   
/*     */   ClientContext context;
/*     */   
/*  40 */   private String label = "";
/*     */ 
/*     */   
/*  43 */   private List inputElements = null;
/*     */ 
/*     */   
/*  46 */   private Element elem = null;
/*     */   
/*     */   private LinkAction linkAction;
/*     */   
/*     */   private boolean hasValidBM = true;
/*     */ 
/*     */   
/*     */   public HistoryLink(ClientContext context, String targetBookmark, History h, DocumentHistory dh, StepResults sr, SIOCPR sioCpr, LinkAction linkAction) {
/*  54 */     super(context.createID(), targetBookmark);
/*  55 */     if (targetBookmark == null) {
/*  56 */       setTargetBookMark("STRARTBM");
/*  57 */       this.hasValidBM = false;
/*     */     } 
/*  59 */     this.documentHistory = dh;
/*  60 */     this.stepResults = sr;
/*  61 */     this.history = h;
/*  62 */     this.sioCpr = sioCpr;
/*  63 */     this.context = context;
/*  64 */     this.linkAction = linkAction;
/*     */   }
/*     */   
/*     */   public HistoryLink(String targetBookmark, LinkAction linkAction) {
/*  68 */     super("", targetBookmark);
/*  69 */     if (targetBookmark == null) {
/*  70 */       setTargetBookMark("STRARTBM");
/*  71 */       this.hasValidBM = false;
/*     */     } 
/*  73 */     this.linkAction = linkAction;
/*  74 */     this.hasValidBM = false;
/*     */   }
/*     */   
/*     */   public boolean hasValidBM() {
/*  78 */     return this.hasValidBM;
/*     */   }
/*     */   
/*     */   public History getHistory() {
/*  82 */     return this.history;
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  86 */     StepResults stepRes = this.stepResults.cloneStepResults();
/*  87 */     if (this.inputElements != null) {
/*  88 */       Iterator<UnitInputElement> it = this.inputElements.iterator();
/*  89 */       while (it.hasNext()) {
/*  90 */         UnitInputElement ie = it.next();
/*  91 */         if (ie.getValue() != null) {
/*  92 */           StringBuffer value = new StringBuffer((String)ie.getValue());
/*  93 */           if (value.length() > 0) {
/*  94 */             String unit = ie.getUnit();
/*  95 */             if (unit != null && unit.length() > 0) {
/*  96 */               value.append(" ");
/*  97 */               value.append(unit);
/*     */             } 
/*  99 */             stepRes.addValue(value.toString());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 104 */     LocaleInfo li = LocaleInfoProvider.getInstance().getLocale(this.context.getLocale());
/* 105 */     String label = this.sioCpr.getElectronicSystemLabel(li);
/*     */     
/* 107 */     SystemHistory sh = new SystemHistory(this.sioCpr.getElectronicSystemCode().intValue(), label);
/*     */     
/* 109 */     this.history.addStepResults(stepRes, this.documentHistory, sh, this.plane, this.isLinkPage);
/* 110 */     return this.linkAction.onClick(submitParams, this, stepRes);
/*     */   }
/*     */   
/*     */   public void setPlane(int plane) {
/* 114 */     this.plane = plane;
/*     */   }
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
/*     */   public String getLabel() {
/* 128 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabel(String label) {
/* 138 */     this.label = label;
/*     */   }
/*     */   
/*     */   public String getHref() {
/* 142 */     String s = getHtmlCode(null);
/* 143 */     int i = s.indexOf("href");
/* 144 */     int iStart = s.indexOf("\"", i + 1);
/* 145 */     int iEnd = s.indexOf("\"", iStart + 1);
/* 146 */     s = s.substring(iStart + 1, iEnd);
/* 147 */     return s;
/*     */   }
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
/*     */   public void setInputElements(List inputElements) {
/* 163 */     this.inputElements = inputElements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getElem() {
/* 172 */     return this.elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElem(Element elem) {
/* 182 */     this.elem = elem;
/*     */   }
/*     */   
/*     */   public void setTargetBookMark(String bm) {
/* 186 */     this.targetBookmark = bm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkAction getLinkAction() {
/* 195 */     return this.linkAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkAction(LinkAction linkAction) {
/* 205 */     this.linkAction = linkAction;
/*     */   }
/*     */   
/*     */   public String getTargetBookmark2() {
/* 209 */     return (this.targetBookmark != null) ? this.targetBookmark : "";
/*     */   }
/*     */   
/*     */   public void setIsLinkPage(boolean isLinkPage) {
/* 213 */     this.isLinkPage = isLinkPage;
/*     */   }
/*     */   
/*     */   public boolean getIsLinkPage() {
/* 217 */     return this.isLinkPage;
/*     */   }
/*     */   
/*     */   public DocumentHistory getDocumentHistory() {
/* 221 */     return this.documentHistory;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\HistoryLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */