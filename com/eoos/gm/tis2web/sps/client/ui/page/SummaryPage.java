/*     */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.SummaryData;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.ControllerUtils;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayAdapter;
/*     */ import com.eoos.gm.tis2web.sps.client.util.Transform;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ControllerReference;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Option;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ListValueImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.DisplaySummaryRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ 
/*     */ public class SummaryPage
/*     */   extends DefaultPage {
/*  36 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*     */   public SummaryPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/*  39 */     super(agent, gui, data, request);
/*     */   }
/*     */   
/*     */   public void activate(Object savepoint) {
/*  43 */     this.gui.setBackButtonState(true);
/*  44 */     initSummaryRequest((DisplaySummaryRequestImpl)this.request);
/*  45 */     super.activate(savepoint);
/*  46 */     this.data.set(this.request.getAttribute(), CommonValue.OK);
/*  47 */     this.agent.triggerRequest();
/*  48 */     this.gui.setNextButtonState(true);
/*     */   }
/*     */   
/*     */   protected void generateSummaryDigest(Summary summary) {
/*  52 */     List<History> digest = new ArrayList();
/*  53 */     digest.add(summary.getCurrentSoftware());
/*  54 */     digest.add(summary.getSelectedSoftware());
/*  55 */     this.data.set(CommonAttribute.SUMMARY_DIGEST, (Value)new ListValueImpl(digest));
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultTableModel constructModuleData(String name, List modules) {
/*  60 */     name = ControllerUtils.getShortNameController(name);
/*  61 */     DefaultTableModel mtable = new DefaultTableModel();
/*  62 */     mtable.setColumnCount(5);
/*  63 */     for (Iterator<Module> iter = modules.iterator(); iter.hasNext(); ) {
/*  64 */       Module m = iter.next();
/*     */       try {
/*  66 */         if (Integer.parseInt(m.getID()) != 0) {
/*  67 */           String description = m.getDenotation(null);
/*  68 */           if (m.getSelectedPart() != null) {
/*  69 */             description = m.getSelectedPart().getDescription(null);
/*     */           }
/*  71 */           description = Transform.convertStringToHtml(description);
/*  72 */           mtable.addRow(new Object[] { name, m.getID(), m.getCurrentCalibration(), m.getSelectedPart().getPartNumber(), description });
/*  73 */           name = "";
/*     */         } 
/*  75 */       } catch (NumberFormatException e) {}
/*     */     } 
/*     */     
/*  78 */     mtable.addRow(new Object[] { "", "", "", "", "" });
/*  79 */     return mtable;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultTableModel constructTypeAppModuleData(String name, List modules) {
/*  84 */     name = ControllerUtils.getShortNameController(name);
/*  85 */     DefaultTableModel mtable = new DefaultTableModel();
/*  86 */     mtable.setColumnCount(5);
/*  87 */     for (Iterator<Module> iter = modules.iterator(); iter.hasNext(); ) {
/*  88 */       Module m = iter.next();
/*     */       try {
/*  90 */         if (Integer.parseInt(m.getID()) != 0) {
/*  91 */           String description = m.getDenotation(null);
/*  92 */           if (m.getSelectedPart() != null) {
/*  93 */             description = m.getSelectedPart().getDescription(null);
/*     */           }
/*  95 */           description = Transform.convertStringToHtml(description);
/*  96 */           String noData = resourceProvider.getLabel(null, "no-data");
/*  97 */           mtable.addRow(new Object[] { name, m.getID(), noData, noData, description });
/*  98 */           name = "";
/*     */         } 
/* 100 */       } catch (NumberFormatException e) {}
/*     */     } 
/*     */     
/* 103 */     mtable.addRow(new Object[] { "", "", "", "", "" });
/* 104 */     return mtable;
/*     */   }
/*     */   
/*     */   protected DefaultTableModel constructModuleData(List modules) {
/* 108 */     DefaultTableModel mtable = new DefaultTableModel();
/* 109 */     mtable.setColumnCount(4);
/* 110 */     for (Iterator<Module> iter = modules.iterator(); iter.hasNext(); ) {
/* 111 */       Module m = iter.next();
/*     */       try {
/* 113 */         if (Integer.parseInt(m.getID()) != 0) {
/* 114 */           String description = m.getDenotation(null);
/* 115 */           if (m.getSelectedPart() != null) {
/* 116 */             description = m.getSelectedPart().getDescription(null);
/*     */           }
/* 118 */           description = Transform.convertStringToHtml(description);
/* 119 */           mtable.addRow(new Object[] { m.getID(), m.getCurrentCalibration(), m.getSelectedPart().getPartNumber(), description });
/*     */         } 
/* 121 */       } catch (NumberFormatException e) {}
/*     */     } 
/*     */     
/* 124 */     return mtable;
/*     */   }
/*     */   
/*     */   protected void initSummaryRequest(DisplaySummaryRequestImpl request) {
/* 128 */     if (request.getSummary() == null && request.getSequenceSummary() == null) {
/*     */       
/* 130 */       ProgrammingDataSelectionRequest pdata = null;
/* 131 */       ProgrammingSequence sequence = request.getProgrammingSequence();
/* 132 */       if (sequence == null) {
/* 133 */         pdata = (ProgrammingDataSelectionRequest)this.predecessor.getRequest();
/* 134 */         if (pdata.getProgrammingSequence() != null) {
/* 135 */           sequence = pdata.getProgrammingSequence();
/*     */         }
/*     */       } 
/* 138 */       if (sequence != null) {
/* 139 */         List<DefaultTableModel> mtables = new ArrayList();
/* 140 */         List<ControllerReference> controllers = sequence.getSequence();
/* 141 */         List<ControllerReference> xcontrollers = new ArrayList();
/* 142 */         for (int i = 0; i < controllers.size(); i++) {
/* 143 */           ControllerReference controllerReference = controllers.get(i);
/* 144 */           List modules = sequence.getProgrammingData(i);
/* 145 */           if (!controllerReference.isType4Application()) {
/* 146 */             mtables.add(constructModuleData(controllerReference.getDenotation(null), modules));
/*     */           } else {
/* 148 */             mtables.add(constructTypeAppModuleData(controllerReference.getDenotation(null), modules));
/*     */           } 
/* 150 */           xcontrollers.add(controllerReference);
/*     */         } 
/* 152 */         request.setModuleData(xcontrollers, mtables);
/*     */       } else {
/*     */         
/* 155 */         List modules = new ArrayList(pdata.getOptions());
/* 156 */         DefaultTableModel mtable = constructModuleData(System.getProperty(CommonAttribute.CONTROLLER.toString()), modules);
/* 157 */         request.setModuleData(mtable);
/*     */       } 
/* 159 */       Value controller = this.data.getValue(CommonAttribute.CONTROLLER);
/* 160 */       String label = controller.toString();
/* 161 */       int index = label.indexOf("\t");
/* 162 */       if (index != -1) {
/* 163 */         label = label.substring(index + 1, label.length());
/*     */       }
/* 165 */       request.setControllerLabel(label);
/*     */     }
/* 167 */     else if (request.getSequenceSummary() != null) {
/*     */       
/* 169 */       List sequence = request.getSequenceSummary();
/* 170 */       List<String> controllers = new ArrayList();
/* 171 */       List<DefaultTableModel> mtables = new ArrayList();
/* 172 */       List<DefaultTableModel> descriptionTables = new ArrayList();
/* 173 */       for (Iterator<Summary> iterator = sequence.iterator(); iterator.hasNext(); ) {
/* 174 */         Summary summary = iterator.next();
/* 175 */         controllers.add(summary.getControllerName());
/* 176 */         SummaryData summaryData = new SummaryData(summary);
/* 177 */         DefaultTableModel dtable = prepareDescriptionDataModel(summaryData);
/* 178 */         descriptionTables.add(dtable);
/* 179 */         DefaultTableModel mtable = prepareModuleDataModel(summaryData);
/* 180 */         request.setModuleData(mtable);
/* 181 */         mtables.add(mtable);
/*     */       } 
/* 183 */       request.setGMEMoreSequence(true);
/* 184 */       request.setModuleDataListGME(controllers, descriptionTables, mtables);
/*     */     }
/*     */     else {
/*     */       
/* 188 */       Summary summary = request.getSummary();
/* 189 */       SummaryData summaryData = new SummaryData(summary);
/*     */       
/* 191 */       DefaultTableModel dtable = prepareDescriptionDataModel(summaryData);
/* 192 */       request.setDescriptionData(dtable);
/*     */       
/* 194 */       if (summary.getSelectedSoftware().getAttributes().size() == 0) {
/*     */         
/* 196 */         DefaultTableModel mtable = new DefaultTableModel();
/* 197 */         List<Module> modules = summary.getModules();
/* 198 */         mtable.setColumnCount(3);
/* 199 */         for (int i = 0; i < modules.size(); i++) {
/* 200 */           Module m = modules.get(i);
/* 201 */           String descrip = m.getDenotation(null);
/* 202 */           if (m.getSelectedPart() != null) {
/* 203 */             descrip = m.getSelectedPart().getDescription(null);
/*     */           }
/* 205 */           descrip = Transform.convertStringToHtml(descrip);
/* 206 */           mtable.addRow(new Object[] { descrip, "", m.toString() });
/*     */         } 
/*     */         
/* 209 */         request.setModuleData(mtable);
/*     */       }
/*     */       else {
/*     */         
/* 213 */         request.setModuleData(prepareModuleDataModel(summaryData));
/* 214 */         generateSummaryDigest(summary);
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     DefaultTableModel vtable = new DefaultTableModel();
/* 219 */     vtable.setColumnCount(2);
/*     */     
/* 221 */     addVehicleData(vtable, CommonAttribute.SALESMAKE);
/* 222 */     addVehicleData(vtable, CommonAttribute.MODELYEAR);
/* 223 */     addVehicleData(vtable, CommonAttribute.MODEL);
/* 224 */     addVehicleData(vtable, CommonAttribute.CARLINE);
/* 225 */     addVehicleData(vtable, CommonAttribute.ENGINE);
/* 226 */     addVehicleData(vtable, CommonAttribute.TRANSMISSION);
/* 227 */     String engine = (String)AVUtil.accessValue((AttributeValueMap)this.data, CommonAttribute.ENGINE);
/* 228 */     Iterator<Attribute> it = this.data.getAttributes().iterator();
/* 229 */     while (it.hasNext()) {
/* 230 */       Attribute attribute = it.next();
/* 231 */       if (attribute instanceof Option) {
/* 232 */         if (engine != null) {
/* 233 */           Option option = (Option)this.data.getValue(attribute);
/* 234 */           if (engine.equals(option.getDenotation(null))) {
/*     */             continue;
/*     */           }
/*     */         } 
/* 238 */         if (((Option)attribute).getID().startsWith("$")) {
/*     */           continue;
/*     */         }
/* 241 */         vtable.addRow(new Object[] { new DisplayAdapter(attribute), new DisplayAdapter(this.data.getValue(attribute)) });
/*     */       } 
/*     */     } 
/* 244 */     request.setVehicleData(vtable);
/*     */   }
/*     */   
/*     */   protected DefaultTableModel prepareModuleDataModel(SummaryData summaryData) {
/* 248 */     DefaultTableModel mtable = new DefaultTableModel();
/* 249 */     mtable.setColumnCount(3);
/* 250 */     Vector<Vector> data = summaryData.getSummaryData();
/* 251 */     for (int i = 0; i < data.size(); i++) {
/* 252 */       mtable.addRow(data.get(i));
/*     */     }
/* 254 */     return mtable;
/*     */   }
/*     */   
/*     */   protected DefaultTableModel prepareDescriptionDataModel(SummaryData summaryData) {
/* 258 */     DefaultTableModel dtable = new DefaultTableModel();
/* 259 */     dtable.setColumnCount(1);
/* 260 */     Vector<Vector> description = summaryData.getDescription();
/* 261 */     if (description != null) {
/* 262 */       for (int i = 0; i < description.size(); i++) {
/* 263 */         dtable.addRow(description.get(i));
/*     */       }
/*     */     }
/* 266 */     return dtable;
/*     */   }
/*     */   
/*     */   protected void addVehicleData(DefaultTableModel vtable, Attribute attribute) {
/* 270 */     Value value = this.data.getValue(attribute);
/* 271 */     if (value != null) {
/* 272 */       vtable.addRow(new Object[] { new DisplayAdapter(attribute), new DisplayAdapter(value) });
/*     */     }
/*     */   }
/*     */   
/*     */   public UIPage undo() {
/* 277 */     if (this.data.getValue(CommonAttribute.REPLACE_INSTRUCTIONS) != null) {
/* 278 */       this.gui.setBackButtonState(false);
/*     */     }
/* 280 */     return super.undo();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\SummaryPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */