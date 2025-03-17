/*     */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.bookmark;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.util.ObservableSupport;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.Locale;
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
/*  24 */   private static final Logger log = Logger.getLogger(Bookmark.class);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2318058238125499746L;
/*     */ 
/*     */   
/*     */   private int sioID;
/*     */ 
/*     */   
/*  33 */   private String name = null;
/*     */   
/*  35 */   private transient SIO sio = null;
/*     */   
/*  37 */   private transient ObservableSupport observableSupport = null;
/*     */   
/*     */   public Bookmark(int sioID, String name) {
/*  40 */     this.sioID = sioID;
/*  41 */     this.name = name;
/*  42 */     this.observableSupport = new ObservableSupport();
/*     */   }
/*     */   
/*     */   public Bookmark(int sioID) {
/*  46 */     this(sioID, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ObservableSupport getObservableSupport() {
/*  56 */     if (this.observableSupport == null) {
/*  57 */       this.observableSupport = new ObservableSupport();
/*     */     }
/*  59 */     return this.observableSupport;
/*     */   }
/*     */   
/*     */   public int getSIOID() {
/*  63 */     return this.sioID;
/*     */   }
/*     */   
/*     */   public String getName(Locale locale, ClientContext context) {
/*  67 */     String retValue = null;
/*  68 */     if (this.name != null) {
/*  69 */       retValue = this.name;
/*     */     } else {
/*  71 */       SIO sio = getSIO(context);
/*  72 */       retValue = sio.getLabel(LocaleInfoProvider.getInstance().getLocale(locale));
/*     */     } 
/*  74 */     return retValue;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  78 */     this.name = name;
/*  79 */     this.observableSupport.notify(new ObservableSupport.Notification() {
/*     */           public void notify(Object observer) {
/*     */             try {
/*  82 */               ((Bookmark.Observer)observer).onChange();
/*  83 */             } catch (Exception e) {
/*  84 */               Bookmark.log.error("unable to notify observer:" + observer + " - error:" + e, e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  92 */     if (this == obj)
/*  93 */       return true; 
/*  94 */     if (obj instanceof Bookmark) {
/*  95 */       Bookmark other = (Bookmark)obj;
/*  96 */       boolean ret = (this.sioID == other.sioID);
/*  97 */       return ret;
/*     */     } 
/*  99 */     return false;
/*     */   }
/*     */   public static interface Observer {
/*     */     void onChange(); }
/*     */   public int hashCode() {
/* 104 */     return this.sioID;
/*     */   }
/*     */   
/*     */   public synchronized SIO getSIO(ClientContext context) {
/* 108 */     if (this.sio == null) {
/* 109 */       this.sio = SIDataAdapterFacade.getInstance(context).getSI().getSIO(this.sioID);
/*     */     }
/* 111 */     return this.sio;
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 115 */     out.writeInt(this.sioID);
/* 116 */     out.writeObject(this.name);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 120 */     this.sioID = in.readInt();
/* 121 */     this.name = (String)in.readObject();
/* 122 */     this.observableSupport = new ObservableSupport();
/*     */   }
/*     */   
/*     */   public Bookmark() {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\bookmark\Bookmark.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */