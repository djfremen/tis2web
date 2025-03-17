/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.log4j.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Appender;
/*    */ import org.apache.log4j.Layout;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PatternLayout;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PatternInputElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(PatternInputElement.class);
/*    */   
/*    */   private Appender appender;
/*    */   
/* 25 */   private TextInputElement inputPattern = null;
/*    */   
/* 27 */   private ClickButtonElement buttonApply = null;
/*    */   
/* 29 */   private Exception e = null;
/*    */   
/*    */   public PatternInputElement(final ClientContext context, Appender appender) throws IllegalArgumentException {
/* 32 */     this.appender = appender;
/*    */     
/* 34 */     Layout layout = appender.getLayout();
/* 35 */     if (!(layout instanceof PatternLayout)) {
/* 36 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 39 */     this.inputPattern = new TextInputElement(ApplicationContext.getInstance().createID(), 30, -1);
/* 40 */     addElement((HtmlElement)this.inputPattern);
/* 41 */     this.inputPattern.setValue(((PatternLayout)layout).getConversionPattern());
/*    */     
/* 43 */     this.buttonApply = new ClickButtonElement(ApplicationContext.getInstance().createID(), null)
/*    */       {
/*    */         protected String getLabel() {
/* 46 */           return context.getLabel("apply");
/*    */         }
/*    */         
/*    */         public Object onClick(Map submitParams) {
/*    */           try {
/* 51 */             String pattern = String.valueOf(PatternInputElement.this.inputPattern.getValue());
/* 52 */             PatternInputElement.this.appender.setLayout((Layout)new PatternLayout(pattern));
/* 53 */             PatternInputElement.this.e = null;
/* 54 */           } catch (Exception e) {
/* 55 */             PatternInputElement.this.e = e;
/* 56 */             PatternInputElement.log.error("unable to set layout pattern - exception:" + e, e);
/*    */           } 
/* 58 */           return null;
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 63 */     addElement((HtmlElement)this.buttonApply);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 67 */     StringBuffer tmp = new StringBuffer();
/* 68 */     tmp.append("<table>{ERROR_INDICATION}<tr><td>{INPUT}</td><td>{BUTTON}</td></tr></table>");
/* 69 */     String errorMsg = (this.e != null) ? ("<tr><td colspan=\"2\" class=\"important\">Unable to set pattern (" + this.e.getLocalizedMessage() + ")</td></tr>") : "";
/* 70 */     StringUtilities.replace(tmp, "{ERROR_INDICATION}", errorMsg);
/* 71 */     StringUtilities.replace(tmp, "{INPUT}", this.inputPattern.getHtmlCode(params));
/* 72 */     StringUtilities.replace(tmp, "{BUTTON}", this.buttonApply.getHtmlCode(params));
/* 73 */     return tmp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\log4\\ui\html\main\PatternInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */