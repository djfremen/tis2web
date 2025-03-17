/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.bookmark;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.util.ObservableSupport;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Bookmark
/*     */   implements Externalizable
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(Bookmark.class);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -8481301961223250781L;
/*     */ 
/*     */   
/*     */   private int sioID;
/*     */ 
/*     */   
/*  32 */   private String name = null;
/*     */   
/*  34 */   private transient ObservableSupport observableSupport = null;
/*     */   
/*     */   public Bookmark(int sioID, String name) {
/*  37 */     this.sioID = sioID;
/*  38 */     this.name = name;
/*  39 */     this.observableSupport = new ObservableSupport();
/*     */   }
/*     */   
/*     */   public Bookmark(int sioID) {
/*  43 */     this(sioID, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ObservableSupport getObservableSupport() {
/*  53 */     if (this.observableSupport == null) {
/*  54 */       this.observableSupport = new ObservableSupport();
/*     */     }
/*  56 */     return this.observableSupport;
/*     */   }
/*     */   
/*     */   public int getSIOID() {
/*  60 */     return this.sioID;
/*     */   }
/*     */   
/*     */   public String getName(ClientContext context) {
/*  64 */     String retValue = null;
/*  65 */     if (this.name != null) {
/*  66 */       retValue = this.name;
/*     */     } else {
/*     */       try {
/*  69 */         SIOLT tmp = getSIOLTElement(context);
/*  70 */         retValue = tmp.getLabel(LocaleInfoProvider.getInstance().getLocale(context.getLocale()));
/*  71 */       } catch (NullPointerException e) {
/*  72 */         retValue = "";
/*     */       } 
/*     */     } 
/*  75 */     return retValue;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  79 */     this.name = name;
/*  80 */     this.observableSupport.notify(new ObservableSupport.Notification() {
/*     */           public void notify(Object observer) {
/*     */             try {
/*  83 */               ((Bookmark.Observer)observer).onChange();
/*  84 */             } catch (Exception e) {
/*  85 */               Bookmark.log.error("unable to notify observer:" + observer + " - error:" + e, e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  93 */     if (this == obj)
/*  94 */       return true; 
/*  95 */     if (obj instanceof Bookmark) {
/*  96 */       Bookmark other = (Bookmark)obj;
/*  97 */       boolean ret = (this.sioID == other.sioID);
/*  98 */       return ret;
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */   public static interface Observer {
/*     */     void onChange(); }
/*     */   public int hashCode() {
/* 105 */     return this.sioID;
/*     */   }
/*     */   
/*     */   public synchronized SIOLT getSIOLTElement(ClientContext context) {
/* 109 */     SIOLT retValue = (SIOLT)LTDataAdapterFacade.getInstance(context).getLT().getSITOCElement(this.sioID);
/* 110 */     return retValue;
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 114 */     out.writeInt(this.sioID);
/* 115 */     out.writeObject(this.name);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 119 */     this.sioID = in.readInt();
/* 120 */     this.name = (String)in.readObject();
/* 121 */     this.observableSupport = new ObservableSupport();
/*     */   }
/*     */   
/*     */   public Bookmark() {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\bookmark\Bookmark.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */