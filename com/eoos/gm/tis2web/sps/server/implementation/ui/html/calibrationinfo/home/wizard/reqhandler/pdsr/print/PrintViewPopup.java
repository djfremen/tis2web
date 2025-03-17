/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr.print;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.ModuleFilter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.Navigation;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.options.SelectedOptionsTable;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrintViewPopup
/*     */   extends Page
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(PrintViewPopup.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  32 */       template = ApplicationContext.getInstance().loadFile(PrintViewPopup.class, "printview.html", null).toString();
/*  33 */     } catch (Exception e) {
/*  34 */       log.error("unable to load template - error:" + e, e);
/*  35 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private List modules;
/*     */   
/*     */   private Navigation navigation;
/*     */   
/*     */   private SelectedOptionsTable sot;
/*     */   
/*     */   public PrintViewPopup(ClientContext context, List<?> modules, Navigation moduleNavigation, CustomAVMap avMap) {
/*  49 */     super(context);
/*  50 */     this.context = context;
/*  51 */     modules = (modules != null) ? new ArrayList(modules) : null;
/*  52 */     List filteredModules = (List)CollectionUtil.filterAndReturn(modules, ModuleFilter.FILTER_MODULE_0);
/*     */     
/*  54 */     this.modules = filteredModules;
/*  55 */     this.navigation = moduleNavigation;
/*     */     
/*  57 */     this.sot = new SelectedOptionsTable(context, avMap);
/*     */   }
/*     */   
/*     */   protected String getFormContent(Map params) {
/*  61 */     StringBuffer retValue = new StringBuffer(template);
/*  62 */     StringUtilities.replace(retValue, "{LABEL_CAPTION}", this.context.getMessage("sps.calibration.info.complete.part.history"));
/*     */     
/*  64 */     StringUtilities.replace(retValue, "{OPTIONS_TABLE}", this.sot.getHtmlCode(params));
/*  65 */     String separator = "<table width=\"100%\"><tr><td><hr size=\"1\" width=\"100%\" /></td></tr></table>";
/*     */     
/*  67 */     for (Iterator<Module> iter = this.modules.iterator(); iter.hasNext(); ) {
/*  68 */       Module module = iter.next();
/*  69 */       StringBuffer chCode = new StringBuffer("<table width=\"100%\"><tr><td>{MULTIPLE_PARTS_HINT}</td></tr><tr><td>{MESSAGE}: <b>{MODULE_DESC}</b></td></tr><tr><td>{TABLES}</td></tr></table>");
/*  70 */       StringUtilities.replace(chCode, "{MESSAGE}", this.context.getMessage("sps.calibration.history.for"));
/*  71 */       StringUtilities.replace(chCode, "{MODULE_DESC}", module.getDenotation(this.context.getLocale()));
/*     */       
/*  73 */       List leafs = this.navigation.getLeafParts(module);
/*  74 */       StringBuffer hint = new StringBuffer();
/*  75 */       if (leafs.size() > 1) {
/*  76 */         hint.append(this.context.getMessage("sps.calibration.info.print.view.hint.multiple.selectable.parts"));
/*  77 */         StringUtilities.replace(hint, "{PARTCOUNT}", String.valueOf(leafs.size()));
/*  78 */         StringUtilities.replace(hint, "{MODULENAME}", module.getDenotation(this.context.getLocale()));
/*     */       } else {
/*  80 */         hint.append("&nbsp;");
/*     */       } 
/*  82 */       StringUtilities.replace(chCode, "{MULTIPLE_PARTS_HINT}", hint.toString());
/*     */       
/*  84 */       for (Iterator<Part> iterLeafs = leafs.iterator(); iterLeafs.hasNext(); ) {
/*  85 */         Part leaf = iterLeafs.next();
/*  86 */         PartHistoryTable pht = new PartHistoryTable(this.context, leaf, this.navigation);
/*  87 */         StringUtilities.replace(chCode, "{TABLES}", pht.getHtmlCode(params) + "<br>{TABLES}");
/*     */       } 
/*  89 */       StringUtilities.replace(chCode, "<br>{TABLES}", "");
/*  90 */       StringUtilities.replace(chCode, "{TABLES}", "");
/*  91 */       StringUtilities.replace(retValue, "{PART_HISTORY_TABLES}", chCode.toString() + separator + "{PART_HISTORY_TABLES}");
/*     */     } 
/*     */     
/*  94 */     StringUtilities.replace(retValue, "{PART_HISTORY_TABLES}", "");
/*     */     
/*  96 */     StringUtilities.replace(retValue, "{LABEL_CLOSE}", this.context.getLabel("close"));
/*  97 */     StringUtilities.replace(retValue, "{LABEL_PRINT}", this.context.getLabel("print"));
/*  98 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   protected String getOnLoadHandlerCode(Map params) {
/* 102 */     return "javascript:window.focus()";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\print\PrintViewPopup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */