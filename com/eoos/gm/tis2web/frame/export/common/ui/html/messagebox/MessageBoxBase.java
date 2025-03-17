/*     */ package com.eoos.gm.tis2web.frame.export.common.ui.html.messagebox;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.Page;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.ImageElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ public abstract class MessageBoxBase
/*     */   extends Page
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(MessageBoxBase.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  34 */       template = ApplicationContext.getInstance().loadFile(MessageBoxBase.class, "messagebox.html", null).toString();
/*  35 */     } catch (Exception e) {
/*  36 */       log.error("Could not load template - error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*  40 */   private List buttons = null;
/*     */   
/*  42 */   private HtmlElement caption = null;
/*     */   
/*  44 */   private HtmlElement content = null;
/*     */   
/*  46 */   private HtmlElement icon = null;
/*     */ 
/*     */   
/*     */   public MessageBoxBase(ClientContext context) {
/*  50 */     super(context);
/*  51 */     this.buttons = new ArrayList();
/*  52 */     createIcon(getIcon());
/*  53 */     createCaption(getCaption());
/*  54 */     createContent(getContent());
/*  55 */     createButtons(getButtons());
/*     */   }
/*     */   
/*     */   private void createButtons(List buttons) {
/*  59 */     if (buttons != null && buttons.size() != 0) {
/*  60 */       Iterator<String> it = buttons.iterator();
/*  61 */       while (it.hasNext()) {
/*  62 */         final String buttonLabel = it.next();
/*  63 */         ClickButtonElement button = new ClickButtonElement(this.context.createID(), null) {
/*     */             public String getLabel() {
/*  65 */               return buttonLabel;
/*     */             }
/*     */             
/*     */             public Object onClick(Map params) {
/*  69 */               return MessageBoxBase.this.onClick(buttonLabel);
/*     */             }
/*     */           };
/*  72 */         addElement((HtmlElement)button);
/*  73 */         this.buttons.add(button);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createCaption(String caption) {
/*  80 */     this.caption = (HtmlElement)new HtmlLabel(caption);
/*  81 */     addElement(this.caption);
/*     */   }
/*     */   
/*     */   private void createContent(String content) {
/*  85 */     this.content = (HtmlElement)new HtmlLabel(content);
/*  86 */     addElement(this.content);
/*     */   }
/*     */   
/*     */   private void createIcon(final String iconFileName) {
/*  90 */     this.icon = (HtmlElement)new ImageElement() {
/*     */         protected void getAdditionalAttributes(Map<String, String> map) {
/*  92 */           map.put("id", "messagebox_icon");
/*     */         }
/*     */         
/*     */         protected String getAlternativeText() {
/*  96 */           return "";
/*     */         }
/*     */         
/*     */         protected String getImageURL() {
/* 100 */           return "pic/" + iconFileName;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getFormContent(Map params) {
/* 112 */     StringBuffer htmlCode = new StringBuffer(template);
/* 113 */     new StringUtilities();
/*     */     
/* 115 */     StringUtilities.replace(htmlCode, "{ICON}", this.icon.getHtmlCode(params));
/* 116 */     StringUtilities.replace(htmlCode, "{CAPTION}", this.caption.getHtmlCode(params));
/* 117 */     StringUtilities.replace(htmlCode, "{CONTENT}", this.content.getHtmlCode(params));
/*     */     
/* 119 */     Iterator<ClickButtonElement> it = this.buttons.iterator();
/* 120 */     while (it.hasNext()) {
/* 121 */       ClickButtonElement button = it.next();
/* 122 */       StringUtilities.replace(htmlCode, "{BUTTONS}", button.getHtmlCode(params) + "&nbsp;{BUTTONS}");
/*     */     } 
/* 124 */     StringUtilities.replace(htmlCode, "&nbsp;{BUTTONS}", "");
/*     */     
/* 126 */     return htmlCode.toString();
/*     */   }
/*     */   
/*     */   protected abstract List getButtons();
/*     */   
/*     */   protected abstract String getCaption();
/*     */   
/*     */   protected abstract String getContent();
/*     */   
/*     */   protected abstract String getIcon();
/*     */   
/*     */   protected abstract Object onClick(String paramString);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\messagebox\MessageBoxBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */