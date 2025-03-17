/*    */ package com.eoos.gm.tis2web.help.implementation.datamodel.domain.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
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
/* 21 */   public Node parent = null;
/*    */   
/* 23 */   public SITOCElement content = null;
/*    */   
/*    */   public String toString() {
/*    */     try {
/* 27 */       return this.content.getLabel(LocaleInfoProvider.getInstance().getLocale(Locale.ENGLISH));
/* 28 */     } catch (Exception e) {
/* 29 */       return super.toString();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 35 */     if (this == obj)
/* 36 */       return true; 
/* 37 */     if (obj instanceof Node) {
/* 38 */       Node other = (Node)obj;
/* 39 */       boolean ret = Util.equals(this.parent, other.parent);
/* 40 */       ret = (ret && Util.equals(this.content, other.content));
/* 41 */       return ret;
/*    */     } 
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 48 */     int ret = Node.class.hashCode();
/* 49 */     ret = HashCalc.addHashCode(ret, this.parent);
/* 50 */     ret = HashCalc.addHashCode(ret, this.content);
/* 51 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\datamodel\domain\toc\Node.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */