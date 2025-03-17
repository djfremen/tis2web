/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.scsm.v2.util.Util;
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
/* 17 */   public Node parent = null;
/*    */   
/* 19 */   public SITOCElement content = null;
/*    */ 
/*    */   
/*    */   public Node(SITOCElement content) {
/* 23 */     this.content = content;
/*    */   }
/*    */   
/*    */   public String getIdentifier() {
/* 27 */     StringBuffer tmp = new StringBuffer();
/* 28 */     tmp.append(this.content.getID());
/*    */     
/* 30 */     Node parent = this.parent;
/* 31 */     while (parent != null) {
/* 32 */       tmp.insert(0, parent.content.getID() + ".");
/* 33 */       parent = parent.parent;
/*    */     } 
/* 35 */     return tmp.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 40 */     if (this == obj)
/* 41 */       return true; 
/* 42 */     if (obj instanceof Node) {
/* 43 */       Node other = (Node)obj;
/* 44 */       boolean ret = Util.equals(getIdentifier(), other.getIdentifier());
/* 45 */       return ret;
/*    */     } 
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     return getIdentifier().hashCode();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\specialbrochures\Node.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */