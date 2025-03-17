/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.bookmarks;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.bookmark.Bookmark;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.bookmark.BookmarkList;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ 
/*     */ public class BookmarkListElement
/*     */   extends ListElement
/*     */ {
/*     */   private ClientContext context;
/*     */   public static final int STATUS_OK = 0;
/*     */   public static final int STATUS_BOOKMARKED_ITEM_NOT_AVAILABLE = 1;
/*  32 */   private int status = 0;
/*     */   
/*     */   private static final String template = "<table width=\"100%\" align=\"center\"><tr><td>{LIST}</td></tr><tr><td>{STATUS}</td></tr></table>";
/*     */ 
/*     */   
/*     */   public BookmarkListElement(ClientContext context, DataRetrievalAbstraction.DataCallback dataCallback) {
/*  38 */     super(dataCallback);
/*  39 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/*  43 */     return 3; } protected HtmlElement getContent(Object data, int columnIndex) {
/*     */     LinkElement linkElement;
/*     */     RemoveBookmarkLink removeBookmarkLink;
/*     */     RenameBookmarkLink link;
/*  47 */     final Bookmark bookmark = (Bookmark)data;
/*     */     
/*  49 */     switch (columnIndex) {
/*     */       case 0:
/*  51 */         linkElement = new LinkElement(this.context.createID(), null)
/*     */           {
/*     */             protected String getLabel() {
/*     */               try {
/*  55 */                 String retValue = bookmark.getSIOLTElement(BookmarkListElement.this.context).getMajorOperationNumber() + " - " + bookmark.getName(BookmarkListElement.this.context);
/*  56 */                 return retValue;
/*  57 */               } catch (Exception e) {
/*  58 */                 return "-";
/*     */               } 
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/*     */               try {
/*  64 */                 SIOLT element = bookmark.getSIOLTElement(BookmarkListElement.this.context);
/*  65 */                 LTClientContext oC = LTClientContext.getInstance(BookmarkListElement.this.context);
/*  66 */                 if (oC.getMainWork(element.getMajorOperationNumber(), true) == null) {
/*  67 */                   BookmarkListElement.this.status = 1;
/*     */                 } else {
/*  69 */                   BookmarkPanel.getInstance(BookmarkListElement.this.context).showDocument(element);
/*     */                 } 
/*  71 */               } catch (Exception e) {}
/*     */ 
/*     */               
/*  74 */               HtmlElementContainer container = getContainer();
/*  75 */               while (container.getContainer() != null) {
/*  76 */                 container = container.getContainer();
/*     */               }
/*  78 */               return container;
/*     */             }
/*     */           };
/*  81 */         return (HtmlElement)linkElement;
/*     */ 
/*     */       
/*     */       case 1:
/*  85 */         removeBookmarkLink = new RemoveBookmarkLink(this.context)
/*     */           {
/*     */             public Object onClick(Map params) {
/*  88 */               BookmarkList.getInstance(BookmarkListElement.this.context).removeBookmark(bookmark);
/*     */               
/*  90 */               HtmlElementContainer container = getContainer();
/*  91 */               while (container.getContainer() != null) {
/*  92 */                 container = container.getContainer();
/*     */               }
/*  94 */               return container;
/*     */             }
/*     */           };
/*  97 */         return (HtmlElement)removeBookmarkLink;
/*     */       
/*     */       case 2:
/* 100 */         link = new RenameBookmarkLink(this.context)
/*     */           {
/*     */             public Object onClick(Map params) {
/* 103 */               BookmarkEditDialog dialog = new BookmarkEditDialog(BookmarkListElement.this.context, bookmark)
/*     */                 {
/*     */                   public Object onClose() {
/* 106 */                     return BookmarkListElement.this.getTopLevelContainer();
/*     */                   }
/*     */                 };
/* 109 */               return dialog;
/*     */             }
/*     */           };
/* 112 */         return (HtmlElement)link;
/*     */     } 
/*     */     
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 120 */     if (columnIndex == 0) {
/* 121 */       return (HtmlElement)new HtmlLabel(this.context.getLabel("bookmarks") + ":");
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map<String, String> map) {
/* 128 */     if (columnIndex == 0) {
/* 129 */       map.put("colspan", "3");
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean enableHeader() {
/* 134 */     return true;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 138 */     StringBuffer code = new StringBuffer("<table width=\"100%\" align=\"center\"><tr><td>{LIST}</td></tr><tr><td>{STATUS}</td></tr></table>");
/* 139 */     StringUtilities.replace(code, "{LIST}", super.getHtmlCode(params));
/* 140 */     String status = "";
/* 141 */     if (this.status == 1) {
/* 142 */       status = "<span style=\"color:red\">" + this.context.getMessage("lt.bookmark.unavailable") + "</span>";
/* 143 */       this.status = 0;
/*     */     } 
/* 145 */     StringUtilities.replace(code, "{STATUS}", status);
/* 146 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\bookmarks\BookmarkListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */