/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  29 */   protected JPanel jContentPane = null;
/*     */   
/*  31 */   protected JPanel messagePanel = null;
/*     */   
/*  33 */   protected String message = null;
/*     */   
/*  35 */   protected JTextArea text = null;
/*     */   
/*  37 */   protected JFrame frame = null;
/*     */   
/*  39 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  41 */   protected static final Logger log = Logger.getLogger(MessageDialog.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageDialog(JFrame frame, String message) {
/*  47 */     super(frame, frame.getTitle(), false);
/*  48 */     this.frame = frame;
/*  49 */     this.message = message;
/*  50 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageDialog(String message) {
/*  56 */     this.message = message;
/*  57 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*     */     try {
/*  69 */       setSize(SwingUtils.getMessageDialog_Size());
/*  70 */       setLocation(UIUtil.getCenterLocation(this, this.frame));
/*  71 */       setContentPane(getJContentPane());
/*  72 */       setDefaultCloseOperation(0);
/*     */     }
/*  74 */     catch (Exception e) {
/*  75 */       log.error("initialize() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getJContentPane() {
/*  86 */     if (this.jContentPane == null) {
/*  87 */       this.jContentPane = new JPanel();
/*  88 */       this.jContentPane.setLayout(new BorderLayout());
/*  89 */       this.jContentPane.add(getMessagePanel(), "Center");
/*     */     } 
/*     */     
/*  92 */     return this.jContentPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getMessagePanel() {
/* 101 */     if (this.messagePanel == null) {
/*     */       try {
/* 103 */         this.messagePanel = new JPanel(new BorderLayout());
/* 104 */         this.messagePanel.setBorder(new EmptyBorder(new Insets(60, 30, 10, 20)));
/* 105 */         this.text = new JTextArea(this.message);
/* 106 */         this.text.setBackground(getJContentPane().getBackground());
/* 107 */         this.text.setEditable(false);
/* 108 */         this.text.setWrapStyleWord(true);
/* 109 */         this.text.setLineWrap(true);
/* 110 */         this.messagePanel.add(this.text, "Center");
/* 111 */         new JPanel(new GridBagLayout());
/* 112 */         GridBagConstraints c = new GridBagConstraints();
/* 113 */         c.gridx = 3;
/* 114 */         c.gridy = 0;
/*     */       }
/* 116 */       catch (Throwable e) {
/* 117 */         log.error("getMessagePanel() method, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 120 */     return this.messagePanel;
/*     */   }
/*     */   
/*     */   public void updateJText(String message) {
/* 124 */     this.text.setText(message);
/* 125 */     getMessagePanel().validate();
/* 126 */     getMessagePanel().doLayout();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeDialog() {
/* 131 */     setVisible(false);
/* 132 */     dispose();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\MessageDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */