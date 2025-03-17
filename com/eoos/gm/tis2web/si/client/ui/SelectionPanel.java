/*     */ package com.eoos.gm.tis2web.si.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.si.client.ClientUtil;
/*     */ import com.eoos.gm.tis2web.si.client.model.Baudrate;
/*     */ import com.eoos.gm.tis2web.si.client.model.Device;
/*     */ import com.eoos.gm.tis2web.si.client.model.Port;
/*     */ import com.eoos.log.Log4jUtil;
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
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class SelectionPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Callback callback;
/*     */   private JList selectionScreen;
/*     */   private JComboBox selectionPort;
/*     */   private JComboBox selectionRate;
/*     */   
/*     */   public SelectionPanel(int defaultScreen, Port defaultPort, Baudrate defaultBaudrate, Callback callback) {
/*  55 */     this.callback = callback;
/*     */     
/*  57 */     setLayout(new GridBagLayout());
/*     */     
/*  59 */     GridBagConstraints c = new GridBagConstraints();
/*  60 */     c.gridx = 0;
/*     */     
/*  62 */     add(createInputPanel(defaultScreen, defaultPort, defaultBaudrate), c);
/*  63 */     add(createButtonPanel(), c);
/*     */   }
/*     */   
/*     */   private Component createInputPanel(int defaultScreen, Port defaultPort, Baudrate defaultRate) {
/*  67 */     JPanel ret = new JPanel(new GridBagLayout());
/*  68 */     GridBagConstraints c = new GridBagConstraints();
/*  69 */     c.gridy = 0;
/*  70 */     c.insets = new Insets(5, 5, 2, 2);
/*  71 */     c.anchor = 17;
/*  72 */     c.fill = 2;
/*     */ 
/*     */     
/*  75 */     JLabel label = new JLabel(this.callback.getText("screen", I18NSupportV2.Type.LABEL) + ":", 4);
/*  76 */     ret.add(label, c);
/*     */     
/*  78 */     Vector<Integer> options = new Vector();
/*  79 */     int count = this.callback.getAvailableScreenCount();
/*  80 */     for (int i = 1; i < count + 1; i++) {
/*  81 */       options.add(Integer.valueOf(i));
/*     */     }
/*     */     
/*  84 */     this.selectionScreen = new JList<Integer>(options);
/*  85 */     JScrollPane scrollPane = new JScrollPane(this.selectionScreen);
/*  86 */     this.selectionScreen.setVisibleRowCount(5);
/*  87 */     this.selectionScreen.setSelectionMode(0);
/*  88 */     if (defaultScreen != -1) {
/*  89 */       this.selectionScreen.setSelectedValue(Integer.valueOf(defaultScreen), true);
/*     */     }
/*     */     
/*  92 */     ret.add(scrollPane, c);
/*     */ 
/*     */     
/*  95 */     c.gridy++;
/*     */ 
/*     */     
/*  98 */     label = new JLabel(this.callback.getText("communication.port", I18NSupportV2.Type.LABEL) + ":", 4);
/*  99 */     ret.add(label, c);
/*     */     
/* 101 */     this.selectionPort = new JComboBox(this.callback.getAvailablePorts().toArray());
/* 102 */     this.selectionPort.setRenderer(new DefaultListCellRenderer()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 107 */             return super.getListCellRendererComponent(list, ((Port)value).toString(), index, isSelected, cellHasFocus);
/*     */           }
/*     */         });
/* 110 */     if (defaultPort != null) {
/* 111 */       this.selectionPort.setSelectedItem(defaultPort);
/*     */     }
/* 113 */     ret.add(this.selectionPort, c);
/*     */ 
/*     */ 
/*     */     
/* 117 */     c.gridy++;
/*     */ 
/*     */     
/* 120 */     label = new JLabel(this.callback.getText("baud.rate", I18NSupportV2.Type.LABEL) + ":", 4);
/* 121 */     ret.add(label, c);
/*     */     
/* 123 */     this.selectionRate = new JComboBox(this.callback.getAvailableRates().toArray());
/* 124 */     this.selectionRate.setRenderer(new DefaultListCellRenderer()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 129 */             return super.getListCellRendererComponent(list, ((Baudrate)value).toString(), index, isSelected, cellHasFocus);
/*     */           }
/*     */         });
/* 132 */     if (defaultRate != null) {
/* 133 */       this.selectionRate.setSelectedItem(defaultRate);
/*     */     }
/* 135 */     ret.add(this.selectionRate, c);
/*     */ 
/*     */ 
/*     */     
/* 139 */     return ret;
/*     */   } public static interface Input {
/*     */     int getSelectedScreen(); Port getSelectedPort(); Baudrate getSelectedBaudrate(); }
/*     */   private Component createButtonPanel() {
/* 143 */     JPanel ret = new JPanel(new GridBagLayout());
/* 144 */     GridBagConstraints c = new GridBagConstraints();
/* 145 */     c.gridy = 0;
/* 146 */     c.anchor = 10;
/* 147 */     c.insets = new Insets(5, 5, 2, 2);
/*     */     
/* 149 */     JButton buttonOK = new JButton(this.callback.getText("ok", I18NSupportV2.Type.LABEL));
/* 150 */     buttonOK.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 153 */             Integer selectedScreen = SelectionPanel.this.selectionScreen.getSelectedValue();
/* 154 */             Port port = (Port)SelectionPanel.this.selectionPort.getSelectedItem();
/* 155 */             Baudrate rate = (Baudrate)SelectionPanel.this.selectionRate.getSelectedItem();
/*     */             try {
/* 157 */               SelectionPanel.this.onOK(selectedScreen.intValue(), port, rate);
/*     */             } finally {
/* 159 */               SelectionPanel.this.close();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 164 */     ret.add(buttonOK, c);
/*     */     
/* 166 */     JButton buttonCancel = new JButton(this.callback.getText("cancel", I18NSupportV2.Type.LABEL));
/* 167 */     buttonCancel.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 170 */             SelectionPanel.this.onCancel();
/*     */           }
/*     */         });
/* 173 */     ret.add(buttonCancel, c);
/*     */     
/* 175 */     return ret;
/*     */   } public static interface Callback extends I18NSupportV2.FixedLocale {
/*     */     List<Port> getAvailablePorts(); List<Baudrate> getAvailableRates(); int getAvailableScreenCount(); }
/*     */   public void onCancel() {
/* 179 */     close();
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
/*     */   public static synchronized Input getInput(final Device device, final int defaultScreen, final Port defaultPort, final Baudrate defaultBaudrate, final Callback callback) throws InterruptedException {
/* 196 */     Util.ensureNotAWTThread();
/*     */     
/* 198 */     final Input[] ret = { null };
/* 199 */     final Object sync = new Object();
/* 200 */     synchronized (sync) {
/* 201 */       Util.executeOnAWTThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 204 */               final JFrame frame = new JFrame();
/* 205 */               frame.setTitle(device.toString());
/*     */               
/* 207 */               final SelectionPanel panel = new SelectionPanel(defaultScreen, defaultPort, defaultBaudrate, callback)
/*     */                 {
/*     */                   private static final long serialVersionUID = 1L;
/*     */ 
/*     */                   
/*     */                   protected void close() {
/* 213 */                     frame.dispose();
/* 214 */                     synchronized (sync) {
/* 215 */                       sync.notify();
/*     */                     } 
/*     */                   }
/*     */ 
/*     */                   
/*     */                   protected void onOK(final int selectedScreen, final Port selectedPort, final Baudrate selectedBaudrate) {
/* 221 */                     ret[0] = new SelectionPanel.Input()
/*     */                       {
/*     */                         public int getSelectedScreen() {
/* 224 */                           return selectedScreen;
/*     */                         }
/*     */                         
/*     */                         public Port getSelectedPort() {
/* 228 */                           return selectedPort;
/*     */                         }
/*     */                         
/*     */                         public Baudrate getSelectedBaudrate() {
/* 232 */                           return selectedBaudrate;
/*     */                         }
/*     */                       };
/*     */                   }
/*     */                 };
/* 237 */               frame.getContentPane().add(panel);
/* 238 */               frame.addWindowListener(new WindowAdapter()
/*     */                   {
/*     */                     public void windowClosing(WindowEvent e) {
/* 241 */                       super.windowClosing(e);
/* 242 */                       panel.onCancel();
/*     */                     }
/*     */                   });
/*     */               
/* 246 */               frame.pack();
/* 247 */               frame.setLocationRelativeTo(null);
/* 248 */               frame.setVisible(true);
/*     */             }
/*     */           }false);
/*     */ 
/*     */       
/* 253 */       sync.wait();
/*     */     } 
/*     */     
/* 256 */     return ret[0];
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Throwable {
/* 260 */     Log4jUtil.attachConsoleAppender();
/* 261 */     Logger log = Logger.getRootLogger();
/* 262 */     final List<Port> ports = Arrays.asList(new Port[] { ClientUtil.toPort("COM1"), ClientUtil.toPort("COM2"), ClientUtil.toPort("COM3") });
/* 263 */     Callback callback = new Callback()
/*     */       {
/*     */         public String getText(String key, I18NSupportV2.Type type) {
/* 266 */           return key;
/*     */         }
/*     */         
/*     */         public int getAvailableScreenCount() {
/* 270 */           return 10;
/*     */         }
/*     */         
/*     */         public List<Port> getAvailablePorts() {
/* 274 */           return ports;
/*     */         }
/*     */         
/*     */         public List<Baudrate> getAvailableRates() {
/* 278 */           return Device.BAUDRATES;
/*     */         }
/*     */       };
/*     */     
/* 282 */     Input input = getInput(Device.TECH31, 9, ClientUtil.toPort("COM2"), Device.BAUDRATES.get(Device.BAUDRATES.size() - 1), callback);
/* 283 */     if (input == null) {
/* 284 */       log.info("nothing selected");
/*     */     } else {
/* 286 */       log.info("selected port:" + input.getSelectedPort());
/* 287 */       log.info("selected screen: " + input.getSelectedScreen());
/*     */     } 
/* 289 */     System.exit(0);
/*     */   }
/*     */   
/*     */   protected abstract void onOK(int paramInt, Port paramPort, Baudrate paramBaudrate);
/*     */   
/*     */   protected abstract void close();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\clien\\ui\SelectionPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */