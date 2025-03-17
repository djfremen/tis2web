/*     */ package com.eoos.gm.tis2web.frame.ws.lt.server;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.InvalidLaborOp;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.LaborOp;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.LaborOpFault;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTAWKSchluessel;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSXAWData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AWAdapter
/*     */ {
/*  19 */   private static final Logger log = Logger.getLogger(AWAdapter.class);
/*     */   
/*     */   public void validateOpNumber(String opNum, LTClientContext ltContext) throws LaborOpFault {
/*  22 */     String opNumber = opNum;
/*  23 */     boolean majorOpAssumed = true;
/*  24 */     if (!assumeMainWork(opNum)) {
/*  25 */       opNumber = getMainWorkNumber(opNum);
/*  26 */       majorOpAssumed = false;
/*     */     } 
/*  28 */     if (!ltContext.isMainWorkValid(opNumber)) {
/*  29 */       InvalidLaborOp invOp = new InvalidLaborOp();
/*  30 */       invOp.setLaborOp(opNum);
/*  31 */       throw new LaborOpFault("Invalid labor operation number", invOp);
/*     */     } 
/*  33 */     boolean supIsValid = false;
/*  34 */     if (!majorOpAssumed) {
/*  35 */       List<LTDataWork> supWorks = ltContext.getAddOnWorks(opNumber);
/*  36 */       Iterator<LTDataWork> itSup = supWorks.iterator();
/*  37 */       while (itSup.hasNext()) {
/*  38 */         LTDataWork supWork = itSup.next();
/*  39 */         if (supWork.getNr() != null && supWork.getNr().compareTo(opNum) == 0) {
/*  40 */           supIsValid = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  45 */     if (!majorOpAssumed && !supIsValid) {
/*  46 */       InvalidLaborOp invOp = new InvalidLaborOp();
/*  47 */       invOp.setLaborOp(opNum);
/*  48 */       throw new LaborOpFault("Invalid labor operation number", invOp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<LaborOp> getFormattedAWList(String opNum, LTClientContext ltContext) throws LaborOpFault {
/*  53 */     List<LaborOp> result = new ArrayList<LaborOp>();
/*  54 */     LTDataWork ltWork = null;
/*  55 */     String opNumber = opNum;
/*  56 */     boolean majorOpAssumed = true;
/*  57 */     if (!assumeMainWork(opNum)) {
/*  58 */       opNumber = getMainWorkNumber(opNum);
/*  59 */       majorOpAssumed = false;
/*     */     } 
/*  61 */     if (ltContext.isMainWorkValid(opNumber)) {
/*  62 */       ltWork = ltContext.getMainWork(opNumber, true);
/*  63 */       if (ltWork.isMainWork() && majorOpAssumed) {
/*  64 */         result.addAll(getFormattedMainWorkList(opNumber, ltContext));
/*  65 */         result.addAll(getFormattedSupWorkList(opNumber, ltContext, null));
/*     */       } else {
/*  67 */         result.addAll(getFormattedSupWorkList(opNumber, ltContext, opNum));
/*  68 */         if (result.isEmpty()) {
/*  69 */           InvalidLaborOp invOp = new InvalidLaborOp();
/*  70 */           invOp.setLaborOp(opNum);
/*  71 */           throw new LaborOpFault("Invalid labor operation number", invOp);
/*     */         } 
/*     */       } 
/*     */     } else {
/*  75 */       InvalidLaborOp invOp = new InvalidLaborOp();
/*  76 */       invOp.setLaborOp(opNum);
/*  77 */       throw new LaborOpFault("Invalid labor operation number", invOp);
/*     */     } 
/*  79 */     return result;
/*     */   }
/*     */   
/*     */   private List<LaborOp> getFormattedMainWorkList(String opNumber, LTClientContext ltContext) {
/*  83 */     List<LaborOp> result = new ArrayList<LaborOp>();
/*  84 */     LTDataWork ltWork = null;
/*  85 */     ltWork = ltContext.getMainWork(opNumber, true);
/*     */     
/*  87 */     if (ltWork.getTasktype() == 1) {
/*  88 */       List<LTDataWork> ltDataWorks = ltContext.getMainWorks(ltWork.getNr());
/*  89 */       if (ltDataWorks != null) {
/*     */         
/*  91 */         Iterator<LTDataWork> pwIterator = ltDataWorks.iterator();
/*  92 */         while (pwIterator.hasNext()) {
/*  93 */           LTDataWork ltWorkPseudo = pwIterator.next();
/*  94 */           ltWorkPseudo.getSXAWList();
/*  95 */           Iterator<LTSXAWData> sxawIterator = ltWorkPseudo.getSXAWList().iterator();
/*  96 */           while (sxawIterator.hasNext()) {
/*  97 */             LaborOp op = new LaborOp();
/*  98 */             LTSXAWData ltsxawData = sxawIterator.next();
/*  99 */             op.setOpNumber(ltWorkPseudo.getNr());
/* 100 */             op.setDescription(ltWorkPseudo.getDescription() + ((ltWork.getLaquerDegree() != null) ? (" - " + ltWork.getLaquerDegree()) : ""));
/* 101 */             LTAWKSchluessel ltawkSchluessel = ltsxawData.getAWKSchluessel();
/* 102 */             op.setCode((ltawkSchluessel != null) ? ltawkSchluessel.getAwtext() : "");
/*     */             
/* 104 */             op.setTa(ltsxawData.getAwFormatted());
/* 105 */             result.add(op);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     ltWork.getSXAWList();
/* 112 */     Iterator<LTSXAWData> it = ltWork.getSXAWList().iterator();
/* 113 */     while (it.hasNext()) {
/* 114 */       LaborOp op = new LaborOp();
/* 115 */       LTSXAWData ltsxawData = it.next();
/* 116 */       op.setOpNumber(ltWork.getMainWorkNr());
/* 117 */       op.setDescription(ltWork.getDescription());
/* 118 */       LTAWKSchluessel ltawkSchluessel = ltsxawData.getAWKSchluessel();
/* 119 */       op.setCode((ltawkSchluessel != null) ? ltawkSchluessel.getAwtext() : "");
/*     */       
/* 121 */       op.setTa(ltsxawData.getAwFormatted());
/* 122 */       result.add(op);
/*     */     } 
/*     */     
/* 125 */     return result;
/*     */   }
/*     */   
/*     */   private List<LaborOp> getFormattedSupWorkList(String opNumber, LTClientContext ltContext, String sWorkNumber) {
/* 129 */     List<LaborOp> result = new ArrayList<LaborOp>();
/* 130 */     List<LTDataWork> supWorks = ltContext.getAddOnWorks(opNumber);
/* 131 */     ltContext.formatAWs(supWorks);
/* 132 */     Iterator<LTDataWork> itSup = supWorks.iterator();
/* 133 */     while (itSup.hasNext()) {
/*     */       
/* 135 */       LTDataWork supWork = itSup.next();
/* 136 */       Iterator<LTSXAWData> itSxaw = supWork.getSXAWList().iterator();
/* 137 */       String awFormatted = null;
/* 138 */       LTSXAWData ltsxawData = null;
/* 139 */       while (itSxaw.hasNext()) {
/* 140 */         ltsxawData = itSxaw.next();
/* 141 */         awFormatted = ltsxawData.getAwFormatted();
/* 142 */         if (sWorkNumber == null || sWorkNumber.compareTo(supWork.getNr()) == 0) {
/* 143 */           LaborOp op = new LaborOp();
/* 144 */           op.setOpNumber(supWork.getNr());
/* 145 */           op.setDescription(supWork.getDescription());
/* 146 */           LTAWKSchluessel ltawkSchluessel = ltsxawData.getAWKSchluessel();
/* 147 */           op.setCode((ltawkSchluessel != null) ? ltawkSchluessel.getAwtext() : "");
/* 148 */           if (supWork.getTasktype() == 6) {
/* 149 */             op.setTa("[" + awFormatted + "]");
/*     */           } else {
/* 151 */             op.setTa(awFormatted);
/*     */           } 
/* 153 */           op.setIsMajor(Boolean.valueOf(false));
/* 154 */           result.add(op);
/*     */         } 
/*     */       } 
/*     */     } 
/* 158 */     return result;
/*     */   }
/*     */   
/*     */   private boolean assumeMainWork(String opNumber) {
/* 162 */     boolean result = false;
/* 163 */     if (opNumber != null && opNumber.length() <= 7) {
/* 164 */       result = true;
/*     */     }
/* 166 */     return result;
/*     */   }
/*     */   
/*     */   private String getMainWorkNumber(String supWork) throws LaborOpFault {
/*     */     String result;
/*     */     try {
/* 172 */       result = supWork.substring(0, 7);
/* 173 */     } catch (Exception e) {
/* 174 */       log.debug("Error when getting main work number from supplementary work number: " + supWork);
/* 175 */       InvalidLaborOp invOp = new InvalidLaborOp();
/* 176 */       invOp.setLaborOp(supWork);
/* 177 */       throw new LaborOpFault("Invalid labor operation number", invOp);
/*     */     } 
/* 179 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\AWAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */