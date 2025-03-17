/*     */ package com.eoos.gm.tis2web.si.v2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCImplBase;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNodeBase;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCSurrogate;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class SICTOCServiceChain
/*     */   implements SICTOCService
/*     */ {
/*  34 */   private static final Logger log = Logger.getLogger(SICTOCServiceChain.class);
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
/*     */   public SICTOCServiceChain(Collection delegates) {
/* 156 */     this.SYNC_CFGS = new Object();
/* 157 */     this.configurations = null; this.delegates = delegates;
/*     */   }
/*     */   public CTOCSurrogate createCTOCSurrogate() { throw new UnsupportedOperationException(); }
/* 160 */   public CTOCSurrogate createCTOCSurrogate(CTOCElement element) { throw new UnsupportedOperationException(); } public IOFactory createIOFactory(IDatabaseLink dblink) { throw new UnsupportedOperationException(); } public VCR extendConstraintVCR(VCR vcr, int dom, String value) { throw new UnsupportedOperationException(); } public Set getConfigurations() { synchronized (this.SYNC_CFGS)
/* 161 */     { if (this.configurations == null) {
/* 162 */         log.debug("initializing supported vehicle configurations...");
/* 163 */         this.configurations = new HashSet((Collection)CollectionUtil.foreach(this.delegates, new CollectionUtil.ForeachCallback()
/*     */               {
/*     */                 public Object executeOperation(Object item) throws Exception {
/* 166 */                   return null;
/*     */                 }
/*     */               }));
/* 169 */         log.debug("...done");
/*     */       } 
/* 171 */       return this.configurations; }  }
/*     */   public CTOC getCTOC() { return (CTOC)new CTOCImplBase() {
/*     */         public List getSITS() { LinkedHashSet<?> ret = new LinkedHashSet(); for (Iterator<SIDataAdapter> iter = SICTOCServiceChain.this.delegates.iterator(); iter.hasNext();) ret.addAll(((SIDataAdapter)iter.next()).getSICTOCService().getCTOC().getSITS());  return new ArrayList(ret); } public List getDTCs() { LinkedHashSet<?> ret = new LinkedHashSet(); for (Iterator<SIDataAdapter> iter = SICTOCServiceChain.this.delegates.iterator(); iter.hasNext(); ) { List dtcs = ((SIDataAdapter)iter.next()).getSICTOCService().getCTOC().getDTCs(); if (dtcs != null) ret.addAll(dtcs);  }  return new ArrayList(ret); } public CTOCNode getCTOC(VCR vcr) { CTOCNode ret = null; for (Iterator<SIDataAdapter> iter = SICTOCServiceChain.this.delegates.iterator(); iter.hasNext(); ) { ret = ((SIDataAdapter)iter.next()).getSICTOCService().getCTOC().getCTOC(vcr); if (ret != null) return ret;  }  return ret; } public CTOCNode getCTOC(CTOCDomain domain) { final Map<Object, Object> children = new LinkedHashMap<Object, Object>(); for (Iterator<SIDataAdapter> iter = SICTOCServiceChain.this.delegates.iterator(); iter.hasNext(); ) { CTOCNode root = ((SIDataAdapter)iter.next()).getSICTOCService().getCTOC().getCTOC(domain); if (root != null && root.getChildren() != null) for (Iterator it = root.getChildren().iterator(); it.hasNext(); ) { Object child = it.next(); if (child instanceof CTOCNode) { if (!children.containsKey(((CTOCNode)child).getID())) children.put(((CTOCNode)child).getID(), child);  continue; }  if (child instanceof com.eoos.gm.tis2web.si.service.cai.SIO) children.put(child, child);  }   }  return Util.isNullOrEmpty(children) ? null : (CTOCNode)new CTOCNodeBase() {
/*     */               public List getChildren() { return new ArrayList(children.values()); } public boolean isSIO() { return false; } public String getLabel(LocaleInfo locale) { return "SUPER"; } public Integer getID() { return Integer.valueOf(getLabel(null).hashCode()); }
/*     */             }; }
/* 176 */       }; } public VCR makeConstraintVCR(LocaleInfo locale, String application, Set sits, Set manufacturers, Set groups, String country) { throw new UnsupportedOperationException(); } public void reset() {} public Object getIdentifier() { throw new UnsupportedOperationException(); } public Set getKeys() { return VehicleConfigurationUtil.KEY_SET; }
/*     */ 
/*     */   
/*     */   public long getLastModified() {
/* 180 */     return 0L;
/*     */   }
/*     */   
/*     */   public boolean supports(IConfiguration cfg) {
/* 184 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\v2\SICTOCServiceChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */