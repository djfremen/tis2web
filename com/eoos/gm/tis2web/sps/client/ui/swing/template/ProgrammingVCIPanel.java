/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
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
/*     */ import java.awt.Insets;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProgrammingVCIPanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  35 */   protected JPanel titlePanel = null;
/*     */   
/*  37 */   protected JPanel messagePanel = null;
/*     */   
/*  39 */   protected JPanel inputPanel = null;
/*     */   
/*  41 */   protected JPanel centerPanel = null;
/*     */   
/*  43 */   protected CustomizeJTextField input = null;
/*     */   
/*  45 */   protected static Locale locale = null;
/*     */   
/*  47 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProgrammingVCIPanel(BaseCustomizeJPanel prevPanel) {
/*  53 */     super(prevPanel);
/*  54 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  63 */     setLayout(new BorderLayout());
/*  64 */     add(getTitlePanel(), "North");
/*  65 */     add(getCenterPanel(), "Center");
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel getTitlePanel() {
/*  70 */     if (this.titlePanel == null) {
/*     */       try {
/*  72 */         this.titlePanel = new JPanel();
/*  73 */         this.titlePanel.setLayout(new GridBagLayout());
/*  74 */         this.titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
/*  75 */         GridBagConstraints northConstraints = new GridBagConstraints();
/*  76 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/*  77 */         northConstraints.gridx = 1;
/*  78 */         northConstraints.gridy = 2;
/*  79 */         JLabel titleLabel = new JLabel(resourceProvider.getLabel(locale, "programmingvciScreen.title"));
/*  80 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/*  81 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/*  82 */         this.titlePanel.add(titleLabel, northConstraints);
/*     */       }
/*  84 */       catch (Throwable e) {
/*  85 */         System.out.println("Exception in getTitlePanel() methode: " + e.getMessage());
/*     */       } 
/*     */     }
/*  88 */     return this.titlePanel;
/*     */   }
/*     */   
/*     */   private JPanel getCenterPanel() {
/*     */     try {
/*  93 */       this.centerPanel = new JPanel(new GridBagLayout());
/*  94 */       GridBagConstraints c = new GridBagConstraints();
/*  95 */       c.gridx = 0;
/*  96 */       c.gridy = 0;
/*  97 */       c.gridheight = 1;
/*  98 */       c.gridwidth = 8;
/*  99 */       c.fill = 2;
/* 100 */       this.centerPanel.add(getMessagePanel(), c);
/*     */       
/* 102 */       c.gridx = 1;
/* 103 */       c.gridy = 1;
/* 104 */       this.centerPanel.add(getInputPanel(), c);
/*     */       
/* 106 */       c.gridx = 0;
/* 107 */       c.gridy = 2;
/* 108 */       c.gridheight = 4;
/* 109 */       c.weighty = 1.0D;
/* 110 */       c.fill = 3;
/* 111 */       this.centerPanel.add(new JPanel(), c);
/*     */     }
/* 113 */     catch (Exception e) {
/* 114 */       System.out.println("Exception in getCenterPanel() methode :" + e.getMessage());
/*     */     } 
/* 116 */     return this.centerPanel;
/*     */   }
/*     */   
/*     */   private JPanel getMessagePanel() {
/* 120 */     if (this.messagePanel == null) {
/*     */       try {
/* 122 */         this.messagePanel = new JPanel();
/* 123 */         this.messagePanel.setLayout(new BorderLayout());
/* 124 */         this.messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
/* 125 */         JTextArea mes = new JTextArea(resourceProvider.getMessage(locale, "programmingvciScreen.input"));
/* 126 */         mes.setPreferredSize(new Dimension(400, 150));
/* 127 */         this.messagePanel.add(mes, "Center");
/* 128 */         mes.setLineWrap(true);
/* 129 */         mes.setWrapStyleWord(true);
/* 130 */         mes.setBackground(getBackground());
/* 131 */         mes.setForeground(Color.black);
/* 132 */         mes.setEditable(false);
/*     */       }
/* 134 */       catch (Throwable e) {
/* 135 */         System.out.println("Exception in getMessagePanel() methode: " + e.getMessage());
/*     */       } 
/*     */       try {
/* 138 */         if (ClientAppContextProvider.getClientAppContext().isVCI1001Enabled()) {
/* 139 */           this.messagePanel.add(new JLabel(resourceProvider.getMessage(locale, "programmingvciScreen.vci1001")), "South");
/*     */         }
/* 141 */       } catch (Throwable e) {
/* 142 */         System.out.println("Exception in getMessagePanel() methode: " + e.getMessage());
/*     */       } 
/*     */     } 
/* 145 */     return this.messagePanel;
/*     */   }
/*     */   
/*     */   private JPanel getInputPanel() {
/* 149 */     if (this.inputPanel == null) {
/*     */       try {
/* 151 */         this.inputPanel = new JPanel();
/* 152 */         this.inputPanel.setLayout(new BorderLayout());
/* 153 */         this.inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 120, 0, 120));
/* 154 */         this.input = new CustomizeJTextField();
/* 155 */         this.inputPanel.add((Component)this.input, "Center");
/*     */       }
/* 157 */       catch (Throwable e) {
/* 158 */         System.out.println("Exception in getInputPanel() methode: " + e.getMessage());
/*     */       } 
/*     */     }
/* 161 */     return this.inputPanel;
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 166 */       setRequestGroup(req.getRequestGroup());
/* 167 */       this.input.setRequest((InputRequest)req);
/* 168 */     } catch (Exception e) {
/* 169 */       System.out.println("Exception in handleRequest methode: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onOpenPanel() {
/* 174 */     Runnable doWorkRunnable = new Runnable() {
/*     */         public void run() {
/* 176 */           ProgrammingVCIPanel.this.input.requestFocus();
/*     */         }
/*     */       };
/* 179 */     SwingUtilities.invokeLater(doWorkRunnable);
/* 180 */     this.input.requestFocus();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\ProgrammingVCIPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */