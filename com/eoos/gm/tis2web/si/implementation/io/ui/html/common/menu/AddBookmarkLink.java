/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.bookmark.BookmarkList;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
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
/*    */ 
/*    */ public abstract class AddBookmarkLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public AddBookmarkLink(final ClientContext context) {
/* 29 */     super("smenu:" + context.createID(), null);
/* 30 */     this.context = context;
/* 31 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 33 */           String image = "si/add-bookmark.gif";
/* 34 */           if (AddBookmarkLink.this.isDisabled()) {
/* 35 */             image = "si/add-bookmark-disabled.gif";
/*    */           }
/* 37 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 41 */           return context.getLabel("si.tooltip.add.bookmark");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 45 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 53 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 57 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/*    */     try {
/* 62 */       SIO sio = getSIO();
/* 63 */       if (sio != null && !(sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR)) {
/* 64 */         BookmarkList.getInstance(this.context).addBookmark(sio);
/*    */       }
/* 66 */     } catch (Exception e) {}
/*    */     
/* 68 */     return MainPage.getInstance(this.context).getHtmlCode(params);
/*    */   }
/*    */   
/*    */   public abstract SIO getSIO();
/*    */   
/*    */   public boolean isDisabled() {
/* 74 */     SIO sio = getSIO();
/* 75 */     return (sio == null || sio instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\AddBookmarkLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */