/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist;
/*    */ 
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HTMLFloatLayoutElementContainer
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   public String getHtmlCode(Map map) {
/* 24 */     String o = new String();
/* 25 */     for (Iterator<HtmlElement> it = this.elements.iterator(); it.hasNext();) {
/* 26 */       o = o + ((HtmlElement)it.next()).getHtmlCode(map);
/*    */     }
/* 28 */     return o;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\ltlist\HTMLFloatLayoutElementContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */