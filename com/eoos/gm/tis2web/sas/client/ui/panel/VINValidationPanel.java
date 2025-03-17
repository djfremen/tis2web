/*     */ package com.eoos.gm.tis2web.sas.client.ui.panel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sas.client.system.SASClientContext;
/*     */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
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
/*     */ public class VINValidationPanel
/*     */   extends PanelBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Callback callback;
/*     */   
/*     */   public static class CallbackAdapter
/*     */     implements Callback
/*     */   {
/*     */     private SASClientContext context;
/*     */     private VIN vin;
/*     */     
/*     */     public CallbackAdapter(SASClientContext context, VIN vin) {
/*  35 */       this.context = context;
/*  36 */       this.vin = vin;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMessage(String key) {
/*  41 */       return this.context.getMessage(key);
/*     */     }
/*     */     
/*     */     public String getLabel(String key) {
/*  45 */       return this.context.getLabel(key);
/*     */     }
/*     */     
/*     */     public VIN getVIN() {
/*  49 */       return this.vin;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VINValidationPanel(Callback callback) {
/*  58 */     this.callback = callback;
/*  59 */     createPanels();
/*     */   }
/*     */   
/*     */   protected JPanel createMainPanel() {
/*  63 */     JPanel panel = new JPanel();
/*     */ 
/*     */     
/*  66 */     GridBagLayout layout = new GridBagLayout();
/*  67 */     panel.setLayout(layout);
/*  68 */     GridBagConstraints constraints = null;
/*     */ 
/*     */ 
/*     */     
/*  72 */     constraints = new GridBagConstraints();
/*  73 */     constraints.fill = 0;
/*  74 */     constraints.anchor = 17;
/*  75 */     constraints.weightx = 1.0D;
/*     */     
/*  77 */     JLabel label = new JLabel(this.callback.getMessage("please.validate.vin"));
/*  78 */     panel.add(label, constraints);
/*     */ 
/*     */     
/*  81 */     constraints = new GridBagConstraints();
/*  82 */     constraints.gridx = 0;
/*  83 */     constraints.gridy = 1;
/*  84 */     constraints.weightx = 1.0D;
/*     */     
/*  86 */     constraints.fill = 2;
/*     */     
/*  88 */     panel.add(createVINDisplayPanel(), constraints);
/*     */     
/*  90 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel createVINDisplayPanel() {
/*  94 */     JPanel panel = new JPanel();
/*     */ 
/*     */     
/*  97 */     GridBagLayout layout = new GridBagLayout();
/*  98 */     panel.setLayout(layout);
/*  99 */     GridBagConstraints constraints = null;
/*     */ 
/*     */ 
/*     */     
/* 103 */     constraints = new GridBagConstraints();
/* 104 */     constraints.weightx = 0.0D;
/*     */     
/* 106 */     JLabel label = new JLabel(this.callback.getLabel("vin") + ":");
/* 107 */     panel.add(label, constraints);
/*     */ 
/*     */     
/* 110 */     constraints = new GridBagConstraints();
/* 111 */     constraints.weightx = 1.0D;
/* 112 */     constraints.fill = 2;
/* 113 */     constraints.insets = new Insets(5, 10, 5, 0);
/*     */     
/* 115 */     JTextField textField = new JTextField(String.valueOf(this.callback.getVIN()));
/* 116 */     textField.setEditable(false);
/* 117 */     panel.add(textField, constraints);
/*     */     
/* 119 */     return panel;
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     String getMessage(String param1String);
/*     */     
/*     */     String getLabel(String param1String);
/*     */     
/*     */     VIN getVIN();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\panel\VINValidationPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */