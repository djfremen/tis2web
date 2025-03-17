/*    */ package com.eoos.gm.tis2web.news.implementation.datamodel.domain.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Locale;
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
/*    */ public class Node
/*    */ {
/* 20 */   public Node parent = null;
/*    */   
/* 22 */   public SITOCElement content = null;
/*    */   
/*    */   public String toString() {
/*    */     try {
/* 26 */       return this.content.getLabel(LocaleInfoProvider.getInstance().getLocale(Locale.ENGLISH));
/* 27 */     } catch (Exception e) {
/* 28 */       return super.toString();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 34 */     if (this == obj)
/* 35 */       return true; 
/* 36 */     if (obj instanceof Node) {
/* 37 */       Node other = (Node)obj;
/* 38 */       boolean ret = Util.equals(this.parent, other.parent);
/* 39 */       ret = (ret && Util.equals(this.content, other.content));
/* 40 */       return ret;
/*    */     } 
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 47 */     int retValue = 0;
/*    */     try {
/* 49 */       retValue += this.parent.hashCode();
/* 50 */     } catch (Exception e) {}
/*    */     
/*    */     try {
/* 53 */       retValue += this.content.hashCode();
/* 54 */     } catch (Exception e) {}
/*    */     
/* 56 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\datamodel\domain\toc\Node.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */