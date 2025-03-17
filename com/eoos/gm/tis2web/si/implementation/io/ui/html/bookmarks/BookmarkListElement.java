/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.bookmarks;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.bookmark.Bookmark;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.bookmark.BookmarkList;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BookmarkListElement
/*     */   extends ListElement
/*     */ {
/*     */   private ClientContext context;
/*     */   
/*     */   public BookmarkListElement(ClientContext context, DataRetrievalAbstraction.DataCallback dataCallback) {
/*  27 */     super(dataCallback);
/*  28 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/*  32 */     return 3; } protected HtmlElement getContent(Object data, int columnIndex) {
/*     */     LinkElement linkElement;
/*     */     RemoveBookmarkLink removeBookmarkLink;
/*     */     RenameBookmarkLink link;
/*  36 */     final Bookmark bookmark = (Bookmark)data;
/*     */     
/*  38 */     switch (columnIndex) {
/*     */       case 0:
/*  40 */         linkElement = new LinkElement(this.context.createID(), null) {
/*     */             protected String getLabel() {
/*  42 */               return bookmark.getName(BookmarkListElement.this.context.getLocale(), BookmarkListElement.this.context);
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/*  46 */               BookmarkPanel.getInstance(BookmarkListElement.this.context).showDocument(bookmark.getSIO(BookmarkListElement.this.context));
/*     */               
/*  48 */               HtmlElementContainer container = getContainer();
/*  49 */               while (container.getContainer() != null) {
/*  50 */                 container = container.getContainer();
/*     */               }
/*  52 */               return container;
/*     */             }
/*     */           };
/*  55 */         return (HtmlElement)linkElement;
/*     */ 
/*     */       
/*     */       case 1:
/*  59 */         removeBookmarkLink = new RemoveBookmarkLink(this.context) {
/*     */             public Object onClick(Map params) {
/*  61 */               BookmarkList.getInstance(BookmarkListElement.this.context).removeBookmark(bookmark);
/*     */               
/*  63 */               HtmlElementContainer container = getContainer();
/*  64 */               while (container.getContainer() != null) {
/*  65 */                 container = container.getContainer();
/*     */               }
/*  67 */               return container;
/*     */             }
/*     */           };
/*  70 */         return (HtmlElement)removeBookmarkLink;
/*     */       
/*     */       case 2:
/*  73 */         link = new RenameBookmarkLink(this.context) {
/*     */             public Object onClick(Map params) {
/*  75 */               BookmarkEditDialog dialog = new BookmarkEditDialog(BookmarkListElement.this.context, bookmark) {
/*     */                   public Object onClose() {
/*  77 */                     return BookmarkListElement.this.getTopLevelContainer();
/*     */                   }
/*     */                 };
/*  80 */               return dialog;
/*     */             }
/*     */           };
/*  83 */         return (HtmlElement)link;
/*     */     } 
/*     */     
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/*  91 */     if (columnIndex == 0) {
/*  92 */       return (HtmlElement)new HtmlLabel(this.context.getLabel("bookmarks") + ":");
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map<String, String> map) {
/*  99 */     if (columnIndex == 0) {
/* 100 */       map.put("colspan", "3");
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean enableHeader() {
/* 105 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\bookmarks\BookmarkListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */