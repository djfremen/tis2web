/*    */ package com.eoos.gm.tis2web.frame.ws.lt.common.generated;
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
/*    */ 
/*    */ @WebServiceClient(name = "LTService", targetNamespace = "http://www.eoos-technologies.com/gm/t2w/lt", wsdlLocation = "file:/home/mh/tmp/wsimport-generated/LaborOp.wsdl")
/*    */ public class LTService
/*    */   extends Service
/*    */ {
/*    */   private static final URL LTSERVICE_WSDL_LOCATION;
/* 27 */   private static final Logger logger = Logger.getLogger(LTService.class.getName());
/*    */   
/*    */   static {
/* 30 */     URL url = null;
/*    */     
/*    */     try {
/* 33 */       URL baseUrl = LTService.class.getResource(".");
/* 34 */       url = new URL(baseUrl, "file:/home/mh/tmp/wsimport-generated/LaborOp.wsdl");
/* 35 */     } catch (MalformedURLException e) {
/* 36 */       logger.warning("Failed to create URL for the wsdl Location: 'file:/home/mh/tmp/wsimport-generated/LaborOp.wsdl', retrying as a local file");
/* 37 */       logger.warning(e.getMessage());
/*    */     } 
/* 39 */     LTSERVICE_WSDL_LOCATION = url;
/*    */   }
/*    */   
/*    */   public LTService(URL wsdlLocation, QName serviceName) {
/* 43 */     super(wsdlLocation, serviceName);
/*    */   }
/*    */   
/*    */   public LTService() {
/* 47 */     super(LTSERVICE_WSDL_LOCATION, new QName("http://www.eoos-technologies.com/gm/t2w/lt", "LTService"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @WebEndpoint(name = "LTServicePort")
/*    */   public LTServicePort getLTServicePort() {
/* 57 */     return getPort(new QName("http://www.eoos-technologies.com/gm/t2w/lt", "LTServicePort"), LTServicePort.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @WebEndpoint(name = "LTServicePort")
/*    */   public LTServicePort getLTServicePort(WebServiceFeature... features) {
/* 69 */     return getPort(new QName("http://www.eoos-technologies.com/gm/t2w/lt", "LTServicePort"), LTServicePort.class, features);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\ws\lt\common\generated\LTService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */