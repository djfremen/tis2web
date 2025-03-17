/*    */ package com.eoos.gm.tis2web.frame.ws.lt.server.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.ws.lt.server.security.WsSecurityData;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.Set;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.SOAPMessage;
/*    */ import javax.xml.ws.handler.MessageContext;
/*    */ import javax.xml.ws.handler.soap.SOAPHandler;
/*    */ import javax.xml.ws.handler.soap.SOAPMessageContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MsgLogHandler
/*    */   implements SOAPHandler<SOAPMessageContext>
/*    */ {
/* 18 */   private static final Logger wsDetailsLog = Logger.getLogger("wslog");
/*    */   
/*    */   public Set<QName> getHeaders() {
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close(MessageContext smc) {}
/*    */   
/*    */   public boolean handleFault(SOAPMessageContext smc) {
/* 28 */     return true;
/*    */   }
/*    */   
/*    */   public boolean handleMessage(SOAPMessageContext smc) {
/* 32 */     boolean result = false;
/* 33 */     if (!((Boolean)smc.get("javax.xml.ws.handler.message.outbound")).booleanValue()) {
/*    */       try {
/* 35 */         if (WsSecurityData.getInstance().isDetailedLog() && WsSecurityData.getInstance().isMessageLog()) {
/* 36 */           SOAPMessage msg = smc.getMessage();
/* 37 */           ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 38 */           msg.writeTo(os);
/* 39 */           wsDetailsLog.debug(os.toString() + "\n");
/*    */         } 
/* 41 */         result = true;
/* 42 */       } catch (Exception e) {
/* 43 */         wsDetailsLog.error("Cannot log SOAP message: " + e, e);
/*    */       } 
/*    */     }
/* 46 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\serve\\util\MsgLogHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */