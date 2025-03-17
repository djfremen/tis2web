/*    */ package com.eoos.gm.tis2web.sps.client.ui.datamodel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.util.List;
/*    */ import java.util.Vector;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModuleInfoData
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(ECUData.class);
/*    */   
/* 23 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/* 25 */   protected Summary summary = null;
/*    */   
/*    */   public ModuleInfoData(Summary summary) {
/* 28 */     this.summary = summary;
/*    */   }
/*    */   
/*    */   public Vector getModuleInfoData(Summary summary) {
/*    */     try {
/* 33 */       List<Module> modules = summary.getModules();
/* 34 */       if (modules != null) {
/* 35 */         Vector<Vector<String>> result = new Vector();
/*    */         
/* 37 */         for (int i = 0; i < modules.size(); i++) {
/* 38 */           Module module = modules.get(i);
/* 39 */           Vector<String> row = new Vector();
/* 40 */           if (module.getDenotation(null) != null) {
/* 41 */             row.add(String.valueOf(i));
/* 42 */             row.add(module.toString());
/* 43 */             row.add(module.getDenotation(null));
/* 44 */             result.add(row);
/*    */           } 
/*    */         } 
/* 47 */         return result;
/*    */       }
/*    */     
/* 50 */     } catch (Exception e) {
/* 51 */       log.error("getModuleInfoData() method, -exception: " + e.getMessage());
/*    */     } 
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\datamodel\ModuleInfoData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */