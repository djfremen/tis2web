/*     */ package com.eoos.gm.tis2web.frame.ws.lt.server.security;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.InvalidUsername;
/*     */ import com.eoos.gm.tis2web.frame.ws.lt.common.generated.SecurityFault;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WsSecurityHandler2
/*     */ {
/*  29 */   private static final Logger wsDetailsLog = Logger.getLogger("wslog");
/*     */   protected static final String CONFIG_PREFIX = "frame.ws.lt.";
/*     */   protected static final String HDR_TYPE = "type";
/*     */   protected static final String HDR_TYPE_USERNAME = "usernametoken";
/*     */   protected static final String HDR_TYPE_SAML = "samltoken";
/*     */   protected static final String HDR_ISSUER = "issuer";
/*     */   protected static final String HDR_ISSUE_INSTANT = "issueinstant";
/*     */   protected static final String HDR_USERNAME = "username";
/*     */   protected static final String HDR_PWD = "pwd";
/*     */   private boolean unitTest = false;
/*     */   
/*     */   public void authorize(MessageContext context, String uid) throws SecurityFault {
/*  41 */     boolean authorized = false;
/*  42 */     String username = null;
/*  43 */     Map<String, String> vData = new HashMap<String, String>();
/*     */     try {
/*  45 */       Header hdr = getWssHeader(context, uid);
/*  46 */       if (hdr != null) {
/*  47 */         XMLStreamReader rdr = hdr.readHeader();
/*  48 */         vData = parseHeader(rdr);
/*     */       } 
/*  50 */       if (WsSecurityData.getInstance().isGlobalTis()) {
/*  51 */         if (WsSecurityData.getInstance().isAuthorized(vData.get("username"), vData.get("pwd")) || WsSecurityData.getInstance().securityOffByConfig()) {
/*  52 */           authorized = true;
/*     */         }
/*     */       }
/*  55 */       else if (WsSecurityData.getInstance().isAuthorizedBySamlToken(vData) || WsSecurityData.getInstance().isAuthorized(vData.get("username"), vData.get("pwd"), uid)) {
/*  56 */         authorized = true;
/*     */       }
/*     */     
/*  59 */     } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (!authorized) {
/*  64 */       if (WsSecurityData.getInstance().isDetailedLog()) {
/*  65 */         String p = ((HttpServletRequest)context.get("javax.xml.ws.servlet.request")).getProtocol();
/*  66 */         wsDetailsLog.info("<!-- Invalid user: " + (String)vData.get("username") + " (" + p + ") -->");
/*     */       } 
/*  68 */       throw getSecurityFault(username);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setUnitTest(boolean b) {
/*  73 */     this.unitTest = b;
/*     */   }
/*     */   
/*     */   private Header getWssHeader(MessageContext context, String uid) {
/*  77 */     Header result = null;
/*  78 */     String p = ((HttpServletRequest)context.get("javax.xml.ws.servlet.request")).getProtocol();
/*     */     try {
/*  80 */       HeaderList headers = (HeaderList)context.get("com.sun.xml.ws.api.message.HeaderList");
/*  81 */       Iterator<Header> hdIterator = headers.iterator();
/*  82 */       while (hdIterator.hasNext()) {
/*  83 */         Header hd = hdIterator.next();
/*  84 */         if (WsSecurityData.getInstance().isDetailedLog()) {
/*  85 */           wsDetailsLog.debug("<!-- SOAP header found: " + hd.getLocalPart() + " (" + p + ") -->");
/*  86 */           if (WsSecurityData.getInstance().isHeaderLog()) {
/*     */             try {
/*  88 */               ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  89 */               XMLStreamWriter xmlWrt = XMLOutputFactory.newInstance().createXMLStreamWriter(bos);
/*  90 */               hd.writeTo(xmlWrt);
/*  91 */               xmlWrt.flush();
/*  92 */               xmlWrt.close();
/*  93 */               wsDetailsLog.debug(bos.toString());
/*  94 */               bos.close();
/*  95 */             } catch (Exception e) {
/*  96 */               wsDetailsLog.debug("Cannot log SOAP header: " + e.toString());
/*     */             } 
/*     */           }
/*     */         } 
/* 100 */         if (hd.getLocalPart() != null && hd.getLocalPart().compareTo("Security") == 0 && hd.getNamespaceURI() != null && hd.getNamespaceURI().compareTo("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") == 0) {
/* 101 */           result = hd;
/*     */         }
/*     */       } 
/* 104 */     } catch (Exception e) {
/* 105 */       if (WsSecurityData.getInstance().isDetailedLog()) {
/* 106 */         wsDetailsLog.info("<!-- Invalid security header [UID=" + uid + "]: " + e.toString() + " (" + p + ") -->");
/*     */       }
/*     */     } 
/* 109 */     return result;
/*     */   }
/*     */   
/*     */   private Map<String, String> parseHeader(XMLStreamReader rdr) {
/* 113 */     Map<String, String> result = new HashMap<String, String>();
/* 114 */     result.put("type", "unknown");
/*     */     try {
/* 116 */       while (rdr.hasNext()) {
/* 117 */         rdr.next();
/* 118 */         if (rdr.isStartElement()) {
/* 119 */           QName qName = rdr.getName();
/* 120 */           if (qName.getLocalPart() != null) {
/* 121 */             if (qName.getLocalPart().compareTo("Assertion") == 0) {
/* 122 */               String issuer = rdr.getAttributeValue(null, "Issuer");
/* 123 */               String issueInst = rdr.getAttributeValue(null, "IssueInstant");
/* 124 */               result.put("type", "samltoken");
/* 125 */               result.put("issuer", issuer);
/* 126 */               result.put("issueinstant", issueInst);
/*     */             } 
/* 128 */             if (qName.getLocalPart().compareTo("UsernameToken") == 0) {
/* 129 */               result.put("type", "usernametoken");
/*     */             }
/*     */             
/* 132 */             if (qName.getLocalPart().compareTo("NameIdentifier") == 0) {
/* 133 */               rdr.next();
/*     */               try {
/* 135 */                 String uname = rdr.getText();
/* 136 */                 result.put("username", uname);
/* 137 */               } catch (Exception e) {}
/*     */             } 
/*     */ 
/*     */             
/* 141 */             if (qName.getLocalPart().compareTo("Username") == 0) {
/* 142 */               rdr.next();
/*     */               try {
/* 144 */                 String uName = rdr.getText();
/* 145 */                 result.put("username", uName);
/* 146 */               } catch (Exception e) {}
/*     */             } 
/*     */             
/* 149 */             if (qName.getLocalPart().compareTo("Password") == 0) {
/* 150 */               rdr.next();
/*     */               try {
/* 152 */                 String pwd = rdr.getText();
/* 153 */                 result.put("pwd", pwd);
/* 154 */               } catch (Exception e) {}
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 159 */         if (result.get("username") != null && result.get("pwd") != null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 163 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 166 */     if (!this.unitTest && WsSecurityData.getInstance().isDetailedLog()) {
/* 167 */       wsDetailsLog.debug("<!-- Username= " + (String)result.get("username") + " -->");
/*     */     }
/* 169 */     return result;
/*     */   }
/*     */   
/*     */   private SecurityFault getSecurityFault(String username) {
/* 173 */     InvalidUsername invName = new InvalidUsername();
/* 174 */     SecurityFault sFault = new SecurityFault("Server Security Fault", invName);
/* 175 */     invName.setUsername(username);
/* 176 */     return sFault;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 180 */     File file = new File(args[0]);
/*     */     try {
/* 182 */       InputStream is = new FileInputStream(file);
/* 183 */       XMLInputFactory factory = XMLInputFactory.newInstance();
/* 184 */       XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(is);
/* 185 */       WsSecurityHandler2 handler = new WsSecurityHandler2();
/* 186 */       handler.setUnitTest(true);
/* 187 */       Map<String, String> myData = handler.parseHeader(xmlStreamReader);
/* 188 */       Iterator<String> it = myData.keySet().iterator();
/* 189 */       while (it.hasNext()) {
/* 190 */         String key = it.next();
/* 191 */         System.out.println(key + "=" + (String)myData.get(key));
/*     */       }
/*     */     
/* 194 */     } catch (Exception e) {
/* 195 */       String msg = "Exception in XML processing: " + e.toString();
/* 196 */       System.out.println(msg);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\server\security\WsSecurityHandler2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */