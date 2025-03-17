/*    */ package com.eoos.gm.tis2web.sps.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ConfirmationRequest;
/*    */ import java.util.List;
/*    */ 
/*    */ public interface DownloadProgressDisplayRequest
/*    */   extends ConfirmationRequest {
/*    */   void addObserver(Observer paramObserver);
/*    */   
/*    */   void removeObserver(Observer paramObserver);
/*    */   
/*    */   void cancelDownload();
/*    */   
/*    */   public static interface Observer {
/* 17 */     public static final Status FINISHED = Status.getInstance("finished");
/* 18 */     public static final Status ERROR = Status.getInstance("error");
/*    */     
/*    */     void onRead(List param1List, ProgrammingDataUnit param1ProgrammingDataUnit, long param1Long);
/*    */     
/*    */     void onFinished(Status param1Status);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\DownloadProgressDisplayRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */