/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.bookmarks;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.bookmark.Bookmark;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BookmarkEditDialog
/*     */   extends DialogBase
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(BookmarkEditDialog.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  28 */       template = ApplicationContext.getInstance().loadFile(BookmarkEditDialog.class, "editbookmarkdialog.html", null).toString();
/*  29 */     } catch (Exception e) {
/*  30 */       log.error("unable to load template - error:" + e, e);
/*  31 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Bookmark bookmark;
/*     */   
/*     */   protected TextInputElement ieName;
/*     */   
/*     */   protected ClickButtonElement buttonOK;
/*     */   protected ClickButtonElement buttonReset;
/*     */   
/*     */   public BookmarkEditDialog(final ClientContext context, final Bookmark bookmark) {
/*  44 */     super(context);
/*  45 */     this.bookmark = bookmark;
/*     */     
/*  47 */     this.ieName = new TextInputElement(context.createID(), 50, -1) {
/*     */         public String getHtmlCode(Map params) {
/*  49 */           setValue(bookmark.getName(context));
/*  50 */           return super.getHtmlCode(params);
/*     */         }
/*     */       };
/*  53 */     addElement((HtmlElement)this.ieName);
/*     */     
/*  55 */     this.buttonOK = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  57 */           return context.getLabel("ok");
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  61 */           String name = (String)BookmarkEditDialog.this.ieName.getValue();
/*  62 */           if (name != null && name.length() > 0) {
/*  63 */             bookmark.setName(name);
/*     */           } else {
/*  65 */             bookmark.setName(null);
/*     */           } 
/*  67 */           return BookmarkEditDialog.this.onClose();
/*     */         }
/*     */       };
/*  70 */     addElement((HtmlElement)this.buttonOK);
/*     */     
/*  72 */     this.buttonReset = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  74 */           return context.getLabel("reset");
/*     */         }
/*     */         
/*     */         public Object onClick(Map params) {
/*  78 */           bookmark.setName(null);
/*     */           
/*  80 */           HtmlElementContainer container = getContainer();
/*  81 */           while (container.getContainer() != null) {
/*  82 */             container = container.getContainer();
/*     */           }
/*  84 */           return container;
/*     */         }
/*     */       };
/*  87 */     addElement((HtmlElement)this.buttonReset);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getTitle(Map params) {
/*  92 */     return this.context.getMessage("edit.bookmark");
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/*  96 */     StringBuffer code = new StringBuffer(template);
/*     */     
/*  98 */     StringUtilities.replace(code, "{LABEL_NAME}", this.context.getLabel("name") + ":");
/*  99 */     StringUtilities.replace(code, "{INPUT_NAME}", this.ieName.getHtmlCode(params));
/* 100 */     StringUtilities.replace(code, "{BUTTON_OK}", this.buttonOK.getHtmlCode(params));
/* 101 */     StringUtilities.replace(code, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/*     */     
/* 103 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected abstract Object onClose();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\bookmarks\BookmarkEditDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */