/*     */ package com.eoos.ref.v3;
/*     */ 
/*     */ import com.eoos.scsm.v2.condition.Condition;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.scsm.v2.util.AssertUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Date;
/*     */ import java.util.TimerTask;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class TimedMutationReference
/*     */   implements IReference, IReference.Snoopable {
/*  17 */   private static final Logger log = Logger.getLogger(TimedMutationReference.class);
/*     */   
/*     */   private static class MutationTask
/*     */     extends TimerTask {
/*  21 */     private static final Logger log = Logger.getLogger(MutationTask.class);
/*     */     
/*  23 */     private WeakReference wr = null;
/*     */     
/*     */     public MutationTask(TimedMutationReference ref) {
/*  26 */       this.wr = new WeakReference<TimedMutationReference>(ref);
/*     */     }
/*     */     
/*     */     public synchronized void run() {
/*     */       try {
/*  31 */         TimedMutationReference ref = null;
/*     */         
/*  33 */         if (this.wr != null && (ref = this.wr.get()) != null) {
/*  34 */           synchronized (ref) {
/*     */ 
/*     */ 
/*     */             
/*  38 */             long diff = ref.getMutationTime() - System.currentTimeMillis();
/*  39 */             if (diff <= 0L) {
/*     */               
/*  41 */               if (log.isDebugEnabled()) {
/*  42 */                 log.debug("mutating " + ref);
/*     */               }
/*  44 */               Object _rescue = ref.snoop();
/*  45 */               ref.referent = ref.changeType(ref.referent, ref.targetType);
/*  46 */               ref.currentState = TimedMutationReference.State.MUTATED;
/*  47 */               ref.onMutation(_rescue);
/*     */             }
/*     */             else {
/*     */               
/*  51 */               if (log.isDebugEnabled()) {
/*  52 */                 log.debug("rescheduling mutation of " + ref + " (new mutation time:" + Util.formatDate(ref.getMutationTime()) + ")");
/*     */               }
/*  54 */               Util.getTimer().schedule(ref.createMutationTask(), new Date(ref.getMutationTime()));
/*     */             } 
/*     */           } 
/*     */         }
/*  58 */       } catch (Throwable t) {
/*  59 */         log.error("unable to execute - exception: ", t);
/*     */       } 
/*     */     }
/*     */     
/*     */     public synchronized boolean cancel() {
/*  64 */       this.wr = null;
/*  65 */       boolean retValue = super.cancel();
/*  66 */       return retValue;
/*     */     }
/*     */   }
/*     */   
/*     */   protected enum State
/*     */   {
/*  72 */     SCHEDULED, MUTATED, CLEARED;
/*     */   }
/*     */   
/*     */   public enum Type {
/*  76 */     HARD, SOFT, WEAK, NULL;
/*     */   }
/*     */   
/*  79 */   private MutationTask mutationTask = null;
/*     */ 
/*     */   
/*     */   private long lastAccess;
/*     */   
/*     */   private State currentState;
/*     */   
/*     */   private Object referent;
/*     */   
/*     */   private Type initalType;
/*     */   
/*     */   private Type targetType;
/*     */   
/*     */   private long periodInactivity;
/*     */   
/*     */   private boolean resurrectable;
/*     */   
/*     */   private ReferenceQueue queue;
/*     */ 
/*     */   
/*     */   public TimedMutationReference(Object referent, Type initalType, Type targetType, long maxInactivity, boolean resurrectable, ReferenceQueue queue) {
/* 100 */     this.referent = referent;
/* 101 */     this.initalType = initalType;
/* 102 */     this.targetType = targetType;
/* 103 */     this.periodInactivity = maxInactivity;
/* 104 */     this.resurrectable = resurrectable;
/*     */     
/* 106 */     AssertUtil.ensure(new Condition()
/*     */         {
/*     */           public boolean check(Object obj) {
/* 109 */             return (TimedMutationReference.this.targetType != TimedMutationReference.Type.NULL || !TimedMutationReference.this.resurrectable);
/*     */           }
/*     */         });
/*     */     
/* 113 */     this.lastAccess = System.currentTimeMillis();
/* 114 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public TimedMutationReference(Object referent, Type initalType, Type targetType, long maxInactivity, boolean resurrectable) {
/* 119 */     this(referent, initalType, targetType, maxInactivity, resurrectable, null);
/*     */   }
/*     */   
/*     */   private void init() {
/* 123 */     this.referent = changeType(this.referent, this.initalType);
/* 124 */     Util.getTimer().schedule(createMutationTask(), new Date(getMutationTime()));
/* 125 */     this.currentState = State.SCHEDULED;
/*     */   }
/*     */   
/*     */   private long getMutationTime() {
/* 129 */     return this.lastAccess + this.periodInactivity;
/*     */   }
/*     */   
/*     */   public State getState() {
/* 133 */     return this.currentState;
/*     */   }
/*     */   
/*     */   private static Object get(Object referent) {
/* 137 */     Object retValue = referent;
/* 138 */     if (retValue != null && retValue instanceof Reference) {
/* 139 */       retValue = ((Reference)retValue).get();
/*     */     }
/* 141 */     return retValue;
/*     */   }
/*     */   
/*     */   private Object changeType(Object referent, Type newType) {
/* 145 */     Object retValue = null;
/* 146 */     if (newType == Type.HARD) {
/* 147 */       retValue = get(referent);
/* 148 */     } else if (newType == Type.SOFT) {
/* 149 */       retValue = new SoftReference(get(referent), this.queue);
/* 150 */     } else if (newType == Type.WEAK) {
/* 151 */       retValue = new WeakReference(get(referent), this.queue);
/*     */     } 
/* 153 */     return retValue;
/*     */   }
/*     */   
/*     */   private MutationTask createMutationTask() {
/* 157 */     this.mutationTask = new MutationTask(this);
/* 158 */     return this.mutationTask;
/*     */   }
/*     */   
/*     */   public synchronized Object get() {
/* 162 */     Object retValue = get(this.referent);
/* 163 */     this.lastAccess = System.currentTimeMillis();
/* 164 */     if (retValue != null && this.currentState == State.MUTATED && this.resurrectable) {
/* 165 */       resurrect(retValue);
/*     */     }
/* 167 */     return retValue;
/*     */   }
/*     */   
/*     */   public synchronized Object snoop() {
/* 171 */     return get(this.referent);
/*     */   }
/*     */   
/* 174 */   private static transient CharSequence clazzName = null;
/*     */   
/*     */   private static synchronized CharSequence getClassName() {
/* 177 */     if (clazzName == null) {
/* 178 */       clazzName = Util.getClassName(TimedMutationReference.class);
/*     */     }
/* 180 */     return clazzName;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 184 */     StringBuffer ret = StringBufferPool.getThreadInstance().get(getClassName());
/*     */     try {
/* 186 */       ret.append("(" + getCurrentReferenceType() + ") ");
/* 187 */       if (getCurrentReferenceType() != Type.NULL) {
/* 188 */         ret.append("to " + Util.toString(get(this.referent)));
/*     */       }
/* 190 */       return ret.toString();
/*     */     } finally {
/*     */       
/* 193 */       StringBufferPool.getThreadInstance().free(ret);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Type getCurrentReferenceType() {
/* 198 */     if (this.referent == null)
/* 199 */       return Type.NULL; 
/* 200 */     if (this.referent instanceof WeakReference)
/* 201 */       return Type.WEAK; 
/* 202 */     if (this.referent instanceof SoftReference) {
/* 203 */       return Type.SOFT;
/*     */     }
/* 205 */     return Type.HARD;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resurrect(Object referent) {
/* 210 */     this.referent = referent;
/* 211 */     init();
/* 212 */     onResurrected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMutation(Object referent) {}
/*     */   
/*     */   protected void onResurrected() {
/* 219 */     if (log.isDebugEnabled()) {
/* 220 */       log.debug("resurrected " + this);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void clear() {
/* 225 */     if (this.currentState != State.CLEARED) {
/* 226 */       this.mutationTask.cancel();
/* 227 */       this.referent = null;
/*     */     } 
/*     */     
/* 230 */     this.currentState = State.CLEARED;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\ref\v3\TimedMutationReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */