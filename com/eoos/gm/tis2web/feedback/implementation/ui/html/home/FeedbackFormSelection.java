/*     */ package com.eoos.gm.tis2web.feedback.implementation.ui.html.home;
/*     */ 
/*     */ import com.eoos.gm.tis2web.feedback.implementation.data.FeedbackForms;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.SelectBoxSelectionElement;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FeedbackFormSelection
/*     */   extends SelectBoxSelectionElement
/*     */ {
/*     */   private ClientContext context;
/*     */   private List[] formList;
/*     */   private FeedbackPanel parent;
/*     */   
/*     */   public FeedbackFormSelection(ClientContext context, FeedbackPanel fdP) {
/*  31 */     super(context.createID(), true, null, 1);
/*  32 */     this.context = context;
/*  33 */     this.parent = fdP;
/*  34 */     this.formList = getFormList(this.parent.getFeedbackElement().getLocale());
/*  35 */     setOptions(this.formList[2]);
/*  36 */     String mod = this.parent.getFeedbackElement().getType();
/*  37 */     String defaultMod = ((ApplicationContext)this.context.getApplicationContext()).getProperty("frame.feedback.form.default");
/*  38 */     defaultMod = (defaultMod != null) ? defaultMod : "web-questionnaire";
/*  39 */     log.debug("angefordert ->" + mod + ": default: ->" + defaultMod);
/*  40 */     String[] feedbackName = detectedForm(mod, defaultMod);
/*  41 */     if (feedbackName[0].compareTo("-1") != 0) {
/*  42 */       this.parent.getFeedbackElement().setLabel(feedbackName[0]);
/*  43 */       this.parent.getFeedbackElement().setTemplateName(feedbackName[1]);
/*  44 */       setValue(feedbackName[0]);
/*     */     } else {
/*  46 */       setValue(this.formList[2].get(0));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String[] detectedForm(String mod, String defaultMod) {
/*  51 */     String[] ret = { "-1", "" };
/*  52 */     boolean found1 = false;
/*  53 */     boolean found2 = false;
/*  54 */     boolean found = false;
/*  55 */     for (Iterator<?> iter = this.formList[0].iterator(); iter.hasNext() && !found; found = (found2 || found1)) {
/*  56 */       String tmp = (String)iter.next();
/*  57 */       String serviceType = this.formList[1].get(this.formList[0].indexOf(tmp));
/*  58 */       found1 = tmp.startsWith(mod.toLowerCase(Locale.ENGLISH));
/*  59 */       found2 = serviceType.startsWith(mod.toLowerCase(Locale.ENGLISH));
/*  60 */       if (found2) {
/*  61 */         ret[0] = this.formList[2].get(this.formList[1].indexOf(serviceType));
/*  62 */         ret[1] = this.formList[3].get(this.formList[1].indexOf(serviceType));
/*     */       } 
/*  64 */       if (!found2 && found1) {
/*  65 */         ret[0] = this.formList[2].get(this.formList[0].indexOf(tmp));
/*  66 */         ret[1] = this.formList[3].get(this.formList[0].indexOf(tmp));
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     if (ret[0].compareTo("-1") == 0 && mod.compareTo(defaultMod) != 0)
/*  71 */       ret = detectedForm(defaultMod, defaultMod); 
/*  72 */     return ret;
/*     */   }
/*     */   
/*     */   protected String[] getFormValues() {
/*  76 */     String Label = getLabel();
/*  77 */     String[] ret = { "-1", Label, "" };
/*  78 */     boolean found = false;
/*  79 */     for (Iterator<?> iter = this.formList[2].iterator(); iter.hasNext() && !found; ) {
/*  80 */       String tmp = (String)iter.next();
/*  81 */       found = (tmp.compareTo(Label) == 0);
/*  82 */       if (found) {
/*  83 */         ret[0] = this.formList[0].get(this.formList[2].indexOf(tmp));
/*  84 */         ret[2] = this.formList[3].get(this.formList[2].indexOf(tmp));
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     return ret;
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  92 */     String Wert = String.valueOf(getValue());
/*  93 */     return Wert;
/*     */   }
/*     */   
/*     */   private List[] getFormList(String Locale) {
/*  97 */     LinkedList[] arrayOfLinkedList = new LinkedList[4];
/*  98 */     arrayOfLinkedList[0] = new LinkedList();
/*  99 */     arrayOfLinkedList[1] = new LinkedList();
/* 100 */     arrayOfLinkedList[2] = new LinkedList();
/* 101 */     arrayOfLinkedList[3] = new LinkedList();
/*     */     try {
/* 103 */       ByteArrayInputStream bais = new ByteArrayInputStream(FeedbackForms.getInstance().loadIndexFile());
/* 104 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */       int j;
/* 106 */       while ((j = bais.read()) != -1)
/* 107 */         baos.write(j); 
/* 108 */       bais.close();
/* 109 */       baos.close();
/* 110 */       StringTokenizer st = new StringTokenizer(baos.toString());
/* 111 */       String[] fileList = new String[st.countTokens()];
/* 112 */       for (int count = 0; st.hasMoreTokens(); count++) {
/* 113 */         fileList[count] = st.nextToken();
/*     */       }
/* 115 */       for (int i = 0; i < fileList.length; i++) {
/* 116 */         bais = new ByteArrayInputStream(FeedbackForms.getInstance().loadFeedbackForm(fileList[i]));
/* 117 */         String[] Liste = getFormName(bais, Locale);
/* 118 */         bais.close();
/* 119 */         if (Liste[0] != null && Liste[2] != null) {
/* 120 */           arrayOfLinkedList[0].add(Liste[0]);
/* 121 */           arrayOfLinkedList[1].add(Liste[1]);
/* 122 */           arrayOfLinkedList[2].add(Liste[2]);
/* 123 */           arrayOfLinkedList[3].add("/feedback/forms/" + fileList[i]);
/*     */         }
/*     */       
/*     */       } 
/* 127 */     } catch (Exception e) {
/* 128 */       log.error("unable to find Forms - error:" + e, e);
/* 129 */       throw new RuntimeException();
/*     */     } 
/* 131 */     return (List[])arrayOfLinkedList;
/*     */   }
/*     */   
/*     */   protected String[] getFormName(ByteArrayInputStream template, String locale) {
/* 135 */     String[] ret = new String[3];
/*     */     try {
/* 137 */       Document doc = this.parent.parse(template);
/* 138 */       Element feedbackType = doc.getRootElement().getChild("FeedbackHeader").getChild("FeedbackType");
/* 139 */       ret[0] = feedbackType.getAttributeValue("FeedbackTypeID");
/* 140 */       if (feedbackType.getAttributes().size() == 2) {
/* 141 */         ret[1] = feedbackType.getAttributeValue("ServiceInformationType").toLowerCase(Locale.ENGLISH);
/*     */       } else {
/* 143 */         ret[1] = "";
/* 144 */       }  Element tmp = this.parent.getLocaleLabel(feedbackType.getChildren("Label"), locale, true);
/* 145 */       if (tmp != null) {
/* 146 */         ret[2] = tmp.getAttributeValue("Label");
/*     */       } else {
/* 148 */         ret[2] = null;
/* 149 */       }  log.debug(ret[0] + ":" + ret[1] + ":" + ret[2] + " ->" + locale);
/* 150 */     } catch (Exception e) {
/* 151 */       log.error("unable to get FormTemplateName - error:" + e, e);
/* 152 */       throw new RuntimeException();
/*     */     } 
/* 154 */     return ret;
/*     */   }
/*     */   
/*     */   protected boolean autoSubmitOnChange() {
/* 158 */     return true;
/*     */   }
/*     */   
/*     */   protected Object onChange(Map submitParams) {
/*     */     HtmlElementContainer container;
/* 163 */     for (container = getContainer(); container.getContainer() != null; container = container.getContainer()) {
/* 164 */       String[] Werte = getFormValues();
/* 165 */       this.parent.setFeedbackContentPanel(Werte);
/* 166 */       this.parent.setSubmit(null);
/*     */     } 
/* 168 */     this.parent.reset();
/* 169 */     return container;
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
/* 181 */   private static final Logger log = Logger.getLogger(FeedbackFormSelection.class);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementatio\\ui\html\home\FeedbackFormSelection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */