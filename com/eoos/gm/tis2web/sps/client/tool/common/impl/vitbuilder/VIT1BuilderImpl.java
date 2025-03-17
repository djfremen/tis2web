/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.impl.vitbuilder;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VITBuilder;
/*    */ import com.eoos.gm.tis2web.sps.common.VIT;
/*    */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*    */ import com.eoos.gm.tis2web.sps.common.impl.VIT1Impl;
/*    */ import com.eoos.gm.tis2web.sps.common.impl.VITImpl;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VIT1BuilderImpl
/*    */   implements VITBuilder
/*    */ {
/* 17 */   private List lstAttrs = null;
/* 18 */   private VIT1 vit1 = null;
/*    */   
/*    */   public VIT1BuilderImpl(Object lstAttrs) {
/* 21 */     this.lstAttrs = (List)lstAttrs;
/* 22 */     this.vit1 = (VIT1)new VIT1Impl();
/*    */   }
/*    */   public VIT build() {
/*    */     VITImpl vITImpl;
/* 26 */     VIT1 vIT1 = this.vit1;
/*    */     
/* 28 */     for (int i = 0; i < this.lstAttrs.size(); i++) {
/* 29 */       String attrName = (String)((Pair)this.lstAttrs.get(i)).getFirst();
/* 30 */       String attrVal = (String)((Pair)this.lstAttrs.get(i)).getSecond();
/*    */ 
/*    */       
/* 33 */       if (isNewCMB(attrName, (VIT)vIT1)) {
/* 34 */         if (!(vIT1 instanceof VIT1)) {
/* 35 */           this.vit1.addControlModuleBlock((VIT)vIT1);
/*    */         }
/* 37 */         vITImpl = new VITImpl();
/*    */       } 
/* 39 */       if (attrName.equals("option")) {
/* 40 */         Pair freeOptAttr = getFreeOptionAttr(attrVal);
/* 41 */         if (freeOptAttr != null) {
/* 42 */           this.vit1.getFreeOptions().addAttribute(freeOptAttr);
/*    */         
/*    */         }
/*    */       }
/* 46 */       else if ("sps_id".equals(attrName) || "chksum".equals(attrName) || "table_len".equals(attrName) || "nav_info".equals(attrName) || "reserved".equals(attrName) || "numcms".equals(attrName) || "devicetype".equals(attrName) || "spsmode".equals(attrName)) {
/* 47 */         this.vit1.addAttribute(this.lstAttrs.get(i));
/*    */       } else {
/*    */         
/* 50 */         vITImpl.addAttribute(this.lstAttrs.get(i));
/*    */       } 
/* 52 */     }  this.vit1.addControlModuleBlock((VIT)vITImpl);
/*    */     
/* 54 */     return (VIT)this.vit1;
/*    */   }
/*    */   
/*    */   private boolean isNewCMB(String attrName, VIT vit) {
/* 58 */     if ((attrName.equals("ssecuhn") || attrName.equals("blocklen") || attrName.equals("disp_type") || attrName.equals("protocol") || attrName.equals("ecu_adr")) && (vit.getAttribute(attrName) != null || vit instanceof VIT1)) {
/* 59 */       return true;
/*    */     }
/* 61 */     return false;
/*    */   }
/*    */   private Pair getFreeOptionAttr(String optAttr) {
/*    */     PairImpl pairImpl;
/* 65 */     Pair attr = null;
/* 66 */     List attrVals = this.vit1.getValues4Value(optAttr, ";");
/* 67 */     if (attrVals.size() > 1) {
/* 68 */       pairImpl = new PairImpl(attrVals.get(0), attrVals.get(1));
/*    */     }
/* 70 */     return (Pair)pairImpl;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\vitbuilder\VIT1BuilderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */