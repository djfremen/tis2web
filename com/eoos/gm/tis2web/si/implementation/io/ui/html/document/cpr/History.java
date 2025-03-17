/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.protocol.ActiveTextInputElement;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.stdinfo.StandardInfoPanel;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.implementation.io.datamodel.VehicleOptionsData;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.TimezoneOffsetInputElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import java.util.TimeZone;
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
/*     */ public class History
/*     */   extends LinkedList
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  42 */   private static final Logger log = Logger.getLogger(History.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  47 */       template = ApplicationContext.getInstance().loadFile(History.class, "historyheader.html", null).toString();
/*  48 */     } catch (Exception e) {
/*  49 */       log.error("unable to load template - error:" + e, e);
/*  50 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */   
/*  54 */   private Stack planeStack = new Stack();
/*     */   
/*     */   private ActiveTextInputElement dealer;
/*     */   
/*     */   private ActiveTextInputElement customer;
/*     */   
/*     */   private ActiveTextInputElement customerComplaint;
/*     */   
/*     */   private ActiveTextInputElement additionalInformation;
/*     */   
/*  64 */   private VCR vcr = null;
/*     */ 
/*     */   
/*     */   private ClientContext context;
/*     */   
/*  69 */   private long startTime = 0L;
/*     */ 
/*     */   
/*  72 */   private TimezoneOffsetInputElement timezoneDiffElement = null;
/*     */   
/*  74 */   private Stack documentStack = new Stack();
/*     */   
/*  76 */   private Stack backStack = new Stack();
/*     */   
/*  78 */   private DocumentHistory lastDoc = null;
/*     */ 
/*     */   
/*     */   public History(final ClientContext context) {
/*  82 */     this.context = context;
/*     */     
/*  84 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/*  87 */             VCR newVCR = null;
/*     */             
/*     */             try {
/*  90 */               newVCR = SIDataAdapterFacade.getInstance(context).getLVCAdapter().toVCR(VCFacade.getInstance(context).getCfg());
/*     */             
/*     */             }
/*  93 */             catch (Exception e) {
/*  94 */               History.log.warn("unable to determine vcr, ignoring - exception: " + e, e);
/*     */             } 
/*  96 */             History.this.checkVcrClear(newVCR);
/*     */           }
/*     */         });
/*  99 */     init();
/*     */   }
/*     */   
/*     */   public synchronized void clearDocumentStack() {
/* 103 */     this.documentStack.clear();
/* 104 */     this.backStack.clear();
/* 105 */     this.lastDoc = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void clearStack() {
/* 112 */     this.documentStack.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/* 118 */     this.dealer = new ActiveTextInputElement(this.context.createID(), 70, 60);
/* 119 */     this.customer = new ActiveTextInputElement(this.context.createID(), 70, 60);
/* 120 */     this.customerComplaint = new ActiveTextInputElement(this.context.createID(), 70, 60);
/* 121 */     this.additionalInformation = new ActiveTextInputElement(this.context.createID(), 70, 60);
/*     */   }
/*     */   
/*     */   public void checkVcr(VCR newVcr) {
/* 125 */     if (this.vcr != null && newVcr != null && 
/* 126 */       !this.vcr.match(newVcr)) {
/* 127 */       reset();
/* 128 */       StandardInfoPanel.getInstance(this.context).resetNodes();
/*     */     } 
/*     */     
/* 131 */     this.vcr = newVcr;
/*     */   }
/*     */   
/*     */   public void checkVcrClear(VCR newVcr) {
/* 135 */     if (this.vcr != null && newVcr != null && 
/* 136 */       !this.vcr.match(newVcr)) {
/* 137 */       reset();
/* 138 */       StandardInfoPanel.getInstance(this.context).clearNodes();
/*     */     } 
/*     */     
/* 141 */     this.vcr = newVcr;
/*     */   }
/*     */   
/*     */   public synchronized void reset() {
/* 145 */     init();
/* 146 */     clear();
/* 147 */     this.planeStack.clear();
/* 148 */     this.documentStack.clear();
/* 149 */     this.backStack.clear();
/*     */   }
/*     */   
/*     */   public synchronized void addInputElements(HtmlElementContainer container) {
/* 153 */     container.addElement((HtmlElement)this.dealer);
/* 154 */     container.addElement((HtmlElement)this.customer);
/* 155 */     container.addElement((HtmlElement)this.customerComplaint);
/* 156 */     container.addElement((HtmlElement)this.additionalInformation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addStepResults(StepResults sr, DocumentHistory dh, SystemHistory sh, int plane, boolean isLink) {
/* 167 */     this.backStack.push(sr);
/* 168 */     SystemHistory shNext = null;
/*     */     try {
/* 170 */       shNext = (SystemHistory)getLast();
/* 171 */     } catch (Exception e) {
/* 172 */       this.startTime = System.currentTimeMillis();
/*     */     } 
/*     */     
/* 175 */     if (shNext == null || (sh.getSysCode() != shNext.getSysCode() && !isLink)) {
/* 176 */       add((E)sh);
/* 177 */       shNext = sh;
/*     */     } 
/*     */ 
/*     */     
/* 181 */     shNext.addStepResults(sr, dh, plane);
/* 182 */     Integer.valueOf(plane);
/* 183 */     int lastPlane = 0;
/*     */     try {
/* 185 */       lastPlane = ((Integer)this.planeStack.peek()).intValue();
/*     */       
/* 187 */       while (lastPlane > plane) {
/* 188 */         this.planeStack.pop();
/* 189 */         lastPlane = ((Integer)this.planeStack.peek()).intValue();
/*     */       } 
/* 191 */     } catch (EmptyStackException e) {}
/*     */ 
/*     */     
/* 194 */     if (lastPlane < plane || this.planeStack.size() == 0) {
/* 195 */       this.planeStack.push(Integer.valueOf(plane));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentHistory getLastDocHist(int plane) {
/* 201 */     int ind = this.planeStack.size() - 1;
/* 202 */     DocumentHistory ret = null;
/* 203 */     if (size() > 0) {
/* 204 */       SystemHistory lastHistory = (SystemHistory)getLast();
/* 205 */       while (ind >= 0 && ret == null) {
/* 206 */         int cur = ((Integer)this.planeStack.get(ind)).intValue();
/* 207 */         if (cur < plane) {
/* 208 */           DocumentHistory dH = lastHistory.getLastDocumentHistory(cur);
/* 209 */           if (dH != null && dH.size() > 0) {
/* 210 */             StepResults sR = (StepResults)dH.getLast();
/* 211 */             if (!sR.getTag().equals("DTCTestStep")) {
/* 212 */               ret = dH;
/*     */             }
/*     */           } 
/*     */         } 
/* 216 */         ind--;
/*     */       } 
/* 218 */       if (plane == 65 && ret == null) {
/* 219 */         ret = lastHistory.getBeforeLastDocumentHistory(plane);
/*     */       }
/*     */     } 
/* 222 */     return ret;
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
/*     */   public String getHtmlProtocol(ClientContext context, Map params) {
/* 235 */     String ret = new String();
/* 236 */     ret = ret + "<table><tr><td id=\"td11\">";
/* 237 */     ret = ret + getProtocolHeader(context, params);
/* 238 */     ret = ret + "</td></tr>\n";
/* 239 */     Iterator<E> it = iterator();
/* 240 */     while (it.hasNext()) {
/* 241 */       SystemHistory sysHist = (SystemHistory)it.next();
/*     */       
/* 243 */       ret = ret + sysHist.getHtmlProtocol(context);
/*     */     } 
/*     */     
/* 246 */     ret = ret + "</table>";
/* 247 */     return ret;
/*     */   }
/*     */   
/*     */   protected String getProtocolHeader(ClientContext context, Map params) {
/* 251 */     String vcrDesc = "";
/* 252 */     String optVcr = "";
/*     */     try {
/* 254 */       vcrDesc = VCFacade.getInstance(context).getCurrentVCDisplay(true);
/*     */       
/* 256 */       Map optVcrMap = VehicleOptionsData.getInstance(context).getVehicleOptionMap();
/* 257 */       if (optVcrMap != null) {
/* 258 */         Set optVcrSet = optVcrMap.entrySet();
/* 259 */         Iterator<Map.Entry> it = optVcrSet.iterator();
/* 260 */         while (it.hasNext()) {
/* 261 */           Map.Entry entry = it.next();
/* 262 */           String opt = (String)entry.getKey();
/* 263 */           String value = (String)entry.getValue();
/* 264 */           optVcr = optVcr + opt;
/* 265 */           optVcr = optVcr + " = ";
/* 266 */           optVcr = optVcr + value;
/* 267 */           if (it.hasNext()) {
/* 268 */             optVcr = optVcr + " | ";
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 274 */     } catch (Exception e) {
/* 275 */       throw new ExceptionWrapper(e);
/*     */     } 
/* 277 */     if (this.startTime == 0L) {
/* 278 */       this.startTime = System.currentTimeMillis();
/*     */     }
/* 280 */     long offset = (this.timezoneDiffElement != null) ? this.timezoneDiffElement.getOffset() : 0L;
/*     */ 
/*     */     
/* 283 */     Date date = new Date(this.startTime - offset);
/* 284 */     DateFormat dateFormat = DateFormat.getDateInstance(2, context.getLocale());
/* 285 */     dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
/* 286 */     String dateStr = dateFormat.format(date);
/*     */     
/* 288 */     DateFormat timeFormat = DateFormat.getDateTimeInstance(2, 2, context.getLocale());
/* 289 */     timeFormat.setTimeZone(TimeZone.getTimeZone("GMT-0:00"));
/* 290 */     timeFormat.format(date);
/*     */     
/* 292 */     StringBuffer code = new StringBuffer(template);
/* 293 */     StringUtilities.replace(code, "{date}", dateStr);
/* 294 */     StringUtilities.replace(code, "{vehicle}", vcrDesc);
/* 295 */     StringUtilities.replace(code, "{dateLbl}", context.getLabel("cpr.Date"));
/* 296 */     StringUtilities.replace(code, "{vehicleLbl}", context.getLabel("cpr.Vehicle"));
/* 297 */     StringUtilities.replace(code, "{dealerLbl}", context.getLabel("cpr.Dealer"));
/* 298 */     StringUtilities.replace(code, "{dealer}", this.dealer.getHtmlCode(params));
/* 299 */     StringUtilities.replace(code, "{customerLbl}", context.getLabel("cpr.Customer"));
/* 300 */     StringUtilities.replace(code, "{customer}", this.customer.getHtmlCode(params));
/* 301 */     StringUtilities.replace(code, "{customerComplaintLbl}", context.getLabel("cpr.CustomerComplaint"));
/* 302 */     StringUtilities.replace(code, "{customerComplaint}", this.customerComplaint.getHtmlCode(params));
/* 303 */     StringUtilities.replace(code, "{additionalInformationLbl}", context.getLabel("cpr.AdditionalInformation"));
/* 304 */     StringUtilities.replace(code, "{additionalInformation}", this.additionalInformation.getHtmlCode(params));
/* 305 */     StringUtilities.replace(code, "{vehicleCfgLbl}", context.getLabel("cpr.VehicleCfg"));
/* 306 */     StringUtilities.replace(code, "{vehicleCfg}", optVcr);
/*     */     
/* 308 */     return code.toString();
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
/*     */   public void setTimezoneDiffElement(TimezoneOffsetInputElement timezoneDiffElement) {
/* 337 */     this.timezoneDiffElement = timezoneDiffElement;
/*     */   }
/*     */   
/*     */   public DocumentHistory getLastDocHist() {
/* 341 */     DocumentHistory dH = null;
/* 342 */     if (this.documentStack.size() > 0) {
/* 343 */       dH = this.documentStack.peek();
/*     */     }
/* 345 */     return dH;
/*     */   }
/*     */   
/*     */   public Object popBackStack() {
/* 349 */     Object sr = null;
/* 350 */     if (this.backStack.size() > 0) {
/* 351 */       sr = this.backStack.pop();
/* 352 */       if (sr instanceof DocumentHistory) {
/* 353 */         this.lastDoc = (DocumentHistory)sr;
/*     */       }
/*     */     } 
/* 356 */     if (this.backStack.size() == 0) {
/* 357 */       this.documentStack.clear();
/* 358 */       this.lastDoc = null;
/*     */     } 
/* 360 */     return sr;
/*     */   }
/*     */   
/*     */   public int getBackStackSize() {
/* 364 */     return this.backStack.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void push() {
/* 369 */     if (size() > 0) {
/* 370 */       SystemHistory shNext = (SystemHistory)getLast();
/* 371 */       DocumentHistory docHist = shNext.getLastDocumentHistory();
/* 372 */       if (docHist != null) {
/* 373 */         StepResults sR = (StepResults)docHist.getLast();
/* 374 */         if (!sR.getTag().equals("DTCTestStep")) {
/* 375 */           if (this.documentStack.size() > 0) {
/* 376 */             DocumentHistory last = this.documentStack.peek();
/* 377 */             if (last.getSioId() != docHist.getSioId()) {
/* 378 */               this.documentStack.push(docHist);
/*     */             }
/*     */           } else {
/* 381 */             this.documentStack.push(docHist);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pushBack() {
/* 389 */     if (size() > 0) {
/* 390 */       SystemHistory shNext = (SystemHistory)getLast();
/* 391 */       DocumentHistory docHist = shNext.getLastDocumentHistory();
/* 392 */       if (docHist != null) {
/* 393 */         this.backStack.push(docHist);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void popX() {
/* 399 */     if (this.documentStack.size() > 0) {
/* 400 */       this.lastDoc = this.documentStack.pop();
/*     */     }
/*     */   }
/*     */   
/*     */   public DocumentHistory getCurDocHist() {
/* 405 */     return this.lastDoc;
/*     */   }
/*     */   
/*     */   public void popBack() {
/* 409 */     if (this.backStack.size() > 0) {
/* 410 */       Object bs = this.backStack.peek();
/* 411 */       if (this.documentStack.size() > 0) {
/* 412 */         Object ds = this.documentStack.peek();
/* 413 */         if (ds instanceof DocumentHistory && bs instanceof DocumentHistory) {
/* 414 */           if (((DocumentHistory)ds).getSioId() == ((DocumentHistory)bs).getSioId()) {
/* 415 */             this.lastDoc = this.documentStack.pop();
/* 416 */           } else if (this.lastDoc != null) {
/* 417 */             StepResults sR = (StepResults)this.lastDoc.getLast();
/* 418 */             if (!sR.getTag().equals("DTCTestStep")) {
/* 419 */               this.documentStack.push(this.lastDoc);
/*     */             }
/*     */           }
/*     */         
/*     */         }
/* 424 */       } else if (this.lastDoc != null) {
/* 425 */         StepResults sR = (StepResults)this.lastDoc.getLast();
/* 426 */         if (!sR.getTag().equals("DTCTestStep"))
/* 427 */           this.documentStack.push(this.lastDoc); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\History.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */