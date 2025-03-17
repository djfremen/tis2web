/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.event.ListSelectionEvent;
/*     */ import javax.swing.event.ListSelectionListener;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProgrammingReconfPanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  34 */   private static final Logger log = Logger.getLogger(ProgrammingReconfPanel.class);
/*     */   
/*  36 */   private JPanel m_titlePanel = null;
/*     */   
/*  38 */   private JPanel m_mainPanel = null;
/*     */   
/*  40 */   private JPanel m_listPanel = null;
/*     */   
/*  42 */   private JPanel m_reconfPanel = null;
/*     */   
/*  44 */   private JList m_listbox = null;
/*     */   
/*  46 */   private JTextField m_txtDRAC = null;
/*     */   
/*  48 */   private JTextField m_txtTRANS = null;
/*     */   
/*  50 */   private JTextField m_txtVCI = null;
/*     */ 
/*     */   
/*     */   private ResourceBundle m_resource;
/*     */ 
/*     */ 
/*     */   
/*     */   public ProgrammingReconfPanel(BaseCustomizeJPanel prevPanel) {
/*  58 */     super(prevPanel);
/*     */ 
/*     */     
/*  61 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  71 */     setLayout(new BorderLayout());
/*  72 */     add(getTitlePanel(), "North");
/*  73 */     add(getMainPanel(), "Center");
/*     */   }
/*     */   
/*     */   private JPanel getTitlePanel() {
/*  77 */     if (this.m_titlePanel == null) {
/*     */       try {
/*  79 */         this.m_titlePanel = new JPanel();
/*  80 */         this.m_titlePanel.setLayout(new GridBagLayout());
/*  81 */         this.m_titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
/*  82 */         GridBagConstraints northConstraints = new GridBagConstraints();
/*  83 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/*  84 */         northConstraints.gridx = 1;
/*  85 */         northConstraints.gridy = 2;
/*  86 */         JLabel titleLabel = new JLabel(this.m_resource.getString("IDS_TITLE_PROGRAMMING_RECONF"));
/*  87 */         this.m_titlePanel.add(titleLabel, northConstraints);
/*     */       }
/*  89 */       catch (Throwable e) {
/*  90 */         log.warn("unable to create title panel, ignoring - exception: " + e, e);
/*     */       } 
/*     */     }
/*  93 */     return this.m_titlePanel;
/*     */   }
/*     */   
/*     */   private JPanel getMainPanel() {
/*  97 */     if (this.m_mainPanel == null) {
/*     */       try {
/*  99 */         this.m_mainPanel = new JPanel();
/* 100 */         this.m_mainPanel.setLayout(new GridLayout(2, 1));
/* 101 */         this.m_mainPanel.add(getListPanel());
/* 102 */         this.m_mainPanel.add(getReconfigurePanel());
/* 103 */       } catch (Throwable e) {
/* 104 */         log.warn("unable to create main panel, ignoring - exception: " + e, e);
/*     */       } 
/*     */     }
/* 107 */     return this.m_mainPanel;
/*     */   }
/*     */   
/*     */   private JPanel getListPanel() {
/* 111 */     if (this.m_listPanel == null) {
/*     */       try {
/* 113 */         this.m_listPanel = new JPanel();
/* 114 */         this.m_listPanel.setLayout(new BorderLayout());
/*     */         
/* 116 */         JLabel lbl = new JLabel(this.m_resource.getString("IDS_RECONF"));
/* 117 */         this.m_listPanel.add(lbl, "North");
/* 118 */         this.m_listbox = new JList();
/*     */ 
/*     */ 
/*     */         
/* 122 */         this.m_listbox.setSelectedIndex(0);
/* 123 */         this.m_listbox.addListSelectionListener(new ListSelectionListener() {
/*     */               public void valueChanged(ListSelectionEvent evt) {
/* 125 */                 ProgrammingReconfPanel.this.updateReconf();
/*     */               }
/*     */             });
/* 128 */         this.m_listPanel.add(new JScrollPane(this.m_listbox), "Center");
/* 129 */       } catch (Throwable e) {
/* 130 */         log.warn("unable to create list panel, ignoring - exception: " + e, e);
/*     */       } 
/*     */     }
/*     */     
/* 134 */     return this.m_listPanel;
/*     */   }
/*     */   
/*     */   private void updateReconf() {
/* 138 */     this.m_txtDRAC.setText("9359197");
/* 139 */     this.m_txtTRANS.setText("0");
/* 140 */     this.m_txtVCI.setText("402494");
/*     */   }
/*     */   
/*     */   private JPanel getReconfigurePanel() {
/* 144 */     if (this.m_reconfPanel == null) {
/*     */       try {
/* 146 */         this.m_reconfPanel = new JPanel() {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Insets getInsets() {
/* 150 */               return new Insets(20, 60, 20, 60);
/*     */             }
/*     */           };
/* 153 */         this.m_reconfPanel.setLayout(new GridBagLayout());
/* 154 */         this.m_reconfPanel.setBorder(new TitledBorder(null, "Reconfiguration", 0, 0, new Font("Dialog", 0, 12)));
/*     */ 
/*     */         
/* 157 */         GridBagConstraints c = new GridBagConstraints();
/*     */         
/* 159 */         c.insets = new Insets(10, 20, 10, 20);
/* 160 */         c.gridx = 3;
/* 161 */         c.gridy = 0;
/* 162 */         c.anchor = 18;
/* 163 */         JLabel lblDrac = new JLabel("DRAC Number");
/* 164 */         lblDrac.setPreferredSize(new Dimension(120, 20));
/* 165 */         this.m_reconfPanel.add(lblDrac, c);
/*     */         
/* 167 */         c.gridx = 3;
/* 168 */         c.gridy = 1;
/* 169 */         c.gridwidth = 1;
/* 170 */         JLabel lblTrans = new JLabel("TRANS Number");
/* 171 */         lblTrans.setPreferredSize(new Dimension(120, 20));
/* 172 */         this.m_reconfPanel.add(lblTrans, c);
/*     */         
/* 174 */         c.gridx = 3;
/* 175 */         c.gridy = 2;
/* 176 */         c.gridwidth = 1;
/* 177 */         JLabel lblVCI = new JLabel("VCI");
/* 178 */         lblVCI.setPreferredSize(new Dimension(1120, 20));
/* 179 */         this.m_reconfPanel.add(lblVCI, c);
/*     */         
/* 181 */         c.insets = new Insets(10, 80, 10, 40);
/* 182 */         c.gridx = 6;
/* 183 */         c.gridy = 0;
/* 184 */         c.weightx = 1.0D;
/* 185 */         c.gridwidth = 2;
/* 186 */         c.fill = 2;
/* 187 */         this.m_txtDRAC = new JTextField("9359142");
/* 188 */         this.m_txtDRAC.setPreferredSize(new Dimension(120, 20));
/* 189 */         this.m_reconfPanel.add(this.m_txtDRAC, c);
/*     */         
/* 191 */         c.gridx = 6;
/* 192 */         c.gridy = 1;
/* 193 */         c.gridwidth = 2;
/* 194 */         this.m_txtTRANS = new JTextField("0");
/* 195 */         this.m_txtTRANS.setPreferredSize(new Dimension(120, 20));
/* 196 */         this.m_reconfPanel.add(this.m_txtTRANS, c);
/*     */         
/* 198 */         c.gridx = 6;
/* 199 */         c.gridy = 2;
/* 200 */         c.gridwidth = 2;
/* 201 */         this.m_txtVCI = new JTextField("");
/* 202 */         this.m_txtVCI.setPreferredSize(new Dimension(120, 20));
/* 203 */         this.m_reconfPanel.add(this.m_txtVCI, c);
/*     */       }
/* 205 */       catch (Throwable e) {
/* 206 */         log.warn("unable to create reconfigure panel, ignoring - exception: " + e, e);
/*     */       } 
/*     */     }
/*     */     
/* 210 */     return this.m_reconfPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute getAttribute() {
/* 229 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\ProgrammingReconfPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */