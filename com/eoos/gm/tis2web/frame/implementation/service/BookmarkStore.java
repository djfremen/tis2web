/*     */ package com.eoos.gm.tis2web.frame.implementation.service;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BookmarkStore
/*     */   extends HashMap
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8361806767176705342L;
/*     */   public static final int SI = 0;
/*     */   public static final int LT = 1;
/*     */   
/*     */   public void add(String user, int module, Integer sioID, String name) {
/*  20 */     BookmarkList bookmarks = (BookmarkList)get(user);
/*  21 */     if (bookmarks == null) {
/*  22 */       bookmarks = new BookmarkList();
/*  23 */       put((K)user, (V)bookmarks);
/*     */     } 
/*  25 */     bookmarks.add(module, sioID, name);
/*     */   }
/*     */   
/*     */   public static class BookmarkList implements Serializable {
/*     */     private static final long serialVersionUID = -1896589085898332127L;
/*     */     private List ltbm;
/*     */     private List sibm;
/*     */     
/*     */     public List getBookmarksSI() {
/*  34 */       return this.sibm;
/*     */     }
/*     */     
/*     */     public List getBookmarksLT() {
/*  38 */       return this.ltbm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(int module, Integer sioID, String name) {
/*  45 */       BookmarkStore.BookmarkRecord bm = new BookmarkStore.BookmarkRecord(sioID, name);
/*  46 */       if (module == 0) {
/*  47 */         if (this.sibm == null) {
/*  48 */           this.sibm = new ArrayList();
/*     */         }
/*  50 */         this.sibm.add(bm);
/*     */       } else {
/*  52 */         if (this.ltbm == null) {
/*  53 */           this.ltbm = new ArrayList();
/*     */         }
/*  55 */         this.ltbm.add(bm);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected boolean match(List a, List b) {
/*  60 */       if (a == null)
/*  61 */         return (b == null); 
/*  62 */       if (b == null) {
/*  63 */         return false;
/*     */       }
/*  65 */       return a.equals(b);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  71 */       if (this == obj)
/*  72 */         return true; 
/*  73 */       if (obj instanceof BookmarkList) {
/*  74 */         BookmarkList other = (BookmarkList)obj;
/*  75 */         boolean ret = (match(this.sibm, other.sibm) && match(this.ltbm, other.ltbm));
/*  76 */         return ret;
/*     */       } 
/*  78 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  84 */       int ret = BookmarkList.class.hashCode();
/*  85 */       ret = HashCalc.addHashCode(ret, this.sibm);
/*  86 */       ret = HashCalc.addHashCode(ret, this.ltbm);
/*  87 */       return ret;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BookmarkRecord implements Serializable {
/*     */     private static final long serialVersionUID = 9006567168675592549L;
/*     */     private Integer sioID;
/*     */     private String name;
/*     */     
/*     */     public BookmarkRecord(Integer sioID, String name) {
/*  97 */       this.sioID = sioID;
/*  98 */       this.name = name;
/*     */     }
/*     */     
/*     */     public Integer getSioID() {
/* 102 */       return this.sioID;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 106 */       return this.name;
/*     */     }
/*     */     
/*     */     protected boolean match(Object a, Object b) {
/* 110 */       if (a == null)
/* 111 */         return (b == null); 
/* 112 */       if (b == null) {
/* 113 */         return false;
/*     */       }
/* 115 */       return a.equals(b);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 121 */       if (this == obj)
/* 122 */         return true; 
/* 123 */       if (obj instanceof BookmarkRecord) {
/* 124 */         BookmarkRecord other = (BookmarkRecord)obj;
/* 125 */         boolean ret = (match(this.sioID, other.sioID) && match(this.name, other.name));
/* 126 */         return ret;
/*     */       } 
/* 128 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 134 */       int ret = BookmarkRecord.class.hashCode();
/* 135 */       ret = HashCalc.addHashCode(ret, this.sioID);
/* 136 */       ret = HashCalc.addHashCode(ret, this.name);
/* 137 */       return ret;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\BookmarkStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */