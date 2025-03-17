/*     */ package com.eoos.gm.tis2web.dtc.cas.tc;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.AuthenticationQuery;
/*     */ import com.eoos.gm.tis2web.dtc.cas.api.DTCUploadFactory;
/*     */ import com.eoos.gm.tis2web.dtc.cas.api.IDTCUpload;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.alphabet.AlphabetUtil;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.util.Collections;
/*     */ import java.util.Properties;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MainFrame
/*     */   extends JFrame
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   private static final Logger log = Logger.getLogger(MainFrame.class);
/*     */   
/*     */   private Object sync;
/*     */   
/*  46 */   private JTextField inputApplicationID = new JTextField();
/*  47 */   private JTextField inputPortalID = new JTextField();
/*  48 */   private JTextField inputCountryCode = new JTextField();
/*  49 */   private JTextField inputBAC = new JTextField(11);
/*  50 */   private JTextArea inputContent = new JTextArea();
/*     */   
/*     */   public MainFrame(Object sync) {
/*  53 */     this.sync = sync;
/*     */     
/*  55 */     this.inputContent.setLineWrap(true);
/*     */     
/*  57 */     Container contentPane = getContentPane();
/*  58 */     contentPane.setLayout(new GridBagLayout());
/*  59 */     GridBagConstraints c = UIUtil.createDefaultConstraints();
/*  60 */     c.insets = new Insets(5, 5, 5, 5);
/*  61 */     c.gridx = 0;
/*  62 */     c.weighty = 1.0D;
/*     */     
/*  64 */     contentPane.add(createInputPanel(), c);
/*  65 */     c.weighty = 0.0D;
/*     */     
/*  67 */     contentPane.add(createButtonPanel(), c);
/*  68 */     contentPane.setPreferredSize(UIUtil.scale(contentPane.getPreferredSize(), 1.5F, 2.0F));
/*     */     
/*  70 */     addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/*  74 */             MainFrame.this.dispose();
/*     */           }
/*     */         });
/*     */     
/*  78 */     pack();
/*  79 */     setLocationRelativeTo(null);
/*     */   }
/*     */   
/*     */   private Component createButtonPanel() {
/*  83 */     JPanel ret = new JPanel(new GridBagLayout());
/*  84 */     GridBagConstraints c = new GridBagConstraints();
/*  85 */     c.gridy = 0;
/*     */     
/*  87 */     JButton buttonOK = new JButton("Upload DTC");
/*  88 */     buttonOK.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  91 */             final String applicationID = MainFrame.this.inputApplicationID.getText();
/*  92 */             final String portalID = MainFrame.this.inputPortalID.getText();
/*  93 */             final String countryCode = MainFrame.this.inputCountryCode.getText();
/*  94 */             final String bac = MainFrame.this.inputBAC.getText();
/*  95 */             final String content = MainFrame.this.inputContent.getText();
/*     */             
/*  97 */             Util.executeAsynchronous(new Callable()
/*     */                 {
/*     */                   public Object call() throws Exception {
/* 100 */                     MainFrame.this.upload(applicationID, portalID, countryCode, bac, content);
/* 101 */                     UIUtil.showSuccessPopup(MainFrame.this, "DTC upload succeeded");
/* 102 */                     return null;
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */     
/* 108 */     ret.add(buttonOK, c);
/* 109 */     ret.add(Box.createRigidArea(new Dimension(20, 20)), c);
/*     */     
/* 111 */     JButton buttonCancel = new JButton("Done");
/* 112 */     buttonCancel.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 115 */             MainFrame.this.dispose();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 120 */     ret.add(buttonCancel, c);
/* 121 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private Component createInputPanel() {
/* 126 */     JPanel ret = new JPanel(new GridBagLayout());
/* 127 */     GridBagConstraints cLabelCol = new GridBagConstraints();
/* 128 */     cLabelCol.anchor = 13;
/* 129 */     cLabelCol.fill = 0;
/* 130 */     cLabelCol.insets = new Insets(5, 10, 5, 10);
/* 131 */     cLabelCol.weighty = 0.0D;
/*     */     
/* 133 */     GridBagConstraints cInputCol = UIUtil.createDefaultConstraints();
/* 134 */     cInputCol.fill = 2;
/* 135 */     cInputCol.weightx = 1.0D;
/* 136 */     cInputCol.weighty = 0.0D;
/*     */     
/* 138 */     GridBagConstraints position = new GridBagConstraints();
/* 139 */     position.gridy = 0;
/* 140 */     ret.add(new JLabel("Application ID:"), UIUtil.mergeContraints(position, cLabelCol));
/* 141 */     ret.add(this.inputApplicationID, UIUtil.mergeContraints(position, cInputCol));
/*     */     
/* 143 */     position.gridy++;
/* 144 */     ret.add(new JLabel("Portal ID:"), UIUtil.mergeContraints(position, cLabelCol));
/* 145 */     ret.add(this.inputPortalID, UIUtil.mergeContraints(position, cInputCol));
/*     */     
/* 147 */     position.gridy++;
/* 148 */     ret.add(new JLabel("Country Code:"), UIUtil.mergeContraints(position, cLabelCol));
/* 149 */     ret.add(this.inputCountryCode, UIUtil.mergeContraints(position, cInputCol));
/*     */     
/* 151 */     position.gridy++;
/* 152 */     ret.add(new JLabel("BAC:"), UIUtil.mergeContraints(position, cLabelCol));
/* 153 */     ret.add(this.inputBAC, UIUtil.mergeContraints(position, cInputCol));
/*     */     
/* 155 */     position.gridy++;
/* 156 */     cInputCol.fill = 1;
/* 157 */     cInputCol.weighty = 1.0D;
/* 158 */     ret.add(new JLabel("Content:"), UIUtil.mergeContraints(position, cLabelCol));
/* 159 */     ret.add(new JScrollPane(this.inputContent), UIUtil.mergeContraints(position, cInputCol));
/*     */     
/* 161 */     JButton fillRandom = new JButton("<<");
/* 162 */     fillRandom.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 165 */             StringBuffer random = Util.createRandomWord(100L, 6000L, AlphabetUtil.create("abcdefghijklmopqrstuvwxyz1234567890!\"Â§$%&/()=).,;:#'+*~\\`Â´"));
/* 166 */             MainFrame.this.inputContent.setText(random.toString());
/*     */           }
/*     */         });
/*     */     
/* 170 */     GridBagConstraints c = new GridBagConstraints();
/* 171 */     c.anchor = 17;
/* 172 */     c.fill = 0;
/* 173 */     ret.add(fillRandom, UIUtil.mergeContraints(position, c));
/*     */     
/* 175 */     return ret;
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 179 */     super.dispose();
/* 180 */     synchronized (this.sync) {
/* 181 */       this.sync.notify();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void upload(String applicationID, String portalID, String countryCode, String bac, String content) {
/* 188 */     log.debug("handling upload request...");
/* 189 */     Properties restore = System.getProperties();
/*     */     
/* 191 */     log.debug("...modifying system properties");
/* 192 */     if (!Util.isNullOrEmpty(portalID)) {
/* 193 */       System.setProperty("portal.id", portalID);
/*     */     }
/* 195 */     if (!Util.isNullOrEmpty(countryCode)) {
/* 196 */       System.setProperty("country.code", countryCode);
/*     */     }
/*     */     
/* 199 */     if (!Util.isNullOrEmpty(bac)) {
/* 200 */       System.setProperty("bac.code", bac);
/*     */     }
/* 202 */     log.debug("... creating dtc upload service");
/* 203 */     IDTCUpload upload = DTCUploadFactory.createInstance(applicationID, getWrkDir(), (AuthenticationQuery)null);
/*     */     
/* 205 */     log.debug("... uploading dtc");
/*     */     try {
/* 207 */       upload.upload(Collections.singleton(content.getBytes()));
/* 208 */       log.debug("...done");
/* 209 */     } catch (Exception e) {
/* 210 */       log.error("unable to upload - exception:", e);
/* 211 */       UIUtil.showErrorPopup(this, "Unable to upload DTC! ");
/*     */     } 
/*     */     
/* 214 */     log.debug("...restoring system properties");
/* 215 */     System.setProperties(restore);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/* 221 */       Log4jUtil.attachConsoleAppender();
/* 222 */       Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
/*     */           {
/*     */             public void uncaughtException(Thread t, Throwable e) {
/* 225 */               MainFrame.log.error("abnormal termination - exception: ", e);
/* 226 */               System.exit(0);
/*     */             }
/*     */           });
/* 229 */       execute(Util.createTmpDir("dtcupload"));
/* 230 */     } catch (Throwable t) {
/* 231 */       log.error("abnormal termination - exception: ", t);
/*     */     } finally {
/* 233 */       log.debug("exiting ");
/* 234 */       System.exit(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void execute(final File wrkDir) throws Throwable {
/* 240 */     final Object sync = new Object();
/* 241 */     synchronized (sync) {
/* 242 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 245 */               (new MainFrame(sync)
/*     */                 {
/*     */                   private static final long serialVersionUID = 1L;
/*     */ 
/*     */                   
/*     */                   protected File getWrkDir() {
/* 251 */                     return wrkDir;
/*     */                   }
/*     */                 }).setVisible(true);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 258 */       if (!SwingUtilities.isEventDispatchThread())
/* 259 */         sync.wait(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract File getWrkDir();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\cas\tc\MainFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */