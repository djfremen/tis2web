/*     */ package com.eoos.gm.tis2web.swdl.client.ctrl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.swdl.client.driver.SDDriver;
/*     */ import com.eoos.gm.tis2web.swdl.common.system.Command;
/*     */ import com.eoos.util.CookieHandlerUninstall;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Base64EncodingUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
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
/*     */ 
/*     */ 
/*     */ public class ServerRequestor
/*     */ {
/*  33 */   private Logger log = Logger.getLogger(ServerRequestor.class);
/*     */   
/*  35 */   private static ServerRequestor instance = null;
/*     */   
/*  37 */   private String urlServer = null;
/*     */   
/*  39 */   private String sessionID = null;
/*     */   
/*  41 */   private String cookie = null;
/*     */   
/*     */   private boolean isLocalInstallation;
/*     */ 
/*     */   
/*     */   public ServerRequestor() {
/*  47 */     this.urlServer = System.getProperty("command.url");
/*  48 */     this.sessionID = System.getProperty("session.id");
/*  49 */     this.cookie = System.getProperty("cookie");
/*     */     try {
/*  51 */       this.cookie = new String(Base64EncodingUtil.decode(this.cookie), "utf-8");
/*  52 */     } catch (UnsupportedEncodingException e) {
/*  53 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  56 */     if (this.cookie == null || this.cookie.length() == 0) {
/*  57 */       this.cookie = "dummy";
/*     */     }
/*  59 */     this.isLocalInstallation = "local".equals(System.getProperty("server.installation"));
/*     */ 
/*     */     
/*     */     try {
/*  63 */       CookieHandlerUninstall.uninstallCookieHandler();
/*  64 */     } catch (Throwable t) {
/*  65 */       this.log.debug("unable to uninstall cookie handler - exception:" + t);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized ServerRequestor getInstance() {
/*  70 */     if (instance == null) {
/*  71 */       instance = new ServerRequestor();
/*     */     }
/*  73 */     return instance;
/*     */   }
/*     */   
/*     */   public boolean isLocalServerInstallation() {
/*  77 */     return this.isLocalInstallation;
/*     */   }
/*     */   
/*     */   public Pair[] getDTCUploadDriverProperties() {
/*  81 */     Pair[] properties = new Pair[1];
/*  82 */     if (Boolean.getBoolean("dtc.upload.disabled")) {
/*  83 */       properties[0] = (Pair)new PairImpl("dtcupload,disabled", SDDriver.DRIVER_PROPERTY_CATEGORY_DTCUPLOAD);
/*     */     } else {
/*  85 */       properties[0] = (Pair)new PairImpl("dtcupload,enabled", SDDriver.DRIVER_PROPERTY_CATEGORY_DTCUPLOAD);
/*     */     } 
/*  87 */     return properties;
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
/*     */   public Object sendRequest(Command command) throws Exception {
/*  99 */     Object objResult = null;
/*     */     
/* 101 */     this.log.debug("sendRequest() - contacting server");
/*     */     
/*     */     try {
/* 104 */       URL urlVC = new URL(this.urlServer);
/*     */       
/* 106 */       HttpURLConnection connection = (HttpURLConnection)urlVC.openConnection();
/* 107 */       connection.setRequestMethod("POST");
/* 108 */       connection.setRequestProperty("Cookie", this.cookie);
/* 109 */       connection.setDoOutput(true);
/* 110 */       connection.setDoInput(true);
/* 111 */       command.addParameter("sessionid", this.sessionID);
/*     */       
/* 113 */       this.log.debug("sendRequest() - sending the request");
/* 114 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 115 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 116 */       oos.writeObject(command);
/* 117 */       byte[] buff = baos.toByteArray();
/* 118 */       String strout = "command=" + StringUtilities.bytesToHex(buff);
/* 119 */       oos.close();
/* 120 */       PrintWriter pw = new PrintWriter(connection.getOutputStream());
/* 121 */       pw.write(strout);
/* 122 */       pw.flush();
/* 123 */       pw.close();
/*     */       
/* 125 */       this.log.debug("sendRequest() - receiving answer");
/* 126 */       ObjectInputStream is = new ObjectInputStream(connection.getInputStream());
/* 127 */       objResult = is.readObject();
/* 128 */       is.close();
/* 129 */     } catch (Exception e) {
/* 130 */       this.log.error("Exception in sendRequest() - " + e, e);
/* 131 */       throw e;
/*     */     } finally {}
/*     */     
/* 134 */     return objResult;
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
/*     */   public Object sendRequestFile(Command command) throws Exception {
/* 146 */     Object objResult = null;
/*     */     
/* 148 */     this.log.debug("sendRequestFile() - contacting server");
/*     */     
/*     */     try {
/* 151 */       URL urlVC = new URL(this.urlServer);
/*     */       
/* 153 */       HttpURLConnection connection = (HttpURLConnection)urlVC.openConnection();
/* 154 */       connection.setRequestMethod("POST");
/* 155 */       connection.setRequestProperty("Cookie", this.cookie);
/*     */       
/* 157 */       connection.setDoOutput(true);
/* 158 */       connection.setDoInput(true);
/* 159 */       command.addParameter("sessionid", this.sessionID);
/*     */       
/* 161 */       this.log.debug("sendRequestFile() - sending the request");
/* 162 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 163 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 164 */       oos.writeObject(command);
/* 165 */       byte[] buff = baos.toByteArray();
/* 166 */       String strout = "command=" + StringUtilities.bytesToHex(buff);
/* 167 */       oos.close();
/*     */       
/* 169 */       PrintWriter pw = new PrintWriter(connection.getOutputStream());
/* 170 */       pw.write(strout);
/* 171 */       pw.flush();
/* 172 */       pw.close();
/*     */       
/* 174 */       this.log.debug("sendRequestFile() - receiving answer");
/* 175 */       InputStream is = connection.getInputStream();
/* 176 */       AppFileInputStream afis = new AppFileInputStream(is, connection.getContentLength());
/* 177 */       buff = afis.read();
/* 178 */       ByteArrayInputStream bais = new ByteArrayInputStream(buff);
/* 179 */       ObjectInputStream ois = new ObjectInputStream(bais);
/* 180 */       objResult = ois.readObject();
/* 181 */       ois.close();
/* 182 */       is.close();
/* 183 */     } catch (Exception e) {
/* 184 */       this.log.error("Exception in sendRequestFile() - " + e, e);
/* 185 */       throw e;
/*     */     } 
/* 187 */     return objResult;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\ctrl\ServerRequestor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */