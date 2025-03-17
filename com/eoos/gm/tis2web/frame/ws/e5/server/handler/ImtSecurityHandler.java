/*     */ package com.eoos.gm.tis2web.frame.ws.e5.server.handler;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.server.config.ConfigData;
/*     */ import java.io.File;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ import javax.xml.ws.handler.soap.SOAPHandler;
/*     */ import javax.xml.ws.handler.soap.SOAPMessageContext;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import sun.misc.BASE64Decoder;
/*     */ import sun.misc.BASE64Encoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImtSecurityHandler
/*     */   implements SOAPHandler<SOAPMessageContext>
/*     */ {
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
/*  59 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Request already authorized by " + mc.get("security.authorized.by") + " -->");
/*  60 */       return true;
/*     */     } 
/*     */     
/*  63 */     String userid = "UNKNOWN";
/*     */     try {
/*  65 */       Document messageDoc = mc.getMessage().getSOAPPart();
/*     */       
/*  67 */       Node node = null;
/*  68 */       NodeList nodeList = messageDoc.getElementsByTagNameNS("http://fair-computer.de/IMTPortal", "CustomHeader");
/*  69 */       if (nodeList.getLength() == 0) {
/*  70 */         nodeList = messageDoc.getElementsByTagNameNS("http://eoos-technologies.com/gm/t2w/euro5", "CustomHeader");
/*     */       }
/*  72 */       if (nodeList.getLength() > 0) {
/*  73 */         node = nodeList.item(0).getFirstChild();
/*  74 */         if (node.getLocalName().compareToIgnoreCase("SubjectID") == 0) {
/*  75 */           userid = node.getTextContent();
/*     */         }
/*     */       } else {
/*  78 */         nodeList = messageDoc.getElementsByTagName("SubjectID");
/*  79 */         if (nodeList.getLength() > 0) {
/*  80 */           userid = nodeList.item(0).getTextContent();
/*     */         }
/*     */       } 
/*  83 */       mc.put("security.userid", userid);
/*     */       
/*  85 */       String sid = null;
/*  86 */       boolean isUnique = false;
/*     */       try {
/*  88 */         nodeList = messageDoc.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
/*  89 */         NamedNodeMap nnm = nodeList.item(0).getAttributes();
/*  90 */         Node attrNode = nnm.getNamedItemNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*  91 */         if (attrNode != null) {
/*  92 */           sid = attrNode.getNodeValue();
/*     */         }
/*  94 */         if (sid == null) {
/*  95 */           nodeList = messageDoc.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
/*  96 */           nnm = nodeList.item(0).getAttributes();
/*  97 */           attrNode = nnm.getNamedItem("Id");
/*  98 */           attrNode = nodeList.item(0).getAttributes().getNamedItem("Id");
/*  99 */           if (attrNode != null) {
/* 100 */             sid = attrNode.getNodeValue();
/*     */           }
/*     */         } 
/* 103 */         FrameService service = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 104 */         isUnique = service.registerUID(sid, ConfigData.getInstance().getImtTolerance());
/*     */       }
/* 106 */       catch (Exception e) {
/* 107 */         e5Log.info("<!-- " + getClass().getSimpleName() + ": Missing request ID -->");
/*     */       } 
/* 109 */       if (!isUnique) {
/* 110 */         e5Log.info("<!-- " + getClass().getSimpleName() + ": Duplicate request ID [" + sid + "] -->");
/*     */       }
/*     */       
/* 113 */       if (isExpired(messageDoc)) {
/* 114 */         throw new Exception("Message expired.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (hasValidSignature(messageDoc) && isAuthenticated(messageDoc)) {
/* 122 */         mc.put("security.authorized", Boolean.TRUE);
/* 123 */         mc.put("security.authorized.by", getClass().getSimpleName());
/* 124 */         e5Log.info("<!-- " + getClass().getSimpleName() + ": Request[" + userid + "] authorized -->");
/*     */       }
/*     */     
/* 127 */     } catch (Exception e) {
/* 128 */       if (e5Log.isDebugEnabled()) {
/* 129 */         e5Log.info("<!-- " + getClass().getSimpleName() + ": Invalid WSSE header received (" + e.toString() + ") - user = " + userid + " -->", e);
/*     */       } else {
/*     */         
/* 132 */         e5Log.info("<!-- " + getClass().getSimpleName() + ": Invalid WSSE header received (" + e.toString() + ") - user = " + userid + " -->");
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     return true;
/*     */   }
/*     */   
/*     */   private boolean hasValidSignature(Document doc) {
/* 140 */     return true;
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
/*     */   private boolean isAuthenticated(Document doc) {
/* 192 */     boolean result = false;
/* 193 */     String uname = "UNKNOWN";
/*     */     try {
/* 195 */       Node node = doc.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Username").item(0);
/* 196 */       uname = node.getTextContent();
/* 197 */       String pwd = ConfigData.getInstance().getPwd(uname);
/* 198 */       node = doc.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Password").item(0);
/* 199 */       NamedNodeMap nnMap = node.getAttributes();
/* 200 */       Node attrNode = nnMap.getNamedItem("Type");
/* 201 */       String pwType = attrNode.getNodeValue();
/* 202 */       String pwdHash = null;
/* 203 */       if (pwType != null && pwType.indexOf("PasswordDigest") >= 0) {
/* 204 */         pwdHash = node.getTextContent();
/*     */       }
/* 206 */       node = doc.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created").item(0);
/* 207 */       String cr = node.getTextContent();
/* 208 */       node = doc.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Nonce").item(0);
/* 209 */       String nnc = node.getTextContent();
/*     */       
/* 211 */       byte[] nonce = (new BASE64Decoder()).decodeBuffer(nnc);
/* 212 */       SecurityUtils su = new SecurityUtils();
/* 213 */       byte[] data = su.addByteArrays(su.addByteArrays(nonce, cr.getBytes()), pwd.getBytes());
/* 214 */       byte[] digest = su.getDigest(data, "SHA-1");
/* 215 */       String digest64 = (new BASE64Encoder()).encode(digest);
/* 216 */       if (pwdHash.compareTo(digest64) == 0) {
/* 217 */         result = true;
/*     */       
/*     */       }
/*     */     }
/* 221 */     catch (Exception e) {
/* 222 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Exception when verifying portal user (" + e.toString() + ") - user = " + uname + " -->");
/*     */     } 
/* 224 */     return result;
/*     */   }
/*     */   
/*     */   private boolean isExpired(Document doc) {
/* 228 */     boolean result = true;
/* 229 */     Node node = doc.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created").item(0);
/* 230 */     String cr = node.getTextContent();
/*     */     
/* 232 */     long cTime = (new Date()).getTime();
/*     */     try {
/* 234 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
/* 235 */       if (cr.endsWith("Z")) {
/* 236 */         cr = cr.substring(0, cr.length() - 1) + "+0000";
/*     */       }
/* 238 */       long iTime = sdf.parse(cr).getTime();
/* 239 */       if (cTime - ConfigData.getInstance().getImtTolerance() <= iTime && iTime <= cTime + ConfigData.getInstance().getImtTolerance()) {
/* 240 */         result = false;
/*     */       }
/*     */     }
/* 243 */     catch (Exception e) {
/* 244 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Invalid timestamp -->");
/*     */     } 
/* 246 */     return result;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 250 */     if (args.length > 0)
/*     */       try {
/* 252 */         File file = new File(args[0]);
/* 253 */         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 254 */         dbf.setNamespaceAware(true);
/* 255 */         Document doc = dbf.newDocumentBuilder().parse(file);
/* 256 */         ImtSecurityHandler imtSecurityHandler = new ImtSecurityHandler();
/* 257 */         boolean isValid = imtSecurityHandler.hasValidSignature(doc);
/* 258 */         System.out.println("isValid = " + isValid);
/*     */       }
/* 260 */       catch (Exception e) {
/* 261 */         System.out.println("Error");
/* 262 */         e.printStackTrace();
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\server\handler\ImtSecurityHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */