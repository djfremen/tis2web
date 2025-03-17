/*    */ package com.eoos.gm.tis2web.sas.client.ui.panel;
/*    */ 
/*    */ import com.jw.swing.layout.PointLayout;
/*    */ import com.jw.swing.layout.SCLayout;
/*    */ import java.awt.LayoutManager;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessagePanel
/*    */   extends PanelBase
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   private static final Logger log = Logger.getLogger(MessagePanel.class);
/*    */ 
/*    */ 
/*    */   
/*    */   private Callback callback;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MessagePanel(Callback callback) {
/* 26 */     this.callback = callback;
/* 27 */     createPanels();
/*    */   }
/*    */   
/*    */   protected JPanel createMainPanel() {
/* 31 */     JPanel panel = new JPanel();
/*    */ 
/*    */     
/* 34 */     PointLayout layout = new PointLayout(PointLayout.CENTER, PointLayout.CENTER);
/* 35 */     panel.setLayout((LayoutManager)layout);
/*    */ 
/*    */ 
/*    */     
/* 39 */     panel.add(createMessagePanel());
/*    */     
/* 41 */     return panel;
/*    */   }
/*    */   
/*    */   private JPanel createMessagePanel() {
/* 45 */     JPanel panel = new JPanel();
/* 46 */     String[] messages = this.callback.getMessages();
/*    */ 
/*    */     
/* 49 */     SCLayout layout = new SCLayout(messages.length, SCLayout.LEFT, SCLayout.CENTER, 5);
/* 50 */     panel.setLayout((LayoutManager)layout);
/*    */ 
/*    */     
/* 53 */     for (int i = 0; i < messages.length; i++) {
/* 54 */       log.debug("adding message: " + String.valueOf(messages[i]));
/* 55 */       panel.add(new JLabel(messages[i]));
/*    */     } 
/* 57 */     return panel;
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     String[] getMessages();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\panel\MessagePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */