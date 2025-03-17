/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.config.ui.html.main;
/*     */ 
/*     */ import com.eoos.automat.Acceptor;
/*     */ import com.eoos.automat.StringAcceptor;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*     */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.gtwo.PagedElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextAreaInputElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MainPanel
/*     */   extends HtmlElementContainerBase {
/*     */   private static final class CT_ModifyConfiguration
/*     */     implements ClusterTask {
/*     */     private final Properties properties;
/*     */     
/*     */     private CT_ModifyConfiguration(Properties properties) {
/*  37 */       this.properties = properties;
/*     */     }
/*     */     private static final long serialVersionUID = 1L;
/*     */     public Object execute() {
/*  41 */       Object retValue = null;
/*     */       try {
/*  43 */         ConfigurationServiceProvider.getService().setProperties(this.properties);
/*  44 */       } catch (Exception e) {
/*  45 */         retValue = e;
/*     */       } 
/*  47 */       return retValue;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class CT_UndoModifications implements ClusterTask {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public Object execute() {
/*  55 */       Object retValue = null;
/*     */       try {
/*  57 */         ConfigurationServiceProvider.getService().setProperties(null);
/*  58 */       } catch (Exception e) {
/*  59 */         retValue = e;
/*     */       } 
/*  61 */       return retValue;
/*     */     }
/*     */     
/*     */     private CT_UndoModifications() {} }
/*  65 */   private static final Logger log = Logger.getLogger(MainPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  70 */       template = ApplicationContext.getInstance().loadFile(MainPanel.class, "mainpanel.html", null).toString();
/*  71 */     } catch (Exception e) {
/*  72 */       log.error("error loading template - error:" + e, e);
/*  73 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private HtmlElement pagedList;
/*  81 */   private final Object SYNC_STATE = new Object();
/*     */   
/*     */   private TextInputElement inputKeyFilter;
/*     */   
/*     */   private TextInputElement inputValueFilter;
/*     */   
/*     */   private ClickButtonElement buttonApply;
/*     */   
/*     */   private TextAreaInputElement inputDelta;
/*     */   
/*     */   private ClickButtonElement buttonSet;
/*     */   
/*     */   private ClickButtonElement buttonUndo;
/*     */   private ClickButtonElement buttonFill;
/*     */   
/*     */   private MainPanel(final ClientContext context) {
/*  97 */     this.context = context;
/*     */     
/*  99 */     this.inputKeyFilter = new TextInputElement(context.createID(), 40, -1);
/* 100 */     addElement((HtmlElement)this.inputKeyFilter);
/*     */     
/* 102 */     this.inputValueFilter = new TextInputElement(context.createID(), 40, -1);
/* 103 */     addElement((HtmlElement)this.inputValueFilter);
/*     */     
/* 105 */     this.buttonApply = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 108 */           return context.getLabel("apply");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 112 */           MainPanel.this.update();
/* 113 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 117 */     addElement((HtmlElement)this.buttonApply);
/*     */     
/* 119 */     this.inputDelta = new TextAreaInputElement(context.createID(), "5", "80");
/* 120 */     addElement((HtmlElement)this.inputDelta);
/*     */     
/* 122 */     this.buttonSet = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 125 */           return context.getLabel("modify");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 130 */             String content = (String)MainPanel.this.inputDelta.getValue();
/* 131 */             InputStream is = new ByteArrayInputStream(content.getBytes("utf-8"));
/*     */             
/* 133 */             Properties properties = new Properties();
/* 134 */             properties.load(is);
/* 135 */             MainPanel.this.modifyConfiguration(properties);
/*     */             
/* 137 */             MainPanel.this.update();
/* 138 */           } catch (Exception e) {
/* 139 */             MainPanel.log.error("unable to modify configuration - exception: " + e, e);
/*     */           } 
/* 141 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 145 */     addElement((HtmlElement)this.buttonSet);
/*     */     
/* 147 */     this.buttonUndo = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 150 */           return context.getLabel("undo.all");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 155 */             MainPanel.this.undoModifications();
/* 156 */             MainPanel.this.update();
/* 157 */           } catch (Exception e) {
/* 158 */             MainPanel.log.error("unable to undo modifications - exception: " + e, e);
/*     */           } 
/* 160 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 164 */     addElement((HtmlElement)this.buttonUndo);
/*     */     
/* 166 */     this.buttonFill = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 170 */             Filter filterKey = MainPanel.this.getKeyFilter();
/* 171 */             Filter filterValue = MainPanel.this.getValueFilter();
/*     */             
/* 173 */             Properties properties = ConfigurationUtil.toProperties((Configuration)ConfigurationServiceProvider.getService());
/* 174 */             for (Iterator<Map.Entry<Object, Object>> iter = properties.entrySet().iterator(); iter.hasNext(); ) {
/* 175 */               Map.Entry entry = iter.next();
/* 176 */               if (!filterKey.include(entry.getKey()) || !filterValue.include(entry.getValue())) {
/* 177 */                 iter.remove();
/*     */               }
/*     */             } 
/*     */             
/* 181 */             ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 182 */             properties.store(baos, (String)null);
/* 183 */             MainPanel.this.inputDelta.setValue(new String(baos.toByteArray(), "utf-8"));
/*     */           }
/* 185 */           catch (Exception e) {
/* 186 */             MainPanel.log.error("unable to fill field - exception: " + e, e);
/*     */           } 
/* 188 */           return null;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         protected String getLabel() {
/* 194 */           return "<<";
/*     */         }
/*     */       };
/* 197 */     addElement((HtmlElement)this.buttonFill);
/* 198 */     update();
/*     */   }
/*     */ 
/*     */   
/*     */   void update() {
/* 203 */     if (this.pagedList != null) {
/* 204 */       removeElement(this.pagedList);
/*     */     }
/*     */     
/* 207 */     ConfigurationListElement listElement = new ConfigurationListElement((Configuration)ApplicationContext.getInstance(), this.context, getValueFilter(), getKeyFilter());
/* 208 */     this.pagedList = (HtmlElement)new PagedElement(this.context.createID(), (HtmlElement)listElement, 18, 20);
/* 209 */     addElement(this.pagedList);
/*     */   }
/*     */   
/*     */   private Filter getValueFilter() {
/*     */     StringAcceptor stringAcceptor1;
/* 214 */     String tmp = (String)this.inputValueFilter.getValue();
/*     */     
/* 216 */     Acceptor acceptor = null;
/* 217 */     if (tmp == null || tmp.trim().length() == 0) {
/* 218 */       acceptor = Acceptor.ACCEPT_ALL;
/*     */     } else {
/* 220 */       stringAcceptor1 = StringAcceptor.create(tmp, true);
/*     */     } 
/* 222 */     final StringAcceptor _acceptor = stringAcceptor1;
/*     */     
/* 224 */     return new Filter()
/*     */       {
/*     */         public boolean include(Object obj) {
/* 227 */           return (obj != null && _acceptor.accept(obj));
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private Filter getKeyFilter() {
/*     */     StringAcceptor stringAcceptor1;
/* 234 */     String tmp = (String)this.inputKeyFilter.getValue();
/*     */     
/* 236 */     Acceptor acceptor = null;
/* 237 */     if (tmp == null || tmp.trim().length() == 0) {
/* 238 */       acceptor = Acceptor.ACCEPT_ALL;
/*     */     } else {
/* 240 */       stringAcceptor1 = StringAcceptor.create(tmp, true);
/*     */     } 
/* 242 */     final StringAcceptor _acceptor = stringAcceptor1;
/*     */     
/* 244 */     return new Filter()
/*     */       {
/*     */         public boolean include(Object obj) {
/* 247 */           return _acceptor.accept(obj);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static MainPanel getInstance(ClientContext context) {
/* 254 */     synchronized (context.getLockObject()) {
/* 255 */       MainPanel instance = (MainPanel)context.getObject(MainPanel.class);
/* 256 */       if (instance == null) {
/* 257 */         instance = new MainPanel(context);
/* 258 */         context.storeObject(MainPanel.class, instance);
/*     */       } 
/* 260 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 265 */     synchronized (this.SYNC_STATE) {
/* 266 */       StringBuffer tmp = new StringBuffer(template);
/* 267 */       StringUtilities.replace(tmp, "{LABEL_KEYFILTER}", this.context.getLabel("key.filter"));
/* 268 */       StringUtilities.replace(tmp, "{LABEL_VALUEFILTER}", this.context.getLabel("value.filter"));
/* 269 */       StringUtilities.replace(tmp, "{INPUT_KEYFILTER}", this.inputKeyFilter.getHtmlCode(params));
/* 270 */       StringUtilities.replace(tmp, "{INPUT_VALUEFILTER}", this.inputValueFilter.getHtmlCode(params));
/* 271 */       StringUtilities.replace(tmp, "{BUTTON_APPLY}", this.buttonApply.getHtmlCode(params));
/* 272 */       StringUtilities.replace(tmp, "{LIST}", this.pagedList.getHtmlCode(params));
/* 273 */       StringUtilities.replace(tmp, "{LABEL_CONFIG}", this.context.getLabel("delta.config"));
/* 274 */       StringUtilities.replace(tmp, "{INPUT_CONFIG}", this.inputDelta.getHtmlCode(params));
/* 275 */       StringUtilities.replace(tmp, "{BUTTON_SET}", this.buttonSet.getHtmlCode(params));
/* 276 */       StringUtilities.replace(tmp, "{BUTTON_UNDO}", this.buttonUndo.getHtmlCode(params));
/* 277 */       StringUtilities.replace(tmp, "{BUTTON_FILL}", this.buttonFill.getHtmlCode(params));
/*     */       
/* 279 */       return tmp.toString();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void modifyConfiguration(Properties properties) throws Exception {
/* 284 */     CT_ModifyConfiguration cT_ModifyConfiguration = new CT_ModifyConfiguration(properties);
/*     */     
/* 286 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)cT_ModifyConfiguration, this.context);
/* 287 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 288 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 289 */       URL url = iter.next();
/* 290 */       if (result.getResult(url) instanceof Exception) {
/* 291 */         log.warn("failed to set configuration - for cluster server :" + url);
/*     */       }
/*     */     } 
/*     */     
/* 295 */     if (result.getLocalResult() instanceof Exception) {
/* 296 */       throw (Exception)result.getLocalResult();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void undoModifications() throws Exception {
/* 302 */     CT_UndoModifications cT_UndoModifications = new CT_UndoModifications();
/*     */     
/* 304 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)cT_UndoModifications, this.context);
/* 305 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 306 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 307 */       URL url = iter.next();
/* 308 */       if (result.getResult(url) instanceof Exception) {
/* 309 */         log.warn("failed to undo configuration modifications - for cluster server :" + url);
/*     */       }
/*     */     } 
/*     */     
/* 313 */     if (result.getLocalResult() instanceof Exception)
/* 314 */       throw (Exception)result.getLocalResult(); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\confi\\ui\html\main\MainPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */