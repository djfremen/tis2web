/*     */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.ITestDriverSettings;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings.VITMapAttrs;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class VIT1DataHandler
/*     */ {
/*     */   public static final String COMMENT = "Rem";
/*     */   public static final String OPTION = "Rem Option";
/*     */   public static final String VIT_ID = "VIT_ID";
/*     */   public static final String STATUS = "Status";
/*     */   public static final String WRITE_MODULES = "Write_Modules";
/*     */   public static final String READ_HANDLING = "Read_Handling";
/*     */   public static final String RENAME_VIT1 = "Rename_VIT1";
/*     */   public static final String VAL_AFTER_READ = "After Read";
/*     */   public static final String VAL_AFTER_DOWNLOAD = "After Download";
/*     */   public static final String VAL_READ = "\"read\"";
/*     */   public static final String EXT_TMP = "_TMP_";
/*     */   
/*     */   public List extractVIT1Data(List<String> lstVIT1, ITestDriverSettings settings) {
/*  40 */     List<PairImpl> vit1Attrs = new ArrayList();
/*  41 */     boolean readCMBData = false;
/*  42 */     List<List> lstCMBBlocks = new ArrayList();
/*  43 */     List lstCMBBlock = new ArrayList();
/*  44 */     List<PairImpl> lstOptions = new ArrayList();
/*  45 */     List lstVIT1Header = new ArrayList();
/*     */     
/*  47 */     for (int i = 0; i < lstVIT1.size(); i++) {
/*  48 */       String line = lstVIT1.get(i);
/*  49 */       line = line.trim();
/*  50 */       if (line.length() != 0)
/*     */       {
/*     */         
/*  53 */         if (!readCMBData) {
/*  54 */           if (line.startsWith(VITMapAttrs.getAttrDesc("blocklen"))) {
/*  55 */             insertVIT1Attribute(line, lstCMBBlock);
/*  56 */             readCMBData = true;
/*  57 */           } else if (line.startsWith("Rem") && !line.startsWith("Rem Option")) {
/*  58 */             extractComment(line, settings);
/*  59 */           } else if (line.startsWith("Rem Option")) {
/*  60 */             lstOptions.add(new PairImpl("option", extractAttrValue(line)));
/*     */           } else {
/*  62 */             insertVIT1Attribute(line, lstVIT1Header);
/*     */           }
/*     */         
/*  65 */         } else if (line.startsWith(VITMapAttrs.getAttrDesc("blocklen"))) {
/*  66 */           lstCMBBlocks.add(lstCMBBlock);
/*  67 */           lstCMBBlock = new ArrayList();
/*  68 */           insertVIT1Attribute(line, lstCMBBlock);
/*  69 */           readCMBData = true;
/*     */         } else {
/*  71 */           insertVIT1Attribute(line, lstCMBBlock);
/*  72 */           if (line.startsWith("Rem END")) {
/*  73 */             lstCMBBlocks.add(lstCMBBlock);
/*  74 */             lstCMBBlock = new ArrayList();
/*  75 */             readCMBData = false;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*  80 */     if (lstCMBBlock.size() > 0) {
/*  81 */       lstCMBBlocks.add(lstCMBBlock);
/*     */     }
/*  83 */     vit1Attrs.addAll(lstVIT1Header);
/*  84 */     addLoggingInfo(vit1Attrs, settings);
/*  85 */     String vit1TypeID = getAttrValue(vit1Attrs, "sps_id");
/*  86 */     if (vit1TypeID == null || vit1TypeID.length() == 0) {
/*  87 */       vit1TypeID = "A7";
/*  88 */       setAttribute(vit1Attrs, (Pair)new PairImpl("sps_id", vit1TypeID));
/*     */     } 
/*  90 */     addCMBBloks(vit1Attrs, lstCMBBlocks, vit1TypeID);
/*  91 */     vit1Attrs.addAll(lstOptions);
/*  92 */     adaptAttributes(vit1Attrs, vit1TypeID);
/*  93 */     return vit1Attrs;
/*     */   }
/*     */   
/*     */   private void setAttribute(List<Pair> vit1Attrs, Pair attr) {
/*  97 */     boolean foundAttr = false;
/*  98 */     int pos = 0;
/*     */     
/* 100 */     for (int i = 0; i < vit1Attrs.size(); i++) {
/* 101 */       if ("vit_type".equals(((Pair)vit1Attrs.get(i)).getFirst())) {
/* 102 */         pos = i;
/*     */       }
/* 104 */       if (attr.getFirst().equals(((Pair)vit1Attrs.get(i)).getFirst())) {
/* 105 */         vit1Attrs.set(i, attr);
/* 106 */         foundAttr = true;
/*     */       } 
/*     */     } 
/* 109 */     if (!foundAttr) {
/* 110 */       vit1Attrs.add(pos, attr);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getAttrValue(List<Pair> lstAttrs, String attrName) {
/* 115 */     for (int i = 0; i < lstAttrs.size(); i++) {
/* 116 */       Pair attr = lstAttrs.get(i);
/* 117 */       String name = (String)attr.getFirst();
/* 118 */       if (attrName.equals(name)) {
/* 119 */         return (String)attr.getSecond();
/*     */       }
/*     */     } 
/* 122 */     return null;
/*     */   }
/*     */   
/*     */   private void adaptAttributes(List lstAttrs, String vit1TypeID) {
/* 126 */     for (int j = 0; j < lstAttrs.size(); j++) {
/* 127 */       adaptAttrValue(lstAttrs, j);
/* 128 */       if (vit1TypeID.compareToIgnoreCase("A5") == 0) {
/* 129 */         adaptAttrName(lstAttrs, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addLoggingInfo(List<PairImpl> lstAttrs, ITestDriverSettings settings) {
/* 135 */     lstAttrs.add(new PairImpl("vit_name", settings.getVIT1FileName()));
/* 136 */     lstAttrs.add(new PairImpl("vit_id", settings.getVITID()));
/*     */   }
/*     */   
/*     */   private void addCMBBloks(List lstAttrs, List<List> lstCMBBlocks, String vit1TypeID) {
/* 140 */     for (int i = 0; i < lstCMBBlocks.size(); i++) {
/* 141 */       List lstCMBBlock = lstCMBBlocks.get(i);
/* 142 */       if (vit1TypeID.compareToIgnoreCase("A7") == 0) {
/* 143 */         sortCMBList(lstCMBBlock);
/*     */       }
/* 145 */       lstAttrs.addAll(lstCMBBlock);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void adaptAttrName(List<Pair> lst, int i) {
/* 150 */     Pair orgAttr = lst.get(i);
/* 151 */     String attrName = (String)orgAttr.getFirst();
/* 152 */     if (attrName.equals("protocol")) {
/* 153 */       attrName = "int_ver";
/* 154 */       PairImpl pairImpl = new PairImpl(attrName, orgAttr.getSecond());
/* 155 */       lst.set(i, pairImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void adaptAttrValue(List<Pair> lst, int i) {
/* 160 */     Pair orgAttr = lst.get(i);
/* 161 */     String attrName = (String)orgAttr.getFirst();
/* 162 */     String attrVal = (String)orgAttr.getSecond();
/* 163 */     if (attrName.equals("blocklen") || attrName.equals("table_len")) {
/*     */       try {
/* 165 */         long l = Long.parseLong(attrVal, 16);
/* 166 */         attrVal = Long.toString(l);
/* 167 */         PairImpl pairImpl = new PairImpl(attrName, attrVal);
/* 168 */         lst.set(i, pairImpl);
/* 169 */       } catch (Exception e) {}
/*     */     }
/*     */     
/* 172 */     if (attrName.equals("protocol") || attrName.equals("int_ver")) {
/* 173 */       if (attrVal.compareToIgnoreCase("UART") == 0) {
/* 174 */         attrVal = "00";
/*     */       }
/* 176 */       if (attrVal.compareToIgnoreCase("Class 2") == 0) {
/* 177 */         attrVal = "01";
/*     */       }
/* 179 */       if (attrVal.compareToIgnoreCase("KWP2000") == 0) {
/* 180 */         attrVal = "02";
/*     */       }
/* 182 */       if (attrVal.compareToIgnoreCase("GMLAN") == 0) {
/* 183 */         attrVal = "03";
/*     */       }
/* 185 */       PairImpl pairImpl = new PairImpl(attrName, attrVal);
/* 186 */       lst.set(i, pairImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sortCMBList(List<Pair> lst) {
/* 191 */     int pos = -1;
/* 192 */     for (int i = 0; i < lst.size(); i++) {
/* 193 */       Pair attr = lst.get(i);
/* 194 */       if ("blocklen".equals(attr.getFirst())) {
/* 195 */         pos = i;
/*     */       }
/* 197 */       if (pos > -1 && "ssecuhn".equals(attr.getFirst())) {
/* 198 */         lst.set(i, lst.get(pos));
/* 199 */         lst.set(pos, attr);
/* 200 */         pos = -1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void extractComment(String line, ITestDriverSettings settings) {
/* 206 */     if (line.indexOf("VIT_ID") != -1) {
/* 207 */       settings.setVITID(extractAttrValue(line));
/*     */     }
/* 209 */     if (line.indexOf("Status") != -1) {
/* 210 */       settings.setStatus(extractAttrValue(line));
/*     */     }
/* 212 */     if (line.indexOf("Write_Modules") != -1) {
/* 213 */       settings.setWriteModules(extractAttrValue(line));
/*     */     }
/* 215 */     if (line.indexOf("Read_Handling") != -1) {
/* 216 */       settings.setReadHandling(extractAttrValue(line));
/*     */     }
/* 218 */     if (line.indexOf("Rename_VIT1") != -1) {
/* 219 */       settings.setRenameVIT1(extractAttrValue(line));
/*     */     }
/*     */   }
/*     */   
/*     */   private void insertVIT1Attribute(String line, List<Pair> vit1Attrs) {
/* 224 */     Pair vit1Attr = extractVIT1Attribute(line);
/* 225 */     if (vit1Attr != null)
/* 226 */       vit1Attrs.add(vit1Attr); 
/*     */   }
/*     */   
/*     */   private Pair extractVIT1Attribute(String line) {
/*     */     PairImpl pairImpl;
/* 231 */     String attrName = extractAttrName(line);
/* 232 */     String attrVal = extractAttrValue(line);
/* 233 */     Pair vit1Attr = null;
/* 234 */     if (attrName != null && attrVal != null) {
/* 235 */       pairImpl = new PairImpl(attrName, attrVal);
/*     */     }
/* 237 */     return (Pair)pairImpl;
/*     */   }
/*     */   
/*     */   private String extractAttrName(String line) {
/* 241 */     int indx = line.indexOf(":");
/* 242 */     if (indx > 0) {
/* 243 */       String attrDesc = line.substring(0, indx);
/* 244 */       attrDesc = attrDesc.trim();
/* 245 */       return VITMapAttrs.getAttrName(attrDesc);
/*     */     } 
/* 247 */     return null;
/*     */   }
/*     */   
/*     */   private String extractAttrValue(String line) {
/* 251 */     int indx = line.indexOf(":");
/* 252 */     if (indx > 0) {
/* 253 */       String attrVal = line.substring(indx + 1);
/* 254 */       attrVal = trim(attrVal);
/* 255 */       if (attrVal.startsWith("\"")) {
/* 256 */         attrVal = attrVal.substring(1);
/*     */       }
/* 258 */       if (attrVal.endsWith("\"")) {
/* 259 */         attrVal = attrVal.substring(0, attrVal.length() - 1);
/*     */       }
/* 261 */       return attrVal;
/*     */     } 
/* 263 */     return "";
/*     */   }
/*     */   
/*     */   private String trim(String org) {
/* 267 */     String orgStr = org;
/* 268 */     while (orgStr.startsWith("\t") || orgStr.startsWith(" ")) {
/* 269 */       orgStr = orgStr.substring(1);
/*     */     }
/* 271 */     while (orgStr.endsWith("\t") || orgStr.startsWith(" ")) {
/* 272 */       orgStr = orgStr.substring(0, orgStr.length() - 1);
/*     */     }
/* 274 */     return orgStr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\VIT1DataHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */