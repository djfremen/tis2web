/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import ca.beq.util.win32.registry.RegistryKey;
/*    */ import ca.beq.util.win32.registry.RegistryValue;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolSimpleFactory;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.J2534ToolParams;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsRegistryImplV0404
/*    */   extends WindowsRegistryImpl
/*    */ {
/*    */   private static final String J2534_VERSION = "04.04";
/* 21 */   private static final String[] KNOWN_PROTOCOLS = new String[] { "CAN", "ISO14230", "ISO15765", "ISO9141", "J1850PWM", "J1850VPW", "SCI_A_ENGINE", "SCI_A_TRANS", "SCI_B_ENGINE", "SCI_B_TRANS" };
/* 22 */   private static Logger log = Logger.getLogger(WindowsRegistryImplV0404.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List getLocalTools() {
/* 29 */     ArrayList<Tool> result = new ArrayList();
/* 30 */     RegistryKey j2534Key = getJ2534Key();
/* 31 */     if (j2534Key != null) {
/* 32 */       Iterator<RegistryKey> subIt = j2534Key.subkeys();
/* 33 */       StringBuffer strBuf = new StringBuffer();
/* 34 */       while (subIt.hasNext()) {
/* 35 */         RegistryKey nextKey = subIt.next();
/*    */         try {
/* 37 */           String name = getValue(nextKey, "Name");
/* 38 */           Iterator<RegistryValue> it = nextKey.values();
/* 39 */           while (it.hasNext()) {
/* 40 */             RegistryValue val = it.next();
/* 41 */             String valName = val.getName();
/* 42 */             if (isSupportedProtocol(valName)) {
/* 43 */               strBuf.append("," + valName);
/*    */             }
/*    */           } 
/* 46 */           if (strBuf.length() > 0 && strBuf.charAt(0) == ',') {
/* 47 */             strBuf.delete(0, 1);
/*    */           }
/* 49 */           String protocols = strBuf.toString();
/* 50 */           String driverVersion = getValue(nextKey, "VersionNumber");
/* 51 */           String dllPath = getValue(nextKey, "FunctionLibrary");
/* 52 */           String configApplication = getValue(nextKey, "ConfigApplication");
/* 53 */           log.info("Found local J2534 tool: " + name + ", " + driverVersion + ", " + protocols + ", " + configApplication);
/* 54 */           J2534ToolParams toolParams = new J2534ToolParams(name, "04.04", protocols, dllPath, configApplication, driverVersion);
/* 55 */           Tool tool = ToolSimpleFactory.getInstance().createTool("PT_J2534", toolParams);
/* 56 */           result.add(tool);
/* 57 */         } catch (Exception e) {
/* 58 */           log.error("Error when reading registry key: " + getValue(nextKey, "Name") + ", " + e);
/*    */         } 
/*    */       } 
/*    */     } 
/* 62 */     return result;
/*    */   }
/*    */   
/*    */   private boolean isSupportedProtocol(String str) {
/* 66 */     boolean result = false;
/* 67 */     for (int i = 0; i < KNOWN_PROTOCOLS.length; i++) {
/* 68 */       if (str.toUpperCase(Locale.ENGLISH).compareTo(KNOWN_PROTOCOLS[i]) == 0) {
/* 69 */         result = true;
/*    */         break;
/*    */       } 
/*    */     } 
/* 73 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\WindowsRegistryImplV0404.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */