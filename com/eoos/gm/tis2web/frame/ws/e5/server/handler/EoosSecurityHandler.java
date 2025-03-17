/*     */ package com.eoos.gm.tis2web.frame.ws.e5.server.handler;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.server.config.ConfigData;
/*     */ import com.eoos.gm.tis2web.frame.ws.util.DesEncrypter;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ import javax.xml.ws.handler.soap.SOAPHandler;
/*     */ import javax.xml.ws.handler.soap.SOAPMessageContext;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EoosSecurityHandler
/*     */   implements SOAPHandler<SOAPMessageContext>
/*     */ {
/*     */   protected static final String HDR_TYPE = "type";
/*     */   protected static final String HDR_TYPE_USERNAME = "usernametoken";
/*     */   protected static final String HDR_TYPE_SAML = "samltoken";
/*     */   protected static final String HDR_ISSUER = "issuer";
/*     */   protected static final String HDR_ISSUE_INSTANT = "issueinstant";
/*     */   protected static final String HDR_USERNAME = "username";
/*     */   protected static final String HDR_PWD = "pwd";
/*     */   protected static final String HDR_USERNAMETOKEN_TK = "tk";
/*  34 */   private static final Logger e5Log = Logger.getLogger("e5log");
/*     */   
/*     */   public Set<QName> getHeaders() {
/*  37 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(MessageContext mc) {}
/*     */   
/*     */   public boolean handleFault(SOAPMessageContext mc) {
/*  44 */     boolean isOutbound = ((Boolean)mc.get("javax.xml.ws.handler.message.outbound")).booleanValue();
/*  45 */     String direction = isOutbound ? "Outbound" : "Inbound";
/*  46 */     e5Log.info("<!-- " + getClass().getSimpleName() + ": " + direction + " fault detected. -->");
/*  47 */     return isOutbound;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleMessage(SOAPMessageContext mc) {
/*  52 */     if (((Boolean)mc.get("javax.xml.ws.handler.message.outbound")).booleanValue()) {
/*  53 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Outbound security - no action. -->");
/*  54 */       return true;
/*     */     } 
/*     */     
/*  57 */     e5Log.debug("<!-- " + getClass().getSimpleName() + ": Inbound security. -->");
/*  58 */     if (SecurityUtils.isAuthorized(mc)) {
/*  59 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ":  Request already authorized by " + mc.get("security.authorized.by") + " -->");
/*  60 */       return true;
/*     */     } 
/*     */     
/*  63 */     Map<String, String> hdrData = new HashMap<String, String>();
/*     */     try {
/*  65 */       Header sHeader = getWssHeader(mc);
/*  66 */       XMLStreamReader rdr = sHeader.readHeader();
/*  67 */       hdrData = parseHeader(rdr);
/*  68 */       if (ConfigData.getInstance().isAuthorized(hdrData.get("username"), decrypt(hdrData.get("pwd"))) && isValidToken(hdrData.get("tk"))) {
/*  69 */         mc.put("security.authorized", Boolean.TRUE);
/*  70 */         mc.put("security.authorized.by", getClass().getSimpleName());
/*  71 */         e5Log.info("<!-- " + getClass().getSimpleName() + ": Request authorized -->");
/*     */       } 
/*  73 */     } catch (Exception e) {
/*  74 */       e5Log.info("<!-- " + getClass().getSimpleName() + ": Invalid WSSE header received (" + e.toString() + ") -->");
/*     */     } 
/*     */     
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   private Header getWssHeader(MessageContext context) {
/*  81 */     Header result = null;
/*     */     try {
/*  83 */       HeaderList headers = (HeaderList)context.get("com.sun.xml.ws.api.message.HeaderList");
/*  84 */       Iterator<Header> hdIterator = headers.iterator();
/*  85 */       while (hdIterator.hasNext()) {
/*  86 */         Header hd = hdIterator.next();
/*  87 */         if (hd.getLocalPart() != null && hd.getLocalPart().compareTo("Security") == 0 && hd.getNamespaceURI() != null && hd.getNamespaceURI().compareTo("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") == 0) {
/*  88 */           result = hd;
/*     */         }
/*     */       } 
/*  91 */     } catch (Exception e) {
/*  92 */       if (ConfigData.getInstance().isDetailedLog()) {
/*  93 */         e5Log.info("<!-- " + getClass().getSimpleName() + ": Invalid security header (" + e.toString() + ")-->");
/*     */       }
/*     */     } 
/*  96 */     return result;
/*     */   }
/*     */   
/*     */   private Map<String, String> parseHeader(XMLStreamReader rdr) {
/* 100 */     Map<String, String> result = new HashMap<String, String>();
/* 101 */     result.put("type", "unknown");
/*     */     try {
/* 103 */       while (rdr.hasNext()) {
/* 104 */         rdr.next();
/* 105 */         if (rdr.isStartElement()) {
/* 106 */           QName qName = rdr.getName();
/* 107 */           if (qName.getLocalPart() != null) {
/* 108 */             if (qName.getLocalPart().compareTo("Assertion") == 0) {
/* 109 */               String issuer = rdr.getAttributeValue(null, "Issuer");
/* 110 */               String issueInst = rdr.getAttributeValue(null, "IssueInstant");
/* 111 */               result.put("type", "samltoken");
/* 112 */               result.put("issuer", issuer);
/* 113 */               result.put("issueinstant", issueInst);
/*     */             } 
/* 115 */             if (qName.getLocalPart().compareTo("UsernameToken") == 0) {
/* 116 */               result.put("type", "usernametoken");
/* 117 */               String ts = rdr.getAttributeValue("http://eoos-technologies.com/gm/t2w/euro5", "tk");
/* 118 */               result.put("tk", ts);
/*     */             } 
/*     */             
/* 121 */             if (qName.getLocalPart().compareTo("NameIdentifier") == 0) {
/* 122 */               rdr.next();
/*     */               try {
/* 124 */                 String uname = rdr.getText();
/* 125 */                 result.put("username", uname);
/* 126 */               } catch (Exception e) {}
/*     */             } 
/*     */ 
/*     */             
/* 130 */             if (qName.getLocalPart().compareTo("Username") == 0) {
/* 131 */               rdr.next();
/*     */               try {
/* 133 */                 String uName = rdr.getText();
/* 134 */                 result.put("username", uName);
/* 135 */               } catch (Exception e) {}
/*     */             } 
/*     */             
/* 138 */             if (qName.getLocalPart().compareTo("Password") == 0) {
/* 139 */               rdr.next();
/*     */               try {
/* 141 */                 String pwd = rdr.getText();
/* 142 */                 result.put("pwd", pwd);
/* 143 */               } catch (Exception e) {}
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 148 */         if (result.get("username") != null && result.get("pwd") != null) {
/*     */           break;
/*     */         }
/*     */       } 
/* 152 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 155 */     if (ConfigData.getInstance().isDetailedLog()) {
/* 156 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Username = " + (String)result.get("username") + "-->");
/*     */     }
/* 158 */     return result;
/*     */   }
/*     */   
/*     */   private String decrypt(String s) {
/* 162 */     String result = null;
/*     */     try {
/* 164 */       byte[] encrpw = ConfigData.getInstance().getE5TokenPwd();
/* 165 */       DesEncrypter desEncrypter = new DesEncrypter(encrpw, encrpw);
/* 166 */       result = desEncrypter.decrypt(s);
/* 167 */     } catch (Exception e) {
/* 168 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Decrypt error (" + e.toString() + ") -->");
/*     */     } 
/* 170 */     return result;
/*     */   }
/*     */   
/*     */   private boolean isValidToken(String tk) {
/* 174 */     boolean result = false;
/*     */     try {
/* 176 */       long tCreate = Long.parseLong(decrypt(tk));
/* 177 */       Date cDate = new Date();
/* 178 */       long tolerance = ConfigData.getInstance().getTolerance();
/* 179 */       if (cDate.getTime() - tolerance <= tCreate && tCreate <= cDate.getTime() + tolerance) {
/* 180 */         result = true;
/*     */       }
/* 182 */     } catch (Exception e) {
/* 183 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Token validation error (" + e.toString() + ") -->");
/*     */     } 
/* 185 */     String v = result ? "valid" : "invalid";
/* 186 */     e5Log.debug("<!-- " + getClass().getSimpleName() + ": Token is " + v + " -->");
/* 187 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\server\handler\EoosSecurityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */