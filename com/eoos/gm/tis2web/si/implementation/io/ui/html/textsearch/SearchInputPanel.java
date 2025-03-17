/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.SitSurrogateImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.HitListSI;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.fts.implementation.IFTS;
/*     */ import com.eoos.gm.tis2web.fts.implementation.service.FTSSIO;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.vcr.VCRUtil;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree.TocTree;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree.TocTreeElement;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VehicleContextData;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.html.element.input.UnhandledSubmit;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class SearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements UnhandledSubmit
/*     */ {
/*  51 */   private static Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   
/*  53 */   public static int DEFAULT_CUTOFF = 200;
/*     */   
/*  55 */   private static int cutoff = 0;
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  60 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinput.html", null).toString();
/*  61 */     } catch (Exception e) {
/*  62 */       log.error("unable to load template - error:" + e, e);
/*  63 */       throw new RuntimeException();
/*     */     } 
/*     */     try {
/*  66 */       String maxHits = ApplicationContext.getInstance().getProperty("frame.fts.max-hits");
/*  67 */       if (maxHits != null) {
/*  68 */         cutoff = Integer.valueOf(maxHits).intValue();
/*     */       }
/*  70 */     } catch (Exception x) {}
/*     */     
/*  72 */     if (cutoff == 0) {
/*  73 */       cutoff = DEFAULT_CUTOFF;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TextInputElement ieText;
/*     */   
/*     */   private SelectBoxSelectionElement ieField;
/*     */   
/*     */   private SelectBoxSelectionElement ieSIT;
/*     */   
/*     */   private ClickButtonElement buttonSearch;
/*     */   
/*     */   private LinkageSelectBox ieLinkage;
/*  89 */   private HtmlElement result = null;
/*     */   
/*  91 */   private HitListSI serviceSI = null;
/*     */ 
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  95 */     this.context = context;
/*     */     
/*  97 */     this.ieText = new TextInputElement(context.createID(), 50, -1);
/*  98 */     addElement((HtmlElement)this.ieText);
/*     */     
/* 100 */     this.ieField = new SearchFieldSelectionElement(context);
/* 101 */     addElement((HtmlElement)this.ieField);
/*     */     
/* 103 */     this.ieSIT = (SelectBoxSelectionElement)new SITSelectBox(context);
/* 104 */     addElement((HtmlElement)this.ieSIT);
/*     */     
/* 106 */     this.ieLinkage = new LinkageSelectBox(context);
/* 107 */     addElement((HtmlElement)this.ieLinkage);
/*     */     
/* 109 */     this.buttonSearch = new ClickButtonElement(context.createID(), null) {
/*     */         public Object onClick(Map params) {
/* 111 */           return SearchInputPanel.this.search();
/*     */         }
/*     */ 
/*     */         
/*     */         public String getLabel() {
/* 116 */           return context.getLabel("search");
/*     */         }
/*     */       };
/* 119 */     addElement((HtmlElement)this.buttonSearch);
/*     */     
/*     */     try {
/* 122 */       this.serviceSI = new HitListSI(context);
/* 123 */     } catch (Exception e) {
/* 124 */       log.error("unable to create hitlistservice - error:" + e, e);
/* 125 */       throw new RuntimeException();
/*     */     } 
/* 127 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 130 */             SearchInputPanel.this.reset();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 137 */     this.result = null;
/* 138 */     this.ieField.setValue(null);
/* 139 */     this.ieLinkage.setValue((Object)null);
/* 140 */     this.ieSIT.setValue(null);
/* 141 */     this.ieText.setValue(null);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 145 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 147 */     StringUtilities.replace(code, "{TITLE}", this.context.getLabel("si.text.search"));
/*     */     
/* 149 */     StringUtilities.replace(code, "{TEXT_TEXT_INPUT}", this.context.getLabel("si.text.search.searchstring") + ":");
/* 150 */     StringUtilities.replace(code, "{INPUT_TEXT}", this.ieText.getHtmlCode(params));
/*     */     
/* 152 */     StringUtilities.replace(code, "{TEXT_FIELD_SELECTION}", this.context.getLabel("si.text.search.field") + ":");
/* 153 */     StringUtilities.replace(code, "{INPUT_FIELD_SELECTION}", this.ieField.getHtmlCode(params));
/*     */     
/* 155 */     StringUtilities.replace(code, "{TEXT_SIT_SELECTION}", this.context.getLabel("si.text.search.sit") + ":");
/* 156 */     StringUtilities.replace(code, "{INPUT_SIT_SELECTION}", this.ieSIT.getHtmlCode(params));
/*     */     
/* 158 */     StringUtilities.replace(code, "{TEXT_LINKAGE}", this.context.getLabel("linkage") + ":");
/* 159 */     StringUtilities.replace(code, "{SELECTION_LINKAGE}", this.ieLinkage.getHtmlCode(params));
/*     */     
/* 161 */     StringUtilities.replace(code, "{BUTTON_SEARCH}", this.buttonSearch.getHtmlCode(params));
/* 162 */     StringUtilities.replace(code, "{RESULT}", (this.result == null) ? "" : this.result.getHtmlCode(params));
/* 163 */     StringUtilities.replace(code, "{SEARCH_VEHICLE_STATE}", "<b>" + getSearchVehicleState() + "</b>");
/*     */     
/* 165 */     return code.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSearchVehicleState() {
/* 170 */     if (VCRUtil.vehicleSelected(VCFacade.getInstance(this.context).getCfg())) {
/* 171 */       return this.context.getMessage("si.search.result.with.vehicle");
/*     */     }
/* 173 */     return this.context.getMessage("si.search.result.without.vehicle");
/*     */   }
/*     */ 
/*     */   
/* 177 */   static String ae = null;
/*     */   
/* 179 */   static String oe = null;
/*     */   
/* 181 */   static String ue = null;
/*     */   
/* 183 */   static String ss = null;
/*     */   static {
/*     */     try {
/* 186 */       ae = new String(new byte[] { -61, -92 }, "utf-8");
/* 187 */       oe = new String(new byte[] { -61, -74 }, "utf-8");
/* 188 */       ue = new String(new byte[] { -61, -68 }, "utf-8");
/* 189 */       ss = new String(new byte[] { -61, -97 }, "utf-8");
/* 190 */     } catch (Exception x) {
/* 191 */       log.debug("umlaut initialization failed.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String mangleQuery(String query) {
/* 201 */     if (query == null) {
/* 202 */       return null;
/*     */     }
/*     */     
/* 205 */     StringBuffer transformation = new StringBuffer(query.length());
/* 206 */     for (int i = 0; i < query.length(); i++) {
/* 207 */       char c = Character.toLowerCase(query.charAt(i));
/* 208 */       if (ae != null && ae.charAt(0) == c) {
/* 209 */         transformation.append('a');
/* 210 */         c = 'e';
/* 211 */       } else if (oe != null && oe.charAt(0) == c) {
/* 212 */         transformation.append('o');
/* 213 */         c = 'e';
/* 214 */       } else if (ue != null && ue.charAt(0) == c) {
/* 215 */         transformation.append('u');
/* 216 */         c = 'e';
/* 217 */       } else if (ss != null && ss.charAt(0) == c) {
/* 218 */         transformation.append('s');
/* 219 */         c = 's';
/*     */       } 
/* 221 */       transformation.append(c);
/*     */     } 
/* 223 */     return transformation.toString();
/*     */   }
/*     */   
/*     */   protected Object search() {
/*     */     try {
/* 228 */       String text = (String)this.ieText.getValue();
/* 229 */       SITOCElement sit = (SITOCElement)this.ieSIT.getValue();
/* 230 */       String field = (String)this.ieField.getValue();
/* 231 */       Integer linkage = (Integer)this.ieLinkage.getValue();
/* 232 */       int operator = -1;
/* 233 */       if (linkage.equals(LinkageSelectBox.AND)) {
/* 234 */         operator = 1;
/* 235 */       } else if (linkage.equals(LinkageSelectBox.OR)) {
/* 236 */         operator = 0;
/*     */       } else {
/* 238 */         operator = 2;
/*     */       } 
/* 240 */       LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(this.context.getLocale());
/* 241 */       if ("de".equalsIgnoreCase(locale.getLanguage())) {
/* 242 */         text = mangleQuery(text);
/*     */       }
/*     */       
/*     */       try {
/* 246 */         vCR = getLVCAdapter().toVCR(VCFacade.getInstance(this.context).getCfg());
/* 247 */       } catch (Exception e1) {
/* 248 */         vCR = VCR.NULL;
/*     */       } 
/*     */       
/* 251 */       VCR vCR = VehicleContextData.getInstance(this.context).filterAuthorizedVCR(vCR, getLVCAdapter().createRetrievalImpl());
/* 252 */       long startTime = System.currentTimeMillis();
/*     */       try {
/* 254 */         Collection sios = this.serviceSI.query(locale, vCR, (CTOCNode)sit, field, text, operator);
/* 255 */         long endTime = System.currentTimeMillis();
/* 256 */         log.info("-------Total Time used to get fts: " + (endTime - startTime) + " ms--------");
/* 257 */         return handleSearchResult((CTOCNode)sit, sios);
/* 258 */       } catch (com.eoos.gm.tis2web.fts.implementation.IFTS.MaximumExceededException e) {
/* 259 */         return NotificationMessageBox.createInfoMessage(this.context, null, this.context.getMessage("fts.currently.unavailable"), getTopLevelContainer());
/*     */       } 
/* 261 */     } catch (Throwable e) {
/* 262 */       log.error("unable to execute search - error:" + e, e);
/* 263 */       HtmlElementContainer container = getContainer();
/* 264 */       while (container.getContainer() != null) {
/* 265 */         container = container.getContainer();
/*     */       }
/* 267 */       return container;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ILVCAdapter getLVCAdapter() {
/* 273 */     return SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */   }
/*     */   protected Object handleSearchResult(CTOCNode sitSelected, Collection sios) {
/*     */     HtmlLabel htmlLabel;
/* 277 */     HtmlElement result = null;
/* 278 */     if (sios != null) {
/* 279 */       SharedContextProxy.getInstance(this.context);
/*     */       
/*     */       try {
/* 282 */         vCR = getLVCAdapter().toVCR(VCFacade.getInstance(this.context).getCfg());
/* 283 */       } catch (Exception e1) {
/* 284 */         vCR = VCR.NULL;
/*     */       } 
/*     */       
/* 287 */       VCR vCR = VehicleContextData.getInstance(this.context).filterAuthorizedVCR(vCR, getLVCAdapter().createRetrievalImpl());
/* 288 */       long startTime = System.currentTimeMillis();
/*     */       
/* 290 */       List children = null;
/* 291 */       Object child = null;
/*     */       
/* 293 */       SitSurrogateImpl sitSurrogate = new SitSurrogateImpl(cutoff, this.context, sios, vCR);
/* 294 */       if (sitSelected != null) {
/* 295 */         children = sitSurrogate.searchSIOs(sitSelected);
/*     */         
/* 297 */         if (children.size() == 1) {
/* 298 */           child = children.get(0);
/* 299 */           if (child instanceof FTSSIO) {
/* 300 */             child = ((FTSSIO)child).lookupSIO();
/*     */           }
/*     */         } 
/*     */       } else {
/* 304 */         children = sitSurrogate.searchSITs();
/*     */       } 
/*     */ 
/*     */       
/* 308 */       long endTime = System.currentTimeMillis();
/* 309 */       log.info("----Time used to apply filter on sios: " + (endTime - startTime) + " ms ------");
/*     */       
/* 311 */       if (children != null && children.size() != 0) {
/*     */         
/* 313 */         if (child != null) {
/* 314 */           TextSearchPanel.getInstance(this.context).showDocument(child);
/*     */         }
/*     */         else {
/*     */           
/* 318 */           final HtmlLabel label = new HtmlLabel("{SEARCH_VEHICLE_STATE}");
/* 319 */           final TocTreeElement element = new TocTreeElement(this.context, new TocTree(this.context, sitSurrogate, sitSelected));
/* 320 */           addElement((HtmlElement)element);
/* 321 */           HtmlElementContainerBase htmlElementContainerBase = new HtmlElementContainerBase() {
/*     */               public String getHtmlCode(Map params) {
/* 323 */                 return label.getHtmlCode(params) + "<br>" + element.getHtmlCode(params);
/*     */               }
/*     */             };
/*     */         } 
/*     */       } else {
/* 328 */         htmlLabel = new HtmlLabel("{SEARCH_VEHICLE_STATE}<br>" + this.context.getMessage("si.search.no.result"));
/*     */       } 
/*     */     } else {
/* 331 */       htmlLabel = new HtmlLabel("{SEARCH_VEHICLE_STATE}<br>" + this.context.getMessage("si.search.no.result"));
/*     */     } 
/*     */     
/* 334 */     removeElement(this.result);
/* 335 */     this.result = (HtmlElement)htmlLabel;
/* 336 */     if (this.result != null) {
/* 337 */       addElement(this.result);
/*     */     }
/*     */     
/* 340 */     HtmlElementContainer container = getContainer();
/* 341 */     while (container.getContainer() != null) {
/* 342 */       container = container.getContainer();
/*     */     }
/* 344 */     return container;
/*     */   }
/*     */   
/*     */   public Object handleSubmit(Map params) {
/* 348 */     return search();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */