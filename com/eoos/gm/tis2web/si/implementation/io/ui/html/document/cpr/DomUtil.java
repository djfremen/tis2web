/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.CharacterData;
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
/*     */ public class DomUtil
/*     */ {
/*     */   public static abstract class ElemWorker
/*     */   {
/*     */     public abstract void work(Element param1Element);
/*     */     
/*     */     public void init(Element elem) {}
/*     */     
/*     */     public void end(NodeList nL) {}
/*     */   }
/*     */   
/*     */   public static String valueFrom(Node node) {
/*     */     String ret;
/*  37 */     Node text = node.getFirstChild();
/*     */     
/*  39 */     if (text != null && (text.getNodeType() == 4 || text.getNodeType() == 3)) {
/*  40 */       ret = ((CharacterData)text).getData();
/*     */     } else {
/*  42 */       ret = null;
/*     */     } 
/*  44 */     return ret;
/*     */   }
/*     */   
/*     */   public static void setValue(Node node, String val) {
/*  48 */     Node text = node.getFirstChild();
/*  49 */     if (text != null && (text.getNodeType() == 4 || text.getNodeType() == 3)) {
/*  50 */       ((CharacterData)text).setData(val);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Element firstChild(Element elem, String name) {
/*  55 */     Element ret = null;
/*  56 */     NodeList subNodes = elem.getElementsByTagName(name);
/*  57 */     if (0 < subNodes.getLength()) {
/*  58 */       ret = (Element)subNodes.item(0);
/*     */     }
/*  60 */     return ret;
/*     */   }
/*     */   
/*     */   public static Element firstChildOfCurPlane(Node elem, String name) {
/*  64 */     Element ret = null;
/*  65 */     NodeList subNodes = elem.getChildNodes();
/*  66 */     for (int ind = 0; ind < subNodes.getLength(); ind++) {
/*  67 */       Node curNode = subNodes.item(ind);
/*  68 */       if (curNode.getNodeType() == 1 && (
/*  69 */         (Element)curNode).getTagName().equals(name)) {
/*  70 */         ret = (Element)curNode;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  75 */     return ret;
/*     */   }
/*     */   
/*     */   public static List getListChilds(Node elem, String name) {
/*  79 */     List<Element> all = new LinkedList();
/*  80 */     Element ret = null;
/*  81 */     NodeList subNodes = elem.getChildNodes();
/*  82 */     for (int ind = 0; ind < subNodes.getLength(); ind++) {
/*  83 */       Node curNode = subNodes.item(ind);
/*  84 */       if (curNode.getNodeType() == 1 && (
/*  85 */         (Element)curNode).getTagName().equals(name)) {
/*  86 */         ret = (Element)curNode;
/*  87 */         all.add(ret);
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     return all;
/*     */   }
/*     */   
/*     */   public static String firstChildValue(Element elem, String name) {
/*  95 */     elem = firstChild(elem, name);
/*  96 */     return (elem != null) ? valueFrom(elem) : "";
/*     */   }
/*     */   
/*     */   public static void doForDescendants(Element elem, String tagName, ElemWorker worker) {
/* 100 */     NodeList subNodes = elem.getElementsByTagName(tagName);
/* 101 */     for (int ind = 0; ind < subNodes.getLength(); ind++) {
/* 102 */       Element inpNode = (Element)subNodes.item(ind);
/* 103 */       if (ind == 0) {
/* 104 */         worker.init(inpNode);
/*     */       }
/* 106 */       worker.work(inpNode);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static NodeList doChangeForDescendants(Element elem, String tagName, ElemWorker worker) {
/* 111 */     NodeList subNodes = elem.getElementsByTagName(tagName);
/* 112 */     List<Element> nodeList = new ArrayList(subNodes.getLength());
/* 113 */     for (int ind = 0; ind < subNodes.getLength(); ind++) {
/* 114 */       Element inpNode = (Element)subNodes.item(ind);
/* 115 */       nodeList.add(inpNode);
/*     */     } 
/* 117 */     Iterator<Element> it = nodeList.iterator();
/* 118 */     if (it.hasNext()) {
/* 119 */       Element inpNode = it.next();
/* 120 */       worker.init(inpNode);
/* 121 */       worker.work(inpNode);
/* 122 */       while (it.hasNext()) {
/* 123 */         inpNode = it.next();
/* 124 */         worker.work(inpNode);
/*     */       } 
/*     */     } 
/* 127 */     worker.end(subNodes);
/* 128 */     return subNodes;
/*     */   }
/*     */   
/*     */   public static Element getFirstParent(Node node, String tagName) {
/* 132 */     Element ret = null;
/*     */     try {
/*     */       do {
/* 135 */         node = node.getParentNode();
/*     */       }
/* 137 */       while (node != null && node.getNodeType() != 1 && !(ret = (Element)node).getTagName().equals(tagName));
/* 138 */     } catch (Exception e) {}
/*     */     
/* 140 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\DomUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */