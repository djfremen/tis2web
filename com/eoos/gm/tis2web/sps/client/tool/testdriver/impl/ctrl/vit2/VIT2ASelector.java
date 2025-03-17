/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.vit2;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT2A;
/*    */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VIT2ASelector
/*    */ {
/* 18 */   private VIT1 vit1 = null;
/* 19 */   private VIT2 vit2 = null;
/* 20 */   private List blobs = null;
/* 21 */   private Map blob2size = null;
/*    */ 
/*    */   
/*    */   public VIT2ASelector(VIT1 vit1, VIT2 vit2, List blobs, Map blob2size) {
/* 25 */     this.vit1 = vit1;
/* 26 */     this.vit2 = vit2;
/* 27 */     this.blobs = blobs;
/* 28 */     this.blob2size = blob2size;
/*    */   }
/*    */   
/*    */   public VIT2A getInstance() {
/* 32 */     String spsID = this.vit2.getAttrValue("sps_id");
/*    */     
/* 34 */     if (spsID.compareToIgnoreCase("A5") == 0) {
/* 35 */       return new VIT2A5Impl(this.vit1, this.vit2, this.blobs, this.blob2size);
/*    */     }
/*    */     
/* 38 */     return new VIT2A7Impl(this.vit1, this.vit2, this.blobs, this.blob2size);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\vit2\VIT2ASelector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */