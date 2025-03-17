/*     */ package com.eoos.gm.tis2web.lt.icop.cfgclient.ui;
/*     */ 
/*     */ import com.eoos.scsm.v2.swing.UIUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CfgInputPanel
/*     */   extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  27 */   private Map<UICallback.ID, JComboBox> inputs = new HashMap<UICallback.ID, JComboBox>();
/*     */ 
/*     */   
/*     */   public CfgInputPanel(UICallback uICallback) {
/*  31 */     setLayout(new GridBagLayout());
/*  32 */     GridBagConstraints c = new GridBagConstraints();
/*  33 */     c.fill = 2;
/*  34 */     c.weightx = 1.0D;
/*  35 */     c.insets = new Insets(10, 2, 0, 5);
/*     */     
/*  37 */     c.gridy = 0;
/*  38 */     add(new JLabel(uICallback.getLabel("server"), 4), c);
/*  39 */     add(createServerInput(uICallback), c);
/*     */     
/*  41 */     c.gridy++;
/*  42 */     add(new JLabel(uICallback.getLabel("default.salesmake"), 4), c);
/*  43 */     add(createMakeInput(uICallback), c);
/*     */     
/*  45 */     c.gridy++;
/*  46 */     add(new JLabel(uICallback.getLabel("default.country"), 4), c);
/*  47 */     add(createCountryInput(uICallback), c);
/*     */     
/*  49 */     c.gridy++;
/*  50 */     add(new JLabel(uICallback.getLabel("default.language"), 4), c);
/*  51 */     add(createLanguageInput(uICallback), c);
/*     */   }
/*     */ 
/*     */   
/*     */   private Component createServerInput(UICallback callback) {
/*  56 */     JComboBox ret = new JComboBox(new Vector(callback.getOptions(UICallback.ID.SERVER)));
/*  57 */     Object selected = callback.getCurrentValue(UICallback.ID.SERVER);
/*  58 */     if (selected != null) {
/*  59 */       ret.setSelectedItem(selected);
/*     */     }
/*  61 */     this.inputs.put(UICallback.ID.SERVER, ret);
/*  62 */     ret.setPreferredSize(UIUtil.scale(ret.getPreferredSize(), 1.5F, 1.0F));
/*     */     
/*  64 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private Component createMakeInput(UICallback callback) {
/*  69 */     JComboBox ret = new JComboBox(new Vector(callback.getOptions(UICallback.ID.MAKE)));
/*  70 */     Object selected = callback.getCurrentValue(UICallback.ID.MAKE);
/*  71 */     if (selected != null) {
/*  72 */       ret.setSelectedItem(selected);
/*     */     }
/*  74 */     this.inputs.put(UICallback.ID.MAKE, ret);
/*  75 */     ret.setPreferredSize(UIUtil.scale(ret.getPreferredSize(), 1.5F, 1.0F));
/*     */     
/*  77 */     return ret;
/*     */   }
/*     */   
/*     */   private Component createCountryInput(final UICallback callback) {
/*  81 */     JComboBox ret = new JComboBox(new Vector(callback.getOptions(UICallback.ID.COUNTRY)));
/*  82 */     ret.setRenderer(new DefaultListCellRenderer()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */ 
/*     */           
/*     */           public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/*  88 */             return super.getListCellRendererComponent(list, ((Locale)value).getDisplayCountry(callback.getLocale()), index, isSelected, cellHasFocus);
/*     */           }
/*     */         });
/*     */     
/*  92 */     Object selected = callback.getCurrentValue(UICallback.ID.COUNTRY);
/*  93 */     if (selected != null) {
/*  94 */       ret.setSelectedItem(selected);
/*     */     }
/*  96 */     this.inputs.put(UICallback.ID.COUNTRY, ret);
/*     */     
/*  98 */     ret.setPreferredSize(UIUtil.scale(ret.getPreferredSize(), 1.5F, 1.0F));
/*     */     
/* 100 */     return ret;
/*     */   }
/*     */   
/*     */   private Component createLanguageInput(final UICallback callback) {
/* 104 */     JComboBox ret = new JComboBox(new Vector(callback.getOptions(UICallback.ID.LANGUAGE)));
/* 105 */     ret.setRenderer(new DefaultListCellRenderer()
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */ 
/*     */           
/*     */           public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 111 */             return super.getListCellRendererComponent(list, ((Locale)value).getDisplayName(callback.getLocale()), index, isSelected, cellHasFocus);
/*     */           }
/*     */         });
/*     */     
/* 115 */     Object selected = callback.getCurrentValue(UICallback.ID.LANGUAGE);
/* 116 */     if (selected != null) {
/* 117 */       ret.setSelectedItem(selected);
/*     */     }
/* 119 */     this.inputs.put(UICallback.ID.LANGUAGE, ret);
/*     */     
/* 121 */     ret.setPreferredSize(UIUtil.scale(ret.getPreferredSize(), 1.5F, 1.0F));
/*     */     
/* 123 */     return ret;
/*     */   }
/*     */   
/*     */   public Object getSelectedValue(UICallback.ID id) {
/* 127 */     return ((JComboBox)this.inputs.get(id)).getSelectedItem();
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 131 */     UIUtil.testPanel(new CfgInputPanel(new UICallback()
/*     */           {
/*     */             public String toString(Object value) {
/* 134 */               return String.valueOf(value);
/*     */             }
/*     */             
/*     */             public List getOptions(UICallback.ID id) {
/* 138 */               return Arrays.asList(new Object[] { "1", "2", "3" }, );
/*     */             }
/*     */             
/*     */             public String getLabel(String key) {
/* 142 */               return key;
/*     */             }
/*     */             
/*     */             public Object getCurrentValue(UICallback.ID id) {
/* 146 */               List options = getOptions(id);
/* 147 */               int index = (int)Util.createRandom(0L, (options.size() - 1));
/* 148 */               return options.get(index);
/*     */             }
/*     */ 
/*     */             
/*     */             public void onCancel() {}
/*     */ 
/*     */             
/*     */             public void onOK(UICallback.Selection selection) {}
/*     */             
/*     */             public Locale getLocale() {
/* 158 */               return Locale.ENGLISH;
/*     */             }
/*     */           }), true);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\cfgclien\\ui\CfgInputPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */