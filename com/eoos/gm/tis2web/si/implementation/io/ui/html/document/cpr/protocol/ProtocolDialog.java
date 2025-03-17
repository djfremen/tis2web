/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.protocol;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogLayout;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.History;
/*     */ import com.eoos.html.Dispatchable;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlLayout;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.util.StringUtilities;
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
/*     */ public class ProtocolDialog
/*     */   extends DialogBase
/*     */ {
/*     */   public static abstract class Callback
/*     */   {
/*     */     public abstract Object onReturn(Map param1Map);
/*     */   }
/*     */   
/*  34 */   private static final Logger log = Logger.getLogger(ProtocolDialog.class);
/*     */   private static String template;
/*     */   
/*     */   static {
/*     */     try {
/*  39 */       template = ApplicationContext.getInstance().loadFile(ProtocolDialog.class, "protocoldialog.html", null).toString();
/*  40 */     } catch (Exception e) {
/*  41 */       log.error("unable to load template - error:" + e, e);
/*  42 */       throw new RuntimeException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Callback callback;
/*     */   
/*     */   private ProtocolPage protocolPage;
/*     */   
/*     */   private ProtocolIFrame protocolIFrame;
/*     */   
/*     */   protected ClickButtonElement buttonClose;
/*     */   
/*     */   protected ClickButtonElement buttonReset;
/*     */   
/*     */   protected ProtocolSaveLink linkSave;
/*     */   private ClientContext context;
/*     */   
/*  60 */   private static DialogLayout layout = new DialogLayout() {
/*     */       public String getHorizontalAlignment() {
/*  62 */         return "center";
/*     */       }
/*     */       
/*     */       public String getWidth() {
/*  66 */         return "100%";
/*     */       }
/*     */     };
/*     */   
/*     */   public ProtocolDialog(final ClientContext context, final History history, Callback callback) {
/*  71 */     super(context);
/*  72 */     this.context = context;
/*  73 */     this.callback = callback;
/*  74 */     this.buttonClose = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  76 */           return context.getLabel("close");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  80 */           context.unregisterDispatchable((Dispatchable)ProtocolDialog.this.protocolPage);
/*  81 */           context.unregisterDispatchable((Dispatchable)ProtocolDialog.this);
/*  82 */           return ProtocolDialog.this.callback.onReturn(submitParams);
/*     */         }
/*     */       };
/*  85 */     addElement((HtmlElement)this.buttonClose);
/*  86 */     this.buttonReset = new ClickButtonElement(context.createID(), null) {
/*     */         protected String getLabel() {
/*  88 */           return context.getLabel("button.reset");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*  92 */           history.reset();
/*     */           
/*  94 */           HtmlElementContainer container = getContainer();
/*  95 */           while (container.getContainer() != null) {
/*  96 */             container = container.getContainer();
/*     */           }
/*  98 */           return container;
/*     */         }
/*     */       };
/* 101 */     addElement((HtmlElement)this.buttonReset);
/* 102 */     this.protocolPage = new ProtocolPage(context, history);
/* 103 */     this.protocolIFrame = new ProtocolIFrame(context, this.protocolPage);
/* 104 */     addElement((HtmlElement)this.protocolIFrame);
/* 105 */     this.linkSave = new ProtocolSaveLink(context, this.protocolPage);
/* 106 */     addElement((HtmlElement)this.linkSave);
/*     */   }
/*     */   
/*     */   protected String getContent(Map params) {
/* 110 */     StringBuffer code = new StringBuffer(template);
/*     */     
/* 112 */     StringUtilities.replace(code, "{BUTTON_RESET}", this.buttonReset.getHtmlCode(params));
/* 113 */     StringUtilities.replace(code, "{BUTTON_CLOSE}", this.buttonClose.getHtmlCode(params));
/* 114 */     StringUtilities.replace(code, "{linkSave}", this.linkSave.getHtmlCode(params));
/* 115 */     StringUtilities.replace(code, "{protocolIFrame}", this.protocolIFrame.getHtmlCode(params));
/*     */     
/* 117 */     return code.toString();
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 121 */     return this.context.getLabel("diagnostic.protocol");
/*     */   }
/*     */   
/*     */   public HtmlLayout getLayout() {
/* 125 */     return (HtmlLayout)layout;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\protocol\ProtocolDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */