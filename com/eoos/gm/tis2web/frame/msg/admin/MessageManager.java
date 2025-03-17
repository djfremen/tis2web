/*     */ package com.eoos.gm.tis2web.frame.msg.admin;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.Group;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Task;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageManager
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(MessageManager.class);
/*     */   
/*  26 */   private static MessageManager instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized MessageManager getInstance() {
/*  33 */     if (instance == null) {
/*  34 */       instance = new MessageManager();
/*     */     }
/*  36 */     return instance;
/*     */   }
/*     */   
/*     */   public void createMessage(IMessage message) throws Exception {
/*  40 */     checkExternalID(message);
/*  41 */     DatabaseAdapter.getInstance().createMessage(message);
/*  42 */     resetActiveMessages();
/*     */   }
/*     */   
/*     */   public Collection getMessages(QueryFilter filter) throws Exception {
/*  46 */     return DatabaseAdapter.getInstance().getMessages(filter);
/*     */   }
/*     */   
/*     */   public boolean existsMessage(String messageID) throws Exception {
/*  50 */     return DatabaseAdapter.getInstance().existsMessage(messageID);
/*     */   }
/*     */   
/*     */   public void delete(IMessage msg) throws Exception {
/*  54 */     DatabaseAdapter.getInstance().deleteMessage(msg.getID());
/*  55 */     resetActiveMessages();
/*     */   }
/*     */   
/*     */   public void setStatus(IMessage msg, IMessage.Status status) throws Exception {
/*  59 */     DatabaseAdapter.getInstance().setStatus(msg, status);
/*  60 */     resetActiveMessages();
/*     */   }
/*     */   
/*     */   private static final class ResetTask implements Task {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public Object execute() {
/*  67 */       synchronized ((MessageManager.getInstance()).SYNC_ACTIVE_MSGS) {
/*  68 */         (MessageManager.getInstance()).activeMessages = null;
/*     */       } 
/*  70 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     private ResetTask() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static class InvalidInputException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     public static final int EXTERNAL_ID = 0;
/*     */     public static final int NO_APPLICATION_SELECTION = 1;
/*     */     public static final int NO_GROUP_SELECTION = 2;
/*     */     public static final int NO_DEFAULT_LOCALE_SELECTION = 3;
/*     */     public static final int TYPE = 3;
/*     */     private int reason;
/*     */     
/*     */     public InvalidInputException(int reason) {
/*  90 */       this.reason = reason;
/*     */     }
/*     */     
/*     */     public int getReason() {
/*  94 */       return this.reason;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void checkExternalID(IMessage msgData) throws InvalidInputException {
/*  99 */     String externalID = msgData.getExternalID();
/* 100 */     if (externalID == null || externalID.trim().length() == 0) {
/* 101 */       throw new InvalidInputException(0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void modify(IMessage newMsg) throws Exception {
/* 106 */     DatabaseAdapter.getInstance().modify(newMsg);
/* 107 */     resetActiveMessages();
/*     */   }
/*     */   
/* 110 */   private final Object SYNC_ACTIVE_MSGS = new Object();
/*     */   
/* 112 */   private Collection activeMessages = null;
/*     */   
/* 114 */   private long ts_activeMsgs = System.currentTimeMillis();
/*     */   
/*     */   private Collection getActiveMessages() throws Exception {
/* 117 */     synchronized (this.SYNC_ACTIVE_MSGS) {
/* 118 */       if (this.activeMessages == null || System.currentTimeMillis() - this.ts_activeMsgs > 300000L) {
/* 119 */         this.activeMessages = DatabaseAdapter.getInstance().getActiveMessages();
/* 120 */         this.ts_activeMsgs = System.currentTimeMillis();
/*     */       } 
/* 122 */       return this.activeMessages;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetActiveMessages() {
/* 127 */     ClusterTaskExecution cte = new ClusterTaskExecution(new ResetTask(), null);
/* 128 */     cte.execute();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getMessages(String moduleType, ClientContext context) {
/* 133 */     return getMessages(Module.getInstance(moduleType), context);
/*     */   }
/*     */   
/*     */   private List getMessages(Module module, ClientContext context) {
/* 137 */     if (log.isDebugEnabled()) {
/* 138 */       log.debug("retrieving suitable messages for module: " + module.getDenotation(Locale.ENGLISH) + " and context: " + context);
/*     */     }
/*     */     
/* 141 */     log.debug("...determining user group(s)");
/* 142 */     Set<Group> userGroups = null;
/*     */     try {
/* 144 */       userGroups = new LinkedHashSet();
/* 145 */       Set tmp = SharedContext.getInstance(context).getUsrGroup2ManufMap().keySet();
/* 146 */       for (Iterator<String> iterator = tmp.iterator(); iterator.hasNext();) {
/* 147 */         userGroups.add(Group.getInstance(iterator.next()));
/*     */       }
/* 149 */     } catch (Exception e) {
/* 150 */       log.error("unable to determine user group(s), continuing with empty set - exception:" + e, e);
/* 151 */       userGroups = Collections.EMPTY_SET;
/*     */     } 
/* 153 */     if (log.isDebugEnabled()) {
/* 154 */       log.debug("...user groups: " + userGroups);
/*     */     }
/*     */     
/* 157 */     List<IMessage> ret = new LinkedList();
/* 158 */     Collection activeMessages = null;
/*     */     try {
/* 160 */       activeMessages = getActiveMessages();
/* 161 */     } catch (Exception e) {
/* 162 */       log.error("unable to retrieve active messages, continuing with empty collection - exception: " + e, e);
/* 163 */       activeMessages = Collections.EMPTY_SET;
/*     */     } 
/*     */     
/* 166 */     for (Iterator<IMessage> iter = activeMessages.iterator(); iter.hasNext(); ) {
/* 167 */       IMessage msg = iter.next();
/* 168 */       boolean include = msg.getTargetModules().contains(module);
/* 169 */       if (!Util.isNullOrEmpty(msg.getUserIDs())) {
/* 170 */         include = (include && Util.contains_IgnoreCase(msg.getUserIDs(), context.getSessionID()));
/*     */       } else {
/* 172 */         include = (include && CollectionUtil.haveIntersection(msg.getUserGroups(), userGroups));
/*     */       } 
/* 174 */       if (include) {
/* 175 */         ret.add(msg);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admin\MessageManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */