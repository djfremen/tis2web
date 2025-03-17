/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJTextField;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
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
/*     */ import java.awt.Insets;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyCodePanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  34 */   protected JPanel titlePanel = null;
/*     */   
/*  36 */   protected JPanel messagePanel = null;
/*     */   
/*  38 */   protected JPanel inputPanel = null;
/*     */   
/*  40 */   protected JPanel unitsPanel = null;
/*     */   
/*  42 */   protected JPanel centerPanel = null;
/*     */   
/*  44 */   protected CustomizeJTextField input = null;
/*     */   
/*  46 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  48 */   protected static final Logger log = Logger.getLogger(OdometerPanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyCodePanel(BaseCustomizeJPanel prevPanel) {
/*  54 */     super(prevPanel);
/*  55 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  64 */     setLayout(new BorderLayout());
/*  65 */     add(getTitlePanel(), "North");
/*  66 */     add(getCenterPanel(), "Center");
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel getTitlePanel() {
/*  71 */     if (this.titlePanel == null) {
/*     */       try {
/*  73 */         this.titlePanel = new JPanel();
/*  74 */         this.titlePanel.setLayout(new GridBagLayout());
/*  75 */         this.titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
/*  76 */         GridBagConstraints northConstraints = new GridBagConstraints();
/*  77 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/*  78 */         northConstraints.gridx = 1;
/*  79 */         northConstraints.gridy = 2;
/*  80 */         JLabel titleLabel = new JLabel(resourceProvider.getLabel(null, "keycodeScreen.title"));
/*  81 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/*  82 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/*  83 */         this.titlePanel.add(titleLabel, northConstraints);
/*     */       }
/*  85 */       catch (Throwable e) {
/*  86 */         log.error("getTitlePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/*  89 */     return this.titlePanel;
/*     */   }
/*     */   
/*     */   private JPanel getCenterPanel() {
/*     */     try {
/*  94 */       this.centerPanel = new JPanel(new GridBagLayout());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 100 */       this.centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
/* 101 */       GridBagConstraints c = new GridBagConstraints();
/* 102 */       c.gridx = 0;
/* 103 */       c.gridy = 0;
/* 104 */       c.gridheight = 1;
/* 105 */       c.gridwidth = 6;
/* 106 */       c.fill = 2;
/* 107 */       this.centerPanel.add(getMessagePanel(), c);
/*     */       
/* 109 */       c.gridx = 0;
/* 110 */       c.gridy = 1;
/* 111 */       c.gridheight = 1;
/* 112 */       c.gridwidth = 1;
/* 113 */       JLabel label = new JLabel(resourceProvider.getLabel(null, "keycodeScreen.keycode"));
/* 114 */       label.setFont(new Font(getFont().getFontName(), 1, getFont().getSize()));
/* 115 */       this.centerPanel.add(label, c);
/*     */       
/* 117 */       c.gridx = 1;
/* 118 */       c.gridy = 1;
/* 119 */       c.gridheight = 1;
/* 120 */       c.gridwidth = 3;
/* 121 */       this.centerPanel.add(getInputPanel(), c);
/*     */       
/* 123 */       c.gridx = 0;
/* 124 */       c.gridy = 2;
/* 125 */       c.gridheight = 4;
/* 126 */       c.weighty = 1.0D;
/* 127 */       c.fill = 3;
/* 128 */       this.centerPanel.add(new JPanel(), c);
/*     */     }
/* 130 */     catch (Exception e) {
/* 131 */       log.error("getCenterPanel() method, -exception: " + e.getMessage());
/*     */     } 
/* 133 */     return this.centerPanel;
/*     */   }
/*     */   
/*     */   private JPanel getMessagePanel() {
/* 137 */     if (this.messagePanel == null) {
/*     */       try {
/* 139 */         this.messagePanel = new JPanel();
/* 140 */         this.messagePanel.setLayout(new BorderLayout());
/*     */         
/* 142 */         JTextArea mes = new JTextArea(resourceProvider.getMessage(null, "keycodeScreen.input"));
/* 143 */         mes.setPreferredSize(new Dimension(400, 150));
/* 144 */         this.messagePanel.add(mes, "Center");
/* 145 */         mes.setLineWrap(true);
/* 146 */         mes.setWrapStyleWord(true);
/* 147 */         mes.setBackground(getBackground());
/* 148 */         mes.setForeground(Color.black);
/* 149 */         mes.setEditable(false);
/*     */       }
/* 151 */       catch (Throwable e) {
/* 152 */         log.error("getMessagePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 155 */     return this.messagePanel;
/*     */   }
/*     */   
/*     */   private JPanel getInputPanel() {
/* 159 */     if (this.inputPanel == null) {
/*     */       try {
/* 161 */         this.inputPanel = new JPanel();
/* 162 */         this.inputPanel.setLayout(new BorderLayout());
/* 163 */         this.inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
/* 164 */         this.input = new CustomizeJTextField();
/* 165 */         this.input.setPreferredSize(new Dimension(200, 20));
/* 166 */         this.inputPanel.add((Component)this.input, "Center");
/*     */       }
/* 168 */       catch (Throwable e) {
/* 169 */         log.error("getInputPanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 172 */     return this.inputPanel;
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 177 */       setRequestGroup(req.getRequestGroup());
/* 178 */       this.input.setRequest((InputRequest)req);
/* 179 */     } catch (Exception e) {
/* 180 */       System.out.println("Exception in handleRequest method:" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onNextAction() {
/*     */     try {
/* 187 */       if (this.input.getText().equals("") || this.input.getText().equals("0")) {
/* 188 */         JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(null, "sps.exception.no-keycode"), "Exception", 0);
/* 189 */         this.input.requestFocus();
/* 190 */         return false;
/*     */       } 
/* 192 */       if (this.input.getText().length() != 5 || !SwingUtils.isNumber(this.input.getText())) {
/* 193 */         JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(null, "sps.exception.invalid-keycode-format-input"), "Exception", 0);
/* 194 */         this.input.requestFocus();
/* 195 */         return false;
/*     */       }
/*     */     
/* 198 */     } catch (Exception e) {
/* 199 */       log.error("onNextAction() method, -exception: " + e.getMessage());
/*     */     } 
/* 201 */     return true;
/*     */   }
/*     */   
/*     */   public void onOpenPanel() {
/* 205 */     Runnable doWorkRunnable = new Runnable() {
/*     */         public void run() {
/* 207 */           KeyCodePanel.this.input.requestFocus();
/*     */         }
/*     */       };
/* 210 */     SwingUtilities.invokeLater(doWorkRunnable);
/* 211 */     this.input.requestFocus();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\KeyCodePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */