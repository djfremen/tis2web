/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class VITLabelResource {
/* 11 */   private static final Logger log = Logger.getLogger(VITLabelResource.class);
/* 12 */   private Properties properties = null;
/*    */   
/*    */   public VITLabelResource() {
/* 15 */     InputStream ios = null;
/*    */     try {
/* 17 */       ios = getClass().getClassLoader().getResourceAsStream("com/eoos/gm/tis2web/sps/client/tool/testdriver/impl/settings/vit1keys.properties");
/* 18 */       this.properties = new Properties();
/* 19 */       this.properties.load(ios);
/* 20 */     } catch (IOException e) {
/* 21 */       log.error("failed to load vit1 mappings", e);
/*    */     } finally {
/* 23 */       if (ios != null) {
/*    */         try {
/* 25 */           ios.close();
/* 26 */         } catch (Exception x) {}
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Map getVITAttrName2Desc() {
/* 33 */     Map<Object, Object> name2desc = new HashMap<Object, Object>();
/*    */     
/* 35 */     Enumeration<?> mappings = this.properties.propertyNames();
/* 36 */     while (mappings.hasMoreElements()) {
/* 37 */       String key = (String)mappings.nextElement();
/* 38 */       if (key.startsWith("vit.")) {
/* 39 */         String attrVal = this.properties.getProperty(key);
/* 40 */         String attrName = key.substring(4);
/* 41 */         name2desc.put(attrName, attrVal);
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return name2desc;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\settings\VITLabelResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */