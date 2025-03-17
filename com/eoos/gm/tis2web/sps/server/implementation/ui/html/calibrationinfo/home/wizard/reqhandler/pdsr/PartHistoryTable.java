/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.PartContext;
/*     */ import com.eoos.html.element.HtmlElementBase;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PartHistoryTable extends HtmlElementBase {
/*     */   private ClientContext context;
/*     */   private Callback callback;
/*     */   
/*     */   public static interface Callback {
/*     */     Part getCurrentPart();
/*     */     
/*     */     Part getPredecessor(Part param1Part);
/*     */   }
/*     */   
/*     */   private static class SubCalibrations {
/*     */     private static String template;
/*     */     
/*     */     static {
/*     */       try {
/*  27 */         template = ApplicationContext.getInstance().loadFile(PartHistoryTable.class, "parthistorytable2.html", null).toString();
/*  28 */       } catch (Exception e) {
/*  29 */         throw new ExceptionWrapper(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  34 */     private static String rowTemplateFirst = "<tr><td rowspan=\"{SUBCOUNT}\">{PARTNUMBER}</td><td>{CALID}</td><td>{CVN}</td><td rowspan=\"{SUBCOUNT}\">{DESCRIPTION}</td></tr>";
/*     */     
/*  36 */     private static String rowTemplateSucceeding = "<tr><td>{CALID}</td><td>{CVN}</td></tr>";
/*     */     
/*     */     public static String getHtmlCode(Map params, PartHistoryTable.Callback callback, ClientContext context) {
/*  39 */       Part part = callback.getCurrentPart();
/*  40 */       if (part == null) {
/*  41 */         return "";
/*     */       }
/*  43 */       StringBuffer retValue = new StringBuffer(template);
/*  44 */       while (part != null) {
/*  45 */         PartContext pc = PartContext.getInstance(part);
/*  46 */         for (int i = 0; i < pc.getCalibrationPartNumbers().size(); i++) {
/*  47 */           String calid = pc.getCalibrationPartNumbers().get(i);
/*  48 */           String cvn = part.getCalibrationVerificationNumber(calid);
/*  49 */           cvn = (cvn == null) ? "N/A" : cvn;
/*  50 */           StringBuffer row = null;
/*  51 */           if (i == 0) {
/*  52 */             row = new StringBuffer(rowTemplateFirst);
/*  53 */             StringUtilities.replace(row, "{PARTNUMBER}", part.getPartNumber());
/*  54 */             StringUtilities.replace(row, "{CALID}", calid);
/*  55 */             StringUtilities.replace(row, "{CVN}", cvn);
/*  56 */             StringUtilities.replace(row, "{DESCRIPTION}", part.getDescription(context.getLocale()));
/*  57 */             StringUtilities.replace(row, "{SUBCOUNT}", String.valueOf(pc.getCalibrationPartNumbers().size()));
/*     */           } else {
/*  59 */             row = new StringBuffer(rowTemplateSucceeding);
/*  60 */             StringUtilities.replace(row, "{CALID}", calid);
/*  61 */             StringUtilities.replace(row, "{CVN}", cvn);
/*     */           } 
/*  63 */           StringUtilities.replace(retValue, "{ROWS}", row.toString() + "{ROWS}");
/*     */         } 
/*     */         
/*  66 */         part = callback.getPredecessor(part);
/*     */       } 
/*     */       
/*  69 */       StringUtilities.replace(retValue, "{ROWS}", "");
/*  70 */       StringUtilities.replace(retValue, "{LABEL_PARTNUMBER}", context.getLabel("sps.partnumber"));
/*  71 */       StringUtilities.replace(retValue, "{LABEL_CVN}", context.getLabel("sps.cvn"));
/*  72 */       StringUtilities.replace(retValue, "{LABEL_DESCRIPTION}", context.getLabel("description"));
/*  73 */       StringUtilities.replace(retValue, "{LABEL_CALID}", context.getLabel("sps.calid"));
/*  74 */       return retValue.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class NoSubCalibrations
/*     */   {
/*     */     private static String template;
/*     */     
/*     */     static {
/*     */       try {
/*  85 */         template = ApplicationContext.getInstance().loadFile(PartHistoryTable.class, "parthistorytable.html", null).toString();
/*  86 */       } catch (Exception e) {
/*  87 */         throw new ExceptionWrapper(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  92 */     private static String rowTemplate = "<tr><td>{PARTNUMBER}</td><td>{CVN}</td><td>{DESCRIPTION}</td></tr>";
/*     */     
/*     */     public static String getHtmlCode(Map params, PartHistoryTable.Callback callback, ClientContext context) {
/*  95 */       Part part = callback.getCurrentPart();
/*  96 */       if (part == null) {
/*  97 */         return "";
/*     */       }
/*  99 */       StringBuffer retValue = new StringBuffer(template);
/* 100 */       while (part != null) {
/* 101 */         StringBuffer row = new StringBuffer(rowTemplate);
/* 102 */         StringUtilities.replace(row, "{PARTNUMBER}", part.getPartNumber());
/* 103 */         StringUtilities.replace(row, "{CVN}", (part.getCVN() != null) ? part.getCVN() : context.getLabel("not.available"));
/* 104 */         StringUtilities.replace(row, "{DESCRIPTION}", part.getDescription(context.getLocale()));
/* 105 */         StringUtilities.replace(retValue, "{ROWS}", row.toString() + "{ROWS}");
/* 106 */         part = callback.getPredecessor(part);
/*     */       } 
/* 108 */       StringUtilities.replace(retValue, "{ROWS}", "");
/* 109 */       StringUtilities.replace(retValue, "{LABEL_PARTNUMBER}", context.getLabel("sps.partnumber"));
/* 110 */       StringUtilities.replace(retValue, "{LABEL_CVN}", context.getLabel("sps.cvn"));
/* 111 */       StringUtilities.replace(retValue, "{LABEL_DESCRIPTION}", context.getLabel("description"));
/*     */       
/* 113 */       return retValue.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PartHistoryTable(ClientContext context, Callback callback) {
/* 123 */     this.context = context;
/* 124 */     this.callback = callback;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 129 */     Part part = this.callback.getCurrentPart();
/* 130 */     if (PartContext.getInstance(part).hasSubParts()) {
/* 131 */       return SubCalibrations.getHtmlCode(params, this.callback, this.context);
/*     */     }
/* 133 */     return NoSubCalibrations.getHtmlCode(params, this.callback, this.context);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\PartHistoryTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */