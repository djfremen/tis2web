/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.searchlog.ui.html.search;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*     */ import com.eoos.gm.tis2web.frame.implementation.admin.searchlog.GetFileContentTask;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.Task;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class FileListElement extends ListElement {
/*  22 */   private static final Logger log = Logger.getLogger(FileListElement.class);
/*     */   
/*     */   private static final int INDEX_SERVER = 0;
/*     */   
/*     */   private static final int INDEX_FILE = 1;
/*     */   
/*     */   private static final int INDEX_DETAILS = 2;
/*     */   
/*     */   private HtmlElement headerServer;
/*     */   
/*     */   private HtmlElement headerFile;
/*     */   
/*     */   private HtmlElement headerDetail;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  38 */   private Map entryToGetContentButton = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */ 
/*     */   
/*     */   public FileListElement(final List entries, ClientContext context) {
/*  42 */     if (entries == null || entries.size() == 0) {
/*  43 */       throw new IllegalArgumentException();
/*     */     }
/*  45 */     this.context = context;
/*  46 */     setDataCallback(new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*     */           public List getData() {
/*  49 */             return entries;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  54 */     this.headerServer = (HtmlElement)new HtmlLabel(context.getLabel("server"));
/*     */     
/*  56 */     this.headerFile = (HtmlElement)new HtmlLabel(context.getLabel("file"));
/*     */     
/*  58 */     this.headerDetail = (HtmlElement)new HtmlLabel("&nbsp;");
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/*  62 */     return 3;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/*  66 */     switch (columnIndex) {
/*     */       case 0:
/*  68 */         return this.headerServer;
/*     */       case 1:
/*  70 */         return this.headerFile;
/*     */       case 2:
/*  72 */         return this.headerDetail;
/*     */     } 
/*  74 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   private ClickButtonElement getContentButton(final RemoteFile file) {
/*  79 */     ClickButtonElement button = (ClickButtonElement)this.entryToGetContentButton.get(file);
/*  80 */     if (button == null) {
/*  81 */       button = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/*  84 */             return FileListElement.this.context.getLabel("content");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/*  89 */               GetFileContentTask task = new GetFileContentTask(file.getFile());
/*  90 */               ClusterTaskExecution ctask = new ClusterTaskExecution((Task)task, FileListElement.this.context) {
/*     */                   protected Collection getClusterURLs() {
/*  92 */                     return Collections.singleton(file.getURL());
/*     */                   }
/*     */                 };
/*  95 */               ClusterTaskExecution.Result result = ctask.execute();
/*     */               
/*  97 */               byte[] data = (byte[])CollectionUtil.getFirst(result.getResults());
/*     */               
/*  99 */               ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 100 */               props.data = data;
/* 101 */               props.filename = file.getFile().getName();
/* 102 */               props.mime = "text/plain;charset=utf-8";
/* 103 */               props.inline = false;
/* 104 */               return new ResultObject(13, false, false, props);
/* 105 */             } catch (Exception e) {
/* 106 */               FileListElement.log.error("unable to deliver file content - exception: " + e, e);
/* 107 */               return null;
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 112 */       this.entryToGetContentButton.put(file, button);
/*     */     } 
/* 114 */     return button;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 118 */     RemoteFile file = (RemoteFile)data;
/* 119 */     switch (columnIndex) {
/*     */       case 0:
/* 121 */         return (HtmlElement)new HtmlLabel(file.getURL().getHost());
/*     */       case 1:
/* 123 */         return (HtmlElement)new HtmlLabel(file.getFile().getPath());
/*     */       case 2:
/* 125 */         return (HtmlElement)getContentButton(file);
/*     */     } 
/* 127 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 131 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 133 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 135 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 137 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>();
/*     */   
/* 139 */   private static final Map ATTRIBUTES_MARKER_CELL = new HashMap<Object, Object>();
/*     */   
/* 141 */   private static final Map ATTRIBUTES_DETAIL_CELL = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/* 144 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 145 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 147 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 148 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 150 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */     
/* 152 */     ATTRIBUTES_MARKER_CELL.put("width", "3%");
/*     */     
/* 154 */     ATTRIBUTES_DETAIL_CELL.put("align", "center");
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {
/* 158 */     if (columnIndex == 2) {
/* 159 */       map.putAll(ATTRIBUTES_DETAIL_CELL);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 164 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 168 */     if (rowIndex % 2 == 0) {
/* 169 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 171 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 176 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\searchlo\\ui\html\search\FileListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */