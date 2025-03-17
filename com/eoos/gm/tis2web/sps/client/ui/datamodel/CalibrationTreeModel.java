/*     */ package com.eoos.gm.tis2web.sps.client.ui.datamodel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.COP;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.event.TreeModelEvent;
/*     */ import javax.swing.event.TreeModelListener;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ 
/*     */ public class CalibrationTreeModel
/*     */   implements TreeModel
/*     */ {
/*     */   protected Module module;
/*     */   protected Part origin;
/*     */   protected Part defaultPart;
/*     */   private List bulletins;
/*     */   private List listeners;
/*     */   
/*     */   public CalibrationTreeModel(Value value) {
/*  25 */     this.module = (Module)value;
/*  26 */     this.origin = this.module.getOriginPart();
/*  27 */     if (this.module.getSelectedPart() != null) {
/*  28 */       if (onlyDefaultSelection(this.origin.getCOP())) {
/*  29 */         this.defaultPart = this.module.getSelectedPart();
/*     */       }
/*     */     } else {
/*  32 */       this.defaultPart = this.origin;
/*  33 */       checkDefaultSelection(this.origin.getCOP());
/*  34 */       if (this.defaultPart != null) {
/*  35 */         this.module.setSelectedPart(this.defaultPart);
/*     */       }
/*     */     } 
/*  38 */     this.bulletins = collectBulletins();
/*  39 */     this.listeners = new ArrayList();
/*     */   }
/*     */   
/*     */   protected void checkDefaultSelection(List<COP> cop) {
/*  43 */     if (cop != null)
/*     */     {
/*  45 */       if (cop.size() > 1) {
/*  46 */         this.defaultPart = null;
/*     */       } else {
/*  48 */         COP link = cop.get(0);
/*  49 */         if (link.getMode() == 1) {
/*  50 */           if (this.defaultPart == this.origin) {
/*  51 */             this.defaultPart = link.getPart();
/*  52 */             link.setMode(3);
/*     */           } else {
/*  54 */             undoDefaultSelection(this.origin.getCOP());
/*     */           } 
/*     */         } else {
/*  57 */           checkDefaultSelection(link.getPart().getCOP());
/*     */         } 
/*     */       }  } 
/*     */   }
/*     */   
/*     */   protected void undoDefaultSelection(List<COP> cop) {
/*  63 */     for (int i = 0; i < cop.size(); i++) {
/*  64 */       COP link = cop.get(i);
/*  65 */       if (link.getMode() == 3) {
/*  66 */         link.setMode(1);
/*  67 */         this.defaultPart = null;
/*     */       } 
/*  69 */       if (this.defaultPart == null) {
/*     */         return;
/*     */       }
/*  72 */       undoDefaultSelection(link.getPart().getCOP());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean onlyDefaultSelection(List<COP> cop) {
/*  77 */     if (cop == null)
/*  78 */       return true; 
/*  79 */     if (cop.size() > 1) {
/*  80 */       return false;
/*     */     }
/*  82 */     COP link = cop.get(0);
/*  83 */     if (!onlyDefaultSelection(link.getPart().getCOP())) {
/*  84 */       return false;
/*     */     }
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasDefaultSelection() {
/*  91 */     return (this.defaultPart != null);
/*     */   }
/*     */   
/*     */   public Object getRoot() {
/*  95 */     return this.origin;
/*     */   }
/*     */   
/*     */   public int getChildCount(Object parent) {
/*  99 */     Part part = null;
/* 100 */     if (parent.equals(this.origin)) {
/* 101 */       part = this.origin;
/*     */     } else {
/* 103 */       part = ((COP)parent).getPart();
/*     */     } 
/* 105 */     return (part.getCOP() == null) ? 0 : part.getCOP().size();
/*     */   }
/*     */   
/*     */   public boolean isLeaf(Object node) {
/* 109 */     if (node.equals(this.origin)) {
/* 110 */       return (this.origin.getCOP() == null);
/*     */     }
/* 112 */     Part part = ((COP)node).getPart();
/* 113 */     return (part.getCOP() == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSelectable(Object node) {
/* 118 */     if (node.equals(this.origin)) {
/* 119 */       return this.origin.equals(this.defaultPart);
/*     */     }
/* 121 */     COP link = (COP)node;
/* 122 */     return (link.getMode() == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSelected(Object node) {
/* 127 */     if (node.equals(this.origin)) {
/* 128 */       return this.origin.equals(this.defaultPart);
/*     */     }
/* 130 */     COP link = (COP)node;
/* 131 */     return (link.getMode() == 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(Object node, boolean selected) {
/* 136 */     if (this.defaultPart != null) {
/*     */       return;
/*     */     }
/* 139 */     COP link = (COP)node;
/* 140 */     if (selected) {
/* 141 */       link.setMode(3);
/* 142 */       this.module.setSelectedPart(link.getPart());
/*     */     } else {
/* 144 */       link.setMode(1);
/* 145 */       fireTreeNodesChanged(makeTreeNodeChangedEvent(link));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getSelectedNode() {
/* 150 */     Part part = this.module.getSelectedPart();
/* 151 */     if (part != null && part.equals(this.origin)) {
/* 152 */       return this.origin;
/*     */     }
/* 154 */     return findSelectedLink(this.origin.getCOP());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object findSelectedLink(List<COP> cop) {
/* 159 */     if (cop != null) {
/* 160 */       for (int i = 0; i < cop.size(); i++) {
/* 161 */         COP link = cop.get(i);
/* 162 */         if (link.getMode() == 3) {
/* 163 */           return link;
/*     */         }
/* 165 */         Object result = findSelectedLink(link.getPart().getCOP());
/* 166 */         if (result != null) {
/* 167 */           return result;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 172 */     return null;
/*     */   }
/*     */   
/*     */   public Object getChild(Object parent, int index) {
/* 176 */     Part part = null;
/* 177 */     if (parent.equals(this.origin)) {
/* 178 */       part = this.origin;
/*     */     } else {
/* 180 */       part = ((COP)parent).getPart();
/*     */     } 
/* 182 */     return part.getCOP().get(index);
/*     */   }
/*     */   
/*     */   public int getIndexOfChild(Object parent, Object child) {
/* 186 */     List<COP> cop = null;
/* 187 */     if (parent.equals(this.origin)) {
/* 188 */       cop = this.origin.getCOP();
/*     */     } else {
/* 190 */       cop = ((COP)parent).getPart().getCOP();
/*     */     } 
/* 192 */     ((COP)child).getPart();
/* 193 */     for (int i = 0; i < cop.size(); i++) {
/* 194 */       if (child.equals(((COP)cop.get(i)).getPart())) {
/* 195 */         return i;
/*     */       }
/*     */     } 
/* 198 */     return -1;
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
/*     */   
/*     */   public List getBulletins(Object node) {
/* 212 */     return this.bulletins;
/*     */   }
/*     */   
/*     */   protected List collectBulletins() {
/* 216 */     List bulletins = new ArrayList();
/* 217 */     List b = this.origin.getBulletins();
/* 218 */     if (b != null) {
/* 219 */       bulletins.addAll(b);
/*     */     }
/* 221 */     collectBulletins(bulletins, this.origin.getCOP());
/* 222 */     return bulletins;
/*     */   }
/*     */   
/*     */   protected void collectBulletins(List bulletins, List<COP> cop) {
/* 226 */     if (cop != null) {
/* 227 */       for (int i = 0; i < cop.size(); i++) {
/* 228 */         COP link = cop.get(i);
/* 229 */         Part part = link.getPart();
/* 230 */         if (part != null) {
/* 231 */           List b = part.getBulletins();
/* 232 */           if (b != null) {
/* 233 */             bulletins.addAll(b);
/*     */           }
/* 235 */           collectBulletins(bulletins, part.getCOP());
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public String getCVN(Object node) {
/* 242 */     if (node.equals(this.origin)) {
/* 243 */       return this.origin.getCVN();
/*     */     }
/* 245 */     Part part = ((COP)node).getPart();
/* 246 */     return part.getCVN();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription(Object node) {
/* 251 */     Part part = node.equals(this.origin) ? this.origin : ((COP)node).getPart();
/* 252 */     String description = part.getDescription(null);
/* 253 */     description = "<html>" + description + "</html>";
/* 254 */     return description;
/*     */   }
/*     */   
/*     */   public String getPartNumber(Object node) {
/* 258 */     Part part = node.equals(this.origin) ? this.origin : ((COP)node).getPart();
/* 259 */     String pno = part.getPartNumber();
/* 260 */     return pno;
/*     */   }
/*     */   
/*     */   public List getHistory(Object node) {
/* 264 */     if (!isLeaf(node))
/* 265 */       return null; 
/* 266 */     if (node instanceof Part) {
/* 267 */       List<Part> list = new ArrayList();
/* 268 */       list.add(this.origin);
/* 269 */       return list;
/*     */     } 
/* 271 */     Part part = ((COP)node).getPart();
/* 272 */     List<Part> history = new ArrayList();
/* 273 */     history.add(part);
/* 274 */     if (!part.equals(this.origin)) {
/* 275 */       collectHistory(this.origin.getCOP(), history, part);
/* 276 */       history.add(this.origin);
/*     */     } 
/* 278 */     return history;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean collectHistory(List<COP> cop, List<Part> history, Part target) {
/* 283 */     if (cop != null) {
/* 284 */       for (int i = 0; i < cop.size(); i++) {
/* 285 */         COP link = cop.get(i);
/* 286 */         Part part = link.getPart();
/* 287 */         if (part.equals(target)) {
/* 288 */           return true;
/*     */         }
/* 290 */         boolean found = collectHistory(part.getCOP(), history, target);
/* 291 */         if (found) {
/* 292 */           history.add(part);
/* 293 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 298 */     return false;
/*     */   }
/*     */   
/*     */   public String getCalibration() {
/* 302 */     return this.module.getCurrentCalibration();
/*     */   }
/*     */   
/*     */   public void addTreeModelListener(TreeModelListener l) {
/* 306 */     if (!this.listeners.contains(l)) {
/* 307 */       this.listeners.add(l);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeTreeModelListener(TreeModelListener l) {
/* 312 */     if (this.listeners.contains(l)) {
/* 313 */       this.listeners.remove(l);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void valueForPathChanged(TreePath path, Object newValue) {}
/*     */   
/*     */   protected boolean constructPath(Object node, List<COP> path, List<COP> cop) {
/* 321 */     if (cop != null) {
/* 322 */       int level = path.size();
/* 323 */       path.add(null);
/* 324 */       for (int i = 0; i < cop.size(); i++) {
/* 325 */         COP link = cop.get(i);
/* 326 */         path.set(level, link);
/* 327 */         if (link == node) {
/* 328 */           return true;
/*     */         }
/* 330 */         boolean success = constructPath(node, path, link.getPart().getCOP());
/* 331 */         if (success) {
/* 332 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 336 */       path.remove(level);
/*     */     } 
/* 338 */     return false;
/*     */   }
/*     */   
/*     */   protected TreeModelEvent makeTreeNodeChangedEvent(Object node) {
/* 342 */     List<Part> path = new ArrayList();
/* 343 */     path.add(this.origin);
/* 344 */     constructPath(node, path, this.origin.getCOP());
/* 345 */     return new TreeModelEvent(this, new TreePath(path.toArray()));
/*     */   }
/*     */   
/*     */   public void fireTreeNodesChanged(TreeModelEvent event) {
/* 349 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 350 */       TreeModelListener listener = this.listeners.get(i);
/* 351 */       listener.treeNodesChanged(event);
/*     */     } 
/*     */   }
/*     */   
/*     */   public TreePath buildTreePath(Object node) {
/* 356 */     List<Part> path = new ArrayList();
/* 357 */     path.add(this.origin);
/* 358 */     constructPath(node, path, this.origin.getCOP());
/* 359 */     return new TreePath(path.toArray());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\datamodel\CalibrationTreeModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */