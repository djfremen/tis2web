/*     */ package com.eoos.gm.tis2web.client.util.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.client.util.DeviceSettings;
/*     */ import com.eoos.gm.tis2web.client.util.DeviceSettingsRI;
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeviceConnectionSettingsPanelV2
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  27 */   private static final Logger log = Logger.getLogger(DeviceConnectionSettingsPanelV2.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private Callback callback;
/*     */ 
/*     */ 
/*     */   
/*     */   private JComboBox selectionPort;
/*     */ 
/*     */ 
/*     */   
/*     */   private JComboBox selectionRate;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object portAUTO;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DeviceConnectionSettingsPanelV2(Callback callback) {
/*  49 */     this.callback = callback;
/*  50 */     this.portAUTO = callback.getLabel("auto").toUpperCase();
/*  51 */     initComponents();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  56 */     setLayout(new GridBagLayout());
/*     */     
/*  58 */     List<Object> ports = new LinkedList(this.callback.getPorts());
/*  59 */     ports.add(0, this.portAUTO);
/*  60 */     this.selectionPort = new JComboBox(ports.toArray());
/*     */     
/*  62 */     this.selectionRate = new JComboBox(this.callback.getBaudrates().toArray());
/*     */     
/*  64 */     DeviceSettings defaults = this.callback.getDefaults();
/*  65 */     if (defaults == null) {
/*  66 */       this.selectionPort.setSelectedItem(this.portAUTO);
/*     */     } else {
/*  68 */       this.selectionPort.setSelectedItem(defaults.getPort());
/*  69 */       this.selectionRate.setSelectedItem(defaults.getBaudrate());
/*     */     } 
/*     */     
/*  72 */     setBorder(BorderFactory.createTitledBorder(this.callback.getLabel("static")));
/*     */     
/*  74 */     Insets insets = new Insets(2, 5, 2, 5);
/*     */     
/*  76 */     GridBagConstraints position = new GridBagConstraints();
/*  77 */     position.gridx = 0;
/*  78 */     position.gridy = 0;
/*     */     
/*  80 */     GridBagConstraints layout = new GridBagConstraints();
/*  81 */     layout.fill = 2;
/*  82 */     layout.insets = insets;
/*     */     
/*  84 */     add(new JLabel(this.callback.getLabel("communication.port") + ":", 2), UIUtil.mergeContraints(position, layout));
/*     */     
/*  86 */     position = UIUtil.getRelativePosition(position, 1, 0);
/*     */     
/*  88 */     layout = new GridBagConstraints();
/*  89 */     layout.fill = 2;
/*  90 */     layout.insets = insets;
/*  91 */     add(this.selectionPort, UIUtil.mergeContraints(position, layout));
/*     */     
/*  93 */     position = UIUtil.getRelativePosition(position, -1, 1);
/*  94 */     layout = new GridBagConstraints();
/*  95 */     layout.fill = 2;
/*  96 */     layout.insets = insets;
/*  97 */     add(new JLabel(this.callback.getLabel("transfer.rate") + ":", 2), UIUtil.mergeContraints(position, layout));
/*     */     
/*  99 */     position = UIUtil.getRelativePosition(position, 1, 0);
/* 100 */     layout = new GridBagConstraints();
/* 101 */     layout.fill = 2;
/* 102 */     layout.insets = insets;
/* 103 */     add(this.selectionRate, UIUtil.mergeContraints(position, layout));
/*     */   }
/*     */ 
/*     */   
/*     */   public static DeviceConnectionSettingsPanelV2 create(Callback callback) {
/* 108 */     return new DeviceConnectionSettingsPanelV2(callback);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeviceSettings getSettings() {
/* 118 */     Object port = this.selectionPort.getSelectedItem();
/* 119 */     Object rate = this.selectionRate.getSelectedItem();
/* 120 */     if (port == this.portAUTO) {
/* 121 */       return null;
/*     */     }
/* 123 */     return (DeviceSettings)new DeviceSettingsRI(port, rate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Throwable {
/* 129 */     Log4jUtil.attachConsoleAppender();
/*     */     try {
/* 131 */       final DeviceConnectionSettingsPanelV2[] panel = new DeviceConnectionSettingsPanelV2[1];
/*     */       
/* 133 */       SwingUtilities.invokeAndWait(new Runnable()
/*     */           {
/*     */             public void run() {
/* 136 */               panel[0] = DeviceConnectionSettingsPanelV2.create(new DeviceConnectionSettingsPanelV2.Callback()
/*     */                   {
/*     */                     public List getPorts() {
/* 139 */                       return Arrays.asList(new String[] { "COM1", "COM2" });
/*     */                     }
/*     */                     
/*     */                     public String getLabel(String key) {
/* 143 */                       return key;
/*     */                     }
/*     */                     
/*     */                     public List getBaudrates() {
/* 147 */                       return Arrays.asList(new String[] { "9600", "1500" });
/*     */                     }
/*     */                     
/*     */                     public DeviceSettings getDefaults() {
/* 151 */                       return null;
/*     */                     }
/*     */                   });
/*     */             }
/*     */           });
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
/* 166 */       Thread t = Util.createAndStartThread(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */               try {
/*     */                 while (true) {
/* 171 */                   DeviceConnectionSettingsPanelV2.log.debug(panel[0].getSettings());
/* 172 */                   Thread.sleep(1000L);
/*     */                 } 
/* 174 */               } catch (InterruptedException e) {
/*     */                 return;
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/* 180 */       UIUtil.testPanel(panel[0], false);
/* 181 */       t.interrupt();
/*     */     } finally {
/* 183 */       System.exit(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     List getPorts();
/*     */     
/*     */     List getBaudrates();
/*     */     
/*     */     String getLabel(String param1String);
/*     */     
/*     */     DeviceSettings getDefaults();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\clien\\uti\\ui\DeviceConnectionSettingsPanelV2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */