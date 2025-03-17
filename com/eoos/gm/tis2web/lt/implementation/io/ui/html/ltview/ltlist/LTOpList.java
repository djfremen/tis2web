/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSXAWData;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTSelectionLists;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTTroublecode;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.ListElement;
/*     */ import com.eoos.html.renderer.HtmlSpanRenderer;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ public class LTOpList
/*     */   extends ListElement
/*     */ {
/*     */   ClientContext context;
/*     */   LTClientContext ltcontext;
/*  36 */   static int[] widths = new int[] { 15, 55, 20, 10 };
/*     */   
/*  38 */   static HashMap tableMap = null;
/*     */   
/*  40 */   static HashMap contMap = null;
/*     */   
/*  42 */   static HashMap contMap2span = null;
/*     */   
/*  44 */   Map mRow2Span = null;
/*     */   
/*  46 */   Set sRowTC = null;
/*     */   
/*  48 */   Map mRow2Work = null;
/*     */   
/*  50 */   private LTTCSelectBox currentSelectBox = null;
/*     */   
/*  52 */   private LTDataWork currentpseudoMain = null;
/*     */   
/*  54 */   private LTDataWork lastwork = null;
/*     */ 
/*     */   
/*     */   private LTSelectionLists selection;
/*     */ 
/*     */   
/*     */   public LTOpList(ClientContext context) {
/*  61 */     this.context = context;
/*  62 */     this.ltcontext = LTClientContext.getInstance(context);
/*  63 */     this.selection = LTClientContext.getInstance(context).getSelection();
/*  64 */     synchronized (LTOpList.class) {
/*  65 */       if (tableMap == null) {
/*  66 */         tableMap = new HashMap<Object, Object>();
/*  67 */         tableMap.put("class", "lttable");
/*  68 */         contMap = new HashMap<Object, Object>();
/*  69 */         contMap.put("class", "ltcontcol");
/*  70 */         contMap2span = (HashMap)contMap.clone();
/*  71 */         contMap2span.put("colspan", "2");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setData(List oL) {
/*  77 */     this.lastwork = null;
/*  78 */     this.currentpseudoMain = null;
/*  79 */     this.currentSelectBox = null;
/*  80 */     this.ltcontext.formatAWs(oL);
/*  81 */     this.mRow2Span = new HashMap<Object, Object>();
/*  82 */     this.mRow2Work = new HashMap<Object, Object>();
/*  83 */     this.sRowTC = new HashSet();
/*  84 */     List<PairImpl> oRes = new LinkedList();
/*  85 */     int iRow = 0, iAllRows = 0;
/*  86 */     for (Iterator<LTDataWork> itL = oL.iterator(); itL.hasNext(); ) {
/*  87 */       LTDataWork w = itL.next();
/*  88 */       int iSpan = 0;
/*  89 */       iRow = iAllRows;
/*  90 */       if (w.getSXAWList() != null && w.getSXAWList().size() != 0) {
/*  91 */         for (Iterator its = w.getSXAWList().iterator(); its.hasNext(); ) {
/*  92 */           oRes.add(new PairImpl(w, its.next()));
/*  93 */           iSpan++;
/*  94 */           iAllRows++;
/*     */         } 
/*     */       } else {
/*  97 */         iSpan++;
/*  98 */         oRes.add(new PairImpl(w, null));
/*  99 */         iAllRows++;
/*     */       } 
/* 101 */       if (w.getTcList() != null && w.getTcList().size() != 0) {
/* 102 */         iSpan++;
/* 103 */         oRes.add(new PairImpl(w, w.getTcList()));
/* 104 */         this.sRowTC.add(Integer.valueOf(iAllRows++));
/*     */       } 
/* 106 */       this.mRow2Work.put(Integer.valueOf(iRow), w);
/* 107 */       this.mRow2Span.put(Integer.valueOf(iRow), Integer.valueOf(iSpan));
/*     */     } 
/* 109 */     super.setData(oRes);
/*     */   }
/*     */   
/*     */   public static String getAWDisplayValue(LTSXAWData w, int iWorkType) {
/* 113 */     if (iWorkType == 6) {
/* 114 */       StringBuffer ob = new StringBuffer(20);
/* 115 */       ob.append('[' + w.getAwFormatted() + ']');
/* 116 */       return ob.toString();
/*     */     } 
/* 118 */     return w.getAwFormatted();
/*     */   }
/*     */ 
/*     */   
/*     */   private HtmlLabel buildAWLabel(LTDataWork work, LTSXAWData awdata) {
/* 123 */     String label = null;
/* 124 */     if (awdata.getAwFormatted() != null) {
/* 125 */       String value = getAWDisplayValue(awdata, work.getTasktype());
/* 126 */       if (awdata.isChange_flag()) {
/* 127 */         label = renderHtmlSpanTag("ltnew", value);
/*     */       } else {
/* 129 */         label = value;
/*     */       } 
/*     */     } else {
/* 132 */       label = "&nbsp;";
/*     */     } 
/* 134 */     return new HtmlLabel(new String(label));
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/* 138 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void createTCBox(LTDataWork work) {
/* 144 */     if (work.getTcList() != null && work.getTcList().size() > 0)
/* 145 */       this.currentSelectBox = new LTTCSelectBox(this.context, work.getTcList()); 
/*     */   } protected HtmlElement getContent(Object obj, int param) {
/*     */     StringBuffer buf;
/*     */     LTSXAWData awdata;
/*     */     String oRes;
/* 150 */     Pair odata = (Pair)obj;
/* 151 */     LTDataWork work = (LTDataWork)odata.getFirst();
/* 152 */     boolean bIsTC = odata.getSecond() instanceof List;
/* 153 */     if (bIsTC) {
/* 154 */       if (param == 1 && 
/* 155 */         work.getTcList() != null && work.getTcList().size() > 0) {
/* 156 */         LTDataWork oWInSel = this.ltcontext.getSelection().getWork(work.getNr(), work.getTasktype());
/* 157 */         if (oWInSel != null && oWInSel.getTcList().size() > 0) {
/*     */ 
/*     */           
/* 160 */           LTTroublecode tcs = oWInSel.getTcList().get(0);
/* 161 */           tcs = work.getTC(tcs.getTroubleCode());
/* 162 */           if (tcs != null) {
/* 163 */             this.currentSelectBox.setValue(tcs);
/*     */           }
/*     */         } 
/* 166 */         return (HtmlElement)this.currentSelectBox;
/*     */       } 
/*     */       
/* 169 */       return null;
/*     */     } 
/* 171 */     switch (param) {
/*     */       case 0:
/* 173 */         if (this.lastwork != work) {
/*     */           String str;
/*     */           
/* 176 */           createTCBox(work);
/* 177 */           if (work.getTasktype() == 1) {
/* 178 */             this.currentpseudoMain = work;
/*     */           }
/*     */           
/* 181 */           if (work.getWarrantyFlag() > 0) {
/* 182 */             char indicator = '#';
/* 183 */             switch (work.getWarrantyFlag()) {
/*     */               case 1:
/* 185 */                 indicator = 'N';
/*     */                 break;
/*     */               case 2:
/* 188 */                 indicator = 'A';
/*     */                 break;
/*     */               case 3:
/* 191 */                 indicator = 'E';
/*     */                 break;
/*     */               case 4:
/* 194 */                 indicator = 'F';
/*     */                 break;
/*     */               case 5:
/* 197 */                 indicator = 'G';
/*     */                 break;
/*     */               case 6:
/* 200 */                 indicator = 'I';
/*     */                 break;
/*     */               case 7:
/* 203 */                 indicator = 'S';
/*     */                 break;
/*     */             } 
/* 206 */             str = work.getNr() + "<br>" + indicator;
/*     */           } else {
/* 208 */             str = work.getNr();
/*     */           } 
/* 210 */           if (!work.isChangeFlag()) {
/* 211 */             return (HtmlElement)new HtmlLabel(str);
/*     */           }
/* 213 */           return (HtmlElement)new HtmlLabel(renderHtmlSpanTag("ltnew", str));
/*     */         } 
/*     */         
/* 216 */         return null;
/*     */ 
/*     */       
/*     */       case 1:
/* 220 */         if (this.lastwork == work) {
/* 221 */           return null;
/*     */         }
/* 223 */         this.lastwork = work;
/* 224 */         buf = new StringBuffer(1024);
/* 225 */         buf.append(Util.escapeReservedHTMLChars(work.getDescription()));
/* 226 */         if (work.getLaquerDegree() != null) {
/* 227 */           buf.append(" - " + work.getLaquerDegree());
/*     */         }
/* 229 */         oRes = work.getTCString();
/* 230 */         if (oRes.length() > 0) {
/* 231 */           buf.append("<br>" + this.context.getLabel("lt.tc") + ": " + oRes);
/*     */         }
/* 233 */         if (work.getTextList() != null) {
/* 234 */           for (Iterator<String> it = work.getTextList().iterator(); it.hasNext(); ) {
/* 235 */             String oT = it.next();
/* 236 */             buf.append("<br>");
/* 237 */             if (oT.startsWith("-:")) {
/* 238 */               buf.append(renderHtmlSpanTag("ltmtext", oT)); continue;
/*     */             } 
/* 240 */             if (oT.startsWith("+:")) {
/* 241 */               buf.append(renderHtmlSpanTag("ltptext", oT)); continue;
/*     */             } 
/* 243 */             buf.append(Util.escapeReservedHTMLChars(oT));
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 248 */         return (HtmlElement)new HtmlLabel(buf.toString());
/*     */       
/*     */       case 2:
/* 251 */         awdata = (LTSXAWData)odata.getSecond();
/* 252 */         return (HtmlElement)new HtmlLabel((awdata != null && awdata.getSx() != null) ? awdata.getSx() : "&nbsp;");
/*     */       
/*     */       case 3:
/* 255 */         awdata = (LTSXAWData)odata.getSecond();
/* 256 */         if (awdata == null) {
/* 257 */           return (HtmlElement)new HtmlLabel("&nbsp;");
/*     */         }
/* 259 */         if (!this.selection.canAddWork(work)) {
/* 260 */           if (this.selection.contains(work.getNr(), work.getTasktype(), awdata.getInternalID())) {
/* 261 */             return (HtmlElement)new LTSXAWPairList(buildAWLabel(work, awdata), (HtmlElement)new LTSelectionLink(work, this.currentpseudoMain, awdata, this.currentSelectBox, this.context, true), this.context);
/*     */           }
/* 263 */           return (HtmlElement)buildAWLabel(work, awdata);
/*     */         } 
/*     */         
/* 266 */         return (HtmlElement)new LTSXAWPairList(buildAWLabel(work, awdata), (HtmlElement)new LTSelectionLink(work, this.currentpseudoMain, awdata, this.currentSelectBox, this.context, false), this.context);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     return (HtmlElement)new HtmlLabel("&nbsp;");
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int param) {
/* 276 */     String oText = "";
/* 277 */     switch (param) {
/*     */       case 0:
/* 279 */         oText = this.context.getLabel("lt.opnr");
/*     */         break;
/*     */       case 1:
/* 282 */         oText = this.context.getLabel("lt.workdesc");
/*     */         break;
/*     */       case 2:
/* 285 */         oText = this.context.getLabel("lt.code");
/*     */         break;
/*     */       case 3:
/* 288 */         oText = this.context.getLabel("lt.ta");
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 293 */     return (HtmlElement)new HtmlLabel(oText);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map<String, String> map) {
/* 297 */     map.put("class", "ltheadcol");
/* 298 */     map.put("width", Integer.valueOf(widths[columnIndex]).toString() + "%");
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int colomnIndex, Map<String, String> map) {
/* 302 */     if (colomnIndex == 0) {
/*     */       
/* 304 */       Integer iSpan = (Integer)this.mRow2Span.get(Integer.valueOf(dataIndex));
/* 305 */       if (iSpan != null && iSpan.intValue() > 1) {
/* 306 */         map.putAll(contMap);
/* 307 */         map.put("rowspan", iSpan.toString());
/*     */         return;
/*     */       } 
/* 310 */       map.putAll(contMap);
/*     */       
/*     */       return;
/*     */     } 
/* 314 */     if (colomnIndex == 1 && this.sRowTC.contains(Integer.valueOf(dataIndex))) {
/* 315 */       map.putAll(contMap);
/* 316 */       map.put("colspan", Integer.valueOf(3));
/*     */       
/*     */       return;
/*     */     } 
/* 320 */     if (colomnIndex == 1) {
/*     */       
/* 322 */       LTDataWork ow = (LTDataWork)this.mRow2Work.get(Integer.valueOf(dataIndex));
/* 323 */       if (ow != null) {
/* 324 */         Integer iSpan = (Integer)this.mRow2Span.get(Integer.valueOf(dataIndex));
/* 325 */         if (iSpan != null && iSpan.intValue() > 1) {
/* 326 */           int iSubstract = (ow.getTcList() != null && ow.getTcList().size() > 0) ? 1 : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 331 */           map.putAll(contMap);
/* 332 */           map.put("rowspan", Integer.valueOf(iSpan.intValue() - iSubstract).toString());
/*     */           return;
/*     */         } 
/* 335 */         map.putAll(contMap);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 342 */     if (colomnIndex == 3) {
/* 343 */       map.put("class", "ltcontcolsxaw");
/*     */       return;
/*     */     } 
/* 346 */     map.putAll(contMap);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 351 */     map.putAll(tableMap);
/*     */   }
/*     */   
/*     */   private String renderHtmlSpanTag(final String clazz, final String content) {
/* 355 */     return HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter() {
/*     */           protected String getClaZZ() {
/* 357 */             return clazz;
/*     */           }
/*     */           
/*     */           public String getContent() {
/* 361 */             return content;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\ltlist\LTOpList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */