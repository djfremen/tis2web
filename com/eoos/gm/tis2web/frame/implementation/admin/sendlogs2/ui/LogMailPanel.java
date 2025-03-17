/*     */ package com.eoos.gm.tis2web.frame.implementation.admin.sendlogs2.ui;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.gm.tis2web.frame.implementation.admin.searchlog.GetFileContentTask;
/*     */ import com.eoos.gm.tis2web.frame.implementation.admin.sendlogs2.GetFilesTask;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.ZipUtil;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogMailPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(LogMailPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  45 */       template = ApplicationContext.getInstance().loadFile(LogMailPanel.class, "logmailpanel.html", null).toString();
/*  46 */     } catch (Exception e) {
/*  47 */       log.error("unable to load template - error:" + e, e);
/*  48 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected TextInputElement ieMailAddress;
/*     */   
/*     */   private TextInputElement ieFilenamePatternInclude;
/*     */   
/*     */   private TextInputElement ieFilenamePatternExclude;
/*     */   
/*     */   private SelectBoxSelectionElement selectionServer;
/*     */   
/*     */   protected ClickButtonElement ieOK;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private static final int STATUS_INITIAL = 0;
/*     */   
/*     */   private static final int STATUS_OK = 1;
/*     */   
/*     */   private static final int STATUS_FAILED = 2;
/*  70 */   private int status = 0;
/*     */ 
/*     */   
/*     */   private LogMailPanel(final ClientContext context) {
/*  74 */     this.context = context;
/*     */     
/*  76 */     this.ieMailAddress = new TextInputElement(context.createID());
/*  77 */     addElement((HtmlElement)this.ieMailAddress);
/*     */     
/*  79 */     this.ieFilenamePatternInclude = new TextInputElement(context.createID());
/*  80 */     addElement((HtmlElement)this.ieFilenamePatternInclude);
/*     */     
/*  82 */     this.ieFilenamePatternExclude = new TextInputElement(context.createID());
/*  83 */     addElement((HtmlElement)this.ieFilenamePatternExclude);
/*     */     
/*  85 */     final List serverURLs = new ArrayList(ApplicationContext.getInstance().getClusterURLs());
/*  86 */     DataRetrievalAbstraction.DataCallback callback = new DataRetrievalAbstraction.DataCallback()
/*     */       {
/*     */         public List getData() {
/*  89 */           return serverURLs;
/*     */         }
/*     */       };
/*     */     
/*  93 */     this.selectionServer = new SelectBoxSelectionElement(context.createID(), false, callback, callback.getData().size())
/*     */       {
/*     */         protected String getDisplayValue(Object option) {
/*  96 */           return ((URL)option).getHost() + ":" + ((URL)option).getPort();
/*     */         }
/*     */       };
/*     */     
/* 100 */     addElement((HtmlElement)this.selectionServer);
/* 101 */     this.selectionServer.setValue(Collections.singleton(callback.getData().get(0)));
/*     */     
/* 103 */     this.ieOK = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/* 105 */           return context.getLabel("send");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 110 */             final String email = (String)LogMailPanel.this.ieMailAddress.getValue();
/* 111 */             if (email != null && email.trim().length() > 0) {
/*     */               
/* 113 */               final Collection servers = (Collection)LogMailPanel.this.selectionServer.getValue();
/*     */               
/* 115 */               String[] filePatternInclude = null;
/* 116 */               String _tmp = (String)LogMailPanel.this.ieFilenamePatternInclude.getValue();
/* 117 */               if (_tmp != null && _tmp.trim().length() > 0) {
/* 118 */                 _tmp = _tmp.replace('/', File.separatorChar);
/* 119 */                 _tmp = _tmp.replace('\\', File.separatorChar);
/* 120 */                 filePatternInclude = _tmp.trim().split(";");
/*     */               } 
/*     */ 
/*     */               
/* 124 */               String[] filePatternExclude = null;
/* 125 */               _tmp = (String)LogMailPanel.this.ieFilenamePatternExclude.getValue();
/* 126 */               if (_tmp != null && _tmp.trim().length() > 0) {
/* 127 */                 _tmp = _tmp.replace('/', File.separatorChar);
/* 128 */                 _tmp = _tmp.replace('\\', File.separatorChar);
/* 129 */                 filePatternExclude = _tmp.trim().split(";");
/*     */               } 
/*     */               
/* 132 */               ClusterTaskExecution cte = new ClusterTaskExecution((Task)new GetFilesTask(filePatternInclude, filePatternExclude), context)
/*     */                 {
/*     */                   protected Collection getClusterURLs() {
/* 135 */                     return servers;
/*     */                   }
/*     */                 };
/*     */ 
/*     */               
/* 140 */               ClusterTaskExecution.Result result = cte.execute();
/* 141 */               for (Iterator<URL> iterURLs = result.getClusterURLs().iterator(); iterURLs.hasNext(); ) {
/* 142 */                 final URL url = iterURLs.next();
/* 143 */                 Collection files = (Collection)result.getResult(url);
/* 144 */                 for (Iterator<File> iterFiles = files.iterator(); iterFiles.hasNext(); ) {
/* 145 */                   final File file = iterFiles.next();
/* 146 */                   ClusterTaskExecution cte2 = new ClusterTaskExecution((Task)new GetFileContentTask(file), context) {
/*     */                       protected Collection getClusterURLs() {
/* 148 */                         return Collections.singleton(url);
/*     */                       }
/*     */                     };
/*     */                   
/* 152 */                   final byte[] content = ZipUtil.gzip((byte[])cte2.execute().getResult(url));
/* 153 */                   final DataSource ds = new DataSource()
/*     */                     {
/*     */                       public OutputStream getOutputStream() throws IOException {
/* 156 */                         throw new IOException("read only");
/*     */                       }
/*     */                       
/*     */                       public String getName() {
/* 160 */                         return url.getHost() + "_" + file.getName() + ".gz";
/*     */                       }
/*     */                       
/*     */                       public InputStream getInputStream() throws IOException {
/* 164 */                         return new ByteArrayInputStream(content);
/*     */                       }
/*     */                       
/*     */                       public String getContentType() {
/* 168 */                         return "application/gzip";
/*     */                       }
/*     */                     };
/*     */ 
/*     */                   
/* 173 */                   MailService.Callback callback = new MailService.Callback()
/*     */                     {
/*     */                       public String getText() {
/* 176 */                         return "";
/*     */                       }
/*     */                       
/*     */                       public String getSubject() {
/* 180 */                         return "server:" + url.getHost() + " log file:" + file.getPath();
/*     */                       }
/*     */                       
/*     */                       public String getSender() {
/* 184 */                         return ApplicationContext.getInstance().getProperty("frame.log.mailer.email.sender");
/*     */                       }
/*     */                       
/*     */                       public Collection getReplyTo() {
/* 188 */                         return null;
/*     */                       }
/*     */                       
/*     */                       public Collection getRecipients() {
/* 192 */                         return Collections.singleton(email.trim());
/*     */                       }
/*     */                       
/*     */                       public DataSource[] getAttachments() {
/* 196 */                         return new DataSource[] { this.val$ds };
/*     */                       }
/*     */                     };
/*     */ 
/*     */                   
/* 201 */                   ((MailService)FrameServiceProvider.getInstance().getService(MailService.class)).send(callback);
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/* 206 */               LogMailPanel.this.status = 1;
/*     */             } 
/* 208 */           } catch (Exception e) {
/* 209 */             LogMailPanel.log.error("failed to send log files - exception:" + e, e);
/* 210 */             LogMailPanel.this.status = 2;
/*     */           } 
/* 212 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 216 */     addElement((HtmlElement)this.ieOK);
/*     */   }
/*     */ 
/*     */   
/*     */   public static LogMailPanel getInstance(ClientContext context) {
/* 221 */     synchronized (context.getLockObject()) {
/* 222 */       LogMailPanel instance = (LogMailPanel)context.getObject(LogMailPanel.class);
/* 223 */       if (instance == null) {
/* 224 */         instance = new LogMailPanel(context);
/* 225 */         context.storeObject(LogMailPanel.class, instance);
/*     */       } 
/* 227 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 232 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 234 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("frame.log.mailer.dialog.title"));
/* 235 */     StringUtilities.replace(code, "{LABEL_EMAIL}", this.context.getLabel("email.address"));
/* 236 */     StringUtilities.replace(code, "{INPUT_EMAIL}", this.ieMailAddress.getHtmlCode(params));
/* 237 */     StringUtilities.replace(code, "{LABEL_FILENAME_INCLUDE}", this.context.getLabel("filename.include.patterns"));
/* 238 */     StringUtilities.replace(code, "{INPUT_FILENAME_INCLUDE}", this.ieFilenamePatternInclude.getHtmlCode(params));
/* 239 */     StringUtilities.replace(code, "{LABEL_FILENAME_EXCLUDE}", this.context.getLabel("filename.exclude.patterns"));
/* 240 */     StringUtilities.replace(code, "{INPUT_FILENAME_EXCLUDE}", this.ieFilenamePatternExclude.getHtmlCode(params));
/* 241 */     StringUtilities.replace(code, "{LABEL_SERVER}", this.context.getLabel("server"));
/* 242 */     StringUtilities.replace(code, "{SELECTION_SERVER}", this.selectionServer.getHtmlCode(params));
/*     */     
/* 244 */     StringUtilities.replace(code, "{BUTTON_OK}", this.ieOK.getHtmlCode(params));
/* 245 */     String status = "";
/* 246 */     if (this.status == 1) {
/* 247 */       status = this.context.getMessage("successfully.send.logs");
/* 248 */     } else if (this.status == 2) {
/* 249 */       status = this.context.getMessage("error.see.log");
/*     */     } 
/* 251 */     StringUtilities.replace(code, "{STATUS}", status);
/*     */     
/* 253 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\sendlogs\\ui\LogMailPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */