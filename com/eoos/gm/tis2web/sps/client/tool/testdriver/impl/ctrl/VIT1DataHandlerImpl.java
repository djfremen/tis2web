/*     */ package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.ctrl;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.sps.client.tool.testdriver.VIT1DataHandler;
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
/*     */ public class VIT1DataHandlerImpl
/*     */   implements VIT1DataHandler
/*     */ {
/*     */   public List extractVIT1Data(List<String> lstVIT1, ITestDriverSettings settings) {
/*  27 */     List<PairImpl> vit1Attrs = new ArrayList();
/*  28 */     boolean readCMBData = false;
/*  29 */     List<List> lstCMBBlocks = new ArrayList();
/*  30 */     List lstCMBBlock = new ArrayList();
/*  31 */     List<PairImpl> lstOptions = new ArrayList();
/*  32 */     List lstVIT1Header = new ArrayList();
/*     */     
/*  34 */     for (int i = 0; i < lstVIT1.size(); i++) {
/*  35 */       String line = lstVIT1.get(i);
/*  36 */       line = line.trim();
/*  37 */       if (line.length() != 0)
/*     */       {
/*     */         
/*  40 */         if (!readCMBData) {
/*  41 */           if (line.startsWith(VITMapAttrs.getAttrDesc("blocklen"))) {
/*  42 */             insertVIT1Attribute(line, lstCMBBlock);
/*  43 */             readCMBData = true;
/*  44 */           } else if (line.startsWith("Rem") && !line.startsWith("Rem Option")) {
/*  45 */             extractComment(line, settings);
/*  46 */           } else if (line.startsWith("Rem Option")) {
/*  47 */             lstOptions.add(new PairImpl("option", extractAttrValue(line)));
/*     */           } else {
/*  49 */             insertVIT1Attribute(line, lstVIT1Header);
/*     */           }
/*     */         
/*  52 */         } else if (line.startsWith(VITMapAttrs.getAttrDesc("blocklen"))) {
/*  53 */           lstCMBBlocks.add(lstCMBBlock);
/*  54 */           lstCMBBlock = new ArrayList();
/*  55 */           insertVIT1Attribute(line, lstCMBBlock);
/*  56 */           readCMBData = true;
/*     */         } else {
/*  58 */           insertVIT1Attribute(line, lstCMBBlock);
/*  59 */           if (line.startsWith("Rem END")) {
/*  60 */             lstCMBBlocks.add(lstCMBBlock);
/*  61 */             lstCMBBlock = new ArrayList();
/*  62 */             readCMBData = false;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*  67 */     if (lstCMBBlock.size() > 0) {
/*  68 */       lstCMBBlocks.add(lstCMBBlock);
/*     */     }
/*  70 */     vit1Attrs.addAll(lstVIT1Header);
/*  71 */     addLoggingInfo(vit1Attrs, settings);
/*  72 */     String vit1TypeID = getAttrValue(vit1Attrs, "sps_id");
/*  73 */     if (vit1TypeID == null || vit1TypeID.length() == 0) {
/*  74 */       vit1TypeID = "A7";
/*  75 */       setAttribute(vit1Attrs, (Pair)new PairImpl("sps_id", vit1TypeID));
/*     */     } 
/*  77 */     addCMBBloks(vit1Attrs, lstCMBBlocks, vit1TypeID);
/*  78 */     vit1Attrs.addAll(lstOptions);
/*  79 */     adaptAttributes(vit1Attrs, vit1TypeID);
/*  80 */     return vit1Attrs;
/*     */   }
/*     */   
/*     */   private void setAttribute(List<Pair> vit1Attrs, Pair attr) {
/*  84 */     boolean foundAttr = false;
/*  85 */     int pos = 0;
/*     */     
/*  87 */     for (int i = 0; i < vit1Attrs.size(); i++) {
/*  88 */       if ("vit_type".equals(((Pair)vit1Attrs.get(i)).getFirst())) {
/*  89 */         pos = i;
/*     */       }
/*  91 */       if (attr.getFirst().equals(((Pair)vit1Attrs.get(i)).getFirst())) {
/*  92 */         vit1Attrs.set(i, attr);
/*  93 */         foundAttr = true;
/*     */       } 
/*     */     } 
/*  96 */     if (!foundAttr) {
/*  97 */       vit1Attrs.add(pos, attr);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getAttrValue(List<Pair> lstAttrs, String attrName) {
/* 102 */     for (int i = 0; i < lstAttrs.size(); i++) {
/* 103 */       Pair attr = lstAttrs.get(i);
/* 104 */       String name = (String)attr.getFirst();
/* 105 */       if (attrName.equals(name)) {
/* 106 */         return (String)attr.getSecond();
/*     */       }
/*     */     } 
/* 109 */     return null;
/*     */   }
/*     */   
/*     */   private void adaptAttributes(List lstAttrs, String vit1TypeID) {
/* 113 */     for (int j = 0; j < lstAttrs.size(); j++) {
/* 114 */       adaptAttrValue(lstAttrs, j);
/* 115 */       if (vit1TypeID.compareToIgnoreCase("A5") == 0) {
/* 116 */         adaptAttrName(lstAttrs, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addLoggingInfo(List<PairImpl> lstAttrs, ITestDriverSettings settings) {
/* 122 */     lstAttrs.add(new PairImpl("vit_name", settings.getVIT1FileName()));
/* 123 */     lstAttrs.add(new PairImpl("vit_id", settings.getVITID()));
/*     */   }
/*     */   
/*     */   private void addCMBBloks(List lstAttrs, List<List> lstCMBBlocks, String vit1TypeID) {
/* 127 */     for (int i = 0; i < lstCMBBlocks.size(); i++) {
/* 128 */       List lstCMBBlock = lstCMBBlocks.get(i);
/* 129 */       if (vit1TypeID.compareToIgnoreCase("A7") == 0) {
/* 130 */         sortCMBList(lstCMBBlock);
/*     */       }
/* 132 */       lstAttrs.addAll(lstCMBBlock);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void adaptAttrName(List<Pair> lst, int i) {
/* 137 */     Pair orgAttr = lst.get(i);
/* 138 */     String attrName = (String)orgAttr.getFirst();
/* 139 */     if (attrName.equals("protocol")) {
/* 140 */       attrName = "int_ver";
/* 141 */       PairImpl pairImpl = new PairImpl(attrName, orgAttr.getSecond());
/* 142 */       lst.set(i, pairImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void adaptAttrValue(List<Pair> lst, int i) {
/* 147 */     Pair orgAttr = lst.get(i);
/* 148 */     String attrName = (String)orgAttr.getFirst();
/* 149 */     String attrVal = (String)orgAttr.getSecond();
/* 150 */     if (attrName.equals("blocklen") || attrName.equals("table_len")) {
/*     */       try {
/* 152 */         long l = Long.parseLong(attrVal, 16);
/* 153 */         attrVal = Long.toString(l);
/* 154 */         PairImpl pairImpl = new PairImpl(attrName, attrVal);
/* 155 */         lst.set(i, pairImpl);
/* 156 */       } catch (Exception e) {}
/*     */     }
/*     */     
/* 159 */     if (attrName.equals("protocol") || attrName.equals("int_ver")) {
/* 160 */       if (attrVal.compareToIgnoreCase("UART") == 0) {
/* 161 */         attrVal = "00";
/*     */       }
/* 163 */       if (attrVal.compareToIgnoreCase("Class 2") == 0) {
/* 164 */         attrVal = "01";
/*     */       }
/* 166 */       if (attrVal.compareToIgnoreCase("KWP2000") == 0) {
/* 167 */         attrVal = "02";
/*     */       }
/* 169 */       if (attrVal.compareToIgnoreCase("GMLAN") == 0) {
/* 170 */         attrVal = "03";
/*     */       }
/* 172 */       PairImpl pairImpl = new PairImpl(attrName, attrVal);
/* 173 */       lst.set(i, pairImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sortCMBList(List<Pair> lst) {
/* 178 */     int pos = -1;
/* 179 */     for (int i = 0; i < lst.size(); i++) {
/* 180 */       Pair attr = lst.get(i);
/* 181 */       if ("blocklen".equals(attr.getFirst())) {
/* 182 */         pos = i;
/*     */       }
/* 184 */       if (pos > -1 && "ssecuhn".equals(attr.getFirst())) {
/* 185 */         lst.set(i, lst.get(pos));
/* 186 */         lst.set(pos, attr);
/* 187 */         pos = -1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void extractComment(String line, ITestDriverSettings settings) {
/* 193 */     if (line.indexOf("VIT_ID") != -1) {
/* 194 */       settings.setVITID(extractAttrValue(line));
/*     */     }
/* 196 */     if (line.indexOf("Status") != -1) {
/* 197 */       settings.setStatus(extractAttrValue(line));
/*     */     }
/* 199 */     if (line.indexOf("Write_Modules") != -1) {
/* 200 */       settings.setWriteModules(extractAttrValue(line));
/*     */     }
/* 202 */     if (line.indexOf("Read_Handling") != -1) {
/* 203 */       settings.setReadHandling(extractAttrValue(line));
/*     */     }
/* 205 */     if (line.indexOf("Rename_VIT1") != -1) {
/* 206 */       settings.setRenameVIT1(extractAttrValue(line));
/*     */     }
/*     */   }
/*     */   
/*     */   private void insertVIT1Attribute(String line, List<Pair> vit1Attrs) {
/* 211 */     Pair vit1Attr = extractVIT1Attribute(line);
/* 212 */     if (vit1Attr != null)
/* 213 */       vit1Attrs.add(vit1Attr); 
/*     */   }
/*     */   
/*     */   private Pair extractVIT1Attribute(String line) {
/*     */     PairImpl pairImpl;
/* 218 */     String attrName = extractAttrName(line);
/* 219 */     String attrVal = extractAttrValue(line);
/* 220 */     Pair vit1Attr = null;
/* 221 */     if (attrName != null && attrVal != null) {
/* 222 */       pairImpl = new PairImpl(attrName, attrVal);
/*     */     }
/* 224 */     return (Pair)pairImpl;
/*     */   }
/*     */   
/*     */   private String extractAttrName(String line) {
/* 228 */     int indx = line.indexOf(":");
/* 229 */     if (indx > 0) {
/* 230 */       String attrDesc = line.substring(0, indx);
/* 231 */       attrDesc = attrDesc.trim();
/* 232 */       return VITMapAttrs.getAttrName(attrDesc);
/*     */     } 
/* 234 */     return null;
/*     */   }
/*     */   
/*     */   private String extractAttrValue(String line) {
/* 238 */     int indx = line.indexOf(":");
/* 239 */     if (indx > 0) {
/* 240 */       String attrVal = line.substring(indx + 1);
/* 241 */       attrVal = trim(attrVal);
/* 242 */       if (attrVal.startsWith("\"")) {
/* 243 */         attrVal = attrVal.substring(1);
/*     */       }
/* 245 */       if (attrVal.endsWith("\"")) {
/* 246 */         attrVal = attrVal.substring(0, attrVal.length() - 1);
/*     */       }
/* 248 */       return attrVal;
/*     */     } 
/* 250 */     return "";
/*     */   }
/*     */   
/*     */   private String trim(String org) {
/* 254 */     String orgStr = org;
/* 255 */     while (orgStr.startsWith("\t") || orgStr.startsWith(" ")) {
/* 256 */       orgStr = orgStr.substring(1);
/*     */     }
/* 258 */     while (orgStr.endsWith("\t") || orgStr.endsWith(" ")) {
/* 259 */       orgStr = orgStr.substring(0, orgStr.length() - 1);
/*     */     }
/* 261 */     return orgStr;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\ctrl\VIT1DataHandlerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */