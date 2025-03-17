/*    */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.Pair;
/*    */ import com.eoos.datatype.gtwo.PairImpl;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT1DataHandler;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.ITestDriverSettings;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TxtVIT1DataHandlerImpl
/*    */   implements VIT1DataHandler
/*    */ {
/* 19 */   private static Logger log = Logger.getLogger(ECUDataHandler.class);
/*    */   
/*    */   public List extractVIT1Data(List<String> lstVIT1, ITestDriverSettings settings) {
/* 22 */     List<Pair> vit1Attrs = new ArrayList();
/* 23 */     List<Pair> lstCMBs = new ArrayList();
/* 24 */     List<Pair> lstVIT1Header = new ArrayList();
/*    */     
/* 26 */     for (int i = 0; i < lstVIT1.size(); i++) {
/* 27 */       String line = lstVIT1.get(i);
/* 28 */       line = line.trim();
/* 29 */       if (line.length() != 0) {
/*    */ 
/*    */         
/* 32 */         Pair attr = extractVIT1Attribute(line);
/* 33 */         if (attr == null) {
/* 34 */           log.debug("check VIT1: " + line);
/*    */         }
/* 36 */         if ("sps_id".equals(attr.getFirst()) || "chksum".equals(attr.getFirst()) || "table_len".equals(attr.getFirst()) || "nav_info".equals(attr.getFirst()) || "reserved".equals(attr.getFirst()) || "numcms".equals(attr.getFirst()) || "devicetype".equals(attr.getFirst()) || "spsmode".equals(attr.getFirst()) || "config_area_size".equals(attr.getFirst())) {
/* 37 */           lstVIT1Header.add(attr);
/*    */         } else {
/* 39 */           lstCMBs.add(attr);
/*    */         } 
/*    */       } 
/* 42 */     }  vit1Attrs.addAll(lstVIT1Header);
/* 43 */     vit1Attrs.addAll(lstCMBs);
/* 44 */     return vit1Attrs;
/*    */   }
/*    */   private Pair extractVIT1Attribute(String line) {
/*    */     PairImpl pairImpl;
/* 48 */     String attrName = extractAttrName(line);
/* 49 */     String attrVal = extractAttrValue(line);
/* 50 */     Pair vit1Attr = null;
/* 51 */     if (attrName != null && attrVal != null) {
/* 52 */       pairImpl = new PairImpl(attrName, attrVal);
/*    */     }
/* 54 */     return (Pair)pairImpl;
/*    */   }
/*    */   
/*    */   private String extractAttrName(String line) {
/* 58 */     int indx = line.indexOf("=");
/* 59 */     if (indx > 0) {
/* 60 */       String attrDesc = line.substring(0, indx);
/* 61 */       attrDesc = trim(attrDesc);
/* 62 */       return attrDesc;
/*    */     } 
/* 64 */     return null;
/*    */   }
/*    */   
/*    */   private String extractAttrValue(String line) {
/* 68 */     int indx = line.indexOf("=");
/* 69 */     if (indx > 0) {
/* 70 */       String attrVal = line.substring(indx + 1);
/* 71 */       attrVal = trim(attrVal);
/* 72 */       return attrVal;
/*    */     } 
/* 74 */     return "";
/*    */   }
/*    */   
/*    */   private String trim(String org) {
/* 78 */     String orgStr = org;
/* 79 */     while (orgStr.startsWith("\t") || orgStr.startsWith(" ")) {
/* 80 */       orgStr = orgStr.substring(1);
/*    */     }
/* 82 */     while (orgStr.endsWith("\t") || orgStr.endsWith(" ")) {
/* 83 */       orgStr = orgStr.substring(0, orgStr.length() - 1);
/*    */     }
/* 85 */     return orgStr;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\TxtVIT1DataHandlerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */