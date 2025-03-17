/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.element.input.tree.TreeControl;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TreeLinkElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected ClientContext context;
/*    */   protected TreeControl control;
/*    */   protected Object node;
/*    */   
/*    */   protected TreeLinkElement(ClientContext context, TreeControl control, Object node) {
/* 20 */     super(context.createID(), "_top");
/* 21 */     this.context = context;
/* 22 */     this.control = control;
/* 23 */     this.node = node;
/*    */   }
/*    */ 
/*    */   
/*    */   protected final String getLabel() {
/* 28 */     return HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */           public String getImageSource() {
/* 30 */             String image = TreeLinkElement.this.getImagePath();
/* 31 */             return "pic/" + image;
/*    */           }
/*    */           
/*    */           public String getAlternativeText() {
/* 35 */             return "";
/*    */           }
/*    */           
/*    */           public void getAdditionalAttributes(Map<String, String> map) {
/* 39 */             map.put("border", "0");
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected abstract String getImagePath();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\TreeLinkElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */