/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary.nao;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.Navigation;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.PartContext;
/*     */ import com.eoos.html.element.HtmlElementBase;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PartHistoryTable
/*     */   extends HtmlElementBase
/*     */ {
/*     */   private ClientContext context;
/*     */   private Callback callback;
/*     */   
/*     */   private static class SubCalibrations
/*     */   {
/*     */     private static String template;
/*     */     
/*     */     static {
/*     */       try {
/*  30 */         template = ApplicationContext.getInstance().loadFile(PartHistoryTable.class, "parthistorytable2.html", null).toString();
/*  31 */       } catch (Exception e) {
/*  32 */         throw new ExceptionWrapper(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  37 */     private static String rowTemplateFirst = "<tr><td rowspan=\"{SUBCOUNT}\">{PARTNUMBER}</td><td>{CALID}</td><td>{CVN}</td><td rowspan=\"{SUBCOUNT}\">{BULLETINNUMBER}</td><td rowspan=\"{SUBCOUNT}\">{DESCRIPTION}</td></tr>";
/*     */     
/*  39 */     private static String rowTemplateSucceeding = "<tr><td>{CALID}</td><td>{CVN}</td></tr>";
/*     */     
/*     */     public static String getHtmlCode(Map params, PartHistoryTable.Callback callback, ClientContext context) {
/*  42 */       Part part = callback.getCurrentPart();
/*  43 */       if (part == null) {
/*  44 */         return "";
/*     */       }
/*  46 */       StringBuffer retValue = new StringBuffer(template);
/*  47 */       while (part != null) {
/*  48 */         PartContext pc = PartContext.getInstance(part);
/*  49 */         for (int i = 0; i < pc.getCalibrationPartNumbers().size(); i++) {
/*  50 */           String calid = pc.getCalibrationPartNumbers().get(i);
/*  51 */           String cvn = part.getCalibrationVerificationNumber(calid);
/*  52 */           cvn = (cvn == null) ? "N/A" : cvn;
/*  53 */           StringBuffer row = null;
/*  54 */           if (i == 0) {
/*  55 */             row = new StringBuffer(rowTemplateFirst);
/*  56 */             StringUtilities.replace(row, "{PARTNUMBER}", part.getPartNumber());
/*  57 */             StringUtilities.replace(row, "{CALID}", calid);
/*  58 */             StringUtilities.replace(row, "{CVN}", cvn);
/*  59 */             StringUtilities.replace(row, "{BULLETINNUMBER}", PartHistoryTable.getBulletinsDisplayCode(part.getBulletins()));
/*  60 */             StringUtilities.replace(row, "{DESCRIPTION}", part.getDescription(context.getLocale()));
/*  61 */             StringUtilities.replace(row, "{SUBCOUNT}", String.valueOf(pc.getCalibrationPartNumbers().size()));
/*     */           } else {
/*  63 */             row = new StringBuffer(rowTemplateSucceeding);
/*  64 */             StringUtilities.replace(row, "{CALID}", calid);
/*  65 */             StringUtilities.replace(row, "{CVN}", cvn);
/*     */           } 
/*  67 */           StringUtilities.replace(retValue, "{ROWS}", row.toString() + "{ROWS}");
/*     */         } 
/*     */         
/*  70 */         part = callback.getPredecessor(part);
/*     */       } 
/*     */       
/*  73 */       StringUtilities.replace(retValue, "{ROWS}", "");
/*  74 */       StringUtilities.replace(retValue, "{LABEL_PARTNUMBER}", context.getLabel("sps.partnumber"));
/*  75 */       StringUtilities.replace(retValue, "{LABEL_CVN}", context.getLabel("sps.cvn"));
/*  76 */       StringUtilities.replace(retValue, "{LABEL_DESCRIPTION}", context.getLabel("description"));
/*  77 */       StringUtilities.replace(retValue, "{LABEL_CALID}", context.getLabel("sps.calid"));
/*  78 */       StringUtilities.replace(retValue, "{LABEL_BULLETINNUMBER}", context.getLabel("sps.bulletinnumber"));
/*  79 */       return retValue.toString();
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
/*  90 */         template = ApplicationContext.getInstance().loadFile(PartHistoryTable.class, "parthistorytable.html", null).toString();
/*  91 */       } catch (Exception e) {
/*  92 */         throw new ExceptionWrapper(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  97 */     private static String rowTemplate = "<tr><td>{PARTNUMBER}</td><td>{CVN}</td><td>{BULLETINNUMBER}</td><td>{DESCRIPTION}</td></tr>";
/*     */     
/*     */     public static String getHtmlCode(Map params, PartHistoryTable.Callback callback, ClientContext context) {
/* 100 */       Part part = callback.getCurrentPart();
/* 101 */       if (part == null) {
/* 102 */         return "";
/*     */       }
/* 104 */       StringBuffer retValue = new StringBuffer(template);
/* 105 */       while (part != null) {
/* 106 */         StringBuffer row = new StringBuffer(rowTemplate);
/* 107 */         StringUtilities.replace(row, "{PARTNUMBER}", part.getPartNumber());
/* 108 */         StringUtilities.replace(row, "{CVN}", (part.getCVN() != null) ? part.getCVN() : context.getLabel("not.available"));
/* 109 */         StringUtilities.replace(row, "{BULLETINNUMBER}", PartHistoryTable.getBulletinsDisplayCode(part.getBulletins()));
/* 110 */         StringUtilities.replace(row, "{DESCRIPTION}", part.getDescription(context.getLocale()));
/*     */         
/* 112 */         StringUtilities.replace(retValue, "{ROWS}", row.toString() + "{ROWS}");
/* 113 */         part = callback.getPredecessor(part);
/*     */       } 
/* 115 */       StringUtilities.replace(retValue, "{ROWS}", "");
/* 116 */       StringUtilities.replace(retValue, "{LABEL_PARTNUMBER}", context.getLabel("sps.partnumber"));
/* 117 */       StringUtilities.replace(retValue, "{LABEL_CVN}", context.getLabel("sps.cvn"));
/* 118 */       StringUtilities.replace(retValue, "{LABEL_DESCRIPTION}", context.getLabel("description"));
/* 119 */       StringUtilities.replace(retValue, "{LABEL_BULLETINNUMBER}", context.getLabel("sps.bulletinnumber"));
/* 120 */       return retValue.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PartHistoryTable(ClientContext context, final Part selectedPart, final Navigation navigation) {
/* 130 */     this.context = context;
/* 131 */     this.callback = new Callback() {
/*     */         public Part getCurrentPart() {
/* 133 */           return selectedPart;
/*     */         }
/*     */         
/*     */         public Part getPredecessor(Part part) {
/* 137 */           return navigation.getParent(part);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 144 */     Part part = this.callback.getCurrentPart();
/* 145 */     if (PartContext.getInstance(part).hasSubParts()) {
/* 146 */       return SubCalibrations.getHtmlCode(params, this.callback, this.context);
/*     */     }
/* 148 */     return NoSubCalibrations.getHtmlCode(params, this.callback, this.context);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getBulletinsDisplayCode(List bulletins) {
/* 153 */     if (bulletins == null || bulletins.size() == 0) {
/* 154 */       return " - ";
/*     */     }
/* 156 */     StringBuffer retValue = new StringBuffer();
/* 157 */     for (Iterator<String> iter = bulletins.iterator(); iter.hasNext();) {
/* 158 */       retValue.append((String)iter.next() + ", ");
/*     */     }
/* 160 */     retValue.delete(retValue.length() - 2, retValue.length());
/* 161 */     return retValue.toString();
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     Part getCurrentPart();
/*     */     
/*     */     Part getPredecessor(Part param1Part);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\summary\nao\PartHistoryTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */