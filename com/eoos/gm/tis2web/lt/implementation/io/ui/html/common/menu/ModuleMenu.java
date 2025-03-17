/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.menu;
/*     */ 
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ public class ModuleMenu
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  27 */   private static Logger log = Logger.getLogger(ModuleMenu.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  32 */       template = ApplicationContext.getInstance().loadFile(ModuleMenu.class, "modulemenu.html", null).toString();
/*  33 */     } catch (Exception e) {
/*  34 */       log.error("unable to load template - error:" + e, e);
/*  35 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final String STANDARD_INFO = "{STDINFO}";
/*     */   
/*     */   protected static final String TEXT_SEARCH = "{TEXTSEARCH}";
/*     */   
/*     */   protected static final String BOOKMARKS = "{BOOKMARKS}";
/*     */   
/*     */   protected static final String TIME_ALLOWANCE_LIST = "{TAL}";
/*     */   
/*     */   protected ClientContext context;
/*     */   
/*     */   private StandardUIIconLink linkStandard;
/*     */   
/*     */   private TextSearchUIIconLink linkTextSearch;
/*     */   
/*     */   private BookmarkUIIconLink linkBookmarks;
/*     */   
/*     */   private LTSelectionListLink linkTAL;
/*     */   protected HtmlElementContainerBase dynamicContainer;
/*     */   
/*     */   public ModuleMenu(ClientContext context) {
/*  60 */     this.context = context;
/*     */     
/*  62 */     this.linkStandard = new StandardUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/*  64 */           ModuleMenu.this.hookOnClick("{STDINFO}");
/*  65 */           return super.onClick(params);
/*     */         }
/*     */       };
/*  68 */     addElement((HtmlElement)this.linkStandard);
/*     */     
/*  70 */     this.linkTextSearch = new TextSearchUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/*  72 */           ModuleMenu.this.hookOnClick("{TEXTSEARCH}");
/*  73 */           return super.onClick(params);
/*     */         }
/*     */       };
/*  76 */     addElement((HtmlElement)this.linkTextSearch);
/*     */     
/*  78 */     this.linkBookmarks = new BookmarkUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/*  80 */           ModuleMenu.this.hookOnClick("{BOOKMARKS}");
/*  81 */           return super.onClick(params);
/*     */         }
/*     */       };
/*  84 */     addElement((HtmlElement)this.linkBookmarks);
/*     */     
/*  86 */     this.linkTAL = new LTSelectionListLink(context) {
/*     */         public Object onClick(Map params) {
/*  88 */           ModuleMenu.this.hookOnClick("{TAL}");
/*  89 */           return super.onClick(params);
/*     */         }
/*     */       };
/*  92 */     addElement((HtmlElement)this.linkTAL);
/*     */     
/*  94 */     this.dynamicContainer = new HtmlElementContainerBase() {
/*     */         public String getHtmlCode(Map params) {
/*  96 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*  99 */     addElement((HtmlElement)this.dynamicContainer);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 104 */     StringBuffer code = new StringBuffer(template);
/* 105 */     String replacer = null;
/* 106 */     LinkElement link = null;
/* 107 */     for (int i = 0; i < 4; i++) {
/* 108 */       switch (i) {
/*     */         case 0:
/* 110 */           replacer = "{STDINFO}";
/* 111 */           link = this.linkStandard;
/*     */           break;
/*     */         case 1:
/* 114 */           replacer = "{TEXTSEARCH}";
/* 115 */           link = this.linkTextSearch;
/*     */           break;
/*     */         case 2:
/* 118 */           replacer = "{BOOKMARKS}";
/* 119 */           link = this.linkBookmarks;
/*     */           break;
/*     */         case 3:
/* 122 */           replacer = "{TAL}";
/* 123 */           link = this.linkTAL;
/*     */           break;
/*     */         default:
/* 126 */           throw new IllegalArgumentException();
/*     */       } 
/* 128 */       if (activate(replacer)) {
/* 129 */         StringUtilities.replace(code, replacer, link.getHtmlCode(params));
/*     */       } else {
/* 131 */         StringUtilities.replace(code, "<td>" + replacer + "</td>", "");
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     this.dynamicContainer.removeAllElements();
/* 136 */     int dynamicItemCount = getDynamicItemCount();
/* 137 */     if (dynamicItemCount > 0) {
/* 138 */       for (int j = 0; j < dynamicItemCount; j++) {
/* 139 */         HtmlElement item = getDynamicItem(j);
/* 140 */         this.dynamicContainer.addElement(item);
/* 141 */         StringUtilities.replace(code, "{DYNAMIC}", "<td>" + item.getHtmlCode(params) + "</td>{DYNAMIC}");
/*     */       } 
/* 143 */       StringUtilities.replace(code, "{DYNAMIC}", "");
/*     */     } else {
/*     */       
/* 146 */       SectionIndex index = StringUtilities.getSectionIndex(code.toString(), "<!--DYNAMIC-->", "<!--/DYNAMIC-->", 0, true, false);
/* 147 */       if (index != null) {
/* 148 */         StringUtilities.replaceSectionContent(code, index, "");
/*     */       }
/*     */     } 
/*     */     
/* 152 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected boolean activate(String linkID) {
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void hookOnClick(String linkID) {}
/*     */   
/*     */   protected int getDynamicItemCount() {
/* 163 */     return 0;
/*     */   }
/*     */   
/*     */   protected HtmlElement getDynamicItem(int index) {
/* 167 */     return (HtmlElement)new HtmlLabel("&nbsp");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\menu\ModuleMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */