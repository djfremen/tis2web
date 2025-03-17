/*     */ package com.eoos.gm.tis2web.sas.client.ui.panel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.ListCellRenderer_Denotation;
/*     */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.ListCellRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolSelectionPanel
/*     */   extends PanelBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Callback callback;
/*     */   private List tools;
/*  37 */   private Tool currentSelection = null;
/*     */ 
/*     */   
/*     */   public ToolSelectionPanel(Callback callback, List tools) {
/*  41 */     this.callback = callback;
/*  42 */     this.tools = (tools != null) ? tools : Collections.EMPTY_LIST;
/*  43 */     createPanels();
/*     */   }
/*     */   
/*     */   protected JPanel createMainPanel() {
/*  47 */     JPanel panel = new JPanel();
/*     */ 
/*     */     
/*  50 */     GridBagLayout layout = new GridBagLayout();
/*  51 */     panel.setLayout(layout);
/*  52 */     GridBagConstraints constraints = null;
/*     */ 
/*     */ 
/*     */     
/*  56 */     constraints = new GridBagConstraints();
/*  57 */     constraints.fill = 0;
/*  58 */     constraints.anchor = 17;
/*  59 */     constraints.weightx = 1.0D;
/*     */     
/*  61 */     JLabel label = new JLabel(this.callback.getMessage("please.select.tool"));
/*  62 */     panel.add(label, constraints);
/*     */ 
/*     */     
/*  65 */     constraints = new GridBagConstraints();
/*  66 */     constraints.gridx = 0;
/*  67 */     constraints.gridy = 1;
/*  68 */     constraints.weightx = 1.0D;
/*     */     
/*  70 */     constraints.fill = 2;
/*     */     
/*  72 */     panel.add(createSelectorPanel(), constraints);
/*     */     
/*  74 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel createSelectorPanel() {
/*  78 */     JPanel panel = new JPanel();
/*     */ 
/*     */     
/*  81 */     GridBagLayout layout = new GridBagLayout();
/*  82 */     panel.setLayout(layout);
/*  83 */     GridBagConstraints constraints = null;
/*     */ 
/*     */ 
/*     */     
/*  87 */     constraints = new GridBagConstraints();
/*  88 */     constraints.weightx = 0.0D;
/*     */     
/*  90 */     JLabel label = new JLabel(this.callback.getLabel("tool") + ": ");
/*  91 */     panel.add(label, constraints);
/*     */ 
/*     */     
/*  94 */     constraints = new GridBagConstraints();
/*  95 */     constraints.weightx = 1.0D;
/*  96 */     constraints.fill = 2;
/*  97 */     constraints.insets = new Insets(5, 10, 5, 0);
/*     */     
/*  99 */     final JComboBox cbToolSelection = new JComboBox(this.tools.toArray());
/* 100 */     ListCellRenderer renderer = cbToolSelection.getRenderer();
/* 101 */     cbToolSelection.setRenderer((ListCellRenderer)new ListCellRenderer_Denotation(renderer));
/* 102 */     cbToolSelection.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 104 */             Tool tool = (Tool)cbToolSelection.getSelectedItem();
/* 105 */             ToolSelectionPanel.this.onSelectionChanged(tool);
/*     */           }
/*     */         });
/* 108 */     onSelectionChanged((Tool)cbToolSelection.getSelectedItem());
/*     */     
/* 110 */     panel.add(cbToolSelection, constraints);
/*     */     
/* 112 */     return panel;
/*     */   }
/*     */   
/*     */   private void onSelectionChanged(Tool tool) {
/* 116 */     this.currentSelection = tool;
/* 117 */     this.callback.onSelectionChanged(tool);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tool getSelectedTool() {
/* 122 */     return this.currentSelection;
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     String getMessage(String param1String);
/*     */     
/*     */     String getLabel(String param1String);
/*     */     
/*     */     void onSelectionChanged(Tool param1Tool);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\panel\ToolSelectionPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */