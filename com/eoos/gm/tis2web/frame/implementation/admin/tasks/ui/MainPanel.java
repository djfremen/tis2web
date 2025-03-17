/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.tasks.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.AdministrativeTask;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.implementation.admin.tasks.ASServiceImpl_ExecTask;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MainPanel
/*     */   extends HtmlElementContainerBase {
/*  24 */   private static final Logger log = Logger.getLogger(MainPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  29 */       template = ApplicationContext.getInstance().loadFile(MainPanel.class, "taskpanel.html", null).toString();
/*  30 */     } catch (Exception e) {
/*  31 */       log.error("error loading template - error:" + e, e);
/*  32 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*  38 */   private LinkedList tasks = new LinkedList();
/*     */   
/*  40 */   private HashMap taskToButton = new HashMap<Object, Object>();
/*     */   
/*  42 */   private String statusMessage = null;
/*     */   private static final String ROW_TEMPLATE = "<tr><td>{DESC}</td><td>{BUTTON}</td></tr>{ROWS}";
/*     */   
/*     */   private MainPanel(ClientContext context) {
/*  46 */     this.context = context;
/*     */     
/*  48 */     log.debug("initializing...");
/*     */     try {
/*  50 */       Configuration configuration = ASServiceImpl_ExecTask.getInstance().getConfiguration();
/*  51 */       String keyPrefix = "task.";
/*  52 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(configuration, "task.");
/*  53 */       LinkedList<Comparable> keys = new LinkedList(subConfigurationWrapper.getKeys());
/*  54 */       Collections.sort(keys);
/*  55 */       for (Iterator<Comparable> iter = keys.iterator(); iter.hasNext(); ) {
/*  56 */         String key = (String)iter.next();
/*  57 */         String className = subConfigurationWrapper.getProperty(key);
/*  58 */         log.debug("...instantiating task (or task container): " + key + " (class:" + className + ")");
/*     */         try {
/*  60 */           List<AdministrativeTask> _tasks = new LinkedList();
/*  61 */           Object obj = Class.forName(className).newInstance();
/*  62 */           if (obj instanceof AdministrativeTask.Container) {
/*  63 */             log.debug("...retrieving tasks from task container");
/*  64 */             _tasks.addAll(((AdministrativeTask.Container)obj).getTasks(context));
/*     */           } else {
/*  66 */             _tasks.add((AdministrativeTask)obj);
/*     */           } 
/*  68 */           for (int i = 0; i < _tasks.size(); i++) {
/*  69 */             AdministrativeTask task = _tasks.get(i);
/*  70 */             if (task.isAvailable(context)) {
/*  71 */               this.tasks.add(task);
/*  72 */               addElement((HtmlElement)getButton(task));
/*     */             } else {
/*  74 */               log.debug("...task \"" + String.valueOf(task.getDenotation(Locale.ENGLISH)) + "\" is unavailable for context: " + context + ", skipping");
/*     */             }
/*     */           
/*     */           } 
/*  78 */         } catch (Exception e) {
/*  79 */           log.error("...unable to instantiate task: " + key + " - exception:" + e, e);
/*     */         }
/*     */       
/*     */       } 
/*  83 */     } catch (Exception e) {
/*  84 */       log.error("...unable to initialize - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClickButtonElement getButton(final AdministrativeTask task) {
/*  90 */     synchronized (this.taskToButton) {
/*  91 */       ClickButtonElement button = (ClickButtonElement)this.taskToButton.get(task);
/*  92 */       if (button == null) {
/*  93 */         button = new ClickButtonElement(this.context.createID(), null)
/*     */           {
/*     */             protected String getLabel() {
/*  96 */               return MainPanel.this.context.getLabel("execute");
/*     */             }
/*     */             
/*     */             public Object onClick(Map submitParams) {
/* 100 */               MainPanel.this.statusMessage = null;
/*     */               try {
/* 102 */                 task.execute(MainPanel.this.context);
/* 103 */                 MainPanel.this.statusMessage = task.getSuccessMessage(MainPanel.this.context.getLocale());
/* 104 */               } catch (Exception e) {
/* 105 */                 MainPanel.this.statusMessage = task.getErrorMessage(e, MainPanel.this.context.getLocale());
/*     */               } 
/* 107 */               return null;
/*     */             }
/*     */           };
/*     */         
/* 111 */         this.taskToButton.put(task, button);
/*     */       } 
/* 113 */       return button;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MainPanel getInstance(ClientContext context) {
/* 118 */     synchronized (context.getLockObject()) {
/* 119 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 120 */       if (instance == null) {
/* 121 */         instance = new MainPanel(context);
/* 122 */         context.storeObject(MainPanel.class, instance);
/*     */       } 
/* 124 */       return instance;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 131 */     if (this.tasks.size() == 0) {
/* 132 */       return "";
/*     */     }
/* 134 */     StringBuffer tmp = new StringBuffer(template);
/* 135 */     for (Iterator<AdministrativeTask> iter = this.tasks.iterator(); iter.hasNext(); ) {
/* 136 */       AdministrativeTask task = iter.next();
/* 137 */       ClickButtonElement button = getButton(task);
/* 138 */       StringBuffer row = new StringBuffer("<tr><td>{DESC}</td><td>{BUTTON}</td></tr>{ROWS}");
/* 139 */       StringUtilities.replace(row, "{DESC}", task.getDenotation(this.context.getLocale()));
/* 140 */       StringUtilities.replace(row, "{BUTTON}", button.getHtmlCode(params));
/*     */       
/* 142 */       StringUtilities.replace(tmp, "{ROWS}", row.toString());
/*     */     } 
/* 144 */     StringUtilities.replace(tmp, "{ROWS}", "");
/*     */     
/* 146 */     StringUtilities.replace(tmp, "{STATUS}", (this.statusMessage != null) ? this.statusMessage : "");
/*     */     
/* 148 */     this.statusMessage = null;
/* 149 */     return tmp.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\task\\ui\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */