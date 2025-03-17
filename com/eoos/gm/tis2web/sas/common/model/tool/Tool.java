/*    */ package com.eoos.gm.tis2web.sas.common.model.tool;
/*    */ 
/*    */ import com.eoos.datatype.Denotation;
/*    */ import com.eoos.gm.tis2web.sas.common.model.AccessType;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Tool
/*    */   extends Denotation
/*    */ {
/*    */   public static final String DRIVER_PROPERTY_TECH2WIN_COM_PORT = "tech2win_com_port";
/* 16 */   public static final Integer DRIVER_PROPERTY_CATEGORY_TECH2WIN_COM_PORT = Integer.valueOf(1003);
/*    */   
/*    */   SecurityAccessRequest readRequest(CommunicationSettings paramCommunicationSettings) throws Exception;
/*    */   
/*    */   AccessType[] writeResponse(SecurityAccessResponse paramSecurityAccessResponse) throws Exception;
/*    */   
/*    */   public static interface CommunicationSettings {
/*    */     Object getPort();
/*    */     
/*    */     Object getBaudrate();
/*    */   }
/*    */   
/*    */   public static class Exception extends Exception implements Serializable {
/*    */     protected static final long serialVersionUID = 1L;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\tool\Tool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */