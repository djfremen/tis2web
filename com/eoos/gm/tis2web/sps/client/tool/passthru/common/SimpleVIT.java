/*     */ package com.eoos.gm.tis2web.sps.client.tool.passthru.common;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VITBuilder;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.vitbuilder.VITBuilderProvider;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.ListValue;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleVIT
/*     */ {
/*  21 */   private static Logger log = Logger.getLogger(SimpleVIT.class);
/*     */   private Pair[] vitData;
/*     */   
/*     */   public SimpleVIT(Pair[] data) {
/*  25 */     this.vitData = data;
/*     */   }
/*     */   
/*     */   public SimpleVIT(List<Pair> pairs) {
/*  29 */     this.vitData = new Pair[pairs.size()];
/*     */     try {
/*  31 */       for (int i = 0; i < pairs.size(); i++) {
/*  32 */         this.vitData[i] = (Pair)new PairImpl(((Pair)pairs.get(i)).getFirst(), ((Pair)pairs.get(i)).getSecond());
/*     */       }
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("Cannot create SimpleVIT from list data provided by test driver: " + e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public VIT1 getVIT1() {
/*  40 */     VIT1 result = null;
/*     */     try {
/*  42 */       List<Pair> in = new ArrayList();
/*  43 */       for (int i = 0; i < this.vitData.length; i++) {
/*  44 */         in.add(this.vitData[i]);
/*     */       }
/*  46 */       VITBuilder builder = VITBuilderProvider.getBuilder("VIT1 Builder", in, null);
/*  47 */       result = (VIT1)builder.build();
/*  48 */     } catch (Exception e) {
/*  49 */       log.error("Cannot build VIT1 from SimpleVIT: " + e);
/*     */     } 
/*  51 */     return result;
/*     */   }
/*     */   
/*     */   public boolean hasData() {
/*  55 */     return (this.vitData != null);
/*     */   }
/*     */   
/*     */   public String getEcuAddr() {
/*  59 */     return getElementValue("ecu_adr");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValue(String element) {
/*  68 */     String result = null;
/*     */     try {
/*  70 */       result = (String)this.vitData[getElementIndex(element)].getSecond();
/*  71 */     } catch (Exception e) {
/*  72 */       log.debug("Missing VIT data element: " + element);
/*     */     } 
/*  74 */     return result;
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
/*     */   public void fixElements() {
/*     */     try {
/* 101 */       if (getElementIndex("vin") < 0) {
/* 102 */         addElement("vin", "");
/*     */       }
/* 104 */     } catch (Exception e) {
/* 105 */       log.error("Cannot fix VIT1 data");
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean update(SimpleVIT vit, AttributeValueMap context) {
/* 110 */     boolean result = false;
/*     */     try {
/* 112 */       List elements = ((ListValue)context.getValue(CommonAttribute.VIT1_TRANSFER)).getItems();
/* 113 */       Iterator<String> it = elements.iterator();
/* 114 */       while (it.hasNext()) {
/* 115 */         String element = it.next();
/* 116 */         String value = vit.getElementValue(element);
/* 117 */         setElement(element, value);
/*     */       } 
/* 119 */       result = true;
/* 120 */     } catch (Exception e) {
/* 121 */       log.error("Cannot merge (NAO) VIT1 attributes: " + e);
/*     */     } 
/* 123 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean updateSaturn(SimpleVIT vit) {
/* 129 */     boolean result = false;
/*     */     try {
/* 131 */       String mySeed = getElementValue("seed");
/* 132 */       String mySsecuhn = getElementValue("ssecuhn");
/* 133 */       this.vitData = vit.getData();
/* 134 */       setElement("seed", mySeed);
/* 135 */       setElement("ssecuhn", mySsecuhn);
/* 136 */       result = true;
/* 137 */     } catch (Exception e) {
/* 138 */       log.error("Cannot merge (SATURN) VIT1 attributes: " + e);
/*     */     } 
/* 140 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isSaturn() {
/* 144 */     boolean result = false;
/* 145 */     if (getElementIndex("ecu_config_data_length") >= 0) {
/*     */       try {
/* 147 */         int len = -1;
/* 148 */         len = Integer.valueOf(getElementValue("ecu_config_data_length")).intValue();
/* 149 */         if (len > 0) {
/* 150 */           result = true;
/*     */         }
/* 152 */       } catch (Exception e) {}
/*     */     }
/*     */     
/* 155 */     return result;
/*     */   }
/*     */   
/*     */   public SimpleVIT getClone() {
/* 159 */     Pair[] data = new Pair[this.vitData.length];
/* 160 */     for (int i = 0; i < this.vitData.length; i++) {
/* 161 */       data[i] = this.vitData[i];
/*     */     }
/* 163 */     return new SimpleVIT(data);
/*     */   }
/*     */   
/*     */   public Pair[] getClonedData() {
/* 167 */     return getClone().getData();
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
/*     */   private int getElementIndex(String element) {
/* 187 */     return getElementIndex(0, element);
/*     */   }
/*     */   
/*     */   private int getElementIndex(int fromIndex, String element) {
/* 191 */     int result = -1;
/*     */     try {
/* 193 */       for (int i = fromIndex; i < this.vitData.length; i++) {
/* 194 */         if (((String)this.vitData[i].getFirst()).compareToIgnoreCase(element) == 0) {
/* 195 */           result = i;
/*     */           break;
/*     */         } 
/*     */       } 
/* 199 */     } catch (Exception e) {
/* 200 */       log.debug("Cannot determine index for element: " + element + " from index " + fromIndex + ", " + e);
/*     */     } 
/* 202 */     return result;
/*     */   }
/*     */   
/*     */   protected Pair[] getData() {
/* 206 */     return this.vitData;
/*     */   }
/*     */   
/*     */   private synchronized void addElement(String element, String value) {
/* 210 */     Pair[] newData = new Pair[this.vitData.length + 1];
/* 211 */     for (int i = 0; i < this.vitData.length; i++) {
/* 212 */       newData[i] = this.vitData[i];
/*     */     }
/* 214 */     PairImpl pairImpl = new PairImpl(element, value);
/* 215 */     newData[newData.length - 1] = (Pair)pairImpl;
/* 216 */     this.vitData = newData;
/*     */   }
/*     */   
/*     */   private synchronized void replaceElement(String element, String value) {
/* 220 */     PairImpl pairImpl = new PairImpl(element, value);
/* 221 */     int i = getElementIndex(element);
/* 222 */     if (i >= 0) {
/* 223 */       this.vitData[i] = (Pair)pairImpl;
/*     */     }
/*     */   }
/*     */   
/*     */   private void setElement(String element, String value) {
/* 228 */     int i = getElementIndex(element);
/* 229 */     if (i < 0) {
/* 230 */       addElement(element, value);
/*     */     } else {
/* 232 */       replaceElement(element, value);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\common\SimpleVIT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */