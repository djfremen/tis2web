/*    */ package com.eoos.filter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FilterLinkage
/*    */   implements Filter
/*    */ {
/*    */   public static final int LINKAGE_OR = 1;
/*    */   public static final int LINKAGE_AND = 2;
/*    */   public static final int LINKAGE_XOR = 3;
/*    */   protected Filter filter1;
/*    */   protected Filter filter2;
/*    */   protected int nLinkage;
/*    */   
/*    */   public FilterLinkage(Filter filter1, Filter filter2, int nLinkage) {
/* 42 */     if (filter1 == null || filter2 == null) {
/* 43 */       throw new RuntimeException();
/*    */     }
/*    */     
/* 46 */     this.filter1 = filter1;
/* 47 */     this.filter2 = filter2;
/*    */     
/* 49 */     this.nLinkage = nLinkage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean include(Object obj) {
/* 60 */     boolean bResult = false;
/*    */     
/* 62 */     switch (this.nLinkage) {
/*    */       case 1:
/* 64 */         bResult = (this.filter1.include(obj) || this.filter2.include(obj));
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
/* 85 */         return bResult;case 2: bResult = (this.filter1.include(obj) && this.filter2.include(obj)); return bResult;case 3: bResult = ((this.filter1.include(obj) && !this.filter2.include(obj)) || (this.filter2.include(obj) && !this.filter1.include(obj))); return bResult;
/*    */     } 
/*    */     throw new RuntimeException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\filter\FilterLinkage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */