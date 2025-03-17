/*     */ package com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.html.element.input.tree.TreeControl;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.ArrayList;
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
/*     */ public abstract class CTOCTree
/*     */   implements TreeControl
/*     */ {
/*  34 */   protected static Logger log = Logger.getLogger(CTOCTree.class);
/*     */   protected ClientContext context;
/*  36 */   protected static final List EMPTY_LIST = new ArrayList(0);
/*     */   
/*     */   public class NodeWrapper {
/*  39 */     public SITOCElement node = null;
/*     */     
/*  41 */     protected String identifier = null;
/*     */     
/*     */     public NodeWrapper(Object node, String identifier) {
/*  44 */       this.node = (SITOCElement)node;
/*  45 */       this.identifier = identifier;
/*  46 */       CTOCTree.this.idToNode.put(this.identifier, this);
/*     */     }
/*     */     
/*     */     public NodeWrapper(Object node) {
/*  50 */       this.node = (SITOCElement)node;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       try {
/*  55 */         return "NodeWrapper(" + this.identifier + ")";
/*  56 */       } catch (NullPointerException e) {
/*  57 */         return "unable to display node";
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  63 */       if (this == obj)
/*  64 */         return true; 
/*  65 */       if (obj instanceof NodeWrapper) {
/*  66 */         NodeWrapper other = (NodeWrapper)obj;
/*  67 */         boolean ret = Util.equals(this.identifier, other.identifier);
/*  68 */         return ret;
/*     */       } 
/*  70 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  75 */       return this.identifier.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  81 */   protected Map idToNode = new HashMap<Object, Object>();
/*     */   
/*  83 */   protected Object selectedNode = null;
/*     */   
/*  85 */   protected Set expandedNodes = new HashSet();
/*     */   
/*  87 */   protected Set observers = new HashSet();
/*     */   
/*  89 */   protected LocaleInfo locale = null;
/*     */   
/*  91 */   protected String country = null;
/*     */ 
/*     */   
/*     */   public CTOCTree(ClientContext context) {
/*  95 */     this.context = context;
/*  96 */     this.country = context.getSharedContext().getCountry();
/*  97 */     this.locale = LocaleInfoProvider.getInstance().getLocale(context.getLocale());
/*     */   }
/*     */ 
/*     */   
/*     */   public LocaleInfo getLocaleInfo() {
/* 102 */     return this.locale;
/*     */   }
/*     */   
/*     */   public String getCountry() {
/* 106 */     return this.country;
/*     */   }
/*     */   
/*     */   public abstract CTOCNode getRootNode();
/*     */   
/*     */   protected abstract List getSITFilter();
/*     */   
/*     */   protected abstract VCR getVCR();
/*     */   
/*     */   public List getRoots() {
/* 116 */     CTOCNode root = getRootNode();
/*     */     
/* 118 */     return (root != null) ? getChilds(new NodeWrapper(root, root.getID().toString())) : EMPTY_LIST;
/*     */   }
/*     */   
/*     */   public List getChilds(Object node) {
/*     */     try {
/* 123 */       NodeWrapper parentWrapper = (NodeWrapper)node;
/* 124 */       List<NodeWrapper> retValue = new LinkedList();
/* 125 */       List<?> childs = parentWrapper.node.getChildren(getSITFilter(), this.locale, this.country, getVCR());
/* 126 */       if (childs != null) {
/* 127 */         Collections.sort(childs, NodeComparatorOrder.getInstance());
/* 128 */         for (int i = 0; i < childs.size(); i++) {
/* 129 */           SITOCElement child = (SITOCElement)childs.get(i);
/* 130 */           NodeWrapper childWrapper = new NodeWrapper(child, parentWrapper.identifier + "." + child.getID());
/* 131 */           retValue.add(childWrapper);
/*     */         } 
/*     */       } 
/* 134 */       return retValue;
/* 135 */     } catch (UnsupportedOperationException e) {
/* 136 */       return null;
/* 137 */     } catch (Exception e) {
/* 138 */       log.error("unable to retrieve childs for node:" + node + " - error:" + e, e);
/* 139 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getIdentifier(Object node) {
/* 145 */     String identifier = ((NodeWrapper)node).identifier;
/* 146 */     return identifier;
/*     */   }
/*     */   
/*     */   public String getLabel(Object node) {
/* 150 */     if (node instanceof NodeWrapper) {
/* 151 */       return getLabel(((NodeWrapper)node).node);
/*     */     }
/* 153 */     return ((SITOCElement)node).getLabel(this.locale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getNode(Object identifier) {
/* 159 */     return this.idToNode.get(identifier);
/*     */   }
/*     */   
/*     */   public Object getSelectedNode() {
/* 163 */     return this.selectedNode;
/*     */   }
/*     */   
/*     */   public List getSelectedPath() {
/* 167 */     List<Object> retValue = new LinkedList();
/* 168 */     Object node = getSelectedNode();
/* 169 */     while (node != null) {
/* 170 */       retValue.add(0, node);
/* 171 */       node = getParent(node);
/*     */     } 
/* 173 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean isLeaf(Object node) {
/* 177 */     List childs = getChilds(node);
/* 178 */     return (childs == null || childs.size() == 0);
/*     */   }
/*     */   
/*     */   public void setSelectedNode(Object node) {
/* 182 */     this.selectedNode = node;
/* 183 */     notifyObservers();
/*     */   }
/*     */   
/*     */   public boolean isExpanded(Object node) {
/* 187 */     return this.expandedNodes.contains(node);
/*     */   }
/*     */   
/*     */   public void toggleExpanded(Object node) {
/* 191 */     if (this.expandedNodes.contains(node)) {
/* 192 */       this.expandedNodes.remove(node);
/*     */     } else {
/* 194 */       this.expandedNodes.add(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isNodeParentSIT(CTOCNode node) {
/*     */     try {
/* 200 */       if (node != null) {
/* 201 */         CTOCNode parentNode = node.getParent();
/* 202 */         boolean isNodeSIT = (parentNode.hasProperty((SITOCProperty)CTOCProperty.SIT) || parentNode.hasProperty((SITOCProperty)CTOCProperty.SITQ));
/* 203 */         if (isNodeSIT) {
/* 204 */           return true;
/*     */         }
/* 206 */         isNodeParentSIT(parentNode);
/*     */       } 
/* 208 */     } catch (Exception ex) {
/* 209 */       log.error("unable to find parent SIT -exception:" + ex, ex);
/*     */     } 
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExpanded(Object node, boolean expanded) {
/* 216 */     if (expanded) {
/* 217 */       this.expandedNodes.add(node);
/*     */     } else {
/* 219 */       this.expandedNodes.remove(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getParentID(String nodeID) {
/* 224 */     String parentID = null;
/*     */     try {
/* 226 */       int index = nodeID.lastIndexOf(".");
/* 227 */       if (index != -1) {
/* 228 */         parentID = nodeID.substring(0, index);
/*     */       }
/* 230 */     } catch (Exception e) {}
/*     */     
/* 232 */     return parentID;
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 236 */     NodeWrapper wrapper = (NodeWrapper)node;
/*     */     
/* 238 */     return this.idToNode.get(getParentID(wrapper.identifier));
/*     */   }
/*     */   
/*     */   protected boolean validPath(NodeWrapper node, List path) {
/* 242 */     if (path == null || path.size() == 0)
/* 243 */       return true; 
/* 244 */     if (path.indexOf(node) == 0) {
/* 245 */       List childs = getChilds(node);
/* 246 */       if (childs == null || childs.size() == 0) {
/* 247 */         return (path.subList(1, path.size()).size() == 0);
/*     */       }
/* 249 */       Iterator<NodeWrapper> iter = getChilds(node).iterator();
/* 250 */       while (iter.hasNext()) {
/* 251 */         if (validPath(iter.next(), path.subList(1, path.size()))) {
/* 252 */           return true;
/*     */         }
/*     */       } 
/* 255 */       return false;
/*     */     } 
/*     */     
/* 258 */     return false;
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
/*     */   
/*     */   public synchronized void addObserver(CTOCTreeObserver observer) {
/* 271 */     this.observers.add(observer);
/*     */   }
/*     */   
/*     */   protected synchronized void notifyObservers() {
/* 275 */     Iterator<CTOCTreeObserver> iter = this.observers.iterator();
/* 276 */     while (iter.hasNext())
/* 277 */       ((CTOCTreeObserver)iter.next()).onSelectionChange((NodeWrapper)getSelectedNode()); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\ctoctree\datamodel\CTOCTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */