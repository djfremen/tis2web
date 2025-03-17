/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.log4j.ui.html.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*    */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.AppenderSkeleton;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ 
/*    */ public class TresholdSelectionElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 20 */   private static final Logger log = Logger.getLogger(TresholdSelectionElement.class);
/*    */   
/*    */   private SelectBoxSelectionElement selection;
/*    */   
/*    */   private AppenderSkeleton appender;
/*    */   
/*    */   public TresholdSelectionElement(AppenderSkeleton appender) {
/* 27 */     this.appender = appender;
/* 28 */     final List<Level> levels = Arrays.asList(new Level[] { Level.ALL, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL, Level.OFF });
/* 29 */     DataRetrievalAbstraction.DataCallback callback = new DataRetrievalAbstraction.DataCallback()
/*    */       {
/*    */         public List getData() {
/* 32 */           return levels;
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 37 */     this.selection = new SelectBoxSelectionElement(ApplicationContext.getInstance().createID(), true, callback, 1) {
/*    */         protected boolean autoSubmitOnChange() {
/* 39 */           return true;
/*    */         }
/*    */         
/*    */         protected Object onChange(Map submitParams) {
/*    */           try {
/* 44 */             TresholdSelectionElement.this.appender.setThreshold((Priority)getValue());
/* 45 */           } catch (Exception e) {
/* 46 */             TresholdSelectionElement.log.error("unable to change treshold - excpetion: " + e, e);
/*    */           } 
/* 48 */           return null;
/*    */         }
/*    */       };
/*    */     
/* 52 */     addElement((HtmlElement)this.selection);
/*    */     
/* 54 */     this.selection.setValue(appender.getThreshold());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 59 */     return this.selection.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\log4\\ui\html\main\TresholdSelectionElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */