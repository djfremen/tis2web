/*     */ package com.eoos.gm.tis2web.frame.msg.admin.ui.el;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ui.html.ContextualElementContainerBase;
/*     */ import com.eoos.gm.tis2web.frame.msg.admin.IMessage;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.gtwo.DataRetrievalAbstraction;
/*     */ import com.eoos.html.element.gtwo.SelectBoxSelectionElement;
/*     */ import com.eoos.html.element.input.ClickButtonElement;
/*     */ import com.eoos.html.element.input.TextInputElement;
/*     */ import com.eoos.html.renderer.HtmlTableRenderer;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class UserIDsInputElement
/*     */   extends ContextualElementContainerBase {
/*  23 */   private static final Logger log = Logger.getLogger(UserIDsInputElement.class);
/*     */   
/*     */   private TextInputElement inputUserID;
/*     */   
/*     */   private ClickButtonElement buttonAdd;
/*     */   
/*     */   private SelectBoxSelectionElement selectionUserIDs;
/*     */   
/*     */   private ClickButtonElement buttonRemove;
/*     */   
/*  33 */   private List users = new LinkedList();
/*     */   
/*  35 */   private HtmlTableRenderer.Callback renderingCallback = new HtmlTableRenderer.Callback()
/*     */     {
/*     */       public void init(Map params) {}
/*     */ 
/*     */       
/*     */       public boolean isHeader(int rowIndex, int columnIndex) {
/*  41 */         return false;
/*     */       }
/*     */       
/*     */       public int getRowCount() {
/*  45 */         return 2;
/*     */       }
/*     */       
/*     */       public String getContent(int rowIndex, int columnIndex) {
/*  49 */         if (rowIndex == 0) {
/*  50 */           if (columnIndex == 0) {
/*  51 */             return UserIDsInputElement.this.inputUserID.getHtmlCode(null);
/*     */           }
/*  53 */           return UserIDsInputElement.this.buttonAdd.getHtmlCode(null);
/*     */         } 
/*     */         
/*  56 */         if (columnIndex == 0) {
/*  57 */           return UserIDsInputElement.this.selectionUserIDs.getHtmlCode(null);
/*     */         }
/*  59 */         return UserIDsInputElement.this.buttonRemove.getHtmlCode(null);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public int getColumnCount() {
/*  65 */         return 2;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public UserIDsInputElement(final ClientContext context) {
/*  71 */     super(context);
/*     */     
/*  73 */     this.inputUserID = new TextInputElement(context.createID());
/*  74 */     addElement((HtmlElement)this.inputUserID);
/*     */     
/*  76 */     this.buttonAdd = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/*  79 */           return context.getLabel("add");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/*  84 */             UserIDsInputElement.this.onAdd();
/*  85 */           } catch (Exception e) {
/*  86 */             UserIDsInputElement.log.error("unable to add userid - exception:" + e, e);
/*     */           } 
/*  88 */           return null;
/*     */         }
/*     */       };
/*     */     
/*  92 */     addElement((HtmlElement)this.buttonAdd);
/*     */     
/*  94 */     DataRetrievalAbstraction.DataCallback dataCallback = new DataRetrievalAbstraction.DataCallback()
/*     */       {
/*     */         public List getData() {
/*  97 */           return UserIDsInputElement.this.users;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 102 */     this.selectionUserIDs = new SelectBoxSelectionElement(context.createID(), false, dataCallback, 5);
/* 103 */     addElement((HtmlElement)this.selectionUserIDs);
/*     */     
/* 105 */     this.buttonRemove = new ClickButtonElement(context.createID(), null)
/*     */       {
/*     */         protected String getLabel() {
/* 108 */           return context.getLabel("remove");
/*     */         }
/*     */         
/*     */         public Object onClick(Map submitParams) {
/*     */           try {
/* 113 */             UserIDsInputElement.this.onRemove();
/* 114 */           } catch (Exception e) {
/* 115 */             UserIDsInputElement.log.error("unable to remove userid(s) - exception: " + e, e);
/*     */           } 
/* 117 */           return null;
/*     */         }
/*     */       };
/*     */     
/* 121 */     addElement((HtmlElement)this.buttonRemove);
/*     */   }
/*     */   
/*     */   private void onAdd() throws Exception {
/* 125 */     String userID = (String)this.inputUserID.getValue();
/* 126 */     if (!Util.isNullOrEmpty(userID) && !this.users.contains(userID)) {
/* 127 */       this.users.add(userID);
/* 128 */       Collections.sort(this.users);
/* 129 */       this.inputUserID.setValue("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onRemove() throws Exception {
/* 135 */     Collection userIDs = (Collection)this.selectionUserIDs.getValue();
/* 136 */     if (!Util.isNullOrEmpty(userIDs)) {
/* 137 */       for (Iterator iter = userIDs.iterator(); iter.hasNext();) {
/* 138 */         this.users.remove(iter.next());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getValue() {
/* 144 */     return new LinkedList(this.users);
/*     */   }
/*     */   
/*     */   public void setValue(Object value) {
/* 148 */     this.users = new LinkedList((Collection)value);
/* 149 */     Collections.sort(this.users);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 153 */     return HtmlTableRenderer.getInstance().getHtmlCode(this.renderingCallback);
/*     */   }
/*     */   
/*     */   public static UserIDsInputElement create(ClientContext context, IMessage message) {
/* 157 */     UserIDsInputElement ret = new UserIDsInputElement(context);
/* 158 */     if (message != null && message.getUserIDs() != null) {
/* 159 */       ret.setValue(message.getUserIDs());
/*     */     }
/* 161 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admi\\ui\el\UserIDsInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */