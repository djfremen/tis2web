/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.element.input.tree.TreeControl;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LabelElement
/*    */   extends LinkElement
/*    */ {
/*    */   protected ClientContext context;
/*    */   protected TreeControl control;
/*    */   protected Object node;
/*    */   
/*    */   public LabelElement(ClientContext context, TreeControl control, Object node) {
/* 20 */     super(context.createID(), "_top");
/* 21 */     this.context = context;
/* 22 */     this.control = control;
/* 23 */     this.node = node;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 28 */     this.control.setSelectedNode(this.node);
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 33 */     String retValue = null;
/* 34 */     Part part = (Part)this.node;
/* 35 */     retValue = part.getPartNumber();
/* 36 */     if (part.getShortDescription(this.context.getLocale()) != null) {
/* 37 */       retValue = retValue + " - " + part.getShortDescription(this.context.getLocale());
/*    */     }
/* 39 */     return retValue;
/*    */   }
/*    */   
/*    */   public boolean isDisabled() {
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\LabelElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */