/*     */ package com.eoos.gm.tis2web.registration.standalone.authorization;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.registration.common.RegistrationForm;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.sax.SAXResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.apache.fop.apps.Driver;
/*     */ import org.apache.fop.apps.Options;
/*     */ import org.apache.fop.configuration.Configuration;
/*     */ import org.apache.xalan.xsltc.trax.TransformerFactoryImpl;
/*     */ 
/*     */ 
/*     */ public class PDFRegistration
/*     */ {
/*     */   public static final String FONT_DIRECTORY_PARAMETER = "<fontdir>";
/*     */   public static final String REGISTRATION = "registration";
/*     */   public static final String AUTHORIZATION = "license";
/*     */   private static final String METHOD_NAME = "getenv";
/*     */   private static final String SYSTEM_ROOT_PROPERTY = "getenv";
/*     */   private static final String WINDIR_PROPERTY = "windir";
/*     */   
/*     */   protected static Transformer getTransformer(Locale locale, String pathTOXLS) throws FileNotFoundException, IOException {
/*     */     Transformer TRANSFORMER;
/*  38 */     byte[] xslData = null;
/*     */     try {
/*  40 */       xslData = ApplicationContext.getInstance().loadResource(pathTOXLS + "_" + locale.getLanguage() + "_" + locale.getCountry() + ".xsl");
/*  41 */     } catch (Exception x) {}
/*     */     
/*  43 */     if (xslData == null || xslData.length == 0) {
/*     */       try {
/*  45 */         xslData = ApplicationContext.getInstance().loadResource(pathTOXLS + "_" + locale.getLanguage() + ".xsl");
/*  46 */       } catch (Exception x) {}
/*     */     }
/*     */     
/*  49 */     if (xslData == null || xslData.length == 0) {
/*  50 */       xslData = ApplicationContext.getInstance().loadResource(pathTOXLS + ".xsl");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     TransformerFactoryImpl transformerFactoryImpl = new TransformerFactoryImpl();
/*     */     
/*  58 */     InputStream is = new ByteArrayInputStream(xslData);
/*     */     
/*     */     try {
/*  61 */       TRANSFORMER = transformerFactoryImpl.newTransformer(new StreamSource(is));
/*  62 */     } catch (TransformerConfigurationException e) {
/*  63 */       e.printStackTrace();
/*  64 */       return null;
/*     */     } 
/*  66 */     return TRANSFORMER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getWindowsDirectory() {
/*  74 */     String directory = null;
/*     */     try {
/*  76 */       Method method = System.class.getMethod("getenv", (Class[])null);
/*  77 */       Map environment = (Map)method.invoke(null, (Object[])null);
/*  78 */       directory = (String)environment.get("getenv");
/*  79 */       if (directory == null) {
/*  80 */         directory = (String)environment.get("windir");
/*     */       }
/*  82 */     } catch (Exception ex) {}
/*     */     
/*  84 */     return directory;
/*     */   }
/*     */   
/*     */   protected String doubleBackSlashes(String directory) {
/*  88 */     StringBuffer sb = new StringBuffer();
/*  89 */     for (int i = 0; i < directory.length(); i++) {
/*  90 */       char c = directory.charAt(i);
/*  91 */       if (c == '\\') {
/*  92 */         sb.append('\\');
/*     */       }
/*  94 */       sb.append(c);
/*     */     } 
/*  96 */     return sb.toString();
/*     */   }
/*     */   
/*     */   protected String getWindowsFontDirectory() {
/* 100 */     String directory = doubleBackSlashes(getWindowsDirectory());
/* 101 */     return (directory != null) ? (directory + '\\' + "Fonts") : null;
/*     */   }
/*     */   
/*     */   public PDFRegistration() {
/*     */     try {
/* 106 */       String fontdir = ApplicationContext.getInstance().getProperty("frame.registration.font.directory");
/* 107 */       if (fontdir == null && ApplicationContext.getInstance().isStandalone()) {
/* 108 */         fontdir = getWindowsFontDirectory();
/*     */       }
/* 110 */       byte[] fopConfData = ApplicationContext.getInstance().loadResource("registration/userconfig.xml");
/* 111 */       if (fontdir != null) {
/* 112 */         String config = new String(fopConfData, "US-ASCII");
/* 113 */         config = StringUtilities.replace(config, "<fontdir>", fontdir);
/* 114 */         fopConfData = config.getBytes("US-ASCII");
/*     */       } 
/* 116 */       new Options(new ByteArrayInputStream(fopConfData));
/*     */       
/* 118 */       String urlFOP = ApplicationContext.getInstance().getProperty("frame.registration.url.base.fop.resource");
/* 119 */       if (Util.contains(urlFOP, "#LOCAL_URL")) {
/* 120 */         urlFOP = urlFOP.replace("#LOCAL_URL", ApplicationContext.getInstance().getLocalURL().toString());
/*     */       }
/*     */       
/* 123 */       if (!urlFOP.endsWith("/")) {
/* 124 */         urlFOP = urlFOP + "/";
/*     */       }
/* 126 */       Configuration.put("baseDir", urlFOP);
/* 127 */     } catch (Exception e) {
/* 128 */       throw new RuntimeException("unable to configure FOP - exception:" + e, e);
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
/*     */   public byte[] create(String template, Locale locale, RegistrationForm form) {
/*     */     try {
/* 142 */       Transformer TRANSFORMER = getTransformer(locale, "registration/" + template);
/* 143 */       Driver driver = new Driver();
/* 144 */       driver.setRenderer(1);
/* 145 */       ByteArrayOutputStream result = new ByteArrayOutputStream();
/* 146 */       driver.setOutputStream(result);
/* 147 */       Source src = new StreamSource(new ByteArrayInputStream(form.getXML()));
/* 148 */       Result res = new SAXResult(driver.getContentHandler());
/* 149 */       TRANSFORMER.transform(src, res);
/* 150 */       result.close();
/* 151 */       return result.toByteArray();
/* 152 */     } catch (Exception e) {
/* 153 */       e.printStackTrace(System.err);
/*     */       
/* 155 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\PDFRegistration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */