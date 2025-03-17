/*     */ package com.eoos.gm.tis2web.sps.client.ui.pdf;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.util.Transform;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.lowagie.text.Document;
/*     */ import com.lowagie.text.DocumentException;
/*     */ import com.lowagie.text.Element;
/*     */ import com.lowagie.text.FontFactory;
/*     */ import com.lowagie.text.PageSize;
/*     */ import com.lowagie.text.Paragraph;
/*     */ import com.lowagie.text.Phrase;
/*     */ import com.lowagie.text.pdf.PdfPCell;
/*     */ import com.lowagie.text.pdf.PdfPTable;
/*     */ import com.lowagie.text.pdf.PdfWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PDFStarter
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(SPSFrame.class);
/*     */   
/*     */   public static final String PDFFILE = "summary.pdf";
/*     */   
/*     */   public static final String PDFDIRECTORY = "pdf";
/*     */   
/*  41 */   public static final String SPS_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + "sps";
/*     */   
/*  43 */   public static final String PATH_TO_PDF_DIR = SPS_PATH + System.getProperty("file.separator") + System.getProperty("file.separator") + "pdf";
/*     */   
/*  45 */   public static final String PATH_TO_PDF_FILE = PATH_TO_PDF_DIR + System.getProperty("file.separator") + "summary.pdf";
/*     */   
/*  47 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  49 */   private TableModel summary = null;
/*     */   
/*  51 */   private TableModel vehicleData = null;
/*     */   
/*  53 */   private TableModel description = null;
/*     */   
/*     */   private String vin;
/*     */   
/*  57 */   File pdfFile = null;
/*     */ 
/*     */   
/*     */   public PDFStarter(TableModel summary, TableModel vehicleData, TableModel description, String vin) {
/*  61 */     this.summary = summary;
/*  62 */     this.vehicleData = vehicleData;
/*  63 */     this.description = description;
/*  64 */     this.vin = vin;
/*     */   }
/*     */   
/*     */   protected PdfPTable createTablePDF(JTable table, float[] widths) throws DocumentException {
/*  68 */     TableModel model = table.getModel();
/*  69 */     PdfPTable itext = new PdfPTable(model.getColumnCount());
/*     */     try {
/*  71 */       itext.setWidthPercentage(100.0F);
/*  72 */       if (widths != null)
/*  73 */         itext.setWidths(widths); 
/*     */       int i;
/*  75 */       for (i = 0; i < model.getColumnCount(); i++) {
/*  76 */         PdfPCell cell = new PdfPCell(new Phrase(model.getColumnName(i), FontFactory.getFont("Helvetica", 12.0F, 1)));
/*  77 */         cell.setHorizontalAlignment(1);
/*  78 */         itext.addCell(cell);
/*     */       } 
/*  80 */       for (i = 0; i < model.getRowCount(); i++) {
/*  81 */         for (int j = 0; j < model.getColumnCount(); j++) {
/*  82 */           Object cellValue = model.getValueAt(i, j);
/*  83 */           String value = cellValue.toString();
/*  84 */           value = Transform.convertHtmlToString(value);
/*  85 */           PdfPCell cell = new PdfPCell(new Phrase((value == null) ? "" : value));
/*     */           
/*  87 */           if (model.getColumnCount() > 2 && (
/*  88 */             j == 1 || j == 2 || j == 3)) {
/*  89 */             cell.setHorizontalAlignment(1);
/*     */           }
/*     */           
/*  92 */           itext.addCell(cell);
/*     */         } 
/*     */       } 
/*  95 */     } catch (DocumentException e) {
/*  96 */       log.error("createTablePDF, exception:" + e.getMessage());
/*     */     } 
/*  98 */     return itext;
/*     */   }
/*     */   
/*     */   public void printSummaryPDF() throws FileNotFoundException {
/* 102 */     File pdfDir = null;
/*     */     
/*     */     try {
/* 105 */       pdfDir = new File(PATH_TO_PDF_DIR);
/* 106 */       if (!pdfDir.exists())
/* 107 */         pdfDir.mkdir(); 
/* 108 */       this.pdfFile = File.createTempFile("summary", ".pdf", pdfDir);
/* 109 */     } catch (IOException e1) {
/* 110 */       log.info("IOException in printSummaryPDF method, " + e1.getMessage());
/*     */     } 
/*     */     
/*     */     try {
/* 114 */       Document document = new Document(PageSize.A4.rotate());
/* 115 */       document.addTitle(resourceProvider.getLabel(null, "summaryScreen.title"));
/* 116 */       PdfWriter.getInstance(document, new FileOutputStream(this.pdfFile));
/*     */       
/* 118 */       document.open();
/*     */       
/* 120 */       Paragraph p = new Paragraph(new Phrase(resourceProvider.getLabel(null, "summaryScreen.title"), FontFactory.getFont("Helvetica", 14.0F, 1)));
/* 121 */       document.add((Element)p);
/* 122 */       p = new Paragraph(new Phrase(this.vin, FontFactory.getFont("Helvetica", 12.0F, 1)));
/* 123 */       p.setAlignment(2);
/* 124 */       p.setSpacingBefore(-15.0F);
/* 125 */       p.setSpacingAfter(5.0F);
/* 126 */       document.add((Element)p);
/*     */       
/* 128 */       document.add((Element)getSummaryPDFTable());
/*     */       
/* 130 */       p = new Paragraph(new Phrase("Vehicle Data", FontFactory.getFont("Helvetica", 12.0F, 1)));
/* 131 */       p.setSpacingBefore(10.0F);
/* 132 */       p.setSpacingAfter(10.0F);
/* 133 */       document.add((Element)p);
/*     */       
/* 135 */       document.add((Element)createTablePDF(new JTable(this.vehicleData), null));
/* 136 */       document.close();
/*     */     }
/* 138 */     catch (DocumentException e) {
/* 139 */       log.error("printSummaryPDF, exception:" + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PdfPTable getSummaryPDFTable() {
/* 145 */     PdfPTable pdfTable = null;
/*     */     
/*     */     try {
/* 148 */       if (this.description != null && this.description.getColumnCount() != 0) {
/*     */         
/* 150 */         pdfTable = new PdfPTable(2);
/* 151 */         pdfTable.getDefaultCell().setBorder(0);
/* 152 */         pdfTable.setWidthPercentage(100.0F);
/* 153 */         pdfTable.setWidths(new float[] { 0.65F, 0.35F });
/*     */         
/* 155 */         float[] widths = null;
/* 156 */         JTable table = new JTable(this.summary);
/* 157 */         widths = new float[] { 0.4F, 0.25F, 0.35F };
/*     */         
/* 159 */         PdfPTable itext = createTablePDF(table, widths);
/* 160 */         pdfTable.addCell(itext);
/*     */         
/* 162 */         if (this.description.getRowCount() == 0) {
/* 163 */           ((DefaultTableModel)this.description).addRow((Object[])new String[] { "" });
/*     */         } else {
/* 165 */           DefaultTableModel mdescription = new DefaultTableModel(this.description.getRowCount(), this.description.getColumnCount());
/* 166 */           for (int i = 0; i < this.description.getRowCount(); i++) {
/* 167 */             for (int k = 0; k < this.description.getColumnCount(); k++) {
/* 168 */               String value = (String)this.description.getValueAt(i, k);
/* 169 */               mdescription.setValueAt(fixHTML(value), i, k);
/*     */             } 
/*     */           } 
/* 172 */           Vector<String> columns = new Vector();
/* 173 */           for (int j = 0; j < this.description.getColumnCount(); j++) {
/* 174 */             columns.add(this.description.getColumnName(j));
/*     */           }
/* 176 */           mdescription.setColumnIdentifiers(columns);
/* 177 */           this.description = mdescription;
/*     */         } 
/* 179 */         float[] widths1 = { 1.0F };
/* 180 */         PdfPTable itext1 = createTablePDF(new JTable(this.description), widths1);
/* 181 */         pdfTable.addCell(itext1);
/*     */       } else {
/*     */         
/* 184 */         float[] widths = { 0.1F, 0.05F, 0.1F, 0.1F, 0.65F };
/* 185 */         JTable table = new JTable(this.summary);
/* 186 */         pdfTable = createTablePDF(table, widths);
/*     */       }
/*     */     
/*     */     }
/* 190 */     catch (DocumentException e) {
/* 191 */       log.error("printSummaryPDF, exception:" + e.getMessage());
/*     */     } 
/* 193 */     return pdfTable;
/*     */   }
/*     */ 
/*     */   
/*     */   private String fixHTML(String html) {
/* 198 */     html = StringUtilities.replace(html, "&lt;a href=&quot;", "<a href=\"");
/* 199 */     html = StringUtilities.replace(html, "&quot;&gt;", "\">");
/* 200 */     html = StringUtilities.replace(html, "&lt;/a&gt;", "");
/* 201 */     int idx = html.indexOf("<a");
/* 202 */     if (idx >= 0) {
/* 203 */       StringBuffer buffer = new StringBuffer();
/* 204 */       buffer.append(html.substring(0, idx));
/* 205 */       for (int i = 0; i < html.length(); i++) {
/* 206 */         if (html.charAt(i) == '>') {
/* 207 */           buffer.append(html.substring(i + 1));
/* 208 */           return fixHTML(buffer.toString());
/*     */         } 
/*     */       } 
/*     */     } 
/* 212 */     return html;
/*     */   }
/*     */   
/*     */   public void openPDF() {
/*     */     try {
/* 217 */       Runtime.getRuntime().exec("cmd /c \"" + this.pdfFile.getAbsoluteFile().getPath() + "\"");
/* 218 */     } catch (IOException e) {
/* 219 */       log.error("openPDF method, exception: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\pdf\PDFStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */