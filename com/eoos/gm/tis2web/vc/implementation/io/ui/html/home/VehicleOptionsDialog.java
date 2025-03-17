/*     */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.home;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogLayout;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.datamodel.VehicleOptionsData;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.VCActionButtonsElement;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.VCListElement;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.VCListRowFactory;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.HtmlLayout;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VehicleOptionsDialog
/*     */   extends DialogBase
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(VehicleOptionsDialog.class);
/*     */   
/*  38 */   private ClientContext context = null;
/*     */   
/*  40 */   private List voDataList = null;
/*     */   
/*  42 */   private VehicleOptionsData vod = null;
/*     */   
/*  44 */   private HtmlElement vcStatusBar = null;
/*     */   
/*  46 */   private HtmlElement caption = null;
/*     */   
/*  48 */   private VCListElement vclist = null;
/*     */   
/*  50 */   private HtmlElement buttons = null;
/*     */   
/*  52 */   private List esc = null;
/*     */   
/*  54 */   private ILVCAdapter.ReturnHandler returnHandler = null;
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  59 */       template = ApplicationContext.getInstance().loadFile(VehicleOptionsDialog.class, "dialogvcoptions.html", null).toString();
/*  60 */     } catch (Exception e) {
/*  61 */       log.error("could not load template - error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ILVCAdapter adapter;
/*     */   
/*     */   public VehicleOptionsDialog(ClientContext context, ILVCAdapter adapter) {
/*  68 */     super(context);
/*  69 */     this.adapter = adapter;
/*  70 */     this.context = context;
/*     */   }
/*     */   
/*     */   public void init(ILVCAdapter.ReturnHandler returnHandler, List esc) {
/*  74 */     this.returnHandler = returnHandler;
/*  75 */     this.esc = esc;
/*  76 */     this.vod = VehicleOptionsData.getInstance(this.context);
/*  77 */     this.vod.setElectronicSystemCode(this.esc, this.adapter);
/*  78 */     createElements();
/*     */   }
/*     */   
/*     */   protected void createElements() {
/*  82 */     this.vcStatusBar = createVCStatusBar();
/*  83 */     this.caption = createCaptionElement();
/*  84 */     this.vclist = createVCListElement();
/*  85 */     this.buttons = createVCActionButtonsElement();
/*     */   }
/*     */   
/*     */   protected HtmlElement createVCStatusBar() {
/*  89 */     StringBuffer buffer = new StringBuffer(this.context.getLabel("vo.basic.vehicle.configuration"));
/*  90 */     buffer.append(" ");
/*     */     try {
/*  92 */       buffer.append(VCFacade.getInstance(this.context).getCurrentVCDisplay(true));
/*  93 */     } catch (Exception e) {
/*  94 */       buffer.append("");
/*     */     } 
/*  96 */     return (HtmlElement)new HtmlLabel(buffer.toString());
/*     */   }
/*     */   
/*     */   protected HtmlElement createCaptionElement() {
/* 100 */     return (HtmlElement)new HtmlLabel(this.context.getLabel("vc.validate.vehicle.options"));
/*     */   }
/*     */   
/*     */   protected VCListElement createVCListElement() {
/* 104 */     VCListElement list = new VCListElement(this.context, createVCListElementRows()) {
/*     */         protected List getData() {
/* 106 */           return VehicleOptionsDialog.this.voDataList;
/*     */         }
/*     */       };
/* 109 */     addElement((HtmlElement)list);
/* 110 */     return list;
/*     */   }
/*     */   
/*     */   protected HtmlElement createVCActionButtonsElement() {
/* 114 */     VCActionButtonsElement actionButtons = new VCActionButtonsElement(this.context) {
/*     */         protected Object onOK(Map params) {
/* 116 */           return VehicleOptionsDialog.this.onOK();
/*     */         }
/*     */         
/*     */         protected Object onCancel(Map params) {
/* 120 */           return VehicleOptionsDialog.this.onCancel();
/*     */         }
/*     */         
/*     */         protected Object onReset(Map params) {
/* 124 */           return VehicleOptionsDialog.this.onReset();
/*     */         }
/*     */       };
/* 127 */     addElement((HtmlElement)actionButtons);
/* 128 */     return (HtmlElement)actionButtons;
/*     */   }
/*     */   
/*     */   protected List createVCListElementRows() {
/* 132 */     this.voDataList = new ArrayList();
/* 133 */     VCListRowFactory rowFactory = VCListRowFactory.getInstance(this.context);
/* 134 */     List optionNames = this.vod.getOptionNames();
/* 135 */     Iterator<String> it = optionNames.iterator();
/* 136 */     while (it.hasNext()) {
/* 137 */       this.voDataList.add(rowFactory.createComboBoxRow(it.next()));
/*     */     }
/* 139 */     return this.voDataList;
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 143 */     return this.caption.getHtmlCode(params);
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/* 147 */     StringBuffer code = new StringBuffer(template);
/* 148 */     StringUtilities.replace(code, "{VC_STATUS_BAR}", this.vcStatusBar.getHtmlCode(params));
/* 149 */     StringUtilities.replace(code, "{VC_LIST}", this.vclist.getHtmlCode(params));
/* 150 */     StringUtilities.replace(code, "{ACTION_BUTTONS}", this.buttons.getHtmlCode(params));
/* 151 */     StringUtilities.replace(code, "{FOOTNOTE}", getFootnoteCode());
/* 152 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected String getFootnoteCode() {
/* 156 */     StringBuffer htmlCode = new StringBuffer("");
/* 157 */     if (this.vod.getTruncatedValueNamesList().size() > 0) {
/* 158 */       htmlCode.append("<TR><TD align=\"center\" valign=\"center\"><BR></TD></TR>");
/* 159 */       htmlCode.append("<TR><TD valign=\"center\">");
/* 160 */       Iterator<Pair> it = this.vod.getTruncatedValueNamesList().iterator();
/* 161 */       long index = 1L;
/* 162 */       while (it.hasNext()) {
/* 163 */         Pair entry = it.next();
/* 164 */         htmlCode.append(index);
/* 165 */         htmlCode.append(") ");
/* 166 */         htmlCode.append((String)entry.getSecond());
/* 167 */         htmlCode.append("<BR>");
/* 168 */         index++;
/*     */       } 
/* 170 */       htmlCode.append("</TD></TR>");
/*     */     } 
/* 172 */     return htmlCode.toString();
/*     */   }
/*     */   
/*     */   public HtmlLayout getLayout() {
/* 176 */     DialogLayout layout = new DialogLayout() {
/*     */         public String getHorizontalAlignment() {
/* 178 */           return "center";
/*     */         }
/*     */         
/*     */         public String getWidth() {
/* 182 */           return ApplicationContext.getInstance().getProperty("frame.vc.vehicle.options.dialog.width") + "%";
/*     */         }
/*     */       };
/* 185 */     return (HtmlLayout)layout;
/*     */   }
/*     */   
/*     */   public Object onOK() {
/* 189 */     this.vod.storeVehicleOptions(this.adapter);
/* 190 */     return new ResultObject(0, this.returnHandler.onOK());
/*     */   }
/*     */   
/*     */   public Object onCancel() {
/* 194 */     this.vod.restoreVehicleOptions(this.adapter);
/* 195 */     return new ResultObject(0, this.returnHandler.onCancel());
/*     */   }
/*     */   
/*     */   public Object onReset() {
/* 199 */     this.vod.reset();
/* 200 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\home\VehicleOptionsDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */