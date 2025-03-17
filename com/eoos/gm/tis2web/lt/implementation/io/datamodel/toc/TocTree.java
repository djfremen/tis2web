/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.html.element.input.tree.TreeControl;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TocTree
/*     */   implements TreeControl, LTClientContext.LTClientContextObserver
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(TocTree.class);
/*     */   
/*  42 */   private List roots = new LinkedList();
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  46 */   private Map idToNode = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  48 */   private Map childToParent = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*  50 */   private Object selectedNode = null;
/*     */   
/*  52 */   private Set expandedNodes = new HashSet();
/*     */   
/*  54 */   protected Set observers = Collections.synchronizedSet(new HashSet());
/*     */ 
/*     */   
/*     */   private TocTree(ClientContext context) {
/*  58 */     this.context = context;
/*     */     
/*  60 */     LTClientContext.getInstance(context).addObserver(this);
/*  61 */     onVehicleChange(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addObserver(Observer observer) {
/*  66 */     this.observers.add(observer);
/*     */   }
/*     */   
/*     */   public void onVehicleChange(boolean bNewMC) {
/*  70 */     if (bNewMC) {
/*  71 */       VCR vcr = LTClientContext.getInstance(this.context).getVCRObject();
/*  72 */       if (vcr != null) {
/*  73 */         LTCTOCService service = LTDataAdapterFacade.getInstance(this.context).getLTCTOCService();
/*  74 */         List ctocNodes = service.getCTOC().getCTOCs();
/*  75 */         this.roots.clear();
/*  76 */         for (Iterator<CTOCNode> it = ctocNodes.iterator(); it.hasNext(); ) {
/*     */           
/*  78 */           CTOCNode oN = it.next();
/*  79 */           if (oN.getVCR() != null && oN.getVCR().match(vcr)) {
/*  80 */             this.roots = new LinkedList(oN.getChildren());
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*  85 */       setSelectedNode(null);
/*  86 */       this.expandedNodes.clear();
/*  87 */       this.idToNode.clear();
/*  88 */       this.childToParent.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static TocTree getInstance(ClientContext context) {
/*  93 */     synchronized (context.getLockObject()) {
/*  94 */       TocTree instance = (TocTree)context.getObject(TocTree.class);
/*  95 */       if (instance == null) {
/*  96 */         instance = new TocTree(context);
/*  97 */         context.storeObject(TocTree.class, instance);
/*     */       } 
/*  99 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List getRoots() {
/* 104 */     return this.roots;
/*     */   }
/*     */   
/*     */   public List getChilds(Object node) {
/*     */     try {
/* 109 */       List<SITOCElement> retValue = ((SITOCElement)node).getChildren();
/* 110 */       if (!(node instanceof CTOCNode)) {
/* 111 */         synchronized (this.childToParent) {
/* 112 */           if (!this.childToParent.values().contains(node)) {
/* 113 */             for (int i = 0; i < retValue.size(); i++) {
/* 114 */               SITOCElement child = retValue.get(i);
/* 115 */               this.childToParent.put(child, node);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/* 120 */       SharedContextProxy.getInstance(this.context);
/* 121 */       VCR vcr = LTClientContext.getInstance(this.context).getVCRObject();
/* 122 */       retValue = applyFilterVCR(vcr, retValue);
/*     */       
/* 124 */       return retValue;
/* 125 */     } catch (Exception e) {
/* 126 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List applyFilterVCR(VCR vcr, List<SITOCElement> children) {
/*     */     try {
/* 133 */       List<SITOCElement> result = new LinkedList();
/* 134 */       if (children != null) {
/* 135 */         LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(this.context.getLocale());
/* 136 */         for (int i = 0; i < children.size(); i++) {
/* 137 */           SITOCElement child = children.get(i);
/* 138 */           if (vcr != null && vcr != VCR.NULL && child.getVCR() != null && !child.getVCR().match(locale, vcr)) {
/* 139 */             log.debug("non-matching vcr = " + child.getVCR());
/*     */           } else {
/*     */             
/* 142 */             result.add(child);
/*     */           } 
/*     */         } 
/* 145 */       }  return result;
/* 146 */     } catch (Exception e) {
/* 147 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getIdentifier(Object node) {
/* 153 */     Object identifier = ((SITOCElement)node).getID();
/* 154 */     this.idToNode.put(identifier, node);
/* 155 */     return identifier;
/*     */   }
/*     */   
/*     */   public String getLabel(Object node) {
/* 159 */     String retValue = "";
/* 160 */     if (node instanceof SIOLT) {
/* 161 */       SIOLT elem = (SIOLT)node;
/* 162 */       retValue = elem.getDisplay(LTClientContext.getInstance(this.context).getLocale());
/*     */     } else {
/* 164 */       retValue = ((SITOCElement)node).getLabel(LTClientContext.getInstance(this.context).getLocale());
/*     */     } 
/* 166 */     return Util.escapeReservedHTMLChars(retValue);
/*     */   }
/*     */   
/*     */   public Object getNode(Object identifier) {
/* 170 */     return this.idToNode.get(identifier);
/*     */   }
/*     */   
/*     */   public Object getSelectedNode() {
/* 174 */     return this.selectedNode;
/*     */   }
/*     */   
/*     */   public List getSelectedPath() {
/* 178 */     List<Object> retValue = new LinkedList();
/* 179 */     Object node = getSelectedNode();
/* 180 */     while (node != null && !(node instanceof com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement)) {
/* 181 */       retValue.add(0, node);
/* 182 */       node = getParent(node);
/*     */     } 
/* 184 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean isLeaf(Object node) {
/* 188 */     List childs = getChilds(node);
/* 189 */     return (childs == null || childs.size() == 0);
/*     */   }
/*     */   
/*     */   public void setSelectedNode(Object node) {
/*     */     boolean changed;
/* 194 */     if (this.selectedNode == null) {
/* 195 */       changed = (node != null);
/*     */     } else {
/* 197 */       changed = !this.selectedNode.equals(node);
/*     */     } 
/*     */     
/* 200 */     this.selectedNode = node;
/*     */     
/* 202 */     if (changed) {
/* 203 */       synchronized (this.observers) {
/* 204 */         for (Iterator<Observer> iter = this.observers.iterator(); iter.hasNext();) {
/* 205 */           ((Observer)iter.next()).onSelectionChanged();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExpanded(Object node) {
/* 213 */     return this.expandedNodes.contains(node);
/*     */   }
/*     */   
/*     */   public void toggleExpanded(Object node) {
/* 217 */     if (this.expandedNodes.contains(node)) {
/* 218 */       this.expandedNodes.remove(node);
/*     */     } else {
/* 220 */       this.expandedNodes.add(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setExpanded(Object node, boolean expanded) {
/* 225 */     if (expanded) {
/* 226 */       this.expandedNodes.add(node);
/*     */     } else {
/* 228 */       this.expandedNodes.remove(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 233 */     if (node instanceof CTOCNode) {
/* 234 */       return ((CTOCNode)node).getParent();
/*     */     }
/* 236 */     return this.childToParent.get(node);
/*     */   }
/*     */   
/*     */   public static interface Observer {
/*     */     void onSelectionChanged();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\toc\TocTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */