/*     */ package com.eoos.gm.tis2web.sps.client.ui.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventsBlocker
/*     */   extends EventQueue
/*     */ {
/*  22 */   private List permittedObj = new ArrayList();
/*  23 */   private List restrictedEvs = new ArrayList();
/*     */   private boolean blocked = false;
/*     */   private boolean alreadyBlockedOnce = false;
/*  26 */   private static EventsBlocker instance = null;
/*  27 */   protected static final Logger log = Logger.getLogger(EventsBlocker.class);
/*     */   
/*     */   public static synchronized EventsBlocker getInstance() {
/*  30 */     if (instance == null) {
/*  31 */       instance = new EventsBlocker();
/*     */     }
/*  33 */     return instance;
/*     */   }
/*     */   
/*     */   public EventsBlocker() {
/*  37 */     this.restrictedEvs.add(Integer.valueOf(500));
/*  38 */     this.restrictedEvs.add(Integer.valueOf(501));
/*  39 */     this.restrictedEvs.add(Integer.valueOf(502));
/*  40 */     this.restrictedEvs.add(Integer.valueOf(503));
/*  41 */     this.restrictedEvs.add(Integer.valueOf(504));
/*  42 */     this.restrictedEvs.add(Integer.valueOf(505));
/*  43 */     this.restrictedEvs.add(Integer.valueOf(506));
/*  44 */     this.restrictedEvs.add(Integer.valueOf(401));
/*  45 */     this.restrictedEvs.add(Integer.valueOf(1001));
/*  46 */     this.restrictedEvs.add(Integer.valueOf(1));
/*  47 */     this.restrictedEvs.add(Integer.valueOf(701));
/*  48 */     this.restrictedEvs.add(Integer.valueOf(2));
/*     */   }
/*     */   
/*     */   public void resetPermittedComponents() {
/*  52 */     this.permittedObj.clear();
/*     */   }
/*     */   
/*     */   public void addPermittedComponent(Object comp) {
/*  56 */     if (!this.permittedObj.contains(comp)) {
/*  57 */       this.permittedObj.add(comp);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removePermittedComponent(Object comp) {
/*  62 */     this.permittedObj.remove(comp);
/*     */   }
/*     */   
/*     */   public void resetRestrictedEvents() {
/*  66 */     this.restrictedEvs.clear();
/*     */   }
/*     */   
/*     */   public void addRestrictedEvent(Integer eventID) {
/*  70 */     if (!this.restrictedEvs.contains(eventID)) {
/*  71 */       this.restrictedEvs.add(eventID);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeRestrictedEvent(Integer eventID) {
/*  76 */     this.restrictedEvs.remove(eventID);
/*     */   }
/*     */   
/*     */   public void setBlockingEnabled(boolean block) {
/*  80 */     if (!this.alreadyBlockedOnce) {
/*  81 */       Toolkit.getDefaultToolkit().getSystemEventQueue().push(this);
/*  82 */       this.alreadyBlockedOnce = true;
/*     */     } 
/*  84 */     this.blocked = block;
/*     */   }
/*     */   
/*     */   protected void dispatchEvent(AWTEvent event) {
/*  88 */     if (this.blocked) {
/*  89 */       Object obj = event.getSource();
/*  90 */       if (event instanceof MouseEvent) {
/*  91 */         MouseEvent ev = (MouseEvent)event;
/*  92 */         Component comp = ev.getComponent();
/*  93 */         obj = SwingUtilities.getDeepestComponentAt(comp, ev.getX(), ev.getY());
/*     */       } 
/*  95 */       if (this.restrictedEvs.contains(Integer.valueOf(event.getID())) && !this.permittedObj.contains(obj)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     try {
/* 100 */       super.dispatchEvent(event);
/* 101 */     } catch (Exception x) {
/* 102 */       log.debug("failed to dispatch event" + x);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\event\EventsBlocker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */