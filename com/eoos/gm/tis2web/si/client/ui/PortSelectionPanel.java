/*     */ package com.eoos.gm.tis2web.si.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.si.client.model.Port;
/*     */ import com.eoos.scsm.v2.util.I18NSupportV2;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.List;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
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
/*     */ public abstract class PortSelectionPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Callback callback;
/*     */   private JComboBox selectionPort;
/*     */   
/*     */   public PortSelectionPanel(Callback callback) {
/*  43 */     this.callback = callback;
/*     */     
/*  45 */     setLayout(new GridBagLayout());
/*     */     
/*  47 */     GridBagConstraints c = new GridBagConstraints();
/*  48 */     c.gridx = 0;
/*     */     
/*  50 */     add(createInputPanel(), c);
/*  51 */     add(createButtonPanel(), c);
/*     */   }
/*     */   
/*     */   private Component createInputPanel() {
/*  55 */     JPanel ret = new JPanel(new GridBagLayout());
/*  56 */     GridBagConstraints c = new GridBagConstraints();
/*  57 */     c.gridy = 0;
/*  58 */     c.insets = new Insets(5, 5, 2, 2);
/*     */     
/*  60 */     JLabel label = new JLabel(this.callback.getText("communication.port", I18NSupportV2.Type.LABEL) + ":", 4);
/*  61 */     ret.add(label, c);
/*     */     
/*  63 */     this.selectionPort = new JComboBox(this.callback.getAvailablePorts().toArray());
/*  64 */     this.selectionPort.setRenderer(new DefaultListCellRenderer()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/*  69 */             return super.getListCellRendererComponent(list, ((Port)value).toString(), index, isSelected, cellHasFocus);
/*     */           }
/*     */         });
/*  72 */     this.selectionPort.setSelectedItem(this.callback.getSelectedPort());
/*  73 */     ret.add(this.selectionPort, c);
/*     */     
/*  75 */     return ret;
/*     */   }
/*     */   
/*     */   private Component createButtonPanel() {
/*  79 */     JPanel ret = new JPanel(new GridBagLayout());
/*  80 */     GridBagConstraints c = new GridBagConstraints();
/*  81 */     c.gridy = 0;
/*  82 */     c.anchor = 10;
/*  83 */     c.insets = new Insets(5, 5, 2, 2);
/*     */     
/*  85 */     JButton buttonOK = new JButton(this.callback.getText("ok", I18NSupportV2.Type.LABEL));
/*  86 */     buttonOK.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  89 */             Port port = (Port)PortSelectionPanel.this.selectionPort.getSelectedItem();
/*  90 */             PortSelectionPanel.this.callback.setSelectedPort(port);
/*  91 */             PortSelectionPanel.this.close();
/*     */           }
/*     */         });
/*  94 */     ret.add(buttonOK, c);
/*     */     
/*  96 */     JButton buttonCancel = new JButton(this.callback.getText("cancel", I18NSupportV2.Type.LABEL));
/*  97 */     buttonCancel.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 100 */             PortSelectionPanel.this.onCancel();
/*     */           }
/*     */         });
/* 103 */     ret.add(buttonCancel, c);
/*     */     
/* 105 */     return ret;
/*     */   } public static interface Callback extends I18NSupportV2.FixedLocale {
/*     */     void setSelectedPort(Port param1Port); Port getSelectedPort(); List<Port> getAvailablePorts(); }
/*     */   public void onCancel() {
/* 109 */     this.callback.setSelectedPort((Port)null);
/* 110 */     close();
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
/*     */   public static synchronized Port selectPort(final Callback callback) throws InterruptedException {
/* 125 */     Util.ensureNotAWTThread();
/* 126 */     Port[] ret = { null };
/*     */     
/* 128 */     final Object sync = new Object();
/* 129 */     synchronized (sync) {
/* 130 */       Util.executeOnAWTThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 133 */               final JDialog dialog = new JDialog();
/* 134 */               dialog.setTitle(callback.getText("please.select.port", I18NSupportV2.Type.MESSAGE));
/* 135 */               dialog.setModal(true);
/*     */               
/* 137 */               final PortSelectionPanel panel = new PortSelectionPanel(callback)
/*     */                 {
/*     */                   private static final long serialVersionUID = 1L;
/*     */ 
/*     */                   
/*     */                   protected void close() {
/* 143 */                     dialog.dispose();
/* 144 */                     synchronized (sync) {
/* 145 */                       sync.notify();
/*     */                     } 
/*     */                   }
/*     */                 };
/* 149 */               dialog.getContentPane().add(panel);
/* 150 */               dialog.addWindowListener(new WindowAdapter()
/*     */                   {
/*     */                     public void windowClosing(WindowEvent e) {
/* 153 */                       super.windowClosing(e);
/* 154 */                       panel.onCancel();
/*     */                     }
/*     */                   });
/*     */               
/* 158 */               dialog.pack();
/* 159 */               dialog.setLocationRelativeTo(null);
/* 160 */               dialog.setVisible(true);
/*     */             }
/*     */           }false);
/*     */ 
/*     */       
/* 165 */       sync.wait();
/*     */     } 
/* 167 */     return ret[0];
/*     */   }
/*     */   
/*     */   protected abstract void close();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\clien\\ui\PortSelectionPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */