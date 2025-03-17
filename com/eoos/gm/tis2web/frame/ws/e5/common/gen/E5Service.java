/*    */ package com.eoos.gm.tis2web.frame.ws.e5.common.gen;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.Service;
/*    */ import javax.xml.ws.WebEndpoint;
/*    */ import javax.xml.ws.WebServiceClient;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebServiceClient(name = "E5Service", targetNamespace = "http://eoos-technologies.com/gm/t2w/euro5", wsdlLocation = "file:/home/mh/tmp/wsimport-generated/euro5.wsdl")
/*    */ public class E5Service
/*    */   extends Service
/*    */ {
/*    */   private static final URL E5SERVICE_WSDL_LOCATION;
/* 26 */   private static final Logger logger = Logger.getLogger(E5Service.class.getName());
/*    */   
/*    */   static {
/* 29 */     URL url = null;
/*    */     
/*    */     try {
/* 32 */       URL baseUrl = E5Service.class.getResource(".");
/* 33 */       url = new URL(baseUrl, "file:/home/mh/tmp/wsimport-generated/euro5.wsdl");
/* 34 */     } catch (MalformedURLException e) {
/* 35 */       logger.warning("Failed to create URL for the wsdl Location: 'file:/home/mh/tmp/wsimport-generated/euro5.wsdl', retrying as a local file");
/* 36 */       logger.warning(e.getMessage());
/*    */     } 
/* 38 */     E5SERVICE_WSDL_LOCATION = url;
/*    */   }
/*    */   
/*    */   public E5Service(URL wsdlLocation, QName serviceName) {
/* 42 */     super(wsdlLocation, serviceName);
/*    */   }
/*    */   
/*    */   public E5Service() {
/* 46 */     super(E5SERVICE_WSDL_LOCATION, new QName("http://eoos-technologies.com/gm/t2w/euro5", "E5Service"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @WebEndpoint(name = "E5ServicePort")
/*    */   public E5ServicePort getE5ServicePort() {
/* 56 */     return getPort(new QName("http://eoos-technologies.com/gm/t2w/euro5", "E5ServicePort"), E5ServicePort.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @WebEndpoint(name = "E5ServicePort")
/*    */   public E5ServicePort getE5ServicePort(WebServiceFeature... features) {
/* 68 */     return getPort(new QName("http://eoos-technologies.com/gm/t2w/euro5", "E5ServicePort"), E5ServicePort.class, features);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\e5\common\gen\E5Service.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */