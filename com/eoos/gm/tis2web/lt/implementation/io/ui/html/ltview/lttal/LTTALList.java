/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTTroublecode;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.LTOpList;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.ListElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public class LTTALList
/*     */   extends ListElement
/*     */ {
/*     */   ClientContext context;
/*     */   LTClientContext ltcontext;
/*  30 */   static int[] widths = new int[] { 15, 65, 10, 5, 5 };
/*     */   
/*  32 */   static HashMap tableMap = null;
/*     */   
/*  34 */   static HashMap contMapsum = null;
/*     */   
/*  36 */   static HashMap contMap = null;
/*     */   
/*     */   private boolean hasW100000;
/*     */   
/*     */   private String sum;
/*     */   
/*  42 */   TextInputElement input = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public LTTALList(ClientContext context) {
/*  47 */     this.context = context;
/*  48 */     this.ltcontext = LTClientContext.getInstance(context);
/*     */     
/*  50 */     synchronized (LTTALList.class) {
/*  51 */       if (tableMap == null) {
/*     */         
/*  53 */         tableMap = new HashMap<Object, Object>();
/*  54 */         tableMap.put("class", "lttaltable");
/*  55 */         contMapsum = new HashMap<Object, Object>();
/*  56 */         contMapsum.put("class", "lttalsumcol");
/*  57 */         contMap = new HashMap<Object, Object>();
/*  58 */         contMap.put("class", "lttalcol");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  66 */     this.input = null;
/*  67 */     List<String> lw = this.ltcontext.getSelection().getTALList();
/*  68 */     this.sum = this.ltcontext.getAWSum(lw);
/*  69 */     if (lw.size() > 0) {
/*  70 */       lw.add(null);
/*  71 */       LTDataWork w100000 = this.ltcontext.getMainWork("W100000", false);
/*  72 */       this.hasW100000 = (w100000 != null && w100000.getSXAWList() != null && w100000.getSXAWList().size() > 0);
/*  73 */       if (this.hasW100000) {
/*  74 */         lw.add("w100000");
/*     */       }
/*     */     } 
/*  77 */     setData(lw);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(List oL) {
/*  82 */     super.setData(oL);
/*     */   }
/*     */   
/*     */   protected int getColumnCount() {
/*  86 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected HtmlElement getContent(Object obj, int param) {
/*  92 */     if (obj instanceof String) {
/*  93 */       switch (param) {
/*     */         case 0:
/*  95 */           return (HtmlElement)new HtmlLabel(this.hasW100000 ? this.context.getLabel("lt.w.pos") : "&nbsp;");
/*     */ 
/*     */         
/*     */         case 1:
/*  99 */           return null;
/*     */         case 2:
/* 101 */           if (!this.hasW100000)
/* 102 */             return (HtmlElement)new HtmlLabel("&nbsp;"); 
/* 103 */           this.input = new TextInputElement("W100000INP");
/* 104 */           this.input.setValue("1");
/* 105 */           if (this.ltcontext.getSelection().contains("W100000", 5)) {
/* 106 */             this.input.setDisabled(new Boolean(true));
/*     */           }
/* 108 */           return (HtmlElement)this.input;
/*     */ 
/*     */         
/*     */         case 3:
/* 112 */           if (!this.hasW100000)
/* 113 */             return (HtmlElement)new HtmlLabel("&nbsp;"); 
/* 114 */           if (this.ltcontext.getSelection().contains("W100000", 5)) {
/* 115 */             this.input.setDisabled(new Boolean(true));
/*     */             
/* 117 */             return (HtmlElement)new LTW100000SelectionLink(this.input, this.context, true);
/*     */           } 
/* 119 */           return (HtmlElement)new LTW100000SelectionLink(this.input, this.context, false);
/*     */       } 
/*     */ 
/*     */       
/* 123 */       return null;
/*     */     } 
/*     */     
/* 126 */     LTDataWork work = (LTDataWork)obj;
/*     */     
/* 128 */     if (obj == null) {
/* 129 */       switch (param) {
/*     */         case 0:
/* 131 */           return (HtmlElement)new HtmlLabel(this.context.getLabel("lt.sum"));
/*     */         case 2:
/* 133 */           return (HtmlElement)new HtmlLabel(this.sum + " " + this.ltcontext.getAWUnit());
/*     */       } 
/*     */       
/* 136 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 140 */     switch (param) {
/*     */       case 0:
/* 142 */         return (HtmlElement)new HtmlLabel(work.getNr());
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 147 */         return (HtmlElement)new HtmlLabel(work.getDescription());
/*     */ 
/*     */       
/*     */       case 2:
/* 151 */         if (work.getSXAWList() != null && work.getSXAWList().size() > 0) {
/* 152 */           String o = LTOpList.getAWDisplayValue(work.getSXAWList().get(0), work.getTasktype());
/* 153 */           return (HtmlElement)new HtmlLabel(o);
/*     */         } 
/* 155 */         return (HtmlElement)new HtmlLabel("&nbsp;");
/*     */ 
/*     */       
/*     */       case 3:
/* 159 */         if (work.getTcList() != null && work.getTcList().size() > 0) {
/* 160 */           return (HtmlElement)new HtmlLabel(((LTTroublecode)work.getTcList().get(0)).getTroubleCode());
/*     */         }
/* 162 */         return (HtmlElement)new HtmlLabel("&nbsp;");
/*     */ 
/*     */       
/*     */       case 4:
/* 166 */         return (HtmlElement)new LTRemoveSelLink(work.getNr(), work.getTasktype(), this.context);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     return (HtmlElement)new HtmlLabel("&nbsp;");
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement getHeader(int param) {
/* 178 */     String oText = "";
/* 179 */     switch (param) {
/*     */       case 0:
/* 181 */         oText = this.context.getLabel("lt.opnr");
/*     */         break;
/*     */       case 1:
/* 184 */         oText = this.context.getLabel("lt.workdesc");
/*     */         break;
/*     */       case 2:
/* 187 */         oText = this.context.getLabel("lt.ta");
/*     */         break;
/*     */       case 3:
/* 190 */         oText = this.context.getLabel("lt.tc");
/*     */         break;
/*     */       case 4:
/* 193 */         return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     return (HtmlElement)new HtmlLabel(oText);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map<String, String> map) {
/* 203 */     map.put("class", "ltheadcol");
/* 204 */     map.put("width", Integer.valueOf(widths[columnIndex]).toString() + "%");
/* 205 */     if (columnIndex == 3) {
/* 206 */       map.put("colspan", "2");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int colomnIndex, Map<String, String> map) {
/* 212 */     if (dataIndex == getData().size() - (this.hasW100000 ? 2 : 1)) {
/*     */       
/* 214 */       if (colomnIndex == 0) {
/*     */         
/* 216 */         map.putAll(contMap);
/* 217 */         map.put("colspan", "2");
/* 218 */         map.put("style", "text-align:right");
/*     */         return;
/*     */       } 
/* 221 */       if (colomnIndex == 2) {
/* 222 */         map.putAll(contMapsum);
/*     */         return;
/*     */       } 
/* 225 */       map.putAll(contMap);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 230 */     if (dataIndex == getData().size() - 1) {
/*     */       
/* 232 */       if (colomnIndex == 0) {
/*     */ 
/*     */         
/* 235 */         map.putAll(contMap);
/* 236 */         map.put("colspan", "2");
/* 237 */         map.put("style", "text-align:right");
/*     */         
/*     */         return;
/*     */       } 
/* 241 */       if (colomnIndex == 2) {
/*     */         
/* 243 */         map.putAll(contMap);
/* 244 */         map.put("colspan", "2");
/* 245 */         map.put("style", "padding-left:5px");
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 250 */     map.putAll(contMap);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 255 */     map.putAll(tableMap);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\lttal\LTTALList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */