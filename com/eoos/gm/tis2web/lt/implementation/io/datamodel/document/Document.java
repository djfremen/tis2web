/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.document;
/*     */ 
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.DynamicResourceController;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.domain.AWBlob;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.domain.BLOBProperty;
/*     */ import com.eoos.html.ResultObject;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class Document
/*     */ {
/*  29 */   protected static final Logger errorLog = Logger.getLogger(Document.class);
/*     */ 
/*     */   
/*     */   protected static final String patternImage = "{LINK_IMAGE:ID:";
/*     */   
/*     */   protected static final String patternEnd = "}";
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private AWBlob documentBlob;
/*     */   
/*  40 */   private static Map images = Collections.synchronizedMap(new HashMap<Object, Object>(1));
/*     */ 
/*     */   
/*     */   public Document(ClientContext context, int iSIOID) {
/*  44 */     this.context = context;
/*     */     
/*  46 */     LTClientContext c = LTClientContext.getInstance(context);
/*  47 */     this.documentBlob = c.getDocument(iSIOID);
/*     */   }
/*     */   
/*     */   public static String buildKey(Integer sioid, Integer lc) {
/*  51 */     return sioid.toString() + "-" + lc.toString();
/*     */   }
/*     */   
/*     */   public String getKey() {
/*  55 */     if (this.documentBlob == null) {
/*  56 */       return "";
/*     */     }
/*  58 */     return buildKey(this.documentBlob.getID(), (Integer)this.documentBlob.getProperty(BLOBProperty.LANGUAGE));
/*     */   }
/*     */   
/*     */   public ResultObject buildResultObject(Map params) {
/*  62 */     if (this.documentBlob == null) {
/*  63 */       return null;
/*     */     }
/*  65 */     ResultObject res = null;
/*  66 */     if (this.documentBlob.getMime() == "text/html") {
/*     */       try {
/*  68 */         res = new ResultObject(0, parseHTML(new String(this.documentBlob.getData(), "UTF-8")));
/*  69 */         return res;
/*  70 */       } catch (UnsupportedEncodingException e) {
/*  71 */         errorLog.error("Wrong encoding for LT Document " + this.documentBlob.getID().toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  76 */     return new ResultObject(10, new PairImpl(this.documentBlob.getMime(), this.documentBlob.getData()));
/*     */   }
/*     */ 
/*     */   
/*     */   private String parseHTML(String odata) {
/*  81 */     if (odata.indexOf("{LINK_IMAGE:ID:") == -1) {
/*  82 */       return odata;
/*     */     }
/*     */     
/*  85 */     odata = StringUtilities.replace(odata, "embed", "img");
/*     */     
/*  87 */     DynamicResourceController cont = DynamicResourceController.getInstance(this.context);
/*  88 */     StringBuffer buf = new StringBuffer(odata.length());
/*     */     
/*  90 */     SectionIndex sidx = new SectionIndex();
/*  91 */     SectionIndex sidxend = null;
/*     */ 
/*     */ 
/*     */     
/*  95 */     while ((sidx = StringUtilities.getSectionIndex(odata, "{LINK_IMAGE:ID:", sidx.start)) != null) {
/*     */ 
/*     */       
/*  98 */       sidxend = StringUtilities.getSectionIndex(odata, "}", sidx.end);
/*  99 */       if (sidxend != null) {
/* 100 */         buf.append(odata.substring(0, sidx.start));
/*     */         
/* 102 */         String id = odata.substring(sidx.start + "{LINK_IMAGE:ID:".length(), sidxend.start);
/* 103 */         AWBlob img = (AWBlob)images.get("LTIMG" + id);
/* 104 */         if (img == null) {
/*     */           try {
/* 106 */             img = LTClientContext.getInstance(this.context).getGraphic(Integer.valueOf(id).intValue());
/* 107 */             if (img != null) {
/* 108 */               images.put("LTIMG" + id, img);
/*     */             }
/* 110 */           } catch (NumberFormatException e) {
/* 111 */             errorLog.error("Wrong Format for Image ID" + id);
/*     */           } 
/*     */         }
/*     */         
/* 115 */         if (img != null)
/*     */         {
/*     */           
/* 118 */           buf.append(cont.getURL(img.getData(), "image/png", "LTIMG" + id));
/*     */         }
/*     */         
/* 121 */         odata = odata.substring(sidxend.end);
/* 122 */         sidx.start = 0;
/*     */         
/* 124 */         sidxend = StringUtilities.getSectionIndex(odata, "type=\"image/itf\"", 0);
/* 125 */         if (sidxend != null) {
/* 126 */           buf.append(odata.substring(0, sidxend.start));
/* 127 */           buf.append(" type=\"");
/* 128 */           buf.append("image/png");
/* 129 */           buf.append('"');
/* 130 */           odata = odata.substring(sidxend.end);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     buf.append(odata);
/*     */     
/* 137 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\document\Document.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */