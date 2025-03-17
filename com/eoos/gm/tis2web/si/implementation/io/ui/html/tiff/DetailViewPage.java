/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tiff;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.WIS;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.gm.tis2web.si.ResourceServlet;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ public class DetailViewPage
/*     */   extends Page
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(DetailViewPage.class);
/*     */   
/*     */   private static String template;
/*     */   
/*     */   private static String templateCGM;
/*     */   
/*     */   private static String templateJPG;
/*     */   private static String templateIMG;
/*     */   
/*     */   static {
/*     */     try {
/*  38 */       template = ApplicationContext.getInstance().loadFile(DetailViewPage.class, "detailviewpage.html", null).toString();
/*  39 */       templateCGM = ApplicationContext.getInstance().loadFile(DetailViewPage.class, "cgmviewpage.html", null).toString();
/*  40 */       templateJPG = ApplicationContext.getInstance().loadFile(DetailViewPage.class, "jpgviewpage.html", null).toString();
/*  41 */       templateIMG = ApplicationContext.getInstance().loadFile(DetailViewPage.class, "imgviewpage.html", null).toString();
/*  42 */       templatePNG = ApplicationContext.getInstance().loadFile(DetailViewPage.class, "pngviewpage.html", null).toString();
/*  43 */     } catch (Exception e) {
/*  44 */       log.error("unable to load template - error:" + e, e);
/*  45 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String templatePNG;
/*     */   
/*     */   private ClickButtonElement backButton;
/*     */   
/*     */   private String dataURL;
/*     */   private String mimetype;
/*     */   public boolean closeButton = false;
/*  57 */   private String initLayerSettings = null;
/*     */   
/*     */   public DetailViewPage(ClientContext context, int sioID, boolean closeButton) {
/*  60 */     this(context, sioID, (List)null, closeButton);
/*     */   }
/*     */   
/*     */   public DetailViewPage(final ClientContext context, int sioID, List layers, boolean closeButton) {
/*  64 */     super(context);
/*  65 */     this.closeButton = closeButton;
/*  66 */     if (layers != null) {
/*  67 */       this.initLayerSettings = assembleInitLayerSettings(layers);
/*     */     }
/*     */ 
/*     */     
/*  71 */     this.backButton = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  73 */           return context.getLabel("back");
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  77 */           return MainPage.getInstance(context);
/*     */         }
/*     */       };
/*  80 */     addElement((HtmlElement)this.backButton);
/*     */     
/*  82 */     this.dataURL = "si/pic/g/" + ResourceServlet.getURLSuffix(sioID);
/*  83 */     this.mimetype = SIDataAdapterFacade.getInstance(context).getSI().getMimeType(sioID);
/*  84 */     if (this.mimetype == null) {
/*  85 */       this.mimetype = "application/x-TIFF-HotSpot";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFormContent(Map params) {
/*  92 */     StringBuffer code = null;
/*  93 */     if (this.mimetype.equals("image/cgm")) {
/*  94 */       code = new StringBuffer(templateCGM);
/*  95 */       if (WIS.hasSaabData(getContext())) {
/*  96 */         StringUtilities.replace(code, "{IMAGE_TYPE}", "application/x-isoview");
/*     */       } else {
/*  98 */         StringUtilities.replace(code, "{IMAGE_TYPE}", "image/cgm");
/*  99 */         StringUtilities.replace(code, "{OBJECT_CLASS}", "classid=\"CLSID:B789F873-EB93-11D2-A6D8-00A0241989B9\"");
/*     */       }
/*     */     
/* 102 */     } else if (this.mimetype.endsWith("jpg")) {
/* 103 */       code = new StringBuffer(templateJPG);
/* 104 */     } else if (this.mimetype.endsWith("gif")) {
/* 105 */       code = new StringBuffer(templateIMG);
/* 106 */       StringUtilities.replace(code, "{IMAGE_TYPE}", "image/gif");
/* 107 */     } else if (this.mimetype.endsWith("png")) {
/* 108 */       code = new StringBuffer(templatePNG);
/*     */     } else {
/* 110 */       code = new StringBuffer(template);
/* 111 */       StringUtilities.replace(code, "{HEIGHT}", "90%");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     StringUtilities.replace(code, "{DATA_LINK}", this.dataURL);
/* 117 */     StringUtilities.replace(code, "{BACK_BUTTON}", this.backButton.getHtmlCode(params));
/* 118 */     String dispose = "";
/* 119 */     if (this.closeButton) {
/* 120 */       dispose = "<button name=\"cb\" type=\"button\" value=\"1\" onClick=\"javascript:window.close()\" >" + this.context.getLabel("close") + "</button>";
/*     */     }
/* 122 */     StringUtilities.replace(code, "{BUTTON_CLOSE}", dispose);
/* 123 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected String assembleInitLayerSettings(List<Pair> layers) {
/* 127 */     StringBuffer js = new StringBuffer();
/* 128 */     js.append("<script language=\"JavaScript\"><!-- \n");
/* 129 */     js.append("function Init(){");
/* 130 */     js.append("wiscgm = document.wisimg;");
/* 131 */     for (int i = 0; i < layers.size(); i++) {
/* 132 */       Pair pair = layers.get(i);
/* 133 */       js.append("wiscgm.SetLayerVisibility(\"" + pair.getFirst() + "\",\"" + pair.getSecond() + "\");");
/*     */     } 
/* 135 */     js.append("}\n//-->\n</script>");
/* 136 */     return js.toString();
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 140 */     return super.getHtmlCode(params);
/*     */   }
/*     */   
/*     */   protected String getOnLoadHandlerCode(Map params) {
/* 144 */     if (this.initLayerSettings != null) {
/* 145 */       return "javascript:Init()";
/*     */     }
/* 147 */     return super.getOnLoadHandlerCode(params);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getAdditionalScript(Map params) {
/* 152 */     if (this.initLayerSettings != null) {
/* 153 */       return this.initLayerSettings;
/*     */     }
/* 155 */     return super.getAdditionalScript(params);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tiff\DetailViewPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */