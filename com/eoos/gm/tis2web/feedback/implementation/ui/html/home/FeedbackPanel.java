/*     */ package com.eoos.gm.tis2web.feedback.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.gm.tis2web.feedback.implementation.data.IgnoreDataBeforeRootElement;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.WarningMessageBox;
/*     */ import com.eoos.gm.tis2web.lt.service.LTService;
/*     */ import com.eoos.gm.tis2web.si.service.SIService;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.jdom.JDOMException;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FeedbackPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(FeedbackPanel.class); static {
/*     */     try {
/*  47 */       template = ApplicationContext.getInstance().loadFile(FeedbackPanel.class, "home.html", null).toString();
/*  48 */     } catch (Exception e) {
/*  49 */       log.error("unable to load template - error:" + e, e);
/*  50 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String template;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private FeedbackFormSelection feedbackFormSelection;
/*     */   
/*     */   private FeedbackContentPanel feedbackContentPanel;
/*     */   
/*     */   private FeedbackElementHeader fel;
/*     */   private Element feedbackFaxHeader;
/*     */   private FeedbackSubmitButton submit;
/*     */   private String currentHtmlContent;
/*     */   
/*     */   public void setFeedbackFaxHeader(Element feedbackFaxHeader) {
/*  69 */     this.feedbackFaxHeader = feedbackFaxHeader;
/*     */   }
/*     */   
/*     */   public Element getFeedbackFaxHeader() {
/*  73 */     return this.feedbackFaxHeader;
/*     */   }
/*     */   
/*     */   public FeedbackPanel(ClientContext context, String moduleType, Map moduleParams) {
/*  77 */     this.feedbackContentPanel = null;
/*  78 */     this.submit = null;
/*  79 */     this.currentHtmlContent = "";
/*  80 */     this.context = context;
/*  81 */     this.fel = loadFeedbackSettings(moduleType, moduleParams);
/*  82 */     this.feedbackFormSelection = new FeedbackFormSelection(context, this);
/*  83 */     setFeedbackContentPanel(this.feedbackFormSelection.getFormValues());
/*  84 */     addElement((HtmlElement)this.feedbackFormSelection);
/*     */   }
/*     */   
/*     */   public static synchronized FeedbackPanel getInstance(ClientContext context, String moduleType, Map moduleParams) {
/*  88 */     return new FeedbackPanel(context, moduleType, moduleParams);
/*     */   }
/*     */   
/*     */   public FeedbackElementHeader loadFeedbackSettings(String moduleType, Map moduleParams) {
/*  92 */     FeedbackElementHeader ret = new FeedbackElementHeader();
/*  93 */     Locale current = this.context.getLocale();
/*  94 */     String lanID = current.toString();
/*  95 */     if (lanID.indexOf("_") == -1) {
/*  96 */       String countryID = ApplicationContext.getInstance().getProperty("frame.langcountry." + lanID);
/*  97 */       if (countryID != null)
/*  98 */         lanID = lanID + "_" + countryID; 
/*     */     } 
/* 100 */     ret.setLocale(lanID);
/* 101 */     ret.setChoosenLocale(lanID);
/* 102 */     ret.setParam("Locale", current.getDisplayLanguage(current));
/* 103 */     new Locale("en", "US");
/* 104 */     ret.setParam("Locale-En", lanID);
/* 105 */     current = new Locale(this.context.getLocale().getLanguage(), this.context.getSharedContext().getCountry());
/* 106 */     ret.setParam("Country", current.getDisplayCountry(current));
/* 107 */     ret.setParam("Country-En", current.getCountry());
/* 108 */     ret.setFallbackLocale("en_US");
/* 109 */     ret.setType(moduleType);
/*     */     try {
/* 111 */       if (moduleParams != null) {
/* 112 */         Set keys = moduleParams.keySet();
/* 113 */         for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
/* 114 */           String key = iter.next();
/* 115 */           ret.setParam(key, (String)moduleParams.get(key));
/* 116 */           if (key.compareTo("ServiceInformationType") == 0) {
/* 117 */             String wert = (String)moduleParams.get(key);
/* 118 */             if (wert.compareTo("SI") == 0) {
/* 119 */               ret.setType("scds"); continue;
/*     */             } 
/* 121 */             ret.setType(wert);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 127 */         ApplicationContext appContext = (ApplicationContext)this.context.getApplicationContext();
/* 128 */         if (appContext != null) {
/* 129 */           ret.setParam("ApplicationVersion", String.valueOf(appContext.getProperty("frame.application.description")));
/* 130 */           ret.setParam("ApplicationVersion-Number", "" + String.valueOf(appContext.getProperty("frame.application.build")));
/*     */         }
/*     */       
/* 133 */       } catch (Exception esi) {}
/*     */       
/* 135 */       String currentMake = VCFacade.getInstance(this.context).getCurrentSalesmake();
/* 136 */       if (currentMake == null) {
/* 137 */         currentMake = "no-sm";
/*     */       } else {
/* 139 */         String vin = VCFacade.getInstance(this.context).getCurrentVIN();
/* 140 */         if (vin == null) {
/* 141 */           vin = "";
/*     */         }
/* 143 */         ret.setParam("VIN", vin);
/* 144 */         IConfiguration cfg = VCFacade.getInstance(this.context).getCfg();
/* 145 */         for (int i = 0; i < VehicleConfigurationUtil.KEYS.length; i++) {
/* 146 */           Object key = VehicleConfigurationUtil.KEYS[i];
/* 147 */           if (key == VehicleConfigurationUtil.KEY_MAKE) {
/* 148 */             ret.setParam("SalesMake", currentMake);
/* 149 */           } else if (key == VehicleConfigurationUtil.KEY_MODELYEAR) {
/* 150 */             String value = VehicleConfigurationUtil.getConfigurationValue(cfg, key);
/* 151 */             ret.setParam("ModelYear", (value != null) ? value : "");
/*     */           } else {
/* 153 */             String value = VehicleConfigurationUtil.getConfigurationValue(cfg, key);
/* 154 */             ret.setParam(VehicleConfigurationUtil.keyToString(key, Locale.ENGLISH), (value != null) ? value : "");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 160 */         Module module = (Module)ConfiguredServiceProvider.getInstance().getService(SIService.class);
/* 161 */         ModuleInformation moduleInfo = module.getModuleInformation();
/* 162 */         if (moduleInfo != null) {
/* 163 */           ret.setParam("ModuleVersion-SI", String.valueOf(moduleInfo.getDescription(this.context.getLocale())));
/* 164 */           ret.setParam("ModuleVersion-SI-Number", "" + String.valueOf(moduleInfo.getVersion()));
/* 165 */           Object object = moduleInfo.getDatabaseVersionInformation();
/* 166 */           DBVersionInformation dbVersionInformation = null;
/* 167 */           if (object != null && object instanceof Collection) {
/* 168 */             dbVersionInformation = (DBVersionInformation)CollectionUtil.getFirst((Collection)object);
/*     */           } else {
/* 170 */             dbVersionInformation = (DBVersionInformation)object;
/*     */           } 
/* 172 */           ret.setParam("DatabaseVersion-SI", dbVersionInformation.getReleaseDescription());
/* 173 */           ret.setParam("DatabaseVersion-SI-Number", dbVersionInformation.getReleaseVersion());
/*     */         }
/*     */       
/* 176 */       } catch (Exception esi) {
/* 177 */         log.error("Exception: " + esi, esi);
/*     */       } 
/*     */       try {
/* 180 */         Module module = (Module)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/* 181 */         ModuleInformation moduleInfo = module.getModuleInformation();
/* 182 */         if (moduleInfo != null) {
/* 183 */           ret.setParam("ModuleVersion-LT", String.valueOf(moduleInfo.getDescription(this.context.getLocale())));
/* 184 */           ret.setParam("ModuleVersion-LT-Number", "" + String.valueOf(moduleInfo.getVersion()));
/* 185 */           Object object = moduleInfo.getDatabaseVersionInformation();
/* 186 */           DBVersionInformation dbVersionInformation = null;
/* 187 */           if (object != null && object instanceof Collection) {
/* 188 */             dbVersionInformation = (DBVersionInformation)CollectionUtil.getFirst((Collection)object);
/*     */           } else {
/* 190 */             dbVersionInformation = (DBVersionInformation)object;
/*     */           } 
/*     */           
/* 193 */           if (dbVersionInformation != null) {
/* 194 */             ret.setParam("DatabaseVersion-LT", dbVersionInformation.getReleaseDescription());
/*     */             
/* 196 */             ret.setParam("DatabaseVersion-LT-Number", dbVersionInformation.getReleaseVersion());
/*     */           } 
/*     */         } 
/* 199 */       } catch (Exception esi) {
/* 200 */         log.error("Exception: " + esi, esi);
/*     */       }
/*     */     
/* 203 */     } catch (Exception fme) {
/* 204 */       throw new RuntimeException(fme);
/*     */     } 
/* 206 */     return ret;
/*     */   }
/*     */   
/*     */   protected FeedbackElementHeader getFeedbackElement() {
/* 210 */     return this.fel;
/*     */   }
/*     */   
/*     */   public void setFeedbackElementHeader(FeedbackElementHeader fel) {
/* 214 */     this.fel = fel;
/*     */   }
/*     */   
/*     */   public void setFeedbackContentPanel(String[] Label) {
/* 218 */     if (this.feedbackContentPanel == null) {
/* 219 */       if (Label[1].compareTo("") != 0) {
/* 220 */         this.feedbackContentPanel = new FeedbackContentPanel(this, this.context);
/* 221 */         this.feedbackContentPanel.setContent(Label[2]);
/*     */       } 
/*     */     } else {
/* 224 */       this.feedbackContentPanel.setContent(Label[2]);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Document parse(ByteArrayInputStream template) {
/* 229 */     Document ret = null;
/* 230 */     SAXBuilder builder = new SAXBuilder();
/* 231 */     builder.setValidation(false);
/*     */     
/* 233 */     try { ret = builder.build(template); }
/* 234 */     catch (JDOMException jde) {  }
/* 235 */     catch (IOException ioe) {}
/*     */     
/*     */     try {
/* 238 */       if (ret == null) {
/* 239 */         template.reset();
/* 240 */         ByteArrayInputStream stream = IgnoreDataBeforeRootElement.compareByteArray(template);
/*     */         try {
/* 242 */           ret = builder.build(stream);
/* 243 */         } catch (JDOMException x) {
/* 244 */           if (stream != null) {
/* 245 */             stream.reset();
/* 246 */             byte[] b = new byte[stream.available()];
/* 247 */             stream.read(b);
/* 248 */             stream.close();
/* 249 */             log.error(new String(b, "utf-8"));
/*     */           } 
/* 251 */           log.error("Template is not wellformed - error:" + x, (Throwable)x);
/* 252 */           throw new RuntimeException();
/*     */         } 
/* 254 */         if (stream != null)
/* 255 */           stream.close(); 
/*     */       } 
/* 257 */     } catch (IOException ioe) {
/* 258 */       log.error("Template is not readable - error:" + ioe, ioe);
/*     */     } 
/* 260 */     return ret;
/*     */   }
/*     */   
/*     */   protected Element getLocaleLabel(List labels, String Locale, boolean change) {
/* 264 */     Element ret = null;
/* 265 */     Iterator<Element> iter = labels.iterator();
/*     */     boolean found;
/* 267 */     for (found = false; iter.hasNext() && !found; ) {
/* 268 */       Element Label = iter.next();
/* 269 */       found = (Locale.equalsIgnoreCase(Label.getAttributeValue("Locale").toString()) || Locale.startsWith(Label.getAttributeValue("Locale").toString()));
/* 270 */       if (found) {
/* 271 */         ret = Label;
/*     */       }
/*     */     } 
/* 274 */     if (!found)
/* 275 */       ret = getCurrentLanguageFallbackLocale(labels, Locale.substring(0, 2), change); 
/* 276 */     if (ret != null && change)
/* 277 */       getFeedbackElement().setChoosenLocale(ret.getAttributeValue("Locale")); 
/* 278 */     return ret;
/*     */   }
/*     */   
/*     */   protected Element getCurrentLanguageFallbackLocale(List labels, String Locale, boolean change) {
/* 282 */     Element ret = null;
/* 283 */     Iterator<Element> iter = labels.iterator();
/*     */     boolean found;
/* 285 */     for (found = false; iter.hasNext() && !found; ) {
/* 286 */       Element Label = iter.next();
/* 287 */       found = (Label.getAttributeValue("Locale").compareTo(Locale) == 0);
/* 288 */       if (found) {
/* 289 */         ret = Label;
/*     */       }
/*     */     } 
/* 292 */     if (!found && Locale.compareTo("en") != 0)
/* 293 */       ret = getCurrentLanguageFallbackLocale(labels, "en", change); 
/* 294 */     if (!found)
/* 295 */       ret = getEnUnderScoreLabel(labels); 
/* 296 */     return ret;
/*     */   }
/*     */   
/*     */   protected Element getEnUnderScoreLabel(List labels) {
/* 300 */     Element ret = null;
/* 301 */     Iterator<Element> iter = labels.iterator();
/*     */     
/* 303 */     for (boolean found = false; iter.hasNext() && !found; ) {
/* 304 */       Element Label = iter.next();
/* 305 */       found = (Label.getAttributeValue("Locale").substring(0, 3).compareTo("en_") == 0);
/* 306 */       if (found)
/* 307 */         ret = Label; 
/*     */     } 
/* 309 */     return ret;
/*     */   }
/*     */   
/*     */   public void setSubmit(FeedbackSubmitButton submit) {
/* 313 */     this.submit = submit;
/*     */   }
/*     */   
/*     */   public FeedbackSubmitButton getSubmit() {
/* 317 */     return this.submit;
/*     */   }
/*     */   
/*     */   protected String getCurrentHtmlContent() {
/* 321 */     return this.currentHtmlContent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 326 */     StringBuffer code = new StringBuffer(template);
/* 327 */     StringUtilities.replace(code, "{SELECTIONLABEL}", this.context.getLabel("feedback.selection.label"));
/* 328 */     StringUtilities.replace(code, "{FEEDBACKSELECTION}", this.feedbackFormSelection.getHtmlCode(params));
/* 329 */     String content = "";
/* 330 */     if (this.feedbackContentPanel != null) {
/* 331 */       this.currentHtmlContent = this.feedbackContentPanel.getHtmlCode(params);
/* 332 */       content = content + this.currentHtmlContent;
/*     */     } 
/* 334 */     StringUtilities.replace(code, "{FEEDBACKCONTENT}", content);
/* 335 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected Object getWarningPopup(FeedbackPanel parent, String message) {
/* 339 */     return getWarningPopup(parent, message, (Object)null);
/*     */   }
/*     */   
/*     */   public Object getWarningPopup(final FeedbackPanel parent, String message, final Object onOKReturnValue) {
/* 343 */     return new WarningMessageBox(this.context, null, message)
/*     */       {
/*     */         protected Object onOK(Map params) {
/* 346 */           return (onOKReturnValue == null) ? parent.feedbackContentPanel : onOKReturnValue;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 353 */     this.feedbackContentPanel.reset();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementatio\\ui\html\home\FeedbackPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */