/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers;
/*     */ 
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.BookMarkLinkAction;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.DocumentCleaner;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.DomUtil;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.HistoryLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.LinkAction;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class TestStepRestrictionParser
/*     */   extends RestrictionParser
/*     */ {
/*     */   private Map bookMarksToLinks;
/*     */   private Map ElementsToYesLinks;
/*     */   private Map ElementsToNoLinks;
/*     */   private HistoryLink linkFrom;
/*  39 */   private Map bookmarksToElem = new HashMap<Object, Object>();
/*     */   
/*  41 */   private String oldBM = null;
/*     */   
/*  43 */   private String firstBM = null;
/*     */   
/*     */   private Map bookMarksToElements;
/*     */   
/*     */   private String T1BM;
/*     */   
/*     */   private boolean hasRestrictions = false;
/*     */ 
/*     */   
/*     */   public TestStepRestrictionParser(VCR vcr, VCR posVcr, VCR negVcr, Map bookMarksToLinks, Map ElementsToYesLinks, Map ElementsToNoLinks, HistoryLink linkFrom, Map bookMarksToElements, ILVCAdapter.Retrieval lvcr) {
/*  53 */     super(vcr, posVcr, negVcr, lvcr);
/*  54 */     this.bookMarksToLinks = bookMarksToLinks;
/*  55 */     this.ElementsToYesLinks = ElementsToYesLinks;
/*  56 */     this.ElementsToNoLinks = ElementsToNoLinks;
/*  57 */     this.linkFrom = linkFrom;
/*  58 */     this.bookMarksToElements = bookMarksToElements;
/*  59 */     if (linkFrom != null) {
/*  60 */       this.oldBM = linkFrom.getTargetBookmark2();
/*     */     }
/*     */   }
/*     */   
/*     */   public void parse(Element elem) {
/*  65 */     Element vcrElem = DomUtil.firstChildOfCurPlane(elem, "VCR");
/*  66 */     String bM = elem.getAttribute("Tag");
/*  67 */     this.bookmarksToElem.put(bM, elem);
/*  68 */     if (vcrElem != null) {
/*  69 */       int restr = hasRestrictions(vcrElem);
/*  70 */       if (restr != 0) {
/*  71 */         List linksFrom = (List)this.bookMarksToLinks.get(bM);
/*  72 */         HistoryLink linkTo = (restr == 1) ? (HistoryLink)this.ElementsToYesLinks.get(elem) : (HistoryLink)this.ElementsToNoLinks.get(elem);
/*  73 */         if (linksFrom != null && linkTo != null) {
/*  74 */           List<HistoryLink> linksFromNew = (List)this.bookMarksToLinks.get(linkTo.getTargetBookmark2());
/*     */           
/*  76 */           Iterator<HistoryLink> it = linksFrom.iterator();
/*  77 */           while (it.hasNext()) {
/*  78 */             HistoryLink historyLink = it.next();
/*  79 */             historyLink.setTargetBookMark(linkTo.getTargetBookmark2());
/*  80 */             historyLink.setLinkAction(linkTo.getLinkAction());
/*  81 */             historyLink.setLabel(linkTo.getLabel());
/*     */             
/*  83 */             Element linkElem = historyLink.getElem();
/*  84 */             if (linkElem != null) {
/*  85 */               linkElem.setAttribute("Tag", historyLink.getHref());
/*  86 */               DomUtil.setValue(linkElem, linkTo.getLabel());
/*     */             } 
/*  88 */             if (linksFromNew != null)
/*  89 */               linksFromNew.add(historyLink); 
/*     */           } 
/*  91 */           this.bookMarksToLinks.remove(bM);
/*     */         } 
/*  93 */         Node parent = elem.getParentNode();
/*  94 */         if (parent != null) {
/*  95 */           parent.removeChild(elem);
/*  96 */           this.hasRestrictions = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(Element elem) {
/* 105 */     this.T1BM = elem.getAttribute("Tag");
/*     */ 
/*     */     
/* 108 */     this.linkFrom = new HistoryLink(this.T1BM, (LinkAction)new BookMarkLinkAction());
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
/* 123 */     this.linkFrom.setTargetBookMark(this.T1BM);
/* 124 */     this.linkFrom.setLinkAction((LinkAction)new BookMarkLinkAction());
/* 125 */     List<HistoryLink> linkLi = (List)this.bookMarksToLinks.get(this.T1BM);
/* 126 */     if (linkLi == null) {
/* 127 */       linkLi = new LinkedList();
/* 128 */       this.bookMarksToLinks.put(this.T1BM, linkLi);
/*     */     } 
/* 130 */     linkLi.add(this.linkFrom);
/* 131 */     this.firstBM = this.T1BM;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replaceBookmark() {
/* 138 */     boolean noNewPage = true;
/* 139 */     if (this.linkFrom != null && this.firstBM != null) {
/* 140 */       String newBM = this.linkFrom.getTargetBookmark2();
/* 141 */       if (!this.firstBM.equals(newBM)) {
/* 142 */         if (this.linkFrom.getLinkAction() instanceof com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.PageLinkAction) {
/* 143 */           this.linkFrom.getLinkAction().onClick(null, this.linkFrom);
/* 144 */           noNewPage = false;
/* 145 */         } else if (this.oldBM != null) {
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
/* 162 */           replaceBookmark(newBM, this.oldBM);
/*     */         } 
/*     */       }
/*     */     } 
/* 166 */     return noNewPage;
/*     */   }
/*     */ 
/*     */   
/*     */   void replaceBookmark(String from, String to) {
/* 171 */     Element elem = (Element)this.bookmarksToElem.get(from);
/*     */     
/* 173 */     if (elem != null) {
/* 174 */       elem.setAttribute("Tag", to);
/*     */     }
/* 176 */     List linksChange = (List)this.bookMarksToLinks.get(from);
/* 177 */     if (linksChange != null) {
/* 178 */       Iterator<HistoryLink> it = linksChange.iterator();
/* 179 */       while (it.hasNext()) {
/* 180 */         HistoryLink hL = it.next();
/* 181 */         hL.setTargetBookMark(to);
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     Object obj = this.bookMarksToElements.get(from);
/* 186 */     if (obj != null) {
/* 187 */       this.bookMarksToElements.remove(from);
/* 188 */       this.bookMarksToElements.put(to, obj);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clean() {
/* 194 */     if (this.firstBM != null && 
/* 195 */       this.linkFrom != null) {
/* 196 */       String newBM = this.linkFrom.getTargetBookmark2();
/* 197 */       if ((!this.firstBM.equals(newBM) || this.linkFrom.hasValidBM()) && 
/* 198 */         this.bookMarksToElements.get(this.linkFrom.getTargetBookmark2()) == null) {
/* 199 */         this.linkFrom.setTargetBookMark(this.T1BM);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 204 */       if (this.hasRestrictions) {
/* 205 */         clean(this.linkFrom);
/*     */       }
/*     */     } 
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
/*     */   public void clean(HistoryLink from) {
/* 222 */     if (this.hasRestrictions) {
/* 223 */       (new DocumentCleaner(this.ElementsToYesLinks, this.ElementsToNoLinks, this.bookMarksToElements)).clean(this.linkFrom);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void end(NodeList nL) {}
/*     */ 
/*     */   
/*     */   public HistoryLink getLinkFrom() {
/* 232 */     return this.linkFrom;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\TestStepRestrictionParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */