/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.NotificationMessageBox;
/*     */ import com.eoos.gm.tis2web.lt.service.LTService;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class AwList
/*     */   extends ListElement
/*     */   implements DataRetrievalAbstraction.DataCallback
/*     */ {
/*     */   private ClientContext context;
/*     */   private HtmlLabel header;
/*     */   private LocaleInfo locale;
/*     */   private AwPage page;
/*  50 */   private static Logger log = Logger.getLogger(AwList.class);
/*     */   
/*     */   public static class LTComparator implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/*  54 */       SIOLT n1 = (SIOLT)o1;
/*  55 */       SIOLT n2 = (SIOLT)o2;
/*  56 */       return n1.getMajorOperationNumber().compareTo(n2.getMajorOperationNumber());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private List data = new LinkedList();
/*     */ 
/*     */ 
/*     */   
/*     */   public AwList(ClientContext context, SIOCPR sioCpr) {
/*  73 */     setDataCallback(this);
/*  74 */     this.context = context;
/*  75 */     this.header = new HtmlLabel(context.getLabel("module.type.lt"));
/*  76 */     this.locale = LocaleInfoProvider.getInstance().getLocale(context.getLocale());
/*  77 */     String es = sioCpr.getElectronicSystemCode().toString();
/*  78 */     if (!LTDataAdapterFacade.getInstance(context).supports(VCFacade.getInstance(context).getCfg())) {
/*     */       return;
/*     */     }
/*  81 */     LTCTOCService ltCTOCService = LTDataAdapterFacade.getInstance(context).getLTCTOCService();
/*     */     
/*  83 */     CTOCNode aws = ltCTOCService.getCTOC().searchMO(es);
/*     */     
/*  85 */     if (aws == null) {
/*     */       return;
/*     */     }
/*  88 */     LTService ltService = (LTService)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/*  89 */     List nodes = aws.getChildren();
/*  90 */     if (nodes != null) {
/*  91 */       Iterator it = nodes.iterator();
/*     */       try {
/*  93 */         ILVCAdapter adapter = SIDataAdapterFacade.getInstance(context).getLVCAdapter();
/*  94 */         VCR vcr = adapter.toVCR(VCFacade.getInstance(context).getCfg());
/*  95 */         Set<String> awNos = new HashSet();
/*  96 */         if (vcr != null) {
/*     */ 
/*     */           
/*  99 */           while (it.hasNext()) {
/* 100 */             Object obj = it.next();
/* 101 */             if (obj instanceof SIOLT) {
/* 102 */               SIOLT sioLt = (SIOLT)obj;
/* 103 */               VCR vcrObj = sioLt.getVCR();
/* 104 */               if (vcrObj != null && 
/* 105 */                 vcrObj.match(vcr) && !awNos.contains(sioLt.getMajorOperationNumber()) && (
/* 106 */                 ltService == null || ltService.isMainWorkValid(context.getSessionID(), sioLt.getMajorOperationNumber()))) {
/* 107 */                 this.data.add(sioLt);
/* 108 */                 awNos.add(sioLt.getMajorOperationNumber());
/*     */               
/*     */               }
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 120 */           this.data.addAll(nodes);
/*     */         } 
/* 122 */         if (this.data.size() > 1) {
/* 123 */           Collections.sort(this.data, new LTComparator());
/*     */         }
/* 125 */       } catch (Exception e) {
/* 126 */         log.error("Failed to process restrictions");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPage(AwPage page) {
/* 132 */     this.page = page;
/*     */   }
/*     */   
/*     */   public List getData() {
/* 136 */     return this.data;
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/* 140 */     return 1;
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/* 144 */     if (data instanceof SIOLT) {
/* 145 */       final SIOLT node = (SIOLT)data;
/* 146 */       return (HtmlElement)new LinkElement(this.context.createID(), null) {
/*     */           protected String getLabel() {
/* 148 */             return node.getMajorOperationNumber() + " " + node.getLabel(AwList.this.locale);
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 152 */             return AwList.this.switchLT(node);
/*     */           }
/*     */         };
/*     */     } 
/* 156 */     return (HtmlElement)new HtmlLabel(((SITOCElement)data).getLabel(this.locale));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object switchLT(SIOLT node) {
/*     */     try {
/* 163 */       VCFacade.getInstance(this.context).getCurrentSalesmake();
/* 164 */       LTService ltMI = (LTService)ConfiguredServiceProvider.getInstance().getService(LTService.class);
/*     */       
/* 166 */       Map usrGrp2Manuf = SharedContextProxy.getInstance(this.context).getUsrGroup2Manuf();
/*     */       
/* 168 */       String country = SharedContextProxy.getInstance(this.context).getCountry();
/*     */       
/* 170 */       boolean authorized = false;
/* 171 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/*     */       
/* 173 */       Set apps = aclMI.getAuthorizedResources("Application", usrGrp2Manuf, country);
/*     */       
/* 175 */       if (apps.contains("lt")) {
/* 176 */         authorized = true;
/*     */       }
/* 178 */       this.context.unregisterDispatchable((Dispatchable)this.page);
/* 179 */       if (ltMI == null)
/* 180 */         return new NotificationMessageBox(this.context, this.context.getLabel("error"), this.context.getMessage("si.lt.no.instance")) {
/*     */             public Object onOK(Map params) {
/* 182 */               return MainPage.getInstance(AwList.this.context);
/*     */             }
/*     */           }; 
/* 185 */       if (!authorized) {
/* 186 */         return MainPage.getInstance(this.context);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       String awNr = node.getMajorOperationNumber();
/*     */       
/*     */       try {
/* 195 */         ltMI.setMainWork(this.context.getSessionID(), awNr);
/* 196 */         return MainPage.getInstance(this.context).switchModule((VisualModule)ltMI);
/* 197 */       } catch (com.eoos.gm.tis2web.lt.service.LTService.LTServiceException e) {
/* 198 */         log.error("unable to set main work " + String.valueOf(awNr) + ", notifying user - exception: " + e, (Throwable)e);
/* 199 */         return new NotificationMessageBox(this.context, this.context.getLabel("error"), this.context.getMessage("si.lt.novc")) {
/*     */             public Object onOK(Map params) {
/* 201 */               return MainPage.getInstance(AwList.this.context);
/*     */             }
/*     */           };
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 208 */     catch (Exception e) {
/* 209 */       HtmlElementContainer htmlElementContainer = getContainer();
/* 210 */       while (htmlElementContainer.getContainer() != null) {
/* 211 */         htmlElementContainer = htmlElementContainer.getContainer();
/*     */       }
/* 213 */       return htmlElementContainer;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 218 */     return (HtmlElement)this.header;
/*     */   }
/*     */   
/*     */   protected boolean enableHeader() {
/* 222 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\appllinks\AwList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */