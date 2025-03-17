/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public abstract class CTOCStore
/*    */ {
/* 12 */   protected static Logger log = Logger.getLogger(CTOCStore.class);
/*    */   
/*    */   public static final int ROOT_INDICATOR = -1;
/*    */   
/*    */   protected Map factories;
/*    */   
/*    */   protected HashMap ctocs;
/*    */   protected ILVCAdapter.Retrieval lvcRetrieval;
/*    */   
/*    */   public HashMap getCTOCs() {
/* 22 */     return this.ctocs;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CTOCStore() {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected CTOCStore(Map factories, ILVCAdapter.Retrieval lvcRetrieval) {
/* 32 */     this.lvcRetrieval = lvcRetrieval;
/* 33 */     this.factories = factories;
/* 34 */     this.ctocs = new HashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   protected VCR makeVCR(int vcrID) {
/* 38 */     return this.lvcRetrieval.getLVCAdapter().makeVCR(vcrID);
/*    */   }
/*    */   
/*    */   public CTOCNode searchByProperty(CTOCNode node, CTOCProperty property, String value, VCR vcr) {
/* 42 */     throw new IllegalArgumentException("not supported");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */