/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.logfiles.ui.html.main;
/*     */ 
/*     */ import com.eoos.file.FileUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.CheckBoxElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.util.DateConvert;
/*     */ import com.eoos.util.v2.ByteUtil;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.LineNumberReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class FileListElement
/*     */   extends ListElement {
/*  41 */   private static final Logger log = Logger.getLogger(FileListElement.class);
/*     */   
/*     */   private static final int HEAD_TAIL_LINES = 200;
/*     */   
/*  45 */   private static final Charset CHARSET = Charset.forName("UTF-8");
/*     */   
/*     */   private static final int INDEX_MARKER = 0;
/*     */   
/*     */   private static final int INDEX_FILENAME = 1;
/*     */   
/*     */   private static final int INDEX_SIZE = 2;
/*     */   
/*     */   private static final int INDEX_DATE = 3;
/*     */   
/*     */   private static final int INDEX_DIRECTACTION = 4;
/*     */   
/*     */   private HtmlElement headerActionMarker;
/*     */   
/*     */   private LinkElement headerName;
/*     */   
/*     */   private LinkElement headerSize;
/*     */   
/*     */   private LinkElement headerDate;
/*     */   
/*     */   private HtmlElement headerDirectAction;
/*     */   
/*  67 */   private Comparator comparator = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  71 */   private Map fileToActionMarker = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  73 */   private Map fileToViewButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  75 */   private Map fileToTailButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  77 */   private Map fileToHeadButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  79 */   private Map fileToButtonContainer = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   private List files;
/*     */   
/*     */   private File rootDir;
/*     */   
/*  85 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */ 
/*     */   
/*     */   public FileListElement(final List files, File rootDir, final ClientContext context) {
/*  89 */     this.context = context;
/*  90 */     this.rootDir = rootDir;
/*  91 */     this.files = files;
/*  92 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  95 */             return files;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 100 */     this.headerActionMarker = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */     
/* 102 */     this.headerDirectAction = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */     
/* 104 */     this.headerDate = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 108 */             if (FileUtil.COMPARATOR_DATE.equals(FileListElement.this.comparator)) {
/* 109 */               FileListElement.this.comparator = Util.reverseComparator(FileListElement.this.comparator);
/*     */             } else {
/* 111 */               FileListElement.this.comparator = FileUtil.COMPARATOR_DATE;
/*     */             } 
/* 113 */             Collections.sort(files, FileListElement.this.comparator);
/* 114 */           } catch (Exception e) {
/* 115 */             FileListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 117 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 122 */           return context.getLabel("date");
/*     */         }
/*     */       };
/*     */     
/* 126 */     addElement((HtmlElement)this.headerDate);
/*     */     
/* 128 */     final FileUtil.ComparatorBase comparatorName = new FileUtil.ComparatorBase()
/*     */       {
/*     */         protected int compareFiles(File file1, File file2) throws Exception {
/* 131 */           return FileListElement.this.getFilename(file1).compareTo(FileListElement.this.getFilename(file2));
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 136 */     this.headerName = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 140 */             if (comparatorName.equals(FileListElement.this.comparator)) {
/* 141 */               FileListElement.this.comparator = Util.reverseComparator(FileListElement.this.comparator);
/*     */             } else {
/* 143 */               FileListElement.this.comparator = comparatorName;
/*     */             } 
/* 145 */             Collections.sort(files, FileListElement.this.comparator);
/* 146 */           } catch (Exception e) {
/* 147 */             FileListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 149 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 154 */           return context.getLabel("name");
/*     */         }
/*     */       };
/*     */     
/* 158 */     addElement((HtmlElement)this.headerName);
/*     */     
/* 160 */     this.headerSize = new LinkElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 164 */             if (FileUtil.COMPARATOR_SIZE.equals(FileListElement.this.comparator)) {
/* 165 */               FileListElement.this.comparator = Util.reverseComparator(FileListElement.this.comparator);
/*     */             } else {
/* 167 */               FileListElement.this.comparator = FileUtil.COMPARATOR_SIZE;
/*     */             } 
/* 169 */             Collections.sort(files, FileListElement.this.comparator);
/* 170 */           } catch (Exception e) {
/* 171 */             FileListElement.log.error("...unable to sort by date - exception: " + e, e);
/*     */           } 
/* 173 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 178 */           return context.getLabel("size") + " (KByte)";
/*     */         }
/*     */       };
/*     */     
/* 182 */     addElement((HtmlElement)this.headerSize);
/*     */     
/* 184 */     this.comparator = Util.reverseComparator(FileUtil.COMPARATOR_DATE);
/* 185 */     Collections.sort(this.files, this.comparator);
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/* 189 */     return 5;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 193 */     switch (columnIndex) {
/*     */       case 0:
/* 195 */         return this.headerActionMarker;
/*     */       case 3:
/* 197 */         return (HtmlElement)this.headerDate;
/*     */       case 1:
/* 199 */         return (HtmlElement)this.headerName;
/*     */       case 4:
/* 201 */         return this.headerDirectAction;
/*     */       case 2:
/* 203 */         return (HtmlElement)this.headerSize;
/*     */     } 
/* 205 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private CheckBoxElement getActionMarker(File file) {
/* 210 */     CheckBoxElement actionMarker = (CheckBoxElement)this.fileToActionMarker.get(file);
/* 211 */     if (actionMarker == null) {
/* 212 */       actionMarker = new CheckBoxElement(this.context.createID());
/* 213 */       this.fileToActionMarker.put(file, actionMarker);
/*     */     } 
/* 215 */     return actionMarker;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getDetailButton(final File file) {
/* 219 */     ClickButtonElement button = (ClickButtonElement)this.fileToViewButton.get(file);
/* 220 */     if (button == null) {
/* 221 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 224 */             return FileListElement.this.context.getLabel("view");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 229 */               FileInputStream fis = new FileInputStream(file);
/* 230 */               byte[] data = StreamUtil.readFully(fis);
/* 231 */               fis.close();
/*     */               
/* 233 */               ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 234 */               props.data = data;
/* 235 */               props.filename = file.getName();
/* 236 */               props.mime = "text/plain";
/* 237 */               props.inline = true;
/* 238 */               return new ResultObject(13, false, false, props);
/* 239 */             } catch (Exception e) {
/* 240 */               FileListElement.log.error("unable to view file - exception: " + e, e);
/* 241 */               return null;
/*     */             } 
/*     */           }
/*     */           
/*     */           protected String getTargetFrame() {
/* 246 */             return "admin.log.files.view";
/*     */           }
/*     */         };
/*     */       
/* 250 */       this.fileToViewButton.put(file, button);
/*     */     } 
/* 252 */     return button;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getTailButton(final File file) {
/* 256 */     ClickButtonElement button = (ClickButtonElement)this.fileToTailButton.get(file);
/* 257 */     if (button == null) {
/* 258 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 261 */             return FileListElement.this.context.getLabel("tail");
/*     */           }
/*     */ 
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 267 */               ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 268 */               OutputStreamWriter osw = new OutputStreamWriter(baos, FileListElement.CHARSET);
/*     */               
/* 270 */               LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
/* 271 */               String line = null;
/* 272 */               while ((line = lnr.readLine()) != null);
/*     */               
/* 274 */               int totalLines = lnr.getLineNumber();
/* 275 */               int position = Math.max(0, totalLines - 200);
/* 276 */               lnr.close();
/* 277 */               lnr = new LineNumberReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
/*     */               
/* 279 */               while (lnr.getLineNumber() < position) {
/* 280 */                 lnr.readLine();
/*     */               }
/*     */               
/* 283 */               while ((line = lnr.readLine()) != null) {
/* 284 */                 osw.write(line + '\n');
/*     */               }
/* 286 */               lnr.close();
/* 287 */               osw.close();
/*     */               
/* 289 */               ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 290 */               props.data = baos.toByteArray();
/* 291 */               props.filename = "tail-" + file.getName();
/* 292 */               props.mime = "text/plain;charset=" + FileListElement.CHARSET.name();
/* 293 */               props.inline = true;
/* 294 */               return new ResultObject(13, false, false, props);
/* 295 */             } catch (Exception e) {
/* 296 */               FileListElement.log.error("unable to view file - exception: " + e, e);
/* 297 */               return null;
/*     */             } 
/*     */           }
/*     */           
/*     */           protected String getTargetFrame() {
/* 302 */             return "admin.log.files.view";
/*     */           }
/*     */         };
/*     */       
/* 306 */       this.fileToTailButton.put(file, button);
/*     */     } 
/* 308 */     return button;
/*     */   }
/*     */   
/*     */   private ClickButtonElement getHeadButton(final File file) {
/* 312 */     ClickButtonElement button = (ClickButtonElement)this.fileToHeadButton.get(file);
/* 313 */     if (button == null) {
/* 314 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 317 */             return FileListElement.this.context.getLabel("head");
/*     */           }
/*     */ 
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 323 */               ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 324 */               OutputStreamWriter osw = new OutputStreamWriter(baos, FileListElement.CHARSET);
/*     */               
/* 326 */               FileInputStream fis = new FileInputStream(file);
/* 327 */               LineNumberReader lnr = new LineNumberReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
/* 328 */               String line = null;
/* 329 */               while ((line = lnr.readLine()) != null && lnr.getLineNumber() < 200) {
/* 330 */                 osw.write(line + '\n');
/*     */               }
/* 332 */               lnr.close();
/* 333 */               osw.close();
/*     */               
/* 335 */               ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 336 */               props.data = baos.toByteArray();
/* 337 */               props.filename = "head-" + file.getName();
/* 338 */               props.mime = "text/plain;charset=" + FileListElement.CHARSET.name();
/* 339 */               props.inline = true;
/* 340 */               return new ResultObject(13, false, false, props);
/* 341 */             } catch (Exception e) {
/* 342 */               FileListElement.log.error("unable to view file - exception: " + e, e);
/* 343 */               return null;
/*     */             } 
/*     */           }
/*     */           
/*     */           protected String getTargetFrame() {
/* 348 */             return "admin.log.files.view";
/*     */           }
/*     */         };
/*     */       
/* 352 */       this.fileToHeadButton.put(file, button);
/*     */     } 
/* 354 */     return button;
/*     */   }
/*     */   private HtmlElementContainer getButtonContainer(File file) {
/*     */     HtmlElementContainerBase htmlElementContainerBase;
/* 358 */     HtmlElementContainer buttonContainer = (HtmlElementContainer)this.fileToButtonContainer.get(file);
/* 359 */     if (buttonContainer == null) {
/* 360 */       final ClickButtonElement buttonView = getDetailButton(file);
/* 361 */       final ClickButtonElement buttonTail = getTailButton(file);
/* 362 */       final ClickButtonElement buttonHead = getHeadButton(file);
/*     */       
/* 364 */       htmlElementContainerBase = new HtmlElementContainerBase()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public String getHtmlCode(Map params)
/*     */           {
/* 372 */             StringBuffer tmp = new StringBuffer();
/* 373 */             tmp.append("<table cellpadding=\"0\" cellspacing=\"1\"><tr>");
/* 374 */             tmp.append("<td>");
/* 375 */             tmp.append(buttonView.getHtmlCode(params));
/* 376 */             tmp.append("</td>");
/* 377 */             tmp.append("<td>");
/* 378 */             tmp.append(buttonHead.getHtmlCode(params));
/* 379 */             tmp.append("</td>");
/* 380 */             tmp.append("<td>");
/* 381 */             tmp.append(buttonTail.getHtmlCode(params));
/* 382 */             tmp.append("</td>");
/* 383 */             tmp.append("</tr></table>");
/* 384 */             return tmp.toString();
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 389 */       this.fileToButtonContainer.put(file, htmlElementContainerBase);
/*     */     } 
/* 391 */     return (HtmlElementContainer)htmlElementContainerBase;
/*     */   }
/*     */   
/*     */   public Collection getMarkedEntries() {
/* 395 */     List<?> retValue = new LinkedList();
/* 396 */     for (Iterator<Map.Entry> iter = this.fileToActionMarker.entrySet().iterator(); iter.hasNext(); ) {
/* 397 */       Map.Entry entry = iter.next();
/* 398 */       CheckBoxElement actionMarker = (CheckBoxElement)entry.getValue();
/* 399 */       if (actionMarker.getValue() == Boolean.TRUE) {
/* 400 */         retValue.add(entry.getKey());
/*     */       }
/*     */     } 
/* 403 */     Collections.sort(retValue, this.comparator);
/* 404 */     return retValue;
/*     */   }
/*     */   
/*     */   public void setMarkedEntries(Collection files) {
/* 408 */     if (files != null) {
/* 409 */       for (Iterator<File> iter = files.iterator(); iter.hasNext(); ) {
/* 410 */         File file = iter.next();
/* 411 */         if (this.files.contains(file)) {
/* 412 */           getActionMarker(file).setValue(Boolean.TRUE);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 419 */     File file = (File)data;
/* 420 */     switch (columnIndex) {
/*     */       case 0:
/* 422 */         return (HtmlElement)getActionMarker(file);
/*     */       
/*     */       case 3:
/* 425 */         return (HtmlElement)new HtmlLabel(DateConvert.toDateString(file.lastModified(), DATE_FORMAT));
/*     */       case 1:
/* 427 */         return (HtmlElement)new HtmlLabel(getFilename(file));
/*     */       case 2:
/* 429 */         return (HtmlElement)new HtmlLabel(String.valueOf(ByteUtil.getAsKiloBytes(file.length())));
/*     */       case 4:
/* 431 */         return (HtmlElement)getButtonContainer(file);
/*     */     } 
/* 433 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 437 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 439 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 441 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 443 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 445 */   private static final Map ATTRIBUTES_MARKER_CELL = new HashMap<Object, Object>();
/*     */   
/* 447 */   private static final Map ATTRIBUTES_DETAIL_CELL = new HashMap<Object, Object>();
/*     */   
/* 449 */   private static final Map ATTRIBUTES_SIZE_CELL = new HashMap<Object, Object>();
/*     */   
/* 451 */   private static final Map ATTRIBUTES_DATE_CELL = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 454 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 455 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 457 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 458 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 460 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */     
/* 462 */     ATTRIBUTES_MARKER_CELL.put("width", "3%");
/* 463 */     ATTRIBUTES_DETAIL_CELL.put("align", "center");
/* 464 */     ATTRIBUTES_SIZE_CELL.put("align", "right");
/* 465 */     ATTRIBUTES_DATE_CELL.put("align", "center");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 470 */     if (columnIndex == 0) {
/* 471 */       map.putAll(ATTRIBUTES_MARKER_CELL);
/* 472 */     } else if (columnIndex == 4) {
/* 473 */       map.putAll(ATTRIBUTES_DETAIL_CELL);
/* 474 */     } else if (columnIndex == 2) {
/* 475 */       map.putAll(ATTRIBUTES_SIZE_CELL);
/* 476 */     } else if (columnIndex == 3) {
/* 477 */       map.putAll(ATTRIBUTES_DATE_CELL);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 482 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 486 */     if (rowIndex % 2 == 0) {
/* 487 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 489 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 494 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   public void markAll() {
/* 498 */     for (Iterator<File> iter = this.files.iterator(); iter.hasNext(); ) {
/* 499 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 500 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unmarkAll() {
/* 505 */     for (Iterator<File> iter = this.files.iterator(); iter.hasNext(); ) {
/* 506 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 507 */       cb.setValue(Boolean.FALSE);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invertMarking() {
/* 512 */     for (Iterator<File> iter = this.files.iterator(); iter.hasNext(); ) {
/* 513 */       CheckBoxElement cb = getActionMarker(iter.next());
/* 514 */       if (cb.getValue() == Boolean.TRUE) {
/* 515 */         cb.setValue(Boolean.FALSE); continue;
/*     */       } 
/* 517 */       cb.setValue(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getFilename(File file) {
/* 523 */     String retValue = this.rootDir.toURI().relativize(file.toURI()).toString();
/* 524 */     return StringUtilities.replace(retValue, "%20", " ");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\logfile\\ui\html\main\FileListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */