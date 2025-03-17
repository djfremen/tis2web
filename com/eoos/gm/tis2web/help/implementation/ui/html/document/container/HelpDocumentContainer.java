/*     */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.container;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.server.FileDwnldUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.DynamicResourceController;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.system.document.HelpDocumentFacade;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.system.document.replacer.HelpDocumentLinkReplacer;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.system.document.replacer.ImageFileReplacer;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.system.document.replacer.Replacer;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.system.document.replacer.StyleSheetReplacer;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.page.DocumentPage;
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
/*     */ 
/*     */ 
/*     */ public class HelpDocumentContainer
/*     */   extends DocumentContainer
/*     */ {
/*  38 */   private static Logger log = Logger.getLogger(HelpDocumentContainer.class);
/*     */   
/*  40 */   private String body = null;
/*     */   
/*  42 */   private String styleSheet = null;
/*     */   
/*  44 */   private String title = null;
/*     */   
/*  46 */   private String encoding = null;
/*     */   
/*     */   private DocumentPage docPage;
/*     */   
/*     */   private SIO sio;
/*     */ 
/*     */   
/*     */   public static void reset() {}
/*     */ 
/*     */   
/*     */   public HelpDocumentContainer(ClientContext context, IIOElement node, DocumentPage docPage) throws DocumentNotFoundException, DocumentContainerConstructionException {
/*  57 */     super(context);
/*  58 */     this.sio = (SIO)node;
/*  59 */     this.docPage = docPage;
/*     */     
/*  61 */     SIOBlob blob = node.getDocument(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/*  62 */     byte[] docData = null;
/*  63 */     if (blob == null || (docData = blob.getData()) == null) {
/*  64 */       log.error("unable to find document:" + node.getID());
/*  65 */       throw new DocumentNotFoundException("");
/*     */     } 
/*     */     
/*     */     try {
/*  69 */       this.encoding = blob.getCharset();
/*  70 */       this.encoding = (this.encoding != null) ? StringUtilities.replace(this.encoding, "'", "") : null;
/*  71 */       String html = new String(docData, this.encoding);
/*  72 */       init(new StringBuffer(html));
/*     */     }
/*  74 */     catch (Exception e) {
/*  75 */       log.error("unable to create document container for node-id:" + node.getID());
/*  76 */       throw new DocumentContainerConstructionException();
/*     */     } 
/*     */   }
/*     */   
/*  80 */   private static final Pattern P_FILE_LINK = Pattern.compile("(?is)<a[^>]*?href\\s*=\\s*\"\\{LINK:FILE:(.*?)\\}\".*?>(.*?)</a>");
/*     */   
/*     */   private synchronized void init(StringBuffer orgDocument) {
/*  83 */     Util.replace(orgDocument, P_FILE_LINK, new Util.ReplacementCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/*  86 */             final String filename = matcherCallback.getGroup(1);
/*  87 */             final String text = matcherCallback.getGroup(2);
/*  88 */             LinkElement link = new LinkElement(HelpDocumentContainer.this.context.createID(), null)
/*     */               {
/*     */                 public Object onClick(Map submitParams)
/*     */                 {
/*  92 */                   HttpServletRequest request = (HttpServletRequest)submitParams.get("request");
/*     */                   
/*  94 */                   return FileDwnldUtil.getJNLP(HelpDocumentContainer.this.context, request, filename);
/*     */                 }
/*     */ 
/*     */                 
/*     */                 protected String getLabel() {
/*  99 */                   return text;
/*     */                 }
/*     */               };
/* 102 */             HelpDocumentContainer.this.addElement((HtmlElement)link);
/* 103 */             return link.getHtmlCode(null);
/*     */           }
/*     */         });
/*     */     
/* 107 */     HelpDocumentFacade df = new HelpDocumentFacade(orgDocument);
/*     */     
/* 109 */     HelpDocumentFacade.ReplacerIterator iter = df.iterator();
/* 110 */     while (iter.hasNext()) {
/* 111 */       final ImageFileReplacer ifr; HelpDocumentLinkReplacer docReplacer; SIOBlob blob; int refID; final SIO reference; LinkElement link; Replacer r = (Replacer)iter.next();
/* 112 */       switch (r.getType()) {
/*     */         case 7:
/* 114 */           throw new IllegalStateException();
/*     */ 
/*     */         
/*     */         case 4:
/* 118 */           this.styleSheet = ((StyleSheetReplacer)r).getName();
/*     */ 
/*     */         
/*     */         case 2:
/* 122 */           ifr = (ImageFileReplacer)r;
/* 123 */           blob = ((IIOElement)this.sio).getGraphic(ifr.getName());
/* 124 */           if (blob != null) {
/* 125 */             final byte[] data = blob.getData();
/* 126 */             final String type = blob.getMime();
/* 127 */             ImageElement image = new ImageElement() {
/*     */                 protected String getImageURL() {
/* 129 */                   return DynamicResourceController.getInstance(HelpDocumentContainer.this.context).getURL(data, type, ifr.getName() + type);
/*     */                 }
/*     */                 
/*     */                 public String getHtmlCode(Map params) {
/* 133 */                   return getImageURL();
/*     */                 }
/*     */               };
/* 136 */             addElement((HtmlElement)image);
/* 137 */             iter.replace(image.getHtmlCode(null)); continue;
/*     */           } 
/* 139 */           iter.replace("");
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 144 */           docReplacer = (HelpDocumentLinkReplacer)r;
/* 145 */           refID = Integer.parseInt(docReplacer.getID());
/* 146 */           reference = ((IIOElement)this.sio).getReferencedSIO(refID);
/* 147 */           link = new LinkElement(this.context.createID(), docReplacer.getBookmark()) {
/*     */               public String getLabel() {
/* 149 */                 return null;
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public Object onClick(Map params) {
/* 160 */                 if (reference == null) {
/* 161 */                   return new DefaultDocumentContainer(HelpDocumentContainer.this.context, HelpDocumentContainer.this.context.getMessage("document.not.available"));
/*     */                 }
/* 163 */                 DocumentPage dp = HelpDocumentContainer.this.docPage;
/* 164 */                 DocumentContainer dc = dp.setPage(reference);
/* 165 */                 dc.setPredecessor(HelpDocumentContainer.this);
/* 166 */                 return dp.getPage(params);
/*     */               }
/*     */               
/*     */               public String getHtmlCode(Map params) {
/* 170 */                 return this.renderingCallback.getLink();
/*     */               }
/*     */             };
/*     */ 
/*     */           
/* 175 */           addElement((HtmlElement)link);
/* 176 */           iter.replace(link.getHtmlCode(null));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 184 */     this.body = df.getBody();
/*     */   }
/*     */   
/*     */   public synchronized String getHtmlCode(Map params) {
/* 188 */     return this.body;
/*     */   }
/*     */   
/*     */   public synchronized String getStyleSheet() {
/* 192 */     return this.styleSheet;
/*     */   }
/*     */   
/*     */   public synchronized String getTitle() {
/* 196 */     return this.title;
/*     */   }
/*     */   
/*     */   public synchronized String getEncoding() {
/* 200 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public synchronized DocumentPage getDocumentPage() {
/* 204 */     return this.docPage;
/*     */   }
/*     */   
/*     */   public SIO getSIO() {
/* 208 */     return this.sio;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\container\HelpDocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */