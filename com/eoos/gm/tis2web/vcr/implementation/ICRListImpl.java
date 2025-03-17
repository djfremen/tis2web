/*    */ package com.eoos.gm.tis2web.vcr.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.ICRList;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ICRListImpl
/*    */   extends ArrayList
/*    */   implements ICRList
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   boolean isSubset(ICRListImpl other) {
/* 18 */     int noExpressions = size();
/*    */ 
/*    */     
/* 21 */     for (int i = 0; i < noExpressions; i++) {
/* 22 */       if (!other.subsetExpression((VCRExpressionImpl)get(i))) {
/* 23 */         return false;
/*    */       }
/*    */     } 
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean subsetExpression(VCRExpressionImpl e) {
/* 32 */     int noExpressions = size();
/* 33 */     for (int i = 0; i < noExpressions; i++) {
/* 34 */       if (e.isSubset((VCRExpressionImpl)get(i))) {
/* 35 */         return true;
/*    */       }
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ICRListImpl intersectExpression(VCRExpressionImpl e) {
/* 44 */     VCRExpression intersection = null;
/* 45 */     ICRListImpl resultVCR = null;
/* 46 */     int noExpressions = size();
/* 47 */     for (int i = 0; i < noExpressions; i++) {
/* 48 */       intersection = e.intersect((VCRExpressionImpl)get(i));
/* 49 */       if (intersection != null) {
/* 50 */         if (resultVCR == null) {
/* 51 */           resultVCR = new ICRListImpl();
/*    */         }
/* 53 */         resultVCR.add((E)intersection);
/* 54 */         intersection = null;
/*    */       } 
/*    */     } 
/* 57 */     if (resultVCR != null) {
/* 58 */       ICROP.simplify(resultVCR);
/*    */     }
/* 60 */     return resultVCR;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 64 */     StringBuffer sb = new StringBuffer();
/* 65 */     int noExpressions = size();
/* 66 */     for (int i = 0; i < noExpressions; i++) {
/* 67 */       if (i > 0) {
/* 68 */         sb.append(" or ");
/*    */       }
/* 70 */       if (noExpressions > 1) {
/* 71 */         sb.append("[");
/*    */       }
/* 73 */       VCRExpression e = (VCRExpression)get(i);
/* 74 */       List<E> t = e.getTerms();
/* 75 */       int noblocks = t.size();
/* 76 */       for (int j = 0; j < noblocks; j++) {
/* 77 */         if (j > 0) {
/* 78 */           sb.append(" and ");
/*    */         }
/* 80 */         sb.append(t.get(j).toString());
/*    */       } 
/* 82 */       if (noExpressions > 1) {
/* 83 */         sb.append("]");
/*    */       }
/*    */     } 
/* 86 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public ICRList intersectExpression(VCRExpression e) {
/* 90 */     return intersectExpression((VCRExpressionImpl)e);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\ICRListImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */