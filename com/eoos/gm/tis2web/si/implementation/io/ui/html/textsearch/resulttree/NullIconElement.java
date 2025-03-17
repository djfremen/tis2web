/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*    */ 
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class NullIconElement
/*    */   extends LinkElement {
/*  9 */   public static String SHOW_ICON = "common/exclamation.gif";
/*    */ 
/*    */   
/*    */   protected String image;
/*    */ 
/*    */   
/*    */   public NullIconElement(TocTreeElement treeElement) {
/* 16 */     super(treeElement.getContext().createID(), null);
/* 17 */     if (SHOW_ICON != null) {
/* 18 */       this.image = "pic/" + SHOW_ICON;
/*    */     }
/* 20 */     setDisabled(Boolean.TRUE);
/*    */   }
/*    */   
/*    */   protected boolean flatMode() {
/* 24 */     return false;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 28 */     if (this.image == null) {
/* 29 */       return "";
/*    */     }
/* 31 */     StringBuffer code = new StringBuffer();
/* 32 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */             public String getImageSource() {
/* 34 */               return NullIconElement.this.image;
/*    */             }
/*    */             
/*    */             public String getAlternativeText() {
/* 38 */               return null;
/*    */             }
/*    */             
/*    */             public void getAdditionalAttributes(Map<String, String> map) {
/* 42 */               map.put("border", "0");
/*    */             }
/*    */           }));
/* 45 */     return code.toString();
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 49 */     return null;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 53 */     if (this.image == null) {
/* 54 */       return "";
/*    */     }
/* 56 */     return super.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\NullIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */