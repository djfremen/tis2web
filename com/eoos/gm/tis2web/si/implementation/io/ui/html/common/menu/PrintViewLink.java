/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu;
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
/*  32 */       reLinkStartProg = comp.compile("<[Aa]\\s*[Hh][Rr][Ee][Ff][^>]*>");
/*  33 */       reLinkEndProg = comp.compile("</[Aa]\\s*>");
/*  34 */     } catch (Exception e) {
/*  35 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private HtmlImgRenderer.Callback imgRendererCallback;
/*     */   
/*     */   public PrintViewLink(final ClientContext context) {
/*  42 */     super("smenu:" + context.createID(), null);
/*  43 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */         public String getImageSource() {
/*  45 */           String image = "si/printview.gif";
/*  46 */           if (PrintViewLink.this.isDisabled()) {
/*  47 */             image = "si/printview-disabled.gif";
/*     */           }
/*  49 */           return "pic/" + image;
/*     */         }
/*     */         
/*     */         public String getAlternativeText() {
/*  53 */           return context.getLabel("printer.friendly.page");
/*     */         }
/*     */         
/*     */         public void getAdditionalAttributes(Map<String, String> map) {
/*  57 */           map.put("border", "0");
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLabel() {
/*  66 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*     */   }
/*     */   
/*     */   protected String getTargetFrame() {
/*  70 */     return "printerfriendly";
/*     */   }
/*     */   
/*     */   public boolean clicked() {
/*  74 */     return (this.clicked && !isDisabled());
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/*  78 */     StringBuffer tmp = new StringBuffer(getCode());
/*     */ 
/*     */     
/*  81 */     SectionIndex index = null;
/*  82 */     while ((index = StringUtilities.getSectionIndex(tmp.toString(), "<!--XPRINT-->", "<!--/XPRINT-->", 0, true, false)) != null) {
/*  83 */       StringUtilities.replaceSectionContent(tmp, index, "");
/*     */     }
/*     */ 
/*     */     
/*  87 */     index = null;
/*  88 */     RE reLinkStart = new RE(reLinkStartProg);
/*  89 */     RE reLinkEnd = new RE(reLinkEndProg);
/*  90 */     while ((index = StringUtilities.getSectionIndex(tmp.toString(), reLinkStart, reLinkEnd, 0, true, false)) != null) {
/*  91 */       String all = StringUtilities.getSectionContent(tmp, index);
/*  92 */       SectionIndex contentIndex = StringUtilities.getSectionIndex(all, reLinkStart, reLinkEnd, 0, false, false);
/*     */       
/*  94 */       StringUtilities.replaceSectionContent(tmp, index, "<u>" + StringUtilities.getSectionContent(all, contentIndex) + "</u>");
/*     */     } 
/*     */ 
/*     */     
/*  98 */     if ((index = StringUtilities.getSectionIndex(tmp.toString(), "//JAVASCRIPT-DISABLE", "//JAVASCRIPT-DISABLE-END", 0, true, false)) != null) {
/*  99 */       StringUtilities.replaceSectionContent(tmp, index, "");
/*     */     }
/*     */     
/* 102 */     return new ResultObject(0, tmp.toString());
/*     */   }
/*     */   
/*     */   public abstract String getCode();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\PrintViewLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */