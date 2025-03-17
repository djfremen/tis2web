/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementHook;
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
/*    */ 
/*    */ public class StdInfoContentHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private SplitContentPanel splitContent;
/*    */   private FlatContentHook flatContent;
/*    */   public static final int SPLIT_VIEW = 0;
/*    */   public static final int FLAT_VIEW = 1;
/* 27 */   private int activeView = 0;
/*    */ 
/*    */   
/*    */   public StdInfoContentHook(ClientContext context) {
/* 31 */     this.splitContent = new SplitContentPanel(context);
/* 32 */     addElement((HtmlElement)this.splitContent);
/*    */     
/* 34 */     this.flatContent = new FlatContentHook(context);
/* 35 */     addElement((HtmlElement)this.flatContent);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 42 */     switch (this.activeView) {
/*    */       case 0:
/* 44 */         return (HtmlElement)this.splitContent;
/*    */       case 1:
/* 46 */         return (HtmlElement)this.flatContent;
/*    */     } 
/* 48 */     throw new IllegalArgumentException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void toggleView() {
/* 53 */     this.activeView = (this.activeView == 0) ? 1 : 0;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\StdInfoContentHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */