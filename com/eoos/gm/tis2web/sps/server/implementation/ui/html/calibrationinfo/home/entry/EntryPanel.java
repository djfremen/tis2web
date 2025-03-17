/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.entry;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.DealerVCIDisplayPermission;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.SecurityCodeDisplayPermission;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.UIContext;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.CalibInfoHomePanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMapImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.WizardPanel;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.CheckBoxElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class EntryPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(EntryPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  34 */       template = ApplicationContext.getInstance().loadFile(EntryPanel.class, "entrypanel.html", null).toString();
/*  35 */     } catch (Exception e) {
/*  36 */       log.error("unable to load template - error:" + e, e);
/*  37 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private CalibInfoHomePanel homePanel;
/*     */   
/*     */   private VINInputElement vinInputElement;
/*     */   
/*     */   private CVNResolutionElement cvnResolutionElement;
/*     */   
/*  50 */   private CheckBoxElement cbDisplayDealerVCI = null;
/*     */   
/*  52 */   private CheckBoxElement cbDisplaySecurityCode = null;
/*     */   
/*     */   public EntryPanel(ClientContext context, CalibInfoHomePanel homePanel) {
/*  55 */     this.context = context;
/*  56 */     this.homePanel = homePanel;
/*     */ 
/*     */     
/*  59 */     this.vinInputElement = new VINInputElement(context, this);
/*  60 */     addElement((HtmlElement)this.vinInputElement);
/*     */     
/*  62 */     this.cvnResolutionElement = new CVNResolutionElement(context, this);
/*  63 */     addElement((HtmlElement)this.cvnResolutionElement);
/*     */     
/*  65 */     if (DealerVCIDisplayPermission.getInstance(context).check()) {
/*  66 */       this.cbDisplayDealerVCI = new DealerVCIDisplayTrigger(context);
/*  67 */       addElement((HtmlElement)this.cbDisplayDealerVCI);
/*     */     } 
/*     */     
/*  70 */     if (SecurityCodeDisplayPermission.getInstance(context).check()) {
/*  71 */       this.cbDisplaySecurityCode = new SecurityCodeDisplayTrigger(context);
/*  72 */       addElement((HtmlElement)this.cbDisplaySecurityCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object onApplyVIN(String vin) throws Exception {
/*  79 */     boolean display = false;
/*  80 */     if (this.cbDisplayDealerVCI != null) {
/*  81 */       display = ((Boolean)this.cbDisplayDealerVCI.getValue()).booleanValue();
/*     */     }
/*  83 */     UIContext.getInstance(this.context).setDisplayDealerVCI(display);
/*     */     
/*  85 */     display = false;
/*  86 */     if (this.cbDisplaySecurityCode != null) {
/*  87 */       display = ((Boolean)this.cbDisplaySecurityCode.getValue()).booleanValue();
/*     */     }
/*  89 */     UIContext.getInstance(this.context).setDisplaySecurityCode(display);
/*     */ 
/*     */     
/*  92 */     CustomAVMapImpl customAVMapImpl = new CustomAVMapImpl();
/*     */ 
/*     */     
/*  95 */     customAVMapImpl.set(CommonAttribute.EXECUTION_MODE, CommonValue.EXECUTION_MODE_INFO);
/*     */ 
/*     */     
/*  98 */     customAVMapImpl.explicitSet(CommonAttribute.VIN, (Value)new ValueAdapter(vin));
/*     */     
/* 100 */     customAVMapImpl.set(CommonAttribute.SESSION_ID, (Value)new ValueAdapter(this.context.getSessionID()));
/*     */     
/* 102 */     customAVMapImpl.set(CommonAttribute.SESSION_TAG, (Value)new ValueAdapter(Long.valueOf(System.currentTimeMillis())));
/*     */     
/* 104 */     WizardPanel wz = new WizardPanel(this.context, this.homePanel, (CustomAVMap)customAVMapImpl);
/* 105 */     this.homePanel.push((HtmlElement)wz);
/*     */     
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 111 */     StringBuffer tmp = new StringBuffer(template);
/* 112 */     if (this.cbDisplayDealerVCI == null) {
/* 113 */       SectionIndex index = null;
/* 114 */       while ((index = StringUtilities.getSectionIndex(tmp.toString(), "<!--XXXXDVCI-->", "<!--/XXXXDVCI-->", 0, true, false)) != null) {
/* 115 */         StringUtilities.replaceSectionContent(tmp, index, "");
/*     */       }
/*     */     } else {
/* 118 */       StringUtilities.replace(tmp, "{CB_DISPLAY_DVCI}", this.cbDisplayDealerVCI.getHtmlCode(params));
/* 119 */       StringUtilities.replace(tmp, "{LABEL_DISPLAY_DVCI}", this.context.getMessage("sps.calibration.info.display.dealer.vci"));
/*     */     } 
/*     */     
/* 122 */     if (this.cbDisplaySecurityCode == null) {
/* 123 */       SectionIndex index = null;
/* 124 */       while ((index = StringUtilities.getSectionIndex(tmp.toString(), "<!--XXXXSC-->", "<!--/XXXXSC-->", 0, true, false)) != null) {
/* 125 */         StringUtilities.replaceSectionContent(tmp, index, "");
/*     */       }
/*     */     } else {
/* 128 */       StringUtilities.replace(tmp, "{CB_DISPLAY_SECURITY_CODE}", this.cbDisplaySecurityCode.getHtmlCode(params));
/* 129 */       StringUtilities.replace(tmp, "{LABEL_DISPLAY_SECURITY_CODE}", this.context.getMessage("sps.calibration.info.display.security.code"));
/*     */     } 
/*     */     
/* 132 */     StringUtilities.replace(tmp, "{MESSAGE}", this.context.getMessage("sps.calib.info.entry.panel.message"));
/* 133 */     StringUtilities.replace(tmp, "{VIN_INPUT}", this.vinInputElement.getHtmlCode(params));
/* 134 */     StringUtilities.replace(tmp, "{CVN_RESOLVE}", this.cvnResolutionElement.getHtmlCode(params));
/*     */     
/* 136 */     StringUtilities.replace(tmp, "{URL_GM_LOGO}", "pic/sps/calinfo/gm_logo_ani.gif");
/* 137 */     StringUtilities.replace(tmp, "{URL_ACDELCO_LOGO}", "pic/sps/calinfo/acdelco.jpg");
/* 138 */     StringUtilities.replace(tmp, "{MESSAGE_GM_LINK}", this.context.getMessage("sps.calibration.info.link.gm.home"));
/* 139 */     StringUtilities.replace(tmp, "{MESSAGE_COPYRIGHT_TITLE}", this.context.getMessage("sps.calibration.info.copyright.title"));
/* 140 */     StringUtilities.replace(tmp, "{MESSAGE_COPYRIGHT_TEXT}", this.context.getMessage("sps.calibration.info.copyright.text"));
/* 141 */     StringUtilities.replace(tmp, "{MESSAGE_PRIVACY_POLICY}", this.context.getMessage("sps.calibration.info.link.privacy.policy"));
/*     */     
/* 143 */     return tmp.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\entry\EntryPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */