/*     */ package com.eoos.gm.tis2web.frame.diag.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.diag.client.IApplicationCallback;
/*     */ import com.eoos.gm.tis2web.frame.diag.client.logconf.AppParamConfigurationMail;
/*     */ import com.eoos.gm.tis2web.frame.diag.client.logconf.AppParamConfigurationManager;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Date;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MailDataForm
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 23102008002L;
/*  39 */   private static final Logger log = Logger.getLogger(MailDataForm.class);
/*     */   
/*     */   private static IApplicationCallback callback;
/*     */   
/*  43 */   private JTextField inputRecipient = new JTextField(25);
/*     */   
/*  45 */   private JTextField inputSender = new JTextField(25);
/*     */   
/*  47 */   private JTextField inputSubject = new JTextField(25);
/*     */   
/*  49 */   private JTextArea inputBody = new JTextArea(4, 1);
/*     */   
/*  51 */   private static JFrame parent = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private MailDataForm(JFrame _parent, IApplicationCallback _callback) {
/*  56 */     super(parent);
/*  57 */     parent = _parent;
/*  58 */     callback = _callback;
/*     */     
/*  60 */     addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e) {
/*  63 */             MailDataForm.this.close((String)null, (String)null, (String)null, (String)null);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  68 */     setTitle(callback.getText("please.specify.email.properties"));
/*  69 */     getContentPane().add(getRootPanel());
/*     */   }
/*     */   
/*     */   private JPanel createPanel() {
/*  73 */     JPanel ret = new JPanel();
/*     */     
/*  75 */     return ret;
/*     */   }
/*     */   
/*     */   private JPanel getRootPanel() {
/*  79 */     JPanel panel = createPanel();
/*  80 */     panel.setLayout(new GridBagLayout());
/*     */     
/*  82 */     GridBagConstraints c = new GridBagConstraints();
/*  83 */     c.gridx = 0;
/*  84 */     c.fill = 1;
/*  85 */     c.weightx = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     c.insets = new Insets(2, 5, 2, 5);
/*  92 */     c.anchor = 10;
/*  93 */     panel.add(getInputPanel(), c);
/*     */     
/*  95 */     c.fill = 2;
/*  96 */     panel.add(new JSeparator(), c);
/*     */     
/*  98 */     c.fill = 1;
/*  99 */     panel.add(getButtonPanel(), c);
/*     */     
/* 101 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel getInputPanel() {
/* 105 */     JPanel panel = createPanel();
/* 106 */     panel.setLayout(new GridBagLayout());
/*     */     
/* 108 */     GridBagConstraints c = new GridBagConstraints();
/* 109 */     c.fill = 2;
/* 110 */     c.insets = new Insets(2, 5, 2, 5);
/* 111 */     c.ipadx = 2;
/* 112 */     c.ipady = 5;
/*     */     
/* 114 */     c.gridy = 0;
/* 115 */     c.anchor = 12;
/* 116 */     c.gridwidth = 1;
/* 117 */     c.weightx = 1.0D;
/* 118 */     JLabel label = new JLabel(callback.getText("address.recipient") + ":", 4);
/* 119 */     panel.add(label, c);
/* 120 */     c.anchor = 18;
/* 121 */     c.gridwidth = 2;
/* 122 */     c.weightx = 2.0D;
/* 123 */     JTextField textField = this.inputRecipient;
/* 124 */     label.setLabelFor(textField);
/* 125 */     panel.add(textField, c);
/*     */     
/* 127 */     c.gridy = 1;
/* 128 */     c.anchor = 12;
/* 129 */     c.gridwidth = 1;
/* 130 */     c.weightx = 1.0D;
/* 131 */     label = new JLabel(callback.getText("address.sender") + ":", 4);
/* 132 */     panel.add(label, c);
/* 133 */     c.anchor = 18;
/* 134 */     c.gridwidth = 2;
/* 135 */     c.weightx = 2.0D;
/* 136 */     textField = this.inputSender;
/* 137 */     label.setLabelFor(textField);
/* 138 */     panel.add(textField, c);
/*     */     
/* 140 */     c.gridy = 2;
/* 141 */     c.anchor = 12;
/* 142 */     c.gridwidth = 1;
/* 143 */     c.weightx = 1.0D;
/* 144 */     label = new JLabel(callback.getText("address.subject") + ":", 4);
/* 145 */     panel.add(label, c);
/* 146 */     c.anchor = 18;
/* 147 */     c.gridwidth = 2;
/* 148 */     c.weightx = 2.0D;
/* 149 */     textField = this.inputSubject;
/* 150 */     textField.setMaximumSize(new Dimension(2, 2));
/* 151 */     label.setLabelFor(textField);
/* 152 */     panel.add(textField, c);
/*     */     
/* 154 */     c.gridy = 3;
/* 155 */     c.anchor = 12;
/* 156 */     c.gridwidth = 1;
/* 157 */     c.weightx = 1.0D;
/* 158 */     label = new JLabel(callback.getText("address.body") + ":", 4);
/* 159 */     panel.add(label, c);
/* 160 */     c.anchor = 18;
/* 161 */     c.gridwidth = 2;
/* 162 */     c.weightx = 2.0D;
/* 163 */     this.inputBody.setBorder(BorderFactory.createLineBorder(Color.black));
/* 164 */     this.inputBody.setAutoscrolls(true);
/* 165 */     JTextArea textArea = this.inputBody;
/* 166 */     textArea.setLineWrap(true);
/* 167 */     label.setLabelFor(textArea);
/* 168 */     JScrollPane scroller = new JScrollPane();
/* 169 */     scroller.getViewport().add(textArea);
/* 170 */     panel.add(scroller, c);
/*     */     
/*     */     try {
/* 173 */       AppParamConfigurationManager manager = callback.getAppParamConfigurationManager();
/* 174 */       AppParamConfigurationMail mail = manager.getConfigurationMail();
/*     */       
/* 176 */       if (mail != null) {
/* 177 */         this.inputRecipient.setText(mail.get_defaultTo());
/* 178 */         this.inputSender.setText(mail.get_defaultFrom());
/* 179 */         this.inputSubject.setText(callback.getText(mail.get_defaultSubject()));
/* 180 */         this.inputBody.setText(callback.getText(mail.get_defaultBody()));
/*     */       } 
/* 182 */     } catch (Exception ex) {
/* 183 */       log.error(ex.toString());
/* 184 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 187 */     return panel;
/*     */   }
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
/*     */   private JPanel getButtonPanel() {
/* 204 */     JPanel panel = createPanel();
/* 205 */     panel.setLayout(new GridBagLayout());
/*     */     
/* 207 */     GridBagConstraints c = new GridBagConstraints();
/* 208 */     c.fill = 0;
/* 209 */     c.gridy = 0;
/* 210 */     c.insets = new Insets(2, 5, 2, 5);
/* 211 */     panel.add(getOKButton(), c);
/*     */     
/* 213 */     panel.add(getCancelButton(), c);
/*     */     
/* 215 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private JButton getOKButton() {
/* 220 */     JButton button = new JButton(callback.getText("ok"));
/* 221 */     button.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 223 */             if (Util.isNullOrEmpty(MailDataForm.this.inputRecipient.getText()) || Util.isNullOrEmpty(MailDataForm.this.inputSender.getText())) {
/* 224 */               if (Util.isNullOrEmpty(MailDataForm.this.inputRecipient.getText()) && Util.isNullOrEmpty(MailDataForm.this.inputSender.getText())) {
/* 225 */                 MailDataForm.showPopup(MailDataForm.parent, "please.specify.email.send.and.recipient", 2);
/* 226 */               } else if (Util.isNullOrEmpty(MailDataForm.this.inputRecipient.getText())) {
/* 227 */                 MailDataForm.showPopup(MailDataForm.parent, "please.specify.email.recipient", 2);
/*     */               } else {
/* 229 */                 MailDataForm.showPopup(MailDataForm.parent, "please.specify.email.sender", 2);
/*     */               }
/*     */             
/* 232 */             } else if (!MailDataForm.callback.checkEmailAddress(MailDataForm.this.inputRecipient.getText()) || !MailDataForm.callback.checkEmailAddress(MailDataForm.this.inputSender.getText())) {
/* 233 */               MailDataForm.showPopup(MailDataForm.parent, "invalid.email.address.format", 2);
/*     */             } else {
/* 235 */               MailDataForm.this.close(MailDataForm.this.inputRecipient.getText(), MailDataForm.this.inputSender.getText(), MailDataForm.this.inputSubject.getText(), MailDataForm.this.inputBody.getText());
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 240 */     return button;
/*     */   }
/*     */   
/*     */   private JButton getCancelButton() {
/* 244 */     JButton button = new JButton(callback.getText("cancel"));
/* 245 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 248 */             MailDataForm.this.close((String)null, (String)null, (String)null, (String)null);
/*     */           }
/*     */         });
/* 251 */     return button;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] show(final JFrame parent, final IApplicationCallback callback) throws InterruptedException {
/* 259 */     Util.ensureNotAWTThread();
/* 260 */     final Object sync = new Object();
/* 261 */     synchronized (sync) {
/* 262 */       final String[] recipient = new String[1];
/* 263 */       final String[] sender = new String[1];
/* 264 */       final String[] subject = new String[1];
/* 265 */       final String[] body = new String[1];
/* 266 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 269 */               JDialog dialog = new MailDataForm(parent, callback) {
/*     */                   private static final long serialVersionUID = 24102008001L;
/*     */                   
/*     */                   protected void close(String _recipient, String _sender, String _subject, String _body) {
/* 273 */                     recipient[0] = _recipient;
/* 274 */                     sender[0] = _sender;
/* 275 */                     if (_subject == null || _subject.trim().equals("")) {
/* 276 */                       Date date = new Date();
/* 277 */                       subject[0] = "Log-Archive <" + date.toString() + "> " + System.getProperty("session.id");
/*     */                     } else {
/* 279 */                       subject[0] = _subject;
/*     */                     } 
/* 281 */                     body[0] = _body;
/*     */                     
/* 283 */                     dispose();
/* 284 */                     synchronized (sync) {
/* 285 */                       sync.notify();
/*     */                     } 
/*     */                   }
/*     */                 };
/* 289 */               dialog.pack();
/* 290 */               dialog.setLocationRelativeTo(parent);
/* 291 */               dialog.setModal(true);
/*     */               
/* 293 */               dialog.setVisible(true);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 298 */       sync.wait();
/*     */       
/* 300 */       if (recipient[0] == null || sender[0] == null) {
/* 301 */         return null;
/*     */       }
/* 303 */       (new String[4])[0] = recipient[0]; (new String[4])[1] = sender[0]; (new String[4])[2] = subject[0]; (new String[4])[3] = body[0]; return new String[4];
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void showPopup(final JFrame parent, final String msgKey, final int popupDecoration) {
/* 308 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 311 */             JOptionPane.showMessageDialog(parent, MailDataForm.callback.getText(msgKey), null, popupDecoration);
/*     */           }
/*     */         }true);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws InterruptedException, Throwable {
/* 317 */     final JFrame[] dummyAccess = new JFrame[1];
/* 318 */     SwingUtilities.invokeAndWait(new Runnable()
/*     */         {
/*     */           public void run() {
/* 321 */             dummyAccess[0] = new JFrame();
/* 322 */             dummyAccess[0].setLocationRelativeTo(null);
/* 323 */             dummyAccess[0].setVisible(true);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 328 */     Object oRet = show(dummyAccess[0], IApplicationCallback.TEST);
/* 329 */     if (oRet instanceof String[]) {
/* 330 */       System.out.println(CollectionUtil.toList((Object[])oRet));
/*     */     }
/* 332 */     System.exit(0);
/*     */   }
/*     */   
/*     */   protected abstract void close(String paramString1, String paramString2, String paramString3, String paramString4);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\clien\\ui\MailDataForm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */