/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.logfiles.ui.html.main;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.SimpleConfirmationMessageBox;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlElementStack;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.PagedElement;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class AdminPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(AdminPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  38 */       template = ApplicationContext.getInstance().loadFile(AdminPanel.class, "adminpanel.html", null).toString();
/*  39 */     } catch (Exception e) {
/*  40 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  46 */   private List entries = null;
/*     */   
/*  48 */   private FileListElement listElement = null;
/*     */   
/*  50 */   private HtmlElement listElementPagedFront = null;
/*     */   
/*  52 */   private static final Object ACTION_DELETE = new Object();
/*     */   
/*  54 */   private static final Object ACTION_EXPORT = new Object();
/*     */   
/*  56 */   private SelectBoxSelectionElement selectionAction = null;
/*     */   
/*  58 */   private ClickButtonElement buttonExecuteAction = null;
/*     */   
/*  60 */   private ClickButtonElement buttonMarkAll = null;
/*     */   
/*  62 */   private ClickButtonElement buttonReset = null;
/*     */   
/*  64 */   private ClickButtonElement buttonInvert = null;
/*     */   
/*     */   private File rootDir;
/*     */   
/*     */   private MainPanel mainPanel;
/*     */ 
/*     */   
/*     */   public AdminPanel(final ClientContext context, Collection<?> files, File rootDir, MainPanel panel) {
/*  72 */     this.context = context;
/*  73 */     this.mainPanel = panel;
/*  74 */     this.rootDir = rootDir;
/*  75 */     this.entries = new ArrayList(files);
/*  76 */     this.listElement = new FileListElement(this.entries, rootDir, context);
/*  77 */     this.listElementPagedFront = (HtmlElement)new PagedElement(context.createID(), (HtmlElement)this.listElement, 20, 20);
/*  78 */     addElement(this.listElementPagedFront);
/*     */     
/*  80 */     this.selectionAction = new SelectBoxSelectionElement(context.createID(), true, new DataRetrievalAbstraction.DataCallback()
/*     */         {
/*  82 */           private List data = Arrays.asList(new Object[] { AdminPanel.access$000(), AdminPanel.access$100() }, );
/*     */           
/*     */           public List getData() {
/*  85 */             return this.data;
/*     */           }
/*     */         },  1)
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/*  90 */           if (option == AdminPanel.ACTION_DELETE)
/*  91 */             return context.getLabel("delete"); 
/*  92 */           if (option == AdminPanel.ACTION_EXPORT) {
/*  93 */             return context.getLabel("export");
/*     */           }
/*  95 */           return "-";
/*     */         }
/*     */       };
/*     */     
/*  99 */     addElement((HtmlElement)this.selectionAction);
/* 100 */     this.selectionAction.setValue(ACTION_EXPORT);
/*     */     
/* 102 */     this.buttonExecuteAction = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 105 */           return context.getLabel("execute");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 110 */             return AdminPanel.this.executeSelectedAction();
/* 111 */           } catch (Exception e) {
/* 112 */             AdminPanel.log.error("...unable to execute action - exception: " + e, e);
/* 113 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 118 */     addElement((HtmlElement)this.buttonExecuteAction);
/*     */     
/* 120 */     this.buttonMarkAll = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 122 */           return context.getLabel("mark.all");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 127 */             AdminPanel.this.listElement.markAll();
/* 128 */           } catch (Exception e) {
/* 129 */             AdminPanel.log.error("...unable to mark all entries - exception:" + e, e);
/*     */           } 
/* 131 */           return null;
/*     */         }
/*     */       };
/* 134 */     addElement((HtmlElement)this.buttonMarkAll);
/*     */     
/* 136 */     this.buttonReset = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 139 */           return context.getLabel("reset.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 144 */             AdminPanel.this.listElement.unmarkAll();
/* 145 */           } catch (Exception e) {
/* 146 */             AdminPanel.log.error("...unable to unmark all entries - exception:" + e, e);
/*     */           } 
/* 148 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 152 */     addElement((HtmlElement)this.buttonReset);
/*     */     
/* 154 */     this.buttonInvert = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 156 */           return context.getLabel("invert.marking");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 161 */             AdminPanel.this.listElement.invertMarking();
/* 162 */           } catch (Exception e) {
/* 163 */             AdminPanel.log.error("...unable to invert marking - exception:" + e, e);
/*     */           } 
/* 165 */           return null;
/*     */         }
/*     */       };
/* 168 */     addElement((HtmlElement)this.buttonInvert);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object executeSelectedAction() throws Exception {
/* 173 */     Collection entries = this.listElement.getMarkedEntries();
/* 174 */     if (entries != null && entries.size() > 0) {
/* 175 */       if (this.selectionAction.getValue() == ACTION_DELETE)
/* 176 */         return executeDeletion(entries); 
/* 177 */       if (this.selectionAction.getValue() == ACTION_EXPORT) {
/* 178 */         return executeExport(entries);
/*     */       }
/*     */     } 
/*     */     
/* 182 */     return null;
/*     */   }
/*     */   
/*     */   private Object executeDeletion(final Collection entries) throws Exception {
/* 186 */     return new SimpleConfirmationMessageBox(this.context, this.context.getLabel("confirmation"), this.context.getMessage("confirmation.deletion"))
/*     */       {
/*     */         protected Object onCancel(Map params) {
/* 189 */           return AdminPanel.this.getTopLevelContainer();
/*     */         }
/*     */         
/*     */         protected Object onOK(Map params) {
/*     */           try {
/* 194 */             AdminPanel.log.info("deleting marked files (" + entries.size() + ")");
/* 195 */             for (Iterator<File> iter = entries.iterator(); iter.hasNext(); ) {
/* 196 */               File file = iter.next();
/*     */               try {
/* 198 */                 if (!file.delete()) {
/* 199 */                   AdminPanel.log.warn("unable to delete file :" + String.valueOf(file));
/*     */                 }
/* 201 */               } catch (Exception e) {
/* 202 */                 AdminPanel.log.warn("unable to delete file :" + String.valueOf(file) + " -exception:" + e + ", skipping");
/*     */               }
/*     */             
/*     */             } 
/* 206 */           } catch (Exception e) {
/* 207 */             AdminPanel.log.error("....unable to delete entries - exception:" + e, e);
/*     */           } 
/* 209 */           AdminPanel.this.mainPanel.update();
/* 210 */           return AdminPanel.this.mainPanel.getTopLevelContainer();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] createExport(Collection entries) throws Exception {
/* 219 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     
/* 221 */     ZipOutputStream zos = new ZipOutputStream(baos);
/*     */     try {
/* 223 */       zos.setLevel(9);
/*     */       
/* 225 */       for (Iterator<File> iter = entries.iterator(); iter.hasNext(); ) {
/* 226 */         File file = iter.next();
/* 227 */         FileInputStream fis = new FileInputStream(file);
/* 228 */         ZipEntry entry = new ZipEntry(getFilename(file));
/* 229 */         zos.putNextEntry(entry);
/* 230 */         StreamUtil.transfer(fis, zos);
/* 231 */         fis.close();
/*     */       } 
/*     */     } finally {
/* 234 */       zos.close();
/*     */     } 
/*     */     
/* 237 */     return baos.toByteArray();
/*     */   }
/*     */   
/*     */   private Object executeExport(Collection entries) throws Exception {
/* 241 */     Object retValue = null;
/* 242 */     if (entries != null && entries.size() > 0) {
/*     */       try {
/* 244 */         log.info("exporting marked files (" + entries.size() + ")");
/* 245 */         byte[] data = createExport(entries);
/* 246 */         ResultObject.FileProperties props = new ResultObject.FileProperties();
/* 247 */         props.filename = "log-files.zip";
/* 248 */         props.mime = "application/zip";
/* 249 */         props.data = data;
/* 250 */         retValue = new ResultObject(13, false, true, props);
/* 251 */       } catch (Exception e) {
/* 252 */         log.error("unable to export entries - exception:" + e, e);
/*     */       } 
/*     */     }
/* 255 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getFilename(File file) {
/* 260 */     String retValue = this.rootDir.toURI().relativize(file.toURI()).toString();
/* 261 */     return StringUtilities.replace(retValue, "%20", " ");
/*     */   }
/*     */   
/*     */   public HtmlElementStack getPanelStack() {
/* 265 */     HtmlElementContainer container = getContainer();
/* 266 */     while (!(container instanceof HtmlElementStack) && container != null) {
/* 267 */       container = container.getContainer();
/*     */     }
/* 269 */     return (HtmlElementStack)container;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 273 */     StringBuffer retvalue = new StringBuffer(template);
/* 274 */     if (this.listElementPagedFront != null) {
/* 275 */       StringUtilities.replace(retvalue, "{LIST}", this.listElementPagedFront.getHtmlCode(params));
/* 276 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", this.buttonMarkAll.getHtmlCode(params));
/* 277 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", this.buttonInvert.getHtmlCode(params));
/* 278 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/*     */     } else {
/*     */       
/* 281 */       StringUtilities.replace(retvalue, "{LIST}", this.context.getMessage("no.entries"));
/* 282 */       StringUtilities.replace(retvalue, "{BUTTON_MARK_ALL}", "");
/* 283 */       StringUtilities.replace(retvalue, "{BUTTON_INVERT}", "");
/* 284 */       StringUtilities.replace(retvalue, "{BUTTON_RESET}", "");
/*     */     } 
/*     */     
/* 287 */     if (this.selectionAction != null) {
/* 288 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", this.selectionAction.getHtmlCode(params));
/* 289 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", this.buttonExecuteAction.getHtmlCode(params));
/*     */     } else {
/* 291 */       StringUtilities.replace(retvalue, "{INPUT_ACTION}", "");
/* 292 */       StringUtilities.replace(retvalue, "{BUTTON_ACTION}", "");
/*     */     } 
/* 294 */     return retvalue.toString();
/*     */   }
/*     */   
/*     */   public Collection getMarkedFiles() {
/* 298 */     return this.listElement.getMarkedEntries();
/*     */   }
/*     */   
/*     */   public void setMarkedFiles(Collection files) {
/* 302 */     this.listElement.setMarkedEntries(files);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\logfile\\ui\html\main\AdminPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */