/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ 
/*    */ 
/*    */ public class HomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 23 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 28 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "homepanel.html", null).toString();
/* 29 */     } catch (Exception e) {
/* 30 */       log.error("unable to load template - error:" + e, e);
/* 31 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   protected TOCIFrameElement tocIFrameElement;
/*    */   
/*    */   protected DocumentIFrameElement documentIFrameElement;
/*    */   
/*    */   protected RatioSelection ieRatio;
/*    */   
/*    */   public HomePanel(ClientContext context, Map parameter) {
/* 45 */     this.context = context;
/*    */     
/* 47 */     this.documentIFrameElement = new DocumentIFrameElement(context);
/* 48 */     addElement((HtmlElement)this.documentIFrameElement);
/*    */     
/* 50 */     this.tocIFrameElement = new TOCIFrameElement(context, parameter, this.documentIFrameElement.getDocumentPage());
/* 51 */     addElement((HtmlElement)this.tocIFrameElement);
/*    */     
/* 53 */     this.ieRatio = new RatioSelection(context);
/* 54 */     addElement((HtmlElement)this.ieRatio);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 59 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 61 */     StringUtilities.replace(code, "{TITLE}", this.context.getLabel("help"));
/* 62 */     StringUtilities.replace(code, "{TOCIFRAME}", this.tocIFrameElement.getHtmlCode(params));
/* 63 */     StringUtilities.replace(code, "{DOCUMENTIFRAME}", this.documentIFrameElement.getHtmlCode(params));
/* 64 */     StringUtilities.replace(code, "{LABEL_RATIO_SELECTION}", this.context.getMessage("select.ratio"));
/* 65 */     StringUtilities.replace(code, "{RATIO_SELECTION}", this.ieRatio.getHtmlCode(params));
/*    */     
/* 67 */     Pair ratio = (Pair)this.ieRatio.getValue();
/* 68 */     StringUtilities.replace(code, "{RATIO_1}", (String)ratio.getFirst());
/* 69 */     StringUtilities.replace(code, "{RATIO_2}", (String)ratio.getSecond());
/*    */     
/* 71 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\home\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */