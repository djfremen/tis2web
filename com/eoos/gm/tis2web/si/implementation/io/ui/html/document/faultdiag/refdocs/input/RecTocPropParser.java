/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.faultdiag.refdocs.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input.TocPropParser;
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
/*    */ public abstract class RecTocPropParser
/*    */   extends TocPropParser
/*    */ {
/*    */   protected boolean found = false;
/* 21 */   protected int plane = 0;
/*    */   protected String[] prefs;
/*    */   
/*    */   public RecTocPropParser(SITOCElement x, SITOCProperty first, SITOCProperty second) {
/* 25 */     super(x, first, second);
/*    */   }
/*    */   
/*    */   public void parse(SITOCElement root, String[] prefs) {
/* 29 */     this.prefs = prefs;
/* 30 */     this.plane = 0;
/* 31 */     if (prefs.length > 0) {
/* 32 */       parse(root, prefs[0]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addPropElement(SITOCElement node) {
/* 40 */     boolean foundStore = this.found;
/* 41 */     this.found = false;
/* 42 */     this.plane++;
/* 43 */     if (this.plane < this.prefs.length) {
/* 44 */       String newPrefs = this.prefs[this.plane];
/* 45 */       if (this.propVal.indexOf(newPrefs) >= 0) {
/* 46 */         String prefStore = this.pref;
/* 47 */         parse(node, newPrefs);
/* 48 */         this.pref = prefStore;
/*    */       } 
/*    */     } 
/* 51 */     this.plane--;
/* 52 */     if (!this.found) {
/* 53 */       addRecElement(node);
/* 54 */       this.found = true;
/*    */     } 
/* 56 */     this.found = (this.found || foundStore);
/*    */   }
/*    */   
/*    */   protected abstract void addRecElement(SITOCElement paramSITOCElement);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\faultdiag\refdocs\input\RecTocPropParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */