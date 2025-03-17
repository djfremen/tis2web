/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.menu.PopupMenu;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*     */ import com.eoos.gm.tis2web.sps.common.VINDisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Locale;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.JTextComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VINJComboBox
/*     */   extends JComboBox
/*     */   implements AttributeInput, ValueRetrieval
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private VINDisplayRequest dataReq;
/*  30 */   protected PopupMenu popupMenu = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public VINJComboBox() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public VINJComboBox(Vector<E> data) {
/*  39 */     super(data);
/*     */   }
/*     */   
/*     */   public void setRequest(VINDisplayRequest dataRequest) {
/*  43 */     this.dataReq = dataRequest;
/*  44 */     if (this.dataReq.getDefaultValue() != null) {
/*  45 */       setSelectedItem(new DisplayAdapter(this.dataReq.getDefaultValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute getAttribute() {
/*  53 */     return this.dataReq.getAttribute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Value getValue(Attribute attr) {
/*  61 */     Object selected = getSelectedItem();
/*  62 */     if (selected instanceof DisplayAdapter) {
/*     */       
/*  64 */       selected = ((DisplayAdapter)selected).getAdaptee();
/*     */     }
/*  66 */     else if (selected instanceof String) {
/*  67 */       selected = new ValueAdapter(((String)getSelectedItem()).toUpperCase(Locale.ENGLISH));
/*     */     } 
/*     */     
/*  70 */     return (Value)selected;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addKeyListener() {
/*  75 */     getEditor().getEditorComponent().addKeyListener(new KeyListener()
/*     */         {
/*     */           public void keyTyped(KeyEvent e)
/*     */           {
/*  79 */             char ch = e.getKeyChar();
/*     */             
/*  81 */             if (Character.isLowerCase(ch)) {
/*     */               try {
/*  83 */                 JTextComponent c = (JTextComponent)e.getSource();
/*  84 */                 c.getDocument().insertString(c.getCaretPosition(), "" + Character.toUpperCase(ch), null);
/*  85 */                 e.consume();
/*  86 */               } catch (BadLocationException ex) {}
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void keyReleased(KeyEvent e) {}
/*     */ 
/*     */           
/*     */           public void keyPressed(KeyEvent e) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMouseListener() {
/* 101 */     this.popupMenu = new PopupMenu(this);
/*     */     
/* 103 */     getEditor().getEditorComponent().addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) {
/*     */             JTextField txtField;
/* 105 */             switch (e.getModifiers()) {
/*     */               case 4:
/* 107 */                 VINJComboBox.this.popupMenu.getJPopupMenu().show(e.getComponent(), e.getX(), e.getY());
/* 108 */                 txtField = (JTextField)e.getComponent();
/* 109 */                 txtField.grabFocus();
/*     */                 break;
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent e) {
/* 116 */             switch (e.getModifiers()) {
/*     */               case 4:
/* 118 */                 if (VINJComboBox.this.popupMenu.getJPopupMenu().isShowing()) {
/* 119 */                   JTextField txtField = (JTextField)e.getComponent();
/* 120 */                   txtField.selectAll();
/* 121 */                   txtField.grabFocus();
/*     */                 } 
/*     */                 break;
/*     */             } 
/*     */           } }
/*     */       );
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\VINJComboBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */