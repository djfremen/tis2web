/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.util.DisplayUtil;
/*     */ import com.eoos.gm.tis2web.sps.client.util.TableUtilities;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DefaultValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Request;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.DefaultTableModel;
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
/*     */ public class CustomizeJTable
/*     */   extends JTable
/*     */   implements AttributeInput, ValueRetrieval
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  36 */   private static final Logger log = Logger.getLogger(CustomizeJTable.class);
/*     */   
/*  38 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*     */   private AssignmentRequest dataReq;
/*  40 */   private List optionValues = null;
/*     */   
/*     */   public AssignmentRequest getRequest() {
/*  43 */     return this.dataReq;
/*     */   }
/*     */   
/*     */   public CustomizeJTable() {
/*  47 */     setRowHeight(TableUtilities.getHeightFont(this));
/*  48 */     getTableHeader().setReorderingAllowed(false);
/*     */   }
/*     */   
/*     */   public void setRequest(Request dataRequest) {
/*     */     try {
/*  53 */       this.dataReq = (AssignmentRequest)dataRequest;
/*  54 */       this.optionValues = ((SelectionRequest)this.dataReq).getOptions();
/*  55 */       List allData = (List)DisplayUtil.createDisplayAdapter(((SelectionRequest)dataRequest).getOptions());
/*  56 */       createModel(allData);
/*  57 */       if (dataRequest instanceof DefaultValueRetrieval) {
/*  58 */         Value defaultValue = ((DefaultValueRetrieval)dataRequest).getDefaultValue();
/*  59 */         setDefaultValue(defaultValue);
/*     */       }
/*     */     
/*  62 */     } catch (Exception e) {
/*  63 */       log.error("Exception in setRequest method:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFunctionSelectionRequest(Request dataRequest, Value controllerReference, boolean orderByASC) {
/*     */     try {
/*  69 */       this.dataReq = (AssignmentRequest)dataRequest;
/*  70 */       this.optionValues = ((SelectionRequest)this.dataReq).getOptions();
/*  71 */       if (orderByASC) {
/*  72 */         sortAlphabeticalDescriptions();
/*     */       }
/*  74 */       List allData = (List)DisplayUtil.createDisplayAdapter(this.optionValues);
/*  75 */       createModel(allData);
/*     */     }
/*  77 */     catch (Exception e) {
/*  78 */       log.error("Exception in setFunctionSelectionRequest method :" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRequest(Request dataRequest, boolean orderByASC) {
/*     */     try {
/*  84 */       this.dataReq = (AssignmentRequest)dataRequest;
/*  85 */       this.optionValues = ((SelectionRequest)this.dataReq).getOptions();
/*  86 */       if (orderByASC) {
/*  87 */         sortAlphabeticalDescriptions();
/*     */       }
/*  89 */       List allData = (List)DisplayUtil.createDisplayAdapter(this.optionValues);
/*  90 */       createModel(allData);
/*  91 */       if (dataRequest instanceof DefaultValueRetrieval) {
/*  92 */         Value defaultValue = ((DefaultValueRetrieval)dataRequest).getDefaultValue();
/*  93 */         setDefaultValue(defaultValue);
/*     */       }
/*     */     
/*  96 */     } catch (Exception e) {
/*  97 */       log.error("Exception in setRequest method :" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void createModel(List dataValues) {
/*     */     try {
/* 103 */       DefaultTableModel model = new DefaultTableModel() {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public boolean isCellEditable(int rowIndex, int columnIndex) {
/* 107 */             return false;
/*     */           }
/*     */         };
/* 110 */       String TAB = "\t";
/* 111 */       Vector<Vector<String>> result = new Vector();
/* 112 */       Vector<String> columns = new Vector();
/*     */       
/* 114 */       Iterator iterator = dataValues.iterator();
/* 115 */       while (iterator.hasNext()) {
/* 116 */         Vector<String> temp = new Vector();
/* 117 */         Object adapter = iterator.next();
/* 118 */         String totalString = adapter.toString();
/* 119 */         int indexTab = totalString.indexOf("\t");
/* 120 */         if (indexTab == -1) {
/* 121 */           temp.add(totalString);
/*     */         } else {
/*     */           
/* 124 */           while (indexTab != -1) {
/* 125 */             String part = totalString.substring(0, indexTab);
/* 126 */             temp.addElement(part);
/* 127 */             totalString = totalString.substring(indexTab + 1, totalString.length());
/* 128 */             indexTab = totalString.indexOf("\t");
/*     */           } 
/* 130 */           temp.addElement(totalString);
/*     */         } 
/* 132 */         result.addElement(temp);
/*     */       } 
/* 134 */       if (result.size() != 0) {
/* 135 */         int size = ((Vector)result.get(0)).size();
/* 136 */         if (this.dataReq instanceof com.eoos.gm.tis2web.sps.common.ControllerSelectionRequest && size == 4) {
/* 137 */           columns.add(resourceProvider.getLabel(null, "controllerScreen.prom.service_no"));
/* 138 */           columns.add(resourceProvider.getLabel(null, "controllerScreen.prom.ecu_type"));
/* 139 */           columns.add(resourceProvider.getLabel(null, "controllerScreen.prom.scan_id"));
/* 140 */           columns.add(resourceProvider.getLabel(null, "controllerScreen.prom.bcc_ext"));
/*     */         } else {
/* 142 */           for (int i = 0; i < size; i++) {
/* 143 */             columns.add("");
/*     */           }
/*     */         } 
/*     */       } 
/* 147 */       model.setDataVector(result, columns);
/* 148 */       setModel(model);
/* 149 */     } catch (Exception e) {
/* 150 */       log.error("Exception in createModel :" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute getAttribute() {
/* 158 */     return this.dataReq.getAttribute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getValues() {
/* 165 */     return this.optionValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sortAlphabeticalValues() {
/* 172 */     if (this.optionValues != null) {
/* 173 */       Collections.sort(this.optionValues, new Comparator()
/*     */           {
/*     */             public int compare(Object obj, Object obj1) {
/*     */               try {
/* 177 */                 return obj.toString().compareToIgnoreCase(obj1.toString());
/* 178 */               } catch (Exception e) {
/* 179 */                 CustomizeJTable.log.debug("Error in method sortAlphabeticalValues, e:" + e.getMessage());
/* 180 */                 return -1;
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sortAlphabeticalDescriptions() {
/* 192 */     if (this.optionValues != null) {
/* 193 */       Collections.sort(this.optionValues, new Comparator() {
/*     */             public int compare(Object obj1, Object obj2) {
/* 195 */               String TAB = "\t";
/* 196 */               String totalString1 = obj1.toString();
/* 197 */               String totalString2 = obj2.toString();
/*     */               
/* 199 */               int indexTab1 = totalString1.indexOf("\t");
/* 200 */               int indexTab2 = totalString2.indexOf("\t");
/*     */               try {
/* 202 */                 if (indexTab1 == -1) {
/* 203 */                   return totalString1.compareToIgnoreCase(totalString2);
/*     */                 }
/* 205 */                 String desc1 = totalString1.substring(indexTab1 + 1, totalString1.length());
/* 206 */                 String desc2 = totalString2.substring(indexTab2 + 1, totalString2.length());
/* 207 */                 return desc1.compareToIgnoreCase(desc2);
/*     */               }
/* 209 */               catch (Exception e) {
/* 210 */                 CustomizeJTable.log.debug("Error in method sortAlphabeticalValues, e:" + e.getMessage());
/* 211 */                 return -1;
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Value getValue(Attribute attr) {
/* 222 */     int rowSelected = getSelectedRow();
/* 223 */     if (rowSelected != -1) {
/* 224 */       List<Value> originalValues = getValues();
/* 225 */       for (int i = 0; i < originalValues.size(); i++) {
/* 226 */         if (rowSelected == i) {
/* 227 */           return originalValues.get(i);
/*     */         }
/*     */       } 
/*     */     } 
/* 231 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Value getSelectedValue() {
/* 241 */     return getValue(getAttribute());
/*     */   }
/*     */   
/*     */   protected void setDefaultValue(Value defaultValue) {
/* 245 */     List originalValues = getValues();
/* 246 */     for (int i = 0; i < originalValues.size(); i++) {
/* 247 */       if (defaultValue.equals(originalValues.get(i)))
/* 248 */         setRowSelectionInterval(i, i); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\CustomizeJTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */