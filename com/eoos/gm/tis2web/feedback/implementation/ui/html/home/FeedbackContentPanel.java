/*     */ package com.eoos.gm.tis2web.feedback.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.gm.tis2web.feedback.implementation.data.FeedbackForms;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Attribute;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.output.XMLOutputter;
/*     */ 
/*     */ 
/*     */ public class FeedbackContentPanel
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(FeedbackContentPanel.class);
/*     */   
/*     */   private String content;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private FeedbackPanel parent;
/*     */   
/*     */   private List elementIdentifiers;
/*     */   
/*     */   private List elementsMandatory;
/*     */   
/*     */   private Map cachedElements;
/*     */   
/*     */   private Map options;
/*     */   
/*     */   public FeedbackContentPanel(FeedbackPanel feedbackPanel, ClientContext context) {
/*  47 */     this.cachedElements = new HashMap<Object, Object>();
/*  48 */     this.options = new HashMap<Object, Object>();
/*  49 */     this.elementIdentifiers = new LinkedList();
/*  50 */     this.elementsMandatory = new LinkedList();
/*  51 */     this.parent = feedbackPanel;
/*  52 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected void setContent(String templateName) {
/*  56 */     this.content = templateName;
/*     */   }
/*     */   
/*     */   private Document generateTransformXML(String templateName) throws Exception {
/*     */     Document ret;
/*  61 */     ByteArrayInputStream bais = null;
/*  62 */     bais = new ByteArrayInputStream(FeedbackForms.getInstance().loadFeedbackForm(templateName));
/*     */     try {
/*  64 */       ret = process(bais);
/*     */     } finally {
/*  66 */       bais.close();
/*     */     } 
/*  68 */     return ret;
/*     */   }
/*     */   
/*     */   private Document process(ByteArrayInputStream input) throws Exception {
/*  72 */     Document ret = null;
/*  73 */     this.elementIdentifiers.clear();
/*  74 */     ret = this.parent.parse(input);
/*  75 */     if (ret != null) {
/*  76 */       Element root = ret.getRootElement();
/*  77 */       if (!root.getAttributes().isEmpty()) {
/*  78 */         this.parent.getFeedbackElement().setParam("FeedbackFormVersion", root.getAttributeValue("Version"));
/*     */       } else {
/*  80 */         root.setAttribute(new Attribute("Version", "0.0"));
/*  81 */       }  Element header = ret.getRootElement().getChild("FeedbackHeader");
/*  82 */       changeHeader(header);
/*  83 */       Element body = ret.getRootElement().getChild("FeedbackBody");
/*  84 */       changeBody(body);
/*  85 */       Element lanBody = minimizeHTMLObjects(body, "FeedbackBody", proofLocale(body));
/*  86 */       ret.getRootElement().removeContent(body);
/*  87 */       ret.getRootElement().addContent(lanBody);
/*     */     } 
/*  89 */     return ret;
/*     */   }
/*     */   
/*     */   private void changeHeader(Object header) {
/*  93 */     String xmlPath = "FeedbackHeader";
/*  94 */     Element head = (Element)header;
/*  95 */     Element feedbackType = head.getChild("FeedbackType");
/*  96 */     String curPath = xmlPath + ".FeedbackType";
/*  97 */     String id = this.context.createID();
/*  98 */     String idName = "FeedbackTypeID";
/*  99 */     TextInputElement tInput = new TextInputElement(curPath + "@" + idName + "." + id);
/* 100 */     this.elementIdentifiers.add(curPath + "@" + idName + "." + id);
/* 101 */     Attribute att = new Attribute("Name", curPath + "@" + idName + "." + id);
/* 102 */     feedbackType.setAttribute(att);
/* 103 */     minimizeLabelOption(feedbackType, null);
/* 104 */     this.parent.getFeedbackElement().setParam("FeedbackType", feedbackType.getChild("Label").getAttributeValue("Xml"));
/* 105 */     Element feedbackForm = head.getChild("FeedbackForm");
/* 106 */     curPath = xmlPath + ".FeedbackForm";
/* 107 */     List<Attribute> feedbackFormList = feedbackForm.getAttributes();
/*     */     int count;
/* 109 */     for (count = feedbackFormList.size(); count > 0; count--) {
/* 110 */       Attribute el = feedbackFormList.get(count - 1);
/* 111 */       if (el.getName().startsWith("Locale"))
/* 112 */         this.parent.getFeedbackElement().setParam("Feedback-Locale", el.getValue()); 
/* 113 */       id = this.context.createID();
/* 114 */       idName = el.getName();
/* 115 */       tInput = new TextInputElement(curPath + "@" + idName + "." + id);
/* 116 */       this.elementIdentifiers.add(curPath + "@" + idName + "." + id);
/* 117 */       att = new Attribute("Name_" + (count - 1), curPath + "@" + idName + "." + id);
/* 118 */       feedbackForm.setAttribute(att);
/*     */     } 
/*     */     
/* 121 */     count = 0;
/* 122 */     Element feedbackProcessing = head.getChild("FeedbackProcessing");
/* 123 */     curPath = xmlPath + ".FeedbackProcessing";
/* 124 */     id = this.context.createID();
/* 125 */     idName = feedbackProcessing.getAttribute("EMail").getName();
/* 126 */     tInput = new TextInputElement(curPath + "@" + idName + "." + id);
/* 127 */     this.elementIdentifiers.add(curPath + "@" + idName + "." + id);
/* 128 */     att = new Attribute("Name", curPath + "@" + idName + "." + id);
/* 129 */     feedbackProcessing.setAttribute(att);
/*     */     try {
/* 131 */       String locale = this.parent.getFeedbackElement().getLocale();
/* 132 */       List<Element> feedbackHeaderList = feedbackProcessing.getChildren("FeedbackFaxHeader");
/* 133 */       for (count = feedbackHeaderList.size(); count > 0; count--) {
/* 134 */         Element el = feedbackHeaderList.get(count - 1);
/* 135 */         Attribute attr = el.getAttribute("Locale");
/* 136 */         if (isMatch(locale, attr) && (this.parent.getFeedbackFaxHeader() == null || isBestMatch(attr))) {
/* 137 */           this.parent.setFeedbackFaxHeader(el);
/*     */         }
/*     */       } 
/* 140 */       feedbackProcessing.removeChildren("FeedbackFaxHeader");
/* 141 */     } catch (Exception noFeedbackHeaderElements) {}
/*     */     
/* 143 */     Element settings = head.getChild("Settings");
/* 144 */     minimizeLabelOption(settings, null);
/* 145 */     this.parent.getFeedbackElement().setParam("Settings", settings.getChild("Label").getAttributeValue("Xml"));
/* 146 */     List groupList = settings.getChildren("Group");
/* 147 */     for (Iterator<Element> iter = groupList.iterator(); iter.hasNext(); ) {
/* 148 */       Element group = iter.next();
/* 149 */       List settingList = group.getChildren("Setting");
/*     */       
/* 151 */       for (Iterator<Element> iterSetting = settingList.iterator(); iterSetting.hasNext(); setting.setAttribute("ItemID", curPath + "@" + idName + "." + id)) {
/* 152 */         curPath = xmlPath + ".Settings.Group" + count + ".Setting";
/* 153 */         Element setting = iterSetting.next();
/* 154 */         String value = this.parent.getFeedbackElement().getKeyValue(setting.getAttribute("ItemID").getValue());
/* 155 */         id = this.context.createID();
/* 156 */         idName = setting.getAttribute("ItemID").getValue();
/* 157 */         tInput = new TextInputElement(curPath + "@" + idName + "." + id);
/* 158 */         this.elementIdentifiers.add(curPath + "@" + idName + "." + id);
/* 159 */         this.parent.addElement((HtmlElement)tInput);
/* 160 */         setting.setAttribute(new Attribute("Value", String.valueOf(value)));
/*     */       } 
/*     */       
/* 163 */       count++;
/*     */     } 
/*     */     
/* 166 */     count = 0;
/*     */   }
/*     */   
/*     */   private boolean isMatch(String locale, Attribute attr) {
/* 170 */     String value = attr.getValue();
/* 171 */     return (locale.startsWith(value) || value.startsWith(locale));
/*     */   }
/*     */   
/*     */   private boolean isBestMatch(Attribute attr) {
/* 175 */     Element selection = this.parent.getFeedbackFaxHeader();
/* 176 */     String locale = selection.getAttribute("Locale").getValue();
/* 177 */     return (attr.getValue().length() > locale.length());
/*     */   }
/*     */   
/*     */   private void changeBody(Object body) {
/* 181 */     String xmlPath = "FeedbackBody";
/* 182 */     Element bdy = (Element)body;
/* 183 */     List feedbackElementList = bdy.getChildren("FeedbackElement");
/* 184 */     changeFeedbackElementList(feedbackElementList, xmlPath);
/* 185 */     List feedbackGroupList = bdy.getChildren("FeedbackGroup");
/*     */     
/* 187 */     for (Iterator<Element> iter = feedbackGroupList.iterator(); iter.hasNext(); changeFeedbackElementList(feedbackElementList, curPath)) {
/* 188 */       Element feedbackGroup = iter.next();
/* 189 */       String curPath = xmlPath + ".FeedbackGroup" + feedbackGroup.getAttributeValue("ID");
/* 190 */       if (feedbackGroup.getChild("Label") != null) {
/* 191 */         minimizeLabelOption(feedbackGroup, null);
/* 192 */         this.parent.getFeedbackElement().setParam("FeedbackGroup" + feedbackGroup.getAttributeValue("ID"), feedbackGroup.getChild("Label").getAttributeValue("Xml"));
/*     */       } 
/* 194 */       String id = this.context.createID();
/* 195 */       String idName = feedbackGroup.getAttribute("ID").getValue();
/* 196 */       TextInputElement tInput = new TextInputElement(curPath + "@" + idName + "." + id);
/*     */       
/* 198 */       this.elementIdentifiers.add(curPath + "@" + idName + "." + id);
/* 199 */       this.parent.addElement((HtmlElement)tInput);
/* 200 */       Attribute att = new Attribute("Name", curPath + "@" + idName + "." + id);
/* 201 */       feedbackGroup.setAttribute(att);
/* 202 */       feedbackElementList = feedbackGroup.getChildren("FeedbackElement");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String proofLocale(Element el) {
/* 208 */     String ret = this.parent.getFeedbackElement().getChoosenLocale();
/* 209 */     String Locale = this.parent.getFeedbackElement().getLocale();
/* 210 */     List HTMLObjects = el.getChildren("HTMLObject");
/* 211 */     if (HTMLObjects.isEmpty()) {
/* 212 */       List feedbackGroups = el.getChildren("FeedbackGroup");
/* 213 */       if (!feedbackGroups.isEmpty()) {
/* 214 */         for (Iterator<Element> iterFeedbackGroups = feedbackGroups.iterator(); iterFeedbackGroups.hasNext(); ) {
/* 215 */           Element feedbackGroup = iterFeedbackGroups.next();
/* 216 */           ret = proofLocale(feedbackGroup);
/* 217 */           if (ret.compareTo(Locale) == 0) {
/* 218 */             return ret;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/* 223 */       Iterator<Element> iterHTMLObjects = HTMLObjects.iterator();
/* 224 */       boolean found = false;
/* 225 */       while (iterHTMLObjects.hasNext()) {
/* 226 */         Element HTMLObject = iterHTMLObjects.next();
/* 227 */         List atts = HTMLObject.getAttributes();
/* 228 */         if (!atts.isEmpty()) {
/* 229 */           Attribute att = HTMLObject.getAttribute("Locale");
/* 230 */           if (att.getValue().compareTo(Locale) == 0)
/* 231 */             return Locale; 
/*     */         } 
/*     */       } 
/* 234 */       if (!found) {
/* 235 */         List feedbackGroups = el.getChildren("FeedbackGroup");
/* 236 */         if (!feedbackGroups.isEmpty()) {
/* 237 */           for (Iterator<Element> iterFeedbackGroups = feedbackGroups.iterator(); iterFeedbackGroups.hasNext(); ) {
/* 238 */             Element feedbackGroup = iterFeedbackGroups.next();
/* 239 */             ret = proofLocale(feedbackGroup);
/* 240 */             if (ret.compareTo(Locale) == 0) {
/* 241 */               return ret;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 247 */     return ret;
/*     */   }
/*     */   
/*     */   protected Element minimizeHTMLObjects(Element el, String Name, String Locale) {
/* 251 */     Element newEl = new Element(Name);
/* 252 */     List<Object> oldattributes = new LinkedList();
/* 253 */     for (int a = 0; a < el.getAttributes().size(); a++) {
/* 254 */       Attribute obj = el.getAttributes().get(a);
/* 255 */       oldattributes.add(obj.clone());
/*     */     } 
/*     */     
/* 258 */     if (oldattributes.size() > 0)
/* 259 */       newEl.setAttributes(oldattributes); 
/* 260 */     List oldObjects = el.getChildren();
/* 261 */     for (Iterator<Element> iter = oldObjects.iterator(); iter.hasNext(); ) {
/* 262 */       Element element = iter.next();
/* 263 */       if (element.getName().compareTo("HTMLObject") == 0) {
/* 264 */         List atts = element.getAttributes();
/* 265 */         if (atts.isEmpty()) {
/* 266 */           newEl.addContent((Element)element.clone()); continue;
/*     */         } 
/* 268 */         for (Iterator<Attribute> iterAtts = atts.iterator(); iterAtts.hasNext(); ) {
/* 269 */           Attribute att = iterAtts.next();
/* 270 */           if (att.getName().compareTo("Locale") == 0 && element.getAttributeValue("Locale").compareTo(Locale) == 0)
/* 271 */             newEl.addContent((Element)element.clone()); 
/*     */         } 
/*     */         continue;
/*     */       } 
/* 275 */       if (element.getName().compareTo("FeedbackGroup") == 0) {
/* 276 */         newEl.addContent(minimizeHTMLObjects(element, "FeedbackGroup", Locale)); continue;
/*     */       } 
/* 278 */       newEl.addContent((Element)element.clone());
/*     */     } 
/*     */     
/* 281 */     return newEl;
/*     */   }
/*     */   
/*     */   private void minimizeLabelOption(Element el, String valueOptionSelected) {
/* 285 */     String Locale = this.parent.getFeedbackElement().getLocale();
/* 286 */     List labels = el.getChildren("Label");
/* 287 */     Element tmp = this.parent.getLocaleLabel(labels, Locale, false);
/* 288 */     if (!this.parent.getFeedbackElement().getChoosenLocale().startsWith("en")) {
/* 289 */       Element tmpEn = this.parent.getLocaleLabel(labels, this.parent.getFeedbackElement().getFallbackLocale(), false);
/* 290 */       el.removeChildren("Label");
/* 291 */       tmp.setAttribute(new Attribute("Xml", tmpEn.getAttribute("Label").getValue()));
/* 292 */       el.addContent(tmp);
/*     */     } else {
/* 294 */       el.removeChildren("Label");
/* 295 */       tmp.setAttribute(new Attribute("Xml", tmp.getAttribute("Label").getValue()));
/* 296 */       el.addContent(tmp);
/*     */     } 
/* 298 */     if (el.getName().equalsIgnoreCase("Option")) {
/* 299 */       Element tmpLocale = this.parent.getLocaleLabel(labels, this.parent.getFeedbackElement().getLocale(), false);
/* 300 */       this.options.put(tmpLocale.getAttributeValue("Label"), el.getAttribute("ID").getValue());
/* 301 */       if (valueOptionSelected != null) {
/* 302 */         if (valueOptionSelected.equalsIgnoreCase(tmpLocale.getAttributeValue("Label"))) {
/* 303 */           el.setAttribute(new Attribute("ValueOptionSelected", valueOptionSelected));
/*     */         } else {
/* 305 */           el.setAttribute(new Attribute("ValueOptionSelected", ""));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Object lookup(String key) {
/* 313 */     return this.cachedElements.get(key);
/*     */   }
/*     */   
/*     */   private void changeFeedbackElementList(List feedbackElementList, String xmlPath) {
/* 317 */     for (Iterator<Element> iterFeedbackElement = feedbackElementList.iterator(); iterFeedbackElement.hasNext(); ) {
/* 318 */       Element feedbackElement = iterFeedbackElement.next();
/* 319 */       String curPath = xmlPath + ".FeedbackElement" + feedbackElement.getAttribute("ID").getValue();
/* 320 */       String ID = this.context.createID();
/* 321 */       String IDName = feedbackElement.getAttribute("ID").getValue();
/*     */       
/* 323 */       String type = ".Text@";
/* 324 */       if (feedbackElement.getChildren("SelectionObject") != null && feedbackElement.getChildren("SelectionObject").size() != 0) {
/* 325 */         type = ".SelectionObject.Option@";
/*     */       }
/* 327 */       TextInputElement tInput = (TextInputElement)lookup(curPath + type + IDName);
/* 328 */       if (tInput != null) {
/* 329 */         ID = tInput.getBookmark().substring(tInput.getBookmark().lastIndexOf(".") + 1);
/*     */       } else {
/* 331 */         tInput = new TextInputElement(curPath + type + IDName + "." + ID);
/* 332 */         this.cachedElements.put(curPath + type + IDName, tInput);
/*     */       } 
/* 334 */       this.parent.addElement((HtmlElement)tInput);
/* 335 */       if (feedbackElement.getChild("Label") != null) {
/* 336 */         minimizeLabelOption(feedbackElement, null);
/* 337 */         this.parent.getFeedbackElement().setParam("FeedbackElement" + feedbackElement.getAttribute("ID").getValue(), feedbackElement.getChild("Label").getAttributeValue("Xml"));
/*     */       } 
/*     */       
/* 340 */       List textObject = feedbackElement.getChildren("TextObject");
/*     */       
/* 342 */       Element Text = null;
/* 343 */       for (Iterator<Element> iterTextObject = textObject.iterator(); iterTextObject.hasNext(); Text.setAttribute(att)) {
/* 344 */         if (!this.elementIdentifiers.contains(curPath + ".Text@" + IDName + "." + ID))
/* 345 */           this.elementIdentifiers.add(curPath + ".Text@" + IDName + "." + ID); 
/* 346 */         Attribute att = new Attribute("Name", curPath + ".Text@" + IDName + "." + ID);
/* 347 */         Text = iterTextObject.next();
/* 348 */         if ((String)tInput.getValue() != null) {
/* 349 */           Text.setAttribute(new Attribute("Value", (String)tInput.getValue()));
/*     */         }
/*     */       } 
/* 352 */       List textAreaObject = feedbackElement.getChildren("TextAreaObject");
/* 353 */       for (Iterator<Element> iterTextAreaObject = textAreaObject.iterator(); iterTextAreaObject.hasNext(); Text.setAttribute(att)) {
/* 354 */         if (!this.elementIdentifiers.contains(curPath + ".Text@" + IDName + "." + ID))
/* 355 */           this.elementIdentifiers.add(curPath + ".Text@" + IDName + "." + ID); 
/* 356 */         Attribute att = new Attribute("Name", curPath + ".Text@" + IDName + "." + ID);
/* 357 */         Text = iterTextAreaObject.next();
/* 358 */         if ((String)tInput.getValue() != null) {
/* 359 */           Text.setAttribute(new Attribute("ValueText", (String)tInput.getValue()));
/*     */         }
/*     */       } 
/*     */       
/* 363 */       List radioBoxObject = feedbackElement.getChildren("RadioBoxObject");
/* 364 */       for (Iterator<Element> iterRadioBoxObject = radioBoxObject.iterator(); iterRadioBoxObject.hasNext(); Text.setAttribute(att)) {
/* 365 */         if (!this.elementIdentifiers.contains(curPath + ".Text@" + IDName + "." + ID))
/* 366 */           this.elementIdentifiers.add(curPath + ".Text@" + IDName + "." + ID); 
/* 367 */         Attribute att = new Attribute("Name", curPath + ".Text@" + IDName + "." + ID);
/* 368 */         Text = iterRadioBoxObject.next();
/* 369 */         if (tInput.getValue() != null && tInput.getValue().toString().equalsIgnoreCase("on")) {
/* 370 */           Text.setAttribute(new Attribute("CheckedEnabled", "on"));
/*     */         } else {
/* 372 */           Text.setAttribute(new Attribute("CheckedEnabled", "off"));
/*     */         } 
/*     */       } 
/*     */       
/* 376 */       List checkBoxObject = feedbackElement.getChildren("CheckBoxObject");
/* 377 */       for (Iterator<Element> iterCheckBoxObject = checkBoxObject.iterator(); iterCheckBoxObject.hasNext(); Text.setAttribute(att)) {
/* 378 */         if (!this.elementIdentifiers.contains(curPath + ".Text@" + IDName + "." + ID))
/* 379 */           this.elementIdentifiers.add(curPath + ".Text@" + IDName + "." + ID); 
/* 380 */         Attribute att = new Attribute("Name", curPath + ".Text@" + IDName + "." + ID);
/* 381 */         Text = iterCheckBoxObject.next();
/* 382 */         if (tInput.getValue() != null && tInput.getValue().toString().equalsIgnoreCase("on")) {
/* 383 */           Text.setAttribute(new Attribute("CheckedEnabled", "on"));
/*     */         } else {
/* 385 */           Text.setAttribute(new Attribute("CheckedEnabled", "off"));
/*     */         } 
/*     */       } 
/*     */       
/* 389 */       List selectionObject = feedbackElement.getChildren("SelectionObject");
/* 390 */       for (Iterator<Element> iterSelectionObject = selectionObject.iterator(); iterSelectionObject.hasNext(); ) {
/* 391 */         if (!this.elementIdentifiers.contains(curPath + ".SelectionObject.Option@" + IDName + "." + ID)) {
/* 392 */           this.elementIdentifiers.add(curPath + ".SelectionObject.Option@" + IDName + "." + ID);
/*     */         }
/* 394 */         Attribute att = new Attribute("Name", curPath + ".SelectionObject.Option@" + IDName + "." + ID);
/* 395 */         Text = iterSelectionObject.next();
/* 396 */         Text.setAttribute(att);
/* 397 */         List optionObjects = Text.getChildren("Option");
/* 398 */         for (Iterator<Element> iterOptionObject = optionObjects.iterator(); iterOptionObject.hasNext(); ) {
/* 399 */           Element optionElement = iterOptionObject.next();
/* 400 */           if (optionElement.getChild("Label") != null) {
/* 401 */             minimizeLabelOption(optionElement, (String)tInput.getValue());
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 406 */       if (feedbackElement.getAttribute("Mandatory") != null && feedbackElement.getAttribute("Mandatory").getValue().equalsIgnoreCase("true")) {
/* 407 */         this.elementsMandatory.add(tInput);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 415 */     FeedbackSubmitButton submit = this.parent.getSubmit();
/* 416 */     if (submit == null)
/* 417 */       submit = new FeedbackSubmitButton(this.context, this.elementIdentifiers, this.elementsMandatory, this.options, this.parent); 
/* 418 */     return transform(null) + submit.getHtmlCode(params);
/*     */   }
/*     */   private String transform(Document inputDoc) {
/*     */     Document document;
/* 422 */     String ret = "";
/*     */     
/* 424 */     if (inputDoc == null) {
/*     */       try {
/* 426 */         document = generateTransformXML(this.content);
/* 427 */       } catch (Exception e) {
/* 428 */         log.error("unable to generate document, rethrowing RuntimeException - exception: " + e, e);
/* 429 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } else {
/* 432 */       document = inputDoc;
/*     */     } 
/* 434 */     XMLOutputter form = new XMLOutputter();
/* 435 */     form.setEncoding("UTF-8");
/* 436 */     form.setTrimAllWhite(true);
/* 437 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     try {
/* 439 */       form.output(document, baos);
/* 440 */     } catch (IOException ioe) {
/* 441 */       log.error("XML is not written - error:" + ioe, ioe);
/* 442 */       throw new RuntimeException();
/*     */     } 
/* 444 */     ByteArrayInputStream baisSource = new ByteArrayInputStream(baos.toByteArray());
/*     */     try {
/* 446 */       TransformerFactory tFactory = TransformerFactory.newInstance();
/* 447 */       StreamSource xmlSource = new StreamSource(baisSource);
/* 448 */       ByteArrayInputStream bais = new ByteArrayInputStream(ApplicationContext.getInstance().loadResource("/feedback/feedback.xsl"));
/* 449 */       Transformer transformer = tFactory.newTransformer(new StreamSource(bais));
/* 450 */       transformer.transform(xmlSource, new StreamResult(baos));
/* 451 */     } catch (Exception e) {
/* 452 */       log.error("XML is not transformed - error:" + e, e);
/* 453 */       throw new RuntimeException();
/*     */     } 
/*     */     try {
/* 456 */       ret = new String(baos.toByteArray(), "utf-8");
/* 457 */       int idx = ret.indexOf("<Feedback ");
/* 458 */       int edx = ret.indexOf("</Feedback>");
/* 459 */       if (idx != -1 && edx != -1) {
/* 460 */         String tmp = ret.substring(0, idx);
/* 461 */         tmp = tmp + ret.substring(edx + "</Feedback>".length());
/* 462 */         ret = tmp;
/*     */       } 
/* 464 */       if (ret.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") != -1)
/* 465 */         ret = replace(ret, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", ""); 
/* 466 */       baos.close();
/* 467 */     } catch (UnsupportedEncodingException e1) {
/* 468 */       e1.printStackTrace();
/* 469 */     } catch (IOException e) {
/* 470 */       e.printStackTrace();
/*     */     } 
/* 472 */     return ret;
/*     */   }
/*     */   
/*     */   private static String replace(String content, String expr, String repl) {
/* 476 */     String ret = content;
/* 477 */     if (content.indexOf(expr) != -1) {
/* 478 */       String before = content.substring(0, content.indexOf(expr));
/* 479 */       String after = content.substring(content.indexOf(expr) + expr.length());
/* 480 */       ret = before + repl + after;
/*     */     } 
/* 482 */     return ret;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 486 */     this.cachedElements.clear();
/* 487 */     this.elementsMandatory.clear();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementatio\\ui\html\home\FeedbackContentPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */