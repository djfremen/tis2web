/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.selection.implementation.SelectionControlSPI;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ListValueImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.Navigation;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.calibinfo.NavigationImpl;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.UIContext;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.RequestHandlerPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.ButtonContainer;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.pdsr.print.PrintViewPopup;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary.SummaryPanel;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.tree.HtmlTreeElement;
/*     */ import com.eoos.html.element.input.tree.TreeControl;
/*     */ import com.eoos.html.element.input.tree.implementation.TreeControlImpl;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ProgrammingDataSelectionRequestPanel
/*     */   extends HtmlElementContainerBase
/*     */   implements RequestHandlerPanel, ButtonContainer
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(ProgrammingDataSelectionRequestPanel.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  50 */       template = ApplicationContext.getInstance().loadFile(ProgrammingDataSelectionRequestPanel.class, "pdrp.html", null).toString();
/*  51 */     } catch (Exception e) {
/*  52 */       log.error("unable to load template - error:" + e, e);
/*  53 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Navigation navigation;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private ProgrammingDataSelectionRequest request;
/*     */   
/*     */   private ModuleSelectionElement moduleSelection;
/*     */   
/*     */   private HtmlTreeElement partSelectionTree;
/*     */   
/*     */   private HtmlElement partHistoryTable;
/*     */   
/*  70 */   private Part currentPart = null;
/*     */   
/*  72 */   private String dealerVCI = null;
/*     */   
/*  74 */   private ClickButtonElement buttonCompleteHistory = null;
/*     */   
/*     */   private CustomAVMap avMap;
/*     */   
/*     */   public ProgrammingDataSelectionRequestPanel(final ClientContext context, final ProgrammingDataSelectionRequest request, String dealerVCI, CustomAVMap avMap) {
/*  79 */     this.context = context;
/*  80 */     this.request = request;
/*  81 */     this.dealerVCI = dealerVCI;
/*  82 */     this.avMap = avMap;
/*  83 */     this.navigation = (Navigation)new NavigationImpl(request.getModules());
/*     */     
/*  85 */     this.buttonCompleteHistory = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  87 */           return context.getLabel("sps.cal.info.complete.history");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  91 */           return ProgrammingDataSelectionRequestPanel.this.onClick_CompleteHistory(request.getModules());
/*     */         }
/*     */         
/*     */         protected String getTargetFrame() {
/*  95 */           return "historycomplete";
/*     */         }
/*     */       };
/*  98 */     addElement((HtmlElement)this.buttonCompleteHistory);
/*     */     
/* 100 */     this.partHistoryTable = (HtmlElement)new PartHistoryTable(context, new PartHistoryTable.Callback() {
/*     */           public Part getCurrentPart() {
/* 102 */             return ProgrammingDataSelectionRequestPanel.this.currentPart;
/*     */           }
/*     */           
/*     */           public Part getPredecessor(Part part) {
/* 106 */             return ProgrammingDataSelectionRequestPanel.this.navigation.getParent(part);
/*     */           }
/*     */         });
/*     */     
/* 110 */     this.moduleSelection = new ModuleSelectionElement(context, request.getModules())
/*     */       {
/*     */         public Object onModuleSelected(Module module) {
/* 113 */           return ProgrammingDataSelectionRequestPanel.this.onModuleSelected(module);
/*     */         }
/*     */         
/*     */         public boolean hasSelectedPart(Module module) {
/* 117 */           return (ProgrammingDataSelectionRequestPanel.this.navigation.getSelectedPart(module) != null);
/*     */         }
/*     */       };
/*     */     
/* 121 */     addElement((HtmlElement)this.moduleSelection);
/*     */     
/* 123 */     onModuleSelected((Module)this.moduleSelection.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 128 */     StringBuffer tmp = new StringBuffer(template);
/*     */     
/* 130 */     StringUtilities.replace(tmp, "{MODULE_SELECTION}", this.moduleSelection.getHtmlCode(params));
/* 131 */     if (UIContext.getInstance(this.context).displayDealerVCI() && this.dealerVCI != null) {
/* 132 */       String label = this.context.getLabel("sps.calibration.info.dealer.vci") + ": ";
/* 133 */       StringUtilities.replace(tmp, "{DEALERVCI}", label + this.dealerVCI);
/*     */     } else {
/*     */       
/* 136 */       StringUtilities.replace(tmp, "{DEALERVCI}", "");
/*     */     } 
/*     */     
/* 139 */     StringUtilities.replace(tmp, "{PART_SELECTION_TREE}", this.partSelectionTree.getHtmlCode(params));
/* 140 */     StringUtilities.replace(tmp, "{HISTORY}", this.partHistoryTable.getHtmlCode(params));
/* 141 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   public Object onModuleSelected(final Module module) {
/* 145 */     this.currentPart = this.navigation.getSelectedPart(module);
/*     */ 
/*     */ 
/*     */     
/* 149 */     TreeNavigationSPI treeNavigation = new TreeNavigationSPI() {
/* 150 */         private final Object SUPERROOT = new Object();
/*     */         
/*     */         public List getChildren(Object node) {
/* 153 */           if (node == this.SUPERROOT) {
/* 154 */             List<Part> retValue = new ArrayList(1);
/* 155 */             retValue.add(ProgrammingDataSelectionRequestPanel.this.navigation.getRootPart(module));
/* 156 */             return retValue;
/*     */           } 
/* 158 */           return ProgrammingDataSelectionRequestPanel.this.navigation.getChildren((Part)node);
/*     */         }
/*     */         
/*     */         public Object getParent(Object node) {
/* 162 */           Object retValue = ProgrammingDataSelectionRequestPanel.this.navigation.getParent((Part)node);
/* 163 */           return (retValue != null) ? retValue : this.SUPERROOT;
/*     */         }
/*     */         
/*     */         public Object getSuperroot() {
/* 167 */           return this.SUPERROOT;
/*     */         }
/*     */       };
/* 170 */     final SelectionControlSPI selectionControl = new SelectionControlSPI() {
/*     */         public void addSelection(Object obj) {
/* 172 */           ProgrammingDataSelectionRequestPanel.this.currentPart = (Part)obj;
/* 173 */           ProgrammingDataSelectionRequestPanel.this.navigation.setSelectedPart(module, ProgrammingDataSelectionRequestPanel.this.currentPart);
/*     */         }
/*     */         
/*     */         public void removeSelection(Object obj) {
/* 177 */           ProgrammingDataSelectionRequestPanel.this.currentPart = (Part)obj;
/* 178 */           ProgrammingDataSelectionRequestPanel.this.navigation.setSelectedPart(module, null);
/*     */         }
/*     */         
/*     */         public Collection getSelection() {
/* 182 */           Object part = ProgrammingDataSelectionRequestPanel.this.navigation.getSelectedPart(module);
/* 183 */           if (part == null) {
/* 184 */             return Collections.EMPTY_LIST;
/*     */           }
/* 186 */           List<Object> retValue = new ArrayList(1);
/* 187 */           retValue.add(part);
/* 188 */           return retValue;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 193 */     final Collection collapsed = new HashSet();
/*     */     
/* 195 */     TreeControlImpl treeControlImpl = new TreeControlImpl(treeNavigation, selectionControl)
/*     */       {
/*     */         public String getLabel(Object node) {
/* 198 */           return ((Part)node).getPartNumber();
/*     */         }
/*     */         
/*     */         public boolean isExpanded(Object node) {
/* 202 */           return !collapsed.contains(node);
/*     */         }
/*     */         
/*     */         public void toggleExpanded(Object node) {
/* 206 */           if (collapsed.contains(node)) {
/* 207 */             collapsed.remove(node);
/*     */           } else {
/* 209 */             collapsed.add(node);
/*     */           } 
/*     */         }
/*     */         
/*     */         public void setExpanded(Object node, boolean expanded) {
/* 214 */           if (expanded) {
/* 215 */             collapsed.remove(node);
/*     */           } else {
/* 217 */             collapsed.add(node);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 222 */     if (this.partSelectionTree != null) {
/* 223 */       removeElement((HtmlElement)this.partSelectionTree);
/*     */     }
/* 225 */     this.partSelectionTree = new HtmlTreeElement((TreeControl)treeControlImpl) {
/*     */         protected HtmlElement createIconElement(Object node) {
/* 227 */           SelectionIconElement.Callback callback = new SelectionIconElement.Callback() {
/*     */               public boolean isSelectable(Object node) {
/* 229 */                 return ProgrammingDataSelectionRequestPanel.this.navigation.isSelectable((Part)node);
/*     */               }
/*     */             };
/* 232 */           return (HtmlElement)new IconElement(ProgrammingDataSelectionRequestPanel.this.context, getTreeControl(), node, selectionControl, callback);
/*     */         }
/*     */ 
/*     */         
/*     */         protected HtmlElement createLabelElement(Object node) {
/* 237 */           return (HtmlElement)new BulletinLabelElement(ProgrammingDataSelectionRequestPanel.this.context, getTreeControl(), node);
/*     */         }
/*     */       };
/* 240 */     addElement((HtmlElement)this.partSelectionTree);
/*     */     
/* 242 */     return null;
/*     */   }
/*     */   
/*     */   public boolean onNext(CustomAVMap avMap) {
/* 246 */     boolean retValue = true;
/* 247 */     List<Part> selectedParts = new LinkedList();
/*     */     
/* 249 */     for (Iterator<Module> iter = this.request.getModules().iterator(); iter.hasNext() && retValue; ) {
/* 250 */       Module module = iter.next();
/* 251 */       Part part = this.navigation.getSelectedPart(module);
/* 252 */       if (part != null) {
/* 253 */         selectedParts.add(part); continue;
/*     */       } 
/* 255 */       retValue = false;
/*     */     } 
/*     */ 
/*     */     
/* 259 */     if (retValue) {
/* 260 */       avMap.set(this.request.getAttribute(), (Value)new ListValueImpl(selectedParts));
/*     */     }
/*     */ 
/*     */     
/* 264 */     avMap.set(SummaryPanel.ATTRIBUTE_SELECTION_REQUEST, (Value)new ValueAdapter(this.request));
/* 265 */     return retValue;
/*     */   }
/*     */   
/*     */   public List getButtonElements() {
/* 269 */     return Arrays.asList(new Object[] { this.buttonCompleteHistory });
/*     */   }
/*     */   
/*     */   protected Object onClick_CompleteHistory(List modules) {
/* 273 */     return new PrintViewPopup(this.context, modules, this.navigation, this.avMap);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\pdsr\ProgrammingDataSelectionRequestPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */