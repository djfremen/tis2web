/*     */ package com.eoos.gm.tis2web.frame.diag.client.ui;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.Border;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SystemInfoDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private I18NSupport.FixedLocale callback;
/*     */   
/*     */   private SystemInfoDialog(JFrame parent, Properties sysInfo, I18NSupport.FixedLocale callback) {
/*  38 */     super(parent);
/*  39 */     this.callback = callback;
/*  40 */     setTitle(callback.getText("system.information"));
/*     */     
/*  42 */     JPanel rootPanel = getRootPanel(sysInfo);
/*  43 */     getContentPane().add(rootPanel);
/*     */     
/*  45 */     addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e) {
/*  48 */             SystemInfoDialog.this.close();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel createPanel() {
/*  56 */     JPanel ret = new JPanel();
/*  57 */     return ret;
/*     */   }
/*     */   
/*     */   private JPanel getRootPanel(Properties sysInfo) {
/*  61 */     JPanel panel = createPanel();
/*  62 */     panel.setLayout(new GridBagLayout());
/*     */     
/*  64 */     GridBagConstraints c = new GridBagConstraints();
/*  65 */     c.gridx = 0;
/*  66 */     c.gridy = -1;
/*  67 */     c.weightx = 1.0D;
/*  68 */     c.weighty = 1.0D;
/*  69 */     c.fill = 1;
/*  70 */     c.insets = new Insets(2, 5, 2, 5);
/*     */     
/*  72 */     JPanel displayPanel = getDisplayPanel(sysInfo);
/*  73 */     JScrollPane sp = new JScrollPane(displayPanel);
/*  74 */     sp.setBorder((Border)null);
/*  75 */     panel.add(sp, c);
/*     */     
/*  77 */     c.weighty = 0.0D;
/*  78 */     c.fill = 2;
/*  79 */     panel.add(getFooterPanel(), c);
/*     */     
/*  81 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel getDisplayPanel(Properties sysInfo) {
/*  85 */     JPanel panel = createPanel();
/*  86 */     panel.setLayout(new GridBagLayout());
/*     */     
/*  88 */     ArrayList<Map.Entry<Object, Object>> list = new ArrayList<Map.Entry<Object, Object>>(sysInfo.entrySet());
/*  89 */     Collections.sort(list, new Comparator<Map.Entry<Object, Object>>()
/*     */         {
/*     */           public int compare(Object o1, Object o2) {
/*  92 */             String s1 = SystemInfoDialog.this.callback.getText((String)((Map.Entry)o1).getKey());
/*  93 */             String s2 = SystemInfoDialog.this.callback.getText((String)((Map.Entry)o2).getKey());
/*  94 */             return s1.compareTo(s2);
/*     */           }
/*     */         });
/*     */     
/*  98 */     GridBagConstraints c = new GridBagConstraints();
/*  99 */     c.fill = 2;
/* 100 */     c.insets = new Insets(2, 5, 2, 5);
/*     */     
/* 102 */     for (int i = 0; i < list.size(); i++) {
/* 103 */       Map.Entry entry = list.get(i);
/* 104 */       String key = getKeyText((String)entry.getKey()) + ":";
/* 105 */       String value = (String)entry.getValue();
/*     */       
/* 107 */       c.gridx = 0;
/* 108 */       c.gridwidth = 1;
/* 109 */       c.weightx = 0.3D;
/* 110 */       c.anchor = 12;
/* 111 */       JLabel labelKey = new JLabel(key);
/* 112 */       labelKey.setHorizontalAlignment(4);
/* 113 */       panel.add(labelKey, c);
/*     */       
/* 115 */       c.gridx = 1;
/* 116 */       c.gridwidth = 2;
/* 117 */       c.weightx = 0.6D;
/* 118 */       c.anchor = 18;
/* 119 */       JLabel labelValue = new JLabel(value);
/* 120 */       labelValue.setVerticalAlignment(1);
/* 121 */       labelValue.setHorizontalAlignment(2);
/*     */ 
/*     */       
/* 124 */       labelValue.setFont(labelValue.getFont().deriveFont(0));
/*     */       
/* 126 */       int width = SwingUtilities.computeStringWidth(labelValue.getFontMetrics(labelValue.getFont()), value);
/*     */       
/* 128 */       if (width > 400) {
/* 129 */         JScrollPane sp = new JScrollPane(labelValue);
/* 130 */         sp.setBorder((Border)null);
/* 131 */         sp.setPreferredSize(new Dimension(400, 40));
/* 132 */         panel.add(sp, c);
/*     */       } else {
/* 134 */         panel.add(labelValue, c);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 139 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getKeyText(String msgKey) {
/* 144 */     String ret = null;
/* 145 */     String orgKey = msgKey;
/*     */     
/* 147 */     while (msgKey != null && ((ret = this.callback.getText(msgKey)) == null || msgKey.equals(ret))) {
/*     */       try {
/* 149 */         msgKey = msgKey.substring(0, msgKey.lastIndexOf('.'));
/* 150 */       } catch (StringIndexOutOfBoundsException e) {
/* 151 */         msgKey = null;
/*     */       } 
/*     */     } 
/* 154 */     return (ret != null) ? ret : orgKey;
/*     */   }
/*     */   
/*     */   private JPanel getFooterPanel() {
/* 158 */     JPanel panel = createPanel();
/* 159 */     panel.setLayout(new GridBagLayout());
/*     */     
/* 161 */     GridBagConstraints c = new GridBagConstraints();
/* 162 */     c.fill = 2;
/* 163 */     c.gridx = 0;
/* 164 */     c.insets = new Insets(2, 5, 2, 5);
/*     */     
/* 166 */     c.weightx = 1.0D;
/* 167 */     panel.add(new JSeparator(0), c);
/*     */     
/* 169 */     c.weightx = 0.0D;
/* 170 */     c.fill = 0;
/* 171 */     c.gridy = 1;
/* 172 */     panel.add(getExitButton(), c);
/*     */     
/* 174 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private JButton getExitButton() {
/* 179 */     JButton button = new JButton(this.callback.getText("ok"));
/* 180 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 183 */             SystemInfoDialog.this.close();
/*     */           }
/*     */         });
/* 186 */     return button;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void show(final JFrame parent, final Properties sysInfo, final I18NSupport.FixedLocale callback) throws InterruptedException {
/* 192 */     Util.ensureNotAWTThread();
/* 193 */     final Object sync = new Object();
/* 194 */     synchronized (sync) {
/* 195 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 198 */               JDialog dialog = new SystemInfoDialog(parent, sysInfo, callback) {
/*     */                   private static final long serialVersionUID = 1L;
/*     */                   
/*     */                   protected void close() {
/* 202 */                     dispose();
/* 203 */                     synchronized (sync) {
/* 204 */                       sync.notify();
/*     */                     } 
/*     */                   }
/*     */                 };
/* 208 */               dialog.pack();
/* 209 */               dialog.setLocationRelativeTo(parent);
/*     */               
/* 211 */               dialog.setModal(true);
/* 212 */               dialog.setVisible(true);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 217 */       sync.wait();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 223 */     String test = "a.b.c";
/*     */     
/* 225 */     while (test != null) {
/* 226 */       System.out.println(test);
/* 227 */       test = test.substring(0, test.lastIndexOf('.'));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main2(String[] args) throws InterruptedException, Throwable {
/* 232 */     final JFrame[] dummyAccess = new JFrame[1];
/* 233 */     SwingUtilities.invokeAndWait(new Runnable()
/*     */         {
/*     */           public void run() {
/* 236 */             dummyAccess[0] = new JFrame();
/* 237 */             dummyAccess[0].setLocationRelativeTo(null);
/* 238 */             dummyAccess[0].setVisible(true);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 243 */     show(dummyAccess[0], System.getProperties(), I18NSupport.FixedLocale.FALLBACK);
/*     */     
/* 245 */     System.exit(0);
/*     */   }
/*     */   
/*     */   protected abstract void close();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\clien\\ui\SystemInfoDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */