/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*     */ 
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.system.context.ModuleContext;
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
/*  28 */   private static Logger log = Logger.getLogger(ModuleMenu.class); private static String template; protected static final String HOME = "{HOME}"; protected static final String STANDARD_INFO = "{STDINFO}";
/*     */   protected static final String BULLETINS = "{BULLETINS}";
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(ModuleMenu.class, "modulemenu.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("unable to load template - error:" + e, e);
/*  36 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final String NUMBER_SEARCH = "{NUMBERSEARCH}";
/*     */ 
/*     */   
/*     */   protected static final String TEXT_SEARCH = "{TEXTSEARCH}";
/*     */ 
/*     */   
/*     */   protected static final String BOOKMARKS = "{BOOKMARKS}";
/*     */ 
/*     */   
/*     */   protected static final String SPECIAL_BROCHURES = "{SPECIALBROCHURES}";
/*     */   
/*     */   protected static final String FAULTDIAG = "{FAULTDIAG}";
/*     */   
/*     */   protected ClientContext context;
/*     */   
/*     */   private HomeUIIconLink linkHome;
/*     */   
/*     */   private StandardUIIconLink linkStandard;
/*     */   
/*     */   private BulletinsUIIconLink linkBulletins;
/*     */   
/*     */   private BrochuresUIIconLink linkBrochures;
/*     */   
/*     */   private NumberSearchUIIconLink linkNumberSearch;
/*     */   
/*     */   private TextSearchUIIconLink linkTextSearch;
/*     */   
/*     */   private BookmarkUIIconLink linkBookmarks;
/*     */   
/*     */   private FaultDiagUIIconLink faultDiag;
/*     */   
/*     */   protected HtmlElementContainerBase dynamicContainer;
/*     */ 
/*     */   
/*     */   public ModuleMenu(ClientContext context) {
/*  77 */     this.context = context;
/*     */     
/*  79 */     this.linkHome = new HomeUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/*  81 */           ModuleMenu.this.hookOnClick("{HOME}");
/*  82 */           return super.onClick(params);
/*     */         }
/*     */       };
/*  85 */     addElement((HtmlElement)this.linkHome);
/*     */     
/*  87 */     this.linkStandard = new StandardUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/*  89 */           ModuleMenu.this.hookOnClick("{STDINFO}");
/*  90 */           return super.onClick(params);
/*     */         }
/*     */       };
/*  93 */     addElement((HtmlElement)this.linkStandard);
/*     */     
/*  95 */     this.linkBulletins = new BulletinsUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/*  97 */           ModuleMenu.this.hookOnClick("{BULLETINS}");
/*  98 */           return super.onClick(params);
/*     */         }
/*     */       };
/* 101 */     addElement((HtmlElement)this.linkBulletins);
/*     */     
/* 103 */     this.linkBrochures = new BrochuresUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/* 105 */           ModuleMenu.this.hookOnClick("{SPECIALBROCHURES}");
/* 106 */           return super.onClick(params);
/*     */         }
/*     */       };
/* 109 */     addElement((HtmlElement)this.linkBrochures);
/*     */     
/* 111 */     this.linkTextSearch = new TextSearchUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/* 113 */           ModuleMenu.this.hookOnClick("{TEXTSEARCH}");
/* 114 */           return super.onClick(params);
/*     */         }
/*     */       };
/* 117 */     addElement((HtmlElement)this.linkTextSearch);
/*     */     
/* 119 */     this.linkNumberSearch = new NumberSearchUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/* 121 */           ModuleMenu.this.hookOnClick("{NUMBERSEARCH}");
/* 122 */           return super.onClick(params);
/*     */         }
/*     */       };
/* 125 */     addElement((HtmlElement)this.linkNumberSearch);
/*     */     
/* 127 */     this.linkBookmarks = new BookmarkUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/* 129 */           ModuleMenu.this.hookOnClick("{BOOKMARKS}");
/* 130 */           return super.onClick(params);
/*     */         }
/*     */       };
/* 133 */     addElement((HtmlElement)this.linkBookmarks);
/*     */     
/* 135 */     this.faultDiag = new FaultDiagUIIconLink(context) {
/*     */         public Object onClick(Map params) {
/* 137 */           ModuleMenu.this.hookOnClick("{FAULTDIAG}");
/* 138 */           return super.onClick(params);
/*     */         }
/*     */       };
/* 141 */     addElement((HtmlElement)this.faultDiag);
/*     */     
/* 143 */     this.dynamicContainer = new HtmlElementContainerBase() {
/*     */         public String getHtmlCode(Map params) {
/* 145 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/* 148 */     addElement((HtmlElement)this.dynamicContainer);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 153 */     StringBuffer code = new StringBuffer(template);
/* 154 */     String replacer = null;
/* 155 */     LinkElement link = null;
/* 156 */     for (int i = 0; i < 8; i++) {
/* 157 */       StandardUIIconLink standardUIIconLink; BulletinsUIIconLink bulletinsUIIconLink; TextSearchUIIconLink textSearchUIIconLink; NumberSearchUIIconLink numberSearchUIIconLink; BookmarkUIIconLink bookmarkUIIconLink; BrochuresUIIconLink brochuresUIIconLink; FaultDiagUIIconLink faultDiagUIIconLink; switch (i) {
/*     */         case 0:
/* 159 */           replacer = "{HOME}";
/* 160 */           link = this.linkHome;
/*     */           break;
/*     */         case 1:
/* 163 */           replacer = "{STDINFO}";
/* 164 */           standardUIIconLink = this.linkStandard;
/*     */           break;
/*     */         case 2:
/* 167 */           replacer = "{BULLETINS}";
/* 168 */           bulletinsUIIconLink = this.linkBulletins;
/*     */           break;
/*     */         case 3:
/* 171 */           replacer = "{TEXTSEARCH}";
/* 172 */           textSearchUIIconLink = this.linkTextSearch;
/*     */           break;
/*     */         case 4:
/* 175 */           replacer = "{NUMBERSEARCH}";
/* 176 */           numberSearchUIIconLink = this.linkNumberSearch;
/*     */           break;
/*     */         case 5:
/* 179 */           replacer = "{BOOKMARKS}";
/* 180 */           bookmarkUIIconLink = this.linkBookmarks;
/*     */           break;
/*     */         case 6:
/* 183 */           replacer = "{SPECIALBROCHURES}";
/* 184 */           brochuresUIIconLink = this.linkBrochures;
/*     */           break;
/*     */         case 7:
/* 187 */           replacer = "{FAULTDIAG}";
/* 188 */           faultDiagUIIconLink = this.faultDiag;
/*     */           break;
/*     */         default:
/* 191 */           throw new IllegalArgumentException();
/*     */       } 
/* 193 */       if (activate(replacer)) {
/* 194 */         StringUtilities.replace(code, replacer, faultDiagUIIconLink.getHtmlCode(params));
/*     */       } else {
/* 196 */         StringUtilities.replace(code, "<td>" + replacer + "</td>", "");
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     this.dynamicContainer.removeAllElements();
/* 201 */     int dynamicItemCount = getDynamicItemCount();
/* 202 */     if (dynamicItemCount > 0) {
/* 203 */       for (int j = 0; j < dynamicItemCount; j++) {
/* 204 */         HtmlElement item = getDynamicItem(j);
/* 205 */         this.dynamicContainer.addElement(item);
/* 206 */         StringUtilities.replace(code, "{DYNAMIC}", "<td>" + item.getHtmlCode(params) + "</td>{DYNAMIC}");
/*     */       } 
/* 208 */       StringUtilities.replace(code, "{DYNAMIC}", "");
/*     */     } else {
/*     */       
/* 211 */       SectionIndex index = StringUtilities.getSectionIndex(code.toString(), "<!--DYNAMIC-->", "<!--/DYNAMIC-->", 0, true, false);
/* 212 */       if (index != null) {
/* 213 */         StringUtilities.replaceSectionContent(code, index, "");
/*     */       }
/*     */     } 
/*     */     
/* 217 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected boolean activate(String linkID) {
/* 221 */     if ("{BULLETINS}".equals(linkID)) {
/* 222 */       return ModuleContext.getInstance(this.context.getSessionID()).allowBulletinAccess();
/*     */     }
/* 224 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void hookOnClick(String linkID) {}
/*     */ 
/*     */   
/*     */   protected int getDynamicItemCount() {
/* 232 */     return 0;
/*     */   }
/*     */   
/*     */   protected HtmlElement getDynamicItem(int index) {
/* 236 */     return (HtmlElement)new HtmlLabel("&nbsp");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\ModuleMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */