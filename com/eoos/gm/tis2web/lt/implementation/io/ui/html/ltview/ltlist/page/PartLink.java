/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.page;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.Cipher;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ public class PartLink extends LinkElement {
/*     */   public class LinkRenderingCallback extends LinkElement.RenderingCallback {
/*     */     public LinkRenderingCallback() {
/*  25 */       super(PartLink.this);
/*     */     } public String getLink() {
/*  27 */       String targetFrame = PartLink.this.getTargetFrame();
/*  28 */       String targetURL = PartLink.this.assembleURL();
/*  29 */       return "javascript:window.open('" + targetURL + "','" + targetFrame + "').focus()";
/*     */     }
/*     */   }
/*     */   
/*  33 */   private static final Logger log = Logger.getLogger(PartLink.class);
/*     */   
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private String partinfo;
/*     */   
/*     */   private String operation;
/*     */   
/*     */   public PartLink(final ClientContext context) {
/*  44 */     super("", null);
/*  45 */     this.context = context;
/*  46 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*     */       {
/*     */         public String getImageSource() {
/*  49 */           return "pic/lt/part-catalog.gif";
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  53 */           return context.getLabel("lt.tooltip.part-catalog.link");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  57 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */     
/*  61 */     this.renderingCallback = new LinkRenderingCallback();
/*     */   }
/*     */   
/*     */   public boolean isAvailable(CTOCNode mo) {
/*  65 */     this.partinfo = null;
/*  66 */     this.operation = null;
/*     */     try {
/*  68 */       if (mo != null) {
/*  69 */         this.operation = (String)mo.getProperty((SITOCProperty)CTOCProperty.MajorOperation);
/*  70 */         if (this.operation.length() > 7) {
/*  71 */           this.operation = this.operation.substring(0, 7);
/*     */         }
/*  73 */         this.partinfo = (String)mo.getProperty((SITOCProperty)CTOCProperty.LINKS);
/*  74 */         return (this.partinfo != null);
/*     */       } 
/*  76 */     } catch (Exception e) {}
/*     */     
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  82 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   protected String getTargetFrame() {
/*  90 */     return "PartCatalog";
/*     */   }
/*     */   
/*     */   protected String dump(Map data) {
/*  94 */     StringBuffer dump = new StringBuffer();
/*  95 */     Iterator it = data.keySet().iterator();
/*  96 */     while (it.hasNext()) {
/*  97 */       Object key = it.next();
/*  98 */       if (dump.length() > 0) {
/*  99 */         dump.append(',');
/*     */       }
/* 101 */       dump.append("[" + key + "->" + data.get(key) + "]");
/*     */     } 
/* 103 */     return dump.toString();
/*     */   }
/*     */   
/*     */   protected List split(String data) {
/* 107 */     List<String> list = new LinkedList();
/* 108 */     StringTokenizer tokenizer = new StringTokenizer(data, ";", false);
/* 109 */     while (tokenizer.hasMoreTokens()) {
/* 110 */       list.add(tokenizer.nextToken());
/*     */     }
/* 112 */     return list;
/*     */   }
/*     */   
/*     */   protected String transfer(Map data) {
/* 116 */     List<String> parts = split((String)data.get("parts"));
/* 117 */     long seed = Long.parseLong(parts.get(0));
/* 118 */     StringBuffer string = new StringBuffer();
/* 119 */     Iterator it = data.keySet().iterator();
/* 120 */     while (it.hasNext()) {
/* 121 */       Object key = it.next();
/* 122 */       if (string.length() > 0) {
/* 123 */         string.append(',');
/*     */       }
/* 125 */       string.append("[" + key + "=" + data.get(key) + "]");
/*     */     } 
/* 127 */     String plain = string.toString();
/* 128 */     String code = Cipher.encrypt(seed, plain);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 135 */       BASE64Encoder encoder = new BASE64Encoder();
/* 136 */       String parameter = encoder.encodeBuffer(code.getBytes("UTF-8"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       return parameter;
/* 145 */     } catch (Exception x) {
/* 146 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String assembleURL() {
/* 151 */     String targetURL = ApplicationContext.getInstance().getProperty("component.lt.epc.url");
/* 152 */     LTClientContext ltContext = LTClientContext.getInstance(this.context);
/* 153 */     Map<Object, Object> parameters = new HashMap<Object, Object>();
/* 154 */     String language = this.context.getLocale().toString();
/* 155 */     Iterator<LocaleInfo> it = LocaleInfoProvider.getInstance().getLocales().iterator();
/* 156 */     while (it.hasNext()) {
/* 157 */       LocaleInfo li = it.next();
/* 158 */       if (li.getLanguage().equalsIgnoreCase(language)) {
/* 159 */         parameters.put("locale", li.getLocale());
/*     */       }
/*     */     } 
/* 162 */     if (parameters.get("locale") == null) {
/* 163 */       parameters.put("locale", this.context.getLocale() + "_" + this.context.getSharedContext().getCountry());
/*     */     }
/*     */     
/* 166 */     parameters.put("user", this.context.getSessionID());
/* 167 */     parameters.put("parts", this.partinfo);
/* 168 */     parameters.put("operation", this.operation);
/* 169 */     parameters.put("make", ltContext.getVCContext().getSalesMake());
/* 170 */     parameters.put("model", ltContext.getVCContext().getModel());
/* 171 */     String myear = ltContext.getVCContext().getYear();
/* 172 */     if (myear != null && myear.length() > 0) {
/* 173 */       parameters.put("year", myear);
/*     */     }
/* 175 */     String engine = ltContext.getVCContext().getEngine();
/* 176 */     if (engine != null && engine.length() > 0) {
/* 177 */       parameters.put("engine", engine);
/*     */     }
/* 179 */     log.debug("part catalog link: " + dump(parameters));
/* 180 */     return targetURL + "?display=" + transfer(parameters);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\ltlist\page\PartLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */