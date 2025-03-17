/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*     */ import com.eoos.gm.tis2web.feedback.service.FeedbackService;
/*     */ import com.eoos.gm.tis2web.feedback.service.FeedbackServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
/*     */ import com.eoos.gm.tis2web.help.service.HelpService;
/*     */ import com.eoos.gm.tis2web.help.service.HelpServiceProvider;
/*     */ import com.eoos.gm.tis2web.news.service.NewsService;
/*     */ import com.eoos.gm.tis2web.news.service.NewsServiceProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.system.context.ModuleContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.db.SIOCPRElement;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.bookmarks.BookmarkPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainer;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.numbersearch.NumberSearchPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.TextSearchPanel;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.BulletinSearchPanel;
/*     */ import com.eoos.gm.tis2web.si.service.SIService;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.base.ClientContextBase;
/*     */ import com.eoos.html.element.HtmlElementHook;
/*     */ import com.eoos.html.element.input.UnhandledSubmit;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
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
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class MainPage
/*     */   extends MainPage
/*     */ {
/*  60 */   private static final Logger log = Logger.getLogger(MainPage.class);
/*     */ 
/*     */   
/*     */   public MainPage(ClientContext context) {
/*  64 */     super(context);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MainPage getInstance(ClientContext context) {
/*  69 */     synchronized (context.getLockObject()) {
/*  70 */       MainPage instance = (MainPage)context.getObject(MainPage.class);
/*  71 */       if (instance == null) {
/*  72 */         instance = new MainPage(context);
/*  73 */         context.storeObject(MainPage.class, instance);
/*     */       } 
/*  75 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getModuleType() {
/*  80 */     return "si";
/*     */   }
/*     */   
/*     */   protected HtmlElementHook createMainHook() {
/*  84 */     return MainHook.getInstance((ClientContext)this.context);
/*     */   }
/*     */   
/*     */   protected Object onUnhandledSubmit(Map params) {
/*  88 */     Object result = null;
/*  89 */     if (this.mainHook instanceof UnhandledSubmit) {
/*  90 */       result = ((UnhandledSubmit)this.mainHook).handleSubmit(params);
/*     */     }
/*  92 */     return (result != null) ? result : this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object onClick_Help(Map submitParams) {
/*     */     try {
/*  98 */       HelpService hmi = HelpServiceProvider.getInstance().getService();
/*     */       
/* 100 */       ModuleContext mcontext = ModuleContext.getInstance(((ClientContext)this.context).getSessionID());
/*     */       
/* 102 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 103 */       ci.put("moduleid", "si");
/* 104 */       ci.put("pageid", mcontext.getPageIdentifier());
/*     */       
/* 106 */       Object<Object> sit = null;
/*     */       try {
/* 108 */         if (mcontext.getPageIdentifier().equals("SI_DISPLAY")) {
/* 109 */           SITOCElement selectedNode = ((CTOCTree.NodeWrapper)TocTree.getInstance((ClientContext)this.context).getSelectedNode()).node;
/* 110 */           if (selectedNode != null) {
/* 111 */             if (selectedNode instanceof CTOCNode) {
/* 112 */               sit = (Object<Object>)((CTOCNode)selectedNode).getProperty((SITOCProperty)CTOCProperty.SIT);
/* 113 */             } else if (selectedNode instanceof SIO) {
/* 114 */               List ctocelements = (List)((SIO)selectedNode).getProperty((SITOCProperty)SIOProperty.SIT);
/* 115 */               if (ctocelements != null) {
/* 116 */                 List<Object> tmp = new LinkedList();
/* 117 */                 Iterator<CTOCElement> iter = ctocelements.iterator();
/* 118 */                 while (iter.hasNext()) {
/* 119 */                   CTOCElement element = iter.next();
/* 120 */                   if (element.hasProperty((SITOCProperty)CTOCProperty.SIT)) {
/* 121 */                     tmp.add(element.getProperty((SITOCProperty)CTOCProperty.SIT));
/*     */                   }
/*     */                 } 
/* 124 */                 sit = (Object<Object>)tmp;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/* 129 */       } catch (Exception e) {}
/*     */ 
/*     */       
/* 132 */       ci.put("sit", sit);
/* 133 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 134 */     } catch (Exception e) {
/* 135 */       log.error("unable to retrieve help ui -error:" + e, e);
/* 136 */       return super.onClick_Help(submitParams);
/*     */     } 
/*     */   }
/*     */   protected Object onClick_Feedback(Map params) {
/*     */     try {
/*     */       DocumentContainer dc;
/* 142 */       HashMap<Object, Object> moduleParams = new HashMap<Object, Object>();
/* 143 */       SIO sio = null;
/* 144 */       int ui = ((MainHook)this.mainHook).getCurrentUI();
/* 145 */       switch (ui) {
/*     */         case 2:
/* 147 */           dc = TextSearchPanel.getInstance((ClientContext)this.context).getDocumentPage().getDocContainer();
/* 148 */           if (dc != null) {
/* 149 */             sio = dc.getSIO();
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 154 */           dc = NumberSearchPanel.getInstance((ClientContext)this.context).getDocumentPage().getDocContainer();
/* 155 */           if (dc != null) {
/* 156 */             sio = dc.getSIO();
/*     */           }
/*     */           break;
/*     */         
/*     */         case 4:
/* 161 */           dc = BookmarkPanel.getInstance((ClientContext)this.context).getDocumentPage().getDocContainer();
/* 162 */           if (dc != null) {
/* 163 */             sio = dc.getSIO();
/*     */           }
/*     */           break;
/*     */         
/*     */         case 5:
/* 168 */           dc = BulletinSearchPanel.getInstance((ClientContext)this.context).getDocumentPage().getDocContainer();
/* 169 */           if (dc != null) {
/* 170 */             sio = dc.getSIO();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case 6:
/* 176 */           sio = SpecialBrochuresContext.getInstance((ClientContext)this.context).getSelectedSIO();
/*     */           break;
/*     */         
/*     */         default:
/* 180 */           dc = DocumentPage.getInstance((ClientContext)this.context).getDocContainer();
/* 181 */           if (dc != null) {
/* 182 */             sio = dc.getSIO();
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 189 */       if (sio != null) {
/* 190 */         boolean isTSB = false;
/* 191 */         if (sio.getProperty((SITOCProperty)SIOProperty.SIT) != null) {
/* 192 */           List<CTOCNode> sits = (List)sio.getProperty((SITOCProperty)SIOProperty.SIT);
/* 193 */           for (int i = 0; i < sits.size(); i++) {
/* 194 */             CTOCNode node = sits.get(i);
/* 195 */             String sit = (String)node.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 196 */             if (sit != null && 
/* 197 */               sit.equalsIgnoreCase("SIT-12")) {
/*     */ 
/*     */               
/* 200 */               isTSB = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 206 */         if (isTSB) {
/* 207 */           moduleParams.put("ServiceInformationType", "TSB");
/*     */         }
/* 209 */         else if (sio.hasProperty((SITOCProperty)SIOProperty.LU) && sio.hasProperty((SITOCProperty)SIOProperty.SIT)) {
/* 210 */           List sits = (List)sio.getProperty((SITOCProperty)SIOProperty.SIT);
/* 211 */           boolean isCPR = false;
/* 212 */           boolean isWD = false;
/* 213 */           for (Iterator<CTOCElement> it = sits.iterator(); it.hasNext(); ) {
/* 214 */             CTOCElement node = it.next();
/* 215 */             if (node.hasProperty((SITOCProperty)CTOCProperty.SIT) && ((String)node.getProperty((SITOCProperty)CTOCProperty.SIT)).equalsIgnoreCase("SIT-15")) {
/* 216 */               isWD = true; continue;
/*     */             } 
/* 218 */             if (node.hasProperty((SITOCProperty)CTOCProperty.SIT) && ((String)node.getProperty((SITOCProperty)CTOCProperty.SIT)).equalsIgnoreCase("SIT-10")) {
/* 219 */               isCPR = true;
/*     */             }
/*     */           } 
/* 222 */           if (isWD) {
/* 223 */             moduleParams.put("ServiceInformationType", "WD");
/* 224 */             moduleParams.put("WiringDiagram", sio.getProperty((SITOCProperty)SIOProperty.LU));
/*     */           }
/* 226 */           else if (isCPR) {
/* 227 */             moduleParams.put("ServiceInformationType", "CPR");
/* 228 */             moduleParams.put("ElectronicSystem-Number", sio.getProperty((SITOCProperty)SIOProperty.LU));
/*     */           } else {
/*     */             
/* 231 */             moduleParams.put("ServiceInformationType", sio.getType().toString());
/*     */           } 
/*     */         } else {
/*     */           
/* 235 */           moduleParams.put("ServiceInformationType", sio.getType().toString());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 240 */         if (sio.hasProperty((SITOCProperty)SIOProperty.LU)) {
/* 241 */           moduleParams.put("LiteratureUnit", sio.getProperty((SITOCProperty)SIOProperty.LU));
/*     */         }
/* 243 */         if (sio.hasProperty((SITOCProperty)SIOProperty.ElectronicSystem) && 
/* 244 */           sio.getType().toString().compareTo("CPR") == 0) {
/* 245 */           SIOCPRElement cprSIO = (SIOCPRElement)sio;
/* 246 */           moduleParams.put("ElectronicSystem-Number", "" + cprSIO.getElectronicSystemCode());
/* 247 */           moduleParams.put("ElectronicSystem", cprSIO.getElectronicSystemLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale())));
/*     */         } 
/*     */ 
/*     */         
/* 251 */         if (sio.hasProperty((SITOCProperty)SIOProperty.WD)) {
/* 252 */           moduleParams.put("WiringDiagram", sio.getProperty((SITOCProperty)SIOProperty.WD).toString());
/*     */         }
/*     */       } else {
/* 255 */         moduleParams.put("ServiceInformationType", "SI");
/*     */       } 
/*     */       
/* 258 */       FeedbackService mi = FeedbackServiceProvider.getInstance().getService();
/* 259 */       return mi.getUI(((ClientContext)this.context).getSessionID(), getModuleType(), moduleParams, params);
/* 260 */     } catch (Exception e) {
/* 261 */       log.error("loading feedback -" + getModuleType() + " error:" + e, e);
/* 262 */       return super.onClick_Feedback(params);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object onClick_News(Map submitParams) {
/*     */     try {
/* 269 */       NewsService hmi = NewsServiceProvider.getInstance().getService();
/*     */       
/* 271 */       ModuleContext mcontext = ModuleContext.getInstance(((ClientContext)this.context).getSessionID());
/*     */       
/* 273 */       Map<Object, Object> ci = new HashMap<Object, Object>();
/* 274 */       ci.put("moduleid", "si");
/* 275 */       ci.put("pageid", mcontext.getPageIdentifier());
/*     */       
/* 277 */       Object<Object> sit = null;
/*     */       try {
/* 279 */         if (mcontext.getPageIdentifier().equals("SI_DISPLAY")) {
/* 280 */           SITOCElement selectedNode = ((CTOCTree.NodeWrapper)TocTree.getInstance((ClientContext)this.context).getSelectedNode()).node;
/* 281 */           if (selectedNode != null) {
/* 282 */             if (selectedNode instanceof CTOCNode) {
/* 283 */               sit = (Object<Object>)((CTOCNode)selectedNode).getProperty((SITOCProperty)CTOCProperty.SIT);
/* 284 */             } else if (selectedNode instanceof SIO) {
/* 285 */               List ctocelements = (List)((SIO)selectedNode).getProperty((SITOCProperty)SIOProperty.SIT);
/* 286 */               if (ctocelements != null) {
/* 287 */                 List<Object> tmp = new LinkedList();
/* 288 */                 Iterator<CTOCElement> iter = ctocelements.iterator();
/* 289 */                 while (iter.hasNext()) {
/* 290 */                   CTOCElement element = iter.next();
/* 291 */                   if (element.hasProperty((SITOCProperty)CTOCProperty.SIT)) {
/* 292 */                     tmp.add(element.getProperty((SITOCProperty)CTOCProperty.SIT));
/*     */                   }
/*     */                 } 
/* 295 */                 sit = (Object<Object>)tmp;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/* 300 */       } catch (Exception e) {}
/*     */ 
/*     */       
/* 303 */       ci.put("sit", sit);
/* 304 */       return hmi.getUI(((ClientContext)this.context).getSessionID(), ci);
/* 305 */     } catch (Exception e) {
/* 306 */       log.error("unable to retrieve news ui -error:" + e, e);
/* 307 */       return super.onClick_News(submitParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected VCLinkElement createVCLink() {
/* 312 */     return new VCLinkElement((ClientContext)this.context, new VCLinkElement.Callback()
/*     */         {
/*     */           public Object getReturnUI() {
/* 315 */             SIService service = (SIService)ConfiguredServiceProvider.getInstance().getService(SIService.class);
/*     */             
/* 317 */             return service.getUI(((ClientContext)MainPage.this.context).getSessionID(), null);
/*     */           }
/*     */           
/*     */           public boolean isMandatory(Object key, IConfiguration currentCfg) {
/* 321 */             return false;
/*     */           }
/*     */           
/*     */           public boolean isReadonly(Object key) {
/* 325 */             return false;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Object searchDocument(Map submitParams) {
/* 332 */     String number = (String)submitParams.get("nb");
/* 333 */     ((MainHook)this.mainHook).switchUI(3);
/* 334 */     NumberSearchPanel.getInstance(getContext()).searchDocument(number);
/* 335 */     return new ResultObject(0, getHtmlCode(submitParams));
/*     */   }
/*     */   
/*     */   public URL getSearchURL(String number) {
/*     */     try {
/* 340 */       URI baseURL = new URI(ApplicationContext.getInstance().getServerURL());
/* 341 */       URI dispatch = new URI(this.context.constructDispatchURL((Dispatchable)this, "searchDocument") + "&nb=" + number);
/* 342 */       return baseURL.resolve(dispatch).toURL();
/* 343 */     } catch (MalformedURLException e) {
/* 344 */       throw new RuntimeException(e);
/* 345 */     } catch (URISyntaxException e) {
/* 346 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\main\MainPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */