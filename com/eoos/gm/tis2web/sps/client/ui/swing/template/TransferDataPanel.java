/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing.template;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.BaseCustomizeJPanel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.Timespan;
/*     */ import com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.ReprogramProgressDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.export.SPSBlob;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransferDataPanel
/*     */   extends BaseCustomizeJPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  45 */   private int instanceCount = 0;
/*     */   
/*  47 */   protected JPanel westPanel = null;
/*     */   
/*  49 */   protected JPanel centerPanel = null;
/*     */   
/*  51 */   protected JPanel northPanel = null;
/*     */   
/*  53 */   protected JPanel downloadStatusPanel = null;
/*     */   
/*  55 */   protected JPanel loadStatusPanel = null;
/*     */   
/*  57 */   protected JPanel upperStatusPanel = null;
/*     */   
/*  59 */   protected JPanel infoPanel = null;
/*     */   
/*  61 */   protected JProgressBar pbarDownload = null;
/*     */   
/*  63 */   protected static JProgressBar pbarReprog = null;
/*     */   
/*  65 */   protected JLabel imageLabel = null;
/*     */   
/*  67 */   protected JLabel titleLabel = null;
/*     */   
/*  69 */   protected JTextField txtTimeRemainDownload = null;
/*     */   
/*  71 */   protected JTextField txtTimeRemainReprogram = null;
/*     */   
/*  73 */   protected JTextField txtStatusDownload = null;
/*     */   
/*     */   protected static boolean reprogFailed = false;
/*     */   
/*  77 */   protected static JTextField txtStatusReprogram = null;
/*     */ 
/*     */   
/*  80 */   protected Controller controller = null;
/*     */   
/*     */   protected int statusPBar;
/*     */   
/*     */   private static String controllerLabel;
/*     */   
/*     */   private static String sequenceLabel;
/*     */   
/*     */   private static JLabel labelLoadStatus;
/*     */   
/*  90 */   protected static Locale locale = null;
/*     */   
/*  92 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  94 */   protected DownloadProgressDisplayRequest downloadReq = null;
/*     */   
/*  96 */   protected static int downloadTime = 0;
/*     */   
/*  98 */   private static final Logger log = Logger.getLogger(TransferDataPanel.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransferDataPanel(Controller controller, BaseCustomizeJPanel prevPanel) {
/* 104 */     super(prevPanel);
/* 105 */     resetVariablesStatic();
/* 106 */     this.controller = controller;
/* 107 */     initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize() {
/* 116 */     setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
/* 117 */     setLayout(new BorderLayout());
/* 118 */     add(getWestPanel(), "West");
/* 119 */     add(getCenterPanel(), "Center");
/* 120 */     add(getNorthPanel(), "North");
/* 121 */     invalidate();
/*     */   }
/*     */   
/*     */   protected void resetVariablesStatic() {
/* 125 */     pbarReprog = null;
/* 126 */     locale = null;
/* 127 */     controllerLabel = null;
/* 128 */     txtStatusReprogram = null;
/*     */   }
/*     */   
/*     */   private JPanel getWestPanel() {
/* 132 */     if (this.westPanel == null) {
/*     */       try {
/* 134 */         this.westPanel = new JPanel();
/* 135 */         this.westPanel.setLayout(new BorderLayout());
/* 136 */         this.westPanel.setBorder(new EmptyBorder(new Insets(0, 5, 5, 5)));
/* 137 */         this.imageLabel = new JLabel();
/* 138 */         this.imageLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/eoos/gm/tis2web/sps/client/common/resources/t2down.jpg")));
/* 139 */         this.westPanel.add(this.imageLabel, "North");
/* 140 */         this.westPanel.setPreferredSize(new Dimension(180, 100));
/* 141 */       } catch (Throwable e) {
/* 142 */         log.error("unable to load Image t2down.jpg, -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 145 */     return this.westPanel;
/*     */   }
/*     */   
/*     */   private JPanel getNorthPanel() {
/* 149 */     if (this.northPanel == null) {
/*     */       try {
/* 151 */         this.northPanel = new JPanel();
/* 152 */         this.northPanel.setBorder(new EmptyBorder(new Insets(0, 0, 20, 0)));
/* 153 */         this.northPanel.setLayout(new GridBagLayout());
/* 154 */         GridBagConstraints northConstraints = new GridBagConstraints();
/* 155 */         this.titleLabel = new JLabel(resourceProvider.getLabel(locale, "transferdataScreen.title"));
/* 156 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 157 */         this.titleLabel.setFont(new Font(this.titleLabel.getFont().getFontName(), 1, fontSize));
/* 158 */         this.northPanel.add(this.titleLabel, northConstraints);
/* 159 */         northConstraints.insets = new Insets(20, 20, 20, 20);
/* 160 */         northConstraints.gridx = 1;
/* 161 */         northConstraints.gridy = 2;
/*     */       }
/* 163 */       catch (Throwable e) {
/* 164 */         log.error("unable to load label , -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 167 */     return this.northPanel;
/*     */   }
/*     */   
/*     */   private JPanel getCenterPanel() {
/* 171 */     if (this.centerPanel == null) {
/*     */       try {
/* 173 */         this.centerPanel = new JPanel();
/* 174 */         this.centerPanel.setLayout(new BoxLayout(this.centerPanel, 1));
/* 175 */         this.centerPanel.add(getInfoPanel());
/* 176 */         this.centerPanel.add(getDownloadStatusPanel());
/* 177 */         this.centerPanel.add(getLoadStatusPanel());
/*     */       }
/* 179 */       catch (Throwable e) {
/* 180 */         log.error("getCenterPanel() methode , -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 183 */     return this.centerPanel;
/*     */   }
/*     */   
/*     */   private JPanel getInfoPanel() {
/* 187 */     if (this.infoPanel == null) {
/*     */       try {
/* 189 */         this.infoPanel = new JPanel(new GridLayout(2, 1));
/* 190 */         JLabel lbl1 = new JLabel(resourceProvider.getLabel(locale, "transferdataScreen.download"));
/* 191 */         JLabel lbl2 = new JLabel(resourceProvider.getLabel(locale, "transferdataScreen.load"));
/* 192 */         lbl1.setFont(lbl1.getFont().deriveFont(1));
/* 193 */         lbl2.setFont(lbl2.getFont().deriveFont(1));
/* 194 */         this.infoPanel.add(lbl1);
/* 195 */         this.infoPanel.add(lbl2);
/*     */         
/* 197 */         this.infoPanel.setFont(this.infoPanel.getFont().deriveFont(1));
/* 198 */       } catch (Throwable e) {
/* 199 */         log.error("unable to load labels , -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 202 */     return this.infoPanel;
/*     */   }
/*     */   
/*     */   private JPanel getDownloadStatusPanel() {
/* 206 */     if (this.downloadStatusPanel == null) {
/*     */       try {
/* 208 */         this.downloadStatusPanel = new JPanel();
/* 209 */         this.downloadStatusPanel.setLayout(new GridBagLayout());
/* 210 */         this.downloadStatusPanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "transferdataScreen.downloadPanel.title"), 0, 0, null, Color.black));
/*     */         
/* 212 */         GridBagConstraints c = new GridBagConstraints();
/* 213 */         c.insets = new Insets(20, 0, 0, 0);
/* 214 */         c.gridx = 0;
/* 215 */         c.gridy = 0;
/* 216 */         c.weightx = 1.0D;
/* 217 */         c.gridwidth = 3;
/* 218 */         c.fill = 2;
/* 219 */         c.anchor = 11;
/* 220 */         JLabel lblStatus = new JLabel(resourceProvider.getLabel(locale, "transferdataScreen.downloadStatus"));
/* 221 */         this.downloadStatusPanel.add(lblStatus, c);
/*     */         
/* 223 */         c.gridx = 3;
/* 224 */         c.gridy = 0;
/* 225 */         JLabel lblTimeRemain = new JLabel(resourceProvider.getLabel(locale, "transferdataScreen.time.estimated"));
/* 226 */         this.downloadStatusPanel.add(lblTimeRemain, c);
/*     */         
/* 228 */         c.gridx = 0;
/* 229 */         c.gridy = 1;
/* 230 */         c.anchor = 18;
/* 231 */         this.txtStatusDownload = new JTextField();
/* 232 */         c.insets = new Insets(5, 0, 0, 0);
/* 233 */         this.txtStatusDownload.setText(resourceProvider.getLabel(locale, "transferdataScreen.seek"));
/* 234 */         this.txtStatusDownload.setEditable(false);
/* 235 */         this.downloadStatusPanel.add(this.txtStatusDownload, c);
/*     */         
/* 237 */         c.gridx = 3;
/* 238 */         c.gridy = 1;
/* 239 */         this.txtTimeRemainDownload = new JTextField();
/* 240 */         this.txtTimeRemainDownload.setText("");
/* 241 */         this.txtTimeRemainDownload.setEditable(false);
/* 242 */         this.downloadStatusPanel.add(this.txtTimeRemainDownload, c);
/*     */         
/* 244 */         c.insets = new Insets(20, 0, 0, 0);
/* 245 */         c.gridx = 0;
/* 246 */         c.gridy = 2;
/* 247 */         JLabel lblProc1 = new JLabel("0%");
/* 248 */         this.downloadStatusPanel.add(lblProc1, c);
/*     */         
/* 250 */         c.gridx = 3;
/* 251 */         c.gridy = 2;
/* 252 */         JLabel lblProc2 = new JLabel("50%");
/* 253 */         this.downloadStatusPanel.add(lblProc2, c);
/*     */         
/* 255 */         c.insets = new Insets(5, 0, 0, 0);
/* 256 */         c.gridx = 0;
/* 257 */         c.gridy = 3;
/* 258 */         c.gridwidth = 6;
/* 259 */         this.pbarDownload = new JProgressBar(0, 0, 100);
/* 260 */         this.pbarDownload.setStringPainted(true);
/* 261 */         this.downloadStatusPanel.add(this.pbarDownload, c);
/*     */       }
/* 263 */       catch (Throwable e) {
/* 264 */         log.error("unable to load labels,getDownloadStatusPanel() , -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 267 */     return this.downloadStatusPanel;
/*     */   }
/*     */   
/*     */   private JPanel getLoadStatusPanel() {
/* 271 */     if (this.loadStatusPanel == null) {
/*     */       try {
/* 273 */         this.loadStatusPanel = new JPanel();
/* 274 */         this.loadStatusPanel.setLayout(new GridBagLayout());
/* 275 */         this.loadStatusPanel.setBorder(BorderFactory.createTitledBorder(null, resourceProvider.getLabel(locale, "transferdataScreen.loadPanel.title"), 0, 0, null, Color.black));
/*     */         
/* 277 */         GridBagConstraints c = new GridBagConstraints();
/* 278 */         c.insets = new Insets(20, 0, 0, 0);
/* 279 */         c.gridx = 0;
/* 280 */         c.gridy = 0;
/* 281 */         c.weightx = 1.0D;
/* 282 */         c.gridwidth = 3;
/* 283 */         c.fill = 2;
/* 284 */         c.anchor = 11;
/* 285 */         labelLoadStatus = new JLabel(resourceProvider.getLabel(locale, "transferdataScreen.loadStatus"));
/* 286 */         this.loadStatusPanel.add(labelLoadStatus, c);
/*     */         
/* 288 */         c.gridx = 3;
/* 289 */         c.gridy = 0;
/* 290 */         JLabel lblTimeRemain = new JLabel(resourceProvider.getLabel(locale, "transferdataScreen.time.estimated"));
/* 291 */         this.loadStatusPanel.add(lblTimeRemain, c);
/*     */         
/* 293 */         c.gridx = 0;
/* 294 */         c.gridy = 1;
/* 295 */         c.anchor = 18;
/* 296 */         txtStatusReprogram = new JTextField();
/* 297 */         c.insets = new Insets(5, 0, 0, 0);
/* 298 */         txtStatusReprogram.setText(resourceProvider.getLabel(locale, "transferdataScreen.reprogram"));
/* 299 */         txtStatusReprogram.setEditable(false);
/* 300 */         this.loadStatusPanel.add(txtStatusReprogram, c);
/*     */         
/* 302 */         c.gridx = 3;
/* 303 */         c.gridy = 1;
/* 304 */         this.txtTimeRemainReprogram = new JTextField();
/* 305 */         this.txtTimeRemainReprogram.setEditable(false);
/* 306 */         this.loadStatusPanel.add(this.txtTimeRemainReprogram, c);
/*     */         
/* 308 */         c.insets = new Insets(20, 0, 0, 0);
/* 309 */         c.gridx = 0;
/* 310 */         c.gridy = 2;
/* 311 */         JLabel lblProc1 = new JLabel("0%");
/* 312 */         this.loadStatusPanel.add(lblProc1, c);
/*     */         
/* 314 */         c.gridx = 3;
/* 315 */         c.gridy = 2;
/* 316 */         JLabel lblProc2 = new JLabel("50%");
/* 317 */         this.loadStatusPanel.add(lblProc2, c);
/*     */         
/* 319 */         c.insets = new Insets(5, 0, 0, 0);
/* 320 */         c.gridx = 0;
/* 321 */         c.gridy = 3;
/* 322 */         c.gridwidth = 6;
/* 323 */         pbarReprog = new JProgressBar(0, 0, 100);
/* 324 */         pbarReprog.setStringPainted(true);
/* 325 */         this.loadStatusPanel.add(pbarReprog, c);
/*     */       }
/* 327 */       catch (Throwable e) {
/* 328 */         log.error("unable to load labels,getDownloadStatusPanel() , -exception: " + e.getMessage());
/*     */       } 
/*     */     }
/* 331 */     return this.loadStatusPanel;
/*     */   }
/*     */   
/*     */   private void setProgressLabel(long read, long total) {
/* 335 */     this.pbarDownload.setString(String.valueOf(read) + " " + resourceProvider.getLabel(locale, "transferdataScreen.bytesDownload") + " (" + resourceProvider.getLabel(locale, "transferdataScreen.of") + " " + String.valueOf(total) + ")");
/*     */   }
/*     */   
/*     */   protected void cancelDownload() {
/* 339 */     if (this.downloadReq != null)
/* 340 */       this.downloadReq.cancelDownload(); 
/*     */   }
/*     */   
/*     */   public void onCancelAction() {
/* 344 */     cancelDownload();
/*     */   }
/*     */   
/*     */   public void onBackAction() {
/* 348 */     cancelDownload();
/*     */   }
/*     */   
/*     */   public static void indicateReprogrammingFailed() {
/* 352 */     txtStatusReprogram.setFont(txtStatusReprogram.getFont().deriveFont(1));
/* 353 */     txtStatusReprogram.setForeground(Color.red);
/* 354 */     txtStatusReprogram.setText(resourceProvider.getLabel(locale, "transferdataScreen.reprogram-failed"));
/* 355 */     if (getReprogrammingController() != null && !getReprogrammingController().equals("")) {
/* 356 */       txtStatusReprogram.setText(txtStatusReprogram.getText() + " (" + getReprogrammingController() + ")");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setReprogrammingController(String label) {
/* 362 */     int index = label.indexOf("\t");
/* 363 */     if (index != -1) {
/* 364 */       label = label.substring(index + 1, label.length());
/*     */     }
/* 366 */     controllerLabel = label;
/*     */   }
/*     */   
/*     */   public static void setReprogrammingController(String function, String position) {
/* 370 */     setReprogrammingController(function);
/* 371 */     String text = labelLoadStatus.getText();
/* 372 */     if (text.endsWith(">")) {
/* 373 */       text = text.substring(0, text.lastIndexOf('<') - 1);
/*     */     }
/* 375 */     sequenceLabel = text + " " + position;
/*     */   }
/*     */   
/*     */   public static String getReprogrammingController() {
/* 379 */     return controllerLabel;
/*     */   }
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/*     */     try {
/* 384 */       setRequestGroup(req.getRequestGroup());
/*     */       
/* 386 */       if (req instanceof DownloadProgressDisplayRequest) {
/* 387 */         handleDownloadRequest(req);
/*     */       }
/* 389 */       else if (req instanceof ReprogramProgressDisplayRequest) {
/* 390 */         handleReprogramRequest(req);
/*     */       }
/*     */     
/* 393 */     } catch (Exception e) {
/* 394 */       log.error("handleRequest() methode , -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleDownloadRequest(AssignmentRequest req) {
/* 399 */     final long requestTime = System.currentTimeMillis();
/* 400 */     downloadTime = 0;
/*     */     try {
/* 402 */       setRequestGroup(req.getRequestGroup());
/*     */       
/* 404 */       this.downloadReq = (DownloadProgressDisplayRequest)req;
/* 405 */       this.downloadReq.addObserver(new DownloadProgressDisplayRequest.Observer()
/*     */           {
/*     */             private boolean init;
/*     */ 
/*     */ 
/*     */             
/*     */             private long total;
/*     */ 
/*     */ 
/*     */             
/*     */             private long startTime;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public synchronized void onRead(List<ProgrammingDataUnit> blobs, ProgrammingDataUnit dataUnit, long byteCount) {
/* 421 */               if (!this.init) {
/* 422 */                 long total = 0L;
/* 423 */                 for (int j = 0; j < blobs.size(); j++) {
/* 424 */                   total += ((ProgrammingDataUnit)blobs.get(j)).getBlobSize().longValue();
/*     */                 }
/* 426 */                 TransferDataPanel.this.pbarDownload.setMaximum((int)total);
/* 427 */                 this.total = total;
/* 428 */                 this.init = true;
/* 429 */                 this.startTime = System.currentTimeMillis();
/*     */               } 
/*     */               
/* 432 */               long read = 0L;
/* 433 */               for (int i = 0; i < blobs.indexOf(dataUnit); i++) {
/* 434 */                 read += ((SPSBlob)blobs.get(i)).getBlobSize().longValue();
/*     */               }
/* 436 */               read += byteCount;
/*     */               
/* 438 */               TransferDataPanel.this.pbarDownload.setValue((int)read);
/* 439 */               TransferDataPanel.this.setProgressLabel(read, this.total);
/*     */               
/* 441 */               long spentTimeSoFar = System.currentTimeMillis() - this.startTime;
/* 442 */               long estimatedTotalTime = spentTimeSoFar * this.total / read;
/* 443 */               long remainTotalTime = estimatedTotalTime - spentTimeSoFar;
/*     */               
/* 445 */               TransferDataPanel.this.txtTimeRemainDownload.setText((new Timespan(remainTotalTime)).toString());
/*     */             }
/*     */             
/*     */             public synchronized void onFinished(Status status) {
/* 449 */               if (status == null || status.equals(FINISHED)) {
/* 450 */                 TransferDataPanel.this.finishDownloadProgress(System.currentTimeMillis() - requestTime);
/*     */               }
/*     */             }
/*     */           });
/* 454 */     } catch (Exception e) {
/* 455 */       log.error("handleDownloadRequest() methode , -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void handleReprogramRequest(AssignmentRequest req) {
/*     */     try {
/* 461 */       ReprogramProgressDisplayRequest rr = (ReprogramProgressDisplayRequest)req;
/*     */       
/* 463 */       if (this.pbarDownload.getValue() != this.pbarDownload.getMaximum()) {
/*     */         
/* 465 */         this.pbarDownload.setValue(this.pbarDownload.getMaximum());
/* 466 */         this.pbarDownload.setString(resourceProvider.getLabel(locale, "finish"));
/*     */       } 
/*     */       
/* 469 */       rr.addObserver(new ReprogramProgressDisplayRequest.Observer() {
/* 470 */             private long total = 0L;
/*     */             
/* 472 */             private long startTime = 0L;
/*     */             
/* 474 */             private Thread t = null;
/*     */             
/*     */             public void onStart(long totalCount) {
/* 477 */               TransferDataPanel.pbarReprog.setMaximum((int)totalCount);
/* 478 */               this.total = totalCount;
/* 479 */               this.startTime = System.currentTimeMillis();
/* 480 */               TransferDataPanel.txtStatusReprogram.setText(TransferDataPanel.resourceProvider.getLabel(TransferDataPanel.locale, "transferdataScreen.reprogram"));
/* 481 */               if (TransferDataPanel.getReprogrammingController() != null && !TransferDataPanel.getReprogrammingController().equals("")) {
/* 482 */                 if (TransferDataPanel.sequenceLabel != null) {
/* 483 */                   TransferDataPanel.labelLoadStatus.setText(TransferDataPanel.sequenceLabel);
/*     */                 }
/* 485 */                 TransferDataPanel.updateStatusReprogram(TransferDataPanel.txtStatusReprogram.getText() + " (" + TransferDataPanel.getReprogrammingController() + ")");
/*     */               } 
/* 487 */               TransferDataPanel.log.debug("------startTime : " + this.startTime);
/*     */             }
/*     */ 
/*     */             
/*     */             public void onProgress(final long actualCount) {
/* 492 */               if (this.t == null || !this.t.isAlive()) {
/* 493 */                 final long spentTimeSoFar = System.currentTimeMillis() - this.startTime;
/* 494 */                 TransferDataPanel.log.debug("spentTimeSoFar : " + spentTimeSoFar + " ,format: " + (new Timespan(spentTimeSoFar)).toString());
/* 495 */                 this.t = new Thread() {
/*     */                     public void run() {
/* 497 */                       TransferDataPanel.pbarReprog.setValue((int)actualCount);
/* 498 */                       TransferDataPanel.pbarReprog.setString(String.valueOf(actualCount) + " " + TransferDataPanel.resourceProvider.getLabel(TransferDataPanel.locale, "transferdataScreen.bytesReprogram") + " (" + TransferDataPanel.resourceProvider.getLabel(TransferDataPanel.locale, "transferdataScreen.of") + " " + String.valueOf(TransferDataPanel.null.this.total) + ")");
/* 499 */                       TransferDataPanel.log.debug("***The Progressbar displays  :" + TransferDataPanel.pbarReprog.getString());
/* 500 */                       long estimatedTotalTime = spentTimeSoFar * TransferDataPanel.null.this.total / actualCount;
/* 501 */                       TransferDataPanel.log.debug("estimatedTotalTime : " + estimatedTotalTime + " ,format: " + (new Timespan(estimatedTotalTime)).toString());
/* 502 */                       long remainTotalTime = estimatedTotalTime - spentTimeSoFar;
/* 503 */                       TransferDataPanel.log.debug("remainTotalTime : " + remainTotalTime + " ,format: " + (new Timespan(remainTotalTime)).toString());
/* 504 */                       TransferDataPanel.this.txtTimeRemainReprogram.setText((new Timespan(remainTotalTime)).toString());
/*     */                     }
/*     */                   };
/*     */                 
/* 508 */                 this.t.start();
/*     */               } else {
/* 510 */                 TransferDataPanel.log.debug("ignoring progress notification, since preceeding time analysis is still in progress");
/*     */               } 
/*     */             }
/*     */             
/*     */             public void onStatusChange(String labelKey) {
/* 515 */               TransferDataPanel.updateStatusReprogram(TransferDataPanel.resourceProvider.getLabel(TransferDataPanel.locale, labelKey));
/* 516 */               TransferDataPanel.log.debug("ON_STATUS_CHANGE : " + TransferDataPanel.resourceProvider.getLabel(TransferDataPanel.locale, labelKey));
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void onFinished() {
/* 525 */               TransferDataPanel.pbarReprog.setValue(TransferDataPanel.pbarReprog.getMaximum());
/* 526 */               TransferDataPanel.pbarReprog.setString(TransferDataPanel.resourceProvider.getLabel(TransferDataPanel.locale, "finish"));
/* 527 */               TransferDataPanel.updateStatusReprogram(TransferDataPanel.resourceProvider.getLabel(TransferDataPanel.locale, "transferdataScreen.reprogram.finish"));
/*     */             }
/*     */           });
/* 530 */     } catch (Exception e) {
/* 531 */       log.error("handleReprogramRequest() methode , -exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void updateStatusReprogram(final String label) {
/* 536 */     SwingUtilities.invokeLater(new Runnable() {
/*     */           public void run() {
/* 538 */             TransferDataPanel.txtStatusReprogram.setText(label);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static void modifyReprogrammingStatus() {
/* 544 */     String controller = getReprogrammingController();
/* 545 */     if (controller != null && !controller.equals("")) {
/* 546 */       String label = resourceProvider.getLabel(locale, "transferdataScreen.reprogram.finish") + " (" + controller + ")";
/* 547 */       updateStatusReprogram(label);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void resetSequenceReprogrammingIndicator() {
/* 552 */     pbarReprog.setValue(0);
/* 553 */     String controller = getReprogrammingController();
/* 554 */     if (controller != null && !controller.equals("")) {
/* 555 */       String label = resourceProvider.getLabel(locale, "transferdataScreen.reprogram.start") + " (" + controller + ")";
/* 556 */       updateStatusReprogram(label);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void resetReprogrammingIndicator() {
/* 561 */     pbarReprog.setValue(0);
/* 562 */     updateStatusReprogram("");
/*     */   }
/*     */   
/*     */   public static int getDownloadTime() {
/* 566 */     return downloadTime;
/*     */   }
/*     */   
/*     */   private void finishDownloadProgress(long downloadTime) {
/* 570 */     TransferDataPanel.downloadTime = (downloadTime / 1000L > 2147483647L) ? 0 : (int)(downloadTime / 1000L);
/* 571 */     this.pbarDownload.setValue(this.pbarDownload.getMaximum());
/* 572 */     this.pbarDownload.setString(resourceProvider.getLabel(locale, "finish"));
/* 573 */     this.txtStatusDownload.setText(resourceProvider.getLabel(locale, "transferdataScreen.download.finish"));
/* 574 */     this.controller.handleFinishDownload();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\template\TransferDataPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */