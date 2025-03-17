/*     */ package com.eoos.gm.tis2web.lt.icop.cfgclient.ui;
/*     */ 
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RootPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private UICallback callback;
/*     */   
/*     */   public RootPanel(UICallback callback) {
/*  29 */     this.callback = callback;
/*     */     
/*  31 */     setLayout(new GridBagLayout());
/*  32 */     GridBagConstraints c = new GridBagConstraints();
/*     */     
/*  34 */     c.gridx = 0;
/*  35 */     c.fill = 1;
/*  36 */     c.weightx = 1.0D;
/*  37 */     c.weighty = 1.0D;
/*  38 */     c.insets = new Insets(5, 5, 5, 5);
/*     */     
/*  40 */     CfgInputPanel inputPanel = new CfgInputPanel(callback);
/*     */     
/*  42 */     add(inputPanel, c);
/*  43 */     add(createButtonPanel(callback, inputPanel), c);
/*     */   }
/*     */   
/*     */   private Component createButtonPanel(UICallback callback, CfgInputPanel inputPanel) {
/*  47 */     JPanel ret = new JPanel();
/*  48 */     ret.setLayout(new GridBagLayout());
/*  49 */     GridBagConstraints c = new GridBagConstraints();
/*  50 */     c.gridy = 0;
/*     */     
/*  52 */     ret.add(createOKButton(callback, inputPanel), c);
/*  53 */     ret.add(Box.createRigidArea(new Dimension(10, 10)), c);
/*  54 */     ret.add(createCancelButton(callback), c);
/*     */     
/*  56 */     return ret;
/*     */   }
/*     */   
/*     */   private Component createCancelButton(final UICallback callback) {
/*  60 */     JButton ret = new JButton(new AbstractAction(callback.getLabel("cancel"))
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public void actionPerformed(ActionEvent e) {
/*  65 */             callback.onCancel();
/*     */           }
/*     */         });
/*  68 */     return ret;
/*     */   }
/*     */   
/*     */   private Component createOKButton(UICallback callback2, final CfgInputPanel inputPanel) {
/*  72 */     JButton ret = new JButton(new AbstractAction(this.callback.getLabel("ok"))
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public void actionPerformed(ActionEvent e) {
/*  77 */             UICallback.Selection selection = new UICallback.Selection()
/*     */               {
/*     */                 public Object getSelection(UICallback.ID id) {
/*  80 */                   return inputPanel.getSelectedValue(id);
/*     */                 }
/*     */               };
/*  83 */             RootPanel.this.callback.onOK(selection);
/*     */           }
/*     */         });
/*  86 */     return ret;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  90 */     UIUtil.testPanel(new RootPanel(new UICallback()
/*     */           {
/*     */             public String toString(Object value) {
/*  93 */               return String.valueOf(value);
/*     */             }
/*     */             
/*     */             public List getOptions(UICallback.ID id) {
/*  97 */               return Arrays.asList(new Object[] { "1", "2", "3" }, );
/*     */             }
/*     */             
/*     */             public String getLabel(String key) {
/* 101 */               return key;
/*     */             }
/*     */             
/*     */             public Object getCurrentValue(UICallback.ID id) {
/* 105 */               List options = getOptions(id);
/* 106 */               int index = (int)Util.createRandom(0L, (options.size() - 1));
/* 107 */               return options.get(index);
/*     */             }
/*     */ 
/*     */             
/*     */             public void onCancel() {}
/*     */ 
/*     */             
/*     */             public void onOK(UICallback.Selection selection) {}
/*     */ 
/*     */             
/*     */             public Locale getLocale() {
/* 118 */               return null;
/*     */             }
/*     */           }), false);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\cfgclien\\ui\RootPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */