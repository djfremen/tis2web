/*     */ package com.eoos.gm.tis2web.si.implementation.statcont.ui.html.menu;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.regexp.RE;
/*     */ import org.apache.regexp.RECompiler;
/*     */ import org.apache.regexp.REProgram;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PrintViewLink
/*     */   extends LinkElement
/*     */ {
/*  26 */   protected static REProgram reLinkStartProg = null;
/*  27 */   protected static REProgram reLinkEndProg = null;
/*     */   
/*     */   static {
/*     */     try {
/*  31 */       RECompiler comp = new RECompiler();
/*  32 */       reLinkStartProg = comp.compile("<a.*?href.*?>");
/*  33 */       reLinkEndProg = comp.compile("</a\\s*>");
/*     */     
/*     */     }
/*  36 */     catch (Exception e) {
/*  37 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   
/*     */   public PrintViewLink(final ClientContext context) {
/*  44 */     super(context.createID(), null);
/*  45 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  47 */           String image = "si/printview.gif";
/*  48 */           if (PrintViewLink.this.isDisabled()) {
/*  49 */             image = "si/printview-disabled.gif";
/*     */           }
/*  51 */           return "pic/" + image;
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  55 */           return context.getLabel("printer.friendly.page");
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
/*     */   
/*     */   protected String getLabel() {
/*  68 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   protected String getTargetFrame() {
/*  72 */     return "printer-friendly";
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/*  76 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  80 */     StringBuffer tmp = new StringBuffer(getCode(submitParams));
/*     */ 
/*     */     
/*  83 */     SectionIndex index = null;
/*  84 */     RE reLinkStart = new RE(reLinkStartProg, 1);
/*  85 */     RE reLinkEnd = new RE(reLinkEndProg, 1);
/*  86 */     while ((index = StringUtilities.getSectionIndex(tmp.toString(), "<!--XPRINT-->", "<!--/XPRINT-->", 0, true, false)) != null) {
/*  87 */       StringUtilities.replaceSectionContent(tmp, index, "");
/*     */     }
/*     */ 
/*     */     
/*  91 */     index = null;
/*  92 */     while ((index = StringUtilities.getSectionIndex(tmp.toString(), reLinkStart, reLinkEnd, 0, true, false)) != null) {
/*  93 */       String all = StringUtilities.getSectionContent(tmp, index);
/*  94 */       SectionIndex contentIndex = StringUtilities.getSectionIndex(all, reLinkStart, reLinkEnd, 0, false, false);
/*     */       
/*  96 */       StringUtilities.replaceSectionContent(tmp, index, "<u>" + StringUtilities.getSectionContent(all, contentIndex) + "</u>");
/*     */     } 
/*     */ 
/*     */     
/* 100 */     if ((index = StringUtilities.getSectionIndex(tmp.toString(), "//JAVASCRIPT-DISABLE", "//JAVASCRIPT-DISABLE-END", 0, true, false)) != null) {
/* 101 */       StringUtilities.replaceSectionContent(tmp, index, "");
/*     */     }
/*     */     
/* 104 */     return new ResultObject(0, tmp.toString());
/*     */   }
/*     */   
/*     */   public abstract String getCode(Map paramMap);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcon\\ui\html\menu\PrintViewLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */