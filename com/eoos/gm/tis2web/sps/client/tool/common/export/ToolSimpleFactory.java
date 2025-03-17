/*    */ package com.eoos.gm.tis2web.sps.client.tool.common.export;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.J2534Tool;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.legacy.LegacyTech2PT;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.tech2remote.impl.Tech2Remote;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.TestDriverImpl;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToolSimpleFactory
/*    */ {
/* 13 */   private static ToolSimpleFactory instance = null;
/* 14 */   private Logger log = Logger.getLogger(ToolSimpleFactory.class);
/*    */   
/*    */   public static synchronized ToolSimpleFactory getInstance() {
/* 17 */     if (instance == null) {
/* 18 */       instance = new ToolSimpleFactory();
/*    */     }
/* 20 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Tool createTool(String type, Object toolParams) {
/*    */     Tech2Remote tech2Remote;
/* 27 */     Tool result = null;
/* 28 */     if (type.compareTo("PT_J2534") == 0) {
/* 29 */       J2534Tool j2534Tool = new J2534Tool(toolParams);
/* 30 */     } else if (type.compareTo("PT_LEGACY") == 0) {
/* 31 */       LegacyTech2PT legacyTech2PT = new LegacyTech2PT();
/* 32 */     } else if (type.compareTo("TEST_DRIVER") == 0) {
/* 33 */       TestDriverImpl testDriverImpl = new TestDriverImpl();
/* 34 */     } else if (type.compareTo("T2_REMOTE") == 0) {
/* 35 */       tech2Remote = new Tech2Remote();
/*    */     } else {
/* 37 */       this.log.error("Unknown tool creation request for type: " + type);
/*    */     } 
/* 39 */     return (Tool)tech2Remote;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\ToolSimpleFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */