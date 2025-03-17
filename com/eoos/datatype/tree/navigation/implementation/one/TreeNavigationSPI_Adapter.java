/*    */ package com.eoos.datatype.tree.navigation.implementation.one;
/*    */ 
/*    */ import com.eoos.filter.Filter;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreeNavigationSPI_Adapter
/*    */   implements TreeNavigationSPI
/*    */ {
/*    */   protected TreeNavigationSPI spi;
/* 20 */   protected Filter filter = null;
/*    */   
/* 22 */   protected Comparator comparator = null;
/*    */ 
/*    */   
/*    */   public TreeNavigationSPI_Adapter(TreeNavigationSPI spi) {
/* 26 */     this.spi = spi;
/*    */   }
/*    */   
/*    */   public void setFilter(Filter filter) {
/* 30 */     this.filter = filter;
/*    */   }
/*    */   
/*    */   public void setComparator(Comparator comparator) {
/* 34 */     this.comparator = comparator;
/*    */   }
/*    */   
/*    */   public List getChildren(Object node) {
/* 38 */     List<?> retValue = this.spi.getChildren(node);
/* 39 */     if (this.filter != null) {
/* 40 */       CollectionUtil.filter(retValue, this.filter);
/*    */     }
/* 42 */     if (this.comparator != null) {
/* 43 */       Collections.sort(retValue, this.comparator);
/*    */     }
/* 45 */     return retValue;
/*    */   }
/*    */   
/*    */   public Object getParent(Object node) {
/* 49 */     return this.spi.getParent(node);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getSuperroot() {
/* 56 */     return this.spi.getSuperroot();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\navigation\implementation\one\TreeNavigationSPI_Adapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */