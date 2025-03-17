/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.lt.service.LTService;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks.AwList;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks.AwPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public abstract class LTLink
/*     */   extends LinkElement
/*     */ {
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   private ClientContext context;
/*  37 */   private final Object SYNC_AUTHORIZED = new Object();
/*     */   
/*  39 */   private Boolean authorized = null;
/*     */ 
/*     */   
/*     */   public LTLink(final ClientContext context) {
/*  43 */     super(context.createID(), null);
/*  44 */     this.context = context;
/*  45 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  47 */           String image = "si/lt.gif";
/*  48 */           if (LTLink.this.isDisabled()) {
/*  49 */             image = "si/lt-disabled.gif";
/*     */           }
/*  51 */           return "pic/" + image;
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  55 */           return context.getLabel("si.labour.times");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  59 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  67 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/*  71 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  75 */     SIO sio = getSIO();
/*  76 */     if (sio != null && sio instanceof SIOCPR) {
/*  77 */       AwList ltlist = new AwList(this.context, (SIOCPR)sio);
/*  78 */       AwPage page = new AwPage(this.context, ltlist);
/*  79 */       List links = page.getLinks();
/*  80 */       if (links.size() == 1) {
/*  81 */         return page.getResult(links.get(0));
/*     */       }
/*  83 */       return new ResultObject(0, page.getHtmlCode(submitParams));
/*     */     } 
/*     */     
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisabled() {
/*  92 */     SIO sio = getSIO();
/*  93 */     if (sio != null && sio instanceof SIOCPR) {
/*     */       try {
/*  95 */         if (ConfiguredServiceProvider.getInstance().getService(LTService.class) == null) {
/*  96 */           return true;
/*     */         }
/*  98 */       } catch (Exception e) {
/*  99 */         return true;
/*     */       } 
/* 101 */       AwList ltlist = new AwList(this.context, (SIOCPR)sio);
/* 102 */       return (!isAuthorized() || ltlist.getData() == null || ltlist.getData().size() == 0);
/*     */     } 
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   protected abstract SIO getSIO();
/*     */   
/*     */   private boolean isAuthorized() {
/* 110 */     synchronized (this.SYNC_AUTHORIZED) {
/* 111 */       if (this.authorized == null) {
/* 112 */         this.authorized = Boolean.FALSE;
/* 113 */         Map usrGrp2Manuf = SharedContextProxy.getInstance(this.context).getUsrGroup2Manuf();
/*     */         
/* 115 */         String country = SharedContextProxy.getInstance(this.context).getCountry();
/*     */         
/*     */         try {
/* 118 */           VCFacade.getInstance(this.context).getCurrentSalesmake();
/* 119 */           LTService ltMI = (LTService)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/* 120 */           ACLService aclMI = ACLServiceProvider.getInstance().getService();
/*     */           
/* 122 */           Set apps = aclMI.getAuthorizedResources("Application", usrGrp2Manuf, country);
/*     */           
/* 124 */           if (ltMI != null && apps.contains("lt"))
/*     */           {
/* 126 */             this.authorized = Boolean.TRUE;
/*     */           }
/* 128 */         } catch (Exception e) {}
/*     */       } 
/*     */ 
/*     */       
/* 132 */       return this.authorized.booleanValue();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\cpr\LTLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */