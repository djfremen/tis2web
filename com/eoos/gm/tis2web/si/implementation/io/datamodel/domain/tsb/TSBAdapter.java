/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TSBAdapter
/*    */ {
/*    */   public static List getTSBDomain(String ag, String symptom, String dtc, ClientContext context) {
/*    */     try {
/* 24 */       List retValue = new ArrayList();
/* 25 */       retValue.addAll(SIDataAdapterFacade.getInstance(context).getSI().provideTSBs());
/* 26 */       return retValue;
/* 27 */     } catch (NullPointerException e) {
/* 28 */       return new LinkedList();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getSymptom(SIOTSB tsb) {
/*    */     try {
/* 34 */       StringBuffer tmp = new StringBuffer("{SYMPTOM}");
/* 35 */       List<String> complaints = (List)tsb.getProperty((SITOCProperty)SIOProperty.COMPLAINT);
/* 36 */       for (int i = 0; i < complaints.size(); i++) {
/* 37 */         StringUtilities.replace(tmp, "{SYMPTOM}", (String)complaints.get(i) + ", {SYMPTOM}");
/*    */       }
/* 39 */       StringUtilities.replace(tmp, "{SYMPTOM}", "");
/* 40 */       tmp.delete(tmp.length() - 2, tmp.length());
/* 41 */       return tmp.toString();
/* 42 */     } catch (NullPointerException e) {
/* 43 */       return "-";
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String getTroubleCode(SIOTSB tsb) {
/*    */     try {
/* 49 */       return (String)tsb.getProperty((SITOCProperty)SIOProperty.DTC);
/* 50 */     } catch (Exception e) {
/* 51 */       return "-";
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean matchAssemblyGroup(SIOTSB tsb, AssemblyGroup ag) {
/* 56 */     return tsb.matchAssemblyGroup((String)ag.getNode().getProperty((SITOCProperty)CTOCProperty.AssemblyGroup));
/*    */   }
/*    */   
/*    */   public static boolean matchSymptom(SIOTSB tsb, Symptom s) {
/* 60 */     return tsb.matchSymptom((String)s.getNode().getProperty((SITOCProperty)CTOCProperty.SITQ));
/*    */   }
/*    */   
/*    */   public static boolean matchTroubleCode(SIOTSB tsb, TroubleCode tc) {
/* 64 */     return tsb.matchDTC(tc.getIdentifier());
/*    */   }
/*    */   
/*    */   public static void main(String[] args) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\domain\tsb\TSBAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */