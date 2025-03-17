/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.bookmark;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BookmarkList
/*    */   implements Bookmark.Observer
/*    */ {
/* 24 */   private static final Logger log = Logger.getLogger(BookmarkList.class);
/*    */   
/*    */   private static final String KEY_LIST = "si.bookmarks";
/*    */   
/* 28 */   private LinkedList list = null;
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private BookmarkList(ClientContext context) {
/* 33 */     this.context = context;
/*    */     try {
/* 35 */       FrameService fmi = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 36 */       this.list = (LinkedList)fmi.getPersistentObject(context.getSessionID(), "si.bookmarks");
/* 37 */     } catch (Exception e) {
/* 38 */       log.error("unable to retrieve bookmark list - error:" + e, e);
/*    */     } 
/*    */     
/* 41 */     if (this.list == null) {
/* 42 */       this.list = new LinkedList();
/*    */     }
/*    */   }
/*    */   
/*    */   public static BookmarkList getInstance(ClientContext context) {
/* 47 */     synchronized (context.getLockObject()) {
/* 48 */       BookmarkList instance = (BookmarkList)context.getObject(BookmarkList.class);
/* 49 */       if (instance == null) {
/* 50 */         instance = new BookmarkList(context);
/* 51 */         context.storeObject(BookmarkList.class, instance);
/*    */       } 
/* 53 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean saveList() {
/*    */     try {
/* 59 */       FrameService fmi = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 60 */       return fmi.setPersistentObject(this.context.getSessionID(), "si.bookmarks", this.list).booleanValue();
/* 61 */     } catch (Exception e) {
/* 62 */       log.error("unable to save bookmark list - error:" + e, e);
/* 63 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean addBookmark(SIO sio) {
/* 68 */     return addBookmark(sio, null);
/*    */   }
/*    */   
/*    */   public boolean addBookmark(SIO sio, String name) {
/* 72 */     boolean retValue = true;
/*    */     
/* 74 */     Bookmark newBookmark = new Bookmark(sio.getID().intValue(), name);
/*    */     
/* 76 */     if (!this.list.contains(newBookmark)) {
/* 77 */       retValue = this.list.add(newBookmark);
/* 78 */       retValue = (retValue && saveList());
/*    */     } 
/* 80 */     return retValue;
/*    */   }
/*    */   
/*    */   public boolean removeBookmark(Bookmark bookmark) {
/* 84 */     boolean retValue = this.list.remove(bookmark);
/* 85 */     retValue = (retValue && saveList());
/* 86 */     return retValue;
/*    */   }
/*    */   
/*    */   public List getList() {
/* 90 */     Iterator<Bookmark> iter = this.list.iterator();
/* 91 */     while (iter.hasNext()) {
/* 92 */       ((Bookmark)iter.next()).getObservableSupport().addObserver(this);
/*    */     }
/* 94 */     return Collections.unmodifiableList(this.list);
/*    */   }
/*    */   
/*    */   public void onChange() {
/* 98 */     saveList();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\bookmark\BookmarkList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */