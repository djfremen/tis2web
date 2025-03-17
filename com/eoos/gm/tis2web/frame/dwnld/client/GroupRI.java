/*    */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*    */ 
/*    */ import com.eoos.datatype.IVersionNumber;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.Group;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IInstalledVersionLookup;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import com.eoos.scsm.v2.filter.Filter;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class GroupRI
/*    */   implements Group
/*    */ {
/* 21 */   private Map versionToUnit = Collections.synchronizedMap(new HashMap<Object, Object>());
/*    */   
/* 23 */   private IDownloadUnit newestUnit = null;
/*    */   
/*    */   private GroupRI(Collection units) {
/* 26 */     if (Util.isNullOrEmpty(units)) {
/* 27 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 30 */     for (Iterator<IDownloadUnit> iter = units.iterator(); iter.hasNext(); ) {
/* 31 */       IDownloadUnit unit = iter.next();
/* 32 */       IVersionNumber vn = unit.getVersionNumber();
/* 33 */       if (this.newestUnit == null || Util.isLower((Comparable)this.newestUnit.getVersionNumber(), (Comparable)vn)) {
/* 34 */         this.newestUnit = unit;
/*    */       }
/* 36 */       this.versionToUnit.put(vn, unit);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getDescription(Locale locale) {
/* 41 */     return this.newestUnit.getDescripition(locale);
/*    */   }
/*    */   
/*    */   public IDownloadUnit getDownloadUnit(IVersionNumber versionNumber) {
/* 45 */     return (IDownloadUnit)this.versionToUnit.get(versionNumber);
/*    */   }
/*    */   
/*    */   public IVersionNumber getInstalledVersion(IInstalledVersionLookup.IRegistryLookup registryLookup) {
/* 49 */     return this.newestUnit.getInstalledVersionLookup().getInstalledVersion(registryLookup);
/*    */   }
/*    */   
/*    */   public IVersionNumber getNewestVersion() {
/* 53 */     return this.newestUnit.getVersionNumber();
/*    */   }
/*    */   
/*    */   public Collection getVersions(Filter filter) {
/* 57 */     Collection ret = new HashSet(this.versionToUnit.keySet());
/* 58 */     CollectionUtil.filter(ret, filter);
/* 59 */     return ret;
/*    */   }
/*    */   
/*    */   public static GroupRI createGroup(Collection units) {
/* 63 */     return new GroupRI(units);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     return "Group[" + String.valueOf(getDescription(Locale.ENGLISH)) + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\GroupRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */