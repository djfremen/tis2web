/*     */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.sitfilter;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceUtil;
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.observable.Notification;
/*     */ import com.eoos.observable.ObservableSupport;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIT
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(SIT.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */ 
/*     */ 
/*     */   
/*  44 */   private ObservableSupport observableSupport = new ObservableSupport();
/*     */   
/*     */   private CTOCNode selectedSIT;
/*     */   
/*  48 */   private Set positiv_ACL_SITs = null;
/*     */ 
/*     */   
/*     */   public SIT(ClientContext context) {
/*  52 */     this.context = context;
/*     */   }
/*     */   
/*     */   public static SIT getInstance(ClientContext context) {
/*  56 */     synchronized (context.getLockObject()) {
/*  57 */       SIT instance = (SIT)context.getObject(SIT.class);
/*  58 */       if (instance == null) {
/*  59 */         instance = new SIT(context);
/*  60 */         context.storeObject(SIT.class, instance);
/*     */       } 
/*  62 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSelectedSIT(CTOCNode selectedSIT) {
/*  67 */     if ((this.selectedSIT == null) ? (selectedSIT != null) : !this.selectedSIT.equals(selectedSIT)) {
/*  68 */       this.selectedSIT = selectedSIT;
/*  69 */       this.observableSupport.notifyObservers(new Notification() {
/*     */             public void notify(Object observer) {
/*     */               try {
/*  72 */                 ((SIT.Observer)observer).onChange(SIT.this.selectedSIT);
/*  73 */               } catch (Exception e) {}
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCNode getSelectedSIT() {
/*  81 */     return this.selectedSIT;
/*     */   } public static interface Observer {
/*     */     void onChange(CTOCNode param1CTOCNode); }
/*     */   public synchronized Set getPositiv_ACL_SITs() {
/*  85 */     if (this.positiv_ACL_SITs == null) {
/*     */       try {
/*  87 */         ACLService aclMI = ACLServiceProvider.getInstance().getService();
/*  88 */         SharedContextProxy scp = SharedContextProxy.getInstance(this.context);
/*  89 */         this.positiv_ACL_SITs = aclMI.getAuthorizedResources("SIT", scp.getUsrGroup2Manuf(), scp.getCountry());
/*  90 */       } catch (Exception e) {
/*  91 */         log.error("unable to retrieve SIT positiv list from ACL module - error:" + e, e);
/*  92 */         throw new RuntimeException();
/*     */       } 
/*     */     }
/*  95 */     return this.positiv_ACL_SITs;
/*     */   }
/*     */   
/*     */   private List applySecurityFilter(List<?> sits) {
/*  99 */     final Set validSITs = getPositiv_ACL_SITs();
/*     */     
/* 101 */     SIDataAdapterFacade.getInstance(this.context).getSICTOCService();
/* 102 */     Filter filter = new Filter() {
/*     */         public boolean include(Object obj) {
/*     */           try {
/* 105 */             CTOCNode node = (CTOCNode)obj;
/* 106 */             if (validSITs.contains(String.valueOf(CTOCServiceUtil.extractSITKey(node)))) {
/* 107 */               return true;
/*     */             }
/* 109 */             return false;
/*     */           }
/* 111 */           catch (Exception e) {
/* 112 */             SIT.log.warn("unable to determine inclusion state for sit, excluding - exception: " + e, e);
/* 113 */             return false;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 118 */     return (sits != null) ? (List)CollectionUtil.filterAndReturn(new LinkedList(sits), filter) : null;
/*     */   }
/*     */   
/*     */   public synchronized List getSITs(CTOCNode node) {
/* 122 */     SharedContextProxy scp = SharedContextProxy.getInstance(this.context);
/* 123 */     SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(this.context).getSICTOCService();
/* 124 */     VCR vcr = SIDataAdapterFacade.getInstance(this.context).getLVCAdapter().toVCR(VCFacade.getInstance(this.context).getCfg());
/* 125 */     if (vcr == null)
/* 126 */       vcr = VCR.NULL; 
/* 127 */     return applySecurityFilter(node.filterSITs(siCTOCService.getCTOC().getCTOC(CTOCDomain.SIT), scp.getLocaleInfo(), scp.getCountry(), vcr));
/*     */   }
/*     */   
/*     */   public synchronized List getSITS() {
/*     */     try {
/* 132 */       List ret = SIDataAdapterFacade.getInstance(this.context).getSICTOCService().getCTOC().getSITS();
/* 133 */       CollectionUtil.unify(ret, new Transforming()
/*     */           {
/*     */             public Object transform(Object object) {
/* 136 */               return CTOCServiceUtil.extractSITKey((CTOCNode)object);
/*     */             }
/*     */           });
/* 139 */       return Util.isNullOrEmpty(ret) ? null : applySecurityFilter(ret);
/* 140 */     } catch (Exception e) {
/* 141 */       log.error("unable to retrieve sits, returning null - exception: " + e, e);
/* 142 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ObservableSupport getObserverFacade() {
/* 147 */     return this.observableSupport;
/*     */   }
/*     */   
/*     */   public static List convertToStringList(List ctocSITs) {
/* 151 */     List<Object> retValue = new LinkedList();
/* 152 */     if (ctocSITs != null) {
/* 153 */       Iterator<CTOCNode> iter = ctocSITs.iterator();
/* 154 */       while (iter.hasNext()) {
/* 155 */         CTOCNode tmp = iter.next();
/* 156 */         retValue.add(tmp.getProperty((SITOCProperty)CTOCProperty.SIT));
/*     */       } 
/*     */     } 
/* 159 */     return retValue;
/*     */   }
/*     */   
/*     */   public static String convertToString(CTOCNode sit) {
/* 163 */     return (String)sit.getProperty((SITOCProperty)CTOCProperty.SIT);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\sitfilter\SIT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */