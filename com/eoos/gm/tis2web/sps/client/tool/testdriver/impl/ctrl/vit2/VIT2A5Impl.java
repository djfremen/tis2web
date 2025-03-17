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
/*     */ public class VIT2A5Impl
/*     */   implements VIT2A
/*     */ {
/*     */   public static final short A5_VIT2_IDBYTE = 1;
/*     */   public static final short A5_VIT2_CHKSUM = 2;
/*     */   public static final short A5_VIT2_TBL_LEN = 1;
/*     */   public static final short A5_VIT2_INT_VER = 1;
/*     */   public static final short A5_VIT2_ECU_ADR = 2;
/*     */   public static final short A5_VIT2_VIN = 17;
/*     */   public static final short A5_VIT2_END_MODEL = 4;
/*     */   public static final short A5_VIT2_VMECUHN = 20;
/*     */   public static final short A5_VIT2_RSCOSN = 10;
/*     */   public static final short A5_VIT2_PD = 4;
/*     */   public static final short A5_VIT2_NUM_SD = 1;
/*     */   public static final short A5_VIT2_SEED = 2;
/*     */   public static final short A5_VIT2_KEY = 2;
/*     */   public static final short A5_VIT2_NUM_MO = 1;
/*     */   public static final short A5_VIT2_MOD_ID = 2;
/*     */   public static final short A5_VIT2_MOD_PNO = 8;
/*     */   public static final short A5_VIT2_FL_CARD = 1;
/*     */   public static final short A5_VIT2_FL_PAGE = 1;
/*     */   public static final short A5_VIT2_MOD_ADR = 4;
/*     */   public static final short A5_VIT2_MOD_LEN = 4;
/*  45 */   private VIT1 vit1 = null;
/*  46 */   private VIT2 vit2 = null;
/*  47 */   private List blobs = null;
/*  48 */   private Map blob2size = null;
/*     */ 
/*     */   
/*     */   public VIT2A5Impl(VIT1 vit1, VIT2 vit2, List blobs, Map blob2size) {
/*  52 */     this.vit1 = vit1;
/*  53 */     this.vit2 = vit2;
/*  54 */     this.blobs = blobs;
/*  55 */     this.blob2size = blob2size;
/*     */   }
/*     */   
/*     */   public List getAttributes() {
/*  59 */     Integer ecuadr = Integer.valueOf(0);
/*     */     try {
/*  61 */       ecuadr = Integer.valueOf(Integer.parseInt(this.vit2.getAttrValue("ecu_adr"), 16));
/*  62 */     } catch (Exception e) {}
/*     */     
/*  64 */     VIT currCMB = this.vit1.getControlModuleBlock(ecuadr);
/*  65 */     List lstAttrs = new ArrayList();
/*     */     
/*  67 */     VIT2Util.addAttr(lstAttrs, "table_len", getTblLen(), new SimpleFormat(4));
/*  68 */     VIT2Util.addProtocol(lstAttrs, currCMB);
/*  69 */     VIT2Util.addAttr(lstAttrs, (VIT)this.vit2, "ecu_adr");
/*  70 */     VIT2Util.addAttr(lstAttrs, (VIT)this.vit2, "vin");
/*  71 */     VIT2Util.addAttr(lstAttrs, (VIT)this.vit2, "ssecusvn");
/*  72 */     VIT2Util.addAttr(lstAttrs, currCMB, "vmecuhn");
/*  73 */     VIT2Util.addAttr(lstAttrs, "shopcode", "");
/*  74 */     VIT2Util.addAttr(lstAttrs, "progdate", (new SimpleDateFormat("yyyyMMdd")).format(new Date()));
/*  75 */     VIT2Util.addAttrNr(lstAttrs, currCMB, "numseeds", new SimpleFormat(2));
/*  76 */     VIT2Util.addSeedAttrs(lstAttrs, currCMB);
/*  77 */     VIT2Util.addAttr(lstAttrs, "num_mod", Integer.toString(this.blobs.size()));
/*  78 */     VIT2Util.addAttr4Modules(lstAttrs, this.blobs, this.blob2size);
/*     */     
/*  80 */     return lstAttrs;
/*     */   }
/*     */   
/*     */   private String getTblLen() {
/*  84 */     String numseeds = this.vit2.getAttrValue("numseeds");
/*  85 */     long lnumseeds = 0L;
/*  86 */     if (numseeds != null) {
/*     */       try {
/*  88 */         lnumseeds = Long.parseLong(numseeds);
/*  89 */       } catch (Exception e) {}
/*     */     }
/*     */     
/*  92 */     short tblLen = 0;
/*     */     
/*  94 */     tblLen = (short)(tblLen + 1);
/*  95 */     tblLen = (short)(tblLen + 2);
/*  96 */     tblLen = (short)(tblLen + 1);
/*  97 */     tblLen = (short)(tblLen + 1);
/*  98 */     tblLen = (short)(tblLen + 2);
/*  99 */     tblLen = (short)(tblLen + 17);
/* 100 */     tblLen = (short)(tblLen + 4);
/* 101 */     tblLen = (short)(tblLen + 20);
/* 102 */     tblLen = (short)(tblLen + 10);
/* 103 */     tblLen = (short)(tblLen + 4);
/* 104 */     tblLen = (short)(tblLen + 1);
/* 105 */     tblLen = (short)(int)(tblLen + lnumseeds * 4L);
/* 106 */     tblLen = (short)(tblLen + 1);
/* 107 */     tblLen = (short)(tblLen + this.blobs.size() * 20);
/* 108 */     return Integer.toHexString(tblLen).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\vit2\VIT2A5Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */