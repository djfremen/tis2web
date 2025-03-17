/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.apache.xpath.XPathAPI;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ 
/*     */ public class DocumentHistory
/*     */   extends LinkedList
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int sioId;
/*     */   private String chapterNr;
/*     */   private String title;
/*  29 */   private HistoryLink linkFrom = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentHistory() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentHistory(Document doc, int sioId) throws TransformerException {
/*  40 */     this.sioId = sioId;
/*  41 */     NodeList nodelist = XPathAPI.selectNodeList(doc, "//ChapterInfo");
/*     */ 
/*     */     
/*  44 */     if (0 < nodelist.getLength()) {
/*     */       
/*  46 */       Element elem = (Element)nodelist.item(0);
/*     */       
/*  48 */       this.chapterNr = elem.getAttribute("ChapterNr");
/*  49 */       NodeList subNodes = elem.getElementsByTagName("Title");
/*  50 */       if (0 < subNodes.getLength()) {
/*  51 */         Element elem2 = (Element)subNodes.item(0);
/*  52 */         this.title = DomUtil.valueFrom(elem2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getChapterNr() {
/*  59 */     return this.chapterNr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSioId() {
/*  68 */     return this.sioId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSioId(int sioId) {
/*  78 */     this.sioId = sioId;
/*     */   }
/*     */   
/*     */   public StringBuffer getProtocolHeader(int colspan) {
/*  82 */     StringBuffer ret = new StringBuffer();
/*  83 */     ret.append("<tr><td id=\"LeftContent\">");
/*  84 */     ret.append(this.chapterNr);
/*  85 */     ret.append("</td><td id=\"RightContent\" colspan=\"");
/*  86 */     ret.append(colspan);
/*  87 */     ret.append("\">");
/*  88 */     ret.append(this.title);
/*  89 */     ret.append("</td></tr>\n");
/*  90 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HistoryLink getLinkFrom() {
/*  99 */     return this.linkFrom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkFrom(HistoryLink linkFrom) {
/* 109 */     this.linkFrom = linkFrom;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\DocumentHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */