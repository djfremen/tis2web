/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.bookmarks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.bookmark.BookmarkList;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.HtmlLabel;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.ListElement;
/*    */ import com.eoos.html.element.gtwo.PagedElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BookmarkListPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 28 */   private static Logger log = Logger.getLogger(BookmarkListPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 33 */       template = ApplicationContext.getInstance().loadFile(BookmarkListPanel.class, "bookmarklistpanel.html", null).toString();
/* 34 */     } catch (Exception e) {
/* 35 */       log.error("unable to load template - error:" + e, e);
/* 36 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private PagedElement pagedElement;
/* 42 */   private static final Object NO_DATA = new Object();
/*    */ 
/*    */   
/*    */   public BookmarkListPanel(final ClientContext context) {
/* 46 */     DataRetrievalAbstraction.DataCallback callback = new DataRetrievalAbstraction.DataCallback() {
/*    */         public List getData() {
/* 48 */           List<Object> retValue = new ArrayList(BookmarkList.getInstance(context).getList());
/* 49 */           if (retValue.size() == 0) {
/* 50 */             retValue.add(BookmarkListPanel.NO_DATA);
/*    */           }
/* 52 */           return retValue;
/*    */         }
/*    */       };
/*    */     
/* 56 */     ListElement listElement = new BookmarkListElement(context, callback)
/*    */       {
/*    */         protected HtmlElement getContent(Object data, int columnIndex) {
/* 59 */           if (data.equals(BookmarkListPanel.NO_DATA)) {
/* 60 */             return (columnIndex == 0) ? (HtmlElement)new HtmlLabel(context.getMessage("lt.no.bookmarks")) : (HtmlElement)new HtmlLabel("");
/*    */           }
/* 62 */           return super.getContent(data, columnIndex);
/*    */         }
/*    */       };
/*    */ 
/*    */ 
/*    */     
/* 68 */     this.pagedElement = new PagedElement(context.createID(), (HtmlElement)listElement, 10, 10);
/* 69 */     addElement((HtmlElement)this.pagedElement);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 73 */     StringBuffer code = new StringBuffer(template);
/* 74 */     StringUtilities.replace(code, "{BOOKMARK_LIST}", this.pagedElement.getHtmlCode(params));
/* 75 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\bookmarks\BookmarkListPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */