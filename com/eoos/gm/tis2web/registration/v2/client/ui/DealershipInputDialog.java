/*     */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.DealershipInfo;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
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
/*     */ public class DealershipInputDialog
/*     */   extends DialogBase
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(DealershipInputDialog.class); private static final String TEMPLATE; private Callback callback; private DealershipInfoInputElement ieDealershipInfo; private ClickButtonElement buttonStore;
/*     */   private ClickButtonElement buttonCancel;
/*     */   
/*     */   static {
/*     */     try {
/*  29 */       TEMPLATE = ApplicationContext.getInstance().loadFile(DealershipInputDialog.class, "dealershipinfo_dialog.html", null).toString();
/*  30 */     } catch (Exception e) {
/*  31 */       throw new RuntimeException(e);
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
/*     */   public DealershipInputDialog(final ClientContext context, Callback callback) {
/*  44 */     super(context);
/*  45 */     this.callback = callback;
/*     */     
/*  47 */     this.ieDealershipInfo = new DealershipInfoInputElement(context);
/*  48 */     addElement((HtmlElement)this.ieDealershipInfo);
/*  49 */     this.ieDealershipInfo.setValue(callback.getDealershipInfo());
/*     */     
/*  51 */     this.buttonCancel = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  54 */           return context.getLabel("cancel");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  58 */           return DealershipInputDialog.this.onCancel();
/*     */         }
/*     */       };
/*     */     
/*  62 */     addElement((HtmlElement)this.buttonCancel);
/*     */     
/*  64 */     this.buttonStore = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  67 */           return context.getLabel("store");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  71 */           return DealershipInputDialog.this.onStore();
/*     */         }
/*     */       };
/*     */     
/*  75 */     addElement((HtmlElement)this.buttonStore);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getContent(Map params) {
/*  80 */     StringBuffer ret = new StringBuffer(TEMPLATE);
/*  81 */     String message = StringUtilities.replace(this.context.getMessage("please.fill.in.dealership.info"), "*", "<span style=\"color:red\">*</span>");
/*     */     
/*  83 */     StringUtilities.replace(ret, "{MSG}", message);
/*  84 */     StringUtilities.replace(ret, "{INPUT_DEALERSHIPINFO}", this.ieDealershipInfo.getHtmlCode(params));
/*  85 */     StringUtilities.replace(ret, "{BUTTON_STORE}", this.buttonStore.getHtmlCode(params));
/*  86 */     StringUtilities.replace(ret, "{BUTTON_CANCEL}", this.buttonCancel.getHtmlCode(params));
/*  87 */     return ret.toString();
/*     */   } public static interface Callback {
/*     */     DealershipInfo getDealershipInfo(); Object onStore(DealershipInfo param1DealershipInfo) throws Exception; Object onCancel(); }
/*     */   protected String getTitle(Map params) {
/*  91 */     return this.context.getLabel("dealership.information");
/*     */   }
/*     */   
/*     */   private Object onCancel() {
/*  95 */     return this.callback.onCancel();
/*     */   }
/*     */   
/*     */   private Object onStore() {
/*     */     try {
/* 100 */       return this.callback.onStore(this.ieDealershipInfo);
/* 101 */     } catch (InvalidInputException e) {
/* 102 */       return getErrorPopup(e);
/* 103 */     } catch (Exception e) {
/* 104 */       log.error("unable to store dealership data - exception: " + e, e);
/* 105 */       return getErrorPopup(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\DealershipInputDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */