/*     */ package com.eoos.gm.tis2web.sps.server.implementation.calibinfo;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.COP;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.util.Util;
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
/*     */ public class NavigationImpl
/*     */   implements Navigation
/*     */ {
/*     */   private List modules;
/*  23 */   private Map partToChildren = new HashMap<Object, Object>();
/*     */   
/*  25 */   private Map partToParent = new HashMap<Object, Object>();
/*     */   
/*  27 */   private Map partToSelectable = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   public NavigationImpl(List modules) {
/*  31 */     this.modules = modules;
/*     */     
/*  33 */     for (Iterator<Module> iter = this.modules.iterator(); iter.hasNext(); ) {
/*  34 */       Module module = iter.next();
/*  35 */       getParts(module);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List getChildren(Part part) {
/*  41 */     List<Part> children = (List)this.partToChildren.get(part);
/*  42 */     if (children == null) {
/*  43 */       children = new LinkedList();
/*  44 */       if (part.getCOP() != null) {
/*  45 */         for (Iterator<COP> iter = part.getCOP().iterator(); iter.hasNext(); ) {
/*  46 */           COP cop = iter.next();
/*  47 */           Part child = cop.getPart();
/*  48 */           this.partToParent.put(child, part);
/*  49 */           Boolean selectable = (cop.getMode() == 1 || cop.getMode() == 3) ? Boolean.TRUE : Boolean.FALSE;
/*  50 */           this.partToSelectable.put(child, selectable);
/*  51 */           children.add(cop.getPart());
/*     */         } 
/*     */       }
/*  54 */       this.partToChildren.put(part, children);
/*     */     } 
/*  56 */     return children;
/*     */   }
/*     */   
/*     */   private Set getSuccessors(Part part) {
/*  60 */     Set<Part> retValue = new HashSet();
/*  61 */     for (Iterator<Part> iter = getChildren(part).iterator(); iter.hasNext(); ) {
/*  62 */       Part child = iter.next();
/*  63 */       retValue.add(child);
/*  64 */       retValue.addAll(getSuccessors(child));
/*     */     } 
/*  66 */     return retValue;
/*     */   }
/*     */   
/*     */   public Set getParts(Module module) {
/*  70 */     Set<Part> retValue = new HashSet();
/*  71 */     Part root = module.getOriginPart();
/*  72 */     retValue.add(root);
/*  73 */     retValue.addAll(getSuccessors(root));
/*  74 */     return retValue;
/*     */   }
/*     */   
/*     */   public Part getRootPart(Module module) {
/*  78 */     return module.getOriginPart();
/*     */   }
/*     */   
/*     */   public Part getSelectedPart(Module module) {
/*  82 */     return module.getSelectedPart();
/*     */   }
/*     */   
/*     */   public void setSelectedPart(Module module, Part part) {
/*  86 */     module.setSelectedPart(part);
/*     */   }
/*     */   
/*     */   public Part getParent(Part part) {
/*  90 */     return (Part)this.partToParent.get(part);
/*     */   }
/*     */   private Boolean isSelectable(Part part, int check) {
/*     */     Iterator<Module> iter;
/*  94 */     Boolean selectable = null;
/*  95 */     switch (check) {
/*     */       
/*     */       case 0:
/*  98 */         selectable = (Boolean)this.partToSelectable.get(part);
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 103 */         for (iter = this.modules.iterator(); iter.hasNext() && selectable == null; ) {
/* 104 */           Module m = iter.next();
/* 105 */           if (Util.equals(getSelectedPart(m), part)) {
/* 106 */             selectable = Boolean.TRUE;
/*     */           }
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 113 */         if (getChildren(part).size() == 0) {
/* 114 */           selectable = Boolean.TRUE;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/* 119 */         selectable = Boolean.FALSE;
/*     */         break;
/*     */     } 
/* 122 */     if (selectable == null) {
/* 123 */       selectable = isSelectable(part, check + 1);
/*     */     }
/* 125 */     return selectable;
/*     */   }
/*     */   
/*     */   public boolean isSelectable(Part part) {
/* 129 */     return isSelectable(part, 0).booleanValue();
/*     */   }
/*     */   
/*     */   private List getLeafParts(Part part) {
/* 133 */     List<Part> retValue = new LinkedList();
/* 134 */     List childs = getChildren(part);
/* 135 */     if (childs.size() == 0) {
/* 136 */       retValue.add(part);
/*     */     } else {
/* 138 */       for (Iterator<Part> iter = childs.iterator(); iter.hasNext(); ) {
/* 139 */         Part child = iter.next();
/* 140 */         retValue.addAll(getLeafParts(child));
/*     */       } 
/*     */     } 
/* 143 */     return retValue;
/*     */   }
/*     */   
/*     */   public List getLeafParts(Module module) {
/* 147 */     return getLeafParts(getRootPart(module));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\calibinfo\NavigationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */