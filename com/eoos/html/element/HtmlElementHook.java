/*    */ package com.eoos.html.element;
/*    */ 
/*    */ import com.eoos.html.element.input.HtmlInputElement;
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
/*    */ public abstract class HtmlElementHook
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(HtmlElementHook.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized String getHtmlCode(Map params) {
/* 24 */     return _getActiveElement().getHtmlCode(params);
/*    */   }
/*    */   
/*    */   private HtmlElement _getActiveElement() {
/* 28 */     HtmlElement element = getActiveElement();
/* 29 */     if (!this.elements.contains(element)) {
/* 30 */       if (element.getContainer() != null) {
/* 31 */         log.error("cannot add element: " + String.valueOf(element) + "to this container:" + String.valueOf(this) + ", because it has already been added to container:" + element.getContainer() + ", throwing IllegalStateException");
/* 32 */         throw new IllegalStateException("element has already been added to another container");
/*    */       } 
/* 34 */       addElement(element);
/*    */     } 
/*    */     
/* 37 */     return element;
/*    */   }
/*    */   
/*    */   protected abstract HtmlElement getActiveElement();
/*    */   
/*    */   public boolean clicked() {
/* 43 */     HtmlElement element = _getActiveElement();
/* 44 */     if (element instanceof HtmlInputElement) {
/* 45 */       HtmlInputElement inputElement = (HtmlInputElement)element;
/* 46 */       if (inputElement.clicked()) {
/* 47 */         return true;
/*    */       }
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 54 */     HtmlElement element = _getActiveElement();
/* 55 */     if (element instanceof HtmlInputElement) {
/* 56 */       HtmlInputElement inputElement = (HtmlInputElement)element;
/* 57 */       if (inputElement.clicked()) {
/* 58 */         return inputElement.onClick(submitParams);
/*    */       }
/*    */     } 
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public void setValue(Map submitParams) {
/* 65 */     HtmlElement element = _getActiveElement();
/* 66 */     if (element instanceof HtmlInputElement)
/* 67 */       ((HtmlInputElement)element).setValue(submitParams); 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlElementHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */