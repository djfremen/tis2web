/*     */ package com.eoos.gm.tis2web.sas.client.ui.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.client.util.DeviceSettings;
/*     */ import com.eoos.gm.tis2web.client.util.ui.DeviceConnectionSettingsPanelV2;
/*     */ import com.eoos.gm.tis2web.sas.client.system.ImageDataProvider;
/*     */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.panel.SplashScreen;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.PanelStack;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.PanelStackImpl;
/*     */ import com.eoos.gm.tis2web.sas.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sas.common.system.LabelResourceProvider;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSeparator;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MainFrame
/*     */   extends JFrame
/*     */   implements PanelStack
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   private static final Logger log = Logger.getLogger(MainFrame.class);
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
/*  54 */   private static Dimension MINIMAL_SIZE = new Dimension(600, 330);
/*     */   
/*  56 */   private static MainFrame instance = null;
/*     */ 
/*     */   
/*     */   private PanelStackImpl stack;
/*     */   
/*     */   private Callback callback;
/*     */ 
/*     */   
/*     */   private MainFrame(Callback callback) {
/*  65 */     this.callback = callback;
/*     */ 
/*     */     
/*  68 */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent e) {
/*  70 */             MainFrame.this.exit();
/*     */           }
/*     */         });
/*     */     
/*  74 */     Container contentPane = getContentPane();
/*  75 */     GridBagLayout layout = new GridBagLayout();
/*  76 */     contentPane.setLayout(layout);
/*  77 */     GridBagConstraints c = null;
/*     */     
/*  79 */     c = new GridBagConstraints();
/*  80 */     c.insets = new Insets(5, 5, 5, 5);
/*  81 */     c.weightx = 1.0D;
/*  82 */     c.weighty = 1.0D;
/*  83 */     c.fill = 1;
/*     */     
/*  85 */     this.stack = new PanelStackImpl();
/*  86 */     contentPane.add((Component)this.stack, c);
/*     */     
/*  88 */     c = new GridBagConstraints();
/*  89 */     c.gridy = 1;
/*  90 */     c.fill = 2;
/*  91 */     c.weightx = 1.0D;
/*  92 */     contentPane.add(new JSeparator(), c);
/*     */     
/*  94 */     c = new GridBagConstraints();
/*  95 */     c.gridy = 2;
/*  96 */     c.fill = 0;
/*  97 */     c.anchor = 17;
/*     */     
/*  99 */     pack();
/*     */     
/* 101 */     setLocation(UIUtil.getScreenCenterLocation(this));
/*     */ 
/*     */     
/*     */     try {
/* 105 */       Locale locale = SASClientContextProvider.getInstance().getContext().getLocale();
/* 106 */       setTitle(LabelResourceProvider.getInstance().getLabelResource().getLabel(locale, "window.title"));
/* 107 */       ImageIcon icon = new ImageIcon(ImageDataProvider.getInstance().getData("sas_icon.jpg"));
/* 108 */       setIconImage(icon.getImage());
/* 109 */     } catch (Exception e) {
/* 110 */       log.warn("unable to set title and/or icon - exception:" + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized MainFrame createInstance(Callback callback) {
/* 116 */     instance = new MainFrame(callback);
/* 117 */     return instance;
/*     */   } public static interface Callback {
/*     */     String getMessage(String param1String); String getLabel(String param1String); List getPorts(); List getBaudrates(); }
/*     */   public static synchronized MainFrame getInstance() {
/* 121 */     if (instance == null) {
/* 122 */       throw new IllegalStateException("instance has not been created yet");
/*     */     }
/* 124 */     return instance;
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 128 */     Dimension retValue = super.getPreferredSize();
/* 129 */     if (retValue.width >= MINIMAL_SIZE.width && retValue.height >= MINIMAL_SIZE.height) {
/* 130 */       return retValue;
/*     */     }
/* 132 */     return MINIMAL_SIZE;
/*     */   }
/*     */ 
/*     */   
/*     */   public PanelStack getPanelStack() {
/* 137 */     return (PanelStack)this.stack;
/*     */   }
/*     */   
/*     */   public void refresh() {
/* 141 */     validate();
/* 142 */     invalidate();
/* 143 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(JPanel panel) {
/* 148 */     this.stack.push(panel);
/* 149 */     pack();
/*     */   }
/*     */   
/*     */   public JPanel pop() {
/* 153 */     JPanel retValue = this.stack.pop();
/* 154 */     pack();
/* 155 */     return retValue;
/*     */   }
/*     */   
/*     */   public JPanel peek() {
/* 159 */     return this.stack.peek();
/*     */   }
/*     */   
/*     */   public void exit() {
/* 163 */     log.debug("exiting application");
/* 164 */     dispose();
/* 165 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 170 */     this.stack.clear();
/*     */   }
/*     */   
/*     */   public void showSplashScreen() {
/* 174 */     SplashScreen splash = SplashScreen.getInstance();
/* 175 */     if (!splash.isVisible()) {
/* 176 */       splash.setVisible(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void hideSplashScreen() {
/* 182 */     SplashScreen splash = SplashScreen.getInstance();
/* 183 */     if (splash.isVisible()) {
/* 184 */       splash.setVisible(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void start() {
/* 189 */     showSplashScreen();
/* 190 */     UIFactory factory = new UIFactory();
/* 191 */     factory.start();
/*     */   }
/*     */   
/*     */   public void showSettingsDialog() {
/* 195 */     final DeviceConnectionSettingsPanelV2 connSettingsPanel = DeviceConnectionSettingsPanelV2.create(new DeviceConnectionSettingsPanelV2.Callback()
/*     */         {
/*     */           public List getPorts() {
/* 198 */             return MainFrame.this.callback.getPorts();
/*     */           }
/*     */           
/*     */           public String getLabel(String key) {
/* 202 */             return MainFrame.this.callback.getLabel(key);
/*     */           }
/*     */           
/*     */           public List getBaudrates() {
/* 206 */             return MainFrame.this.callback.getBaudrates();
/*     */           }
/*     */           
/*     */           public DeviceSettings getDefaults() {
/* 210 */             return SASClientContextProvider.getInstance().getContext().getDeviceSettings();
/*     */           }
/*     */         });
/* 213 */     final JDialog dialog = new JDialog(this);
/* 214 */     dialog.setTitle(this.callback.getLabel("settings"));
/*     */     
/* 216 */     JPanel contentPanel = new JPanel(new GridBagLayout());
/*     */     
/* 218 */     connSettingsPanel.setBorder(BorderFactory.createTitledBorder(this.callback.getLabel("tech.communication.settings")));
/*     */     
/* 220 */     GridBagConstraints c = new GridBagConstraints();
/* 221 */     c.gridy = 0;
/* 222 */     c.insets = new Insets(0, 10, 0, 0);
/* 223 */     c.fill = 2;
/* 224 */     contentPanel.add((Component)connSettingsPanel, c);
/*     */     
/* 226 */     JPanel buttonPanel = new JPanel(new FlowLayout(1, 10, 10));
/* 227 */     JButton okButton = new JButton(this.callback.getLabel("ok"));
/* 228 */     okButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 231 */             SASClientContextProvider.getInstance().getContext().setDeviceSettings(connSettingsPanel.getSettings());
/* 232 */             dialog.dispose();
/*     */           }
/*     */         });
/*     */     
/* 236 */     buttonPanel.add(okButton);
/*     */     
/* 238 */     JButton cancelButton = new JButton(this.callback.getLabel("cancel"));
/* 239 */     cancelButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 242 */             dialog.dispose();
/*     */           }
/*     */         });
/*     */     
/* 246 */     buttonPanel.add(cancelButton);
/*     */     
/* 248 */     c.gridy++;
/* 249 */     contentPanel.add(buttonPanel, c);
/*     */     
/* 251 */     dialog.setContentPane(contentPanel);
/*     */     
/* 253 */     dialog.setDefaultCloseOperation(2);
/* 254 */     dialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosed(WindowEvent e) {}
/*     */         });
/*     */ 
/*     */     
/* 260 */     dialog.setModal(true);
/*     */     
/* 262 */     dialog.pack();
/* 263 */     dialog.setLocationRelativeTo(this);
/* 264 */     dialog.setVisible(true);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\main\MainFrame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */