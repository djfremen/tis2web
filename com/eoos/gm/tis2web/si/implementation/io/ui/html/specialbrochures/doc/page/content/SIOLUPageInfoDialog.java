/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIOLUPageInfoDialog
/*     */   extends DialogBase
/*     */ {
/*  27 */   private static final Logger log = Logger.getLogger(SIOLUPageInfoDialog.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  32 */       template = ApplicationContext.getInstance().loadFile(SIOLUPageInfoDialog.class, "silupageinfo.html", null).toString();
/*  33 */     } catch (Exception e) {
/*  34 */       log.error("unable to load template - error:" + e, e);
/*  35 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SIOLU lu;
/*     */   
/*     */   protected ClickButtonElement buttonClose;
/*     */ 
/*     */   
/*     */   public SIOLUPageInfoDialog(final ClientContext context, SIOLU lu) {
/*  47 */     super(context);
/*  48 */     this.lu = lu;
/*     */     
/*  50 */     this.buttonClose = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  52 */           return context.getLabel("close");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  56 */           return MainPage.getInstance(context);
/*     */         }
/*     */       };
/*  59 */     addElement((HtmlElement)this.buttonClose);
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/*  63 */     return this.context.getLabel("si.pageinfo");
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/*  67 */     StringBuffer code = new StringBuffer(template);
/*     */     
/*  69 */     String luNumber = null;
/*  70 */     String kta = null;
/*  71 */     String subject = null;
/*  72 */     Date pubDate = null;
/*     */     
/*     */     try {
/*  75 */       luNumber = (String)this.lu.getProperty((SITOCProperty)SIOProperty.LU);
/*  76 */       kta = (String)this.lu.getProperty((SITOCProperty)SIOProperty.Publication);
/*  77 */       subject = this.lu.getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*  78 */       String str = (String)this.lu.getProperty((SITOCProperty)SIOProperty.PublicationDate);
/*     */       try {
/*  80 */         SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/*  81 */         pubDate = sdf.parse(str);
/*  82 */       } catch (Exception e) {
/*  83 */         log.error("unable to retrieve publication date - error:" + e, e);
/*     */       } 
/*  85 */     } catch (Exception e) {
/*  86 */       log.error("unable to retrieve page information - error:" + e, e);
/*     */     } 
/*     */     
/*  89 */     StringUtilities.replace(code, "{LABEL_LU}", this.context.getLabel("si.lu"));
/*  90 */     StringUtilities.replace(code, "{LU}", (luNumber != null) ? luNumber : "");
/*     */     
/*  92 */     StringUtilities.replace(code, "{LABEL_KTA}", this.context.getLabel("si.kta"));
/*  93 */     StringUtilities.replace(code, "{KTA}", (kta != null) ? kta : "");
/*     */     
/*  95 */     StringUtilities.replace(code, "{LABEL_SUBJECT}", this.context.getLabel("si.subject"));
/*  96 */     StringUtilities.replace(code, "{SUBJECT}", (subject != null) ? subject : "");
/*     */     
/*  98 */     String tmpDate = "";
/*     */     try {
/* 100 */       DateFormat df = DateFormat.getDateInstance(2, this.context.getLocale());
/* 101 */       tmpDate = df.format(pubDate);
/* 102 */     } catch (Exception e) {
/* 103 */       log.error("unable to format date - error:" + e, e);
/*     */     } 
/* 105 */     StringUtilities.replace(code, "{LABEL_PUB_DATE}", this.context.getLabel("si.publication.date"));
/* 106 */     StringUtilities.replace(code, "{PUB_DATE}", tmpDate);
/*     */     
/* 108 */     StringUtilities.replace(code, "{BUTTON_CLOSE}", this.buttonClose.getHtmlCode(params));
/*     */     
/* 110 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\doc\page\content\SIOLUPageInfoDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */