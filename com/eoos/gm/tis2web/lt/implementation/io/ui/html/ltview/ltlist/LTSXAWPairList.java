/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.ListElement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTSXAWPairList
/*     */   extends ListElement
/*     */ {
/*     */   private HtmlLabel aw;
/*     */   private HtmlElement link;
/*  27 */   static HashMap tableMap = null;
/*     */   
/*  29 */   static HashMap contentMap = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LTSXAWPairList(HtmlLabel data, HtmlElement link, ClientContext context) {
/*  43 */     this.aw = data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     this.link = link;
/*  50 */     List<HtmlLabel> l = new ArrayList(1);
/*  51 */     l.add(data);
/*  52 */     setData(l);
/*     */     
/*  54 */     synchronized (LTSXAWPairList.class) {
/*  55 */       if (tableMap == null) {
/*  56 */         tableMap = new HashMap<Object, Object>();
/*  57 */         tableMap.put("width", "100%");
/*     */       } 
/*  59 */       if (contentMap == null) {
/*  60 */         contentMap = new HashMap<Object, Object>();
/*  61 */         contentMap.put("width", "50%");
/*  62 */         contentMap.put("style", "vertical-align:top");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean enableHeader() {
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/*  73 */     return 2;
/*     */   }
/*     */   
/*     */   public boolean isHeader(int rowIndex, int columnIndex) {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*  82 */     switch (columnIndex) {
/*     */       case 0:
/*  84 */         return (HtmlElement)this.aw;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*  89 */         return this.link;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/*  99 */     map.putAll(tableMap);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int colomnIndex, Map map) {
/* 103 */     map.putAll(contentMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 109 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\ltlist\LTSXAWPairList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */