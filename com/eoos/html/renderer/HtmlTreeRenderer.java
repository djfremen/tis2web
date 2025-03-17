/*    */ package com.eoos.html.renderer;
/*    */ 
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
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
/*    */ public class HtmlTreeRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   protected static final String LEVEL_TEMPLATE = "<table class=\"tree\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">{ROWS}</table>";
/*    */   protected static final String ROW_TEMPLATE = "<tr><td valign=\"top\">{ICON_ELEMENT}</td><td>{LABEL_ELEMENT}{NEXT_LEVEL}</td></tr>";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback
/*    */   {
/*    */     public HtmlTreeRenderer.Callback getSubLevelCallback(int rowIndex) {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */   }
/*    */ 
/*    */   
/* 36 */   private static HtmlTreeRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlTreeRenderer getInstance() {
/* 45 */     if (instance == null) {
/* 46 */       instance = new HtmlTreeRenderer();
/*    */     }
/*    */     
/* 49 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 53 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 57 */     StringBuffer code = StringBufferPool.getThreadInstance().get("<table class=\"tree\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">{ROWS}</table>");
/*    */     try {
/* 59 */       callback.init(params);
/* 60 */       StringBuffer rows = StringBufferPool.getThreadInstance().get(callback.getRowCount() * 500);
/*    */       
/*    */       try {
/* 63 */         for (int rowIndex = 0; rowIndex < callback.getRowCount(); rowIndex++) {
/* 64 */           StringBuffer row = StringBufferPool.getThreadInstance().get("<tr><td valign=\"top\">{ICON_ELEMENT}</td><td>{LABEL_ELEMENT}{NEXT_LEVEL}</td></tr>");
/*    */           
/*    */           try {
/* 67 */             StringUtilities.replace(row, "{ICON_ELEMENT}", callback.getIconElement(rowIndex).getHtmlCode(null));
/* 68 */             StringUtilities.replace(row, "{LABEL_ELEMENT}", callback.getLabelElement(rowIndex).getHtmlCode(null));
/*    */             
/* 70 */             Callback subCallback = callback.getSubLevelCallback(rowIndex);
/*    */             
/* 72 */             if (subCallback != null) {
/* 73 */               StringUtilities.replace(row, "{NEXT_LEVEL}", "<br>" + getHtmlCode(subCallback));
/*    */             } else {
/* 75 */               StringUtilities.replace(row, "{NEXT_LEVEL}", "");
/*    */             } 
/*    */             
/* 78 */             rows.append(row.toString());
/*    */           } finally {
/* 80 */             StringBufferPool.getThreadInstance().free(row);
/*    */           } 
/*    */         } 
/*    */         
/* 84 */         StringUtilities.replace(code, "{ROWS}", rows.toString());
/*    */       } finally {
/*    */         
/* 87 */         StringBufferPool.getThreadInstance().free(rows);
/*    */       } 
/*    */       
/* 90 */       return code.toString();
/*    */     } finally {
/*    */       
/* 93 */       StringBufferPool.getThreadInstance().free(code);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 98 */     return getHtmlCode((Callback)callback, params);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     HtmlElement getIconElement(int param1Int);
/*    */     
/*    */     HtmlElement getLabelElement(int param1Int);
/*    */     
/*    */     int getRowCount();
/*    */     
/*    */     Callback getSubLevelCallback(int param1Int);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlTreeRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */