/*     */ package com.eoos.gm.tis2web.registration.v2.client.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ContextualElementContainerBase;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKey;
/*     */ import com.eoos.gm.tis2web.registration.common.ui.datamodel.LicenseKeyFactory;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.html.renderer.HtmlTextInputFieldRenderer;
/*     */ import com.eoos.util.v2.StringUtilities;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LicenseKeyInputElement
/*     */   extends ContextualElementContainerBase {
/*  17 */   private static final Logger log = Logger.getLogger(LicenseKeyInputElement.class);
/*     */   
/*     */   static {
/*     */     try {
/*  21 */       TEMPLATE = ApplicationContext.getInstance().loadFile(LicenseKeyInputElement.class, "licensekey_input.html", null).toString();
/*  22 */     } catch (Exception e) {
/*  23 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final String TEMPLATE;
/*     */   
/*     */   private static final String RULER1 = "           1           2            3           4            5           6     ";
/*     */   
/*     */   private static final String RULER2 = "1234-5678-9012-3456-7890-1234-5678-9012-3456-7890-1234-5678-9012-3456-7890-1234";
/*     */   private TextInputElement ieTextInput;
/*     */   private HtmlTextInputFieldRenderer.Callback callbackRuler1;
/*     */   private HtmlTextInputFieldRenderer.Callback callbackRuler2;
/*     */   
/*     */   public LicenseKeyInputElement(ClientContext context) {
/*  38 */     super(context);
/*     */     
/*  40 */     this.ieTextInput = new TextInputElement(context.createID(), 79, 79);
/*  41 */     addElement((HtmlElement)this.ieTextInput);
/*     */     
/*  43 */     this.callbackRuler1 = new HtmlTextInputFieldRenderer.Callback()
/*     */       {
/*     */         public void init(Map params) {}
/*     */ 
/*     */         
/*     */         public boolean isReadonly() {
/*  49 */           return true;
/*     */         }
/*     */         
/*     */         public boolean isMasked() {
/*  53 */           return false;
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/*  57 */           return false;
/*     */         }
/*     */         
/*     */         public String getValue() {
/*  61 */           return "           1           2            3           4            5           6     ";
/*     */         }
/*     */         
/*     */         public String getParameterName() {
/*  65 */           return "ruler1";
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  70 */     this.callbackRuler2 = new HtmlTextInputFieldRenderer.Callback()
/*     */       {
/*     */         public void init(Map params) {}
/*     */ 
/*     */         
/*     */         public boolean isReadonly() {
/*  76 */           return true;
/*     */         }
/*     */         
/*     */         public boolean isMasked() {
/*  80 */           return false;
/*     */         }
/*     */         
/*     */         public boolean isDisabled() {
/*  84 */           return false;
/*     */         }
/*     */         
/*     */         public String getValue() {
/*  88 */           return "1234-5678-9012-3456-7890-1234-5678-9012-3456-7890-1234-5678-9012-3456-7890-1234";
/*     */         }
/*     */         
/*     */         public String getParameterName() {
/*  92 */           return "ruler2";
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  99 */     StringBuffer tmp = new StringBuffer(TEMPLATE);
/* 100 */     StringUtilities.replace(tmp, "{LABEL}", this.context.getLabel("license.key"));
/* 101 */     StringUtilities.replace(tmp, "{INPUT_KEY}", this.ieTextInput.getHtmlCode(params));
/* 102 */     StringUtilities.replace(tmp, "{RULER1}", HtmlTextInputFieldRenderer.getInstance().getHtmlCode(this.callbackRuler1));
/* 103 */     StringUtilities.replace(tmp, "{RULER2}", HtmlTextInputFieldRenderer.getInstance().getHtmlCode(this.callbackRuler2));
/* 104 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   public Object getValue() {
/* 108 */     String tmp = (String)this.ieTextInput.getValue();
/*     */     try {
/* 110 */       return LicenseKeyFactory.createLicenseKey(tmp);
/* 111 */     } catch (Exception e) {
/* 112 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setValue(Map map) {
/* 117 */     super.setValue(map);
/*     */     try {
/* 119 */       normalizeDisplayValue();
/* 120 */     } catch (Exception e) {
/* 121 */       log.warn("unable to normalize value, ignoring - exception: " + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void normalizeDisplayValue() throws Exception {
/* 126 */     String tmp = (String)this.ieTextInput.getValue();
/* 127 */     LicenseKey lk = LicenseKeyFactory.createLicenseKey(tmp);
/* 128 */     this.ieTextInput.setValue(lk.toExternalForm(true));
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 132 */     if (value != null) {
/* 133 */       this.ieTextInput.setValue(((LicenseKey)value).toExternalForm(true));
/*     */     } else {
/* 135 */       this.ieTextInput.setValue("");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\v2\clien\\ui\LicenseKeyInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */