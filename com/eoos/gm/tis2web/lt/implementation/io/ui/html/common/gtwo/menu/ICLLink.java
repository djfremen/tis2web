/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.icl.ICLContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page.LTListPage;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.CfgMngmnt_RI;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProviderImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.IntersectingCfgsCalculation;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.IntersectingCfgsCalculation_STDImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.ValueMngmnt_RI;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.provider.ContextualCfgProviderFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCServiceProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Engine;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Modelyear;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.GlobalVINResolver;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VINResolver;
/*     */ import com.eoos.html.HtmlCodeRenderer;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ public class ICLLink
/*     */   extends LinkElement
/*     */ {
/*  49 */   private static final Logger log = Logger.getLogger(ICLLink.class); private HtmlImgRenderer.Callback imgRendererCallback; private ClientContext context; protected ICLContext iclContext; protected LTListPage listPage;
/*     */   private IntersectingCfgsCalculation calc;
/*     */   private int paramOffset;
/*     */   
/*     */   protected String getLabel() {
/*     */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public boolean isAvailable() {
/*     */     return this.iclContext.checklistAvailable();
/*     */   }
/*     */   
/*  61 */   public ICLLink(final ClientContext context, final LTListPage listPage) { super("smenu:" + context.createID(), null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 116 */     this.calc = null;
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
/*     */ 
/*     */     
/* 309 */     this.paramOffset = 0; this.context = context; this.listPage = listPage; this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() { public String getImageSource() { String image = "lt/inspection-cl.gif"; if (ICLLink.this.isDisabled()) image = "lt/inspection-cl-disabled.gif";  return "pic/" + image; } public String getAlternativeText() { return context.getLabel("lt.tooltip.inspection.list"); } public void getAdditionalAttributes(Map<String, String> map) { map.put("border", "0"); } }
/*     */       ; ICLContext.ICLContextCallback callback = new ICLContext.ICLContextCallback() {
/*     */         public SIOLT getCurrentElement() { return listPage.getCurrentElement(); }
/* 312 */       }; this.iclContext = new ICLContext(context, callback); } protected String getLink(String targetFrame) { String _parameterName = this.parameterName + "." + ++this.paramOffset;
/* 313 */     if (targetFrame != null) {
/* 314 */       return "javascript:TFormSubmit('" + _parameterName + "','1','" + getTargetBookmark() + "','" + targetFrame + "')";
/*     */     }
/* 316 */     return "javascript:TFormSubmit('" + _parameterName + "','1','" + getTargetBookmark() + "',null)"; }
/*     */   public boolean isDisabled() { return (this.listPage == null || !isAvailable()); } protected String getTargetFrame() {
/*     */     if (this.iclContext.multipleChecklistAvailable())
/*     */       return "_top"; 
/*     */     return "icl";
/*     */   } public void setValue(Map submitParams) {
/* 322 */     this.clicked = false;
/* 323 */     if (!Util.isNullOrEmpty(submitParams))
/* 324 */       for (Iterator iter = submitParams.keySet().iterator(); iter.hasNext(); ) {
/* 325 */         String key = String.valueOf(iter.next());
/* 326 */         if (key.startsWith(this.parameterName) && key.indexOf('.') != -1 && key.subSequence(0, key.indexOf('.')).equals(this.parameterName))
/* 327 */           this.clicked = true; 
/*     */       }  
/*     */   }
/*     */   
/*     */   private CfgDataProvider createRetrictedDataProvider(List pairs, Set<Modelyear> wildcardMYs, Set<Engine> wildcardEngines) {
/*     */     if (log.isDebugEnabled()) {
/*     */       log.debug("creating restricted data provider ...");
/*     */       log.debug("...modelyear/engine restriction: " + pairs);
/*     */     } 
/*     */     CfgMngmnt_RI cfgManagement = VehicleConfigurationUtil.cfgManagement;
/*     */     ValueMngmnt_RI valueManagement = VehicleConfigurationUtil.valueManagement;
/*     */     IConfiguration vc = VCFacade.getInstance(this.context).getCfg();
/*     */     IConfiguration baseCfg = vc;
/*     */     baseCfg = cfgManagement.removeAttribute(baseCfg, VehicleConfigurationUtil.KEY_MODELYEAR);
/*     */     baseCfg = cfgManagement.removeAttribute(baseCfg, VehicleConfigurationUtil.KEY_ENGINE);
/*     */     baseCfg = cfgManagement.removeAttribute(baseCfg, VehicleConfigurationUtil.KEY_TRANSMISSION);
/*     */     if (this.calc == null)
/*     */       this.calc = (IntersectingCfgsCalculation)new IntersectingCfgsCalculation_STDImpl((CfgProvider)ContextualCfgProviderFacade.getInstance(this.context), VehicleConfigurationUtil.cfgUtil); 
/*     */     this.calc.getIntersectingConfigs(baseCfg);
/*     */     final Set<IConfiguration> includedConfigurations = new HashSet();
/*     */     for (Iterator<Pair> iter = pairs.iterator(); iter.hasNext(); ) {
/*     */       Pair pair = iter.next();
/*     */       IConfiguration cfg = baseCfg;
/*     */       String strModelyear = ((String)pair.getFirst()).trim();
/*     */       boolean wildcardMY = strModelyear.equals("*");
/*     */       if (cfg.getValue(VehicleConfigurationUtil.KEY_MODELYEAR) == null)
/*     */         if (!wildcardMY) {
/*     */           cfg = cfgManagement.setAttribute(cfg, VehicleConfigurationUtil.KEY_MODELYEAR, valueManagement.createInclusionValue(Collections.singleton(VehicleConfigurationUtil.toModelyear(strModelyear))));
/*     */         } else {
/*     */           cfg = cfgManagement.setAttribute(cfg, VehicleConfigurationUtil.KEY_MODELYEAR, valueManagement.getANY());
/*     */         }  
/*     */       String strEngine = ((String)pair.getSecond()).trim();
/*     */       boolean wildcardEngine = strEngine.equals("*");
/*     */       if (cfg.getValue(VehicleConfigurationUtil.KEY_ENGINE) == null)
/*     */         if (!wildcardEngine) {
/*     */           cfg = cfgManagement.setAttribute(cfg, VehicleConfigurationUtil.KEY_ENGINE, valueManagement.createInclusionValue(Collections.singleton(VehicleConfigurationUtil.toEngine(strEngine))));
/*     */         } else {
/*     */           cfg = cfgManagement.setAttribute(cfg, VehicleConfigurationUtil.KEY_ENGINE, valueManagement.getANY());
/*     */         }  
/*     */       if (wildcardMY ^ wildcardEngine) {
/*     */         if (log.isDebugEnabled())
/*     */           log.debug("...found wildcard pair: " + pair); 
/*     */         if (wildcardMY) {
/*     */           Engine engine = VehicleConfigurationUtil.toEngine(strEngine);
/*     */           wildcardEngines.add(engine);
/*     */           if (log.isDebugEnabled())
/*     */             log.debug("...added engine: " + engine + " to wildcard engines"); 
/*     */         } else {
/*     */           Modelyear modelyear = VehicleConfigurationUtil.toModelyear(strModelyear);
/*     */           wildcardMYs.add(modelyear);
/*     */           if (log.isDebugEnabled())
/*     */             log.debug("...added modelyear: " + modelyear + " to wildcard modelyears"); 
/*     */         } 
/*     */       } 
/*     */       if (log.isDebugEnabled())
/*     */         log.debug("...intersection the allowed configuration:" + cfg + " with the cfg source"); 
/*     */       for (Iterator<IConfiguration> iterCfgs = this.calc.getIntersectingConfigs(cfg).iterator(); iterCfgs.hasNext(); ) {
/*     */         IConfiguration tmp = iterCfgs.next();
/*     */         includedConfigurations.add(tmp);
/*     */         if (log.isDebugEnabled())
/*     */           log.debug("...added configuration: " + tmp); 
/*     */       } 
/*     */     } 
/*     */     CfgProvider cp = new CfgProvider() {
/*     */         public long getLastModified() {
/*     */           return 0L;
/*     */         }
/*     */         
/*     */         public Set getConfigurations() {
/*     */           return includedConfigurations;
/*     */         }
/*     */         
/*     */         public Set getKeys() {
/*     */           return VehicleConfigurationUtil.KEY_SET;
/*     */         }
/*     */       };
/*     */     return (CfgDataProvider)new CfgDataProviderImpl(cp, VehicleConfigurationUtil.cfgUtil);
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*     */     if (this.iclContext.multipleChecklistAvailable()) {
/*     */       List pairs = this.iclContext.getVehicleRestriction();
/*     */       final Set wildcardMYs = new HashSet();
/*     */       final Set wildcardEngines = new HashSet();
/*     */       final CfgDataProvider dataProvider = createRetrictedDataProvider(pairs, wildcardMYs, wildcardEngines);
/*     */       HtmlCodeRenderer vcDialog = VCFacade.getInstance(this.context).getDialog(new VCService.DialogCallback() {
/*     */             public Object onClose(boolean cancelled) {
/*     */               if (!cancelled)
/*     */                 MainPage.getInstance(ICLLink.this.context).setOnLoadHandler(new MainPage.OnLoadHandler() {
/*     */                       public String getCode(Map params) {
/*     */                         return ICLLink.this.getLink("icl");
/*     */                       }
/*     */                       
/*     */                       public boolean isTransient() {
/*     */                         return true;
/*     */                       }
/*     */                     }); 
/*     */               return MainPage.getInstance(ICLLink.this.context).getHtmlCode(null);
/*     */             }
/*     */             
/*     */             public boolean isReadonly(Object key) {
/*     */               boolean ret = (VehicleConfigurationUtil.KEY_MAKE == key);
/*     */               ret = (ret || VehicleConfigurationUtil.KEY_MODEL == key);
/*     */               return ret;
/*     */             }
/*     */             
/*     */             public boolean isMandatory(Object key, IConfiguration currentCfg) {
/*     */               if (key != VehicleConfigurationUtil.KEY_ENGINE && key != VehicleConfigurationUtil.KEY_MODELYEAR)
/*     */                 return false; 
/*     */               boolean isMYSet = (VehicleConfigurationUtil.getModelyear(currentCfg) != null);
/*     */               boolean isEngineSet = (VehicleConfigurationUtil.getEngine(currentCfg) != null);
/*     */               if (isMYSet ^ isEngineSet) {
/*     */                 if (isMYSet)
/*     */                   return !wildcardMYs.contains(VehicleConfigurationUtil.getModelyear(currentCfg)); 
/*     */                 return !wildcardEngines.contains(VehicleConfigurationUtil.getEngine(currentCfg));
/*     */               } 
/*     */               return true;
/*     */             }
/*     */             
/*     */             public VINResolver getVINResolver() {
/*     */               return (VINResolver)GlobalVINResolver.getInstance(ICLLink.this.context);
/*     */             }
/*     */             
/*     */             public VehicleCfgStorage getStorage() {
/*     */               return (VehicleCfgStorage)VCServiceProvider.getInstance().getService(ICLLink.this.context);
/*     */             }
/*     */             
/*     */             public CfgDataProvider getDataProvider() {
/*     */               return dataProvider;
/*     */             }
/*     */           });
/*     */       return new ResultObject(0, vcDialog.getHtmlCode(submitParams));
/*     */     } 
/*     */     byte[] data = null;
/*     */     try {
/*     */       data = this.iclContext.getChecklist();
/*     */     } catch (Exception e) {
/*     */       log.error("unable to retrieve checklist - error:" + e, e);
/*     */     } 
/*     */     if (data == null)
/*     */       return new ResultObject(0, "The checklist is currently not available"); 
/*     */     ResultObject.FileProperties props = new ResultObject.FileProperties();
/*     */     props.data = data;
/*     */     props.filename = "icl.pdf";
/*     */     props.mime = "application/pdf";
/*     */     props.inline = true;
/*     */     return new ResultObject(13, true, false, props);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\ICLLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */