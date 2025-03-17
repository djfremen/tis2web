/*    */ package com.eoos.gm.tis2web.registration.common.ui.base;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.input.date.DateSelectionElement;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import java.util.Calendar;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class DateSelectionElement extends DateSelectionElement {
/*    */   public DateSelectionElement(String parameterName, int yearSelectionWidth, Locale locale) {
/* 13 */     super(parameterName, yearSelectionWidth, locale);
/*    */   }
/*    */   private static final String datetemplate = "<table><tr><td>{YEAR}</td><td>-</td><td>{MONTH}</td><td>-</td><td>{DAY}</td></tr></table>";
/*    */   public DateSelectionElement(String parameterName, int yearSelectionWidth, Locale locale, int startYear) {
/* 17 */     super(parameterName, yearSelectionWidth, locale, startYear);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 23 */     StringBuffer tmp = new StringBuffer("<table><tr><td>{YEAR}</td><td>-</td><td>{MONTH}</td><td>-</td><td>{DAY}</td></tr></table>");
/* 24 */     StringUtilities.replace(tmp, "{YEAR}", this.selectionYear.getHtmlCode(params));
/* 25 */     StringUtilities.replace(tmp, "{MONTH}", this.selectionMonth.getHtmlCode(params));
/* 26 */     StringUtilities.replace(tmp, "{DAY}", this.selectionDay.getHtmlCode(params));
/* 27 */     StringUtilities.replace(tmp, "{HOUR}", "");
/* 28 */     StringUtilities.replace(tmp, "{MINUTE}", "");
/* 29 */     StringUtilities.replace(tmp, "{SECOND}", "");
/* 30 */     return tmp.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 35 */     Calendar c = Calendar.getInstance();
/*    */     try {
/* 37 */       c.set(1, ((Integer)this.selectionYear.getValue()).intValue());
/* 38 */       c.set(2, ((Integer)this.selectionMonth.getValue()).intValue() - 1);
/* 39 */       c.set(5, ((Integer)this.selectionDay.getValue()).intValue());
/* 40 */       return c.getTime();
/* 41 */     } catch (ClassCastException e) {
/* 42 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\base\DateSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */