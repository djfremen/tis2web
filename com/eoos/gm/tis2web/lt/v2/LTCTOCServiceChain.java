/*     */ package com.eoos.gm.tis2web.lt.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCImplBase;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCSurrogate;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTCTOCServiceChain
/*     */   implements LTCTOCService
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(LTCTOCServiceChain.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection delegates;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object SYNC_CFGS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set configurations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LTCTOCServiceChain(Collection delegates) {
/* 106 */     this.SYNC_CFGS = new Object();
/* 107 */     this.configurations = null; this.delegates = delegates;
/*     */   }
/*     */   public CTOCSurrogate createCTOCSurrogate() { throw new UnsupportedOperationException(); }
/* 110 */   public CTOCSurrogate createCTOCSurrogate(CTOCElement element) { throw new UnsupportedOperationException(); } public IOFactory createIOFactory(IDatabaseLink dblink) { throw new UnsupportedOperationException(); } public VCR extendConstraintVCR(VCR vcr, int dom, String value) { throw new UnsupportedOperationException(); } public Integer extractSITKey(CTOCNode sit) { throw new UnsupportedOperationException(); } public Set getConfigurations() { synchronized (this.SYNC_CFGS)
/* 111 */     { if (this.configurations == null) {
/* 112 */         log.debug("initializing supported vehicle configurations...");
/* 113 */         this.configurations = (Set)CollectionUtil.foreach(this.delegates, new CollectionUtil.ForeachCallback()
/*     */             {
/*     */               public Object executeOperation(Object item) throws Exception {
/* 116 */                 return ((LTCTOCService)item).getConfigurations();
/*     */               }
/*     */             },  CollectionUtil.ForeachCallback.ResultHandling.FLATTENSET);
/* 119 */         log.debug("...done");
/*     */       } 
/* 121 */       return this.configurations; }  }
/*     */   public CTOC getCTOC() { return (CTOC)new CTOCImplBase() {
/*     */         public CTOCNode lookupMO(Integer ctocID) { CTOCNode ret = null; for (Iterator<LTDataAdapter> iter = LTCTOCServiceChain.this.delegates.iterator(); iter.hasNext(); ) { ret = ((LTDataAdapter)iter.next()).getLTCTOCService().getCTOC().lookupMO(ctocID); if (ret != null) return ret;  }  return ret; }
/*     */         public CTOCNode searchMO(String es) { CTOCNode ret = null; for (Iterator<LTDataAdapter> iter = LTCTOCServiceChain.this.delegates.iterator(); iter.hasNext(); ) { ret = ((LTDataAdapter)iter.next()).getLTCTOCService().getCTOC().searchMO(es); if (ret != null) return ret;  }  return ret; }
/*     */       }; }
/* 126 */   public VCR makeConstraintVCR(LocaleInfo locale, String application, Set sits, Set manufacturers, Set groups, String country) { throw new UnsupportedOperationException(); } public void reset() { throw new UnsupportedOperationException(); } public String translateSIT(String sit) { throw new UnsupportedOperationException(); } public Object getIdentifier() { throw new UnsupportedOperationException(); } public boolean supports(IConfiguration cfg) { throw new UnsupportedOperationException(); } public Set getKeys() { return VehicleConfigurationUtil.KEY_SET; }
/*     */ 
/*     */   
/*     */   public long getLastModified() {
/* 130 */     return 0L;
/*     */   }
/*     */   
/*     */   public Set resolveVIN(final VIN vin) throws VIN.InvalidVINException {
/* 134 */     return (Set)CollectionUtil.foreach(this.delegates, new CollectionUtil.ForeachCallback()
/*     */         {
/*     */           public Object executeOperation(Object item) throws Exception {
/* 137 */             return ((LTCTOCService)item).resolveVIN(vin);
/*     */           }
/*     */         },  CollectionUtil.ForeachCallback.ResultHandling.FLATTENSET);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\v2\LTCTOCServiceChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */