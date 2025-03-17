/*     */ package com.eoos.scsm.v2.swing;
/*     */ 
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.LineBorder;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UIUtil
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(UIUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class RestoreI
/*     */     implements Restore
/*     */   {
/*  56 */     private Map<Component, Boolean> enablingState = new HashMap<Component, Boolean>();
/*     */     
/*     */     public void restore() {
/*  59 */       Util.executeOnAWTThread(new Runnable()
/*     */           {
/*     */             public void run() {
/*  62 */               for (Iterator<Map.Entry<Component, Boolean>> iter = UIUtil.RestoreI.this.enablingState.entrySet().iterator(); iter.hasNext(); ) {
/*  63 */                 Map.Entry<Component, Boolean> entry = iter.next();
/*  64 */                 ((Component)entry.getKey()).setEnabled(((Boolean)entry.getValue()).booleanValue());
/*     */               } 
/*     */             }
/*     */           },  true);
/*     */     }
/*     */     
/*     */     private RestoreI() {}
/*     */   }
/*     */   
/*     */   public static Restore setEnabledV2(final Component component, final boolean enabled) {
/*  74 */     final RestoreI ret = new RestoreI();
/*  75 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*  78 */             UIUtil._setEnabledV2(component, enabled, ret);
/*     */           }
/*     */         }true);
/*     */     
/*  82 */     return ret;
/*     */   }
/*     */   
/*     */   private static void _setEnabledV2(Component component, boolean enabled, RestoreI restore) {
/*  86 */     if (component != null) {
/*  87 */       if (component instanceof Container) {
/*  88 */         for (int i = 0; i < ((Container)component).getComponentCount(); i++) {
/*  89 */           _setEnabledV2(((Container)component).getComponent(i), enabled, restore);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*  94 */       if (component.getParent() != null && component.isEnabled() != enabled) {
/*  95 */         restore.enablingState.put(component, Boolean.valueOf(component.isEnabled()));
/*  96 */         component.setEnabled(enabled);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setEnabled(Component component, boolean enabled) {
/* 103 */     if (component != null) {
/* 104 */       if (component instanceof Container)
/*     */       {
/* 106 */         for (int i = 0; i < ((Container)component).getComponentCount(); i++) {
/* 107 */           setEnabled(((Container)component).getComponent(i), enabled);
/*     */         }
/*     */       }
/*     */       
/* 111 */       component.setEnabled(enabled);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Point getScreenCenterLocation(Window window) {
/* 116 */     Dimension dimScreen = window.getToolkit().getScreenSize();
/* 117 */     return getCenterLocation(window, dimScreen, null);
/*     */   }
/*     */   
/*     */   public static Point getCenterLocation(Window window, Window container) {
/* 121 */     return getCenterLocation(window, container.getSize(), container.getLocation());
/*     */   }
/*     */   
/*     */   public static Point getCenterLocation(Window window, Dimension containerDimension, Point containerLocation) {
/* 125 */     Dimension dimWindow = window.getSize();
/* 126 */     Point retValue = new Point();
/*     */     
/* 128 */     retValue.x = (containerDimension.width - dimWindow.width) / 2;
/* 129 */     retValue.y = (containerDimension.height - dimWindow.height) / 2;
/* 130 */     if (containerLocation != null) {
/* 131 */       retValue.x += containerLocation.x;
/* 132 */       retValue.y += containerLocation.y;
/*     */     } 
/* 134 */     return retValue;
/*     */   }
/*     */   
/*     */   public static void recursiveAddBorder(Component component) {
/* 138 */     if (component instanceof JComponent) {
/* 139 */       ((JComponent)component).setBorder(new LineBorder(Color.BLACK));
/*     */     }
/* 141 */     if (component instanceof Container) {
/* 142 */       Component[] children = ((Container)component).getComponents();
/* 143 */       for (int i = 0; i < children.length; i++) {
/* 144 */         recursiveAddBorder(children[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void recursiveSetVisible(Component component) {
/* 150 */     if (component instanceof JComponent) {
/* 151 */       ((JComponent)component).setVisible(true);
/*     */     }
/* 153 */     if (component instanceof Container) {
/* 154 */       Component[] children = ((Container)component).getComponents();
/* 155 */       for (int i = 0; i < children.length; i++) {
/* 156 */         recursiveSetVisible(children[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void showWarningPopup(final Component component, final String msg) {
/* 162 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 165 */             UIUtil.setEnabled(component, false);
/*     */             try {
/* 167 */               JOptionPane.showMessageDialog(component, msg, null, 2);
/*     */             } finally {
/* 169 */               UIUtil.setEnabled(component, true);
/*     */             } 
/*     */           }
/*     */         }true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void showErrorPopup(final Component component, final String msg) {
/* 177 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 180 */             UIUtil.setEnabled(component, false);
/*     */             try {
/* 182 */               JOptionPane.showMessageDialog(component, msg, null, 0);
/*     */             } finally {
/* 184 */               UIUtil.setEnabled(component, true);
/*     */             } 
/*     */           }
/*     */         }true);
/*     */   }
/*     */   
/*     */   public static void showErrorPopupAndClose(final Window window, final String msg) {
/* 191 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 194 */             UIUtil.setEnabled(window, false);
/*     */             try {
/* 196 */               JOptionPane.showMessageDialog(window, msg, null, 0);
/*     */             } finally {
/* 198 */               UIUtil.setEnabled(window, true);
/*     */             } 
/* 200 */             window.dispose();
/*     */           }
/*     */         }true);
/*     */   }
/*     */   
/*     */   public static void close(final Window window) {
/* 206 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 209 */             window.dispose();
/*     */           }
/*     */         },  true);
/*     */   }
/*     */   
/*     */   public static void showSuccessPopup(final Component component, final String msg) {
/* 215 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 218 */             UIUtil.setEnabled(component, false);
/*     */             try {
/* 220 */               JOptionPane.showMessageDialog(component, msg, null, 1);
/*     */             } finally {
/* 222 */               UIUtil.setEnabled(component, true);
/*     */             } 
/*     */           }
/*     */         }true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void showSuccessPopupAndClose(final Window window, final String msg) {
/* 230 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/* 233 */             UIUtil.setEnabled(window, false);
/*     */             try {
/* 235 */               JOptionPane.showMessageDialog(window, msg, null, 1);
/*     */             } finally {
/* 237 */               UIUtil.setEnabled(window, true);
/*     */             } 
/* 239 */             window.dispose();
/*     */           }
/*     */         }true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DialogHandle showActivityDialog(final Object owner, final String msg) {
/* 250 */     final DialogHandle[] ret = new DialogHandle[1];
/* 251 */     Util.executeOnAWTThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             final JDialog dialog;
/* 255 */             if (owner instanceof Frame) {
/* 256 */               dialog = new JDialog((Frame)owner);
/* 257 */             } else if (owner instanceof Dialog) {
/* 258 */               dialog = new JDialog((Dialog)owner);
/*     */             } else {
/* 260 */               dialog = new JDialog();
/*     */             } 
/* 262 */             dialog.getContentPane().setLayout(new GridBagLayout());
/* 263 */             GridBagConstraints c = new GridBagConstraints();
/* 264 */             c.gridx = 0;
/* 265 */             c.fill = 2;
/* 266 */             c.weightx = 1.0D;
/* 267 */             c.insets = new Insets(2, 5, 2, 5);
/*     */             
/* 269 */             if (msg != null) {
/* 270 */               JLabel label = new JLabel(msg);
/* 271 */               dialog.getContentPane().add(label, c);
/*     */             } 
/*     */             
/* 274 */             JProgressBar progessBar = new JProgressBar();
/* 275 */             progessBar.setIndeterminate(true);
/* 276 */             progessBar.setString(msg);
/* 277 */             dialog.getContentPane().add(progessBar, c);
/*     */             
/* 279 */             dialog.setModal(false);
/* 280 */             dialog.pack();
/* 281 */             if (owner instanceof Component) {
/* 282 */               dialog.setLocationRelativeTo((Component)owner);
/*     */             }
/* 284 */             UIUtil.setEnabled(dialog.getOwner(), false);
/* 285 */             dialog.setVisible(true);
/*     */             
/* 287 */             synchronized (ret) {
/* 288 */               ret[0] = new UIUtil.DialogHandle()
/*     */                 {
/*     */                   public void closeDialog() {
/* 291 */                     Util.executeOnAWTThread(new Runnable()
/*     */                         {
/*     */                           public void run() {
/* 294 */                             dialog.setVisible(false);
/* 295 */                             UIUtil.setEnabled(dialog.getOwner(), true);
/*     */                             
/* 297 */                             dialog.dispose();
/*     */                           }
/*     */                         }false);
/*     */                   }
/*     */                 };
/*     */               
/* 303 */               dialog.addWindowListener(new WindowAdapter() {
/*     */                     public void windowClosing(WindowEvent event) {
/* 305 */                       ret[0].closeDialog();
/*     */                     }
/*     */                   });
/* 308 */               ret.notify();
/*     */             } 
/*     */           }
/*     */         }true);
/*     */     
/* 313 */     return ret[0];
/*     */   }
/*     */   
/*     */   public static void testPanel(final JPanel panel, final boolean drawBorders) {
/* 317 */     final Object sync = new Object();
/* 318 */     synchronized (sync) {
/* 319 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 322 */               JFrame frame = new JFrame();
/* 323 */               frame.getContentPane().add(panel);
/*     */               
/* 325 */               frame.addWindowListener(new WindowAdapter()
/*     */                   {
/*     */                     public void windowClosing(WindowEvent e) {
/* 328 */                       super.windowClosing(e);
/* 329 */                       synchronized (sync) {
/* 330 */                         sync.notify();
/*     */                       } 
/*     */                     }
/*     */                   });
/*     */               
/* 335 */               if (drawBorders) {
/* 336 */                 UIUtil.recursiveAddBorder(frame);
/*     */               }
/* 338 */               frame.pack();
/* 339 */               frame.setLocationRelativeTo((Component)null);
/* 340 */               frame.setVisible(true);
/*     */             }
/*     */           });
/*     */       try {
/* 344 */         sync.wait();
/* 345 */       } catch (InterruptedException e) {
/* 346 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Dimension scale(Dimension dimension, float factorWidth, float factorHeight) {
/* 353 */     if (dimension != null) {
/* 354 */       return new Dimension(Math.round(dimension.width * factorWidth), Math.round(dimension.height * factorHeight));
/*     */     }
/* 356 */     return null;
/*     */   }
/*     */   
/*     */   public static interface ProgressObserver
/*     */   {
/* 361 */     public static final ProgressObserver DUMMY = new ProgressObserver()
/*     */       {
/*     */         public void setProgress(String message) {}
/*     */ 
/*     */         
/*     */         public void setProgress(String message, int value, int maximum) {}
/*     */ 
/*     */         
/*     */         public void close() {}
/*     */       };
/*     */ 
/*     */     
/*     */     void setProgress(String param1String, int param1Int1, int param1Int2);
/*     */     
/*     */     void setProgress(String param1String);
/*     */     
/*     */     void close();
/*     */   }
/*     */   
/*     */   public static ProgressObserver showProgressObserver(Window owner, I18NSupport.FixedLocale i18nSupport, String initialMsg) {
/* 381 */     return showProgressObserver(owner, i18nSupport, i18nSupport, initialMsg, false);
/*     */   }
/*     */   
/*     */   public static ProgressObserver showProgressObserver(Window owner, I18NSupport.FixedLocale labelResource, I18NSupport.FixedLocale msgResource, String initialMsg) {
/* 385 */     return showProgressObserver(owner, labelResource, msgResource, initialMsg, false);
/*     */   }
/*     */   
/*     */   public static ProgressObserver showProgressObserver(final Window owner, final I18NSupport.FixedLocale labelResource, final I18NSupport.FixedLocale msgResource, final String initialMsg, final boolean alwaysOnTop) {
/* 389 */     Util.ensureNotAWTThread();
/*     */     
/* 391 */     final Thread executionThread = Thread.currentThread();
/* 392 */     final ProgressObserver[] ret = { null };
/*     */     
/* 394 */     final Object sync = new Object();
/* 395 */     synchronized (sync) {
/* 396 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */               final JDialog dialog;
/* 400 */               JPanel rootPanel = new JPanel(new GridBagLayout());
/* 401 */               GridBagConstraints c = new GridBagConstraints();
/* 402 */               c.gridx = 0;
/* 403 */               c.fill = 2;
/* 404 */               c.weightx = 1.0D;
/* 405 */               c.insets = new Insets(2, 5, 2, 5);
/*     */ 
/*     */               
/* 408 */               final JLabel label = new JLabel();
/* 409 */               if (initialMsg != null) {
/* 410 */                 label.setText(msgResource.getText(initialMsg));
/*     */               }
/* 412 */               rootPanel.add(label, c);
/*     */ 
/*     */               
/* 415 */               final JProgressBar progressBar = new JProgressBar();
/* 416 */               progressBar.setIndeterminate(true);
/* 417 */               progressBar.setStringPainted(false);
/*     */               
/* 419 */               rootPanel.add(progressBar, c);
/*     */ 
/*     */               
/* 422 */               c.fill = 0;
/* 423 */               JButton button = new JButton(labelResource.getText("cancel"));
/* 424 */               button.addActionListener(new ActionListener()
/*     */                   {
/*     */                     public void actionPerformed(ActionEvent e) {
/* 427 */                       Util.interrupt(executionThread);
/*     */                     }
/*     */                   });
/*     */               
/* 431 */               rootPanel.add(button, c);
/*     */ 
/*     */ 
/*     */               
/* 435 */               if (owner instanceof Frame) {
/* 436 */                 dialog = new JDialog((Frame)owner);
/* 437 */               } else if (owner instanceof Dialog) {
/* 438 */                 dialog = new JDialog((Dialog)owner);
/*     */               } else {
/* 440 */                 dialog = new JDialog();
/*     */               } 
/* 442 */               dialog.setContentPane(rootPanel);
/*     */               
/* 444 */               dialog.setModal(true);
/* 445 */               dialog.setAlwaysOnTop(alwaysOnTop);
/*     */               
/* 447 */               dialog.pack();
/* 448 */               dialog.setLocationRelativeTo(owner);
/*     */               
/* 450 */               dialog.addWindowListener(new WindowAdapter()
/*     */                   {
/*     */                     public void windowClosing(WindowEvent event) {
/* 453 */                       executionThread.interrupt();
/* 454 */                       dialog.dispose();
/*     */                     }
/*     */                   });
/*     */ 
/*     */               
/* 459 */               synchronized (sync) {
/* 460 */                 ret[0] = new UIUtil.ProgressObserver()
/*     */                   {
/*     */                     public void setProgress(final String message, final int current, final int maximum) {
/* 463 */                       Util.executeOnAWTThread(new Runnable()
/*     */                           {
/*     */                             public void run() {
/* 466 */                               if (message != null) {
/* 467 */                                 label.setText(msgResource.getText(message));
/* 468 */                                 int newWidth = SwingUtilities.computeStringWidth(progressBar.getFontMetrics(progressBar.getFont()), message);
/* 469 */                                 UIUtil.log.debug("current width: " + label.getWidth());
/* 470 */                                 UIUtil.log.debug("new width: " + newWidth);
/*     */                                 
/* 472 */                                 if (newWidth > label.getWidth() || newWidth / label.getWidth() < 0.30000001192092896D) {
/* 473 */                                   UIUtil.log.debug("resizing dialog");
/* 474 */                                   dialog.pack();
/* 475 */                                   dialog.setLocationRelativeTo(dialog.getOwner());
/*     */                                 } 
/*     */                               } 
/* 478 */                               if (current > 0 && maximum > 0) {
/* 479 */                                 progressBar.setIndeterminate(false);
/* 480 */                                 progressBar.setStringPainted(true);
/* 481 */                                 progressBar.setMaximum(maximum);
/* 482 */                                 progressBar.setValue(current);
/*     */                               } else {
/* 484 */                                 progressBar.setIndeterminate(true);
/* 485 */                                 progressBar.setStringPainted(false);
/*     */                               } 
/*     */                             }
/*     */                           }false);
/*     */                     }
/*     */ 
/*     */                     
/*     */                     public void setProgress(String message) {
/* 493 */                       setProgress(message, -1, -1);
/*     */                     }
/*     */                     
/*     */                     public void close() {
/* 497 */                       Util.executeOnAWTThread(new Runnable()
/*     */                           {
/*     */                             public void run() {
/* 500 */                               dialog.setVisible(false);
/*     */                               
/* 502 */                               dialog.dispose();
/*     */                             }
/*     */                           },  false);
/*     */                     }
/*     */                   };
/*     */ 
/*     */                 
/* 509 */                 sync.notify();
/*     */               } 
/* 511 */               dialog.setVisible(true);
/*     */             }
/*     */           });
/*     */       
/*     */       try {
/* 516 */         sync.wait();
/* 517 */       } catch (InterruptedException e) {
/* 518 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */       
/* 521 */       return ret[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ProgressObserver showProgressObserverV2(final Window owner, final I18NSupport.FixedLocale labelResource) {
/* 527 */     Util.ensureNotAWTThread();
/*     */     
/* 529 */     final Thread executionThread = Thread.currentThread();
/* 530 */     final ProgressObserver[] ret = { null };
/*     */     
/* 532 */     final Object sync = new Object();
/* 533 */     synchronized (sync) {
/* 534 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */               final JDialog dialog;
/* 538 */               JPanel rootPanel = new JPanel(new GridBagLayout());
/* 539 */               GridBagConstraints c = new GridBagConstraints();
/* 540 */               c.gridx = 0;
/* 541 */               c.fill = 2;
/* 542 */               c.weightx = 1.0D;
/* 543 */               c.insets = new Insets(5, 5, 2, 5);
/*     */ 
/*     */               
/* 546 */               final JLabel label = new JLabel();
/* 547 */               rootPanel.add(label, c);
/*     */               
/* 549 */               c.insets = new Insets(2, 5, 2, 5);
/*     */ 
/*     */               
/* 552 */               final JProgressBar progressBar = new JProgressBar();
/* 553 */               progressBar.setIndeterminate(true);
/* 554 */               progressBar.setStringPainted(false);
/*     */               
/* 556 */               rootPanel.add(progressBar, c);
/*     */ 
/*     */               
/* 559 */               c.fill = 0;
/* 560 */               JButton button = new JButton(labelResource.getText("cancel"));
/* 561 */               button.addActionListener(new ActionListener()
/*     */                   {
/*     */                     public void actionPerformed(ActionEvent e) {
/* 564 */                       executionThread.interrupt();
/*     */                     }
/*     */                   });
/* 567 */               c.insets = new Insets(2, 5, 5, 5);
/*     */               
/* 569 */               rootPanel.add(button, c);
/*     */ 
/*     */ 
/*     */               
/* 573 */               if (owner instanceof Frame) {
/* 574 */                 dialog = new JDialog((Frame)owner);
/* 575 */               } else if (owner instanceof Dialog) {
/* 576 */                 dialog = new JDialog((Dialog)owner);
/*     */               } else {
/* 578 */                 dialog = new JDialog();
/*     */               } 
/* 580 */               dialog.setContentPane(rootPanel);
/*     */               
/* 582 */               dialog.setModal(true);
/*     */               
/* 584 */               dialog.pack();
/* 585 */               dialog.setLocationRelativeTo(owner);
/*     */               
/* 587 */               dialog.addWindowListener(new WindowAdapter()
/*     */                   {
/*     */                     public void windowClosing(WindowEvent event) {
/* 590 */                       executionThread.interrupt();
/* 591 */                       dialog.dispose();
/*     */                     }
/*     */                   });
/*     */ 
/*     */               
/* 596 */               synchronized (sync) {
/* 597 */                 ret[0] = new UIUtil.ProgressObserver()
/*     */                   {
/*     */                     public void setProgress(final String message, final int current, final int maximum) {
/* 600 */                       Util.executeOnAWTThread(new Runnable()
/*     */                           {
/*     */                             public void run() {
/* 603 */                               if (message != null) {
/* 604 */                                 label.setText(message);
/* 605 */                                 int newWidth = SwingUtilities.computeStringWidth(progressBar.getFontMetrics(progressBar.getFont()), message);
/* 606 */                                 UIUtil.log.debug("current width: " + label.getWidth());
/* 607 */                                 UIUtil.log.debug("new width: " + newWidth);
/*     */                                 
/* 609 */                                 if (newWidth > label.getWidth() || newWidth / label.getWidth() < 0.30000001192092896D) {
/* 610 */                                   UIUtil.log.debug("resizing dialog");
/* 611 */                                   dialog.pack();
/* 612 */                                   dialog.setLocationRelativeTo(dialog.getOwner());
/*     */                                 } 
/*     */                               } 
/* 615 */                               if (current > 0 && maximum > 0) {
/* 616 */                                 progressBar.setIndeterminate(false);
/* 617 */                                 progressBar.setStringPainted(true);
/* 618 */                                 progressBar.setMaximum(maximum);
/* 619 */                                 progressBar.setValue(current);
/*     */                               } else {
/* 621 */                                 progressBar.setIndeterminate(true);
/* 622 */                                 progressBar.setStringPainted(false);
/*     */                               } 
/*     */                             }
/*     */                           }false);
/*     */                     }
/*     */ 
/*     */                     
/*     */                     public void setProgress(String message) {
/* 630 */                       setProgress(message, -1, -1);
/*     */                     }
/*     */                     
/*     */                     public void close() {
/* 634 */                       Util.executeOnAWTThread(new Runnable()
/*     */                           {
/*     */                             public void run() {
/* 637 */                               dialog.setVisible(false);
/*     */                               
/* 639 */                               dialog.dispose();
/*     */                             }
/*     */                           },  true);
/*     */                     }
/*     */                   };
/*     */ 
/*     */                 
/* 646 */                 sync.notify();
/*     */               } 
/* 648 */               UIUtil.showDialog(dialog);
/*     */             }
/*     */           });
/*     */       
/*     */       try {
/* 653 */         sync.wait();
/* 654 */       } catch (InterruptedException e) {
/* 655 */         Thread.currentThread().interrupt();
/*     */       } 
/*     */       
/* 658 */       return ret[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 664 */     Log4jUtil.attachConsoleAppender();
/*     */     
/* 666 */     ProgressObserver observer = showProgressObserver(null, I18NSupport.FixedLocale.FALLBACK, null);
/*     */ 
/*     */     
/*     */     try {
/* 670 */       String[] msg = { "Preparing...", "Extracting ...", "Extracting ...", "Extracting ...", "Cleaning up temporary directory d:/localsettings/user/smui/Testdir ...", "Cleaning up temporary directory d:/local ...", "Finish..." };
/* 671 */       for (int i = 0; i < msg.length; i++) {
/* 672 */         observer.setProgress(msg[i], -1, -1);
/* 673 */         Thread.sleep(1000L);
/*     */       } 
/* 675 */     } catch (InterruptedException e) {
/* 676 */       log.info("interruped");
/* 677 */       Thread.currentThread().interrupt();
/*     */     }
/*     */     finally {
/*     */       
/* 681 */       observer.close();
/*     */     } 
/* 683 */     log.info("dialog should have been disposed");
/*     */     try {
/* 685 */       Thread.sleep(2000L);
/* 686 */     } catch (InterruptedException e) {
/* 687 */       throw new RuntimeException(e);
/*     */     } finally {
/*     */       
/* 690 */       System.exit(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Window getWindow(Component component) {
/* 695 */     Window ret = null;
/* 696 */     if (component != null) {
/* 697 */       Container container = (component instanceof Container) ? (Container)component : component.getParent();
/* 698 */       while (ret == null && container != null) {
/* 699 */         if (container instanceof Window) {
/* 700 */           ret = (Window)container; continue;
/*     */         } 
/* 702 */         container = container.getParent();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 707 */     return ret;
/*     */   }
/*     */   
/*     */   public static Frame getFrame(Component component) {
/* 711 */     Frame ret = null;
/* 712 */     if (component != null) {
/* 713 */       Container container = (component instanceof Container) ? (Container)component : component.getParent();
/* 714 */       while (ret == null && container != null) {
/* 715 */         if (container instanceof Frame) {
/* 716 */           ret = (Frame)container; continue;
/*     */         } 
/* 718 */         container = container.getParent();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 723 */     return ret;
/*     */   }
/*     */   
/*     */   public static Dialog getDialog(Component component) {
/* 727 */     Dialog ret = null;
/* 728 */     if (component != null) {
/* 729 */       Container container = (component instanceof Container) ? (Container)component : component.getParent();
/* 730 */       while (ret == null && container != null) {
/* 731 */         if (container instanceof Dialog) {
/* 732 */           ret = (Dialog)container; continue;
/*     */         } 
/* 734 */         container = container.getParent();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 739 */     return ret;
/*     */   }
/*     */   
/*     */   public static JDialog createDialog(Component parent) {
/* 743 */     if (getDialog(parent) != null) {
/* 744 */       return new JDialog(getDialog(parent));
/*     */     }
/* 746 */     return new JDialog(getFrame(parent));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static GridBagConstraints getRelativePosition(GridBagConstraints c, int offsetX, int offsetY) {
/* 752 */     GridBagConstraints ret = new GridBagConstraints();
/* 753 */     if (c.gridx != -1) {
/* 754 */       c.gridx += offsetX;
/* 755 */     } else if (offsetX != 0) {
/* 756 */       throw new RuntimeException("cannot calculate with position specification 'RELATIVE'");
/*     */     } 
/*     */     
/* 759 */     if (c.gridy != -1) {
/* 760 */       c.gridy += offsetY;
/* 761 */     } else if (offsetY != 0) {
/* 762 */       throw new RuntimeException("cannot calculate with position specification 'RELATIVE'");
/*     */     } 
/*     */     
/* 765 */     return ret;
/*     */   }
/*     */   
/*     */   public static GridBagConstraints mergeContraints(GridBagConstraints position, GridBagConstraints attributes) {
/* 769 */     GridBagConstraints ret = new GridBagConstraints();
/*     */     
/* 771 */     Field[] fields = GridBagConstraints.class.getFields();
/* 772 */     for (int i = 0; i < fields.length; i++) {
/* 773 */       Field field = fields[i];
/* 774 */       if (!Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers())) {
/*     */         try {
/* 776 */           if (field.getName().equals("gridx") || field.getName().equals("gridy")) {
/*     */             
/* 778 */             field.set(ret, field.get(position));
/*     */           } else {
/* 780 */             field.set(ret, field.get(attributes));
/*     */           } 
/* 782 */         } catch (IllegalAccessException e) {
/* 783 */           throw new RuntimeException(e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 788 */     return ret;
/*     */   }
/*     */   
/*     */   public static GridBagConstraints createDefaultConstraints() {
/* 792 */     GridBagConstraints ret = new GridBagConstraints();
/* 793 */     ret.weightx = 1.0D;
/* 794 */     ret.weighty = 1.0D;
/* 795 */     ret.fill = 1;
/* 796 */     return ret;
/*     */   }
/*     */   
/*     */   public static void showDialog(final JDialog dialog) {
/* 800 */     if (!SwingUtilities.isEventDispatchThread()) {
/* 801 */       Util.executeOnAWTThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 804 */               UIUtil.showDialog(dialog);
/*     */             }
/*     */           }, 
/*     */           true);
/*     */     } else {
/* 809 */       if (dialog.getOwner() != null && dialog.isModal()) {
/* 810 */         final Restore restore = setEnabledV2(dialog.getOwner(), false);
/* 811 */         dialog.addWindowListener(new WindowAdapter()
/*     */             {
/*     */               public void windowClosed(WindowEvent e)
/*     */               {
/* 815 */                 restore.restore();
/*     */               }
/*     */               
/*     */               public void windowClosing(WindowEvent e) {
/* 819 */                 restore.restore();
/*     */               }
/*     */             });
/*     */       } 
/* 823 */       dialog.setVisible(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface DialogHandle {
/*     */     void closeDialog();
/*     */   }
/*     */   
/*     */   public static interface Restore {
/*     */     void restore();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\UIUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */