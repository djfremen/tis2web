/*     */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.vit2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.VITMapAttrs;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*     */ import java.text.Format;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ public class VIT2Util
/*     */ {
/*     */   public static void addAttr(List<String> lst, VIT vit, String attrName) {
/*  27 */     String attrVal = null;
/*  28 */     if (vit != null) {
/*  29 */       attrVal = vit.getAttrValue(attrName);
/*     */     }
/*  31 */     if (attrVal == null) {
/*  32 */       attrVal = "";
/*     */     }
/*  34 */     String attr = VITMapAttrs.getAttrDesc(attrName) + ": \"" + attrVal + "" + "\"";
/*  35 */     lst.add(attr);
/*     */   }
/*     */   
/*     */   public static void addProtocol(List<String> lst, VIT vit) {
/*  39 */     String attrVal = null;
/*  40 */     int val = -1;
/*  41 */     if (vit != null) {
/*  42 */       attrVal = vit.getAttrValue("protocol");
/*     */     }
/*  44 */     if (attrVal == null) {
/*  45 */       attrVal = "";
/*     */     } else {
/*  47 */       val = Integer.parseInt(attrVal);
/*     */     } 
/*  49 */     switch (val) {
/*     */       case 0:
/*  51 */         attrVal = "UART";
/*     */         break;
/*     */       case 1:
/*  54 */         attrVal = "Class 2";
/*     */         break;
/*     */       case 2:
/*  57 */         attrVal = "KWP2000";
/*     */         break;
/*     */       case 3:
/*  60 */         attrVal = "GMLAN";
/*     */         break;
/*     */     } 
/*  63 */     String attr = VITMapAttrs.getAttrDesc("protocol") + ": \"" + attrVal + "" + "\"";
/*  64 */     lst.add(attr);
/*     */   }
/*     */   
/*     */   public static void addAttrIfExist(List<String> lst, VIT vit, String attrName) {
/*  68 */     String attrVal = null;
/*  69 */     if (vit != null) {
/*  70 */       attrVal = vit.getAttrValue(attrName);
/*     */     }
/*  72 */     if (attrVal == null) {
/*     */       return;
/*     */     }
/*  75 */     String attr = VITMapAttrs.getAttrDesc(attrName) + ": \"" + attrVal + "" + "\"";
/*  76 */     lst.add(attr);
/*     */   }
/*     */   
/*     */   public static void addAttr(List<String> lst, VIT vit, String attrName, Format format) {
/*  80 */     String attrVal = null;
/*  81 */     if (vit != null) {
/*  82 */       attrVal = vit.getAttrValue(attrName);
/*     */     } else {
/*  84 */       attrVal = "";
/*     */     } 
/*     */     try {
/*  87 */       attrVal = format.format(attrVal);
/*  88 */     } catch (Exception e) {
/*  89 */       attrVal = "";
/*     */     } 
/*     */     
/*  92 */     String attr = VITMapAttrs.getAttrDesc(attrName) + ": \"" + attrVal + "\"";
/*  93 */     lst.add(attr);
/*     */   }
/*     */   
/*     */   public static void addAttrNr(List<String> lst, VIT vit, String attrName, Format format) {
/*  97 */     String attrVal = null;
/*  98 */     if (vit != null) {
/*  99 */       attrVal = vit.getAttrValue(attrName);
/*     */     } else {
/* 101 */       attrVal = "0";
/*     */     } 
/*     */     try {
/* 104 */       attrVal = format.format(attrVal);
/* 105 */     } catch (Exception e) {
/* 106 */       attrVal = "0";
/*     */     } 
/*     */     
/* 109 */     String attr = VITMapAttrs.getAttrDesc(attrName) + ": \"" + attrVal + "\"";
/* 110 */     lst.add(attr);
/*     */   }
/*     */   
/*     */   public static void addAttr(List<String> lst, String attrName, String attrVal, Format format) {
/* 114 */     if (attrVal != null) {
/*     */       try {
/* 116 */         attrVal = format.format(attrVal);
/* 117 */       } catch (Exception e) {
/* 118 */         attrVal = "";
/*     */       } 
/*     */     } else {
/* 121 */       attrVal = "";
/*     */     } 
/* 123 */     String attr = VITMapAttrs.getAttrDesc(attrName) + ": \"" + attrVal + "\"";
/* 124 */     lst.add(attr);
/*     */   }
/*     */   
/*     */   public static void addAttr(List<String> lst, String attrName, String attrVal) {
/* 128 */     if (attrVal == null) {
/* 129 */       attrVal = "";
/*     */     }
/* 131 */     String attr = VITMapAttrs.getAttrDesc(attrName) + ": \"" + attrVal + "\"";
/* 132 */     lst.add(attr);
/*     */   }
/*     */   
/*     */   public static void addAttr4Modules(List lst, List<ProgrammingDataUnit> blobs, Map blob2size) {
/* 136 */     for (int i = 0; i < blobs.size(); i++) {
/* 137 */       ProgrammingDataUnit dataUnit = blobs.get(i);
/* 138 */       addAttr(lst, "mod_id", (new SimpleFormat(2)).format(Integer.toHexString(i).toUpperCase(Locale.ENGLISH)));
/* 139 */       addAttr(lst, "mod_partno", dataUnit.getBlobName());
/* 140 */       addAttr(lst, "mod_card", "00");
/* 141 */       addAttr(lst, "mod_page", "00");
/* 142 */       addAttr(lst, "mod_adr", "000000");
/* 143 */       int size = -1;
/* 144 */       if (blob2size.size() > 0) {
/* 145 */         size = ((Integer)blob2size.get(dataUnit.getBlobID())).intValue();
/*     */       }
/* 147 */       addAttr(lst, "mod_len", (new SimpleFormat(8)).format(Integer.toHexString(size).toUpperCase(Locale.ENGLISH)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void addSeedAttrs(List lst, VIT vit) {
/* 152 */     if (vit != null) {
/* 153 */       List<String> lstVals = vit.getAttrValues("seed");
/* 154 */       for (int i = 0; i < lstVals.size(); i++) {
/* 155 */         addAttr(lst, "seed", lstVals.get(i));
/* 156 */         addAttr(lst, "key_pair", "0000");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\vit2\VIT2Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */