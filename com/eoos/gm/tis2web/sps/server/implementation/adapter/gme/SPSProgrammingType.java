/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingType;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class SPSProgrammingType extends SPSProgrammingType {
/*    */   private static final long serialVersionUID = 1L;
/* 13 */   public static SPSProgrammingType GME_NORMAL = new SPSProgrammingType(NORMAL);
/*    */   
/* 15 */   public static SPSProgrammingType GME_VCI = new SPSProgrammingType(VCI);
/*    */   
/*    */   protected SPSProgrammingType(Integer id) {
/* 18 */     this.id = id;
/*    */   }
/*    */   
/*    */   protected SPSProgrammingType(SPSLanguage language, SPSProgrammingType template) {
/* 22 */     this.id = template.getID();
/* 23 */     if (this.id.equals(NORMAL)) {
/* 24 */       this.description = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.programming-type.normal");
/* 25 */     } else if (this.id.equals(VCI)) {
/* 26 */       this.description = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.programming-type.vci");
/* 27 */     } else if (this.id.equals(RECONFIGURE)) {
/* 28 */       this.description = ApplicationContext.getInstance().getLabel(language.getJavaLocale(), "sps.programming-type.reconfigure");
/*    */     } 
/*    */   }
/*    */   
/*    */   static List getProgrammingTypes(SPSSession session, SPSSchemaAdapterGME adapter) {
/* 33 */     List<SPSProgrammingType> result = new ArrayList();
/* 34 */     result.add(new SPSProgrammingType((SPSLanguage)session.getLanguage(), GME_NORMAL));
/* 35 */     if (SPSModel.getInstance(adapter).checkSetting(((SPSVehicle)session.getVehicle()).getSalesMakeID(), "UseVCI", "enabled")) {
/* 36 */       result.add(new SPSProgrammingType((SPSLanguage)session.getLanguage(), GME_VCI));
/*    */     }
/* 38 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSProgrammingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */