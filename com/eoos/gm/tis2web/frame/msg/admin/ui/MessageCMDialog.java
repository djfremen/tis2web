/*     */ package com.eoos.gm.tis2web.frame.msg.admin.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.DialogBase;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.MessageManager;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.ui.el.GroupSelectionElement;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.ui.el.LocaleSelectionElement;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.ui.el.ModuleSelectionElement;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.ui.el.TypeSelectionElement;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.ui.el.UserIDsInputElement;
/*     */ import com.eoos.gm.tis2web.frame.msg.util.MessageUtil;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextAreaInputElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.scsm.v2.util.StringUtilities;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class MessageCMDialog
/*     */   extends DialogBase
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(MessageCMDialog.class);
/*     */   
/*     */   private static String template;
/*     */   
/*     */   private Callback callback;
/*     */   
/*     */   private ClientContext context;
/*     */   
/*     */   private TextInputElement inputExternalID;
/*     */   
/*     */   private TypeSelectionElement selectionType;
/*     */   
/*     */   private ModuleSelectionElement selectionModules;
/*     */   
/*     */   private GroupSelectionElement selectionGroup;
/*     */   
/*     */   private UserIDsInputElement selectionUserIDs;
/*     */   
/*     */   private SelectBoxSelectionElement selectionLocale;
/*     */   
/*     */   static {
/*     */     try {
/*  54 */       template = ApplicationContext.getInstance().loadFile(SearchInputPanel.class, "detailpanel.html", null).toString();
/*  55 */     } catch (Exception e) {
/*  56 */       log.error("error loading template - error:" + e, e);
/*  57 */       throw new RuntimeException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private Map localeToTitleInput = new HashMap<Object, Object>();
/*     */   
/*  79 */   private Map localeToTextInput = new HashMap<Object, Object>();
/*     */   
/*     */   private LocaleSelectionElement defaultLocaleSelection;
/*     */   
/*  83 */   private TextInputElement currentTitleInput = null;
/*     */   
/*  85 */   private TextAreaInputElement currentTextInput = null;
/*     */   
/*     */   private ClickButtonElement buttonClose;
/*     */   
/*     */   private ClickButtonElement buttonAction;
/*     */   
/*     */   public MessageCMDialog(ClientContext context, Callback callback) {
/*  92 */     super(context);
/*  93 */     this.callback = callback;
/*  94 */     this.context = context;
/*  95 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/* 100 */     removeAllElements();
/*     */     
/* 102 */     IMessage message = this.callback.getMessage();
/* 103 */     this.inputExternalID = new TextInputElement(this.context.createID());
/* 104 */     addElement((HtmlElement)this.inputExternalID);
/* 105 */     if (message != null) {
/* 106 */       this.inputExternalID.setValue(message.getExternalID());
/*     */     }
/*     */     
/* 109 */     this.selectionType = TypeSelectionElement.create(this.context, message);
/* 110 */     addElement((HtmlElement)this.selectionType);
/*     */     
/* 112 */     this.selectionModules = ModuleSelectionElement.create(this.context, message);
/* 113 */     addElement((HtmlElement)this.selectionModules);
/*     */     
/* 115 */     this.selectionGroup = GroupSelectionElement.create(this.context, message);
/* 116 */     addElement((HtmlElement)this.selectionGroup);
/*     */     
/* 118 */     this.selectionUserIDs = UserIDsInputElement.create(this.context, message);
/* 119 */     addElement((HtmlElement)this.selectionUserIDs);
/*     */     
/* 121 */     this.defaultLocaleSelection = LocaleSelectionElement.create(this.context, message, new LocaleSelectionElement.Callback()
/*     */         {
/*     */           public Object onChange() {
/* 124 */             return null;
/*     */           }
/*     */           
/*     */           public boolean mark(Locale locale) {
/* 128 */             return false;
/*     */           }
/*     */           
/*     */           public boolean autoSubmit() {
/* 132 */             return false;
/*     */           }
/*     */         });
/*     */     
/* 136 */     addElement((HtmlElement)this.defaultLocaleSelection);
/* 137 */     if (message != null) {
/* 138 */       Locale df = message.getDefaultLocale();
/* 139 */       this.defaultLocaleSelection.setValue((df != null) ? df : Locale.ENGLISH);
/*     */     } else {
/* 141 */       this.defaultLocaleSelection.setValue(Locale.ENGLISH);
/*     */     } 
/*     */     
/* 144 */     this.selectionLocale = (SelectBoxSelectionElement)LocaleSelectionElement.create(this.context, message, new LocaleSelectionElement.Callback()
/*     */         {
/*     */           public Object onChange() {
/* 147 */             Locale locale = (Locale)MessageCMDialog.this.selectionLocale.getValue();
/* 148 */             MessageCMDialog.this.onLocaleChange(locale);
/* 149 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean mark(Locale locale) {
/* 154 */             String title = (String)MessageCMDialog.this.getTitleInput(locale).getValue();
/* 155 */             return (title != null && title.trim().length() > 0);
/*     */           }
/*     */           
/*     */           public boolean autoSubmit() {
/* 159 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 163 */     addElement((HtmlElement)this.selectionLocale);
/* 164 */     onLocaleChange(Locale.ENGLISH);
/* 165 */     if (message != null) {
/* 166 */       for (Iterator<Locale> iter = message.getSupportedLocales().iterator(); iter.hasNext(); ) {
/* 167 */         Locale locale = iter.next();
/* 168 */         IMessage.IContent content = message.getContent(locale);
/* 169 */         getTitleInput(locale).setValue(content.getTitle());
/* 170 */         getTextInput(locale).setValue(content.getText());
/*     */       } 
/*     */     }
/*     */     
/* 174 */     this.buttonAction = new ClickButtonElement(this.context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 177 */           return MessageCMDialog.this.creationMode() ? MessageCMDialog.this.context.getLabel("create") : MessageCMDialog.this.context.getLabel("modify");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 182 */             return MessageCMDialog.this.callback.onCreateOrModify(MessageCMDialog.this.callback.getMessage(), MessageCMDialog.this.getCurrentMessage());
/* 183 */           } catch (com.eoos.gm.tis2web.frame.msg.admin.MessageManager.InvalidInputException e) {
/* 184 */             return MessageCMDialog.this.getErrorPopup(MessageCMDialog.this.context.getMessage("invalid.or.incomplete.input"));
/* 185 */           } catch (Exception e) {
/* 186 */             MessageCMDialog.log.error("unable to create or modify message  - exception: " + e, e);
/* 187 */             return MessageCMDialog.this.getErrorPopup(MessageCMDialog.this.context.getMessage("unable.to." + (MessageCMDialog.this.creationMode() ? "create" : "modify") + ".msg"));
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 192 */     addElement((HtmlElement)this.buttonAction);
/*     */     
/* 194 */     this.buttonClose = new ClickButtonElement(this.context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 197 */           return MessageCMDialog.this.context.getLabel("cancel");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/* 201 */           return MessageCMDialog.this.callback.onClose();
/*     */         }
/*     */       };
/*     */     
/* 205 */     addElement((HtmlElement)this.buttonClose);
/*     */     
/* 207 */     this.selectionLocale.setValue(Locale.ENGLISH);
/* 208 */     onLocaleChange(Locale.ENGLISH);
/*     */   }
/*     */ 
/*     */   
/*     */   private void onLocaleChange(Locale locale) {
/* 213 */     if (this.currentTextInput != null) {
/* 214 */       removeElement((HtmlElement)this.currentTextInput);
/*     */     }
/* 216 */     if (this.currentTitleInput != null) {
/* 217 */       removeElement((HtmlElement)this.currentTitleInput);
/*     */     }
/*     */     
/* 220 */     this.currentTextInput = getTextInput(locale);
/* 221 */     addElement((HtmlElement)this.currentTextInput);
/*     */     
/* 223 */     this.currentTitleInput = getTitleInput(locale);
/* 224 */     addElement((HtmlElement)this.currentTitleInput);
/*     */   } public static interface Callback {
/*     */     public static final int MODE_READONLY = 1; public static final int MODE_MODFIY = 2; public static final int MODE_CREATE = 3; IMessage getMessage(); Object onClose(); Object onCreateOrModify(IMessage param1IMessage1, IMessage param1IMessage2) throws Exception;
/*     */     int getMode(); }
/*     */   protected String getContent(Map params) {
/* 229 */     StringBuffer tmp = new StringBuffer(template);
/*     */     
/* 231 */     StringUtilities.replace(tmp, "{LABEL_ID}", this.context.getLabel("identifier"));
/* 232 */     StringUtilities.replace(tmp, "{INPUT_ID}", this.inputExternalID.getHtmlCode(params));
/*     */     
/* 234 */     StringUtilities.replace(tmp, "{LABEL_TYPE}", this.context.getLabel("type"));
/* 235 */     StringUtilities.replace(tmp, "{SELECTION_TYPE}", this.selectionType.getHtmlCode(params));
/*     */     
/* 237 */     StringUtilities.replace(tmp, "{LABEL_APPLICATIONS}", this.context.getLabel("application.s"));
/* 238 */     StringUtilities.replace(tmp, "{SELECTION_APPLICATIONS}", this.selectionModules.getHtmlCode(params));
/*     */     
/* 240 */     StringUtilities.replace(tmp, "{LABEL_GROUPS}", this.context.getLabel("group.s"));
/* 241 */     StringUtilities.replace(tmp, "{SELECTION_GROUPS}", this.selectionGroup.getHtmlCode(params));
/*     */     
/* 243 */     StringUtilities.replace(tmp, "{LABEL_USERIDS}", this.context.getLabel("user.s"));
/* 244 */     StringUtilities.replace(tmp, "{INPUT_USERIDS}", this.selectionUserIDs.getHtmlCode(params));
/*     */     
/* 246 */     StringUtilities.replace(tmp, "{LABEL_LOCALE}", this.context.getLabel("language"));
/* 247 */     StringUtilities.replace(tmp, "{SELECTION_LOCALE}", this.selectionLocale.getHtmlCode(params));
/*     */     
/* 249 */     StringUtilities.replace(tmp, "{SELECTION_DEFAULT_LOCALE}", this.defaultLocaleSelection.getHtmlCode(params));
/* 250 */     StringUtilities.replace(tmp, "{LABEL_DEFAULT_LOCALE}", this.context.getLabel("default.locale"));
/*     */     
/* 252 */     this.selectionLocale.getValue();
/* 253 */     StringUtilities.replace(tmp, "{LABEL_TITLE}", this.context.getLabel("title"));
/* 254 */     StringUtilities.replace(tmp, "{INPUT_TITLE}", this.currentTitleInput.getHtmlCode(params));
/*     */     
/* 256 */     StringUtilities.replace(tmp, "{LABEL_TEXT}", this.context.getLabel("text"));
/* 257 */     StringUtilities.replace(tmp, "{INPUT_TEXT}", this.currentTextInput.getHtmlCode(params));
/*     */     
/* 259 */     StringUtilities.replace(tmp, "{BUTTON_ACTION}", this.buttonAction.getHtmlCode(params));
/*     */     
/* 261 */     StringUtilities.replace(tmp, "{BUTTON_CLOSE}", this.buttonClose.getHtmlCode(params));
/*     */     
/* 263 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   private boolean creationMode() {
/* 267 */     return (this.callback.getMode() == 3);
/*     */   }
/*     */   
/*     */   protected String getTitle(Map params) {
/* 271 */     if (creationMode()) {
/* 272 */       return this.context.getLabel("create.message");
/*     */     }
/* 274 */     return this.context.getLabel("modify.message");
/*     */   }
/*     */ 
/*     */   
/*     */   private IMessage getCurrentMessage() {
/* 279 */     final IMessage backend = this.callback.getMessage();
/*     */     
/* 281 */     return new IMessage()
/*     */       {
/*     */         public IMessage.Type getType() {
/* 284 */           return (IMessage.Type)MessageCMDialog.this.selectionType.getValue();
/*     */         }
/*     */         
/*     */         public IMessage.Status getStatus() {
/* 288 */           if (backend != null) {
/* 289 */             return backend.getStatus();
/*     */           }
/* 291 */           return IMessage.Status.INACTIVE;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getID() {
/* 296 */           return backend.getID();
/*     */         }
/*     */         
/*     */         public String getExternalID() {
/* 300 */           return (String)MessageCMDialog.this.inputExternalID.getValue();
/*     */         }
/*     */         
/*     */         public Locale getDefaultLocale() {
/* 304 */           return (Locale)MessageCMDialog.this.defaultLocaleSelection.getValue();
/*     */         }
/*     */         
/*     */         public Set getUserGroups() {
/* 308 */           return new HashSet((Collection)MessageCMDialog.this.selectionGroup.getValue());
/*     */         }
/*     */         
/*     */         public IMessage.IContent getContent(final Locale locale) {
/* 312 */           return new IMessage.IContent()
/*     */             {
/*     */               public String getTitle() {
/* 315 */                 return (String)MessageCMDialog.this.getTitleInput(locale).getValue();
/*     */               }
/*     */               
/*     */               public String getText() {
/* 319 */                 return (String)MessageCMDialog.this.getTextInput(locale).getValue();
/*     */               }
/*     */               
/*     */               public Locale getLocale() {
/* 323 */                 return locale;
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public Set getSupportedLocales() {
/* 331 */           Set<Locale> ret = new HashSet(MessageCMDialog.this.localeToTitleInput.keySet());
/* 332 */           ret.addAll(MessageCMDialog.this.localeToTextInput.keySet());
/* 333 */           for (Iterator<Locale> iter = ret.iterator(); iter.hasNext(); ) {
/* 334 */             Locale locale = iter.next();
/* 335 */             if (MessageUtil.emptyContent(this, locale)) {
/* 336 */               iter.remove();
/*     */             }
/*     */           } 
/* 339 */           ret.add(getDefaultLocale());
/* 340 */           return ret;
/*     */         }
/*     */         
/*     */         public Set getTargetModules() {
/* 344 */           return new HashSet((Collection)MessageCMDialog.this.selectionModules.getValue());
/*     */         }
/*     */         
/*     */         public Set getUserIDs() {
/* 348 */           return new HashSet((Collection)MessageCMDialog.this.selectionUserIDs.getValue());
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private TextInputElement getTitleInput(Locale locale) {
/* 355 */     TextInputElement ret = (TextInputElement)this.localeToTitleInput.get(locale);
/* 356 */     if (ret == null) {
/* 357 */       ret = new TextInputElement(this.context.createID());
/* 358 */       this.localeToTitleInput.put(locale, ret);
/*     */     } 
/* 360 */     return ret;
/*     */   }
/*     */   
/*     */   private TextAreaInputElement getTextInput(Locale locale) {
/* 364 */     TextAreaInputElement ret = (TextAreaInputElement)this.localeToTextInput.get(locale);
/* 365 */     if (ret == null) {
/* 366 */       ret = new TextAreaInputElement(this.context.createID(), "20", "50");
/* 367 */       this.localeToTextInput.put(locale, ret);
/*     */     } 
/* 369 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\MessageCMDialog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */