/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT2DataHandler;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.ITestDriverSettings;
/*    */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TxtVIT2DataHandlerImpl
/*    */   implements VIT2DataHandler
/*    */ {
/*    */   public List getExVIT2Attrs(VIT1 vit1, VIT2 vit2, ITestDriverSettings settings, List blobs) {
/* 17 */     List<String> lines = new ArrayList();
/* 18 */     for (int i = 0; i < vit2.getAttributes().size(); i++) {
/* 19 */       Pair attr = vit2.getAttributes().get(i);
/* 20 */       String line = attr.getFirst() + " = " + attr.getSecond();
/* 21 */       lines.add(line);
/*    */     } 
/* 23 */     return lines;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\TxtVIT2DataHandlerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */