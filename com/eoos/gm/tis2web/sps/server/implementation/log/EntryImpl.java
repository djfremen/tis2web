/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*     */ 
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntryImpl
/*     */   implements SPSEventLog.LoggedEntry
/*     */ {
/*     */   private String servername;
/*     */   private long timestamp;
/*  17 */   private Adapter adapter = null;
/*     */   
/*     */   private String eventName;
/*     */   
/*  21 */   private final Object SYNC_ATTRIBUTES = new Object();
/*     */   
/*  23 */   private List attributes = null;
/*     */   
/*     */   private long identifier;
/*     */   
/*  27 */   private final Object SYNC_ATTACHMENT_KEYS = new Object();
/*  28 */   private Set attachmentKeys = null;
/*     */ 
/*     */   
/*     */   public EntryImpl(Adapter adapter, String eventName, String servername, long timestamp, long identifier) {
/*  32 */     this.eventName = trim(eventName);
/*  33 */     this.adapter = adapter;
/*  34 */     this.servername = trim(servername);
/*  35 */     this.timestamp = timestamp;
/*  36 */     this.identifier = identifier;
/*     */   }
/*     */   
/*     */   private static String trim(String string) {
/*  40 */     if (string == null) {
/*  41 */       return string;
/*     */     }
/*  43 */     return string.trim();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntryImpl(Adapter adapter, String eventName) {
/*  48 */     this(adapter, eventName, null, -1L, -1L);
/*     */   }
/*     */   
/*     */   public Adapter getAdapter() {
/*  52 */     return this.adapter;
/*     */   }
/*     */   
/*     */   public long getTimestamp() {
/*  56 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public String getServer() {
/*  60 */     return this.servername;
/*     */   }
/*     */   
/*     */   public String getEventName() {
/*  64 */     return this.eventName;
/*     */   }
/*     */   
/*     */   public List getEventAttributes() {
/*  68 */     synchronized (this.SYNC_ATTRIBUTES) {
/*  69 */       if (this.attributes == null) {
/*  70 */         this.attributes = initAttributes();
/*     */       }
/*  72 */       return this.attributes;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract List initAttributes();
/*     */   
/*     */   public String toString() {
/*  80 */     Map<Object, Object> map = new HashMap<Object, Object>(1);
/*  81 */     map.put("eventname", this.eventName);
/*  82 */     return Util.toString(this, map);
/*     */   }
/*     */   
/*     */   public long getIdentifier() {
/*  86 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public static EntryImpl create(Adapter adapter, String eventName, final List attributes) {
/*  90 */     return new EntryImpl(adapter, eventName)
/*     */       {
/*     */         protected List initAttributes() {
/*  93 */           return attributes;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getAttachmentKeys() {
/* 100 */     synchronized (this.SYNC_ATTACHMENT_KEYS) {
/* 101 */       if (this.attachmentKeys == null) {
/* 102 */         this.attachmentKeys = retrieveAttachmentKeys();
/* 103 */         return this.attachmentKeys;
/*     */       } 
/* 105 */       return this.attachmentKeys;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set retrieveAttachmentKeys() {
/* 111 */     return Collections.EMPTY_SET;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\EntryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */