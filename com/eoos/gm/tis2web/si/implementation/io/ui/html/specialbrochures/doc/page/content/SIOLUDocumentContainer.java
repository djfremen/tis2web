/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content;
/*     */ 
/*     */ import com.eoos.datatype.MIME;
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.lt.service.LTService;
/*     */ import com.eoos.gm.tis2web.si.ResourceServlet;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.SIODocumentFacade;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.replacer.Replacer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tiff.DetailViewPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.ImageElement;
/*     */ import com.eoos.html.element.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.regexp.RE;
/*     */ import org.apache.regexp.RECompiler;
/*     */ import org.apache.regexp.REProgram;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIOLUDocumentContainer
/*     */   extends DocumentContainer
/*     */   implements Replacer.ReplacerCallback
/*     */ {
/*  55 */   private static Logger log = Logger.getLogger(SIOLUDocumentContainer.class); private static String template; protected String body = null; protected String styleSheet = null; protected String styleTag = null; public String getStyleTag() {
/*     */     return this.styleTag;
/*     */   } protected String title = null; protected String encoding = null;
/*     */   static {
/*     */     try {
/*  60 */       template = ApplicationContext.getInstance().loadFile(SIOLUDocumentContainer.class, "silucontainer.html", null).toString();
/*  61 */     } catch (Exception e) {
/*  62 */       log.error("unable to load template - error:" + e, e);
/*  63 */       throw new RuntimeException();
/*     */     } 
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
/*     */     try {
/*  96 */       RECompiler comp = new RECompiler();
/*  97 */       techLinkBeginProg = comp.compile("<[Aa]\\s*[Hh][Rr][Ee][Ff]\\s*=\\s*\"[Tt][Ee][Cc][Hh]");
/*  98 */       techLinkEndProg = comp.compile("</[Aa][^>]*>");
/*  99 */     } catch (Exception e) {
/* 100 */       log.error("unable to create RE(s) for tech-tag - error:" + e, e);
/* 101 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   protected ListElement relatedLUElement = null; protected DocumentPage docPage; private static REProgram techLinkBeginProg;
/*     */   private static REProgram techLinkEndProg;
/*     */   protected SIO sio;
/*     */   
/*     */   public SIOLUDocumentContainer(ClientContext context, SIOLU node, DocumentPage docPage) throws DocumentNotFoundException, DocumentContainerConstructionException {
/* 109 */     super(context);
/* 110 */     this.sio = (SIO)node;
/* 111 */     this.docPage = docPage;
/*     */     
/*     */     try {
/* 114 */       Util.checkInterruption();
/*     */       
/* 116 */       SIOBlob blob = node.getDocument(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/* 117 */       byte[] docData = null;
/* 118 */       if (blob == null || (docData = blob.getData()) == null) {
/* 119 */         log.error("unable to find document:" + node.getID());
/* 120 */         throw new DocumentNotFoundException((String)node.getProperty(SIOProperty.LU));
/*     */       } 
/*     */       
/* 123 */       Util.checkInterruption();
/*     */       
/* 125 */       this.encoding = blob.getCharset();
/* 126 */       this.encoding = (this.encoding != null) ? StringUtilities.replace(this.encoding, "'", "") : null;
/* 127 */       String html = new String(docData, this.encoding);
/*     */       
/* 129 */       if (html.indexOf("Microsoft Word") > 0) {
/* 130 */         int styleTagStart = html.indexOf("<style>");
/* 131 */         int styleTagEnd = html.indexOf("</style>");
/* 132 */         if (styleTagStart > 0 && styleTagEnd > 0) {
/* 133 */           this.styleTag = html.substring(styleTagStart, styleTagEnd + 8);
/*     */         }
/*     */       } 
/*     */       
/* 137 */       init(html);
/*     */       
/* 139 */       List relatedLUs = node.getRelatedLUs();
/* 140 */       if (relatedLUs != null && relatedLUs.size() != 0) {
/* 141 */         this.relatedLUElement = new RelatedLUListElement(this, relatedLUs);
/* 142 */         addElement((HtmlElement)this.relatedLUElement);
/*     */       } 
/*     */ 
/*     */       
/* 146 */       RE techLinkBegin = new RE(techLinkBeginProg);
/* 147 */       RE techLinkEnd = new RE(techLinkEndProg);
/* 148 */       SectionIndex index = StringUtilities.getSectionIndex(this.body, techLinkBegin, techLinkEnd, 0, true, false);
/* 149 */       if (index != null) {
/* 150 */         StringBuffer tmp = new StringBuffer(this.body);
/* 151 */         StringUtilities.replaceSectionContent(tmp, index, "");
/* 152 */         this.body = tmp.toString();
/*     */       }
/*     */     
/* 155 */     } catch (InterruptedException e) {
/* 156 */       throw new RuntimeException(e);
/* 157 */     } catch (Exception e) {
/* 158 */       if (Util.hasCause(e, InterruptedException.class)) {
/* 159 */         Util.rethrowRuntimeException(e);
/*     */       }
/* 161 */       log.error("unable to create document container for node-id:" + node.getID());
/* 162 */       throw new DocumentContainerConstructionException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(String orgDocument)
/*     */   {
/* 169 */     Replacer replacer = new Replacer(this);
/* 170 */     StringBuffer modDocument = replacer.apply(orgDocument);
/*     */     
/* 172 */     SIODocumentFacade df = new SIODocumentFacade(modDocument);
/* 173 */     this.body = df.getBody(); } public String replace(Replacer.TemplateReplacer replacer) { Replacer.StyleSheetReplacer ssr; Replacer.ImageFileReplacer ifr; Replacer.GraphicReplacer gr; Replacer.ImageReplacer ir; Replacer.SIODocumentLinkReplacer docReplacer; Replacer.DetailViewReplacer dwr; Replacer.AWLinkReplacer awlr; final String name; final String key; final int sioID;
/*     */     final String key;
/*     */     final String awNr;
/*     */     ImageElement image;
/*     */     LinkElement link;
/*     */     try {
/* 179 */       Util.checkInterruption();
/* 180 */     } catch (InterruptedException e) {
/* 181 */       throw new RuntimeException(e);
/*     */     } 
/* 183 */     switch (replacer.getType()) {
/*     */       case 4:
/* 185 */         ssr = (Replacer.StyleSheetReplacer)replacer;
/* 186 */         this.styleSheet = ssr.getKey();
/* 187 */         return null;
/*     */ 
/*     */       
/*     */       case 2:
/* 191 */         ifr = (Replacer.ImageFileReplacer)replacer;
/* 192 */         name = ifr.getKey();
/* 193 */         MIME.getMIME(name);
/* 194 */         image = new ImageElement() {
/*     */             protected String getImageURL() {
/* 196 */               return "pic/si/" + name.toLowerCase(Locale.ENGLISH);
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 200 */               return getImageURL();
/*     */             }
/*     */           };
/* 203 */         addElement((HtmlElement)image);
/* 204 */         return image.getHtmlCode(null);
/*     */ 
/*     */       
/*     */       case 9:
/* 208 */         gr = (Replacer.GraphicReplacer)replacer;
/* 209 */         str1 = gr.getKey();
/* 210 */         image = new ImageElement() {
/*     */             protected String getImageURL() {
/* 212 */               return "si/pic/g/" + ResourceServlet.getURLSuffix(key, SIOLUDocumentContainer.this.sio.getID());
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 216 */               return getImageURL();
/*     */             }
/*     */           };
/* 219 */         addElement((HtmlElement)image);
/* 220 */         return image.getHtmlCode(null);
/*     */ 
/*     */       
/*     */       case 3:
/* 224 */         ir = (Replacer.ImageReplacer)replacer;
/* 225 */         str1 = ir.getKey();
/* 226 */         image = new ImageElement() {
/*     */             protected String getImageURL() {
/* 228 */               return "si/pic/i/" + ResourceServlet.getURLSuffix(key, SIOLUDocumentContainer.this.sio.getID());
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 232 */               return getImageURL();
/*     */             }
/*     */           };
/* 235 */         addElement((HtmlElement)image);
/* 236 */         return image.getHtmlCode(null);
/*     */       
/*     */       case 1:
/* 239 */         docReplacer = (Replacer.SIODocumentLinkReplacer)replacer;
/* 240 */         sioID = Integer.parseInt(docReplacer.getKey());
/* 241 */         link = new LinkElement(this.context.createID(), docReplacer.getBookmark()) {
/*     */             public String getLabel() {
/* 243 */               return null;
/*     */             }
/*     */             
/*     */             protected String getTargetFrame() {
/* 247 */               return "_top";
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/* 251 */               SIO sio = null;
/*     */               try {
/* 253 */                 sio = SIDataAdapterFacade.getInstance(SIOLUDocumentContainer.this.context).getSI().getSIO(sioID);
/* 254 */               } catch (IllegalArgumentException e) {
/* 255 */                 return new DefaultDocumentContainer(SIOLUDocumentContainer.this.context, SIOLUDocumentContainer.this.context.getMessage("si.document.not.available"));
/*     */               } 
/* 257 */               SpecialBrochuresContext.getInstance(SIOLUDocumentContainer.this.context).setSelectedSIO(sio, false);
/* 258 */               return MainPage.getInstance(SIOLUDocumentContainer.this.context);
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 262 */               return this.renderingCallback.getLink();
/*     */             }
/*     */           };
/* 265 */         addElement((HtmlElement)link);
/* 266 */         return link.getHtmlCode(null);
/*     */       
/*     */       case 5:
/* 269 */         dwr = (Replacer.DetailViewReplacer)replacer;
/* 270 */         key = dwr.getKey();
/* 271 */         link = new LinkElement(this.context.createID(), null) {
/*     */             public String getLabel() {
/* 273 */               return null;
/*     */             }
/*     */             
/*     */             public String getTargetFrame() {
/* 277 */               return "detail";
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/* 281 */               DetailViewPage detailPage = new DetailViewPage(SIOLUDocumentContainer.this.context, Integer.parseInt(key), true);
/* 282 */               return detailPage;
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 286 */               return this.renderingCallback.getLink();
/*     */             }
/*     */           };
/* 289 */         addElement((HtmlElement)link);
/* 290 */         return link.getHtmlCode(null);
/*     */       
/*     */       case 6:
/* 293 */         awlr = (Replacer.AWLinkReplacer)replacer;
/* 294 */         awNr = awlr.getKey();
/* 295 */         link = new LinkElement(this.context.createID(), null) {
/*     */             public String getLabel() {
/* 297 */               return null;
/*     */             }
/*     */             
/*     */             protected String getTargetFrame() {
/* 301 */               return "_top";
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/*     */               try {
/* 306 */                 LTService ltMI = (LTService)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/*     */                 
/* 308 */                 Map usrGrp2Manuf = SharedContextProxy.getInstance(SIOLUDocumentContainer.this.context).getUsrGroup2Manuf();
/*     */                 
/* 310 */                 String country = SharedContextProxy.getInstance(SIOLUDocumentContainer.this.context).getCountry();
/*     */                 
/* 312 */                 boolean authorized = false;
/* 313 */                 ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 314 */                 Set apps = aclMI.getAuthorizedResources("Application", usrGrp2Manuf, country);
/*     */                 
/* 316 */                 if (apps.contains("lt")) {
/* 317 */                   authorized = true;
/*     */                 }
/* 319 */                 if (ltMI == null)
/* 320 */                   return new NotificationMessageBox(SIOLUDocumentContainer.this.context, SIOLUDocumentContainer.this.context.getLabel("error"), SIOLUDocumentContainer.this.context.getMessage("si.lt.no.instance")) {
/*     */                       public Object onOK(Map params) {
/* 322 */                         return MainPage.getInstance((ClientContext)this.context);
/*     */                       }
/*     */                     }; 
/* 325 */                 if (!authorized) {
/* 326 */                   return MainPage.getInstance(SIOLUDocumentContainer.this.context);
/*     */                 }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 try {
/* 337 */                   ltMI.setMainWork(SIOLUDocumentContainer.this.context.getSessionID(), awNr);
/* 338 */                   return MainPage.getInstance(SIOLUDocumentContainer.this.context).switchModule((VisualModule)ltMI);
/* 339 */                 } catch (com.eoos.gm.tis2web.lt.service.LTService.LTServiceException e) {
/* 340 */                   SIOLUDocumentContainer.log.error("unable to set main work " + String.valueOf(awNr) + ", notifying  - exception: " + e, (Throwable)e);
/* 341 */                   return new NotificationMessageBox(SIOLUDocumentContainer.this.context, SIOLUDocumentContainer.this.context.getLabel("error"), SIOLUDocumentContainer.this.context.getMessage("si.lt.novc")) {
/*     */                       public Object onOK(Map params) {
/* 343 */                         return MainPage.getInstance((ClientContext)this.context);
/*     */                       }
/*     */                     };
/*     */                 }
/*     */               
/* 348 */               } catch (Exception e) {
/* 349 */                 HtmlElementContainer htmlElementContainer = getContainer();
/* 350 */                 while (htmlElementContainer.getContainer() != null) {
/* 351 */                   htmlElementContainer = htmlElementContainer.getContainer();
/*     */                 }
/* 353 */                 return htmlElementContainer;
/*     */               } 
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 358 */               String result = this.renderingCallback.getLink();
/* 359 */               return result;
/*     */             }
/*     */           };
/* 362 */         addElement((HtmlElement)link);
/* 363 */         return link.getHtmlCode(null);
/*     */     } 
/*     */     
/* 366 */     return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getHtmlCode(Map params) {
/* 372 */     boolean related = (this.relatedLUElement != null);
/*     */     
/* 374 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 376 */     StringUtilities.replace(code, "{LINK_BOOKMARK}", related ? ("<a href=\"#" + this.relatedLUElement.getBookmark() + "\">" + this.context.getLabel("si.related.literature.units") + "</a>") : "");
/* 377 */     StringUtilities.replace(code, "{DOCUMENT}", this.body);
/* 378 */     StringUtilities.replace(code, "{RELATED_LU_LIST}", related ? this.relatedLUElement.getHtmlCode(params) : "");
/* 379 */     return code.toString();
/*     */   }
/*     */   
/*     */   public synchronized String getStyleSheet() {
/* 383 */     return this.styleSheet;
/*     */   }
/*     */   
/*     */   public synchronized String getTitle() {
/* 387 */     return this.title;
/*     */   }
/*     */   
/*     */   public synchronized String getEncoding() {
/* 391 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public synchronized DocumentPage getDocumentPage() {
/* 395 */     return this.docPage;
/*     */   }
/*     */   
/*     */   public SIO getSIO() {
/* 399 */     return this.sio;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\doc\page\content\SIOLUDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */