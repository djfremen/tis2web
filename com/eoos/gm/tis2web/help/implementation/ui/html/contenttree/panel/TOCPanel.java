/*     */ package com.eoos.gm.tis2web.help.implementation.ui.html.contenttree.panel;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.tree.navigation.TreeNavigation2;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationImpl;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceUtil;
/*     */ import com.eoos.gm.tis2web.ctoc.service.HelpCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.service.VisualModule;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.domain.toc.HelpTOCControl;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.domain.toc.HelpTOCNavigationSPIImpl;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.domain.toc.Node;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.tree.gtwo.TreeControl;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
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
/*     */ 
/*     */ public class TOCPanel
/*     */   extends HtmlElementContainerBase
/*     */ {
/*  55 */   private static final Logger log = Logger.getLogger(TOCPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  60 */       template = ApplicationContext.getInstance().loadFile(TOCPanel.class, "tocpanel.html", null).toString();
/*  61 */     } catch (Exception e) {
/*  62 */       log.error("unable to load template - error:" + e, e);
/*  63 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   protected HelpTOCTreeElement tree;
/*     */   
/*     */   public TOCPanel(ClientContext context, Map parameters, final DocumentPage documentPage) {
/*  73 */     this.context = context;
/*     */     
/*  75 */     SharedContextProxy scp = SharedContextProxy.getInstance(context);
/*  76 */     LocaleInfo li = scp.getLocaleInfo();
/*  77 */     String moduleType = (String)parameters.get("moduleid");
/*  78 */     String pageIdentifier = (String)parameters.get("pageid");
/*     */     
/*  80 */     Set _sits = null;
/*     */     try {
/*  82 */       ACLService aCLService = ACLServiceProvider.getInstance().getService();
/*  83 */       _sits = aCLService.getAuthorizedResources("SIT", scp.getUsrGroup2Manuf(), scp.getCountry());
/*  84 */     } catch (Exception e) {
/*  85 */       log.error("unable to retrieve SIT positiv list from ACL module - error:" + e, e);
/*  86 */       throw new RuntimeException();
/*     */     } 
/*     */     
/*  89 */     HelpCTOCService service = (HelpCTOCService)CTOCServiceProvider.getInstance().getService(CTOCServiceProvider.HELP);
/*  90 */     Object sit = parameters.get("sit");
/*  91 */     if (sit != null) {
/*  92 */       if (sit instanceof String) {
/*  93 */         sit = CTOCServiceUtil.translateSIT((String)sit);
/*  94 */       } else if (sit instanceof Collection && ((Collection)sit).iterator().hasNext()) {
/*  95 */         sit = ((Collection)sit).iterator().next();
/*  96 */         if (sit != null && sit instanceof String) {
/*  97 */           sit = CTOCServiceUtil.translateSIT((String)sit);
/*     */         }
/*     */       } else {
/* 100 */         sit = null;
/*     */       } 
/*     */     }
/*     */     
/* 104 */     Set<String> sits = null;
/*     */     
/* 106 */     if (_sits != null) {
/* 107 */       sits = new HashSet(_sits.size());
/* 108 */       Iterator<String> iter = _sits.iterator();
/* 109 */       while (iter.hasNext()) {
/* 110 */         String s = CTOCServiceUtil.translateSIT(iter.next());
/* 111 */         if (sit != null && !sit.equals(s)) {
/*     */           continue;
/*     */         }
/* 114 */         sits.add(s);
/*     */       } 
/*     */     } 
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
/* 131 */     String country = scp.getCountry();
/*     */     
/* 133 */     Set userGroups = null;
/* 134 */     Set manufacturers = null;
/*     */     
/*     */     try {
/* 137 */       FrameService fmi = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 138 */       Map group2Manufacturer = fmi.getUsrGroup2ManufMap(this.context.getSessionID());
/* 139 */       userGroups = group2Manufacturer.keySet();
/* 140 */       userGroups = (userGroups.size() > 0) ? userGroups : null;
/*     */       
/* 142 */       manufacturers = (Set)CollectionUtil.flattenAndReturn(group2Manufacturer.values(), new LinkedHashSet());
/* 143 */       if (manufacturers != null && manufacturers.size() == 0) {
/* 144 */         manufacturers = null;
/*     */       }
/* 146 */     } catch (Exception e) {
/* 147 */       log.error("unable to retrieve manufacturers and groups for user - error:" + e, e);
/*     */     } 
/*     */     
/* 150 */     log.info("context information: *******");
/* 151 */     log.info("locale: " + li.getLocale());
/* 152 */     log.info("moduleType: " + moduleType);
/* 153 */     log.info("pageID: " + pageIdentifier);
/* 154 */     log.info("sits: " + sits);
/* 155 */     log.info("country: " + country);
/* 156 */     log.info("usergroups: " + userGroups);
/* 157 */     log.info("manufacturers: " + manufacturers);
/* 158 */     log.info("******************************");
/*     */     
/* 160 */     VCR vcr = service.makeConstraintVCR(li, moduleType, sits, manufacturers, userGroups, country);
/*     */     
/* 162 */     CTOCNode superroot = service.getCTOC().getCTOC(CTOCDomain.HELP);
/* 163 */     SIO selectedNode = searchPage(service.getCTOC(), superroot, vcr, pageIdentifier);
/*     */     
/* 165 */     if (selectedNode == null) {
/* 166 */       log.info("selected node is null, setting node to toc-root");
/*     */     } else {
/*     */       
/* 169 */       log.info("selected node id:" + selectedNode.getID());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 174 */     Set<String> nosits = new HashSet();
/* 175 */     nosits.add("SIT-0");
/* 176 */     VCR vcr2 = service.makeConstraintVCR(li, null, nosits, manufacturers, userGroups, country);
/*     */     
/*     */     try {
/* 179 */       Collection modules = ConfiguredServiceProvider.getInstance().getServices(VisualModule.class);
/* 180 */       for (Iterator<VisualModule> iter = modules.iterator(); iter.hasNext(); ) {
/* 181 */         VisualModule module = iter.next();
/* 182 */         vcr2 = service.extendConstraintVCR(vcr2, 1, module.getType());
/*     */       } 
/* 184 */     } catch (Exception e) {}
/*     */ 
/*     */     
/* 187 */     TreeNavigationImpl treeNavigationImpl = new TreeNavigationImpl((TreeNavigationSPI)new HelpTOCNavigationSPIImpl((SITOCElement)superroot, vcr2, null));
/* 188 */     HelpTOCControl helpTOCControl = new HelpTOCControl(context, (TreeNavigation2)treeNavigationImpl);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     this.tree = new HelpTOCTreeElement(context, (TreeControl)helpTOCControl) {
/*     */         protected void setPage(IIOElement node) {
/* 200 */           documentPage.setPage((SIO)node);
/*     */         }
/*     */       };
/* 203 */     addElement((HtmlElement)this.tree);
/*     */     
/* 205 */     if (selectedNode != null) {
/*     */       try {
/* 207 */         boolean found = false;
/* 208 */         Iterator<Node> iter = treeNavigationImpl.getNodes().iterator();
/* 209 */         while (iter.hasNext() && !found) {
/* 210 */           Node node = iter.next();
/* 211 */           if (node.content.getID().equals(selectedNode.getID())) {
/* 212 */             helpTOCControl.setSelectedNode(node);
/* 213 */             documentPage.setPage((SIO)node.content);
/* 214 */             found = true;
/*     */           } 
/*     */         } 
/*     */         
/* 218 */         iter = helpTOCControl.getSelectedPath().iterator();
/* 219 */         while (iter.hasNext()) {
/* 220 */           this.tree.setExpanded(iter.next(), true);
/*     */         }
/* 222 */       } catch (Exception e) {
/* 223 */         log.warn("unable to select node - error:" + e, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 229 */     StringBuffer code = new StringBuffer(template);
/* 230 */     StringUtilities.replace(code, "{TREE}", this.tree.getHtmlCode(params));
/*     */     
/* 232 */     return code.toString();
/*     */   }
/*     */   
/*     */   public SIO searchPage(CTOC ctoc, CTOCNode root, VCR vcr, String page) {
/* 236 */     CTOCNode result = ctoc.searchByProperty(root, CTOCProperty.Page, page, vcr);
/* 237 */     if (result != null && result.getChildren() != null) {
/* 238 */       return result.getChildren().get(0);
/*     */     }
/* 240 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\contenttree\panel\TOCPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */