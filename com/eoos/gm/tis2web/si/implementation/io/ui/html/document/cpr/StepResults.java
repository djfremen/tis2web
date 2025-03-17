/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*     */ 
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.FIPParser;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.Parser;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers.ParserMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StepResults
/*     */ {
/*     */   private Integer nextPlane;
/*  29 */   private String label = new String("");
/*     */   
/*  31 */   private String description = new String("");
/*     */   
/*  33 */   private List values = new LinkedList();
/*     */ 
/*     */   
/*     */   private String bookMark;
/*     */   
/*     */   private String tag;
/*     */   
/*  40 */   private static FIPParser fIPParser = new FIPParser();
/*     */ 
/*     */   
/*  43 */   private int linkType = 0;
/*     */ 
/*     */   
/*     */   public StepResults(String label, String description, String bookMark, String tag) {
/*  47 */     this.label = label;
/*  48 */     this.description = description;
/*  49 */     this.bookMark = bookMark;
/*  50 */     this.tag = tag;
/*  51 */     this.nextPlane = null;
/*     */   }
/*     */   
/*     */   public StepResults() {
/*  55 */     this.label = "";
/*  56 */     this.description = "";
/*  57 */     this.bookMark = "";
/*  58 */     this.tag = "";
/*  59 */     this.nextPlane = null;
/*     */   }
/*     */   
/*     */   public StepResults cloneStepResults() {
/*  63 */     StepResults ret = new StepResults(new String(this.label), new String(this.description), new String(this.bookMark), new String(this.tag));
/*  64 */     Iterator<String> it = this.values.iterator();
/*  65 */     while (it.hasNext()) {
/*  66 */       ret.values.add(new String(it.next()));
/*     */     }
/*  68 */     return ret;
/*     */   }
/*     */   
/*     */   public String getTag() {
/*  72 */     return this.tag;
/*     */   }
/*     */   
/*     */   public String getContent() {
/*  76 */     String ret = new String();
/*  77 */     ret = ret + "<table><tr><td>";
/*  78 */     ret = ret + this.description;
/*  79 */     ret = ret + "</td></tr><tr><td>";
/*  80 */     Iterator<String> it = this.values.iterator();
/*  81 */     if (it.hasNext()) {
/*  82 */       ret = ret + (String)it.next();
/*     */     }
/*  84 */     while (it.hasNext()) {
/*  85 */       ret = ret + ", ";
/*  86 */       ret = ret + (String)it.next();
/*     */     } 
/*  88 */     ret = ret + "</td></tr></table>\n";
/*  89 */     return ret;
/*     */   }
/*     */   
/*     */   public String getLabel() {
/*  93 */     return this.label;
/*     */   }
/*     */   
/*     */   public String getLinkLabel() {
/*  97 */     return this.label.equals("&nbsp;") ? "" : this.label;
/*     */   }
/*     */ 
/*     */   
/*     */   public Element init(Element elem) {
/* 102 */     Parser parser = null;
/* 103 */     String val = DomUtil.valueFrom(elem);
/* 104 */     elem = (Element)elem.getParentNode();
/*     */     
/* 106 */     String tagName = elem.getTagName();
/*     */     boolean isYesLink;
/* 108 */     if (elem != null && ((isYesLink = tagName.equals("YesLink")) || tagName.equals("NoLink"))) {
/* 109 */       val = DomUtil.firstChildValue(elem, "Label");
/* 110 */       val = val.trim();
/* 111 */       if (val.length() > 0 && val.charAt(val.length() - 1) == ':') {
/* 112 */         val = val.substring(0, val.length() - 1);
/*     */       }
/* 114 */       this.linkType = isYesLink ? 1 : -1;
/*     */     } 
/*     */     
/* 117 */     this.values.add(val);
/*     */     try {
/* 119 */       while (elem != null) {
/* 120 */         if (elem.getNodeType() == 1) {
/* 121 */           String name = elem.getTagName();
/* 122 */           ParserMap parserMap = ParserMap.getInstance();
/* 123 */           parser = (Parser)parserMap.get(name);
/* 124 */           if (parser != null) {
/* 125 */             this.label = parser.getLabel(elem);
/* 126 */             this.description = parser.getDescription(elem);
/* 127 */             this.bookMark = parser.getBookmark(elem);
/* 128 */             this.tag = name;
/*     */             break;
/*     */           } 
/*     */         } 
/* 132 */         elem = (Element)elem.getParentNode();
/*     */       }
/*     */     
/* 135 */     } catch (Exception e) {
/* 136 */       elem = null;
/*     */     } 
/*     */     
/* 139 */     return elem;
/*     */   }
/*     */   
/*     */   public void initFIP(Element elem) {
/* 143 */     this.label = fIPParser.getLabel(elem);
/* 144 */     this.description = fIPParser.getDescription(elem);
/* 145 */     this.bookMark = fIPParser.getBookmark(elem);
/* 146 */     this.tag = elem.getTagName();
/*     */   }
/*     */   
/*     */   public Parser init2(Element elem) {
/* 150 */     String name = elem.getTagName();
/* 151 */     ParserMap parserMap = ParserMap.getInstance();
/* 152 */     Parser parser = (Parser)parserMap.get(name);
/* 153 */     if (parser != null) {
/* 154 */       this.label = parser.getLabel(elem);
/* 155 */       this.description = parser.getDescription(elem);
/* 156 */       this.bookMark = parser.getBookmark(elem);
/* 157 */       this.tag = name;
/*     */     } 
/* 159 */     return parser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getNextPlane() {
/* 168 */     return this.nextPlane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNextPlane(Integer nextPlane) {
/* 178 */     this.nextPlane = nextPlane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBookMark() {
/* 187 */     return this.bookMark;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBookMark(String bookMark) {
/* 197 */     this.bookMark = bookMark;
/*     */   }
/*     */   
/*     */   public void addValue(String val) {
/* 201 */     this.values.add(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLinkType() {
/* 210 */     return this.linkType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkType(int linkType) {
/* 220 */     this.linkType = linkType;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\StepResults.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */