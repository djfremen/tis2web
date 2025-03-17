/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.bookmark.BookmarkList;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*    */ public abstract class AddBookmarkLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public AddBookmarkLink(final ClientContext context) {
/* 28 */     super("smenu:" + context.createID(), null);
/* 29 */     this.context = context;
/* 30 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 32 */           String image = "lt/add-bookmark.gif";
/* 33 */           if (AddBookmarkLink.this.isDisabled()) {
/* 34 */             image = "lt/add-bookmark-disabled.gif";
/*    */           }
/* 36 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 40 */           return context.getLabel("lt.tooltip.add.bookmark");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 44 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 52 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 56 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/*    */     try {
/* 61 */       SIOLT sio = getSIOLTElement();
/* 62 */       if (sio != null) {
/* 63 */         BookmarkList.getInstance(this.context).addBookmark(sio);
/*    */       }
/* 65 */     } catch (Exception e) {}
/*    */     
/* 67 */     return MainPage.getInstance(this.context).getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public abstract SIOLT getSIOLTElement();
/*    */   
/*    */   public boolean isDisabled() {
/* 73 */     SIOLT sio = getSIOLTElement();
/* 74 */     return (sio == null);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\AddBookmarkLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */