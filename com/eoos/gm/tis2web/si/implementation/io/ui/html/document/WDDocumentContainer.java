/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*     */ 
/*     */ import com.eoos.datatype.MIME;
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.ResourceServlet;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.document.SIODocumentFacade;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.replacer.Replacer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tiff.DetailViewPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOWD;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.ImageElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.scsm.v2.util.UncheckedInterruptedException;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ public class WDDocumentContainer
/*     */   extends DocumentContainer
/*     */   implements Replacer.ReplacerCallback
/*     */ {
/*     */   private static final REProgram historyARefStartProg;
/*     */   private static final REProgram aEndProg;
/*     */   
/*     */   static {
/*     */     try {
/*  46 */       RECompiler comp = new RECompiler();
/*  47 */       historyARefStartProg = comp.compile("<[Aa]\\s+[Hh][Rr][Ee][Ff]=\"javascript:history[^>]*>");
/*  48 */       aEndProg = comp.compile("</[Aa]>");
/*  49 */     } catch (Exception e) {
/*  50 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*  54 */   private static Logger log = Logger.getLogger(WDDocumentContainer.class);
/*     */   
/*  56 */   private String body = null;
/*     */   
/*  58 */   private String styleSheet = null;
/*     */   
/*  60 */   private String title = null;
/*     */   
/*  62 */   private String encoding = null;
/*     */   
/*     */   private SIO sio;
/*     */   
/*     */   private SIODocLinkElement docLinkElement;
/*     */ 
/*     */   
/*     */   public WDDocumentContainer(ClientContext context, SIOWD node, DocumentPage page) throws DocumentNotFoundException, DocumentContainerConstructionException {
/*  70 */     super(context);
/*  71 */     StopWatch sw = StopWatch.getInstance().start();
/*  72 */     this.sio = (SIO)node;
/*  73 */     this.docLinkElement = new SIODocLinkElement(context, context.createID(), null, page, this);
/*  74 */     addElement((HtmlElement)this.docLinkElement);
/*     */     try {
/*  76 */       Util.checkInterruption2();
/*     */       
/*  78 */       SIOBlob blob = node.getWiringDiagram(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/*  79 */       log.debug("retrieved blob for wiring diagram after:" + sw.getElapsedTime() + " ms");
/*  80 */       byte[] docData = null;
/*  81 */       if (blob == null || (docData = blob.getData()) == null) {
/*  82 */         log.error("unable to find document:" + node.getID());
/*  83 */         throw new DocumentNotFoundException((String)node.getProperty(SIOProperty.LU));
/*     */       } 
/*     */       
/*  86 */       Util.checkInterruption2();
/*     */       
/*  88 */       this.encoding = blob.getCharset();
/*  89 */       this.encoding = (this.encoding != null) ? StringUtilities.replace(this.encoding, "'", "") : null;
/*  90 */       init(new String(docData, this.encoding));
/*     */     }
/*  92 */     catch (UncheckedInterruptedException e) {
/*  93 */       throw e;
/*  94 */     } catch (Exception e) {
/*  95 */       if (Util.hasCause(e, InterruptedException.class)) {
/*  96 */         Util.rethrowRuntimeException(e);
/*     */       }
/*  98 */       log.error("unable to create document container for node-id:" + node.getID());
/*  99 */       throw new DocumentContainerConstructionException();
/*     */     } finally {
/* 101 */       log.debug("created wd document container after:" + sw.stop() + " ms");
/* 102 */       StopWatch.freeInstance(sw);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String orgDocument) {
/* 110 */     Replacer replacer = new Replacer(this);
/* 111 */     StringBuffer modDocument = replacer.apply(orgDocument);
/*     */     
/* 113 */     SIODocumentFacade df = new SIODocumentFacade(modDocument);
/*     */     
/*     */     try {
/* 116 */       StringBuffer tmp = new StringBuffer(df.getBody());
/* 117 */       SectionIndex index = null;
/* 118 */       RE historyARefStart = new RE(historyARefStartProg);
/*     */       
/* 120 */       RE aEnd = new RE(aEndProg); while (true) {
/* 121 */         if ((index = StringUtilities.getSectionIndex(tmp.toString(), historyARefStart, aEnd, (index == null) ? 0 : index.start, true, false)) != null) {
/* 122 */           StringUtilities.replaceSectionContent(tmp, index, "&nbsp;"); continue;
/*     */         }  break;
/* 124 */       }  this.body = tmp.toString();
/* 125 */     } catch (Exception e) {
/* 126 */       log.error("unable to delete navigation table - error:" + e, e);
/* 127 */       this.body = df.getBody();
/*     */     }  } public String replace(Replacer.TemplateReplacer replacer) { Replacer.StyleSheetReplacer ssr; Replacer.ImageFileReplacer ifr; Replacer.ImageReplacer ir; Replacer.SIODocumentLinkReplacer docReplacer; Replacer.DetailViewReplacer dwr; final String name, key; int sioID; final String key;
/*     */     ImageElement image;
/*     */     String code;
/*     */     LinkElement link;
/*     */     String parameterName;
/* 133 */     Util.checkInterruption2();
/* 134 */     switch (replacer.getType()) {
/*     */       case 4:
/* 136 */         ssr = (Replacer.StyleSheetReplacer)replacer;
/* 137 */         this.styleSheet = ssr.getKey();
/* 138 */         return null;
/*     */       
/*     */       case 2:
/* 141 */         ifr = (Replacer.ImageFileReplacer)replacer;
/* 142 */         name = ifr.getKey();
/* 143 */         MIME.getMIME(name);
/* 144 */         image = new ImageElement() {
/*     */             protected String getImageURL() {
/* 146 */               return "pic/si/" + name.toLowerCase(Locale.ENGLISH);
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 150 */               return getImageURL();
/*     */             }
/*     */           };
/* 153 */         addElement((HtmlElement)image);
/* 154 */         return image.getHtmlCode(null);
/*     */       
/*     */       case 3:
/* 157 */         ir = (Replacer.ImageReplacer)replacer;
/* 158 */         str1 = ir.getKey();
/* 159 */         image = new ImageElement() {
/*     */             protected String getImageURL() {
/* 161 */               return "si/pic/g/" + ResourceServlet.getURLSuffix(key, WDDocumentContainer.this.sio.getID());
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 165 */               return getImageURL();
/*     */             }
/*     */           };
/* 168 */         addElement((HtmlElement)image);
/* 169 */         return image.getHtmlCode(null);
/*     */       
/*     */       case 1:
/* 172 */         docReplacer = (Replacer.SIODocumentLinkReplacer)replacer;
/* 173 */         sioID = Integer.parseInt(docReplacer.getKey());
/* 174 */         code = this.docLinkElement.getHtmlCode(null);
/* 175 */         parameterName = this.docLinkElement.getParameterName();
/* 176 */         code = StringUtilities.replace(code, parameterName, parameterName + String.valueOf(sioID));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 188 */         return code;
/*     */       
/*     */       case 5:
/* 191 */         dwr = (Replacer.DetailViewReplacer)replacer;
/* 192 */         key = dwr.getKey();
/* 193 */         link = new LinkElement(this.context.createID(), null) {
/*     */             public String getLabel() {
/* 195 */               return null;
/*     */             }
/*     */             
/*     */             public String getTargetFrame() {
/* 199 */               return "detail";
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/* 203 */               DetailViewPage detailPage = new DetailViewPage(WDDocumentContainer.this.context, Integer.parseInt(key), true);
/* 204 */               return detailPage;
/*     */             }
/*     */             
/*     */             public String getHtmlCode(Map params) {
/* 208 */               return this.renderingCallback.getLink();
/*     */             }
/*     */           };
/* 211 */         addElement((HtmlElement)link);
/* 212 */         return link.getHtmlCode(null);
/*     */     } 
/*     */     
/* 215 */     return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 221 */     return this.body;
/*     */   }
/*     */   
/*     */   public String getStyleSheet() {
/* 225 */     return this.styleSheet;
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 229 */     return this.title;
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 233 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 237 */     String s = new String(" ");
/* 238 */     byte[] b = s.getBytes("utf-8");
/* 239 */     System.out.println(StringUtilities.bytesToHex(b));
/*     */   }
/*     */ 
/*     */   
/*     */   public SIO getSIO() {
/* 244 */     return this.sio;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\WDDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */