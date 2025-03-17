/*     */ package com.eoos.gm.tis2web.sps.client.tool.spstool.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.spstool.ITraceInfo;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TraceInfoData
/*     */ {
/*     */   protected String msgErr;
/*     */   protected List data;
/*  18 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  20 */   protected static String TDTag_Begin = "<td WIDTH=\"33%\" align=\"center\">";
/*     */   
/*  22 */   protected static String TDTag_End = "</td>";
/*     */   
/*  24 */   protected static String THTag_Begin = "<th WIDTH=\"33%\" align=\"center\">";
/*     */   
/*  26 */   protected static String THTag_End = "</th>";
/*     */   
/*  28 */   protected static String TABLETag_Begin = "<table WIDTH=\"100%\" border=\"1\">";
/*     */   
/*  30 */   protected static String TABLETag_End = "</table>";
/*     */   
/*  32 */   protected static String HTMLTag_Begin = "<html>";
/*     */   
/*  34 */   protected static String HTMLTag_End = "</html>";
/*     */   
/*  36 */   protected static String BODYTag_Begin = "<body>";
/*     */   
/*  38 */   protected static String BODYTag_End = "</body>";
/*     */   
/*  40 */   protected static String TRTag_Begin = "<tr>";
/*     */   
/*  42 */   protected static String TRTag_End = "</tr>";
/*     */   
/*  44 */   protected static String QUOT = "&quot; &quot;";
/*     */   
/*  46 */   protected String line_Header = resourceProvider.getLabel(null, "trace-info.line");
/*     */   
/*  48 */   protected String operation_Header = resourceProvider.getLabel(null, "trace-info.opCode");
/*     */   
/*  50 */   protected String errorCode_Header = resourceProvider.getLabel(null, "trace-info.errCode");
/*     */   
/*     */   public TraceInfoData(String msgErr, List data) {
/*  53 */     this.msgErr = msgErr;
/*  54 */     this.data = data;
/*     */   }
/*     */   
/*     */   public String getHTML() {
/*  58 */     StringBuffer html = new StringBuffer();
/*  59 */     if (this.data == null) {
/*  60 */       html.append(HTMLTag_Begin + BODYTag_Begin);
/*  61 */       if (this.msgErr != null)
/*  62 */         html.append(getErrMsgPTag(this.msgErr)); 
/*  63 */       html.append(TABLETag_Begin);
/*  64 */       html.append(getHeader());
/*  65 */       html.append(TRTag_Begin);
/*  66 */       html.append(TDTag_Begin + QUOT + TDTag_End);
/*  67 */       html.append(TDTag_Begin + QUOT + TDTag_End);
/*  68 */       html.append(TDTag_Begin + QUOT + TDTag_End);
/*  69 */       html.append(TRTag_End);
/*  70 */       html.append(TABLETag_End + BODYTag_End + HTMLTag_End);
/*     */       
/*  72 */       return html.toString();
/*     */     } 
/*     */     
/*  75 */     Iterator iterator = this.data.iterator();
/*  76 */     boolean first = true;
/*  77 */     while (iterator.hasNext()) {
/*  78 */       if (first) {
/*  79 */         first = false;
/*  80 */         html.append(HTMLTag_Begin + BODYTag_Begin);
/*  81 */         if (this.msgErr != null)
/*  82 */           html.append(getErrMsgPTag(this.msgErr)); 
/*  83 */         html.append(TABLETag_Begin);
/*  84 */         html.append(getHeader());
/*     */       } 
/*     */       
/*  87 */       Object row = iterator.next();
/*  88 */       if (row instanceof ITraceInfo) {
/*  89 */         Integer line = ((ITraceInfo)row).getStep();
/*  90 */         String lineValue = QUOT;
/*  91 */         if (line != null)
/*  92 */           lineValue = Integer.toHexString(line.intValue()); 
/*  93 */         Integer operation = ((ITraceInfo)row).getOperationCode();
/*  94 */         String operationValue = QUOT;
/*  95 */         if (operation != null)
/*  96 */           operationValue = Integer.toHexString(operation.intValue()); 
/*  97 */         Integer errorCode = ((ITraceInfo)row).getResponseCode();
/*  98 */         String errorCodeValue = QUOT;
/*  99 */         if (errorCode != null)
/* 100 */           errorCodeValue = Integer.toHexString(errorCode.intValue()); 
/* 101 */         html.append(TRTag_Begin);
/* 102 */         html.append(TDTag_Begin + lineValue.toUpperCase(Locale.ENGLISH) + TDTag_End);
/* 103 */         html.append(TDTag_Begin + operationValue.toUpperCase(Locale.ENGLISH) + TDTag_End);
/* 104 */         html.append(TDTag_Begin + errorCodeValue.toUpperCase(Locale.ENGLISH) + TDTag_End);
/* 105 */         html.append(TRTag_End);
/*     */       } 
/*     */     } 
/* 108 */     if (html.length() > 0) {
/* 109 */       html.append(TABLETag_End + BODYTag_End + HTMLTag_End);
/*     */     }
/*     */     
/* 112 */     return html.toString();
/*     */   }
/*     */   
/*     */   protected String getHeader() {
/* 116 */     StringBuffer html = new StringBuffer();
/* 117 */     html.append(TRTag_Begin);
/* 118 */     html.append(THTag_Begin + this.line_Header + THTag_End);
/* 119 */     html.append(THTag_Begin + this.operation_Header + THTag_End);
/* 120 */     html.append(THTag_Begin + this.errorCode_Header + THTag_End);
/* 121 */     html.append(TRTag_End);
/* 122 */     return html.toString();
/*     */   }
/*     */   
/*     */   protected String getErrMsgPTag(String msgErr) {
/* 126 */     StringBuffer html = new StringBuffer();
/* 127 */     if (msgErr != null) {
/* 128 */       msgErr = StringUtilities.replace(msgErr, "\r\n", "<br/>");
/* 129 */       msgErr = StringUtilities.replace(msgErr, "\n", "<br/>");
/* 130 */       html.append("<p>" + msgErr + "</p>");
/* 131 */       html.append("<br/>");
/* 132 */       return html.toString();
/*     */     } 
/* 134 */     return null;
/*     */   }
/*     */   
/*     */   public String getHTMLString() {
/* 138 */     StringBuffer html = new StringBuffer();
/*     */ 
/*     */     
/* 141 */     html.append("<html><body><p>E4491: Fehler beim Reprogrammieren ! Alle Verbindungen ï¿½berprï¿½fen und Programmiereinheit zurï¿½cksetzen.E4423: Unbekannter Fehler wï¿½hrend der Reprogrammierung 5 bei Schritt 0</p></br>");
/* 142 */     html.append("<table WIDTH=100% border=1><tr><th WIDTH=33% align=center>Line</th><th WIDTH=33% align=center>Operation</th><th WIDTH=33% align=center>ErrorCode</th></tr><tr><td WIDTH=33% align=center>2</td><td WIDTH=33% align=center>28</td><td WIDTH=33% align=center>FD</td></tr><tr><td WIDTH=33% align=center>2</td><td WIDTH=33% align=center>28</td><td WIDTH=33% align=center>FD</td></tr></table></body></html>");
/* 143 */     return html.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\spstool\impl\TraceInfoData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */