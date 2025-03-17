/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.contenttree.panel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.help.implementation.datamodel.domain.toc.Node;
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
/*    */ public class IDLabelElement
/*    */   extends LabelElement
/*    */ {
/*    */   private String id;
/*    */   
/*    */   public IDLabelElement(ClientContext context, HelpTOCTreeElement treeElement, Node node, String idPref) {
/* 24 */     super(context, treeElement, node);
/* 25 */     this.id = idPref + ":" + this.parameterName;
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributes(Map<String, String> map) {
/* 29 */     super.getAdditionalAttributes(map);
/* 30 */     map.put("id", this.id);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\contenttree\panel\IDLabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */