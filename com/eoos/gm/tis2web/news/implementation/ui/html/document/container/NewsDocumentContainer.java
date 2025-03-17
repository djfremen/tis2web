/*     */ package com.eoos.gm.tis2web.news.implementation.ui.html.document.container;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.server.FileDwnldUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.DynamicResourceController;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.NewsDocumentFacade;
/*     */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.ImageFileReplacer;
/*     */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.NewsDocumentLinkReplacer;
/*     */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.Replacer;
/*     */ import com.eoos.gm.tis2web.news.implementation.datamodel.system.document.replacer.StyleSheetReplacer;
/*     */ import com.eoos.gm.tis2web.news.implementation.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.ImageElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NewsDocumentContainer
/*     */   extends DocumentContainer
/*     */ {
/*  36 */   private static Logger log = Logger.getLogger(NewsDocumentContainer.class);
/*     */   
/*  38 */   private String body = null;
/*     */   
/*  40 */   private String styleSheet = null;
/*     */   
/*  42 */   private String title = null;
/*     */   
/*  44 */   private String encoding = null;
/*     */   
/*     */   private DocumentPage docPage;
/*     */   
/*     */   private IIOElement sio;
/*     */ 
/*     */   
/*     */   public static void reset() {}
/*     */ 
/*     */   
/*     */   public NewsDocumentContainer(ClientContext context, IIOElement node, DocumentPage docPage) throws DocumentNotFoundException, DocumentContainerConstructionException {
/*  55 */     super(context);
/*  56 */     this.sio = node;
/*  57 */     this.docPage = docPage;
/*     */     
/*  59 */     SIOBlob blob = node.getDocument(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/*  60 */     byte[] docData = null;
/*  61 */     if (blob == null || (docData = blob.getData()) == null) {
/*  62 */       log.error("unable to find document:" + node.getID());
/*  63 */       throw new DocumentNotFoundException("");
/*     */     } 
/*     */     
/*     */     try {
/*  67 */       this.encoding = blob.getCharset();
/*  68 */       this.encoding = (this.encoding != null) ? StringUtilities.replace(this.encoding, "'", "") : null;
/*  69 */       String html = new String(docData, this.encoding);
/*  70 */       init(new StringBuffer(html));
/*     */     }
/*  72 */     catch (Exception e) {
/*  73 */       log.error("unable to create document container for node-id:" + node.getID());
/*  74 */       throw new DocumentContainerConstructionException();
/*     */     } 
/*     */   }
/*     */   
/*  78 */   private static final Pattern P_FILE_LINK = Pattern.compile("(?is)<a[^>]*?href\\s*=\\s*\"\\{LINK:FILE:(.*?)\\}\".*?>(.*?)</a>");
/*     */   
/*     */   private synchronized void init(StringBuffer orgDocument) {
/*  81 */     Util.replace(orgDocument, P_FILE_LINK, new Util.ReplacementCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/*  84 */             final String filename = matcherCallback.getGroup(1);
/*  85 */             final String text = matcherCallback.getGroup(2);
/*  86 */             LinkElement link = new LinkElement(NewsDocumentContainer.this.context.createID(), null)
/*     */               {
/*     */                 public Object onClick(Map submitParams)
/*     */                 {
/*  90 */                   HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*     */                   
/*  92 */                   return FileDwnldUtil.getJNLP(NewsDocumentContainer.this.context, request, filename);
/*     */                 }
/*     */ 
/*     */                 
/*     */                 protected String getLabel() {
/*  97 */                   return text;
/*     */                 }
/*     */               };
/* 100 */             NewsDocumentContainer.this.addElement((HtmlElement)link);
/* 101 */             return link.getHtmlCode(null);
/*     */           }
/*     */         });
/*     */     
/* 105 */     NewsDocumentFacade df = new NewsDocumentFacade(orgDocument);
/*     */     
/* 107 */     NewsDocumentFacade.ReplacerIterator iter = df.iterator();
/* 108 */     while (iter.hasNext()) {
/* 109 */       final ImageFileReplacer ifr; NewsDocumentLinkReplacer docReplacer; SIOBlob blob; int refID; final SIO reference; LinkElement link; Replacer r = (Replacer)iter.next();
/* 110 */       switch (r.getType()) {
/*     */         case 7:
/* 112 */           throw new IllegalStateException();
/*     */ 
/*     */         
/*     */         case 4:
/* 116 */           this.styleSheet = ((StyleSheetReplacer)r).getName();
/*     */ 
/*     */         
/*     */         case 2:
/* 120 */           ifr = (ImageFileReplacer)r;
/*     */           
/* 122 */           blob = this.sio.getGraphic(ifr.getName());
/* 123 */           if (blob != null) {
/* 124 */             final byte[] data = blob.getData();
/* 125 */             final String type = blob.getMime();
/* 126 */             ImageElement image = new ImageElement() {
/*     */                 protected String getImageURL() {
/* 128 */                   return DynamicResourceController.getInstance(NewsDocumentContainer.this.context).getURL(data, type, ifr.getName() + type);
/*     */                 }
/*     */                 
/*     */                 public String getHtmlCode(Map params) {
/* 132 */                   return getImageURL();
/*     */                 }
/*     */               };
/* 135 */             addElement((HtmlElement)image);
/* 136 */             iter.replace(image.getHtmlCode(null)); continue;
/*     */           } 
/* 138 */           iter.replace("");
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 143 */           docReplacer = (NewsDocumentLinkReplacer)r;
/* 144 */           refID = Integer.parseInt(docReplacer.getID());
/* 145 */           reference = this.sio.getReferencedSIO(refID);
/* 146 */           link = new LinkElement(this.context.createID(), docReplacer.getBookmark()) {
/*     */               public String getLabel() {
/* 148 */                 return null;
/*     */               }
/*     */               
/*     */               public Object onClick(Map params) {
/* 152 */                 if (reference == null) {
/* 153 */                   return new DefaultDocumentContainer(NewsDocumentContainer.this.context, NewsDocumentContainer.this.context.getMessage("document.not.available"));
/*     */                 }
/* 155 */                 DocumentPage dp = NewsDocumentContainer.this.docPage;
/* 156 */                 DocumentContainer dc = dp.setPage(reference);
/* 157 */                 dc.setPredecessor(NewsDocumentContainer.this);
/* 158 */                 return dp.getPage(params);
/*     */               }
/*     */               
/*     */               public String getHtmlCode(Map params) {
/* 162 */                 return this.renderingCallback.getLink();
/*     */               }
/*     */             };
/*     */ 
/*     */           
/* 167 */           addElement((HtmlElement)link);
/* 168 */           iter.replace(link.getHtmlCode(null));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 176 */     this.body = df.getBody();
/*     */   }
/*     */   
/*     */   public synchronized String getHtmlCode(Map params) {
/* 180 */     return this.body;
/*     */   }
/*     */   
/*     */   public synchronized String getStyleSheet() {
/* 184 */     return this.styleSheet;
/*     */   }
/*     */   
/*     */   public synchronized String getTitle() {
/* 188 */     return this.title;
/*     */   }
/*     */   
/*     */   public synchronized String getEncoding() {
/* 192 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public synchronized DocumentPage getDocumentPage() {
/* 196 */     return this.docPage;
/*     */   }
/*     */   
/*     */   public SIO getSIO() {
/* 200 */     return (SIO)this.sio;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementatio\\ui\html\document\container\NewsDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */