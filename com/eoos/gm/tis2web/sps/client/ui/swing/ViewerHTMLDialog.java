/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.SwingUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.UIUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.javio.webwindow.ConnectionHandler;
/*     */ import com.javio.webwindow.HTMLPane;
/*     */ import com.javio.webwindow.WebWindow;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.StringReader;
/*     */ import java.net.URL;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ViewerHTMLDialog
/*     */   extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected Container content;
/*     */   protected CustomizeHtmlEditorPane editorPane;
/*  43 */   protected CustomizeHtmlRender window = null;
/*     */   
/*     */   protected JScrollPane scroller;
/*     */   
/*     */   protected JPanel btnPanel;
/*     */   
/*     */   protected JButton ok;
/*     */   
/*     */   protected SPSFrame mainFrame;
/*     */   
/*     */   protected String htmlString;
/*     */   
/*     */   protected Controller controller;
/*     */   
/*  57 */   protected ClientSettings clientSettings = null;
/*     */   
/*  59 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*  61 */   private static final Logger log = Logger.getLogger(ViewerHTMLDialog.class);
/*     */ 
/*     */   
/*     */   public ViewerHTMLDialog(SPSFrame frame, String html) {
/*  65 */     super(frame, frame.getTitle(), false);
/*  66 */     this.mainFrame = frame;
/*  67 */     this.clientSettings = ClientAppContextProvider.getClientAppContext().getClientSettings();
/*  68 */     this.controller = frame;
/*  69 */     this.htmlString = html;
/*  70 */     initialize();
/*  71 */     setVisible(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initialize() {
/*     */     try {
/*  77 */       addWindowListener(new WindowAdapter() {
/*     */             public void windowClosing(WindowEvent e) {
/*  79 */               ViewerHTMLDialog.this.dispose();
/*  80 */               ViewerHTMLDialog.this.mainFrame.repaint();
/*     */             }
/*     */           });
/*  83 */       this.content = getContentPane();
/*  84 */       boolean isWebWindowActiv = Boolean.valueOf(this.clientSettings.getProperty("webWindow")).booleanValue();
/*  85 */       if (isWebWindowActiv) {
/*  86 */         this.scroller = new JScrollPane((Component)getWebWindow());
/*  87 */         getWebWindow().scrollToPosition(0);
/*     */       } else {
/*     */         
/*  90 */         this.scroller = new JScrollPane(getEditorPane());
/*  91 */         getEditorPane().setCaretPosition(0);
/*     */       } 
/*  93 */       this.content.add(this.scroller, "Center");
/*  94 */       this.content.add(getButtonPanel(), "South");
/*  95 */       setSize(SwingUtils.getViewerHTML_Width(), SwingUtils.getViewerHTML_Height());
/*  96 */       setLocation(UIUtil.getCenterLocation(this, this.mainFrame));
/*     */     }
/*  98 */     catch (Exception e) {
/*  99 */       System.out.println("Exception in initialize() method: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected CustomizeHtmlRender getWebWindow() {
/* 104 */     if (this.window == null) {
/* 105 */       this.window = new CustomizeHtmlRender();
/*     */       
/*     */       try {
/* 108 */         if (this.htmlString != null) {
/* 109 */           HTMLPane p = this.window.getHTMLPane();
/* 110 */           StringReader reader = new StringReader(this.htmlString);
/* 111 */           URL url = new URL("http://");
/* 112 */           p.setConnectionHandler((ConnectionHandler)new TiswebImageHTML(this.controller));
/* 113 */           p.loadPage(reader, url);
/*     */         }
/*     */       
/* 116 */       } catch (Throwable e) {
/* 117 */         log.warn("unable to create web window, ignoring - exception: " + e, e);
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return this.window;
/*     */   }
/*     */   
/*     */   public static void loadHTML(WebWindow window, String html) {
/*     */     try {
/* 126 */       if (html != null) {
/* 127 */         HTMLPane p = window.getHTMLPane();
/* 128 */         StringReader reader = new StringReader(html);
/* 129 */         URL url = new URL("http://");
/* 130 */         p.loadPage(reader, url);
/* 131 */         p.addLinkListener(new TiswebLinkHandler());
/*     */       }
/*     */     
/* 134 */     } catch (Throwable ioe) {
/* 135 */       log.debug("Can't build Web Window , exception:" + ioe.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static CustomizeHtmlRender getWebWindow(Controller controller, String html) {
/* 141 */     CustomizeHtmlRender window = new CustomizeHtmlRender();
/*     */     
/*     */     try {
/* 144 */       if (html != null) {
/* 145 */         HTMLPane p = window.getHTMLPane();
/* 146 */         StringReader reader = new StringReader(html);
/* 147 */         URL url = new URL("http://");
/* 148 */         p.loadPage(reader, url);
/* 149 */         p.setConnectionHandler((ConnectionHandler)new TiswebImageHTML(controller));
/*     */       }
/*     */     
/* 152 */     } catch (Throwable ioe) {
/* 153 */       log.debug("Can't build Web Window , exception:" + ioe.getMessage());
/*     */     } 
/*     */     
/* 156 */     return window;
/*     */   }
/*     */   
/*     */   protected CustomizeHtmlEditorPane getEditorPane() {
/* 160 */     if (this.editorPane == null) {
/* 161 */       this.editorPane = new CustomizeHtmlEditorPane();
/*     */       try {
/* 163 */         if (this.htmlString != null) {
/* 164 */           this.editorPane.setContentType("text/html");
/* 165 */           this.editorPane.setText(this.htmlString);
/*     */         }
/*     */       
/* 168 */       } catch (Throwable e) {
/* 169 */         log.warn("unable to create editor panel, ignoring - exception: " + e, e);
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     return this.editorPane;
/*     */   }
/*     */   
/*     */   private JPanel getButtonPanel() {
/* 177 */     if (this.btnPanel == null) {
/*     */       try {
/* 179 */         this.btnPanel = new JPanel(new GridBagLayout());
/* 180 */         JButton btnOK = new JButton(resourceProvider.getLabel(null, "ok"));
/* 181 */         btnOK.addActionListener(new ActionListener() {
/*     */               public void actionPerformed(ActionEvent evt) {
/* 183 */                 ViewerHTMLDialog.this.closeDialog();
/*     */               }
/*     */             });
/*     */         
/* 187 */         GridBagConstraints c = new GridBagConstraints();
/* 188 */         c.gridx = 3;
/* 189 */         c.gridy = 0;
/* 190 */         this.btnPanel.add(btnOK, c);
/* 191 */       } catch (Exception e) {
/* 192 */         log.error("Exception in getButtonPanel() method" + e.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 196 */     return this.btnPanel;
/*     */   }
/*     */ 
/*     */   
/*     */   private void closeDialog() {
/* 201 */     dispose();
/* 202 */     this.mainFrame.repaint();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\ViewerHTMLDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */