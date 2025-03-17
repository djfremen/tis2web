/*     */ package com.eoos.gm.tis2web.sps.client.ui.datamodel;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ValueAdapter;
/*     */ import com.eoos.gm.tis2web.sps.common.impl.VITImpl;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
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
/*     */ public class ECUData
/*     */ {
/*  30 */   protected ValueRetrieval valueRetrieval = null;
/*  31 */   protected VIT1 vit1 = null;
/*     */   
/*     */   protected Integer device;
/*  34 */   private static final Logger log = Logger.getLogger(ECUData.class);
/*     */   
/*  36 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   
/*     */   public ECUData(VIT1 vit1, Integer device) {
/*  39 */     this.vit1 = vit1;
/*  40 */     this.device = device;
/*     */   }
/*     */   
/*     */   public ECUData(ValueRetrieval value) {
/*  44 */     this.valueRetrieval = value;
/*     */   }
/*     */   
/*     */   public Tool getTool() {
/*  48 */     Tool selectedTool = null;
/*  49 */     String key = null;
/*     */     
/*     */     try {
/*  52 */       ValueAdapter selection = (ValueAdapter)this.valueRetrieval.getValue(CommonAttribute.DEVICE);
/*  53 */       key = (String)selection.getAdaptee();
/*  54 */     } catch (Exception e) {
/*  55 */       log.error("unable to finde the selected tool, -exception: " + e.getMessage());
/*  56 */       return null;
/*     */     } 
/*     */     
/*  59 */     List<Tool> tools = ClientAppContextProvider.getClientAppContext().getTools();
/*  60 */     for (int i = 0; i < tools.size(); i++) {
/*  61 */       Tool tool = tools.get(i);
/*  62 */       if (tool != null) {
/*  63 */         if (tool.getId().equals(key)) {
/*  64 */           selectedTool = tool;
/*     */         }
/*     */       } else {
/*  67 */         log.debug("Tool not found. ");
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     return selectedTool;
/*     */   }
/*     */   
/*     */   public Vector getECUData() {
/*  75 */     Vector result = new Vector();
/*     */     try {
/*  77 */       if (this.vit1 != null) {
/*  78 */         result = getMappingData(this.vit1.getAttributes(), result);
/*  79 */         if (this.device == null) {
/*  80 */           List cmbs = this.vit1.getControlModuleBlocks();
/*  81 */           if (cmbs != null) {
/*  82 */             result = getMappingData(cmbs, result);
/*     */           }
/*     */         } else {
/*  85 */           VIT vit = this.vit1.getControlModuleBlock(this.device);
/*  86 */           if (vit != null) {
/*  87 */             result = getMappingData(vit.getAttributes(), result);
/*     */           }
/*     */         } 
/*     */       } 
/*  91 */     } catch (Exception e) {
/*  92 */       log.error("no ECU Data , -exception: " + e.getMessage());
/*     */     } 
/*  94 */     return result;
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
/*     */   
/*     */   private Vector getMappingData(Collection attributes, Vector<Vector<String>> tempResult) {
/*     */     try {
/* 125 */       Iterator iter = attributes.iterator();
/* 126 */       while (iter.hasNext()) {
/* 127 */         Object object = iter.next();
/* 128 */         if (object instanceof Pair) {
/* 129 */           Pair pair = (Pair)object;
/* 130 */           Vector<String> row = new Vector();
/* 131 */           row.add(resourceProvider.getLabel(null, "vit." + (String)pair.getFirst()));
/* 132 */           row.add("\"" + (String)pair.getSecond() + "\"");
/* 133 */           tempResult.add(row); continue;
/*     */         } 
/* 135 */         if (object instanceof VITImpl) {
/* 136 */           tempResult = getMappingData(((VITImpl)object).getAttributes(), tempResult);
/*     */         }
/*     */       }
/*     */     
/* 140 */     } catch (Exception e) {
/* 141 */       log.error("unable to mapping data for ECU , -exception: " + e.getMessage());
/*     */     } 
/* 143 */     return tempResult;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\datamodel\ECUData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */