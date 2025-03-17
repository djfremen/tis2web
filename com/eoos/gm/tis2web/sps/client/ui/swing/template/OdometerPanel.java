/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJTextField;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InputRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OdometerPanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final String KILOMETER = "KM";
/*     */   public static final String MILES = "M";
/*  45 */   protected JPanel titlePanel = null;
/*     */   
/*  47 */   protected JPanel messagePanel = null;
/*     */   
/*  49 */   protected JPanel inputPanel = null;
/*     */   
/*  51 */   protected JPanel unitsPanel = null;
/*     */   
/*  53 */   protected JPanel centerPanel = null;
/*     */   
/*  55 */   protected CustomizeJTextField input = null;
/*     */   
/*  57 */   protected static Locale locale = null;
/*     */   
/*  59 */   protected JRadioButton mileRadioButton = null;
/*     */   
/*  61 */   protected JRadioButton kilometerRadioButton = null;
/*     */   
/*  63 */   protected ButtonGroup buttonGroup = null;
/*     */   
/*  65 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  67 */   protected static final Logger log = Logger.getLogger(OdometerPanel.class);
/*     */   
/*  69 */   protected String unit = "M";
/*  70 */   protected double valueReal = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OdometerPanel(BaseCustomizeJPanel prevPanel) {
/*  76 */     super(prevPanel);
/*  77 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  86 */     setLayout(new BorderLayout());
/*  87 */     add(getTitlePanel(), "North");
/*  88 */     add(getCenterPanel(), "Center");
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel getTitlePanel() {
/*  93 */     if (this.titlePanel == null) {
/*     */       try {
/*  95 */         this.titlePanel = new JPanel();
/*  96 */         this.titlePanel.setLayout(new GridBagLayout());
/*  97 */         this.titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
/*  98 */         GridBagConstraints northConstraints = new GridBagConstraints();
/*  99 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/* 100 */         northConstraints.gridx = 1;
/* 101 */         northConstraints.gridy = 2;
/* 102 */         JLabel titleLabel = new JLabel(resourceProvider.getLabel(locale, "odometerScreen.title"));
/* 103 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 104 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/* 105 */         this.titlePanel.add(titleLabel, northConstraints);
/*     */       }
/* 107 */       catch (Throwable e) {
/* 108 */         log.error("getTitlePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 111 */     return this.titlePanel;
/*     */   }
/*     */   
/*     */   private JPanel getCenterPanel() {
/*     */     try {
/* 116 */       this.centerPanel = new JPanel(new GridBagLayout());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       this.centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
/* 123 */       GridBagConstraints c = new GridBagConstraints();
/* 124 */       c.gridx = 0;
/* 125 */       c.gridy = 0;
/* 126 */       c.gridheight = 1;
/* 127 */       c.gridwidth = 6;
/* 128 */       c.fill = 2;
/* 129 */       this.centerPanel.add(getMessagePanel(), c);
/*     */       
/* 131 */       c.gridx = 0;
/* 132 */       c.gridy = 1;
/* 133 */       c.gridheight = 1;
/* 134 */       c.gridwidth = 3;
/* 135 */       this.centerPanel.add(getInputPanel(), c);
/*     */       
/* 137 */       c.gridx = 3;
/* 138 */       c.gridy = 1;
/* 139 */       c.gridheight = 1;
/* 140 */       c.gridwidth = 2;
/* 141 */       this.centerPanel.add(getUnitsPanel(), c);
/*     */       
/* 143 */       c.gridx = 0;
/* 144 */       c.gridy = 2;
/* 145 */       c.gridheight = 4;
/* 146 */       c.weighty = 1.0D;
/* 147 */       c.fill = 3;
/* 148 */       this.centerPanel.add(new JPanel(), c);
/*     */     }
/* 150 */     catch (Exception e) {
/* 151 */       log.error("getCenterPanel() method, -exception: " + e.getMessage());
/*     */     } 
/* 153 */     return this.centerPanel;
/*     */   }
/*     */   
/*     */   private JPanel getMessagePanel() {
/* 157 */     if (this.messagePanel == null) {
/*     */       try {
/* 159 */         this.messagePanel = new JPanel();
/* 160 */         this.messagePanel.setLayout(new BorderLayout());
/* 161 */         this.messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
/* 162 */         JTextArea mes = new JTextArea(resourceProvider.getMessage(locale, "odometerScreen.input"));
/* 163 */         mes.setPreferredSize(new Dimension(400, 150));
/* 164 */         this.messagePanel.add(mes, "Center");
/* 165 */         mes.setLineWrap(true);
/* 166 */         mes.setWrapStyleWord(true);
/* 167 */         mes.setBackground(getBackground());
/* 168 */         mes.setForeground(Color.black);
/* 169 */         mes.setEditable(false);
/*     */       }
/* 171 */       catch (Throwable e) {
/* 172 */         log.error("getMessagePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 175 */     return this.messagePanel;
/*     */   }
/*     */   
/*     */   private JPanel getInputPanel() {
/* 179 */     if (this.inputPanel == null) {
/*     */       try {
/* 181 */         this.inputPanel = new JPanel();
/* 182 */         this.inputPanel.setLayout(new BorderLayout());
/* 183 */         this.inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
/* 184 */         this.input = new CustomizeJTextField();
/* 185 */         this.input.addFocusListener(new FocusAdapter()
/*     */             {
/*     */               public void focusGained(FocusEvent e) {}
/*     */ 
/*     */ 
/*     */               
/*     */               public void focusLost(FocusEvent e) {
/*     */                 try {
/* 193 */                   OdometerPanel.this.valueReal = Double.parseDouble(OdometerPanel.this.input.getText());
/* 194 */                 } catch (NumberFormatException ex) {}
/*     */               }
/*     */             });
/*     */         
/* 198 */         this.input.setPreferredSize(new Dimension(200, 20));
/* 199 */         this.inputPanel.add((Component)this.input, "Center");
/*     */       }
/* 201 */       catch (Throwable e) {
/* 202 */         log.error("getInputPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 205 */     return this.inputPanel;
/*     */   }
/*     */   
/*     */   private JPanel getUnitsPanel() {
/* 209 */     if (this.unitsPanel == null) {
/*     */       try {
/* 211 */         this.unitsPanel = new JPanel();
/* 212 */         this.unitsPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null, Color.black));
/* 213 */         this.unitsPanel.setLayout(new GridLayout(0, 1));
/*     */         
/* 215 */         this.mileRadioButton = new JRadioButton(resourceProvider.getLabel(locale, "odometerScreen.mile"));
/* 216 */         this.mileRadioButton.setFont(new Font(getFont().getFontName(), 1, getFont().getSize()));
/* 217 */         this.kilometerRadioButton = new JRadioButton(resourceProvider.getLabel(locale, "odometerScreen.kilometer"));
/* 218 */         this.kilometerRadioButton.setFont(new Font(getFont().getFontName(), 1, getFont().getSize()));
/*     */ 
/*     */         
/* 221 */         this.buttonGroup = new ButtonGroup();
/* 222 */         this.buttonGroup.add(this.mileRadioButton);
/* 223 */         this.buttonGroup.add(this.kilometerRadioButton);
/* 224 */         ButtonModel model = this.mileRadioButton.getModel();
/* 225 */         this.buttonGroup.setSelected(model, true);
/*     */         
/* 227 */         this.mileRadioButton.addItemListener(new ItemListener() {
/*     */               public void itemStateChanged(ItemEvent e) {
/* 229 */                 if (e.getStateChange() == 1) {
/* 230 */                   OdometerPanel.this.mileActionPerformed();
/*     */                 }
/*     */               }
/*     */             });
/*     */         
/* 235 */         this.kilometerRadioButton.addItemListener(new ItemListener() {
/*     */               public void itemStateChanged(ItemEvent e) {
/* 237 */                 if (e.getStateChange() == 1) {
/* 238 */                   OdometerPanel.this.kilometerActionPerformed();
/*     */                 }
/*     */               }
/*     */             });
/*     */         
/* 243 */         this.unitsPanel.add(this.mileRadioButton);
/* 244 */         this.unitsPanel.add(this.kilometerRadioButton);
/*     */       }
/* 246 */       catch (Throwable e) {
/* 247 */         log.error("getUnitsPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 250 */     return this.unitsPanel;
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 255 */       setRequestGroup(req.getRequestGroup());
/* 256 */       this.input.setRequest((InputRequest)req);
/* 257 */       this.valueReal = Double.parseDouble(this.input.getText());
/* 258 */     } catch (Exception e) {
/* 259 */       System.out.println("Exception in handleRequest method:" + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onNextAction() {
/* 264 */     this.mileRadioButton.getModel();
/*     */ 
/*     */     
/*     */     try {
/* 268 */       double value = this.valueReal;
/*     */       
/* 270 */       if (this.unit == "KM") {
/* 271 */         this.valueReal *= 0.62D;
/*     */       }
/* 273 */       this.input.setRealValue(this.valueReal);
/* 274 */       if (value <= 0.0D || value > 419430.0D) {
/* 275 */         JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(locale, "odometerScreen.invalid-value"), "Exception", 0);
/* 276 */         this.input.requestFocus();
/* 277 */         return false;
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 284 */     catch (Exception e) {
/* 285 */       log.error("onNextAction() method, -exception: " + e.getMessage());
/*     */     } 
/* 287 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mileActionPerformed() {
/* 292 */     this.unit = "M";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void kilometerActionPerformed() {
/* 302 */     this.unit = "KM";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void displayFormattedInput(double value) {
/* 311 */     this.valueReal = value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     this.input.setText(String.valueOf((int)value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onOpenPanel() {
/* 321 */     Runnable doWorkRunnable = new Runnable() {
/*     */         public void run() {
/* 323 */           OdometerPanel.this.input.requestFocus();
/*     */         }
/*     */       };
/* 326 */     SwingUtilities.invokeLater(doWorkRunnable);
/* 327 */     this.input.requestFocus();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\OdometerPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */