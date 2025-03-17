/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
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
/*    */ public abstract class TocPropParser
/*    */   extends TocParser
/*    */ {
/*    */   protected SITOCProperty second;
/*    */   protected String propVal;
/*    */   protected String pref;
/*    */   
/*    */   public TocPropParser(SITOCElement x, SITOCProperty first, SITOCProperty second) {
/* 24 */     this.second = second;
/* 25 */     if (x != null) {
/* 26 */       Object agObj = x.getProperty(first);
/* 27 */       if (agObj != null && agObj instanceof String) {
/* 28 */         this.propVal = (String)agObj;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void parse(SITOCElement root, String pref) {
/* 34 */     this.pref = pref;
/* 35 */     parseChildren(root);
/*    */   }
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
/*    */   protected void addElement(SITOCElement node) {
/* 53 */     Object obj = node.getProperty(this.second);
/* 54 */     if (obj instanceof String) {
/* 55 */       String search = this.pref + (String)obj;
/* 56 */       int ind = this.propVal.indexOf(search);
/* 57 */       if (ind >= 0) {
/* 58 */         ind += search.length();
/* 59 */         if (ind >= this.propVal.length() || this.propVal.charAt(ind) == ';')
/* 60 */           addPropElement(node); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void addPropElement(SITOCElement paramSITOCElement);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\TocPropParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */