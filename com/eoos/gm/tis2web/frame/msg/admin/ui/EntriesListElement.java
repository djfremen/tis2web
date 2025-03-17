/*     */ package com.eoos.gm.tis2web.frame.msg.admin.ui;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.Group;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ErrorMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.SimpleConfirmationMessageBox;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.MessageManager;
/*     */ import com.eoos.gm.tis2web.frame.msg.util.MessageUtil;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.HtmlLabel;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.ListElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class EntriesListElement
/*     */   extends ListElement
/*     */ {
/*  32 */   private static final Logger log = Logger.getLogger(EntriesListElement.class);
/*     */   
/*     */   private static final int INDEX_EXTID = 0;
/*     */   
/*     */   private static final int INDEX_TITLE = 1;
/*     */   
/*     */   private static final int INDEX_APPLICATIONS = 2;
/*     */   
/*     */   private static final int INDEX_GROUPS = 3;
/*     */   
/*     */   private static final int INDEX_STATUS = 4;
/*     */   
/*     */   private static final int INDEX_ACTION = 5;
/*     */   
/*     */   private HtmlElement headerAction;
/*     */   
/*     */   private LinkElement headerExtID;
/*     */   
/*     */   private LinkElement headerTitle;
/*     */   
/*     */   private HtmlElement headerApplications;
/*     */   
/*     */   private HtmlElement headerGroups;
/*     */   
/*     */   private LinkElement headerStatus;
/*     */   
/*  58 */   private Comparator comparator = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientContext context;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List entries;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SearchResultPanel parentPanel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getColumnCount() {
/* 167 */     return 6;
/*     */   }
/*     */   
/*     */   protected HtmlElement getHeader(int columnIndex) {
/* 171 */     switch (columnIndex) {
/*     */       case 0:
/* 173 */         return (HtmlElement)this.headerExtID;
/*     */       case 1:
/* 175 */         return (HtmlElement)this.headerTitle;
/*     */       case 2:
/* 177 */         return this.headerApplications;
/*     */       case 4:
/* 179 */         return (HtmlElement)this.headerStatus;
/*     */       case 3:
/* 181 */         return this.headerGroups;
/*     */       case 5:
/* 183 */         return this.headerAction;
/*     */     } 
/* 185 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   protected HtmlElement getContent(Object data, int columnIndex) {
/*     */     String title;
/* 190 */     IMessage msg = (IMessage)data;
/* 191 */     switch (columnIndex) {
/*     */       case 0:
/* 193 */         return (HtmlElement)new HtmlLabel(msg.getExternalID());
/*     */       case 1:
/* 195 */         title = MessageUtil.getTitle(msg, this.context.getLocale());
/* 196 */         if (title == null || title.trim().length() == 0) {
/* 197 */           title = MessageUtil.getTitle(msg, msg.getDefaultLocale());
/*     */         }
/* 199 */         return (HtmlElement)new HtmlLabel(title);
/*     */       
/*     */       case 2:
/* 202 */         return (HtmlElement)new HtmlLabel(getModulesLabel(msg));
/*     */       case 3:
/* 204 */         return (HtmlElement)new HtmlLabel(getGroupsLabel(msg));
/*     */       case 4:
/* 206 */         return (HtmlElement)new HtmlLabel(MessageUtil.getStatusLabel(msg.getStatus(), this.context.getLocale()));
/*     */       case 5:
/* 208 */         return getButtonPanel(msg);
/*     */     } 
/*     */     
/* 211 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/* 215 */   private static final Map ATTRIBUTES_TABLE = new HashMap<Object, Object>();
/*     */   
/* 217 */   private static final Map ATTRIBUTES_ROW_EVEN = new HashMap<Object, Object>();
/*     */   
/* 219 */   private static final Map ATTRIBUTES_ROW_ODD = new HashMap<Object, Object>();
/*     */   
/* 221 */   private static final Map ATTRIBUTES_HEADER = new HashMap<Object, Object>(); private Map msgToButtonPanel;
/*     */   
/*     */   static {
/* 224 */     ATTRIBUTES_TABLE.put("width", "100%");
/* 225 */     ATTRIBUTES_TABLE.put("class", "list");
/*     */     
/* 227 */     ATTRIBUTES_ROW_EVEN.put("class", "even");
/* 228 */     ATTRIBUTES_ROW_ODD.put("class", "odd");
/*     */     
/* 230 */     ATTRIBUTES_HEADER.put("class", "header");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesContent(int dataIndex, int columnIndex, Map map) {}
/*     */ 
/*     */   
/*     */   protected void getAdditionalAttributesHeader(int columnIndex, Map map) {
/* 238 */     map.putAll(ATTRIBUTES_HEADER);
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesRow(int rowIndex, Map map) {
/* 242 */     if (rowIndex % 2 == 0) {
/* 243 */       map.putAll(ATTRIBUTES_ROW_EVEN);
/*     */     } else {
/* 245 */       map.putAll(ATTRIBUTES_ROW_ODD);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void getAdditionalAttributesTable(Map map) {
/* 250 */     map.putAll(ATTRIBUTES_TABLE);
/*     */   }
/*     */   
/*     */   private String getModulesLabel(IMessage msg) {
/* 254 */     return MessageUtil.getModulesLabel(msg.getTargetModules(), this.context.getLocale());
/*     */   }
/*     */   
/*     */   private String getGroupsLabel(IMessage msg) {
/* 258 */     if (msg == null || msg.getUserGroups() == null) {
/* 259 */       return "-";
/*     */     }
/* 261 */     StringBuffer ret = new StringBuffer();
/* 262 */     for (Iterator<Group> iter = msg.getUserGroups().iterator(); iter.hasNext(); ) {
/* 263 */       Group group = iter.next();
/* 264 */       ret.append(group.toString().toUpperCase(Locale.ENGLISH));
/* 265 */       ret.append(", ");
/*     */     } 
/* 267 */     if (ret.length() > 0) {
/* 268 */       ret.delete(ret.length() - 2, ret.length());
/*     */     }
/* 270 */     return ret.toString();
/*     */   }
/*     */   
/* 273 */   public EntriesListElement(final List entries, final ClientContext context, SearchResultPanel parentPanel) { this.msgToButtonPanel = new HashMap<Object, Object>(); this.parentPanel = parentPanel; if (entries == null || entries.size() == 0) throw new IllegalArgumentException();  this.context = context; this.entries = entries; setDataCallback(new DataRetrievalAbstraction.DataCallback() { public List getData() { return entries; } }
/*     */       ); this.headerExtID = new LinkElement(context.createID(), null) { public Object onClick(Map submitParams) { try { if (IMessage.COMPARATOR_EXTID.equals(EntriesListElement.this.comparator)) { EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator); } else { EntriesListElement.this.comparator = IMessage.COMPARATOR_EXTID; }  Collections.sort(entries, EntriesListElement.this.comparator); } catch (Exception e) { EntriesListElement.log.error("...unable to sort  - exception: " + e, e); }  return null; } protected String getLabel() { return context.getLabel("identifier"); } }
/*     */       ; addElement((HtmlElement)this.headerExtID); final IMessage.TitleComparator COMPARATOR_TITLE = new IMessage.TitleComparator(context.getLocale()); this.headerTitle = new LinkElement(context.createID(), null) { public Object onClick(Map submitParams) { try { if (COMPARATOR_TITLE.equals(EntriesListElement.this.comparator)) { EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator); } else { EntriesListElement.this.comparator = COMPARATOR_TITLE; }  Collections.sort(entries, EntriesListElement.this.comparator); } catch (Exception e) { EntriesListElement.log.error("...unable to sort - exception: " + e, e); }  return null; } protected String getLabel() { return context.getLabel("title"); } }
/* 276 */       ; addElement((HtmlElement)this.headerTitle); this.headerApplications = (HtmlElement)new HtmlLabel(context.getLabel("application.s")); this.headerAction = (HtmlElement)new HtmlLabel(""); this.headerGroups = (HtmlElement)new HtmlLabel(context.getLabel("group.s")); this.headerStatus = new LinkElement(context.createID(), null) { public Object onClick(Map submitParams) { try { if (IMessage.COMPARATOR_STATUS.equals(EntriesListElement.this.comparator)) { EntriesListElement.this.comparator = Util.reverseComparator(EntriesListElement.this.comparator); } else { EntriesListElement.this.comparator = IMessage.COMPARATOR_STATUS; }  Collections.sort(entries, EntriesListElement.this.comparator); } catch (Exception e) { EntriesListElement.log.error("...unable to sort  - exception: " + e, e); }  return null; } protected String getLabel() { return context.getLabel("status"); } }; addElement((HtmlElement)this.headerStatus); this.comparator = IMessage.COMPARATOR_EXTID; Collections.sort(this.entries, this.comparator); } private synchronized HtmlElement getButtonPanel(final IMessage msg) { HtmlElementContainerBase htmlElementContainerBase; HtmlElementContainer ret = (HtmlElementContainer)this.msgToButtonPanel.get(msg);
/* 277 */     if (ret == null) {
/*     */       
/* 279 */       final ClickButtonElement buttonStatusToggle = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           private boolean isActive(IMessage msg) {
/* 282 */             return (msg.getStatus() == IMessage.Status.ACTIVE);
/*     */           }
/*     */           
/*     */           protected String getLabel() {
/* 286 */             return EntriesListElement.this.context.getLabel(isActive(msg) ? "deactivate" : "activate");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/*     */             try {
/* 291 */               IMessage.Status status = IMessage.Status.ACTIVE;
/* 292 */               if (isActive(msg)) {
/* 293 */                 status = IMessage.Status.INACTIVE;
/*     */               }
/* 295 */               MessageManager.getInstance().setStatus(msg, status);
/* 296 */               EntriesListElement.this.parentPanel.update();
/*     */             }
/* 298 */             catch (Exception e) {
/* 299 */               EntriesListElement.log.error("unable to switch status, ignoring - exception: " + e, e);
/*     */             } 
/* 301 */             return null;
/*     */           }
/*     */         };
/*     */       
/* 305 */       final ClickButtonElement buttonDetail = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 308 */             return EntriesListElement.this.context.getLabel("detail");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 312 */             final HtmlElementContainer toplevel = getTopLevelContainer();
/* 313 */             return new MessageDetailDialog(EntriesListElement.this.context, new MessageDetailDialog.Callback()
/*     */                 {
/*     */                   public Object onClose() {
/* 316 */                     return toplevel;
/*     */                   }
/*     */                   
/*     */                   public IMessage getMessage() {
/* 320 */                     return msg;
/*     */                   }
/*     */                 });
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 327 */       final ClickButtonElement buttonModify = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 330 */             return EntriesListElement.this.context.getLabel("modify");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 334 */             final HtmlElementContainer toplevel = getTopLevelContainer();
/* 335 */             return new MessageCMDialog(EntriesListElement.this.context, new MessageCMDialog.Callback()
/*     */                 {
/*     */                   public Object onCreateOrModify(IMessage oldMsg, IMessage newMsg) throws Exception {
/* 338 */                     MessageManager.getInstance().modify(newMsg);
/* 339 */                     EntriesListElement.this.parentPanel.update();
/* 340 */                     return toplevel;
/*     */                   }
/*     */ 
/*     */                   
/*     */                   public Object onClose() {
/* 345 */                     return toplevel;
/*     */                   }
/*     */                   
/*     */                   public int getMode() {
/* 349 */                     return 2;
/*     */                   }
/*     */                   
/*     */                   public IMessage getMessage() {
/* 353 */                     return msg;
/*     */                   }
/*     */                 });
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 362 */       final ClickButtonElement buttonDelete = new ClickButtonElement(this.context.createID(), null)
/*     */         {
/*     */           protected String getLabel() {
/* 365 */             return EntriesListElement.this.context.getLabel("delete");
/*     */           }
/*     */           
/*     */           public Object onClick(Map submitParams) {
/* 369 */             final Object ret = getTopLevelContainer();
/* 370 */             return new SimpleConfirmationMessageBox(EntriesListElement.this.context, EntriesListElement.this.context.getLabel("confirmation"), EntriesListElement.this.context.getMessage("confirmation.deletion"))
/*     */               {
/*     */                 protected Object onOK(Map params) {
/*     */                   try {
/* 374 */                     MessageManager.getInstance().delete(msg);
/* 375 */                     EntriesListElement.this.parentPanel.update();
/* 376 */                     return ret;
/* 377 */                   } catch (Exception e) {
/* 378 */                     return ErrorMessageBox.create((ClientContext)this.context, null, this.context.getMessage("unable.to.delete.msg"), ret);
/*     */                   } 
/*     */                 }
/*     */ 
/*     */                 
/*     */                 protected Object onCancel(Map params) {
/* 384 */                   return ret;
/*     */                 }
/*     */               };
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 393 */       htmlElementContainerBase = new HtmlElementContainerBase()
/*     */         {
/*     */           public String getHtmlCode(Map params) {
/* 396 */             StringBuffer ret = new StringBuffer();
/* 397 */             ret.append("<table><tr><td nowrap=\"nowrap\">");
/* 398 */             ret.append(buttonStatusToggle.getHtmlCode(params));
/* 399 */             ret.append("&nbsp;");
/* 400 */             ret.append(buttonModify.getHtmlCode(params));
/* 401 */             ret.append("&nbsp;");
/* 402 */             ret.append(buttonDetail.getHtmlCode(params));
/* 403 */             ret.append("&nbsp;");
/* 404 */             ret.append(buttonDelete.getHtmlCode(params));
/* 405 */             ret.append("</td></tr></table>");
/* 406 */             return ret.toString();
/*     */           }
/*     */         };
/*     */       
/* 410 */       htmlElementContainerBase.addElement((HtmlElement)buttonStatusToggle);
/* 411 */       htmlElementContainerBase.addElement((HtmlElement)buttonDetail);
/* 412 */       htmlElementContainerBase.addElement((HtmlElement)buttonModify);
/* 413 */       htmlElementContainerBase.addElement((HtmlElement)buttonDelete);
/* 414 */       addElement((HtmlElement)htmlElementContainerBase);
/* 415 */       this.msgToButtonPanel.put(msg, htmlElementContainerBase);
/*     */     } 
/*     */     
/* 418 */     return (HtmlElement)htmlElementContainerBase; }
/*     */ 
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\EntriesListElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */