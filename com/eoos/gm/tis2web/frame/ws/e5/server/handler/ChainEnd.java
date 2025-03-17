/*    */ package com.eoos.gm.tis2web.frame.ws.e5.server.handler;
/*    */ 
/*    */ import java.util.Set;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Detail;
/*    */ import javax.xml.soap.DetailEntry;
/*    */ import javax.xml.soap.MessageFactory;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPBody;
/*    */ import javax.xml.soap.SOAPEnvelope;
/*    */ import javax.xml.soap.SOAPFault;
/*    */ import javax.xml.soap.SOAPMessage;
/*    */ import javax.xml.ws.handler.MessageContext;
/*    */ import javax.xml.ws.handler.soap.SOAPHandler;
/*    */ import javax.xml.ws.handler.soap.SOAPMessageContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChainEnd
/*    */   implements SOAPHandler<SOAPMessageContext>
/*    */ {
/* 23 */   private static final Logger e5Log = Logger.getLogger("e5log");
/*    */   
/*    */   public Set<QName> getHeaders() {
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close(MessageContext mc) {}
/*    */   
/*    */   public boolean handleFault(SOAPMessageContext mc) {
/* 33 */     boolean isOutbound = ((Boolean)mc.get("javax.xml.ws.handler.message.outbound")).booleanValue();
/* 34 */     String direction = isOutbound ? "Outbound" : "Inbound";
/* 35 */     e5Log.info("<!-- " + getClass().getSimpleName() + ": " + direction + " fault detected. -->");
/* 36 */     return isOutbound;
/*    */   }
/*    */   
/*    */   public boolean handleMessage(SOAPMessageContext mc) {
/* 40 */     boolean authorized = false;
/* 41 */     if (!((Boolean)mc.get("javax.xml.ws.handler.message.outbound")).booleanValue()) {
/* 42 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Inbound security. -->");
/* 43 */       if (SecurityUtils.isAuthorized(mc)) {
/* 44 */         e5Log.debug("<!-- " + getClass().getSimpleName() + ": Request already authorized by " + mc.get("security.authorized.by") + " -->");
/* 45 */         authorized = true;
/*    */       } 
/*    */     } else {
/* 48 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Outbound security - no action. -->");
/* 49 */       authorized = true;
/*    */     } 
/* 51 */     if (!authorized) {
/* 52 */       e5Log.debug("<!-- " + getClass().getSimpleName() + ": Authorization failed. -->");
/* 53 */       createSOAPFault(mc);
/*    */     } 
/* 55 */     return authorized;
/*    */   }
/*    */   
/*    */   private void createSOAPFault(SOAPMessageContext smc) {
/*    */     try {
/* 60 */       MessageFactory messageFactory = MessageFactory.newInstance();
/* 61 */       SOAPMessage responseMsg = messageFactory.createMessage();
/* 62 */       smc.setMessage(responseMsg);
/* 63 */       SOAPEnvelope envelope = responseMsg.getSOAPPart().getEnvelope();
/* 64 */       SOAPBody body = envelope.getBody();
/* 65 */       SOAPFault fault = body.addFault();
/* 66 */       fault.setFaultString("Forbidden");
/* 67 */       Name name = envelope.createName("Server", null, "http://schemas.xmlsoap.org/soap/envelope/");
/* 68 */       fault.setFaultCode(name);
/* 69 */       fault.setFaultActor("E5Service Security");
/* 70 */       Detail detail = fault.addDetail();
/* 71 */       detail.addNamespaceDeclaration("e5s", "http://www.eoos-technologies.com");
/* 72 */       name = envelope.createName("detail-message", "e5s", "http://www.eoos-technologies.com");
/* 73 */       detail.addDetailEntry(name);
/* 74 */       DetailEntry detailEntry = detail.getDetailEntries().next();
/* 75 */       detailEntry.setTextContent("TIS2Web/GlobalTIS access denied.");
/* 76 */     } catch (Exception e) {
/* 77 */       e5Log.error("<!-- " + getClass().getSimpleName() + ": Unexpected event when creating SOAP fault (" + e.toString() + ") -->");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\server\handler\ChainEnd.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */