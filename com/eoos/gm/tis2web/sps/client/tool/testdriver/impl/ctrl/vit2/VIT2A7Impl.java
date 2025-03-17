/*     */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl.vit2;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.tool.common.export.VIT2;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT2A;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT;
/*     */ import com.eoos.gm.tis2web.sps.common.VIT1;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
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
/*     */ public class VIT2A7Impl
/*     */   implements VIT2A
/*     */ {
/*     */   public static final short A7_VIT2_IDBYTE = 1;
/*     */   public static final short A7_VIT2_CHKSUM = 2;
/*     */   public static final short A7_VIT2_TBL_LEN = 2;
/*     */   public static final short A7_VIT2_NAV_INFO = 20;
/*     */   public static final short A7_VIT2_RESERVED = 4;
/*     */   public static final short A7_VIT2_VIN = 17;
/*     */   public static final short A7_VIT2_RSCOSN = 10;
/*     */   public static final short A7_VIT2_PD = 4;
/*     */   public static final short A7_VIT2_EVNT_TYPE = 1;
/*     */   public static final short A7_VIT2_NUM_CMB = 1;
/*     */   public static final short A7_VIT2_BLK_LEN = 2;
/*     */   public static final short A7_VIT2_DISP_TYPE = 1;
/*     */   public static final short A7_VIT2_DEVICE_ID = 1;
/*     */   public static final short A7_VIT2_PROTOCOL = 1;
/*     */   public static final short A7_VIT2_END_MOD_PART_NUM = 16;
/*     */   public static final short A7_VIT2_MANUFACT_HW_NUM = 20;
/*     */   public static final short A7_VIT2_SUPPLIER_HW_NUM = 20;
/*     */   public static final short A7_VIT2_SAE_DIAG_CONN = 2;
/*     */   public static final short A7_VIT2_NUM_SD = 1;
/*     */   public static final short A7_VIT2_SEED = 2;
/*     */   public static final short A7_VIT2_KEY = 2;
/*     */   public static final short A7_VIT2_NUM_OF_PARTS = 1;
/*     */   public static final short A7_VIT2_MOD_ID = 2;
/*     */   public static final short A7_VIT2_MOD_PART_NUMBER = 16;
/*     */   public static final short A7_VIT2_FL_CARD = 1;
/*     */   public static final short A7_VIT2_FL_PAGE = 1;
/*     */   public static final short A7_VIT2_MOD_ADR = 4;
/*     */   public static final short A7_VIT2_MOD_LENGTH = 4;
/*     */   public static final short A7_VIT2_ECU_CONF_DATA_LEN = 2;
/*     */   public static final short A7_VIT2_ECU_CONF_DATA_BYTE = 1;
/*  55 */   private VIT1 vit1 = null;
/*  56 */   private VIT2 vit2 = null;
/*  57 */   private List blobs = null;
/*  58 */   private Map blob2size = null;
/*     */ 
/*     */   
/*     */   public VIT2A7Impl(VIT1 vit1, VIT2 vit2, List blobs, Map blob2size) {
/*  62 */     this.vit1 = vit1;
/*  63 */     this.vit2 = vit2;
/*  64 */     this.blobs = blobs;
/*  65 */     this.blob2size = blob2size;
/*     */   }
/*     */   
/*     */   public List getAttributes() {
/*  69 */     Integer ecuadr = Integer.valueOf(0);
/*     */     try {
/*  71 */       ecuadr = Integer.valueOf(Integer.parseInt(this.vit2.getAttrValue("ecu_adr"), 16));
/*  72 */     } catch (Exception e) {}
/*     */     
/*  74 */     VIT currCMB = this.vit1.getControlModuleBlock(ecuadr);
/*  75 */     List lstAttrs = new ArrayList();
/*  76 */     long numseeds = getLongAttr("numseeds");
/*  77 */     long confBytesNo = getLongAttr("config_area_size");
/*     */     
/*  79 */     VIT2Util.addAttr(lstAttrs, "table_len", getTblLen(numseeds, confBytesNo), new SimpleFormat(4));
/*  80 */     VIT2Util.addAttr(lstAttrs, (VIT)this.vit2, "nav_info");
/*  81 */     VIT2Util.addAttr(lstAttrs, "reserved", "FFFFFFFF");
/*  82 */     VIT2Util.addAttr(lstAttrs, (VIT)this.vit2, "vin");
/*  83 */     VIT2Util.addAttrIfExist(lstAttrs, (VIT)this.vit2, "SecCodeVeh");
/*  84 */     VIT2Util.addAttr(lstAttrs, "shopcode", "");
/*  85 */     VIT2Util.addAttr(lstAttrs, "progdate", (new SimpleDateFormat("yyyyMMdd")).format(new Date()));
/*  86 */     VIT2Util.addAttr(lstAttrs, "event_type", "Programming Event");
/*  87 */     VIT2Util.addAttr(lstAttrs, "numcms", "01");
/*  88 */     VIT2Util.addAttr(lstAttrs, "blocklen", getBlockLen(numseeds, confBytesNo), new SimpleFormat(4));
/*  89 */     VIT2Util.addAttr(lstAttrs, currCMB, "disp_type");
/*  90 */     VIT2Util.addAttr(lstAttrs, (VIT)this.vit2, "ecu_adr");
/*  91 */     VIT2Util.addProtocol(lstAttrs, currCMB);
/*  92 */     VIT2Util.addAttr(lstAttrs, (VIT)this.vit2, "ssecusvn");
/*  93 */     VIT2Util.addAttr(lstAttrs, currCMB, "vmecuhn");
/*  94 */     VIT2Util.addAttr(lstAttrs, (VIT)this.vit2, "ssecuhn");
/*  95 */     VIT2Util.addAttr(lstAttrs, currCMB, "pinnum");
/*  96 */     VIT2Util.addAttrNr(lstAttrs, currCMB, "numseeds", new SimpleFormat(2));
/*  97 */     VIT2Util.addSeedAttrs(lstAttrs, currCMB);
/*  98 */     VIT2Util.addAttr(lstAttrs, "num_mod", Integer.toString(this.blobs.size()), new SimpleFormat(2));
/*  99 */     VIT2Util.addAttr4Modules(lstAttrs, this.blobs, this.blob2size);
/* 100 */     VIT2Util.addAttr(lstAttrs, "config_area_size", getHexAttr(currCMB, "config_area_size"), new SimpleFormat(4));
/* 101 */     VIT2Util.addAttrIfExist(lstAttrs, (VIT)this.vit2, "conf_byte");
/*     */     
/* 103 */     return lstAttrs;
/*     */   }
/*     */   
/*     */   private String getTblLen(long numseeds, long confBytesNo) {
/* 107 */     short tblLen = 0;
/* 108 */     tblLen = (short)(tblLen + 1);
/* 109 */     tblLen = (short)(tblLen + 2);
/* 110 */     tblLen = (short)(tblLen + 2);
/* 111 */     tblLen = (short)(tblLen + 20);
/* 112 */     tblLen = (short)(tblLen + 4);
/* 113 */     tblLen = (short)(tblLen + 17);
/* 114 */     tblLen = (short)(tblLen + 10);
/* 115 */     tblLen = (short)(tblLen + 4);
/* 116 */     tblLen = (short)(tblLen + 1);
/* 117 */     tblLen = (short)(tblLen + 1);
/* 118 */     tblLen = (short)(tblLen + 2);
/* 119 */     tblLen = (short)(tblLen + 1);
/* 120 */     tblLen = (short)(tblLen + 1);
/* 121 */     tblLen = (short)(tblLen + 1);
/* 122 */     tblLen = (short)(tblLen + 16);
/* 123 */     tblLen = (short)(tblLen + 20);
/* 124 */     tblLen = (short)(tblLen + 20);
/* 125 */     tblLen = (short)(tblLen + 2);
/* 126 */     tblLen = (short)(tblLen + 1);
/* 127 */     tblLen = (short)(int)(tblLen + numseeds * 4L);
/* 128 */     tblLen = (short)(tblLen + 1);
/* 129 */     tblLen = (short)(tblLen + this.blobs.size() * 28);
/* 130 */     tblLen = (short)(tblLen + 2);
/* 131 */     tblLen = (short)(int)(tblLen + confBytesNo * 1L);
/* 132 */     return Integer.toHexString(tblLen).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   private String getBlockLen(long numseeds, long confBytesNo) {
/* 136 */     short blockLen = 0;
/*     */     
/* 138 */     blockLen = (short)(blockLen + 2);
/* 139 */     blockLen = (short)(blockLen + 1);
/* 140 */     blockLen = (short)(blockLen + 1);
/* 141 */     blockLen = (short)(blockLen + 1);
/* 142 */     blockLen = (short)(blockLen + 16);
/* 143 */     blockLen = (short)(blockLen + 20);
/* 144 */     blockLen = (short)(blockLen + 20);
/* 145 */     blockLen = (short)(blockLen + 2);
/* 146 */     blockLen = (short)(blockLen + 1);
/* 147 */     blockLen = (short)(int)(blockLen + numseeds * 4L);
/* 148 */     blockLen = (short)(blockLen + 1);
/* 149 */     blockLen = (short)(blockLen + this.blobs.size() * 28);
/* 150 */     blockLen = (short)(blockLen + 2);
/* 151 */     blockLen = (short)(int)(blockLen + confBytesNo * 1L);
/* 152 */     return Integer.toHexString(blockLen).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */   
/*     */   private long getLongAttr(String attrName) {
/* 156 */     String val = this.vit2.getAttrValue(attrName);
/* 157 */     long lval = 0L;
/* 158 */     if (val != null) {
/*     */       try {
/* 160 */         lval = Long.parseLong(val);
/* 161 */       } catch (Exception e) {}
/*     */     }
/*     */     
/* 164 */     return lval;
/*     */   }
/*     */   
/*     */   private String getHexAttr(VIT cmb, String attrName) {
/* 168 */     String val = null;
/* 169 */     if (cmb != null) {
/* 170 */       val = cmb.getAttrValue(attrName);
/*     */     }
/* 172 */     int lval = 0;
/* 173 */     if (val != null) {
/*     */       try {
/* 175 */         lval = Integer.parseInt(val);
/* 176 */       } catch (Exception e) {}
/*     */     }
/*     */     
/* 179 */     return Integer.toHexString(lval).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\vit2\VIT2A7Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */