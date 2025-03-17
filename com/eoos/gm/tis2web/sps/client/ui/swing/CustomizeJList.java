/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayUtil;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.util.List;
/*    */ import javax.swing.DefaultListModel;
/*    */ import javax.swing.JList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomizeJList
/*    */   extends JList
/*    */   implements AttributeInput, ValueRetrieval
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private SelectionRequest dataReq;
/*    */   
/*    */   public CustomizeJList() {
/* 29 */     initialize();
/*    */   }
/*    */   
/*    */   private void initialize() {
/* 33 */     setSelectionMode(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRequest(SelectionRequest dataRequest) {
/* 42 */     this.dataReq = dataRequest;
/* 43 */     setModel(getModel(false));
/*    */   }
/*    */   
/*    */   public void setRequest(SelectionRequest dataRequest, boolean notDisplayFirstElement) {
/* 47 */     this.dataReq = dataRequest;
/* 48 */     setModel(getModel(notDisplayFirstElement));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private DefaultListModel getModel(boolean notDisplayFirstElement) {
/* 59 */     Object[] values = DisplayUtil.createDisplayAdapter(this.dataReq.getOptions()).toArray();
/* 60 */     int length = values.length;
/* 61 */     int i = 0;
/* 62 */     if (notDisplayFirstElement)
/* 63 */       i = 1; 
/* 64 */     DefaultListModel<Object> defaultListModel = new DefaultListModel();
/* 65 */     for (; i < length; i++) {
/* 66 */       defaultListModel.addElement(values[i]);
/*    */     }
/* 68 */     return defaultListModel;
/*    */   }
/*    */   
/*    */   public Attribute getAttribute() {
/* 72 */     if (this.dataReq == null) {
/* 73 */       return null;
/*    */     }
/* 75 */     return this.dataReq.getAttribute();
/*    */   }
/*    */ 
/*    */   
/*    */   public List getValues() {
/* 80 */     return this.dataReq.getOptions();
/*    */   }
/*    */   
/*    */   public Value getValue(Attribute attr) {
/* 84 */     DisplayAdapter selected = (DisplayAdapter)getSelectedValue();
/* 85 */     if (selected == null) {
/* 86 */       return null;
/*    */     }
/* 88 */     return (Value)selected.getAdaptee();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\CustomizeJList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */