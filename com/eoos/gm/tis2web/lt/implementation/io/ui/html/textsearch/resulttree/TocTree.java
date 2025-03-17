/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.textsearch.resulttree;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.ctoc.service.CTOCServiceUtil;
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.NodeComparatorOrder;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.html.element.input.tree.TreeControl;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TocTree
/*     */   implements TreeControl
/*     */ {
/*  44 */   private CTOCNode root = null;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  48 */   private Map idToNode = new HashMap<Object, Object>();
/*     */   
/*  50 */   private Map childToParent = new HashMap<Object, Object>();
/*     */   
/*  52 */   private Object selectedNode = null;
/*     */   
/*  54 */   private Set expandedNodes = new HashSet();
/*     */   
/*     */   private LocaleInfo locale;
/*     */   
/*     */   private String country;
/*     */   
/*     */   private VCR vcr;
/*     */ 
/*     */   
/*     */   public TocTree(ClientContext context, CTOCNode root, LocaleInfo locale, String country, VCR vcr) {
/*  64 */     this.context = context;
/*  65 */     this.root = root;
/*  66 */     this.locale = locale;
/*  67 */     this.country = country;
/*  68 */     this.vcr = vcr;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getRoots() {
/*  73 */     return (this.root != null) ? getChilds(this.root) : new LinkedList();
/*     */   }
/*     */   
/*     */   public List getChilds(Object node) {
/*     */     try {
/*  78 */       List sitFilters = getSecurityFilter(this.root);
/*  79 */       List<?> retValue = ((SITOCElement)node).getChildren(sitFilters, this.locale, this.country, this.vcr);
/*  80 */       if (retValue != null) {
/*  81 */         Collections.sort(retValue, (Comparator<?>)NodeComparatorOrder.getInstance());
/*     */         
/*  83 */         for (int i = 0; i < retValue.size(); i++) {
/*  84 */           SITOCElement child = (SITOCElement)retValue.get(i);
/*  85 */           this.childToParent.put(child, node);
/*     */         } 
/*     */       } 
/*  88 */       return retValue;
/*  89 */     } catch (Exception e) {
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getIdentifier(Object node) {
/*  96 */     Object identifier = ((SITOCElement)node).getID();
/*  97 */     this.idToNode.put(identifier, node);
/*  98 */     return identifier;
/*     */   }
/*     */   
/*     */   public String getLabel(Object node) {
/* 102 */     return ((SITOCElement)node).getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getNode(Object identifier) {
/* 107 */     return this.idToNode.get(identifier);
/*     */   }
/*     */   
/*     */   public Object getSelectedNode() {
/* 111 */     return this.selectedNode;
/*     */   }
/*     */   
/*     */   public List getSelectedPath() {
/* 115 */     List<Object> retValue = new LinkedList();
/* 116 */     Object node = getSelectedNode();
/* 117 */     while (node != null) {
/* 118 */       retValue.add(0, node);
/* 119 */       node = getParent(node);
/*     */     } 
/* 121 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean isLeaf(Object node) {
/* 125 */     List childs = getChilds(node);
/* 126 */     return (childs == null || childs.size() == 0);
/*     */   }
/*     */   
/*     */   public void setSelectedNode(Object node) {
/* 130 */     this.selectedNode = node;
/*     */   }
/*     */   
/*     */   public boolean isExpanded(Object node) {
/* 134 */     return this.expandedNodes.contains(node);
/*     */   }
/*     */   
/*     */   public void toggleExpanded(Object node) {
/* 138 */     if (this.expandedNodes.contains(node)) {
/* 139 */       this.expandedNodes.remove(node);
/*     */     } else {
/* 141 */       this.expandedNodes.add(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setExpanded(Object node, boolean expanded) {
/* 146 */     if (expanded) {
/* 147 */       this.expandedNodes.add(node);
/*     */     } else {
/* 149 */       this.expandedNodes.remove(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 154 */     return this.childToParent.get(node);
/*     */   }
/*     */   
/*     */   protected List getSecurityFilter(CTOCNode node) {
/* 158 */     SharedContextProxy scp = SharedContextProxy.getInstance(this.context);
/* 159 */     String country = scp.getCountry();
/* 160 */     ILVCAdapter adapter = LTDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */     
/* 162 */     VCR vcr = adapter.toVCR(VCFacade.getInstance(this.context).getCfg());
/* 163 */     if (vcr == null) {
/* 164 */       vcr = VCR.NULL;
/*     */     }
/* 166 */     SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(this.context).getSICTOCService();
/* 167 */     List<?> filters = node.filterSITs(siCTOCService.getCTOC().getCTOC(CTOCDomain.SIT), LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()), country, vcr);
/*     */     
/* 169 */     final Set sits = new HashSet();
/*     */     try {
/* 171 */       ACLService aclMI = ACLServiceProvider.getInstance().getService();
/* 172 */       sits.addAll(aclMI.getAuthorizedResources("SIT", scp.getUsrGroup2Manuf(), country));
/* 173 */     } catch (Exception fme) {
/* 174 */       throw new ExceptionWrapper(fme);
/*     */     } 
/*     */     
/* 177 */     Filter filter = new Filter() {
/*     */         public boolean include(Object obj) {
/*     */           try {
/* 180 */             CTOCNode node = (CTOCNode)obj;
/* 181 */             if (sits.contains(String.valueOf(CTOCServiceUtil.extractSITKey(node)))) {
/* 182 */               return true;
/*     */             }
/* 184 */             return false;
/*     */           }
/* 186 */           catch (Exception e) {
/* 187 */             return false;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 192 */     filters = (filters != null) ? new ArrayList(filters) : null;
/* 193 */     return convertSITFilter((List)CollectionUtil.filterAndReturn(filters, filter));
/*     */   }
/*     */   
/*     */   protected List convertSITFilter(List ctocNodeList) {
/* 197 */     List<Object> retValue = new LinkedList();
/*     */     try {
/* 199 */       Iterator<CTOCNode> iter = ctocNodeList.iterator();
/* 200 */       while (iter.hasNext()) {
/* 201 */         CTOCNode tmp = iter.next();
/* 202 */         retValue.add(tmp.getProperty((SITOCProperty)CTOCProperty.SIT));
/*     */       } 
/* 204 */     } catch (NullPointerException e) {}
/*     */ 
/*     */     
/* 207 */     return (retValue.size() == 0) ? null : retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\textsearch\resulttree\TocTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */