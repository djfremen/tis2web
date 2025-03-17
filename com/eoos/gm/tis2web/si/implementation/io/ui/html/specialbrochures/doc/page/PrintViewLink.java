/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.util.Map;
/*    */ import org.apache.regexp.RECompiler;
/*    */ import org.apache.regexp.REProgram;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PrintViewLink
/*    */   extends LinkElement
/*    */ {
/* 22 */   protected static REProgram reLinkStart = null;
/*    */   
/* 24 */   protected static REProgram reLinkEnd = null;
/*    */   
/*    */   static {
/*    */     try {
/* 28 */       RECompiler comp = new RECompiler();
/* 29 */       reLinkStart = comp.compile("<[Aa]\\s*[Hh][Rr][Ee][Ff][^>]*>");
/* 30 */       reLinkEnd = comp.compile("</[Aa]\\s*>");
/* 31 */     } catch (Exception e) {
/* 32 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   public PrintViewLink(final ClientContext context) {
/* 40 */     super(context.createID(), null);
/* 41 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 43 */           return "pic/common/print.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 47 */           return context.getLabel("printer.friendly.page");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 51 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 60 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 64 */     return "printer-friendly";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\doc\page\PrintViewLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */