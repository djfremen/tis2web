/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.replacer;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Replacer
/*     */ {
/*     */   public static final int TYPE_COMMON_PREFIX_HANDLER = 0;
/*     */   public static final int TYPE_SIO_DOC_LINK = 1;
/*     */   public static final int TYPE_IMAGE_FILE_LINK = 2;
/*     */   public static final int TYPE_IMAGE_LINK = 3;
/*     */   public static final int TYPE_STYLESHEET = 4;
/*     */   public static final int TYPE_DETAIL_VIEW = 5;
/*     */   public static final int TYPE_AW_LINK = 6;
/*     */   public static final int TYPE_CELL_LINK = 7;
/*     */   public static final int TYPE_LINK_LIST = 8;
/*     */   public static final int TYPE_GRAPHIC_LINK = 9;
/*     */   public static final int TYPE_FAULTFINDER_LINK = 10;
/*     */   public static final int TYPE_COMPONENT_LINK = 11;
/*     */   protected static final String patternDocumentLink = "LINK_SIO:ID:";
/*     */   protected static final String patternImage = "LINK_IMAGE:ID:";
/*     */   protected static final String patternImageFile = "IMAGE:FILE:";
/*     */   protected static final String patternStylesheet = "STYLE_SHEET:FILE:";
/*     */   protected static final String patternDetail = "LINK_DETAIL:ID:";
/*     */   protected static final String patternAWLink = "LINK_AW:ID:";
/*     */   protected static final String patternCellLink = "LINK_CELL:ID:";
/*     */   protected static final String patternLinkList = "LINK_SIO:LIST:";
/*     */   protected static final String patternGraphic = "LINK_GRAPHIC:ID:";
/*     */   protected static final String patternFaultFinderLink = "LINK_FF:ID:";
/*     */   protected static final String patternComponentLink = "LINK_COMPONENTS:ID:";
/*     */   
/*     */   public static interface ReplacerCallback
/*     */   {
/*     */     String replace(Replacer.TemplateReplacer param1TemplateReplacer);
/*     */   }
/*     */   
/*     */   public static abstract class TemplateReplacer
/*     */   {
/*     */     protected int type;
/*     */     protected String pattern;
/*     */     
/*     */     public TemplateReplacer() {
/*  44 */       this.type = 0;
/*     */     }
/*     */     
/*     */     public TemplateReplacer(int type, String pattern) {
/*  48 */       this.type = type;
/*  49 */       this.pattern = pattern;
/*     */     }
/*     */     
/*     */     public int getType() {
/*  53 */       return this.type;
/*     */     }
/*     */     
/*     */     public String getPattern() {
/*  57 */       return this.pattern;
/*     */     }
/*     */     
/*     */     public boolean match(String html, int offset) {
/*  61 */       return match(html, offset, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean match(String html, int offset, int skip) {
/*  66 */       for (int i = skip, pos = offset + skip; i < this.pattern.length(); i++, pos++) {
/*  67 */         char c = html.charAt(pos);
/*  68 */         if (c != this.pattern.charAt(i)) {
/*  69 */           return false;
/*     */         }
/*     */       } 
/*  72 */       return true;
/*     */     }
/*     */     
/*     */     public abstract void parse(String param1String, int param1Int);
/*     */   }
/*     */   
/*     */   public static abstract class KeyReplacer extends TemplateReplacer {
/*     */     protected String key;
/*     */     
/*     */     public KeyReplacer(int type, String pattern) {
/*  82 */       super(type, pattern);
/*     */     }
/*     */     
/*     */     public String getKey() {
/*  86 */       return this.key;
/*     */     }
/*     */     
/*     */     public void parse(String html, int offset) {
/*  90 */       offset += this.pattern.length();
/*  91 */       StringBuffer id = new StringBuffer();
/*  92 */       for (int i = offset; i < html.length(); i++) {
/*  93 */         char c = html.charAt(i);
/*  94 */         if (c == '}' || c == ':') {
/*  95 */           this.key = id.toString();
/*     */           return;
/*     */         } 
/*  98 */         id.append(c);
/*     */       } 
/* 100 */       throw new RuntimeException("failed match (pos = " + offset + ": '" + this.pattern + "')");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SIODocumentLinkReplacer extends KeyReplacer {
/*     */     protected String bookmark;
/*     */     
/*     */     public SIODocumentLinkReplacer() {
/* 108 */       super(1, "LINK_SIO:ID:");
/*     */     }
/*     */     
/*     */     protected SIODocumentLinkReplacer(int type, String pattern) {
/* 112 */       super(type, pattern);
/*     */     }
/*     */     
/*     */     public String getBookmark() {
/* 116 */       return this.bookmark;
/*     */     }
/*     */     
/*     */     protected void extractBookmark(String html, int offset) {
/* 120 */       this.bookmark = null;
/* 121 */       int idx = offset + this.pattern.length() + getKey().length();
/* 122 */       char c = html.charAt(idx);
/* 123 */       if (c == ':' && ":BM:".equals(html.substring(idx, idx + 4))) {
/* 124 */         StringBuffer bm = new StringBuffer();
/* 125 */         for (int i = idx + 4; i < html.length(); i++) {
/* 126 */           c = html.charAt(i);
/* 127 */           if (c == '}') {
/* 128 */             this.bookmark = bm.toString();
/*     */             return;
/*     */           } 
/* 131 */           bm.append(c);
/*     */         } 
/* 133 */         throw new RuntimeException("failed match (pos = " + offset + ": '" + this.pattern + "')");
/*     */       } 
/*     */     }
/*     */     
/*     */     public void parse(String html, int offset) {
/* 138 */       super.parse(html, offset);
/* 139 */       extractBookmark(html, offset);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ImageFileReplacer extends KeyReplacer {
/*     */     public ImageFileReplacer() {
/* 145 */       super(2, "IMAGE:FILE:");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ImageReplacer extends KeyReplacer {
/*     */     public ImageReplacer() {
/* 151 */       super(3, "LINK_IMAGE:ID:");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class StyleSheetReplacer extends KeyReplacer {
/*     */     public StyleSheetReplacer() {
/* 157 */       super(4, "STYLE_SHEET:FILE:");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DetailViewReplacer extends KeyReplacer {
/*     */     protected List settings;
/*     */     
/*     */     public DetailViewReplacer() {
/* 165 */       super(5, "LINK_DETAIL:ID:");
/*     */     }
/*     */     
/*     */     public List getLayerSettings() {
/* 169 */       return this.settings;
/*     */     }
/*     */     
/*     */     public void parse(String html, int offset) {
/* 173 */       offset += this.pattern.length();
/* 174 */       StringBuffer content = new StringBuffer();
/* 175 */       for (int i = offset; i < html.length(); i++) {
/* 176 */         char c = html.charAt(i);
/* 177 */         if (c == '}' || c == ':') {
/* 178 */           this.key = content.toString();
/* 179 */           if (c == ':') {
/* 180 */             parseLayerSettings(html, i);
/*     */           }
/*     */           return;
/*     */         } 
/* 184 */         content.append(c);
/*     */       } 
/* 186 */       throw new RuntimeException("failed match (pos = " + offset + ": '" + this.pattern + "')");
/*     */     }
/*     */     
/*     */     protected void parseLayerSettings(String html, int offset) {
/* 190 */       this.settings = new LinkedList();
/* 191 */       StringBuffer layer = new StringBuffer();
/* 192 */       StringBuffer setting = new StringBuffer();
/* 193 */       boolean parseLayer = false;
/* 194 */       boolean parseSetting = false;
/* 195 */       for (int i = offset; i < html.length(); i++) {
/* 196 */         char c = html.charAt(i);
/* 197 */         if (c == '}') {
/* 198 */           if (this.settings.size() == 0)
/*     */           {
/* 200 */             this.settings = null; } 
/*     */           return;
/*     */         } 
/* 203 */         if (c == '(') {
/* 204 */           parseLayer = true;
/* 205 */         } else if (c == ',') {
/* 206 */           parseLayer = false;
/* 207 */           parseSetting = true;
/* 208 */         } else if (c == ')') {
/* 209 */           parseSetting = false;
/* 210 */           this.settings.add(new PairImpl(layer.toString(), setting.toString()));
/* 211 */           layer.setLength(0);
/* 212 */           setting.setLength(0);
/* 213 */         } else if (parseLayer) {
/* 214 */           layer.append(c);
/* 215 */         } else if (parseSetting) {
/* 216 */           setting.append(c);
/*     */         } 
/*     */       } 
/* 219 */       throw new RuntimeException("failed match (pos = " + offset + ": '" + this.pattern + "')");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class AWLinkReplacer extends KeyReplacer {
/*     */     public AWLinkReplacer() {
/* 225 */       super(6, "LINK_AW:ID:");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SIOCellLinkReplacer extends SIODocumentLinkReplacer {
/*     */     public SIOCellLinkReplacer() {
/* 231 */       super(7, "LINK_CELL:ID:");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FaultFinderReplacer extends KeyReplacer {
/*     */     public FaultFinderReplacer() {
/* 237 */       super(10, "LINK_FF:ID:");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ComponentLinkReplacer extends KeyReplacer {
/*     */     public ComponentLinkReplacer() {
/* 243 */       super(11, "LINK_COMPONENTS:ID:");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SIOLinkListReplacer extends TemplateReplacer {
/*     */     protected List links;
/*     */     
/*     */     public SIOLinkListReplacer() {
/* 251 */       super(8, "LINK_SIO:LIST:");
/*     */     }
/*     */     
/*     */     public List getLinks() {
/* 255 */       return this.links;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void parse(String html, int offset) {
/* 262 */       this.links = new LinkedList();
/* 263 */       offset += this.pattern.length();
/* 264 */       StringBuffer content = null;
/* 265 */       Replacer.SIOLink link = null;
/* 266 */       int iCount = 0;
/* 267 */       for (int i = offset; i < html.length(); i++) {
/* 268 */         char c = html.charAt(i);
/* 269 */         if (c == '}')
/*     */           return; 
/* 271 */         if (c == '(') {
/* 272 */           content = new StringBuffer();
/* 273 */         } else if (c == ':') {
/* 274 */           iCount++;
/* 275 */           if (iCount == 1) {
/* 276 */             link = new Replacer.SIOLink(content.toString());
/* 277 */           } else if (iCount == 2) {
/* 278 */             link.setSIO(content.toString());
/*     */           } 
/* 280 */           content = new StringBuffer();
/* 281 */         } else if (c == ')') {
/* 282 */           if (link.getSIO() < 0) {
/* 283 */             link.setSIO(content.toString());
/*     */           } else {
/* 285 */             link.setBookmark(content.toString());
/*     */           } 
/* 287 */           this.links.add(link);
/* 288 */           link = null;
/* 289 */           iCount = 0;
/* 290 */         } else if (content != null) {
/* 291 */           content.append(c);
/*     */         } 
/*     */       } 
/* 294 */       throw new RuntimeException("failed match (pos = " + offset + ": '" + this.pattern + "')");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SIOLink {
/* 299 */     int sio = -1;
/*     */     String bm;
/*     */     String qualifier;
/*     */     
/*     */     public SIOLink(String qualifier) {
/* 304 */       this.qualifier = qualifier;
/*     */     }
/*     */     
/*     */     public SIOLink(int sio, String bm) {
/* 308 */       this.sio = sio;
/* 309 */       this.bm = bm;
/*     */     }
/*     */     
/*     */     public SIOLink(int sio) {
/* 313 */       this.sio = sio;
/*     */     }
/*     */     
/*     */     public void setSIO(int sio) {
/* 317 */       this.sio = sio;
/*     */     }
/*     */     
/*     */     public void setSIO(String sio) {
/* 321 */       this.sio = Integer.parseInt(sio);
/*     */     }
/*     */     
/*     */     public void setQualifier(String qualifier) {
/* 325 */       this.qualifier = qualifier;
/*     */     }
/*     */     
/*     */     public void setBookmark(String bm) {
/* 329 */       this.bm = bm;
/*     */     }
/*     */     
/*     */     public int getSIO() {
/* 333 */       return this.sio;
/*     */     }
/*     */     
/*     */     public String getBookmark() {
/* 337 */       return this.bm;
/*     */     }
/*     */     
/*     */     public String getQualifier() {
/* 341 */       return this.qualifier;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GraphicReplacer extends KeyReplacer {
/*     */     public GraphicReplacer() {
/* 347 */       super(9, "LINK_GRAPHIC:ID:");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CommonPrefixHandler extends TemplateReplacer {
/* 352 */     protected List replacers = new LinkedList();
/* 353 */     protected Replacer.TemplateReplacer handler = null;
/*     */     
/*     */     public Replacer.TemplateReplacer getHandler() {
/* 356 */       return this.handler;
/*     */     }
/*     */     
/*     */     public CommonPrefixHandler(Replacer.TemplateReplacer a, Replacer.TemplateReplacer b) {
/* 360 */       String patternA = a.getPattern();
/* 361 */       String patternB = b.getPattern();
/* 362 */       this.pattern = computeCommonPrefix(patternA, patternB);
/* 363 */       this.replacers.add(a);
/* 364 */       this.replacers.add(b);
/*     */     }
/*     */     
/*     */     public void add(Replacer.TemplateReplacer replacer) {
/* 368 */       this.pattern = computeCommonPrefix(getPattern(), replacer.getPattern());
/* 369 */       this.replacers.add(replacer);
/*     */     }
/*     */     
/*     */     protected String computeCommonPrefix(String patternA, String patternB) {
/* 373 */       StringBuffer prefix = new StringBuffer(patternA.length());
/* 374 */       for (int i = 0; i < patternA.length(); i++) {
/* 375 */         char c = patternA.charAt(i);
/* 376 */         if (c == patternB.charAt(i)) {
/* 377 */           prefix.append(c);
/*     */         } else {
/* 379 */           return prefix.toString();
/*     */         } 
/*     */       } 
/* 382 */       return patternA;
/*     */     }
/*     */     
/*     */     public boolean match(String html, int offset) {
/* 386 */       return match(html, offset, 0);
/*     */     }
/*     */     
/*     */     protected boolean match(String html, int offset, int skip) {
/* 390 */       skip += this.pattern.length();
/* 391 */       for (int i = 0; i < this.replacers.size(); i++) {
/* 392 */         Replacer.TemplateReplacer replacer = this.replacers.get(i);
/* 393 */         if (replacer.match(html, offset, skip)) {
/* 394 */           this.handler = replacer;
/* 395 */           return true;
/*     */         } 
/*     */       } 
/* 398 */       return false;
/*     */     }
/*     */     
/*     */     public void parse(String html, int offset) {
/* 402 */       this.handler.parse(html, offset);
/*     */     }
/*     */   }
/*     */   
/* 406 */   protected TemplateReplacer[] replacers = new TemplateReplacer[255];
/*     */   protected ReplacerCallback callback;
/*     */   
/*     */   public Replacer(ReplacerCallback callback) {
/* 410 */     this.callback = callback;
/* 411 */     add(new SIODocumentLinkReplacer());
/* 412 */     add(new ImageReplacer());
/* 413 */     add(new ImageFileReplacer());
/* 414 */     add(new StyleSheetReplacer());
/* 415 */     add(new DetailViewReplacer());
/* 416 */     add(new AWLinkReplacer());
/* 417 */     add(new SIOCellLinkReplacer());
/* 418 */     add(new SIOLinkListReplacer());
/* 419 */     add(new GraphicReplacer());
/* 420 */     add(new FaultFinderReplacer());
/* 421 */     add(new ComponentLinkReplacer());
/*     */   }
/*     */   
/*     */   public void add(TemplateReplacer handler) {
/* 425 */     String pattern = handler.getPattern();
/* 426 */     TemplateReplacer replacer = this.replacers[pattern.charAt(0)];
/* 427 */     if (replacer == null) {
/* 428 */       this.replacers[pattern.charAt(0)] = handler;
/* 429 */     } else if (replacer instanceof CommonPrefixHandler) {
/* 430 */       ((CommonPrefixHandler)replacer).add(handler);
/*     */     } else {
/* 432 */       this.replacers[pattern.charAt(0)] = new CommonPrefixHandler(replacer, handler);
/*     */     } 
/*     */   }
/*     */   
/*     */   public StringBuffer apply(String html) {
/* 437 */     StringBuffer result = new StringBuffer((int)(html.length() * 1.1D));
/* 438 */     int pos = 0;
/* 439 */     for (int i = 0; i < html.length(); i++) {
/* 440 */       char c = html.charAt(i);
/* 441 */       if (c == '{') {
/*     */ 
/*     */         
/* 444 */         c = html.charAt(++i);
/* 445 */         TemplateReplacer replacer = this.replacers[c];
/* 446 */         if (replacer != null && 
/* 447 */           replacer.match(html, i)) {
/* 448 */           result.append(html.substring(pos, i - 1));
/* 449 */           replacer.parse(html, i);
/* 450 */           String replacement = this.callback.replace(resolve(replacer));
/* 451 */           if (replacement != null) {
/* 452 */             result.append(replacement);
/*     */           }
/* 454 */           for (i += replacer.getPattern().length(); i < html.length() && 
/* 455 */             html.charAt(i) != '}'; i++);
/*     */ 
/*     */ 
/*     */           
/* 459 */           pos = i + 1;
/*     */         } 
/*     */       } 
/*     */     } 
/* 463 */     if (pos < html.length()) {
/* 464 */       result.append(html.substring(pos));
/*     */     }
/* 466 */     return result;
/*     */   }
/*     */   
/*     */   protected TemplateReplacer resolve(TemplateReplacer replacer) {
/* 470 */     while (replacer instanceof CommonPrefixHandler) {
/* 471 */       replacer = ((CommonPrefixHandler)replacer).getHandler();
/*     */     }
/* 473 */     return replacer;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\replacer\Replacer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */