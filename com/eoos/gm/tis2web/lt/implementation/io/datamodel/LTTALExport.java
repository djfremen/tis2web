/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class LTTALExport
/*     */ {
/*     */   ClientContext context;
/*  22 */   static String VCSep = new String(":\t");
/*     */ 
/*     */   
/*     */   public LTTALExport(ClientContext context) {
/*  26 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   public String ExportTAL() {
/*  31 */     LTClientContext lt = LTClientContext.getInstance(this.context);
/*     */     
/*  33 */     List lTAL = lt.getSelection().getTALList();
/*  34 */     LTVCContext vc = lt.getVCContext();
/*     */     
/*  36 */     StringBuffer strBuf = new StringBuffer(getHeader(lt, vc));
/*     */     
/*  38 */     for (Iterator<LTDataWork> itm = lTAL.iterator(); itm.hasNext(); ) {
/*  39 */       LTDataWork work = itm.next();
/*  40 */       strBuf.append(work.getNr());
/*  41 */       strBuf.append('\t');
/*  42 */       strBuf.append(work.getDescription());
/*  43 */       strBuf.append('\t');
/*  44 */       if (work.getSXAWList() != null && work.getSXAWList().size() > 0) {
/*  45 */         strBuf.append(((LTSXAWData)work.getSXAWList().get(0)).getAwFormatted());
/*     */       }
/*  47 */       strBuf.append('\t');
/*  48 */       if (work.getTcList() != null && work.getTcList().size() > 0) {
/*  49 */         strBuf.append(((LTTroublecode)work.getTcList().get(0)).getTroubleCode());
/*     */       }
/*  51 */       strBuf.append("\r\n");
/*     */     } 
/*  53 */     strBuf.append(getTrailer(lt));
/*  54 */     return strBuf.toString();
/*     */   }
/*     */   
/*     */   public String getHeader() {
/*  58 */     LTClientContext lt = LTClientContext.getInstance(this.context);
/*  59 */     LTVCContext vc = lt.getVCContext();
/*  60 */     return getHeader(lt, vc);
/*     */   }
/*     */   
/*     */   public String getTrailer() {
/*  64 */     LTClientContext lt = LTClientContext.getInstance(this.context);
/*  65 */     return getTrailer(lt);
/*     */   }
/*     */   
/*     */   private String getHeader(LTClientContext lt, LTVCContext vc) {
/*  69 */     StringBuffer strBuf = new StringBuffer();
/*     */     
/*  71 */     if (vc.getSalesMake().length() > 0) {
/*  72 */       strBuf.append(vc.getSalesMake());
/*  73 */       strBuf.append("\r\n");
/*     */     } 
/*     */     
/*  76 */     if (vc.getVIN() != null && vc.getVIN().length() > 0) {
/*  77 */       strBuf.append(this.context.getLabel("lt.vc.attributename.vin"));
/*  78 */       strBuf.append(VCSep);
/*  79 */       strBuf.append(vc.getVIN());
/*  80 */       strBuf.append("\r\n");
/*     */     } 
/*     */     
/*  83 */     if (vc.getModel().length() > 0) {
/*  84 */       strBuf.append(this.context.getLabel("lt.vc.attributename.model"));
/*  85 */       strBuf.append(VCSep);
/*  86 */       strBuf.append(vc.getModel());
/*  87 */       strBuf.append("\r\n");
/*     */     } 
/*     */     
/*  90 */     if (vc.getYear().length() > 0) {
/*  91 */       strBuf.append(this.context.getLabel("lt.vc.attributename.modelyear"));
/*  92 */       strBuf.append(VCSep);
/*  93 */       strBuf.append(vc.getYear());
/*  94 */       strBuf.append("\r\n");
/*     */     } 
/*     */     
/*  97 */     if (vc.getEngine().length() > 0) {
/*  98 */       strBuf.append(this.context.getLabel("lt.vc.attributename.engine"));
/*  99 */       strBuf.append(VCSep);
/* 100 */       strBuf.append(vc.getEngine());
/* 101 */       strBuf.append("\r\n");
/*     */     } 
/*     */     
/* 104 */     if (vc.getTransmission().length() > 0) {
/* 105 */       strBuf.append(this.context.getLabel("lt.vc.attributename.transmission"));
/* 106 */       strBuf.append(VCSep);
/* 107 */       strBuf.append(vc.getTransmission());
/* 108 */       strBuf.append("\r\n");
/*     */     } 
/*     */     
/* 111 */     strBuf.append("\r\n");
/* 112 */     strBuf.append(this.context.getLabel("lt.talexport.selected"));
/* 113 */     strBuf.append(":\r\n");
/* 114 */     return strBuf.toString();
/*     */   }
/*     */   
/*     */   private String getTrailer(LTClientContext lt) {
/* 118 */     StringBuffer strBuf = new StringBuffer();
/* 119 */     strBuf.append("\r\n");
/* 120 */     strBuf.append(this.context.getLabel("lt.sum"));
/* 121 */     strBuf.append("\t\t");
/* 122 */     String oSum = lt.getAWSum(lt.getSelection().getTALList());
/* 123 */     strBuf.append(oSum);
/* 124 */     strBuf.append(" ");
/* 125 */     strBuf.append(lt.getAWUnit());
/* 126 */     strBuf.append("\r\n");
/* 127 */     return strBuf.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTTALExport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */