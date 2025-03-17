/*    */ package com.eoos.gm.tis2web.frame.ws.e5.server.handler;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.ws.e5.server.config.ConfigData;
/*    */ import com.sun.xml.ws.api.message.Header;
/*    */ import com.sun.xml.ws.api.message.HeaderList;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.SOAPMessage;
/*    */ import javax.xml.stream.XMLOutputFactory;
/*    */ import javax.xml.stream.XMLStreamWriter;
/*    */ import javax.xml.ws.handler.MessageContext;
/*    */ import javax.xml.ws.handler.soap.SOAPHandler;
/*    */ import javax.xml.ws.handler.soap.SOAPMessageContext;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogHandler
/*    */   implements SOAPHandler<SOAPMessageContext>
/*    */ {
/* 24 */   private static final Logger e5Log = Logger.getLogger("e5log");
/*    */   
/*    */   public Set<QName> getHeaders() {
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close(MessageContext smc) {}
/*    */   
/*    */   public boolean handleFault(SOAPMessageContext smc) {
/* 34 */     return true;
/*    */   }
/*    */   
/*    */   public boolean handleMessage(SOAPMessageContext smc) {
/* 38 */     if (!((Boolean)smc.get("javax.xml.ws.handler.message.outbound")).booleanValue() && ConfigData.getInstance().isDetailedLog()) {
/* 39 */       if (ConfigData.getInstance().isMessageLog()) {
/*    */         try {
/* 41 */           SOAPMessage msg = smc.getMessage();
/* 42 */           ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 43 */           msg.writeTo(os);
/* 44 */           e5Log.debug(os.toString());
/* 45 */           os.close();
/* 46 */         } catch (Exception e) {
/* 47 */           e5Log.error("Cannot log SOAP message: " + e, e);
/*    */         } 
/* 49 */       } else if (ConfigData.getInstance().isHeaderLog()) {
/* 50 */         HeaderList headers = (HeaderList)smc.get("com.sun.xml.ws.api.message.HeaderList");
/* 51 */         Iterator<Header> hdIterator = headers.iterator();
/* 52 */         while (hdIterator.hasNext()) {
/* 53 */           Header hd = hdIterator.next();
/* 54 */           String hdrName = null;
/*    */           try {
/* 56 */             hdrName = hd.getLocalPart();
/* 57 */             ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 58 */             XMLStreamWriter xmlWrt = XMLOutputFactory.newInstance().createXMLStreamWriter(os);
/* 59 */             hd.writeTo(xmlWrt);
/* 60 */             xmlWrt.flush();
/* 61 */             xmlWrt.close();
/* 62 */             e5Log.debug(os.toString());
/* 63 */             os.close();
/* 64 */           } catch (Exception e) {
/* 65 */             e5Log.debug("Cannot log SOAP header[" + hdrName + "]: " + e.toString());
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     }
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\server\handler\LogHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */