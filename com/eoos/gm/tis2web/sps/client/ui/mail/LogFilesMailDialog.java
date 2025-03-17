/*     */ package com.eoos.gm.tis2web.sps.client.ui.mail;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.error.ErrorPopupFacade;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogFilesMailDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  28 */   private JPanel jContentPane = null;
/*     */   
/*  30 */   private JLabel labelSender = null;
/*     */   
/*  32 */   private JLabel labelRecipients = null;
/*     */   
/*  34 */   private JLabel labelSubject = null;
/*     */   
/*  36 */   private JLabel labelText = null;
/*     */   
/*  38 */   private JTextField inputSender = null;
/*     */   
/*  40 */   private JTextField inputRecipients = null;
/*     */   
/*  42 */   private JTextField inputSubject = null;
/*     */   
/*  44 */   private JTextArea inputText = null;
/*     */   
/*  46 */   private JButton buttonSubmit = null; private Callback callback;
/*     */   private Action submitAction;
/*  48 */   private JButton buttonCancel = null;
/*     */   
/*     */   public static interface Callback {
/*  51 */     public static final Callback DUMMY = new Callback()
/*     */       {
/*     */         public String getMessage(String key) {
/*  54 */           return key;
/*     */         }
/*     */         
/*     */         public String getLabel(String key) {
/*  58 */           return key;
/*     */         }
/*     */       };
/*     */     
/*     */     String getLabel(String param1String);
/*     */     
/*     */     String getMessage(String param1String);
/*     */   }
/*     */   
/*     */   public static interface Action
/*     */   {
/*  69 */     public static final Action DUMMY = new Action()
/*     */       {
/*     */         public boolean handleException(Exception e, LogFilesMailDialog.ErrorNotification errorNotification) {
/*  72 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void execute(LogFilesMailDialog.DataAccess dataAccess) throws Exception {}
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void execute(LogFilesMailDialog.DataAccess param1DataAccess) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean handleException(Exception param1Exception, LogFilesMailDialog.ErrorNotification param1ErrorNotification);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private final ErrorNotification errorNotification = new ErrorNotification()
/*     */     {
/*     */       public void showNotification(String messageKey) {
/* 109 */         ErrorPopupFacade.showErrorPopup(LogFilesMailDialog.this, messageKey);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogFilesMailDialog(Frame owner, Callback callback, Action submitAction) {
/* 118 */     super(owner, true);
/* 119 */     this.callback = callback;
/* 120 */     this.submitAction = submitAction;
/* 121 */     initialize();
/* 122 */     setLocationRelativeTo(owner);
/* 123 */     addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e) {
/* 126 */             super.windowClosing(e);
/* 127 */             LogFilesMailDialog.this.dispose();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/* 139 */     setSize(300, 200);
/* 140 */     setContentPane(getJContentPane());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getJContentPane() {
/* 150 */     if (this.jContentPane == null) {
/* 151 */       GridBagConstraints gbcButtonCancel = new GridBagConstraints();
/* 152 */       gbcButtonCancel.gridx = 2;
/* 153 */       gbcButtonCancel.weightx = 1.0D;
/* 154 */       gbcButtonCancel.weighty = 1.0D;
/* 155 */       gbcButtonCancel.insets = new Insets(3, 1, 3, 1);
/* 156 */       gbcButtonCancel.ipadx = 2;
/* 157 */       gbcButtonCancel.ipady = 2;
/* 158 */       gbcButtonCancel.gridy = 4;
/*     */       
/* 160 */       GridBagConstraints gbcButtonSubmit = new GridBagConstraints();
/* 161 */       gbcButtonSubmit.gridx = 1;
/* 162 */       gbcButtonSubmit.weightx = 1.0D;
/* 163 */       gbcButtonSubmit.weighty = 1.0D;
/* 164 */       gbcButtonSubmit.ipadx = 2;
/* 165 */       gbcButtonSubmit.ipady = 2;
/* 166 */       gbcButtonSubmit.insets = new Insets(3, 1, 3, 1);
/* 167 */       gbcButtonSubmit.gridy = 4;
/*     */       
/* 169 */       GridBagConstraints gbcInputText = new GridBagConstraints();
/* 170 */       gbcInputText.fill = 1;
/* 171 */       gbcInputText.gridy = 3;
/* 172 */       gbcInputText.weightx = 1.0D;
/* 173 */       gbcInputText.weighty = 3.0D;
/* 174 */       gbcInputText.gridwidth = 2;
/* 175 */       gbcInputText.gridx = 1;
/*     */       
/* 177 */       GridBagConstraints gbcInputSubject = new GridBagConstraints();
/* 178 */       gbcInputSubject.gridy = 2;
/* 179 */       gbcInputSubject.fill = 2;
/* 180 */       gbcInputSubject.gridwidth = 2;
/* 181 */       gbcInputSubject.weightx = 1.0D;
/* 182 */       gbcInputSubject.weighty = 1.0D;
/* 183 */       gbcInputSubject.gridx = 1;
/*     */       
/* 185 */       GridBagConstraints gbcInputRecipients = new GridBagConstraints();
/* 186 */       gbcInputRecipients.fill = 2;
/* 187 */       gbcInputRecipients.gridy = 1;
/* 188 */       gbcInputRecipients.weightx = 1.0D;
/* 189 */       gbcInputRecipients.gridwidth = 2;
/* 190 */       gbcInputRecipients.weighty = 1.0D;
/* 191 */       gbcInputRecipients.gridx = 1;
/*     */       
/* 193 */       GridBagConstraints gbcInputSender = new GridBagConstraints();
/* 194 */       gbcInputSender.fill = 2;
/* 195 */       gbcInputSender.gridy = 0;
/* 196 */       gbcInputSender.weightx = 1.0D;
/* 197 */       gbcInputSender.gridwidth = 2;
/* 198 */       gbcInputSender.weighty = 1.0D;
/* 199 */       gbcInputSender.gridx = 1;
/*     */       
/* 201 */       Insets labelInsets = new Insets(5, 5, 5, 5);
/*     */       
/* 203 */       GridBagConstraints gbcLabelText = new GridBagConstraints();
/* 204 */       gbcLabelText.gridx = 0;
/* 205 */       gbcLabelText.weighty = 3.0D;
/* 206 */       gbcLabelText.gridy = 3;
/* 207 */       gbcLabelText.anchor = 13;
/* 208 */       gbcLabelText.insets = labelInsets;
/* 209 */       this.labelText = new JLabel();
/* 210 */       this.labelText.setText(this.callback.getLabel("text") + ":");
/*     */       
/* 212 */       GridBagConstraints gbcLabelSubject = new GridBagConstraints();
/* 213 */       gbcLabelSubject.gridx = 0;
/* 214 */       gbcLabelSubject.gridy = 2;
/* 215 */       gbcLabelSubject.anchor = 13;
/* 216 */       gbcLabelSubject.insets = labelInsets;
/* 217 */       this.labelSubject = new JLabel();
/* 218 */       this.labelSubject.setText(this.callback.getLabel("subject") + ":");
/*     */       
/* 220 */       GridBagConstraints gbcLabelRecipients = new GridBagConstraints();
/* 221 */       gbcLabelRecipients.gridx = 0;
/* 222 */       gbcLabelRecipients.gridy = 1;
/* 223 */       gbcLabelRecipients.anchor = 13;
/* 224 */       gbcLabelRecipients.insets = labelInsets;
/* 225 */       this.labelRecipients = new JLabel();
/* 226 */       this.labelRecipients.setText(this.callback.getLabel("recipient.s") + ":");
/*     */       
/* 228 */       GridBagConstraints gbcLabelSender = new GridBagConstraints();
/* 229 */       gbcLabelSender.gridx = 0;
/* 230 */       gbcLabelSender.gridy = 0;
/* 231 */       gbcLabelSender.anchor = 13;
/* 232 */       gbcLabelSender.insets = labelInsets;
/* 233 */       this.labelSender = new JLabel();
/* 234 */       this.labelSender.setText(this.callback.getLabel("sender") + ":");
/*     */       
/* 236 */       this.jContentPane = new JPanel();
/* 237 */       this.jContentPane.setLayout(new GridBagLayout());
/* 238 */       this.jContentPane.add(this.labelSender, gbcLabelSender);
/* 239 */       this.jContentPane.add(this.labelRecipients, gbcLabelRecipients);
/* 240 */       this.jContentPane.add(this.labelSubject, gbcLabelSubject);
/* 241 */       this.jContentPane.add(this.labelText, gbcLabelText);
/* 242 */       this.jContentPane.add(getInputSender(), gbcInputSender);
/* 243 */       this.jContentPane.add(getInputRecipients(), gbcInputRecipients);
/* 244 */       this.jContentPane.add(getInputSubject(), gbcInputSubject);
/* 245 */       this.jContentPane.add(new JScrollPane(getInputText()), gbcInputText);
/* 246 */       this.jContentPane.add(getButtonSubmit(), gbcButtonSubmit);
/* 247 */       this.jContentPane.add(getButtonCancel(), gbcButtonCancel);
/*     */     } 
/* 249 */     return this.jContentPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTextField getInputSender() {
/* 258 */     if (this.inputSender == null) {
/* 259 */       this.inputSender = new JTextField();
/*     */     }
/* 261 */     return this.inputSender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTextField getInputRecipients() {
/* 270 */     if (this.inputRecipients == null) {
/* 271 */       this.inputRecipients = new JTextField();
/*     */     }
/* 273 */     return this.inputRecipients;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTextField getInputSubject() {
/* 282 */     if (this.inputSubject == null) {
/* 283 */       this.inputSubject = new JTextField();
/*     */     }
/* 285 */     return this.inputSubject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JTextArea getInputText() {
/* 294 */     if (this.inputText == null) {
/* 295 */       this.inputText = new JTextArea();
/* 296 */       this.inputText.setLineWrap(true);
/*     */     } 
/* 298 */     return this.inputText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton getButtonSubmit() {
/* 307 */     if (this.buttonSubmit == null) {
/* 308 */       this.buttonSubmit = new JButton(this.callback.getLabel("submit"));
/* 309 */       this.buttonSubmit.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e) {
/* 312 */               LogFilesMailDialog.this.onSubmit();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 317 */     return this.buttonSubmit;
/*     */   }
/*     */   
/*     */   private void onSubmit() {
/* 321 */     UIUtil.setEnabled(this, false);
/* 322 */     final DataAccess da = new DataAccess()
/*     */       {
/*     */         public String getText() {
/* 325 */           return LogFilesMailDialog.this.getInputText().getText();
/*     */         }
/*     */         
/*     */         public String getSubject() {
/* 329 */           return LogFilesMailDialog.this.getInputSubject().getText();
/*     */         }
/*     */         
/*     */         public String getSender() {
/* 333 */           return LogFilesMailDialog.this.getInputSender().getText();
/*     */         }
/*     */         
/*     */         public String getRecipients() {
/* 337 */           return LogFilesMailDialog.this.getInputRecipients().getText();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 342 */     (new Thread() {
/*     */         public void run() {
/* 344 */           boolean dispose = true;
/*     */           try {
/* 346 */             LogFilesMailDialog.this.submitAction.execute(da);
/* 347 */           } catch (Exception e) {
/* 348 */             dispose = LogFilesMailDialog.this.submitAction.handleException(e, LogFilesMailDialog.this.errorNotification);
/*     */           } finally {
/* 350 */             final boolean fdispose = dispose;
/* 351 */             SwingUtilities.invokeLater(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 354 */                     if (fdispose) {
/* 355 */                       LogFilesMailDialog.this.dispose();
/*     */                     } else {
/* 357 */                       UIUtil.setEnabled(LogFilesMailDialog.this, true);
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */           } 
/*     */         }
/*     */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton getButtonCancel() {
/* 373 */     if (this.buttonCancel == null) {
/* 374 */       this.buttonCancel = new JButton(this.callback.getLabel("cancel"));
/* 375 */       this.buttonCancel.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e) {
/* 378 */               LogFilesMailDialog.this.dispose();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 383 */     return this.buttonCancel;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 387 */     JDialog dialog = new LogFilesMailDialog(null, Callback.DUMMY, Action.DUMMY);
/* 388 */     dialog.setVisible(true);
/*     */   }
/*     */   
/*     */   public static interface DataAccess {
/*     */     String getSender();
/*     */     
/*     */     String getRecipients();
/*     */     
/*     */     String getSubject();
/*     */     
/*     */     String getText();
/*     */   }
/*     */   
/*     */   public static interface ErrorNotification {
/*     */     void showNotification(String param1String);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\mail\LogFilesMailDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */