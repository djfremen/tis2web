/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.logfiles.ui.html.main;
/*     */ 
/*     */ import com.eoos.automat.Acceptor;
/*     */ import com.eoos.automat.StringAcceptor;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MainPanel
/*     */   extends HtmlElementContainerBase {
/*  21 */   private static final Logger log = Logger.getLogger(MainPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  26 */       template = ApplicationContext.getInstance().loadFile(MainPanel.class, "mainpanel.html", null).toString();
/*  27 */     } catch (Exception e) {
/*  28 */       log.error("error loading template - error:" + e, e);
/*  29 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final boolean INCLUDE_SUBDIRS = true;
/*     */   
/*     */   private ClientContext context;
/*  37 */   private final Object SYNC = new Object();
/*     */   
/*     */   private AdminPanel adminPanel;
/*     */   
/*     */   private ClickButtonElement buttonUpdate;
/*     */   
/*     */   private TextInputElement inputFilter;
/*     */   
/*     */   private ClickButtonElement applyFilter;
/*     */   
/*  47 */   private Acceptor filenameAcceptor = null;
/*     */ 
/*     */   
/*     */   private MainPanel(final ClientContext context) {
/*  51 */     this.context = context;
/*  52 */     this.buttonUpdate = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  55 */           return context.getLabel("update");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  59 */           MainPanel.this.update();
/*  60 */           return null;
/*     */         }
/*     */       };
/*     */     
/*  64 */     addElement((HtmlElement)this.buttonUpdate);
/*  65 */     this.inputFilter = new TextInputElement(context.createID(), 20, -1);
/*  66 */     addElement((HtmlElement)this.inputFilter);
/*     */     
/*  68 */     this.applyFilter = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  71 */           return context.getLabel("apply");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  76 */             MainPanel.this.applyFilter();
/*  77 */           } catch (Exception e) {
/*  78 */             MainPanel.log.error("unable to apply filter, ignoring - exception:" + e, e);
/*     */           } 
/*  80 */           return null;
/*     */         }
/*     */       };
/*     */     
/*  84 */     addElement((HtmlElement)this.applyFilter);
/*  85 */     init();
/*     */   }
/*     */   
/*     */   public void update() {
/*     */     try {
/*  90 */       init();
/*  91 */     } catch (Exception e) {
/*  92 */       log.error("unable to update - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void collectFiles(File rootDir, File dir, Set<File> collectedFiles) {
/*  97 */     if (!dir.isDirectory()) {
/*     */       return;
/*     */     }
/* 100 */     File[] files = dir.listFiles();
/* 101 */     if (files != null) {
/* 102 */       for (int i = 0; i < files.length; i++) {
/* 103 */         log.debug("...investigating file: " + String.valueOf(files[i]));
/* 104 */         if (files[i].isFile()) {
/* 105 */           if (this.filenameAcceptor == null || this.filenameAcceptor.accept(getFilename(rootDir, files[i]))) {
/* 106 */             collectedFiles.add(files[i]);
/*     */           }
/*     */         } else {
/* 109 */           collectFiles(rootDir, files[i], collectedFiles);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 113 */       log.warn("unable to list directory content for dir: " + String.valueOf(dir) + " (listFiles() returned null), IGNORING DIRECTORY!!!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getFilename(File rootDir, File file) {
/* 119 */     String retValue = rootDir.toURI().relativize(file.toURI()).toString();
/* 120 */     return StringUtilities.replace(retValue, "%20", " ");
/*     */   }
/*     */   
/*     */   private void init() {
/* 124 */     synchronized (this.SYNC) {
/* 125 */       log.info("(re-) initializing");
/* 126 */       String _logDir = ApplicationContext.getInstance().getProperty("frame.log.dir");
/* 127 */       log.debug("...log directory is:" + String.valueOf(_logDir));
/* 128 */       File logDir = new File(_logDir);
/* 129 */       if (!logDir.exists() || !logDir.isDirectory()) {
/* 130 */         throw new IllegalStateException("log.dir is not an existing directory");
/*     */       }
/*     */       
/* 133 */       log.debug("...collecting files");
/* 134 */       Set logFiles = new HashSet();
/* 135 */       collectFiles(logDir, logDir, logFiles);
/* 136 */       log.debug("....collected files: " + logFiles);
/* 137 */       Collection markedFiles = null;
/* 138 */       if (this.adminPanel != null) {
/* 139 */         markedFiles = this.adminPanel.getMarkedFiles();
/* 140 */         removeElement((HtmlElement)this.adminPanel);
/*     */       } 
/* 142 */       this.adminPanel = new AdminPanel(this.context, logFiles, logDir, this);
/* 143 */       this.adminPanel.setMarkedFiles(markedFiles);
/* 144 */       addElement((HtmlElement)this.adminPanel);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static MainPanel getInstance(ClientContext context) {
/* 150 */     synchronized (context.getLockObject()) {
/* 151 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 152 */       if (instance == null) {
/* 153 */         instance = new MainPanel(context);
/* 154 */         context.storeObject(MainPanel.class, instance);
/*     */       } 
/* 156 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 161 */     synchronized (this.SYNC) {
/* 162 */       StringBuffer tmp = new StringBuffer(template);
/* 163 */       StringUtilities.replace(tmp, "{LABEL_FILTER}", this.context.getLabel("filter"));
/* 164 */       StringUtilities.replace(tmp, "{INPUT_FILTER}", this.inputFilter.getHtmlCode(params));
/* 165 */       StringUtilities.replace(tmp, "{BUTTON_APPLY}", this.applyFilter.getHtmlCode(params));
/*     */       
/* 167 */       StringUtilities.replace(tmp, "{BUTTON_UPDATE}", this.buttonUpdate.getHtmlCode(params));
/* 168 */       StringUtilities.replace(tmp, "{PANEL}", this.adminPanel.getHtmlCode(params));
/* 169 */       return tmp.toString();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyFilter() {
/* 174 */     String tmp = (String)this.inputFilter.getValue();
/* 175 */     if (tmp != null && tmp.trim().length() > 0) {
/* 176 */       this.filenameAcceptor = (Acceptor)StringAcceptor.create(tmp, true);
/*     */     } else {
/* 178 */       this.filenameAcceptor = null;
/*     */     } 
/* 180 */     update();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\logfile\\ui\html\main\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */