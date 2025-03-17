/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.dtc.impl.DTCServiceImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.ctrl.RequestHandler;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.javio.webwindow.ConnectionHandler;
/*     */ import com.javio.webwindow.DefaultConnectionHandler;
/*     */ import com.javio.webwindow.HTMLPane;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.io.StringReader;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViewerPanel
/*     */   extends BaseCustomizeJPanel
/*     */   implements RequestHandler, AttributeInput
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  38 */   private static Logger log = Logger.getLogger(ViewerPanel.class);
/*     */   
/*     */   private String m_initialURL;
/*     */   
/*     */   private String m_title;
/*     */   
/*     */   private JPanel m_titlePanel;
/*     */   
/*  46 */   private JPanel paneHTML = null;
/*     */   
/*     */   private DisplayRequest request;
/*     */   
/*     */   private boolean isWebWindowActiv = false;
/*     */   
/*  52 */   protected ClientSettings clientSettings = null;
/*     */   
/*  54 */   protected CustomizeHtmlEditorPane editorPane = null;
/*     */   
/*  56 */   protected CustomizeHtmlRender window = null;
/*     */   
/*     */   protected DefaultConnectionHandler connect;
/*     */   
/*     */   protected Controller controller;
/*     */   
/*  62 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */ 
/*     */   
/*     */   protected List instructions;
/*     */ 
/*     */   
/*     */   protected int actualInstruction;
/*     */ 
/*     */   
/*     */   protected String finalInstructions;
/*     */ 
/*     */   
/*     */   public ViewerPanel(String u_initalURL, String u_title, Controller controller, BaseCustomizeJPanel prevPanel) {
/*  75 */     super(prevPanel);
/*  76 */     this.m_initialURL = u_initalURL;
/*  77 */     this.m_title = u_title;
/*  78 */     this.controller = controller;
/*     */     try {
/*  80 */       this.clientSettings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/*  81 */       this.isWebWindowActiv = Boolean.valueOf(this.clientSettings.getProperty("webWindow")).booleanValue();
/*  82 */       initialize();
/*  83 */     } catch (Exception ex) {
/*  84 */       log.error("ViewerPanel Constructor, exception :" + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ViewerPanel(String content, List instructions, Controller controller, BaseCustomizeJPanel prevPanel) {
/*  90 */     super(prevPanel);
/*  91 */     this.actualInstruction = 0;
/*  92 */     this.instructions = instructions;
/*  93 */     this.m_initialURL = content;
/*  94 */     this.m_title = null;
/*  95 */     this.controller = controller;
/*     */     try {
/*  97 */       this.clientSettings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/*  98 */       this.isWebWindowActiv = Boolean.valueOf(this.clientSettings.getProperty("webWindow")).booleanValue();
/*  99 */       initialize();
/* 100 */     } catch (Exception ex) {
/* 101 */       log.error("ViewerPanel Constructor, exception :" + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initialize() {
/*     */     try {
/* 108 */       setLayout(new BorderLayout());
/* 109 */       add(getHTMLPane(), "Center");
/* 110 */       add(getTitlePanel(), "North");
/* 111 */     } catch (Throwable ioe) {
/* 112 */       log.debug("unbale to initialize ViewerPanel :" + ioe.getMessage());
/*     */     } 
/* 114 */     setVisible(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public JPanel getHTMLPaneTest() {
/* 119 */     this.paneHTML = new JPanel(new BorderLayout());
/*     */     
/*     */     try {
/* 122 */       String urlString = "com/eoos/gm/tis2web/sps/client/common/resources/html/Tech2-Remote-Prep4Comm.htm";
/* 123 */       URL url = getClass().getClassLoader().getResource(urlString);
/*     */       
/* 125 */       if (this.isWebWindowActiv) {
/* 126 */         this.window = getWebWindow((String)null, url);
/* 127 */         this.paneHTML.add((Component)this.window, "Center");
/*     */       }
/*     */     
/* 130 */     } catch (Throwable ioe) {
/* 131 */       log.debug("unable to build web window Panel Test :" + ioe.getMessage());
/*     */     } 
/*     */     
/* 134 */     return this.paneHTML;
/*     */   }
/*     */ 
/*     */   
/*     */   public JPanel getHTMLPane() {
/* 139 */     this.paneHTML = new JPanel(new BorderLayout());
/*     */     try {
/* 141 */       String htmlString = null;
/* 142 */       String urlString = null;
/* 143 */       URL url = null;
/*     */       
/* 145 */       if (this.m_initialURL.indexOf("pre-prog-instructions") >= 0) {
/* 146 */         this.m_title = resourceProvider.getLabel(null, "pre-programming.instructions");
/* 147 */         htmlString = assemblePreProgInstruction();
/* 148 */       } else if (this.m_initialURL.indexOf("intermediate-pre-prog-instructions") >= 0) {
/*     */ 
/*     */         
/* 151 */         this.m_title = resourceProvider.getLabel(null, "programming-sequence.pre-instructions");
/* 152 */         htmlString = "<html><head><title>" + this.m_title + "</title></head><body><br><b>" + this.m_title + "</b><br><br>" + this.m_initialURL.substring("intermediate-pre-prog-instructions".length() + 1) + "</body></html>";
/*     */       }
/* 154 */       else if (this.m_initialURL.indexOf("intermediate-post-prog-instructions") >= 0) {
/*     */ 
/*     */         
/* 157 */         this.m_title = resourceProvider.getLabel(null, "programming-sequence.post-instructions");
/* 158 */         htmlString = "<html><head><title>" + this.m_title + "</title></head><body><br><b>" + this.m_title + "</b><br><br>" + this.m_initialURL.substring("intermediate-post-prog-instructions".length() + 1) + "</body></html>";
/*     */       }
/* 160 */       else if (this.m_initialURL.indexOf("final-instructions") >= 0) {
/*     */         
/* 162 */         if (this.isWebWindowActiv)
/*     */         {
/*     */ 
/*     */           
/* 166 */           int pos = this.m_initialURL.indexOf("final-instructions");
/* 167 */           int length = pos + "final-instructions".length();
/* 168 */           if (this.instructions != null) {
/* 169 */             String id = this.m_initialURL.substring(0, length);
/* 170 */             htmlString = this.controller.requestInstructionHTML(id);
/* 171 */             if (missingInstructionHTML(htmlString, id, true)) {
/* 172 */               this.instructions = null;
/* 173 */               return this.paneHTML;
/*     */             } 
/* 175 */             htmlString = injectPostProgrammingMessage(htmlString);
/*     */           } else {
/*     */             
/* 178 */             htmlString = this.controller.requestInstructionHTML(this.m_initialURL);
/* 179 */             if (missingInstructionHTML(htmlString, this.m_initialURL, true))
/* 180 */               return this.paneHTML; 
/* 181 */             htmlString = injectClearDTCMessage(htmlString);
/*     */           } 
/* 183 */           this.m_title = getTitle(htmlString);
/*     */         }
/*     */         else
/*     */         {
/* 187 */           this.m_title = resourceProvider.getLabel(null, "final.instructions");
/* 188 */           urlString = "com/eoos/gm/tis2web/sps/client/common/resources/html/FinalInstructions.htm";
/*     */         }
/*     */       
/* 191 */       } else if (this.m_initialURL.indexOf("replace-instructions") >= 0) {
/*     */         
/* 193 */         if (this.isWebWindowActiv) {
/* 194 */           int pos = this.m_initialURL.indexOf("replace-instructions");
/* 195 */           int length = pos + "replace-instructions".length();
/* 196 */           if (this.m_initialURL.length() > length) {
/* 197 */             String id = this.m_initialURL.substring(0, length);
/* 198 */             htmlString = this.controller.requestInstructionHTML(id);
/* 199 */             if (missingInstructionHTML(htmlString, id, false)) {
/* 200 */               return this.paneHTML;
/*     */             }
/* 202 */             String replaceInstructions = this.m_initialURL.substring(length + 1);
/* 203 */             replaceInstructions = "&nbsp;<p>" + replaceInstructions + "</p>&nbsp;";
/* 204 */             htmlString = StringUtilities.replace(htmlString, "&nbsp;<p>&nbsp;</p><p>&nbsp;</p>", replaceInstructions);
/*     */           } else {
/* 206 */             htmlString = this.controller.requestInstructionHTML(this.m_initialURL);
/* 207 */             if (missingInstructionHTML(htmlString, this.m_initialURL, false)) {
/* 208 */               return this.paneHTML;
/*     */             }
/*     */           } 
/* 211 */           this.m_title = getTitle(htmlString);
/*     */         } else {
/* 213 */           this.m_title = resourceProvider.getLabel(null, "replace.instructions");
/* 214 */           urlString = "com/eoos/gm/tis2web/sps/client/common/resources/html/ReplaceInstructions.htm";
/*     */         }
/*     */       
/*     */       }
/* 218 */       else if (this.isWebWindowActiv) {
/* 219 */         htmlString = this.controller.requestInstructionHTML(this.m_initialURL);
/* 220 */         if (missingInstructionHTML(htmlString, this.m_initialURL, false))
/* 221 */           return this.paneHTML; 
/* 222 */         this.m_title = getTitle(htmlString);
/*     */       } else {
/*     */         
/* 225 */         urlString = "com/eoos/gm/tis2web/sps/client/common/resources/html/" + this.m_initialURL + ".htm";
/*     */       } 
/*     */ 
/*     */       
/* 229 */       if (urlString != null) {
/* 230 */         url = getClass().getClassLoader().getResource(urlString);
/*     */       }
/*     */       
/* 233 */       if (this.isWebWindowActiv) {
/* 234 */         this.window = getWebWindow(htmlString, url);
/* 235 */         this.window.getHTMLPane().addLinkListener(new TiswebLinkHandler());
/* 236 */         this.paneHTML.add((Component)this.window, "Center");
/*     */       } else {
/*     */         
/* 239 */         this.editorPane = getEditorPane(htmlString, url);
/* 240 */         this.paneHTML.add(this.editorPane, "Center");
/*     */       }
/*     */     
/* 243 */     } catch (Throwable ioe) {
/* 244 */       log.debug("Error in method getHTMLPane :" + ioe.getMessage());
/*     */     } 
/*     */     
/* 247 */     return this.paneHTML;
/*     */   }
/*     */   
/*     */   protected void updateWebWindow(String htmlString) {
/*     */     try {
/* 252 */       loadWebWindow(this.window, htmlString);
/* 253 */     } catch (Throwable ioe) {
/* 254 */       log.debug("Can't update HTML pane for " + this.m_initialURL + ": " + ioe + " updateWebWindow() methode");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadWebWindow(CustomizeHtmlRender window, String htmlString) throws MalformedURLException {
/* 259 */     HTMLPane p = window.getHTMLPane();
/* 260 */     StringReader reader = new StringReader(htmlString);
/* 261 */     URL url = new URL("http://");
/* 262 */     p.setConnectionHandler((ConnectionHandler)new TiswebImageHTML(this.controller));
/* 263 */     p.loadPage(reader, url);
/*     */   }
/*     */ 
/*     */   
/*     */   protected CustomizeHtmlRender getWebWindow(String htmlString, URL url) {
/* 268 */     if (this.window == null) {
/* 269 */       this.window = new CustomizeHtmlRender();
/*     */       try {
/* 271 */         if (htmlString != null) {
/* 272 */           loadWebWindow(this.window, htmlString);
/*     */         }
/* 274 */         else if (url != null) {
/* 275 */           HTMLPane p = this.window.getHTMLPane();
/* 276 */           p.loadPage(url);
/*     */         }
/*     */       
/* 279 */       } catch (Throwable ioe) {
/* 280 */         log.debug("Can't build HTML pane for " + this.m_initialURL + ": " + ioe + " getWebWindow() methode");
/*     */       } 
/*     */     } 
/* 283 */     return this.window;
/*     */   }
/*     */   
/*     */   protected CustomizeHtmlEditorPane getEditorPane(String htmlString, URL url) {
/* 287 */     if (this.editorPane == null) {
/* 288 */       this.editorPane = new CustomizeHtmlEditorPane();
/*     */       try {
/* 290 */         if (htmlString != null) {
/* 291 */           this.editorPane.setContentType("text/html");
/* 292 */           this.editorPane.setText(htmlString);
/*     */         } else {
/*     */           try {
/* 295 */             if (url != null) {
/* 296 */               this.editorPane.setPage(url);
/*     */             }
/* 298 */           } catch (MalformedURLException ex) {
/* 299 */             url = null;
/*     */           }
/*     */         
/*     */         } 
/* 303 */       } catch (Throwable ioe) {
/* 304 */         log.debug("Can't build HTML pane for " + this.m_initialURL + ": " + ioe + " getEditorPane() methode");
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     return this.editorPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean missingInstructionHTML(String htmlString, String message, boolean isFinalInstr) {
/* 317 */     if (htmlString == null || htmlString.equals("")) {
/* 318 */       log.error(message);
/* 319 */       URL url = null;
/* 320 */       String urlString = null;
/*     */       
/* 322 */       if (isFinalInstr) {
/* 323 */         urlString = "com/eoos/gm/tis2web/sps/client/common/resources/html/FinalInstructions.htm";
/*     */       } else {
/*     */         
/* 326 */         this.m_title = resourceProvider.getMessage(null, "sps.html-not-available");
/* 327 */         urlString = "com/eoos/gm/tis2web/sps/client/common/resources/html/Missing-Page.htm";
/*     */       } 
/* 329 */       if (urlString != null) {
/* 330 */         url = getClass().getClassLoader().getResource(urlString);
/*     */       }
/*     */       
/* 333 */       if (this.isWebWindowActiv) {
/* 334 */         this.window = getWebWindow(htmlString, url);
/* 335 */         this.paneHTML.add((Component)this.window, "Center");
/*     */       } else {
/*     */         
/* 338 */         this.editorPane = getEditorPane(htmlString, url);
/* 339 */         this.paneHTML.add(this.editorPane, "Center");
/*     */       } 
/* 341 */       return true;
/*     */     } 
/*     */     
/* 344 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel getTitlePanel() {
/* 353 */     if (this.m_titlePanel == null) {
/*     */       try {
/* 355 */         this.m_titlePanel = new JPanel();
/* 356 */         GridBagConstraints titelPanelConstraint = new GridBagConstraints();
/* 357 */         JLabel titleLabel = new JLabel(this.m_title);
/* 358 */         int fontSize = Integer.parseInt(System.getProperty("font.size.title"));
/* 359 */         titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), 1, fontSize));
/* 360 */         this.m_titlePanel.setLayout(new GridBagLayout());
/* 361 */         titelPanelConstraint.gridx = 0;
/* 362 */         titelPanelConstraint.gridy = 3;
/* 363 */         this.m_titlePanel.add(titleLabel, titelPanelConstraint);
/*     */       }
/* 365 */       catch (Throwable e) {
/* 366 */         log.error("unable to build Title Panel, " + e);
/*     */       } 
/*     */     }
/* 369 */     return this.m_titlePanel;
/*     */   }
/*     */   
/*     */   public String getTitle(String htmlString) {
/* 373 */     if (htmlString == null)
/* 374 */       return ""; 
/* 375 */     String searchWord = "<title>";
/* 376 */     int beginIndex = htmlString.indexOf("<title>");
/* 377 */     int endIndex = htmlString.indexOf("</title>");
/* 378 */     if (beginIndex != -1 && endIndex != -1) {
/* 379 */       return htmlString.substring(beginIndex + searchWord.length(), endIndex);
/*     */     }
/* 381 */     return "";
/*     */   }
/*     */   
/*     */   protected String assemblePreProgInstruction() {
/* 385 */     String instruction = this.instructions.get(this.actualInstruction);
/* 386 */     return "<html><head><title>" + this.m_title + "</title></head><body><br><b>" + this.m_title + "</b><br><br>" + instruction + "</body></html>";
/*     */   }
/*     */   
/*     */   protected String injectPostProgrammingMessage(String htmlString) {
/* 390 */     this.finalInstructions = htmlString;
/* 391 */     String postInstructions = this.instructions.get(this.actualInstruction);
/* 392 */     if (isLast()) {
/* 393 */       postInstructions = addToPostInstructionClearDTCMessage(postInstructions);
/*     */     }
/* 395 */     postInstructions = "&nbsp;<p>" + postInstructions + "</p>&nbsp;";
/* 396 */     return StringUtilities.replace(htmlString, "&nbsp;<p>&nbsp;</p><p>&nbsp;</p>", postInstructions);
/*     */   }
/*     */   
/*     */   protected String injectClearDTCMessage(String htmlString) {
/* 400 */     this.finalInstructions = htmlString;
/* 401 */     if (DTCServiceImpl.getInstance().isAutomaticallyClearDTCsMode()) {
/* 402 */       String clearDTCsInstruction = "&nbsp;<p>" + resourceProvider.getMessage(null, "sps.clearDTCs.msg.add-to-final-instruction") + "</p>&nbsp;";
/* 403 */       return StringUtilities.replace(htmlString, "&nbsp;<p>&nbsp;</p><p>&nbsp;</p>", clearDTCsInstruction);
/*     */     } 
/* 405 */     return htmlString;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String addToPostInstructionClearDTCMessage(String postInstructions) {
/* 410 */     postInstructions = "<p>" + postInstructions + "</p>";
/* 411 */     if (DTCServiceImpl.getInstance().isAutomaticallyClearDTCsMode() && !DTCServiceImpl.getInstance().isAlreadyClearDTCsExecuted()) {
/* 412 */       String clearDTCsInstruction = "<p>" + resourceProvider.getMessage(null, "sps.clearDTCs.msg.add-to-final-instruction") + "</p>";
/* 413 */       postInstructions = clearDTCsInstruction + postInstructions;
/*     */     } 
/* 415 */     return postInstructions;
/*     */   }
/*     */   
/*     */   public boolean isFirst() {
/* 419 */     return (this.instructions == null || this.actualInstruction == 0);
/*     */   }
/*     */   
/*     */   public boolean isLast() {
/* 423 */     return (this.instructions == null || this.actualInstruction == this.instructions.size() - 1);
/*     */   }
/*     */   
/*     */   public boolean isPreProgMessage() {
/* 427 */     return (this.m_initialURL.indexOf("pre-prog-instructions") >= 0);
/*     */   }
/*     */   
/*     */   public boolean isFinalInstruction() {
/* 431 */     return (this.m_initialURL.indexOf("final-instructions") >= 0);
/*     */   }
/*     */   
/*     */   public boolean isInstructionRequest() {
/* 435 */     return (isPreProgMessage() || isFinalInstruction());
/*     */   }
/*     */   
/*     */   public void advance() {
/* 439 */     this.actualInstruction++;
/* 440 */     if (this.m_initialURL.indexOf("pre-prog-instructions") >= 0) {
/* 441 */       String htmlString = assemblePreProgInstruction();
/* 442 */       updateWebWindow(htmlString);
/* 443 */     } else if (this.m_initialURL.indexOf("final-instructions") >= 0) {
/* 444 */       String postInstructions = this.instructions.get(this.actualInstruction);
/* 445 */       if (isLast()) {
/* 446 */         postInstructions = addToPostInstructionClearDTCMessage(postInstructions);
/*     */       }
/* 448 */       postInstructions = "&nbsp;<p>" + postInstructions + "</p>&nbsp;";
/* 449 */       String htmlString = StringUtilities.replace(this.finalInstructions, "&nbsp;<p>&nbsp;</p><p>&nbsp;</p>", postInstructions);
/* 450 */       updateWebWindow(htmlString);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveBack() {
/* 455 */     this.actualInstruction--;
/* 456 */     if (this.m_initialURL.indexOf("pre-prog-instructions") >= 0) {
/* 457 */       String htmlString = assemblePreProgInstruction();
/* 458 */       updateWebWindow(htmlString);
/* 459 */     } else if (this.m_initialURL.indexOf("final-instructions") >= 0 && 
/* 460 */       this.finalInstructions != null) {
/* 461 */       String postInstructions = this.instructions.get(this.actualInstruction);
/* 462 */       postInstructions = "&nbsp;<p>" + postInstructions + "</p>&nbsp;";
/* 463 */       String htmlString = StringUtilities.replace(this.finalInstructions, "&nbsp;<p>&nbsp;</p><p>&nbsp;</p>", postInstructions);
/* 464 */       updateWebWindow(htmlString);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleRequest(AssignmentRequest req) {
/* 470 */     log.debug("received html display request");
/* 471 */     setRequestGroup(req.getRequestGroup());
/* 472 */     if (this.window != null) {
/* 473 */       this.window.setRequest((DisplayRequest)req);
/*     */     }
/* 475 */     else if (this.editorPane != null) {
/* 476 */       this.editorPane.setRequest((DisplayRequest)req);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Attribute getAttribute() {
/* 481 */     return this.request.getAttribute();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\ViewerPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */