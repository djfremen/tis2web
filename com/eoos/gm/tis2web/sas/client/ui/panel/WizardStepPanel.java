/*     */ package com.eoos.gm.tis2web.sas.client.ui.panel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sas.client.ui.main.MainFrame;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.ButtonHandle;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.PanelStack;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.ProgressMonitor;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.UIUtil;
/*     */ import com.eoos.thread.AsynchronousExecutionCallback2;
/*     */ import com.eoos.thread.ProgressInfo;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Component;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
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
/*     */ public abstract class WizardStepPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Callback callback;
/*     */   private JButton buttonBack;
/*     */   private JButton buttonNext;
/*     */   private JButton buttonCancel;
/*     */   
/*     */   protected WizardStepPanel(Callback callback) {
/*  60 */     this.callback = callback;
/*     */ 
/*     */     
/*  63 */     GridBagLayout layout = new GridBagLayout();
/*  64 */     setLayout(layout);
/*  65 */     GridBagConstraints constraints = null;
/*     */ 
/*     */ 
/*     */     
/*  69 */     constraints = new GridBagConstraints();
/*  70 */     constraints.fill = 1;
/*  71 */     constraints.weightx = 1.0D;
/*  72 */     constraints.weighty = 1.0D;
/*  73 */     add(this.callback.getContentPanel(), constraints);
/*     */ 
/*     */     
/*  76 */     constraints = new GridBagConstraints();
/*  77 */     constraints.gridx = 0;
/*  78 */     constraints.gridy = 1;
/*  79 */     constraints.fill = 2;
/*  80 */     constraints.weightx = 1.0D;
/*  81 */     constraints.weighty = 0.0D;
/*     */     
/*  83 */     add(createButtonPanel(), constraints);
/*     */   }
/*     */   
/*     */   private class ButtonPanel
/*     */     extends JPanel
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public ButtonPanel() {
/*  92 */       setOpaque(false);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void paintComponent(Graphics g) {
/*  97 */       super.paintComponent(g);
/*     */       
/*  99 */       Graphics2D gc = (Graphics2D)g.create();
/*     */       
/* 101 */       gc.setColor(getBackground().darker().darker());
/* 102 */       gc.setStroke(new BasicStroke(2.0F));
/* 103 */       gc.drawLine(0, 0, getWidth(), 0);
/* 104 */       gc.dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   private JPanel createButtonPanel() {
/* 109 */     JPanel panel = new ButtonPanel();
/*     */ 
/*     */     
/* 112 */     GridBagLayout layout = new GridBagLayout();
/* 113 */     panel.setLayout(layout);
/* 114 */     GridBagConstraints constraints = null;
/*     */ 
/*     */ 
/*     */     
/* 118 */     constraints = new GridBagConstraints();
/* 119 */     constraints.fill = 0;
/* 120 */     constraints.anchor = 17;
/* 121 */     constraints.weightx = 0.0D;
/* 122 */     panel.add(createPopupButtonPanel(), constraints);
/*     */ 
/*     */     
/* 125 */     constraints = new GridBagConstraints();
/* 126 */     constraints.fill = 1;
/* 127 */     constraints.weightx = 1.0D;
/* 128 */     JPanel dummy = new JPanel();
/* 129 */     dummy.setOpaque(false);
/*     */     
/* 131 */     panel.add(dummy, constraints);
/*     */ 
/*     */     
/* 134 */     constraints = new GridBagConstraints();
/* 135 */     constraints.fill = 0;
/* 136 */     constraints.anchor = 13;
/* 137 */     constraints.weightx = 0.0D;
/*     */     
/* 139 */     panel.add(createStandardButtonPanel(), constraints);
/* 140 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private Component createPopupButtonPanel() {
/* 145 */     JPanel panel = new JPanel();
/* 146 */     panel.setOpaque(false);
/*     */     
/* 148 */     FlowLayout layout = new FlowLayout();
/* 149 */     panel.setLayout(layout);
/*     */     
/* 151 */     List popupButtons = this.callback.getPopupButtons(this);
/* 152 */     if (popupButtons != null) {
/* 153 */       for (Iterator<JButton> iter = popupButtons.iterator(); iter.hasNext(); ) {
/* 154 */         JButton button = iter.next();
/* 155 */         panel.add(button);
/*     */       } 
/*     */     }
/* 158 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private Component createStandardButtonPanel() {
/* 163 */     JPanel panel = new JPanel();
/* 164 */     panel.setOpaque(false);
/*     */ 
/*     */     
/* 167 */     FlowLayout layout = new FlowLayout(2);
/* 168 */     panel.setLayout(layout);
/*     */ 
/*     */ 
/*     */     
/* 172 */     this.buttonBack = new JButton(this.callback.getLabel("button.back"));
/* 173 */     this.buttonBack.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 175 */             WizardStepPanel.this.onBack();
/*     */           }
/*     */         });
/*     */     
/* 179 */     panel.add(this.buttonBack);
/* 180 */     if (this.callback.getPanelStack().peek() == null) {
/* 181 */       this.buttonBack.setVisible(false);
/*     */     }
/*     */     
/* 184 */     this.buttonNext = new JButton(this.callback.getLabel("button.next"));
/* 185 */     this.buttonNext.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 187 */             WizardStepPanel.this.onNext();
/*     */           }
/*     */         });
/* 190 */     panel.add(this.buttonNext);
/*     */     
/* 192 */     this.buttonCancel = new JButton(this.callback.getLabel("button.cancel"));
/* 193 */     this.buttonCancel.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 195 */             WizardStepPanel.this.onCancel();
/*     */           }
/*     */         });
/* 198 */     panel.add(this.buttonCancel);
/*     */     
/* 200 */     this.callback.setButtonHandle(new ButtonHandle() {
/*     */           private JButton getButton(ButtonHandle.Type type) {
/* 202 */             if (type == ButtonHandle.BACK)
/* 203 */               return WizardStepPanel.this.buttonBack; 
/* 204 */             if (type == ButtonHandle.NEXT)
/* 205 */               return WizardStepPanel.this.buttonNext; 
/* 206 */             if (type == ButtonHandle.CANCEL) {
/* 207 */               return WizardStepPanel.this.buttonCancel;
/*     */             }
/* 209 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public void setEnabled(ButtonHandle.Type type, boolean enabled) {
/* 214 */             getButton(type).setEnabled(enabled);
/*     */           }
/*     */           
/*     */           public void setLabel(ButtonHandle.Type type, String label) {
/* 218 */             getButton(type).setText(label);
/*     */           }
/*     */           
/*     */           public void setVisible(ButtonHandle.Type type, boolean visible) {
/* 222 */             getButton(type).setVisible(visible);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 227 */     return panel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNext() {
/* 233 */     UIUtil.setEnabled(this, false);
/*     */     
/* 235 */     AsynchronousExecutionCallback2 callback = new AsynchronousExecutionCallback2()
/*     */       {
/* 237 */         private ProgressInfo currentProgressInfo = null;
/*     */         
/* 239 */         private ProgressMonitor monitor = null;
/*     */         
/*     */         private void updateMonitor(ProgressInfo progressInfo) {
/* 242 */           if (progressInfo.getTotalTicks() != null) {
/* 243 */             this.monitor.setMaximum(progressInfo.getTotalTicks().intValue());
/*     */           }
/* 245 */           if (progressInfo.getProcessedTicks() != null) {
/* 246 */             this.monitor.setProgress(progressInfo.getProcessedTicks().intValue());
/*     */           }
/* 248 */           if (progressInfo.getMessage() != null && progressInfo.getMessage().length() > 0) {
/* 249 */             this.monitor.setNote(WizardStepPanel.this.callback.getMessage(progressInfo.getMessage()));
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public synchronized boolean onProcessing(ProgressInfo progressInfo) {
/* 257 */           if (this.monitor != null && this.monitor.isCanceled()) {
/* 258 */             return false;
/*     */           }
/* 260 */           if (progressInfo.equals(this.currentProgressInfo)) {
/* 261 */             updateMonitor(progressInfo);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 266 */             closeMonitor();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 271 */             this.monitor = new ProgressMonitor((Component)MainFrame.getInstance(), WizardStepPanel.this.callback.getLabel("progress"));
/* 272 */             this.monitor.setMessage(WizardStepPanel.this.callback.getMessage("please.wait"));
/* 273 */             updateMonitor(progressInfo);
/* 274 */             this.currentProgressInfo = progressInfo;
/*     */           } 
/* 276 */           return true;
/*     */         }
/*     */ 
/*     */         
/*     */         public void onFinished(Object result) {
/* 281 */           JPanel panel = (JPanel)result;
/* 282 */           if (panel != null) {
/* 283 */             WizardStepPanel.this.callback.getPanelStack().push(panel);
/*     */           }
/* 285 */           UIUtil.setEnabled(WizardStepPanel.this, true);
/* 286 */           closeMonitor();
/*     */         }
/*     */ 
/*     */         
/*     */         private void closeMonitor() {
/* 291 */           if (this.monitor != null) {
/* 292 */             this.monitor.close();
/*     */           }
/*     */         }
/*     */         
/*     */         public void onException(Throwable t) {
/* 297 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 302 */     createNextPanel(getContentPanel(), callback);
/*     */   } public static interface Callback {
/*     */     Locale getLocale(); String getLabel(String param1String); String getMessage(String param1String); void setButtonHandle(ButtonHandle param1ButtonHandle); List getPopupButtons(JPanel param1JPanel); PanelStack getPanelStack();
/*     */     JPanel getContentPanel(); }
/*     */   public void onBack() {
/* 307 */     UIUtil.setEnabled(this, false);
/* 308 */     this.callback.getPanelStack().pop();
/* 309 */     UIUtil.setEnabled(this, true);
/*     */   }
/*     */   
/*     */   public JPanel getContentPanel() {
/* 313 */     return this.callback.getContentPanel();
/*     */   }
/*     */   
/*     */   public abstract void onCancel();
/*     */   
/*     */   protected abstract void createNextPanel(JPanel paramJPanel, AsynchronousExecutionCallback2 paramAsynchronousExecutionCallback2);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\panel\WizardStepPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */