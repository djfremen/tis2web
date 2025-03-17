/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.FontUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class QuestionDialog
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(QuestionDialog.class);
/*     */   
/*  25 */   protected LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*     */   protected SPSFrame frame;
/*     */   
/*     */   protected JOptionPane optionPaneHTML;
/*     */   
/*     */   protected CustomizeHtmlRender webWindowHTML;
/*     */   
/*     */   protected boolean responseDialog;
/*     */   
/*     */   protected JDialog dialogMessage;
/*     */   
/*  37 */   protected String ok = this.resourceProvider.getLabel(null, "ok");
/*     */   
/*  39 */   protected String cancel = this.resourceProvider.getLabel(null, "cancel");
/*     */   
/*  41 */   protected final JButton[] OK_CANCEL = new JButton[] { new JButton(this.ok), new JButton(this.cancel) };
/*     */   
/*  43 */   protected final JButton[] OK = new JButton[] { new JButton(this.ok) };
/*     */   
/*     */   public QuestionDialog(SPSFrame frame) {
/*  46 */     this.frame = frame;
/*  47 */     initActionButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initActionButtons() {
/*  53 */     this.OK[0].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  55 */             QuestionDialog.this.onOK();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  60 */     this.OK_CANCEL[0].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  62 */             QuestionDialog.this.onOK();
/*     */           }
/*     */         });
/*     */     
/*  66 */     this.OK_CANCEL[1].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  68 */             QuestionDialog.this.onCancel();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onOK() {
/*  75 */     this.responseDialog = true;
/*  76 */     this.dialogMessage.setVisible(false);
/*     */   }
/*     */   
/*     */   protected void onCancel() {
/*  80 */     this.responseDialog = false;
/*  81 */     this.dialogMessage.setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean displayQuestionMessage(String errMsg, String title) {
/*  86 */     dialogMessage(errMsg, title, this.OK_CANCEL, -1);
/*  87 */     return this.responseDialog;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMessage(String errMsg, String title, JButton[] buttons, int iconType) {
/*     */     try {
/*  94 */       JTextArea editor = new JTextArea(errMsg);
/*  95 */       editor.setBackground(this.frame.getBackground());
/*  96 */       editor.setEditable(false);
/*  97 */       editor.setWrapStyleWord(true);
/*  98 */       editor.setLineWrap(true);
/*  99 */       JScrollPane scrollPane = new JScrollPane(editor);
/*     */       
/* 101 */       scrollPane.getViewport().add(editor);
/* 102 */       this.optionPaneHTML = new JOptionPane(scrollPane, -1, iconType, null, (Object[])buttons, buttons[0]);
/* 103 */       this.dialogMessage = this.optionPaneHTML.createDialog(this.frame, title);
/* 104 */       this.dialogMessage.pack();
/* 105 */       this.dialogMessage.setSize(new Dimension((int)SwingUtils.getDialogHTML_Width(), (int)SwingUtils.getDialogHTML_Height()));
/* 106 */       this.dialogMessage.setLocation(UIUtil.getCenterLocation(this.dialogMessage, this.frame));
/* 107 */       this.dialogMessage.setVisible(true);
/* 108 */       this.dialogMessage.setModal(true);
/*     */     }
/* 110 */     catch (Exception except) {
/* 111 */       log.error("unable to display  message Dialog, -exception: " + except.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean dialogQuestionMessage(String errMsg, String title) {
/* 116 */     FontMetrics fm = this.frame.getFontMetrics(FontUtils.getSelectedFont());
/* 117 */     StringTokenizer buff = new StringTokenizer(errMsg, "\n");
/* 118 */     if (buff.hasMoreTokens()) {
/* 119 */       while (buff.hasMoreTokens()) {
/* 120 */         if (isLongMessage(buff.nextToken(), fm))
/*     */         {
/* 122 */           return displayQuestionMessage(errMsg, title);
/*     */         }
/*     */       }
/*     */     
/* 126 */     } else if (isLongMessage(errMsg, fm)) {
/*     */       
/* 128 */       return displayQuestionMessage(errMsg, title);
/*     */     } 
/*     */ 
/*     */     
/* 132 */     String[] choices = { this.resourceProvider.getLabel(null, "ok"), this.resourceProvider.getLabel(null, "cancel") };
/* 133 */     int response = JOptionPane.showOptionDialog(this.frame, errMsg, title, 0, -1, null, (Object[])choices, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (response == 0) {
/* 144 */       return true;
/*     */     }
/* 146 */     if (response == 1) {
/* 147 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   public void displayMessage(String errMsg, String title, int iconType) {
/* 155 */     FontMetrics fm = this.frame.getFontMetrics(FontUtils.getSelectedFont());
/* 156 */     StringTokenizer buff = new StringTokenizer(errMsg, "\n");
/* 157 */     if (buff.hasMoreTokens()) {
/* 158 */       while (buff.hasMoreTokens()) {
/* 159 */         if (isLongMessage(buff.nextToken(), fm)) {
/*     */           
/* 161 */           dialogMessage(errMsg, title, this.OK, iconType);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 166 */     } else if (isLongMessage(errMsg, fm)) {
/*     */       
/* 168 */       dialogMessage(errMsg, title, this.OK, iconType);
/*     */       
/*     */       return;
/*     */     } 
/* 172 */     JOptionPane.showMessageDialog(this.frame, errMsg, title, 0);
/*     */   }
/*     */   
/*     */   protected boolean isLongMessage(String message, FontMetrics fm) {
/* 176 */     int stringWidth = fm.stringWidth(message);
/* 177 */     int widthApp = (int)this.frame.jContentPane.getSize().getWidth();
/* 178 */     if (stringWidth > widthApp * 5 / 6) {
/* 179 */       return true;
/*     */     }
/* 181 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\QuestionDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */