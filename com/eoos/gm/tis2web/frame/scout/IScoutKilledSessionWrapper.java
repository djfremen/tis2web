/*    */ package com.eoos.gm.tis2web.frame.scout;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IScoutKilledSessionWrapper
/*    */   implements IScout
/*    */ {
/*    */   private IScout iscout;
/*    */   
/*    */   public IScoutKilledSessionWrapper(IScout iscout) {
/* 15 */     this.iscout = iscout;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LoginInfo getLoginInfo(Map params) {
/* 23 */     LoginInfo retValue = (LoginInfo)params.get("loginInfo");
/* 24 */     if (retValue == null) {
/* 25 */       retValue = this.iscout.getLoginInfo(params);
/*    */     }
/* 27 */     return retValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getScoutClassName() {
/* 35 */     return this.iscout.getScoutClassName();
/*    */   }
/*    */   
/*    */   public String getScoutId() {
/* 39 */     return this.iscout.getScoutId();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPortalMapped() {}
/*    */   
/*    */   public boolean isPortalMapped() {
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\IScoutKilledSessionWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */