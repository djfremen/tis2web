/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.datatype.IVersionNumber;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IInstalledVersionLookup;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ public class RegistryVersionLookupContainer
/*    */   implements IInstalledVersionLookup, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Collection delegates;
/*    */   
/*    */   public RegistryVersionLookupContainer(Collection delegates) {
/* 17 */     this.delegates = delegates;
/*    */   }
/*    */   
/*    */   public IVersionNumber getInstalledVersion(IInstalledVersionLookup.IRegistryLookup registryLookup) {
/* 21 */     IVersionNumber ret = null;
/* 22 */     for (Iterator<IInstalledVersionLookup> iter = this.delegates.iterator(); iter.hasNext() && ret == null;) {
/* 23 */       ret = ((IInstalledVersionLookup)iter.next()).getInstalledVersion(registryLookup);
/*    */     }
/* 25 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\RegistryVersionLookupContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */