/*     */ package com.eoos.gm.tis2web.sps.client.ui.print;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Paper;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.util.Locale;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrintableComponent
/*     */   implements Printable
/*     */ {
/*  26 */   private static final Logger log = Logger.getLogger(PrintableComponent.class);
/*     */   
/*  28 */   protected Component component = null;
/*     */   
/*  30 */   protected PageFormat defaultPage = null;
/*     */   
/*  32 */   protected Paper paperUserDefined = null;
/*     */   
/*     */   public static final String A4 = "A4";
/*     */   
/*     */   public static final String A5 = "A5";
/*     */   
/*     */   public static final String B4 = "B4";
/*     */   
/*     */   public static final String B5 = "B5";
/*     */   
/*     */   public static final String LETTER = "Letter";
/*     */   
/*     */   public static final String LEGAL = "Legal";
/*     */   
/*     */   public static final String LANDSCAPE = "Landscape";
/*     */   
/*     */   public static final String PORTRAIT = "PORTRAIT";
/*     */   
/*     */   public static final String REVERSE_LANDSCAPE = "REVERSE_LANDSCAPE";
/*     */   
/*  52 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */ 
/*     */   
/*     */   public PrintableComponent(Component c) {
/*  56 */     this.component = c;
/*     */   }
/*     */   
/*     */   protected Paper getPaperFormat(String paperFormat) {
/*  60 */     Paper paper = new Paper();
/*     */     
/*  62 */     if (paperFormat == null) {
/*  63 */       return getPaperFormatDefault();
/*     */     }
/*     */     
/*  66 */     if (paperFormat.equalsIgnoreCase("A4")) {
/*  67 */       paper.setSize(595.275D, 841.889D);
/*     */     }
/*  69 */     else if (paperFormat.equalsIgnoreCase("A5")) {
/*  70 */       paper.setSize(419.527D, 595.275D);
/*     */     }
/*  72 */     else if (paperFormat.equalsIgnoreCase("Letter")) {
/*  73 */       paper.setSize(612.0D, 792.0D);
/*     */     }
/*  75 */     else if (paperFormat.equalsIgnoreCase("Legal")) {
/*  76 */       paper.setSize(612.0D, 1008.0D);
/*     */     }
/*  78 */     else if (paperFormat.equalsIgnoreCase("B4")) {
/*  79 */       paper.setSize(702.0D, 1008.0D);
/*     */     }
/*  81 */     else if (paperFormat.equalsIgnoreCase("B5")) {
/*  82 */       paper.setSize(504.0D, 702.0D);
/*     */     }
/*     */     else {
/*     */       
/*  86 */       return getPaperFormatDefault();
/*     */     } 
/*     */ 
/*     */     
/*  90 */     return paper;
/*     */   }
/*     */   
/*     */   protected Paper getPaperFormatDefault() {
/*  94 */     Paper paper = new Paper();
/*     */ 
/*     */ 
/*     */     
/*  98 */     String defaultCountry = Locale.getDefault().getCountry();
/*  99 */     if (!Locale.getDefault().equals(Locale.ENGLISH) && defaultCountry != null && !defaultCountry.equals(Locale.US.getCountry()) && !defaultCountry.equals(Locale.CANADA.getCountry())) {
/*     */       
/* 101 */       paper.setSize(595.275D, 841.889D);
/*     */     } else {
/*     */       
/* 104 */       paper.setSize(612.0D, 792.0D);
/*     */     } 
/* 106 */     return paper;
/*     */   }
/*     */   
/*     */   protected PageFormat getPageFormat(String pageOrient) {
/* 110 */     PageFormat pageFormat = new PageFormat();
/* 111 */     pageFormat.setOrientation(0);
/* 112 */     if (pageOrient == null) {
/* 113 */       return pageFormat;
/*     */     }
/* 115 */     if (pageOrient.equalsIgnoreCase("PORTRAIT")) {
/* 116 */       pageFormat.setOrientation(1);
/*     */     }
/* 118 */     else if (pageOrient.equalsIgnoreCase("REVERSE_LANDSCAPE")) {
/* 119 */       pageFormat.setOrientation(2);
/*     */     } 
/*     */     
/* 122 */     return pageFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getScale() {
/* 127 */     double m_wPage = this.defaultPage.getWidth();
/* 128 */     double m_hPage = this.defaultPage.getHeight();
/* 129 */     if (this.paperUserDefined != null) {
/* 130 */       m_wPage = this.paperUserDefined.getWidth();
/* 131 */       m_hPage = this.paperUserDefined.getHeight();
/*     */     } 
/* 133 */     Dimension d = this.component.getPreferredSize();
/*     */     
/* 135 */     double scale = Math.min(m_wPage / d.width, m_hPage / d.height);
/* 136 */     return scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(String pageOrient, String paperFormat) throws PrinterException {
/*     */     try {
/* 146 */       PrinterJob job = PrinterJob.getPrinterJob();
/*     */ 
/*     */       
/* 149 */       this.defaultPage = job.defaultPage();
/* 150 */       if (job.getPrintService() == null) {
/* 151 */         String message = resourceProvider.getMessage(null, "sps.exception.no-print");
/* 152 */         SPSFrame.displayErrorMessage(message);
/*     */         return;
/*     */       } 
/* 155 */       PageFormat pageFormat = getPageFormat(pageOrient);
/* 156 */       this.paperUserDefined = getPaperFormat(paperFormat);
/* 157 */       if (this.paperUserDefined != null)
/* 158 */         pageFormat.setPaper(this.paperUserDefined); 
/* 159 */       job.setPrintable(this, pageFormat);
/* 160 */       job.print();
/*     */     }
/* 162 */     catch (PrinterException ex) {
/* 163 */       SPSFrame.getInstance().handleException(ex);
/* 164 */       log.debug("Exception in print method:" + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int print(Graphics g, PageFormat format, int pagenum) {
/*     */     try {
/* 177 */       if (pagenum > 0) {
/* 178 */         return 1;
/*     */       }
/*     */       
/* 181 */       Graphics2D g2 = (Graphics2D)g;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       g2.translate(format.getImageableX(), format.getImageableY());
/* 187 */       double scale = getScale();
/* 188 */       if (scale < 1.0D) {
/* 189 */         g2.scale(scale, scale);
/*     */       }
/* 191 */       this.component.paint(g2);
/*     */ 
/*     */     
/*     */     }
/* 195 */     catch (Exception e) {
/* 196 */       System.out.println("Exception in print :" + e.getMessage());
/*     */     } 
/* 198 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\print\PrintableComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */