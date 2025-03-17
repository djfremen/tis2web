/*     */ package com.eoos.gm.tis2web.sps.client.ui.render;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.CalibrationTreeModel;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.template.TreeNodeIcon;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.tree.TreeCellRenderer;
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
/*     */ public class IconCellRenderer
/*     */   extends JLabel
/*     */   implements TreeCellRenderer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected Color m_textSelectionColor;
/*     */   protected Color m_textNonSelectionColor;
/*     */   protected Color m_bkSelectionColor;
/*     */   protected Color m_bkNonSelectionColor;
/*     */   protected Color m_borderSelectionColor;
/*     */   protected boolean m_selected = false;
/*     */   protected boolean userSelected = false;
/*     */   
/*     */   public IconCellRenderer() {
/*  42 */     this.m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
/*  43 */     this.m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
/*  44 */     this.m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
/*  45 */     this.m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
/*  46 */     this.m_borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
/*  47 */     setOpaque(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
/*  52 */     CalibrationTreeModel model = (CalibrationTreeModel)tree.getModel();
/*  53 */     setText(value.toString());
/*  54 */     this.m_selected = false;
/*  55 */     this.userSelected = sel;
/*     */     
/*  57 */     if (model.isSelected(value)) {
/*  58 */       setIcon(TreeNodeIcon.ICON_SELECTED);
/*  59 */       sel = true;
/*     */     }
/*  61 */     else if (model.isSelectable(value)) {
/*  62 */       setIcon(TreeNodeIcon.ICON_UNSELECTED);
/*     */     } else {
/*     */       
/*  65 */       setIcon(TreeNodeIcon.ICON_NOSELECTION);
/*  66 */       sel = false;
/*     */     } 
/*     */     
/*  69 */     this.m_selected = sel;
/*  70 */     setFont(tree.getFont());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     setForeground(sel ? this.m_textSelectionColor : this.m_textNonSelectionColor);
/*  76 */     setBackground(sel ? this.m_bkSelectionColor : this.m_bkNonSelectionColor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics g) {
/*  88 */     Color bColor = getBackground();
/*  89 */     Icon icon = getIcon();
/*     */     
/*  91 */     g.setColor(bColor);
/*  92 */     int offset = 0;
/*  93 */     if (icon != null && getText() != null) {
/*  94 */       offset = icon.getIconWidth() + getIconTextGap();
/*     */     }
/*  96 */     g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
/*     */     
/*  98 */     if (this.m_selected) {
/*  99 */       g.setColor(this.m_borderSelectionColor);
/* 100 */       g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
/*     */     } else {
/* 102 */       g.setColor(bColor);
/*     */     } 
/*     */     
/* 105 */     super.paintComponent(g);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\render\IconCellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */