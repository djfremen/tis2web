/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers;
/*     */ 
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.DomUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ public class ElementPath
/*     */   extends LinkedList
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public Element getElement(Element elem) {
/*  32 */     Iterator<E> it = iterator();
/*  33 */     boolean ex = true;
/*  34 */     while (it.hasNext() && ex) {
/*     */ 
/*     */       
/*  37 */       NodeList subNodes = elem.getElementsByTagName((String)it.next());
/*  38 */       if (0 == subNodes.getLength()) {
/*  39 */         ex = false;
/*     */       }
/*  41 */       elem = (Element)subNodes.item(0);
/*     */     } 
/*     */     
/*  44 */     return ex ? elem : null;
/*     */   }
/*     */   
/*     */   public NodeList getNodeList(Element elem) {
/*  48 */     Iterator<E> it = iterator();
/*  49 */     boolean ex = true;
/*  50 */     NodeList subNodes = null;
/*  51 */     while (it.hasNext() && ex) {
/*     */ 
/*     */       
/*  54 */       subNodes = elem.getElementsByTagName((String)it.next());
/*  55 */       if (0 == subNodes.getLength()) {
/*  56 */         ex = false;
/*     */       }
/*  58 */       elem = (Element)subNodes.item(0);
/*     */     } 
/*     */     
/*  61 */     return subNodes;
/*     */   }
/*     */   
/*     */   public String getValueOfAttr(Element elem, String attr, String attrVal) {
/*  65 */     String ret = "";
/*  66 */     NodeList subNodes = getNodeList(elem);
/*  67 */     if (subNodes != null) {
/*  68 */       for (int ind = 0; ind < subNodes.getLength(); ind++) {
/*  69 */         Element curElem = (Element)subNodes.item(ind);
/*  70 */         String curAttrVal = curElem.getAttribute(attr);
/*  71 */         if (curAttrVal.equals(attrVal)) {
/*  72 */           ret = DomUtil.valueFrom(curElem);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*  77 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getValuesOfAttr(Element elem, String attr) {
/*  82 */     Map<Object, Object> ret = new HashMap<Object, Object>();
/*  83 */     NodeList subNodes = getNodeList(elem);
/*  84 */     if (subNodes != null) {
/*  85 */       for (int ind = 0; ind < subNodes.getLength(); ind++) {
/*  86 */         Element curElem = (Element)subNodes.item(ind);
/*  87 */         String curAttrVal = curElem.getAttribute(attr);
/*  88 */         ret.put(curAttrVal, DomUtil.valueFrom(curElem));
/*     */       } 
/*     */     }
/*  91 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue(Element elem) {
/*  96 */     Element toFind = getElement(elem);
/*  97 */     return (toFind != null) ? DomUtil.valueFrom(toFind) : "";
/*     */   }
/*     */   
/*     */   public String getAttribut(Element elem, String attr) {
/* 101 */     Element toFind = getElement(elem);
/* 102 */     return (toFind != null) ? toFind.getAttribute(attr) : "";
/*     */   }
/*     */   
/*     */   public void doForDescendants(Node elem, DomUtil.ElemWorker worker) {
/* 106 */     Iterator<E> it = iterator();
/* 107 */     if (it.hasNext()) {
/* 108 */       doForDescendants(elem, worker, it);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void doForDescendants(Node elem, DomUtil.ElemWorker worker, Iterator<String> it) {
/* 113 */     String name = it.next();
/* 114 */     NodeList subNodes = elem.getChildNodes();
/* 115 */     if (it.hasNext()) {
/*     */       
/* 117 */       for (int ind = 0; ind < subNodes.getLength(); ind++) {
/* 118 */         Node curNode = subNodes.item(ind);
/* 119 */         if (curNode.getNodeType() == 1) {
/* 120 */           if (((Element)curNode).getTagName().equals(name)) {
/* 121 */             it.next();
/*     */           }
/* 123 */           doForDescendants(curNode, worker, it);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 127 */       for (int ind = 0; ind < subNodes.getLength(); ind++) {
/* 128 */         Node curNode = subNodes.item(ind);
/* 129 */         if (curNode.getNodeType() == 1 && ((Element)curNode).getTagName().equals(name))
/* 130 */           worker.work((Element)curNode); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\ElementPath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */