/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class SIOPageInfoDialog
/*     */   extends DialogBase
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(SIOPageInfoDialog.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  37 */       template = ApplicationContext.getInstance().loadFile(SIOPageInfoDialog.class, "sipageinfo.html", null).toString();
/*  38 */     } catch (Exception e) {
/*  39 */       log.error("unable to load template - error:" + e, e);
/*  40 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SIO lu;
/*     */   
/*     */   protected ClickButtonElement buttonClose;
/*     */ 
/*     */   
/*     */   public SIOPageInfoDialog(final ClientContext context, SIO lu) {
/*  51 */     super(context);
/*  52 */     this.lu = lu;
/*     */     
/*  54 */     this.buttonClose = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  56 */           return context.getLabel("close");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  60 */           return MainPage.getInstance(context);
/*     */         }
/*     */       };
/*  63 */     addElement((HtmlElement)this.buttonClose);
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/*  67 */     return this.context.getLabel("si.pageinfo");
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/*  71 */     StringBuffer code = new StringBuffer(template);
/*     */     
/*  73 */     String luNumber = null;
/*  74 */     String kta = null;
/*  75 */     String subject = null;
/*  76 */     Date pubDate = null;
/*     */     try {
/*  78 */       subject = this.lu.getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*  79 */       if (this.lu instanceof com.eoos.gm.tis2web.si.service.cai.SIOLU) {
/*  80 */         luNumber = (String)this.lu.getProperty((SITOCProperty)SIOProperty.LU);
/*  81 */         kta = (String)this.lu.getProperty((SITOCProperty)SIOProperty.Publication);
/*  82 */         String str = (String)this.lu.getProperty((SITOCProperty)SIOProperty.PublicationDate);
/*  83 */         if (str != null) {
/*     */           try {
/*  85 */             SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/*  86 */             pubDate = sdf.parse(str);
/*  87 */           } catch (Exception e) {
/*  88 */             log.error("unable to retrieve publication date - error:" + e, e);
/*     */           } 
/*     */         }
/*  91 */         StringUtilities.replace(code, "{LABEL_LU}", this.context.getLabel("si.lu"));
/*     */       }
/*  93 */       else if (this.lu instanceof com.eoos.gm.tis2web.si.service.cai.SIOCPR) {
/*  94 */         String delimitator = ",";
/*  95 */         List list = (List)this.lu.getProperty((SITOCProperty)SIOProperty.ElectronicSystem);
/*  96 */         for (Iterator<Integer> it = list.iterator(); it.hasNext();) {
/*  97 */           luNumber = (Integer)it.next() + delimitator;
/*     */         }
/*  99 */         luNumber = StringUtilities.replace(luNumber, delimitator, "");
/* 100 */         StringUtilities.replace(code, "{LABEL_LU}", "CPR");
/*     */       }
/* 102 */       else if (this.lu instanceof com.eoos.gm.tis2web.si.service.cai.SIOWD) {
/* 103 */         luNumber = this.lu.getProperty((SITOCProperty)SIOProperty.WD).toString();
/* 104 */         StringUtilities.replace(code, "{LABEL_LU}", "WD");
/*     */       }
/*     */     
/* 107 */     } catch (Exception e) {
/* 108 */       log.error("unable to retrieve page information - error:" + e, e);
/*     */     } 
/* 110 */     StringUtilities.replace(code, "{LU}", (luNumber != null) ? luNumber : "");
/*     */     
/* 112 */     StringUtilities.replace(code, "{LABEL_KTA}", this.context.getLabel("si.kta"));
/* 113 */     StringUtilities.replace(code, "{KTA}", (kta != null) ? kta : "");
/*     */     
/* 115 */     StringUtilities.replace(code, "{LABEL_SUBJECT}", this.context.getLabel("si.subject"));
/* 116 */     StringUtilities.replace(code, "{SUBJECT}", (subject != null) ? subject : "");
/*     */     
/* 118 */     String tmpDate = "";
/* 119 */     if (pubDate != null) {
/*     */       try {
/* 121 */         DateFormat df = DateFormat.getDateInstance(2, this.context.getLocale());
/* 122 */         tmpDate = df.format(pubDate);
/* 123 */       } catch (Exception e) {
/* 124 */         log.error("unable to format date - error:" + e, e);
/*     */       } 
/*     */     }
/* 127 */     StringUtilities.replace(code, "{LABEL_PUB_DATE}", this.context.getLabel("si.publication.date"));
/* 128 */     StringUtilities.replace(code, "{PUB_DATE}", tmpDate);
/*     */     
/* 130 */     StringUtilities.replace(code, "{BUTTON_CLOSE}", this.buttonClose.getHtmlCode(params));
/*     */     
/* 132 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\SIOPageInfoDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */