/*     */ package com.eoos.gm.tis2web.profile.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.msg.MsgFacade;
/*     */ import com.eoos.gm.tis2web.profile.implementation.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.profile.service.ProfileService;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.value.Make;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.CheckBoxElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.security.execution.delimiter.ExecutionDelimiter;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ public class HomePanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  31 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  36 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "home.html", null).toString();
/*  37 */     } catch (Exception e) {
/*  38 */       log.error("unable to load template - error:" + e, e);
/*  39 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private HeightSelection heightSelection;
/*     */   
/*     */   private DefaultSalesmakeSelection salesmakeSelection;
/*     */   
/*     */   private EncodingSelection encodingSelection;
/*     */   
/*     */   private CheckBoxElement enablePluginCheck;
/*     */   
/*     */   private ClickButtonElement closeButton;
/*     */   
/*     */   private ClickButtonElement buttonResetMsgs;
/*     */   
/*     */   private TextInputElement inputTicket;
/*     */   
/*     */   private CheckBoxElement useLTHours;
/*     */   
/*     */   private MainPage mainPage;
/*     */   
/*     */   public HomePanel(MainPage mainPage, final ClientContext context, final ProfileService.ReturnHandler returnHandler) {
/*  65 */     this.context = context;
/*  66 */     this.mainPage = mainPage;
/*     */     
/*  68 */     this.heightSelection = new HeightSelection(context);
/*  69 */     addElement((HtmlElement)this.heightSelection);
/*     */     
/*  71 */     this.salesmakeSelection = new DefaultSalesmakeSelection(context);
/*  72 */     addElement((HtmlElement)this.salesmakeSelection);
/*     */     
/*  74 */     this.encodingSelection = new EncodingSelection(context, null);
/*  75 */     addElement((HtmlElement)this.encodingSelection);
/*     */     
/*  77 */     this.enablePluginCheck = new PluginCheckTrigger(context);
/*  78 */     addElement((HtmlElement)this.enablePluginCheck);
/*     */     
/*  80 */     this.useLTHours = new UseLTHoursTrigger(context);
/*  81 */     addElement((HtmlElement)this.useLTHours);
/*     */     
/*  83 */     this.inputTicket = new TextInputElement(context.createID());
/*  84 */     addElement((HtmlElement)this.inputTicket);
/*     */     
/*  86 */     this.closeButton = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  88 */           return context.getLabel("ok");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  92 */           HomePanel.this.saveSettings();
/*  93 */           return returnHandler.onReturn(submitParams);
/*     */         }
/*     */       };
/*  96 */     addElement((HtmlElement)this.closeButton);
/*     */     
/*  98 */     this.buttonResetMsgs = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 100 */           return context.getLabel("reset");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 105 */             MsgFacade.getInstance(context).resetExclusion();
/* 106 */             return HomePanel.this.mainPage.getInfoPopup(context.getMessage("msg.exclusion.reset.successful"), null);
/*     */           }
/* 108 */           catch (Exception e) {
/* 109 */             HomePanel.log.error("unable to reset message exclusion - exception:" + e, e);
/* 110 */             return HomePanel.this.mainPage.getWarningPopup(context.getMessage("msg.exclusion.reset.failed"), null);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 115 */     addElement((HtmlElement)this.buttonResetMsgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 120 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 122 */     StringUtilities.replace(code, "{LABEL_PROFILE}", this.context.getLabel("profile.application.title"));
/*     */     
/* 124 */     StringUtilities.replace(code, "{LABEL_SALESMAKE_SELECTION}", this.context.getLabel("profile.default.salesmake.selection") + ":");
/* 125 */     StringUtilities.replace(code, "{INPUT_SALESMAKE}", this.salesmakeSelection.getHtmlCode(params));
/*     */     
/* 127 */     StringUtilities.replace(code, "{LABEL_HEIGHT_SELECTION}", this.context.getLabel("profile.height.selection") + ":");
/* 128 */     StringUtilities.replace(code, "{INPUT_HEIGHT_SELECTION}", this.heightSelection.getHtmlCode(params));
/*     */     
/* 130 */     StringUtilities.replace(code, "{LABEL_TEXT_ENCODING_FOR_DOWNLOAD}", this.context.getLabel("profile.download.encoding") + ":");
/* 131 */     StringUtilities.replace(code, "{INPUT_TEXT_ENCODING}", this.encodingSelection.getHtmlCode(params));
/*     */     
/* 133 */     StringUtilities.replace(code, "{LABEL_PLUGIN_CHECK}", this.context.getLabel("profile.enable.plugin.check") + ":");
/* 134 */     StringUtilities.replace(code, "{INPUT_PLUGIN_CHECK}", this.enablePluginCheck.getHtmlCode(params));
/*     */     
/* 136 */     StringUtilities.replace(code, "{LABEL_USE_LTHOURS}", this.context.getLabel("profile.use.lt.hours") + ":");
/* 137 */     StringUtilities.replace(code, "{INPUT_USE_LTHOURS}", this.useLTHours.getHtmlCode(params));
/*     */     
/* 139 */     StringUtilities.replace(code, "{LABEL_RESET_MSG}", this.context.getLabel("profile.reset.msgs") + ":");
/* 140 */     StringUtilities.replace(code, "{INPUT_RESET_MSG}", this.buttonResetMsgs.getHtmlCode(params));
/*     */     
/* 142 */     if (this.context.offerSpecialAccess()) {
/* 143 */       StringBuffer tmp = (new StringBuffer("<tr><td class=\"content\">")).append(this.context.getLabel("ticket")).append(":</td>");
/* 144 */       tmp.append("<td class=\"content\">").append(this.inputTicket.getHtmlCode(params)).append("</td></tr>");
/* 145 */       StringUtilities.replace(code, "<!-- INSERT -->", tmp.toString());
/*     */     } 
/*     */ 
/*     */     
/* 149 */     StringUtilities.replace(code, "{BUTTON_CLOSE}", this.closeButton.getHtmlCode(params));
/*     */     
/* 151 */     return code.toString();
/*     */   }
/*     */   
/*     */   public void saveSettings() {
/*     */     try {
/* 156 */       Make make = (Make)this.salesmakeSelection.getValue();
/* 157 */       if (make != null) {
/* 158 */         this.context.getSharedContext().setDefaultSalesmake(VCFacade.getInstance(this.context).getDisplayValue(make));
/*     */       }
/* 160 */       this.context.getSharedContext().setDisplayHeight((Integer)this.heightSelection.getValue());
/* 161 */       this.context.getSharedContext().setTextEncoding((Integer)this.encodingSelection.getValue());
/* 162 */       this.context.getSharedContext().setCheckPlugins((Boolean)this.enablePluginCheck.getValue());
/* 163 */       this.context.getSharedContext().setUseLTHours((Boolean)this.useLTHours.getValue());
/* 164 */       if (this.context.offerSpecialAccess()) {
/* 165 */         String token = (String)this.inputTicket.getValue();
/* 166 */         if (token != null && token.trim().length() > 0) {
/* 167 */           this.context.getSharedContext().setSpecialAccess(ExecutionDelimiter.check(token));
/*     */         }
/*     */       }
/*     */     
/* 171 */     } catch (Exception e) {
/* 172 */       log.error("unable to save settings - error:" + e, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\implementatio\\ui\html\home\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */