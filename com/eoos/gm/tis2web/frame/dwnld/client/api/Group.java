/*    */ package com.eoos.gm.tis2web.frame.dwnld.client.api;
/*    */ import com.eoos.datatype.IVersionNumber;
/*    */ 
/*    */ public interface Group {
/*    */   String getDescription(Locale paramLocale);
/*    */   
/*    */   IDownloadUnit getDownloadUnit(IVersionNumber paramIVersionNumber);
/*    */   
/*    */   IVersionNumber getInstalledVersion(IInstalledVersionLookup.IRegistryLookup paramIRegistryLookup);
/*    */   
/*    */   IVersionNumber getNewestVersion();
/*    */   
/*    */   Collection getVersions(Filter paramFilter);
/*    */   
/*    */   public static final class DescriptionComparator implements Comparator {
/* 16 */     private static final Logger log = Logger.getLogger(DescriptionComparator.class);
/*    */     
/*    */     private Locale locale;
/*    */     
/*    */     public DescriptionComparator(Locale locale) {
/* 21 */       this.locale = locale;
/*    */     }
/*    */     
/*    */     public int compare(Object o1, Object o2) {
/* 25 */       int ret = 0;
/*    */       try {
/* 27 */         String desc1 = ((Group)o1).getDescription(this.locale);
/* 28 */         String desc2 = ((Group)o2).getDescription(this.locale);
/* 29 */         ret = Util.compare(desc1, desc2);
/* 30 */       } catch (Exception e) {
/* 31 */         log.warn("unable to compare " + String.valueOf(o1) + " with " + String.valueOf(o2) + ", returning 0 - exception: " + e, e);
/*    */       } 
/* 33 */       return ret;
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class UpdateVersionFilter
/*    */     implements Filter
/*    */   {
/*    */     private IVersionNumber referenceVersion;
/*    */     
/*    */     public UpdateVersionFilter(IVersionNumber referenceVersion) {
/* 43 */       this.referenceVersion = referenceVersion;
/*    */     }
/*    */     
/*    */     public boolean include(Object obj) {
/* 47 */       IVersionNumber vn = (IVersionNumber)obj;
/* 48 */       return (this.referenceVersion == null || Util.isHigher((Comparable)vn, (Comparable)this.referenceVersion));
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class ObsoleteVersionFilter implements Filter {
/*    */     private IVersionNumber referenceVersion;
/*    */     
/*    */     public ObsoleteVersionFilter(IVersionNumber referenceVersion) {
/* 56 */       this.referenceVersion = referenceVersion;
/*    */     }
/*    */     
/*    */     public boolean include(Object obj) {
/* 60 */       IVersionNumber vn = (IVersionNumber)obj;
/* 61 */       return (this.referenceVersion != null && Util.isLower((Comparable)vn, (Comparable)this.referenceVersion));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\Group.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */