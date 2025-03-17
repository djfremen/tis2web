/*    */ package com.eoos.gm.tis2web.ctoc.implementation.service;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.ctoc.implementation.common.ConstraintFactory;
/*    */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCSurrogateImpl;
/*    */ import com.eoos.gm.tis2web.ctoc.implementation.common.domain.IOFactoryImpl;
/*    */ import com.eoos.gm.tis2web.ctoc.service.CTOCService;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCSurrogate;
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*    */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CTOCServiceImpl
/*    */   implements CTOCService, Configurable
/*    */ {
/* 29 */   private static final Logger log = Logger.getLogger(CTOCServiceImpl.class);
/*    */   
/*    */   protected SI.Retrieval siRetrieval;
/*    */   
/*    */   protected ILVCAdapter.Retrieval lvcRetrieval;
/*    */   
/*    */   protected FTSService.Retrieval ftsRetrieval;
/*    */ 
/*    */   
/*    */   public CTOCServiceImpl(SI.Retrieval siRetrieval, ILVCAdapter.Retrieval lvcRetrieval, FTSService.Retrieval ftsRetrieval) {
/* 39 */     this.siRetrieval = siRetrieval;
/* 40 */     this.lvcRetrieval = lvcRetrieval;
/* 41 */     this.ftsRetrieval = ftsRetrieval;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 45 */     return null;
/*    */   }
/*    */   
/*    */   public CTOCSurrogate createCTOCSurrogate() {
/* 49 */     return (CTOCSurrogate)new CTOCSurrogateImpl(this.siRetrieval, this.lvcRetrieval, this.ftsRetrieval);
/*    */   }
/*    */   
/*    */   public CTOCSurrogate createCTOCSurrogate(CTOCElement element) {
/* 53 */     return (CTOCSurrogate)new CTOCSurrogateImpl(element, this.siRetrieval, this.lvcRetrieval, this.ftsRetrieval);
/*    */   }
/*    */   
/*    */   public VCR makeConstraintVCR(LocaleInfo locale, String application, Set sits, Set manufacturers, Set groups, String country) {
/* 57 */     return ConstraintFactory.makeConstraintVCR(locale, application, sits, manufacturers, groups, country, this.lvcRetrieval);
/*    */   }
/*    */   
/*    */   public VCR extendConstraintVCR(VCR vcr, int dom, String value) {
/* 61 */     return ConstraintFactory.extendConstraintVCR(vcr, dom, value, this.lvcRetrieval);
/*    */   }
/*    */   
/*    */   public IOFactory createIOFactory(IDatabaseLink dblink) {
/*    */     try {
/* 66 */       return (IOFactory)new IOFactoryImpl(dblink);
/* 67 */     } catch (Exception e) {
/* 68 */       log.error("unable to create IOFactoryImpl -exception:" + e);
/* 69 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public CTOC getCTOC() {
/* 74 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 78 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\service\CTOCServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */