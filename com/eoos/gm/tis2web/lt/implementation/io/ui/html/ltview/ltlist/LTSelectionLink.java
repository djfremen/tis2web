/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSXAWData;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSelectionLists;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTTroublecode;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.LinkElement;
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
/*     */ public class LTSelectionLink
/*     */   extends LinkElement
/*     */ {
/*  25 */   private HtmlImgRenderer.Callback imgRendererCallback = null;
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */   
/*     */   private LTDataWork work;
/*     */ 
/*     */   
/*     */   private LTDataWork currentpseudoMain;
/*     */ 
/*     */   
/*     */   private LTSXAWData awdata;
/*     */   
/*     */   private LTTCSelectBox tcsel;
/*     */   
/*     */   private boolean bAlreadyInList;
/*     */ 
/*     */   
/*     */   public LTSelectionLink(LTDataWork work, LTDataWork currentpseudoMain, LTSXAWData aw, LTTCSelectBox tcb, ClientContext context, final boolean bAlreadyInList) {
/*  45 */     super(buildID(work.getNr(), aw.getInternalID()), null);
/*     */     
/*  47 */     this.bAlreadyInList = bAlreadyInList;
/*  48 */     this.work = work;
/*  49 */     this.currentpseudoMain = currentpseudoMain;
/*  50 */     this.awdata = aw;
/*  51 */     this.context = context;
/*  52 */     this.tcsel = tcb;
/*     */     
/*  54 */     if (this.imgRendererCallback == null) {
/*  55 */       this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*     */         {
/*     */           public String getImageSource() {
/*  58 */             if (bAlreadyInList) {
/*  59 */               return "pic/lt/ltsel.gif";
/*     */             }
/*  61 */             return "pic/lt/ltunsel.gif";
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public String getAlternativeText() {
/*  67 */             return "";
/*     */           }
/*     */           
/*     */           public void getAdditionalAttributes(Map<String, String> map) {
/*  71 */             map.put("border", "0");
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static String buildID(String oWorknr, int iSXAWID) {
/*  79 */     return "w" + Integer.valueOf(iSXAWID).toString() + "-" + oWorknr;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  84 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  90 */     LTSelectionLists s = LTClientContext.getInstance(this.context).getSelection();
/*  91 */     LTDataWork takenwork = this.work;
/*  92 */     if (this.currentpseudoMain != null) {
/*  93 */       takenwork = this.currentpseudoMain;
/*     */     }
/*     */     
/*  96 */     if (this.bAlreadyInList) {
/*     */       
/*  98 */       s.removeWork(takenwork.getNr(), takenwork.getTasktype());
/*     */     } else {
/*     */       
/* 101 */       LTTroublecode tc = null;
/* 102 */       if (this.tcsel != null) {
/* 103 */         tc = (LTTroublecode)this.tcsel.getValue();
/* 104 */         if (tc != null && (tc.getTroubleCode() == null || tc.getTroubleCode().length() == 0)) {
/* 105 */           tc = null;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 110 */       s.addWork(takenwork, this.awdata, tc, (this.currentpseudoMain != null) ? this.work.getNr() : null);
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
/* 127 */     HtmlElementContainer container = getContainer();
/* 128 */     while (container.getContainer() != null) {
/* 129 */       container = container.getContainer();
/*     */     }
/* 131 */     return container;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\ltlist\LTSelectionLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */