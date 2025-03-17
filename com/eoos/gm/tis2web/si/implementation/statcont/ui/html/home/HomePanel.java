/*    */ package com.eoos.gm.tis2web.si.implementation.statcont.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.statcont.ui.html.menu.StandardUIIconLink;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.IconElement;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 20 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 25 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "homepanel.html", null).toString();
/* 26 */     } catch (Exception e) {
/* 27 */       log.error("could not load template - error:" + e, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   private StandardUIIconLink linkStandardInfo;
/*    */   
/*    */   private IconElement iconBulletins;
/*    */   
/*    */   private IconElement iconBrochures;
/*    */   
/*    */   private IconElement iconNumberSearch;
/*    */   
/*    */   private IconElement iconFullSearch;
/*    */   
/*    */   private IconElement iconBookmarks;
/*    */   
/*    */   private HomePanel(ClientContext context) {
/* 47 */     this.context = context;
/*    */     
/* 49 */     this.linkStandardInfo = new StandardUIIconLink(context);
/* 50 */     addElement((HtmlElement)this.linkStandardInfo);
/*    */     
/* 52 */     this.iconBulletins = new IconElement("pic/si/bulletins-disabled.gif", null);
/* 53 */     this.iconBrochures = new IconElement("pic/si/brochures-disabled.gif", null);
/* 54 */     this.iconNumberSearch = new IconElement("pic/si/numbersearch-disabled.gif", null);
/* 55 */     this.iconFullSearch = new IconElement("pic/si/textsearch-disabled.gif", null);
/* 56 */     this.iconBookmarks = new IconElement("pic/si/bookmarks-disabled.gif", null);
/*    */   }
/*    */   
/*    */   public static HomePanel getInstance(ClientContext context) {
/* 60 */     synchronized (context.getLockObject()) {
/* 61 */       HomePanel instance = (HomePanel)context.getObject(HomePanel.class);
/* 62 */       if (instance == null) {
/* 63 */         instance = new HomePanel(context);
/* 64 */         context.storeObject(HomePanel.class, instance);
/*    */       } 
/* 66 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 71 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 73 */     StringUtilities.replace(code, "{TEXT_HOME_TITLE}", this.context.getMessage("si.home.title"));
/* 74 */     StringUtilities.replace(code, "{LINK_STANDARD}", this.linkStandardInfo.getHtmlCode(params));
/* 75 */     StringUtilities.replace(code, "{TEXT_STANDARD}", this.context.getMessage("si.home.stdinfo"));
/* 76 */     StringUtilities.replace(code, "{LINK_BOOKMARKS}", this.iconBookmarks.getHtmlCode(params));
/* 77 */     StringUtilities.replace(code, "{TEXT_BOOKMARKS}", this.context.getMessage("si.home.bookmarks"));
/*    */     
/* 79 */     StringUtilities.replace(code, "{LINK_BULLETINS}", this.iconBulletins.getHtmlCode(params));
/* 80 */     StringUtilities.replace(code, "{TEXT_BULLETINS}", this.context.getMessage("si.home.bulletins"));
/*    */     
/* 82 */     StringUtilities.replace(code, "{LINK_BROCHURES}", this.iconBrochures.getHtmlCode(params));
/* 83 */     StringUtilities.replace(code, "{TEXT_BROCHURES}", this.context.getMessage("si.home.brochures"));
/*    */     
/* 85 */     StringUtilities.replace(code, "{LINK_NUMBER_SEARCH}", this.iconNumberSearch.getHtmlCode(params));
/* 86 */     StringUtilities.replace(code, "{TEXT_NUMBER_SEARCH}", this.context.getMessage("si.home.number.search"));
/*    */     
/* 88 */     StringUtilities.replace(code, "{LINK_FULL_SEARCH}", this.iconFullSearch.getHtmlCode(params));
/* 89 */     StringUtilities.replace(code, "{TEXT_FULL_SEARCH}", this.context.getMessage("si.home.text.search"));
/*    */     
/* 91 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcon\\ui\html\home\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */