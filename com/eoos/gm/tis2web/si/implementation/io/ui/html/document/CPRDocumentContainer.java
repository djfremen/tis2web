/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.DynamicResourceController;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.BookMarkLinkAction;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.DocumentHistory;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.DomUtil;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.HistoryLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.LinkAction;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.PageLinkBackAction;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.PageLinkForwardAction;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.StepResults;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.UnitInputElement;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.ElementPath;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.NominalValueParser;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.ParameterVcrParser;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.RestrictionParser;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.TerminalParser;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.TestStepRestrictionParser;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tiff.DetailViewPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.datamodel.VehicleOptionsData;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.ImageElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.scsm.v2.util.UncheckedInterruptedException;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.xalan.xsltc.trax.TransformerFactoryImpl;
/*     */ import org.apache.xpath.XPathAPI;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CPRDocumentContainer
/*     */   extends DocumentContainer
/*     */ {
/*  76 */   private static Logger log = Logger.getLogger(CPRDocumentContainer.class);
/*     */   
/*     */   private static Transformer transformer;
/*     */   
/*  80 */   private static ElementPath dataMapPath = new ElementPath();
/*     */ 
/*     */ 
/*     */   
/*     */   private static Transformer xformer;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  90 */       TransformerFactoryImpl transformerFactoryImpl = new TransformerFactoryImpl();
/*     */       
/*  92 */       ByteArrayInputStream bais = new ByteArrayInputStream(ApplicationContext.getInstance().loadResource("/cpr/cpr.xslt"));
/*  93 */       transformer = transformerFactoryImpl.newTransformer(new StreamSource(bais));
/*  94 */       xformer = transformerFactoryImpl.newTransformer();
/*  95 */     } catch (Throwable e) {
/*  96 */       log.error("unable to create xslt transformer - error:" + e, e);
/*  97 */       throw new RuntimeException();
/*     */     } 
/*     */     
/* 100 */     dataMapPath.add("DataMap");
/* 101 */     dataMapPath.add("Value");
/*     */   }
/*     */   
/* 104 */   private String body = null;
/*     */   
/* 106 */   private String styleSheet = null;
/*     */   
/* 108 */   private String title = null;
/*     */   
/* 110 */   private String encoding = null;
/*     */   
/*     */   private DocumentPage docPage;
/*     */   
/*     */   private SIOCPR sio;
/*     */   
/*     */   private History history;
/*     */   
/* 118 */   private byte[] docData = null;
/*     */   
/*     */   private boolean useClean;
/*     */ 
/*     */   
/*     */   public void unregister() {}
/*     */ 
/*     */   
/*     */   public CPRDocumentContainer(ClientContext context, SIOCPR node, DocumentPage page, History history, HistoryLink linkFrom) throws DocumentNotFoundException, DocumentContainerConstructionException {
/* 127 */     super(context);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     this.linkFrom = null;
/*     */ 
/*     */     
/* 310 */     this.clean = null; this.sio = node; this.docPage = page; this.linkFrom = linkFrom; SIOBlob blob = node.getDocument(LocaleInfoProvider.getInstance().getLocale(context.getLocale())); if (blob == null || (this.docData = blob.getData()) == null) { log.error("unable to find document:" + node.getID()); throw new DocumentNotFoundException((String)node.getProperty(SIOProperty.LU)); }  this.history = history; try { this.encoding = blob.getCharset(); this.encoding = (this.encoding != null) ? StringUtilities.replace(this.encoding, "'", "") : null; } catch (Exception e) { log.error("unable to create document container for node-id:" + node.getID() + " exception:" + e, e); throw new DocumentContainerConstructionException(); } 
/*     */   }
/*     */   public boolean init() { boolean noNewPage = true; if (this.docData != null) try { noNewPage = init(this.docData); } catch (UncheckedInterruptedException e) { throw e; } catch (Exception e) { log.error("unable to change Document: " + e, e); }   return noNewPage; }
/*     */   private boolean init(byte[] xmlData) throws Exception { boolean noNewPage = true; ByteArrayInputStream bais = new ByteArrayInputStream(xmlData); ByteArrayOutputStream baos = new ByteArrayOutputStream(); Util.checkInterruption2(); Document doc = parseXml(bais); if (doc == null) { log.error("Failed to parse XML document. Fallback to original stream (no navigation) "); } else { noNewPage = ProcessDocument(doc); if (noNewPage) { byte[] trans = writeXml(doc); bais = new ByteArrayInputStream(trans); }  }  if (noNewPage) { Util.checkInterruption2(); synchronized (transformer) { transformer.transform(new StreamSource(bais), new StreamResult(baos)); }  this.body = new String(baos.toByteArray(), "utf-8"); Element elem = DomUtil.firstChildOfCurPlane(doc, "CPRFragment"); if (elem != null) { elem = DomUtil.firstChildOfCurPlane(doc, "Picture"); if (elem == null) { String addLinesStr = ((ApplicationContext)this.context.getApplicationContext()).getProperty("component.si.cpr.trailing.lines"); int addLines = (addLinesStr == null) ? 0 : Integer.parseInt(addLinesStr); String append = new String(); for (int ind = 0; ind < addLines; ind++)
/*     */             append = append + "<br>";  this.body += append; }  }  }  return noNewPage; } private boolean ProcessDocument(Document doc) { boolean noNewPage = true; try { Node data = XPathAPI.selectSingleNode(doc, "/CPRFragment/DataMap"); if (data == null) { data = doc.createElement("DataMap"); doc.insertBefore(data, doc.getChildNodes().item(0)); }  Element val = doc.createElement("Value"); val.appendChild(doc.createTextNode("yes")); val.setAttribute("Key", "html-nohead"); data.appendChild(val); val = doc.createElement("Value"); val.appendChild(doc.createTextNode("pic/cpr/v-measure.gif")); val.setAttribute("Key", "std-icon"); data.appendChild(val); } catch (TransformerException e) {} noNewPage = TransformLinks(doc); if (noNewPage)
/* 315 */       TransformImages(doc);  return noNewPage; } private static final String LINK = new String("LINK_SIO:ID:"); private void TransformImages(Document doc) { try { NodeList nodelist = XPathAPI.selectNodeList(doc, "CPRFragment/Picture/PictureInfo");
/*     */ 
/*     */       
/* 318 */       for (int i = 0; i < nodelist.getLength(); i++) {
/* 319 */         Util.checkInterruption2();
/*     */         
/* 321 */         Element elem = (Element)nodelist.item(i);
/*     */         
/* 323 */         String oTag = elem.getAttribute("IllusID");
/*     */         
/* 325 */         int iLen = 0;
/* 326 */         int iStart = oTag.indexOf(LINKIMG);
/* 327 */         if (iStart == -1) {
/* 328 */           iStart = oTag.indexOf(LINK);
/* 329 */           iLen = LINK.length();
/*     */         } else {
/* 331 */           iLen = LINKIMG.length();
/*     */         } 
/*     */         
/* 334 */         if (iStart != -1) {
/* 335 */           final String id = oTag.substring(iStart + iLen);
/* 336 */           SIOBlob blob = SIDataAdapterFacade.getInstance(this.context).getSI().getImage(Integer.parseInt(id));
/* 337 */           boolean isGraphic = false;
/* 338 */           if (blob == null || blob.getData() == null) {
/* 339 */             blob = SIDataAdapterFacade.getInstance(this.context).getSI().getGraphic(Integer.parseInt(id));
/* 340 */             isGraphic = true;
/*     */           } 
/* 342 */           if (blob == null || blob.getData() == null) {
/* 343 */             elem.setAttribute("type", "none");
/* 344 */             elem.setAttribute("IllusID", "");
/*     */           } else {
/*     */             
/* 347 */             final byte[] data = blob.getData();
/* 348 */             String type = blob.getMime();
/* 349 */             if ((isGraphic && type.equals("0")) || type.indexOf("tif") != -1 || type.indexOf("TIF") != -1) {
/* 350 */               type = "application/x-TIFF-HotSpot";
/* 351 */               elem.setAttribute("type", "tiff");
/* 352 */             } else if (type.indexOf("png") != -1 || type.indexOf("p") != -1) {
/* 353 */               elem.setAttribute("type", "png");
/* 354 */               LinkElement link = new LinkElement(this.context.createID(), null) {
/*     */                   public String getLabel() {
/* 356 */                     return null;
/*     */                   }
/*     */                   
/*     */                   public String getTargetFrame() {
/* 360 */                     return "detail";
/*     */                   }
/*     */                   
/*     */                   public Object onClick(Map params) {
/* 364 */                     DetailViewPage detailPage = new DetailViewPage(CPRDocumentContainer.this.context, Integer.parseInt(id), true);
/* 365 */                     return detailPage;
/*     */                   }
/*     */                   
/*     */                   public String getHtmlCode(Map params) {
/* 369 */                     return this.renderingCallback.getLink();
/*     */                   }
/*     */                 };
/*     */ 
/*     */               
/* 374 */               addElement((HtmlElement)link);
/* 375 */               elem.setAttribute("DetailHref", link.getHtmlCode(null));
/* 376 */               elem.setAttribute("DetailHrefSrc", "pic/si/zoom.gif");
/* 377 */               elem.setAttribute("DetailHrefAlt", this.context.getMessage("detailed.picture"));
/*     */             } else {
/*     */               
/* 380 */               elem.setAttribute("type", "none");
/*     */             } 
/*     */             
/* 383 */             final String ofType = type;
/* 384 */             ImageElement image = new ImageElement() {
/*     */                 protected String getImageURL() {
/* 386 */                   return DynamicResourceController.getInstance(CPRDocumentContainer.this.context).getURL(data, ofType, id + ofType);
/*     */                 }
/*     */                 
/*     */                 public String getHtmlCode(Map params) {
/* 390 */                   return getImageURL();
/*     */                 }
/*     */               };
/* 393 */             addElement((HtmlElement)image);
/* 394 */             elem.setAttribute("IllusID", image.getHtmlCode(null));
/*     */           } 
/*     */         } 
/*     */       }  }
/* 398 */     catch (TransformerException e) {} }
/*     */   
/*     */   private static final String LINKBM = new String(":BM:"); private static final String GRLINKBM = new String(":BM:-"); private static final String LINKIMG = new String("LINK_IMAGE:ID:"); private HistoryLink linkFrom; private HistoryLink clean;
/*     */   
/*     */   private boolean TransformLinks(Document doc) {
/* 403 */     boolean noNewPage = true;
/*     */ 
/*     */     
/*     */     try {
/* 407 */       int plane = 65;
/*     */       
/* 409 */       NodeList nodelist = XPathAPI.selectNodeList(doc, "//ChapterInfo");
/*     */       
/* 411 */       if (0 < nodelist.getLength()) {
/* 412 */         Element elem = (Element)nodelist.item(0);
/* 413 */         String planeStr = elem.getAttribute("TreeLevelPrefix");
/* 414 */         if (planeStr.length() > 0) {
/* 415 */           plane = planeStr.charAt(0);
/* 416 */           if (plane == 65) {
/* 417 */             this.history.clearStack();
/*     */           }
/*     */         } 
/*     */       } 
/* 421 */       Map<Object, Object> stepToInput = new HashMap<Object, Object>();
/* 422 */       Map<Object, Object> ElementsToYesLinks = new HashMap<Object, Object>();
/* 423 */       Map<Object, Object> ElementsToNoLinks = new HashMap<Object, Object>();
/* 424 */       nodelist = XPathAPI.selectNodeList(doc, "//Link");
/*     */       
/* 426 */       DocumentHistory docHist = new DocumentHistory(doc, this.sio.getID().intValue());
/* 427 */       docHist.setLinkFrom(this.linkFrom);
/*     */       
/* 429 */       Map<Object, Object> bookMarksToLinks = new HashMap<Object, Object>();
/* 430 */       String oBackBM = null;
/* 431 */       String oBackSIOID = null;
/* 432 */       String backLabel = null;
/* 433 */       DocumentHistory lastRes = this.history.getLastDocHist();
/* 434 */       if (this.linkFrom != null && this.linkFrom.hasValidBM()) {
/* 435 */         LinkAction lA = this.linkFrom.getLinkAction();
/* 436 */         DocumentHistory curDoc = this.history.getCurDocHist();
/* 437 */         if (lA != null && lA instanceof PageLinkBackAction && curDoc != null) {
/* 438 */           docHist.setLinkFrom(curDoc.getLinkFrom());
/*     */         }
/* 440 */         if (lA == null || !(lA instanceof PageLinkBackAction) || curDoc == null || curDoc.getLinkFrom() == null || curDoc.getLinkFrom().hasValidBM()) {
/* 441 */           if (lastRes != null) {
/* 442 */             StepResults stRes = (StepResults)lastRes.getLast();
/* 443 */             oBackBM = stRes.getBookMark();
/* 444 */             oBackSIOID = Integer.toString(lastRes.getSioId());
/* 445 */             backLabel = lastRes.getChapterNr() + " " + stRes.getLinkLabel();
/*     */           } 
/* 447 */           String oldBM = this.linkFrom.getTargetBookmark2();
/* 448 */           if (oldBM != "") {
/*     */             
/* 450 */             List<HistoryLink> linkLi = new LinkedList();
/* 451 */             bookMarksToLinks.put(oldBM, linkLi);
/* 452 */             linkLi.add(this.linkFrom);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 457 */       HistoryLink oldLinkFrom = (lastRes == null) ? null : lastRes.getLinkFrom();
/*     */       
/* 459 */       for (int i = 0; i < nodelist.getLength(); i++) {
/* 460 */         HistoryLink dl; Util.checkInterruption2();
/*     */         
/* 462 */         Element elem = (Element)nodelist.item(i);
/* 463 */         StepResults stepResults = new StepResults();
/* 464 */         Element stepRoot = stepResults.init(elem);
/*     */         
/* 466 */         String oTag = elem.getAttribute("Tag");
/* 467 */         String oSIOID = null;
/* 468 */         String oBM = null;
/* 469 */         boolean backLink = false;
/* 470 */         boolean genericReturn = false;
/* 471 */         String tagName = stepResults.getTag();
/* 472 */         if (GRLINKBM.equals(oTag)) {
/*     */ 
/*     */           
/* 475 */           genericReturn = true;
/* 476 */           DocumentHistory lastDocument = this.history.getLastDocHist();
/* 477 */           if (lastDocument != null) {
/*     */ 
/*     */ 
/*     */             
/* 481 */             StepResults stRes = (StepResults)lastDocument.getLast();
/* 482 */             oBM = stRes.getBookMark();
/* 483 */             oSIOID = Integer.toString(lastDocument.getSioId());
/* 484 */             backLabel = lastDocument.getChapterNr() + " " + stRes.getLinkLabel();
/* 485 */             backLink = true;
/*     */           } else {
/*     */             
/* 488 */             oBM = "-";
/* 489 */             backLabel = this.context.getLabel((plane == 65) ? "cpr.End.of.Diagnostic.Procedure" : "cpr.End.of.Diagnosis");
/*     */           } 
/* 491 */         } else if (oBackSIOID != null && stepResults.getLinkType() == 1 && (tagName.equals("DLPStep") || tagName.equals("TypeIIITestStep"))) {
/*     */ 
/*     */           
/* 494 */           oSIOID = oBackSIOID;
/* 495 */           oBM = oBackBM;
/* 496 */           backLink = true;
/*     */         } else {
/* 498 */           int iStart = oTag.indexOf(LINK);
/*     */           
/* 500 */           int iStartBM = oTag.indexOf(LINKBM);
/* 501 */           if (iStartBM < 0) {
/* 502 */             iStartBM = oTag.length();
/*     */           }
/* 504 */           if (iStart >= 0) {
/* 505 */             oSIOID = oTag.substring(iStart + LINK.length(), iStartBM);
/*     */           }
/* 507 */           if (iStartBM != oTag.length()) {
/* 508 */             oBM = oTag.substring(iStartBM + LINKBM.length());
/*     */           }
/*     */         } 
/*     */         
/* 512 */         if (oSIOID != null) {
/*     */           PageLinkForwardAction pageLinkForwardAction;
/* 514 */           if (backLink) {
/* 515 */             PageLinkBackAction pageLinkBackAction = new PageLinkBackAction(this.context, oSIOID, this.docPage, oldLinkFrom);
/*     */           } else {
/* 517 */             pageLinkForwardAction = new PageLinkForwardAction(this.context, oSIOID, this.docPage);
/*     */           } 
/* 519 */           dl = new HistoryLink(this.context, oBM, this.history, docHist, stepResults, this.sio, (LinkAction)pageLinkForwardAction);
/*     */         } else {
/* 521 */           dl = new HistoryLink(this.context, oBM, this.history, docHist, stepResults, this.sio, (LinkAction)new BookMarkLinkAction());
/* 522 */           List<HistoryLink> linkLi = (List)bookMarksToLinks.get(oBM);
/* 523 */           if (linkLi == null) {
/* 524 */             linkLi = new LinkedList();
/* 525 */             linkLi.add(dl);
/* 526 */             bookMarksToLinks.put(oBM, linkLi);
/*     */           } else {
/*     */             
/* 529 */             linkLi.add(dl);
/*     */           } 
/*     */         } 
/*     */         
/* 533 */         dl.setIsLinkPage((this.linkFrom != null));
/* 534 */         dl.setElem(elem);
/* 535 */         dl.setPlane(plane);
/* 536 */         if (backLink || genericReturn) {
/* 537 */           dl.setLabel(backLabel);
/* 538 */           DomUtil.setValue(elem, backLabel);
/*     */         } else {
/*     */           
/* 541 */           dl.setLabel(DomUtil.valueFrom(elem));
/*     */         } 
/* 543 */         dl.getHtmlCode(null);
/* 544 */         elem.setAttribute("Tag", dl.getHref());
/* 545 */         addElement((HtmlElement)dl);
/* 546 */         if (stepRoot != null) {
/* 547 */           if (stepResults.getLinkType() == 1) {
/* 548 */             ElementsToYesLinks.put(stepRoot, dl);
/* 549 */           } else if (stepResults.getLinkType() == -1) {
/* 550 */             ElementsToNoLinks.put(stepRoot, dl);
/*     */           } 
/* 552 */           TransformInputElements(stepToInput, dl, stepRoot);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 559 */       noNewPage = insertLinks(doc, docHist, plane, bookMarksToLinks, ElementsToYesLinks, ElementsToNoLinks);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 568 */     catch (TransformerException e) {}
/*     */     
/* 570 */     return noNewPage;
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
/*     */   static abstract class CreateHistoryLink
/*     */   {
/* 587 */     private HistoryLink created = null;
/*     */     
/*     */     public abstract HistoryLink create2(StepResults param1StepResults);
/*     */     
/*     */     public HistoryLink create(StepResults stepResults) {
/* 592 */       this.created = create2(stepResults);
/* 593 */       return this.created;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HistoryLink getCreated() {
/* 602 */       return this.created;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset() {
/* 612 */       this.created = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean insertLinks(Document document, final DocumentHistory docHist, int plane, Map bookMarksToLinks, Map ElementsToYesLinks, Map ElementsToNoLinks) {
/* 618 */     boolean noNewPage = true;
/* 619 */     NodeList childs = document.getChildNodes();
/* 620 */     Element elem = null;
/* 621 */     for (int ind = 0; ind < childs.getLength(); ind++) {
/* 622 */       Node node = childs.item(ind);
/* 623 */       if (node.getNodeType() == 1) {
/* 624 */         elem = (Element)node;
/*     */         break;
/*     */       } 
/*     */     } 
/* 628 */     DocumentHistory lastRes = this.history.getLastDocHist();
/* 629 */     if (lastRes != null && lastRes.getSioId() == this.sio.getID().intValue()) {
/* 630 */       this.history.popX();
/* 631 */       lastRes = this.history.getLastDocHist();
/*     */     } 
/* 633 */     final HistoryLink oldLinkFrom = (lastRes == null) ? null : lastRes.getLinkFrom();
/* 634 */     if (elem != null) {
/*     */       CreateHistoryLink historyLink; String backLabel;
/* 636 */       Map LabelToVal = dataMapPath.getValuesOfAttr(elem, "Key");
/* 637 */       String label = (String)LabelToVal.get("repair-label");
/* 638 */       if (label == null)
/* 639 */         label = ""; 
/* 640 */       String yesLabel = (String)LabelToVal.get("yes-label");
/* 641 */       if (yesLabel == null)
/* 642 */         yesLabel = ""; 
/* 643 */       String noLabel = (String)LabelToVal.get("no-label");
/* 644 */       if (noLabel == null) {
/* 645 */         noLabel = "";
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 654 */       if (lastRes != null && lastRes.size() > 0) {
/* 655 */         StepResults stRes = (StepResults)lastRes.getLast();
/* 656 */         final String oBM = stRes.getBookMark();
/* 657 */         final String oSIOID = Integer.toString(lastRes.getSioId());
/* 658 */         historyLink = new CreateHistoryLink() {
/*     */             public HistoryLink create2(StepResults stepResults) {
/* 660 */               HistoryLink hs = new HistoryLink(CPRDocumentContainer.this.context, oBM, CPRDocumentContainer.this.history, docHist, stepResults, CPRDocumentContainer.this.sio, (LinkAction)new PageLinkBackAction(CPRDocumentContainer.this.context, oSIOID, CPRDocumentContainer.this.docPage, oldLinkFrom));
/* 661 */               hs.setIsLinkPage((CPRDocumentContainer.this.linkFrom != null));
/* 662 */               return hs;
/*     */             }
/*     */           };
/* 665 */         backLabel = lastRes.getChapterNr() + " " + stRes.getLinkLabel();
/*     */       } else {
/* 667 */         historyLink = new CreateHistoryLink() {
/*     */             public HistoryLink create2(StepResults stepResults) {
/* 669 */               HistoryLink hs = new HistoryLink(CPRDocumentContainer.this.context, "", CPRDocumentContainer.this.history, docHist, stepResults, CPRDocumentContainer.this.sio, new LinkAction());
/* 670 */               hs.setIsLinkPage((CPRDocumentContainer.this.linkFrom != null));
/* 671 */               return hs;
/*     */             }
/*     */           };
/* 674 */         backLabel = this.context.getLabel((plane == 65) ? "cpr.End.of.Diagnostic.Procedure" : "cpr.End.of.Diagnosis");
/*     */       } 
/* 676 */       ClientContext context = this.context;
/*     */       
/*     */       try {
/* 679 */         ILVCAdapter lvcAdapter = this.sio.getILVCAdapter();
/* 680 */         ILVCAdapter.Retrieval lvcr = lvcAdapter.createRetrievalImpl();
/*     */         
/* 682 */         String posVcrStr = VehicleOptionsData.getInstance(context).getPositiveOptionsVCR();
/* 683 */         String negVcrStr = VehicleOptionsData.getInstance(context).getNegativeOptionsVCR();
/* 684 */         VCR posVcr = (posVcrStr != null && !posVcrStr.equals("[null-VCR]")) ? lvcAdapter.makeVCR(posVcrStr) : VCR.NULL;
/* 685 */         VCR negVcr = (negVcrStr != null && !negVcrStr.equals("[null-VCR]")) ? lvcAdapter.makeVCR(negVcrStr) : VCR.NULL;
/* 686 */         VCR vcr = lvcAdapter.toVCR(VCFacade.getInstance(context).getCfg());
/*     */         
/* 688 */         Map<Object, Object> bookMarksToElements = new HashMap<Object, Object>();
/* 689 */         insertFIPLinks(document, elem, "FIPResultStep", label, plane, historyLink, bookMarksToElements);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 694 */         NominalValueParser nomPars = new NominalValueParser(vcr, posVcr, negVcr, lvcr);
/* 695 */         TestStepRestrictionParser restr = new TestStepRestrictionParser(vcr, posVcr, negVcr, bookMarksToLinks, ElementsToYesLinks, ElementsToNoLinks, this.linkFrom, bookMarksToElements, lvcr);
/* 696 */         insertYesNoLinksAndProcessVCR(document, elem, "TestStep", backLabel, plane, historyLink, yesLabel, noLabel, (RestrictionParser)restr, ElementsToYesLinks, ElementsToNoLinks, (RestrictionParser)nomPars, bookMarksToElements);
/* 697 */         insertYesNoLinksAndProcessVCR(document, elem, "DLPStep", backLabel, plane, historyLink, yesLabel, noLabel, (RestrictionParser)nomPars, bookMarksToElements);
/* 698 */         noNewPage = restr.replaceBookmark();
/*     */         
/* 700 */         if (noNewPage) {
/* 701 */           insertYesNoLinks(document, elem, "TypeIIITestStep", backLabel, plane, historyLink, yesLabel, noLabel, bookMarksToElements);
/* 702 */           String yesLabelSCT = this.context.getLabel("cpr.Done");
/* 703 */           insertYesNoLinks(document, elem, "SCTTestStep", backLabel, plane, historyLink, yesLabelSCT, noLabel, bookMarksToElements);
/* 704 */           DomUtil.doForDescendants(elem, "ParameterVCR", (DomUtil.ElemWorker)new ParameterVcrParser(vcr, posVcr, negVcr, lvcr));
/* 705 */           DomUtil.doForDescendants(elem, "Terminals", (DomUtil.ElemWorker)new TerminalParser(vcr, posVcr, negVcr, lvcr));
/* 706 */           if (this.clean != null) {
/* 707 */             restr.clean(this.clean);
/* 708 */           } else if (!this.useClean) {
/* 709 */             restr.clean();
/*     */           } 
/*     */         } 
/*     */         
/* 713 */         if (this.linkFrom != null) {
/* 714 */           this.linkFrom.setTargetBookMark(restr.getLinkFrom().getTargetBookmark2());
/*     */         } else {
/* 716 */           docHist.setLinkFrom(restr.getLinkFrom());
/*     */         } 
/* 718 */       } catch (UncheckedInterruptedException e) {
/* 719 */         throw e;
/* 720 */       } catch (Exception e) {
/* 721 */         log.error("Failed to process restrictions, exception:" + e, e);
/* 722 */         throw new ExceptionWrapper(e);
/*     */       } 
/*     */     } 
/*     */     
/* 726 */     return noNewPage;
/*     */   }
/*     */   
/*     */   void insertYesNoLinks(final Document document, Element elem, String descendants, final String backLabel, final int plane, final CreateHistoryLink cH, final String yesLabel, String noLabel, final Map bookMarksToElements) {
/* 730 */     DomUtil.doForDescendants(elem, descendants, new DomUtil.ElemWorker() {
/*     */           public void work(Element elem) {
/* 732 */             CPRDocumentContainer.this.insertYesNoLink(document, elem, backLabel, plane, cH, yesLabel, "YesLink");
/*     */             
/* 734 */             bookMarksToElements.put(elem.getAttribute("Tag"), elem);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void insertYesNoLinksAndProcessVCR(final Document document, Element elem, String descendants, final String backLabel, final int plane, final CreateHistoryLink cH, final String yesLabel, String noLabel, final RestrictionParser restrPars, final Map bookMarksToElements) {
/* 742 */     DomUtil.doForDescendants(elem, descendants, new DomUtil.ElemWorker() {
/*     */           public void work(Element elem) {
/* 744 */             CPRDocumentContainer.this.insertYesNoLink(document, elem, backLabel, plane, cH, yesLabel, "YesLink");
/*     */             
/* 746 */             restrPars.work(elem);
/* 747 */             bookMarksToElements.put(elem.getAttribute("Tag"), elem);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   void insertYesNoLinksAndProcessVCR(final Document document, Element elem, String descendants, final String backLabel, final int plane, final CreateHistoryLink cH, final String yesLabel, String noLabel, final RestrictionParser restrPars, final Map ElementsToYesLinks, Map ElementsToNoLinks, final RestrictionParser nomPars, final Map bookMarksToElements) {
/* 753 */     DomUtil.doChangeForDescendants(elem, descendants, new DomUtil.ElemWorker() {
/*     */           public void work(Element elem) {
/* 755 */             cH.reset();
/* 756 */             CPRDocumentContainer.this.insertYesNoLink(document, elem, backLabel, plane, cH, yesLabel, "YesLink");
/* 757 */             if (cH.getCreated() != null) {
/* 758 */               ElementsToYesLinks.put(elem, cH.getCreated());
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 766 */             restrPars.work(elem);
/* 767 */             nomPars.work(elem);
/* 768 */             bookMarksToElements.put(elem.getAttribute("Tag"), elem);
/*     */           }
/*     */           
/*     */           public void init(Element elem) {
/* 772 */             restrPars.init(elem);
/*     */           }
/*     */           
/*     */           public void end(NodeList nL) {
/* 776 */             restrPars.end(nL);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void insertYesNoLink(Document document, Element elem, String backLabel, int plane, CreateHistoryLink cH, String yesNoLabel, String yesNoTag) {
/* 782 */     NodeList nodeList = elem.getElementsByTagName(yesNoTag);
/* 783 */     Element linkElem = null;
/* 784 */     if (nodeList.getLength() == 0) {
/*     */       
/* 786 */       Element newElem = document.createElement(yesNoTag);
/* 787 */       elem.appendChild(newElem);
/* 788 */       Element labelElem = document.createElement("Label");
/* 789 */       newElem.appendChild(labelElem);
/* 790 */       Text text = document.createTextNode(yesNoLabel);
/* 791 */       labelElem.appendChild(text);
/* 792 */       String value = yesNoLabel.trim();
/* 793 */       if (value.length() > 0 && value.charAt(value.length() - 1) == ':') {
/* 794 */         value = value.substring(0, value.length() - 1);
/*     */       }
/* 796 */       linkElem = insertLink(document, elem, backLabel, plane, cH, value);
/* 797 */       newElem.appendChild(linkElem);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void insertFIPLinks(final Document document, Element elem, String descendants, final String Label, final int plane, final CreateHistoryLink cH, final Map bookMarksToElements) {
/* 803 */     DomUtil.doForDescendants(elem, descendants, new DomUtil.ElemWorker() {
/*     */           public void work(Element elem) {
/* 805 */             CPRDocumentContainer.this.insertLink(document, elem, Label, plane, cH, Label);
/* 806 */             bookMarksToElements.put(elem.getAttribute("Tag"), elem);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   Element insertLink(Document document, Element elem, String Label, int plane, CreateHistoryLink cH, String value) {
/* 813 */     Element newElem = document.createElement("Link");
/* 814 */     StepResults stepResults = new StepResults();
/* 815 */     stepResults.init2(elem);
/* 816 */     stepResults.addValue(value);
/*     */     
/* 818 */     HistoryLink pL = cH.create(stepResults);
/* 819 */     newElem.setAttribute("Tag", pL.getHref());
/* 820 */     elem.appendChild(newElem);
/* 821 */     Text text = document.createTextNode(Label);
/* 822 */     newElem.appendChild(text);
/* 823 */     pL.setPlane(plane);
/*     */     
/* 825 */     addElement((HtmlElement)pL);
/* 826 */     return newElem;
/*     */   }
/*     */   
/*     */   void TransformInputElements(Map<Element, List> stepToInput, HistoryLink historyLink, Element elem) {
/* 830 */     List<UnitInputElement> inputElements = (List)stepToInput.get(elem);
/* 831 */     if (inputElements == null) {
/* 832 */       inputElements = new LinkedList();
/* 833 */       stepToInput.put(elem, inputElements);
/* 834 */       NodeList subNodes = elem.getElementsByTagName("Icon");
/* 835 */       for (int ind = 0; ind < subNodes.getLength(); ind++) {
/* 836 */         Util.checkInterruption2();
/* 837 */         Element inpNode = (Element)subNodes.item(ind);
/* 838 */         String name = this.context.createID();
/* 839 */         inpNode.setAttribute("name", name);
/* 840 */         UnitInputElement inpElem = new UnitInputElement(name, 15, 255);
/* 841 */         addElement((HtmlElement)inpElem);
/* 842 */         inputElements.add(inpElem);
/* 843 */         inpElem.setUnit(inpNode.getAttribute("Unit"));
/*     */       } 
/*     */     } 
/*     */     
/* 847 */     historyLink.setInputElements(inputElements);
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
/*     */   public byte[] writeXml(Document doc) {
/*     */     try {
/* 877 */       Source source = new DOMSource(doc);
/*     */       
/* 879 */       ByteArrayOutputStream str = null;
/*     */       
/* 881 */       StreamResult result = new StreamResult(str = new ByteArrayOutputStream());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 886 */       synchronized (xformer) {
/* 887 */         xformer.transform(source, result);
/*     */       } 
/* 889 */       return str.toByteArray();
/* 890 */     } catch (TransformerConfigurationException e) {
/* 891 */       log.error("unable to write XML Document:" + e, e);
/* 892 */     } catch (TransformerException e) {
/* 893 */       log.error("unable to write XML Document:" + e, e);
/*     */     } 
/*     */     
/* 896 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Document parseXml(InputStream sstream) {
/*     */     
/* 905 */     try { DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 906 */       factory.setValidating(false);
/*     */       
/* 908 */       Document doc = factory.newDocumentBuilder().parse(sstream);
/* 909 */       return doc; }
/* 910 */     catch (SAXException e)
/* 911 */     { log.warn("unable to parse xml, returning null - exception: " + e, e); }
/*     */     
/* 913 */     catch (ParserConfigurationException e) {  }
/* 914 */     catch (IOException e) {}
/*     */     
/* 916 */     return null;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*     */     try {
/* 921 */       SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/* 922 */       VCR vcr = SIDataAdapterFacade.getInstance(this.context).getLVCAdapter().toVCR(VCFacade.getInstance(this.context).getCfg());
/* 923 */       this.history.checkVcr(vcr);
/* 924 */     } catch (Exception e) {
/* 925 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */     
/* 928 */     return this.body;
/*     */   }
/*     */   
/*     */   public String getStyleSheet() {
/* 932 */     return this.styleSheet;
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 936 */     return this.title;
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 940 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public SIO getSIO() {
/* 944 */     return (SIO)this.sio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HistoryLink getLinkFrom() {
/* 953 */     return this.linkFrom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkFrom(HistoryLink linkFrom) {
/* 963 */     this.linkFrom = linkFrom;
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
/*     */   public void setClean(HistoryLink clean, boolean useClean) {
/* 979 */     this.clean = clean;
/* 980 */     this.useClean = useClean;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\CPRDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */