/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.Controller;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.util.FontUtils;
/*     */ import com.eoos.util.v2.SectionIndex;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.regexp.RE;
/*     */ import org.apache.regexp.RESyntaxException;
/*     */ 
/*     */ public class TiswebImageHTML extends DefaultConnectionHandler {
/*  20 */   private static final Logger log = Logger.getLogger(TiswebImageHTML.class);
/*     */   
/*     */   protected Controller controller;
/*     */   
/*     */   public TiswebImageHTML(Controller controller) {
/*  25 */     this.controller = controller;
/*     */   }
/*     */   
/*     */   public URLConnection openConnection(URL url) throws IOException {
/*  29 */     if (url.toExternalForm().endsWith(".css")) {
/*  30 */       return new PseudoURLConnection(url, this.controller);
/*     */     }
/*  32 */     return super.openConnection(url);
/*     */   }
/*     */ 
/*     */   
/*     */   public Image getImage(URL arg) {
/*  37 */     Image newImage = null;
/*     */     try {
/*  39 */       int begin = arg.getFile().lastIndexOf('/');
/*  40 */       if (begin < 0) {
/*  41 */         begin = 0;
/*     */       } else {
/*  43 */         begin++;
/*     */       } 
/*  45 */       int end = arg.getFile().lastIndexOf('.');
/*  46 */       String image = arg.getFile().substring(begin, end);
/*  47 */       byte[] imageBytes = this.controller.requestInstructionImage(image);
/*  48 */       if (imageBytes != null) {
/*  49 */         newImage = Toolkit.getDefaultToolkit().createImage(imageBytes);
/*     */       }
/*  51 */     } catch (Exception e) {
/*  52 */       log.warn("unable to create Image, -exception: " + e.getMessage());
/*     */     } 
/*  54 */     return newImage;
/*     */   }
/*     */   
/*     */   public static class PseudoURLConnection extends URLConnection {
/*  58 */     protected static Map downloads = new HashMap<Object, Object>();
/*     */     private Controller controller;
/*     */     
/*     */     protected PseudoURLConnection(URL url, Controller controller) {
/*  62 */       super(url);
/*  63 */       this.controller = controller;
/*     */     }
/*     */ 
/*     */     
/*     */     public void connect() throws IOException {}
/*     */     
/*     */     public String getContentType() {
/*  70 */       return "text/css";
/*     */     }
/*     */     
/*     */     protected String load() throws IOException {
/*  74 */       String css = null;
/*     */       try {
/*  76 */         css = this.controller.requestInstructionHTML(getURL().getFile());
/*  77 */       } catch (Exception ex) {
/*  78 */         TiswebImageHTML.log.warn("unable to create load css, -exception: " + ex.getMessage());
/*     */       } 
/*  80 */       if (css != null) {
/*  81 */         downloads.put(getURL().getFile(), css);
/*     */       }
/*  83 */       return css;
/*     */     }
/*     */     
/*     */     public InputStream getInputStream() throws IOException {
/*  87 */       String css = (String)downloads.get(getURL().getFile());
/*  88 */       if (css == null) {
/*  89 */         css = load();
/*     */       }
/*  91 */       String newCss = TiswebImageHTML.recalculateFontSizeCSS(css);
/*  92 */       ByteArrayInputStream input = null;
/*  93 */       if (newCss != null) {
/*  94 */         css = newCss;
/*  95 */         input = new ByteArrayInputStream(css.getBytes());
/*  96 */         return input;
/*     */       } 
/*     */       
/*  99 */       return super.getInputStream();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String recalculateFontSizeCSS(String css) {
/*     */     try {
/* 117 */       if (css == null) {
/* 118 */         return null;
/*     */       }
/* 120 */       StringBuffer newCss = new StringBuffer(css);
/* 121 */       RE bodyStylePattern = new RE("\\.bodystyle\\s*\\{.*?font-size:\\s*(\\d*)px", 5);
/* 122 */       SectionIndex indexBody = StringUtilities.getSectionIndex(css, bodyStylePattern, 1, 0);
/* 123 */       String fontValueString = (String)StringUtilities.getSectionContent(css, indexBody);
/* 124 */       int fontBodySize = Integer.parseInt(fontValueString);
/* 125 */       int appDefaultFont = FontUtils.getSelectedFont().getSize();
/*     */       
/* 127 */       RE contentPattern = new RE("font-size:\\s*\\d*px", 3);
/* 128 */       final int delta = appDefaultFont - fontBodySize;
/*     */       
/* 130 */       StringUtilities.replace(newCss, contentPattern, new StringUtilities.ReplaceCallback()
/*     */           {
/*     */             private String normalize(CharSequence sequence) {
/* 133 */               StringBuffer retValue = (new StringBuffer()).append(sequence);
/* 134 */               StringUtilities.replace(retValue, " ", "");
/* 135 */               return retValue.toString();
/*     */             }
/*     */             
/*     */             public CharSequence getReplacement(CharSequence sectionContent) {
/* 139 */               String tmp = normalize(sectionContent);
/* 140 */               String strNumber = tmp.substring(tmp.indexOf(":") + 1, tmp.indexOf("px"));
/* 141 */               int number = Integer.parseInt(strNumber) + delta;
/*     */               
/* 143 */               return "font-size:" + number + "px";
/*     */             }
/*     */           });
/*     */       
/* 147 */       return newCss.toString();
/*     */     }
/* 149 */     catch (RESyntaxException e) {
/* 150 */       log.error("unable to recalculate Font Size in CSS File ,exception:" + e);
/* 151 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\TiswebImageHTML.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */