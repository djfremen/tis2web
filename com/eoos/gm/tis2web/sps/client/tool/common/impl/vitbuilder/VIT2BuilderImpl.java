/*     */ package com.eoos.gm.tis2web.sps.client.tool.common.impl.vitbuilder;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VITBuilder;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.impl.VIT2Impl;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.OptionByte;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VIT2BuilderImpl
/*     */   implements VITBuilder
/*     */ {
/*  19 */   private List lstAttrs = null;
/*  20 */   private ProgrammingData progData = null;
/*  21 */   private VIT2 vit2 = null;
/*     */   
/*     */   public VIT2BuilderImpl(Object lstAttrs, Object progData) {
/*  24 */     this.lstAttrs = (List)lstAttrs;
/*  25 */     this.progData = (ProgrammingData)progData;
/*  26 */     this.vit2 = (VIT2)new VIT2Impl();
/*  27 */     init();
/*     */   }
/*     */   
/*     */   protected void init() {
/*  31 */     this.vit2.addAttribute((Pair)new PairImpl("ssecuhn", "0"));
/*  32 */     this.vit2.addAttribute((Pair)new PairImpl("vin", this.progData.getVIN()));
/*  33 */     this.vit2.addAttribute((Pair)new PairImpl("snoet", "0"));
/*  34 */     this.vit2.addAttribute((Pair)new PairImpl("vmecuhn", "0"));
/*  35 */     this.vit2.addAttribute((Pair)new PairImpl("ssecusvn", (this.progData.getSSECUSVN() == null) ? "0" : this.progData.getSSECUSVN()));
/*  36 */     this.vit2.addAttribute((Pair)new PairImpl("ecu_adr", Integer.toHexString(this.progData.getDeviceID().intValue())));
/*  37 */     this.vit2.addAttribute((Pair)new PairImpl("num_part", "0"));
/*  38 */     this.vit2.addAttribute((Pair)new PairImpl("numcms", "1"));
/*  39 */     this.vit2.addAttribute((Pair)new PairImpl("blocklen", "0"));
/*  40 */     this.vit2.addAttribute((Pair)new PairImpl("disp_type", "0"));
/*  41 */     this.vit2.addAttribute((Pair)new PairImpl("protocol", "1"));
/*  42 */     this.vit2.addAttribute((Pair)new PairImpl("swcompat_id", "0"));
/*  43 */     this.vit2.addAttribute((Pair)new PairImpl("diagdata_id", "0"));
/*  44 */     this.vit2.addAttribute((Pair)new PairImpl("shopcode", "0"));
/*  45 */     this.vit2.addAttribute((Pair)new PairImpl("progdate", "0"));
/*  46 */     this.vit2.addAttribute((Pair)new PairImpl("pinnum", "0"));
/*  47 */     this.vit2.addAttribute((Pair)new PairImpl("numseeds", "0"));
/*  48 */     this.vit2.addAttribute((Pair)new PairImpl("event_type", "1"));
/*     */   }
/*     */   
/*     */   public VIT build() {
/*  52 */     List vit1Header = new ArrayList();
/*  53 */     Pair attr = null;
/*  54 */     for (int i = 0; i < this.lstAttrs.size(); i++) {
/*  55 */       attr = this.lstAttrs.get(i);
/*  56 */       if (!addSSECUHNAttr(attr))
/*     */       {
/*     */         
/*  59 */         if (!addSNOETAttr(attr))
/*     */         {
/*     */           
/*  62 */           if (!addBLOCKLENAttr(attr))
/*     */           {
/*     */             
/*  65 */             if (!addRestOfAttrs(attr))
/*     */             {
/*     */               
/*  68 */               if (addVIT1HeaderAttrs(attr, vit1Header)); }  } 
/*     */         }
/*     */       }
/*     */     } 
/*  72 */     this.vit2.addAttributes(vit1Header);
/*     */     
/*  74 */     this.vit2.addAttribute((Pair)new PairImpl("post_prog_instructions", ""));
/*     */     
/*  76 */     addOdometerAndKeycode();
/*     */     
/*  78 */     if (this.progData.getOptionBytes() != null) {
/*  79 */       addAdaptivesAndSelectedOptionBytes(this.progData);
/*     */     } else {
/*  81 */       addAdaptivesAndSelectedOptionBytes(this.lstAttrs);
/*     */     } 
/*     */     
/*  84 */     if (this.progData.getRepairShopCode() != null) {
/*  85 */       this.vit2.setAttribute((Pair)new PairImpl("shopcode", this.progData.getRepairShopCode()));
/*     */     }
/*  87 */     return (VIT)this.vit2;
/*     */   }
/*     */   
/*     */   private Pair getAttribute(String attrName) {
/*  91 */     for (int i = 0; i < this.lstAttrs.size(); i++) {
/*  92 */       Pair attr = this.lstAttrs.get(i);
/*  93 */       String name = (String)attr.getFirst();
/*  94 */       if (attrName.equals(name)) {
/*  95 */         return attr;
/*     */       }
/*     */     } 
/*  98 */     return null;
/*     */   }
/*     */   
/*     */   private boolean addSSECUHNAttr(Pair attr) {
/* 102 */     if ("ssecuhn".equals(attr.getFirst())) {
/* 103 */       this.vit2.setAttribute(attr);
/* 104 */       this.vit2.setAttribute((Pair)new PairImpl("vin", this.progData.getVIN()));
/* 105 */       return true;
/*     */     } 
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   private boolean addSNOETAttr(Pair attr) {
/* 111 */     if ("snoet".equals(attr.getFirst())) {
/* 112 */       this.vit2.setAttribute(attr);
/* 113 */       this.vit2.setAttribute((Pair)new PairImpl("vmecuhn", (this.progData.getVMECUHN() == null) ? "0" : this.progData.getVMECUHN()));
/* 114 */       this.vit2.setAttribute((Pair)new PairImpl("ssecusvn", (this.progData.getSSECUSVN() == null) ? "0" : this.progData.getSSECUSVN()));
/* 115 */       attr = getAttribute("ecu_adr");
/* 116 */       this.vit2.setAttribute((attr != null) ? attr : (Pair)new PairImpl("ecu_adr", Integer.toHexString(this.progData.getDeviceID().intValue())));
/* 117 */       return true;
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   private boolean addBLOCKLENAttr(Pair attr) {
/* 123 */     if ("blocklen".equals(attr.getFirst())) {
/* 124 */       Pair attrNumcms = getAttribute("numcms");
/* 125 */       this.vit2.setAttribute((attrNumcms != null) ? attrNumcms : (Pair)new PairImpl("numcms", ""));
/* 126 */       this.vit2.setAttribute(attr);
/* 127 */       return true;
/*     */     } 
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   private boolean addRestOfAttrs(Pair attr) {
/* 133 */     if ("partno".equals(attr.getFirst()) || "seed".equals(attr.getFirst()) || "conf_byte".equals(attr.getFirst())) {
/* 134 */       this.vit2.addAttribute(attr);
/* 135 */       return true;
/*     */     } 
/* 137 */     if ("num_part".equals(attr.getFirst()) || "disp_type".equals(attr.getFirst()) || "protocol".equals(attr.getFirst()) || "swcompat_id".equals(attr.getFirst()) || "diagdata_id".equals(attr.getFirst()) || "shopcode".equals(attr.getFirst()) || "progdate".equals(attr.getFirst()) || "pinnum".equals(attr.getFirst()) || "numseeds".equals(attr.getFirst()) || "config_area_size".equals(attr.getFirst()) || "devicetype".equals(attr.getFirst()) || "spsmode".equals(attr.getFirst())) {
/*     */       
/* 139 */       this.vit2.setAttribute(attr);
/* 140 */       return true;
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   private boolean addVIT1HeaderAttrs(Pair attr, List<Pair> vit1Header) {
/* 146 */     if ("sps_id".equals(attr.getFirst()) || "chksum".equals(attr.getFirst()) || "table_len".equals(attr.getFirst()) || "nav_info".equals(attr.getFirst()) || "reserved".equals(attr.getFirst())) {
/* 147 */       vit1Header.add(attr);
/* 148 */       return true;
/*     */     } 
/* 150 */     return false;
/*     */   }
/*     */   
/*     */   private void addOdometerAndKeycode() {
/* 154 */     if (this.progData.getOdometer() != null) {
/* 155 */       this.vit2.addAttribute((Pair)new PairImpl("odometer", this.progData.getOdometer()));
/*     */     }
/* 157 */     if (this.progData.getKeycode() != null) {
/* 158 */       this.vit2.addAttribute((Pair)new PairImpl("keycode", this.progData.getKeycode()));
/*     */     }
/*     */   }
/*     */   
/*     */   private void addAdaptivesAndSelectedOptionBytes(List<Pair> lstAttrs) {
/* 163 */     int len = 0;
/* 164 */     List<Pair> tempLst = new ArrayList();
/*     */     
/* 166 */     for (int i = 0; i < lstAttrs.size(); i++) {
/* 167 */       Pair attr = lstAttrs.get(i);
/* 168 */       if ("ecu_config_data_length".equals(attr.getFirst())) {
/* 169 */         long length = 0L;
/*     */         try {
/* 171 */           length = Long.parseLong((String)attr.getSecond(), 16);
/* 172 */         } catch (Exception e) {}
/*     */ 
/*     */         
/* 175 */         for (int j = 1; j <= length && i + j < lstAttrs.size(); j++) {
/* 176 */           attr = lstAttrs.get(i + j);
/* 177 */           if ("type".equals(attr.getFirst())) {
/* 178 */             String attrVal = (String)attr.getSecond();
/* 179 */             if (attrVal.equals("00") || attrVal.equals("01")) {
/* 180 */               tempLst.add(attr);
/* 181 */               attr = lstAttrs.get(++j + i);
/* 182 */               tempLst.add(attr);
/* 183 */               attr = lstAttrs.get(++j + i);
/* 184 */               tempLst.add(attr);
/* 185 */               attr = lstAttrs.get(++j + i);
/* 186 */               tempLst.add(attr);
/* 187 */               len += 4;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 193 */     if (len > 0) {
/* 194 */       this.vit2.addAttribute((Pair)new PairImpl("ecu_config_data_length", Integer.toHexString(len)));
/* 195 */       this.vit2.addAttributes(tempLst);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addAdaptivesAndSelectedOptionBytes(ProgrammingData progData) {
/* 200 */     List<OptionByte> optionBytes = progData.getOptionBytes();
/* 201 */     this.vit2.addAttribute((Pair)new PairImpl("ecu_config_data_length", Integer.toHexString(optionBytes.size() * 4)));
/* 202 */     for (int i = 0; i < optionBytes.size(); i++) {
/* 203 */       OptionByte option = optionBytes.get(i);
/* 204 */       this.vit2.addAttribute((Pair)new PairImpl("type", "0" + option.getType()));
/* 205 */       this.vit2.addAttribute((Pair)new PairImpl("blocknum", Integer.toHexString(option.getBlockNum() & 0xFF)));
/* 206 */       this.vit2.addAttribute((Pair)new PairImpl("bytenum", Integer.toHexString(option.getByteNum() & 0xFF)));
/* 207 */       this.vit2.addAttribute((Pair)new PairImpl("bytedata", Integer.toHexString(option.getData() & 0xFF)));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\impl\vitbuilder\VIT2BuilderImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */