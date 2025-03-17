/*    */ package com.eoos.gm.tis2web.si.implementation.statcont.datamodel;
/*    */ 
/*    */ import com.eoos.automat.StringAcceptor;
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.si.implementation.statcont.datamodel.system.DataProvider;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.LineNumberReader;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class TrackingList
/*    */ {
/*    */   private static final String FILENAME = "listofdocs";
/* 20 */   private static TrackingList instance = null;
/*    */   
/* 22 */   private List inclusionAcceptors = new LinkedList();
/*    */   
/* 24 */   private List exclusionAcceptors = new LinkedList();
/*    */   
/* 26 */   private Set inclusionSet = new HashSet();
/*    */ 
/*    */   
/*    */   private TrackingList() throws Exception {
/* 30 */     LineNumberReader lnr = new LineNumberReader(new InputStreamReader(new ByteArrayInputStream(DataProvider.getInstance().getData("listofdocs"))));
/* 31 */     String filename = null;
/* 32 */     while ((filename = lnr.readLine()) != null) {
/* 33 */       if (filename.trim().length() > 0) {
/* 34 */         if (filename.startsWith("!")) {
/* 35 */           filename = filename.substring(1);
/* 36 */           this.exclusionAcceptors.add(StringAcceptor.create(filename, true)); continue;
/* 37 */         }  if (filename.indexOf("*") != -1 || filename.indexOf("?") != -1) {
/* 38 */           this.inclusionAcceptors.add(StringAcceptor.create(filename, true)); continue;
/*    */         } 
/* 40 */         if (!filename.startsWith("/")) {
/* 41 */           filename = "/" + filename;
/*    */         }
/* 43 */         this.inclusionSet.add(filename.toLowerCase(Locale.ENGLISH));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized TrackingList getInstance() {
/* 50 */     if (instance == null) {
/*    */       try {
/* 52 */         instance = new TrackingList();
/* 53 */       } catch (Exception e) {
/* 54 */         throw new ExceptionWrapper(e);
/*    */       } 
/*    */     }
/* 57 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isIncluded(String filename) {
/* 61 */     boolean exclude = true;
/* 62 */     exclude = (exclude && !this.inclusionSet.contains(filename));
/* 63 */     for (Iterator<StringAcceptor> iterator1 = this.inclusionAcceptors.iterator(); iterator1.hasNext() && exclude;) {
/* 64 */       exclude = (exclude && !((StringAcceptor)iterator1.next()).accept(filename));
/*    */     }
/* 66 */     for (Iterator<StringAcceptor> iter = this.exclusionAcceptors.iterator(); iter.hasNext() && !exclude;) {
/* 67 */       exclude = (exclude || ((StringAcceptor)iter.next()).accept(filename));
/*    */     }
/* 69 */     return !exclude;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcont\datamodel\TrackingList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */