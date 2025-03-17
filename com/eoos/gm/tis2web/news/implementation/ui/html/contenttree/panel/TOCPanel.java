/*     */ package com.eoos.gm.tis2web.news.implementation.ui.html.contenttree.panel;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.tree.navigation.TreeNavigation2;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationImpl;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceUtil;
/*     */ import com.eoos.gm.tis2web.ctoc.service.NewsCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.news.implementation.datamodel.domain.toc.NewsTOCControl;
/*     */ import com.eoos.gm.tis2web.news.implementation.datamodel.domain.toc.NewsTOCNavigationSPIImpl;
/*     */ import com.eoos.gm.tis2web.news.implementation.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.tree.gtwo.TreeControl;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TOCPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  53 */   private static final Logger log = Logger.getLogger(TOCPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  58 */       template = ApplicationContext.getInstance().loadFile(TOCPanel.class, "tocpanel.html", null).toString();
/*  59 */     } catch (Exception e) {
/*  60 */       log.error("unable to load template - error:" + e, e);
/*  61 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   protected NewsTOCTreeElement tree;
/*     */   
/*     */   public TOCPanel(ClientContext context, Map parameters, final DocumentPage documentPage) {
/*  71 */     this.context = context;
/*     */     
/*  73 */     NewsCTOCService service = (NewsCTOCService)CTOCServiceProvider.getInstance().getService(CTOCServiceProvider.NEWS);
/*  74 */     CTOCNode superroot = service.getCTOC().getCTOC(CTOCDomain.NEWS);
/*  75 */     CTOCNode root = null;
/*  76 */     if (root == null) {
/*  77 */       root = superroot;
/*     */     }
/*     */     
/*  80 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/*  81 */     LocaleInfo li = scp.getLocaleInfo();
/*  82 */     String country = scp.getCountry();
/*     */     
/*  84 */     Set _sits = null;
/*     */     try {
/*  86 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/*  87 */       _sits = aclMI.getAuthorizedResources("SIT", scp.getUsrGroup2Manuf(), scp.getCountry());
/*  88 */     } catch (Exception e) {
/*  89 */       log.error("unable to retrieve SIT positiv list from ACL module - error:" + e, e);
/*  90 */       throw new RuntimeException();
/*     */     } 
/*     */     
/*  93 */     Set<String> sits = null;
/*     */     
/*  95 */     if (_sits != null) {
/*  96 */       sits = new HashSet(_sits.size());
/*  97 */       Iterator<String> iter = _sits.iterator();
/*  98 */       while (iter.hasNext()) {
/*  99 */         sits.add(CTOCServiceUtil.translateSIT(iter.next()));
/*     */       }
/*     */     } 
/*     */     
/* 103 */     List<String> applications = new LinkedList();
/*     */     try {
/* 105 */       for (Iterator<VisualModule> iter = ConfiguredServiceProvider.getInstance().getServices(VisualModule.class).iterator(); iter.hasNext(); ) {
/* 106 */         VisualModule module = iter.next();
/* 107 */         String moduleType = module.getType();
/* 108 */         applications.add(moduleType);
/*     */       } 
/* 110 */     } catch (Exception e) {
/* 111 */       log.error("unable to retrieve applications for user - error:" + e, e);
/*     */     } 
/*     */     
/* 114 */     Set userGroups = null;
/* 115 */     Set manufacturers = null;
/*     */     try {
/* 117 */       FrameService fmi = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 118 */       Map group2Manufacturer = fmi.getUsrGroup2ManufMap(this.context.getSessionID());
/* 119 */       userGroups = group2Manufacturer.keySet();
/* 120 */       userGroups = (userGroups.size() > 0) ? userGroups : null;
/*     */       
/* 122 */       manufacturers = (Set)CollectionUtil.flattenAndReturn(group2Manufacturer.values(), new LinkedHashSet());
/* 123 */       if (manufacturers != null && manufacturers.size() == 0) {
/* 124 */         manufacturers = null;
/*     */       }
/* 126 */     } catch (Exception e) {
/* 127 */       log.error("unable to retrieve manufacturers and groups for user - error:" + e, e);
/*     */     } 
/*     */     
/* 130 */     log.info("context information: *******");
/* 131 */     log.info("locale: " + li.getLocale());
/* 132 */     log.info("country: " + country);
/* 133 */     StringBuffer apps = new StringBuffer();
/* 134 */     for (int i = 0; i < applications.size(); i++) {
/* 135 */       if (i > 0) {
/* 136 */         apps.append(",");
/*     */       }
/* 138 */       apps.append(applications.get(i));
/*     */     } 
/* 140 */     log.info("applications: " + apps.toString());
/* 141 */     log.info("sits: " + sits);
/* 142 */     log.info("usergroups: " + userGroups);
/* 143 */     log.info("manufacturers: " + manufacturers);
/* 144 */     log.info("******************************");
/*     */     
/* 146 */     VCR vcr2 = service.makeConstraintVCR(li, null, sits, manufacturers, userGroups, country);
/* 147 */     for (int j = 0; j < applications.size(); j++) {
/* 148 */       vcr2 = service.extendConstraintVCR(vcr2, 1, applications.get(j));
/*     */     }
/*     */     
/* 151 */     TreeNavigationImpl treeNavigationImpl = new TreeNavigationImpl((TreeNavigationSPI)new NewsTOCNavigationSPIImpl((SITOCElement)root, vcr2, null));
/* 152 */     NewsTOCControl newsTOCControl = new NewsTOCControl(context, (TreeNavigation2)treeNavigationImpl);
/*     */     
/* 154 */     this.tree = new NewsTOCTreeElement(context, (TreeControl)newsTOCControl) {
/*     */         protected void setPage(SIO node) {
/* 156 */           documentPage.setPage(node);
/*     */         }
/*     */       };
/* 159 */     addElement((HtmlElement)this.tree);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 164 */     StringBuffer code = new StringBuffer(template);
/* 165 */     StringUtilities.replace(code, "{TREE}", this.tree.getHtmlCode(params));
/*     */     
/* 167 */     return code.toString();
/*     */   }
/*     */   
/*     */   public CTOCNode searchPage(CTOC ctoc, CTOCNode root, VCR vcr, String page) {
/* 171 */     return ctoc.searchByProperty(root, CTOCProperty.Page, page, vcr);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementatio\\ui\html\contenttree\panel\TOCPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */