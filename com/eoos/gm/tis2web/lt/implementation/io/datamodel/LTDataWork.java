/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTDataWork
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int LT_PSEUDOMAIN = 1;
/*     */   public static final int LT_MAINADDON = 2;
/*     */   public static final int LT_EXCLUSIVADDON = 3;
/*     */   public static final int LT_ADDON = 4;
/*     */   public static final int LT_MAIN = 5;
/*     */   public static final int LT_ADDONBRACKET = 6;
/*  23 */   private String description = null;
/*     */   
/*  25 */   private String algoCode = null;
/*     */ 
/*     */   
/*     */   private boolean needsvalidation = true;
/*     */ 
/*     */   
/*  31 */   private String nr = null;
/*     */   
/*  33 */   private int tasktype = 0;
/*     */   
/*  35 */   private int warrantyFlag = 0;
/*     */ 
/*     */   
/*     */   private boolean changeFlag = false;
/*     */ 
/*     */   
/*  41 */   private String laquerDegree = null;
/*     */   
/*  43 */   private Integer mc = Integer.valueOf(-1);
/*     */   
/*  45 */   private LinkedList textList = null;
/*     */   
/*  47 */   private List SXAWList = null;
/*     */   
/*  49 */   private LinkedList tcList = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LTDataWork create(Integer mc, String nr, String description, int tasktype, String laquerDegree, int warrantyFlag, boolean changeFlag, String algoCode) {
/*  56 */     LTDataWork ret = new LTDataWork();
/*  57 */     ret.mc = mc;
/*  58 */     ret.nr = nr;
/*  59 */     ret.description = description;
/*  60 */     ret.tasktype = tasktype;
/*  61 */     ret.laquerDegree = laquerDegree;
/*  62 */     ret.warrantyFlag = warrantyFlag;
/*  63 */     ret.changeFlag = changeFlag;
/*     */     
/*  65 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean needsValidation() {
/*  69 */     return this.needsvalidation;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  73 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/*  77 */     this.description = description;
/*     */   }
/*     */   
/*     */   public String getAlgoCode() {
/*  81 */     return this.algoCode;
/*     */   }
/*     */   
/*     */   public void setAlgoCode(String algoCode) {
/*  85 */     if (algoCode != null) {
/*  86 */       this.algoCode = algoCode.trim().toUpperCase();
/*     */     } else {
/*  88 */       this.algoCode = null;
/*     */     } 
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
/*     */   public static String getMainWorkNr(String x) {
/* 104 */     if (x.length() <= 7) {
/* 105 */       return x;
/*     */     }
/* 107 */     return x.substring(0, 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMainWorkNr() {
/* 112 */     return this.nr.substring(0, 7);
/*     */   }
/*     */   
/*     */   public String getNr() {
/* 116 */     return this.nr;
/*     */   }
/*     */   
/*     */   public void setNr(String nr) {
/* 120 */     this.nr = nr;
/*     */   }
/*     */   
/*     */   public LTSXAWData getSXAWbyInernalID(int i) {
/* 124 */     if (this.SXAWList == null) {
/* 125 */       return null;
/*     */     }
/*     */     
/* 128 */     for (Iterator<LTSXAWData> tcIt = this.SXAWList.iterator(); tcIt.hasNext(); ) {
/* 129 */       LTSXAWData curtc = tcIt.next();
/* 130 */       if (curtc.getInternalID() == i) {
/* 131 */         return curtc;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return null;
/*     */   }
/*     */   
/*     */   public int getTasktype() {
/* 139 */     return this.tasktype;
/*     */   }
/*     */   
/*     */   public String getTasktypeAsString() {
/* 143 */     switch (this.tasktype) {
/*     */       case 1:
/* 145 */         return "LT_PSEUDOMAIN";
/*     */       case 2:
/* 147 */         return "LT_MAINADDON";
/*     */       case 3:
/* 149 */         return "LT_EXCLUSIVADDON";
/*     */       case 4:
/* 151 */         return "LT_ADDON";
/*     */       case 5:
/* 153 */         return "LT_MAIN";
/*     */       case 6:
/* 155 */         return "LT_ADDONBRACKET";
/*     */     } 
/* 157 */     return "LT_MAIN";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTasktype(int tasktype) {
/* 162 */     this.tasktype = tasktype;
/*     */   }
/*     */   
/*     */   public int getWarrantyFlag() {
/* 166 */     return this.warrantyFlag;
/*     */   }
/*     */   
/*     */   public void setWarrantyFlag(int warrantyFlag) {
/* 170 */     this.warrantyFlag = warrantyFlag;
/*     */   }
/*     */   
/*     */   public boolean isChangeFlag() {
/* 174 */     return this.changeFlag;
/*     */   }
/*     */   
/*     */   public void setChangeFlag(boolean changeFlag) {
/* 178 */     this.changeFlag = changeFlag;
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
/*     */   public String getLaquerDegree() {
/* 190 */     return this.laquerDegree;
/*     */   }
/*     */   
/*     */   public void setLaquerDegree(String laquerDegree) {
/* 194 */     this.laquerDegree = laquerDegree;
/*     */   }
/*     */   
/*     */   public Integer getMc() {
/* 198 */     return this.mc;
/*     */   }
/*     */   
/*     */   public void setMc(Integer mc) {
/* 202 */     this.mc = mc;
/*     */   }
/*     */   
/*     */   public LinkedList getTextList() {
/* 206 */     return this.textList;
/*     */   }
/*     */   
/*     */   public void setTextList(LinkedList textList) {
/* 210 */     this.textList = textList;
/*     */   }
/*     */   
/*     */   public List getSXAWList() {
/* 214 */     return this.SXAWList;
/*     */   }
/*     */   
/*     */   public void setSXAWList(List SXAWList, boolean bneedsval) {
/* 218 */     this.SXAWList = SXAWList;
/* 219 */     this.needsvalidation = bneedsval;
/*     */   }
/*     */   
/*     */   public LinkedList getTcList() {
/* 223 */     return this.tcList;
/*     */   }
/*     */   
/*     */   public boolean isMainWork() {
/* 227 */     return isMainWork(this.tasktype);
/*     */   }
/*     */   
/*     */   public static boolean isMainWork(int iWorkType) {
/* 231 */     return (iWorkType == 5 || iWorkType == 2 || iWorkType == 1);
/*     */   }
/*     */   
/*     */   public void setTcList(LinkedList tcList) {
/* 235 */     this.tcList = tcList;
/*     */   }
/*     */   
/*     */   public LTTroublecode getTC(String tc) {
/* 239 */     if (this.tcList == null) {
/* 240 */       return null;
/*     */     }
/*     */     
/* 243 */     if (tc != null) {
/* 244 */       for (Iterator<LTTroublecode> tcIt = this.tcList.iterator(); tcIt.hasNext(); ) {
/* 245 */         LTTroublecode curtc = tcIt.next();
/* 246 */         if (curtc.getTroubleCode().equals(tc)) {
/* 247 */           return curtc;
/*     */         }
/*     */       } 
/*     */     }
/* 251 */     return null;
/*     */   }
/*     */   
/*     */   public LTDataWork copy() {
/* 255 */     LTDataWork oW = new LTDataWork();
/* 256 */     oW.description = this.description;
/* 257 */     oW.nr = this.nr;
/* 258 */     oW.needsvalidation = this.needsvalidation;
/* 259 */     oW.tasktype = this.tasktype;
/* 260 */     oW.warrantyFlag = this.warrantyFlag;
/* 261 */     oW.changeFlag = this.changeFlag;
/* 262 */     oW.laquerDegree = this.laquerDegree;
/* 263 */     oW.mc = this.mc;
/* 264 */     oW.textList = this.textList;
/* 265 */     oW.SXAWList = this.SXAWList;
/* 266 */     oW.tcList = this.tcList;
/* 267 */     oW.algoCode = this.algoCode;
/* 268 */     return oW;
/*     */   }
/*     */   
/*     */   public LTDataWork doCloneForSXAWData() {
/* 272 */     LTDataWork oW = new LTDataWork();
/* 273 */     oW.description = this.description;
/*     */     
/* 275 */     oW.nr = this.nr;
/* 276 */     oW.needsvalidation = this.needsvalidation;
/*     */     
/* 278 */     oW.tasktype = this.tasktype;
/* 279 */     oW.warrantyFlag = this.warrantyFlag;
/* 280 */     oW.changeFlag = this.changeFlag;
/* 281 */     oW.laquerDegree = this.laquerDegree;
/* 282 */     oW.mc = this.mc;
/* 283 */     oW.textList = this.textList;
/* 284 */     if (this.SXAWList != null) {
/* 285 */       oW.SXAWList = CloneSXAWList(this.SXAWList);
/*     */     } else {
/* 287 */       oW.SXAWList = null;
/*     */     } 
/* 289 */     oW.tcList = this.tcList;
/* 290 */     oW.algoCode = this.algoCode;
/* 291 */     return oW;
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 295 */     LTDataWork oW = new LTDataWork();
/* 296 */     oW.description = this.description;
/*     */     
/* 298 */     oW.nr = this.nr;
/* 299 */     oW.needsvalidation = this.needsvalidation;
/*     */     
/* 301 */     oW.tasktype = this.tasktype;
/* 302 */     oW.warrantyFlag = this.warrantyFlag;
/* 303 */     oW.changeFlag = this.changeFlag;
/* 304 */     oW.laquerDegree = this.laquerDegree;
/* 305 */     oW.mc = this.mc;
/* 306 */     if (this.textList != null) {
/* 307 */       oW.textList = (LinkedList)this.textList.clone();
/*     */     } else {
/* 309 */       oW.textList = null;
/* 310 */     }  if (this.SXAWList != null) {
/* 311 */       oW.SXAWList = CloneSXAWList(this.SXAWList);
/*     */     } else {
/* 313 */       oW.SXAWList = null;
/* 314 */     }  if (this.tcList != null) {
/* 315 */       oW.tcList = (LinkedList)this.tcList.clone();
/*     */     } else {
/* 317 */       oW.tcList = null;
/* 318 */     }  oW.algoCode = this.algoCode;
/* 319 */     return oW;
/*     */   }
/*     */   
/*     */   public List CloneSXAWList(List oL) {
/* 323 */     List<Object> oR = new ArrayList(oL.size());
/* 324 */     for (Iterator<LTSXAWData> it = oL.iterator(); it.hasNext(); ) {
/* 325 */       LTSXAWData oD = it.next();
/* 326 */       oR.add(oD.clone());
/*     */     } 
/* 328 */     return oR;
/*     */   }
/*     */   
/*     */   public String getTCString() {
/* 332 */     StringBuffer bufTemp = new StringBuffer(1024);
/*     */     
/* 334 */     if (getTcList() != null && getTcList().size() > 0) {
/* 335 */       Iterator<LTTroublecode> tcIt = getTcList().iterator();
/* 336 */       while (tcIt.hasNext()) {
/* 337 */         bufTemp.append(((LTTroublecode)tcIt.next()).getTroubleCode());
/* 338 */         if (tcIt.hasNext()) {
/* 339 */           bufTemp.append(',');
/*     */         }
/*     */       } 
/*     */     } 
/* 343 */     return bufTemp.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTDataWork.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */