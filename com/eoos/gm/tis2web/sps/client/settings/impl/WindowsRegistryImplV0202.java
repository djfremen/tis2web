/*    */ package com.eoos.gm.tis2web.sps.client.settings.impl;
/*    */ 
/*    */ import ca.beq.util.win32.registry.RegistryKey;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.common.export.ToolSimpleFactory;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.J2534ToolParams;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsRegistryImplV0202
/*    */   extends WindowsRegistryImpl
/*    */ {
/*    */   private static final String J2534_VERSION = "02.02";
/* 19 */   private static Logger log = Logger.getLogger(WindowsRegistryImplV0202.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List getLocalTools() {
/* 26 */     ArrayList<Tool> result = new ArrayList();
/* 27 */     RegistryKey j2534Key = getJ2534Key();
/* 28 */     if (j2534Key != null) {
/* 29 */       Iterator<RegistryKey> vendorIt = j2534Key.subkeys();
/* 30 */       while (vendorIt.hasNext()) {
/* 31 */         RegistryKey nextVendor = vendorIt.next();
/* 32 */         String toolName = "no-name";
/* 33 */         if (nextVendor.hasSubkey("Devices")) {
/* 34 */           RegistryKey devices = nextVendor.subkeys().next();
/* 35 */           if (devices.hasSubkeys()) {
/* 36 */             Iterator<RegistryKey> deviceIt = devices.subkeys();
/* 37 */             while (deviceIt.hasNext()) {
/* 38 */               RegistryKey nextDevice = deviceIt.next();
/* 39 */               String name = getValue(nextDevice, "Name");
/* 40 */               if (name != null) {
/* 41 */                 toolName = name;
/*    */               }
/* 43 */               String protocols = getValue(nextDevice, "ProtocolsSupported");
/* 44 */               String dllPath = getValue(nextDevice, "FunctionLibrary");
/* 45 */               String configApplication = getValue(nextDevice, "ConfigApplication");
/* 46 */               log.info("Found local J2534 tool: " + name + ", " + protocols + ", " + configApplication);
/* 47 */               J2534ToolParams toolParams = new J2534ToolParams(name, "02.02", protocols, dllPath, configApplication, null);
/* 48 */               Tool tool = ToolSimpleFactory.getInstance().createTool("PT_J2534", toolParams);
/* 49 */               result.add(tool);
/*    */             }  continue;
/*    */           } 
/* 52 */           log.warn("Skipping J2534 tool due to invalid registry entries: " + toolName);
/*    */           
/*    */           continue;
/*    */         } 
/* 56 */         log.warn("Skipping J2534 tool due to invalid registry entries: " + toolName);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 61 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\impl\WindowsRegistryImplV0202.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */