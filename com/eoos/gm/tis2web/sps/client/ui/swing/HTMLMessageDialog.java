/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.impl.TraceInfoData;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.javio.webwindow.PrintableHTMLPane;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.util.List;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class HTMLMessageDialog
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(HTMLMessageDialog.class);
/*     */   
/*  27 */   protected LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*     */   protected SPSFrame frame;
/*     */   
/*     */   protected JOptionPane optionPaneHTML;
/*     */   
/*     */   protected CustomizeHtmlRender webWindowHTML;
/*     */   
/*     */   protected boolean responseDialogHTML;
/*     */   
/*     */   protected JDialog dialogHTML;
/*     */   
/*  39 */   protected String print = this.resourceProvider.getLabel(null, "print");
/*     */   
/*  41 */   protected String ok = this.resourceProvider.getLabel(null, "ok");
/*     */   
/*  43 */   protected String cancel = this.resourceProvider.getLabel(null, "cancel");
/*     */   
/*  45 */   protected final JButton[] PRINT_OK_CANCEL = new JButton[] { new JButton(this.print), new JButton(this.ok), new JButton(this.cancel) };
/*     */   
/*  47 */   protected final JButton[] PRINT_OK = new JButton[] { new JButton(this.print), new JButton(this.ok) };
/*     */   
/*     */   public HTMLMessageDialog(SPSFrame frame) {
/*  50 */     this.frame = frame;
/*  51 */     initActionButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initActionButtons() {
/*  56 */     this.PRINT_OK_CANCEL[0].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  58 */             HTMLMessageDialog.this.onPrint();
/*     */           }
/*     */         });
/*     */     
/*  62 */     this.PRINT_OK_CANCEL[1].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  64 */             HTMLMessageDialog.this.onOK();
/*     */           }
/*     */         });
/*     */     
/*  68 */     this.PRINT_OK_CANCEL[2].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  70 */             HTMLMessageDialog.this.onCancel();
/*     */           }
/*     */         });
/*     */     
/*  74 */     this.PRINT_OK[0].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  76 */             HTMLMessageDialog.this.onPrint();
/*     */           }
/*     */         });
/*     */     
/*  80 */     this.PRINT_OK[1].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent evt) {
/*  82 */             HTMLMessageDialog.this.onOK();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void onPrint() {
/*  88 */     PrintableHTMLPane mPane = (PrintableHTMLPane)this.webWindowHTML.getHTMLPane();
/*  89 */     PrinterJob job = PrinterJob.getPrinterJob();
/*  90 */     PageFormat defaultPage = job.defaultPage();
/*  91 */     mPane.setPageFormat(defaultPage);
/*  92 */     mPane.printPage(false, false, false, false, true, null, null, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     mPane.setZoom(1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onOK() {
/* 106 */     this.responseDialogHTML = true;
/* 107 */     this.dialogHTML.setVisible(false);
/*     */   }
/*     */   
/*     */   protected void onCancel() {
/* 111 */     this.responseDialogHTML = false;
/* 112 */     this.dialogHTML.setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean displayQuestionHTMLMessage(String errMsg, List traceInfoList) {
/* 117 */     dialogHTMLMessage(errMsg, traceInfoList, this.PRINT_OK_CANCEL);
/* 118 */     return this.responseDialogHTML;
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayHTMLMessage(String errMsg, List traceInfoList) {
/* 123 */     dialogHTMLMessage(errMsg, traceInfoList, this.PRINT_OK);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dialogHTMLMessage(String errMsg, List traceInfoList, JButton[] buttons) {
/* 128 */     TraceInfoData traceData = new TraceInfoData(errMsg, traceInfoList);
/* 129 */     String html = traceData.getHTML();
/* 130 */     String title = this.resourceProvider.getLabel(null, "trace-info.title");
/*     */     try {
/* 132 */       this.webWindowHTML = ViewerHTMLDialog.getWebWindow(this.frame, html);
/* 133 */       JScrollPane scrollPane = new JScrollPane((Component)this.webWindowHTML);
/* 134 */       scrollPane.getViewport().add((Component)this.webWindowHTML);
/* 135 */       this.webWindowHTML.scrollToPosition(0);
/* 136 */       this.optionPaneHTML = new JOptionPane(scrollPane, -1, -1, null, (Object[])buttons, buttons[1]);
/* 137 */       this.dialogHTML = this.optionPaneHTML.createDialog(this.frame, title);
/* 138 */       this.dialogHTML.pack();
/* 139 */       this.dialogHTML.setSize(new Dimension((int)SwingUtils.getDialogHTML_Width(), (int)SwingUtils.getDialogHTML_Height()));
/* 140 */       this.dialogHTML.setLocation(UIUtil.getCenterLocation(this.dialogHTML, this.frame));
/* 141 */       this.dialogHTML.setVisible(true);
/* 142 */       this.dialogHTML.setModal(true);
/*     */     }
/* 144 */     catch (Exception except) {
/* 145 */       log.error("unable to display html message Dialog, -exception: " + except.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\HTMLMessageDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */