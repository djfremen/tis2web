/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TocPanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(TocPanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 26 */       template = ApplicationContext.getInstance().loadFile(TocPanel.class, "tocpanel.html", null).toString();
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to load template - error:" + e, e);
/* 29 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private TocTreeElement treeElement;
/*    */   
/*    */   private SITFilterWidget widgetSITFilter;
/*    */   
/*    */   public TocPanel(ClientContext context, int tocMode) {
/* 39 */     TocTree tree = TocTree.getInstance(context);
/*    */     
/* 41 */     this.widgetSITFilter = new SITFilterWidget(context);
/* 42 */     addElement((HtmlElement)this.widgetSITFilter);
/*    */     
/* 44 */     this.treeElement = new TocTreeElement(context, tocMode, tree);
/* 45 */     addElement((HtmlElement)this.treeElement);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 50 */     StringBuffer code = new StringBuffer(template);
/* 51 */     StringUtilities.replace(code, "{SIT_FILTER}", this.widgetSITFilter.getHtmlCode(params));
/* 52 */     StringUtilities.replace(code, "{TREE}", this.treeElement.getHtmlCode(params));
/*    */     
/* 54 */     return code.toString();
/*    */   }
/*    */   
/*    */   public void toggleNavigationMode() {
/* 58 */     this.treeElement.toggleMode();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\TocPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */