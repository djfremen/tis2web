/*     */ package com.eoos.scsm.v2.swing.v2;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.scsm.v2.util.progress.v2.CancellationListener;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressInfo;
/*     */ import com.eoos.scsm.v2.util.progress.v2.ProgressObserver;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Arrays;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.SwingUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProgressDialog
/*     */   extends JDialog
/*     */   implements ProgressObserver
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JLabel labelMessage;
/*     */   private JProgressBar progressBar;
/*  67 */   private JButton buttonCancel = null;
/*     */   
/*  69 */   private final Object SYNC_CANCELLISTENER = new Object();
/*     */   
/*  71 */   private CancellationListener cancellationListener = null; private final Object SYNC_DISPOSED;
/*     */   private boolean disposed;
/*     */   
/*  74 */   private ProgressDialog(Dialog owner, Callback callback) throws HeadlessException { super(owner);
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
/* 192 */     this.SYNC_DISPOSED = new Object();
/*     */     
/* 194 */     this.disposed = false; init(callback); } private ProgressDialog(Frame owner, Callback callback) throws HeadlessException { super(owner); this.SYNC_DISPOSED = new Object(); this.disposed = false; init(callback); } private void adjustSizeAndPosition() { pack(); } private void init(Callback callback) { addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { ProgressDialog.this.onCancel(); } }
/*     */       ); setUndecorated(false); JPanel panel = new JPanel(); ComponentListener cl = new ComponentListener() { public void componentShown(ComponentEvent e) { ProgressDialog.this.adjustSizeAndPosition(); } public void componentResized(ComponentEvent e) { ProgressDialog.this.adjustSizeAndPosition(); } public void componentMoved(ComponentEvent e) {} public void componentHidden(ComponentEvent e) { ProgressDialog.this.adjustSizeAndPosition(); } }
/*     */       ; setContentPane(panel); this.labelMessage = new JLabel(); this.labelMessage.addComponentListener(cl); panel.add(this.labelMessage); this.progressBar = new JProgressBar(); this.progressBar.setStringPainted(true); Dimension dimension = this.progressBar.getPreferredSize(); char[] tmp = new char[Math.max(10, callback.getEstimatedMessageWidth())]; Arrays.fill(tmp, 'm'); int width = SwingUtilities.computeStringWidth(this.labelMessage.getFontMetrics(this.labelMessage.getFont()), new String(tmp)); this.progressBar.setPreferredSize(new Dimension(width, Math.max(dimension.height, 20))); this.progressBar.addComponentListener(cl); panel.add(this.progressBar); this.buttonCancel = new JButton(callback.getLabel("cancel")); this.buttonCancel.addComponentListener(cl); this.buttonCancel.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { ProgressDialog.this.onCancel(); } }
/* 197 */       ); panel.add(this.buttonCancel); this.buttonCancel.setVisible(false); GridBagLayout layout = new GridBagLayout(); panel.setLayout(layout); GridBagConstraints gbc = new GridBagConstraints(); gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(5, 5, 5, 5); gbc.fill = 2; gbc.anchor = 17; layout.setConstraints(this.labelMessage, gbc); gbc = new GridBagConstraints(); gbc.gridx = 0; gbc.gridy = 1; gbc.insets = new Insets(5, 5, 5, 5); gbc.fill = 2; layout.setConstraints(this.progressBar, gbc); gbc = new GridBagConstraints(); gbc.gridx = 0; gbc.gridy = 2; gbc.insets = new Insets(5, 5, 5, 5); gbc.fill = 0; layout.setConstraints(this.buttonCancel, gbc); } private boolean isDisposed() { synchronized (this.SYNC_DISPOSED)
/* 198 */     { return this.disposed; }  } public static interface Callback {
/*     */     String getLabel(String param1String); int getEstimatedMessageWidth();
/*     */   } private void onCancel() { synchronized (this.SYNC_CANCELLISTENER) { if (this.cancellationListener != null)
/*     */         (new Thread() { public void run() { ProgressDialog.this.cancellationListener.onCancel(); } }
/*     */           ).start();  }
/* 203 */      } public void dispose() { synchronized (this.SYNC_DISPOSED) {
/* 204 */       super.dispose();
/* 205 */       this.disposed = true;
/*     */     }  }
/*     */ 
/*     */   
/*     */   public void onProgress(final ProgressInfo info) {
/* 210 */     if (isDisposed()) {
/*     */       return;
/*     */     }
/*     */     
/* 214 */     Runnable runnable = new Runnable()
/*     */       {
/*     */         public void run() {
/* 217 */           if (info == ProgressInfo.FINSIHED) {
/* 218 */             ProgressDialog.this.dispose();
/*     */             
/*     */             return;
/*     */           } 
/* 222 */           String message = null;
/* 223 */           int progress = -1;
/* 224 */           int maxProgress = -1;
/*     */           
/* 226 */           if (info instanceof ProgressInfoRI) {
/* 227 */             ProgressInfoRI pi = (ProgressInfoRI)info;
/* 228 */             message = pi.getMessage();
/* 229 */             maxProgress = pi.getMaxProgress();
/* 230 */             progress = pi.getProgress();
/*     */           } else {
/* 232 */             message = String.valueOf(info);
/*     */           } 
/*     */           
/* 235 */           if (!Util.equals(ProgressDialog.this.labelMessage.getText(), message)) {
/* 236 */             ProgressDialog.this.labelMessage.setText(message);
/*     */           }
/*     */           
/* 239 */           if (maxProgress == -1 || progress == -1) {
/* 240 */             ProgressDialog.this.progressBar.setIndeterminate(true);
/* 241 */             ProgressDialog.this.progressBar.setString("");
/*     */           } else {
/* 243 */             ProgressDialog.this.progressBar.setIndeterminate(false);
/* 244 */             ProgressDialog.this.progressBar.setString((String)null);
/* 245 */             if (ProgressDialog.this.progressBar.getMaximum() != maxProgress) {
/* 246 */               ProgressDialog.this.progressBar.setMaximum(maxProgress);
/*     */             }
/* 248 */             if (ProgressDialog.this.progressBar.getValue() != progress) {
/* 249 */               ProgressDialog.this.progressBar.setValue(progress);
/*     */             }
/*     */           } 
/*     */           
/* 253 */           if (!ProgressDialog.this.isVisible()) {
/* 254 */             ProgressDialog.this.pack();
/* 255 */             ProgressDialog.this.setLocationRelativeTo(ProgressDialog.this.getParent());
/* 256 */             ProgressDialog.this.setVisible(true);
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 262 */     Util.executeOnAWTThread(runnable, false);
/*     */   }
/*     */   
/*     */   public void setCancellationListener(final CancellationListener listener) {
/* 266 */     Runnable runnable = new Runnable()
/*     */       {
/*     */         public void run() {
/* 269 */           synchronized (ProgressDialog.this.SYNC_CANCELLISTENER) {
/* 270 */             if (listener != null) {
/* 271 */               ProgressDialog.this.buttonCancel.setVisible(true);
/* 272 */               ProgressDialog.this.validate();
/*     */             } else {
/* 274 */               ProgressDialog.this.buttonCancel.setVisible(false);
/*     */             } 
/* 276 */             ProgressDialog.this.cancellationListener = listener;
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 282 */     Util.executeOnAWTThread(runnable, false);
/*     */   }
/*     */   
/*     */   public static ProgressObserver create(final Window parent, final Callback callback, final boolean modal) {
/* 286 */     final Object sync = new Object();
/* 287 */     final ProgressObserver[] ret = new ProgressObserver[1];
/* 288 */     synchronized (sync) {
/* 289 */       Util.executeOnAWTThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 292 */               ProgressDialog pd = null;
/* 293 */               if (parent == null || parent instanceof Frame) {
/* 294 */                 pd = new ProgressDialog((Frame)parent, callback);
/*     */               } else {
/* 296 */                 pd = new ProgressDialog((Dialog)parent, callback);
/*     */               } 
/* 298 */               pd.setModal(modal);
/* 299 */               synchronized (sync) {
/* 300 */                 ret[0] = pd;
/* 301 */                 sync.notify();
/*     */               } 
/*     */             }
/*     */           }false);
/*     */ 
/*     */       
/*     */       try {
/* 308 */         sync.wait();
/* 309 */       } catch (InterruptedException e) {
/* 310 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 315 */     return ret[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main1(String[] args) {
/* 320 */     ProgressObserver po = create((Window)null, new Callback()
/*     */         {
/*     */           public String getLabel(String key) {
/* 323 */             return Util.capitalize(key, null);
/*     */           }
/*     */           
/*     */           public int getEstimatedMessageWidth() {
/* 327 */             return 30;
/*     */           }
/*     */         }true);
/*     */ 
/*     */     
/* 332 */     final Thread executionThread = Thread.currentThread();
/*     */     try {
/* 334 */       po.setCancellationListener(new CancellationListener()
/*     */           {
/*     */             public void onCancel() {
/* 337 */               executionThread.interrupt();
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 342 */       String[] status = { "Reading file: d:/tmp/edir.properties", "zwei", "drei und dreieinhalb", "vier das ist jetzt ein ganz langer status, vier das ist jetzt ein ganz langer status,der hat sehr viele Zeichen", "fÃ¼nf" };
/*     */       
/* 344 */       po.onProgress(new ProgressInfoRI("Initializing...", 1, 10));
/* 345 */       Thread.sleep(1000L);
/*     */       
/* 347 */       for (int i = 0; i < status.length; i++) {
/* 348 */         final String message = status[i];
/* 349 */         if (i < 2) {
/* 350 */           po.onProgress(new ProgressInfo() {
/*     */                 public String toString() {
/* 352 */                   return message;
/*     */                 }
/*     */               });
/*     */         } else {
/* 356 */           po.onProgress(new ProgressInfoRI(message, i, status.length));
/*     */         } 
/*     */         
/* 359 */         Thread.sleep(1000L);
/*     */       } 
/* 361 */     } catch (InterruptedException e) {
/* 362 */       System.out.println("interrupted");
/*     */     } 
/* 364 */     po.onProgress(ProgressInfo.FINSIHED);
/* 365 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main2(String[] args) {
/* 370 */     JFrame frame = new JFrame("mein Frame");
/* 371 */     frame.setBounds(new Rectangle(100, 100));
/* 372 */     frame.setVisible(true);
/*     */     
/* 374 */     ProgressObserver po = create(frame, new Callback()
/*     */         {
/*     */           public String getLabel(String key) {
/* 377 */             return Util.capitalize(key, null);
/*     */           }
/*     */           
/*     */           public int getEstimatedMessageWidth() {
/* 381 */             return 30;
/*     */           }
/*     */         }false);
/*     */ 
/*     */     
/* 386 */     final Thread executionThread = Thread.currentThread();
/*     */     try {
/* 388 */       po.setCancellationListener(new CancellationListener()
/*     */           {
/*     */             public void onCancel() {
/* 391 */               executionThread.interrupt();
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 396 */       String[] status = { "Reading file: d:/tmp/edir.properties", "zwei", "drei und dreieinhalb", "vier das ist jetzt ein ganz langer status, vier das ist jetzt ein ganz langer status,der hat sehr viele Zeichen", "fÃ¼nf" };
/*     */       
/* 398 */       po.onProgress(new ProgressInfoRI("Initializing...", 1, 10));
/* 399 */       Thread.sleep(1000L);
/*     */       
/* 401 */       for (int i = 0; i < status.length; i++) {
/* 402 */         final String message = status[i];
/* 403 */         if (i < 2) {
/* 404 */           po.onProgress(new ProgressInfo() {
/*     */                 public String toString() {
/* 406 */                   return message;
/*     */                 }
/*     */               });
/*     */         } else {
/* 410 */           po.onProgress(new ProgressInfoRI(message, i, status.length));
/*     */         } 
/*     */         
/* 413 */         Thread.sleep(1000L);
/*     */       } 
/* 415 */     } catch (InterruptedException e) {
/* 416 */       System.out.println("interrupted");
/*     */     } 
/* 418 */     po.onProgress(ProgressInfo.FINSIHED);
/* 419 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 424 */     main1(args);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\v2\ProgressDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */