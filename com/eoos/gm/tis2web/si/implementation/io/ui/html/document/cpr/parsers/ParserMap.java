/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParserMap
/*    */   extends HashMap
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   private static ParserMap instance = new ParserMap();
/*    */   
/*    */   public static ParserMap getInstance() {
/* 19 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ParserMap() {
/* 25 */     put((K)"TestStep", (V)new StdParser());
/* 26 */     put((K)"DTCTestStep", (V)new DTCParser());
/* 27 */     put((K)"DLPStep", (V)new DLPParser());
/* 28 */     put((K)"TypeIIITestStep", (V)new DLPParser());
/* 29 */     put((K)"SCTTestStep", (V)new SCTParser());
/* 30 */     put((K)"FIPResultStep", (V)new FIPParser());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\ParserMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */