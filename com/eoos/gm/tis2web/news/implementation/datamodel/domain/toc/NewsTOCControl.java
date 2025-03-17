/*    */ package com.eoos.gm.tis2web.news.implementation.datamodel.domain.toc;
/*    */ 
/*    */ import com.eoos.datatype.selection.SelectionControl;
/*    */ import com.eoos.datatype.selection.implementation.SelectionControlImpl;
/*    */ import com.eoos.datatype.tree.navigation.TreeNavigation2;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.html.element.input.tree.gtwo.implementation.TreeControlImpl;
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
/*    */ public class NewsTOCControl
/*    */   extends TreeControlImpl
/*    */ {
/*    */   protected ClientContext context;
/*    */   
/*    */   public NewsTOCControl(ClientContext context, TreeNavigation2 treeNavigation) {
/* 28 */     super(treeNavigation, (SelectionControl)new SelectionControlImpl(new NewsTOCSelectionSPIImpl()));
/* 29 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getIdentifier(Object node) {
/* 38 */     Node _node = (Node)node;
/* 39 */     if (node == null) {
/* 40 */       return "";
/*    */     }
/* 42 */     return (String)getIdentifier(_node.parent) + "." + String.valueOf(((Node)node).content.getID());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLabel(Object node) {
/* 52 */     String retValue = null;
/*    */     try {
/* 54 */       Node _node = (Node)node;
/* 55 */       retValue = _node.content.getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/* 56 */     } catch (Exception e) {
/* 57 */       retValue = null;
/*    */     } 
/* 59 */     return (retValue != null) ? retValue : "label not available";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\datamodel\domain\toc\NewsTOCControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */