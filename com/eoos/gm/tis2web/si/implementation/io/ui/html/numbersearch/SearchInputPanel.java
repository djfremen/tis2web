/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.numbersearch;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceUtil;
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCSurrogate;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.NodeComparatorOrder;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.gtwo.PagedElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchInputPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  57 */   private static Logger log = Logger.getLogger(SearchInputPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  62 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "searchinput.html", null).toString();
/*  63 */     } catch (Exception e) {
/*  64 */       log.error("unable to load template - error:" + e, e);
/*  65 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TextInputElement ieBulletinNumber;
/*     */   
/*     */   private ClickButtonElement buttonSearchBulletin;
/*     */   
/*     */   private TextInputElement ieDocumentID;
/*     */   
/*     */   private ClickButtonElement buttonSearchDocument;
/*  79 */   private HtmlElement result = null;
/*     */ 
/*     */   
/*     */   public SearchInputPanel(final ClientContext context) {
/*  83 */     this.context = context;
/*     */     
/*  85 */     this.ieBulletinNumber = new TextInputElement(context.createID());
/*  86 */     addElement((HtmlElement)this.ieBulletinNumber);
/*     */     
/*  88 */     this.ieDocumentID = new TextInputElement(context.createID());
/*  89 */     addElement((HtmlElement)this.ieDocumentID);
/*     */     
/*  91 */     this.buttonSearchBulletin = new ClickButtonElement(context.createID(), null) {
/*     */         public Object onClick(Map params) {
/*  93 */           return SearchInputPanel.this.searchBulletin();
/*     */         }
/*     */         
/*     */         public String getLabel() {
/*  97 */           return context.getLabel("si.search.matching.document");
/*     */         }
/*     */       };
/* 100 */     addElement((HtmlElement)this.buttonSearchBulletin);
/*     */     
/* 102 */     this.buttonSearchDocument = new ClickButtonElement(context.createID(), null) {
/*     */         public Object onClick(Map params) {
/* 104 */           return SearchInputPanel.this.searchDocument();
/*     */         }
/*     */         
/*     */         public String getLabel() {
/* 108 */           return context.getLabel("si.search.matching.document");
/*     */         }
/*     */       };
/*     */     
/* 112 */     addElement((HtmlElement)this.buttonSearchDocument);
/* 113 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/* 116 */             SearchInputPanel.this.reset();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 124 */     this.result = null;
/* 125 */     this.ieBulletinNumber.setValue(null);
/* 126 */     this.ieDocumentID.setValue(null);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 130 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 132 */     StringUtilities.replace(code, "{TEXT_ENTER_BULLETIN}", this.context.getMessage("si.number.search.enter.bulletin"));
/* 133 */     StringUtilities.replace(code, "{INPUT_BULLETIN}", this.ieBulletinNumber.getHtmlCode(params));
/* 134 */     StringUtilities.replace(code, "{TEXT_ENTER_DOCUMENT}", this.context.getMessage("si.number.search.enter.document"));
/* 135 */     StringUtilities.replace(code, "{INPUT_DOCUMENT}", this.ieDocumentID.getHtmlCode(params));
/*     */     
/* 137 */     StringUtilities.replace(code, "{BUTTON_SEARCH_BULLETIN}", this.buttonSearchBulletin.getHtmlCode(params));
/* 138 */     StringUtilities.replace(code, "{BUTTON_SEARCH_DOCUMENT}", this.buttonSearchDocument.getHtmlCode(params));
/*     */     
/* 140 */     StringUtilities.replace(code, "{RESULT}", (this.result == null) ? "" : this.result.getHtmlCode(params));
/* 141 */     return code.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object searchBulletin() {
/* 146 */     String number = (String)this.ieBulletinNumber.getValue();
/* 147 */     this.ieDocumentID.setValue("");
/* 148 */     if (number != null) {
/* 149 */       return handleSearchResult(SIDataAdapterFacade.getInstance(this.context).getSI().searchDocumentsByPublicationID(number));
/*     */     }
/* 151 */     return getTopLevelContainer();
/*     */   }
/*     */   
/*     */   protected Object handleSearchResult(CTOCNode node) {
/*     */     HtmlLabel htmlLabel;
/* 156 */     HtmlElement result = null;
/* 157 */     if (node != null) {
/*     */       
/* 159 */       String country = SharedContextProxy.getInstance(this.context).getCountry();
/*     */       
/* 161 */       ILVCAdapter adapter = SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */       
/* 163 */       VCR vcr = adapter.toVCR(VCFacade.getInstance(this.context).getCfg());
/* 164 */       if (vcr == null) {
/* 165 */         vcr = VCR.NULL;
/*     */       }
/* 167 */       List<SITOCElement> childs = ((CTOCSurrogate)node).getChildren(node, LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()), country, vcr);
/* 168 */       if (childs != null && childs.size() != 0) {
/* 169 */         if (childs.size() == 1) {
/* 170 */           NumberSearchPanel.getInstance(this.context).showDocument(childs.get(0));
/*     */         } else {
/* 172 */           Collections.sort(childs, (Comparator<? super SITOCElement>)NodeComparatorOrder.getInstance());
/* 173 */           final List<SITOCElement> results = childs;
/* 174 */           ListElement listElement = new SearchResultListElement(this.context, new DataRetrievalAbstraction.DataCallback() {
/*     */                 public List getData() {
/* 176 */                   return results;
/*     */                 }
/*     */               });
/* 179 */           PagedElement pagedElement = new PagedElement(this.context.createID(), (HtmlElement)listElement, 10, 10);
/*     */         } 
/*     */       } else {
/* 182 */         htmlLabel = new HtmlLabel(this.context.getMessage("si.search.no.result"));
/*     */       } 
/*     */     } else {
/* 185 */       htmlLabel = new HtmlLabel(this.context.getMessage("si.search.no.result"));
/*     */     } 
/*     */     
/* 188 */     removeElement(this.result);
/* 189 */     this.result = (HtmlElement)htmlLabel;
/* 190 */     if (this.result != null) {
/* 191 */       addElement(this.result);
/*     */     }
/*     */     
/* 194 */     HtmlElementContainer container = getContainer();
/* 195 */     while (container.getContainer() != null) {
/* 196 */       container = container.getContainer();
/*     */     }
/* 198 */     return container;
/*     */   }
/*     */   
/*     */   protected Object searchDocument() {
/* 202 */     String number = (String)this.ieDocumentID.getValue();
/* 203 */     this.ieBulletinNumber.setValue("");
/* 204 */     return searchDocument(number);
/*     */   }
/*     */   
/*     */   public Object searchDocument(String number) {
/* 208 */     if (!Util.isNullOrEmpty(number)) {
/* 209 */       CTOCNode node = null;
/* 210 */       node = SIDataAdapterFacade.getInstance(this.context).getSI().searchDocumentsByNumber(number);
/* 211 */       return handleSearchResult(node);
/*     */     } 
/* 213 */     return getTopLevelContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   protected List getSecurityFilter(CTOCNode node) {
/* 218 */     SharedContextProxy scp = SharedContextProxy.getInstance(this.context);
/* 219 */     String country = scp.getCountry();
/* 220 */     ILVCAdapter adapter = SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */     
/* 222 */     VCR vcr = adapter.toVCR(VCFacade.getInstance(this.context).getCfg());
/* 223 */     if (vcr == null) {
/* 224 */       vcr = VCR.NULL;
/*     */     }
/* 226 */     SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(this.context).getSICTOCService();
/*     */     
/* 228 */     List<?> filters = node.filterSITs(siCTOCService.getCTOC().getCTOC(CTOCDomain.SIT), LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()), country, vcr);
/*     */     
/* 230 */     final Set sits = new HashSet();
/*     */     try {
/* 232 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 233 */       sits.addAll(aclMI.getAuthorizedResources("SIT", scp.getUsrGroup2Manuf(), country));
/* 234 */     } catch (Exception fme) {
/* 235 */       throw new ExceptionWrapper(fme);
/*     */     } 
/*     */     
/* 238 */     Filter filter = new Filter() {
/*     */         public boolean include(Object obj) {
/*     */           try {
/* 241 */             CTOCNode node = (CTOCNode)obj;
/*     */             
/* 243 */             if (sits.contains(String.valueOf(CTOCServiceUtil.extractSITKey(node)))) {
/* 244 */               return true;
/*     */             }
/* 246 */             return false;
/*     */           }
/* 248 */           catch (Exception e) {
/* 249 */             return false;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 254 */     filters = (filters != null) ? new ArrayList(filters) : null;
/* 255 */     return convertSITFilter((List)CollectionUtil.filterAndReturn(filters, filter));
/*     */   }
/*     */   
/*     */   protected List convertSITFilter(List ctocNodeList) {
/* 259 */     List<Object> retValue = new LinkedList();
/*     */     try {
/* 261 */       Iterator<CTOCNode> iter = ctocNodeList.iterator();
/* 262 */       while (iter.hasNext()) {
/* 263 */         CTOCNode tmp = iter.next();
/* 264 */         retValue.add(tmp.getProperty((SITOCProperty)CTOCProperty.SIT));
/*     */       } 
/* 266 */     } catch (NullPointerException e) {}
/*     */ 
/*     */     
/* 269 */     return (retValue.size() == 0) ? null : retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\numbersearch\SearchInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */