/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.WindowsEncodingMap;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTTALExport;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LTDownloadTALLink
/*    */   extends LinkElement
/*    */ {
/* 28 */   private HtmlImgRenderer.Callback imgRendererCallback = null;
/*    */   
/*    */   private ClientContext context;
/*    */ 
/*    */   
/*    */   public LTDownloadTALLink(final ClientContext context) {
/* 34 */     super(context.createID(), null);
/*    */     
/* 36 */     this.context = context;
/*    */     
/* 38 */     if (this.imgRendererCallback == null) {
/* 39 */       this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */           public String getImageSource() {
/* 41 */             return "pic/lt/lt-download.gif";
/*    */           }
/*    */           
/*    */           public String getAlternativeText() {
/* 45 */             return context.getLabel("lt.tooltip.download");
/*    */           }
/*    */           
/*    */           public void getAdditionalAttributes(Map<String, String> map) {
/* 49 */             map.put("border", "0");
/*    */           }
/*    */         };
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 57 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   protected String getTargetFrame() {
/* 61 */     return "newone";
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 66 */     LTTALExport exp = new LTTALExport(this.context);
/* 67 */     String oText = exp.ExportTAL();
/*    */     
/* 69 */     byte[] data = null;
/* 70 */     String mime = "text/plain; charset=";
/*    */     try {
/* 72 */       Integer textEncoding = this.context.getSharedContext().getTextEncoding();
/* 73 */       if (textEncoding == null) {
/* 74 */         textEncoding = FrameService.ENC_WINDOWS;
/* 75 */         this.context.getSharedContext().setTextEncoding(textEncoding);
/*    */       } 
/* 77 */       if (textEncoding.equals(FrameService.ENC_UTF8)) {
/* 78 */         data = oText.getBytes("UTF-8");
/* 79 */         mime = mime + "utf-8";
/* 80 */       } else if (textEncoding.equals(FrameService.ENC_WINDOWS)) {
/* 81 */         String encoding = WindowsEncodingMap.getInstance().get(this.context.getLocale());
/* 82 */         data = oText.getBytes(encoding);
/* 83 */         mime = mime + encoding;
/*    */       }
/*    */     
/* 86 */     } catch (UnsupportedEncodingException e) {
/* 87 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */     
/* 90 */     PairImpl pairImpl = new PairImpl(mime, data);
/*    */ 
/*    */ 
/*    */     
/* 94 */     return new ResultObject(10, true, pairImpl);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\lttal\LTDownloadTALLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */