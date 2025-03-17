/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.FontUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayUtil;
/*     */ import com.eoos.gm.tis2web.sps.client.util.Transform;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SettingsCommonPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   protected static final Logger log = Logger.getLogger(SettingsCommonPanel.class);
/*  43 */   protected JPanel commonPanel = null;
/*  44 */   protected JLabel lblSPSVersionValue = null;
/*  45 */   protected JLabel lblFSValue = null;
/*     */   protected JButton plusButton;
/*     */   protected JButton minusButton;
/*  48 */   protected ClientAppContext clientAppContext = null;
/*  49 */   protected ClientSettings clientSettings = null;
/*  50 */   protected static Locale locale = null;
/*  51 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   protected static boolean changedFontSize = false;
/*     */   protected SPSFrame frame;
/*     */   
/*     */   public SettingsCommonPanel(SPSFrame frame, ClientSettings clientSettings) {
/*  56 */     this.clientAppContext = ClientAppContextProvider.getClientAppContext();
/*  57 */     this.clientSettings = clientSettings;
/*  58 */     this.frame = frame;
/*  59 */     initialize();
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  63 */     setLayout(new BorderLayout());
/*  64 */     add(getCommonPanel(), "Center");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getCommonPanel() {
/*  73 */     if (this.commonPanel == null) {
/*     */       
/*     */       try {
/*  76 */         this.commonPanel = new JPanel();
/*  77 */         this.commonPanel.setLayout(new GridBagLayout());
/*  78 */         this.commonPanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "settingsDialog.commonTab.title"), 0, 0, this.commonPanel.getFont().deriveFont(1), Color.gray));
/*  79 */         GridBagConstraints c = new GridBagConstraints();
/*  80 */         c.insets = new Insets(0, 10, 2, 10);
/*     */         
/*  82 */         c.gridx = 0;
/*  83 */         c.gridy = 0;
/*  84 */         c.anchor = 18;
/*  85 */         JLabel lblSPSVersion = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.version"));
/*  86 */         lblSPSVersion.setFont(lblSPSVersion.getFont().deriveFont(1));
/*  87 */         this.commonPanel.add(lblSPSVersion, c);
/*     */         
/*  89 */         c.gridx = 2;
/*  90 */         c.gridy = 0;
/*  91 */         this.lblSPSVersionValue = new JLabel(this.clientAppContext.getClientVersion());
/*  92 */         this.commonPanel.add(this.lblSPSVersionValue, c);
/*     */         
/*  94 */         c.gridx = 0;
/*  95 */         c.gridy = 1;
/*  96 */         JLabel lblDealerShip = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.dealership"));
/*  97 */         lblDealerShip.setFont(lblDealerShip.getFont().deriveFont(1));
/*  98 */         this.commonPanel.add(lblDealerShip, c);
/*     */         
/* 100 */         c.gridx = 2;
/* 101 */         c.gridy = 1;
/* 102 */         c.weightx = 1.0D;
/* 103 */         c.weighty = 1.0D;
/* 104 */         c.gridheight = 1;
/* 105 */         c.gridwidth = 1;
/* 106 */         c.fill = 1;
/* 107 */         List listDealer = (List)DisplayUtil.createDisplayAdapter(this.clientAppContext.getSupportedBrands());
/* 108 */         Vector<?> vectorDealer = Transform.createVector(listDealer);
/* 109 */         JList lstDealerShip = new JList(vectorDealer);
/*     */         
/* 111 */         lstDealerShip.setSelectedIndex(-1);
/* 112 */         lstDealerShip.setBackground(this.commonPanel.getBackground());
/* 113 */         JScrollPane scrollPane = new JScrollPane(lstDealerShip);
/* 114 */         scrollPane.setPreferredSize(new Dimension(200, 100));
/* 115 */         scrollPane.setViewportView(lstDealerShip);
/* 116 */         this.commonPanel.add(scrollPane, c);
/*     */         
/* 118 */         c.gridx = 0;
/* 119 */         c.gridy = 3;
/* 120 */         c.weightx = 0.0D;
/* 121 */         c.weighty = 0.0D;
/* 122 */         c.fill = 0;
/* 123 */         JLabel lblFontSize = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.fontSize"));
/* 124 */         lblFontSize.setFont(lblFontSize.getFont().deriveFont(1));
/* 125 */         this.commonPanel.add(lblFontSize, c);
/*     */         
/* 127 */         c.gridx = 2;
/* 128 */         c.gridy = 3;
/* 129 */         this.commonPanel.add(getFontPanel(), c);
/*     */         
/* 131 */         c.gridx = 0;
/* 132 */         c.gridy = 4;
/* 133 */         JLabel lblPaperFormat = new JLabel(resourceProvider.getLabel(locale, "settingsDialog.paperFormat"));
/* 134 */         lblPaperFormat.setFont(lblPaperFormat.getFont().deriveFont(1));
/* 135 */         this.commonPanel.add(lblPaperFormat, c);
/*     */         
/* 137 */         c.gridx = 2;
/* 138 */         c.gridy = 4;
/* 139 */         this.commonPanel.add(getPaperFormatCbo(), c);
/*     */       }
/* 141 */       catch (Throwable e) {
/* 142 */         log.error("unable to load the resources, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 145 */     return this.commonPanel;
/*     */   }
/*     */   
/*     */   protected JPanel getFontPanel() {
/* 149 */     JPanel fontPanel = new JPanel();
/*     */     try {
/* 151 */       fontPanel.setLayout(new GridBagLayout());
/* 152 */       GridBagConstraints c = new GridBagConstraints();
/*     */       
/* 154 */       c.insets = new Insets(0, 5, 0, 5);
/* 155 */       c.gridx = 0;
/* 156 */       c.gridy = 0;
/*     */       
/* 158 */       int fontSize = FontUtils.getSelectedFont().getSize();
/* 159 */       this.lblFSValue = new JLabel(String.valueOf(fontSize));
/* 160 */       fontPanel.add(this.lblFSValue, c);
/*     */       
/* 162 */       c.gridx = 1;
/* 163 */       c.gridy = 0;
/* 164 */       this.plusButton = new JButton("+");
/* 165 */       fontPanel.add(this.plusButton, c);
/* 166 */       if (isFontSizeSelectedGreaterAsMax())
/* 167 */         this.plusButton.setEnabled(false); 
/* 168 */       this.plusButton.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent e) {
/* 170 */               SettingsCommonPanel.this.plusAction();
/*     */             }
/*     */           });
/*     */       
/* 174 */       c.gridx = 2;
/* 175 */       c.gridy = 0;
/* 176 */       this.minusButton = new JButton("-");
/* 177 */       fontPanel.add(this.minusButton, c);
/* 178 */       this.minusButton.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent e) {
/* 180 */               SettingsCommonPanel.this.minusAction();
/*     */             }
/*     */           });
/* 183 */     } catch (Exception e) {
/* 184 */       log.error("getFontPanel() method, -exception: " + e.getMessage());
/*     */     } 
/* 186 */     return fontPanel;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JComboBox getPaperFormatCbo() {
/* 191 */     JComboBox<String> cboPaperFormat = null;
/*     */     
/*     */     try {
/* 194 */       String paperFormat = this.clientSettings.getProperty("print.paper.format");
/* 195 */       if (paperFormat == null) {
/*     */ 
/*     */ 
/*     */         
/* 199 */         String defaultCountry = Locale.getDefault().getCountry();
/* 200 */         if (!Locale.getDefault().equals(Locale.ENGLISH) && defaultCountry != null && !defaultCountry.equals(Locale.US.getCountry()) && !defaultCountry.equals(Locale.CANADA.getCountry())) {
/*     */ 
/*     */           
/* 203 */           paperFormat = "A4";
/*     */         } else {
/*     */           
/* 206 */           paperFormat = "LETTER";
/*     */         } 
/*     */       } 
/* 209 */       Vector<String> paperFormatVector = new Vector();
/* 210 */       paperFormatVector.add("A4");
/* 211 */       paperFormatVector.add("LETTER");
/* 212 */       cboPaperFormat = new JComboBox<String>(paperFormatVector);
/* 213 */       cboPaperFormat.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent e) {
/* 215 */               SettingsCommonPanel.this.cboBoxAction(e);
/*     */             }
/*     */           });
/* 218 */       cboPaperFormat.setName("PaperFormat");
/* 219 */       cboPaperFormat.setSelectedItem(paperFormat);
/*     */     }
/* 221 */     catch (Exception e) {
/* 222 */       log.error("getPaperFormatPanel() method, -exception: " + e.getMessage());
/*     */     } 
/* 224 */     return cboPaperFormat;
/*     */   }
/*     */   
/*     */   private void cboBoxAction(ActionEvent e) {
/*     */     try {
/* 229 */       JComboBox combo = (JComboBox)e.getSource();
/* 230 */       this.clientSettings.setProperty("print.paper.format", combo.getSelectedItem().toString());
/*     */     }
/* 232 */     catch (Exception ex) {
/* 233 */       log.error("cboBoxAction() method, -exception: " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void plusAction() {
/*     */     try {
/* 240 */       if (isFontSizeSelectedGreaterAsMax()) {
/* 241 */         this.plusButton.setEnabled(false);
/*     */         return;
/*     */       } 
/* 244 */       int fontSize = Integer.parseInt(this.lblFSValue.getText());
/* 245 */       fontSize++;
/* 246 */       this.lblFSValue.setText(String.valueOf(fontSize));
/* 247 */       this.clientSettings.setProperty("font.size", this.lblFSValue.getText());
/* 248 */       changedFontSize = true;
/* 249 */     } catch (Exception e) {
/* 250 */       log.error(" plusAction() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void minusAction() {
/*     */     try {
/* 256 */       int fontSize = Integer.parseInt(this.lblFSValue.getText());
/* 257 */       this.plusButton.setEnabled(true);
/* 258 */       if (fontSize < 8) {
/* 259 */         this.minusButton.setEnabled(false);
/*     */         return;
/*     */       } 
/* 262 */       fontSize--;
/* 263 */       this.lblFSValue.setText(String.valueOf(fontSize));
/* 264 */       this.clientSettings.setProperty("font.size", this.lblFSValue.getText());
/* 265 */       changedFontSize = true;
/* 266 */     } catch (Exception e) {
/* 267 */       log.error(" minusAction() method, -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isChangedFontSize() {
/* 272 */     return changedFontSize;
/*     */   }
/*     */   
/*     */   public boolean isFontSizeSelectedGreaterAsMax() {
/* 276 */     int fontSize = Integer.parseInt(this.lblFSValue.getText());
/* 277 */     if (fontSize >= FontUtils.getMaxFontSize()) {
/* 278 */       return true;
/*     */     }
/* 280 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SettingsCommonPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */