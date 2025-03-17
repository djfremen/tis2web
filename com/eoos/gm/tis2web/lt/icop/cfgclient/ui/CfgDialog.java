/*     */ package com.eoos.gm.tis2web.lt.icop.cfgclient.ui;
/*     */ 
/*     */ import com.eoos.log.Log4jUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CfgDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  21 */   private static final Logger log = Logger.getLogger(CfgDialog.class);
/*     */   
/*     */   private Object sync;
/*     */   
/*     */   public CfgDialog(Object sync, final UICallback callback) {
/*  26 */     this.sync = sync;
/*  27 */     final UICallback callback2 = new UICallback()
/*     */       {
/*     */         public String toString(Object value) {
/*  30 */           return callback.toString();
/*     */         }
/*     */         
/*     */         public void onOK(UICallback.Selection selection) {
/*  34 */           callback.onOK(selection);
/*  35 */           CfgDialog.this.dispose();
/*     */         }
/*     */         
/*     */         public void onCancel() {
/*  39 */           CfgDialog.this.dispose();
/*     */         }
/*     */         
/*     */         public List getOptions(UICallback.ID id) {
/*  43 */           return callback.getOptions(id);
/*     */         }
/*     */         
/*     */         public String getLabel(String key) {
/*  47 */           return callback.getLabel(key);
/*     */         }
/*     */         
/*     */         public Object getCurrentValue(UICallback.ID id) {
/*  51 */           return callback.getCurrentValue(id);
/*     */         }
/*     */         
/*     */         public Locale getLocale() {
/*  55 */           return callback.getLocale();
/*     */         }
/*     */       };
/*     */     
/*  59 */     getContentPane().add(new RootPanel(callback2));
/*  60 */     addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/*  64 */             callback2.onCancel();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/*  73 */     super.dispose();
/*  74 */     synchronized (this.sync) {
/*  75 */       this.sync.notify();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void show(final UICallback callback) throws Exception {
/*  81 */     final Object sync = new Object();
/*  82 */     synchronized (sync) {
/*  83 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/*  86 */               JDialog dialog = new CfgDialog(sync, callback);
/*  87 */               dialog.setTitle(callback.getLabel("dialog.title"));
/*  88 */               dialog.pack();
/*  89 */               dialog.setLocationRelativeTo(null);
/*  90 */               dialog.setVisible(true);
/*     */             }
/*     */           });
/*     */       
/*  94 */       sync.wait();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 100 */     Log4jUtil.attachConsoleAppender();
/* 101 */     show(new UICallback()
/*     */         {
/*     */           public String toString(Object value) {
/* 104 */             return String.valueOf(value);
/*     */           }
/*     */           
/*     */           public List getOptions(UICallback.ID id) {
/* 108 */             return Arrays.asList(new Object[] { "1", "2", "3" });
/*     */           }
/*     */           
/*     */           public String getLabel(String key) {
/* 112 */             return key;
/*     */           }
/*     */           
/*     */           public Object getCurrentValue(UICallback.ID id) {
/* 116 */             List options = getOptions(id);
/* 117 */             int index = (int)Util.createRandom(0L, (options.size() - 1));
/* 118 */             return options.get(index);
/*     */           }
/*     */           
/*     */           public void onCancel() {
/* 122 */             CfgDialog.log.debug("cancelled");
/*     */           }
/*     */           
/*     */           public void onOK(UICallback.Selection selection) {
/* 126 */             for (UICallback.ID id : UICallback.ID.values()) {
/* 127 */               CfgDialog.log.debug(id.toString() + " -> " + selection.getSelection(id));
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public Locale getLocale() {
/* 133 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\icop\cfgclien\\ui\CfgDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */