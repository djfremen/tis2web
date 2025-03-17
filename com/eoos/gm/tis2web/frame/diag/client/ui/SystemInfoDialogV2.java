/*     */ package com.eoos.gm.tis2web.frame.diag.client.ui;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.I18NSupport;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.ComponentListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SystemInfoDialogV2
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private I18NSupport.FixedLocale callback;
/*     */   
/*     */   private SystemInfoDialogV2(JFrame parent, Properties sysInfo, I18NSupport.FixedLocale callback) {
/*  42 */     super(parent);
/*  43 */     this.callback = callback;
/*  44 */     setTitle(callback.getText("system.information"));
/*     */     
/*  46 */     getContentPane().add(getRootPanel(sysInfo));
/*     */   }
/*     */   
/*     */   private JPanel createPanel() {
/*  50 */     JPanel ret = new JPanel();
/*  51 */     return ret;
/*     */   }
/*     */   
/*     */   private JPanel getRootPanel(Properties sysInfo) {
/*  55 */     JPanel panel = createPanel();
/*  56 */     panel.setLayout(new GridBagLayout());
/*     */     
/*  58 */     GridBagConstraints c = new GridBagConstraints();
/*  59 */     c.gridx = 0;
/*  60 */     c.gridy = -1;
/*  61 */     c.weightx = 1.0D;
/*  62 */     c.fill = 2;
/*     */     
/*  64 */     panel.add(getDisplayPanel(sysInfo), c);
/*     */     
/*  66 */     panel.add(getFooterPanel(), c);
/*     */     
/*  68 */     return panel;
/*     */   }
/*     */   
/*     */   private JPanel getDisplayPanel(final Properties sysInfo) {
/*  72 */     JPanel panel = createPanel();
/*  73 */     panel.setLayout(new GridBagLayout());
/*     */     
/*  75 */     GridBagConstraints c = new GridBagConstraints();
/*  76 */     c.fill = 2;
/*  77 */     c.weightx = 2.0D;
/*  78 */     c.gridx = 0;
/*  79 */     final ArrayList<Map.Entry<Object, Object>> list = new ArrayList<Map.Entry<Object, Object>>(sysInfo.entrySet());
/*  80 */     Collections.sort(list, new Comparator<Map.Entry<Object, Object>>()
/*     */         {
/*     */           public int compare(Object o1, Object o2) {
/*  83 */             String s1 = SystemInfoDialogV2.this.callback.getText((String)((Map.Entry)o1).getKey());
/*  84 */             String s2 = SystemInfoDialogV2.this.callback.getText((String)((Map.Entry)o2).getKey());
/*  85 */             return s1.compareTo(s2);
/*     */           }
/*     */         });
/*     */     
/*  89 */     TableModel model = new AbstractTableModel()
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Object getValueAt(int rowIndex, int columnIndex) {
/*  94 */           Map.Entry entry = list.get(rowIndex);
/*  95 */           if (columnIndex == 0) {
/*  96 */             return SystemInfoDialogV2.this.callback.getText((String)entry.getKey()) + ":";
/*     */           }
/*  98 */           return entry.getValue();
/*     */         }
/*     */ 
/*     */         
/*     */         public int getRowCount() {
/* 103 */           return sysInfo.size();
/*     */         }
/*     */         
/*     */         public int getColumnCount() {
/* 107 */           return 2;
/*     */         }
/*     */       };
/*     */     
/* 111 */     JTable table = new JTable(model);
/*     */ 
/*     */     
/* 114 */     DefaultTableCellRenderer renderer = new DefaultTableCellRenderer()
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public void setFont(Font font) {
/* 119 */           System.out.println("setting font: " + font);
/* 120 */           if (font != null) {
/* 121 */             super.setFont(font.deriveFont(1));
/*     */           }
/*     */         }
/*     */         
/*     */         protected void setValue(Object value) {
/* 126 */           int width = SwingUtilities.computeStringWidth(getFontMetrics(getFont()), (String)value);
/* 127 */           setPreferredSize(new Dimension(width, 15));
/* 128 */           super.setValue(value);
/*     */         }
/*     */       };
/*     */     
/* 132 */     renderer.setHorizontalAlignment(4);
/*     */     
/* 134 */     table.getColumnModel().getColumn(0).setCellRenderer(renderer);
/* 135 */     TableColumnModel cm = table.getColumnModel();
/*     */     
/* 137 */     final TableColumn column1 = cm.getColumn(1);
/* 138 */     column1.getCellRenderer();
/*     */     
/* 140 */     cm.getColumn(1).setPreferredWidth(cm.getColumn(0).getPreferredWidth() * 2);
/* 141 */     table.addComponentListener(new ComponentListener()
/*     */         {
/*     */           public void componentShown(ComponentEvent e) {}
/*     */ 
/*     */           
/*     */           public void componentResized(ComponentEvent e) {
/* 147 */             int total = e.getComponent().getWidth();
/* 148 */             column1.setPreferredWidth(Math.round((total + 0.0F) / 3.0F * 1.8F));
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void componentMoved(ComponentEvent e) {}
/*     */ 
/*     */           
/*     */           public void componentHidden(ComponentEvent e) {}
/*     */         });
/* 158 */     TableColumnModel cmHeader = table.getTableHeader().getColumnModel();
/* 159 */     cmHeader.getColumn(0).setHeaderValue(this.callback.getText("description"));
/* 160 */     cmHeader.getColumn(1).setHeaderValue(this.callback.getText("value"));
/*     */     
/* 162 */     JScrollPane sp = new JScrollPane(table);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     panel.add(sp, c);
/* 169 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private JPanel getFooterPanel() {
/* 174 */     JPanel panel = createPanel();
/* 175 */     panel.setLayout(new GridBagLayout());
/*     */     
/* 177 */     GridBagConstraints c = new GridBagConstraints();
/* 178 */     c.fill = 2;
/* 179 */     c.gridx = 0;
/*     */     
/* 181 */     c.weightx = 1.0D;
/* 182 */     panel.add(new JSeparator(0), c);
/*     */     
/* 184 */     c.weightx = 0.0D;
/* 185 */     c.fill = 0;
/* 186 */     c.gridy = 1;
/* 187 */     panel.add(getExitButton(), c);
/*     */     
/* 189 */     return panel;
/*     */   }
/*     */ 
/*     */   
/*     */   private JButton getExitButton() {
/* 194 */     JButton button = new JButton(this.callback.getText("ok"));
/* 195 */     button.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 198 */             SystemInfoDialogV2.this.close();
/*     */           }
/*     */         });
/* 201 */     return button;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void show(final JFrame parent, final Properties sysInfo, final I18NSupport.FixedLocale callback) throws InterruptedException {
/* 207 */     Util.ensureNotAWTThread();
/* 208 */     final Object sync = new Object();
/* 209 */     synchronized (sync) {
/* 210 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 213 */               JDialog dialog = new SystemInfoDialogV2(parent, sysInfo, callback) {
/*     */                   private static final long serialVersionUID = 1L;
/*     */                   
/*     */                   protected void close() {
/* 217 */                     dispose();
/* 218 */                     synchronized (sync) {
/* 219 */                       sync.notify();
/*     */                     } 
/*     */                   }
/*     */                 };
/* 223 */               dialog.pack();
/* 224 */               dialog.setLocationRelativeTo(parent);
/*     */               
/* 226 */               dialog.setVisible(true);
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 231 */       sync.wait();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws InterruptedException, Throwable {
/* 237 */     final JFrame[] dummyAccess = new JFrame[1];
/* 238 */     SwingUtilities.invokeAndWait(new Runnable()
/*     */         {
/*     */           public void run() {
/* 241 */             dummyAccess[0] = new JFrame();
/* 242 */             dummyAccess[0].setLocationRelativeTo(null);
/* 243 */             dummyAccess[0].setVisible(true);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 248 */     show(dummyAccess[0], System.getProperties(), I18NSupport.FixedLocale.FALLBACK);
/* 249 */     System.exit(0);
/*     */   }
/*     */   
/*     */   protected abstract void close();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\diag\clien\\ui\SystemInfoDialogV2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */