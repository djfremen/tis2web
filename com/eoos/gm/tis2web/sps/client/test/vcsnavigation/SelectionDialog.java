/*    */ package com.eoos.gm.tis2web.sps.client.test.vcsnavigation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.LineNumberReader;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectionDialog
/*    */ {
/*    */   public String perform(DisplayableAttribute attr, List values, Locale locale) {
/* 21 */     String result = null;
/* 22 */     System.out.print(attr.getDenotation(locale) + ":");
/* 23 */     Iterator<DisplayableValue> it = values.iterator();
/* 24 */     while (it.hasNext()) {
/* 25 */       DisplayableValue value = it.next();
/* 26 */       System.out.println("\t" + value.getDenotation(locale));
/*    */     } 
/* 28 */     LineNumberReader rdr = new LineNumberReader(new InputStreamReader(System.in));
/*    */     try {
/* 30 */       result = rdr.readLine();
/* 31 */     } catch (Exception e) {
/* 32 */       System.out.println("Damned dialog: " + e);
/*    */     } 
/* 34 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\vcsnavigation\SelectionDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */