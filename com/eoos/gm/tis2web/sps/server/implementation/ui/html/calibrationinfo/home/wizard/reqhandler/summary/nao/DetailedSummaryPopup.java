/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary.nao;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*    */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.ModuleFilter;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.Navigation;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.NavigationImpl;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.options.SelectedOptionsTable;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary.SummaryPanel;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DetailedSummaryPopup
/*    */   extends Page
/*    */ {
/* 30 */   private static final Logger log = Logger.getLogger(DetailedSummaryPopup.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 35 */       template = ApplicationContext.getInstance().loadFile(DetailedSummaryPopup.class, "detailedsummary.html", null).toString();
/* 36 */     } catch (Exception e) {
/* 37 */       log.error("unable to load template - error:" + e, e);
/* 38 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private List modules;
/*    */   
/*    */   private Navigation navigation;
/*    */   
/*    */   private SelectedOptionsTable sot;
/*    */   
/*    */   public DetailedSummaryPopup(ClientContext context, CustomAVMap avMap) {
/* 52 */     super(context);
/* 53 */     this.context = context;
/*    */     
/* 55 */     ProgrammingDataSelectionRequest pdata = (ProgrammingDataSelectionRequest)AVUtil.accessValue((AttributeValueMap)avMap, SummaryPanel.ATTRIBUTE_SELECTION_REQUEST);
/* 56 */     this.modules = pdata.getOptions();
/* 57 */     CollectionUtil.filter(this.modules, ModuleFilter.FILTER_MODULE_0);
/*    */     
/* 59 */     this.navigation = (Navigation)new NavigationImpl(this.modules);
/*    */     
/* 61 */     this.sot = new SelectedOptionsTable(context, avMap);
/*    */   }
/*    */   
/*    */   protected String getFormContent(Map params) {
/* 65 */     StringBuffer retValue = new StringBuffer(template);
/* 66 */     StringUtilities.replace(retValue, "{LABEL_CAPTION}", this.context.getMessage("sps.calibration.info.detailed.summary.caption"));
/*    */     
/* 68 */     StringUtilities.replace(retValue, "{OPTIONS_TABLE}", this.sot.getHtmlCode(params));
/* 69 */     String separator = "<table width=\"100%\"><tr><td><hr size=\"1\" width=\"100%\" /></td></tr></table>";
/*    */     
/* 71 */     for (Iterator<Module> iter = this.modules.iterator(); iter.hasNext(); ) {
/* 72 */       Module module = iter.next();
/* 73 */       Part selectedPart = this.navigation.getSelectedPart(module);
/* 74 */       StringBuffer chCode = new StringBuffer("<table width=\"100%\"><tr><td>{MESSAGE}: <b>{MODULE_DESC}</b></td></tr><tr><td>{TABLE}</td></tr></table>");
/* 75 */       StringUtilities.replace(chCode, "{MESSAGE}", this.context.getMessage("sps.calibration.history.for"));
/* 76 */       StringUtilities.replace(chCode, "{MODULE_DESC}", module.getDenotation(this.context.getLocale()));
/* 77 */       PartHistoryTable pht = new PartHistoryTable(this.context, selectedPart, this.navigation);
/* 78 */       StringUtilities.replace(chCode, "{TABLE}", pht.getHtmlCode(params));
/* 79 */       StringUtilities.replace(retValue, "{PART_HISTORY_TABLES}", chCode.toString() + separator + "{PART_HISTORY_TABLES}");
/*    */     } 
/*    */     
/* 82 */     StringUtilities.replace(retValue, "{PART_HISTORY_TABLES}", "");
/*    */     
/* 84 */     StringUtilities.replace(retValue, "{LABEL_CLOSE}", this.context.getLabel("close"));
/* 85 */     StringUtilities.replace(retValue, "{LABEL_PRINT}", this.context.getLabel("print"));
/* 86 */     return retValue.toString();
/*    */   }
/*    */   
/*    */   protected String getOnLoadHandlerCode(Map params) {
/* 90 */     return "javascript:window.focus()";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\summary\nao\DetailedSummaryPopup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */