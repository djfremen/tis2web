/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.HitListLT;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.fts.implementation.IFTS;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.vcr.VCRUtil;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.db.SIOLTElement;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VehicleContextData;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.html.element.input.UnhandledSubmit;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements UnhandledSubmit
/*     */ {
/*  49 */   private static Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   
/*     */   protected static class LTComparator implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/*  53 */       SIOLTElement n1 = (SIOLTElement)o1;
/*  54 */       SIOLTElement n2 = (SIOLTElement)o2;
/*  55 */       return n1.getMajorOperationNumber().compareTo(n2.getMajorOperationNumber());
/*     */     }
/*     */   }
/*     */   
/*  59 */   protected static LTComparator ltComparator = new LTComparator();
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  64 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinput.html", null).toString();
/*  65 */     } catch (Exception e) {
/*  66 */       log.error("unable to load template - error:" + e, e);
/*  67 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TextInputElement ieText;
/*     */   
/*     */   private ClickButtonElement buttonSearch;
/*     */   
/*     */   private LinkageSelectBox ieLinkage;
/*  79 */   private HtmlElement result = null;
/*     */   
/*  81 */   private HitListLT serviceLT = null;
/*     */ 
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  85 */     this.context = context;
/*     */     
/*  87 */     this.ieText = new TextInputElement(context.createID(), 50, -1);
/*  88 */     addElement((HtmlElement)this.ieText);
/*     */     
/*  90 */     this.ieLinkage = new LinkageSelectBox(context);
/*  91 */     addElement((HtmlElement)this.ieLinkage);
/*     */     
/*  93 */     this.buttonSearch = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map params) {
/*  96 */           return SearchInputPanel.this.search();
/*     */         }
/*     */ 
/*     */         
/*     */         public String getLabel() {
/* 101 */           return context.getLabel("search");
/*     */         }
/*     */       };
/* 104 */     addElement((HtmlElement)this.buttonSearch);
/*     */     
/*     */     try {
/* 107 */       this.serviceLT = new HitListLT(context);
/* 108 */     } catch (Exception e) {
/* 109 */       log.error("unable to create hitlistservice - error:" + e, e);
/* 110 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 115 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 117 */     StringUtilities.replace(code, "{TITLE}", this.context.getLabel("si.text.search"));
/*     */     
/* 119 */     StringUtilities.replace(code, "{TEXT_TEXT_INPUT}", this.context.getLabel("si.text.search.searchstring") + ":");
/* 120 */     StringUtilities.replace(code, "{INPUT_TEXT}", this.ieText.getHtmlCode(params));
/*     */     
/* 122 */     StringUtilities.replace(code, "{TEXT_LINKAGE}", this.context.getLabel("linkage") + ":");
/* 123 */     StringUtilities.replace(code, "{SELECTION_LINKAGE}", this.ieLinkage.getHtmlCode(params));
/*     */     
/* 125 */     StringUtilities.replace(code, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/*     */     
/* 127 */     StringUtilities.replace(code, "{RESULT}", (this.result == null) ? "" : this.result.getHtmlCode(params));
/* 128 */     StringUtilities.replace(code, "{SEARCH_VEHICLE_STATE}", "<b>" + getSearchVehicleState() + "</b>");
/*     */     
/* 130 */     return code.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSearchVehicleState() {
/* 135 */     if (VCRUtil.vehicleSelected(VCFacade.getInstance(this.context).getCfg())) {
/* 136 */       return this.context.getMessage("lt.search.result.with.vehicle");
/*     */     }
/* 138 */     return this.context.getMessage("lt.search.result.without.vehicle");
/*     */   }
/*     */ 
/*     */   
/* 142 */   static String ae = null;
/*     */   
/* 144 */   static String oe = null;
/*     */   
/* 146 */   static String ue = null;
/*     */   
/* 148 */   static String ss = null;
/*     */   static {
/*     */     try {
/* 151 */       ae = new String(new byte[] { -61, -92 }, "utf-8");
/* 152 */       oe = new String(new byte[] { -61, -74 }, "utf-8");
/* 153 */       ue = new String(new byte[] { -61, -68 }, "utf-8");
/* 154 */       ss = new String(new byte[] { -61, -97 }, "utf-8");
/* 155 */     } catch (Exception x) {
/* 156 */       log.debug("umlaut initialization failed.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String mangleQuery(String query) {
/* 166 */     if (query == null) {
/* 167 */       return null;
/*     */     }
/*     */     
/* 170 */     StringBuffer transformation = new StringBuffer(query.length());
/* 171 */     for (int i = 0; i < query.length(); i++) {
/* 172 */       char c = Character.toLowerCase(query.charAt(i));
/* 173 */       if (ae != null && ae.charAt(0) == c) {
/* 174 */         transformation.append('a');
/* 175 */         c = 'e';
/* 176 */       } else if (oe != null && oe.charAt(0) == c) {
/* 177 */         transformation.append('o');
/* 178 */         c = 'e';
/* 179 */       } else if (ue != null && ue.charAt(0) == c) {
/* 180 */         transformation.append('u');
/* 181 */         c = 'e';
/* 182 */       } else if (ss != null && ss.charAt(0) == c) {
/* 183 */         transformation.append('s');
/* 184 */         c = 's';
/*     */       } 
/* 186 */       transformation.append(c);
/*     */     } 
/* 188 */     return transformation.toString();
/*     */   }
/*     */   
/*     */   protected Object search() {
/*     */     try {
/* 193 */       String text = (String)this.ieText.getValue();
/* 194 */       Integer linkage = (Integer)this.ieLinkage.getValue();
/* 195 */       int operator = -1;
/* 196 */       if (linkage.equals(LinkageSelectBox.AND)) {
/* 197 */         operator = 1;
/* 198 */       } else if (linkage.equals(LinkageSelectBox.OR)) {
/* 199 */         operator = 0;
/*     */       } else {
/* 201 */         operator = 2;
/*     */       } 
/* 203 */       LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(this.context.getLocale());
/* 204 */       if ("de".equalsIgnoreCase(locale.getLanguage())) {
/* 205 */         text = mangleQuery(text);
/*     */       }
/* 207 */       VCR vcr = getLVCAdapter().toVCR(VCFacade.getInstance(this.context).getCfg());
/*     */       
/*     */       try {
/* 210 */         Collection mos = this.serviceLT.query(locale, vcr, text, operator);
/* 211 */         return handleSearchResult(mos);
/* 212 */       } catch (com.eoos.gm.tis2web.fts.implementation.IFTS.MaximumExceededException e) {
/* 213 */         return NotificationMessageBox.createInfoMessage(this.context, null, this.context.getMessage("fts.currently.unavailable"), getTopLevelContainer());
/*     */       } 
/* 215 */     } catch (Throwable e) {
/* 216 */       log.error("unable to execute search - error:" + e, e);
/* 217 */       HtmlElementContainer container = getContainer();
/* 218 */       while (container.getContainer() != null) {
/* 219 */         container = container.getContainer();
/*     */       }
/* 221 */       return container;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ILVCAdapter getLVCAdapter() {
/* 227 */     return LTDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */   }
/*     */   protected Object handleSearchResult(Collection<?> mos) {
/*     */     HtmlLabel htmlLabel;
/* 231 */     HtmlElement result = null;
/*     */     
/* 233 */     if (mos != null && mos.size() != 0) {
/* 234 */       VCR vCR1; SharedContextProxy.getInstance(this.context);
/*     */       
/*     */       try {
/* 237 */         VCR xvcr = getLVCAdapter().toVCR(VCFacade.getInstance(this.context).getCfg());
/* 238 */         vCR1 = VehicleContextData.getInstance(this.context).filterAuthorizedVCR(xvcr, getLVCAdapter().createRetrievalImpl());
/* 239 */       } catch (Exception e) {
/*     */         
/* 241 */         vCR1 = null;
/*     */       } 
/* 243 */       final VCR vcr = vCR1;
/* 244 */       final List labelsIds = new ArrayList(mos.size());
/* 245 */       if (mos != null && mos.size() != 0) {
/* 246 */         final List mosResult = new ArrayList(mos.size());
/*     */         
/* 248 */         final LTClientContext ltc = LTClientContext.getInstance(this.context);
/* 249 */         Filter filter = new Filter() {
/*     */             public boolean include(Object obj) {
/* 251 */               boolean retValue = false;
/*     */               try {
/* 253 */                 if (obj instanceof SIOLTElement) {
/* 254 */                   SIOLTElement element = (SIOLTElement)obj;
/* 255 */                   if (!mosResult.contains(element.getMajorOperationNumber())) {
/* 256 */                     VCR elementVCR = element.getVCR();
/* 257 */                     if (elementVCR != null && vcr != null && elementVCR.match(vcr)) {
/* 258 */                       LTDataWork mo = ltc.getMainWork(element.getMajorOperationNumber(), false);
/* 259 */                       if (mo != null) {
/* 260 */                         labelsIds.add(element.getLabelID());
/* 261 */                         mosResult.add(element.getMajorOperationNumber());
/* 262 */                         retValue = true;
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/* 267 */               } catch (Exception e) {}
/*     */               
/* 269 */               return retValue;
/*     */             }
/*     */           };
/* 272 */         CollectionUtil.filter(mos, filter);
/*     */       } 
/* 274 */       if (mos != null && mos.size() != 0) {
/* 275 */         final List<?> mosResult = new ArrayList(mos);
/* 276 */         Collections.sort(mosResult, ltComparator);
/* 277 */         TextSearchPanel.getInstance(this.context).showSearchResult(mosResult);
/*     */         
/* 279 */         HtmlElementContainer htmlElementContainer = getContainer();
/* 280 */         while (htmlElementContainer.getContainer() != null) {
/* 281 */           htmlElementContainer = htmlElementContainer.getContainer();
/*     */         }
/* 283 */         return htmlElementContainer;
/*     */       } 
/*     */       
/* 286 */       htmlLabel = new HtmlLabel(this.context.getMessage("lt.search.no.result"));
/*     */     } else {
/*     */       
/* 289 */       htmlLabel = new HtmlLabel(this.context.getMessage("lt.search.no.result"));
/*     */     } 
/*     */     
/* 292 */     removeElement(this.result);
/* 293 */     this.result = (HtmlElement)htmlLabel;
/* 294 */     if (this.result != null) {
/* 295 */       addElement(this.result);
/*     */     }
/*     */     
/* 298 */     HtmlElementContainer container = getContainer();
/* 299 */     while (container.getContainer() != null) {
/* 300 */       container = container.getContainer();
/*     */     }
/* 302 */     return container;
/*     */   }
/*     */   
/*     */   protected List applyFilter(Collection sios, LocaleInfo locale, String country, VCR vcr) {
/* 306 */     List<SITOCElement> result = new LinkedList();
/* 307 */     if (sios != null) {
/* 308 */       for (Iterator<SITOCElement> it = sios.iterator(); it.hasNext(); ) {
/* 309 */         SITOCElement sio = it.next();
/* 310 */         if (!checkSio(sio, locale, vcr)) {
/*     */           continue;
/*     */         }
/* 313 */         result.add(sio);
/*     */         
/* 315 */         return result;
/*     */       } 
/*     */     }
/*     */     
/* 319 */     return (result.size() > 0) ? result : null;
/*     */   }
/*     */   
/*     */   protected boolean checkSio(SITOCElement sio, LocaleInfo locale, VCR vcr) {
/* 323 */     if (vcr != null && vcr != VCR.NULL && sio.getVCR() != null && !sio.getVCR().match(locale, vcr)) {
/* 324 */       return false;
/*     */     }
/*     */     
/* 327 */     return true;
/*     */   }
/*     */   
/*     */   public Object handleSubmit(Map params) {
/* 331 */     return search();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\textsearch\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */