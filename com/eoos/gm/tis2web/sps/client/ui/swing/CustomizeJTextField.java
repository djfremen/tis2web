/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InputRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomizeJTextField
/*    */   extends JTextField
/*    */   implements AttributeInput, ValueRetrieval
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 21 */   InputRequest dataReq = null;
/* 22 */   protected double realValue = 0.0D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRequest(InputRequest dataRequest) {
/* 30 */     this.dataReq = dataRequest;
/* 31 */     if (dataRequest instanceof DefaultValueRetrieval) {
/* 32 */       Value defaultValue = ((DefaultValueRetrieval)dataRequest).getDefaultValue();
/* 33 */       if (defaultValue != null) {
/* 34 */         DisplayAdapter valueDisplay = new DisplayAdapter(defaultValue);
/* 35 */         if (valueDisplay.toString() != null) {
/* 36 */           setText(valueDisplay.toString());
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Attribute getAttribute() {
/* 44 */     return this.dataReq.getAttribute();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Value getValue(Attribute attr) {
/* 56 */     if (getText() == null) {
/* 57 */       return null;
/*    */     }
/* 59 */     if (this.realValue != 0.0D)
/*    */     {
/* 61 */       return (Value)new ValueAdapter(String.valueOf((int)this.realValue));
/*    */     }
/*    */     
/* 64 */     return (Value)new ValueAdapter(getText());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRealValue(double value) {
/* 70 */     this.realValue = value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\CustomizeJTextField.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */