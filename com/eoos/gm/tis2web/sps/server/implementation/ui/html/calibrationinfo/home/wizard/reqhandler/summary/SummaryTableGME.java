/*     */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.summary;
/*     */ 
/*     */ import com.eoos.datatype.Denotation;
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ControllerReference;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.History;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Option;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.AVUtil;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.av.CustomAVMap;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.WizardPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.options.SelectedOptionsPanel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.options.SelectedOptionsTable;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.wizard.reqhandler.ButtonContainer;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.Transforming;
/*     */ import com.eoos.util.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SummaryTableGME
/*     */   extends HtmlElementContainerBase
/*     */   implements ButtonContainer
/*     */ {
/*     */   private ClientContext context;
/*     */   private ClickButtonElement historyButton;
/*     */   private CustomAVMap avMap;
/*     */   private Summary summary;
/*     */   
/*     */   public SummaryTableGME(final ClientContext context, Summary summary, final CustomAVMap avMap, WizardPanel wizard) {
/*  49 */     this.context = context;
/*  50 */     this.avMap = avMap;
/*  51 */     this.summary = summary;
/*     */     
/*  53 */     this.historyButton = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  55 */           return context.getLabel("history");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  60 */             ControllerReference ref = (ControllerReference)AVUtil.accessValue((AttributeValueMap)avMap, CommonAttribute.CONTROLLER);
/*     */             
/*  62 */             return new HistoryPopup(context, ref.getHistory());
/*  63 */           } catch (Exception e) {
/*  64 */             return "<html><body onLoad=\"javascript:window.close()\"></body></html>";
/*     */           } 
/*     */         }
/*     */         
/*     */         protected String getTargetFrame() {
/*  69 */           return "history";
/*     */         }
/*     */       };
/*  72 */     addElement((HtmlElement)this.historyButton);
/*     */ 
/*     */ 
/*     */     
/*  76 */     final List<PairImpl> options = new LinkedList();
/*     */     
/*  78 */     for (Iterator<CustomAVMap.Entry> iter = this.avMap.getExplicitEntries().iterator(); iter.hasNext(); ) {
/*  79 */       CustomAVMap.Entry entry = iter.next();
/*  80 */       Attribute attribute = entry.getAttribute();
/*  81 */       if (attribute instanceof Option && ((Option)attribute).getID().startsWith("$")) {
/*     */         continue;
/*     */       }
/*  84 */       options.add(new PairImpl(attribute, entry.getValue()));
/*     */     } 
/*     */ 
/*     */     
/*  88 */     for (int i = 0; i < 6; i++) {
/*  89 */       Attribute attribute = null;
/*  90 */       switch (i) {
/*     */         case 0:
/*  92 */           attribute = CommonAttribute.SALESMAKE;
/*     */           break;
/*     */         case 1:
/*  95 */           attribute = CommonAttribute.MODELYEAR;
/*     */           break;
/*     */         case 2:
/*  98 */           attribute = CommonAttribute.MODEL;
/*     */           break;
/*     */         case 3:
/* 101 */           attribute = CommonAttribute.CARLINE;
/*     */           break;
/*     */         case 4:
/* 104 */           attribute = CommonAttribute.ENGINE;
/*     */           break;
/*     */         case 5:
/* 107 */           attribute = CommonAttribute.TRANSMISSION;
/*     */           break;
/*     */         default:
/* 110 */           throw new IllegalStateException();
/*     */       } 
/* 112 */       Value value = this.avMap.getValue(attribute);
/* 113 */       if (value != null) {
/* 114 */         options.add(new PairImpl(attribute, value));
/*     */       }
/*     */     } 
/*     */     
/* 118 */     Collections.sort(options, new Comparator<PairImpl>() {
/*     */           public int compare(Object o1, Object o2) {
/*     */             try {
/* 121 */               Attribute a1 = (Attribute)((Pair)o1).getFirst();
/* 122 */               Attribute a2 = (Attribute)((Pair)o2).getFirst();
/* 123 */               int order1 = SummaryTableGME.this.getOrder(a1);
/* 124 */               if (order1 == -1) {
/* 125 */                 order1 = options.size() + options.indexOf(o1);
/*     */               }
/* 127 */               int order2 = SummaryTableGME.this.getOrder(a2);
/* 128 */               if (order2 == -1) {
/* 129 */                 order2 = options.size() + options.indexOf(o2);
/*     */               }
/* 131 */               return order1 - order2;
/* 132 */             } catch (Exception e) {
/* 133 */               return 0;
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 140 */     CollectionUtil.unify(options, new Transforming()
/*     */         {
/*     */           public Object transform(Object object) {
/*     */             try {
/* 144 */               Pair pair = (Pair)object;
/* 145 */               Attribute attribute = (Attribute)pair.getFirst();
/* 146 */               Value value = (Value)pair.getSecond();
/*     */               
/* 148 */               return SummaryTableGME.this.toString(attribute) + SummaryTableGME.this.toString(value);
/* 149 */             } catch (Exception e) {
/* 150 */               return object;
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 155 */     SelectedOptionsTable.Callback callback = new SelectedOptionsTable.Callback() {
/*     */         public int getOptionsCount() {
/* 157 */           return options.size();
/*     */         }
/*     */         
/*     */         public Attribute getAttribute(int optionIndex) {
/* 161 */           return (Attribute)((Pair)options.get(optionIndex)).getFirst();
/*     */         }
/*     */         
/*     */         public Value getValue(int optionIndex) {
/* 165 */           return (Value)((Pair)options.get(optionIndex)).getSecond();
/*     */         }
/*     */       };
/*     */     
/* 169 */     wizard.setSelectedOptionsPanel(new SelectedOptionsPanel(this.context, callback));
/*     */   }
/*     */ 
/*     */   
/*     */   private String toString(Object object) {
/* 174 */     if (object instanceof Denotation) {
/* 175 */       return ((Denotation)object).getDenotation(this.context.getLocale());
/*     */     }
/* 177 */     return String.valueOf(object);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getOrder(Attribute attribute) {
/* 182 */     if (attribute == CommonAttribute.VIN)
/* 183 */       return 0; 
/* 184 */     if (attribute == CommonAttribute.SALESMAKE)
/* 185 */       return 1; 
/* 186 */     if (attribute == CommonAttribute.MODELYEAR)
/* 187 */       return 2; 
/* 188 */     if (attribute == CommonAttribute.MODEL)
/* 189 */       return 3; 
/* 190 */     if (attribute == CommonAttribute.ENGINE || Util.equals(toString(attribute), toString(CommonAttribute.ENGINE)))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 195 */       return 4; } 
/* 196 */     if (attribute == CommonAttribute.CARLINE) {
/* 197 */       return 5;
/*     */     }
/* 199 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 204 */     StringBuffer table = new StringBuffer("<table><tr><th>{LABEL_SOFTWARE}</th><th>{LABEL_DESCRIPTION}</th></tr>{ROWS}</table>");
/* 205 */     StringUtilities.replace(table, "{LABEL_SOFTWARE}", this.context.getLabel("software"));
/* 206 */     StringUtilities.replace(table, "{LABEL_DESCRIPTION}", this.context.getLabel("description"));
/* 207 */     History history = this.summary.getSelectedSoftware();
/* 208 */     StringBuffer row = new StringBuffer("<tr><td>{ATTRIBUTE_TABLE}</td><td>{DESCRIPTION}</td></tr>");
/* 209 */     StringUtilities.replace(row, "{RELEASEDATE}", history.getReleaseDate());
/* 210 */     StringUtilities.replace(row, "{DESCRIPTION}", history.getDescription());
/*     */     
/* 212 */     StringBuffer attTable = new StringBuffer("<span class=\"att_table\"><table>{ROWS}</table></span>");
/* 213 */     for (Iterator<Pair> iterAttributes = history.getAttributes().iterator(); iterAttributes.hasNext(); ) {
/* 214 */       Pair pair = iterAttributes.next();
/* 215 */       String ident = String.valueOf(pair.getFirst());
/* 216 */       String value = String.valueOf(pair.getSecond());
/* 217 */       if (value != null && value.indexOf("#NULL#") >= 0) {
/* 218 */         value = "";
/*     */       }
/* 220 */       StringUtilities.replace(attTable, "{ROWS}", "<tr><th>" + ident + "</th><td>" + String.valueOf(value + "</td></tr>{ROWS}"));
/*     */     } 
/* 222 */     StringUtilities.replace(attTable, "{ROWS}", "");
/*     */     
/* 224 */     StringUtilities.replace(row, "{ATTRIBUTE_TABLE}", attTable.toString());
/* 225 */     StringUtilities.replace(table, "{ROWS}", row.toString());
/*     */     
/* 227 */     return table.toString();
/*     */   }
/*     */   
/*     */   public List getButtonElements() {
/* 231 */     List<ClickButtonElement> retValue = Collections.EMPTY_LIST;
/*     */     try {
/* 233 */       ControllerReference ref = (ControllerReference)AVUtil.accessValue((AttributeValueMap)this.avMap, CommonAttribute.CONTROLLER);
/* 234 */       if (ref.getHistory() != null) {
/* 235 */         retValue = new LinkedList();
/* 236 */         retValue.add(this.historyButton);
/*     */       } 
/* 238 */     } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/* 242 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\wizard\reqhandler\summary\SummaryTableGME.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */