/*    */ package com.eoos.gm.tis2web.frame.ws.lt.server.wrapper;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.LaborOp;
/*    */ import com.eoos.gm.tis2web.lt.icop.server.ICOPServerSupport;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class LaborOpWrapper
/*    */ {
/*    */   public List<LaborOp> getTalItems(ICOPServerSupport.TAL tal) {
/* 13 */     List<LaborOp> result = new ArrayList<LaborOp>();
/* 14 */     Iterator<String[]> it = (Iterator)tal.getPositions().iterator();
/* 15 */     while (it.hasNext()) {
/* 16 */       String[] item = it.next();
/* 17 */       LaborOp laborOp = new LaborOp();
/* 18 */       laborOp.setOpNumber(item[0]);
/* 19 */       laborOp.setDescription(item[1]);
/* 20 */       laborOp.setCode(item[2]);
/* 21 */       laborOp.setTa(item[3]);
/* 22 */       result.add(laborOp);
/*    */     } 
/* 24 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\wrapper\LaborOpWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */