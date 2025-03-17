/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class SystemHistory
/*     */   extends HashMap
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  28 */   private static final Logger log = Logger.getLogger(SystemHistory.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  33 */       template = ApplicationContext.getInstance().loadFile(SystemHistory.class, "systemhistoryheader.html", null).toString();
/*  34 */     } catch (Exception e) {
/*  35 */       log.error("unable to load template - error:" + e, e);
/*  36 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  41 */   private Integer firstPlane = null;
/*     */   
/*  43 */   private int curPlane = 0;
/*     */ 
/*     */   
/*     */   private int sysCode;
/*     */ 
/*     */   
/*     */   private String sysDesc;
/*     */ 
/*     */   
/*     */   public SystemHistory(int sysCode, String sysDesc) {
/*  53 */     this.sysCode = sysCode;
/*  54 */     this.sysDesc = sysDesc;
/*  55 */     this.curPlane = 0;
/*     */   }
/*     */   
/*     */   public void addDocumentHistory(DocumentHistory dH, int plane) {
/*  59 */     Integer planeObj = Integer.valueOf(plane);
/*  60 */     if (this.firstPlane == null || size() == 0) {
/*  61 */       this.firstPlane = planeObj;
/*     */     }
/*  63 */     List<DocumentHistory> docHs = (List)get(planeObj);
/*  64 */     if (docHs == null) {
/*  65 */       docHs = new LinkedList();
/*  66 */       put((K)planeObj, (V)docHs);
/*     */     } 
/*  68 */     docHs.add(dH);
/*     */   }
/*     */   
/*     */   public void addStepResults(StepResults sr, DocumentHistory dh, int plane) {
/*  72 */     DocumentHistory dhCur = getLastDocumentHistory(this.curPlane);
/*     */     StepResults lastStep;
/*  74 */     if (dhCur != null && (lastStep = (StepResults)dhCur.getLast()) != null) {
/*  75 */       lastStep.setNextPlane(Integer.valueOf(plane));
/*     */     }
/*     */     
/*  78 */     DocumentHistory dhNext = null;
/*     */     try {
/*  80 */       dhNext = getLastDocumentHistory(plane);
/*  81 */     } catch (Exception e) {}
/*     */     
/*  83 */     if (dhNext == null || dh.getSioId() != dhNext.getSioId() || plane > this.curPlane) {
/*  84 */       addDocumentHistory(dh, plane);
/*  85 */       dh.add((E)sr);
/*     */     } else {
/*  87 */       dhNext.add((E)sr);
/*     */     } 
/*  89 */     this.curPlane = plane;
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentHistory getLastDocumentHistory(int plane) {
/*  94 */     LinkedList<DocumentHistory> dHs = (LinkedList)get(Integer.valueOf(plane));
/*  95 */     DocumentHistory dH = null;
/*  96 */     if (dHs != null) {
/*     */       try {
/*  98 */         dH = dHs.getLast();
/*  99 */       } catch (Exception e) {}
/*     */     }
/*     */     
/* 102 */     return dH;
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentHistory getLastDocumentHistory() {
/* 107 */     return getLastDocumentHistory(this.curPlane);
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentHistory getBeforeLastDocumentHistory(int plane) {
/* 112 */     LinkedList<DocumentHistory> dHs = (LinkedList)get(Integer.valueOf(plane));
/* 113 */     DocumentHistory dH = null;
/* 114 */     if (dHs != null && dHs.size() > 1) {
/* 115 */       dH = dHs.get(dHs.size() - 2);
/*     */     }
/* 117 */     return dH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSysCode() {
/* 127 */     return this.sysCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSysCode(int sysCode) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSysDesc() {
/* 145 */     return this.sysDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSysDesc(String sysDesc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHtmlProtocol(ClientContext context) {
/* 158 */     StringBuffer ret = new StringBuffer();
/*     */     
/* 160 */     ret.append("<tr><td id=\"SystemHeader\">");
/* 161 */     ret.append(getProtocolHeader(context));
/* 162 */     ret.append("</td></tr><tr><td id=\"td00\">\n");
/* 163 */     ret.append("<table width=\"100%\">");
/* 164 */     Integer res = this.firstPlane;
/* 165 */     Map itMap = createIteratorMap();
/*     */     while (true) {
/* 167 */       res = (writeProtocolContent(res, ret, itMap, size())).plane;
/* 168 */       if (res == null) {
/* 169 */         ret.append("</table>");
/* 170 */         ret.append("</td></tr>");
/*     */         
/* 172 */         return ret.toString();
/*     */       } 
/*     */     } 
/*     */   } public Map createIteratorMap() {
/* 176 */     Map<Object, Object> itMap = new HashMap<Object, Object>();
/* 177 */     Set<Map.Entry<K, V>> entries = entrySet();
/* 178 */     Iterator<Map.Entry<K, V>> it = entries.iterator();
/* 179 */     while (it.hasNext()) {
/* 180 */       Map.Entry entry = it.next();
/* 181 */       List value = (List)entry.getValue();
/* 182 */       itMap.put(entry.getKey(), value.iterator());
/*     */     } 
/* 184 */     return itMap;
/*     */   }
/*     */   
/*     */   String getProtocolHeader(ClientContext context) {
/* 188 */     StringBuffer code = new StringBuffer(template);
/* 189 */     StringUtilities.replace(code, "{systemLbl}", context.getLabel("cpr.System"));
/* 190 */     StringUtilities.replace(code, "{system}", this.sysDesc);
/* 191 */     StringUtilities.replace(code, "{PerformedDiagnosticTests}", context.getLabel("cpr.Performed.Diagnostic.Tests"));
/* 192 */     return code.toString();
/*     */   }
/*     */   static class WriteRes { public Integer plane;
/*     */     
/*     */     WriteRes(Integer plane, int rowspan) {
/* 197 */       this.plane = plane;
/* 198 */       this.rowspan = rowspan;
/*     */     }
/*     */ 
/*     */     
/*     */     public int rowspan; }
/*     */ 
/*     */ 
/*     */   
/*     */   WriteRes writeProtocolContent(Integer plane, StringBuffer cont, Map itMap, int colspan) {
/* 207 */     List docs = (List)get(plane);
/* 208 */     Iterator<DocumentHistory> itDocs = (Iterator)itMap.get(plane);
/* 209 */     Integer nextPlane = null;
/* 210 */     int rowspan = 0;
/* 211 */     if (docs != null && itDocs != null) {
/* 212 */       boolean goOn = true;
/* 213 */       while (itDocs.hasNext() && goOn) {
/* 214 */         DocumentHistory docHis = itDocs.next();
/* 215 */         cont.append(docHis.getProtocolHeader(colspan));
/* 216 */         rowspan++;
/* 217 */         Iterator<E> itSteps = docHis.iterator();
/* 218 */         if (!itSteps.hasNext()) {
/* 219 */           nextPlane = null;
/*     */         }
/* 221 */         while (itSteps.hasNext() && goOn) {
/* 222 */           StepResults stepRes = (StepResults)itSteps.next();
/* 223 */           nextPlane = stepRes.getNextPlane();
/*     */           
/* 225 */           if (nextPlane == null || nextPlane.intValue() <= plane.intValue()) {
/* 226 */             cont.append("<tr><td id=\"LeftContent\">");
/* 227 */             cont.append(stepRes.getLabel());
/* 228 */             cont.append("</td><td id=\"RightContent\" colspan=\"");
/* 229 */             cont.append(colspan);
/* 230 */             cont.append("\">");
/* 231 */             cont.append(stepRes.getContent());
/* 232 */             cont.append("</td></tr>\n");
/* 233 */             rowspan++;
/*     */           } else {
/* 235 */             List<StringBuffer> contents = new LinkedList();
/* 236 */             int curRowSpan = 0;
/* 237 */             while (nextPlane != null && nextPlane.intValue() > plane.intValue()) {
/* 238 */               StringBuffer nextPlaneCont = new StringBuffer();
/* 239 */               WriteRes writeRes = writeProtocolContent(nextPlane, nextPlaneCont, itMap, colspan - 1);
/* 240 */               curRowSpan += writeRes.rowspan;
/* 241 */               nextPlane = writeRes.plane;
/* 242 */               contents.add(nextPlaneCont);
/*     */             } 
/* 244 */             if (contents.size() > 0) {
/* 245 */               curRowSpan++;
/* 246 */               rowspan += curRowSpan;
/* 247 */               cont.append("<tr><td id=\"LeftContent\" rowspan=\"");
/* 248 */               cont.append(curRowSpan);
/* 249 */               cont.append("\">");
/* 250 */               cont.append(stepRes.getLabel());
/* 251 */               cont.append("</td><td id=\"RightContent\" colspan=\"");
/* 252 */               cont.append(colspan);
/* 253 */               cont.append("\">");
/* 254 */               cont.append(stepRes.getContent());
/* 255 */               cont.append("</td></tr>\n");
/*     */               
/* 257 */               Iterator<StringBuffer> itCont = contents.iterator();
/* 258 */               while (itCont.hasNext()) {
/* 259 */                 StringBuffer nextPlaneCont = itCont.next();
/* 260 */                 cont.append(nextPlaneCont);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 265 */           goOn = (nextPlane != null && nextPlane.intValue() >= plane.intValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 271 */     if (nextPlane != null && nextPlane.equals(plane)) {
/* 272 */       nextPlane = null;
/*     */     }
/* 274 */     return new WriteRes(nextPlane, rowspan);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\SystemHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */