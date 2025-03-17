/*    */ package com.eoos.gm.tis2web.frame.scout.nao;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NaoVspScout_Development
/*    */   extends NaoVspScout
/*    */ {
/*    */   protected boolean isValidLoginInfo(LoginInfo info) {
/* 14 */     return true;
/*    */   }
/*    */   
/*    */   protected boolean isSporefValid(Map params, boolean sporefValidation) {
/* 18 */     return true;
/*    */   }
/*    */   
/*    */   protected NaoVspCandidate getGroupMapWinner(Map params) {
/* 22 */     return new NaoVspCandidate(null, null, null, null, null, null, null, (String)params.get("group"), null);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\nao\NaoVspScout_Development.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */