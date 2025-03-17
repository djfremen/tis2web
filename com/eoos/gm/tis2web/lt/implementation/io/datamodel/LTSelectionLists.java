/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class LTSelectionLists
/*     */ {
/*  23 */   private Map mMainOps = null;
/*     */   
/*  25 */   private Map mMain2Add = null;
/*     */   
/*  27 */   private Map mAddOps = null;
/*     */   
/*  29 */   private Map mAddNrs = null;
/*     */ 
/*     */   
/*  32 */   private List lMainOps = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LTSelectionLists() {
/*  38 */     clear();
/*     */   }
/*     */   
/*     */   public void clear() {
/*  42 */     this.lMainOps = new LinkedList();
/*  43 */     this.mMainOps = new HashMap<Object, Object>();
/*  44 */     this.mMain2Add = new HashMap<Object, Object>();
/*  45 */     this.mAddOps = new HashMap<Object, Object>();
/*  46 */     this.mAddNrs = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   public LTDataWork getWork(String nr, int iWorkType) {
/*  50 */     if (LTDataWork.isMainWork(iWorkType)) {
/*  51 */       return (LTDataWork)this.mMainOps.get(nr.substring(0, 7));
/*     */     }
/*  53 */     return (LTDataWork)this.mAddOps.get(nr);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addWork(LTDataWork w, LTSXAWData sx, LTTroublecode c, String oStoreUnderThisNumberAlso) {
/*  58 */     if (!canAddWork(w)) {
/*  59 */       return false;
/*     */     }
/*     */     
/*  62 */     LTDataWork w2 = w.copy();
/*  63 */     w2.setSXAWList(new ArrayList(1), false);
/*  64 */     w2.getSXAWList().add(sx);
/*  65 */     w2.setTcList(new LinkedList());
/*  66 */     if (c != null) {
/*  67 */       w2.getTcList().add(c);
/*     */     }
/*     */     
/*  70 */     if (w.isMainWork()) {
/*  71 */       if (oStoreUnderThisNumberAlso != null) {
/*  72 */         this.mAddNrs.put(oStoreUnderThisNumberAlso, w2);
/*     */       }
/*     */ 
/*     */       
/*  76 */       addMainWork(w2);
/*     */     } else {
/*  78 */       addAddOnWork(w2);
/*     */     } 
/*     */     
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   public boolean contains(String onr, int iWorkType, int InternalIDSXAWData) {
/*  85 */     if (!contains(onr, iWorkType)) {
/*  86 */       return false;
/*     */     }
/*     */     
/*  89 */     LTDataWork oW = getWork(onr, iWorkType);
/*  90 */     if (oW == null || oW.getSXAWList() == null) {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     return (oW.getSXAWbyInernalID(InternalIDSXAWData) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String onr, int iWorkType) {
/* 100 */     if (iWorkType == 5 || iWorkType == 2 || iWorkType == 1) {
/* 101 */       if (this.mAddNrs.containsKey(onr)) {
/* 102 */         return true;
/*     */       }
/* 104 */       return this.mMainOps.containsKey(onr.substring(0, 7));
/*     */     } 
/* 106 */     return this.mAddOps.containsKey(onr);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean addMainWork(LTDataWork w) {
/* 111 */     if (this.lMainOps.size() > 0 && W100000Handler.isW100000Work(this.lMainOps.get(this.lMainOps.size() - 1))) {
/*     */ 
/*     */ 
/*     */       
/* 115 */       this.lMainOps.add(this.lMainOps.size() - 1, w.getNr());
/*     */     } else {
/* 117 */       this.lMainOps.add(w.getMainWorkNr());
/*     */     } 
/*     */ 
/*     */     
/* 121 */     this.mMainOps.put(w.getMainWorkNr(), w);
/* 122 */     return true;
/*     */   }
/*     */   
/*     */   private boolean addAddOnWork(LTDataWork w) {
/* 126 */     List<LTDataWork> lAdd = (List)this.mMain2Add.get(w.getMainWorkNr());
/* 127 */     if (lAdd == null) {
/*     */       
/* 129 */       lAdd = new LinkedList();
/* 130 */       this.mMain2Add.put(w.getMainWorkNr(), lAdd);
/*     */     } 
/* 132 */     lAdd.add(w);
/*     */     
/* 134 */     this.mAddOps.put(w.getNr(), w);
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAddWork(LTDataWork work) {
/* 140 */     String onr = work.getMainWorkNr();
/*     */ 
/*     */     
/* 143 */     boolean bMainThere = this.mMainOps.containsKey(onr);
/*     */ 
/*     */     
/* 146 */     if (work.getTasktype() == 5 || work.getTasktype() == 2 || work.getTasktype() == 1) {
/* 147 */       if (bMainThere) {
/* 148 */         return false;
/*     */       }
/* 150 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 155 */     if (!bMainThere) {
/* 156 */       return false;
/*     */     }
/*     */     
/* 159 */     if (this.mAddOps.containsKey(work.getNr()))
/*     */     {
/* 161 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (work.getTasktype() == 3)
/*     */     {
/* 172 */       return canAddExclusivAddOn(work);
/*     */     }
/*     */     
/* 175 */     if (work.getTasktype() == 4 && 
/* 176 */       containsExlusivAddOn(work)) {
/* 177 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 182 */     return true;
/*     */   }
/*     */   
/*     */   public boolean containsExlusivAddOn(LTDataWork w) {
/* 186 */     List l = (List)this.mMain2Add.get(w.getMainWorkNr());
/*     */     
/* 188 */     if (l == null) {
/* 189 */       return false;
/*     */     }
/*     */     
/* 192 */     for (Iterator<LTDataWork> it = l.iterator(); it.hasNext();) {
/* 193 */       if (((LTDataWork)it.next()).getTasktype() == 3) {
/* 194 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 198 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canAddExclusivAddOn(LTDataWork w) {
/* 202 */     List l = (List)this.mMain2Add.get(w.getMainWorkNr());
/* 203 */     if (l == null) {
/* 204 */       return true;
/*     */     }
/*     */     
/* 207 */     for (Iterator<LTDataWork> it = l.iterator(); it.hasNext();) {
/* 208 */       if (((LTDataWork)it.next()).getTasktype() != 6) {
/* 209 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 213 */     return true;
/*     */   }
/*     */   
/*     */   public boolean containsMainWork(String nr) {
/* 217 */     return this.mMainOps.containsKey(nr);
/*     */   }
/*     */   
/*     */   public boolean containsAddWork(String nr) {
/* 221 */     return this.mAddOps.containsKey(nr);
/*     */   }
/*     */   
/*     */   class WorkComp
/*     */     implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/* 227 */       LTDataWork ow1 = (LTDataWork)o1;
/* 228 */       LTDataWork ow2 = (LTDataWork)o2;
/* 229 */       int i1 = Integer.valueOf(ow1.getNr().substring(1)).intValue();
/* 230 */       int i2 = Integer.valueOf(ow2.getNr().substring(1)).intValue();
/*     */       
/* 232 */       if (i1 < i2) {
/* 233 */         return -1;
/*     */       }
/* 235 */       if (i1 > i2) {
/* 236 */         return 1;
/*     */       }
/*     */       
/* 239 */       return 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public List getTALList() {
/* 244 */     new WorkComp();
/* 245 */     LinkedList<LTDataWork> oRes = new LinkedList();
/*     */ 
/*     */ 
/*     */     
/* 249 */     for (Iterator<String> itm = this.lMainOps.iterator(); itm.hasNext(); ) {
/* 250 */       LTDataWork work = (LTDataWork)this.mMainOps.get(itm.next());
/*     */       
/* 252 */       oRes.add(work);
/*     */       
/* 254 */       if (this.mMain2Add.containsKey(work.getMainWorkNr())) {
/* 255 */         List<? extends LTDataWork> ladd = (List)this.mMain2Add.get(work.getMainWorkNr());
/*     */         
/* 257 */         oRes.addAll(ladd);
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     return oRes;
/*     */   }
/*     */   
/*     */   public void removeWork(String oNr, int iWorkType) {
/* 265 */     String oMain = oNr.substring(0, 7);
/*     */     
/* 267 */     if (LTDataWork.isMainWork(iWorkType)) {
/*     */       
/* 269 */       LTDataWork w = getWork(oMain, iWorkType);
/* 270 */       if (w == null) {
/*     */         return;
/*     */       }
/*     */       
/* 274 */       this.lMainOps.remove(oMain);
/* 275 */       this.mMainOps.remove(oMain);
/*     */ 
/*     */       
/* 278 */       if (this.mAddNrs.containsValue(w)) {
/* 279 */         Set entryset = this.mAddNrs.entrySet();
/* 280 */         for (Iterator<Map.Entry> itS = entryset.iterator(); itS.hasNext(); ) {
/* 281 */           Map.Entry e = itS.next();
/* 282 */           if (e.getValue() == w) {
/* 283 */             this.mAddNrs.remove(oMain);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 289 */       List ladd = (List)this.mMain2Add.get(oMain);
/* 290 */       if (ladd != null) {
/* 291 */         for (Iterator<LTDataWork> itm = ladd.iterator(); itm.hasNext(); ) {
/*     */           
/* 293 */           LTDataWork awork = itm.next();
/* 294 */           this.mAddOps.remove(awork.getNr());
/*     */         } 
/*     */         
/* 297 */         this.mMain2Add.remove(oMain);
/*     */       } 
/*     */     } else {
/*     */       
/* 301 */       List ladd = (List)this.mMain2Add.get(oMain);
/* 302 */       if (ladd != null) {
/* 303 */         this.mAddOps.remove(oNr);
/*     */         
/* 305 */         for (Iterator<LTDataWork> itm = ladd.iterator(); itm.hasNext(); ) {
/*     */           
/* 307 */           LTDataWork awork = itm.next();
/* 308 */           if (awork.getNr().equals(oNr)) {
/* 309 */             itm.remove();
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(LTClientContext ctx) {
/* 322 */     List lMainOps = new ArrayList(this.lMainOps);
/* 323 */     for (Iterator<String> itm = lMainOps.iterator(); itm.hasNext(); ) {
/* 324 */       String oNr = itm.next();
/* 325 */       LTDataWork mwork = (LTDataWork)this.mMainOps.get(oNr);
/*     */       
/* 327 */       if (!ctx.isMainWorkValid(oNr)) {
/* 328 */         removeWork(oNr, mwork.getTasktype()); continue;
/*     */       } 
/* 330 */       LTDataWork mworkNew = ctx.getMainWork(oNr, false);
/* 331 */       if (mworkNew != null) {
/*     */         
/* 333 */         if (mworkNew.getTasktype() == 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 340 */           String oAddOnMainNr = findAddOnMainNumber(mwork);
/* 341 */           if (oAddOnMainNr != null) {
/* 342 */             List lamworks = ctx.getMainWorks(oNr);
/* 343 */             LTDataWork copy = (LTDataWork)mwork.clone();
/* 344 */             copy.setNr(oAddOnMainNr);
/*     */             
/* 346 */             if (lamworks == null || !isAddOnWorkValid(copy, lamworks)) {
/* 347 */               removeWork(oNr, mwork.getTasktype());
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 361 */         else if (!containsSXAWID(mworkNew, ((LTSXAWData)mwork.getSXAWList().get(0)).getInternalID())) {
/* 362 */           removeWork(oNr, mwork.getTasktype());
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 372 */         List<?> tmp = (List)this.mMain2Add.get(oNr);
/*     */         
/* 374 */         List lAddOn = (tmp == null) ? null : new ArrayList(tmp);
/*     */         
/* 376 */         List lAddOnNew = ctx.getAddOnWorks(oNr);
/*     */         
/* 378 */         if (lAddOn != null && lAddOnNew != null) {
/* 379 */           for (Iterator<LTDataWork> ita = lAddOn.iterator(); ita.hasNext(); ) {
/* 380 */             LTDataWork awork = ita.next();
/* 381 */             if (!isAddOnWorkValid(awork, lAddOnNew)) {
/* 382 */               removeWork(awork.getNr(), awork.getTasktype());
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 396 */         if (lAddOnNew == null && lAddOn != null) {
/* 397 */           for (Iterator<LTDataWork> ita = lAddOn.iterator(); ita.hasNext(); ) {
/* 398 */             LTDataWork awork = ita.next();
/* 399 */             removeWork(awork.getNr(), awork.getTasktype());
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       removeWork(oNr, mwork.getTasktype());
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
/*     */   private boolean isAddOnWorkValid(LTDataWork awork, List lAddOnNew) {
/* 427 */     for (Iterator<LTDataWork> ita = lAddOnNew.iterator(); ita.hasNext(); ) {
/* 428 */       LTDataWork w = ita.next();
/* 429 */       if (w.getNr().equals(awork.getNr())) {
/* 430 */         for (Iterator<LTSXAWData> its = w.getSXAWList().iterator(); its.hasNext(); ) {
/* 431 */           LTSXAWData sx = its.next();
/* 432 */           if (sx.getInternalID() == ((LTSXAWData)awork.getSXAWList().get(0)).getInternalID()) {
/* 433 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 437 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 441 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean containsSXAWID(LTDataWork work, int intid) {
/* 446 */     for (Iterator<LTSXAWData> ita = work.getSXAWList().iterator(); ita.hasNext(); ) {
/* 447 */       LTSXAWData sx = ita.next();
/* 448 */       if (sx.getInternalID() == intid) {
/* 449 */         return true;
/*     */       }
/*     */     } 
/* 452 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String findAddOnMainNumber(LTDataWork w) {
/* 458 */     if (!this.mAddNrs.containsValue(w)) {
/* 459 */       return null;
/*     */     }
/*     */     
/* 462 */     Set entryset = this.mAddNrs.entrySet();
/* 463 */     for (Iterator<Map.Entry> itS = entryset.iterator(); itS.hasNext(); ) {
/* 464 */       Map.Entry e = itS.next();
/* 465 */       if (e.getValue() == w) {
/* 466 */         return (String)e.getKey();
/*     */       }
/*     */     } 
/*     */     
/* 470 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTSelectionLists.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */