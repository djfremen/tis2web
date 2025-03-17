/*    */ package com.eoos.gm.tis2web.ctoc.implementation.common.domain;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.IOCache;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*    */ import java.io.File;
/*    */ 
/*    */ public class IOFactoryImpl
/*    */   implements IOFactory
/*    */ {
/*    */   protected IOCache cache;
/*    */   
/*    */   public IOFactoryImpl(File source) throws Exception {
/* 17 */     this.cache = new IOCache(source);
/*    */   }
/*    */   
/*    */   public IOFactoryImpl(IDatabaseLink dblink) throws Exception {
/* 21 */     this.cache = new IOCache(dblink);
/*    */   }
/*    */   
/*    */   public CTOCFactory getFactory() {
/* 25 */     return (CTOCFactory)this.cache;
/*    */   }
/*    */   
/*    */   public SIO getSIO(Integer sioID) {
/* 29 */     return (SIO)this.cache.getElement(sioID);
/*    */   }
/*    */   
/*    */   public SIOBlob getGraphic(String sioID) {
/* 33 */     return this.cache.getGraphic(sioID);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\domain\IOFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */