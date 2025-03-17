/*     */ package com.eoos.gm.tis2web.frame.ws.e5.mhtml;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.si.ResourceServletDelegate;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainerConstructionException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentNotFoundException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.CPRDocumentNotSupportedException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.replacer.Replacer;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOWD;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.UncheckedInterruptedException;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.mail.BodyPart;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.Multipart;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MHTMLDocument
/*     */   extends DocumentContainer
/*     */   implements Replacer.ReplacerCallback
/*     */ {
/*  52 */   private static Logger log = Logger.getLogger(MHTMLDocument.class);
/*     */   
/*  54 */   protected Map icons = new HashMap<Object, Object>();
/*     */   
/*  56 */   protected Map images = new HashMap<Object, Object>();
/*     */   
/*  58 */   protected Map graphics = new HashMap<Object, Object>();
/*     */   
/*  60 */   protected List stylesheets = new ArrayList();
/*     */   
/*  62 */   private String styleSheet = null;
/*     */   
/*  64 */   private String title = null;
/*     */   
/*  66 */   private String encoding = null;
/*     */   
/*     */   private Integer sioId;
/*     */   
/*     */   private SIOType sioType;
/*     */   
/*     */   private DataSource dsDocument;
/*     */   
/*  74 */   private static Pattern anchorElementRemovePattern = Pattern.compile("(?is)<a\\s+[^>]*href\\s*=\\s*\"(?!(cid:|#)).*?>(.*?)</a.*?>");
/*     */   
/*  76 */   private static Pattern gmElementRemovePattern = Pattern.compile("(?is)<gm:locator\\s+[^>]*href\\s*=\\s*\"(?!(SIO_LINK_BEGIN:|#)).*?>(.*?)</gm:locator.*?>");
/*     */ 
/*     */ 
/*     */   
/*  80 */   private static Pattern imgElementRemovePattern = Pattern.compile("(?is)<img\\s+[^>]*\"cid:(ZOOM\\.GIF|FORWARD\\.GIF|BACK\\.GIF)\"[^>]*>");
/*     */   
/*     */   private static final String SIO_LINK_BEGIN = "SIO_LINK_BEGIN";
/*     */   private static final String SIO_LINK_END = "SIO_LINK_END";
/*     */   
/*     */   public class MHTMLImpl
/*     */     implements SI.MHTML
/*     */   {
/*  88 */     MimeMessage mime = null;
/*     */     
/*     */     protected MHTMLImpl(MimeMessage mime) {
/*  91 */       this.mime = mime;
/*     */     }
/*     */     
/*     */     public String getMIMEType() {
/*  95 */       return "multipart/related";
/*     */     }
/*     */     
/*     */     public void writeContent(OutputStream os) {
/*     */       
/* 100 */       try { this.mime.writeTo(os); }
/* 101 */       catch (IOException e) {  }
/* 102 */       catch (MessagingException e)
/* 103 */       { MHTMLDocument.log.error("unable to write content: - exception: " + e, (Throwable)e); }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MHTMLDocument(SIO sio, LocaleInfo locale) throws DocumentNotFoundException, DocumentContainerConstructionException, CPRDocumentNotSupportedException {
/* 110 */     super(null);
/*     */     try {
/* 112 */       Util.checkInterruption2();
/* 113 */       this.sioId = sio.getID();
/* 114 */       SIOBlob blob = null;
/* 115 */       if (sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOLU) {
/* 116 */         blob = SIDataAdapterFacade.getInstance().getSI().getDocumentBlob(SIDataAdapterFacade.getInstance().getSI().lookupSIO(this.sioId.intValue()), locale);
/* 117 */         this.sioType = SIOType.SI;
/*     */       }
/* 119 */       else if (sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOProxy) {
/* 120 */         blob = SIDataAdapterFacade.getInstance().getSI().getDocumentBlob(SIDataAdapterFacade.getInstance().getSI().lookupSIO(this.sioId.intValue()), locale);
/* 121 */         this.sioType = SIOType.WIS;
/*     */       }
/* 123 */       else if (sio instanceof SIOWD) {
/* 124 */         blob = ((SIOWD)sio).getWiringDiagram(locale);
/* 125 */         this.sioType = SIOType.WD;
/*     */       }
/* 127 */       else if (sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR) {
/* 128 */         throw new CPRDocumentNotSupportedException(this.sioId.toString());
/*     */       } 
/*     */       
/* 131 */       if (blob != null) {
/* 132 */         byte[] docData = blob.getData();
/*     */         
/* 134 */         this.encoding = blob.getCharset();
/* 135 */         this.encoding = (this.encoding != null) ? StringUtilities.replace(this.encoding, "'", "") : null;
/* 136 */         String html = new String(docData, this.encoding);
/* 137 */         init(html);
/*     */       } else {
/* 139 */         throw new DocumentNotFoundException(this.sioId.toString());
/*     */       } 
/* 141 */     } catch (UncheckedInterruptedException e) {
/* 142 */       throw e;
/* 143 */     } catch (CPRDocumentNotSupportedException e) {
/* 144 */       throw e;
/* 145 */     } catch (DocumentNotFoundException e) {
/* 146 */       throw e;
/* 147 */     } catch (Exception e) {
/* 148 */       log.error("unable to create document container for node-id: - exception: " + e, e);
/* 149 */       throw new DocumentContainerConstructionException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(String orgDocument) {
/* 156 */     Replacer replacer = new Replacer(this);
/* 157 */     StringBuffer buffer = new StringBuffer(replacer.apply(orgDocument));
/*     */     
/* 159 */     Util.replace(buffer, gmElementRemovePattern, new Util.ReplacementCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback)
/*     */           {
/* 163 */             String sioId = null;
/*     */             try {
/* 165 */               if (match.toString().indexOf("SIO_LINK_BEGIN") != -1) {
/* 166 */                 sioId = match.toString().substring(match.toString().indexOf("SIO_LINK_BEGIN") + "SIO_LINK_BEGIN".length(), match.toString().indexOf("SIO_LINK_END"));
/* 167 */                 String rest1 = match.toString().substring(0, match.toString().indexOf("href="));
/* 168 */                 String rest2 = match.toString().substring(match.toString().indexOf("SIO_LINK_END") + "SIO_LINK_END".length() + 1, match.toString().length());
/* 169 */                 String replace = "<!--SCDS SIO-Link: " + sioId + "-->";
/* 170 */                 return replace + rest1 + rest2;
/*     */               } 
/* 172 */               return matcherCallback.getGroup(2);
/* 173 */             } catch (Exception ex) {
/* 174 */               MHTMLDocument.log.error("unable to parse '" + match + "'" + ex, ex);
/* 175 */               return "";
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 181 */     Util.replace(buffer, anchorElementRemovePattern, new Util.ReplacementCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback)
/*     */           {
/* 185 */             String sioId = null;
/*     */             try {
/* 187 */               if (match.toString().indexOf("SIO_LINK_BEGIN") != -1) {
/* 188 */                 sioId = match.toString().substring(match.toString().indexOf("SIO_LINK_BEGIN") + "SIO_LINK_BEGIN".length(), match.toString().indexOf("SIO_LINK_END"));
/* 189 */                 String replace = "<!--SCDS SIO-Link: " + sioId + "-->";
/* 190 */                 return replace + matcherCallback.getGroup(2);
/*     */               } 
/* 192 */               return matcherCallback.getGroup(2);
/* 193 */             } catch (Exception ex) {
/* 194 */               MHTMLDocument.log.error("unable to parse '" + match + "'" + ex, ex);
/* 195 */               return "";
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 201 */     Util.replace(buffer, imgElementRemovePattern, new Util.ReplacementCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/* 204 */             return "";
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 209 */     final String modDocument = buffer.toString();
/*     */     
/* 211 */     this.dsDocument = new DataSource()
/*     */       {
/*     */         public OutputStream getOutputStream() throws IOException {
/* 214 */           return null;
/*     */         }
/*     */         
/*     */         public String getName() {
/* 218 */           return null;
/*     */         }
/*     */         
/*     */         public InputStream getInputStream() throws IOException {
/* 222 */           return new ByteArrayInputStream(modDocument.getBytes("utf-8"));
/*     */         }
/*     */         
/*     */         public String getContentType() {
/* 226 */           return "text/html;charset=\"utf-8\"";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SI.MHTML getMHTMLDocument(String subject) throws DocumentContainerConstructionException {
/* 235 */     FileOutputStream fos = null;
/* 236 */     this.title = subject;
/*     */     try {
/* 238 */       Properties props = new Properties();
/* 239 */       Session session = Session.getInstance(props, null);
/*     */       
/* 241 */       MimeMessage message = new MimeMessage(session);
/* 242 */       MimeMultipart mpart = new MimeMultipart("related");
/*     */       
/* 244 */       mpart.addBodyPart(bodyPartHTML(this.dsDocument));
/* 245 */       for (Iterator<String> iterator3 = this.stylesheets.iterator(); iterator3.hasNext(); ) {
/* 246 */         String stylesheet = iterator3.next();
/* 247 */         mpart.addBodyPart(bodyPartSTYLE(stylesheet));
/*     */       } 
/*     */       
/* 250 */       for (Iterator<String> iterator2 = this.icons.keySet().iterator(); iterator2.hasNext(); ) {
/* 251 */         String icon = iterator2.next();
/* 252 */         String mimeTypeIcon = (String)this.icons.get(icon);
/* 253 */         mpart.addBodyPart(bodyPartICON(icon, mimeTypeIcon));
/*     */       } 
/*     */       
/* 256 */       for (Iterator<String> iterator1 = this.images.keySet().iterator(); iterator1.hasNext(); ) {
/* 257 */         String image = iterator1.next();
/* 258 */         String mimeTypeImage = (String)this.images.get(image);
/* 259 */         mpart.addBodyPart(bodyPartObject(image, mimeTypeImage));
/*     */       } 
/*     */       
/* 262 */       for (Iterator<String> it = this.graphics.keySet().iterator(); it.hasNext(); ) {
/* 263 */         String graphic = it.next();
/* 264 */         String mimeTypeGraphic = (String)this.graphics.get(graphic);
/* 265 */         mpart.addBodyPart(bodyPartObject(graphic, mimeTypeGraphic));
/*     */       } 
/*     */       
/* 268 */       message.setContent((Multipart)mpart);
/* 269 */       message.setSubject(subject);
/* 270 */       return new MHTMLImpl(message);
/* 271 */     } catch (Exception ex) {
/* 272 */       log.error("unable to create a MHTML:" + ex, ex);
/*     */       try {
/* 274 */         if (fos != null)
/* 275 */           fos.close(); 
/* 276 */       } catch (IOException e) {}
/*     */ 
/*     */       
/* 279 */       return null;
/*     */     }  } public String replace(Replacer.TemplateReplacer replacer) { Replacer.StyleSheetReplacer ssr; Replacer.ImageFileReplacer ifr; Replacer.ImageReplacer imageReplacer; Replacer.GraphicReplacer ir; Replacer.SIODocumentLinkReplacer docReplacer; Replacer.DetailViewReplacer dwr; Replacer.AWLinkReplacer awlr; String name, str1; int sioID;
/*     */     String key, awNr;
/*     */     int pos;
/*     */     String mime, typeIcon, str2;
/* 284 */     Util.checkInterruption2();
/* 285 */     switch (replacer.getType()) {
/*     */       
/*     */       case 4:
/* 288 */         ssr = (Replacer.StyleSheetReplacer)replacer;
/* 289 */         this.styleSheet = ssr.getKey();
/* 290 */         this.stylesheets.add(this.styleSheet);
/* 291 */         return "cid:" + this.styleSheet;
/*     */ 
/*     */       
/*     */       case 2:
/* 295 */         ifr = (Replacer.ImageFileReplacer)replacer;
/* 296 */         name = ifr.getKey();
/* 297 */         pos = name.indexOf(".");
/* 298 */         typeIcon = null;
/* 299 */         if (pos != -1) {
/* 300 */           typeIcon = name.substring(pos + 1);
/*     */         }
/* 302 */         str2 = (typeIcon != null) ? ("image/" + typeIcon) : "image/gif";
/* 303 */         if (name.toUpperCase().indexOf("ZOOM") == -1 && name.toUpperCase().indexOf("BACK") == -1 && name.toUpperCase().indexOf("FORWARD") == -1)
/*     */         {
/* 305 */           this.icons.put(name, str2);
/*     */         }
/* 307 */         return "cid:" + name;
/*     */ 
/*     */       
/*     */       case 3:
/* 311 */         imageReplacer = (Replacer.ImageReplacer)replacer;
/* 312 */         str1 = imageReplacer.getKey();
/* 313 */         mime = null;
/*     */         
/* 315 */         if (this.sioType.ord() == SIOType.SI.ord()) {
/* 316 */           mime = SIDataAdapterFacade.getInstance().getSI().getMimeType4Image(Integer.valueOf(str1).intValue());
/* 317 */           this.images.put(str1, mime);
/*     */         }
/* 319 */         else if (this.sioType.ord() == SIOType.WD.ord()) {
/* 320 */           mime = SIDataAdapterFacade.getInstance().getSI().getMimeType(Integer.valueOf(str1).intValue());
/* 321 */           this.graphics.put(str1, mime);
/*     */         }
/* 323 */         else if (this.sioType.ord() == SIOType.CPR.ord()) {
/* 324 */           mime = SIDataAdapterFacade.getInstance().getSI().getMimeType4Image(Integer.valueOf(str1).intValue());
/* 325 */           if (mime != null) {
/* 326 */             this.images.put(str1, mime);
/*     */           } else {
/* 328 */             mime = SIDataAdapterFacade.getInstance().getSI().getMimeType(Integer.valueOf(str1).intValue());
/* 329 */             this.graphics.put(str1, mime);
/*     */           } 
/*     */         } 
/*     */         
/* 333 */         if (mime != null) {
/* 334 */           int i = mime.indexOf("/");
/* 335 */           if (i != -1) {
/* 336 */             mime = "." + mime.substring(i + 1);
/*     */           }
/* 338 */           return "cid:" + str1 + mime;
/*     */         } 
/* 340 */         return "cid:" + str1;
/*     */ 
/*     */ 
/*     */       
/*     */       case 9:
/* 345 */         ir = (Replacer.GraphicReplacer)replacer;
/* 346 */         str1 = ir.getKey();
/* 347 */         mime = SIDataAdapterFacade.getInstance().getSI().getMimeType(Integer.valueOf(str1).intValue());
/* 348 */         if (mime != null) {
/* 349 */           this.graphics.put(str1, mime);
/* 350 */           int i = mime.indexOf("/");
/* 351 */           mime = "." + mime.substring(i + 1);
/* 352 */           return "cid:" + str1 + mime;
/*     */         } 
/* 354 */         return "cid:" + str1;
/*     */ 
/*     */       
/*     */       case 1:
/* 358 */         docReplacer = (Replacer.SIODocumentLinkReplacer)replacer;
/* 359 */         sioID = Integer.parseInt(docReplacer.getKey());
/* 360 */         if (docReplacer.getBookmark() != null) {
/* 361 */           return "SIO_LINK_BEGIN" + sioID + ":BM:" + docReplacer.getBookmark() + "SIO_LINK_END";
/*     */         }
/* 363 */         return "SIO_LINK_BEGIN" + sioID + "SIO_LINK_END";
/*     */ 
/*     */       
/*     */       case 8:
/* 367 */         return "disabled";
/*     */ 
/*     */       
/*     */       case 11:
/* 371 */         return "disabled";
/*     */ 
/*     */       
/*     */       case 10:
/* 375 */         return "disabled";
/*     */ 
/*     */       
/*     */       case 7:
/* 379 */         return "disabled";
/*     */ 
/*     */       
/*     */       case 5:
/* 383 */         dwr = (Replacer.DetailViewReplacer)replacer;
/* 384 */         key = dwr.getKey();
/* 385 */         return key;
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 390 */         awlr = (Replacer.AWLinkReplacer)replacer;
/* 391 */         awNr = awlr.getKey();
/* 392 */         return awNr;
/*     */     } 
/*     */ 
/*     */     
/* 396 */     return "disabled"; }
/*     */ 
/*     */   
/*     */   public synchronized String getHtmlCode(Map params) {
/* 400 */     return null;
/*     */   }
/*     */   
/*     */   public synchronized String getStyleSheet() {
/* 404 */     return this.styleSheet;
/*     */   }
/*     */   
/*     */   public synchronized String getTitle() {
/* 408 */     return this.title;
/*     */   }
/*     */   
/*     */   public synchronized String getEncoding() {
/* 412 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public SIO getSIO() {
/* 416 */     return null;
/*     */   }
/*     */   
/*     */   protected BodyPart bodyPart(DataSource ds) throws MessagingException {
/* 420 */     MimeBodyPart body = new MimeBodyPart();
/* 421 */     DataHandler dh = new DataHandler(ds);
/* 422 */     body.setDisposition("inline");
/* 423 */     body.setDataHandler(dh);
/* 424 */     body.setFileName(dh.getName());
/* 425 */     body.addHeader("Content-Location", dh.getName());
/* 426 */     return (BodyPart)body;
/*     */   }
/*     */   
/*     */   protected BodyPart bodyPartICON(final String icon, final String mimeType) throws MessagingException {
/* 430 */     MimeBodyPart body = new MimeBodyPart();
/*     */     
/* 432 */     DataSource fds = new DataSource()
/*     */       {
/*     */         public OutputStream getOutputStream() throws IOException {
/* 435 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public String getName() {
/* 439 */           return null;
/*     */         }
/*     */         
/*     */         public InputStream getInputStream() throws IOException {
/* 443 */           byte[] docData = null;
/* 444 */           docData = ApplicationContext.getInstance().loadResource("si/" + icon.toLowerCase(Locale.ENGLISH));
/* 445 */           return (docData == null) ? StreamUtil.EMPTY_InputStream : new ByteArrayInputStream(docData);
/*     */         }
/*     */         
/*     */         public String getContentType() {
/* 449 */           return mimeType;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 454 */     DataHandler dh = new DataHandler(fds);
/* 455 */     body.setDisposition("inline");
/* 456 */     body.setDataHandler(dh);
/* 457 */     body.setFileName(icon);
/* 458 */     body.setHeader("Content-ID", "<" + icon + ">");
/* 459 */     return (BodyPart)body;
/*     */   }
/*     */   
/*     */   protected synchronized BodyPart bodyPartObject(final String id, final String mimeType) throws MessagingException {
/* 463 */     MimeBodyPart body = new MimeBodyPart();
/*     */     
/* 465 */     DataSource fds = new DataSource()
/*     */       {
/*     */         public OutputStream getOutputStream() throws IOException {
/* 468 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public String getName() {
/* 472 */           return id;
/*     */         }
/*     */         
/*     */         public InputStream getInputStream() {
/* 476 */           byte[] docData = null;
/*     */           try {
/* 478 */             ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */             try {
/* 480 */               if (MHTMLDocument.this.sioType.ord() == SIOType.SI.ord()) {
/* 481 */                 ResourceServletDelegate.getInstance().getResource(ResourceServletDelegate.Type.IMAGE, Integer.valueOf(id).intValue(), baos, null);
/*     */               }
/* 483 */               else if (MHTMLDocument.this.sioType.ord() == SIOType.WD.ord()) {
/* 484 */                 ResourceServletDelegate.getInstance().getResource(ResourceServletDelegate.Type.GRAPHIC, Integer.valueOf(id).intValue(), baos, null);
/*     */               }
/* 486 */               else if (MHTMLDocument.this.sioType.ord() == SIOType.CPR.ord()) {
/* 487 */                 ResourceServletDelegate.getInstance().getResource(ResourceServletDelegate.Type.IMAGE, Integer.valueOf(id).intValue(), baos, null);
/* 488 */                 if (baos == null) {
/* 489 */                   ResourceServletDelegate.getInstance().getResource(ResourceServletDelegate.Type.GRAPHIC, Integer.valueOf(id).intValue(), baos, null);
/*     */                 }
/*     */               } 
/*     */             } finally {
/*     */               
/* 494 */               StreamUtil.close(baos, MHTMLDocument.log);
/*     */             } 
/* 496 */             docData = baos.toByteArray();
/* 497 */           } catch (IOException ex) {
/* 498 */             MHTMLDocument.log.error("unable to getInputStream: " + ex, ex);
/* 499 */             return StreamUtil.EMPTY_InputStream;
/*     */           } 
/* 501 */           return (docData == null) ? StreamUtil.EMPTY_InputStream : new ByteArrayInputStream(docData);
/*     */         }
/*     */         
/*     */         public String getContentType() {
/* 505 */           return mimeType;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 510 */     DataHandler dh = new DataHandler(fds);
/* 511 */     body.setDisposition("inline");
/* 512 */     body.setDataHandler(dh);
/* 513 */     int pos = mimeType.indexOf("/");
/* 514 */     String typeIMAGE = "." + mimeType.substring(pos + 1);
/* 515 */     body.setFileName(id + typeIMAGE);
/* 516 */     body.setHeader("Content-ID", "<" + id + typeIMAGE + ">");
/* 517 */     return (BodyPart)body;
/*     */   }
/*     */   
/*     */   protected BodyPart bodyPartSTYLE(final String stylesheet) throws MessagingException {
/* 521 */     MimeBodyPart body = new MimeBodyPart();
/*     */     
/* 523 */     DataSource fds = new DataSource()
/*     */       {
/*     */         public OutputStream getOutputStream() throws IOException {
/* 526 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         public String getName() {
/* 530 */           return null;
/*     */         }
/*     */         
/*     */         public InputStream getInputStream() throws IOException {
/* 534 */           byte[] docData = null;
/* 535 */           docData = ApplicationContext.getInstance().loadResource("si/styles/" + stylesheet.toLowerCase(Locale.ENGLISH));
/* 536 */           return (docData == null) ? StreamUtil.EMPTY_InputStream : new ByteArrayInputStream(docData);
/*     */         }
/*     */         
/*     */         public String getContentType() {
/* 540 */           return "text/html;charset=\"utf-8\"";
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 545 */     DataHandler dh = new DataHandler(fds);
/* 546 */     body.setDisposition("inline");
/* 547 */     body.setDataHandler(dh);
/* 548 */     body.setFileName(stylesheet);
/* 549 */     body.setHeader("Content-ID", "<" + stylesheet + ">");
/* 550 */     return (BodyPart)body;
/*     */   }
/*     */   
/*     */   protected BodyPart bodyPartHTML(DataSource ds) throws MessagingException {
/* 554 */     MimeBodyPart body = new MimeBodyPart();
/* 555 */     DataHandler dh = new DataHandler(ds);
/* 556 */     body.setDisposition("inline");
/* 557 */     body.setDataHandler(dh);
/* 558 */     body.setFileName(this.sioId + "mhtml");
/* 559 */     body.addHeader("Content-Location", dh.getName());
/* 560 */     return (BodyPart)body;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\mhtml\MHTMLDocument.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */