/*    */ package com.eoos.gm.tis2web.sas.client.ui.panel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.client.system.ImageDataProvider;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.Insets;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PanelBase
/*    */   extends JPanel
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 21 */   private static final Logger log = Logger.getLogger(PanelBase.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void createPanels() {
/* 29 */     GridBagLayout layout = new GridBagLayout();
/* 30 */     setLayout(layout);
/*    */     
/* 32 */     GridBagConstraints constraints = null;
/*    */ 
/*    */ 
/*    */     
/* 36 */     constraints = new GridBagConstraints();
/* 37 */     constraints.weightx = 0.0D;
/* 38 */     constraints.anchor = 17;
/* 39 */     constraints.insets = new Insets(4, 4, 4, 4);
/*    */     
/* 41 */     add(createImagePanel(), constraints);
/*    */     
/* 43 */     constraints = new GridBagConstraints();
/* 44 */     constraints.weightx = 1.0D;
/* 45 */     constraints.anchor = 17;
/* 46 */     constraints.fill = 1;
/* 47 */     constraints.insets = new Insets(0, 10, 0, 0);
/*    */     
/* 49 */     add(createMainPanel(), constraints);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private JPanel createImagePanel() {
/*    */     try {
/* 56 */       JLabel image = new JLabel();
/* 57 */       ImageIcon icon = new ImageIcon(ImageDataProvider.getInstance().getData(getImageName()));
/* 58 */       new Dimension(icon.getIconWidth(), icon.getIconHeight());
/* 59 */       image.setIcon(icon);
/*    */       
/* 61 */       JPanel panel = new JPanel();
/*    */ 
/*    */       
/* 64 */       GridBagLayout layout = new GridBagLayout();
/* 65 */       panel.setLayout(layout);
/* 66 */       GridBagConstraints constraints = null;
/*    */ 
/*    */       
/* 69 */       constraints = new GridBagConstraints();
/* 70 */       constraints.fill = 1;
/*    */       
/* 72 */       panel.add(image, constraints);
/* 73 */       return panel;
/* 74 */     } catch (Exception e) {
/* 75 */       log.warn("unable to create image panel - exception: " + e);
/* 76 */       log.warn("...returning empty panel");
/* 77 */       return new JPanel();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getImageName() {
/* 82 */     return "device.jpg";
/*    */   }
/*    */   
/*    */   protected abstract JPanel createMainPanel();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\panel\PanelBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */