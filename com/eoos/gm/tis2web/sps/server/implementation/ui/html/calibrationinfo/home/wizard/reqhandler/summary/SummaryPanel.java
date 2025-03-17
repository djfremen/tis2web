/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AttributeImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.DisplaySummaryRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.ModuleFilter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.DisplayUtil;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.UIContext;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.RequestHandlerPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.WizardPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.ButtonContainer;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SummaryPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements RequestHandlerPanel, ButtonContainer
/*     */ {
/*  39 */   private static final Logger log = Logger.getLogger(SummaryPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  44 */       template = ApplicationContext.getInstance().loadFile(SummaryPanel.class, "summarypanel.html", null).toString();
/*  45 */     } catch (Exception e) {
/*  46 */       log.error("unable to load template - error:" + e, e);
/*  47 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  52 */   public static final Attribute ATTRIBUTE_SELECTION_REQUEST = AttributeImpl.getInstance("pd.selection.request");
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private HtmlElement tableSummary;
/*     */   
/*     */   private DisplaySummaryRequest request;
/*     */   
/*     */   private String dealerVCI;
/*     */   
/*     */   private String securityCode;
/*     */ 
/*     */   
/*     */   public SummaryPanel(ClientContext context, DisplaySummaryRequest request, CustomAVMap avMap, WizardPanel wizard) {
/*  66 */     this.context = context;
/*  67 */     this.request = request;
/*  68 */     if (request.getSummary() != null) {
/*  69 */       this.tableSummary = (HtmlElement)new SummaryTableGME(context, request.getSummary(), avMap, wizard);
/*  70 */       addElement(this.tableSummary);
/*  71 */       this.dealerVCI = String.valueOf(AVUtil.accessValue((AttributeValueMap)avMap, CommonAttribute.DEALER_VCI));
/*  72 */       if (this.dealerVCI != null && "null".equalsIgnoreCase(this.dealerVCI)) {
/*  73 */         this.dealerVCI = null;
/*     */       }
/*     */     } else {
/*     */       
/*  77 */       this.securityCode = String.valueOf(AVUtil.accessValue((AttributeValueMap)avMap, CommonAttribute.SECURITY_CODE));
/*  78 */       if (this.securityCode != null && "null".equalsIgnoreCase(this.securityCode)) {
/*  79 */         this.securityCode = null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  84 */       ProgrammingDataSelectionRequest pdata = (ProgrammingDataSelectionRequest)AVUtil.accessValue((AttributeValueMap)avMap, ATTRIBUTE_SELECTION_REQUEST);
/*  85 */       List<?> modules = pdata.getOptions();
/*     */       
/*  87 */       modules = (modules != null) ? new ArrayList(modules) : null;
/*  88 */       List<Module> filteredModules = (List)CollectionUtil.filterAndReturn(modules, ModuleFilter.FILTER_MODULE_0);
/*     */       
/*  90 */       DefaultTableModel mtable = new DefaultTableModel();
/*  91 */       mtable.setColumnCount(3);
/*  92 */       for (int i = 0; i < filteredModules.size(); i++) {
/*  93 */         Module m = filteredModules.get(i);
/*  94 */         String description = m.getDenotation(null);
/*  95 */         if (m.getSelectedPart() != null) {
/*  96 */           description = m.getSelectedPart().getDescription(null);
/*     */         }
/*  98 */         description = DisplayUtil.convertStringToHtml(description);
/*     */         
/* 100 */         mtable.addRow(new Object[] { m.getID(), Integer.valueOf(m.getSelectedPart().getID()), description });
/*     */       } 
/* 102 */       ((DisplaySummaryRequestImpl)request).setModuleData(mtable);
/* 103 */       this.tableSummary = (HtmlElement)new SummaryTableNAO(context, request.getModuleData(), avMap);
/* 104 */       addElement(this.tableSummary);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onNext(CustomAVMap avMap) {
/* 110 */     avMap.set(this.request.getAttribute(), CommonValue.OK);
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 115 */     StringBuffer tmp = new StringBuffer(template);
/* 116 */     StringUtilities.replace(tmp, "{LABEL_SUMMARY}", this.context.getLabel("summary"));
/* 117 */     if (!(this.tableSummary instanceof SummaryTableGME)) {
/* 118 */       this.dealerVCI = null;
/*     */     }
/* 120 */     if (UIContext.getInstance(this.context).displayDealerVCI() && this.dealerVCI != null) {
/* 121 */       String label = this.context.getLabel("sps.calibration.info.dealer.vci") + ": ";
/* 122 */       StringUtilities.replace(tmp, "{DEALER_VCI}", label + this.dealerVCI);
/*     */     } else {
/* 124 */       StringUtilities.replace(tmp, "{DEALER_VCI}", "");
/*     */     } 
/* 126 */     if (UIContext.getInstance(this.context).displaySecurityCode()) {
/* 127 */       String label = this.context.getLabel("sps.calibration.info.security.code") + ": ";
/* 128 */       StringUtilities.replace(tmp, "{SECURITY_CODE}", label + this.securityCode);
/*     */     } else {
/* 130 */       StringUtilities.replace(tmp, "{SECURITY_CODE}", "");
/*     */     } 
/* 132 */     StringUtilities.replace(tmp, "{SUMMARY_TABLE}", this.tableSummary.getHtmlCode(params));
/* 133 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   public List getButtonElements() {
/* 137 */     if (this.tableSummary instanceof ButtonContainer) {
/* 138 */       return ((ButtonContainer)this.tableSummary).getButtonElements();
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\summary\SummaryPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */