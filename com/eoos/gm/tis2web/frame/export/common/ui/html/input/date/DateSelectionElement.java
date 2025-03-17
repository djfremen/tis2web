/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html.input.date;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DateSelectionElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   protected SelectBoxSelectionElement selectionDay;
/*     */   protected SelectBoxSelectionElement selectionMonth;
/*     */   protected SelectBoxSelectionElement selectionYear;
/*     */   protected SelectBoxSelectionElement selectionHour;
/*     */   protected SelectBoxSelectionElement selectionMinute;
/*     */   protected SelectBoxSelectionElement selectionSecond;
/*  31 */   private static final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance();
/*     */   static {
/*  33 */     NUMBER_FORMAT.setMinimumIntegerDigits(2);
/*     */   }
/*     */   
/*  36 */   private static final Object INPUT_EXPLANATION = new Object(); private static final int START_YEAR = 2005;
/*     */   private static final String template = "<table><tr><td>{YEAR}</td><td>-</td><td>{MONTH}</td><td>-</td><td>{DAY}</td><td>&nbsp;</td><td>{HOUR}</td><td>:</td><td>{MINUTE}</td><td>:</td><td>{SECOND}</td></tr></table>";
/*     */   
/*     */   public DateSelectionElement(String parameterName, int yearSelectionWidth, Locale locale) {
/*  40 */     this(parameterName, yearSelectionWidth, locale, 2005);
/*     */   }
/*     */ 
/*     */   
/*     */   public DateSelectionElement(String parameterName, final int yearSelectionWidth, final Locale locale, final int startYear) {
/*  45 */     final ApplicationContext context = ApplicationContext.getInstance();
/*     */     
/*  47 */     this.selectionDay = new SelectBoxSelectionElement(parameterName + "1", true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*  49 */           private final List data = new ArrayList(31);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public List getData() {
/*  58 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/*  63 */           if (option == DateSelectionElement.INPUT_EXPLANATION) {
/*  64 */             return context.getLabel(locale, "day");
/*     */           }
/*  66 */           return super.getDisplayValue(option);
/*     */         }
/*     */       };
/*     */     
/*  70 */     addElement((HtmlElement)this.selectionDay);
/*     */     
/*  72 */     this.selectionMonth = new SelectBoxSelectionElement(parameterName + "2", true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*  74 */           private final List data = new ArrayList(12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public List getData() {
/*  83 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/*  88 */           if (option == DateSelectionElement.INPUT_EXPLANATION) {
/*  89 */             return context.getLabel(locale, "month");
/*     */           }
/*  91 */           return super.getDisplayValue(option);
/*     */         }
/*     */       };
/*     */     
/*  95 */     addElement((HtmlElement)this.selectionMonth);
/*     */     
/*  97 */     this.selectionYear = new SelectBoxSelectionElement(parameterName + "3", true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*  99 */           private final List data = new ArrayList(31);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public List getData() {
/* 110 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/* 115 */           if (option == DateSelectionElement.INPUT_EXPLANATION) {
/* 116 */             return context.getLabel(locale, "year");
/*     */           }
/* 118 */           return super.getDisplayValue(option);
/*     */         }
/*     */       };
/*     */     
/* 122 */     addElement((HtmlElement)this.selectionYear);
/*     */     
/* 124 */     this.selectionHour = new SelectBoxSelectionElement(parameterName + "4", true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/* 126 */           private final List data = new ArrayList(24);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public List getData() {
/* 135 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/* 140 */           if (option == DateSelectionElement.INPUT_EXPLANATION) {
/* 141 */             return context.getLabel(locale, "hour");
/*     */           }
/* 143 */           return DateSelectionElement.NUMBER_FORMAT.format(option);
/*     */         }
/*     */       };
/*     */     
/* 147 */     addElement((HtmlElement)this.selectionHour);
/*     */     
/* 149 */     this.selectionMinute = new SelectBoxSelectionElement(parameterName + "5", true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/* 151 */           private final List data = new ArrayList(60);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public List getData() {
/* 160 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/* 165 */           if (option == DateSelectionElement.INPUT_EXPLANATION) {
/* 166 */             return context.getLabel(locale, "minute");
/*     */           }
/* 168 */           return DateSelectionElement.NUMBER_FORMAT.format(option);
/*     */         }
/*     */       };
/*     */     
/* 172 */     addElement((HtmlElement)this.selectionMinute);
/*     */     
/* 174 */     this.selectionSecond = new SelectBoxSelectionElement(parameterName + "6", true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/* 176 */           private final List data = new ArrayList(60);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public List getData() {
/* 185 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/* 190 */           if (option == DateSelectionElement.INPUT_EXPLANATION) {
/* 191 */             return context.getLabel(locale, "second");
/*     */           }
/* 193 */           return DateSelectionElement.NUMBER_FORMAT.format(option);
/*     */         }
/*     */       };
/*     */     
/* 197 */     addElement((HtmlElement)this.selectionSecond);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 204 */     ApplicationContext.getInstance();
/* 205 */     StringBuffer tmp = new StringBuffer("<table><tr><td>{YEAR}</td><td>-</td><td>{MONTH}</td><td>-</td><td>{DAY}</td><td>&nbsp;</td><td>{HOUR}</td><td>:</td><td>{MINUTE}</td><td>:</td><td>{SECOND}</td></tr></table>");
/* 206 */     StringUtilities.replace(tmp, "{YEAR}", this.selectionYear.getHtmlCode(params));
/* 207 */     StringUtilities.replace(tmp, "{MONTH}", this.selectionMonth.getHtmlCode(params));
/* 208 */     StringUtilities.replace(tmp, "{DAY}", this.selectionDay.getHtmlCode(params));
/* 209 */     StringUtilities.replace(tmp, "{HOUR}", this.selectionHour.getHtmlCode(params));
/* 210 */     StringUtilities.replace(tmp, "{MINUTE}", this.selectionMinute.getHtmlCode(params));
/* 211 */     StringUtilities.replace(tmp, "{SECOND}", this.selectionSecond.getHtmlCode(params));
/* 212 */     return tmp.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 217 */     Calendar c = Calendar.getInstance();
/*     */     try {
/* 219 */       c.set(1, ((Integer)this.selectionYear.getValue()).intValue());
/* 220 */       c.set(2, ((Integer)this.selectionMonth.getValue()).intValue() - 1);
/* 221 */       c.set(5, ((Integer)this.selectionDay.getValue()).intValue());
/* 222 */       c.set(11, ((Integer)this.selectionHour.getValue()).intValue());
/* 223 */       c.set(12, ((Integer)this.selectionMinute.getValue()).intValue());
/* 224 */       c.set(13, ((Integer)this.selectionSecond.getValue()).intValue());
/* 225 */       c.set(14, 0);
/* 226 */       return c.getTime();
/* 227 */     } catch (ClassCastException e) {
/* 228 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 233 */     if (value == null) {
/* 234 */       this.selectionYear.setValue(INPUT_EXPLANATION);
/* 235 */       this.selectionMonth.setValue(INPUT_EXPLANATION);
/* 236 */       this.selectionDay.setValue(INPUT_EXPLANATION);
/* 237 */       this.selectionHour.setValue(INPUT_EXPLANATION);
/* 238 */       this.selectionMinute.setValue(INPUT_EXPLANATION);
/* 239 */       this.selectionSecond.setValue(INPUT_EXPLANATION);
/*     */     } else {
/* 241 */       if (!(value instanceof Date)) {
/* 242 */         throw new IllegalArgumentException();
/*     */       }
/* 244 */       Calendar c = Calendar.getInstance();
/* 245 */       c.setTime((Date)value);
/* 246 */       this.selectionYear.setValue(Integer.valueOf(c.get(1)));
/* 247 */       this.selectionMonth.setValue(Integer.valueOf(c.get(2) + 1));
/* 248 */       this.selectionDay.setValue(Integer.valueOf(c.get(5)));
/* 249 */       this.selectionHour.setValue(Integer.valueOf(c.get(11)));
/* 250 */       this.selectionMinute.setValue(Integer.valueOf(c.get(12)));
/* 251 */       this.selectionSecond.setValue(Integer.valueOf(c.get(13)));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\input\date\DateSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */