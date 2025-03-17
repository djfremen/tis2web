/*    */ package com.eoos.gm.tis2web.help.implementation.datamodel.domain.toc;
/*    */ 
/*    */ import com.eoos.datatype.selection.implementation.SelectionControlSPI;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
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
/*    */ public class HelpTOCSelectionSPIImpl
/*    */   implements SelectionControlSPI
/*    */ {
/* 20 */   protected Node selectedNode = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addSelection(Object obj) {
/* 26 */     this.selectedNode = (Node)obj;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeSelection(Object obj) {
/* 36 */     this.selectedNode = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection getSelection() {
/* 46 */     Collection<Node> retValue = new ArrayList(1);
/* 47 */     if (this.selectedNode != null) {
/* 48 */       retValue.add(this.selectedNode);
/*    */     }
/* 50 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\datamodel\domain\toc\HelpTOCSelectionSPIImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */