/*    */ package com.eoos.util.v2;
/*    */ 
/*    */ import com.eoos.condition.Condition;
/*    */ 
/*    */ public class SectionIndex {
/*    */   public final int start;
/*    */   public final int end;
/*    */   
/*    */   public SectionIndex(final int start, final int end) {
/* 10 */     AssertUtil.ensure(new Condition()
/*    */         {
/*    */           public boolean check(Object obj) {
/* 13 */             boolean retValue = true;
/* 14 */             retValue = (retValue && start >= 0);
/* 15 */             retValue = (retValue && end >= start);
/* 16 */             return retValue;
/*    */           }
/*    */         });
/*    */     
/* 20 */     this.start = start;
/* 21 */     this.end = end;
/*    */   }
/*    */   
/*    */   public int getSectionSize() {
/* 25 */     return this.end - this.start;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 29 */     return super.toString() + "[" + this.start + "..(" + this.end + ")]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\SectionIndex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */