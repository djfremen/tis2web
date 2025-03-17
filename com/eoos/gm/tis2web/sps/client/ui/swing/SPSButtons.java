/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResourceWrapper;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.action.ButtonsAction;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSButtons
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  30 */   private static final Logger log = Logger.getLogger(SPSButtons.class);
/*     */   
/*  32 */   protected JButton btnNext = null;
/*     */   
/*  34 */   protected JButton btnPrev = null;
/*     */   
/*  36 */   protected JButton btnNew = null;
/*     */   
/*  38 */   protected JButton btnProceed = null;
/*     */   
/*  40 */   protected JButton btnCancel = null;
/*     */   
/*  42 */   protected JButton btnSettings = null;
/*     */   
/*  44 */   protected JButton btnEcuData = null;
/*     */   
/*  46 */   protected JButton btnReset = null;
/*     */   
/*  48 */   protected JButton btnHistory = null;
/*     */   
/*  50 */   protected JButton btnModuleInfo = null;
/*     */   
/*  52 */   protected JButton btnPrint = null;
/*     */   
/*  54 */   protected JButton btnClearDTCs = null;
/*     */   
/*  56 */   protected JPanel westButtonPanel = null;
/*     */   
/*  58 */   protected JPanel eastButtonPanel = null;
/*     */   
/*  60 */   protected JLabel lblBarStatus = null;
/*     */   
/*  62 */   protected JPanel barStatusPanel = null;
/*     */   
/*  64 */   protected JLabel lblVIN = null;
/*     */   
/*  66 */   protected ButtonsAction buttonsAction = null;
/*     */   
/*  68 */   protected Dimension dimension = new Dimension(100, 20);
/*     */   
/*  70 */   protected static LabelResource resourceProvider = (LabelResource)new LabelResourceWrapper(LabelResourceProvider.getInstance().getLabelResource());
/*     */   
/*     */   public SPSButtons(ButtonsAction action) {
/*  73 */     this.buttonsAction = action;
/*  74 */     initialize();
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  78 */     setLayout(new BorderLayout());
/*  79 */     add(getBarStatusPanel(), "North");
/*  80 */     add(getWestButtonPanel(), "West");
/*  81 */     add(getEastButtonPanel(), "East");
/*     */   }
/*     */ 
/*     */   
/*     */   public JPanel getBarStatusPanel() {
/*  86 */     if (this.barStatusPanel == null) {
/*     */       try {
/*  88 */         this.barStatusPanel = new JPanel();
/*  89 */         this.barStatusPanel.setLayout(new BorderLayout());
/*  90 */         this.barStatusPanel.setBorder(BorderFactory.createTitledBorder(null, null, 0, 0, null));
/*  91 */         this.barStatusPanel.add(getInfoBarStatus(), "West");
/*  92 */         this.barStatusPanel.add(getVINBarStatus(), "East");
/*  93 */       } catch (Exception e) {
/*  94 */         log.warn("unable to create status bar panel, ignoring - exception: " + e, e);
/*     */       } 
/*     */     }
/*     */     
/*  98 */     return this.barStatusPanel;
/*     */   }
/*     */   
/*     */   public JLabel getVINBarStatus() {
/* 102 */     if (this.lblVIN == null) {
/* 103 */       this.lblVIN = new JLabel();
/* 104 */       this.lblVIN.setBorder(new EmptyBorder(new Insets(0, 0, 0, 30)));
/* 105 */       this.lblVIN.setFont(this.lblBarStatus.getFont().deriveFont(1));
/*     */     } 
/* 107 */     return this.lblVIN;
/*     */   }
/*     */   
/*     */   public JLabel getInfoBarStatus() {
/* 111 */     if (this.lblBarStatus == null) {
/* 112 */       this.lblBarStatus = new JLabel();
/* 113 */       this.lblBarStatus.setBorder(new EmptyBorder(new Insets(0, 30, 0, 0)));
/* 114 */       this.lblBarStatus.setFont(this.lblBarStatus.getFont().deriveFont(1));
/*     */     } 
/* 116 */     return this.lblBarStatus;
/*     */   }
/*     */   
/*     */   public void setInfoBarStatus(String info) {
/* 120 */     String text = getInfoBarStatus().getText();
/* 121 */     if (info == null) {
/* 122 */       info = "";
/*     */     }
/* 124 */     if (info.trim().length() == 0) {
/* 125 */       if (text.equals(" ")) {
/*     */         return;
/*     */       }
/* 128 */       getInfoBarStatus().setText(info);
/*     */     }
/* 130 */     else if (info.indexOf("\n") >= 0) {
/* 131 */       info = StringUtilities.replace(info, "\\n", "<br>");
/* 132 */       info = StringUtilities.replace(info, "\n", "<br>");
/* 133 */       info = "<html><table><tr><td>" + info + "</td></tr></table></html>";
/* 134 */       getInfoBarStatus().setText(info);
/*     */     } else {
/* 136 */       getInfoBarStatus().setText(info);
/*     */     } 
/*     */   }
/*     */   
/*     */   public JPanel getWestButtonPanel() {
/* 141 */     if (this.westButtonPanel == null) {
/* 142 */       this.westButtonPanel = new JPanel();
/* 143 */       this.westButtonPanel.setLayout(new FlowLayout());
/* 144 */       this.westButtonPanel.add(getPrintButton());
/* 145 */       this.westButtonPanel.add(getSettingsButton());
/* 146 */       this.westButtonPanel.add(getEcuDataButton());
/* 147 */       this.westButtonPanel.add(getResetButton());
/* 148 */       this.westButtonPanel.add(getHistoryButton());
/* 149 */       this.westButtonPanel.add(getModuleInfoButton());
/*     */     } 
/* 151 */     return this.westButtonPanel;
/*     */   }
/*     */   
/*     */   public JPanel getEastButtonPanel() {
/* 155 */     if (this.eastButtonPanel == null) {
/*     */       try {
/* 157 */         this.eastButtonPanel = new JPanel();
/* 158 */         this.eastButtonPanel.setLayout(new FlowLayout());
/* 159 */         this.eastButtonPanel.add(getClearDTCsButton());
/* 160 */         this.eastButtonPanel.add(getProceedButton());
/* 161 */         this.eastButtonPanel.add(getNewButton());
/* 162 */         this.eastButtonPanel.add(getPrevButton());
/* 163 */         this.eastButtonPanel.add(getNextButton());
/* 164 */         this.eastButtonPanel.add(getCancelButton());
/* 165 */       } catch (Exception e) {
/* 166 */         log.warn("unable to create button panel, ignoring - exception: " + e, e);
/*     */       } 
/*     */     }
/* 169 */     return this.eastButtonPanel;
/*     */   }
/*     */   
/*     */   public JButton getCancelButton() {
/* 173 */     if (this.btnCancel == null) {
/*     */       try {
/* 175 */         this.btnCancel = new JButton(resourceProvider.getLabel(null, "cancel"));
/* 176 */         this.btnCancel.setFont(this.btnCancel.getFont().deriveFont(1));
/* 177 */         this.btnCancel.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 179 */                 SPSButtons.this.buttonsAction.onCancelAction();
/*     */               }
/*     */             });
/* 182 */       } catch (Throwable e) {
/* 183 */         log.error("unable to init cancel button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 186 */     return this.btnCancel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getResetButton() {
/* 195 */     if (this.btnReset == null) {
/*     */       try {
/* 197 */         this.btnReset = new JButton(resourceProvider.getLabel(null, "reset"));
/* 198 */         this.btnReset.setVisible(false);
/* 199 */         this.btnReset.setName("reset");
/* 200 */         this.btnReset.setFont(this.btnReset.getFont().deriveFont(1));
/* 201 */         this.btnReset.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 203 */                 SPSButtons.this.buttonsAction.onResetAction();
/*     */               }
/*     */             });
/* 206 */       } catch (Throwable e) {
/* 207 */         log.error("unable to init reset button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 210 */     return this.btnReset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getHistoryButton() {
/* 219 */     if (this.btnHistory == null) {
/*     */       try {
/* 221 */         this.btnHistory = new JButton(resourceProvider.getLabel(null, "history"));
/* 222 */         this.btnHistory.setVisible(false);
/* 223 */         this.btnHistory.setName("history");
/* 224 */         this.btnHistory.setFont(this.btnHistory.getFont().deriveFont(1));
/* 225 */         this.btnHistory.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 227 */                 SPSButtons.this.buttonsAction.onHistoryAction();
/*     */               }
/*     */             });
/* 230 */       } catch (Throwable e) {
/* 231 */         log.error("unable to init history button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 234 */     return this.btnHistory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getModuleInfoButton() {
/* 243 */     if (this.btnModuleInfo == null) {
/*     */       try {
/* 245 */         this.btnModuleInfo = new JButton(resourceProvider.getLabel(null, "moduleInfo"));
/* 246 */         this.btnModuleInfo.setVisible(false);
/* 247 */         this.btnModuleInfo.setName("moduleInfo");
/* 248 */         this.btnModuleInfo.setFont(this.btnModuleInfo.getFont().deriveFont(1));
/* 249 */         this.btnModuleInfo.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 251 */                 SPSButtons.this.buttonsAction.onModuleInfoAction();
/*     */               }
/*     */             });
/* 254 */       } catch (Throwable e) {
/* 255 */         log.error("unable to init module info button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 258 */     return this.btnModuleInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getPrintButton() {
/* 267 */     if (this.btnPrint == null) {
/*     */       try {
/* 269 */         this.btnPrint = new JButton(resourceProvider.getLabel(null, "print"));
/* 270 */         this.btnPrint.setVisible(false);
/* 271 */         this.btnPrint.setName("print");
/* 272 */         this.btnPrint.setFont(this.btnPrint.getFont().deriveFont(1));
/* 273 */         this.btnPrint.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 275 */                 SPSButtons.this.buttonsAction.onPrintAction();
/*     */               }
/*     */             });
/* 278 */       } catch (Throwable e) {
/* 279 */         log.error("unable to init print button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 282 */     return this.btnPrint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getPrevButton() {
/* 291 */     if (this.btnPrev == null) {
/*     */       try {
/* 293 */         this.btnPrev = new JButton(resourceProvider.getLabel(null, "back"));
/* 294 */         this.btnPrev.setFont(this.btnPrev.getFont().deriveFont(1));
/* 295 */         this.btnPrev.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 297 */                 SPSButtons.this.buttonsAction.onBackAction();
/*     */               }
/*     */             });
/* 300 */       } catch (Throwable e) {
/* 301 */         log.error("unable to init previous button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 304 */     return this.btnPrev;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getNextButton() {
/* 314 */     if (this.btnNext == null) {
/*     */       try {
/* 316 */         this.btnNext = new JButton(resourceProvider.getLabel(null, "next"));
/* 317 */         this.btnNext.setEnabled(false);
/* 318 */         this.btnNext.setFont(this.btnNext.getFont().deriveFont(1));
/* 319 */         this.btnNext.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 321 */                 SPSButtons.this.buttonsAction.onNextAction();
/*     */               }
/*     */             });
/* 324 */       } catch (Throwable e) {
/* 325 */         log.error("unable to init next button - exception:" + e, e);
/*     */       } 
/*     */     }
/*     */     
/* 329 */     return this.btnNext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getNewButton() {
/* 339 */     if (this.btnNew == null) {
/*     */       try {
/* 341 */         this.btnNew = new JButton(resourceProvider.getLabel(null, "new"));
/* 342 */         this.btnNew.setVisible(false);
/* 343 */         this.btnNew.setFont(this.btnNew.getFont().deriveFont(1));
/* 344 */         this.btnNew.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 346 */                 SPSButtons.this.buttonsAction.onNewAction();
/*     */               }
/*     */             });
/* 349 */       } catch (Throwable e) {
/* 350 */         log.error("unable to init new button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 353 */     return this.btnNew;
/*     */   }
/*     */   
/*     */   public JButton getProceedButton() {
/* 357 */     if (this.btnProceed == null) {
/*     */       try {
/* 359 */         this.btnProceed = new JButton(resourceProvider.getLabel(null, "proceed.same.vin"));
/* 360 */         this.btnProceed.setVisible(false);
/* 361 */         this.btnProceed.setFont(this.btnProceed.getFont().deriveFont(1));
/* 362 */         this.btnProceed.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 364 */                 SPSButtons.this.buttonsAction.onProceedAction();
/*     */               }
/*     */             });
/* 367 */       } catch (Throwable e) {
/* 368 */         log.error("unable to init proceed button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 371 */     return this.btnProceed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getSettingsButton() {
/* 380 */     if (this.btnSettings == null) {
/*     */       try {
/* 382 */         this.btnSettings = new JButton(resourceProvider.getLabel(null, "settings"));
/* 383 */         this.btnSettings.setVisible(false);
/* 384 */         this.btnSettings.setName("settings");
/* 385 */         this.btnSettings.setFont(this.btnSettings.getFont().deriveFont(1));
/* 386 */         this.btnSettings.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 388 */                 SPSButtons.this.buttonsAction.onSettingsAction();
/*     */               }
/*     */             });
/* 391 */       } catch (Throwable e) {
/* 392 */         log.error("unable to init settings button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 395 */     return this.btnSettings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getClearDTCsButton() {
/* 404 */     if (this.btnClearDTCs == null) {
/*     */       try {
/* 406 */         this.btnClearDTCs = new JButton(resourceProvider.getLabel(null, "clearDTCs"));
/* 407 */         this.btnClearDTCs.setVisible(false);
/* 408 */         this.btnClearDTCs.setName("clearDTCs");
/* 409 */         this.btnClearDTCs.setFont(this.btnClearDTCs.getFont().deriveFont(1));
/* 410 */         this.btnClearDTCs.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 412 */                 SPSButtons.this.buttonsAction.onClearDTCsAction();
/*     */               }
/*     */             });
/* 415 */       } catch (Throwable e) {
/* 416 */         log.error("unable to init clearButtonDTCs button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 419 */     return this.btnClearDTCs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getEcuDataButton() {
/* 428 */     if (this.btnEcuData == null) {
/*     */       try {
/* 430 */         this.btnEcuData = new JButton(resourceProvider.getLabel(null, "ecuData"));
/* 431 */         this.btnEcuData.setVisible(false);
/* 432 */         this.btnEcuData.setName("ecuData");
/* 433 */         this.btnEcuData.setFont(this.btnEcuData.getFont().deriveFont(1));
/* 434 */         this.btnEcuData.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 436 */                 SPSButtons.this.buttonsAction.onECUDataAction();
/*     */               }
/*     */             });
/* 439 */       } catch (Throwable e) {
/* 440 */         log.error("unable to init ecudata button - exception:" + e, e);
/*     */       } 
/*     */     }
/* 443 */     return this.btnEcuData;
/*     */   }
/*     */   
/*     */   public void visibleAllTemporaryButtons(boolean status) {
/* 447 */     Component[] comps = getWestButtonPanel().getComponents();
/* 448 */     for (int i = 0; i < comps.length; i++) {
/* 449 */       if (comps[i] instanceof JButton) {
/* 450 */         comps[i].setVisible(status);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void visibleTemporaryButton(String name, boolean status) {
/*     */     try {
/* 457 */       Component[] comps = getWestButtonPanel().getComponents();
/* 458 */       for (int i = 0; i < comps.length; i++) {
/* 459 */         if (comps[i] instanceof JButton && comps[i].getName().equalsIgnoreCase(name)) {
/* 460 */           comps[i].setVisible(status);
/*     */         }
/*     */       } 
/* 463 */     } catch (Exception e) {
/* 464 */       log.error("unable to visible Temporary Button - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hideTemporaryButtonsExceptOne(JButton button) {
/* 474 */     Component[] westButtons = getWestButtonPanel().getComponents();
/* 475 */     for (int i = 0; i < westButtons.length - 1; i++) {
/* 476 */       if (westButtons[i] instanceof JButton && westButtons[i].isVisible() && 
/* 477 */         !westButtons[i].equals(button))
/* 478 */         westButtons[i].setVisible(false); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\SPSButtons.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */