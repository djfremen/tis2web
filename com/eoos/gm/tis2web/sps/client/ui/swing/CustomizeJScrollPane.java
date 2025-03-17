/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.awt.Color;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.JScrollPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomizeJScrollPane
/*    */   extends JScrollPane
/*    */   implements AttributeInput, ValueRetrieval
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 22 */   protected CustomizeJTable table = null;
/*    */   
/* 24 */   protected JComponent component = null;
/*    */   
/*    */   public CustomizeJScrollPane(JComponent component) {
/* 27 */     super(component);
/* 28 */     this.component = component;
/* 29 */     getViewport().setBackground(Color.white);
/* 30 */     getViewport().setOpaque(true);
/*    */   }
/*    */   
/*    */   public Attribute getAttribute() {
/* 34 */     if (this.component instanceof AttributeInput) {
/* 35 */       return ((AttributeInput)this.component).getAttribute();
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public Value getValue(Attribute attr) {
/* 41 */     if (this.component instanceof ValueRetrieval) {
/* 42 */       return ((ValueRetrieval)this.component).getValue(attr);
/*    */     }
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\CustomizeJScrollPane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */