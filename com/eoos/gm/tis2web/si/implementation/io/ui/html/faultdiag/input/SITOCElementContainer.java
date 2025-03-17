/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SITOCElementContainer
/*    */   implements Comparable
/*    */ {
/*    */   private SITOCElement element;
/*    */   private Object dtc;
/*    */   
/*    */   public SITOCElementContainer(SITOCElement element, SITOCProperty prop) {
/* 22 */     this.element = element;
/* 23 */     this.dtc = element.getProperty(prop);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(Object o) {
/* 31 */     return this.dtc.toString().compareTo(o.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 36 */     if (this == obj)
/* 37 */       return true; 
/* 38 */     if (obj instanceof SITOCElementContainer) {
/* 39 */       SITOCElementContainer c = (SITOCElementContainer)obj;
/* 40 */       boolean ret = this.dtc.toString().equals(c.dtc.toString());
/* 41 */       ret = (ret && this.element.equals(c.element));
/* 42 */       return ret;
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     int ret = SITOCElementContainer.class.hashCode();
/* 51 */     ret = HashCalc.addHashCode(ret, this.dtc.toString());
/* 52 */     ret = HashCalc.addHashCode(ret, this.element);
/*    */     
/* 54 */     return ret;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SITOCElement getElement() {
/* 61 */     return this.element;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 68 */     return this.dtc.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\SITOCElementContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */