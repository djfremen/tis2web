/*    */ package com.eoos.gm.tis2web.sps.client.lt;
/*    */ 
/*    */ import net.sourceforge.jeval.Evaluator;
/*    */ 
/*    */ public class LTFormula
/*    */ {
/*    */   private Evaluator evaluator;
/*    */   private String formula;
/*    */   
/*    */   public LTFormula(String formula) {
/* 11 */     this.formula = formula;
/* 12 */     this.evaluator = new Evaluator();
/*    */   }
/*    */   
/*    */   public String calculate(int value) throws Exception {
/* 16 */     return calculate(Integer.toString(value));
/*    */   }
/*    */   
/*    */   public String calculate(double value) throws Exception {
/* 20 */     return calculate(Double.toString(value));
/*    */   }
/*    */   
/*    */   public String calculate(String value) throws Exception {
/* 24 */     this.evaluator.putVariable("X", value);
/* 25 */     String result = this.evaluator.evaluate(this.formula);
/* 26 */     return result;
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 30 */     LTFormula lt = new LTFormula("round( 10*(( (#{X} * 1.21) + 179 ) / 3600 )) / 10");
/* 31 */     System.out.println("221 -> " + lt.calculate("221"));
/* 32 */     System.out.println("666 -> " + lt.calculate(666));
/* 33 */     System.out.println("30 -> " + lt.calculate(30));
/* 34 */     System.out.println("130 -> " + lt.calculate(130));
/* 35 */     System.out.println("1442 -> " + lt.calculate(1442.0D));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\lt\LTFormula.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */