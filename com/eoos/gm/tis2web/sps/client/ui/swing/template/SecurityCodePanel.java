/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.CustomizeJTextField;
/*     */ import com.eoos.gm.tis2web.sps.common.ControllerSecurityCodeRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.InputRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SecurityCodePanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   protected JPanel northPanel = null;
/*     */   
/*  35 */   protected JPanel messagePanel = null;
/*     */   
/*  37 */   protected JPanel descriptionPanel = null;
/*     */   
/*  39 */   protected JPanel inputPanel = null;
/*     */   
/*  41 */   protected JPanel retypePanel = null;
/*     */   
/*  43 */   protected JPanel unitsPanel = null;
/*     */   
/*  45 */   protected JPanel centerPanel = null;
/*     */   
/*  47 */   protected CustomizeJTextField input = null;
/*     */   
/*  49 */   protected CustomizeJTextField inputRetype = null;
/*     */   
/*  51 */   protected String vin = null;
/*     */   
/*     */   protected boolean isNao;
/*     */   
/*  55 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*  56 */   protected static final Logger log = Logger.getLogger(SecurityCodePanel.class);
/*     */   
/*     */   public SecurityCodePanel(BaseCustomizeJPanel prevPanel, String vin, boolean isNao) {
/*  59 */     super(prevPanel);
/*  60 */     this.vin = vin;
/*  61 */     this.isNao = isNao;
/*  62 */     initialize((ControllerSecurityCodeRequest)null);
/*     */   }
/*     */   
/*     */   public SecurityCodePanel(BaseCustomizeJPanel prevPanel, ControllerSecurityCodeRequest req, String vin, boolean isNao) {
/*  66 */     super(prevPanel);
/*  67 */     this.vin = vin;
/*  68 */     this.isNao = isNao;
/*  69 */     initialize(req);
/*     */   }
/*     */   
/*     */   private void initialize(ControllerSecurityCodeRequest req) {
/*  73 */     setLayout(new BorderLayout());
/*  74 */     add(getNorthPanel(), "North");
/*  75 */     add(getCenterPanel(req), "Center");
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel getNorthPanel() {
/*  80 */     if (this.northPanel == null) {
/*     */       try {
/*  82 */         this.northPanel = new JPanel();
/*  83 */         this.northPanel.setLayout(new GridBagLayout());
/*  84 */         this.northPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
/*  85 */         GridBagConstraints northConstraints = new GridBagConstraints();
/*  86 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/*  87 */         northConstraints.gridx = 0;
/*  88 */         northConstraints.anchor = 10;
/*  89 */         JLabel titleLabel = new JLabel(resourceProvider.getLabel(null, "securityCodeScreen.title"));
/*  90 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/*  91 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/*  92 */         this.northPanel.add(titleLabel, northConstraints);
/*     */         
/*  94 */         if (this.isNao) {
/*  95 */           northConstraints.fill = 2;
/*  96 */           northConstraints.anchor = 17;
/*  97 */           this.northPanel.add(getDescriptionMessagePanel(), northConstraints);
/*     */         }
/*     */       
/* 100 */       } catch (Exception e) {
/* 101 */         log.error("getNorthPanel() method, -exception: " + e, e);
/*     */       } 
/*     */     }
/* 104 */     return this.northPanel;
/*     */   }
/*     */   
/*     */   private JPanel getCenterPanel(ControllerSecurityCodeRequest req) {
/*     */     try {
/* 109 */       this.centerPanel = new JPanel(new GridBagLayout());
/* 110 */       this.centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
/* 111 */       GridBagConstraints c = new GridBagConstraints();
/* 112 */       c.gridx = 0;
/* 113 */       c.gridy = 0;
/* 114 */       c.gridheight = 1;
/* 115 */       c.gridwidth = 6;
/* 116 */       c.fill = 2;
/* 117 */       if (req != null && req instanceof ControllerSecurityCodeRequest) {
/* 118 */         this.centerPanel.add(getMessagePanel_VINController(req.getControllerVIN()), c);
/*     */       } else {
/* 120 */         this.centerPanel.add(getMessagePanel(), c);
/*     */       } 
/*     */       
/* 123 */       c.gridx = 0;
/* 124 */       c.gridy = 1;
/* 125 */       c.gridheight = 1;
/* 126 */       c.gridwidth = 1;
/* 127 */       String message = resourceProvider.getLabel(null, "securityCodeScreen.securitycode");
/* 128 */       JLabel label = new JLabel(message);
/* 129 */       label.setFont(new Font(getFont().getFontName(), 1, getFont().getSize()));
/* 130 */       FontMetrics fontMetrics = label.getFontMetrics(label.getFont());
/* 131 */       int width = fontMetrics.stringWidth(message) + 20;
/* 132 */       int height = fontMetrics.getHeight();
/* 133 */       label.setPreferredSize(new Dimension(width, height));
/* 134 */       this.centerPanel.add(label, c);
/*     */       
/* 136 */       c.gridx = 1;
/* 137 */       c.gridy = 1;
/* 138 */       c.gridheight = 1;
/* 139 */       c.gridwidth = 3;
/* 140 */       this.centerPanel.add(getInputPanel(), c);
/*     */       
/* 142 */       c.gridx = 0;
/* 143 */       c.gridy = 2;
/* 144 */       c.gridheight = 1;
/* 145 */       c.gridwidth = 3;
/* 146 */       String messageRetype = resourceProvider.getLabel(null, "securityCodeScreen.securitycoderetype");
/* 147 */       label = new JLabel(messageRetype);
/* 148 */       label.setFont(new Font(getFont().getFontName(), 1, getFont().getSize()));
/* 149 */       fontMetrics = label.getFontMetrics(label.getFont());
/* 150 */       width = fontMetrics.stringWidth(messageRetype) + 20;
/* 151 */       height = fontMetrics.getHeight();
/* 152 */       label.setPreferredSize(new Dimension(width, height));
/* 153 */       this.centerPanel.add(label, c);
/*     */       
/* 155 */       c.gridx = 1;
/* 156 */       c.gridy = 2;
/* 157 */       c.gridheight = 1;
/* 158 */       c.gridwidth = 3;
/* 159 */       this.centerPanel.add(getInputRetypePanel(), c);
/*     */       
/* 161 */       c.gridx = 0;
/* 162 */       c.gridy = 3;
/* 163 */       c.gridheight = 4;
/* 164 */       c.weighty = 1.0D;
/* 165 */       c.fill = 3;
/* 166 */       this.centerPanel.add(new JPanel(), c);
/*     */       
/* 168 */       getInputPanel().setBorder(BorderFactory.createEmptyBorder(0, width - 20, 0, 60));
/* 169 */       getInputRetypePanel().setBorder(BorderFactory.createEmptyBorder(0, width - 20, 0, 60));
/* 170 */     } catch (Exception e) {
/* 171 */       log.error("getCenterPanel() method, -exception: " + e, e);
/*     */     } 
/* 173 */     return this.centerPanel;
/*     */   }
/*     */   
/*     */   private JPanel getDescriptionMessagePanel() {
/* 177 */     if (this.descriptionPanel == null) {
/*     */       try {
/* 179 */         this.descriptionPanel = new JPanel();
/* 180 */         this.descriptionPanel.setLayout(new BorderLayout());
/* 181 */         this.descriptionPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 20));
/* 182 */         String message = null;
/*     */         
/* 184 */         message = resourceProvider.getMessage(null, "securityCodeScreen.attention.description");
/*     */         
/* 186 */         JTextArea textArea = new JTextArea(message);
/* 187 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 188 */         textArea.setFont(new Font(textArea.getFont().getFontName(), 1, fontSize));
/* 189 */         FontMetrics fontMetrics = textArea.getFontMetrics(textArea.getFont());
/* 190 */         int width = fontMetrics.stringWidth(message) + 20;
/* 191 */         int height = fontMetrics.getHeight();
/* 192 */         textArea.setPreferredSize(new Dimension(width, height));
/* 193 */         this.descriptionPanel.add(textArea, "Center");
/*     */ 
/*     */         
/* 196 */         textArea.setBackground(getBackground());
/* 197 */         textArea.setForeground(Color.black);
/* 198 */         textArea.setEditable(false);
/*     */       }
/* 200 */       catch (Exception e) {
/* 201 */         log.error("getMessagePanel() method, -exception: " + e, e);
/*     */       } 
/*     */     }
/* 204 */     return this.descriptionPanel;
/*     */   }
/*     */   
/*     */   private JPanel getMessagePanel() {
/* 208 */     if (this.messagePanel == null) {
/*     */       try {
/* 210 */         this.messagePanel = new JPanel();
/* 211 */         this.messagePanel.setLayout(new BorderLayout());
/* 212 */         this.messagePanel.setBorder(BorderFactory.createEmptyBorder(150, 0, 20, 20));
/* 213 */         String message = null;
/*     */         
/* 215 */         if (this.vin != null) {
/* 216 */           message = resourceProvider.getMessage(null, "securityCodeScreen.input");
/* 217 */           message = message + " " + this.vin + ":";
/*     */         } else {
/* 219 */           message = resourceProvider.getMessage(null, "securityCodeScreen.controller.input") + ":";
/*     */         } 
/*     */         
/* 222 */         JTextArea textArea = new JTextArea(message);
/* 223 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 224 */         textArea.setFont(new Font(textArea.getFont().getFontName(), 1, fontSize));
/* 225 */         FontMetrics fontMetrics = textArea.getFontMetrics(textArea.getFont());
/* 226 */         int width = fontMetrics.stringWidth(message) + 20;
/* 227 */         int height = fontMetrics.getHeight();
/* 228 */         textArea.setPreferredSize(new Dimension(width, height));
/* 229 */         this.messagePanel.add(textArea, "Center");
/* 230 */         textArea.setLineWrap(true);
/* 231 */         textArea.setWrapStyleWord(true);
/* 232 */         textArea.setBackground(getBackground());
/* 233 */         textArea.setForeground(Color.black);
/* 234 */         textArea.setEditable(false);
/*     */       }
/* 236 */       catch (Exception e) {
/* 237 */         log.error("getMessagePanel() method, -exception: " + e, e);
/*     */       } 
/*     */     }
/* 240 */     return this.messagePanel;
/*     */   }
/*     */   
/*     */   private JPanel getMessagePanel_VINController(String controllerVIN) {
/* 244 */     if (this.messagePanel == null) {
/*     */       try {
/* 246 */         this.messagePanel = new JPanel();
/* 247 */         this.messagePanel.setLayout(new GridLayout(2, 1, 0, 10));
/* 248 */         this.messagePanel.setBorder(BorderFactory.createEmptyBorder(150, 0, 20, 20));
/* 249 */         String message = null;
/* 250 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/*     */         
/* 252 */         message = resourceProvider.getMessage(null, "securityCodeScreen.controller.input.different-vin");
/* 253 */         message = message.replace("{display_your_controller_vin}", controllerVIN);
/* 254 */         message = message.replace("{display_your_vehicle_vin}", this.vin);
/* 255 */         JTextArea textAreaVINController = new JTextArea(message);
/* 256 */         textAreaVINController.setFont(new Font(textAreaVINController.getFont().getFontName(), 1, fontSize));
/* 257 */         FontMetrics fontMetrics2 = textAreaVINController.getFontMetrics(textAreaVINController.getFont());
/* 258 */         int width2 = fontMetrics2.stringWidth(message) + 20;
/* 259 */         int height2 = fontMetrics2.getHeight();
/* 260 */         textAreaVINController.setPreferredSize(new Dimension(width2, height2));
/* 261 */         this.messagePanel.add(textAreaVINController);
/* 262 */         textAreaVINController.setLineWrap(true);
/* 263 */         textAreaVINController.setWrapStyleWord(true);
/* 264 */         textAreaVINController.setBackground(getBackground());
/* 265 */         textAreaVINController.setForeground(Color.black);
/* 266 */         textAreaVINController.setEditable(false);
/*     */         
/* 268 */         if (this.vin != null) {
/* 269 */           message = resourceProvider.getMessage(null, "securityCodeScreen.input");
/* 270 */           message = message + " " + controllerVIN + ":";
/*     */         } else {
/* 272 */           message = resourceProvider.getMessage(null, "securityCodeScreen.controller.input") + ":";
/*     */         } 
/* 274 */         JTextArea textArea = new JTextArea(message);
/*     */         
/* 276 */         textArea.setFont(new Font(textArea.getFont().getFontName(), 1, fontSize));
/* 277 */         FontMetrics fontMetrics = textArea.getFontMetrics(textArea.getFont());
/* 278 */         int width = fontMetrics.stringWidth(message) + 20;
/* 279 */         int height = fontMetrics.getHeight();
/* 280 */         textArea.setPreferredSize(new Dimension(width, height));
/* 281 */         this.messagePanel.add(textArea);
/* 282 */         textArea.setLineWrap(true);
/* 283 */         textArea.setWrapStyleWord(true);
/* 284 */         textArea.setBackground(getBackground());
/* 285 */         textArea.setForeground(Color.black);
/* 286 */         textArea.setEditable(false);
/*     */       }
/* 288 */       catch (Exception e) {
/* 289 */         log.error("getMessagePanel() method, -exception: " + e, e);
/*     */       } 
/*     */     }
/* 292 */     return this.messagePanel;
/*     */   }
/*     */   
/*     */   private JPanel getInputPanel() {
/* 296 */     if (this.inputPanel == null) {
/*     */       try {
/* 298 */         this.inputPanel = new JPanel();
/* 299 */         this.inputPanel.setLayout(new BorderLayout());
/* 300 */         this.input = new CustomizeJTextField();
/* 301 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 302 */         this.input.setFont(new Font(this.input.getFont().getFontName(), 1, fontSize));
/* 303 */         FontMetrics fontMetrics = this.input.getFontMetrics(this.input.getFont());
/* 304 */         int height = fontMetrics.getHeight();
/* 305 */         this.input.setPreferredSize(new Dimension(60, height));
/* 306 */         this.inputPanel.add((Component)this.input, "Center");
/* 307 */       } catch (Exception e) {
/* 308 */         log.error("getInputPanel() method, -exception: " + e, e);
/*     */       } 
/*     */     }
/* 311 */     return this.inputPanel;
/*     */   }
/*     */   
/*     */   private JPanel getInputRetypePanel() {
/* 315 */     if (this.retypePanel == null) {
/*     */       try {
/* 317 */         this.retypePanel = new JPanel();
/* 318 */         this.retypePanel.setLayout(new BorderLayout());
/* 319 */         this.inputRetype = new CustomizeJTextField();
/* 320 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 321 */         this.inputRetype.setFont(new Font(this.inputRetype.getFont().getFontName(), 1, fontSize));
/* 322 */         FontMetrics fontMetrics = this.inputRetype.getFontMetrics(this.inputRetype.getFont());
/* 323 */         int height = fontMetrics.getHeight();
/* 324 */         this.inputRetype.setPreferredSize(new Dimension(60, height));
/* 325 */         this.retypePanel.add((Component)this.inputRetype, "Center");
/* 326 */       } catch (Exception e) {
/* 327 */         log.error("getInputRetypePanel() method, -exception: " + e, e);
/*     */       } 
/*     */     }
/* 330 */     return this.retypePanel;
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 335 */       setRequestGroup(req.getRequestGroup());
/* 336 */       this.input.setRequest((InputRequest)req);
/* 337 */     } catch (Exception e) {
/* 338 */       log.error("handleRequest() method:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onNextAction() {
/*     */     try {
/* 345 */       if (this.input.getText().equals("") || this.input.getText().equals("0")) {
/* 346 */         JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(null, "sps.exception.no-securitycode"), "Exception", 0);
/* 347 */         this.input.requestFocus();
/* 348 */         return false;
/*     */       } 
/* 350 */       if (this.input.getText().length() != 4 || !isLetterorDigit(this.input.getText())) {
/* 351 */         JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(null, "sps.exception.invalid-securitycode-format-input"), "Exception", 0);
/* 352 */         this.input.requestFocus();
/* 353 */         return false;
/*     */       } 
/*     */       
/* 356 */       if (this.inputRetype.getText().equals("") || this.inputRetype.getText().equals("0")) {
/* 357 */         JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(null, "sps.exception.no-retype-securitycode"), "Exception", 0);
/* 358 */         this.inputRetype.requestFocus();
/* 359 */         return false;
/*     */       } 
/* 361 */       if (this.inputRetype.getText().length() != 4 || !isLetterorDigit(this.inputRetype.getText())) {
/* 362 */         JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(null, "sps.exception.invalid-securitycode-format-input"), "Exception", 0);
/* 363 */         this.inputRetype.requestFocus();
/* 364 */         return false;
/*     */       } 
/*     */       
/* 367 */       if (!this.input.getText().equals(this.inputRetype.getText())) {
/* 368 */         JOptionPane.showMessageDialog((Component)this, resourceProvider.getMessage(null, "sps.exception.no-match-retype-securitycode"), "Exception", 0);
/* 369 */         this.inputRetype.requestFocus();
/* 370 */         return false;
/*     */       }
/*     */     
/* 373 */     } catch (Exception e) {
/* 374 */       log.error("onNextAction() method, -exception: " + e, e);
/* 375 */       return false;
/*     */     } 
/* 377 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isLetterorDigit(String input) {
/* 381 */     if (input == null || input.length() == 0) {
/* 382 */       return false;
/*     */     }
/* 384 */     for (int i = 0; i < input.length(); i++) {
/* 385 */       char c = input.charAt(i);
/* 386 */       if (!Character.isLetterOrDigit(c)) {
/* 387 */         return false;
/*     */       }
/*     */     } 
/* 390 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onOpenPanel() {
/* 395 */     Runnable doWorkRunnable = new Runnable() {
/*     */         public void run() {
/* 397 */           SecurityCodePanel.this.input.requestFocus();
/*     */         }
/*     */       };
/* 400 */     SwingUtilities.invokeLater(doWorkRunnable);
/* 401 */     this.input.requestFocus();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\SecurityCodePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */