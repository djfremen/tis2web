/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.IDLinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*     */ 
/*     */ 
/*     */ public class LogoutLinkElement
/*     */   extends IDLinkElement
/*     */ {
/*  28 */   private static final Logger log = Logger.getLogger(LogoutLinkElement.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(LogoutLinkElement.class, "logout.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("unable to load template  - error:" + e, e);
/*  36 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   
/*     */   public LogoutLinkElement(final ClientContext context) {
/*  46 */     super(context.createID(), null, "Logout");
/*  47 */     this.context = context;
/*     */     
/*  49 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  51 */           return "pic/common/logout.gif";
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  55 */           return context.getLabel("tooltip.logout");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  59 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  66 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   public Object logout() {
/*  70 */     StringBuffer code = new StringBuffer(template);
/*  71 */     StringUtilities.replace(code, "{MESSAGE_GOOD_BYE}", this.context.getMessage("good.bye"));
/*     */     
/*     */     try {
/*  74 */       FrameService service = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/*  75 */       service.invalidateSession(this.context.getSessionID());
/*  76 */     } catch (Exception e) {
/*  77 */       log.error("unable to logout - error:" + e, e);
/*     */     } 
/*  79 */     return new ResultObject(0, code.toString());
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams, boolean confirm) {
/*  83 */     if (confirm) {
/*  84 */       return new ConfirmationMessageBox(this.context) {
/*     */           public Object onClick(String buttonLabel) {
/*  86 */             if (buttonLabel.equals(this.BUTTONLABEL_OK)) {
/*  87 */               return LogoutLinkElement.this.logout();
/*     */             }
/*  89 */             HtmlElementContainer container = LogoutLinkElement.this.getContainer();
/*  90 */             while (container.getContainer() != null) {
/*  91 */               container = container.getContainer();
/*     */             }
/*  93 */             return container;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*  99 */     return logout();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object onClick(Map params) {
/* 104 */     return onClick(params, true);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\main\toplink\LogoutLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */