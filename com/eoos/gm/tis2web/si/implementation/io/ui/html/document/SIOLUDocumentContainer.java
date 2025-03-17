/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*     */ 
/*     */ import com.eoos.datatype.MIME;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.dls.server.SoftwareKeyCheckServer;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.SecurityToken;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.CookieUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.URLTweak;
/*     */ import com.eoos.gm.tis2web.frame.implementation.jnlp.JnlpDownloadServlet;
/*     */ import com.eoos.gm.tis2web.lt.service.LTService;
/*     */ import com.eoos.gm.tis2web.si.ResourceServlet;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.SIODocumentFacade;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs.RefDocDialog;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs.input.ComponentParser;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.replacer.Replacer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tiff.DetailViewPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.IFrameElement;
/*     */ import com.eoos.html.element.ImageElement;
/*     */ import com.eoos.html.element.ListElement;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.scsm.v2.util.UncheckedInterruptedException;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIOLUDocumentContainer
/*     */   extends DocumentContainer
/*     */   implements Replacer.ReplacerCallback
/*     */ {
/*  71 */   private static Logger log = Logger.getLogger(SIOLUDocumentContainer.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  76 */       template = ApplicationContext.getInstance().loadFile(SIOLUDocumentContainer.class, "silucontainer.html", null).toString();
/*  77 */     } catch (Exception e) {
/*  78 */       log.error("unable to load template - error:" + e, e);
/*  79 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*  83 */   private String body = null;
/*     */   
/*  85 */   private String bodyStyleTagAttribute = null;
/*     */   
/*  87 */   private String styleSheet = null;
/*     */   
/*  89 */   private String styleTag = null;
/*     */   
/*     */   public String getStyleTag() {
/*  92 */     return this.styleTag;
/*     */   }
/*     */   
/*  95 */   private String title = null;
/*     */   
/*  97 */   private String encoding = null;
/*     */   
/*  99 */   private ListElement relatedLUElement = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DocumentPage docPage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SIO sio;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   private static final Pattern PATTERN_TECHDISPLAY = Pattern.compile("(?is)<a\\s+href\\s*=\\s*[\\\"\\']?((tech31|tech32)_(.*?))[\\\"\\']?(\\s+.*?)?>(.*?)</a>");
/*     */   
/*     */   private static final long DOWNLOAD_REGISTRATION_VALIDITY = 3600000L;
/*     */   private static final String TECH_JNLP;
/*     */   
/*     */   public SIOLUDocumentContainer(final ClientContext context, SIOLU node, DocumentPage docPage) throws DocumentNotFoundException, DocumentContainerConstructionException {
/* 122 */     super(context);
/* 123 */     this.sio = (SIO)node;
/* 124 */     this.docPage = docPage;
/*     */     
/*     */     try {
/* 127 */       Util.checkInterruption2();
/*     */       
/* 129 */       SIOBlob blob = node.getDocument(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/* 130 */       byte[] docData = null;
/* 131 */       if (blob == null || (docData = blob.getData()) == null) {
/* 132 */         log.warn("unable to find document:" + node.getID());
/* 133 */         throw new DocumentNotFoundException((String)node.getProperty(SIOProperty.LU));
/*     */       } 
/*     */       
/* 136 */       Util.checkInterruption2();
/*     */       
/* 138 */       this.encoding = blob.getCharset();
/* 139 */       this.encoding = (this.encoding != null) ? StringUtilities.replace(this.encoding, "'", "") : null;
/* 140 */       String html = new String(docData, this.encoding);
/*     */       
/* 142 */       if (html.indexOf("Microsoft Word") > 0) {
/* 143 */         int styleTagStart = html.indexOf("<style>");
/* 144 */         int styleTagEnd = html.indexOf("</style>");
/* 145 */         if (styleTagStart > 0 && styleTagEnd > 0) {
/* 146 */           this.styleTag = html.substring(styleTagStart, styleTagEnd + 8);
/*     */         }
/*     */       } 
/*     */       
/* 150 */       init(html);
/*     */       
/* 152 */       List relatedLUs = node.getRelatedLUs();
/* 153 */       if (relatedLUs != null && relatedLUs.size() != 0) {
/* 154 */         this.relatedLUElement = new RelatedLUListElement(this, relatedLUs);
/* 155 */         addElement((HtmlElement)this.relatedLUElement);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 160 */       StringBuffer tmp = new StringBuffer(this.body);
/* 161 */       Util.replace(tmp, PATTERN_TECHDISPLAY, new Util.ReplacementCallback()
/*     */           {
/*     */             public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/* 164 */               final String techID = matcherCallback.getGroup(2);
/* 165 */               final String dataID = matcherCallback.getGroup(3);
/* 166 */               final String content = matcherCallback.getGroup(5);
/*     */               
/* 168 */               LinkElement linkElement = new LinkElement(context.createID(), null)
/*     */                 {
/*     */                   public Object onClick(Map submitParams)
/*     */                   {
/* 172 */                     HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/* 173 */                     return SIOLUDocumentContainer.this.retrieveTechJNLP(techID, dataID, request);
/*     */                   }
/*     */ 
/*     */                   
/*     */                   protected String getLabel() {
/* 178 */                     return content;
/*     */                   }
/*     */                 };
/*     */               
/* 182 */               SIOLUDocumentContainer.this.addElement((HtmlElement)linkElement);
/* 183 */               return linkElement.getHtmlCode(null);
/*     */             }
/*     */           });
/*     */       
/* 187 */       this.body = tmp.toString();
/* 188 */     } catch (UncheckedInterruptedException e) {
/* 189 */       throw e;
/* 190 */     } catch (Exception e) {
/* 191 */       if (!(e instanceof DocumentNotFoundException)) {
/* 192 */         log.error("unable to create document container for node-id:" + node.getID() + " - exception: " + e, e);
/*     */       }
/* 194 */       throw new DocumentContainerConstructionException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String orgDocument) {
/* 201 */     Replacer replacer = new Replacer(this);
/* 202 */     StringBuffer modDocument = replacer.apply(orgDocument);
/*     */     
/* 204 */     SIODocumentFacade df = new SIODocumentFacade(modDocument);
/* 205 */     this.body = df.getBody();
/* 206 */     String bodyTagAttributes = df.getBodyStyleAttribute();
/* 207 */     if (bodyTagAttributes == null || bodyTagAttributes.trim().length() == 0) {
/* 208 */       this.bodyStyleTagAttribute = null;
/*     */     } else {
/* 210 */       this.bodyStyleTagAttribute = bodyTagAttributes.trim();
/*     */     }  } public String replace(Replacer.TemplateReplacer replacer) { Replacer.StyleSheetReplacer ssr; Replacer.ImageFileReplacer ifr; Replacer.ImageReplacer ir; Replacer.GraphicReplacer gr; Replacer.SIODocumentLinkReplacer sIODocumentLinkReplacer; Replacer.SIOLinkListReplacer sIOLinkListReplacer; Replacer.ComponentLinkReplacer componentLinkReplacer; Replacer.FaultFinderReplacer faultFinderReplacer; Replacer.SIOCellLinkReplacer docReplacer; Replacer.DetailViewReplacer dwr; Replacer.AWLinkReplacer awlr; final String name, key; final int sioID; Replacer.SIOLink target; final int componentID, sioID, cellID; final String key, awNr; ImageElement image; LinkElement linkElement2;
/*     */     final CTOCNode component;
/*     */     LinkElement linkElement1;
/*     */     final List layers;
/*     */     LinkElement link, linkElement3;
/* 216 */     Util.checkInterruption2();
/* 217 */     switch (replacer.getType()) {
/*     */       case 4:
/* 219 */         ssr = (Replacer.StyleSheetReplacer)replacer;
/* 220 */         this.styleSheet = ssr.getKey();
/* 221 */         return null;
/*     */       
/*     */       case 2:
/* 224 */         ifr = (Replacer.ImageFileReplacer)replacer;
/* 225 */         name = ifr.getKey();
/* 226 */         MIME.getMIME(name);
/* 227 */         image = new ImageElement() {
/*     */             protected String getImageURL() {
/* 229 */               return "pic/si/" + name.toLowerCase(Locale.ENGLISH);
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 233 */               return getImageURL();
/*     */             }
/*     */           };
/* 236 */         addElement((HtmlElement)image);
/* 237 */         return image.getHtmlCode(null);
/*     */       
/*     */       case 3:
/* 240 */         ir = (Replacer.ImageReplacer)replacer;
/* 241 */         str1 = ir.getKey();
/* 242 */         image = new ImageElement() {
/*     */             protected String getImageURL() {
/* 244 */               return "si/pic/i/" + ResourceServlet.getURLSuffix(key, SIOLUDocumentContainer.this.sio.getID());
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 248 */               return Util.escapeReservedHTMLChars(getImageURL());
/*     */             }
/*     */           };
/* 251 */         addElement((HtmlElement)image);
/* 252 */         return image.getHtmlCode(null);
/*     */ 
/*     */       
/*     */       case 9:
/* 256 */         gr = (Replacer.GraphicReplacer)replacer;
/* 257 */         str1 = gr.getKey();
/* 258 */         image = new ImageElement() {
/*     */             protected String getImageURL() {
/* 260 */               return "si/pic/g/" + ResourceServlet.getURLSuffix(key, SIOLUDocumentContainer.this.sio.getID());
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 264 */               return Util.escapeReservedHTMLChars(getImageURL());
/*     */             }
/*     */           };
/* 267 */         addElement((HtmlElement)image);
/* 268 */         return image.getHtmlCode(null);
/*     */       
/*     */       case 1:
/* 271 */         sIODocumentLinkReplacer = (Replacer.SIODocumentLinkReplacer)replacer;
/* 272 */         i = Integer.parseInt(sIODocumentLinkReplacer.getKey());
/* 273 */         linkElement2 = new LinkElement(this.context.createID(), sIODocumentLinkReplacer.getBookmark()) {
/*     */             public String getLabel() {
/* 275 */               return null;
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/* 279 */               SIO sio = null;
/*     */               try {
/* 281 */                 sio = SIDataAdapterFacade.getInstance(SIOLUDocumentContainer.this.context).getSI().getSIO(sioID);
/* 282 */               } catch (IllegalArgumentException e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 287 */               DocumentPage dp = SIOLUDocumentContainer.this.docPage;
/* 288 */               DocumentContainer dc = dp.setPage(sio);
/* 289 */               dc.setPredecessor(SIOLUDocumentContainer.this);
/* 290 */               if (dp.getLinkTargetFrame() == null) {
/* 291 */                 return MainPage.getInstance(SIOLUDocumentContainer.this.context);
/*     */               }
/* 293 */               return dp.getPage(params);
/*     */             }
/*     */ 
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 298 */               return this.renderingCallback.getLink();
/*     */             }
/*     */             
/*     */             protected String getTargetFrame() {
/* 302 */               IFrameElement frame = SIOLUDocumentContainer.this.docPage.getLinkTargetFrame();
/* 303 */               return (frame == null) ? "_top" : frame.getName();
/*     */             }
/*     */           };
/* 306 */         addElement((HtmlElement)linkElement2);
/* 307 */         return linkElement2.getHtmlCode(null);
/*     */       
/*     */       case 8:
/* 310 */         sIOLinkListReplacer = (Replacer.SIOLinkListReplacer)replacer;
/* 311 */         target = determineTargetSIO(sIOLinkListReplacer.getLinks());
/* 312 */         if (target != null) {
/* 313 */           final int sioID = target.getSIO();
/* 314 */           LinkElement linkElement = new LinkElement(this.context.createID(), target.getBookmark()) {
/*     */               public String getLabel() {
/* 316 */                 return null;
/*     */               }
/*     */               
/*     */               public Object onClick(Map params) {
/* 320 */                 SIO sio = null;
/*     */                 try {
/* 322 */                   sio = SIDataAdapterFacade.getInstance(SIOLUDocumentContainer.this.context).getSI().getSIO(sioID);
/* 323 */                 } catch (IllegalArgumentException e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 328 */                 DocumentPage dp = SIOLUDocumentContainer.this.docPage;
/* 329 */                 DocumentContainer dc = dp.setPage(sio);
/* 330 */                 dc.setPredecessor(SIOLUDocumentContainer.this);
/*     */                 
/* 332 */                 return MainPage.getInstance(SIOLUDocumentContainer.this.context);
/*     */               }
/*     */               
/*     */               protected String getTargetFrame() {
/* 336 */                 return "_top";
/*     */               }
/*     */               
/*     */               public String getHtmlCode(Map params) {
/* 340 */                 return this.renderingCallback.getLink();
/*     */               }
/*     */             };
/* 343 */           addElement((HtmlElement)linkElement);
/* 344 */           return linkElement.getHtmlCode(null);
/*     */         } 
/* 346 */         return prepareLinkPopup(sIOLinkListReplacer.getLinks());
/*     */ 
/*     */       
/*     */       case 11:
/* 350 */         componentLinkReplacer = (Replacer.ComponentLinkReplacer)replacer;
/* 351 */         componentID = Integer.parseInt(componentLinkReplacer.getKey());
/* 352 */         component = lookupComponentLinks(componentID);
/* 353 */         if (component != null) {
/* 354 */           LinkElement linkElement = new LinkElement(this.context.createID(), null) {
/*     */               public String getLabel() {
/* 356 */                 return null;
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public Object onClick(Map params) {
/* 365 */                 TocTree tree = TocTree.getInstance(SIOLUDocumentContainer.this.context);
/* 366 */                 CTOCNode root = tree.getRootNode();
/* 367 */                 RefDocDialog rdd = new RefDocDialog(SIOLUDocumentContainer.this.context);
/* 368 */                 rdd.setElements((DataRetrievalAbstraction.DataCallback)new ComponentParser((SITOCElement)root, (SITOCElement)component, SIOLUDocumentContainer.this.context.getLocale()));
/* 369 */                 return new ResultObject(0, rdd.getHtmlCode(params));
/*     */               }
/*     */               
/*     */               protected String getTargetFrame() {
/* 373 */                 return "ComponentLink";
/*     */               }
/*     */               
/*     */               protected String getLink(String targetFrame) {
/* 377 */                 return "javascript:SpecialSubmit('" + this.parameterName + "','1','" + getTargetBookmark() + "','componentlink')";
/*     */               }
/*     */ 
/*     */               
/*     */               public String getHtmlCode(Map params) {
/* 382 */                 return this.renderingCallback.getLink();
/*     */               }
/*     */             };
/* 385 */           addElement((HtmlElement)linkElement);
/* 386 */           return linkElement.getHtmlCode(null);
/*     */         } 
/* 388 */         return "";
/*     */ 
/*     */       
/*     */       case 10:
/* 392 */         faultFinderReplacer = (Replacer.FaultFinderReplacer)replacer;
/* 393 */         sioID = Integer.parseInt(faultFinderReplacer.getKey());
/* 394 */         linkElement1 = new LinkElement(this.context.createID(), null) {
/*     */             public String getLabel() {
/* 396 */               return null;
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/* 400 */               SIO sio = null;
/*     */               try {
/* 402 */                 sio = SIDataAdapterFacade.getInstance(SIOLUDocumentContainer.this.context).getSI().getSIO(CTOCType.WIS, sioID);
/* 403 */               } catch (IllegalArgumentException e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 408 */               DocumentPage dp = SIOLUDocumentContainer.this.docPage;
/* 409 */               DocumentContainer dc = dp.setPage(sio);
/* 410 */               dc.setPredecessor(SIOLUDocumentContainer.this);
/*     */               
/* 412 */               return MainPage.getInstance(SIOLUDocumentContainer.this.context);
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 416 */               return this.renderingCallback.getLink();
/*     */             }
/*     */             
/*     */             protected String getTargetFrame() {
/* 420 */               return "_top";
/*     */             }
/*     */           };
/* 423 */         addElement((HtmlElement)linkElement1);
/* 424 */         return linkElement1.getHtmlCode(null);
/*     */       
/*     */       case 7:
/* 427 */         docReplacer = (Replacer.SIOCellLinkReplacer)replacer;
/* 428 */         cellID = Integer.parseInt(docReplacer.getKey());
/* 429 */         linkElement1 = new LinkElement(this.context.createID(), docReplacer.getBookmark()) {
/*     */             public String getLabel() {
/* 431 */               return null;
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/* 435 */               SIO sio = null;
/*     */               try {
/* 437 */                 TocTree tree = TocTree.getInstance(SIOLUDocumentContainer.this.context);
/* 438 */                 CTOCNode root = tree.getRootNode();
/*     */                 
/* 440 */                 String pubID = (String)root.getProperty((SITOCProperty)CTOCProperty.PublicationID);
/* 441 */                 List<Integer> links = SIDataAdapterFacade.getInstance(SIOLUDocumentContainer.this.context).getSICTOCService().getCTOC().getCellLinks(pubID, cellID);
/* 442 */                 Integer sioID = links.get(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 448 */                 sio = SIDataAdapterFacade.getInstance(SIOLUDocumentContainer.this.context).getSI().getSIO(sioID.intValue());
/* 449 */               } catch (Exception x) {}
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 454 */               DocumentPage dp = SIOLUDocumentContainer.this.docPage;
/* 455 */               DocumentContainer dc = dp.setPage(sio);
/* 456 */               dc.setPredecessor(SIOLUDocumentContainer.this);
/*     */               
/* 458 */               return MainPage.getInstance(SIOLUDocumentContainer.this.context);
/*     */             }
/*     */             
/*     */             protected String getTargetFrame() {
/* 462 */               return "_top";
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 466 */               return this.renderingCallback.getLink();
/*     */             }
/*     */           };
/* 469 */         addElement((HtmlElement)linkElement1);
/* 470 */         return linkElement1.getHtmlCode(null);
/*     */       
/*     */       case 5:
/* 473 */         dwr = (Replacer.DetailViewReplacer)replacer;
/* 474 */         key = dwr.getKey();
/* 475 */         layers = dwr.getLayerSettings();
/* 476 */         linkElement3 = new LinkElement(this.context.createID(), null) {
/*     */             public String getLabel() {
/* 478 */               return null;
/*     */             }
/*     */             
/*     */             public String getTargetFrame() {
/* 482 */               return "detail";
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/* 486 */               DetailViewPage detailPage = new DetailViewPage(SIOLUDocumentContainer.this.context, Integer.parseInt(key), layers, true);
/* 487 */               return detailPage;
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 491 */               return this.renderingCallback.getLink();
/*     */             }
/*     */           };
/* 494 */         addElement((HtmlElement)linkElement3);
/* 495 */         return linkElement3.getHtmlCode(null);
/*     */       
/*     */       case 6:
/* 498 */         awlr = (Replacer.AWLinkReplacer)replacer;
/* 499 */         awNr = awlr.getKey();
/* 500 */         link = new LinkElement(this.context.createID(), null) {
/*     */             public String getLabel() {
/* 502 */               return null;
/*     */             }
/*     */             
/*     */             protected String getTargetFrame() {
/* 506 */               return "_top";
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public Object onClick(Map params) {
/*     */               try {
/* 513 */                 LTService ltMI = (LTService)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/*     */                 
/* 515 */                 Map usrGrp2Manuf = SharedContextProxy.getInstance(SIOLUDocumentContainer.this.context).getUsrGroup2Manuf();
/* 516 */                 String country = SharedContextProxy.getInstance(SIOLUDocumentContainer.this.context).getCountry();
/* 517 */                 boolean authorized = false;
/* 518 */                 ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 519 */                 Set apps = aclMI.getAuthorizedResources("Application", usrGrp2Manuf, country);
/* 520 */                 if (apps.contains("lt")) {
/* 521 */                   authorized = true;
/*     */                 }
/* 523 */                 if (ltMI == null)
/* 524 */                   return new NotificationMessageBox(SIOLUDocumentContainer.this.context, SIOLUDocumentContainer.this.context.getLabel("error"), SIOLUDocumentContainer.this.context.getMessage("si.lt.no.instance")) {
/*     */                       public Object onOK(Map params) {
/* 526 */                         return MainPage.getInstance((ClientContext)this.context);
/*     */                       }
/*     */                     }; 
/* 529 */                 if (!authorized) {
/* 530 */                   return MainPage.getInstance(SIOLUDocumentContainer.this.context);
/*     */                 }
/*     */                 try {
/* 533 */                   ltMI.setMainWork(SIOLUDocumentContainer.this.context.getSessionID(), awNr);
/* 534 */                   return MainPage.getInstance(SIOLUDocumentContainer.this.context).switchModule((VisualModule)ltMI);
/* 535 */                 } catch (com.eoos.gm.tis2web.lt.service.LTService.LTServiceException e) {
/* 536 */                   SIOLUDocumentContainer.log.error("unable to set main work " + String.valueOf(awNr) + ", called by si document' " + SIOLUDocumentContainer.this.sio.getID() + "', for vehicle '" + VCFacade.getInstance(SIOLUDocumentContainer.this.context).getCfg().toString() + "', notifying  - exception: " + e, (Throwable)e);
/* 537 */                   return new NotificationMessageBox(SIOLUDocumentContainer.this.context, SIOLUDocumentContainer.this.context.getLabel("error"), SIOLUDocumentContainer.this.context.getMessage("si.lt.novc")) {
/*     */                       public Object onOK(Map params) {
/* 539 */                         return MainPage.getInstance((ClientContext)this.context);
/*     */                       }
/*     */                     };
/*     */                 }
/*     */               
/*     */               }
/* 545 */               catch (Exception e) {
/* 546 */                 HtmlElementContainer htmlElementContainer = getContainer();
/* 547 */                 while (htmlElementContainer.getContainer() != null) {
/* 548 */                   htmlElementContainer = htmlElementContainer.getContainer();
/*     */                 }
/* 550 */                 return htmlElementContainer;
/*     */               } 
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 555 */               String result = this.renderingCallback.getLink();
/* 556 */               return result;
/*     */             }
/*     */           };
/* 559 */         addElement((HtmlElement)link);
/* 560 */         return link.getHtmlCode(null);
/*     */     } 
/*     */     
/* 563 */     return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CTOCNode lookupComponentLinks(int componentID) {
/* 569 */     TocTree tree = TocTree.getInstance(this.context);
/* 570 */     CTOCNode root = tree.getRootNode();
/* 571 */     CTOCNode components = (CTOCNode)root.getProperty((SITOCProperty)CTOCProperty.COMPONENT_LIST);
/* 572 */     if (components != null) {
/* 573 */       List<CTOCNode> list = components.getChildren();
/* 574 */       if (list != null) {
/* 575 */         for (int i = 0; i < list.size(); i++) {
/* 576 */           CTOCNode component = list.get(i);
/* 577 */           String compid = (String)component.getProperty((SITOCProperty)CTOCProperty.COMPONENT_ID);
/* 578 */           int id = Integer.parseInt(compid);
/* 579 */           if (id == componentID) {
/* 580 */             return component;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 585 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Replacer.SIOLink determineTargetSIO(List<Replacer.SIOLink> links) {
/*     */     try {
/* 591 */       String my = VCFacade.getInstance(this.context).getCurrentModelyear();
/* 592 */       if (my != null) {
/* 593 */         for (int i = 0; i < links.size(); i++) {
/* 594 */           Replacer.SIOLink link = links.get(i);
/* 595 */           if (my.equals(link.getQualifier())) {
/* 596 */             return link;
/*     */           }
/*     */         } 
/*     */       }
/* 600 */     } catch (Exception x) {}
/*     */     
/* 602 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String prepareLinkPopup(final List links) {
/* 607 */     LinkElement link = new LinkElement(this.context.createID(), null) {
/*     */         public String getLabel() {
/* 609 */           return null;
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/* 613 */           SIOLinkDialog dialog = new SIOLinkDialog(SIOLUDocumentContainer.this.context, SIOLUDocumentContainer.this, links);
/* 614 */           return new ResultObject(0, dialog.getHtmlCode(params));
/*     */         }
/*     */         
/*     */         public String getHtmlCode(Map params) {
/* 618 */           return this.renderingCallback.getLink();
/*     */         }
/*     */       };
/* 621 */     addElement((HtmlElement)link);
/* 622 */     return link.getHtmlCode(null);
/*     */   }
/*     */   
/*     */   public synchronized String getHtmlCode(Map params) {
/* 626 */     boolean related = (this.relatedLUElement != null);
/*     */     
/* 628 */     StringBuffer code = new StringBuffer();
/* 629 */     code.append("\n<script type=\"text/javascript\">\n<!-- \nfunction SpecialSubmit(paramName,paramValue,bookmark,targetFrame) {\t\nvar _form=document.forms[0]; \t\nif (_form) {\t\t\nvar url=_form.action+\"&\"+paramName+\"=\"+paramValue+\"#\"+bookmark;\t\t\nwindow.open(url,targetFrame,\"toolbar=no, resizable=yes, menubar=yes, scrollbars=yes\");\t\t\t\n} else {\t\t\nalert(\"form not found\");\n}\n}\n// -->\n</script>");
/* 630 */     code.append(template);
/* 631 */     if (this.bodyStyleTagAttribute != null) {
/* 632 */       StringUtilities.replace(code, "{BODYSTYLE_REPEAT}", "style=\"" + this.bodyStyleTagAttribute + "\"");
/*     */     } else {
/* 634 */       StringUtilities.replace(code, "{BODYSTYLE_REPEAT}", "");
/*     */     } 
/*     */     
/* 637 */     StringUtilities.replace(code, "{LINK_BOOKMARK}", related ? ("<a href=\"#" + this.relatedLUElement.getBookmark() + "\">" + this.context.getLabel("si.related.literature.units") + "</a>") : "");
/* 638 */     StringUtilities.replace(code, "{DOCUMENT}", this.body);
/* 639 */     StringUtilities.replace(code, "{RELATED_LU_LIST}", related ? this.relatedLUElement.getHtmlCode(params) : "");
/* 640 */     if (related) {
/* 641 */       StringUtilities.replace(code, "{LINE}", "<tr><td><hr size=\"1\" ></td></tr>");
/*     */     } else {
/* 643 */       StringUtilities.replace(code, "{LINE}", "");
/*     */     } 
/*     */     
/* 646 */     return code.toString();
/*     */   }
/*     */   
/*     */   public synchronized String getStyleSheet() {
/* 650 */     return this.styleSheet;
/*     */   }
/*     */   
/*     */   public synchronized String getTitle() {
/* 654 */     return this.title;
/*     */   }
/*     */   
/*     */   public synchronized String getEncoding() {
/* 658 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public synchronized DocumentPage getDocumentPage() {
/* 662 */     return this.docPage;
/*     */   }
/*     */   
/*     */   public SIO getSIO() {
/* 666 */     return this.sio;
/*     */   }
/*     */   
/*     */   public String getBodyStyleAttribute() {
/* 670 */     return this.bodyStyleTagAttribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 678 */       TECH_JNLP = ApplicationContext.getInstance().loadTextResource("/si/client/siclient.jnlp", "utf-8");
/* 679 */     } catch (Exception e) {
/* 680 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   private ResultObject retrieveTechJNLP(String techID, String dataID, HttpServletRequest request) {
/*     */     URL codebase;
/* 685 */     StringBuffer jnlp = new StringBuffer(TECH_JNLP);
/*     */     
/* 687 */     String cookie = request.getHeader("Cookie");
/*     */     
/* 689 */     String codeBaseURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("component.si.url.download.server"), request);
/*     */ 
/*     */     
/*     */     try {
/* 693 */       codebase = JnlpDownloadServlet.prepareDownloadPermission(this.context, new URL(codeBaseURL), 3600000L);
/* 694 */     } catch (MalformedURLException e) {
/* 695 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 698 */     String dispatchURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("frame.url.task.execution"), request);
/*     */     
/* 700 */     StringUtilities.replace(jnlp, "{CODEBASEURL}", codebase.toExternalForm());
/* 701 */     StringUtilities.replace(jnlp, "{DISPATCHURL}", dispatchURL);
/*     */     
/* 703 */     String serverURL = URLTweak.fixURL(ApplicationContext.getInstance().getProperty("replacer.tis2web.server.url"), request);
/* 704 */     StringUtilities.replace(jnlp, "{SERVER.URL}", serverURL);
/*     */     
/* 706 */     StringUtilities.replace(jnlp, "{SESSIONID}", this.context.getSessionID());
/* 707 */     StringUtilities.replace(jnlp, "{SESSION_TIMEOUT}", String.valueOf(ApplicationContext.getInstance().getSessionTimeout(false)));
/*     */     
/* 709 */     StringUtilities.replace(jnlp, "{LANGUAGEID}", String.valueOf(this.context.getLocale()));
/* 710 */     StringUtilities.replace(jnlp, "{SECTOKEN}", SecurityToken.getInstance(this.context).createToken());
/* 711 */     StringUtilities.replace(jnlp, "{COOKIE}", Base64EncodingUtil.encode(Util.getUTF8Bytes(CookieUtil.adjustCookieOrdering(String.valueOf(cookie)))));
/*     */ 
/*     */     
/* 714 */     jnlp = SoftwareKeyCheckServer.setSoftwareKeyCheck(this.context.getSessionID(), jnlp);
/* 715 */     StringUtilities.replace(jnlp, "{MODE}", String.valueOf(techID).toUpperCase(Locale.ENGLISH));
/* 716 */     StringUtilities.replace(jnlp, "{DATAID}", String.valueOf(dataID));
/*     */     
/*     */     try {
/* 719 */       return new ResultObject(10, false, false, new PairImpl("application/x-java-jnlp-file", jnlp.toString().getBytes("utf-8")));
/* 720 */     } catch (UnsupportedEncodingException e) {
/* 721 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\SIOLUDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */