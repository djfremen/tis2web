/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSelectionLists;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal.page.LTTALListPage;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import java.util.Map;
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
/*     */ public class LTW100000SelectionLink
/*     */   extends LinkElement
/*     */ {
/*  26 */   private HtmlImgRenderer.Callback imgRendererCallback = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TextInputElement input;
/*     */ 
/*     */   
/*     */   public LTW100000SelectionLink(TextInputElement input, ClientContext context, boolean bAlreadyInList) {
/*  34 */     super("W100000", null);
/*  35 */     this.input = input;
/*  36 */     setDisabled(new Boolean(bAlreadyInList));
/*  37 */     this.context = context;
/*     */     
/*  39 */     if (this.imgRendererCallback == null) {
/*  40 */       this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*     */         {
/*     */           public String getImageSource() {
/*  43 */             if (LTW100000SelectionLink.this.isDisabled()) {
/*  44 */               return "pic/lt/ltsel.gif";
/*     */             }
/*  46 */             return "pic/lt/ltunsel.gif";
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public String getAlternativeText() {
/*  52 */             return "";
/*     */           }
/*     */           
/*     */           public void getAdditionalAttributes(Map<String, String> map) {
/*  56 */             map.put("border", "0");
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static String buildID(String oWorknr, int iSXAWID) {
/*  64 */     return Integer.valueOf(iSXAWID).toString() + "-" + oWorknr;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  69 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  75 */     String val = (String)this.input.getValue();
/*  76 */     if (val == null) {
/*  77 */       return LTTALListPage.getInstance(this.context);
/*     */     }
/*     */     
/*  80 */     Integer iValue = null;
/*     */     try {
/*  82 */       iValue = Integer.decode(val);
/*  83 */     } catch (NumberFormatException nfe) {
/*  84 */       return LTTALListPage.getInstance(this.context);
/*     */     } 
/*     */     
/*  87 */     LTClientContext ltcontext = LTClientContext.getInstance(this.context);
/*     */     
/*  89 */     LTDataWork work = ltcontext.getW100000AW(iValue);
/*     */     
/*  91 */     if (work != null && work.getSXAWList() != null && work.getSXAWList().size() != 0) {
/*  92 */       LTSelectionLists s = ltcontext.getSelection();
/*  93 */       s.addWork(work, work.getSXAWList().get(0), null, null);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     HtmlElementContainer container = getContainer();
/* 112 */     while (container.getContainer() != null) {
/* 113 */       container = container.getContainer();
/*     */     }
/* 115 */     return container;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\lttal\LTW100000SelectionLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */