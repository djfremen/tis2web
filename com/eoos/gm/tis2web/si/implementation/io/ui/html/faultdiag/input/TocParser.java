/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.faultdiag.input;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*    */ import java.util.List;
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
/*    */ public abstract class TocParser
/*    */ {
/*    */   protected boolean go = true;
/*    */   
/*    */   public void parse(SITOCElement root, SITOCProperty first) {
/* 25 */     parseChildren(root.getProperty(first));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isGo() {
/* 32 */     return this.go;
/*    */   }
/*    */   
/*    */   public void parseChildren(Object rootObj) {
/* 36 */     if (rootObj != null && rootObj instanceof SITOCElement) {
/* 37 */       SITOCElement root = (SITOCElement)rootObj;
/* 38 */       List list = root.getChildren();
/* 39 */       parseList(list);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void parseList(Object obj) {
/* 44 */     if (obj != null && obj instanceof List) {
/* 45 */       List list = (List)obj;
/* 46 */       for (int i = 0; i < list.size() && this.go; i++) {
/* 47 */         Object x = list.get(i);
/* 48 */         if (x != null && x instanceof SITOCElement)
/* 49 */           addElement((SITOCElement)x); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void addElement(SITOCElement paramSITOCElement);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\faultdiag\input\TocParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */