/*     */ package com.eoos.gm.tis2web.feedback.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.gm.tis2web.feedback.implementation.data.InputsParser;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FeedbackSubmitButton
/*     */   extends ClickButtonElement
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(FeedbackSubmitButton.class);
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private List ids;
/*     */   
/*     */   private List mandatories;
/*     */   
/*     */   private Map options;
/*     */   
/*     */   private FeedbackPanel parent;
/*     */   
/*     */   public FeedbackSubmitButton(ClientContext context, List ids, List mandatories, Map options, FeedbackPanel feedbackPanel) {
/*  53 */     super(context.createID(), null);
/*  54 */     this.context = context;
/*  55 */     this.ids = ids;
/*  56 */     this.mandatories = mandatories;
/*  57 */     this.options = options;
/*  58 */     this.parent = feedbackPanel;
/*  59 */     this.parent.addElement((HtmlElement)this);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean mailSystemAvailable() {
/*  64 */     String host = ApplicationContext.getInstance().getProperty("frame.mail.service.host");
/*  65 */     if (host != null && host.trim().length() > 0) {
/*  66 */       return true;
/*     */     }
/*  68 */     return ApplicationContext.getInstance().developMode();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean outputXML() {
/*  73 */     return TypeDecorator.getBoolean("frame.feedback.attachfile.type.xml", (Configuration)ApplicationContext.getInstance(), false);
/*     */   }
/*     */   
/*     */   private boolean outputEmptyFields() {
/*  77 */     return TypeDecorator.getBoolean("frame.feedback.attachfile.type.xml.empty", (Configuration)ApplicationContext.getInstance(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addToSummary(Document doc, String row) {
/*  82 */     String workPath = row;
/*  83 */     String workElementPath = "";
/*  84 */     String attribute = "";
/*  85 */     String ElementName = "";
/*  86 */     Element workEl = doc.getRootElement();
/*  87 */     if (workPath.indexOf("@") != -1) {
/*  88 */       workElementPath = workPath.substring(0, workPath.indexOf("@"));
/*  89 */       attribute = workPath.substring(workPath.indexOf("@") + 1);
/*     */     } 
/*  91 */     for (; workElementPath.lastIndexOf(".") != -1; workElementPath = workElementPath.substring(workElementPath.indexOf(".") + 1)) {
/*  92 */       ElementName = workElementPath.substring(0, workElementPath.indexOf("."));
/*  93 */       workEl = findElement(ElementName, workEl);
/*     */     } 
/*     */     
/*  96 */     workEl = findElement(workElementPath, workEl);
/*  97 */     Attribute att = new Attribute(attribute.substring(0, attribute.indexOf(" -> ")), attribute.substring(attribute.indexOf(" -> ") + 4));
/*  98 */     if (att.getName().startsWith("DatabaseVersion-") || att.getName().startsWith("ApplicationVersion") || att.getName().startsWith("ModuleVersion-"))
/*  99 */       att.setValue(this.parent.getFeedbackElement().getKeyValue(att.getName() + "-Number")); 
/* 100 */     if (workEl.getName().startsWith("FeedbackForm") && workEl.getName().endsWith("FeedbackForm")) {
/* 101 */       if (att.getName().startsWith("Locale") && att.getName().endsWith("Locale"))
/* 102 */         att.setValue(this.parent.getFeedbackElement().getKeyValue("Feedback-Locale")); 
/* 103 */     } else if ((att.getName().startsWith("Country") && att.getName().endsWith("Country")) || (att.getName().startsWith("Locale") && att.getName().endsWith("Locale"))) {
/* 104 */       att.setValue(this.parent.getFeedbackElement().getKeyValue(att.getName() + "-En"));
/* 105 */     }  if (workEl.getName().startsWith("Setting") && workEl.getName().endsWith("Setting")) {
/* 106 */       workEl.setAttribute(new Attribute("ItemID", att.getName()));
/* 107 */       att.setName("Value");
/*     */     } 
/* 109 */     if (att.getValue().indexOf("_;_") != -1) {
/* 110 */       String strIDandEN = att.getValue();
/* 111 */       String attID = att.getValue().substring(0, strIDandEN.indexOf("_;_"));
/* 112 */       String attEN = att.getValue().substring(strIDandEN.indexOf("_;_") + 3);
/* 113 */       Element childEl = new Element("Label");
/* 114 */       childEl.setAttribute(new Attribute("Label", attEN));
/* 115 */       workEl.addContent(childEl);
/* 116 */       att.setName("ID");
/* 117 */       att.setValue(attID);
/*     */     } 
/* 119 */     if (workEl.getName().startsWith("Text") && workEl.getName().endsWith("Text")) {
/* 120 */       workEl.addContent(att.getValue());
/*     */     } else {
/* 122 */       workEl.setAttribute(att);
/*     */     } 
/* 124 */     if (workEl.getName().equalsIgnoreCase("Option")) {
/*     */       
/* 126 */       String id = (String)this.options.get(att.getValue());
/* 127 */       if (id != null) {
/* 128 */         Attribute attOption = new Attribute(id, att.getValue());
/* 129 */         workEl.removeAttribute(att);
/* 130 */         workEl.setAttribute(attOption);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Element findElement(String Name, Element parentEl) {
/* 136 */     Element tmpElement = parentEl.getChild(Name);
/*     */     
/* 138 */     if (Name.startsWith("Group")) {
/* 139 */       List fGroupList = parentEl.getChildren("Group");
/* 140 */       if (fGroupList != null) {
/* 141 */         for (Iterator<Element> iter = fGroupList.iterator(); iter.hasNext(); ) {
/* 142 */           tmpElement = iter.next();
/* 143 */           String Wert = tmpElement.getAttributeValue("ID");
/* 144 */           if (Name.compareTo("Group" + Wert) == 0)
/* 145 */             return tmpElement; 
/* 146 */           tmpElement = null;
/*     */         } 
/*     */       }
/*     */       
/* 150 */       if (tmpElement == null) {
/* 151 */         tmpElement = new Element("Group");
/* 152 */         Attribute att = new Attribute("ID", Name.substring("Group".length()));
/* 153 */         tmpElement.setAttribute(att);
/* 154 */         parentEl.addContent(tmpElement);
/* 155 */         return tmpElement;
/*     */       } 
/*     */     } 
/* 158 */     if (Name.startsWith("Settings") && Name.endsWith("Settings") && tmpElement == null) {
/* 159 */       tmpElement = new Element("Settings");
/* 160 */       String strLabel = this.parent.getFeedbackElement().getKeyValue("Settings");
/* 161 */       if (strLabel != null && strLabel.length() != 0) {
/* 162 */         Element tLabel = new Element("Label");
/* 163 */         tLabel.setAttribute(new Attribute("Label", strLabel));
/* 164 */         tmpElement.addContent(tLabel);
/*     */       } 
/* 166 */       parentEl.addContent(tmpElement);
/* 167 */       return tmpElement;
/*     */     } 
/* 169 */     if (Name.startsWith("FeedbackType") && Name.endsWith("FeedbackType") && tmpElement == null) {
/* 170 */       tmpElement = new Element("FeedbackType");
/* 171 */       String strLabel = this.parent.getFeedbackElement().getKeyValue("FeedbackType");
/* 172 */       if (strLabel != null && strLabel.length() != 0) {
/* 173 */         Element tLabel = new Element("Label");
/* 174 */         tLabel.setAttribute(new Attribute("Label", strLabel));
/* 175 */         tmpElement.addContent(tLabel);
/*     */       } 
/* 177 */       parentEl.addContent(tmpElement);
/* 178 */       return tmpElement;
/*     */     } 
/* 180 */     if (Name.startsWith("FeedbackGroup")) {
/* 181 */       List fGroupList = parentEl.getChildren("FeedbackGroup");
/* 182 */       if (fGroupList != null) {
/* 183 */         for (Iterator<Element> iter = fGroupList.iterator(); iter.hasNext(); ) {
/* 184 */           tmpElement = iter.next();
/* 185 */           String Wert = tmpElement.getAttributeValue("ID");
/* 186 */           if (Name.compareTo("FeedbackGroup" + Wert) == 0)
/* 187 */             return tmpElement; 
/* 188 */           tmpElement = null;
/*     */         } 
/*     */       }
/*     */       
/* 192 */       if (tmpElement == null) {
/* 193 */         tmpElement = new Element("FeedbackGroup");
/* 194 */         Attribute att = new Attribute("ID", Name.substring("FeedbackGroup".length()));
/* 195 */         tmpElement.setAttribute(att);
/* 196 */         String strLabel = this.parent.getFeedbackElement().getKeyValue(Name);
/* 197 */         if (strLabel != null && strLabel.length() != 0) {
/* 198 */           Element tLabel = new Element("Label");
/* 199 */           tLabel.setAttribute(new Attribute("Label", strLabel));
/* 200 */           tmpElement.addContent(tLabel);
/*     */         } 
/* 202 */         parentEl.addContent(tmpElement);
/* 203 */         return tmpElement;
/*     */       } 
/*     */     } 
/* 206 */     if (Name.startsWith("FeedbackElement")) {
/* 207 */       List fGroupList = parentEl.getChildren("FeedbackElement");
/* 208 */       if (fGroupList != null) {
/* 209 */         for (Iterator<Element> iter = fGroupList.iterator(); iter.hasNext(); ) {
/* 210 */           tmpElement = iter.next();
/* 211 */           String Wert = tmpElement.getAttributeValue("ID");
/* 212 */           if (Name.compareTo("FeedbackElement" + Wert) == 0)
/* 213 */             return tmpElement; 
/* 214 */           tmpElement = null;
/*     */         } 
/*     */       }
/*     */       
/* 218 */       if (tmpElement == null) {
/* 219 */         tmpElement = new Element("FeedbackElement");
/* 220 */         Attribute att = new Attribute("ID", Name.substring("FeedbackElement".length()));
/* 221 */         tmpElement.setAttribute(att);
/* 222 */         String strLabel = this.parent.getFeedbackElement().getKeyValue(Name);
/* 223 */         if (strLabel != null && strLabel.length() != 0)
/* 224 */           tmpElement.setAttribute(new Attribute("Label", strLabel)); 
/* 225 */         parentEl.addContent(tmpElement);
/* 226 */         return tmpElement;
/*     */       } 
/*     */     } 
/* 229 */     if (Name.compareTo("Setting") == 0 && tmpElement != null && tmpElement.getAttributes() != null) {
/* 230 */       tmpElement = new Element("Setting");
/* 231 */       parentEl.addContent(tmpElement);
/* 232 */       return tmpElement;
/*     */     } 
/* 234 */     if (Name.compareTo("SelectionObject") == 0 && tmpElement != null && tmpElement.getAttributes() != null) {
/* 235 */       tmpElement = new Element("SelectionObject");
/* 236 */       parentEl.addContent(tmpElement);
/* 237 */       return tmpElement;
/*     */     } 
/* 239 */     if (Name.compareTo("Text") == 0 && tmpElement != null && tmpElement.getAttributes() != null) {
/* 240 */       tmpElement = new Element("Text");
/* 241 */       parentEl.addContent(tmpElement);
/* 242 */       return tmpElement;
/*     */     } 
/* 244 */     if (Name.compareTo("Object") == 0 && tmpElement != null && tmpElement.getAttributes() != null) {
/* 245 */       tmpElement = new Element("Object");
/* 246 */       parentEl.addContent(tmpElement);
/* 247 */       return tmpElement;
/*     */     } 
/* 249 */     if (tmpElement == null) {
/* 250 */       tmpElement = new Element(Name);
/* 251 */       parentEl.addContent(tmpElement);
/* 252 */       return tmpElement;
/*     */     } 
/* 254 */     return tmpElement;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/* 259 */     return this.context.getLabel("button.send");
/*     */   }
/*     */   
/*     */   public Object onClick(Map params) {
/* 263 */     boolean outputEmptyFields = outputEmptyFields();
/* 264 */     final boolean isXML = outputXML();
/*     */     
/* 266 */     if (!checkMandatories()) {
/* 267 */       return this.parent.getWarningPopup(this.parent, this.context.getMessage("feedback.mandatories.missing"), this.parent.getTopLevelContainer());
/*     */     }
/*     */     
/* 270 */     if (mailSystemAvailable()) {
/* 271 */       String ret = "";
/* 272 */       String data = "";
/*     */       
/* 274 */       if (isXML) {
/*     */         try {
/* 276 */           data = getDocXML(params, outputEmptyFields);
/* 277 */         } catch (RuntimeException ex) {
/* 278 */           throw new RuntimeException();
/*     */         } 
/*     */       }
/*     */       
/* 282 */       final String dataHTML = getDocHTML(params, outputEmptyFields);
/*     */       
/* 284 */       final List<String> recipients = new LinkedList();
/*     */       
/*     */       try {
/* 287 */         recipients.add(getEmailRecipient(params));
/* 288 */         final String dataXML = data;
/*     */ 
/*     */         
/* 291 */         MailService service = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 292 */         MailService.Callback callback = new MailService.Callback()
/*     */           {
/*     */             public DataSource[] getAttachments() {
/* 295 */               (new DataSource[2])[0] = FeedbackSubmitButton.this.getAttachement(true, dataXML); (new DataSource[2])[1] = FeedbackSubmitButton.this.getAttachement(false, dataHTML); (new DataSource[1])[0] = FeedbackSubmitButton.this.getAttachement(false, dataHTML); return isXML ? new DataSource[2] : new DataSource[1];
/*     */             }
/*     */             
/*     */             public Collection getRecipients() {
/* 299 */               return recipients;
/*     */             }
/*     */             
/*     */             public String getSender() {
/* 303 */               return ApplicationContext.getInstance().getProperty("frame.feedback.mail.sender");
/*     */             }
/*     */             
/*     */             public String getSubject() {
/* 307 */               return ApplicationContext.getInstance().getProperty("frame.feedback.mail.subject");
/*     */             }
/*     */             
/*     */             public String getText() {
/* 311 */               return "see attachment";
/*     */             }
/*     */             
/*     */             public Collection getReplyTo() {
/* 315 */               return null;
/*     */             }
/*     */           };
/*     */ 
/*     */         
/* 320 */         service.send(callback);
/* 321 */         ret = this.context.getLabel("feedback.send.ok");
/*     */       }
/* 323 */       catch (Exception e) {
/* 324 */         log.error("unable to send mail - exception:" + e);
/* 325 */         ret = this.context.getLabel("feedback.send.failed");
/*     */       } 
/*     */       
/* 328 */       ret = "<HTML><HEAD><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></HEAD><BODY>" + ret + "</BODY></HTML>";
/* 329 */       return new ResultObject(0, ret);
/*     */     } 
/*     */ 
/*     */     
/* 333 */     StringBuffer tmp = new StringBuffer();
/* 334 */     tmp.append("<html><head><style type=\"text/css\"><!-- @media print {button{display:none}}--></style><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>");
/* 335 */     Element feedbackFaxHeader = this.parent.getFeedbackFaxHeader();
/* 336 */     if (feedbackFaxHeader != null) {
/* 337 */       injectFeedbackHeader(tmp, feedbackFaxHeader);
/*     */     }
/* 339 */     tmp.append("<p><ul>");
/* 340 */     for (Iterator<String> iter = this.ids.iterator(); iter.hasNext(); ) {
/* 341 */       String value, key = iter.next();
/* 342 */       Object _value = params.get(key);
/*     */       
/* 344 */       if (_value instanceof String[]) {
/* 345 */         value = ((String[])_value)[0];
/*     */       } else {
/* 347 */         value = (String)_value;
/*     */       } 
/* 349 */       if (value != null || outputEmptyFields) {
/*     */         
/* 351 */         tmp.append("<li>");
/* 352 */         tmp.append(key.substring(0, key.lastIndexOf(".")) + " -> " + ((value == null) ? "" : value));
/* 353 */         tmp.append("</li>");
/*     */       } 
/*     */     } 
/* 356 */     tmp.append("</ul></p><p><button onclick=\"javascript:window.print()\">");
/* 357 */     tmp.append(this.context.getLabel("print"));
/* 358 */     tmp.append("</button></p></body></html>");
/* 359 */     return new ResultObject(0, tmp.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDocXML(Map params, boolean outputEmptyFields) throws RuntimeException {
/* 365 */     Document doc = new Document();
/* 366 */     Element rootElement = new Element("Feedback");
/* 367 */     rootElement.setAttribute(new Attribute("Version", this.parent.getFeedbackElement().getKeyValue("FeedbackFormVersion")));
/* 368 */     Attribute att = new Attribute("ID", (new Timestamp((new Date()).getTime())).toString());
/* 369 */     rootElement.setAttribute(att);
/* 370 */     doc.setRootElement(rootElement);
/* 371 */     for (Iterator<String> iter = this.ids.iterator(); iter.hasNext(); ) {
/* 372 */       String elementIdentifier = iter.next();
/* 373 */       if (params.containsKey(elementIdentifier)) {
/* 374 */         if (params.get(elementIdentifier) == null) {
/* 375 */           log.debug("there is no submit value for element identifier: " + elementIdentifier); continue;
/*     */         } 
/* 377 */         String value = "";
/*     */         
/* 379 */         Object submitValue = params.get(elementIdentifier);
/* 380 */         if (submitValue instanceof String[]) {
/* 381 */           value = ((String[])submitValue)[0];
/*     */         } else {
/* 383 */           value = (String)submitValue;
/*     */         } 
/*     */         
/* 386 */         if (value.length() > 0 || outputEmptyFields) {
/* 387 */           addToSummary(doc, elementIdentifier.substring(0, elementIdentifier.lastIndexOf(".")) + " -> " + value);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 392 */     removeGroupID(doc);
/* 393 */     StringWriter sw = new StringWriter();
/* 394 */     XMLOutputter xmlOut = new XMLOutputter();
/* 395 */     xmlOut.setIndent("   ");
/* 396 */     xmlOut.setNewlines(true);
/*     */     try {
/* 398 */       xmlOut.output(doc, sw);
/* 399 */     } catch (IOException ioe) {
/* 400 */       log.error("XML is not written - error:" + ioe, ioe);
/* 401 */       throw new RuntimeException();
/*     */     } 
/* 403 */     return sw.toString();
/*     */   }
/*     */   
/*     */   private String getDocHTML(Map params, boolean outputEmptyFields) {
/* 407 */     String dataHTML = parseHtml(params, outputEmptyFields);
/* 408 */     if ((dataHTML.indexOf("<html>") == -1 && dataHTML.indexOf("</html>") == -1) || (dataHTML.indexOf("<HTML>") == -1 && dataHTML.indexOf("</HTML>") == -1)) {
/* 409 */       dataHTML = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>FeedbackMail</title></head><body>" + dataHTML + "</body></html>";
/*     */     }
/* 411 */     return dataHTML;
/*     */   }
/*     */   
/*     */   private DataSource getAttachement(final boolean isXML, final String data) {
/* 415 */     DataSource attachment = new DataSource()
/*     */       {
/*     */         public String getContentType() {
/* 418 */           return isXML ? "text/xml;charset=\"UTF-8\"" : "text/html;charset=\"UTF-8\"";
/*     */         }
/*     */         
/*     */         public InputStream getInputStream() throws IOException {
/* 422 */           return new ByteArrayInputStream(data.getBytes("utf-8"));
/*     */         }
/*     */         
/*     */         public String getName() {
/* 426 */           return "feedback." + (isXML ? "xml" : "html");
/*     */         }
/*     */         
/*     */         public OutputStream getOutputStream() throws IOException {
/* 430 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */     
/* 434 */     return attachment;
/*     */   }
/*     */   
/*     */   private void injectFeedbackHeader(StringBuffer tmp, Element feedbackFaxHeader) {
/* 438 */     String label = feedbackFaxHeader.getAttributeValue("Label");
/* 439 */     boolean isBold = "true".equalsIgnoreCase(feedbackFaxHeader.getAttributeValue("Bold"));
/* 440 */     if (isBold) {
/* 441 */       tmp.append("<b>");
/*     */     }
/* 443 */     tmp.append("<big>" + label + "</big>");
/* 444 */     if (isBold) {
/* 445 */       tmp.append("</b>");
/*     */     }
/* 447 */     tmp.append("<br><br>");
/*     */   }
/*     */   
/*     */   private String getEmailRecipient(Map params) {
/* 451 */     String ret = null;
/* 452 */     String keyPrefix = "FeedbackHeader.FeedbackProcessing@EMail.";
/* 453 */     for (Iterator<String> iter = this.ids.iterator(); iter.hasNext() && ret == null; ) {
/* 454 */       String elementIdentifier = iter.next();
/* 455 */       if (elementIdentifier.startsWith(keyPrefix)) {
/* 456 */         ret = (String)params.get(elementIdentifier);
/*     */       }
/*     */     } 
/* 459 */     return ret;
/*     */   }
/*     */   
/*     */   protected String parseHtml(Map submitParams, boolean withEmptyFields) {
/* 463 */     String ret = "";
/* 464 */     if (submitParams != null) {
/* 465 */       String html = this.parent.getCurrentHtmlContent();
/* 466 */       ret = InputsParser.getFilledForm(html, submitParams, withEmptyFields);
/*     */     } 
/* 468 */     return ret;
/*     */   }
/*     */   
/*     */   private void removeGroupID(Document doc) {
/* 472 */     Element workEl = doc.getRootElement().getChild("FeedbackHeader").getChild("Settings");
/* 473 */     List fGroupList = workEl.getChildren("Group");
/* 474 */     if (fGroupList != null) {
/* 475 */       for (Iterator<Element> iter = fGroupList.iterator(); iter.hasNext(); ) {
/* 476 */         Element tmpElement = iter.next();
/* 477 */         if (tmpElement.getAttribute("ID") != null) {
/* 478 */           tmpElement.removeAttribute("ID");
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean checkMandatories() {
/* 485 */     for (Iterator<TextInputElement> it = this.mandatories.iterator(); it.hasNext(); ) {
/* 486 */       TextInputElement element = it.next();
/* 487 */       if (Util.isNullOrEmpty(element.getValue())) {
/* 488 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 492 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementatio\\ui\html\home\FeedbackSubmitButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */