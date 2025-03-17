/*     */ package com.eoos.gm.tis2web.client.util.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.client.util.DeviceSettings;
/*     */ import com.eoos.gm.tis2web.client.util.DeviceSettingsRI;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviceConnectionSettingsPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   private static final Logger log = Logger.getLogger(DeviceConnectionSettingsPanel.class);
/*     */ 
/*     */   
/*     */   private Callback callback;
/*     */ 
/*     */   
/*     */   private ButtonGroup buttonGroup;
/*     */ 
/*     */   
/*     */   private JRadioButton selectionAuto;
/*     */ 
/*     */   
/*     */   private JRadioButton selectionManual;
/*     */ 
/*     */   
/*     */   private JComboBox selectionPort;
/*     */ 
/*     */   
/*     */   private JComboBox selectionRate;
/*     */ 
/*     */ 
/*     */   
/*     */   private DeviceConnectionSettingsPanel(Callback callback) {
/*  55 */     this.callback = callback;
/*  56 */     initComponents();
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*  60 */     setLayout(new GridBagLayout());
/*     */     
/*  62 */     this.buttonGroup = new ButtonGroup();
/*     */     
/*  64 */     this.selectionAuto = new JRadioButton();
/*  65 */     this.buttonGroup.add(this.selectionAuto);
/*     */     
/*  67 */     this.selectionManual = new JRadioButton();
/*  68 */     this.buttonGroup.add(this.selectionManual);
/*     */     
/*  70 */     this.selectionPort = new JComboBox(this.callback.getPorts().toArray());
/*     */     
/*  72 */     this.selectionRate = new JComboBox(this.callback.getBaudrates().toArray());
/*     */     
/*  74 */     ChangeListener listener = new ChangeListener()
/*     */       {
/*     */         public void stateChanged(ChangeEvent e) {
/*  77 */           DeviceConnectionSettingsPanel.this.selectionPort.setEnabled(DeviceConnectionSettingsPanel.this.selectionManual.isSelected());
/*  78 */           DeviceConnectionSettingsPanel.this.selectionRate.setEnabled(DeviceConnectionSettingsPanel.this.selectionManual.isSelected());
/*     */         }
/*     */       };
/*     */     
/*  82 */     this.selectionAuto.addChangeListener(listener);
/*  83 */     this.selectionManual.addChangeListener(listener);
/*     */     
/*  85 */     DeviceSettings defaults = this.callback.getDefaults();
/*  86 */     if (defaults == null) {
/*  87 */       this.selectionAuto.setSelected(true);
/*     */     } else {
/*  89 */       this.selectionManual.setSelected(true);
/*  90 */       this.selectionPort.setSelectedItem(defaults.getPort());
/*  91 */       this.selectionRate.setSelectedItem(defaults.getBaudrate());
/*     */     } 
/*     */     
/*  94 */     GridBagConstraints position = new GridBagConstraints();
/*  95 */     position.gridx = 0;
/*  96 */     position.gridy = 0;
/*     */     
/*  98 */     Insets insets = new Insets(2, 5, 2, 5);
/*  99 */     GridBagConstraints c = new GridBagConstraints();
/* 100 */     c.insets = insets;
/* 101 */     add(this.selectionAuto, UIUtil.mergeContraints(position, c));
/*     */     
/* 103 */     position = UIUtil.getRelativePosition(position, 1, 0);
/* 104 */     c = new GridBagConstraints();
/* 105 */     c.anchor = 17;
/* 106 */     c.insets = insets;
/* 107 */     final JLabel labelAuto = new JLabel(this.callback.getLabel("automatic"));
/* 108 */     add(labelAuto, UIUtil.mergeContraints(position, c));
/*     */     
/* 110 */     position = UIUtil.getRelativePosition(position, -1, 1);
/* 111 */     c = new GridBagConstraints();
/* 112 */     c.insets = insets;
/* 113 */     add(this.selectionManual, UIUtil.mergeContraints(position, c));
/*     */     
/* 115 */     position = UIUtil.getRelativePosition(position, 1, 0);
/* 116 */     c = new GridBagConstraints();
/* 117 */     c.anchor = 17;
/* 118 */     c.insets = insets;
/* 119 */     final JLabel labelManual = new JLabel(this.callback.getLabel("manual"));
/* 120 */     add(labelManual, UIUtil.mergeContraints(position, c));
/*     */     
/* 122 */     position = UIUtil.getRelativePosition(position, 0, 1);
/* 123 */     c = new GridBagConstraints();
/* 124 */     c.fill = 2;
/* 125 */     c.insets = insets;
/* 126 */     add(new JLabel(this.callback.getLabel("port") + ":", 4), UIUtil.mergeContraints(position, c));
/*     */     
/* 128 */     position = UIUtil.getRelativePosition(position, 1, 0);
/* 129 */     c = new GridBagConstraints();
/* 130 */     c.fill = 2;
/* 131 */     c.weightx = 1.0D;
/* 132 */     c.insets = insets;
/* 133 */     add(this.selectionPort, UIUtil.mergeContraints(position, c));
/*     */     
/* 135 */     position = UIUtil.getRelativePosition(position, -1, 1);
/* 136 */     c = new GridBagConstraints();
/*     */     
/* 138 */     c.insets = insets;
/* 139 */     add(new JLabel(this.callback.getLabel("transfer.rate") + ":", 4), UIUtil.mergeContraints(position, c));
/*     */     
/* 141 */     position = UIUtil.getRelativePosition(position, 1, 0);
/* 142 */     c = new GridBagConstraints();
/* 143 */     c.fill = 2;
/* 144 */     c.weightx = 1.0D;
/* 145 */     c.insets = insets;
/* 146 */     add(this.selectionRate, UIUtil.mergeContraints(position, c));
/*     */     
/* 148 */     addMouseListener(new MouseAdapter() {
/*     */           public void mouseClicked(MouseEvent e) {
/* 150 */             Component component = DeviceConnectionSettingsPanel.this.getComponentAt(e.getPoint());
/* 151 */             if (labelAuto == component) {
/* 152 */               DeviceConnectionSettingsPanel.this.selectionAuto.setSelected(true);
/* 153 */             } else if (labelManual == component) {
/* 154 */               DeviceConnectionSettingsPanel.this.selectionManual.setSelected(true);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DeviceConnectionSettingsPanel create(Callback callback) {
/* 163 */     return new DeviceConnectionSettingsPanel(callback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceSettings getSettings() {
/* 173 */     if (this.selectionAuto.isSelected()) {
/* 174 */       return null;
/*     */     }
/* 176 */     Object port = this.selectionPort.getSelectedItem();
/* 177 */     Object rate = this.selectionRate.getSelectedItem();
/* 178 */     return (DeviceSettings)new DeviceSettingsRI(port, rate);
/*     */   } public static interface Callback {
/*     */     List getPorts();
/*     */     List getBaudrates();
/*     */     String getLabel(String param1String);
/*     */     DeviceSettings getDefaults(); }
/*     */   public static void main(String[] args) throws Throwable {
/* 185 */     Log4jUtil.attachConsoleAppender();
/*     */     try {
/* 187 */       final DeviceConnectionSettingsPanel[] panel = new DeviceConnectionSettingsPanel[1];
/*     */       
/* 189 */       SwingUtilities.invokeAndWait(new Runnable()
/*     */           {
/*     */             public void run() {
/* 192 */               panel[0] = DeviceConnectionSettingsPanel.create(new DeviceConnectionSettingsPanel.Callback()
/*     */                   {
/*     */                     public List getPorts() {
/* 195 */                       return Arrays.asList(new String[] { "COM1", "COM2" });
/*     */                     }
/*     */                     
/*     */                     public String getLabel(String key) {
/* 199 */                       return key;
/*     */                     }
/*     */                     
/*     */                     public List getBaudrates() {
/* 203 */                       return Arrays.asList(new String[] { "9600", "1500" });
/*     */                     }
/*     */                     
/*     */                     public DeviceSettings getDefaults() {
/* 207 */                       return null;
/*     */                     }
/*     */                   });
/*     */             }
/*     */           });
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
/* 222 */       Thread t = Util.createAndStartThread(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */               try {
/*     */                 while (true) {
/* 227 */                   DeviceConnectionSettingsPanel.log.debug(panel[0].getSettings());
/* 228 */                   Thread.sleep(1000L);
/*     */                 } 
/* 230 */               } catch (InterruptedException e) {
/* 231 */                 Thread.currentThread().interrupt();
/*     */                 return;
/*     */               } 
/*     */             }
/*     */           });
/* 236 */       UIUtil.testPanel(panel[0], false);
/* 237 */       t.interrupt();
/*     */     } finally {
/* 239 */       System.exit(0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\clien\\uti\\ui\DeviceConnectionSettingsPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */