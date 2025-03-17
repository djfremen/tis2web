/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.PanelController;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSOption;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomizeJComboBox
/*     */   extends JComboBox
/*     */   implements AttributeInput, ValueRetrieval
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private AssignmentRequest dataReq;
/*     */   private PanelController panelController;
/*     */   
/*     */   public CustomizeJComboBox(PanelController pnlControl, Request dataRequest) {
/*  40 */     super((E[])DisplayUtil.createDisplayAdapter(((SelectionRequest)dataRequest).getOptions()).toArray());
/*  41 */     this.panelController = pnlControl;
/*  42 */     this.dataReq = (AssignmentRequest)dataRequest;
/*  43 */     initialize();
/*  44 */     if (dataRequest instanceof DefaultValueRetrieval) {
/*  45 */       Object defaultValue = ((DefaultValueRetrieval)this.dataReq).getDefaultValue();
/*  46 */       if (defaultValue != null) {
/*  47 */         setSelectedItem(new DisplayAdapter(defaultValue));
/*     */       } else {
/*  49 */         setSelectedItem((Object)null);
/*     */       } 
/*     */     } else {
/*  52 */       setSelectedItem((Object)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  58 */     addItemListener(new ItemListener() {
/*     */           public void itemStateChanged(ItemEvent e) {
/*  60 */             CustomizeJComboBox.this.comboBox_itemStateChanged(e);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void comboBox_itemStateChanged(ItemEvent e) {
/*  71 */     if (isPopupVisible() && 
/*  72 */       e.getStateChange() == 1) {
/*  73 */       onSelectedItemAction(e);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showPopupList() {
/*  83 */     Runnable r = new Runnable() {
/*     */         public void run() {
/*  85 */           if (CustomizeJComboBox.this.getSelectedIndex() == -1 && CustomizeJComboBox.this.getItemCount() != 0 && 
/*  86 */             CustomizeJComboBox.this.isShowing()) {
/*  87 */             CustomizeJComboBox.this.showPopup();
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*  92 */     SwingUtilities.invokeLater(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute getAttribute() {
/*  99 */     return this.dataReq.getAttribute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getValues() {
/* 106 */     return ((SelectionRequest)this.dataReq).getOptions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Value getValue(Attribute attr) {
/* 113 */     DisplayAdapter selected = (DisplayAdapter)getSelectedItem();
/* 114 */     if (selected == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     return (Value)selected.getAdaptee();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onSelectedItemAction(ItemEvent e) {
/* 128 */     this.panelController.handleComboSelection((JComponent)e.getSource());
/*     */ 
/*     */ 
/*     */     
/* 132 */     DisplayAdapter selected = (DisplayAdapter)getSelectedItem();
/*     */     
/* 134 */     this.panelController.handleSelection(getAttribute(), (Value)selected.getAdaptee());
/*     */   }
/*     */   
/*     */   public static class SPSOptionSort
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2) {
/* 141 */       return ((SPSOption)o1).getDenotation(Locale.US).compareTo(((SPSOption)o2).getDenotation(Locale.US));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\CustomizeJComboBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */