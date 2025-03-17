/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT2A;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT2DataHandler;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.vit2.VIT2ASelector;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.vit2.VIT2Util;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.ITestDriverSettings;
/*    */ import com.eoos.gm.tis2web.sps.common.VIT;
/*    */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VIT2DataHandlerImpl
/*    */   implements VIT2DataHandler
/*    */ {
/*    */   public List getExVIT2Attrs(VIT1 vit1, VIT2 vit2, ITestDriverSettings settings, List blobs) {
/* 27 */     List lstAttrs = new ArrayList();
/* 28 */     addVIT2BaseAttrs(vit2, settings, lstAttrs);
/* 29 */     VIT2A vit2A = (new VIT2ASelector(vit1, vit2, blobs, settings.getBlobID2Size())).getInstance();
/* 30 */     if (vit2A != null) {
/* 31 */       lstAttrs.addAll(vit2A.getAttributes());
/*    */     }
/*    */     
/* 34 */     return lstAttrs;
/*    */   }
/*    */   
/*    */   private void addVIT2BaseAttrs(VIT2 vit2, ITestDriverSettings settings, List lstAttrs) {
/* 38 */     VIT2Util.addAttr(lstAttrs, "vit_id", settings.getVITID());
/* 39 */     VIT2Util.addAttr(lstAttrs, "vit_type", "VIT2");
/* 40 */     VIT2Util.addAttr(lstAttrs, (VIT)vit2, "sps_id");
/* 41 */     VIT2Util.addAttr(lstAttrs, "chksum", "0000");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\VIT2DataHandlerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */